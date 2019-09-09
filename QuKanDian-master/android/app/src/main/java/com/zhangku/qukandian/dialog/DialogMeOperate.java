package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutNewTaskProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.OperateUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by yuzuoning on 2017/5/6.
 * 运营弹框
 */

public class DialogMeOperate extends BaseDialog implements View.OnClickListener {
    private ImageView mIvBg;
    private ImageView mIvClose;
    private PutNewTaskProtocol mSubmitTaskProtocol;

    public DialogMeOperate(Context context) {
        super(context, R.style.zhangku_dialog);
        mContext = context;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_operate_view;
    }

    @Override
    protected void initView() {
        mIvBg = findViewById(R.id.dialog_operate_bg);
        mIvClose = findViewById(R.id.dialog_operate_close);
        mIvBg.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        if (UserManager.getInst().getmRuleBean() != null && UserManager.getInst().getmRuleBean().getMyPageToast() != null) {
            List<String> imgs = UserManager.getInst().getmRuleBean().getMyPageToast().getImgs();
            if (imgs.size() == 0) {
                GlideUtils.preloadImage(getContext(), UserManager.getInst().getmRuleBean().getMyPageToast().getToastLink(), mIvBg);
            } else {
                int size = imgs.size();
                int i = AdsRecordUtils.getInstance().getMeFragClickNum() % size;
                GlideUtils.preloadImage(getContext(), imgs.get(i), mIvBg);
                AdsRecordUtils.getInstance().saveMeFragClickNum();
            }

            mIvBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (UserManager.getInst().hadLogin()) {
                        MobclickAgent.onEvent(getContext(), "291_count_operate");
                        String url = UserManager.getInst().getmRuleBean().getMyPageToast().getToastGotoLink();
                        if (url.contains("http")) {//跳转h5页面
                            if (url.contains("flag=chbyxj")) {
                                ActivityUtils.startToChbyxjUrlActivity(getContext(), url);
                            } else {
                                ActivityUtils.startToWebviewAct(getContext(), url);
                            }
                            if((url).contains("shoutu")){
                                MobclickAgent.onEvent(getContext(), "295-qinyouyaoqingbanner");
                            }
                        } else if (url.startsWith("xcxα")) {
                            // 分享小程序
                            if (mContext instanceof android.app.Activity)
                                OperateUtil.shareMiniAppMain(url, (android.app.Activity) mContext, null);
                        } else {//跳转app自己页面
                            if (url.contains("|")) {//预防返回出现|导致奔溃
                                String[] urls = url.split("\\|");
                                if (urls.length > 1) {
                                    ActivityUtils.startToAssignActivity(getContext(), urls[0], Integer.valueOf(urls[1]));
                                } else {
                                    ActivityUtils.startToAssignActivity(getContext(), url, -1);
                                }
                            } else {
                                ActivityUtils.startToAssignActivity(getContext(), url, -1);
                            }
                        }
                        if (null == mSubmitTaskProtocol) {
                            mSubmitTaskProtocol = new PutNewTaskProtocol(mContext, "mypagetoast", new BaseModel.OnResultListener<DoneTaskResBean>() {
                                @Override
                                public void onResultListener(DoneTaskResBean response) {
                                    if (response.getGoldAmount() > 0) {
                                        CustomToast.showToast(mContext, response.getGoldAmount() + "", response.getDescription());
                                    }
                                    mSubmitTaskProtocol = null;
                                }

                                @Override
                                public void onFailureListener(int code, String error) {
                                    mSubmitTaskProtocol = null;
                                }
                            });
                            mSubmitTaskProtocol.postRequest();
                        }
                    } else {
                        ActivityUtils.startToBeforeLogingActivity(getContext());
                    }
                    dismiss();
                }
            });
        }
    }

    @Override
    protected void release() {
        mIvClose = null;
        mIvBg = null;
    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_operate_bg:
                dismiss();
                break;
            case R.id.dialog_operate_close:
                dismiss();
                break;
        }
    }

    @Override
    protected boolean isCanceledOnTouchOutside() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
