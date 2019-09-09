package com.zhangku.qukandian.fragment.Video;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.androidkun.xtablayout.XTabLayout;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.SearchActivity;
import com.zhangku.qukandian.adapter.VideoTabAdapter;
import com.zhangku.qukandian.base.BaseFragment;
import com.zhangku.qukandian.bean.ChannelBean;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnSharedListener;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetInformationChannelTabProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/3/24.
 */

public class VideoFragment extends BaseFragment {
    private int mCurrentTab = 0;
    private XTabLayout mTabLayout;
    private ViewPager mViewPager;
    private VideoTabAdapter mVideoTabAdapter;
    private ImageView mIvSearchBtn;

    private ArrayList<String> mTabNames = new ArrayList<>();
    private ArrayList<VideoInsideFragment> mFragments = new ArrayList<>();
    private ArrayList<ChannelBean> mTabInfors = new ArrayList<>();
    private GetInformationChannelTabProtocol mGetInformationChannelTabProtocol;
    private boolean isLoaded = false;
//    private OnSharedListener mOnSharedListener;
    private FragmentManager cfm;

    @Override
    public void onResume() {
        super.onResume();
        Map<String,String> map = new ArrayMap<>();
        map.put("to","视频页");
        MobclickAgent.onEvent(getContext(), "AllPv",map);
    }

    @Override
    protected void noNetword() {

    }

    @Override
    public void loadData(Context context) {
        List<ChannelBean> list = DBTools.queryByChannel(Constants.TYPE_VIDEO);
        if(mViewPager != null && cfm != null && list != null && list.size() > 0){
            ChannelBean bean = new ChannelBean();
            mTabInfors.add(bean);
            mTabInfors.addAll(list);
            setView();
            isLoaded = true;
            hideLoadingLayout();
        }else {
            mGetInformationChannelTabProtocol.postRequest();
        }
    }

    @Override
    protected void onLoadingFailClick() {
        super.onLoadingFailClick();
        showLoading();
        loadData(getContext());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_video_layout;
    }

    @Override
    protected void initViews(View convertView) {

        cfm = getChildFragmentManager();

        addLoadingView(convertView, R.id.fragment_video_loading);
        final int versionCode = AppUtils.getVersionCode(getActivity());
        mGetInformationChannelTabProtocol = new GetInformationChannelTabProtocol(getContext(),Constants.TYPE_VIDEO,versionCode, new BaseModel.OnResultListener<ArrayList<ChannelBean>>() {
            @Override
            public void onResultListener(ArrayList<ChannelBean> response) {
                if (response.size() > 0) {
                    mTabInfors.clear();
                    ChannelBean bean = new ChannelBean();
                    mTabInfors.add(bean);
                    mTabInfors.addAll(response);
                    DBTools.addToChannels(response,Constants.TYPE_VIDEO);
                    setView();
                    isLoaded = true;
                    hideLoadingLayout();
                } else {
                    showEmptySearch("暂无数据");
                }

            }

            @Override
            public void onFailureListener(int code,String error) {
                showLoadFail();
            }
        });
        mIvSearchBtn = (ImageView) convertView.findViewById(R.id.fragment_video_search);
        mTabLayout = (XTabLayout) convertView.findViewById(R.id.fragment_video_tablayout);
        mViewPager = (ViewPager) convertView.findViewById(R.id.fragment_video_viewpager);
        mVideoTabAdapter = new VideoTabAdapter(cfm, mFragments, mTabNames);
        mViewPager.setAdapter(mVideoTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentTab = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mIvSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startToSearchActivity(getContext(), "", SearchActivity.FROM_VIDEO);
            }
        });
    }

    private void setView() {
        clearFragments();
        mTabNames.clear();
        mFragments.clear();
        for (int i = 0; i < mTabInfors.size(); i++) {
            VideoInsideFragment fragment = new VideoInsideFragment();
//            fragment.setOnSharedListener(this);
            if (i == 0) {
                fragment.setChannelId(0);
                mTabNames.add("推荐");
            } else {
                fragment.setChannelId(mTabInfors.get(i).getChannelId());
                mTabNames.add(mTabInfors.get(i).getDisplayName().trim());
            }
            mFragments.add(fragment);
        }
        mVideoTabAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(mCurrentTab);
        hideLoadingLayout();
    }

    @Override
    public String setPagerName() {
        return "视频";
    }

    public void refresh() {
        if (isLoaded) {
            if (mFragments.size() > 0) {
                mFragments.get(mCurrentTab).loadMore(true);
            }
        }
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        clearFragments();
        mIvSearchBtn = null;
        mTabNames.clear();
        mTabNames = null;
        mFragments.clear();
        mFragments = null;
        mTabInfors.clear();
        mTabInfors = null;
        mTabLayout = null;
        mViewPager = null;
        mVideoTabAdapter = null;
        mGetInformationChannelTabProtocol = null;
    }

//    @Override
//    public void onSharedListener(DetailsBean mDetailsBean, SHARE_MEDIA share_media) {
//        if (null != mOnSharedListener) {
//            mOnSharedListener.onSharedListener(mDetailsBean, share_media);
//        }
//    }
//
//    public void setOnSharedListener(OnSharedListener onSharedListener) {
//        mOnSharedListener = onSharedListener;
//    }

    private void clearLayout() {
        clearFragments();
        mFragments = new ArrayList<>();
        mTabNames = new ArrayList<>();
        mTabInfors = new ArrayList<>();
    }

    private void clearFragments() {
        if (cfm != null && mFragments != null && mFragments.size() > 0) {
            FragmentTransaction transaction = cfm.beginTransaction();
            for (Fragment mFragment : mFragments) {
                transaction.remove(mFragment);
            }
            transaction.commitAllowingStateLoss();
        }
    }

}
