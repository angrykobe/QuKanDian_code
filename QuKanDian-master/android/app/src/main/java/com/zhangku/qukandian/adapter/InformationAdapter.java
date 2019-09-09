package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.shulian.sdk.NativeAds;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.ArticleShieldDialog;
import com.zhangku.qukandian.interfaces.OnCilicReadRecordListener;
import com.zhangku.qukandian.interfaces.OnClickApkDownloadListener;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.widght.ItemAdLayout;
import com.zhangku.qukandian.widght.MyselfAdViewForArtHome;
import com.zhangku.qukandian.widght.ItemInformationAdapterNonePictureLayout;
import com.zhangku.qukandian.widght.ItemInformationAdapterOnePictureLayout;
import com.zhangku.qukandian.widght.ItemInformationAdapterThreePictureLayout;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.adsdk.api.nati.NativeADDataRef;

/**
 * Created by yuzuoning on 2017/3/29.
 * 资讯列表适配器
 */

public class InformationAdapter extends BaseRecyclerViewAdapter<Object> implements ArticleShieldDialog.OnClickDelete {
    private boolean mShowReadrecord = false;
    private int mShowReadrecordPosition = -1;
    private OnCilicReadRecordListener mOnCilicReadRecordListener;//刚刚看到这里点击刷新  按钮监听
    private boolean mIsRecord = false;
    private boolean mIsTag = false;//最近阅读记录  变灰

