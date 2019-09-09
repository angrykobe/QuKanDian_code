package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DisplayUtils;

/**
 * Created by yuzuoning on 2017/8/10.
 */

public class DialogWechatRemind extends BaseDialog {
    private TextView mTvTitle;
    private TextView mTvConfirm;

    public DialogWechatRemind(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_wechat_remind_layout;
    }

    @Override
    protected void initView() {
        mTvConfirm = (TextView) findViewById(R.id.dialog_wechat_remind_confirm);
        mTvTitle = (TextView) findViewById(R.id.dialog_wechat_remind_title);

        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    public void setTvTitle(String memo) {
        CommonHelper.setTextMultiColor(mTvTitle, DisplayUtils.dip2px(getContext(),13),"完成活动，并领取现金奖励～",memo.replace("神秘金活动，","神秘金活动\n"));
    }

}
