package com.zhangku.qukandian.activitys.information;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.SearchActivity;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.DisLikeNumBean;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.bean.PostTextDtl;
import com.zhangku.qukandian.bean.UserPostBehaviorDto;
import com.zhangku.qukandian.dialog.ArticleShieldDialog;
import com.zhangku.qukandian.javascript.MJavascriptInterface;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.DisLikeNewsProtocol;
import com.zhangku.qukandian.protocol.LikeNumProtocol;
import com.zhangku.qukandian.protocol.RemovePraiseProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AnimUtil;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.SetWebSettings;
import com.zhangku.qukandian.utils.ShareUtil;
import com.zhangku.qukandian.utils.StringUtils;
import com.zhangku.qukandian.widght.DetailBigImgAdView;
import com.zhangku.qukandian.widght.LableLayout;

import java.util.Map;

public class InformationDetailsHeadLayout extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private TextView mTvTitle;//标题
    private TextView mTvAuthor;//作者
    private WebView mWebView;//内容详情html
    private FrameLayout mWebViewParent;//内容详情html
    private TextView mPraiseTV;//点赞
    private TextView mPraiseAnimTV;//点赞需要的动画
    private DetailBigImgAdView bigImgAdView;//大图广告位
    private View mTvReadAllBtn;
    private View mTvReadAllBtnBg;
    private LableLayout mLableLayout;
    private LikeNumProtocol likeNumProtocol;
    private DetailsBean mDetailsBean;

    public InformationDetailsHeadLayout(Context context) {
        this(context, null);
    }

    public InformationDetailsHeadLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InformationDetailsHeadLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.header_information_layout, this);

        mPraiseTV = findViewById(R.id.header_information_layout_praise);//点赞
        mPraiseAnimTV = findViewById(R.id.header_information_layout_praise_anim_view);//点赞动画
        mTvTitle = findViewById(R.id.header_information_title);
        mTvAuthor = findViewById(R.id.header_information_author);
        bigImgAdView = findViewById(R.id.bigImgAdView);
        mLableLayout = findViewById(R.id.lable_layout_layout);
        mTvReadAllBtn = findViewById(R.id.header_information_read_all_btn);//点击阅读全文
        mTvReadAllBtnBg = findViewById(R.id.header_information_read_all_tran_bg);//点击阅读全文
        mWebView = new WebView(getContext().getApplicationContext());
        mWebViewParent = findViewById(R.id.detail_webview_parent);
        mWebViewParent.addView(mWebView, 0);
        SetWebSettings.setWebviewForDetails(mContext, mWebView);

        findViewById(R.id.header_information_layout_nolike).setOnClickListener(this);//不喜欢
        findViewById(R.id.header_information_layout_wechat).setOnClickListener(this);//微信分享
        findViewById(R.id.header_information_layout_circle).setOnClickListener(this);//朋友圈分享
        mPraiseTV.setOnClickListener(this);
        mTvReadAllBtn.setOnClickListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    @Override
    public void onClick(View v) {
        if (mDetailsBean == null) return;
        switch (v.getId()) {
            case R.id.header_information_layout_nolike:
                if (UserManager.getInst().hadLogin()) {
                    ArticleShieldDialog dialog = new ArticleShieldDialog(getContext(), mDetailsBean.getNewId(), mDetailsBean.getZyId());
                    dialog.show();
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                break;
            case R.id.header_information_layout_wechat:
                ShareUtil.shareArtDetailsToGetUrl((Activity) mContext, mDetailsBean, SHARE_MEDIA.WEIXIN);
                break;
            case R.id.header_information_layout_circle:
                ShareUtil.shareArtDetailsToGetUrl((Activity) mContext, mDetailsBean, SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.header_information_layout_praise:
                praise();
                break;
            case R.id.header_information_read_all_btn:
                Map<String, String> map = new ArrayMap<>();
                map.put("count", "click");
                MobclickAgent.onEvent(getContext(), "ReadAllBtn", map);
                showAllContent();
        }
    }


    public void showAllContent() {
        ViewGroup.LayoutParams lp = mWebViewParent.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mWebViewParent.setLayoutParams(lp);
        mTvReadAllBtn.setVisibility(View.GONE);
        mTvReadAllBtnBg.setVisibility(View.GONE);
    }

    public void setData(PostTextDtl response) {
        mDetailsBean = response.getPostText();
        mTvTitle.setText(response.getPostText().getTitle());
        mTvAuthor.setText(response.getPostText().getAuthorName());
        mLableLayout.setData(response.getPostText().getLabelTerms(), SearchActivity.FROM_OTHER);

        if (likeNumProtocol == null) {
            //likeNumProtocol = new LikeNumProtocol(getContext(), mDetailsBean.getNewId(), mDetailsBean.getZyId(), new BaseModel.OnResultListener<DisLikeNumBean>() {
            //    @Override
            //    public void onResultListener(DisLikeNumBean response) {
            //        mPraiseTV.setSelected(response.isLike());
            //        mPraiseTV.setText("" + response.getLikeNumber());
            //    }
//
            //    @Override
            //    public void onFailureListener(int code, String error) {
//
            //    }
            //});
            //likeNumProtocol.postRequest();
        }
//        String string = UserSharedPreferences.getInstance().getString(Constants.StaticPageHost, "");//文章详情静态页域名

//        if (TextUtils.isEmpty(response.getStaticUrl()) || TextUtils.isEmpty(string)) {

        mPraiseTV.setSelected(response.getUserBehavior().isLike());
        mPraiseTV.setText("" + response.getUserBehavior().getLikeNumber());
        if (response.getPostText().getContentDisType() == 0) {//原文
            String mHtml = mDetailsBean.getPostTextDatas().get(0).getContent();
            mWebView.loadDataWithBaseURL("", mHtml, "text/html", "UTF-8", "");
            mWebView.addJavascriptInterface(new MJavascriptInterface(mContext, mWebView,
                    StringUtils.returnImageUrlsFromHtml(mHtml)), "imagelistener");
            //mTvTitle.setVisibility(View.VISIBLE);
            //mTvAuthor.setVisibility(View.VISIBLE);
        } else {//加载静态页（唔哩文章）
            mTvTitle.setVisibility(View.GONE);
            mTvAuthor.setVisibility(View.GONE);
            mWebView.loadUrl(mDetailsBean.getStaticUrl());
            mWebView.addJavascriptInterface(new MJavascriptInterface(mContext, mWebView,
                    StringUtils.returnImageUrlsFromHtml("")), "imagelistener");
        }
    }

    public void setAdData(Object object) {
        if (object instanceof AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) {
            bigImgAdView.setUrlAdData((AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) object);
        } else if (object instanceof NativeAdInfo) {
            bigImgAdView.setNativeData((NativeAdInfo) object);
        } else {
            bigImgAdView.setNullData();
        }
    }

    //点赞
    private void praise() {
        if (!mPraiseTV.isSelected()) {
            //点赞
            UserPostBehaviorDto bean = new UserPostBehaviorDto();
            bean.setActionType(8);
            if (!TextUtils.isEmpty(DeviceUtil.getDeviceInfo(getContext()).imei)){
                bean.setImei(DeviceUtil.getDeviceInfo(getContext()).imei);
            }else {
                bean.setImei("");
            }
            bean.setPostId(mDetailsBean.getNewId());
            bean.setUserId(UserManager.getInst().getUserBeam().getId());
            bean.setZyId(mDetailsBean.getZyId());
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
            new RemovePraiseProtocol(getContext(), UserManager.getInst().getUserBeam().getId(), mDetailsBean, new BaseModel.OnResultListener<Object>() {
                @Override
                public void onResultListener(Object response) {
                    if ("1".equals(response)) {
                        mPraiseTV.setSelected(false);
                        String num = mPraiseTV.getText().toString();
                        if (!TextUtils.isEmpty(num)) {
                            long nowNum = Long.valueOf(num) - 1;
                            mPraiseTV.setText("" + nowNum);
                        }
                    }
                }

                @Override
                public void onFailureListener(int code, String error) {
                    if (code == 1) {
                        mPraiseTV.setSelected(false);
                        String num = mPraiseTV.getText().toString();
                        if (!TextUtils.isEmpty(num)) {
                            long nowNum = Long.valueOf(num) - 1;
                            mPraiseTV.setText("" + nowNum);
                        }
                    }
                }
            }).postRequest();
        }
    }

    public View getmTvReadAllBtn() {
        return mTvReadAllBtn;
    }

    public WebView getWebView() {
        return mWebView;
    }

    public FrameLayout getWebViewParent() {
        return mWebViewParent;
    }

//    public WebView getAdWebView() {
//        return bigImgAdView.getmDetailMySelfAdView();
//    }

    public void reflashUI() {
        bigImgAdView.reflash();
    }

    //是否已经展示全文
    public boolean isShowAllContent() {
        return mTvReadAllBtn.getVisibility() == View.GONE;
    }

}
