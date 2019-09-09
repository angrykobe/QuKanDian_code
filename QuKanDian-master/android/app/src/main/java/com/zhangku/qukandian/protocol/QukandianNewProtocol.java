package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.QukandianBean;
import com.zhangku.qukandian.bean.WithdrawalsRemindBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.PingObserver;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.MachineInfoUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/5/5.
 */

public class QukandianNewProtocol extends NewBaseProtocol<QukandianBean> {
    private String mImei;
    private int mHasSIM;

    public QukandianNewProtocol(Context context, BaseModel.OnResultListener<QukandianBean> onResultListener) {
        super(context, onResultListener);
        mImei = MachineInfoUtil.getInstance().getIMEI(context);
        mHasSIM = CommonHelper.isSim(context) ? 1 : 0;
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().pingAccount("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
                mImei, mHasSIM, "" + QuKanDianApplication.getCode(),CommonHelper.getLocation(mContext));
    }

    @Override
    protected void getResult(QukandianBean qukandianBean) {
        UserManager.IS_INIT = true;
        UserManager.getInst().setQukandianBean(qukandianBean);
//        PingObserver.getInstance().notifySuccess(qukandianBean.getHeben(),qukandianBean.getGrayscaleNum(),qukandianBean.getChannelVersion());
        //////////////////////////////////时间差
        double currentTimeStamp = qukandianBean.getCurrentTimeStamp();//服务端时间
        long clientTime = System.currentTimeMillis();
        long serverTime = (long) (currentTimeStamp * 1000);
        QuKanDianApplication.mTimeOffset = serverTime - clientTime;//时间差 手机本地的时间跟服务器之间的时间差

        UserManager.mPubwebNiceurl = qukandianBean.getPubwebUrl() + "shoutu/";//收徒页path
        UserManager.dataKey = qukandianBean.getDataKey();
        UserManager.isGetInstallPacs = qukandianBean.getIsGetInstallPacs();
        UserManager.isChestboxShow = qukandianBean.getIsChestboxShow();
        UserManager.readingDuration = qukandianBean.getReadingDuration();
        UserManager.vedioDuration = qukandianBean.getVedioDuration();
        UserManager.adsProgressCnt = qukandianBean.getReadprogress().getAdsProgressCnt();//阅读进度-已经获取的红包数量
        UserManager.adsLog = qukandianBean.getAdsLog();//广告日志上传开关
        AdUtil.mAdsCnt = qukandianBean.getAdsCnt();

        String zhifubaostr = qukandianBean.getZhifubaostr();
        if (zhifubaostr != null && !zhifubaostr.isEmpty()) {
            String[] zhifu = zhifubaostr.split(",");
            CommonHelper.copy(getContext(), zhifu[new Random().nextInt(zhifu.length)], "");
        }

//        if (UserManager.getInst().hadLogin()) {
//            MobclickAgent.onProfileSignIn(UserManager.getInst().getUserBeam().getId() + "");
//        }
        super.getResult(qukandianBean);
    }

