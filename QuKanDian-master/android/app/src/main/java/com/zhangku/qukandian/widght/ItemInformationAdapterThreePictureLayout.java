package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.ArticleShieldDialog;
import com.zhangku.qukandian.interfaces.OnCilicReadRecordListener;
import com.zhangku.qukandian.interfaces.OnClickSelectedLisntener;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.AES;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.IsShowShareState;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/3/29.
 * 三张图item
 */

public class ItemInformationAdapterThreePictureLayout extends LinearLayout {
    private TextView mTvTitle;
    private RatioImageView mImageView1;
    private RatioImageView mImageView2;
    private RatioImageView mImageView3;
    private TextView mTvAuthor;
    private TextView mTvNumber;
    private TextView mTvReleasTime;
    private TextView mTvShareIcon;
    private TextView mTvTop;
    private View mPlaceHolderView;
    private View mHighPriceView;//高价文
    private View mFilterIV;//新闻过滤
    private int mPostId = -1;
    private int mType = -1;
    private LinearLayout mReadRecordBtn;//刚刚看到这里 点击刷新
    private LinearLayout mLayoutSelect;
    private ImageView mIvSelectIcon;
    private boolean mIsSelected = false;
    private boolean mEditState = false;
    private boolean mIsReadRecord = false;
    private OnClickSelectedLisntener mOnClickSelectedLisntener;
    private LinearLayout mLayout;
    private ArticleShieldDialog mDialog;//新闻过滤弹框
    private ArticleShieldDialog.OnClickDelete clickLestener;//不喜欢点击回调

    public void setOnCilicReadRecordListener(OnCilicReadRecordListener onCilicReadRecordListener) {
        mOnCilicReadRecordListener = onCilicReadRecordListener;
    }

    private OnCilicReadRecordListener mOnCilicReadRecordListener;

    public ItemInformationAdapterThreePictureLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLayout = (LinearLayout) findViewById(R.id.item_information_adapter_three_pic_layout);
        mHighPriceView = findViewById(R.id.item_information_adapter_one_pic_price);
        mPlaceHolderView = findViewById(R.id.item_information_adapter_three_pic_placeholder);
        mLayoutSelect = (LinearLayout) findViewById(R.id.item_information_adapter_three_pic_select);
        mIvSelectIcon = (ImageView) findViewById(R.id.item_information_adapter_three_pic_select_icon);
        mReadRecordBtn = (LinearLayout) findViewById(R.id.item_read_record_view_item);//刚刚看到这里 点击刷新   按钮
        mTvTitle = (TextView) findViewById(R.id.item_information_adapter_three_pic_title);
        mImageView1 = (RatioImageView) findViewById(R.id.item_information_adapter_three_pic_imgs1);
        mImageView2 = (RatioImageView) findViewById(R.id.item_information_adapter_three_pic_imgs2);
        mImageView3 = (RatioImageView) findViewById(R.id.item_information_adapter_three_pic_imgs3);
        mTvAuthor = (TextView) findViewById(R.id.item_information_adapter_three_pic_author);
        mTvNumber = (TextView) findViewById(R.id.item_information_adapter_three_pic_number);
        mTvReleasTime = (TextView) findViewById(R.id.item_information_adapter_three_pic_releas_time);
        mTvShareIcon = findViewById(R.id.item_information_adapter_three_pic_share_icon);
        mTvTop = findViewById(R.id.item_information_adapter_three_pic_top);
        mFilterIV = findViewById(R.id.item_information_adapter_one_pic_filter);//文章过滤按钮

        mTvReleasTime.setVisibility(View.GONE);

        mReadRecordBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnCilicReadRecordListener) {
                    mOnCilicReadRecordListener.onCilicReadRecordListener();
                }
            }
        });
    }

    public void setData(final InformationBean bean, final boolean isRecord, final int position) {
        if (null != bean) {
            mFilterIV.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UserManager.getInst().hadLogin()) {
                        if (mDialog == null)
                            mDialog = new ArticleShieldDialog(v.getContext(), mPostId, bean.getZyId());
                        mDialog.setmPostId(mPostId);
                        mDialog.setClickLestener(clickLestener);
                        mDialog.show();
                    } else {
                        ActivityUtils.startToBeforeLogingActivity(v.getContext());
                    }
                }
            });
            mHighPriceView.setVisibility(Constants.HIGH_PRICE_NAME.equals(bean.getChannelName()) ? View.VISIBLE : View.GONE);
            if (IsShowShareState.isShow()) {
                mTvShareIcon.setVisibility(View.GONE);
                mTvShareIcon.setText(UserManager.getInst().getQukandianBean().getSharearticleamount() + "金币");
            } else {
                mTvShareIcon.setVisibility(View.GONE);
            }
            if (bean.isTop() && bean.getChannelId() == 0) {
                mTvTop.setVisibility(View.VISIBLE);
            } else {
                mTvTop.setVisibility(View.GONE);
            }
            mPostId = bean.getPostId();
            mType = bean.getTextType();
            mTvTitle.setText(bean.getTitle() + "");
            mTvAuthor.setText(bean.getAuthorName() + "");
            mTvNumber.setText(bean.getViewNumber() + bean.getViewSeedNumber() + "阅读");
            mTvReleasTime.setText(bean.getReleaseTimeMemo() + "");
            if (bean.getPostTextImages().size() >= 3) {
                GlideUtils.displayImage(getContext(), bean.getPostTextImages().get(0).getSrc(), mImageView1);
                GlideUtils.displayImage(getContext(), bean.getPostTextImages().get(1).getSrc(), mImageView2);
                GlideUtils.displayImage(getContext(), bean.getPostTextImages().get(2).getSrc(), mImageView3);
            }

            ItemInformationAdapterThreePictureLayout.this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIsReadRecord) {
                        mTvTitle.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                        mTvAuthor.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                        mTvNumber.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                        mTvReleasTime.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                    }
                    if (mEditState) {
                        mIsSelected = !mIsSelected;
                        mIvSelectIcon.setSelected(mIsSelected);
                        if (null != mOnClickSelectedLisntener) {
                            mOnClickSelectedLisntener.onClickSelectedLisntener(position, mIsSelected);
                        }
                    } else {
                        if (isRecord) {
                            Map<String, String> map = new ArrayMap<>();
                            map.put("from", "文章列表");
                            MobclickAgent.onEvent(getContext(), "ArtilePV", map);
                        }
                        if (mType == Constants.TYPE_NEW) {
                            if (Constants.XIGUANG_NAME.equals(bean.getChannelName())) {
                                ActivityUtils.startToInformationDetailsActivity(getContext(), mPostId, AnnoCon.ART_DETAIL_FROM_XIGUANG);
                            } else if (Constants.HIGH_PRICE_NAME.equals(bean.getChannelName())) {
                                MobclickAgent.onEvent(getContext(), "291_count_High_Price_Click");
                                if (bean.getContentDisType() == 2 && bean.getArticleType() == 1) {
                                    // h5页面
                                    String key = "bs" + AES.encryUserId(UserManager.getInst().getUserBeam().getId())
                                            + "_foot" + AES.encryPostId(bean.getId())
                                            + "_pp" + AES.encryLog(100001);
                                    String url = bean.getStaticUrl() + "&key=" +key;
                                    ;
                                    ActivityUtils.startToWebviewAct(getContext(), url);
                                } else {
                                    ActivityUtils.startToInformationDetailsActivity(getContext(), mPostId, AnnoCon.ART_DETAIL_FROM_HIGH_PRICE);
                                }
                            } else {
                                ActivityUtils.startToInformationDetailsActivity(getContext(), mPostId, AnnoCon.ART_DETAIL_FROM_ORDINARY, bean.getZyId(), bean.getChannelId());
                            }
                        } else {
                            ActivityUtils.startToVideoDetailsActivity(getContext(), mPostId, -1);
                        }
                    }
                }
            });
        }
    }

    public void showReadrecord(boolean show) {
        if (show) {
            mReadRecordBtn.setVisibility(View.VISIBLE);
        } else {
            mReadRecordBtn.setVisibility(View.GONE);
        }
    }

    public void showSelectLayout(boolean show) {
        mEditState = show;
        if (show) {
            mLayoutSelect.setVisibility(View.VISIBLE);
            mPlaceHolderView.setVisibility(View.VISIBLE);
        } else {
            mLayoutSelect.setVisibility(View.GONE);
            mPlaceHolderView.setVisibility(View.GONE);
        }
    }

    public void setOnClickSelectedLisntener(OnClickSelectedLisntener onClickSelectedLisntener) {
        mOnClickSelectedLisntener = onClickSelectedLisntener;
    }

    public void selecetdAll(boolean all) {
        mIsSelected = all;
        mIvSelectIcon.setSelected(all);
    }

    public void setIsTag(boolean isTag, InformationBean bean) {
        mIsReadRecord = isTag;
        if (isTag) {
            if (DBTools.queryReadRecordByPostId(bean.getPostId(), bean.getTitle())) {
                mTvTitle.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                mTvAuthor.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                mTvNumber.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                mTvReleasTime.setTextColor(CommonHelper.parseColor("#a9a9a9"));
            } else {
                mTvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.black_1b));
                mTvAuthor.setTextColor(ContextCompat.getColor(getContext(), R.color.black_a9));
                mTvNumber.setTextColor(ContextCompat.getColor(getContext(), R.color.black_a9));
                mTvReleasTime.setTextColor(ContextCompat.getColor(getContext(), R.color.black_a9));
            }
        } else {

        }
    }

    public void setClickLestener(ArticleShieldDialog.OnClickDelete clickLestener) {
        this.clickLestener = clickLestener;
    }
}
