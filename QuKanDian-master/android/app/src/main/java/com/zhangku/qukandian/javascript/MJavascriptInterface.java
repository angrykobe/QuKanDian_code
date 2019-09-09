package com.zhangku.qukandian.javascript;

import android.app.Activity;
import android.content.Context;
import com.tencent.smtt.sdk.WebView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.zhangku.qukandian.utils.Img.ImagPagerUtil;

/**
 * Created by yuzuoning on 2017/7/6.
 */

public class MJavascriptInterface {
    private String[] mImageUrls;
    private Context mContext;
    private WebView mWebView;

    public MJavascriptInterface(Context context, WebView webView, String[] imageUrls) {
        this.mImageUrls = imageUrls;
        mContext = context;
        mWebView = webView;
    }

    @android.webkit.JavascriptInterface
    public void openImage(final String img) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mImageUrls == null) return;
                ImagPagerUtil imagPagerUtil = new ImagPagerUtil(mContext, mImageUrls);
                imagPagerUtil.setPosition(img);
                imagPagerUtil.show();
            }
        });
    }

    @android.webkit.JavascriptInterface
    public void resize(final float height) {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mWebView.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height1 = (int) (height * mContext.getResources().getDisplayMetrics().density + 100);
                lp.height = height1;
                mWebView.setLayoutParams(lp);
            }
        });
    }
}
