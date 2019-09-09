package com.zhangku.qukandian.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.OperateUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * Created by yuzuoning on 2017/5/6.
 * 运营弹框
 */

public class DialogOperate extends BaseDialog implements View.OnClickListener {
    private ImageView mIvBg;
    private ImageView mIvClose;
    private OnClickCloseListener mOnClickCloseListener;
    private Context mContext;
    private Activity mActivity;

    public DialogOperate(Context context,OnClickCloseListener onClickCloseListener) {
        super(context, R.style.zhangku_dialog);
        mOnClickCloseListener = onClickCloseListener;
        mContext = context;
        if (mContext instanceof Activity) {
            mActivity = (Activity)mContext;
        }
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
        if (UserManager.getInst().getmRuleBean() != null && UserManager.getInst().getmRuleBean().getOperationConfig() != null) {
            GlideUtils.displayImage(getContext(), UserManager.getInst().getmRuleBean().getOperationConfig().getToastLink(), mIvBg);
            mIvBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recordCount();
                    if (UserManager.getInst().hadLogin()) {
                        String urls = UserManager.getInst().getmRuleBean().getOperationConfig().getToastGotoLink();
                        //                    urls = "xcxα3254545562";
                        if (urls.contains("http")) {
                            if (urls.contains("flag=chbyxj")) {
                                ActivityUtils.startToChbyxjUrlActivity(getContext(), urls);
                            } else {
                                ActivityUtils.startToWebviewAct(getContext(), urls);
                            }
                        } else if (urls.startsWith("xcxα")) {
                            // 分享小程序
                            OperateUtil.shareMiniAppMain(urls, mActivity, mDialogPrograss);
                        } else {
                            if (urls.contains("|")) {
                                String[] url = urls.split("\\|");
                                if (url.length > 1) {
                                    ActivityUtils.startToAssignActivity(getContext(), url[0], Integer.valueOf(url[1]));
                                } else {
                                    ActivityUtils.startToAssignActivity(getContext(), urls, -1);
                                }
                            } else {
                                ActivityUtils.startToAssignActivity(getContext(), urls, -1);
                            }
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
        MobclickAgent.onEvent(getContext(), "7783654765_showActivityPopup");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_operate_bg:
                MobclickAgent.onEvent(getContext(), "8658156175_clickActivityPopupClose");
                dismiss();
                break;
            case R.id.dialog_operate_close:
                MobclickAgent.onEvent(getContext(), "8465803765_clickActivityPopup");
                 UserSharedPreferences.getInstance().putBoolean(Constants.ACTIVITY_STATUS, false);
                recordCount();
                if(null != mOnClickCloseListener){
                    mOnClickCloseListener.onClickCloseListener();
                }
                dismiss();
                break;
        }
    }

    private void recordCount() {
        String today = CommonHelper.formatTimeYMD(System.currentTimeMillis(), true);
        int countDay = UserSharedPreferences.getInstance().getInt(Constants.SHOW_OPERATION_DAY_COUT);
        int count = AdsRecordUtils.getInstance().getInt(Constants.SHOW_OPERATION);
        if (!today.equals(UserSharedPreferences.getInstance().getString(Constants.SHOW_OPERATION_DAY, ""))) {
            countDay++;
            UserSharedPreferences.getInstance().putInt(Constants.SHOW_OPERATION_DAY_COUT, countDay);
            UserSharedPreferences.getInstance().putString(Constants.SHOW_OPERATION_DAY, today);
            count = 0;
            AdsRecordUtils.getInstance().putInt(Constants.SHOW_OPERATION, count);
        }
        count++;
        AdsRecordUtils.getInstance().putInt(Constants.SHOW_OPERATION, count);
    }

    public interface OnClickCloseListener{
        void onClickCloseListener();
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
