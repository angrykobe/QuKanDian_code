package com.zhangku.qukandian.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.AdResultBean;
import com.zhangku.qukandian.bean.AdsClickParam;
import com.zhangku.qukandian.interfaces.OnClickApkDownloadListener;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutNewAdClickProtocol;
import com.zhangku.qukandian.service.DownloadService;

public class ApkDownloadUtils {

    public static void doAdApkDownload(Context mContext,String targetAdLink,
                                       AdLocationBeans.AdLocationsBean.ClientAdvertisesBean mBean,
                                       OnClickApkDownloadListener listener){
        startDownloadService(mContext,targetAdLink);
        requestAdGold(mContext,mBean,listener);
    }


    public static void startDownloadService(Context packageContext, String targetAdLink) {
        Intent intent = new Intent(packageContext, DownloadService.class);
        intent.putExtra("name", "thirdpart");
        intent.putExtra("url", targetAdLink);
        packageContext.startService(intent);
    }

    //下载apk广告红包获取金币
    public static void requestAdGold(final Context mContext, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean mBean,
                                     final OnClickApkDownloadListener listener) {
        AdsClickParam mAdsClickParam = new AdsClickParam();
        if (null != mBean) {
            mAdsClickParam.setAdsBagType(mBean.isIsSecondClickGoldEnable() ? 1 : 0);
            mAdsClickParam.setAdLocId(mBean.getAdLocId());
            mAdsClickParam.setAdvertiserId(mBean.getAdverId());
            mAdsClickParam.setAdType(mBean.getAdType());
            mAdsClickParam.setAdvertiserName(mBean.getAdverName());
            mAdsClickParam.setAmount(mBean.getClickGold());
            mAdsClickParam.setAdLink(mBean.getAdLink());
            mAdsClickParam.setReferId(mBean.getReferId());
        }
        if (null != UserManager.getInst().getUserBeam().getGoldAccount()) {
            new PutNewAdClickProtocol(mContext, mAdsClickParam, new BaseModel.OnResultListener<AdResultBean>() {
                @Override
                public void onResultListener(final AdResultBean response) {
                    if (response.getGoldAmount() != 0) {
                        Activity activity = (Activity) mContext;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CustomToast.showToast(mContext, response.getGoldAmount() + "", "下载奖励");
                            }
                        });
                    } else {
                        ToastUtils.showShortToast(mContext, "该奖励今天已领取过了哦~");
                    }
                    listener.onApkDownloadListener();

                }

                @Override
                public void onFailureListener(int code, String error) {

                }
            }).postRequest();
        }
    }
}
