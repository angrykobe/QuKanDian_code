package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;

import com.zhangku.qukandian.R;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/17
 * 你不注释一下？
 */
public class ShareForWebView extends DialogShared {

    public ShareForWebView(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();

        findViewById(R.id.shareBottomView).setVisibility(View.GONE);
    }
}
