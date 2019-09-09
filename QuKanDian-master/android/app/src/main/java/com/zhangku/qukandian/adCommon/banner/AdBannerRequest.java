package com.zhangku.qukandian.adCommon.banner;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by yuzuoning on 2017/10/19.
 */

public abstract class AdBannerRequest {
    protected Context mContext;
    public AdBannerRequest(Context context) {
        mContext = context;
    }
    public abstract void getAdData(ViewGroup view);
}
