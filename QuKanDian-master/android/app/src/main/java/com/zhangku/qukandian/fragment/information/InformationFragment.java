package com.zhangku.qukandian.fragment.information;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.yilan.sdk.ui.littlevideo.KSLittleVideoFragment;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.SearchActivity;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.adapter.InformationTabAdapter;
import com.zhangku.qukandian.base.BaseFragment;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.ChannelBean;
import com.zhangku.qukandian.bean.QukandianBean;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.SougouCacheBean;
import com.zhangku.qukandian.bean.SougouHotwordBean;
import com.zhangku.qukandian.bean.TaskChestBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.bean.WithdrawalsRemindBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.fragment.SougouFragment;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.ChannelChangerObserver;
import com.zhangku.qukandian.observer.PingObserver;
import com.zhangku.qukandian.protocol.GetInfoSougouProtocol;
import com.zhangku.qukandian.protocol.GetInformationChannelTabProtocol;
import com.zhangku.qukandian.protocol.QukandianNewProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.CacheManage;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.widght.ChaiBaoxiangLayout;
import com.zhangku.qukandian.widght.CustomViewPager;
import com.zhangku.qukandian.widght.MyTabLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ikidou.reflect.TypeToken;

/**
 * Created by yuzuoning on 2017/3/24.
 */

