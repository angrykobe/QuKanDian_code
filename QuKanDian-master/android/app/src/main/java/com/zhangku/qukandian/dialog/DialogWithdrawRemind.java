package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.WithdrawalsRemindBean;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/6/14.
 */

public class DialogWithdrawRemind extends BaseDialog {
    private TextView mTvContent;
    private TextView mTvConfirmBtn;

    public DialogWithdrawRemind(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_withdraw_remind_view;
    }

    @Override
    protected void initView() {
        mTvContent = (TextView) findViewById(R.id.dialog_withdrawals_remind_content);
        mTvConfirmBtn = (TextView) findViewById(R.id.dialog_withdrawals_remind_confirm);

        mTvConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setData(ArrayList<WithdrawalsRemindBean> withdrawalsRemindBean){
        ArrayList<WithdrawalsRemindBean> list = withdrawalsRemindBean;
        if (null != list) {
            int money = 0;
            for (int i = 0; i < list.size(); i++) {
                money += list.get(i).getFee();
            }
            mTvContent.setText(money + "元微信红包");
        }
    }

    @Override
    protected void release() {
        mTvContent = null;
        mTvConfirmBtn = null;
    }

    @Override
    protected void setPosition() {

    }
}
