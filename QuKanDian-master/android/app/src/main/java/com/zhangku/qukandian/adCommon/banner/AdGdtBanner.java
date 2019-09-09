package com.zhangku.qukandian.adCommon.banner;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;

/**
 * Created by yuzuoning on 2017/10/19.
 */

public class AdGdtBanner extends AdBannerRequest {
    private BannerView bv;
    public AdGdtBanner(Context context) {
        super(context);
    }

    @Override
    public void getAdData(ViewGroup view) {
        bv = new BannerView((Activity) mContext, ADSize.BANNER, "1106304292", "1090728454395350");
        bv.setRefresh(30);
        bv.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(AdError adError) {

            }

            @Override
            public void onADReceiv() {

            }
        });
        view.addView(bv);
        this.bv.loadAD();
    }
}
