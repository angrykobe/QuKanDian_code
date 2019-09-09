package com.zhangku.qukandian.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.QkdPushBean;
import com.zhangku.qukandian.bean.QukandianBean;
import com.zhangku.qukandian.bean.ReadTipsBean;
import com.zhangku.qukandian.bean.RuleBean;
import com.zhangku.qukandian.bean.ShareBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetTokenProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;


/**
 * @author yuzuoning
 */
public class UserManager {
    public static boolean IS_INIT = false;
    //    public static boolean COLLECT = false;
    public static String KEY = "";
    public static boolean SECOND_CLIDK = false;
    public static boolean isShowNewAnswer = false;
    public static String vedioDuration = "30";
    public static String isChestboxShow = "0";
    //是否上传竞品列表
    public static String dataKey = Config.DATAKEY;
    public static String isGetInstallPacs = "0";
    public static String readingDuration = "20";
    public static String grayJurisdiction = "";//灰度权限
    public static int mPushPostId = -1;
    public static int mPushType;
    public static int adsProgressCnt;//阅读进度-已经获取的红包数量
    public static int adsLog;//广告日志上传开关  1：上传
    public static String mActivityPath;
    public static int mTab = 0;
    public static String mUrl;
    public static boolean ANSWERED = false;
    public static String mPubwebNiceurl = "";
    public QkdPushBean mQkdPushBean;

    public boolean isLaunch() {
        return mIsLaunch;
    }

    public void setLaunch(boolean launch) {
        mIsLaunch = launch;
    }

    public static boolean SOUGOU_SEARCH_GIFT = false;
    public static boolean ADS_CLICK_GIFT = false;
    public static boolean HIGH_PRICE_NEWS = false;//高价文权限
    public static boolean MALL_DUIBA_COIN = false;
    public static boolean REGIST_MISSION = false;
    public static boolean OPRATION_MISSION = false;
    public static boolean BOTTOM_MISSION = false;
    public static boolean READ_PROGRESS = false;//read_progress 阅读进度按钮显示
    public static boolean OPERATION_RIGHTICON = false;//??
    public static boolean SUCCESS = false;
    public static ShareBean mShareBean;
    public static List<Bitmap> mBitmaps = new ArrayList<>();
    private static UserManager mInst = null;
    private WeChatBean mWeChatBean;

    private boolean mIsLaunch = true;
    private UserBean mUserBean;//用户信息
    private RuleBean mRuleBean;
    private QukandianBean QukandianBean;//app配置信息
    private List<ReadTipsBean> readTipsList;//提示语
    private static Object mLock = new Object();

    /////////////////获取配置信息
    public QukandianBean getQukandianBean() {
        if (QukandianBean == null) {
            QukandianBean = new Gson().fromJson(UserSharedPreferences.getInstance().getString(Constants.QUKANDIAN_BEAN, ""), QukandianBean.class);
        }
        return QukandianBean == null ? new QukandianBean() : QukandianBean;
    }

    //设置配置信息
    public void setQukandianBean(QukandianBean bean) {
        UserSharedPreferences.getInstance().putString(Constants.QUKANDIAN_BEAN, new Gson().toJson(bean));//保存信息
        QukandianBean = bean;
    }

    //////////////////////获取提示语
    //key  对应有效阅读返回的“description”的值（其中有三个非有效阅读奖励接口返回的信息：slide-滑动提醒，click-需多次点击，error-接口调用失败提示）
    public String getReadTips(String key, String defaultStr) {
        if (TextUtils.isEmpty(key)) return defaultStr;
        if (readTipsList == null) {
            readTipsList = GsonUtil.fromJsonForList(UserSharedPreferences.getInstance().getString(Constants.ReadTipsBean, ""), ReadTipsBean.class);
        }
        if (readTipsList == null) return defaultStr;
        for (ReadTipsBean bean : readTipsList) {
            if (key.equals(bean.getKey())) {
                return bean.getValue();
            }
        }
        return defaultStr;
    }

    public String getReadTips(String key) {
        return getReadTips(key, "");
    }

    //设置配置信息
    public void setReadTips(List<ReadTipsBean> list) {
        UserSharedPreferences.getInstance().putString(Constants.ReadTipsBean, new Gson().toJson(list));//保存信息
        readTipsList = list;
    }

    ///////////////设置用户信息
    public void setUserInfor(UserBean bean) {
        UserSharedPreferences.getInstance().putString(Constants.USER_INFO, new Gson().toJson(bean));//保存用户信息
        mUserBean = bean;
    }

