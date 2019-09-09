package com.zhangku.qukandian.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.blankj.utilcode.util.PhoneUtils;
import com.google.gson.Gson;
import com.zhangku.qukandian.activitys.AdNativeWebActivity;
import com.zhangku.qukandian.activitys.FaceToFaceInviteActivity;
import com.zhangku.qukandian.activitys.GuideActivity;
import com.zhangku.qukandian.activitys.LauncherActivity;
import com.zhangku.qukandian.activitys.MainActivity;
import com.zhangku.qukandian.activitys.MallUrlActivity;
import com.zhangku.qukandian.activitys.MySharedActivity;
import com.zhangku.qukandian.activitys.NewPeopleTaskActivity;
import com.zhangku.qukandian.activitys.ProtocolWebViewAct;
import com.zhangku.qukandian.activitys.SearchActivity;
import com.zhangku.qukandian.activitys.ShareIncomeActivity;
import com.zhangku.qukandian.activitys.TaskWebViewAct;
import com.zhangku.qukandian.activitys.TodayNewsActivity;
import com.zhangku.qukandian.activitys.UrlWebViewActivity;
import com.zhangku.qukandian.activitys.ad.AdWebViewAct;
import com.zhangku.qukandian.activitys.ad.ResouAct;
import com.zhangku.qukandian.activitys.ad.VideoAdAct;
import com.zhangku.qukandian.activitys.ad.VideoAdEndCardAct;
import com.zhangku.qukandian.activitys.WebviewAct;
import com.zhangku.qukandian.activitys.WebviewForDiankaiSdkAct;
import com.zhangku.qukandian.activitys.ad.VideoAdForOupengAct;
import com.zhangku.qukandian.activitys.additional.ChbyxjFaceToFaceInviteActivity;
import com.zhangku.qukandian.activitys.additional.ChbyxjUrlActivity;
import com.zhangku.qukandian.activitys.additional.PutongBrowserActivity;
import com.zhangku.qukandian.activitys.additional.PutongWebActivity;
import com.zhangku.qukandian.activitys.additional.ShouTuWebActivity;
import com.zhangku.qukandian.activitys.information.InformationChannelEditActivity;
import com.zhangku.qukandian.activitys.information.InformationDetailsAtivity;
import com.zhangku.qukandian.activitys.information.ReadProfitActivity;
import com.zhangku.qukandian.activitys.information.ReadProgressActivity;
import com.zhangku.qukandian.activitys.information.SougouActivity;
import com.zhangku.qukandian.activitys.me.AboutActivity;
import com.zhangku.qukandian.activitys.me.BeforeLoginActivity;
import com.zhangku.qukandian.activitys.me.BindPhoneActivity;
import com.zhangku.qukandian.activitys.me.BrowHistoryActivity;
import com.zhangku.qukandian.activitys.me.DownAppDetailAct;
import com.zhangku.qukandian.activitys.me.FeedbackActivity;
import com.zhangku.qukandian.activitys.me.ForgetPasswordActivity;
import com.zhangku.qukandian.activitys.me.GetGoldForDownActivity;
import com.zhangku.qukandian.activitys.me.IncomeDetailsActivity;
import com.zhangku.qukandian.activitys.me.IncomeRankActivity;
import com.zhangku.qukandian.activitys.me.LoginActivity;
import com.zhangku.qukandian.activitys.me.LoginDynamicActivity;
import com.zhangku.qukandian.activitys.me.MessageCenterActivity;
import com.zhangku.qukandian.activitys.me.NoviceAnswerActivity;
import com.zhangku.qukandian.activitys.me.QukandianCourseActivity;
import com.zhangku.qukandian.activitys.me.RegisterActivity;
import com.zhangku.qukandian.activitys.me.UpdateWechatBindActivity;
import com.zhangku.qukandian.activitys.me.UserLevelAct;
import com.zhangku.qukandian.activitys.me.UserLevelInforAct;
import com.zhangku.qukandian.activitys.me.WithdrawalsActivity;
import com.zhangku.qukandian.activitys.me.WithdrawalsRecordActivity;
import com.zhangku.qukandian.activitys.member.ArticleCollectActivity;
import com.zhangku.qukandian.activitys.member.PerfectInforActivity;
import com.zhangku.qukandian.activitys.member.SettingActivity;
import com.zhangku.qukandian.activitys.member.UpdateNicknameActivity;
import com.zhangku.qukandian.activitys.task.NoviceReadPrizeActivity;
import com.zhangku.qukandian.activitys.task.QuBaoGameActivity;
import com.zhangku.qukandian.activitys.task.WriteInvitationCodeActivity;
import com.zhangku.qukandian.activitys.video.VideoDetailsActivity;
import com.zhangku.qukandian.bean.ActivityBean;
import com.zhangku.qukandian.bean.ChannelBean;
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.bean.QukandianBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.biz.adbeen.wangmai.WangMaiResBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adpackage.lezhenshiwan.WowanIndex;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.QukandianNewProtocol;

