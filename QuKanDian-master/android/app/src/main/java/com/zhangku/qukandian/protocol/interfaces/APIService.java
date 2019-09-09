package com.zhangku.qukandian.protocol.interfaces;

import com.zhangku.qukandian.bean.AdMissionBean;
import com.zhangku.qukandian.bean.AdMissionOtherBean;
import com.zhangku.qukandian.bean.AdsClickParam;
import com.zhangku.qukandian.bean.AdsCountsBean;
import com.zhangku.qukandian.bean.AppErrorLogDto;
import com.zhangku.qukandian.bean.CollectBean;
import com.zhangku.qukandian.bean.EncryptBean;
import com.zhangku.qukandian.bean.FeedbackBean;
import com.zhangku.qukandian.bean.InitUserBean;
import com.zhangku.qukandian.bean.LogUpBean;
import com.zhangku.qukandian.bean.PasswordBean;
import com.zhangku.qukandian.bean.PostAdAwakenBean;
import com.zhangku.qukandian.bean.PushCommentBean;
import com.zhangku.qukandian.bean.RegisterBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.bean.UserPostBehaviorDto;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.bean.XyzBean;
import com.zhangku.qukandian.protocol.GyroInfoProtocol;

import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by yuzuoning on 2017/4/3.
 */

public interface APIService {
    //微信登录
    @POST("user/reg/LoginByWx")
    Call<String> loginByWx(
            @Header("Authorization") String author, @Header("Content-Type") String ctype,
            @Header("signature") String signature, @Header("timestamp") long timestamp,
            @Header("appid") String appid, @Header("nonceStr") String nonceStr,
            @Query("openId") String openId, @Query("regSource") String regSource);

    //登录验证用户
    @POST("/user/reg/LoginByMobile")
    Call<String> loginDynamic(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                              @Header("signature") String signature, @Header("timestamp") long timestamp,
                              @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                              @Header("Versions") String version,
                              @Query("tel") String tel, @Query("code") String code, @Query("regSource") String regSource,
                              @Query("inviterId") long inviterId
    );

    //登录验证用户
    @POST("/user/reg/Login")
    Call<String> login(
            @Header("Authorization") String author, @Header("Content-Type") String ctype,
            @Header("signature") String signature, @Header("timestamp") long timestamp,
            @Header("appid") String appid, @Header("nonceStr") String nonceStr,
            @Header("Versions") String version, @Body RegisterBean registerBean);

    //注册用户
    @POST("/user/reg/Register")
    Call<String> register(
            @Header("Authorization") String author, @Header("Content-Type") String ctype,
            @Header("signature") String signature, @Header("timestamp") long timestamp,
            @Header("appid") String appid, @Header("nonceStr") String nonceStr,
            @Header("Versions") String version, @Body RegisterBean registerBean, @Query("RegSource") String RegSource, @Query("openId") String openId);

    //
    @POST("/user/reg/WechatBindNumber")
    Call<String> register(
            @Header("Authorization") String author, @Header("Content-Type") String ctype,
            @Header("signature") String signature, @Header("timestamp") long timestamp,
            @Header("appid") String appid, @Header("nonceStr") String nonceStr,
            @Header("Versions") String version, @Body WeChatBean bean);

    @GET("/api/version")
    Call<String> checkUpdate(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("platform") int platform, @Query("group") String group, @Query("version") String version);

    @PUT("/client/Channel")
    Call<String> getMainListResponse(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("channelId") int channelId, @Query("yesNo") boolean yesNo);

    @PUT("/client/Channel")
    Call<String> getMainListResponse(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("channelId") int channelId, @Query("orderId") int orderId);

    //频道
    @GET("api/post/Channel")
    Call<String> getTabList(@Header("Authorization") String Authorization, @Header("Content-Type") String ctype, @Query("type") int type, @Query("version") int version);

    //搜狗热词 不同域名 特殊处理
    @GET("query?num=50&length=15&forbid=5,11,21&leadip=&pid=sogou-appi-e3958a8c7218de84")
    Call<String> getSougouHotword(@Header("Authorization") String Authorization, @Header("Content-Type") String ctype, @Query("shijianchuo") long shijianchuo);

    @POST("/token")
    Call<String> getClientGredentials(@Header("Authorization") String Authorization, @Header("Versions") String Versions, @Body RequestBody body);

