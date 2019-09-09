package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.shulian.sdk.NativeAds;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnClickApkDownloadListener;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.AES;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.LayoutInflaterUtils;
import com.zhangku.qukandian.widght.ItemAdLayout;
import com.zhangku.qukandian.widght.MyselfAdViewForVideoDetail;
import com.zhangku.qukandian.widght.RatioImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jiguang.adsdk.api.nati.NativeADDataRef;
import cn.jiguang.adsdk.api.nati.NativeExpressADView;

/**
 * Created by yuzuoning on 2017/3/30.
 * adapter复用，新闻详情和视频详情共用
 */

public class DetailsAdapter extends BaseRecyclerViewAdapter<Object> {
    private int mType;
    private String mBelong;

    public DetailsAdapter(Context context, List<Object> beans, int type) {
        super(context, beans);
        mType = type;
        switch (mType) {
            case Constants.TYPE_VIDEO:
                mBelong = "视频详情";
                break;
            case Constants.TYPE_NEW:
                mBelong = "文章详情";
                break;
        }
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int type) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (type) {
            case TYPE_AD_VIEW://联盟广告
                viewHolder = new NativAdHodler(LayoutInflater.from(mContext).inflate(R.layout.details_ad_view_right, parent, false));
                break;
            case TYPE_AD_VIEW_SELF://自主广告
                viewHolder = new MyAdHolder(new MyselfAdViewForVideoDetail(mContext));
                break;
            case VIEW_TYPE_NORMAL://（视频，文章）列表
                viewHolder = new MyViewHolder(LayoutInflaterUtils.inflateView(mContext, R.layout.item_details_adapter_view));
                break;
            case TYPE_EMPTY:
                viewHolder = new EmptyViewHolder(new Space(mContext));
                break;
        }
        return viewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder,final int viewPosition, int dataIndex, final Object bean1) {
        if (holder instanceof MyViewHolder && bean1 instanceof InformationBean) {
            MyViewHolder view = (MyViewHolder) holder;
            final InformationBean bean = (InformationBean) bean1;
            view.mTvContent.setText(bean.getTitle());
            if (bean.getPostTextImages().size() > 0) {
                GlideUtils.displayImage(QuKanDianApplication.getAppContext(), bean.getPostTextImages().get(0).getSrc(), view.mRatioImageView);
            }

            if (bean.getTextType() == Constants.TYPE_VIDEO) {
                view.mIvPlayIcon.setVisibility(View.VISIBLE);
                view.mTvPlayTime.setVisibility(View.VISIBLE);
                if (bean.getPostTextVideos().size() > 0) {
                    view.mTvPlayTime.setText(bean.getPostTextVideos().get(0).getDurationTime());
                }
            } else {
                view.mIvPlayIcon.setVisibility(View.GONE);
                view.mTvPlayTime.setVisibility(View.GONE);
            }

            view.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getTextType() == Constants.TYPE_NEW) {
                        Map<String, String> map = new ArrayMap<>();
                        map.put("from", "文章相关");
                        MobclickAgent.onEvent(mContext, "ArtilePV", map);

                        if (Constants.XIGUANG_NAME.equals(bean.getChannelName())) {
                            ActivityUtils.startToInformationDetailsActivity(mContext, bean.getId(), AnnoCon.ART_DETAIL_FROM_XIGUANG);
                        } else if (Constants.HIGH_PRICE_NAME.equals(bean.getChannelName())) {
                            MobclickAgent.onEvent(mContext, "291_count_High_Price_Click");
                            if (bean.getContentDisType() == 2 && bean.getArticleType() == 1) {
                                // h5页面
                                String url = bean.getStaticUrl() + "&key=bs" + AES.encryUserId(UserManager.getInst().getUserBeam().getId());
                                ActivityUtils.startToWebviewAct(mContext, url);
                            } else {
                                ActivityUtils.startToInformationDetailsActivity(mContext, bean.getId(), AnnoCon.ART_DETAIL_FROM_HIGH_PRICE);
                            }
                        } else {
                            ActivityUtils.startToInformationDetailsActivity(mContext, bean.getId(), AnnoCon.ART_DETAIL_FROM_ORDINARY, bean.getZyId(), bean.getChannel().getId());
                        }

                    } else {
                        Map<String, String> map = new ArrayMap<>();
                        map.put("from", "视频相关");
                        MobclickAgent.onEvent(mContext, "VideoPV", map);
                        ActivityUtils.startToVideoDetailsActivity(mContext, bean.getId(), -1);
                    }
                }
            });
        } else if (holder instanceof NativAdHodler) {
            final NativAdHodler view = (NativAdHodler) holder;
            final NativeAdInfo nativeAdInfo = (NativeAdInfo) bean1;
            view.mItemAdLayout.setData(nativeAdInfo, 1);
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
                List<View> clickViewList = new ArrayList<View>();
                clickViewList.add(view.itemView);
                //触发创意广告的view（点击下载或拨打电话）
                List<View> creativeViewList = new ArrayList<View>();
                creativeViewList.add(view.itemView);
                ((TTFeedAd) nativeAdInfo.getOrigin()).registerViewForInteraction(view.mItemAdLayout, clickViewList, creativeViewList, new TTFeedAd.AdInteractionListener() {
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
                        nativeAdInfo.onClick(mContext, v, nativeAdInfo.getAdsRuleBean());
                    }
                });
            }
        } else if (holder instanceof MyAdHolder) {
            MyselfAdViewForVideoDetail itemView = (MyselfAdViewForVideoDetail) holder.itemView;
            itemView.setData((AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) bean1, new OnClickApkDownloadListener() {
                @Override
                public void onApkDownloadListener() {
                    //点击下载apk 隐藏对应的单个列表
                    getList().remove(viewPosition);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    protected int getTypeView(int postion) {
        if (mBeans.get(postion - 1) == null) {
            return TYPE_EMPTY;
        }
        if (mBeans.get(postion - 1) instanceof NativeAdInfo) {
            return TYPE_AD_VIEW;
        } else if (mBeans.get(postion - 1) instanceof AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) {
            return TYPE_AD_VIEW_SELF;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        RatioImageView mRatioImageView;
        TextView mTvContent;
        RelativeLayout mLinearLayout;
        ImageView mIvPlayIcon;
        TextView mTvPlayTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.item_details_adapter_view_layout);
            mRatioImageView = (RatioImageView) itemView.findViewById(R.id.item_details_adapter_view_img);
            mTvContent = (TextView) itemView.findViewById(R.id.item_details_adapter_view_title);
            mIvPlayIcon = (ImageView) itemView.findViewById(R.id.item_details_adapter_view_play_icon);
            mTvPlayTime = (TextView) itemView.findViewById(R.id.item_details_adapter_view_time);
        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class MyAdHolder extends RecyclerView.ViewHolder {

        public MyAdHolder(View itemView) {
            super(itemView);
        }
    }

    private class NativAdHodler extends RecyclerView.ViewHolder {
        ItemAdLayout mItemAdLayout;

        public NativAdHodler(View itemView) {
            super(itemView);
            mItemAdLayout = (ItemAdLayout) itemView;
        }
    }

}
