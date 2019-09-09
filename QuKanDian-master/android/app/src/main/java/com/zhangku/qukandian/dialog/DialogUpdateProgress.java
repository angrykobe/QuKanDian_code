package com.zhangku.qukandian.dialog;

import android.content.Context;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.interfaces.OnClickApkDownloadListener;
import com.zhangku.qukandian.widght.UpdateView;

/**
 * Created by yuzuoning on 2017/6/7.
 */

public class DialogUpdateProgress extends BaseDialog {
    private UpdateView mUpdateView;

    public DialogUpdateProgress(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_update_progress_layout;
    }

    @Override
    protected void initView() {
        mUpdateView = (UpdateView) findViewById(R.id.update);
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        mUpdateView.setOnDownloadSuccessListener(new OnClickApkDownloadListener() {
            @Override
            public void onApkDownloadListener() {//下载完成 弹窗消失
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
}
