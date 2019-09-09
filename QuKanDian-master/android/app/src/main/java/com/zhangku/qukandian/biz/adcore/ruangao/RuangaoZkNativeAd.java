package com.zhangku.qukandian.biz.adcore.ruangao;

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
import com.zhangku.qukandian.biz.adbeen.ruangao.RuangaoResBean;
import com.zhangku.qukandian.biz.adbeen.saibo.SaiboResBean;
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

public class RuangaoZkNativeAd extends ZkNativeAdBase implements ZkNativeAd, AdDownObserver.OnAdDownListener {
    private NiceNativeAdDataBeen niceDataBeen;

    private boolean clcIsNice = false;
    private boolean expIsNice = false;
    private boolean isDowning;//是否下载提示   true 正在下载  false 下载完成
    int[] positionDown;
    int[] positionUp;

    public NiceNativeAdDataBeen getNiceDataBeen() {
        return niceDataBeen;
    }

    public void setNiceDataBeen(NiceNativeAdDataBeen niceDataBeen) {
        this.niceDataBeen = niceDataBeen;
    }

    @Override
    public void onAdClick(View view, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        if (view == null) return;
        final Context context = view.getContext();
        if (context == null) return;

        String clickUrl = niceDataBeen.getClickUrl();
        String deeplink = niceDataBeen.getDeeplink();

        if (niceDataBeen.getClickAction() == 1) {//下载步骤
//            if (isDowning) {//正在下载 弹框提示
//                ToastUtils.showShortToast(context, context.getString(R.string.ad_downing_text));
//            } else {
//                NativeAdInfo.requestGold(view, bean);
//                Long downID = openDownApk(clickUrl, niceDataBeen.getTitle(), context);
//                AdDownObserver.getInstance().addOnAdDownListener(downID, this);
//                onAdDownStartListener();
//            }
        } else {
            if (!openDeepLink(deeplink, context, bean)) {
                openUrl(context, clickUrl, bean);
            } else {//打开deeplink成功 上报deeplink click url
                //上报deeplink点击
                List<String> clc = niceDataBeen.getClc_deeplink();
                if (clc != null && clc.size() > 0) {
                    for (final String item : clc) {
                        OkHttp3Utils.getInstance().doGetWithUA(context, item, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                            }
                        });
                    }
                }
            }
        }
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
//            return str.replace("__CLICK_DOWN_X__", localPositionDown[0]+"")
//                    .replace("__CLICK_DOWN_Y__", localPositionDown[1]+"")
//                    .replace("__CLICK_UP_X__", localPositionUp[0]+"")
//                    .replace("__CLICK_UP_Y__", localPositionUp[1]+"")
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
    public NiceNativeAdDataBeen makeNiceNativeAdDataBeen(RuangaoResBean allBean) {
        //原生创意物料  adt=3时填写，原生创意物料详情见下
        if (allBean == null) return new NiceNativeAdDataBeen();
        NiceNativeAdDataBeen niceBeen = new NiceNativeAdDataBeen();
        try {

            //添加imageUrl
            if (!TextUtils.isEmpty(allBean.getAd_url()))
                niceBeen.setImageUrl(allBean.getAd_url());
            //添加imageUrls
            niceBeen.getImageUrls().add(allBean.getAd_url());
            niceBeen.setTitle(allBean.getTitle());
            niceBeen.setDescription(allBean.getDesc());

            //曝光监测地址
            if (allBean.getPv() != null) {
                for (RuangaoResBean.PvBean bean: allBean.getPv()) {
                    niceBeen.getExp().add(bean.getUrl());
                }
            }
            //点击监测地址
            if (allBean.getClick() != null) {
                for (RuangaoResBean.ClickBean bean: allBean.getClick()) {
                    niceBeen.getClc().add(bean.getUrl());
                }
            }

            niceBeen.setClickUrl(allBean.getTarget());// 点击行为地址
            niceBeen.setDeeplink(allBean.getDplurl());
            niceBeen.setClickIsDown(allBean.getAd_type() == 1);
//            if (allBean.getAction() == 2) {
//                niceBeen.setDownUrl(allBean.getLink());
//                SaiboResBean.ImpBean.ExtBean ext = allBean.getExt();
//                if (ext != null) {
//                    niceBeen.getDownUpUrlMap().put(STAER_DOWN, ext.getDs());
//                    niceBeen.getDownUpUrlMap().put(COMPLETE_DOWN, ext.getDf());
//                }
////                niceBeen.getDownUpUrlMap().put(COMPLETE_INSTANLL, allBean.getExt().getDs());
//            }
            this.niceDataBeen = niceBeen;
        } catch (Exception e) {

        }
        return this.niceDataBeen;
    }

    @Override
    public void onAdDownStartListener() {
        isDowning = true;
        List<String> start_down = niceDataBeen.getDownUpUrlMap().get(STAER_DOWN);
        if (start_down != null)
            for (final String url : start_down) {
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
        List<String> start_instanll = niceDataBeen.getDownUpUrlMap().get(STAER_INSTANLL);
        if (start_instanll != null)
            for (final String url : start_instanll) {
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
    public void onAdDownInstallCompleteListener() {
    }
}
