package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.shulian.sdk.NativeAds;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.widght.MyselfAdViewForSign;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.adsdk.api.nati.NativeADDataRef;

/**
 * Created by yuzuoning on 2017/11/27.
 */

public class SignDialogAdapter extends BaseRecyclerViewAdapter<Object> {
    public SignDialogAdapter(Context context, List<Object> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_AD_VIEW) {
            return new MyNativeHolder(LayoutInflater.from(mContext).inflate(R.layout.view_myself_adview_for_sign, parent, false));
        } else {
            return new MyAdHolder(new MyselfAdViewForSign(mContext));
        }
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final Object bean) {
        if (holder instanceof MyNativeHolder) {
            final MyNativeHolder view = (MyNativeHolder) holder;
            final NativeAdInfo nativeAdInfo = (NativeAdInfo) bean;

            if (nativeAdInfo.getOrigin() instanceof View) {
                view.addView.setVisibility(View.VISIBLE);
                View view1 = (View) nativeAdInfo.getOrigin();
                view.addView.removeAllViews();
                view.addView.addView(view1);
            } else {
                view.addView.setVisibility(View.GONE);
                view.mTvTitle.setText(nativeAdInfo.getTitle());
                GlideUtils.displayImageNice(mContext, nativeAdInfo.getImageUrl(), view.mIvImg, view.itemView, (NativeAdInfo) bean);
            }

            switch (AdZhiZuNative.getRedStateForOtherAd(nativeAdInfo.getAdsRuleBean())) {
                case 1://红包
                    view.mIvGold.setImageResource(R.mipmap.ad_red);
                    view.mLayoutGold.setVisibility(View.VISIBLE);
                    break;
                case 2://金包
                    view.mLayoutGold.setVisibility(View.VISIBLE);
                    view.mIvGold.setImageResource(R.mipmap.ad_yellow);
                    break;
                case 0:
                default:
                    view.mLayoutGold.setVisibility(View.GONE);
                    break;
            }

            if (nativeAdInfo.getOrigin() instanceof NativeAds) {
                String id = ((NativeAds) nativeAdInfo.getOrigin()).getId();
                final NativeAds nativeAds = NativeAds.getNativeAds(id);
                nativeAds.register(view.itemView);
            } else if (nativeAdInfo.getOrigin() instanceof NativeADDataRef) {// 极光sdk广告
                final NativeADDataRef nativeADDataRef = (NativeADDataRef) nativeAdInfo.getOrigin();
                nativeADDataRef.onExposured(view.itemView); // 需要先调用曝光接口
                nativeADDataRef.bindTouchView(view.itemView);// 必须绑定触发点击事件的view，否则将无法触发点击

                view.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nativeADDataRef.onClicked(v);
                        nativeAdInfo.onClick(mContext, v, nativeAdInfo.getAdsRuleBean());
                    }
                });
            } else if (nativeAdInfo.getOrigin() instanceof TTFeedAd) {
                try {
                    TextView ad_dec = view.itemView.findViewById(R.id.ad_dec);
                    ImageView logoImageView = view.itemView.findViewById(R.id.logoImageView);
                    ad_dec.setVisibility(View.GONE);
                    logoImageView.setVisibility(View.GONE);
                    if (nativeAdInfo.getOrigin() instanceof TTFeedAd) {
                        TTFeedAd ttBean = (TTFeedAd) nativeAdInfo.getOrigin();

                        ad_dec.setVisibility(View.VISIBLE);
                        logoImageView.setVisibility(View.VISIBLE);

                        ad_dec.setText(""+ttBean.getDescription());
                        logoImageView.setImageBitmap(ttBean.getAdLogo());
                    }
                }catch (Exception e){
                }

                List<View> clickViewList = new ArrayList<View>();
                clickViewList.add(view.itemView);
                //触发创意广告的view（点击下载或拨打电话）
                List<View> creativeViewList = new ArrayList<View>();
                creativeViewList.add(view.itemView);
                ((TTFeedAd) nativeAdInfo.getOrigin()).registerViewForInteraction((ViewGroup) view.itemView, clickViewList, creativeViewList, new TTFeedAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, TTNativeAd ad) {
//                        nativeAdInfo.onClick(mContext, view, nativeAdInfo.getAdsRuleBean());
                    }

                    @Override
                    public void onAdCreativeClick(View view, TTNativeAd ad) {
                        nativeAdInfo.onClick(mContext, view, nativeAdInfo.getAdsRuleBean());
                    }

                    @Override
                    public void onAdShow(TTNativeAd ad) {
                        nativeAdInfo.onDisplay(mContext, view.itemView);
                    }
                });
            } else {
                nativeAdInfo.onDisplay(mContext, view.itemView);
                view.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MobclickAgent.onEvent(mContext, "03_02_01_clickrecommend");
                        nativeAdInfo.onClick(mContext, v, nativeAdInfo.getAdsRuleBean());
                    }
                });
            }
        } else {
            MyselfAdViewForSign itemView = (MyselfAdViewForSign) holder.itemView;
            itemView.setData((AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) bean);
        }
    }

    @Override
    protected int getTypeView(int position) {
        if (mBeans.get(position) instanceof NativeAdInfo) {
            return TYPE_AD_VIEW;
        } else {
            return TYPE_AD_VIEW_SELF;
        }
    }

    private class MyNativeHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        ImageView mIvImg;
        ImageView mIvGold;
        LinearLayout mLayoutGold;
        FrameLayout addView;

        public MyNativeHolder(View itemView) {
            super(itemView);
            mLayoutGold = itemView.findViewById(R.id.dialog_sign_gold_icon_layout);
            mIvGold = itemView.findViewById(R.id.dialog_sign_gold_icon);
            mTvTitle = itemView.findViewById(R.id.dialog_sign_title);
            mIvImg = itemView.findViewById(R.id.dialog_sign_img);
            addView = itemView.findViewById(R.id.addView);
        }
    }

    private class MyAdHolder extends RecyclerView.ViewHolder {

        public MyAdHolder(View itemView) {
            super(itemView);
        }
    }
}
