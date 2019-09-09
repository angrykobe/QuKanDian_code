package com.zhangku.qukandian.javascript;

import android.content.Context;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.EventBusBean.ShowNewPeopleTaskDialogBean;
import com.zhangku.qukandian.bean.UserAddGoldBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.SubmitTaskProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.ClipboardUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

public class DialogShareInterface2 {
    private DialogShareInterface2.OnShareListener mOnShareListener;

    public DialogShareInterface2(DialogShareInterface2.OnShareListener context) {
        mOnShareListener = context;
    }

    //获取token
    @android.webkit.JavascriptInterface
    public String getToken() {
        String token = "Bearer " + UserSharedPreferences.getInstance().getString(Constants.TOKEN, "");
        return token;
    }

    //获取用户id
    @android.webkit.JavascriptInterface
    public int getUserID() {
        return UserManager.getInst().getUserBeam().getId();
    }

    /**
     * 自己弹框分享
     *
     * @param title  分享标题
     * @param desc   分享内容
     * @param imgUrl 分享图片链接
     * @param url    分享点击跳转url
     */
    @android.webkit.JavascriptInterface
    public void startShareWithUrl(String title, String desc, String imgUrl, String url) {
        if (null != mOnShareListener) {
            mOnShareListener.onShareDialog(title, desc, imgUrl, url);
        }
    }

    //唤醒登录
    @android.webkit.JavascriptInterface
    public void startToLoginAct() {
        ActivityUtils.startToBeforeLogingActivity(QuKanDianApplication.getAppContext());
    }


    /**
     * @param type  分享出去类型  0微信 1朋友圈 2qq 3QQ空间
     * @param title
     * @param desc
     */
    @android.webkit.JavascriptInterface
    public void startShare(int type, String title, String desc) {
        //0微信 1朋友圈 2qq 3QQ空间
        if (null != mOnShareListener) {
            mOnShareListener.onShareListener(type, title, desc, "");
        }
    }

    @android.webkit.JavascriptInterface
    public void startShare(int type, String title, String desc, String tel) {
        //0微信 1朋友圈 2qq 3QQ空间
        if (null != mOnShareListener) {
            mOnShareListener.onShareListener(type, title, desc, tel);
        }
    }

    @android.webkit.JavascriptInterface
    public void startShare(int type) {
        //WebviewAct
//        startShare(type, "", "", "");//todo 不同收徒页面 调用到的WebviewAct、ShouTuWebActivity 的方法不同 ，遗留的bug
        if (null != mOnShareListener) {
            mOnShareListener.onShareListener(type, "", "", "", "");
        }
    }

    @android.webkit.JavascriptInterface
    public void toMain(int type) {
        //0首页 1收徒 2提现 3任务中心 4兑换商场
        if (null != mOnShareListener) {
            mOnShareListener.onToMainListener(type);
        }
    }

    @android.webkit.JavascriptInterface
    public void showAnswer() {
        UserManager.isShowNewAnswer = true;
        if (null != mOnShareListener) {
            mOnShareListener.onBackListener();
        }
    }

    @android.webkit.JavascriptInterface
    public void back() {
        if (null != mOnShareListener) {
            mOnShareListener.onBackListener();
        }
    }

    //跳转广告红包页
    @android.webkit.JavascriptInterface
    public void gotoAdRed(String url) {
        ActivityUtils.startToUrlActivity(QuKanDianApplication.getAppContext(), url, Constants.URL_FROM_SOUGOU, true, null);
    }

    //跳转热搜红包页 293
    @android.webkit.JavascriptInterface
    public void gotoSougouRed(String url, String json) {
        ActivityUtils.startToResouAct(QuKanDianApplication.getAppContext(), url, json);
    }

    @android.webkit.JavascriptInterface
    public void gotoSougouRed(String url, String urlType, String adjson, String json) {
        ActivityUtils.startToResouAct(QuKanDianApplication.getAppContext(), url, urlType, adjson, json);
    }

    @android.webkit.JavascriptInterface
    public void toAnywhere(String deeplink) {
        // 跳转到应用的任何地方
        if (null != mOnShareListener) {
            mOnShareListener.onToAnywhereListener(deeplink);
        }
    }

    @android.webkit.JavascriptInterface
    public void reflashGold(int goldNum) {
        EventBus.getDefault().post(new UserAddGoldBean(goldNum));
    }

    @android.webkit.JavascriptInterface
    public boolean isHasTitleBar() {
        return (null != mOnShareListener && mOnShareListener instanceof BaseTitleActivity);
    }

    @android.webkit.JavascriptInterface
    public boolean clickCopy(String text) {
        MobclickAgent.onEvent(QuKanDianApplication.getmContext(), "293-copyyaoqingma");
        if (TextUtils.isEmpty(text)) return false;
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        Context appContext = QuKanDianApplication.getAppContext();
        try {
            if (appContext != null) {
                ClipboardUtils.setText(appContext, text);
                ToastUtils.showTostCententShort(QuKanDianApplication.getAppContext(), "复制成功");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @android.webkit.JavascriptInterface
    public void onShareNoDialog(int type, String shareUrl, String bitmapUrl, String title, String content) {
        //0微信 1朋友圈 2qq 3QQ空间
        if (null != mOnShareListener) {
            mOnShareListener.onShareListener(type, shareUrl, bitmapUrl, title, content);
        }
    }

    @android.webkit.JavascriptInterface
    public void myAlert(String text) {
        if (mOnShareListener != null && mOnShareListener instanceof Context) {
            ToastUtils.showLongToast(((Context) mOnShareListener), text);
        }
    }

    private SubmitTaskProtocol mSubmitTaskProtocol;

    @android.webkit.JavascriptInterface
    public void requestNewPeopleAnswer() {
        if (mSubmitTaskProtocol == null) {
            mSubmitTaskProtocol = new SubmitTaskProtocol(QuKanDianApplication.getmContext(), new BaseModel.OnResultListener<Boolean>() {
                @Override
                public void onResultListener(Boolean response) {
                    MobclickAgent.onEvent(QuKanDianApplication.getmContext(), "294-finishdati");
                    if (response) {
                        ToastUtils.showLongToast(QuKanDianApplication.getmContext(), "恭喜您，成功通过测试。 ");
                        UserManager.ANSWERED = true;
                    }
                    EventBus.getDefault().post(new ShowNewPeopleTaskDialogBean());
                    ActivityUtils.startToMainActivity(QuKanDianApplication.getmContext(), 2, 0);
                    mSubmitTaskProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    ToastUtils.showShortToast(QuKanDianApplication.getmContext(), error);
                    mSubmitTaskProtocol = null;
                }
            });
            mSubmitTaskProtocol.submitTask(-1, Constants.NOVICE_ANSWER);
        }
    }

    @android.webkit.JavascriptInterface
    public void getPhone() {
        if (mOnShareListener != null) {
            mOnShareListener.onGetPhone();
        }
    }

    public interface OnShareListener {
        //app分享（无弹框）
        @Deprecated
        void onShareListener(int type, String title, String desc, String tel);

        void onShareListener(int type, String shareUrl, String bitmapUrl, String title, String content);

        //弹框分享（app分享弹框）
        void onShareDialog(String title, String desc, String imgUrl, String url);

        @Deprecated
        void onToMainListener(int type);

        //
        void onToAnywhereListener(String deeplink);

        //
        void onBackListener();

        void onGetPhone();
    }
}
