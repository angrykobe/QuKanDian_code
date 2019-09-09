package com.zhangku.qukandian.widght;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogConfirm;
import com.zhangku.qukandian.interfaces.OnClickApkDownloadListener;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.ApkDownloadUtils;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.SetWebSettings;
import com.zhangku.qukandian.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自主广告视频列表原生展示  视频自主广告展示模板
 */
public class MyselfAdViewForVideoDetail extends LinearLayout {
    private WebView linkWebview;//
    private View myselfView;//
    private TextView titleTV;//标题
    private ImageView bgIV;//右图
    private LinearLayout bottomRedView;//底部红包标识
    private ImageView bottomRedIv;//
    private Context mContext;
    private LinearLayout linearLayout;

    public MyselfAdViewForVideoDetail(Context context) {
        this(context, null);
    }

    public MyselfAdViewForVideoDetail(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyselfAdViewForVideoDetail(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initUI(context);
    }

    private void initUI(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_myself_ad_for_detail, this);
        linearLayout = findViewById(R.id.ll);
        this.myselfView = findViewById(R.id.myselfView);

        this.bottomRedView = findViewById(R.id.bottomView);
        this.bottomRedIv = findViewById(R.id.bottomRedIV);
        this.bgIV = findViewById(R.id.imgIV);
        this.titleTV = findViewById(R.id.titleTV);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setData(final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean,final OnClickApkDownloadListener listener) {
        if (bean.getAdType() == 3) { //1 自主广告 2 联盟广告 3 合作链接
            linearLayout.setVisibility(View.VISIBLE);
            myselfView.setVisibility(View.GONE);
            if(this.linkWebview == null){
                this.linkWebview = new WebView(QuKanDianApplication.getContext());
                linearLayout.addView(linkWebview);
            }

            String url = bean.getAdLink()
                    + "?advertiserId=" + bean.getAdverId()
                    + "&Advertisinglogo=" + (bean.isIsShowAdsIcon() ? 1 : 0)
                    + "&redenvelope=" + AdZhiZuNative.getRedState(bean)
                    + "&envelopeNum=" + bean.getClickGold()
                    + "&version=" + QuKanDianApplication.getCode();
            SetWebSettings.setWebview(mContext, linkWebview, bean);
            linkWebview.loadUrl(url);
            ViewGroup.LayoutParams lp = linkWebview.getLayoutParams();
            if (AdZhiZuNative.getRedState(bean) != 0) {
                lp.height = DisplayUtils.dip2px(mContext, 140.0f);
            } else {
                lp.height = DisplayUtils.dip2px(mContext, 100.0f);
            }
            linkWebview.setLayoutParams(lp);
            this.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToastUtils.showLongToast(v.getContext(), "" + bean.getAdverName());
                    return false;
                }
            });
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("zhizu", "show_" + bean.getBelong() + "_" + bean.getPageIndex());
            MobclickAgent.onEvent(getContext(), "AdRequestCount", map);
            linearLayout.setVisibility(View.GONE);
            myselfView.setVisibility(View.VISIBLE);
            findViewById(R.id.mAdSignIV).setVisibility(bean.isIsShowAdsIcon() ? View.VISIBLE : View.GONE);

            List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.AdMaterialImagesBean> adMaterialImages = bean.getAdMaterialImages();
            if (adMaterialImages == null || adMaterialImages.size()==0) return;
            GlideUtils.displayImage(mContext, adMaterialImages.get(0).getSrc(), bgIV);
            titleTV.setText("" + bean.getTitle());
            final String targetAdLink = bean.getAdLink();//点击链接

            switch (AdZhiZuNative.getRedState(bean)) {
                case 1://红包
                    bottomRedView.setVisibility(View.VISIBLE);
                    bottomRedIv.setImageResource(R.mipmap.ad_red);
                    break;
                case 2://金包
                    bottomRedView.setVisibility(View.VISIBLE);
                    bottomRedIv.setImageResource(R.mipmap.ad_yellow);
                    break;
                case 0:
                default:
                    bottomRedView.setVisibility(View.GONE);
                    break;
            }

            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new HashMap<>();
                    map.put("zhizu", "click_" + bean.getBelong() + "_" + bean.getPageIndex());
                    MobclickAgent.onEvent(getContext(), "AdRequestCount", map);
                    if (targetAdLink.contains(".apk")) {
//                        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(targetAdLink));
//                        mContext.startActivity(viewIntent);
                        ApkDownloadUtils.doAdApkDownload(mContext, targetAdLink, bean, listener);
                    }
                    if (!UserManager.getInst().hadLogin() && bottomRedView.getVisibility() == View.VISIBLE) {//有红包，且无登陆，跳转登陆界面
                        new DialogConfirm(mContext).setMessage(R.string.goto_login_for_red_str)
                                .setYesBtnText(R.string.goto_login_for_red_btn)
                                .setListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //有红包 且无登录状态 跳登录
                                        ActivityUtils.startToBeforeLogingActivity(mContext);
                                    }
                                }).show();
                    } else {
                        if (bean.getDeliveryMode() == 1) {
                            ActivityUtils.startToAdWebViewAct(mContext, targetAdLink, bean);
                        } else {
                            ActivityUtils.startToUrlActivity(mContext, targetAdLink, Constants.URL_FROM_ADS, bottomRedView.getVisibility() == View.VISIBLE, bean);
                        }
//                        ActivityUtils.startToUrlActivity(mContext, targetAdLink, Constants.URL_FROM_ADS, bottomRedView.getVisibility() == View.VISIBLE, bean);
                    }
                }
            });
        }
    }
}
