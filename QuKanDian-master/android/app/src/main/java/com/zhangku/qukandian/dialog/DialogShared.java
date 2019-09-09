package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnSharedListener;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * Created by yuzuoning on 2017/4/13.
 */

public class DialogShared extends BaseDialog implements View.OnClickListener {
    private TextView mTvCircel;
    private TextView mTvChat;
    private TextView mTvQzone;
    private TextView mTvQQ;
    private TextView mTvWb;
    private TextView mTvMessage;
    private TextView mTvSystem;
    private TextView mTvCopy;
    private TextView mTvCancel;
    private Context mContext;
    private OnSharedListener mOnSharedListener;
    private DetailsBean mDetailsBean;

    public DialogShared(Context context) {
        super(context, R.style.zhangku_dialog);
        mContext = context;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_shared_layout;
    }

    @Override
    protected void initView() {
        mTvCircel = (TextView) findViewById(R.id.dialog_shared_circel);
        mTvChat = (TextView) findViewById(R.id.dialog_shared_chat);
        mTvQzone = (TextView) findViewById(R.id.dialog_shared_qzone);
        mTvQQ = (TextView) findViewById(R.id.dialog_shared_qq);
        mTvWb = (TextView) findViewById(R.id.dialog_shared_wb);
        mTvMessage = (TextView) findViewById(R.id.dialog_shared_message);
        mTvSystem = (TextView) findViewById(R.id.dialog_shared_system);
        mTvCopy = (TextView) findViewById(R.id.dialog_shared_copy);
        mTvCancel = (TextView) findViewById(R.id.dialog_shared_cancel);

        mTvCircel.setOnClickListener(this);
        mTvChat.setOnClickListener(this);
        mTvQzone.setOnClickListener(this);
        mTvQQ.setOnClickListener(this);
        mTvWb.setOnClickListener(this);
        mTvMessage.setOnClickListener(this);
        mTvSystem.setOnClickListener(this);
        mTvCopy.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    protected void release() {
        mContext = null;
    }

    @Override
    protected void setPosition() {
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = Config.SCREEN_WIDTH;
        window.setWindowAnimations(R.style.popupAnimation);
        window.setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_shared_circel:
                if (null != mOnSharedListener) {
                    mOnSharedListener.onSharedListener(mDetailsBean, SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                dismiss();
                break;
            case R.id.dialog_shared_chat:
                if (null != mOnSharedListener) {
                    mOnSharedListener.onSharedListener(mDetailsBean, SHARE_MEDIA.WEIXIN);
                }
                dismiss();
                break;
            case R.id.dialog_shared_qzone:
                if (null != mOnSharedListener) {
                    mOnSharedListener.onSharedListener(mDetailsBean, SHARE_MEDIA.QZONE);
                }
                dismiss();
                break;
            case R.id.dialog_shared_qq:
                if (null != mOnSharedListener) {
                    mOnSharedListener.onSharedListener(mDetailsBean, SHARE_MEDIA.QQ);
                }
                dismiss();
                break;
            case R.id.dialog_shared_wb:
                if (null != mOnSharedListener) {
                    mOnSharedListener.onSharedListener(mDetailsBean, SHARE_MEDIA.SINA);
                }
                dismiss();
                break;
            case R.id.dialog_shared_message:
                if (null != mOnSharedListener) {
                    mOnSharedListener.onSharedListener(mDetailsBean, SHARE_MEDIA.SMS);
                }
                dismiss();
                break;
            case R.id.dialog_shared_system:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, mDetailsBean.getTitle() + "http://" + UserManager.getInst().getQukandianBean().getPostHost() + "/web/article/to/" + mDetailsBean.getNewId());
                shareIntent.setType("text/plain");
                getContext().startActivity(Intent.createChooser(shareIntent, "分享到"));
                dismiss();
                break;
            case R.id.dialog_shared_copy:
                if (!TextUtils.isEmpty(UserManager.getInst().getQukandianBean().getPostHost())) {
                    CommonHelper.copy(getContext(), "http://" + UserManager.getInst().getQukandianBean().getPostHost() + "/web/article/to/" + mDetailsBean.getNewId(), "复制链接成功");
                }
                dismiss();
                break;
            case R.id.dialog_shared_cancel:
                dismiss();
                break;
        }
    }

    public void setOnSharedListener(OnSharedListener onSharedListener) {
        mOnSharedListener = onSharedListener;
    }

    public void setData(DetailsBean detailsBean) {
        mDetailsBean = detailsBean;
    }

}