    @POST("/token?type=wx_login")
    Call<String> getClientGredentialsWX(@Header("Authorization") String Authorization, @Header("Versions") String Versions, @Body RequestBody body);

    @POST("/token?type=mobile_login")
    Call<String> getClientGredentialsDynamic(@Header("Authorization") String Authorization, @Header("Versions") String Versions, @Body RequestBody body);

    //接口无文档说明
    @GET("api/sms/captcha")
    Call<String> getVerificationCode(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("tel") String tel);

    //接口无文档说明
    @GET("api/graph/captchacodenew")
    Call<String> getVerificationCodeNew(@Header("Authorization") String author, @Header("Content-Type") String ctype, @QueryMap Map<String, String> map);

    //获得搜索列表
    @GET("/api/post/search")
    Call<String> getSearchRestul(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("Type") int type, @Query("q") String SearchKey, @Query("topN") int pageSize, @Query("page") int page);

    //修改密码
    @POST("/user/info/password")
    Call<String> updatePwd(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body PasswordBean bean);

    //更新用户信息
    @PUT("/user/info")
    Call<String> putUserInfo(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body UserBean user);

    //广告日志上传
    @POST("/api/adslog")
    Call<String> adsLogUp(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                              @Header("signature") String signature, @Header("timestamp") long timestamp,
                              @Header("appid") String appid,@Body Object[] logUpBean
    );

    //获取用户信息
    @GET("/user/info")
    Call<String> getUserInfo(@Header("Authorization") String Authorization, @Header("Content-Type") String ctype);

    //绑定微信 用户信息
    @PUT("/user/info/wechat")
    Call<String> uploadWeChatInfo(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body WeChatBean bean);    //上传用户头像

    @Multipart
    @POST("/user/info/avatar")
    Call<String> uploadAvatar(@Header("Authorization") String author, @Part MultipartBody.Part body);

    //上传信息
    @Multipart
    @POST("api/logo/like")
    Call<String> uploadLogoImg(@Header("Authorization") String author, @Part("number") int number, @Part("MemoName") String memoName, @PartMap Map<String, RequestBody> maps);

    @GET("/user/post/favorite")
    Call<String> getFavorite(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("PostType") int PostType, @Query("PageSize") int PageSize, @Query("Page") int Page);

    @GET("/user/post/favorite")
    Call<String> getFavoriteTogether(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("PageSize") int PageSize, @Query("Page") int Page);

    //获得所有任务列表
    @GET("/api/eco/mission/getmission")
    Call<String> getTaskListInfo(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("version") String version);

    //签到接口
    @GET("/api/eco/mission/log/checkin")
    Call<String> getSignCheckinMission(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("spanDay") int spanDay);

    @GET("/api/eco/mission/log/reading")
    Call<String> getReadMission(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("spanDay") int spanDay);

    //获得单个任务列表 根据名称
    @GET("/api/eco/Mission")
    Call<String> getTaskInfoByName(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("name") String name);

    //PUT api/eco/mission?name={name}  完成任务
    @PUT("/api/eco/mission")
    Call<String> putTaskInfoByName(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                   @Header("signature") String signature, @Header("timestamp") long timestamp,
                                   @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                   @Query("name") String name);

    //广告点击获取金币
    @PUT("/api/eco/mission")
    Call<String> putAdsClick(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                             @Header("signature") String signature, @Header("timestamp") long timestamp,
                             @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                             @Body AdsClickParam param);

    //自己成为Mentor的徒弟
    @PUT("/user/friend/mentor2")
    Call<String> putMentorId(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                             @Header("signature") String signature, @Header("timestamp") long timestamp,
                             @Header("appid") String appid, @Header("nonceStr") String nonceStr, @Query("mentorId") long mentorId);

    //我的徒弟
    @GET("/user/friend/mentee")
    Call<String> getMentee(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("Page") int page, @Query("pageSize") int pageSize);

    //奖励自己红包 请求自己成为徒弟
    @PUT("/user/friend/mentor/mission2")
    Call<String> putMentorHonbao(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                 @Header("signature") String signature, @Header("timestamp") long timestamp,
                                 @Header("appid") String appid, @Header("nonceStr") String nonceStr, @Query("mentorId") long mentorId);