    //获取用户信息
    public UserBean getUserBeam() {
        if (mUserBean == null) {
            mUserBean = new Gson().fromJson(UserSharedPreferences.getInstance().getString(Constants.USER_INFO, ""), UserBean.class);
        }
        return mUserBean == null ? new UserBean() : mUserBean;
    }

    /////////////是否已经登录
    public boolean hadLogin() {
        if (mUserBean == null) {
            mUserBean = new Gson().fromJson(UserSharedPreferences.getInstance().getString(Constants.USER_INFO, ""), UserBean.class);
        }
        return mUserBean != null;
    }

    /////////////// app规则
    public RuleBean getmRuleBean() {
        if (mRuleBean == null) {
            mRuleBean = new Gson().fromJson(UserSharedPreferences.getInstance().getString(Constants.RULE_BEAN, ""), RuleBean.class);
        }
        if (mRuleBean == null) {
            mRuleBean = new RuleBean();
        }
        return mRuleBean;
    }

    public void setmRuleBean(RuleBean ruleBean) {
        UserSharedPreferences.getInstance().putString(Constants.RULE_BEAN, new Gson().toJson(ruleBean));//保存用户信息
        mRuleBean = ruleBean;
    }

    ////////////////////////////////// 灰度权限
    public String getGrayJurisdiction() {
        grayJurisdiction = UserSharedPreferences.getInstance().getString(Constants.GRAY_JURISDICTION, "");
        return grayJurisdiction;
    }

    public void setGrayJurisdiction(String grayJurisdiction) {
        UserSharedPreferences.getInstance().putString(Constants.GRAY_JURISDICTION, grayJurisdiction);
        this.grayJurisdiction = grayJurisdiction;
    }

    ////////////////////////////////////
    private List<IOnUserInfoChange> mOnUserInfoChanges = new ArrayList<>();
    private List<IOnLoginStatusLisnter> mIOnLoginStatusLisnters = new ArrayList<>();
    private List<IOnGoldChangeListener> mIOnGoldChangeListeners = new ArrayList<>();


    private UserManager() {
    }

    public static UserManager getInst() {
        if (mInst == null) {
            synchronized (mLock) {
                if (mInst == null) {
                    mInst = new UserManager();
                }
            }
        }
        return mInst;
    }

    public void addGoldListener(IOnGoldChangeListener iOnGoldChangeListener) {
        if (!mIOnGoldChangeListeners.contains(iOnGoldChangeListener)) {
            mIOnGoldChangeListeners.add(iOnGoldChangeListener);
        }
    }

    public void removGoldListener(IOnGoldChangeListener iOnGoldChangeListener) {
        if (mIOnGoldChangeListeners.contains(iOnGoldChangeListener)) {
            mIOnGoldChangeListeners.remove(iOnGoldChangeListener);
        }
    }

    public void goldChangeNofity(int addGoldNum) {
        for (IOnGoldChangeListener iOnGoldChangeListener : mIOnGoldChangeListeners) {
            if (iOnGoldChangeListener != null) {
                iOnGoldChangeListener.onGoldChangeListener(addGoldNum);
            }
        }
    }

    public void addLoginListener(IOnLoginStatusLisnter iOnLoginStatusLisnter) {
        if (!mIOnLoginStatusLisnters.contains(iOnLoginStatusLisnter)) {
            mIOnLoginStatusLisnters.add(iOnLoginStatusLisnter);
        }
    }

    public void removeLoginListener(IOnLoginStatusLisnter iOnLoginStatusLisnter) {
        if (mIOnLoginStatusLisnters.contains(iOnLoginStatusLisnter)) {
            mIOnLoginStatusLisnters.remove(iOnLoginStatusLisnter);
        }
    }

    public void updateLoginStatus(boolean state) {
        for (IOnLoginStatusLisnter listener : mIOnLoginStatusLisnters) {
            if (listener != null) {
                listener.onLoginStatusListener(state);
            }
        }
    }

    public WeChatBean getWeChatBean() {
        return mWeChatBean;
    }

    public void setWeChatBean(WeChatBean weChatBean) {
        mWeChatBean = weChatBean;
    }

    //添加用户信息和变化监听
    public void addUserInfoListener(IOnUserInfoChange onUserInfoChange) {
        if (onUserInfoChange != null && !mOnUserInfoChanges.contains(onUserInfoChange)) {
            mOnUserInfoChanges.add(onUserInfoChange);
        }
    }


