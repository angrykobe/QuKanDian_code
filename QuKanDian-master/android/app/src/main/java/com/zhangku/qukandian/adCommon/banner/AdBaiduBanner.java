package com.zhangku.qukandian.adCommon.banner;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;

import org.json.JSONObject;

/**
 * Created by yuzuoning on 2017/10/19.
 */

public class AdBaiduBanner extends AdBannerRequest {
    AdView adView;
    public AdBaiduBanner(Context context) {
        super(context);
    }

    @Override
    public void getAdData(ViewGroup view) {
        String adPlaceId = "4338851";
        adView = new AdView(mContext, adPlaceId);
        adView.setListener(new AdViewListener() {
            public void onAdSwitch() {
                Log.w("", "onAdSwitch");
            }

            public void onAdShow(JSONObject info) {
                // 广告已经渲染出来
                Log.w("", "onAdShow " + info.toString());
            }

            public void onAdReady(AdView adView) {
                // 资源已经缓存完毕，还没有渲染出来
                Log.w("", "onAdReady " + adView);
            }

            public void onAdFailed(String reason) {
                Log.w("", "onAdFailed " + reason);
            }

            public void onAdClick(JSONObject info) {
                // Log.w("", "onAdClick " + info.toString());

            }

            @Override
            public void onAdClose(JSONObject arg0) {
                Log.w("", "onAdClose");
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        int winW = dm.widthPixels;
        int winH = dm.heightPixels;
        int width = Math.min(winW, winH);
        int height = width * 3 / 20;
        // 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(width, height);
        rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.addView(adView, rllp);
    }
}
