package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.biz.adcore.ZKAdTypeEnum;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.GlideUtils;

/**
 * Created by yuzuoning on 2017/7/12.
 */

public class ItemAdLayout extends LinearLayout {
    private LinearLayout mLayoutMutilPic;//三图
    private TextView mTextView;
    private ImageView mAdImageView;
    private RatioImageView mAdImageViewOne;
    private RatioImageView mAdImageViewTwo;
    private RatioImageView mAdImageViewThree;
    private ImageView mIvAdText;
    private ImageView mIvAdIcon1;
    private ImageView mIvAdText1;
    private TextView mTvAdText1;
    private RelativeLayout mAdLayout;
    private LinearLayout ad_view_card_001;
    private RelativeLayout ad_view_card_002;
    private ImageView ad_imageview_002;
    private ImageView ad_one_text_002;
    private FrameLayout addView;

    private TextView ad_title_002;

    public ItemAdLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ad_view_card_001 = (LinearLayout) findViewById(R.id.ad_view_card_001);
        addView = findViewById(R.id.addView);
        ad_view_card_002 = (RelativeLayout) findViewById(R.id.ad_view_card_002);
        mLayoutMutilPic = (LinearLayout) findViewById(R.id.ad_mutilpic_layout);
        mTextView = (TextView) findViewById(R.id.ad_title);
        ad_title_002 = (TextView) findViewById(R.id.ad_title_002);
        mAdImageView = (ImageView) findViewById(R.id.ad_imageview);
        ad_imageview_002 = (ImageView) findViewById(R.id.ad_imageview_002);
        ad_one_text_002 = (ImageView) findViewById(R.id.ad_one_text_002);
        mAdImageViewOne = (RatioImageView) findViewById(R.id.ad_mutilpic_one);
        mAdImageViewTwo = (RatioImageView) findViewById(R.id.ad_mutilpic_two);
        mAdImageViewThree = (RatioImageView) findViewById(R.id.ad_mutilpic_three);
        mIvAdText = findViewById(R.id.ad_one_text);
        mIvAdIcon1 = findViewById(R.id.ad_texts);
        mTvAdText1 = findViewById(R.id.ad_remind);
        mIvAdText1 = findViewById(R.id.ad_icon);
        mAdLayout = findViewById(R.id.ad_other_layout);
    }

    public void setData(NativeAdInfo bean, int type) {
        if (bean == null) return;
        //刚版本，后期优化
        try {
            TextView ad_dec = findViewById(R.id.ad_dec);
            ImageView logoImageView = findViewById(R.id.logoImageView);
            ad_dec.setVisibility(View.GONE);
            logoImageView.setVisibility(View.GONE);
            if (bean.getOrigin() instanceof TTFeedAd) {
                TTFeedAd ttBean = (TTFeedAd) bean.getOrigin();

                ad_dec.setVisibility(View.VISIBLE);
                logoImageView.setVisibility(View.VISIBLE);

                ad_dec.setText(""+ttBean.getDescription());
                logoImageView.setImageBitmap(ttBean.getAdLogo());
            }
        }catch (Exception e){
        }
        //end
        if(bean.getOrigin() instanceof View){
            ad_view_card_001.setVisibility(View.GONE);
            ad_view_card_002.setVisibility(View.GONE);
            addView.setVisibility(View.VISIBLE);
            View view = (View) bean.getOrigin();
            addView.removeAllViews();
            addView.addView(view);
            return;
        }else{
            ad_view_card_001.setVisibility(View.VISIBLE);
            ad_view_card_002.setVisibility(View.VISIBLE);
            addView.setVisibility(View.GONE);
        }

        //旧版广告布局
        if (!TextUtils.isEmpty(bean.getTitle())) {
            mTextView.setText(bean.getTitle());
            ad_title_002.setText(bean.getTitle());
            mTextView.setVisibility(View.VISIBLE);
            ad_title_002.setVisibility(View.VISIBLE);
        } else {
            mTextView.setText("这里是一条广告");
            mTextView.setVisibility(View.GONE);
            ad_title_002.setVisibility(View.GONE);
        }
        if (type != Constants.TYPE_NEW) {
            setAdImageView(bean.getImageUrl(), bean.getTemplateType(), bean);
        } else {
            if (null != bean.getImageUrls() && bean.getImageUrls().size() >= 3) {
                ad_view_card_001.setVisibility(View.VISIBLE);
                ad_view_card_002.setVisibility(View.GONE);
                mTextView.setMaxLines(1);
                mLayoutMutilPic.setVisibility(View.VISIBLE);
                mAdImageView.setVisibility(View.GONE);
                findViewById(R.id.rightImgView).setVisibility(View.GONE);
                if (bean.getImageUrls().size() >= 3) {
                    GlideUtils.displayImageNice(getContext(), bean.getImageUrls().get(0), mAdImageViewOne, this, bean);
                    GlideUtils.displayImage(getContext(), bean.getImageUrls().get(1), mAdImageViewTwo);
                    GlideUtils.displayImage(getContext(), bean.getImageUrls().get(2), mAdImageViewThree);
                }
            } else {
                setAdImageView(bean.getImageUrl(), bean.getTemplateType(), bean);
            }
        }

        switch (AdZhiZuNative.getRedStateForOtherAd(bean.getAdsRuleBean())) {
            case 1://红包
                mAdLayout.setVisibility(View.VISIBLE);
                mIvAdText.setVisibility(View.GONE);
                ad_one_text_002.setVisibility(View.GONE);
                mIvAdText1.setBackgroundResource(R.mipmap.ad_red);
                break;
            case 2://金包
                mAdLayout.setVisibility(View.VISIBLE);
                mIvAdText.setVisibility(View.GONE);
                ad_one_text_002.setVisibility(View.GONE);
                mIvAdText1.setBackgroundResource(R.mipmap.ad_yellow);
                break;
            case 0:
            default:
                mAdLayout.setVisibility(View.GONE);
                mIvAdText.setVisibility(View.VISIBLE);
                ad_one_text_002.setVisibility(View.VISIBLE);
                break;
        }
    }

    void setAdImageView(String imageUrl, ZKAdTypeEnum templateType, NativeAdInfo bean) {
        if (TextUtils.isEmpty(imageUrl)) return;
        mLayoutMutilPic.setVisibility(View.GONE);

        if (templateType == ZKAdTypeEnum.FLOW_BIG_IMG) {
            ad_view_card_001.setVisibility(View.GONE);
            ad_view_card_002.setVisibility(View.VISIBLE);
            // 大图，特别设置
            ad_title_002.setMaxLines(1);
            ad_imageview_002.setVisibility(View.VISIBLE);
            GlideUtils.displayImageNice(getContext(), imageUrl, ad_imageview_002, this, bean);
        } else {
            ad_view_card_001.setVisibility(View.VISIBLE);
            ad_view_card_002.setVisibility(View.GONE);
            mTextView.setMaxLines(2);
            mAdImageView.setVisibility(View.VISIBLE);
            findViewById(R.id.rightImgView).setVisibility(View.VISIBLE);
            GlideUtils.displayImageNice(getContext(), imageUrl, mAdImageView, this, bean);
        }
    }
}
