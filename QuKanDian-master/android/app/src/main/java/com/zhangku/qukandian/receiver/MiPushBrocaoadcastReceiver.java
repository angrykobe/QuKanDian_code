package com.zhangku.qukandian.receiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.QkdPushBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.observer.PushOberver;
import com.zhangku.qukandian.utils.ActivityUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/4/26.
 */

public class MiPushBrocaoadcastReceiver extends PushMessageReceiver {
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;

    public static final int TYPE_ARTICL = 1;//文章
    public static final int TYPE_VIDEO = 2;//视频
    public static final int TYPE_URL = 3;//外链
    public static final int TYPE_PLATFORM_MESSAGE = 4;//平台公告
    public static final int TYPE_USER_MESSAGE = 5;//用户公告
    public static final int TYPE_WITHDRAWALS = 6;//提现记录
    public static final int TYPE_RATE = 7;//金币兑换    应该过期了
    public static final int TYPE_TRIBUTE_FIRST = 8;//第一次进贡    应该过期了
    public static final int TYPE_TASK_EXPIRED = 9;//8元任务过期    应该过期了
    public static final int TYPE_NEW_TUDI_ACTIVITY = 10;//神秘金    应该过期了
    public static final int TYPE_NEW_ACTIVITY = 11;//活动
    public static final int DIY_TYPE_RECEIVER_1 = 1000;//收徒红包
    public MiPushBrocaoadcastReceiver() {
    }

