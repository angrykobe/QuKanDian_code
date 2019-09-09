package com.zhangku.qukandian.widght;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseLoadingActivity;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.ToastUtils;


public class LoadingLayout extends FrameLayout {

    private View mLoadingView;
    private View mLoadFialView;
    private View mNodataView;
    private View mShowView;
    private LinearLayout mEmptySearch;
    private LinearLayout mEmptyCollect;
    private LinearLayout mEmptyMessage;
    private LinearLayout mEmptyNetword;
    private LinearLayout mNoDataMoreParent;
    private LinearLayout mLoadFailMoreParent;
    private TextView mEmptySearchText;
    private TextView mEmptyCollectText;

    private ImageView mIvLoadingImg;
    private AnimationDrawable mAnimationDrawable;

    private boolean mNoDataMoreViewAdded = false;
    private boolean mLoadFailMoreViewAdded = false;

    private OnLoadingAction mAction;

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        ExitActivityObserver.getInst().addExitActivityObserverAction(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView(this);
    }

    private void initView(View view) {
        mIvLoadingImg = (ImageView) view.findViewById(R.id.loading_layout_loading_img);
        mEmptyCollect = (LinearLayout) view.findViewById(R.id.empty_collect);
        mEmptyMessage = (LinearLayout) view.findViewById(R.id.empty_message);
        mEmptyNetword = (LinearLayout) view.findViewById(R.id.empty_netword);
        mEmptySearch = (LinearLayout) view.findViewById(R.id.empty_search);
        mEmptySearchText = (TextView) view.findViewById(R.id.empty_search_text);
        mEmptyCollectText = (TextView) view.findViewById(R.id.empty_collect_text);
        mLoadingView = view.findViewById(R.id.loading_layout_loading);
        mLoadFialView = view.findViewById(R.id.loading_layout_fail);
        mNodataView = view.findViewById(R.id.loading_layout_nodata);
        mNoDataMoreParent = (LinearLayout) view.findViewById(R.id.loading_layout_nodata_more);
        mLoadFailMoreParent = (LinearLayout) view.findViewById(R.id.loading_layout_fail_more);
        mShowView = mLoadingView;

        mIvLoadingImg.setBackgroundResource(R.drawable.load_anim);
        mAnimationDrawable = (AnimationDrawable) mIvLoadingImg.getBackground();
        showLoading();
    }


    @Override
    public void setBackgroundResource(int resid) {
        if (mLoadingView != null) {
            mLoadingView.setBackgroundResource(resid);
        }
        if (mLoadFialView != null) {
            mLoadFialView.setBackgroundResource(resid);
        }
        if (mNodataView != null) {
            mNodataView.setBackgroundResource(resid);
        }
    }

    public void hideLoadingLayout() {
        if (mShowView != null) {
            mShowView.setVisibility(View.GONE);
            mEmptyNetword.setVisibility(View.GONE);
            mEmptySearch.setVisibility(View.GONE);
            mEmptyCollect.setVisibility(View.GONE);
            mEmptyMessage.setVisibility(View.GONE);
            this.setVisibility(View.GONE);
            mAnimationDrawable.stop();
        }
    }

