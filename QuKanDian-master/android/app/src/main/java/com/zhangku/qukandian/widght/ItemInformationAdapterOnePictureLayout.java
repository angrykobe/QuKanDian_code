package com.zhangku.qukandian.widght;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.GlideUtils;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/3/29.
 * 一张图item
 */

public class ItemInformationAdapterOnePictureLayout extends LinearLayout {
    private ImageView mImageViewBg;
    private TextView mTvTitle;
    private TextView mTvAuthor;
    private TextView mTvNumber;
    private TextView mTvReleasTime;
    private ImageView mIvPlayIcon;
    private TextView mTvTop;
    private View mPlaceHolderView;
    private View mFilterCloceIV;//新闻过滤
    private View mHighPriceView;//限时高价

    private int mPostId = -1;//文章id
    private int mType = -1;
    private LinearLayout mReadRecordBtn;
    private RelativeLayout mTextLayout;
    private RelativeLayout mLayoutSelect;
    private ImageView mIvSelectIcon;
    private boolean mIsSelected = false;
    private boolean mEditState = false;
    private boolean mIsReadRecord = false;
    private OnClickSelectedLisntener mOnClickSelectedLisntener;
    private LinearLayout mLayout;
    private ArticleShieldDialog mDialog;//新闻过滤弹框
    private ArticleShieldDialog.OnClickDelete clickLestener;//不喜欢点击回调

    private OnCilicReadRecordListener mOnCilicReadRecordListener;
    private int mPposition;//列表下标

    public ItemInformationAdapterOnePictureLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLayout = (LinearLayout) findViewById(R.id.item_information_adapter_one_pic_layout);
        mPlaceHolderView = findViewById(R.id.item_information_adapter_one_pic_layout_placeholder);
        mHighPriceView = findViewById(R.id.item_information_adapter_one_pic_price);
        mLayoutSelect = (RelativeLayout) findViewById(R.id.item_information_adapter_one_pic_layout_select);
        mIvSelectIcon = (ImageView) findViewById(R.id.item_information_adapter_one_pic_layout_select_icon);
        mReadRecordBtn = (LinearLayout) findViewById(R.id.item_read_record_view_item);
        mIvPlayIcon = (ImageView) findViewById(R.id.item_information_adapter_one_pic_play_icon);
        mImageViewBg = (ImageView) findViewById(R.id.item_information_adapter_one_pic_imageview);
        mTvTitle = (TextView) findViewById(R.id.item_information_adapter_one_pic_title);
        mTvAuthor = (TextView) findViewById(R.id.item_information_adapter_one_pic_author);
        mTvNumber = (TextView) findViewById(R.id.item_information_adapter_one_pic_number);
        mTextLayout = (RelativeLayout) findViewById(R.id.text_layout);
        mTvReleasTime = (TextView) findViewById(R.id.item_information_adapter_one_pic_release_time);
        mTvTop = findViewById(R.id.item_information_adapter_one_pic_top);
        mFilterCloceIV = findViewById(R.id.item_information_adapter_one_pic_filter);

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
        mFilterCloceIV.setOnClickListener(new OnClickListener() {
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
        mPposition = position;
        if (null != bean) {
            mHighPriceView.setVisibility(Constants.HIGH_PRICE_NAME.equals(bean.getChannelName()) ? View.VISIBLE : View.GONE);
            if (bean.isTop() && bean.getChannelId() == 0) {
                mTvTop.setVisibility(View.VISIBLE);
            } else {
                mTvTop.setVisibility(View.GONE);
            }
            mPostId = bean.getPostId();
            mType = bean.getTextType();
            mTvTitle.setText(bean.getTitle());
            mTvAuthor.setText(bean.getAuthorName());
            mTvNumber.setText(bean.getViewNumber() + bean.getViewSeedNumber() + "阅读");
            mTvReleasTime.setText(bean.getReleaseTimeMemo());
            ViewTreeObserver viewTreeObserver = mTvTitle.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= 16) {
                        mTvTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);//避免重复监听
                    } else {
                        mTvTitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTextLayout.getLayoutParams();
                    if (mTvTitle.getLineCount() >= 3) {
                        lp.setMargins(0, DisplayUtils.dip2px(getContext(), 0), 0, 0);
                    } else if (mTvTitle.getLineCount() == 2) {
                        lp.setMargins(0, DisplayUtils.dip2px(getContext(), 10), 0, 0);
                    } else {
                        lp.setMargins(0, DisplayUtils.dip2px(getContext(), 35), 0, 0);
                    }
                    mTextLayout.setLayoutParams(lp);

                }
            });

            if (bean.getTextType() == Constants.TYPE_NEW) {
                mIvPlayIcon.setVisibility(View.GONE);
            } else {
                mIvPlayIcon.setVisibility(View.VISIBLE);
            }
            if (bean.getPostTextImages().size() > 0) {
                GlideUtils.displayImage(getContext(), bean.getPostTextImages().get(0).getSrc(), mImageViewBg);
            }
            ItemInformationAdapterOnePictureLayout.this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIsReadRecord) {
                        mTvTitle.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                        mTvAuthor.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                        mTvNumber.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                        mTvReleasTime.setTextColor(CommonHelper.parseColor("#a9a9a9"));
                    }
                    if (!mEditState) {
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
                                    String url = bean.getStaticUrl() + "&key=" + key;
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
                    } else {
                        mIsSelected = !mIsSelected;
                        mIvSelectIcon.setSelected(mIsSelected);
                        if (null != mOnClickSelectedLisntener) {
                            mOnClickSelectedLisntener.onClickSelectedLisntener(position, mIsSelected);
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
            mPlaceHolderView.setVisibility(View.VISIBLE);
            mLayoutSelect.setVisibility(View.VISIBLE);
        } else {
            mPlaceHolderView.setVisibility(View.GONE);
            mLayoutSelect.setVisibility(View.GONE);
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

    public void setOnCilicReadRecordListener(OnCilicReadRecordListener onCilicReadRecordListener) {
        mOnCilicReadRecordListener = onCilicReadRecordListener;
    }

    public void setClickLestener(ArticleShieldDialog.OnClickDelete clickLestener) {
        this.clickLestener = clickLestener;
    }
}
