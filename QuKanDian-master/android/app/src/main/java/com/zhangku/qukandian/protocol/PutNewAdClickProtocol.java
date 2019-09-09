package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.AdResultBean;
import com.zhangku.qukandian.bean.AdsClickParam;
import com.zhangku.qukandian.bean.InitUserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/12/7.
 */

public class PutNewAdClickProtocol extends NewBaseProtocol<AdResultBean> {
    private AdsClickParam mAdsClickParam;
    public boolean success = false;

    public PutNewAdClickProtocol(Context context, AdsClickParam adsClickParam, OnResultListener<AdResultBean> onResultListener) {
        super(context, onResultListener);
        mAdsClickParam = adsClickParam;
    }

    public PutNewAdClickProtocol(Context context, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean mBean, OnResultListener<AdResultBean> onResultListener) {
        super(context, onResultListener);
        mAdsClickParam = new AdsClickParam();
        mAdsClickParam.setAdsBagType(mBean.isIsSecondClickGoldEnable() ? 1 : 0);
        mAdsClickParam.setAdLocId(mBean.getAdLocId());
        mAdsClickParam.setAdvertiserId(mBean.getAdverId());
        mAdsClickParam.setAdType(mBean.getAdType());
        mAdsClickParam.setAdvertiserName(mBean.getAdverName());
        mAdsClickParam.setAmount(mBean.getClickGold());
        mAdsClickParam.setAdLink(mBean.getAdLink());
        mAdsClickParam.setReferId(mBean.getReferId());
        mAdsClickParam.setAdPageType(mBean.getAdPageType());
//        switch (mBean.getBelong()) {//枚举：1-首页，2-视频详情页，3-文章详情页，4-视频列表，6-开屏页，7-签到页，8-宝箱页）
//            case "首页"://首页
//                mAdsClickParam.setAdPageType(1);
//                break;
//            case "视频详情页大图":
//            case "视频详情页列表":
//                mAdsClickParam.setAdPageType(2);
//                break;
//            case "文章详情页大图":
//            case "文章详情页列表":
//                mAdsClickParam.setAdPageType(3);
//                break;
//            case "视频列表页":
//                mAdsClickParam.setAdPageType(4);
//                break;
//            case "开屏页":
//                mAdsClickParam.setAdPageType(6);
//                break;
//            case "签到弹框":
//                mAdsClickParam.setAdPageType(7);
//                break;
//            case "拆宝箱弹框":
//                mAdsClickParam.setAdPageType(8);
//                break;
//            case "未知的位置":
//                mAdsClickParam.setAdPageType(0);
//                break;
//        }
    }

    @Override
    protected Call getMyCall() {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);
        String okey = "oK2XOwUtwp6P-rCDZgH6cRQE_2TA";
        mAdsClickParam.setUnionId(okey);
        mAdsClickParam.setLocation(CommonHelper.getLocation(mContext));
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String g = gson.toJson(mAdsClickParam);
        String signature = CommonHelper.md5(key + "data" + g + "nonceStr" + rand + "timestamp" + time);

        LogUtils.LogW("key=" + key + " param=" + g + " rand=" + rand + " timestamp=" + time);
        LogUtils.LogW("signature=" + signature);

        return getAPIService().putAdsClick(mAuthorization, mContentType, signature,
                time, appid, rand, mAdsClickParam);
    }

    @Override
    protected void getResult(AdResultBean adResultBean) {
        //??
        UserSharedPreferences.getInstance().putBoolean(Constants.REFRESH_FLAG, true);
        UserSharedPreferences.getInstance().putBoolean(Constants.REFRESH_FLAG_1, true);//刷新
        if (adResultBean.getAdsCnt() == -1) {
            AdUtil.mAdsCnt--;
        }
        if (adResultBean.getAdsCnt() >= 0) {
            AdUtil.mAdsCnt = adResultBean.getAdsCnt();
        }
        if (UserManager.SECOND_CLIDK && UserManager.getInst().getUserBeam().getUserAdsType() == 1) {
            int count = UserSharedPreferences.getInstance().getInt("second_click");
            count++;
            UserSharedPreferences.getInstance().putInt("second_click", count);
            if (count >= UserManager.getInst().getmRuleBean().getAdsClickInductionRule().getFirstInTime()) {
                new AdsuserProtocol(mContext, new InitUserBean(UserManager.getInst().getUserBeam().getId(), 2, count), new BaseModel.OnResultListener<Boolean>() {
                    @Override
                    public void onResultListener(Boolean response) {
                        if (response) {
                            UserManager.getInst().getUserBeam().setUserAdsType(2);
                        }
                    }

                    @Override
                    public void onFailureListener(int code, String error) {

                    }
                }).postRequest();
            }
        }
        if (adResultBean.getAdsProgressCnt() > 0) {//阅读进度已获取的红包数量
            UserManager.adsProgressCnt = adResultBean.getAdsProgressCnt();
        }
        super.getResult(adResultBean);
    }

    //293  用户获得金币后，广告移除，替换新的广告内容； 解决广告主反馈用户重复率高的问题
    public static void removeGoldAd(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean mBean) {
        //if(!mBean.isRemove()) return;
        // String string = UserSharedPreferences.getInstance().getString(Constants.AD_INFO, "");
        // List<AdLocationBeans> list = GsonUtil.fromJsonForList(string,AdLocationBeans.class);
        // for (AdLocationBeans bean : list) {
        //     List<AdLocationBeans.AdLocationsBean> lists = bean.getAdLocations();
        //     if (null != lists && lists.size() > 0) {
        //         //初始化列表中的广告位
        //         for (int i = 0; i < lists.size() && i < 6; i++) {
        //             if (null == lists.get(i).getClientAdvertises() || lists.get(i).getClientAdvertises().size() == 0) {
        //                 continue;
        //             }
        //             final AdLocationBeans.AdLocationsBean adLocationsBean = lists.get(i);
        //             if (adLocationsBean == null) continue;
//
        //             Iterator<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> iterator = adLocationsBean.getClientAdvertises().iterator();
        //             while (iterator.hasNext()) {
        //                 AdLocationBeans.AdLocationsBean.ClientAdvertisesBean next = iterator.next();
        //                 if (mBean.getAdLink().equals(next.getAdLink()) && mBean.getAdverId() == next.getAdverId()) {
        //                     iterator.remove();
        //                 }
        //             }
//
        //         }
        //     }
        // }
        // UserSharedPreferences.getInstance().putString(Constants.AD_INFO, new Gson().toJson(list));
    }
}
