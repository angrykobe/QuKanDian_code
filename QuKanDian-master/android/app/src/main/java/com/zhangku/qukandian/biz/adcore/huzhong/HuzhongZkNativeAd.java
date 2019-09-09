package com.zhangku.qukandian.biz.adcore.huzhong;

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
import com.zhangku.qukandian.biz.adbeen.huzhong.HuzhongAdResponseBean;
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

public class HuzhongZkNativeAd extends ZkNativeAdBase implements ZkNativeAd, AdDownObserver.OnAdDownListener {
    private NiceNativeAdDataBeen niceDataBeen;

    private boolean expIsNice = false;
    private boolean isDowning;//是否下载提示   true 正在下载  false 下载完成
    int[] positionDown;
    int[] positionUp;

    @Override
    public void onAdClick(View view, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        if (view == null) return;
        final Context context = view.getContext();
        if (context == null) return;

        String clickUrl = niceDataBeen.getClickUrl();
        String deeplink = niceDataBeen.getDeeplink();

        if (niceDataBeen.getClickAction() == 0) {//下载步骤
            if (isDowning) {//正在下载 弹框提示
                ToastUtils.showShortToast(context, context.getString(R.string.ad_downing_text));
            } else {
                NativeAdInfo.requestGold(view,bean);
                clickUrl = replaceMacro(clickUrl, view);
                Long downID = openDownApk(clickUrl, niceDataBeen.getTitle(), context);
                AdDownObserver.getInstance().addOnAdDownListener(downID, this);
                onAdDownStartListener();
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
        } else {
            if (!openDeepLink(deeplink, context,bean)) {
                String url = replaceMacro(clickUrl, view);

                openUrl(context, url,bean);
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
            try {
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
                }
            } catch (Exception e) {
            }
        }
    }

    //点击监测宏替换
    public String replaceMacro(String str, View view) {
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

            return str.replace("__AZMX__", localPositionDown[0] + "")
                    .replace("__AZMY__", localPositionDown[1] + "")
                    .replace("__AZCX__", localPositionUp[0] + "")
                    .replace("__AZCY__", localPositionUp[1] + "")
                    .replace("__WIDTH__", view.getWidth() + "")
                    .replace("__HEIGHT__", view.getHeight() + "");
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
    public void makeNiceNativeAdDataBeen(HuzhongAdResponseBean.AdBean allBean) {
        NiceNativeAdDataBeen niceBeen = new NiceNativeAdDataBeen();
        try {
            niceBeen.setImageUrl(allBean.getSrc());
            niceBeen.getImageUrls().add(allBean.getSrc());
            //添加描述
            niceBeen.setDescription(allBean.getDesc());
            // 如果title没有就展示decription
            if (!TextUtils.isEmpty(allBean.getTitle())) {
                niceBeen.setTitle(allBean.getTitle());
            } else if (!TextUtils.isEmpty(allBean.getDesc())) {
                niceBeen.setTitle(allBean.getDesc());
            } else {
                niceBeen.setTitle("");
            }
//            //设置大图广告模式（视频列表）
//            niceBeen.setTemplateType(ZKAdTypeEnum.FLOW_THREE_IMG);
            //曝光监测地址
            HuzhongAdResponseBean.AdBean.ImpBean imp = allBean.getImp();
            niceBeen.setExp(imp.getimpList());
            //点击监测地址
            if (allBean.getClk() != null)
                niceBeen.getClc().addAll(allBean.getClk());

            niceBeen.setClickUrl(allBean.getUrl());// 点击行为地址
            //定义动作，各平台api不同定义而不同
            niceBeen.setClickIsDown(allBean.getAction());//广告对应的动作，移动类型的Ad使用。 0：下载， 1：浏览器打开， 2：通知栏推送

            niceBeen.setDeeplink(niceBeen.getDeeplink());//deeplink地址，如该字段为null，则不执行deeplink
            if (allBean.getDp_clk() != null)
                niceBeen.getClc_deeplink().addAll(allBean.getDp_clk());

            if (allBean.getAction() == 0) {
                niceBeen.setDownUrl(allBean.getUrl());
                niceBeen.getDownUpUrlMap().put(STAER_DOWN, allBean.getDownload_urls());
                niceBeen.getDownUpUrlMap().put(COMPLETE_DOWN, allBean.getDownloaded_urls());
                niceBeen.getDownUpUrlMap().put(STAER_INSTANLL, allBean.getInstall_urls());
                niceBeen.getDownUpUrlMap().put(COMPLETE_INSTANLL, allBean.getInstalled_urls());
            }

        } catch (Exception e) {

        }
        this.niceDataBeen = niceBeen;
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
