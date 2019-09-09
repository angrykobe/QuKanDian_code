package com.zhangku.qukandian.utils;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.LogUpBean;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.CacheManage;

import java.util.List;

public class AdsLogUpUtils {

    public static int ADLOGUP_TYPE_INFO_LIST = 0;
    public static int ADLOGUP_TYPE_INFO_DETAIL = 1;
    public static int ADLOGUP_TYPE_VIDEO_LIST = 2;
    public static int ADLOGUP_TYPE_VIDEO_DETAIL = 3;

    /**
     * 保存广告信息到本地
     * Splash
     */
    public static void saveAds(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        if (UserManager.adsLog != 1) {//后台控制广告日志上传
            return;
        }
        if (bean instanceof AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) {
            AdLocationBeans.AdLocationsBean.ClientAdvertisesBean curItem = (AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) bean;
            if (curItem.getAdType() != 2)//只记录联盟广告
                return;
        }

        List<LogUpBean> logUpBeans = CacheManage.get(Constants.ADSLOG_UP, LogUpBean.class);

        LogUpBean logUpBean = new LogUpBean();
        logUpBean.setPhone_imei(MachineInfoUtil.getInstance().getIMEI(QuKanDianApplication.getAppContext()));
        logUpBean.setSave_time(com.blankj.utilcode.util.TimeUtils.getNowString());
        if (UserManager.getInst().hadLogin()) {
            logUpBean.setPhone_user(UserManager.getInst().getUserBeam().getId() + "");
        } else {
            logUpBean.setPhone_user(MachineInfoUtil.getInstance().getIMEI(QuKanDianApplication.getAppContext()));
        }
        logUpBean.setAd_type("Splash");

        logUpBean.setAd_title(bean.getTitle());
        logUpBean.setAd_company(Constants.AD_SPLASH + bean.getAdverName());
        logUpBean.setAd_remarks(bean.toString());
        logUpBeans.add(logUpBean);

        CacheManage.put(Constants.ADSLOG_UP, logUpBeans);
    }

    public static void saveAdsList(int type, Object bean) {
        if (UserManager.adsLog != 1) {//后台控制广告日志上传
            return;
        }
        if (bean instanceof AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) {
            AdLocationBeans.AdLocationsBean.ClientAdvertisesBean curItem = (AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) bean;
            if (curItem.getAdType() != 2)//只记录联盟广告
                return;
        }
        List<LogUpBean> logUpBeans = CacheManage.get(Constants.ADSLOG_UP, LogUpBean.class);

        LogUpBean logUpBean = new LogUpBean();
        logUpBean.setPhone_imei(MachineInfoUtil.getInstance().getIMEI(QuKanDianApplication.getAppContext()));
        logUpBean.setSave_time(com.blankj.utilcode.util.TimeUtils.getNowString());
        if (UserManager.getInst().hadLogin()) {
            logUpBean.setPhone_user(UserManager.getInst().getUserBeam().getId() + "");
        } else {
            logUpBean.setPhone_user(MachineInfoUtil.getInstance().getIMEI(QuKanDianApplication.getAppContext()));
        }
        if (type == ADLOGUP_TYPE_INFO_LIST) {
            logUpBean.setAd_type("info_list");
        } else if (type == ADLOGUP_TYPE_INFO_DETAIL) {
            logUpBean.setAd_type("info_detail");
        } else if (type == ADLOGUP_TYPE_VIDEO_LIST) {
            logUpBean.setAd_type("video_list");
        } else if (type == ADLOGUP_TYPE_VIDEO_DETAIL) {
            logUpBean.setAd_type("video_detail");
        }

        if (bean instanceof AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) {
            AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean1 = (AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) bean;

            logUpBean.setAd_title(bean1.getTitle());
            logUpBean.setAd_company(bean1.getAdverName());
        } else if (bean instanceof NativeAdInfo) {
            NativeAdInfo bean1 = (NativeAdInfo) bean;

            logUpBean.setAd_title(bean1.getTitle());
            logUpBean.setAd_company(bean1.getAdFromName());
        }


        logUpBean.setAd_remarks(bean.toString());
        logUpBeans.add(logUpBean);

        CacheManage.put(Constants.ADSLOG_UP, logUpBeans);
    }
}