    public void showEmptySearch(String text) {
        setVisibility(View.VISIBLE);
        mEmptySearch.setVisibility(View.VISIBLE);
        mEmptyNetword.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) {
            mEmptySearchText.setText(text);
        }
    }

    public void showEmptySearch() {
        setVisibility(View.VISIBLE);
        mEmptyNetword.setVisibility(View.GONE);
        mEmptySearch.setVisibility(View.VISIBLE);

    }

    public void showEmptyCollect() {
        setVisibility(View.VISIBLE);
        mEmptyNetword.setVisibility(View.GONE);
        mEmptyCollect.setVisibility(View.VISIBLE);
    }

    public void showEmptyCollect(String text) {
        setVisibility(View.VISIBLE);
        mEmptyNetword.setVisibility(View.GONE);
        mEmptyCollect.setVisibility(View.VISIBLE);
        mEmptyCollectText.setText(text);
    }

    public void showEmptyMessage() {
        setVisibility(View.VISIBLE);
        mEmptyNetword.setVisibility(View.GONE);
        mEmptyMessage.setVisibility(View.VISIBLE);
    }

    public void showEmptyNetword() {
        setVisibility(View.VISIBLE);
        mEmptyNetword.setVisibility(View.VISIBLE);
        mEmptyNetword.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mAction) {
                    ToastUtils.showShortToast(getContext(),"尝试中");
                    mAction.onNetworkFail();
                }
            }
        });
    }

    public void showLoading() {
        setVisibility(View.VISIBLE);
        if (!mShowView.equals(mLoadingView)) {
            mShowView.setVisibility(View.GONE);
        }
        mLoadingView.setVisibility(View.VISIBLE);

        mAnimationDrawable.start();
        mShowView = mLoadingView;
        mLoadingView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
    }

    public void showLoadingMarginTop(int marginTop) {
        LayoutParams params = (LayoutParams) mLoadingView.getLayoutParams();
        params.topMargin = marginTop;
        mLoadingView.setLayoutParams(params);
        showLoading();
    }

    /**
     * @param text
     * @param drawTopId 传0表示使用默认图标，非0表示使用新图标
     */
    public void showNodata(String text, int drawTopId) {
        setVisibility(View.VISIBLE);
        if (!mShowView.equals(mNodataView)) {
            mShowView.setVisibility(View.GONE);
        }

        mNodataView.setVisibility(View.VISIBLE);
        TextView nodataTv = (TextView) mNodataView.findViewById(R.id.loading_layout_nodata_tv);
        nodataTv.setText(text);
        if (drawTopId > 0) {
            Drawable drawable = getResources().getDrawable(drawTopId);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                nodataTv.setCompoundDrawables(null, drawable, null, null);
            }
        }
        mShowView = mNodataView;
        mNodataView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });
    }

    public void showNodata(String text) {
        showNodata(text, 0);
    }

    public void hideNoData() {
        if (mShowView != null) {
            mShowView.setVisibility(View.GONE);
        }
    }

    /**
     * @param marginTop
     * @param bgRes
     * @param text
     * @param drawTopId 传0则表示用默认图标，不传0则表示用心、新图标
     */
    public void showNodata(int marginTop, int bgRes, String text, int drawTopId) {
        setVisibility(View.VISIBLE);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, marginTop, 0, 0);
        mNodataView.setLayoutParams(layoutParams);
        if (bgRes > 0) {
            mNodataView.setBackgroundResource(bgRes);
        }
        showNodata(text, drawTopId);
    }

    public void showLoadFail() {
        setVisibility(View.VISIBLE);
        if (mShowView.equals(mLoadFialView)) return;
        mShowView.setVisibility(View.VISIBLE);

        mLoadingView.setVisibility(View.GONE);
        mLoadFialView.setVisibility(View.VISIBLE);
        mShowView = mLoadFialView;
        mShowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mAction != null) {
                    mAction.onLoadingFail();
                }
            }
        });
    }

    /**
     * 在没有数据时添加附加视图，比如添加跳转按钮
     *
     * @param view
     */
    public void addMoreViewBelowNoData(View view) {
        if (view != null && !mNoDataMoreViewAdded) {
            mNoDataMoreViewAdded = true;
            mNoDataMoreParent.setVisibility(View.VISIBLE);
            mNoDataMoreParent.addView(view);
        }
    }

    /**
     * 在数据加载错误时添加附加视图，比如添加跳转按钮
     *
     * @param view
     */
    public void addMoreViewBelowLoadFail(View view) {
        if (view != null && !mLoadFailMoreViewAdded) {
            mLoadFailMoreViewAdded = true;
            mLoadFailMoreParent.setVisibility(View.VISIBLE);
            mLoadFailMoreParent.addView(view);
        }
    }

    public void showLoadFailMarginTop(int marginTop) {
        LayoutParams params = (LayoutParams) mLoadFialView.getLayoutParams();
        params.topMargin = marginTop;
        mLoadFialView.setLayoutParams(params);
        showLoadFail();
    }


    public void setOnLoadingAction(OnLoadingAction mAction) {
        this.mAction = mAction;
    }

    public interface OnLoadingAction {
        void onLoadingFail();

        void onNetworkFail();
    }

    public boolean isShowNoData() {
        return mNodataView.equals(mShowView);
    }

//    @Override
//    public void onActivityDestory() {
//        mAction = null;
//        mLoadingView = null;
//        mLoadFialView = null;
//        mNodataView = null;
////		mNetworkErrorView 	= null;
//        mShowView = null;
//        if (mNoDataMoreParent != null) {
//            mNoDataMoreParent.removeAllViews();
//            mNoDataMoreParent = null;
//        }
//        if (mLoadFailMoreParent != null) {
//            mLoadFailMoreParent.removeAllViews();
//            mLoadFailMoreParent = null;
//        }
//    }

    public void attachToActivity(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        View contentView = viewGroup.getChildAt(0);
        viewGroup.removeView(contentView);
        addView(contentView, 0);
        viewGroup.addView(this);
    }

    public void attachView(View view, int childId) {
        try {
            View contentView = view.findViewById(childId);
            ViewGroup viewGroup = (ViewGroup) contentView.getParent();
            if (viewGroup == null) {
                throw new Exception("the view must has a parent");
            }
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                if (child.getId() == childId) {
                    viewGroup.removeView(contentView);
                    viewGroup.addView(this, i, contentView.getLayoutParams());
                    break;
                }
            }
            addView(contentView, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
