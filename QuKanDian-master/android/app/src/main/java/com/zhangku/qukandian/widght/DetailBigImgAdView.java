package com.zhangku.qukandian.widght;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.shulian.sdk.NativeAds;
import com.tencent.smtt.sdk.WebView;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogConfirm;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.SetWebSettings;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.adsdk.api.nati.NativeADDataRef;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/10
 * 详情大图广告view
 */
public class DetailBigImgAdView extends FrameLayout {
    private View mDetailOtherAdView;//联盟广告view
    private TextView mTvTitle;
    private ImageView mTvTopAdText;//广告文字图片
    private ImageView mAdSignIV;//红包 -- 广告文字图片
    private ImageView mIvAdicon;
    private RelativeLayout mLayoutAd;//广告红包
    private ImageView mIvBg;
    private Object object;
    private WebView mDetailMySelfAdView;
    private FrameLayout addView;
    private ImageView logoImageView;
    private TextView ad_dec;

    public DetailBigImgAdView(@NonNull Context context) {
        this(context, null);
    }

    public DetailBigImgAdView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailBigImgAdView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_detail_big_img_ad, this);

        mDetailOtherAdView = findViewById(R.id.details_ad_view_layout);
        mTvTopAdText = findViewById(R.id.ad_text_top);
        mAdSignIV = findViewById(R.id.ad_text_bottom);
        mIvAdicon = findViewById(R.id.ad_icon);
        mLayoutAd = findViewById(R.id.ad_layout_text);
        addView = findViewById(R.id.addView);
        mTvTitle = findViewById(R.id.detials_ad_view_title);
        mIvBg = findViewById(R.id.detials_ad_view_img);

        logoImageView = findViewById(R.id.logoImageView);
        ad_dec = findViewById(R.id.ad_dec);
        //自主webview广告
        mDetailMySelfAdView = findViewById(R.id.details_ssp_ad);

        ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) mIvBg.getLayoutParams();
        linearParams.width = DisplayUtils.getScreenWidth(getContext());
        linearParams.height = DisplayUtils.getScreenWidth(getContext()) / 2;
        mIvBg.setLayoutParams(linearParams);
    }

    public void setNullData() {
        DetailBigImgAdView.this.setVisibility(View.GONE);
    }

    // 添加自主url广告
    public void setUrlAdData(final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        object = bean;
        if (bean.getAdType() == 3) { //1 自主广告 2 联盟广告 3 合作链接
            String url = bean.getAdLink()
                    + "?advertiserId=" + bean.getAdverId()
                    + "&Advertisinglogo=" + (bean.isIsShowAdsIcon() ? 1 : 0)
                    + "&redenvelope=" + AdZhiZuNative.getRedState(bean)
                    + "&envelopeNum=" + bean.getClickGold()
                    + "&version=" + QuKanDianApplication.getCode();

            DetailBigImgAdView.this.setVisibility(View.VISIBLE);
            mDetailMySelfAdView.setVisibility(View.VISIBLE);
            mDetailOtherAdView.setVisibility(View.GONE);
            SetWebSettings.setWebview(getContext(), mDetailMySelfAdView, bean);
            mDetailMySelfAdView.loadUrl(url);
            mDetailMySelfAdView.setTag(url);
            ViewGroup.LayoutParams lp = mDetailMySelfAdView.getLayoutParams();
            if (AdZhiZuNative.getRedState(bean) != 0) {
                lp.height = DisplayUtils.getScreenWidth(getContext()) / 2 + 45;
            } else {
                lp.height = DisplayUtils.getScreenWidth(getContext()) / 2;
            }
            mDetailMySelfAdView.setLayoutParams(lp);
        } else {
            DetailBigImgAdView.this.setVisibility(View.VISIBLE);
            mTvTitle.setText(bean.getTitle());
            mAdSignIV.setVisibility(bean.isIsShowAdsIcon() ? View.VISIBLE : View.GONE);
            //图片
            if (bean.getAdMaterialImages() != null && bean.getAdMaterialImages().size() > 0)
                GlideUtils.displayImage(QuKanDianApplication.getAppContext(), bean.getAdMaterialImages().get(0).getSrc(), mIvBg);
            //红包展示
            switch (AdZhiZuNative.getRedState(bean)) {
                case 1://红包
                    mLayoutAd.setVisibility(View.VISIBLE);
                    mTvTopAdText.setVisibility(View.GONE);
                    mIvAdicon.setBackgroundResource(R.mipmap.ad_red);
                    break;
                case 2://金包
                    mLayoutAd.setVisibility(View.VISIBLE);
                    mTvTopAdText.setVisibility(View.GONE);
                    mIvAdicon.setBackgroundResource(R.mipmap.ad_yellow);
                    break;
                case 0:
                default:
                    mLayoutAd.setVisibility(View.GONE);
                    mTvTopAdText.setVisibility(View.VISIBLE);
                    break;
            }

            mDetailOtherAdView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getAdLink().contains(".apk")) {
                        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bean.getAdLink()));
                        getContext().startActivity(viewIntent);
                    }
                    if (!UserManager.getInst().hadLogin() && mLayoutAd.getVisibility() == View.VISIBLE) {//有红包，且无登陆，跳转登陆界面
                        new DialogConfirm(getContext()).setMessage(R.string.goto_login_for_red_str)
                                .setYesBtnText(R.string.goto_login_for_red_btn)
                                .setListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //有红包 且无登录状态 跳登录
                                        ActivityUtils.startToBeforeLogingActivity(getContext());
                                    }
                                }).show();
                    } else {
                        ActivityUtils.startToUrlActivity(getContext(), bean.getAdLink(), Constants.URL_FROM_ADS, mLayoutAd.getVisibility() == View.VISIBLE, bean);
                    }
                }
            });
        }
    }

    // 添加联盟广告
    public void setNativeData(final NativeAdInfo nativeAdInfo) {
        object = nativeAdInfo;
        DetailBigImgAdView.this.setVisibility(View.VISIBLE);

        if (nativeAdInfo.getOrigin() instanceof View) {
            mDetailOtherAdView.setVisibility(View.GONE);
            addView.setVisibility(View.VISIBLE);
            View view = (View) nativeAdInfo.getOrigin();
            addView.removeAllViews();
            addView.addView(view);
            return;
        } else {
            mDetailOtherAdView.setVisibility(View.VISIBLE);
            addView.setVisibility(View.GONE);
            mTvTitle.setText(nativeAdInfo.getTitle());
            GlideUtils.displayImageNice(QuKanDianApplication.getAppContext(), nativeAdInfo.getImageUrl(), mIvBg, this, nativeAdInfo);
        }

        //红包展示
        switch (AdZhiZuNative.getRedState(nativeAdInfo.getAdsRuleBean())) {
            case 1://红包
                mLayoutAd.setVisibility(View.VISIBLE);
                mTvTopAdText.setVisibility(View.GONE);
                mIvAdicon.setBackgroundResource(R.mipmap.ad_red);
                break;
            case 2://金包
                mLayoutAd.setVisibility(View.VISIBLE);
                mTvTopAdText.setVisibility(View.GONE);
                mIvAdicon.setBackgroundResource(R.mipmap.ad_yellow);
                break;
            case 0:
            default:
                mLayoutAd.setVisibility(View.GONE);
                mTvTopAdText.setVisibility(View.VISIBLE);
                break;
        }
        ad_dec.setVisibility(View.GONE);
        logoImageView.setVisibility(View.GONE);
        if (nativeAdInfo.getOrigin() instanceof NativeAds) {
            String id = ((NativeAds) nativeAdInfo.getOrigin()).getId();
            final NativeAds nativeAds = NativeAds.getNativeAds(id);
            nativeAds.register(this);
        } else if (nativeAdInfo.getOrigin() instanceof NativeADDataRef) {// 极光sdk广告
            final NativeADDataRef nativeADDataRef = (NativeADDataRef) nativeAdInfo.getOrigin();

            nativeADDataRef.onExposured(this); // 需要先调用曝光接口
            nativeADDataRef.bindTouchView(this);// 必须绑定触发点击事件的view，否则将无法触发点击
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    nativeADDataRef.onClicked(v);
                    nativeAdInfo.onClick(v.getContext(), v, nativeAdInfo.getAdsRuleBean());
                }
            });
        } else if (nativeAdInfo.getOrigin() instanceof TTFeedAd) {
            TTFeedAd tTFeedAd = (TTFeedAd) nativeAdInfo.getOrigin();
            ad_dec.setVisibility(View.VISIBLE);
            logoImageView.setVisibility(View.VISIBLE);

            ad_dec.setText("" + tTFeedAd.getDescription());
            logoImageView.setImageBitmap(tTFeedAd.getAdLogo());

            List<View> clickViewList = new ArrayList<View>();
            clickViewList.add(this);
            //触发创意广告的view（点击下载或拨打电话）
            List<View> creativeViewList = new ArrayList<View>();
            creativeViewList.add(this);
            tTFeedAd.registerViewForInteraction(this, clickViewList, creativeViewList, new TTFeedAd.AdInteractionListener() {
                @Override
                public void onAdClicked(View view, TTNativeAd ad) {
//                    nativeAdInfo.onClick(view.getContext(), view, nativeAdInfo.getAdsRuleBean());
                }

                @Override
                public void onAdCreativeClick(View view, TTNativeAd ad) {
                    nativeAdInfo.onClick(view.getContext(), view, nativeAdInfo.getAdsRuleBean());
                }

                @Override
                public void onAdShow(TTNativeAd ad) {
                    nativeAdInfo.onDisplay(DetailBigImgAdView.this.getContext(), DetailBigImgAdView.this);
                }
            });
        } else {
            nativeAdInfo.onDisplay(getContext(), mDetailOtherAdView);
            mDetailOtherAdView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nativeAdInfo.onClick(getContext(), v, nativeAdInfo.getAdsRuleBean());
                }
            });
        }
    }

    public void reflash() {
        if (object instanceof NativeAdInfo) {
            setNativeData((NativeAdInfo) object);
        } else if (object instanceof AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) {
            setUrlAdData((AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) object);
        }
    }

}
