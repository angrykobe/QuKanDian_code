package com.zhangku.qukandian.biz.adcore.vlion;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.biz.adbeen.NiceNativeAdDataBeen;
import com.zhangku.qukandian.biz.adbeen.vlion.VlionAdResponseBean;
import com.zhangku.qukandian.biz.adcore.ZKAdTypeEnum;
import com.zhangku.qukandian.biz.adcore.ZkNativeAd;
import com.zhangku.qukandian.biz.adcore.ZkNativeAdBase;
import com.zhangku.qukandian.observer.AdDownObserver;
import com.zhangku.qukandian.utils.OkHttp3Utils;
import com.zhangku.qukandian.utils.ToastUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class VlionZkNativeAd extends ZkNativeAdBase implements ZkNativeAd, AdDownObserver.OnAdDownListener {
    private NiceNativeAdDataBeen niceDataBeen;

    private boolean clcIsNice = false;
    private boolean expIsNice = false;
    int[] positionDown;
    int[] positionUp;
    private boolean isDowning;//是否下载提示   true 正在下载  false 下载完成

    @Override
    public void onAdClick(View view, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        if (view == null) return;
        Context context = view.getContext();
        if (context == null) return;

        String deeplink = niceDataBeen.getDeeplink();
        String clickUrl = replaceMacro(niceDataBeen.getClickUrl());
        String downUrl = replaceMacro(niceDataBeen.getDownUrl());

        if(openDeepLink(deeplink, context, bean)){
            //上报deeplink点击
            List<String> clc = niceDataBeen.getClc_deeplink();
            if (clc != null && clc.size() > 0) {
                for (final String item : clc) {
                    OkHttp3Utils.getInstance().doGetWithUA(context, replaceMacro(item), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                        }
                    });
                }
            }
        }else if(niceDataBeen.getClickAction() == 1){//下载步骤
            if (isDowning) {//正在下载 弹框提示
                ToastUtils.showShortToast(context, context.getString(R.string.ad_downing_text));
            } else {
                NativeAdInfo.requestGold(view,bean);
                Long downID = openDownApk(downUrl, niceDataBeen.getTitle(), context);
                AdDownObserver.getInstance().addOnAdDownListener(downID, this);
                onAdDownStartListener();
            }
        }else{
            openUrl(context, clickUrl, bean);
        }
        //上报点击
        List<String> clc = niceDataBeen.getClc();
        if (!clcIsNice && clc != null && clc.size() > 0) {
            for (final String item : clc) {
                OkHttp3Utils.getInstance().doGetWithUA(context, replaceMacro(item), new Callback() {
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
                        for (final String item : fExp) {
                            OkHttp3Utils.getInstance().doGetWithUA(view.getContext(), item, new Callback() {
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

    //点击监测宏替换
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
                    .replace("__CLICK_ID__", localPositionUp[1] + "")
                    ;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

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
    public NiceNativeAdDataBeen makeNiceNativeAdDataBeen(VlionAdResponseBean allBean) {
        //原生创意物料  adt=3时填写，原生创意物料详情见下
        VlionAdResponseBean.NativeadBean itemData = allBean.getNativead();
        if (itemData == null) return new NiceNativeAdDataBeen();
        NiceNativeAdDataBeen niceBeen = new NiceNativeAdDataBeen();
        try {
            if (itemData != null) {
                for (VlionAdResponseBean.NativeadBean.ImgBean bean : itemData.getImg()) {
                    //添加imageUrl
                    if (TextUtils.isEmpty(niceBeen.getImageUrl()))
                        niceBeen.setImageUrl(bean.getUrl());
                    //添加imageUrls
                    niceBeen.getImageUrls().add(bean.getUrl());
                }
                //添加描述
                niceBeen.setDescription(itemData.getDesc());
                //添加icon
                if (itemData.getIcon() != null && itemData.getIcon().size() > 0) {
                    niceBeen.setIconUrl(itemData.getIcon().get(0).getUrl());
                }
                // 如果title没有就展示decription
                if (!TextUtils.isEmpty(itemData.getTitle())) {
                    niceBeen.setTitle(itemData.getTitle());
                } else if (!TextUtils.isEmpty(itemData.getDesc())) {
                    niceBeen.setTitle(itemData.getDesc());
                } else {
                    niceBeen.setTitle("");
                }
                //设置大图广告模式（视频列表）
                niceBeen.setTemplateType(ZKAdTypeEnum.FLOW_THREE_IMG);
            }

            niceBeen.setExp(allBean.getImp_tracking());//曝光监测地址
            niceBeen.setClc(allBean.getClk_tracking()); //点击监测地址
            niceBeen.getClc_deeplink().addAll(allBean.getDp_tracking());
            niceBeen.setClickUrl(itemData.getLdp());// 落地页
            niceBeen.setDeeplink(itemData.getDeeplink());//deeplink

            niceBeen.setClickIsDown(allBean.getInteract_type() == 1);//  点击行为   0-打开网页，1-下载；不填写默认为打开网页
            if (allBean.getInteract_type() == 1) {
                //下载链接 5-下载开始 6-安装完成 7-下载完成 8-安装开始 9-应用激活
                List<VlionAdResponseBean.ConvTrackingBean> conv_tracking = allBean.getConv_tracking();
                for (VlionAdResponseBean.ConvTrackingBean convTrackingBean : conv_tracking) {
                    List<String> list = new ArrayList<>();
                    list.add(convTrackingBean.getUrl());
                    switch (convTrackingBean.getTrack_type()) {
                        case 5:
                            niceBeen.getDownUpUrlMap().put(STAER_DOWN, list);
                            break;
                        case 6:
                            niceBeen.getDownUpUrlMap().put(COMPLETE_INSTANLL, list);
                            break;
                        case 7:
                            niceBeen.getDownUpUrlMap().put(COMPLETE_DOWN, list);
                            break;
                        case 8:
                            niceBeen.getDownUpUrlMap().put(STAER_INSTANLL, list);
                            break;
                    }
                }
                niceBeen.setDownUrl(itemData.getApp_download_url());//下载地址

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
        List<String> start_down = niceDataBeen.getDownUpUrlMap().get(STAER_DOWN);
        if (start_down != null)
            for (final String url : start_down) {
                OkHttp3Utils.getInstance().doGet(replaceMacro(url), new Callback() {
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
    public void onAdDownCompleteListener() {
        isDowning = false;
        List<String> downCom = niceDataBeen.getDownUpUrlMap().get(COMPLETE_DOWN);
        if (downCom != null)
            for (final String url : downCom) {
                OkHttp3Utils.getInstance().doGet(replaceMacro(url), new Callback() {
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
        List<String> start_instanll = niceDataBeen.getDownUpUrlMap().get(STAER_INSTANLL);
        if (start_instanll != null)
            for (final String url : start_instanll) {
                OkHttp3Utils.getInstance().doGet(replaceMacro(url), new Callback() {
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
    public void onAdDownInstallCompleteListener() {
    }
}