    //金币日志
    @GET("/api/eco/currency/gold")
    Call<String> getGoldRecord(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("Page") int Page, @Query("PageSize") int PageSize);

    //零钱日志
    @GET("/api/eco/currency/coin")
    Call<String> getCoinRecord(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("Page") int Page, @Query("PageSize") int PageSize);

    //获得帐户信息
    @GET("/api/eco/currency")
    Call<String> getIncomeDetailsTopData(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //
//金币总排序 实时计算
    @GET("/api/eco/currency/top")
    Call<String> getIncomeRank(@Header("Authorization") String author, @Header("Content-Type") String tcype);

    //可以兑换红包
    @GET("/api/eco/consume/gift")
    Call<String> getGift(@Header("Authorization") String author, @Header("Content-Type") String ctype,@Query("Payment") String payment);

    //一元提现
    @PUT("/api/eco/consume/gift")
    Call<String> putGift(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("id") int id);

    //兑换记录
    @GET("/api/eco/consume/gift/log")
    Call<String> getWithdrawalsRecord(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("Page") int Page, @Query("PageSize") int PageSize);

    //No documentation available.
    @POST("/api/eco/feedback")
    Call<String> postFeedback(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body FeedbackBean bean);

    //新增图片上传
    @Multipart
    @POST("api/eco/feedback/PostV2")
    Call<String> postFeedback2(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body FeedbackBean bean,@Part MultipartBody.Part body);

    @Multipart
    @POST("api/eco/feedback/PostV2")
    Call<String> postFeedback2(@Header("Authorization") String author, @Header("Content-Type") String ctype,
//                               @Query("content")String content,@Query("contactWay")String contactWay,@Query("phoneVersion")String phoneVersion,@Part() MultipartBody.Part parts);
                               @Query("content")String content,@Query("contactWay")String contactWay,@Query("phoneVersion")String phoneVersion,@Part() MultipartBody.Builder parts);

    //No documentation available.
    @GET("/api/eco/doc/help")
    Call<String> getEcoHelp(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //
//获取未读数
    @GET("/api/message/tips")
    Call<String> getTips(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //已读
    @PUT("/api/message/tips")
    //type 消息类型 用户消息或者平台消息
    Call<String> putTips(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("type") int type);

    //
    @GET("/api/message")
    Call<String> getMessage(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("Page") int Page, @Query("PageSize") int PageSize, @Query("type") int type);

    //获取消息公告列表
    @GET("/api/message")
    Call<String> getMessageByTime(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("lastTime") Date lastTime, @Query("type") int type);

    //获得推荐列表
//    @GET("/api/post/recommend")
    @GET("/api/post/recom")
    Call<String> getRecommend(@Header("Authorization") String author, @Header("Content-Type") String ctype
            , @Query("channelId") int channelId, @Query("type") int type, @Query("topN") int topN, @Query("istop") boolean istop, @Query("version") String version);

    //获得推荐列表
    @GET("/api/post/recomads")
    Call<String> getRecommendAds(@Header("Authorization") String author, @Header("Content-Type") String ctype
            , @Query("channelId") int channelId, @Query("type") int type, @Query("topN") int topN, @Query("istop") boolean istop, @Query("version") String version, @Query("location") String location);

    //
//获取配置信息 1、分享域名信息
    @GET("/api/qukandian")
    Call<String> pingAccount(@Header("Authorization") String author, @Query("imei") String imei, @Query("simType") int simType, @Query("version") String version,@Query("location") String location);

    //信息
    @GET("/api/eco/top")
    Call<String> getTodayRemindData(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //No documentation available.
    @GET("api/eco/missions/bag")
    Call<String> getMissionsBag(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("type") int type);

    //开启红包任务
    @PUT("api/eco/missions/bag/pickup")
    Call<String> putBag(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("type") int type);

    //
//用户行为 主要是防刷作用
    @PUT("api/qukandian/behavior")
    Call<String> behavior(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("userId") int userId, @Query("type") int type, @Query("times") int times);

    //用户第一次调用 用户当前排名
    @GET("api/eco/ranking")
    Call<String> ranking(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //No documentation available.
    @GET("user/awaken")
    Call<String> getAwaken(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("page") int page, @Query("pageSize") int pageSize);

    //No documentation available.
    @PUT("user/awaken")
    Call<String> putWake(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("menteeId") int menteeId);

    //
    @Streaming
    @GET("fileService")
    Call<ResponseBody> downloadFile(@Field("param") String param);

    //
//领取第二阶段奖励
    @PUT("api/eco/missions/bag/plus/step2/open/v2")
    Call<String> open2(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //领取第一阶段奖励
    @PUT("api/eco/missions/bag/plus/step1/open")
    Call<String> open1(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //本次活动收取的徒弟
    @GET("api/eco/missions/bag/plus")
    Call<String> getNewTudi(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("page") int page, @Query("pageSize") int pageSize);

    //活动用户记录
    @PUT("api/eco/missions/bag/plus/share")
    Call<String> putShare(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //获取今日推送文章列表
    @GET("api/post/push")
    Call<String> getPushInfo(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //最后一次开宝箱任务
    @GET("api/eco/mission/log/chestbox")
    Call<String> getChestBox(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //创建一个新分享
    @PUT("api/user/share/momentcoin")
    Call<String> getShareMomentCoin(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //获取正常收徒页面域名地址
    @GET("api/appsetting/stnormaldomain")
    Call<String> getShoutuShareHost(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //非金币利诱文章转发地址
    @GET("api/appsetting/ptdomain")
    Call<String> getArticleShareHost(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("postId") int postId);

    //获取用户的集赞
    @GET("api/logo/like")
    Call<String> getLogoState(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //开启徒弟送的红包
    @PUT("api/eco/missions/bag/plus/mentee/coin")
    Call<String> openMySteriousHongbao(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("plusBagId") int plusBagId);

    //获得评论列表
    @GET("api/post/comment")
    Call<String> getCommentList(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("postId") int postId, @Query("order") int order, @Query("page") int page, @Query("pageSize") int pageSize);

    //一条新评论
    @POST("api/post/comment")
    Call<String> submitComment(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body PushCommentBean bean);

    //喜欢一条评论
    @PUT("api/post/comment/like")
    Call<String> putPraise(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("commentId") int commentId, @Query("incrNum") int incrNum);

    //No documentation available.
    @POST("user/info/binding")
    Call<String> bindDefault(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("openId") String openId, @Query("userId") int userId);

    //No documentation available.
    @PUT("api/graph/captcha")
    Call<String> verificationCode(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("code") String code);

    //
    @GET("Quyou2/GetImageCodeNew")
    Call<ResponseBody> getVerificationCodeImg(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("sessionid") String sessionid);

    //
    @GET("user/quyou2/getimagecode")
    Call<ResponseBody> getVerificationCodeImgNew(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("tel") String tel);

    //活动列表徒弟
    @GET("api/eco/v3/missions/bag/plus/mentee")
    Call<String> getV3Mentee(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("step") int step, @Query("page") int page, @Query("pageSize") int pageSize);

    //开启第二阶段奖励
    @GET("api/eco/v3/missions/bag/plus/step2/coin")
    Call<String> getV3CoinAll(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //开启第二阶段奖励 一个用户开启一次
    @GET("api/eco/v3/missions/bag/plus/mentee/coin")
    Call<String> getV3Coin(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("plusBagId") int plusBagId);

    //第二阶段任务结果
    @GET("api/eco/v3/missions/bag/plus/result/step2")
    Call<String> getV3Step2(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //第三阶段任务结果
    @GET("api/eco/v3/missions/bag/plus/result/step3")
    Call<String> getV3Step3(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //获取第一阶段奖励
    @PUT("api/eco/v3/missions/bag/plus/award/step1")
    Call<String> putV3Step1(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //获取第二阶段奖励
    @PUT("api/eco/v3/missions/bag/plus/award/step2")
    Call<String> putV3Step2(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //活动用户记录
    @PUT("api/eco/v3/missions/bag/plus/share")
    Call<String> putV3Share(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //数据埋点标记
    @PUT("api/eco/v3/missions/bag/plus/flag")
    Call<String> putV3Flag(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("name") String name);

    //No documentation available.  广告请求
    @GET("api/ads/advertisement/getpagesnew2")
    Call<String> getAdLocation(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("type") int type, @Query("userId") int userId, @Query("version") String version);

    // 轮询广告请求
    @GET("api/ads/getadslist")
    Call<String> getAdListForType(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("type") int type, @Query("channelId") int channelId, @Query("location") String location,@Query("version") String version);

    //转发分享获取主域名
    @GET("api/appsetting/getmaindomain")
    Call<String> getShareUrl(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("userid") int userid, @Query("postId") int postId);

    //高价文 转发分享获取主域名   zfType==1 高价文分享  0 普通金币文章分享
    @GET("api/appsetting/gotodomain")
    Call<String> getShareUrl(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("userid") int userid, @Query("postId") int postId, @Query("zfType") int zfType);

    //获取转发分享收益列表
    @GET("api/eco/mission/sharearticle/awardlist")
    Call<String> getSharedData(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("userId") int userId, @Query("Page") int Page, @Query("PageSize") int PageSize);

    //获取配置
    @GET("api/appsetting/getbykeyvalue")
    Call<String> getKey(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("key") String keys);

    //
    @GET
    Call<String> getVideoUrl(@Url String url);

    @GET("api/appsetting/startupcofig")
    Call<String> getAdClickRule(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //普通任务接口，目前是惠头条和搜狗
    @PUT("api/eco/mission/common")
    Call<String> putMissionCommon(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                  @Header("signature") String signature, @Header("timestamp") long timestamp,
                                  @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                  @Body AdMissionBean bean);

    //No documentation available.  任务中心 要完成的任务规则
    @GET("api/appsetting/httmissionrule")
    Call<String> getHttmissionRule(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("httType") int httType);

    //获取测试任务(设置灰度权限)
    @GET("api/eco/test/missions")
    Call<String> getTestMission(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //更新广告统计
    @POST("api/ads/advertisement/statistic")
    Call<String> postAdvertisement(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body List<AdsCountsBean> list);

    //获取徒弟信息
    @GET("user/info/usermentee")
    Call<String> getUserMentee(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //获取每日推荐列表   type（1：首页任务弹窗，2：签到弹窗，3：宝箱弹窗）
    @GET("api/eco/mission/recommendmissions")
    Call<String> recommendmissions(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("type") int type);

    //No documentation available.
    @PUT("api/eco/mission/dynamicmission")
    Call<String> dynamicmission(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                @Header("signature") String signature, @Header("timestamp") long timestamp,
                                @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                @Body AdMissionOtherBean bean);

    //No documentation available.  更改用户资历  新用户
    @PUT("user/info/adsuser")
    Call<String> adsuser(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body InitUserBean bean);

    //获取配置
    @GET("api/appsetting/getbykeyvalue")
    Call<String> getAppsetting(@Query("key") String key);

    //获取宝箱规则
    @GET("api/appsetting/chestboxrule")
    Call<String> getChestBoxRule();

    //
//获取每日签到规则（）
    @GET("api/appsetting/checkinrule")
    Call<String> getCheckinrule();

    //收徒页banner条配置
    @GET("api/appsetting/shoutubanner")
    Call<String> getBanner(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //收徒页面海报配置
    @GET("api/appsetting/shoutuposter")
    Call<String> getShareImg();

    //获取收徒跳转页域名,返回0代表域名未配置
    @GET("api/appsetting/shoutugotolink")
    Call<String> getGotoLink();

    //
//No documentation available.
    @GET("api/appsetting/stadomain")
    Call<String> getStadomain();

    //获取团信息
    @GET("api/Youzan/GetTuanInfo")
    Call<String> getTuanInfo(@Query("tid") String tid);

    //
//No documentation available.
    @GET("api/appsetting/youzandomain")
    Call<String> getYouzandomain(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //
//No documentation available.
    @POST("api/applog/addlog")
    Call<String> addLog(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body AppErrorLogDto app);

    //我要体现页面banner条配置
    @GET("api/appsetting/hebenpagebanner")
    Call<String> hebenpagebanner(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //金币奖励
    @POST("api/xcx/reward")
    Call<String> getTest(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                         @Header("signature") String signature, @Header("timestamp") long timestamp,
                         @Header("appid") String appid, @Header("nonceStr") String nonceStr, @Body EncryptBean bean);

    //
    @POST("api/eco/consume/gift/sign")
    Call<String> getTest(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                         @Query("str") String str);

    //上传用户数据
    @PUT("api/eco/common/packages")
    Call<String> upPackage(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("content") String content);

    //
    @GET("api/eco/consume/gift?random=0")
    Call<String> getGiftStatus(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //一元提现
    @PUT("/api/eco/consume/gift")
    Call<String> putGift(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                         @Header("signature") String signature, @Header("timestamp") long timestamp,
                         @Header("appid") String appid, @Header("nonceStr") String nonceStr);


    //获取不喜欢文章的理由
    @GET("user/post/postbehavior/dislikereason")
    Call<String> getDislikeReason(@Header("Authorization") String author, @Header("Content-Type") String ctype);


    //是否显示任务中的去提现(一元提现)
    @GET("api/eco/consume/gift/existlog")
    Call<String> getNewPeopleGold(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //获取新手任务列表
    @GET("api/eco/mission/onetimemissions")
    Call<String> getNewPeopleTask(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //今日收益
    @GET("/api/eco/currency/readgold")
    Call<String> getTodayGoldRecord(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("Page") int Page, @Query("PageSize") int PageSize);


    //赏金任务列表
    @GET("/api/bounty/tasklist")
    Call<String> getGoldTaskList(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //我参与的列表   , @Query("Page") int Page, @Query("PageSize") int PageSize
    @GET("/api/bounty/awardslist")
    Call<String> getGoldTaskHadDoneList(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("Page") int Page, @Query("PageSize") int PageSize);

    //静态页面地址请求接口   参数：文章id：postId，静态页面类型：htmlType（0-APP内置，2-非金币利诱转发）
    @GET("/api/appsetting/ptstaticpageurl")
    Call<String> getPtstaticpageurl(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("postId") int postId, @Query("htmlType") int htmlType);

    //做任务，需加验签（下载完、已满足试玩时间、点击领取奖励时都需请求该接口）   type：类型 1-下载完，2-已试玩，3-领取奖励；taskId：赏金任务id；gold：赏金任务奖励金币
    @PUT("/api/bounty/put")
    //?type=1&taskId=3&gold=500
    Call<String> putDownAppTask(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                @Header("signature") String signature, @Header("timestamp") long timestamp,
                                @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                @Query("type") int type, @Query("taskId") int taskId, @Query("gold") int gold);

    //我的菜单页
    @GET("/api/user/menu/grtlist")
    Call<String> getGrtList(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    @GET("/api/user/menu/grtlist")
    Call<String> getGrtList(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                            @Query("pageType") int pageType
    );


    //获取昨日战报接口
    @GET("/user/info/GetYesterdayGold")
    Call<String> GetYesterdayGold(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //任务宝箱配置接口
    @GET("/api/eco/mission/taskchestboxconfig")
    Call<String> GetTaskchestboxconfig(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //任务宝箱奖励接口   （加验签，宝箱状态为2：已完成时才请求此接口；orderId表示第几个宝箱）
    @PUT("/api/eco/mission/taskchestbox")
    Call<String> PutTaskchestboxconfig(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                       @Header("signature") String signature, @Header("timestamp") long timestamp,
                                       @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                       @Query("orderId") int orderId);
    //领取阅读进度奖励
    @PUT("api/readprogress/put")
    Call<String> PutReadProgress(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                       @Header("signature") String signature, @Header("timestamp") long timestamp,
                                       @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                       @Query("orderId") int orderId);

    ////////////////292 xiguan 文章修改
    //内容接口   , @Query("zyId") String zyId, @Query("channelId") int channelId
    @GET("/api/post/articlev3")
    Call<String> getInformationDetails2(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("id") int id, @Query("zyId") String zyId, @Query("channelId") int channelId);

    //阅读进度完成情况及下方广告
    @GET("api/readprogress/get")
    Call<String> getReadProgressDetails(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("location") String location);

    @GET("/api/post/article")
    Call<String> getInformationDetails(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("id") int id, @Query("zyId") String zyId, @Query("channelId") int channelId);

    //获得相关列表
    @GET("/api/post/related")
    Call<String> getAboutList(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("postId") int postId, @Query("topN") int topN, @Query("zyId") String zyId);

    //获得相关列表
    @GET("/api/post/relatedads")
    Call<String> getAboutListAds(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("postId") int postId, @Query("topN") int topN, @Query("zyId") String zyId,  @Query("type") int type, @Query("location") String location);

    //获取是否收藏
    @GET("/user/post/favorite/is")
    Call<String> getFavoriteStatus(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("postId") int postId, @Query("zyId") String zyId);

    //取消点赞
    @POST("user/post/postbehavior/removelike")
    Call<String> removePraise(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                              @Header("signature") String signature, @Header("timestamp") long timestamp,
                              @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                              @Query("userId") int userId, @Query("postId") long postid,
                              @Query("zyId") String zyId, @Query("textType") int textType);//TextType（文章类型：0-文章，1-视频）    //取消点赞

    @POST("user/post/postbehavior/removelike")
    Call<String> removePraiseForVideo(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                      @Header("signature") String signature, @Header("timestamp") long timestamp,
                                      @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                      @Query("userId") int userId, @Query("postId") long postid);

    //屏蔽新闻    -1：已经插入过此类数据 1：操作成功 -2:出错
    @POST("user/post/postbehavior/add")
    Call<String> dislikeNews(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                             @Header("signature") String signature, @Header("timestamp") long timestamp,
                             @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                             @Body UserPostBehaviorDto UserPostBehaviorDto);

    //获取文章针对此用户的行为数据   userId={userId}&postId={postId}
    @GET("user/post/postbehavior/singlepost")
    Call<String> singlePost(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("userId") int userId, @Query("postId") int postId, @Query("zyId") String zyId);

    @PUT("/user/post/favorite/collects")
    Call<String> collect(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("yesNo") boolean yesNo, @Body List<CollectBean> postId);

    //PUT api/eco/mission/read?  阅读文章详情获取金币
    @PUT("/api/eco/mission/read")
    Call<String> putTaskForReadArticle(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                       @Header("signature") String signature, @Header("timestamp") long timestamp,
                                       @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                       @Query("postId") int postId, @Query("duration") int duration,
                                       @Query("zyId") String zyId
    );

    //PUT api/eco/mission/read?  阅读文章详情获取金币
    @PUT("/api/eco/mission/read")
    Call<String> putTaskForReadVideo(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                     @Header("signature") String signature, @Header("timestamp") long timestamp,
                                     @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                     @Query("postId") int postId, @Query("duration") int duration);

    /////////////////////////////// end
    ////////292  用户等级
    @PUT("/user/level")
    Call<String> PutUpLevel(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    @GET("/user/level")
//用户等级页面1、等级从0开始2、升到最高级时，nextLevelInfo为空
    Call<String> GetUserLevel(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //用户信息页面banner条配置
    @GET("api/appsetting/mypagebanner")
    Call<String> mypagebanner(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("regSource") String regSource);

    //293
    @GET("/api/graph/imgcode")
    Call<String> Getimgcode1(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                             @Header("signature") String signature, @Header("timestamp") long timestamp,
                             @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                             @Query("tel") String tel);

    @GET("/api/comm/logincaptcha")
    Call<String> Getlogincaptcha(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                 @Header("signature") String signature, @Header("timestamp") long timestamp,
                                 @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                 @Query("tel") String tel, @Query("code") String code);

    @GET("/api/comm/logincaptcha")
    Call<String> Getlogincaptcha(@Header("Authorization") String author, @Header("Content-Type") String ctype,
                                 @Header("signature") String signature, @Header("timestamp") long timestamp,
                                 @Header("appid") String appid, @Header("nonceStr") String nonceStr,
                                 @Query("tel") String tel);
    //////////294  唤醒广告报错信息接口

    @POST("api/ads/awakenerror")
    Call<String> postAwaken(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body PostAdAwakenBean bean);

    //新手任务
    @GET("api/eco/mission/freshman")
    Call<String> getNewPeopleTask(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("version") String str);

    //阅读提示接口
    @GET("api/appsetting/readtips")
    Call<String> getReadtips(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //新手提现后提示语
    @GET("api/appsetting/hebentips")
    Call<String> getNewPeopleWitTips(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //经验明细
    @GET("user/level/exp")
    Call<String> getExpInfor(@Header("Authorization") String author, @Header("Content-Type") String ctype);

    //特权信息
    @GET("user/level")
    Call<String> getPowerInfor(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Query("up") boolean up);

    //陀螺仪
    @POST("user/info/gyro")
    Call<String> gyroInfo(@Header("Authorization") String author, @Header("Content-Type") String ctype, @Body XyzBean xyzBean);

}
