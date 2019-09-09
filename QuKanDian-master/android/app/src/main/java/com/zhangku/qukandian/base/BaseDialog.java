package com.zhangku.qukandian.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.dialog.DialogPrograss;
import com.zhangku.qukandian.utils.LogUtils;


/**
 * Created by yuzuoning on 2016/12/19.
 * dialog父类
 */

public abstract class BaseDialog extends Dialog {
    protected DialogPrograss mDialogPrograss;
    protected boolean mIsCancel = true;
    protected Context mContext;
    public BaseDialog(Context context) {
        super(context);
        mContext = context;
    }

    protected void setTranBg(){
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseDialog.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setContentView(centerViewId());
        mDialogPrograss = new DialogPrograss(getContext());
        this.setCanceledOnTouchOutside(isCanceledOnTouchOutside());
        initView();
        setPosition();
        LogUtils.LogE("ClassName == " + this.getClass().getName());
    }

    protected abstract int centerViewId();
    protected abstract void initView();
    protected abstract void release();
    protected abstract void setPosition();

    protected void setToBottom(){
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = Config.SCREEN_WIDTH;
        window.setWindowAnimations(R.style.popupAnimation);
        window.setAttributes(params);
    }

    protected void setToFullDialog(){
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Config.SCREEN_WIDTH;
        params.height = Config.SCREEN_HEIGHT;
        window.setWindowAnimations(R.style.nullpopupAnimation);
        window.setAttributes(params);
    }
    protected boolean isCanceledOnTouchOutside(){
        return true;
    }

    @Override
    public void show() {
        super.show();
    }
}