    public InformationAdapter(Context context, List<Object> beans, boolean isRecord, OnCilicReadRecordListener onCilicReadRecordListener, boolean isTag) {
        super(context, beans);
        mOnCilicReadRecordListener = onCilicReadRecordListener;
        mIsRecord = isRecord;
        mIsTag = isTag;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder view = null;
        switch (viewType) {
            case TYPE_EMPTY:
                view = new EmptyViewHolder(new Space(mContext));
                break;
            case TYPE_AD_VIEW:
                view = new BaiduNativeH5AdViewHolder(LayoutInflater.from(mContext).inflate(R.layout
                        .information_list_ad_view, parent, false));
                break;
            case TYPE_AD_VIEW_SELF:
                view = new MyAdHolder(new MyselfAdViewForArtHome(mContext));
                break;
            case TYPE_NONE://？？
                view = new NoneViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.item_information_adapter_none_picture_layout, parent, false));
                break;
            case TYPE_ONE://？？
                view = new OneViewHolder(LayoutInflater.from(mContext).inflate(
                        R.layout.item_information_adapter_one_pic_layout, parent, false));
                break;
            case TYPE_THREE://??
                view = new ThreeViewHolder(LayoutInflater.from(mContext).inflate(
                        R.layout.item_information_adapter_three_picture_layout, parent, false));
                break;
        }
        return view;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int viewPosition, int dataIndex, final Object bean) {
        LogUtils.LogW("bindView" + viewPosition);
        if (holder instanceof NoneViewHolder) {//文章无图模式
            if (bean instanceof InformationBean) {
                NoneViewHolder view = (NoneViewHolder) holder;
                view.mNonePictureView.setData((InformationBean) bean, mIsRecord, dataIndex);
                if (mShowReadrecord && mShowReadrecordPosition == dataIndex) {
                    view.mNonePictureView.setOnCilicReadRecordListener(mOnCilicReadRecordListener);
                    view.mNonePictureView.showReadrecord(true);
                } else {
                    view.mNonePictureView.showReadrecord(false);
                }
                view.mNonePictureView.setIsTag(mIsTag, (InformationBean) bean);
            }
        } else if (holder instanceof OneViewHolder) {//文章1图模式
            if (bean instanceof InformationBean) {
                OneViewHolder view = (OneViewHolder) holder;
                view.mOnePictureView.setData((InformationBean) bean, mIsRecord, dataIndex);
                view.mOnePictureView.setClickLestener(this);
                if (mShowReadrecord && mShowReadrecordPosition == dataIndex) {
                    view.mOnePictureView.setOnCilicReadRecordListener(mOnCilicReadRecordListener);
                    view.mOnePictureView.showReadrecord(true);
                } else {
                    view.mOnePictureView.showReadrecord(false);
                }
                view.mOnePictureView.setIsTag(mIsTag, (InformationBean) bean);
            }
        } else if (holder instanceof ThreeViewHolder) {//文章3图模式
            if (bean instanceof InformationBean) {
                ThreeViewHolder view = (ThreeViewHolder) holder;
                view.mThreePictureView.setData((InformationBean) bean, mIsRecord, dataIndex);
                view.mThreePictureView.setClickLestener(this);
                if (mShowReadrecord && mShowReadrecordPosition == dataIndex) {
                    view.mThreePictureView.setOnCilicReadRecordListener(mOnCilicReadRecordListener);
                    view.mThreePictureView.showReadrecord(true);
                } else {
                    view.mThreePictureView.showReadrecord(false);
                }
                view.mThreePictureView.setIsTag(mIsTag, (InformationBean) bean);
            }
        } else if (holder instanceof BaiduNativeH5AdViewHolder) {//惠头条广告走这里
            final BaiduNativeH5AdViewHolder myHodler = (BaiduNativeH5AdViewHolder) holder;
            final NativeAdInfo nativeAdInfo = (NativeAdInfo) bean;
            //设置view
            myHodler.mItemAdLayout.setData(nativeAdInfo, Constants.TYPE_NEW);
            if (nativeAdInfo.getOrigin() instanceof NativeAds) {
                String id = ((NativeAds) nativeAdInfo.getOrigin()).getId();
                final NativeAds nativeAds = NativeAds.getNativeAds(id);
                nativeAds.register(myHodler.itemView);
            } else if (nativeAdInfo.getOrigin() instanceof NativeADDataRef) {// 极光sdk广告
                final NativeADDataRef nativeADDataRef = (NativeADDataRef) nativeAdInfo.getOrigin();
                nativeADDataRef.onExposured(myHodler.itemView); // 需要先调用曝光接口
                nativeADDataRef.bindTouchView(myHodler.itemView);// 必须绑定触发点击事件的view，否则将无法触发点击

                myHodler.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nativeADDataRef.onClicked(v);
                        nativeAdInfo.onClick(mContext, v, nativeAdInfo.getAdsRuleBean());
                    }
                });
            } else if (nativeAdInfo.getOrigin() instanceof TTFeedAd) {
                List<View> clickViewList = new ArrayList<View>();
                clickViewList.add(myHodler.mItemAdLayout);
                //触发创意广告的view（点击下载或拨打电话）
                List<View> creativeViewList = new ArrayList<View>();
                creativeViewList.add(myHodler.mItemAdLayout);
                ((TTFeedAd) nativeAdInfo.getOrigin()).registerViewForInteraction(myHodler.mItemAdLayout, clickViewList, creativeViewList, new TTFeedAd.AdInteractionListener() {
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
                        nativeAdInfo.onDisplay(mContext, myHodler.mItemAdLayout);
                    }
                });
            } else {//惠头条广告走这里
                nativeAdInfo.onDisplay(mContext, myHodler.mItemAdLayout);
                myHodler.mItemAdLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nativeAdInfo.onClick(mContext, v, nativeAdInfo.getAdsRuleBean());
                    }
                });
            }
        } else {
            if (bean instanceof AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) {
                MyAdHolder myAdHolder = (MyAdHolder) holder;
                AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean1 = (AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) bean;
                myAdHolder.mItemAdLayout.setData(bean1, new OnClickApkDownloadListener() {
                    @Override
                    public void onApkDownloadListener() {
                        //点击下载apk 隐藏对应的单个列表
                        getList().remove(viewPosition);
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    protected int getTypeView(int position) {
        if (mBeans.get(position) == null) {
            return TYPE_EMPTY;
        }

        if (mBeans.get(position) instanceof NativeAdInfo) {//原生api广告  百度，惠头条之类的
            return TYPE_AD_VIEW;
        } else if (mBeans.get(position) instanceof AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) {//自主广告的样子？
            return TYPE_AD_VIEW_SELF;
        } else {
            if (null != ((InformationBean) mBeans.get(position)).getPostTextImages()) {
                if (((InformationBean) mBeans.get(position)).getPostTextImages().size() >= 3) {
                    if (((InformationBean) mBeans.get(position)).getId() % 3 == 0) {
                        return TYPE_THREE;
                    } else {
                        return TYPE_ONE;
                    }
                } else if (((InformationBean) mBeans.get(position)).getPostTextImages().size() >= 1) {
                    return TYPE_ONE;
                } else {
                    return TYPE_NONE;
                }
            } else {
                return TYPE_NONE;
            }
        }

    }

    public void showReadRecord(int position) {
        mShowReadrecord = true;
        mShowReadrecordPosition = position;
    }

    /**
     * 点击不喜欢移除该item 根据需求暂时关闭
     *
     * @param pos 文章id
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

    private class NoneViewHolder extends RecyclerView.ViewHolder {
        ItemInformationAdapterNonePictureLayout mNonePictureView;

        public NoneViewHolder(View itemView) {
            super(itemView);
            mNonePictureView = itemView.findViewById(R.id.item_information_adapter_none_pic_layout);

        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class OneViewHolder extends RecyclerView.ViewHolder {
        ItemInformationAdapterOnePictureLayout mOnePictureView;

        public OneViewHolder(View itemView) {
            super(itemView);
            mOnePictureView = itemView.findViewById(R.id.item_information_adapter_one_pic_layout);
        }
    }

    private class ThreeViewHolder extends RecyclerView.ViewHolder {
        ItemInformationAdapterThreePictureLayout mThreePictureView;

        public ThreeViewHolder(View itemView) {
            super(itemView);
            mThreePictureView = itemView.findViewById(R.id.item_information_adapter_three_pic_layout);
        }
    }

    public class BaiduNativeH5AdViewHolder extends RecyclerView.ViewHolder {
        ItemAdLayout mItemAdLayout;

        public BaiduNativeH5AdViewHolder(View itemView) {
            super(itemView);
            mItemAdLayout = (ItemAdLayout) itemView;
        }
    }


    private class MyAdHolder extends RecyclerView.ViewHolder {
        MyselfAdViewForArtHome mItemAdLayout;

        public MyAdHolder(View itemView) {
            super(itemView);
            mItemAdLayout = (MyselfAdViewForArtHome) itemView;
        }
    }
}