public class InformationFragment extends BaseFragment implements ChannelChangerObserver.IChannelChangerListener,
        View.OnClickListener, UserManager.IOnLoginStatusLisnter,
        PingObserver.OnPintSuccessListener, UserManager.IOnGoldChangeListener {
    private int mCurrentTab = 0;
    private MyTabLayout mTabLayout;
    private CustomViewPager mViewPager;
    private InformationTabAdapter mInformationTabAdapter;
    private ImageView mIvInformationEdit;

    private ImageView mIvSearchCountIcon;
    private TextView mTvSearchNumber;
    //
    private ImageView mIvAdCountIcon;
    private TextView mTvAdsNumber;
    private ChaiBaoxiangLayout mChaiBaoxiangLayout;

    private ArrayList<String> mTabNames = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private HashMap<String, Fragment> mFragmentsMap = new HashMap<>();
    private ArrayList<ChannelBean> mTabInfors = new ArrayList<>();
    private GetInformationChannelTabProtocol mGetInformationChannelTabProtocol;
    private boolean isLoaded = false;
    private int isAddOne = 0;
    private FragmentManager cfm;

    @Override
    public void onResume() {
        super.onResume();
        Map<String, String> map = new ArrayMap<>();
        map.put("to", "首页");
        MobclickAgent.onEvent(getContext(), "AllPv", map);
        getIconShow();
//        if (isLoaded) {
//            refreshAdIcon();
//        }
    }

    @Override
    protected void noNetword() {

    }

    @Override
    public void loadData(Context context) {
        List<ChannelBean> list = DBTools.queryByChannel(Constants.TYPE_NEW);
        if (mViewPager != null && cfm != null && list != null && list.size() != 0) {
            clearLayout();
            ChannelBean channelBean = new ChannelBean();
            mTabInfors.add(channelBean);
            boolean isActivity = false;
            if (!UserManager.SOUGOU_SEARCH_GIFT) {
                isActivity = true;
            } else {
                if (UserManager.getInst().hadLogin()) {
                    if (UserManager.getInst().getUserBeam().getMissionGrarntedUsers() != null) {
                        for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                            if (Constants.SEARCH_SOUGOU.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                                isActivity = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                            }
                        }
                    }
                }
            }
            if (UserManager.getInst().getmRuleBean().getSougouGoldRule().isIsEnable()) {
                if (UserManager.SOUGOU_SEARCH_GIFT) {
                    if ((isActivity && UserManager.getInst().getmRuleBean().getSougouGoldRule().isIsEnable()) && UserManager.getInst().hadLogin()) {
                        ChannelBean channelBean1 = new ChannelBean();
                        mTabInfors.add(channelBean1);
                    }
                } else {
                    if (UserManager.getInst().hadLogin()) {
                        ChannelBean channelBean1 = new ChannelBean();
                        mTabInfors.add(channelBean1);
                    }
                }
            }

            for (ChannelBean bean : list) {
                if (Constants.HIGH_PRICE_NAME.equals(bean.getName())) {//高价文id
                    if (UserBean.getHighPriceNews()) {
                        mTabInfors.add(bean);
                    }
                } else if (bean.isYesNo()) {
                    mTabInfors.add(bean);
                }
            }
            setView();
            isLoaded = true;
        } else {
            mGetInformationChannelTabProtocol.postRequest();
        }
    }

    private void initData() {
        loadData(getContext());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_information_layout;
    }

    @Override
    protected void initViews(View convertView) {

        cfm = getChildFragmentManager();

        addLoadingView(convertView, R.id.information_framgent_loading);

        mChaiBaoxiangLayout = convertView.findViewById(R.id.chai_hongbao_widght);
        mIvInformationEdit = (ImageView) convertView.findViewById(R.id.fragment_information_edit);
        mTabLayout = convertView.findViewById(R.id.fragment_information_tablayout);
        mViewPager = (CustomViewPager) convertView.findViewById(R.id.fragment_information_viewpager);

        mIvInformationEdit.setOnClickListener(this);
        mChaiBaoxiangLayout.getmSearch().setOnClickListener(this);

        int versionCode = AppUtils.getVersionCode(getActivity());
        mGetInformationChannelTabProtocol = new GetInformationChannelTabProtocol(getContext(), Constants.TYPE_NEW, versionCode,
                new BaseModel.OnResultListener<ArrayList<ChannelBean>>() {
                    @Override
                    public void onResultListener(ArrayList<ChannelBean> response) {
                        if (null != response) {
                            isLoaded = true;
                            clearLayout();
                            ChannelBean channelBean = new ChannelBean();
                            mTabInfors.add(channelBean);
                            boolean isActivity = false;
                            if (!UserManager.SOUGOU_SEARCH_GIFT) {
                                isActivity = true;
                            } else {
                                if (UserManager.getInst().hadLogin()) {
                                    if (UserManager.getInst().getUserBeam().getMissionGrarntedUsers() != null) {
                                        for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                                            if (Constants.SEARCH_SOUGOU.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                                                isActivity = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                                            }
                                        }
                                    }
                                }
                            }
                            if (UserManager.getInst().getmRuleBean().getSougouGoldRule().isIsEnable()) {
                                if (UserManager.SOUGOU_SEARCH_GIFT) {
                                    if ((isActivity && UserManager.getInst().getmRuleBean().getSougouGoldRule().isIsEnable()) && UserManager.getInst().hadLogin()) {
                                        ChannelBean channelBean1 = new ChannelBean();
                                        mTabInfors.add(channelBean1);
                                    }
                                } else {
                                    if (UserManager.getInst().hadLogin()) {
                                        ChannelBean channelBean1 = new ChannelBean();
                                        mTabInfors.add(channelBean1);
                                    }
                                }
                            }
                            DBTools.addToChannels(response, Constants.TYPE_NEW);

                            mTabInfors.addAll(response);
                            for (ChannelBean bean : mTabInfors) {
                                if (Constants.HIGH_PRICE_NAME.equals(bean.getName()) && !UserBean.getHighPriceNews()) {//高价文id
                                    mTabInfors.remove(bean);
                                    break;
                                }
                            }
                            setView();
                            hideLoadingLayout();
                        }
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        showLoadFail();
                    }

                });

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

        ChannelChangerObserver.getInstance().addChannerListener(this);
        UserManager.getInst().addLoginListener(this);
        UserManager.getInst().addGoldListener(this);
        PingObserver.getInstance().addOOnPintSuccessListener(this);
    }


    @Override
    protected void onLoadingFailClick() {
        super.onLoadingFailClick();
        showLoading();
        initData();
    }

    private void setView() {
        boolean isActivity = false;
        if (!UserManager.SOUGOU_SEARCH_GIFT) {
            isActivity = true;
        } else {
            if (UserManager.getInst().hadLogin()) {
                if (UserManager.getInst().getUserBeam().getMissionGrarntedUsers() != null) {
                    for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                        if (Constants.SEARCH_SOUGOU.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                            isActivity = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                        }
                    }
                }
            }
        }

        for (int i = 0; i < mTabInfors.size(); i++) {
            InformationInSideFragment fragment = new InformationInSideFragment();
            if (i == 0) {
                fragment.setChannelId(0, "推荐");
                mTabNames.add("推荐");
                mFragments.add(fragment);
            }
            //2.9.6.3 改版：隐藏热搜到 右上角
//            else if (i == 1) {

//            }
            else {
                if (Constants.NOVEL_NAME.equals(mTabInfors.get(i).getName())) {//小说频道
                    mTabNames.add(mTabInfors.get(i).getDisplayName());
                    mFragments.add(new NovelFragment());
                } else if (Constants.NOVEL_YUEMENG.equals(mTabInfors.get(i).getName())) {//阅盟小说频道
                    mTabNames.add(mTabInfors.get(i).getDisplayName());
                    mFragments.add(new NovelYueMengFragment());
                } else if (Constants.HAOTU_VIDEO.equals(mTabInfors.get(i).getName())) {//好兔视频
                    mTabNames.add(mTabInfors.get(i).getDisplayName());
                    mFragments.add(new HaoTuVideoFragment());
                } else if (mTabInfors.get(i).getDisplayName() != null) {
                    fragment.setChannelId(mTabInfors.get(i).getChannelId(), mTabInfors.get(i).getDisplayName());
                    mTabNames.add(mTabInfors.get(i).getDisplayName());
                    mFragments.add(fragment);
                }
            }
            if (UserManager.getInst().getmRuleBean().getSougouGoldRule().isIsEnable()) {
                if (UserManager.SOUGOU_SEARCH_GIFT) {
                    if ((isActivity) && UserManager.getInst().hadLogin() && UserManager.getInst().getQukandianBean().getRscnt() != -2) {
//                            SougouFragment sougouFragment = new SougouFragment();
//                            mTabNames.add("热搜");
//                            mFragments.add(sougouFragment);
                        isAddOne = 1;
                    } else if (mTabInfors.get(i).getDisplayName() != null) {
//                            fragment.setChannelId(mTabInfors.get(i).getChannelId(), mTabInfors.get(i).getDisplayName());
//                            mTabNames.add(mTabInfors.get(i).getDisplayName());
//                            mFragments.add(fragment);
                        isAddOne = 0;
                    }
                } else {
                    if (UserManager.getInst().hadLogin() && UserManager.getInst().getQukandianBean().getRscnt() != -2) {
//                            SougouFragment sougouFragment = new SougouFragment();
//                            mTabNames.add("热搜");
//                            mFragments.add(sougouFragment);
                        isAddOne = 1;
                    } else if (mTabInfors.get(i).getDisplayName() != null) {
//                            fragment.setChannelId(mTabInfors.get(i).getChannelId(), mTabInfors.get(i).getDisplayName());
//                            mTabNames.add(mTabInfors.get(i).getDisplayName());
//                            mFragments.add(fragment);
                        isAddOne = 0;
                    }
                }
            }

        }
        mInformationTabAdapter = new InformationTabAdapter(cfm, mFragments, mTabNames);
        mViewPager.setAdapter(mInformationTabAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // mInformationTabAdapter.notifyDataSetChanged();
        hideLoadingLayout();

        for (int i = 0; i < mTabNames.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.view_item_infor_img, null);
            TextView itemTitleTV = view.findViewById(R.id.view_item_infor_tab_title);
            itemTitleTV.setText(mTabNames.get(i));
            switch (i) {
                case 0:
                    view.setSelected(true);
                    mIvAdCountIcon = view.findViewById(R.id.view_item_infor_tab_iv);
                    mTvAdsNumber = view.findViewById(R.id.view_item_infor_tab_number_tv);
                    mIvAdCountIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtils.showTostCentent(getContext(), "点击阅读列表中代有红包标志的内容，可得更多收益哦。");
                        }
                    });
                    break;
                case 1:
