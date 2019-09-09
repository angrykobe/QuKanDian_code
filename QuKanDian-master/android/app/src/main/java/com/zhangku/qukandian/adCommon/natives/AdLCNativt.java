//package com.zhangku.qukandian.adCommon.natives;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.shulian.sdk.NativeAds;
//import com.shulian.sdk.NativeSize;
//import com.zhangku.qukandian.bean.AdLocationBeans;
//import com.zhangku.qukandian.bean.NativeAdInfo;
//import com.zhangku.qukandian.config.Constants;
//import com.zhangku.qukandian.interfaces.OnAdNativeRequestListener;
//import com.zhangku.qukandian.utils.CommonHelper;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
///**
// * Created by yuzuoning on 2018/4/12.
// */
//
//public class AdLCNativt extends AdRequestBase {
//
//    @Override
//    public void getAdData(final String name, final int adlocId, final int index, final String belong, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean, final OnAdNativeRequestListener onAdNativeRequestListener) {
//        // 步骤一
//        NativeAds.preLoad(mContext);
//
//        // 步骤二
//        final NativeAds nativeAds = new NativeAds(mContext, "sb10d260", new NativeSize(110, 70));
//        // 步骤三
//        nativeAds.setListener(new NativeAds.NativeAdsListener() {
//            @Override
//            public void onAdReady(JSONObject arg0) {
//                // 步骤四
//                ArrayList<NativeAdInfo> lists = new ArrayList<>();
//                NativeAdInfo bean = new NativeAdInfo();
//                bean.setAdlocId(adlocId);
//                bean.setAdsRuleBean(adsRuleBean);
//                bean.setClickGold(adsRuleBean.getClickGold());
//                bean.setOrigin(nativeAds);
//                bean.setType(Constants.AD_TYPT_LC);
////                bean.setImageUrls(response.getImgList());
//                bean.setIndex(index);
//                bean.setBelong(belong);
//                bean.setCurrentData(CommonHelper.formatTimeYMD(System.currentTimeMillis(),true));
//                bean.setDescription(nativeAds.getDesc1());
//                bean.setIconUrl(nativeAds.getLogoUrl());
//                bean.setImageUrl(nativeAds.getImgUrl());
//                bean.setTitle(nativeAds.getTitle());
//
//                lists.add(bean);
//                if(null != onAdNativeRequestListener){
//                    onAdNativeRequestListener.onResponse(lists,name,2);
//                }
//
//            }
//
//            @Override
//            public void onAdFailed(JSONObject data) {
//                Log.e("asdfsfdsf","onAdFailed/"+data.toString());
//            }
//
//            @Override
//            public void onAdClick(JSONObject jsonObject) {
//                Log.e("asdfsfdsf","onAdClick/"+jsonObject.toString());
//            }
//
//            @Override
//            public void onAdShow(JSONObject jsonObject) {
//                Log.e("asdfsfdsf","onAdShow/"+jsonObject.toString());
//            }
//        });
//    }
//
//    @Override
//    public void getAdData(AdLocationBeans.AdLocationsBean adLocationsBean, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean clientBean, OnAdNativeRequestListener onAdNativeRequestListener) {
//
//    }
//}
