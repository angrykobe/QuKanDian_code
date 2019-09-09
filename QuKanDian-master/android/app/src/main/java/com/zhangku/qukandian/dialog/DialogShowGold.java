package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.TaskInfoBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetTaskInfoByNameProtocol;
import com.zhangku.qukandian.protocol.PutMentorHongbaoProtocol;
import com.zhangku.qukandian.protocol.SubmitTaskProtocol;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/6.
 */

public class DialogShowGold extends BaseDialog {
    private TextView mTvMoney;
    private TextView mTvConfirmBtn;
    private OnClickConfirmListener mOnClickConfirmListener;
    private PutMentorHongbaoProtocol mPutMentorHongbaoProtocol;
    private String mTaskType;
    private TextView mTvTop;
    private TextView mTvBottom;
    private double mCoin = 0;
    private GetTaskInfoByNameProtocol mGetTaskInfoByNameProtocol;
    private SubmitTaskProtocol mSubmitTaskProtocol;
    private boolean isSuccess = false;

    public DialogShowGold(Context context, OnClickConfirmListener onClickConfirmListener) {
        super(context, R.style.zhangku_dialog);
        mOnClickConfirmListener = onClickConfirmListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_show_gold_view;
    }

    @Override
    protected void setPosition() {
    }

    @Override
    protected void initView() {
        mTvMoney = (TextView) findViewById(R.id.dialog_show_gold_view_money);
        mTvConfirmBtn = (TextView) findViewById(R.id.dialog_show_gold_view_confirm);
        mTvTop = (TextView) findViewById(R.id.dialog_show_gold_view_top);
        mTvBottom = (TextView) findViewById(R.id.dialog_show_gold_view_money_bottom);

        mTvConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mTaskType)) {
                    dismiss();
                } else {
                    if (Constants.FIRST_ON_MENTEE.equals(mTaskType)) {
                        if (null == mPutMentorHongbaoProtocol) {
                            mPutMentorHongbaoProtocol = new PutMentorHongbaoProtocol(getContext(), UserManager.getInst().getUserBeam().getMentorUser().getMentorId(),
                                    new BaseModel.OnResultListener<Boolean>() {
                                        @Override
                                        public void onResultListener(Boolean response) {
                                            isSuccess = true;
                                            UserManager.ANSWERED = true;
                                            if (null != mOnClickConfirmListener) {
                                                mOnClickConfirmListener.onClickConfirmListener();
                                            }
                                            UserManager.getInst().goldChangeNofity(0);
                                            mPutMentorHongbaoProtocol = null;
                                            dismiss();
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {
                                            mPutMentorHongbaoProtocol = null;
                                        }
                                    });
                            mPutMentorHongbaoProtocol.postRequest();
                        }
                    } else if (Constants.REGISTER_COIN.equals(mTaskType)) {
                        if (null == mSubmitTaskProtocol) {
                            mSubmitTaskProtocol = new SubmitTaskProtocol(getContext(), new BaseModel.OnResultListener<Boolean>() {
                                @Override
                                public void onResultListener(Boolean response) {
                                    isSuccess = true;
                                    UserManager.ANSWERED = true;
                                    UserManager.getInst().getUserBeam().getMission().getFinished().add(Constants.REGISTER_COIN);
                                    UserManager.getInst().goldChangeNofity(0);
                                    dismiss();
                                    mSubmitTaskProtocol = null;
                                }

                                @Override
                                public void onFailureListener(int code, String error) {
                                    mSubmitTaskProtocol = null;
                                }
                            });
                            mSubmitTaskProtocol.submitTask(-2, Constants.REGISTER_COIN);
                        }
                    } else {
                        dismiss();
                    }
                }
            }
        });
    }

    @Override
    protected void release() {

    }

    public void setCoin(String type, String content) {

        mTaskType = type;
        if (Constants.FIRST_ON_MENTEE.equals(mTaskType)) {
            if (null == mGetTaskInfoByNameProtocol) {
                mGetTaskInfoByNameProtocol = new GetTaskInfoByNameProtocol(getContext(), Constants.FIRST_ON_MENTEE, new BaseModel.OnResultListener<List<TaskInfoBean>>() {
                    @Override
                    public void onResultListener(List<TaskInfoBean> response) {
                        mCoin = response.get(0).getMinCoinAmount();
                        mTvMoney.setText("¥" + response.get(0).getMinCoinAmount());
                        mGetTaskInfoByNameProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mGetTaskInfoByNameProtocol = null;
                    }
                });
                mGetTaskInfoByNameProtocol.postRequest();
            }
        } else if (Constants.REGISTER_COIN.equals(mTaskType)) {
            if (null == mGetTaskInfoByNameProtocol) {
                mGetTaskInfoByNameProtocol = new GetTaskInfoByNameProtocol(getContext(), Constants.REGISTER_COIN, new BaseModel.OnResultListener<List<TaskInfoBean>>() {
                    @Override
                    public void onResultListener(List<TaskInfoBean> response) {
                        if (response.size() > 0) {
                            mCoin = response.get(0).getMinCoinAmount();
                            mTvMoney.setText("¥" + response.get(0).getMinCoinAmount());
                        }
                        mGetTaskInfoByNameProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mGetTaskInfoByNameProtocol = null;
                    }
                });
                mGetTaskInfoByNameProtocol.postRequest();
            }
        } else {
            mTvTop.setText("恭喜您成功获得");
            mTvMoney.setText(content);
            mTvBottom.setText("神秘金红包");
            mTvConfirmBtn.setText("确定");
        }
    }

    public interface OnClickConfirmListener {
        void onClickConfirmListener();

        void onCancelListener();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (null != mOnClickConfirmListener && !isSuccess)
            mOnClickConfirmListener.onCancelListener();
    }
}
