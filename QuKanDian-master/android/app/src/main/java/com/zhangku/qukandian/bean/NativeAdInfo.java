package com.zhangku.qukandian.bean;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.MotionEvent;
import android.view.View;

import com.ak.android.engine.nav.NativeAd;
import com.baidu.mobad.feeds.NativeResponse;
import com.db.ta.sdk.NonStandardTm;
import com.hmob.hmsdk.entity.NativeResource;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.biz.adcore.ZKAdTypeEnum;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogConfirm;
import com.zhangku.qukandian.dialog.DialogNewPeopleDoneTask;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutNewAdClickProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/10/17.
 */

public class NativeAdInfo {
    private Object origin;
    private AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean;
    private String iconUrl;
    private NonStandardTm mNonStandardTm;
    private int iconWidth;
    private int iconHeight;
    private String title;
    private String description;
    private String link;
    private String imageUrl;
    private List<String> imageUrls = new ArrayList<>();
    private int imageWidth;
    private int imageHeight;
    private String rating;
    private String id;
    private String adFromName;//第三方广告名称
    private int index;//  广告在列表中第几个位置item行数
    private int adlocId;
    private String belong;
    private String adIconFlag;
    private String adLogoFlag;
    private String currentData;
    private boolean isShow = false;
    private int clickGold;
    private int userAdsType;
    private ZKAdTypeEnum templateType;

    public String getAdFromName() {
        return adFromName;
    }

    public void setAdFromName(String adFromName) {
        this.adFromName = adFromName;
    }

    public void setTemplateType(ZKAdTypeEnum templateType) {
        this.templateType = templateType;
    }

    public ZKAdTypeEnum getTemplateType() {
        return templateType;
    }

    public NonStandardTm getNonStandardTm() {
        return mNonStandardTm;
    }

    public void setNonStandardTm(NonStandardTm nonStandardTm) {
        mNonStandardTm = nonStandardTm;
    }

    public int getUserAdsType() {
        return userAdsType;
    }

    public void setUserAdsType(int userAdsType) {
        this.userAdsType = userAdsType;
    }

    public int getClickGold() {
        return clickGold;
    }

    public void setClickGold(int clickGold) {
        this.clickGold = clickGold;
    }

    public int getAdlocId() {
        return adlocId;
    }

    public void setAdlocId(int adlocId) {
        this.adlocId = adlocId;
    }

    public AdLocationBeans.AdLocationsBean.ClientAdvertisesBean getAdsRuleBean() {
        return adsRuleBean;
    }

    public void setAdsRuleBean(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adsRuleBean) {
        this.adsRuleBean = adsRuleBean;
    }

    public String getCurrentData() {
        return currentData;
    }

    public void setCurrentData(String currentData) {
        this.currentData = currentData;
    }

