package com.zhangku.qukandian.base;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.widght.ActionBarLayout;

import static com.zhangku.qukandian.R.id.layout_title_back;

@Deprecated
public abstract class BaseTitleActivity extends BaseActivity {

    protected boolean toShowBackIcon() {
        return true;
    }

    @Override
    protected void attachView() {
        initActionBarView();
        initActionBarData();
        addOtherView(mFrameLayout);
    }

    protected void addOtherView(ViewGroup viewGroup) {
    }

    private void initActionBarView() {
        mActionBarLayout.setOnBackListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackAction();
            }
        });
        mActionBarLayout.setOnActionBarMenuAction(new ActionBarLayout.OnActionBarMenuAction() {

            @Override
            public void onMenuAction(int menuId) {
                actionMenuOnClick(menuId);
            }
        });
    }

    public void hideBackBtn() {
        if (mActionBarLayout != null) {
            mActionBarLayout.findViewById(layout_title_back).setVisibility(View.GONE);
        }
    }

    public void setActionBarLayoutColor(int color) {
        if (mActionBarLayout != null) {
            mActionBarLayout.findViewById(R.id.layout_title).setBackgroundResource(color);
        }
    }

    /**
     * 配置头部数据
     */
    protected abstract void initActionBarData();

    @Override
    public void setTitle(int titleId) {
        mActionBarLayout.setTitle(titleId);
        if (toShowBackIcon()) {
            mActionBarLayout.showBackIcon(View.VISIBLE);
        } else {
            mActionBarLayout.showBackIcon(View.GONE);
        }
    }

    public void setTitleColors(int color) {
        mActionBarLayout.setTitleColor(color);
    }

    @Override
    public void setTitle(CharSequence title) {
        mActionBarLayout.setTitle(title);
        if (toShowBackIcon()) {
            mActionBarLayout.showBackIcon(View.VISIBLE);
        } else {
            mActionBarLayout.showBackIcon(View.GONE);
        }
    }

    /**
     * 头部返回键事�?
     */
    protected void onBackAction() {
        finish();
    }

    protected void addMenuItem(int... resId) {
        mActionBarLayout.addMenu(resId);
    }

    protected View addMenuItem(int resId) {
        mActionBarLayout.addMenu(resId);
        return null;
    }

    protected void addMenuItem(int viewId, View view) {
        mActionBarLayout.addMenu(viewId, view);
    }

    protected void updateMenuItem(int dstId, int resId) {
        mActionBarLayout.updateMenuItem(dstId, resId);
    }

    protected void removeMenuItem(int... resId) {
        if (mActionBarLayout == null) return;
        mActionBarLayout.removeMenuItem(resId);
    }


    protected void actionMenuOnClick(int menuId) {

    }


    @Override
    protected void releaseRes() {
        if (mActionBarLayout != null) {
            mActionBarLayout.removeAllViews();
            mActionBarLayout = null;
        }
        super.releaseRes();
    }
}