    //移除用户信息变化监听
    public void removeUserInfoListener(IOnUserInfoChange onUserInfoChange) {
        if (onUserInfoChange != null && mOnUserInfoChanges.contains(onUserInfoChange)) {
            mOnUserInfoChanges.remove(onUserInfoChange);
        }
    }

    //修改头像后调用
    public void updateUserIcon(String userIcon) {
        if (hadLogin()) {
            mUserBean.setAvatarUrl(userIcon);
            for (int i = 0; i < mOnUserInfoChanges.size(); i++) {
                if (mOnUserInfoChanges.get(i) != null) {
                    mOnUserInfoChanges.get(i).onIconChange(mUserBean.getAvatarUrl());
                }
            }
        }
    }

    //修改昵称后调用
    public void updateNickName(String nickName) {
        if (hadLogin()) {
            mUserBean.setNickName(nickName);
            for (int i = 0; i < mOnUserInfoChanges.size(); i++) {
                if (mOnUserInfoChanges.get(i) != null) {
                    mOnUserInfoChanges.get(i).onNickNameChange(mUserBean.getNickName());
                }
            }
        }
    }

    private GetTokenProtocol mGetTokenProtocol;

    // 退出登录？？？
    public void logout(final Context activity) {
        if (null == mGetTokenProtocol) {
            mGetTokenProtocol = new GetTokenProtocol(activity, new BaseModel.OnResultListener<Boolean>() {
                @Override
                public void onResultListener(Boolean response) {
                    UserSharedPreferences.getInstance().putInt(Constants.USER_ID, 0);
                    UserSharedPreferences.getInstance().putString(Constants.USER_INFO, "");//清用户信息
                    if (UserManager.getInst() != null) {
                        if (UserManager.getInst().hadLogin()) {
                            JPushInterface.deleteAlias(activity, UserManager.getInst().getUserBeam().getId());
                        }
                        UserManager.getInst().updateLoginStatus(false);
                        UserManager.getInst().setUserInfor(null);
                    }
                    MobclickAgent.onProfileSignOff();
                    mGetTokenProtocol = null;
//                    ActivityUtils.startToLogingActivityForBagToken(QuKanDianApplication.getmContext());
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetTokenProtocol = null;
                    ActivityUtils.startToLogingActivityForBagToken(QuKanDianApplication.getmContext());
                }
            });
            mGetTokenProtocol.getClientToken();
        }
    }

    // 退出登录？？？
    public void logoutGotoLogin(final Context activity) {
        StackTraceElement[] temp = Thread.currentThread().getStackTrace();
        StackTraceElement a = (StackTraceElement) temp[3];
        LogUtils.LogW("----from--" + a.getClassName() + "." + a.getMethodName() + ":" + a.getLineNumber() + "--method----------to use-refreshcart--------");

        if (null == mGetTokenProtocol) {
            mGetTokenProtocol = new GetTokenProtocol(activity, new BaseModel.OnResultListener<Boolean>() {
                @Override
                public void onResultListener(Boolean response) {
                    UserSharedPreferences.getInstance().putInt(Constants.USER_ID, 0);
                    UserSharedPreferences.getInstance().putString(Constants.USER_INFO, "");//清用户信息
                    if (UserManager.getInst() != null) {
                        if (UserManager.getInst().hadLogin()) {
                            JPushInterface.deleteAlias(activity, UserManager.getInst().getUserBeam().getId());
                        }
                        UserManager.getInst().updateLoginStatus(false);
                        UserManager.getInst().setUserInfor(null);
                    }
                    MobclickAgent.onProfileSignOff();
                    mGetTokenProtocol = null;
                    ActivityUtils.startToLogingActivityForBagToken(QuKanDianApplication.getmContext());
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetTokenProtocol = null;
                    ActivityUtils.startToLogingActivityForBagToken(QuKanDianApplication.getmContext());
                }
            });
            mGetTokenProtocol.getClientToken();
        }
    }
/*

    public static int getAdMaxNumEvery() {
        return UserSharedPreferences.getInstance().getInt(Constants.AD_MAX_NUM_EVERY, 0);
    }
*/


    //用户信息改变
    public interface IOnUserInfoChange {
        void onIconChange(String userIcon);

        void onNickNameChange(String nickName);
    }

    //用户登录状态改变
    public interface IOnLoginStatusLisnter {
        void onLoginStatusListener(boolean state);
    }

    //用户金币
    public interface IOnGoldChangeListener {
        void onGoldChangeListener(int addMoney);
    }
}