    public NativeAdInfo() {
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAdIconFlag() {
        return this.adIconFlag;
    }

    public void setAdIconFlag(String adIconFlag) {
        this.adIconFlag = adIconFlag;
    }

    public String getAdLogoFlag() {
        return this.adLogoFlag;
    }

    public void setAdLogoFlag(String adLogoFlag) {
        this.adLogoFlag = adLogoFlag;
    }

    public Object getOrigin() {
        return this.origin;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getIconWidth() {
        return this.iconWidth;
    }

    public void setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
    }

    public int getIconHeight() {
        return this.iconHeight;
    }

    public void setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageWidth() {
        return this.imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return this.imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public void onClick(final Context context, View view,AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        if(AdZhiZuNative.getRedState(bean)!=0 && !UserManager.getInst().hadLogin()){
            new DialogConfirm(context).setMessage(R.string.goto_login_for_red_str)
                    .setYesBtnText(R.string.goto_login_for_red_btn)
                    .setListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //有红包 且无登录状态 跳登录
                            ActivityUtils.startToBeforeLogingActivity(context);
                        }
                    }).show();
            return;
        }
        Map<String, String> map3 = new HashMap<>();
        switch (this.adFromName) {
            case AnnoCon.AD_TYPT_GDT:
                map3.put("AD_TYPT_GDT", "click_" + belong + "_" +index);
                ((NativeADDataRef) this.origin).onClicked(view);
                requestGold(view,bean);
                break;
            case AnnoCon.AD_TYPT_BAIDU:
                map3.put("AD_TYPT_BAIDU", "click_" + belong + "_" +index);
                ((NativeResponse) this.origin).handleClick(view);
                requestGold(view,bean);
                break;
            case AnnoCon.AD_TYPT_360:
                map3.put("360", "click_" + belong + "_" +index);
//                强转有问题   android.view.ContextThemeWrapper cannot be cast to android.app.Activity
//                ((NativeAd) this.origin).onAdClick((Activity) context, view);
                ((NativeAd) this.origin).onAdClick(scanForActivity(context), view);
                requestGold(view,bean);
                break;
            case AnnoCon.AD_TYPT_XW:
                map3.put("AD_TYPT_XW", "click_" + belong + "_" +index);
                if (null != mNonStandardTm) {
                    mNonStandardTm.adClicked();
                    ActivityUtils.startToAdWebViewAct(context, ((AdTuiABean) this.origin).getClick_url(),bean);
                }
                requestGold(view,bean);
                break;
            case AnnoCon.AD_TYPT_HUITOUTIAO:
                map3.put("AD_TYPT_HUITOUTIAO", "click_" + belong + "_" +index);
                ((ZkNativeAd) this.origin).onAdClick(view,bean);
                break;
            case AnnoCon.AD_TYPT_VLION:
                map3.put("AD_TYPT_VLION", "click_" + belong + "_" +index);
                ((ZkNativeAd) this.origin).onAdClick(view,bean);
                break;
            case AnnoCon.AD_TYPT_HUZHONG:
                map3.put("AD_TYPT_HUZHONG", "click_" + belong + "_" +index);
                ((ZkNativeAd) this.origin).onAdClick(view,bean);
                break;
            case AnnoCon.AD_TYPT_YUEMENG:
                map3.put("AD_TYPT_YUEMENG", "click_" + belong + "_" +index);
                ((ZkNativeAd) this.origin).onAdClick(view,bean);
                break;
            case AnnoCon.AD_TYPT_JRTT:
                map3.put("AD_TYPT_JRTT", "click_" + belong + "_" +index);
                requestGold(view,bean);
                break;
            case AnnoCon.AD_TYPT_JIGUANG:
                map3.put("AD_TYPT_JIGUANG", "click_" + belong + "_" +index);
                requestGold(view,bean);
                break;
            case AnnoCon.AD_TYPT_CHANGJIAN:
                map3.put("AD_TYPT_CHANGJIAN", "click_" + belong + "_" +index);
                ((ZkNativeAd) this.origin).onAdClick(view,bean);
                break;
            case AnnoCon.AD_TYPT_SHAIBO:
                map3.put("AD_TYPT_SHAIBO", "click_" + belong + "_" +index);
                ((ZkNativeAd) this.origin).onAdClick(view,bean);
                break;
            case AnnoCon.AD_TYPT_RUANGAO:
                map3.put("AD_TYPT_RUANGAO", "click_" + belong + "_" +index);
                ((ZkNativeAd) this.origin).onAdClick(view,bean);
                break;
            case AnnoCon.AD_TYPT_MAIYOU:
                map3.put("AD_TYPT_MAIYOU", "click_" + belong + "_" +index);
                ((ZkNativeAd) this.origin).onAdClick(view,bean);
                break;
            case AnnoCon.AD_TYPT_YITAN:
                map3.put("AD_TYPT_YITAN", "click_" + belong + "_" +index);
                requestGold(view,bean);
                break;
        }
        /////////////////293
        if("拆宝箱弹框".equals(belong)){
            MobclickAgent.onEvent(QuKanDianApplication.getmContext(), "293-baoxiangtanchuang_ad");
        }else if("签到弹框".equals(belong)){
            MobclickAgent.onEvent(QuKanDianApplication.getmContext(), "293-qiandaotuijian_ad");
        }
        ////////////////////统计 290加
        MobclickAgent.onEvent(context, "AdRequestCount", map3);
        ///////////////////////////////
        Map<String, String> map = new HashMap<>();
        map.put(belong, "" + index);
        MobclickAgent.onEvent(context, "AdIndexClick", map);
        Map<String, String> map1 = new HashMap<>();
        map1.put(belong, "" + index);
        MobclickAgent.onEvent(context, adsRuleBean.getAdverName(), map1);
    }

    public static void requestGold(final View view , final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean){
        if(AdZhiZuNative.getRedState(bean)!=0){
            int tempDuration = 10;
            //联盟广告只能配置一次点击，如果是多次点击，走默认写死配置，10s后获取奖励
            if(bean.getAdverClickRules()!=null && bean.getAdverClickRules().size()==1)
            {
                AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.AdClRulesBean firstRule = bean.getAdverClickRules().get(0);
                String duration = firstRule.getDuration();
                if(firstRule.getClickCount()==1)
                {
                    String durationStr= duration.split(",")[0];
                    tempDuration = Integer.parseInt(durationStr);
                }
            }
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PutNewAdClickProtocol mSubmitAdClickProtocol = new PutNewAdClickProtocol(view.getContext(), bean, new BaseModel.OnResultListener<AdResultBean>() {
                        @Override
                        public void onResultListener(final AdResultBean response) {
                            int anInt = UserSharedPreferences.getInstance().getInt(Constants.READ_ART_COUNT, 0);
                            UserSharedPreferences.getInstance().putInt(Constants.READ_ART_COUNT,anInt+1);
                            // 获取金币结果
                            // DBTools.saveMyselfAdBean(bean);
                            //PutNewAdClickProtocol.removeGoldAd(bean);
                            AdUtil.mAdsCache.add(bean.toString());
                            if ("first".equals(response.getDescription())) {
                                MobclickAgent.onEvent(QuKanDianApplication.getmContext(), "294-finishhongbao");
//                                new DialogNewPeopleTask(view.getContext(),null,true).show();
                                new DialogNewPeopleDoneTask(view.getContext(), "" + response.getGoldAmount()).show();
                                UserManager.getInst().goldChangeNofity(response.getGoldAmount());
                            } else if (!"0".equals(response.getDescription())) {
                                if (response.getGoldAmount() == 0) {//红包获取失败
                                    ToastUtils.showLongToast(view.getContext(),"红包获取失败");
                                } else if (response.getGoldAmount() > 0) {
                                    CustomToast.showToast(view.getContext(), response.getGoldAmount() + "", "阅读奖励");
                                    UserManager.getInst().goldChangeNofity(response.getGoldAmount());
                                } else {
                                    ToastUtils.showLongToast(view.getContext(),"奖励领取失败");
                                }
                            } else {
                                ToastUtils.showLongToast(view.getContext(),"奖励领取失败");
                            }
                        }

                        @Override
                        public void onFailureListener(int code, String error) {
                        }
                    });
                    mSubmitAdClickProtocol.postRequest();
                }
            },tempDuration*1000);
        }
    }

    public void onDisplay(Context context, View view) {
        Map<String, String> map3 = new HashMap<>();
        if (!isShow) {
            isShow = true;
            switch (this.adFromName) {
                case AnnoCon.AD_TYPT_GDT:
                    map3.put("AD_TYPT_GDT", "show_" + belong + "_" +index);
                    ((NativeADDataRef) this.origin).onExposured(view);
                    break;
                case AnnoCon.AD_TYPT_BAIDU:
                    map3.put("AD_TYPT_BAIDU", "show_" + belong + "_" +index);
                    ((NativeResponse) this.origin).recordImpression(view);
                    break;
                case AnnoCon.AD_TYPT_360:
                    map3.put("AD_TYPT_360", "show_" + belong + "_" +index);
                    ((NativeAd) this.origin).onAdShowed(view);
                    break;
                case AnnoCon.AD_TYPT_XW:
                    map3.put("AD_TYPT_XW", "show_" + belong + "_" +index);
                    if (null != mNonStandardTm) {
                        mNonStandardTm.adExposed();
                    }
                    break;
                case AnnoCon.AD_TYPT_HUITOUTIAO:
                    map3.put("AD_TYPT_HUITOUTIAO", "show_" + belong + "_" +index);
                    // 惠头条要求图片加载四分之三才能曝光上报，所以要在图片图片加载监听那边做
                    ((ZkNativeAd) this.origin).onAdReadied(view);
                    break;
                case AnnoCon.AD_TYPT_VLION:
                    map3.put("AD_TYPT_VLION", "show_" + belong + "_" +index);
                    ((ZkNativeAd) this.origin).onAdReadied(view);
                    ((ZkNativeAd) this.origin).onAdShowed(view);
                    break;
                case AnnoCon.AD_TYPT_HUZHONG:
                    map3.put("AD_TYPT_HUZHONG", "show_" + belong + "_" +index);
                    ((ZkNativeAd) this.origin).onAdReadied(view);
                    ((ZkNativeAd) this.origin).onAdShowed(view);
                    break;
                case AnnoCon.AD_TYPT_YUEMENG:
                    map3.put("AD_TYPT_YUEMENG", "show_" + belong + "_" +index);
                    ((ZkNativeAd) this.origin).onAdReadied(view);
                    ((ZkNativeAd) this.origin).onAdShowed(view);
                    break;
                case AnnoCon.AD_TYPT_JRTT:
                    map3.put("AD_TYPT_JRTT", "show_" + belong + "_" +index);
                    break;
                case AnnoCon.AD_TYPT_JIGUANG:
                    map3.put("AD_TYPT_JIGUANG", "show_" + belong + "_" +index);
                    break;
                case AnnoCon.AD_TYPT_CHANGJIAN:
                    map3.put("AD_TYPT_CHANGJIAN", "click_" + belong + "_" +index);
                    ((ZkNativeAd) this.origin).onAdShowed(view);
                    break;
                case AnnoCon.AD_TYPT_SHAIBO:
                    map3.put("AD_TYPT_CHANGJIAN", "show_" + belong + "_" +index);
                    ((ZkNativeAd) this.origin).onAdShowed(view);
                    break;
                case AnnoCon.AD_TYPT_RUANGAO:
                    map3.put("AD_TYPT_RUANGAO", "show_" + belong + "_" +index);
                    ((ZkNativeAd) this.origin).onAdShowed(view);
                    break;
                case AnnoCon.AD_TYPT_MAIYOU:
                    map3.put("AD_TYPT_MAIYOU", "show_" + belong + "_" +index);
                    ((ZkNativeAd) this.origin).onAdShowed(view);
                    break;
                case AnnoCon.AD_TYPT_YITAN:
                    map3.put("AD_TYPT_YITAN", "show_" + belong + "_" +index);
                    if(origin instanceof NativeResource){
                        final NativeResource nativeResource = (NativeResource) origin;
                        nativeResource.onExposed(view);
                        view.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if(nativeResource instanceof NativeResource){
                                    nativeResource.onTouch(v, event);
                                }
                                return false;
                            }
                        });
                    }
                    break;
            }
            ////////////////////统计 290加
            MobclickAgent.onEvent(context, "AdRequestCount", map3);
            ///////////////////////////////
            Map<String, String> map = new HashMap<>();
            map.put(belong, "" + index);
            MobclickAgent.onEvent(context, "AdIndex", map);

            Map<String, String> map1 = new HashMap<>();
            map1.put(belong, "" + index);
            MobclickAgent.onEvent(context, adsRuleBean.getAdverName(), map1);
        }
    }

    private Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NativeAdInfo{" +
                "origin=" + origin +
                ", adsRuleBean=" + adsRuleBean +
                ", iconUrl='" + iconUrl + '\'' +
                ", mNonStandardTm=" + mNonStandardTm +
                ", iconWidth=" + iconWidth +
                ", iconHeight=" + iconHeight +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageUrls=" + imageUrls +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                ", rating='" + rating + '\'' +
                ", id='" + id + '\'' +
                ", index=" + index +
                ", adlocId=" + adlocId +
                ", belong='" + belong + '\'' +
                ", adIconFlag='" + adIconFlag + '\'' +
                ", adLogoFlag='" + adLogoFlag + '\'' +
                ", currentData='" + currentData + '\'' +
                ", isShow=" + isShow +
                ", clickGold=" + clickGold +
                ", userAdsType=" + userAdsType +
                '}';
    }
}
