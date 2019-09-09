package com.zhangku.qukandian.adCommon.natives;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.mobad.feeds.BaiduNative;
import com.baidu.mobad.feeds.NativeErrorCode;
import com.baidu.mobad.feeds.NativeResponse;
import com.baidu.mobad.feeds.RequestParameters;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/10/17.
 */

public class MyAdBaiduNative extends AdRequestBase {

    private BaiduNative mBaiduNative;

    public MyAdBaiduNative(Context context, @AnnoCon.AdverFrom String from) {
        super(context, from);
    }


    @Override
    public void getAdData(final AdLocationBeans.AdLocationsBean adLocationsBean, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final OnAdNativeRequestListener onAdNativeRequestListener) {
        try {
            if (null == mBaiduNative) {
                String adsCode = adsRuleBean.getAdsCode();
                String s = TextUtils.isEmpty(adsCode) ? "4508136" : adsRuleBean.getAdsCode();
                mBaiduNative = new BaiduNative(mContext, s, new BaiduNative.BaiduNativeNetworkListener() {
                    @Override
                    public void onNativeLoad(List<NativeResponse> list) {
                        if (null != onAdNativeRequestListener) {
                            int adlocId = adLocationsBean.getId();//广告位id
                            int index = adLocationsBean.getPageIndex();//广告在列表的位置
                            String name = adsRuleBean.getAdverName();//广告名称
                            String belong = adsRuleBean.getBelong();//广告位置 埋点
                            int clickGold = adsRuleBean.getClickGold();//点击金币

                            List<NativeAdInfo> list1 = new ArrayList();
                            for (int i = 0; i < list.size(); i++) {
                                NativeAdInfo bean = new NativeAdInfo();
                                NativeResponse response = list.get(i);
                                bean.setAdlocId(adlocId);
                                bean.setOrigin(response);
                                bean.setClickGold(clickGold);
                                bean.setAdsRuleBean(adsRuleBean);
                                bean.setAdFromName(from);
                                bean.setIndex(index);
                                bean.setBelong(belong);
                                if(response.getImageUrl().isEmpty()){
                                    bean.setImageUrl(response.getMultiPicUrls().get(0));
                                }else {
                                    bean.setImageUrl(response.getImageUrl());
                                }
                                bean.setImageUrls(response.getMultiPicUrls());
                                bean.setCurrentData(CommonHelper.formatTimeYMD(System.currentTimeMillis(), true));
                                bean.setDescription(response.getDesc());
                                bean.setIconUrl(response.getIconUrl());
                                bean.setTitle(response.getTitle());
                                list1.add(bean);
                            }
                            onAdNativeRequestListener.onResponse(list1, 2);
                            mBaiduNative.destroy();
                        }
                    }

                    @Override
                    public void onNativeFail(NativeErrorCode nativeErrorCode) {
                        onAdNativeRequestListener.onFail();
                        mBaiduNative.destroy();
                    }
                });
            }
            RequestParameters requestParameters = new RequestParameters.Builder().downloadAppConfirmPolicy(RequestParameters.DOWNLOAD_APP_CONFIRM_ONLY_MOBILE).build();
            mBaiduNative.makeRequest(requestParameters);
        }catch (Exception e){
            onAdNativeRequestListener.onFail();
        }
    }
}
