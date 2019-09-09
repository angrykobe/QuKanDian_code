package com.zhangku.qukandian.adCommon.natives;

import android.content.Context;

import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;

/**
 * Created by yuzuoning on 2017/10/17.
 */

public abstract class AdRequestBase {
    protected Context mContext;
    protected String from;

    public AdRequestBase(Context context, @AnnoCon.AdverFrom String from) {
        mContext = context;
        this.from = from;
    }

    public abstract void getAdData(AdLocationBeans.AdLocationsBean adLocationsBean
                                 , AdLocationBeans.AdLocationsBean.ClientAdvertisesBean clientBean
                                 , OnAdNativeRequestListener onAdNativeRequestListener);
}
