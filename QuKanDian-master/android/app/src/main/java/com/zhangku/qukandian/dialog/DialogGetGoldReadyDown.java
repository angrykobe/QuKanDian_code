package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutNewGetGoldDownAppProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.DownHelper;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.TimeUtils;

import java.io.File;


/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 赚赏金弹框
 */
public class DialogGetGoldReadyDown extends BaseDialog implements View.OnClickListener {
    private DownAppBean mBean;
    private TextView mPlayGameTV;
    private TextView mGetTaskTV;
    private ImageView mAppImg;
    private TextView mGoldNumTV;
    private TextView mAppNameTV;

    private OnGetGoldListener listener;
    private TextView mTimeShowTV;

    public DialogGetGoldReadyDown(Context context, DownAppBean bean) {
        super(context);
        this.mBean = bean;
    }

    public DownAppBean getmBean() {
        return mBean;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_getgold_ready_down;
    }

    @Override
    protected void initView() {
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        mPlayGameTV = findViewById(R.id.playGameTV);
        mGetTaskTV = findViewById(R.id.getTaskTV);
        mTimeShowTV = findViewById(R.id.timeShowTV);

        mAppImg = findViewById(R.id.appImg);
        mGoldNumTV = findViewById(R.id.goldNumTV);
        mAppNameTV = findViewById(R.id.appNameTV);
        mPlayGameTV.setOnClickListener(this);
        mGetTaskTV.setOnClickListener(this);

        updateView(mBean);
    }

    public void updateView(DownAppBean bean){
        this.mBean = bean;
        GlideUtils.displayRoundImage(mContext, bean.getLogoImgSrc(), mAppImg,6);
        mGoldNumTV.setText("+"+bean.getGold()+"金币");
        mAppNameTV.setText(""+bean.getAppName());
        mTimeShowTV.setText(mContext.getString(R.string.down_app_time_show, mBean.getTrialMinutes()));

        //-1 无操作 0 开始下载 1、下载完成 2、安装完成（未打开app） 3、试玩中(打开了app) 4 任务完成
        mGetTaskTV.setSelected(true);
        mPlayGameTV.setSelected(true);
        switch (bean.getStage()) {
            case -1:
            case 0:
                mPlayGameTV.setText("开始任务");
                mPlayGameTV.setSelected(false);
                break;
            case 1:
                if (new File(bean.getDownPath()).exists()) {
//                    new File(mBean.getDownPath()).delete();
//                    mPlayGameTV.setText("开始任务");
                    mPlayGameTV.setText("开始安装");
                } else {
                    mPlayGameTV.setText("开始任务");
                }
                break;
            case 2:
                mPlayGameTV.setText("立即试玩");
                break;
            case 3:
                mPlayGameTV.setText("立即试玩");
                mGetTaskTV.setSelected(false);
                break;
            case 4:
                mPlayGameTV.setText("立即试玩");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playGameTV://试玩
                mPlayGameTV.setSelected(true);
                switch (mPlayGameTV.getText().toString()) {
                    case "开始任务":
                        if(mBean.getStage()!=DownAppBean.APP_DOWN_START){//已经下载过的不让继续下载
                            startDownApp();
                        }
                        mPlayGameTV.setSelected(false);
                        break;
                    case "开始安装":
                        installApk(mBean.getDownPath());
                        break;
                    case "立即试玩":
                        if (!CommonHelper.startApp(mBean.getAppPackage())) {
                            mPlayGameTV.setText("开始任务");
                        } else {
                            mBean.setStage(DownAppBean.APP_PLAY);
                            mBean.setStartTime(System.currentTimeMillis());
                            DBTools.updateDownAppBean(mBean);
                            //请求接口，变为试玩任务
                            new PutNewGetGoldDownAppProtocol(v.getContext(), mBean, new BaseModel.OnResultListener<Object>() {
                                @Override
                                public void onResultListener(Object response) {
                                }

                                @Override
                                public void onFailureListener(int code, String error) {
                                }
                            }).postRequest();
                        }
                        break;
                }

                break;
            case R.id.getTaskTV://领金币
                if(mGetTaskTV.isSelected()) return;
                if(mBean.getPlayTime()<mBean.getTrialMinutes()*60*1000){
                    long l = mBean.getTrialMinutes()*60*1000 - mBean.getPlayTime();
                    String str = TimeUtils.timeFormat(l, "mm分ss秒");
                    String string = v.getContext().getString(R.string.get_gold_for_down_app_time, str);
                    Toast.makeText(v.getContext(),string,Toast.LENGTH_LONG).show();
                }else{
                    if(mBean.getStage() != DownAppBean.APP_DONE){
                        //领取奖励
                        mBean.setStage(DownAppBean.APP_DONE);
                        new PutNewGetGoldDownAppProtocol(v.getContext(), mBean, new BaseModel.OnResultListener<Object>() {

                            @Override
                            public void onResultListener(Object response) {
                                mBean.setStage(DownAppBean.APP_DONE);
                                DBTools.updateDownAppBean(mBean);
                                mGetTaskTV.setText("已领取");
                                mGetTaskTV.setSelected(true);
                                UserManager.getInst().goldChangeNofity(mBean.getGold());
                                CustomToast.showToast(mContext, "+" + mBean.getGold(), "金币奖励");
                                if(listener!=null)
                                    listener.onGetGoldListener(mBean);
                            }

                            @Override
                            public void onFailureListener(int code, String error) {
                                mBean.setStage(DownAppBean.APP_PLAY);
                            }
                        }).postRequest();

                    }
                }
                break;
        }
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    private void startDownApp() {
        long l = DownHelper.getInstance().startDownloading(mBean.getUrl(), mBean.getAppName() + ".apk");
        mBean.setDownId(l);
        mBean.setStage(DownAppBean.APP_DOWN_START);
        DBTools.updateDownAppBean(mBean);
        DownHelper.getInstance().setDownListen(new DownHelper.DownProcess() {
            @Override
            public void downProcess(int process, int state, long downId) {
                if(downId == mBean.getDownId()){
                    mPlayGameTV.setText("正在下载：" + process + "%");
                    if (process == 100) {
                        mPlayGameTV.setText("开始安装");
                        mPlayGameTV.setSelected(true);
                        //下载路径
                        String path = Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/";
                        //文件路径
                        String s = path + mBean.getAppName() + ".apk";
                        mBean.setDownPath(s);
                        mBean.setStage(DownAppBean.APP_DOWN_END);
                        DBTools.updateDownAppBean(mBean);
                        DownHelper.getInstance().setDownListen(null);
                    }
                    if(listener!=null)
                        listener.onGetGoldListener(mBean);
                }
            }
        });
    }

    private void installApk(String filePath) {
        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>=24) { //Android 7.0及以上
//            BuildConfig.APPLICATION_ID+
            // 参数2 清单文件中provider节点里面的authorities ; 参数3  共享的文件,即apk包的file类
//            Uri apkUri = FileProvider.getUriForFile(QuKanDianApplication.getmContext(), "com.zhangku.qukandian.fileprovider", file);
            Uri apkUri = FileProvider.getUriForFile(QuKanDianApplication.getmContext(), mContext.getPackageName() + ".addam.apkdn.fileprovider", file);
            //对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        QuKanDianApplication.getmContext().startActivity(intent);
    }

    public void setListener(OnGetGoldListener listener) {
        this.listener = listener;
    }
    //任务状态改变监听
    public interface OnGetGoldListener{
        void onGetGoldListener(DownAppBean bean);
    }

}
