package com.zhangku.qukandian.dialog;

import android.content.Context;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;

/**
 * Created by yuzuoning on 2017/12/15.
 */

public class DialogUrlWeb extends BaseDialog {
    public DialogUrlWeb(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_url_webview_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }
}
