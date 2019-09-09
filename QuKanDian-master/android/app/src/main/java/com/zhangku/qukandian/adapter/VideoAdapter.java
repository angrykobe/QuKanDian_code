package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.shulian.sdk.NativeAds;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.SearchActivity;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.dialog.ArticleShieldDialog;
import com.zhangku.qukandian.dialog.DialogShared;
import com.zhangku.qukandian.interfaces.OnClickApkDownloadListener;
import com.zhangku.qukandian.interfaces.OnSharedListener;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.widght.LableLayout;
import com.zhangku.qukandian.widght.MyselfAdViewForVideoHome;
import com.zhangku.qukandian.widght.RatioImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jiguang.adsdk.api.nati.NativeADDataRef;
import cn.jiguang.adsdk.api.nati.NativeExpressADView;

/**
 * Created by yuzuoning on 2017/3/29.
 */

public class VideoAdapter extends BaseRecyclerViewAdapter<Object> implements ArticleShieldDialog.OnClickDelete {
    //    private DialogShared mDialogShared;
    private OnVideoSharedListener mOnVideoSharedListener;
    private int mPosition = -1;
    private String mRemindText;
    private boolean mShowHeaderRemind = false;
    private String mBelong = "";
    private ArticleShieldDialog mDialog;//新闻过滤弹框

    public VideoAdapter(Context context, List<Object> beans, OnVideoSharedListener onVideoSharedListener) {
        super(context, beans);
//        mDialogShared = new DialogShared(context);
        mOnVideoSharedListener = onVideoSharedListener;
        mBelong = "视频列表页";
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int type) {
        RecyclerView.ViewHolder view = null;
        switch (type) {
            case TYPE_AD_VIEW://广告
                view = new MyAdHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ad_video_layout, parent, false));
                break;
            case VIEW_TYPE_NORMAL://
                view = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_video_fragment_adapter_view, parent, false));
                break;
            case TYPE_AD_VIEW_SELF:
                view = new MyAdHolderSelft(new MyselfAdViewForVideoHome(mContext));
                break;
            case TYPE_EMPTY:
                view = new EmptyViewHolder(new Space(mContext));
                break;
        }
        return view;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder,final int viewPosition, int dataIndex, final Object bean) {
        if (holder instanceof MyViewHolder) {//新闻视图，非广告
            MyViewHolder view = (MyViewHolder) holder;
            final InformationBean informationBean = (InformationBean) bean;
            view.mTvTitle.setText(informationBean.getTitle());
            view.mLableLayout.setData(informationBean.getLabelTerms(), SearchActivity.FROM_VIDEO);
            if (informationBean.getPostTextVideos().size() > 0) {
                String time = informationBean.getPostTextVideos().get(0).getDurationTime();
                if ("00:00:01".equals(time) || "00:00:00".equals(time)) {
                    view.mTvTime.setVisibility(View.GONE);
                } else {
                    view.mTvTime.setVisibility(View.VISIBLE);
                    view.mTvTime.setText(informationBean.getPostTextVideos().get(0).getDurationTime());
                }
            }
            view.mTvPlayNumber.setText(informationBean.getViewNumber() + informationBean.getViewSeedNumber() + "次");
            if (informationBean.getPostTextImages().size() > 0) {
                GlideUtils.displayImage(mContext, informationBean.getPostTextImages().get(0).getSrc(), view.mIvBg);
            }

            view.mTvRemindText.setText(mRemindText);
            if (mPosition == dataIndex && dataIndex != 0) {
                view.mVideoReadRecord.setVisibility(View.VISIBLE);
            } else {
                view.mVideoReadRecord.setVisibility(View.GONE);
            }

            if (mShowHeaderRemind && dataIndex == 0) {
                view.mHeaderRemind.setVisibility(View.VISIBLE);
            } else {
                view.mHeaderRemind.setVisibility(View.GONE);
            }
            view.mVideoReadRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnVideoSharedListener.onClickReadRecordListener();
                }
            });