    public MiPushBrocaoadcastReceiver(Context context, MiPushMessage message) {
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
    }


    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        Map<String, String> object = message.getExtra();
        Integer type = Integer.valueOf(object.get("Type"));
//        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//        Integer.valueOf(message.getExtra().get("type"))
        switch (type) {
            case TYPE_ARTICL:
                if (UserManager.getInst().isLaunch()) {
                    ActivityUtils.startToInformationDetailsActivity(context,Integer.valueOf(object.get("Val")),AnnoCon.ART_DETAIL_FROM_PUSH);
                } else {
                    ActivityUtils.startToLaunchActivity(context, Integer.valueOf(object.get("Val")), TYPE_ARTICL);
                }
                break;
            case TYPE_VIDEO:
                if (UserManager.getInst().isLaunch()) {
                    ActivityUtils.startToVideoDetailsActivity(context, Integer.valueOf(object.get("Val")), 0);
                } else {
                    ActivityUtils.startToLaunchActivity(context, Integer.valueOf(object.get("Val")), TYPE_VIDEO);
                }
                break;
            case TYPE_URL:
                ActivityUtils.startToWebviewAct(context, object.get("Val"));
                break;
            case TYPE_PLATFORM_MESSAGE:
                if (UserManager.getInst().isLaunch()) {
                    if (UserManager.getInst().hadLogin()) {
                        ActivityUtils.startToMessageConterActivity(context,0);
                    }
                } else {
                    ActivityUtils.startToLaunchActivity(context, 0, TYPE_PLATFORM_MESSAGE);
                }
                break;
            case TYPE_USER_MESSAGE:
                if (UserManager.getInst().isLaunch()) {
                    if (UserManager.getInst().hadLogin()) {
                        ActivityUtils.startToMessageConterActivity(context,0);
                    }
                } else {
                    ActivityUtils.startToLaunchActivity(context, 0, TYPE_USER_MESSAGE);
                }

                break;
            case TYPE_WITHDRAWALS:
                if (UserManager.getInst().isLaunch()) {
                    if (UserManager.getInst().hadLogin()) {
                        ActivityUtils.startToWithdrawalsRecordActivity(context);
                    }
                } else {
                    ActivityUtils.startToLaunchActivity(context, 0, TYPE_WITHDRAWALS);
                }

                break;
            case TYPE_RATE:
            case TYPE_TRIBUTE_FIRST:
                if (UserManager.getInst().isLaunch()) {
                    if (UserManager.getInst().hadLogin()) {
                        ActivityUtils.startToIncomeDetailsActivity(context);
                    }
                } else {
                    ActivityUtils.startToLaunchActivity(context, 0, TYPE_RATE);
                }

                break;
            case TYPE_TASK_EXPIRED:
                if (UserManager.getInst().isLaunch()) {
                    if (UserManager.getInst().hadLogin()) {
//                                ActivityUtils.startToNewPersonHongbaoActivity(context);
                    }
                } else {
                    ActivityUtils.startToLaunchActivity(context, 0, TYPE_TASK_EXPIRED);
                }
                break;
            case TYPE_NEW_TUDI_ACTIVITY:
                if (UserManager.getInst().isLaunch()) {
                    ActivityUtils.startToMainActivity(context, Constants.TAB_ME, 0);
                } else {
                    ActivityUtils.startToLaunchActivity(context, 0, TYPE_NEW_TUDI_ACTIVITY);
                }
                break;
            case TYPE_NEW_ACTIVITY:
                UserManager.mUrl = object.get("Val");
                UserManager.mActivityPath = object.get("ActionUrl");
                String s = object.get("ActionUrl2");
                if(!TextUtils.isEmpty(s)){
                    UserManager.mTab = Integer.parseInt(object.get("ActionUrl2"));
                }
                if (UserManager.getInst().isLaunch()) {
                    ActivityUtils.startToAssignActivity(context, UserManager.mActivityPath, UserManager.mTab);
                } else {
                    ActivityUtils.startToLaunchActivity(context, 0, TYPE_NEW_ACTIVITY);
                }
                break;

        }


        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        String title = message.getTitle();
        String description = message.getDescription();
        Map<String, String> extra = message.getExtra();
        if(!TextUtils.isEmpty(extra.get("type"))){
            //构建通知，8.0需要兼容NotificationChannel
            RemoteViews customRemoteView = new RemoteViews(context.getPackageName(), R.layout.customer_notitfication_layout);
            customRemoteView.setTextViewText(R.id.text, description);
//            AppInfo appInfo = DBTools.qereyAppInfo(object.optString("packName").trim());
//            byte[] images = appInfo.getIcon();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(images, 0, images.length);
//            if (null != appInfo) {
//                BitmapFactory.decodeResource(context.getResources(),R.mipmap.app_icon);
//                Icon.createWithResource(context,R.mipmap.app_icon);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_icon);
            customRemoteView.setImageViewBitmap(R.id.icon, bitmap);
            Notification.Builder builder = null;
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= 26) {
                Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(extra.get("deeplink")));
                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pintent = PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                int importance = NotificationManager.IMPORTANCE_LOW;
                @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("11","123",importance);
                nm.createNotificationChannel(channel);
                builder = new Notification.Builder(context, "11");
                builder.setContentTitle(title)
                        .setContentText(description)
                        .setSmallIcon(Icon.createWithBitmap(bitmap))
                        .setContentIntent(pintent)
                        .setOngoing(true)
                        .setAutoCancel(true);
            } else {
                customRemoteView.setTextViewText(R.id.title, title);
                Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(extra.get("deeplink")));
                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pintent = PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder = new Notification.Builder(context);
                builder.setContent(customRemoteView)
                        .setSmallIcon(R.mipmap.app_icon)
                        .setContentIntent(pintent)
                        .setOngoing(true)
                        .setAutoCancel(true);
            }
            Notification notification = builder.build();
            nm.notify(1, notification);
//            }
            //收徒红包提醒
            if(DIY_TYPE_RECEIVER_1 == Integer.valueOf(extra.get("type"))){
                QkdPushBean qkdPushBean = new QkdPushBean(Integer.valueOf(extra.get("type")), title, description, extra.get("url"), extra.get("imgurl"), extra.get("deeplink"), Integer.valueOf(extra.get("tab")));
                PushOberver.getInstance().notifyUp(qkdPushBean);
            }
        }


        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }



    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        }
    }
//    onReceivePassThroghMessage
//    onReceivePassThroughMessage
    //透传消息接受
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceivePassThroughMessage(context, miPushMessage);
    }
}
