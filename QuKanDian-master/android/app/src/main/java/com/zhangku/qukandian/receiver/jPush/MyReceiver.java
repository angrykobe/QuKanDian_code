package com.zhangku.qukandian.receiver.jPush;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.AppInfo;
import com.zhangku.qukandian.bean.QkdPushBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.observer.PushOberver;
import com.zhangku.qukandian.receiver.CommonReceiver;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    public static final int TYPE_ARTICL = 1;//文章
    public static final int TYPE_VIDEO = 2;//视频
    public static final int TYPE_URL = 3;//外链
    public static final int TYPE_PLATFORM_MESSAGE = 4;//平台公告
    public static final int TYPE_USER_MESSAGE = 5;//用户公告
    public static final int TYPE_WITHDRAWALS = 6;//提现记录
    public static final int TYPE_RATE = 7;//金币兑换
    public static final int TYPE_TRIBUTE_FIRST = 8;//第一次进贡
    public static final int TYPE_TASK_EXPIRED = 9;//8元任务过期
    public static final int TYPE_NEW_TUDI_ACTIVITY = 10;//神秘金
    public static final int TYPE_NEW_ACTIVITY = 11;//活动

    //自定义消息类型
    public static final int DIY_TYPE_RECEIVER_1 = 1000;//透传 活动弹框

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            LogUtils.LogE("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            // 接收极光广播
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                boolean isGoon = processCustomMessage(context, bundle);

                // 不走原先的唤醒指定应用功能
                if (isGoon) {
                    String o = bundle.get(JPushInterface.EXTRA_EXTRA).toString();
                    if (null != o) {
                        JSONObject object = new JSONObject(o);
                        Uri iconUri = null;
                        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)
                                && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                            File sdCardFile = Environment.getExternalStorageDirectory();
                            iconUri = Uri.fromFile(new File(sdCardFile, "test.png"));
                        }
                        //构建通知，8.0需要兼容NotificationChannel
                        RemoteViews customRemoteView = new RemoteViews(context.getPackageName(), R.layout.customer_notitfication_layout);
                        customRemoteView.setTextViewText(R.id.text, bundle.getString(JPushInterface.EXTRA_MESSAGE));
                        AppInfo appInfo = DBTools.qereyAppInfo(object.optString("packName").trim());
                        byte[] images = appInfo.getIcon();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(images, 0, images.length);
                        if (null != appInfo) {
                            customRemoteView.setImageViewBitmap(R.id.icon, bitmap);
                            Notification.Builder builder = null;
                            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= 26) {
                                Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(object.optString("deeplink")));
                                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                PendingIntent pintent = PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                int importance = NotificationManager.IMPORTANCE_LOW;
                                @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("11", "123", importance);
                                nm.createNotificationChannel(channel);
                                builder = new Notification.Builder(context, "11");
                                builder.setContentTitle(object.optString("title"))
                                        .setContentText(bundle.getString(JPushInterface.EXTRA_MESSAGE))
                                        .setSmallIcon(Icon.createWithBitmap(bitmap))
                                        .setContentIntent(pintent)
                                        .setOngoing(true)
                                        .setAutoCancel(true);
                            } else {
                                customRemoteView.setTextViewText(R.id.title, object.optString("title"));
                                Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(object.optString("deeplink")));
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
                        }
                    }
                }

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");
                LogUtils.LogE("[MyReceiver] 用户点击打开了通知");
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                String o = bundle.get(JPushInterface.EXTRA_EXTRA).toString();
                JSONObject object = new JSONObject(o);
                switch (Integer.valueOf(object.optInt("Type"))) {
                    case TYPE_ARTICL:
                        if (UserManager.getInst().isLaunch()) {
                            ActivityUtils.startToInformationDetailsActivity(context, Integer.valueOf(object.optString("Val")), AnnoCon.ART_DETAIL_FROM_PUSH);
                        } else {
                            ActivityUtils.startToLaunchActivity(context, Integer.valueOf(object.optString("Val")), TYPE_ARTICL);
                        }
                        break;
                    case TYPE_VIDEO:
                        if (UserManager.getInst().isLaunch()) {
                            ActivityUtils.startToVideoDetailsActivity(context, Integer.valueOf(object.optString("Val")), 0);
                        } else {
                            ActivityUtils.startToLaunchActivity(context, Integer.valueOf(object.optString("Val")), TYPE_VIDEO);
                        }
                        break;
                    case TYPE_URL:
                        ActivityUtils.startToWebviewAct(context, object.optString("Val"));
                        break;
                    case TYPE_PLATFORM_MESSAGE:
                        if (UserManager.getInst().isLaunch()) {
                            if (UserManager.getInst().hadLogin()) {
                                ActivityUtils.startToMessageConterActivity(context, 0);
                            }
                        } else {
                            ActivityUtils.startToLaunchActivity(context, 0, TYPE_PLATFORM_MESSAGE);
                        }
                        break;
                    case TYPE_USER_MESSAGE:
                        if (UserManager.getInst().isLaunch()) {
                            if (UserManager.getInst().hadLogin()) {
                                ActivityUtils.startToMessageConterActivity(context, 0);
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
                        UserManager.mUrl = object.optString("Val");
                        UserManager.mActivityPath = object.optString("ActionUrl");
                        UserManager.mTab = object.optInt("ActionUrl2");
                        if (UserManager.getInst().isLaunch()) {
                            ActivityUtils.startToAssignActivity(context, UserManager.mActivityPath, UserManager.mTab);
                        } else {
                            ActivityUtils.startToLaunchActivity(context, 0, TYPE_NEW_ACTIVITY);
                        }
                        break;

                }
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
                Log.e("mPushPostId", "////用户收到到");
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private boolean processCustomMessage(Context context, Bundle bundle) {
        boolean isGoon = true;
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtils.LogE("----extras=" + extras);
        String mainActivityClassName = context.getPackageName() + ".activitys.MainActivity";

        if (null != extras) {
            JSONObject extrasObject = null;
            try {
                extrasObject = new JSONObject(extras);
                // 只要有type字段就设置false
                isGoon = extrasObject.has("type");
                String type = "";
                if (extrasObject.has("type")) {
                    type = extrasObject.optString("type").trim();
                }
                String url = "";
                if (extrasObject.has("url")) {
                    url = extrasObject.optString("url").trim();
                }
                String imgurl = "";
                if (extrasObject.has("imgurl")) {
                    imgurl = extrasObject.optString("imgurl").trim();
                }
                String deeplink = "";
                if (extrasObject.has("deeplink")) {
                    deeplink = extrasObject.optString("deeplink").trim();
                }
                String tab = "";
                if (extrasObject.has("tab")) {
                    tab = extrasObject.optString("tab").trim();
                }

                int iType = -1;
                if (!TextUtils.isEmpty(type)) {
                    iType = Integer.parseInt(type);
                }
                int iTab = -1;
                if (!TextUtils.isEmpty(tab)) {
                    iTab = Integer.parseInt(tab);
                }
                QkdPushBean qkdPushBean = new QkdPushBean(iType, title, message, url, imgurl, deeplink, iTab);

                LogUtils.LogE("iType=" + iType);
                switch (iType) {
                    case DIY_TYPE_RECEIVER_1:
                        // 用户正打开主界面
//                        if (CommonUtil.isForeground(context, mainActivityClassName)) {
                        if (UserManager.getInst().isLaunch()) {
                            // 通知主界面显示右下角图标
                            LogUtils.LogE("用户正在主界面");
                            PushOberver.getInstance().notifyUp(qkdPushBean);
                        } else {
                            // 抛出通知栏通知Intent
                            LogUtils.LogE("用户没在主界面");
//                            sendNotificationWakeUpApp(context, qkdPushBean);
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return isGoon;
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//		}
    }

    //------S 发送Notification相关
    private void sendNotificationWakeUpApp(Context context, QkdPushBean qkdPushBean) {
        Notification notification = makeNotification(context, qkdPushBean, CommonReceiver.ACTION_RECEIVER_WAKE_UP_APP);
        NotificationManager nm = makeNotificationManager(context);
        nm.notify(1, notification);
    }

    private Notification makeNotification(Context context, QkdPushBean qkdPushBean, String action) {

        PendingIntent pIntent = makePendingIntent(context, action, qkdPushBean);

        Notification.Builder builder = makeNotificationBuilder(context, qkdPushBean.getTitle(), qkdPushBean.getMessage(), pIntent);
        return builder.build();
    }

    private PendingIntent makePendingIntent(Context context, String action, QkdPushBean data) {
        //创建Intent
        Intent intent = new Intent(action);//这个意图的action为
        intent.putExtra(Constants.INTENT_DATA, data);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        return pIntent;
    }

    private NotificationManager makeNotificationManager(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("11", "123", importance);
            nm.createNotificationChannel(channel);
        }

        return nm;
    }

    private Notification.Builder makeNotificationBuilder(Context context, String title, String text, PendingIntent pIntent) {
        int icon = context.getApplicationInfo().icon;

        //构建通知，8.0需要兼容NotificationChannel
        RemoteViews customRemoteView = new RemoteViews(context.getPackageName(), R.layout.customer_notitfication_layout);
        customRemoteView.setTextViewText(R.id.text, text);
        customRemoteView.setImageViewResource(R.id.icon, R.mipmap.app_icon);
        Notification.Builder builder = null;

        if (Build.VERSION.SDK_INT >= 26) {
            builder = new Notification.Builder(context, "11");
            builder.setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.mipmap.app_icon)
                    .setContentIntent(pIntent)
                    .setOngoing(true)
                    .setAutoCancel(true);
        } else {
            customRemoteView.setTextViewText(R.id.title, title);
            builder = new Notification.Builder(context);
            builder.setContent(customRemoteView)
                    .setSmallIcon(R.mipmap.app_icon)
                    .setContentIntent(pIntent)
                    .setOngoing(true)
                    .setAutoCancel(true);
        }

        return builder;
    }
    //------E
}
