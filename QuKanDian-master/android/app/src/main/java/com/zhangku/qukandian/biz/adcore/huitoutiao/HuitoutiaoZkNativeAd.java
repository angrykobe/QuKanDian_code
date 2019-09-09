package com.zhangku.qukandian.biz.adcore.huitoutiao;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.biz.adbeen.NiceNativeAdDataBeen;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.AdCreative;
import com.zhangku.qukandian.biz.adbeen.huitoutiao.AdInfoVO;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.biz.adcore.ZkNativeAdBase;
import com.zhangku.qukandian.observer.AdDownObserver;
import com.zhangku.qukandian.utils.OkHttp3Utils;
import com.zhangku.qukandian.utils.ToastUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HuitoutiaoZkNativeAd extends ZkNativeAdBase implements ZkNativeAd, AdDownObserver.OnAdDownListener {
    NiceNativeAdDataBeen niceDataBeen;

    private boolean clcIsNice = false;//去重参数
    private boolean expIsNice = false;//去重参数
    private boolean isDowning = false;//是否正在下载

    int[] positionDown;
    int[] positionUp;

    public NiceNativeAdDataBeen getNiceDataBeen() {
        return niceDataBeen;
    }

    public void setNiceDataBeen(NiceNativeAdDataBeen niceDataBeen) {
        this.niceDataBeen = niceDataBeen;
    }

    /**
     * 根据返回的结果，制造符合系统需要的数据
     *
     * @param itemData
     * @return
     */
    public NiceNativeAdDataBeen makeNiceNativeAdDataBeen(AdInfoVO itemData) {
        if (itemData == null) return null;
        NiceNativeAdDataBeen niceBeen = new NiceNativeAdDataBeen();
        try {
            if (itemData.getAdCreative() != null) {
                AdCreative adCreative = itemData.getAdCreative();
                List<String> imgList = adCreative.getImgList();
                if (imgList != null && imgList.size() > 0) {
                    niceBeen.setImageUrl(imgList.get(0));
                    niceBeen.setImageUrls(imgList);
                } else {
                    niceBeen.setImageUrl("");
                    niceBeen.setImageUrls(new ArrayList<String>());
                }
                if (adCreative.getDescription() != null) {
                    niceBeen.setDescription(adCreative.getDescription());
                }
                if (adCreative.getIcon() != null) {
                    niceBeen.setIconUrl(adCreative.getIcon());
                }
                // 如果title没有就展示decription
                if (!TextUtils.isEmpty(adCreative.getTitle())) {
                    niceBeen.setTitle(adCreative.getTitle());
                } else if (!TextUtils.isEmpty(adCreative.getDescription())) {
                    niceBeen.setTitle(adCreative.getDescription());
                } else {
                    niceBeen.setTitle("");
                }
            }

            if (itemData.getEventTracking() != null) {
                Map<String, List<String>> eventTracking = itemData.getEventTracking();
                if (eventTracking != null && eventTracking.size() > 0) {
                    if (eventTracking.containsKey("clc")) {
                        niceBeen.setClc(eventTracking.get("clc"));
                    }
                    if (eventTracking.containsKey("exp")) {//展示上报list
                        niceBeen.setExp(eventTracking.get("exp"));
                    }
                }
            }

            niceBeen.setClickUrl(itemData.getClickUrl());
            niceBeen.setClickIsDown(itemData.getClickAction());// 交互类型，1为下载, 2为打开落地页。
            niceBeen.setDeeplink(itemData.getDeeplink());
        } catch (Exception e) {

        }
        this.niceDataBeen = niceBeen;

        return this.niceDataBeen;
    }

//    @Override
//    public ZKAdTypeEnum getTemplateType() {
//        ZKAdTypeEnum templateTypeEnum = ZKAdTypeEnum.NONE;
//        if (niceDataBeen != null) {
//            templateTypeEnum = niceDataBeen.getTemplateType();
//        }
//        return templateTypeEnum;
//    }

    @Override
    public void onAdClick(View view,AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        try {
            Context context = view.getContext();
            if (view == null || context==null) return;

            String deeplink = niceDataBeen.getDeeplink();
            String clickUrl = niceDataBeen.getClickUrl();
            // 交互类型，1为下载, 2为打开落地页。
            if(niceDataBeen.getClickAction() == 1){
                if(!isDowning){
//                    openUrl(context, "");
                    NativeAdInfo.requestGold(view,bean);
                    Long downID = openDownApk(clickUrl, niceDataBeen.getTitle(), context);
                    AdDownObserver.getInstance().addOnAdDownListener(downID, this);
                    onAdDownStartListener();
                }else{
                    ToastUtils.showShortToast(context,context.getString(R.string.ad_downing_text));
                }
            }else{
                if (!openDeepLink(deeplink, context,bean)) {
                    String url = replaceMacro(clickUrl);
                    openUrl(context, url,bean);
                } else {//打开deeplink成功 上报deeplink click url
                }
            }
            //惠头条deeplek 下载 点击 上报都是同一个
            if (!clcIsNice) {
                // 开始上报点击行为
                List<String> clc = niceDataBeen.getClc();
                if (clc != null && clc.size() > 0) {
                    for (final String item : clc) {
                        OkHttp3Utils.getInstance().doGetWithUA(context, item, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                clcIsNice = true;
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onAdShowed(View view) {
        try {
            if (!expIsNice) {
                // 开始上报展示行为
                final List<String> fExp = niceDataBeen.getExp();
                // 延时上报
                if (fExp != null && fExp.size() > 0) {
                    for (String item : fExp) {
                        if (!TextUtils.isEmpty(item)) {
                            final String url = item;
                            OkHttp3Utils.getInstance().doGet(url, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    expIsNice = true;
                                }
                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onAdReadied(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int[] positionTouch = {(int) motionEvent.getX(), (int) motionEvent.getY()};
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        positionDown = positionTouch;
                        break;
                    case MotionEvent.ACTION_UP:
                        positionUp = positionTouch;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;
            }
        });
    }

    public String replaceMacro(String str) {
        if (TextUtils.isEmpty(str)) return "";
        try {
            int[] localPositionDown = {-999, -999};
            int[] localPositionUp = {-999, -999};
            if (positionDown != null) {
                localPositionDown = positionDown;
            }
            if (positionUp != null) {
                localPositionUp = positionUp;
            }

            return str.replace("__CLICK_DOWN_X__", localPositionDown[0] + "")
                    .replace("__CLICK_DOWN_Y__", localPositionDown[1] + "")
                    .replace("__CLICK_UP_X__", localPositionUp[0] + "")
                    .replace("__CLICK_UP_Y__", localPositionUp[1] + "")
                    ;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onAdClick(Activity activity, View view) {

    }

    @Override
    public void onAdClosed() {

    }

    @Override
    public int getActionType() {
        return 0;
    }

    @Override
    public int getAPPStatus() {
        return 0;
    }

    @Override
    public int getProgress() {
        return 0;
    }

    @Override
    public String getAdSpaceId() {
        return null;
    }

    @Override
    public JSONObject getAPPInfo() {
        return null;
    }

    @Override
    public void onAdDownStartListener() {
        isDowning = true;
    }

    @Override
    public void onAdDownCompleteListener() {
        isDowning = false;
    }

    @Override
    public void onAdDownInstallStartListener() {

    }

    @Override
    public void onAdDownInstallCompleteListener() {

    }
}
