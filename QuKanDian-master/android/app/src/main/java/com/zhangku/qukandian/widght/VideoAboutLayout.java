package com.zhangku.qukandian.widght;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.adapter.DetailsAdapter;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.DisLikeNumBean;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.bean.UserPostBehaviorDto;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.ArticleShieldDialog;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.DisLikeNewsProtocol;
import com.zhangku.qukandian.protocol.GetInformationDetailsAboutDataProtocol;
import com.zhangku.qukandian.protocol.LikeNumProtocol;
import com.zhangku.qukandian.protocol.RemovePraiseProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AdsLogUpUtils;
import com.zhangku.qukandian.utils.AnimUtil;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.LayoutInflaterUtils;
import com.zhangku.qukandian.utils.RecyclerViewDivider;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/7/18.
 */

public class VideoAboutLayout extends LinearLayout {
    private GetInformationDetailsAboutDataProtocol mGetInformationDetailsAboutDataProtocol;
    private LikeNumProtocol likeNumProtocol;
    private ArrayList<Object> mDatas = new ArrayList<>();
    private DetailsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTvContent;
    private TextView mTvPlayNumber;
    private TextView mTvReleaseTime;
    private TextView mPraiseTV;//点赞
    private TextView mPraiseAnimTV;//点赞动画
    private TextView mDeleteTV;//不喜欢
    private DetailBigImgAdView bigImgAdView;
    private View mAd_header_line;
    private int mPostId;

    public VideoAboutLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //头部
        View view = LayoutInflaterUtils.inflateView(getContext(), R.layout.header_video_layout);
        mAd_header_line = view.findViewById(R.id.ad_header_line);
        bigImgAdView = view.findViewById(R.id.bigImgAdView);
        mTvContent = (TextView) view.findViewById(R.id.header_video_layout_content);
        mTvPlayNumber = (TextView) view.findViewById(R.id.header_video_layout_play_number);
        mTvReleaseTime = (TextView) view.findViewById(R.id.header_video_layout_date);
        mPraiseTV = view.findViewById(R.id.header_video_layout_play_praise);
        mPraiseAnimTV = view.findViewById(R.id.header_information_layout_praise_anim_view);
        mDeleteTV = view.findViewById(R.id.header_video_layout_play_nolike);

