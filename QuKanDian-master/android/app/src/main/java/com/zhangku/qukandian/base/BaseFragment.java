package com.zhangku.qukandian.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.dialog.DialogPrograss;
import com.zhangku.qukandian.dialog.DlgLoading;
import com.zhangku.qukandian.observer.NetOberver;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.LoadingLayout;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public abstract class BaseFragment extends Fragment implements NetOberver.OnNetTypeChangeListener {

    protected DlgLoading mDlgLoading;
    protected LoadingLayout mLoadingLayout;
    protected Activity mParent;
    protected DialogPrograss mDialogPrograss;

    //用于延迟加载数据
    private boolean mHasShowed = false;
    private boolean mHasCreateView = false;
    public MyHandler mHandler;

    public boolean mIsAllowedPreload = true;

    private View mRootView;
    public boolean isDestroy = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = getActivity();
        mHandler = new MyHandler(this);
        mDialogPrograss = new DialogPrograss(getContext());
        NetOberver.getInstance().addNetTypeChangeListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = inflater.inflate(getLayoutRes(), container, false);
            initTitleBar(mRootView);
            initViews(mRootView);

            if (mAction != null) {
                mAction.onCreateViewSuccess(mRootView);
            }
            addOtherViewToLayout(mRootView);
            if (!mHasCreateView) {
                mHasCreateView = true;
                if (mHasCreateView && mHasShowed) {
                    loadData(mParent);
                }
            }
        }
        NetOberver.getInstance().notityNetChange(CommonHelper.isNetworkAvailable(getContext()), CommonHelper.isWifi(getContext()));
        LogUtils.LogI("ClassName ==" + getClass().getName());
        return mRootView;
    }

    @Override
    public void onNetChange(boolean isAvailable, boolean isWifi) {
        if (!isAvailable && !isWifi) {
            noNetword();
            ToastUtils.showLongToast(getContext(), "网络异常,请检查您的网络设置");
        }
    }

    protected abstract void noNetword();

    protected static class MyHandler extends Handler {

        private WeakReference<BaseFragment> wr;

        public MyHandler(BaseFragment activity) {
            wr = new WeakReference<BaseFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (wr != null && wr.get() != null) {
                wr.get().consumeMessage(msg);
            }
        }

    }

    protected void initTitleBar(View convertView) {

    }

    protected void addOtherViewToLayout(View convertView) {

    }

    public void lazyLoadData(Context context) {
        if (!mHasShowed) {
            mHasShowed = true;
            if (mHasCreateView && mHasShowed) {
                loadData(context);
            }
        }
    }

    public void setHasShowed(boolean mHasShowed) {
        this.mHasShowed = mHasShowed;
    }

    public abstract void loadData(Context context);

    protected abstract int getLayoutRes();

    protected abstract void initViews(View convertView);

    public void addLoadingView(View convertView, int resId) {
        mLoadingLayout = (LoadingLayout) LayoutInflater.from(mParent).inflate(R.layout.layout_loading, (ViewGroup) convertView, false);
        FrameLayout contentLayout = (FrameLayout) convertView.findViewById(resId);
        contentLayout.addView(mLoadingLayout);
        mLoadingLayout.setOnLoadingAction(new LoadingLayout.OnLoadingAction() {

            @Override
            public void onLoadingFail() {
                onLoadingFailClick();
            }

            @Override
            public void onNetworkFail() {
                onLoadNotNetwordClick();
            }
        });
    }


    protected boolean consumeMessage(Message msg) {
        return false;
    }

    protected void showLoading() {
        if (null != mLoadingLayout) {
            mLoadingLayout.showLoading();
        }
    }

    protected void hideLoadingLayout() {
        if (mLoadingLayout != null) {
            mLoadingLayout.hideLoadingLayout();
        }
    }

    protected void showEmptySearch(String text) {
        if (mLoadingLayout != null) {
            mLoadingLayout.showEmptySearch(text);
        }
    }

    protected void showEmptySearch() {
        if (mLoadingLayout != null) {
            mLoadingLayout.showEmptySearch();
        }
    }

    protected void showEmptyNetword() {
        if (mLoadingLayout != null) {
            mLoadingLayout.showEmptyNetword();
        }
    }

    protected void showEmptyCollect() {
        if (mLoadingLayout != null) {
            mLoadingLayout.showEmptyCollect();
        }
    }

    protected void showEmptyMessage() {
        if (mLoadingLayout != null) {
            mLoadingLayout.showEmptyMessage();
        }
    }

    protected void setBackgroundResource(int resid) {
        if (mLoadingLayout != null) {
            mLoadingLayout.setBackgroundResource(resid);
        }
    }

    protected void showNodata(String text) {
        if (mLoadingLayout != null) {
            mLoadingLayout.showNodata(text, 0);
        }
    }

    protected void showNodata(int marginTop, int bgRes, String text) {
        if (mLoadingLayout != null) {
            mLoadingLayout.showNodata(marginTop, bgRes, text, 0);
        }
    }

    protected void addMoreViewBelowNoData(View view) {
        if (mLoadingLayout != null) {
            mLoadingLayout.addMoreViewBelowNoData(view);
        }
    }

    protected void addMoreViewBelowLoadFail(View view) {
        if (mLoadingLayout != null) {
            mLoadingLayout.addMoreViewBelowLoadFail(view);
        }
    }

    protected void showLoadFail() {
        if (null != mLoadingLayout) {
            mLoadingLayout.showLoadFail();
        }
    }

    protected boolean isShowNoData() {
        return mLoadingLayout.isShowNoData();
    }

    public boolean isHasShowed() {
        return mHasShowed;
    }

    public boolean isHasCreateView() {
        return mHasCreateView;
    }

    protected void onLoadingFailClick() {

    }

    protected void onLoadNotNetwordClick() {
        showLoading();
    }

    @Override
    public void onResume() {
        super.onResume();
        isDestroy = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoadData(getContext());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    public abstract String setPagerName();

    @Override
    public void onPause() {
        super.onPause();
    }

    void showDlgloading(String text) {
        if (mDlgLoading == null) {
            mDlgLoading = DlgLoading.createDlg(mParent, text);
            mDlgLoading.showDlg();
            postRequest();
        }
    }

    void postRequest() {

    }

    void closeDlgloading() {
        if (mDlgLoading != null) {
            mDlgLoading.closeDlg();
            mDlgLoading = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroy = true;
        NetOberver.getInstance().removeNetTypeChangeListener(this);
        releaseRes();
        closeDlgloading();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.removeAllViews();
            mLoadingLayout = null;
        }
        mParent = null;
    }


    protected void releaseRes() {
        try {
            Field field = getChildFragmentManager().getClass().getDeclaredField("mAdded");
            field.setAccessible(true);
            Object object = field.get(getChildFragmentManager());
            if (object != null) {
                object.getClass().getMethod("clear").invoke(object);
                object = null;
            }
        } catch (Exception e) {
        }
    }

    private BaseFragmentAction mAction;

    public void setBaseFragmentAction(BaseFragmentAction mAction) {
        this.mAction = mAction;
    }

    public interface BaseFragmentAction {

        void onCreateViewSuccess(View view);
    }


}