//            view.mIvMoreBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mDialogShared.show();
//                    mDialogShared.setData(new DetailsBean(informationBean));
//                    mDialogShared.setOnSharedListener(new OnSharedListener() {
//                        @Override
//                        public void onSharedListener(DetailsBean mDetailsBean, SHARE_MEDIA share_media) {
//                            mOnVideoSharedListener.onSharedListener(mDetailsBean, share_media);
//                        }
//
//                    });
//                }
//            });

            view.mTvShareIcon.setVisibility(View.GONE);

            view.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new ArrayMap<>();
                    map.put("from", "视频列表");
                    MobclickAgent.onEvent(mContext, "VideoPV", map);
                    ActivityUtils.startToVideoDetailsActivity(mContext, informationBean.getPostId(), -1);
                }
            });
            view.mFilterIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UserManager.getInst().hadLogin()) {
                        if (mDialog == null)
                            mDialog = new ArticleShieldDialog(mContext, informationBean.getPostId(), "");
                        mDialog.setmPostId(informationBean.getPostId());
                        mDialog.setClickLestener(VideoAdapter.this);
                        mDialog.show();
                    } else {
                        ActivityUtils.startToBeforeLogingActivity(mContext);
                    }
                }
            });
        } else if (holder instanceof MyAdHolder) {
            final MyAdHolder view = (MyAdHolder) holder;
            final NativeAdInfo nativeAdInfo = (NativeAdInfo) bean;

            if (nativeAdInfo.getOrigin() instanceof View) {//第三方View
                view.addView.setVisibility(View.VISIBLE);
                view.ad_video_img_card.setVisibility(View.GONE);
                view.addView.removeAllViews();
                view.addView.addView((View) nativeAdInfo.getOrigin());
            } else {
                view.addView.setVisibility(View.GONE);
                view.ad_video_img_card.setVisibility(View.VISIBLE);
                GlideUtils.displayImageNice(mContext, nativeAdInfo.getImageUrl(), view.mImageView, view.itemView, nativeAdInfo);
                view.mTextView.setText(nativeAdInfo.getTitle());
            }

            switch (AdZhiZuNative.getRedStateForOtherAd(nativeAdInfo.getAdsRuleBean())) {
                case 1://红包
                    view.mAdLayout.setVisibility(View.VISIBLE);
                    view.mTvAdText.setVisibility(View.GONE);
                    view.mIvAdIcon1.setBackgroundResource(R.mipmap.ad_red);
                    break;
                case 2://金包
                    view.mAdLayout.setVisibility(View.VISIBLE);
                    view.mTvAdText.setVisibility(View.GONE);
                    view.mIvAdIcon1.setBackgroundResource(R.mipmap.ad_yellow);
                    break;
                case 0:
                default:
                    view.mAdLayout.setVisibility(View.GONE);
                    view.mTvAdText.setVisibility(View.VISIBLE);
                    break;
            }

            view.ad_dec.setVisibility(View.GONE);
            view.logoImageView.setVisibility(View.GONE);
            if (nativeAdInfo.getOrigin() instanceof NativeAds) {
                String id = ((NativeAds) nativeAdInfo.getOrigin()).getId();
                final NativeAds nativeAds = NativeAds.getNativeAds(id);
                nativeAds.register(view.itemView);
            } else if (nativeAdInfo.getOrigin() instanceof NativeADDataRef) {// 极光sdk广告
                final NativeADDataRef nativeADDataRef = (NativeADDataRef) ((NativeAdInfo) bean).getOrigin();

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
                view.ad_dec.setVisibility(View.VISIBLE);
                view.logoImageView.setVisibility(View.VISIBLE);
                TTFeedAd ttBean = (TTFeedAd) nativeAdInfo.getOrigin();

                view.ad_dec.setText("" + ttBean.getDescription());
                view.logoImageView.setImageBitmap(ttBean.getAdLogo());

                List<View> clickViewList = new ArrayList<View>();
                clickViewList.add(view.mLinearLayout);
                //触发创意广告的view（点击下载或拨打电话）
                List<View> creativeViewList = new ArrayList<View>();
                creativeViewList.add(view.mLinearLayout);
                ttBean.registerViewForInteraction(view.mLinearLayout, clickViewList, creativeViewList, new TTFeedAd.AdInteractionListener() {
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
        } else if (holder instanceof MyAdHolderSelft) {//自主广告
            MyselfAdViewForVideoHome itemView = (MyselfAdViewForVideoHome) holder.itemView;
            itemView.setData((AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) bean, new OnClickApkDownloadListener() {
                @Override
                public void onApkDownloadListener() {
                    //点击下载apk 隐藏对应的单个列表
                    getList().remove(viewPosition);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void showReadRecord(int i) {
        mPosition = i;
    }

    public void showHeaderRemind(boolean show) {
        mShowHeaderRemind = show;
    }

    public void setTextContent(String s) {
        mRemindText = s;
    }

    @Override
    protected int getTypeView(int postion) {
        if (mBeans.get(postion) == null) {
            return TYPE_EMPTY;
        }
        if (mBeans.get(postion) instanceof NativeAdInfo) {
            return TYPE_AD_VIEW;
        } else if (mBeans.get(postion) instanceof AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) {
            return TYPE_AD_VIEW_SELF;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    /**
     * 点击不喜欢移除该item 根据需求暂时关闭
     *
     * @param pos
     */
    @Override
    public void clickDelete(int pos) {
//        for (Object o:mBeans) {
//            if(o instanceof InformationBean
//                    && ((InformationBean) o).getPostId() == pos ){
//                mBeans.remove(o);
//                DataSupport.delete(InformationBean.class, ((InformationBean) o).getId());
//                notifyDataSetChanged();
//                return;
//            }
//        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        LableLayout mLableLayout;
        LinearLayout mHeaderRemind;
        LinearLayout mVideoReadRecord;
        TextView mTvShareIcon;
        TextView mTvTitle;
        TextView mTvTime;
        TextView mTvPlayNumber;
        TextView mTvRemindText;
        ImageView mIvBg;
        ImageView mIvMoreBtn;
        View mFilterIV;//过滤

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvShareIcon = itemView.findViewById(R.id.item_video_share_icon);
            mHeaderRemind = (LinearLayout) itemView.findViewById(R.id.item_read_record_view_item);
            mVideoReadRecord = (LinearLayout) itemView.findViewById(R.id.item_video_read_record_view_item);
            mLableLayout = (LableLayout) itemView.findViewById(R.id.lable_layout_layout);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_video_fragment_adapter_view_layout);
            mTvTitle = (TextView) itemView.findViewById(R.id.item_video_fragment_adapter_view_title);
            mTvTime = (TextView) itemView.findViewById(R.id.item_video_fragment_adapter_view_time);
            mTvPlayNumber = (TextView) itemView.findViewById(R.id.item_video_fragment_adapter_view_number);
            mIvBg = (ImageView) itemView.findViewById(R.id.item_video_fragment_adapter_view_img);
            mIvMoreBtn = (ImageView) itemView.findViewById(R.id.item_video_fragment_adapter_view_more);
            mTvRemindText = ((TextView) mHeaderRemind.findViewById(R.id.item_read_record_view_text));
            mFilterIV = itemView.findViewById(R.id.item_video_fragment_adapter_view_filter);
        }
    }

    class MyAdHolder extends RecyclerView.ViewHolder {
        RelativeLayout ad_video_img_card;
        LinearLayout mLinearLayout;
        RatioImageView mImageView;
        TextView mTextView;
        TextView mTvAdText;
        ImageView mIvAdIcon1;
        ImageView mIvAdText1;
        LinearLayout mAdLayout;
        FrameLayout addView;
        TextView ad_dec;
        ImageView logoImageView;

        public MyAdHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView;
            mTextView = (TextView) itemView.findViewById(R.id.ad_video_title);
            mImageView = (RatioImageView) itemView.findViewById(R.id.ad_video_img);
            ad_video_img_card = (RelativeLayout) itemView.findViewById(R.id.ad_video_img_card);
            mTvAdText = itemView.findViewById(R.id.ad_video_adtext);
            mIvAdIcon1 = itemView.findViewById(R.id.ad_icon);
            mIvAdText1 = itemView.findViewById(R.id.ad_texts);
            mAdLayout = itemView.findViewById(R.id.ad_other_layout);
            addView = itemView.findViewById(R.id.addView);

            ad_dec = itemView.findViewById(R.id.ad_dec);
            logoImageView = itemView.findViewById(R.id.logoImageView);
        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class MyAdHolderSelft extends RecyclerView.ViewHolder {

        public MyAdHolderSelft(View itemView) {
            super(itemView);
        }
    }

    public interface OnVideoSharedListener {
//        void onSharedListener(DetailsBean mDetailsBean, SHARE_MEDIA share_media);

        void onClickReadRecordListener();
    }
}