        mRecyclerView = (RecyclerView) findViewById(R.id.video_about_layout_recyclerview);
        mTvReleaseTime.setVisibility(View.GONE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), 1, LinearLayoutManager.HORIZONTAL, 0.5f, ContextCompat.getColor(getContext(), R.color.grey_e5)));
        mAdapter = new DetailsAdapter(getContext(), mDatas, Constants.TYPE_VIDEO);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setHeaderView(view);

        mPraiseTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mPraiseTV.isSelected()) {
                    UserPostBehaviorDto bean = new UserPostBehaviorDto();
                    bean.setActionType(8);
                    bean.setImei(DeviceUtil.getDeviceInfo(getContext()).imei);
                    bean.setPostId(mPostId);
                    bean.setUserId(UserManager.getInst().getUserBeam().getId());
                    new DisLikeNewsProtocol(getContext(), bean, new BaseModel.OnResultListener<Object>() {
                        @Override
                        public void onResultListener(Object response) {
                            mPraiseTV.setSelected(true);
                            String num = mPraiseTV.getText().toString();
                            if (!TextUtils.isEmpty(num)) {
                                long nowNum = Long.valueOf(num) + 1;
                                mPraiseTV.setText("" + nowNum);
                            }
                        }

                        @Override
                        public void onFailureListener(int code, String error) {

                        }
                    }).postRequest();
                    AnimUtil.animPraise(mPraiseAnimTV);
                } else {
                    //取消点赞
                    new RemovePraiseProtocol(getContext(), UserManager.getInst().getUserBeam().getId(), mPostId, new BaseModel.OnResultListener<Object>() {
                        @Override
                        public void onResultListener(Object response) {
                            if ("1".equals(response)) {
                                mPraiseTV.setSelected(false);
                                String num = mPraiseTV.getText().toString();
                                long nowNum = Long.valueOf(num) - 1;
                                mPraiseTV.setText("" + nowNum);
                            }
                        }

                        @Override
                        public void onFailureListener(int code, String error) {
                            if (code == 1) {
                                mPraiseTV.setSelected(false);
                                String num = mPraiseTV.getText().toString();
                                long nowNum = Long.valueOf(num) - 1;
                                mPraiseTV.setText("" + nowNum);
                            }
                        }
                    }).postRequest();
                }
            }
        });
        mDeleteTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManager.getInst().hadLogin()) {
                    ArticleShieldDialog dialog = new ArticleShieldDialog(getContext(), mPostId,"");
                    dialog.show();
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
            }
        });
    }

    public void setDatas(int mPostId, final DetailsBean bean) {
        this.mPostId = mPostId;
        if (null != bean) {
            if (null != mTvContent) {
                mTvContent.setText(bean.getTitle());
            }
            if (null != mTvPlayNumber) {
                mTvPlayNumber.setText(bean.getViewNumber() + bean.getViewSeedNumber() + "次播放");
            }
            if (null != mTvReleaseTime) {
                if (bean.getReleaseTime().contains("-")) {
                    mTvReleaseTime.setText(CommonHelper.utcToString(bean.getReleaseTime(), "yyyy-MM-dd"));
                } else {
                    mTvReleaseTime.setText(CommonHelper.formatTimeline(Long.valueOf(bean.getReleaseTime())));
                }
            }
            if (null == mGetInformationDetailsAboutDataProtocol) {
                mGetInformationDetailsAboutDataProtocol = new GetInformationDetailsAboutDataProtocol(getContext(), mPostId, Constants.SIZE , new BaseModel.OnResultListener<ArrayList<InformationBean>>() {
                    @Override
                    public void onResultListener(ArrayList<InformationBean> response) {
                        if (null != response) {
                            if (response.size() > 0) {
                                if (null != mDatas) {
                                    mDatas.clear();
                                    mDatas.addAll(response);
                                    mAdapter.notifyDataSetChanged();

                                    if(getContext() instanceof Activity){
                                        AdUtil.fetchAd( getContext(),AnnoCon.AD_TYPE_VIDEO_DETAILS,bean.getChannelId(), new AdUtil.AdCallBack() {
                                            @Override
                                            public void getAdContent(Object object, int adIndex) {
                                                if (adIndex == 1) {//大图位置
                                                    if (object instanceof AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) {
                                                        bigImgAdView.setUrlAdData((AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) object);
                                                    } else if (object instanceof NativeAdInfo) {
                                                        bigImgAdView.setNativeData((NativeAdInfo) object);
                                                    } else {
                                                        bigImgAdView.setNullData();
                                                    }
                                                } else if (mDatas.size() >= adIndex) {//adIndex从2开始，mDatas从0开始，
                                                    if (adIndex - 2 >= 0)
                                                        mDatas.set(adIndex - 2, object);
                                                    else
                                                        mDatas.set(adIndex - 1, object);
                                                    mAdapter.notifyDataSetChanged();
                                                } else {
                                                    mDatas.add(object);
                                                    mAdapter.notifyDataSetChanged();
                                                }

                                                //保存广告日志
                                                AdsLogUpUtils.saveAdsList(AdsLogUpUtils.ADLOGUP_TYPE_VIDEO_DETAIL,object);
                                            }
                                        });
                                    }

                                }
                            }
                        }
                        mGetInformationDetailsAboutDataProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mGetInformationDetailsAboutDataProtocol = null;
                    }
                });
                mGetInformationDetailsAboutDataProtocol.postRequest();
            }
            if (likeNumProtocol == null) {
                likeNumProtocol = new LikeNumProtocol(getContext(), mPostId, "",new BaseModel.OnResultListener<DisLikeNumBean>() {
                    @Override
                    public void onResultListener(DisLikeNumBean response) {
                        mPraiseTV.setSelected(response.isLike());
                        mPraiseTV.setText("" + response.getLikeNumber());
                    }

                    @Override
                    public void onFailureListener(int code, String error) {

                    }
                });
                likeNumProtocol.postRequest();
            }


        }
    }

    public void adapterNotify() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        if (bigImgAdView != null) {
            bigImgAdView.reflash();
        }
    }
}
