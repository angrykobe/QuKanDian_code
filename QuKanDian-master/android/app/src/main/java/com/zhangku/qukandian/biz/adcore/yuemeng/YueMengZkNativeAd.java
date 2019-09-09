package com.zhangku.qukandian.biz.adcore.yuemeng;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.biz.adbeen.NiceNativeAdDataBeen;
import com.zhangku.qukandian.biz.adbeen.yuemeng.YueMengResBean;
import com.zhangku.qukandian.biz.adcore.ZKAdTypeEnum;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.biz.adcore.ZkNativeAdBase;
import com.zhangku.qukandian.observer.AdDownObserver;
import com.zhangku.qukandian.utils.OkHttp3Utils;
import com.zhangku.qukandian.utils.ToastUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class YueMengZkNativeAd extends ZkNativeAdBase implements ZkNativeAd, AdDownObserver.OnAdDownListener {
    private NiceNativeAdDataBeen niceDataBeen;

    private Boolean isDowning = null;//是否下载提示   null 没有下载，true 正在下载  false 下载完成
    private boolean clcIsNice = false;
    private boolean expIsNice = false;
    int[] positionDown;
    int[] positionUp;

    private static final String COMPLETE_DOWN = "CompleteDown";
    private static final String COMPLETE_INSTANLL = "CompleteInstanll";

//    @Override
//    public ZKAdTypeEnum getTemplateType() {
//
//        ZKAdTypeEnum templateTypeEnum = ZKAdTypeEnum.NONE;
//        if (niceDataBeen != null) {
//            templateTypeEnum = niceDataBeen.getTemplateType();
//        }
//        return templateTypeEnum;
//    }

    @Override
    public void onAdClick(View view,AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        if (view == null) return;
        Context context = view.getContext();

        String url = niceDataBeen.getClickUrl();
        String downUrl = niceDataBeen.getDownUrl();
        int clickAction = niceDataBeen.getClickAction();
        if(clickAction == 1){
            if(true == isDowning){//正在下载 弹框提示
                ToastUtils.showShortToast(context,context.getString(R.string.ad_downing_text));
            }else{
                NativeAdInfo.requestGold(view,bean);
                Long downID = openDownApk(downUrl, niceDataBeen.getTitle(), context);
                AdDownObserver.getInstance().addOnAdDownListener(downID, this);
                onAdDownStartListener();
                if(!clcIsNice){
                    //上报点击
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
            }
        }else{
            openUrl(context, url,bean);
            if(!clcIsNice){
                //上报点击
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
        }
    }

    @Override
    public void onAdClick(Activity activity, View view) {

    }

    @Override
    public void onAdShowed(final View view) {
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
                // 不能返回true，否则view的click事件将失效
                return false;
            }
        });

        if (!expIsNice) {
            // 开始上报展示行为
            final List<String> fExp = niceDataBeen.getExp();
            // 延时上报
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        for (String item : fExp) {
                            final String url = item;
                            OkHttp3Utils.getInstance().doGetWithUA(view.getContext(), url, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    expIsNice = true;
                                }
                            });
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {

                    }
                }
            }).start();
        }

    }

//    //点击监测宏替换
//    public String replaceMacro(String str) {
//        if (TextUtils.isEmpty(str)) return "";
//        try {
//            int[] localPositionDown = {-999, -999};
//            int[] localPositionUp = {-999, -999};
//            if (positionDown != null) {
//                localPositionDown = positionDown;
//            }
//            if (positionUp != null) {
//                localPositionUp = positionUp;
//            }
//
//            return str.replace("__CLICK_DOWN_X__", localPositionDown[0] + "")
//                    .replace("__CLICK_DOWN_Y__", localPositionDown[1] + "")
//                    .replace("__CLICK_UP_X__", localPositionUp[0] + "")
//                    .replace("__CLICK_UP_Y__", localPositionUp[1] + "")
//                    ;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
    @Override
    public void onAdReadied(View view) {

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

    @Nullable
    @Override
    public String getAdSpaceId() {
        return null;
    }

    @Nullable
    @Override
    public JSONObject getAPPInfo() {
        return null;
    }


    /**
     * 根据返回的结果，制造符合系统需要的数据
     *
     * @param allBean //  点击行为   0-打开网页，1-下载；不填写默认为打开网页
     * @return
     */
    public NiceNativeAdDataBeen makeNiceNativeAdDataBeen(YueMengResBean.AdsBean allBean) {
        //原生创意物料  adt=3时填写，原生创意物料详情见下
        NiceNativeAdDataBeen niceBeen = new NiceNativeAdDataBeen();
        try {
            niceBeen.setImageUrl(allBean.getAd_pic());
            niceBeen.getImageUrls().add(allBean.getAd_pic());
            //添加描述
            niceBeen.setDescription(allBean.getContent());
            //添加icon
            niceBeen.setIconUrl(allBean.getDesc());
            // title
            niceBeen.setTitle(allBean.getText_title());
            //设置大图广告模式（视频列表）
            niceBeen.setTemplateType(ZKAdTypeEnum.FLOW_THREE_IMG);
            //曝光监测地址
            if (allBean.getShowurl() != null) {
                niceBeen.setExp(allBean.getShowurl());
            }
            //点击监测地址
            if (allBean.getClickurl() != null) {
                niceBeen.setClc(allBean.getClickurl());
            }

            YueMengResBean.AdsBean.ActionParam actionParam = new Gson().fromJson(allBean.getAdhotactionparam(), YueMengResBean.AdsBean.ActionParam.class);

            niceBeen.setClickUrl(actionParam.getUrl());// 点击行为地址
            niceBeen.setClickIsDown("安装".equals(allBean.getAdhotactiontype()) ? 1 : 2);// （ install: 安 装;show:展示）

            if ("安装".equals(allBean.getAdhotactiontype())) {
                niceBeen.setDownUrl(actionParam.getUrl());
                niceBeen.getDownUpUrlMap().put(COMPLETE_DOWN, actionParam.getCpd_report_urls());
                niceBeen.getDownUpUrlMap().put(COMPLETE_INSTANLL, actionParam.getCpa_report_urls());
            }
        } catch (Exception e) {

        }
        this.niceDataBeen = niceBeen;

        return this.niceDataBeen;
    }

    public NiceNativeAdDataBeen getNiceDataBeen() {
        return niceDataBeen;
    }

    public void setNiceDataBeen(NiceNativeAdDataBeen niceDataBeen) {
        this.niceDataBeen = niceDataBeen;
    }

    @Override
    public void onAdDownStartListener() {
        isDowning = true;

    }

    @Override
    public void onAdDownCompleteListener() {
        isDowning = false;
        List<String> downCom = niceDataBeen.getDownUpUrlMap().get(COMPLETE_DOWN);
        if (downCom != null)
            for (final String url : downCom) {
                OkHttp3Utils.getInstance().doGet(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    }
                });
            }
    }

    @Override
    public void onAdDownInstallStartListener() {

    }

    @Override
    public void onAdDownInstallCompleteListener() {

    }
}
