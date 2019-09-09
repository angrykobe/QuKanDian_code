package com.zhangku.qukandian.BaseNew;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhangku.qukandian.dialog.DialogPrograss;
import com.zhangku.qukandian.utils.LogUtils;

import java.lang.reflect.Field;

public abstract class BaseFra extends Fragment{

    protected Activity mActivity;
    protected Context mContext;
    protected DialogPrograss mDialogPrograss;
    private View mRootView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getContext();
        mDialogPrograss = new DialogPrograss(getContext());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = inflater.inflate(getLayoutRes(), container, false);
            initViews(mRootView);
            loadData(mActivity);
        }
        LogUtils.LogI("ClassName ==" + getClass().getName());
        return mRootView;
    }
    //数据加载
    protected abstract void loadData(Context context);
    //
    protected abstract int getLayoutRes();
    //
    protected abstract void initViews(View convertView);

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        releaseRes();
    }


//    protected void releaseRes() {
//        try {
//            Field field = getChildFragmentManager().getClass().getDeclaredField("mAdded");
//            field.setAccessible(true);
//            Object object = field.get(getChildFragmentManager());
//            if (object != null) {
//                object.getClass().getMethod("clear").invoke(object);
//                object = null;
//            }
//        } catch (Exception e) {
//        }
//    }

}
