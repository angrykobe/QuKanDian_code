package com.zhangku.qukandian.adCommon.natives;

import android.content.Context;

import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yuzuoning on 2018/4/10.
 */

public class AdZhiZuNative extends AdRequestBase {

    public AdZhiZuNative(Context context, @AnnoCon.AdverFrom String from) {
        super(context, from);
    }

    @Override
    public void getAdData(AdLocationBeans.AdLocationsBean adLocationsBean, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, OnAdNativeRequestListener onAdNativeRequestListener) {
        if (onAdNativeRequestListener != null) {
            List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> list = new ArrayList<>();
            adsRuleBean.setAdLocId(adLocationsBean.getId());
            adsRuleBean.setAdPageLocationId(adLocationsBean.getAdPageLocationId());
            list.add(adsRuleBean);
            onAdNativeRequestListener.onResponse(list, adsRuleBean.getAdType());
        }
    }

    /**
     * 获取红包icon展示
     *
     * @param bean return state  1 红包      2  金包   其他 不展示
     */
    public static int getRedState(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        //自主广告红包展示
        int state = 0;//是否展示红包
        if (UserBean.getAdGift()//用户权限
                && bean.getClickGold() > 0//是否有金币
        ) {//vpt是否大于后台设置
            //金包，银包区分分界线
            if (bean.getClickGold() >= UserManager.getInst().getmRuleBean().getAdsClickGiftRule().getGoldGradeLimit()) {
                state = 2;
            } else {
                state = 1;
            }
        }
        return state;
    }

    /**
     * 联盟广告（非自主）获取红包icon展示
     *
     * @param bean return state  1 红包      2  金包   其他 不展示
     */
    public static int getRedStateForOtherAd(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        //自主广告红包展示
        int state = 0;//是否展示红包
        if (UserBean.getAdGift()//用户权限
                && bean.getClickGold() > 0//是否有金币
        ) {//vpt是否大于后台设置
            //金包，银包区分分界线
            if (bean.getClickGold() >= UserManager.getInst().getmRuleBean().getAdsClickGiftRule().getGoldGradeLimit()) {
                state = 2;
            } else {
                state = 1;
            }
        }
        return state;
    }

}