    //    @Override
//    protected void getResult(JSONObject object) {
//        if(null != object.optJSONObject(mResult)){
//            JSONObject result = object.optJSONObject(mResult);
//            JSONArray array = result.optJSONArray("heben");//如果空或者没有，则没有消息
//            int grayscaleNum = result.optInt("grayscaleNum");//灰度userid小于 10000+grayscaleNum 更新
//            int channelVersion = result.optInt("channelVersion");//频道数据变化的版本
//            String ip = result.optString("clientIp");//客户端ip
//            String taobaoUrl = result.optString("talbaoUrl");//淘宝地址
//            String shareGold = result.optString("sharearticleamount");//
//            String zhifubaostr = result.optString("zhifubaostr");//
//            String isChestboxShow = result.optString("isChestboxShow");//是否显示宝箱 1显示 其他不显示
//            String readingDuration = result.optString("readingDuration");//文章有效阅读时间
//            String vedioDuration = result.optString("vedioDuration");//视频有效阅读时间
//            String isGetInstallPacs = result.optString("isGetInstallPacs");//是否获取竞品安装消息
//            String pakUrl = result.optString("pakUrl");//安装包下载地址
//            int isOpen = result.optInt("isOpen");//文章详情页面是否显示点击展开全文按钮  //1：展开；0或空：不自动展开
//            String staticPageHost = result.optString("staticPageHost");//静态页地址
//            String RegisterCoinTxt = result.optString("registerCoinTxt");//新手红包文案  registerCoinTxt=打开最高得5元
//            int isShowYesterdayGold = result.optInt("isShowYesterdayGold",0);//判断是否显示昨日战报弹窗 0：不显示，1：显示
//            int isShowTaskChestBox = result.optInt("isShowTaskChestBox",0);//判断是否显示昨日战报弹窗 0：不显示，1：显示
//            String IsShowReadMaxTip = result.optString("isShowReadMaxTip");//是否提示用户有效阅读已经达到最大次数，1 需要 其他不提示
//            // 登录页设置参数  例如 01111  第一位跳转登录页判断 0 微信 1手机验证码登录
//            // 第二位微信登录图标 第三位密码登录图标 第四位手机号码图标 第五位游客图标   0不显示1显示
//            String loginOrder = result.optString("loginOrder");  //loginOrder=11010
//            double currentTimeStamp = result.optDouble("currentTimeStamp");//服务端时间
//            long clientTime = System.currentTimeMillis();
//            long serverTime =(long)(currentTimeStamp*1000);
//            QuKanDianApplication.mTimeOffset = serverTime - clientTime;//时间差 手机本地的时间跟服务器之间的时间差
//            UserSharedPreferences.getInstance().putString(Constants.READ_DETAIL_REMIND_FOR_MAX,IsShowReadMaxTip);
//            UserSharedPreferences.getInstance().putString(Constants.PAK_URL,pakUrl);
//            UserSharedPreferences.getInstance().putInt(Constants.IS_OPEN,isOpen);//1：展开；0或空：不自动展开
//            UserSharedPreferences.getInstance().putString(Constants.LOGIN_ORDER,loginOrder);//跳转登录页判断 0 微信 1手机验证码登录
//            UserSharedPreferences.getInstance().putString(Constants.RegisterCoinTxt,RegisterCoinTxt);//新手红包文案  registerCoinTxt=打开最高得5元
//            UserSharedPreferences.getInstance().putString(Constants.StaticPageHost,staticPageHost);//文章详情静态页域名
//            UserSharedPreferences.getInstance().putInt(Constants.isShowYesterdayGold,isShowYesterdayGold);//判断是否显示昨日战报弹窗
//
//
//            if (result.has("pubwebUrl")) {
//                String pubwebUrl = result.optString("pubwebUrl");//h5网页公共地址
//                if (!TextUtils.isEmpty(pubwebUrl)) {
//                    UserSharedPreferences.getInstance().putString(Constants.PUBWEB_BASEURL, pubwebUrl);
//                    if (!TextUtils.isEmpty(pubwebUrl)) {
//                        String tmpbaseurl = pubwebUrl;
//                        if (tmpbaseurl.endsWith("/")) {
//                            tmpbaseurl = tmpbaseurl.substring(0, tmpbaseurl.length() - 1);
//                        }
//                        UserManager.mPubwebNiceurl = tmpbaseurl + Constants.PUBWEB_SHOUTUPATH;
//                    }
//                }
//            }
//
//            if (result.has("dataKey")) {//秘钥
//                String dataKey = result.optString("dataKey");
//                if (!TextUtils.isEmpty(dataKey)) {
//                    UserManager.dataKey = dataKey;
//                }
//            }
//
//            UserManager.isGetInstallPacs = isGetInstallPacs;
//            UserManager.isChestboxShow = isChestboxShow;
//            UserManager.readingDuration = readingDuration;
//            UserManager.vedioDuration = vedioDuration;
//
//            if(zhifubaostr != null && !zhifubaostr.isEmpty()){
//                String[] zhifu = zhifubaostr.split(",");
//                CommonHelper.copy(getContext(),zhifu[new Random().nextInt(zhifu.length)],"");
//            }
//
//            UserSharedPreferences.getInstance().putString(Constants.TAOBAO_URL,taobaoUrl);
//            UserSharedPreferences.getInstance().putString(Constants.SHARE_GOLDE,shareGold);
//            UserSharedPreferences.getInstance().putString(Constants.IP,ip);
////            if(!TextUtils.isEmpty(ip)){
////                MobclickAgent.onProfileSignIn(ip);
////            }
//            if(UserManager.getInst().hadLogin()){
//                MobclickAgent.onProfileSignIn(UserManager.getInst().getUserBeam().getId()+"");
//            }
//            UserSharedPreferences.getInstance().putString(Constants.HOST,object.optJSONObject(mResult).optString("host"));//分享收徒域名
//            UserSharedPreferences.getInstance().putString(Constants.POSTHOST,object.optJSONObject(mResult).optString("postHost"));//分享文章域名
//            ArrayList<WithdrawalsRemindBean> list = new ArrayList<>();
//            if(null != array && array.length() > 0){
//                Gson gson = new Gson();
//                for (int i = 0; i < array.length(); i++) {
//                    WithdrawalsRemindBean withdrawalsRemindBean = gson.fromJson(array.optJSONObject(i).toString(),WithdrawalsRemindBean.class);
//                    list.add(withdrawalsRemindBean);
//                }
//            }
//
//            PingObserver.getInstance().notifySuccess(list,grayscaleNum,channelVersion);
//        }
//    }

}
