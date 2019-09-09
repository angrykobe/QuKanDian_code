package com.zhangku.qukandian.activitys.information;

import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.adapter.DetailsAdapter;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.CollectBean;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.PostTextDtl;
import com.zhangku.qukandian.bean.ReadProgressBean;
import com.zhangku.qukandian.bean.RecommendAdsBean;
import com.zhangku.qukandian.bean.SubmitTaskBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogChangeWordSize;
import com.zhangku.qukandian.dialog.DialogSharedArtNoGold;
import com.zhangku.qukandian.dialog.DialogSharedForGold;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewArtDetailsAboutDataProtocol;
import com.zhangku.qukandian.protocol.GetNewDetailsArtProtocol;
import com.zhangku.qukandian.protocol.PutNewCollectArticleProtocol;
import com.zhangku.qukandian.protocol.PutNewTaskForReadArtProtocol;
import com.zhangku.qukandian.protocol.SubmitTaskProtocol;
import com.zhangku.qukandian.utils.ActivityManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AdsLogUpUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.IsShowShareState;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.RecyclerViewDivider;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.ReadGoldProcessView;
import com.zhangku.qukandian.widght.ReadGoldView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.zhangku.qukandian.config.AnnoCon.ART_DETAIL_FROM_HIGH_PRICE;


/**
 * Created by yuzuoning on 2017/4/1.
 * 资讯详情页
 */
public class InformationDetailsAtivity extends BaseAct {
    private int mPostId = -1;
    private @AnnoCon.ArticleFrom
    int mFrom = AnnoCon.ART_DETAIL_FROM_ORDINARY;
    private View mTooLongView;
    private RecyclerView mRecyclerView;
    private ImageView mIvBackBtn;
    private ImageView mCollectIV;
    private ImageView mShareIV;
    private ImageView mMoreIV;
    private ReadGoldView readGoldView;
    private View loadingView;

    private PutNewTaskForReadArtProtocol mSubmitTaskProtocol;//	完成任务
    private SubmitTaskProtocol mSubmitTaskProtocolExtra;
    private PutNewCollectArticleProtocol mCollectArticleProtocol;//收藏点击
    private boolean isTouchToRead;//是否触发滚动阅读开关
    private DetailsAdapter mAdapter;
    private DetailsBean mDetailsBean;
    private InformationDetailsHeadLayout headView;
    private ArrayList<Object> mDatas = new ArrayList<>();
    private boolean doneTask = false;

    protected RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            isTouchToRead = true;
        }
    };
    private View mChangeWordsSizeView;
    private TextView mTvDetailsGold;//金币

    @Override
    protected void initViews() {
        mPostId = getIntent().getExtras().getInt(Constants.ID);
        mFrom = getIntent().getExtras().getInt("from", AnnoCon.ART_DETAIL_FROM_ORDINARY);

        readGoldView = findViewById(R.id.ReadGoldView);
        loadingView = findViewById(R.id.loadingView);
        mIvBackBtn = findViewById(R.id.activity_information_details_layout_back);
        mCollectIV = findViewById(R.id.collectIV);
        mShareIV = findViewById(R.id.shareIV);
        mMoreIV = findViewById(R.id.moreIV);
        mTooLongView = findViewById(R.id.readDetailTooLongView);

        View StatusBar = findViewById(R.id.StatusBar);
        setStatusView(this, StatusBar);
        //刷新
        SmartRefreshLayout refreshLayout = findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        refreshLayout.setEnableRefresh(false);

        mRecyclerView = findViewById(R.id.RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new DetailsAdapter(this, mDatas, Constants.TYPE_NEW);//下部列表（广告，推荐）
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, 1, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.grey_f2)));

        //头部布局，标题，内容，点击张开等功能
        headView = (InformationDetailsHeadLayout) LayoutInflater.from(this).inflate(R.layout.view_infor_top, mRecyclerView, false);
        mAdapter.setHeaderView(headView);

        mIvBackBtn.setOnClickListener(this);
        mCollectIV.setOnClickListener(this);
        mShareIV.setOnClickListener(this);
        mMoreIV.setOnClickListener(this);
        findViewById(R.id.activity_information_details_layout_goto_home).setOnClickListener(this);
        findViewById(R.id.readDetailTooLongClose).setOnClickListener(this);//文章停留过久弹框关闭按钮
        mChangeWordsSizeView = findViewById(R.id.changeWordsSizeTV);
        mChangeWordsSizeView.setOnClickListener(this);
        mTvDetailsGold = findViewById(R.id.tv_details_gold);
        mTvDetailsGold.setOnClickListener(this);
        //阅读进度初始化
        readGoldView.setMaxProcess(Integer.valueOf(TextUtils.isEmpty(UserManager.readingDuration) ? "20" : UserManager.readingDuration));
        readGoldView.setTouchListener(touchViewListen);

        ActivityManager.addActivity(this);