//                    mIvSearchCountIcon = view.findViewById(R.id.view_item_infor_tab_iv);
//                    mTvSearchNumber = view.findViewById(R.id.view_item_infor_tab_number_tv);
                    mIvSearchCountIcon = mChaiBaoxiangLayout.findViewById(R.id.view_item_infor_tab_iv);
                    mTvSearchNumber = mChaiBaoxiangLayout.findViewById(R.id.view_item_infor_tab_number_tv);
                    break;
                default:
                    view.findViewById(R.id.view_item_infor_tab_iv).setVisibility(View.GONE);
                    view.findViewById(R.id.view_item_infor_tab_number_tv).setVisibility(View.GONE);
            }
            mTabLayout.getTabAt(i).setCustomView(view);
        }
        getIconShow();
    }


    @Override
    public String setPagerName() {
        return "资讯";
    }

    public void setTab(int channel) {
        if (channel != 0 && channel != 10000000) {
//            isAddOne = 1;//2.9.6.3 改版：隐藏热搜到 右上角
            channel += isAddOne;
        } else {
            if (channel == 10000000) {
//                channel = 1;
                channel = 0;
            } else {
                channel = 0;
            }
        }
        mCurrentTab = channel;
    }

    public void setCurrentTab(int channel) {
        if (null != mViewPager) {
            if (channel != 0 && channel != 10000000) {
//                isAddOne = 1;//2.9.6.3 改版：隐藏热搜到 右上角
                channel += isAddOne;
            } else {
                if (channel == 10000000) {
//                    channel = 1;
                    channel = 0;
                } else {
                    channel = 0;
                }
            }
            mViewPager.setCurrentItem(channel);
        }
    }

    public void setCurChannel(ChannelBean channelBean) {
        for (int i = 0; i < mTabInfors.size(); i++) {
            if (channelBean.getDisplayName().equals(mTabInfors.get(i).getDisplayName())) {
//                isAddOne = 1;//2.9.6.3 改版：频道隐藏热搜到 右上角
                mViewPager.setCurrentItem(i - isAddOne);
                mCurrentTab = i - isAddOne;
//                mViewPager.setCurrentItem(i);
//                mCurrentTab = i;
                return;
            }
        }
        if (mViewPager != null)
            mViewPager.setCurrentItem(0);
        mCurrentTab = 0;
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        ChannelChangerObserver.getInstance().removeChannerListener(this);
        clearFragments();
        mTabNames.clear();
        mTabNames = null;
        mFragments.clear();
        mFragments = null;
        mTabInfors.clear();
        mTabInfors = null;
        mTabLayout = null;
        mInformationTabAdapter = null;
        mIvInformationEdit = null;
        UserManager.getInst().removGoldListener(this);
    }

    @Override
    public void onChannelChangerListener(List<ChannelBean> tab, boolean remove) {
        clearLayout();
        ChannelBean channelBean = new ChannelBean();
        mTabInfors.add(channelBean);

        if (UserBean.getShowSougouFrag() && UserManager.getInst().getmRuleBean().getSougouGoldRule().isIsEnable()) {
            ChannelBean channelBean1 = new ChannelBean();
            mTabInfors.add(channelBean1);
        }

        for (ChannelBean bean : tab) {
            if (Constants.HIGH_PRICE_NAME.equals(bean.getName())) {//高价文id
                if (UserBean.getHighPriceNews()) {
                    mTabInfors.add(bean);
                }
            } else if (bean.isYesNo()) {
                mTabInfors.add(bean);
            }
//            if (bean.isYesNo()) {
//                mTabInfors.add(bean);
//            }
            DBTools.updateChannel(bean);
        }
        setView();
    }

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

    @Override
    protected void onLoadNotNetwordClick() {
        super.onLoadNotNetwordClick();
        showLoading();
        loadData(getContext());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_information_edit:
                ActivityUtils.startToInformationEditView(getContext());
                break;
            case R.id.main_search_layout:
                MobclickAgent.onEvent(getContext(), "01_02_clicksearch");
                ActivityUtils.startToSearchActivity(getContext(), "", SearchActivity.FROM_OTHER);
                break;
            case R.id.first_chai_remind:
//                mLayoutFirstChaiRemind.setVisibility(View.GONE);
//                UserSharedPreferences.getInstance().putBoolean(FIRST_CHAI, false);
                break;
            default:
                break;
        }
    }

    public void refresh() {
        if (isLoaded) {
            if (mFragments.size() > 0) {
                if (mFragments.size() > mCurrentTab && mFragments.get(mCurrentTab) instanceof InformationInSideFragment) {
                    ((InformationInSideFragment) mFragments.get(mCurrentTab)).loadMore(true);
                } else if (mFragments.size() > mCurrentTab && mFragments.get(mCurrentTab) instanceof SougouFragment) {
                    ((SougouFragment) mFragments.get(mCurrentTab)).reflash();
                }
            }
        }
    }

    @Override
    public void onLoginStatusListener(boolean state) {
        new QukandianNewProtocol(getActivity(), new BaseModel.OnResultListener<QukandianBean>() {
            @Override
            public void onResultListener(QukandianBean response) {
                getIconShow();
                loadData(getContext());
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PingObserver.getInstance().removeOnPintSuccessListener(this);
        UserManager.getInst().removeLoginListener(this);
        ChannelChangerObserver.getInstance().removeChannerListener(this);
    }

    @Override
    public void onPintSuccessListener(ArrayList<WithdrawalsRemindBean> withdrawalsRemindBean, int grayscaleNum, int channelVersion) {
        if (channelVersion != UserSharedPreferences.getInstance().getInt(Constants.CHANNEL_VERSION)) {
            loadData(getContext());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getIconShow();
            if (mChaiBaoxiangLayout != null)
                mChaiBaoxiangLayout.getChestBoxNew();//重新请求数据 避免进入后台太久被回收
        }
    }

    public void getIconShow() {
        if (mTvSearchNumber == null || mIvSearchCountIcon == null || mIvAdCountIcon == null || mTvAdsNumber == null) {
            return;
        }
        int number = AdUtil.mAdsCnt;
        if (number > 0) {
            mTvAdsNumber.setVisibility(View.VISIBLE);
            mIvAdCountIcon.setImageResource(R.mipmap.home_ads_icon_red);
            mTvAdsNumber.setText(number + "");
        } else {
            mTvAdsNumber.setVisibility(View.GONE);
            mIvAdCountIcon.setImageResource(R.mipmap.home_ads_icon_gray);
        }
        //搜狗--------------------------
        if (UserManager.getInst().hadLogin()
//                && UserManager.getInst().getmRuleBean().getSougouGoldRule().getDayMaxCount() > 0
//                && UserManager.getInst().getmRuleBean().getSougouGoldRule().isIsEnable()
//                && UserManager.getInst().getmRuleBean().getSougouGoldRule().getDPT()<=UserManager.getInst().getUserBeam().getGoldAccount().getSum()//阈值
//                &&  ( UserManager.getInst().getmRuleBean().getSougouGoldRule().getDptMax() == 0
//                || UserManager.getInst().getmRuleBean().getSougouGoldRule().getDptMax() >= UserManager.getInst().getUserBeam().getGoldAccount().getSum() )
        ) {
            if (UserManager.SOUGOU_SEARCH_GIFT) {
                boolean isActivity = false;
                if (null != UserManager.getInst().getUserBeam().getMissionGrarntedUsers()) {
                    for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                        if (Constants.SEARCH_SOUGOU.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                            isActivity = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                        }
                    }
                }
                if (isActivity) {
                    int rscnt = UserManager.getInst().getQukandianBean().getRscnt();
                    if (rscnt < 0) {
                        mTvSearchNumber.setVisibility(View.GONE);
                        mIvSearchCountIcon.setVisibility(View.GONE);
                        if (rscnt == -2) {//后台关闭热搜
                            mChaiBaoxiangLayout.setTitleShow(true);
                        } else {
                            mChaiBaoxiangLayout.setTitleShow(false);
                        }
                    } else if (rscnt == 0) {
                        mChaiBaoxiangLayout.setTitleShow(false);
                        mTvSearchNumber.setVisibility(View.GONE);
                        mIvSearchCountIcon.setImageResource(R.mipmap.home_search_icon_gray);
                    } else {
                        mChaiBaoxiangLayout.setTitleShow(false);
                        mTvSearchNumber.setVisibility(View.VISIBLE);
                        mIvSearchCountIcon.setVisibility(View.VISIBLE);
                        mTvSearchNumber.setText(rscnt + "");
                        mIvSearchCountIcon.setImageResource(R.mipmap.home_search_icon_red);
                    }
//                    if (SougouCacheBean.isShowSougouAd()) {
//                        mTvSearchNumber.setVisibility(View.VISIBLE);
//                        mIvSearchCountIcon.setVisibility(View.VISIBLE);
//                        mTvSearchNumber.setText(SougouCacheBean.GetSougouAdNumHour() + "");
//                        mIvSearchCountIcon.setImageResource(R.mipmap.home_search_icon_red);
//                    } else {
//                        //1小时缓存数 大于 后台规定最大数量 显示灰色
//                        mTvSearchNumber.setVisibility(View.GONE);
//                        mIvSearchCountIcon.setImageResource(R.mipmap.home_search_icon_gray);
//                    }
                } else {
                    mTvSearchNumber.setVisibility(View.GONE);
                    mIvSearchCountIcon.setVisibility(View.GONE);
                }
            } else {
//                if (SougouCacheBean.isShowSougouAd()) {
//                    mTvSearchNumber.setVisibility(View.VISIBLE);
//                    mIvSearchCountIcon.setVisibility(View.VISIBLE);
//                    mTvSearchNumber.setText(SougouCacheBean.GetSougouAdNumHour() + "");
//                    mIvSearchCountIcon.setImageResource(R.mipmap.home_search_icon_red);
//                } else {
//                    //1小时缓存数 大于 后台规定最大数量 显示灰色
//                    mTvSearchNumber.setVisibility(View.GONE);
//                    mIvSearchCountIcon.setImageResource(R.mipmap.home_search_icon_gray);
//                }
                int rscnt = UserManager.getInst().getQukandianBean().getRscnt();
                if (rscnt < 0) {
                    mTvSearchNumber.setVisibility(View.GONE);
                    mIvSearchCountIcon.setVisibility(View.GONE);
                    if (rscnt == -2) {//后台关闭热搜
                        mChaiBaoxiangLayout.setTitleShow(true);
                    } else {
                        mChaiBaoxiangLayout.setTitleShow(false);
                    }
                } else if (rscnt == 0) {
                    mChaiBaoxiangLayout.setTitleShow(false);
                    mTvSearchNumber.setVisibility(View.GONE);
                    mIvSearchCountIcon.setImageResource(R.mipmap.home_search_icon_gray);
                } else {
                    mChaiBaoxiangLayout.setTitleShow(false);
                    mTvSearchNumber.setVisibility(View.VISIBLE);
                    mIvSearchCountIcon.setVisibility(View.VISIBLE);
                    mTvSearchNumber.setText(rscnt + "");
                    mIvSearchCountIcon.setImageResource(R.mipmap.home_search_icon_red);
                }
            }
        } else {
            mTvSearchNumber.setVisibility(View.GONE);
            mIvSearchCountIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGoldChangeListener(int addMoney) {
        if (mTvSearchNumber == null) {
            mTvSearchNumber.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getIconShow();
                }
            }, 100);
        }
    }
}
