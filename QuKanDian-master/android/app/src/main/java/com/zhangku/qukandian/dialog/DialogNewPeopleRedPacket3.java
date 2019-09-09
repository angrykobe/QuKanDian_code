package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;

import java.util.HashMap;

/**
 * 新手红包(注册红包)
 */

public class DialogNewPeopleRedPacket3 extends BaseDialog implements View.OnClickListener {
    private TextView submitTV;
    private View mIvCancel;
    private OnListenaer listenaer;

    public DialogNewPeopleRedPacket3(Context context,OnListenaer listenaer) {
        super(context, R.style.zhangku_dialog);
        this.listenaer = listenaer;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_chai_hong_bao_view;
    }

    @Override
    protected void initView() {
        mIvCancel = findViewById(R.id.cancleTV);
        submitTV = findViewById(R.id.submitTV);
        TextView mUserNumTV = findViewById(R.id.mUserNumTV);

        String str = UserManager.getInst().getQukandianBean().getRegisterCoinTxt();//新手红包文案  registerCoinTxt=打开最高得5元
        mUserNumTV.setText(Html.fromHtml(mContext.getString(R.string.user_num, UserManager.getInst().getQukandianBean().getUserRegisterNum())));
        submitTV.setText(str);
        mIvCancel.setOnClickListener(this);
        submitTV.setOnClickListener(this);
    }

    @Override
    protected boolean isCanceledOnTouchOutside() {
        return false;
    }

    @Override
    protected void setPosition() {
    }

    @Override
    protected void release() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitTV:
                dismiss();
                if(listenaer!=null) listenaer.onSubmitListener(this);
                MobclickAgent.onEvent(mContext, "293-hblingqu");
                break;
            case R.id.cancleTV:
                dismiss();
                if(listenaer!=null) listenaer.onCancelListener(this);
                MobclickAgent.onEvent(mContext, "293-hbguangguang");
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        MobclickAgent.onEvent(mContext, "293-hbfuchuang");
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface OnListenaer {
        void onSubmitListener(DialogNewPeopleRedPacket3 dialog);
        void onCancelListener(DialogNewPeopleRedPacket3 dialog);
    }


}