//        if (ActivityManager.getActivityStack().size() > 1) {
        findViewById(R.id.activity_information_details_layout_goto_home).setVisibility(View.VISIBLE);
//        } else {
//            findViewById(R.id.activity_information_details_layout_goto_home).setVisibility(View.GONE);
//        }
        //头部webview加载进度
        final ProgressBar progressBar = findViewById(R.id.webprogressBar);
        //后台控制是否显示点击查看全文按钮
        final int isOpen = UserManager.getInst().getQukandianBean().getIsOpen();//1：展开；0或空：不自动展开
        headView.getmTvReadAllBtn().setVisibility(isOpen == 1 ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        headView.getWebView().setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i != 100 && progressBar.getVisibility() != View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(i);
                } else if (progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                    readGoldView.startTiming(4);
                    isTouchToRead = false;
                    if (isOpen == 1)
                        headView.getmTvReadAllBtn().performClick();
                }
            }
        });
    }

    //文章阅读process进度回调
    private ReadGoldProcessView.OnTouchViewListen touchViewListen = new ReadGoldProcessView.OnTouchViewListen() {


        @Override
        public boolean onTouchView(int position) {
            switch (position) {
                case 1:
                case 2:
                case 3:
                    break;
                case 4:
                    if (UserManager.getInst().hadLogin()) {
                        readOn();
                    }
                    return true;
            }
            if (!UserManager.getInst().hadLogin()) {
                //弹出登陆提示提示
                readGoldView.showRemind(R.string.read_remindstr_for_login, R.string.read_remindbtn_for_login, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readGoldView.hideRemind();
                        ActivityUtils.startToBeforeLogingActivity(InformationDetailsAtivity.this);
                        MobclickAgent.onEvent(InformationDetailsAtivity.this, "01_05_02_clicksigninbtn");
                    }
                });
                return false;
            }
