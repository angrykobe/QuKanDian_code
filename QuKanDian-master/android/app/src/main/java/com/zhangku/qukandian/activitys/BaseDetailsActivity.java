package com.zhangku.qukandian.activitys;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.VideoOneItemAdapter;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseLoadingActivity;
import com.zhangku.qukandian.bean.CollectBean;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.LocalFavoriteBean;
import com.zhangku.qukandian.bean.SubmitTaskBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogShared;
import com.zhangku.qukandian.interfaces.OnSharedListener;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.DetailsProtocol;
import com.zhangku.qukandian.protocol.GetFavoriteStatusProtocol;
import com.zhangku.qukandian.protocol.PutNewCollectArticleProtocol;
import com.zhangku.qukandian.protocol.PutNewTaskForVideoProtocol;
import com.zhangku.qukandian.protocol.SubmitTaskProtocol;
import com.zhangku.qukandian.utils.ActivityManager;
import com.zhangku.qukandian.utils.LeftAndRightAnimation;
import com.zhangku.qukandian.utils.RecyclerViewDivider;
import com.zhangku.qukandian.utils.ThirdWeChatShare;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.ReadGoldView;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/3/29.
 * 详情页父类
 */

public abstract class BaseDetailsActivity extends BaseLoadingActivity implements View.OnClickListener,
        OnSharedListener, UserManager.IOnLoginStatusLisnter{
    protected int mPostId = -1;
    protected int mFrom = -1;
    protected FrameLayout mFrameLayout;
    private ImageView mIvBackBtn;
    private ImageView mIvCollectBtn;
    private ImageView mIvSharedBtn;
//    private LinearLayout mLayoutLoginRemind;
    protected LinearLayout mActionBar;
    protected TextView wifiRemindTV;
    protected CheckBox wifiNotRemindCB;
    protected ReadGoldView readGoldView;
    protected RelativeLayout mRelativeLayout;
    protected View wifiRemindView;//非WiFi环境提示布局
    protected VideoOneItemAdapter mAdapter;
    protected GridLayoutManager mLinearLayoutManager;
    protected RecyclerView mRecyclerView;
    protected ArrayList<Object> mDatas = new ArrayList<>();
    private boolean isIsFavorite = false;
    protected DialogShared mDialogShared;
    protected long mStartTime = 0;
    protected DetailsBean mDetailsBean;
    private LocalFavoriteBean mLocalFavoriteBean;
    private DetailsProtocol mDetailsProtocol;
    private int mCunt = 0;
    private PutNewCollectArticleProtocol mCollectArticleProtocol;
    private GetFavoriteStatusProtocol mGetFavoriteStatusProtocol;
    private PutNewTaskForVideoProtocol mSubmitTaskProtocol;
    private SubmitTaskProtocol mSubmitTaskProtocolExtra;
    private LeftAndRightAnimation mLeftAndRightAnimationHongbao;
//    private DialogNewPeopleRedPacket mDialogChaiHongBao;
    private boolean isRegistRed = false;

    @Override
    public int getLoadingViewParentId() {
        return R.id.activity_information_details_layout_loading_layout;
    }

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void loadData() {
        super.loadData();
        showLoading();
        loadVideo();
        getAboutData();
        if (UserManager.getInst().hadLogin()) {
            if (null == mGetFavoriteStatusProtocol) {
                mGetFavoriteStatusProtocol = new GetFavoriteStatusProtocol(BaseDetailsActivity.this, mPostId, new BaseModel.OnResultListener<Boolean>() {
                    @Override
                    public void onResultListener(Boolean response) {
                        isIsFavorite = response;

                        if (null != mDetailsBean) {
                            mDetailsBean.setFavorite(isIsFavorite);
                            mDetailsBean.setNewId(mPostId);
                            DBTools.addFarverite(mDetailsBean);
                        }
                        if (null != mIvCollectBtn) {
                            mIvCollectBtn.setSelected(isIsFavorite);
                        }
                        mGetFavoriteStatusProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mGetFavoriteStatusProtocol = null;
                    }
                });
                mGetFavoriteStatusProtocol.postRequest();
            }
        }
    }

    protected void loadVideo() {
        if (null == mDetailsProtocol && mCunt < 3) {
            mDetailsProtocol = new DetailsProtocol(this, mPostId, new BaseModel.OnResultListener<DetailsBean>() {
                @Override
                public void onResultListener(DetailsBean response) {
                    if (null != response) {
                        mDetailsBean = response;
                        if (UserManager.getInst().hadLogin()) {
                        } else {
                            isIsFavorite = DBTools.getStateById(mPostId);
                            mDetailsBean.setLocalIsFavorte(isIsFavorite);
                            mIvCollectBtn.setSelected(isIsFavorite);
                        }
                        DBTools.addFarverite(mDetailsBean);
                        mStartTime = System.currentTimeMillis();
                        getHeaderData(mDetailsBean);
                        mDetailsProtocol = null;

                    }
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mDetailsProtocol = null;
                }
            });
            mDetailsProtocol.postRequest();
        }
    }

    protected abstract void getHeaderData(DetailsBean bean);

    protected abstract void getAboutData();

    @Override
    protected void initViews() {
        if (null != getIntent()) {
            mPostId = getIntent().getExtras().getInt(Constants.ID);
            mFrom = getIntent().getExtras().getInt("from", -1);
        }
        mLeftAndRightAnimationHongbao = new LeftAndRightAnimation();
        mDialogShared = new DialogShared(this);
        mLocalFavoriteBean = new LocalFavoriteBean();
        mFrameLayout = (FrameLayout) findViewById(R.id.activity_information_details_layout_loading_layout);
        mActionBar = (LinearLayout) findViewById(R.id.video_details_actionbar);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_information_details_layout_empty);
        wifiRemindView = findViewById(R.id.wifiRemindView);
        mIvBackBtn = (ImageView) findViewById(R.id.activity_information_details_layout_back);
        mIvCollectBtn = (ImageView) findViewById(R.id.activity_information_details_layout_collect);
        mIvSharedBtn = (ImageView) findViewById(R.id.activity_information_details_layout_shared);
        wifiRemindTV = findViewById(R.id.wifiRemindTV);
        readGoldView = findViewById(R.id.ReadGoldView);
        wifiNotRemindCB = findViewById(R.id.wifiNotRemindCB);
        findViewById(R.id.wifiNotRemindView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiNotRemindCB.performClick();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_information_details_layout_recyclerview);
        mLinearLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, 1, LinearLayoutManager.HORIZONTAL, 0.5f, ContextCompat.getColor(this, R.color.grey_e5)));
        mAdapter = new VideoOneItemAdapter(this, mPostId);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mLinearLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mLinearLayoutManager.getSpanCount();
            }
        });


        if (UserManager.getInst().hadLogin()) {
            isRegistRed = false;
        } else {
            if (null != UserManager.getInst().getmRuleBean().getNewbieConfig()) {
                if (UserManager.getInst().getmRuleBean().getNewbieConfig().getStatusForUnlogin() == 1) {
                    isRegistRed = true;
                } else {
                    isRegistRed = false;
                }
            }
        }

        mIvSharedBtn.setVisibility(View.GONE);

        mIvBackBtn.setOnClickListener(this);
        mIvCollectBtn.setOnClickListener(this);
        mIvSharedBtn.setOnClickListener(this);
        mDialogShared.setOnSharedListener(this);
        UserManager.getInst().addLoginListener(this);

        findViewById(R.id.activity_video_details_layout_goto_home).setOnClickListener(this);
        ActivityManager.addActivity(this);
        if(ActivityManager.getActivityStack().size()>1){
            findViewById(R.id.activity_video_details_layout_goto_home).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.activity_video_details_layout_goto_home).setVisibility(View.GONE);
        }
    }

    protected RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            scrollListener(recyclerView, dx, dy);
        }
    };

    private boolean isShow() {
        return null != UserManager.getInst().getUserBeam().getMissionGrarntedUsers()
                && UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size() > 0
                && UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(0).isIsActive();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_information_details_layout;
    }

    protected void scrollListener(RecyclerView recyclerView, int dx, int dy) {
    }

    @Override
    public String setPagerName() {
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_information_details_layout_back:
                finishActtivity();
                break;
            case R.id.activity_video_details_layout_goto_home://回首页
                ActivityManager.finishAllActivity();
                break;
//            case R.id.activity_video_details_hongbao_icon:
//                ActivityUtils.startToBeforeLogingActivity(this);
//                break;
            case R.id.activity_information_details_layout_collect:
                MobclickAgent.onEvent(this, "02_01_cllickCollection");
                if (UserManager.getInst().hadLogin()) {
                    if (null == mCollectArticleProtocol) {
                        CollectBean bean = new CollectBean();
                        bean.setPostId(mPostId);
                        bean.setTextType(1);
                        mCollectArticleProtocol = new PutNewCollectArticleProtocol(this, !isIsFavorite, bean, new BaseModel.OnResultListener<Object>() {
                            @Override
                            public void onResultListener(Object response) {
                                isIsFavorite = !isIsFavorite;
                                if (null != mIvCollectBtn) {
                                    mIvCollectBtn.setSelected(isIsFavorite);
                                }
                                if (isIsFavorite) {
                                    ToastUtils.showLongToast(BaseDetailsActivity.this, "收藏成功");
                                } else {
                                    ToastUtils.showLongToast(BaseDetailsActivity.this, "已取消收藏");
                                }
                                mCollectArticleProtocol = null;
                            }

                            @Override
                            public void onFailureListener(int code, String error) {
                                mCollectArticleProtocol = null;
                            }
                        });
                        mCollectArticleProtocol.postRequest();
                    }
                } else {
                    isIsFavorite = !isIsFavorite;
                    mLocalFavoriteBean.setFavorite(isIsFavorite);
                    mLocalFavoriteBean.setPostId(mPostId);
                    DBTools.saveLocalFavorite(mLocalFavoriteBean);
                    mDetailsBean.setFavorite(isIsFavorite);
                    mDetailsBean.setId(mPostId);
                    DBTools.addFarverite(mDetailsBean);
                    mIvCollectBtn.setSelected(isIsFavorite);
                    if (isIsFavorite) {
                        ToastUtils.showLongToast(BaseDetailsActivity.this, "收藏成功");
                    } else {
                        ToastUtils.showLongToast(BaseDetailsActivity.this, "已取消收藏");
                    }
                }
                break;
            case R.id.activity_information_details_layout_shared:
                mDialogShared.show();
                mDialogShared.setData(mDetailsBean);
                break;
        }
    }

    protected void finishActtivity() {
        finish();
    }

    private UMShareListener mUMShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(BaseDetailsActivity.this, "正在打开应用");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(BaseDetailsActivity.this, "分享成功:");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(QuKanDianApplication.getmContext()).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSharedListener(DetailsBean mDetailsBean, final SHARE_MEDIA share_media) {
        new ThirdWeChatShare().shared(Constants.TYPE_VIDEO, this, mDetailsBean, share_media, mUMShareListener);
    }

    protected synchronized void readOn(int type, final long duration) {
        if (null == mSubmitTaskProtocol) {
            mSubmitTaskProtocol = new PutNewTaskForVideoProtocol(this, mPostId,readGoldView, new BaseModel.OnResultListener<SubmitTaskBean>() {
                @Override
                public void onResultListener(SubmitTaskBean response) {
                    if (null == mSubmitTaskProtocolExtra && mFrom == 0) {
                        mSubmitTaskProtocolExtra = new SubmitTaskProtocol(BaseDetailsActivity.this, new BaseModel.OnResultListener<Boolean>() {
                            @Override
                            public void onResultListener(Boolean response) {
                                mSubmitTaskProtocolExtra = null;
                            }

                            @Override
                            public void onFailureListener(int code, String error) {
                                mSubmitTaskProtocolExtra = null;
                            }
                        });
                        mSubmitTaskProtocolExtra.submitTask(-1, "push_extra_gold@" + mPostId);
                    }
                    mSubmitTaskProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mSubmitTaskProtocol = null;
                }
            });
            mSubmitTaskProtocol.postRequest();
        }
    }

    @Override
    public void onNetworkFail() {

    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        UserManager.getInst().removeLoginListener(this);
        mIvBackBtn = null;
        mIvCollectBtn = null;
        mIvSharedBtn = null;
        mAdapter = null;
        mLinearLayoutManager = null;
        mRecyclerView = null;
        mDatas.clear();
        mDatas = null;
        mDialogShared = null;
        mLeftAndRightAnimationHongbao.stopAnimation();
        ActivityManager.finishActivity(this);
    }

    @Override
    public void onLoginStatusListener(boolean state) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
