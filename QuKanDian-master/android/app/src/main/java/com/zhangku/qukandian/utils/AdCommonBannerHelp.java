package com.zhangku.qukandian.utils;

import android.content.Context;
import android.view.ViewGroup;

import com.zhangku.qukandian.adCommon.banner.AdBaiduBanner;
import com.zhangku.qukandian.adCommon.banner.AdFelinkBanner;
import com.zhangku.qukandian.adCommon.banner.AdGdtBanner;

import java.util.Random;

/**
 * Created by yuzuoning on 2017/10/19.
 */

public class AdCommonBannerHelp {
    private String[] mTypes = new String[]{"baidu", "gdt"};
    private static AdCommonBannerHelp mAdCommonBannerHelp = null;

    private AdCommonBannerHelp() {
    }

    public static AdCommonBannerHelp getInstance() {
        if (null == mAdCommonBannerHelp) {
            synchronized (AdCommonBannerHelp.class) {
                if (null == mAdCommonBannerHelp) {
                    mAdCommonBannerHelp = new AdCommonBannerHelp();
                }
            }
        }
        return mAdCommonBannerHelp;
    }

    public void getAdResult(Context context, ViewGroup view) {
       Random random = new Random();
       int type = random.nextInt(9);
        if (type <= 4) {
            new AdBaiduBanner(context).getAdData(view);
       } else {
            new AdFelinkBanner(context).getAdData(view);
        }
    }
}