//            if (!headView.isShowAllContent()) {//没有点击阅读全文
//                //没展示提示语（有可能提示用户未登录）展示阅读全文提示
//                if (!readGoldView.isShowing()) {
//                    Spanned spanned = Html.fromHtml(getString(R.string.detail_read));
//                    readGoldView.showRemind(spanned, "点击阅读全文", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            readGoldView.hideRemind();
//                            View allClickView = headView.getmTvReadAllBtn();
//                            if (allClickView != null) allClickView.performClick();
//                        }
//                    });
//                }
//                return false;
//            } else
            if (isTouchToRead) {//滑动全文
                if (readGoldView.isShowing() && UserManager.getInst().hadLogin()) {
                    readGoldView.hideRemind();
                }
                isTouchToRead = false;
                return true;
            } else {//没有滑动全文
                if (!readGoldView.isShowing()) {
                    readGoldView.showRemind(UserManager.getInst().getReadTips("slide", "滑动页面，认真阅读可获得金币"), null);
                }
                return false;
            }
        }

        @Override
        public void stayTimeTooLong() {
            mTooLongView.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_information_details_layout2;
    }

    @Override
    protected String setTitle() {
        return "资讯详情页";
    }

    @Override
    protected void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.activity_information_details_layout_back:
                finish();
                break;
            case R.id.activity_information_details_layout_goto_home://回首页
                ActivityManager.finishAllActivity();
                break;
            case R.id.collectIV:
                if (mDetailsBean == null) return;
                MobclickAgent.onEvent(this, "01_03_clickCollection");
                if (UserManager.getInst().hadLogin()) {
                    if (null == mCollectArticleProtocol) {
                        CollectBean bean = new CollectBean();
                        bean.setPostId(mPostId);
                        bean.setTextType(0);
                        bean.setZyId(mDetailsBean.getZyId());

                        mCollectArticleProtocol = new PutNewCollectArticleProtocol(this, !mCollectIV.isSelected(), bean, new BaseModel.OnResultListener<Object>() {
                            @Override
                            public void onResultListener(Object response) {
                                if (mCollectIV.isSelected()) {
                                    ToastUtils.showLongToast(InformationDetailsAtivity.this, "已取消收藏");
                                    mCollectIV.setSelected(false);
                                } else {
                                    ToastUtils.showLongToast(InformationDetailsAtivity.this, "收藏成功");
                                    mCollectIV.setSelected(true);
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
                    ActivityUtils.startToBeforeLogingActivity(this);
                }
                break;
            case R.id.readDetailTooLongClose:
                if (mDetailsBean == null) return;
                mTooLongView.setVisibility(View.GONE);
                break;
            case R.id.changeWordsSizeTV:
                if (mDetailsBean == null) return;
                changeWordsSize();
                break;
            case R.id.shareIV:
                if (mDetailsBean == null) return;
                if (IsShowShareState.isShow()) {//有金币的分享
                    if (ART_DETAIL_FROM_HIGH_PRICE == mFrom) {//高价文分享
                        MobclickAgent.onEvent(this, "291_count_High_Price_Share");
                        new DialogSharedForGold(this, mDetailsBean, 1).show();
                    } else {//普通有金币分享
                        new DialogSharedForGold(this, mDetailsBean, 0).show();
                    }
                    MobclickAgent.onEvent(this, "01_04_clisksharebtn");
                } else {//无金币的分享
                    MobclickAgent.onEvent(this, "01_04_clisksharebtn");
                    new DialogSharedArtNoGold(this, mDetailsBean).show();
                }
                break;
            case R.id.moreIV://更多
                if (mDetailsBean == null) return;
                changeWordsSize();
                break;
            case R.id.tv_details_gold://金币--阅读进度
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToReadProgressActivity(this);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(this);
                }
                break;
        }
    }

    private AnimationDrawable mAnimationDrawable;

    @Override
    protected void loadData() {
        if (!CommonHelper.isNetworkAvalible(this)) {
            setContentView(R.layout.view_error);
            return;
        }

        int channel = getIntent().getIntExtra("channel", 0);
        String zyId = getIntent().getStringExtra("zyId");
        loadingView.setVisibility(View.VISIBLE);
        View viewById = loadingView.findViewById(R.id.loadingIV);
        viewById.setBackgroundResource(R.drawable.load_anim);
//        mAnimationDrawable = (AnimationDrawable) viewById.getBackground();
//        if (mAnimationDrawable != null) mAnimationDrawable.start();
        new GetNewDetailsArtProtocol(this, mPostId, zyId, channel, new BaseModel.OnResultListener<PostTextDtl>() {
            @Override
            public void onResultListener(final PostTextDtl detailsBean) {
                loadingView.setVisibility(View.GONE);
                if (detailsBean.getPostText() == null) return;
                if (detailsBean.getUserBehavior() == null) return;
//                if (mAnimationDrawable != null) mAnimationDrawable.stop();
                Map<String, String> map = new ArrayMap<>();
                map.put("To", detailsBean.getPostText().getChannel().getDisplayName());
                MobclickAgent.onEvent(InformationDetailsAtivity.this, "ArticleChannel", map);
                mDetailsBean = detailsBean.getPostText();
                mDetailsBean.setNewId(mPostId);
                headView.setData(detailsBean);
                if (Constants.HIGH_PRICE_NAME.equals(detailsBean.getPostText().getChannel().getName())) {//高价文id
                    if (IsShowShareState.isShowForHighArt()) {
                        mShareIV.setImageResource(R.mipmap.share_new_icon);//有金币的分享
                    } else {
                        mShareIV.setImageResource(R.mipmap.shared_cion);//无金币的分享
                    }
                } else {
                    if (IsShowShareState.isShow()) {
                        mShareIV.setImageResource(R.mipmap.share_new_icon);//有金币的分享
                    } else {
                        mShareIV.setImageResource(R.mipmap.shared_cion);//无金币的分享
                    }
                }
                if ("xiguang_news_shehui".equals(detailsBean.getPostText().getChannel().getName())) {//社会频道内容隐藏分享（优先级最高）
                    findViewById(R.id.shareIV).setVisibility(View.GONE);
                    headView.findViewById(R.id.header_information_layout_wechat).setVisibility(View.GONE);
                    headView.findViewById(R.id.header_information_layout_circle).setVisibility(View.GONE);
                }
                if ("社会摇".equals(detailsBean.getPostText().getChannel().getName())) {
                    MobclickAgent.onEvent(InformationDetailsAtivity.this, "293_Channel_shehuiyao_detail");
                }
                ////////////////////////////////////////////////////////////////
                DBTools.addReadRecord(mPostId, detailsBean.getPostText().getTitle());
                DBTools.addFarverite(mDetailsBean);
                ///////////////////////////////////////////////////////////////////
                if (UserManager.getInst().hadLogin()) {
                    ///获取是否收藏
                    //new GetFavoriteStatusProtocol(InformationDetailsAtivity.this, mPostId, detailsBean.getPostText().getZyId(), new BaseModel.OnResultListener<Boolean>() {
                    //    @Override
                    //    public void onResultListener(Boolean response) {
                    //        mDetailsBean.setFavorite(response);
                    //        if (null != mCollectIV) {
                    //            mCollectIV.setSelected(response);
                    //        }
                    //    }
//
                    //    @Override
                    //    public void onFailureListener(int code, String error) {
                    //    }
                    //}).postRequest();
                    mDetailsBean.setFavorite(detailsBean.isFavourite());
                    if (null != mCollectIV) {
                        mCollectIV.setSelected(detailsBean.isFavourite());
                    }
                }

                //相关推荐
                new GetNewArtDetailsAboutDataProtocol(InformationDetailsAtivity.this, mPostId, Constants.SIZE, detailsBean.getPostText().getZyId(), new BaseModel.OnResultListener<RecommendAdsBean>() {
                    @Override
                    public void onResultListener(RecommendAdsBean recommendAdsBean) {
                        List<InformationBean> response = recommendAdsBean.getPostTexts();
                        if (response != null) {
                            mDatas.clear();
                            mDatas.addAll(response);
                            mAdapter.notifyItemRangeChanged(1, mDatas.size());
                            startFetchAdThread(0, recommendAdsBean.getAds());
                        }
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                    }
                }).postRequest();

            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
        showGoldView();
    }

    /**
     * 插入广告
     */
    private void startFetchAdThread(final int start, List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> ads) {
        if (ads == null) return;

        Collections.sort(ads, new Comparator<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean>() {
            @Override
            public int compare(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean o1, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean o2) {
                if (o1.getPageIndex() > o2.getPageIndex()) {
                    return 1;
                }
                if (o1.getPageIndex() < o2.getPageIndex()) {
                    return -1;
                }
                return 0;
            }
        });

        for (int i = 1; i < ads.size(); i++) {
            AdLocationBeans.AdLocationsBean.ClientAdvertisesBean ad = ads.get(i);
            mDatas.add(start + ad.getPageIndex() - 2, null);
            LogUtils.LogW("aaaaaadd:" + (start + ad.getPageIndex() - 1));
        }

        AdUtil.fetchAd(this, ads, AnnoCon.AD_TYPE_NEWS_DETAILS, mDetailsBean.getChannelId(), new AdUtil.AdCallBack() {
            @Override
            public void getAdContent(Object object, int adIndex) {
                if (adIndex == 1) {
                    //大图位置
                    headView.setAdData(object);
                    return;
                }
                int adIndexForList = start + adIndex - 2;//广告对于list中的位置，（上啦加载更多的时候会变动）
                LogUtils.LogW("aaaaaset:" + adIndexForList);
                if (adIndexForList >= 0 && mDatas.size() > adIndexForList) {
                    mDatas.set(adIndexForList, object);
                    if (mAdapter != null) {
                        mAdapter.notifyItemRangeChanged(1, mDatas.size());
                    }

                    //保存广告日志
                    AdsLogUpUtils.saveAdsList(AdsLogUpUtils.ADLOGUP_TYPE_INFO_DETAIL,object);
                }
            }
        });
    }

    private void readOn() {
        if (null == mSubmitTaskProtocol) {
            mSubmitTaskProtocol = new PutNewTaskForReadArtProtocol(this, mPostId, mFrom, mDetailsBean.getZyId(), readGoldView, new BaseModel.OnResultListener<SubmitTaskBean>() {
                @Override
                public void onResultListener(SubmitTaskBean response) {
                    doneTask = true;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mSubmitTaskProtocol = null;
                }
            });
            mSubmitTaskProtocol.postRequest();
        }


        if (null == mSubmitTaskProtocolExtra && mFrom == AnnoCon.ART_DETAIL_FROM_PUSH) {
            new SubmitTaskProtocol(this, new BaseModel.OnResultListener<Boolean>() {
                @Override
                public void onResultListener(Boolean response) {
                    mSubmitTaskProtocolExtra = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mSubmitTaskProtocolExtra = null;
                }
            }).submitTask(-1, "push_extra_gold@" + mPostId);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        headView.reflashUI();
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
        MobclickAgent.onEvent(this, "ArticleDetails");
        MobclickAgent.onEvent(this, "DetailsCount");
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            readGoldView.setDestory();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (readGoldView != null && !doneTask) {
            readGoldView.getReadGoldProcessView().setmCurProcess(0);
            readGoldView.getReadGoldProcessView().setStopRun(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (readGoldView != null && !doneTask) {
            readGoldView.getReadGoldProcessView().setStopRun(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        headView.getWebView().removeAllViews();
        headView.getWebViewParent().removeView(headView.getWebView());
        headView.getWebView().destroy();
        readGoldView.setDestory();
        ActivityManager.removeActivity(this);
    }

    //修改中字体弹窗
    private void changeWordsSize() {
        DialogChangeWordSize dialogChangeWordSize = new DialogChangeWordSize(this);
        dialogChangeWordSize.setCheckList(new DialogChangeWordSize.ChangeWordSizeDao() {
            @Override
            public void changeSize(int size) {
                switch (size) {
                    case 1:
                        headView.getWebView().getSettings().setTextZoom(75);
                        break;
                    case 2:
                        headView.getWebView().getSettings().setTextZoom(100);
                        break;
                    case 3:
                        headView.getWebView().getSettings().setTextZoom(125);
                        break;
                }

                if (headView.isShowAllContent()) {
                    headView.getWebView().loadUrl("javascript:imagelistener.resize(document.body.getBoundingClientRect().bottom)");
                }
            }

            @Override
            public void onSave() {
                if (headView.isShowAllContent() && mDetailsBean.getContentDisType() != 0) {//静态页文章(唔哩文章)改变字体大小会有空白bug
                    InformationDetailsAtivity.this.recreate();
                }
            }
        });
        dialogChangeWordSize.show();
    }

    //显示阅读进度按钮
    private void showGoldView() {
        if (UserManager.getInst().hadLogin()) {//金币
            if (UserBean.getShowReadProgress()) {
                if (UserManager.getInst().getQukandianBean().getReadprogress() == null)
                    return;
                mTvDetailsGold.setVisibility(View.VISIBLE);
                ArrayList<ReadProgressBean.ProgressRuleBean> list = UserManager.getInst().getQukandianBean().getReadprogress().getProgressRule();
                if (list != null && list.size() > 0) {
                    int gold = 0;//阅读进度能获取到奖励的总金币数
                    for (ReadProgressBean.ProgressRuleBean bean : list) {
                        gold = gold + bean.getGold();
                    }
                    mTvDetailsGold.setText("+" + gold);
                }
            }
        }
    }
}