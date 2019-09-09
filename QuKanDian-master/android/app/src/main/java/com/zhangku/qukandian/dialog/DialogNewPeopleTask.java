package com.zhangku.qukandian.dialog;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.DialogNewPeopleTaskAdapter;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewTaskForNewPeoplePro;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AnimUtil;
import com.zhangku.qukandian.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by yuzuoning on 2018/1/3.
 */
public class DialogNewPeopleTask extends BaseDialog implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private DialogNewPeopleTaskAdapter mAdapter;
    private List<NewPeopleTaskBean> mList = new ArrayList<>();
    private DismissListener mListener;
    private TextView mGoldTV;
    private TextView mWithdrawalsTV;
    private LinearLayoutManager mLayout;
    private boolean isTrueDis = false;
    private ObjectAnimator objectAnimator;
    private Thread thread;

    public DialogNewPeopleTask(Context context, List<NewPeopleTaskBean> response, DismissListener listener) {
        super(context, R.style.zhangku_dialog);
        this.mList = response;
        mListener = listener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_new_people_task;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.mRecyclerView);

        mGoldTV = findViewById(R.id.mGoldTV);
        View topView = findViewById(R.id.topView);
        mWithdrawalsTV = findViewById(R.id.mWithdrawalsTV);
        mLayout = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayout);
        mAdapter = new DialogNewPeopleTaskAdapter(getContext(), mList, this);
        mRecyclerView.setAdapter(mAdapter);
        mWithdrawalsTV.setOnClickListener(this);
        initBtn();
        int v = (int) (Config.SCREEN_HEIGHT * 0.1);
        mRecyclerView.setPadding(v, 0, v, 0);
        topView.setPadding(v, DisplayUtils.dip2px(mContext, 60), v, DisplayUtils.dip2px(mContext, 20));
        findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void dismissWithoutAnim() {
        isTrueDis = true;
        dismiss();
    }

    @Override
    protected boolean isCanceledOnTouchOutside() {
        return true;
    }

    private void initBtn() {
        if (mWithdrawalsTV != null) {
            if (UserManager.getInst().getUserBeam().getGoldAccount().getAmount() < 10000 || UserManager.getInst().getUserBeam().isHeben()) {
                mWithdrawalsTV.setSelected(true);
                mWithdrawalsTV.setOnClickListener(null);
            } else {
                mWithdrawalsTV.setSelected(false);
                mWithdrawalsTV.setOnClickListener(this);
                AnimUtil.animBigMore(mWithdrawalsTV);
            }

            final int lastGoldNum = Integer.valueOf(mGoldTV.getText().toString());
            final int nowGoldNum = UserManager.getInst().getUserBeam().getGoldAccount().getAmount();
            final int disNum = nowGoldNum - lastGoldNum;
            startAnimForGold(disNum);
        }
    }

    public void startAnimForGold(final int disNum) {
        final int disCha = disNum / 50;
        final int lastGoldNum = Integer.valueOf(mGoldTV.getText().toString());

        if (thread != null) return;
        if (disNum > 50) {
            if (thread == null) {
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= disNum; i += disCha) {
                            final int j = i;
                            try {
                                Thread.sleep(60);
                                mGoldTV.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mGoldTV.setText(lastGoldNum + j + "");
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        mGoldTV.post(new Runnable() {
                            @Override
                            public void run() {
                                mGoldTV.setText("" + UserManager.getInst().getUserBeam().getGoldAccount().getAmount());
                            }
                        });
                        thread = null;
                    }
                });
                thread.start();
            }
        } else {
            mGoldTV.setText("" + UserManager.getInst().getUserBeam().getGoldAccount().getAmount());
        }
    }

    @Override
    public void dismiss() {
        if (isTrueDis) {
            super.dismiss();
            int doneNum = 0;
            for (NewPeopleTaskBean bean : mAdapter.getList()) {
                if (bean.isIsFinished()) {
                    doneNum++;
                }
            }
            if (mListener != null)
                mListener.dismissListener(this, doneNum == mList.size() && UserManager.getInst().getUserBeam().isHeben());
            isTrueDis = false;
        } else {
            move(this.getWindow().getDecorView());
        }
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Config.SCREEN_WIDTH;
        params.height = Config.SCREEN_HEIGHT;
        window.setWindowAnimations(R.style.nullpopupAnimation);
        window.setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
        initBtn();
        getData();
        MobclickAgent.onEvent(mContext, "294-showxinshourenwu");
    }

    @Override
    public void onClick(View v) {
        MobclickAgent.onEvent(mContext, "294-renwutanchuangtixian");
        ActivityUtils.startToWithdrawalsActivity(mContext);
    }

    public interface DismissListener {
        void dismissListener(DialogNewPeopleTask dialog, boolean doneTask);
    }

    private GetNewTaskForNewPeoplePro mGetNewTaskForNewPeoplePro;

    private void getData() {
        if (mGetNewTaskForNewPeoplePro == null) {
            mGetNewTaskForNewPeoplePro = new GetNewTaskForNewPeoplePro(mContext, new BaseModel.OnResultListener<List<NewPeopleTaskBean>>() {
                @Override
                public void onResultListener(final List<NewPeopleTaskBean> response) {
                    if (mList != null && mList.size() != 0) {
                        List<NewPeopleTaskBean> donelist = new ArrayList<>();//要播放动画的任务
                        List<NewPeopleTaskBean> list = new ArrayList<>();//
                        for (NewPeopleTaskBean nowBean : response) {//最新新手任务
                            for (NewPeopleTaskBean lastBean : mList) {//缓存新手任务
                                if (lastBean.getDisplayName().equals(nowBean.getDisplayName())) {
                                    if (lastBean.isIsFinished() != nowBean.isIsFinished()) {//缓存跟服务端的数据不一样，默认用户完成该任务（需要播放动画）
                                        nowBean.setChange(true);
                                        donelist.add(nowBean);
                                    } else {
                                        nowBean.setChange(false);
                                        list.add(nowBean);
                                    }
                                }
                            }
                        }
                        boolean isDone = true;
                        for (NewPeopleTaskBean bean : response) {
                            if (!bean.isIsFinished()) {
                                isDone = false;
                            }
                        }
                        if(isDone && donelist.size()!=0){
                            MobclickAgent.onEvent(mContext, "294-finishnewpeopletask");
                        }

                        mList.clear();
                        mList.addAll(donelist);
                        mList.addAll(list);
                        if (mLayout != null) mLayout.scrollToPosition(0);
                        mAdapter.setList(mList);
                    } else {
                        mAdapter.setList(response);
                    }
                    mGetNewTaskForNewPeoplePro = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetNewTaskForNewPeoplePro = null;
                }
            });
            mGetNewTaskForNewPeoplePro.postRequest();
        }
    }

    private void move(final View view) {
        int i2 = Config.SCREEN_HEIGHT / 2 - DisplayUtils.dip2px(mContext, 122.0f);
        int i3 = Config.SCREEN_WIDTH / 2 - DisplayUtils.dip2px(mContext, 20.0f);
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.2f);
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.2f);
        PropertyValuesHolder valuesHolder3 = PropertyValuesHolder.ofFloat("translationX", 0.0f, i3);
        PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("translationY", 0.0f, -300, 300 + i2);
        PropertyValuesHolder valuesHolder4 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f);
        PropertyValuesHolder valuesHolder5 = PropertyValuesHolder.ofFloat("rotation", 0, 360, 720);
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolder,
                    valuesHolder1,
                    valuesHolder2,
                    valuesHolder3,
                    valuesHolder4,
                    valuesHolder5);

            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isTrueDis = true;
                    if (animation != null) {
                        animation.cancel();
                    }
                    view.setTranslationX(0);
                    view.setTranslationY(0);
                    view.setAlpha(1);
                    view.setScaleX(1);
                    view.setScaleY(1);
                    view.setRotation(0);
                    dismiss();
                    objectAnimator = null;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            objectAnimator.setDuration(1000).start();
        }
    }

}
