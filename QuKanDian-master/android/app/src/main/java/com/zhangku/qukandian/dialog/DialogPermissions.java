package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.utils.CommonHelper;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * Created by yuzuoning on 2017/6/2.
 */

public class DialogPermissions extends BaseDialog implements View.OnClickListener {
    private TextView mTvCancelBtn;
    private TextView mTvConfirmBtn;
    private TextView mTvRemind;
    private OnClickListener mOnClickListener;

    public DialogPermissions(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_permissions_layout;
    }

    @Override
    protected void initView() {
        mTvCancelBtn = (TextView) findViewById(R.id.dialog_permissions_cancel);
        mTvConfirmBtn = (TextView) findViewById(R.id.dialog_permissions_confirm);
        mTvRemind = (TextView) findViewById(R.id.dialog_permissions_remind);
        mTvCancelBtn.setOnClickListener(this);
        mTvConfirmBtn.setOnClickListener(this);
    }

    public void setRemindContent(String content){
        mTvRemind.setText(content+"");
    }

    public DialogPermissions shows(){
        show();
        return this;
    }

    @Override
    protected void release() {
        mTvCancelBtn = null;
        mTvConfirmBtn = null;
        mTvRemind = null;
    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_permissions_cancel:
                dismiss();
                break;
            case R.id.dialog_permissions_confirm:
                if (CommonHelper.isMIUI()) {
                    CommonHelper.gotoMIUIPermissionSettings(getContext());
                    dismiss();
                } else if (CommonHelper.isOPPO()) {
                    CommonHelper.gotoOPPOPermissionSettings(getContext());
                    dismiss();
                } else {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    getContext().startActivity(intent);
                    dismiss();
                }
                break;
        }
    }

    public interface OnClickListener {
        void onClickConfirmListener();

        void onClickCancelListener();
    }
}
