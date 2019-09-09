package com.zhangku.qukandian.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.media.UMImage;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.protocol.GetUserShareProtocol;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/5/24.
 */

public class QRShare {
    private OnShareSuccessListener mOnShareSuccessListener;

    public QRShare(OnShareSuccessListener onShareSuccessListener) {
        mOnShareSuccessListener = onShareSuccessListener;
    }

    public QRShare() {
    }

    private GetUserShareProtocol mGetUserShareProtocol;

    public void shared(final Context mContext, File file) {
        if (UserManager.getInst().getmRuleBean() == null
                ||UserManager.getInst().getmRuleBean().getShoutuPosterConfig()==null
                ||UserManager.getInst().getmRuleBean().getShoutuPosterConfig().getShareFriendText()==null) {
            ToastUtils.showLongToast(mContext, "数据加载中，请稍后重试");
            return;
        }
        ToastUtils.showLongToast(mContext, "正在打开应用");
        MobclickAgent.onEvent(mContext, "SharedRecord");
        UMImage image = new UMImage(mContext, R.mipmap.app_icon);
        ArrayList<Uri> uris = new ArrayList<>();
        int size = UserManager.mBitmaps.size();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                Uri uri1 = Uri.fromFile(file);
                uris.add(uri1);
            } else {
                Uri uri2 = Uri.fromFile(SaveBitmapToFile.save(mContext, UserManager.mBitmaps.get(i), "share" + i));
                uris.add(uri2);
            }

        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"); // 朋友圈
        intent.setComponent(comp); // 不添加就是所有可分享的都出现
        intent.setAction(Intent.ACTION_SEND_MULTIPLE); // 传多张图片
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*"); // 可传图片
        intent.putExtra("Kdescription", UserManager.getInst().getmRuleBean().getShoutuPosterConfig().getShareFriendText().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));// 标题
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris); // 传多张图片
        try {
            mContext.startActivity(intent);
            if (null != mOnShareSuccessListener) {
                mOnShareSuccessListener.onShareSuccessListener();
            }
        } catch (Exception e) {
            ToastUtils.showLongToast(mContext, "没有安装应用");
        }
    }

    public void shareRank(Context mContext, File file, String title) {
        MobclickAgent.onEvent(mContext, "SharedRecord");
        Uri uri = Uri.fromFile(file);
        ToastUtils.showLongToast(mContext, "正在打开应用");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"); // 朋友圈
        intent.setComponent(comp); // 不添加就是所有可分享的都出现
        intent.setAction(Intent.ACTION_SEND);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*"); // 可传图片
        intent.putExtra("Kdescription", title.replace("\n", ","));// 标题
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showLongToast(mContext, "没有安装应用");
        }
    }

    public void shareIncome(final Context mContext, File file, final String money) {
        MobclickAgent.onEvent(mContext, "SharedRecord");
        ToastUtils.showLongToast(mContext, "正在打开应用");

        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"); // 朋友圈
        intent.setComponent(comp); // 不添加就是所有可分享的都出现
        intent.setAction(Intent.ACTION_SEND);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*"); // 可传图片
        intent.putExtra("Kdescription", "我在趣看视界，看资讯赚了" + money + "！你还在等什么?赶快加入吧~");// 标题
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showLongToast(mContext, "没有安装应用");
        }
    }

    public interface OnShareSuccessListener {
        void onShareSuccessListener();
    }
}
