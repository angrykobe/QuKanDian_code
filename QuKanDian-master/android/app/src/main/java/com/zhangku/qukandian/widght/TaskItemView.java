package com.zhangku.qukandian.widght;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.AnimUtil;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.ToastUtils;

/**
 * 创建者          xuzhida
 * 创建日期        2018/9/27
 * 你不注释一下？
 */
public class TaskItemView extends LinearLayout {

    private TextView contentTV;
    private TextView titleTV;
    private TextView subtitleTV;
    private TextView goldNumTV;
    private ImageView titleIconIV;
    private View arrowImg;
    private TextView doTaskTV;
    private View titleView;

    private View processView;
    private View mOcclusionView;
    private TextView processTV;
    private ProgressBar progressBar;

    private final int animTime = 400;
    private TaskBean bean;

    public TaskItemView(Context context) {
        this(context, null);
    }

    public TaskItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    private void initUI() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_task_item, this);
        arrowImg = findViewById(R.id.arrowImg);
        mOcclusionView = findViewById(R.id.mOcclusionView);
        doTaskTV = findViewById(R.id.doTaskTV);
        titleView = findViewById(R.id.titleView);
        titleTV = findViewById(R.id.titleTV);
        titleIconIV = findViewById(R.id.titleIconIV);
        goldNumTV = findViewById(R.id.goldNumTV);

        processView = findViewById(R.id.processView);
        processTV = findViewById(R.id.processTV);
        progressBar = findViewById(R.id.progressBar);

        contentTV = findViewById(R.id.contentTV);


        subtitleTV = findViewById(R.id.subtitleTV);
        arrowImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentTV.getVisibility() == View.GONE) {
                    bean.setOpen(true);
                    AnimUtil.animTaskOpen(contentTV, arrowImg, animTime, bean.getContentHeight());
                } else {
                    bean.setOpen(false);
                    AnimUtil.animTaskClose(contentTV, arrowImg, animTime, bean.getContentHeight());
                }
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @SuppressLint("ResourceAsColor")
    public void setData(final TaskBean bean) {
        this.bean = bean;
        this.setOnClickListener(null);
        this.setAlpha(1f);

        //头部名称
        if (bean.getClassifyId() == 1) {
            titleTV.setText("新手任务");
            titleIconIV.setImageResource(R.mipmap.icon_task_newpeople);
        } else if (bean.getClassifyId() == 2) {
            titleTV.setText("幸运任务");
            titleIconIV.setImageResource(R.mipmap.icon_task_luck);
        } else if (bean.getClassifyId() == 3) {
            titleTV.setText("收徒任务");
            titleIconIV.setImageResource(R.mipmap.icon_task_shoutu);
        } else if (bean.getClassifyId() == 4) {
            titleTV.setText("阅读任务");
            titleIconIV.setImageResource(R.mipmap.icon_task_read);
        }

        if (Constants.FIRST_RESOU.equals(bean.getName())) {
            if (!(UserManager.getInst().getmRuleBean().getSougouGoldRule().getDPT() <= UserManager.getInst().getUserBeam().getGoldAccount().getSum()
                    && (UserManager.getInst().getmRuleBean().getSougouGoldRule().getDptMax() == 0
                    || UserManager.getInst().getmRuleBean().getSougouGoldRule().getDptMax() >= UserManager.getInst().getUserBeam().getGoldAccount().getSum()))) {
                //隐藏
                this.subtitleTV.setText("神秘任务");
                this.goldNumTV.setText("+???");
                mOcclusionView.setVisibility(View.VISIBLE);
                mOcclusionView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showTostShortCentent(getContext(), "即将解锁，先去获得其它新手福利吧");
                    }
                });
                this.setAlpha(0.5f);
                return;
            }
        }
        mOcclusionView.setVisibility(View.GONE);

        subtitleTV.setText("" + bean.getDisplayName());
        contentTV.setText("" + bean.getDescription());
        goldNumTV.setText((TextUtils.isEmpty(bean.getCoinAmountScope()) ? "" : bean.getCoinAmountScope() + "元+")
                + (TextUtils.isEmpty(bean.getGoldAmountScope()) ? "" : "+" + bean.getGoldAmountScope()));
        //多任务
        if (bean.getKindType() == 4) {
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    processView.performClick();
                }
            });
            processView.setVisibility(View.VISIBLE);
            doTaskTV.setVisibility(View.GONE);
            processTV.setTextColor(ContextCompat.getColor(getContext(), R.color.black_33));//
            progressBar.setMax(bean.getAwardsTime());
            progressBar.setProgress(bean.getFinishedTime());
            processTV.setText(bean.getFinishedTime() + "/" + bean.getAwardsTime());
            if (Constants.FIRST_RESOU.equals(bean.getName())) {
                if (bean.getFinishedTime() == bean.getAwardsTime()) {
                    if (bean.isButtonEnable()) {
                        processTV.setText("可领取");
                        processTV.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        processTV.setText("已领取");
                    }
                }
            }
        } else {
            processView.setVisibility(View.GONE);
            //是否显示按钮 ？？
            if (null != bean.getBindingButton()) {
                doTaskTV.setVisibility(View.VISIBLE);
                doTaskTV.setText(bean.getBindingButton());
            } else {
                doTaskTV.setVisibility(View.GONE);
            }
        }

        //任务详情显示
        if (bean.isOpen()) {
            open();
        } else {
            close();
        }
    }

    public View getTitleView() {
        return titleView;
    }

    public TextView getDoTaskTV() {
        return doTaskTV;
    }

    public void open() {
        contentTV.setVisibility(View.VISIBLE);
        arrowImg.setRotation(180);
    }

    public void close() {
        contentTV.setVisibility(View.GONE);
        arrowImg.setRotation(0);
    }

    public View getProcessView() {
        return processView;
    }

    public ProgressBar getProcessBar() {
        return progressBar;
    }
}