import java.util.ArrayList;

import com.zhangku.qukandian.bean.AdLocationBeans.AdLocationsBean.ClientAdvertisesBean;

/**
 * Created by yuzuoning on 2017/3/24.
 */

public class ActivityUtils {
    public static void startToInformationEditView(Context context) {
        context.startActivity(new Intent(context, InformationChannelEditActivity.class));
    }

    public static void startToUserLevelAct(Context context) {
        context.startActivity(new Intent(context, UserLevelAct.class));
    }

    //账号密码登录
    public static void startToLogingActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    //账号密码登录
    public static void startToLogingActivityForBagToken(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    //登录
    public static void startToBeforeLogingActivity(Context context) {
        try {
            String loginOrder = UserManager.getInst().getQukandianBean().getLoginOrder();
            char[] c = loginOrder.toCharArray();
            if (c.length > 0 && '0' == c[0]) {//跳转登录页判断 0 微信 1手机验证码登录
                context.startActivity(new Intent(context, BeforeLoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                startToLoginDynamicActivity(context);
            }
        } catch (Exception e) {
        }
    }

    //手机验证码登录
    public static void startToLoginDynamicActivity(Context context) {
        context.startActivity(new Intent(context, LoginDynamicActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void startToRegisterActivity(Activity context, int code) {
        context.startActivityForResult(new Intent(context, RegisterActivity.class), code);
    }

    public static void startToForgetPasswordActivity(Context context) {
        context.startActivity(new Intent(context, ForgetPasswordActivity.class));
    }

    /**
     * @param context
     * @param tab     首页底部位置
     * @param channel 首页头部位置
     */
    public static void startToMainActivity(Context context, int tab, int channel) {
        FileBuildForBugUtils.Log();
        context.startActivity(new Intent(context, MainActivity.class)
                .putExtra(Constants.MAIN_TAB, tab)
                .putExtra(Constants.CHANNEL_TAB, channel)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * @param context
     * @param tab     首页底部位置
     * @param channel 首页头部位置
     */
    public static void startToMainActivity(Context context, int tab, int channel, ChannelBean channelBean) {
        context.startActivity(new Intent(context, MainActivity.class)
                .putExtra(Constants.MAIN_TAB, tab)
                .putExtra(Constants.CHANNEL_TAB, channel)
                .putExtra(Constants.CHANNEL_BEAN, channelBean)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void startToSearchActivity(Context context, String lable, int from) {
        context.startActivity(new Intent(context, SearchActivity.class)
                .putExtra(Constants.KEY_WORD, lable)
                .putExtra(Constants.FROM_TYPE, from));
    }

    public static void startToInformationDetailsActivity(Context context, int postId, @AnnoCon.ArticleFrom int from) {
        context.startActivity(new Intent(context, InformationDetailsAtivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Constants.ID, postId)
                .putExtra("from", from));
    }
    //阅读进度
    public static void startToReadProgressActivity(Context context) {
        context.startActivity(new Intent(context, ReadProgressActivity.class));
    }

    //xiguang社会频道
    public static void startToInformationDetailsActivity(Context context, int postId, @AnnoCon.ArticleFrom int from, String zyId, int channel) {
        context.startActivity(new Intent(context, InformationDetailsAtivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Constants.ID, postId)
                .putExtra("zyId", zyId)
                .putExtra("channel", channel)
                .putExtra("from", from));
    }

    public static void startToVideoDetailsActivity(Context context, int postId, int from) {
        context.startActivity(new Intent(context, VideoDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Constants.ID, postId)
                .putExtra("from", from));
    }

    public static void startToSettingActivity(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    public static void startToUserLevelInforAct(Context context) {
        context.startActivity(new Intent(context, UserLevelInforAct.class));
    }

    public static void startToPerfectActivity(Context context) {
        context.startActivity(new Intent(context, PerfectInforActivity.class));
    }

    public static void startToUpdateNicknameActivity(Context context, String nickname) {
        context.startActivity(new Intent(context, UpdateNicknameActivity.class).putExtra(Constants.NICKNAME, nickname));
    }

    public static void startToMyCollectActiivty(Context context) {
        context.startActivity(new Intent(context, ArticleCollectActivity.class));
    }

    public static void startToNoviceReadTaskActivity(Context context) {
        context.startActivity(new Intent(context, NoviceReadPrizeActivity.class));
    }

    public static void startToWriteInviteCodeActivity(Context context) {
        context.startActivity(new Intent(context, WriteInvitationCodeActivity.class));
    }


    public static void startToIncomeRankActivity(Context context) {
        context.startActivity(new Intent(context, IncomeRankActivity.class));
    }

    public static void startToIncomeDetailsActivity(Context context) {
        context.startActivity(new Intent(context, IncomeDetailsActivity.class));
    }

    public static void startToWithdrawalsActivity(Context context) {
        context.startActivity(new Intent(context, WithdrawalsActivity.class));
    }

    public static void startToWithdrawalsRecordActivity(Context context) {
        context.startActivity(new Intent(context, WithdrawalsRecordActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void startToBrowHistoryActivity(Context context) {
        context.startActivity(new Intent(context, BrowHistoryActivity.class));
    }

    public static void startToFeedbackActivity(Context context) {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }

    public static void startToAboutActivity(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    public static void startToNewPeopleTaskActivity(Context context) {
        context.startActivity(new Intent(context, NewPeopleTaskActivity.class));
    }

    public static void startToMessageConterActivity(Context context, int message_tab) {
        context.startActivity(new Intent(context, MessageCenterActivity.class)
                .putExtra("message_tab", message_tab)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void startToQukandianCourseActivity(Context context) {
        context.startActivity(new Intent(context, QukandianCourseActivity.class));
    }

    public static void startToGuideActivity(Context context) {
        context.startActivity(new Intent(context, GuideActivity.class));
    }

    public static void startToShareIncomeActivity(Context context) {
        context.startActivity(new Intent(context, ShareIncomeActivity.class));
    }


    public static void startToFace2FaceInviteActivity(Context context) {
        context.startActivity(new Intent(context, FaceToFaceInviteActivity.class));
    }

    public static void startToChbyxjFaceToFaceInviteActivity(Context context, Bundle extras) {
        Intent i = new Intent(context, ChbyxjFaceToFaceInviteActivity.class);
        if (extras != null) i.putExtras(extras);
        context.startActivity(i);
    }


    public static void startToLaunchActivity(Context context, int postId, int type) {
        context.startActivity(new Intent(context, LauncherActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Constants.ID, postId)
                .putExtra(Constants.PUSH_TYPE, type));
    }


    public static void startToTodayNewsActivity(Context context) {
        context.startActivity(new Intent(context, TodayNewsActivity.class));
    }

    public static void startToReadProfitActivity(Context context) {
        context.startActivity(new Intent(context, ReadProfitActivity.class));
    }

    public static void startToBindPhoneActivity(Context context) {
        context.startActivity(new Intent(context, BindPhoneActivity.class));
    }

    public static void startToSougouActivity(Context context,String hotword,String redNum,int rscnt) {
        context.startActivity(new Intent(context, SougouActivity.class)
                .putExtra("hotword",hotword)
                .putExtra("redNum",redNum)
                .putExtra("rscnt",rscnt));
    }

    public static void startToUpdateWechatBindActivity(Context context, ArrayList<WeChatBean> list) {
        context.startActivity(new Intent(context, UpdateWechatBindActivity.class).putParcelableArrayListExtra("list", list));
    }

    public static void startToMySharedActivity(Context context) {
        context.startActivity(new Intent(context, MySharedActivity.class));
    }

    public static void startToNoviceAnswerActivity(Context context) {
        context.startActivity(new Intent(context, NoviceAnswerActivity.class));
    }

    public static void startToChbyxjUrlActivity(Context context, String url) {
        context.startActivity(new Intent(context, ChbyxjUrlActivity.class).putExtra("url", url));
    }

    ////////////////////////////////纯h5页面///////////////////////////////////
    public static void startToPutongWebActivity(Context context, String url) {
        context.startActivity(new Intent(context, PutongWebActivity.class).putExtra("url", url));
    }

    public static void startToWebviewAct(Context context, String url) {
//        context.startActivity(new Intent(context, WebviewAct.class).putExtra("url", url));
        startToWebviewAct(context, url, "");
    }

    public static void startToWebviewAct(Context context, String url, String title) {
        context.startActivity(new Intent(context, WebviewAct.class).putExtra("url", url).putExtra("title", title));
    }
    //隐私政策，更多福利
    public static void startToAboutWebviewAct(Context context, String url, String title) {
        context.startActivity(new Intent(context, ProtocolWebViewAct.class).putExtra("url", url).putExtra("title", title));
    }

    public static void startToVideoAdActivity(Context context, TaskBean bean) {
        context.startActivity(new Intent(context, VideoAdAct.class).putExtra("bean", bean));
    }

    public static void startToWebviewForDiankaiSdkAct(Context context) {
        context.startActivity(new Intent(context, WebviewForDiankaiSdkAct.class));
    }

    public static void startToVideoAdEndCardAct(Context context, WangMaiResBean.WxadBean.VideoBean.ExtBean ext, String imgUrl) {
        context.startActivity(new Intent(context, VideoAdEndCardAct.class)
                .putExtra("bean", ext)
                .putExtra("imgUrl", imgUrl)
        );
    }

    public static void startToAdWebViewAct(Context context, String url, ClientAdvertisesBean o) {
        context.startActivity(new Intent(context, AdWebViewAct.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("url", url)
                .putExtra("ClientAdvertisesBean", o)
        );
    }

    public static void startToResouAct(Context context, String url, String beanJson) {
        context.startActivity(new Intent(context, ResouAct.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("url", url)
                .putExtra("beanJson", beanJson)
        );
    }
    public static void startToResouAct(Context context, String url,  String urlType,String adjson, String beanJson) {
        context.startActivity(new Intent(context, ResouAct.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("url", url)
                .putExtra("urlType", urlType)
                .putExtra("adjson", adjson)
                .putExtra("beanJson", beanJson)
        );
    }
    //乐真试玩ad
    public static void startToLeZhenShiWan(Context context,String deviceid) {
        context.startActivity(new Intent(context, WowanIndex.class)
                .putExtra("cid", AdConfig.lezhen_game_cid)
                .putExtra("cuid",UserManager.getInst().getUserBeam().getId() + "")
                .putExtra("deviceid", deviceid)
        );
    }
    //趣豹游戏
    public static void startToQuBaoGame(Context context) {
        context.startActivity(new Intent(context, QuBaoGameActivity.class)
        );
    }

    public static void startToUrlActivity(Context context, String url, int from, boolean isShow, ClientAdvertisesBean o) {
        context.startActivity(new Intent(context, UrlWebViewActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("url", url)
                .putExtra("from", from)
                .putExtra("isShow", isShow)
                .putExtra("object", o));
    }
//    public static void startToSougouAdActivity(Context context, String url, int clickSignNum) {
//        context.startActivity(new Intent(context, SougouAdActivity.class)
//                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                .putExtra("url", url)
//                .putExtra("clickSignNum", clickSignNum)
//        );
//    }

    public static void startToPutongBrowserActivity(Context context, String url) {
        context.startActivity(new Intent(context, PutongBrowserActivity.class)
                .putExtra("url", url)
        );
    }
    ////////////////////////////////纯h5页面  end///////////////////////////////////

    public static void startToAssignActivity(Context context, String activityPath, int tab) {
        try {
            if (activityPath.equals("com.zhangku.qukandian.activitys.ShoutuActivity")) {
                startToShoutuActivity(context);
                return;
            }
            Class clazz = Class.forName(activityPath);
            if (tab >= 0) {
                context.startActivity(new Intent(context, clazz)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Constants.MAIN_TAB, tab));
            } else {
                context.startActivity(new Intent(context, clazz));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void startToAssignActivity(Context context, String activityPath, int tab, int channel) {
        try {
            if (activityPath.equals("com.zhangku.qukandian.activitys.ShoutuActivity")) {
                startToShoutuActivity(context);
                return;
            }
            Class clazz = Class.forName(activityPath);
            if (tab >= 0) {
                context.startActivity(new Intent(context, clazz)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(Constants.MAIN_TAB, tab)
                        .putExtra(Constants.CHANNEL_TAB, channel));
            } else {
                context.startActivity(new Intent(context, clazz));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void startToMallActivity(Context context, String s) {
        context.startActivity(new Intent(context, MallUrlActivity.class)
                .putExtra("url", s));
    }

    public static void startToGetGoldForDownActivity(Context context) {
        context.startActivity(new Intent(context, GetGoldForDownActivity.class));
    }

    public static void startToDownAppDetailAct(Context context, DownAppBean bean) {
        Intent intent = new Intent(context, DownAppDetailAct.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    public static void startToShouTuWebActivity(Context context, String url) {
        context.startActivity(new Intent(context, ShouTuWebActivity.class).putExtra("url", url));
    }

    public static void startToShoutuActivity(final Context context) {
//        context.startActivity(new Intent(context, ShoutuActivity.class));
        // 收徒页改成H5
        String url = UserManager.mPubwebNiceurl;
        if (!TextUtils.isEmpty(url) || UserManager.getInst().getmRuleBean() == null || UserManager.getInst().getmRuleBean().getActiveShoutuConfig() == null) {
            ActivityUtils.startToShouTuWebActivity(context, url);
        } else {
            new QukandianNewProtocol(context, new BaseModel.OnResultListener<QukandianBean>() {
                @Override
                public void onResultListener(QukandianBean response) {
                    startToShoutuActivity(context);
                }

                @Override
                public void onFailureListener(int code, String error) {
                    ToastUtils.showShortToast(context, "数据加载失败");
                }
            }).postRequest();
        }
    }

    public static void startToTaskWebViewActivity(Context context, TaskBean bean) {
        Intent isNeedSecend1 = new Intent(context, TaskWebViewAct.class)
                .putExtra("TaskBean", bean);
        context.startActivity(isNeedSecend1);
    }


    public static void startToNativeUrlActivity(Context context, String click_url) {
        context.startActivity(new Intent(context, AdNativeWebActivity.class).putExtra("url", click_url));
    }

    public static void startToVideoAdForOupengAct(Context context, TaskBean bean) {
        context.startActivity(new Intent(context, VideoAdForOupengAct.class).putExtra("bean", bean));
    }

    /**
     * @param context
     */
    public static void startToAnyWhereJsonActivity(Context context, String json) {
        Class clazz = null;
        try {
            ActivityBean bean = new Gson().fromJson(json, ActivityBean.class);

            clazz = Class.forName(bean.getActivity());
            Intent intent = new Intent(context, clazz);
            for (ActivityBean.Params params : bean.getParams()) {
                intent.putExtra(params.getName(), params.getValue());
            }

            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            if (clazz != null)
                context.startActivity(new Intent(context, clazz));
        }
    }

//    public static void aa(Context context){
//        try {
//            Class clazz = Class.forName("com.zhangku.qukandian.utils.ActivityUtils");
//            Method m = clazz.getDeclaredMethod("startToGetGoldForDownActivity",Context.class); //获取方法
//            m.invoke(clazz, context);  //反射调用，static方法调用时，不必得到对象示例
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
}
