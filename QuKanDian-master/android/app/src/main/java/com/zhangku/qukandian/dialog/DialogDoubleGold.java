package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DisplayUtils;

/**
 * Created by yuzuoning on 2018/4/3.
 */

public class DialogDoubleGold extends BaseDialog {
    private TextView mTvContinueBtn;
    private TextView mTvGold;
    private String mGoldText;

    public DialogDoubleGold(Context context, String goldText) {
        super(context, R.style.zhangku_dialog);
        mGoldText = goldText;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_double_gold_view;
    }

    @Override
    protected void initView() {
        setCancelable(false);
        mTvGold = findViewById(R.id.dislog_double_gold_text);
        mTvContinueBtn = findViewById(R.id.new_gold_dialog_btn);
        mTvContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        CommonHelper.setTextMultiColor(mTvGold, DisplayUtils.dip2px(getContext(),30),mGoldText+"X2",mGoldText+"X2\n金币");
//        mTvGold.setText(mTvGoldoldText+"X2\n金币");
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
}
