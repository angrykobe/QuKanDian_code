package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.MainActivity;
import com.zhangku.qukandian.adapter.DialogNewPerAdapter;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.interfaces.DialogOnDismissListener;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.widght.TextSwitcherView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

/**
 * 新手注册红包已获取弹框
 */
public class DialogNewPeopleRedGotGold extends BaseDialog implements DialogNewPerAdapter.OnCliclItemListener {
    private TextView mTvGold;
    private TextView mTvGold2;
    private View.OnClickListener mListener;

    public DialogNewPeopleRedGotGold(Context context, View.OnClickListener onDismissListener) {
        super(context, R.style.zhangku_dialog);
        mListener = onDismissListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_new_show_gold;
    }

    @Override
    protected void initView() {
        mTvGold = findViewById(R.id.chai_hongbao_red_show_gold);
        mTvGold2 = findViewById(R.id.chai_hongbao_red_show_gold2);

        TextView titleTV = findViewById(R.id.titleTV);
        titleTV.setText(Html.fromHtml(mContext.getString(R.string.newpeople_get_title)));

        View viewById = findViewById(R.id.cancleTV);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView tv = findViewById(R.id.doneOtherTaskBtn);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ActivityUtils.startToNewPeopleTaskActivity(mContext);
            }
        });
        //设置头部滚动提现
        TextSwitcherView mTextSwitcherView = findViewById(R.id.task_activity_marquee);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number = new Random().nextInt(9000);
            int money = new Random().nextInt(96);
            String temp = "趣友**" + (number + 1000)
                    + "拆开红包获得了" + (4 + money) + "000金币";
            arrayList.add(temp);
        }
        mTextSwitcherView.getResource(arrayList);
    }

    @Override
    protected boolean isCanceledOnTouchOutside() {
        return false;
    }

    @Override
    protected void release() {
    }

    @Override
    protected void setPosition() {

    }

    public void setCoin(String coin) {
        mTvGold.setText(getContext().getString(R.string.new_people_done_task, "" + coin));
        BigDecimal num1 = new BigDecimal(coin);
        BigDecimal num2 = new BigDecimal(10000);
        BigDecimal result = num1.divide(num2);
        String money = result.toPlainString();
        mTvGold2.setText("(约" + money + "元)");
    }

    @Override
    public void onCliclItemListener(int position) {
        dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mListener != null) mListener.onClick(null);
    }
}
