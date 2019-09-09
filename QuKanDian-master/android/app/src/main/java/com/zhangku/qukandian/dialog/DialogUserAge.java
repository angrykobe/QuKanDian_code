package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutUserInfoProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.widght.datepicker.MyDatePicker;
import com.zhangku.qukandian.widght.datepicker.Sound;

/**
 * Created by yuzuoning on 2017/3/31.
 */

public class DialogUserAge extends BaseDialog implements View.OnClickListener {
    private TextView mTvCancleBtn;
    private TextView mTvConfirmBtn;
    private OnClickBtnListener mOnClickBtnListener;
    private String mAge;
    private MyDatePicker mMyDatePicker;
    private int mCurrentSetYear;
    private int mCurrentSetMonth;
    private int mCurrentSetDay;
    private String mBirthday;
    private PutUserInfoProtocol mPutUserInfoProtocol;

    public DialogUserAge(Context context, int themeResId,OnClickBtnListener onClickBtnListener) {
        super(context, themeResId);
        mPutUserInfoProtocol = new PutUserInfoProtocol(context, new BaseModel.OnResultListener() {
            @Override
            public void onResultListener(Object response) {
                mDialogPrograss.dismiss();
            }
            @Override
            public void onFailureListener(int code,String error) {

            }
        });
        mOnClickBtnListener = onClickBtnListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_user_age_layout;
    }

    @Override
    protected void initView() {
        mTvCancleBtn = (TextView) findViewById(R.id.dialog_user_age_layout_cancle);
        mTvConfirmBtn = (TextView) findViewById(R.id.dialog_user_age_layout_confirm);
        mMyDatePicker = (MyDatePicker) findViewById(R.id.dialog_user_age_layout_date);

        mTvCancleBtn.setOnClickListener(this);
        mTvConfirmBtn.setOnClickListener(this);

        final String date = CommonHelper.formatTime(System.currentTimeMillis() / 1000, true);
        final int mYear = Integer.valueOf(date.substring(0, 4));
        final int mMonth = Integer.valueOf(date.substring(5, 7));
        final int mDay = Integer.valueOf(date.substring(8, 10));
        Sound sound = new Sound(DialogUserAge.this.getContext());
        mMyDatePicker.setSoundEffect(sound).setFlagTextColor(Color.BLACK)
                .setTextColor(Color.BLACK).setDividerLineColor(R.color.grey_f2)
                .setTextSize(DisplayUtils.dip2px(DialogUserAge.this.getContext(), 18F))
                .setFlagTextSize(DisplayUtils.dip2px(DialogUserAge.this.getContext(), 18F))
                .setOnDateChangedListener(new MyDatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(MyDatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        view.setMaxYear(mYear);
                        if (year == mYear) {
                            view.setMaxMonth(mMonth);
                            if (monthOfYear == mMonth) {
                                view.setMaxDay(mDay);
                                if (dayOfMonth > mDay) {
                                    dayOfMonth = mDay;
                                }
                            }else if(monthOfYear > mMonth){
                                monthOfYear = mMonth;
                            }
                        } else {
                            view.setMaxMonth(12);
                        }
                        mOnClickBtnListener.onClickConfirmBtnListener(getAge(year, monthOfYear, dayOfMonth));
                        mBirthday = year + "-" + (monthOfYear < 10 ? "0" + monthOfYear : monthOfYear) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                    }
                }).setSoundEffectsEnabled(true);
        mMyDatePicker.setCurrentDate(mCurrentSetYear, mCurrentSetMonth, mCurrentSetDay);
    }

    private String getAge(int year, int monthOfYear, int dayOfMonth) {
        String date = CommonHelper.formatTimeline(System.currentTimeMillis());
        int years = Integer.valueOf(date.substring(0, 4));
        int months = Integer.valueOf(date.substring(5, 7));
        int days = Integer.valueOf(date.substring(8, 10));
        int age = years - year;
        if (months - monthOfYear > 0) {
            age += 1;
        } else if(months - monthOfYear == 0){
            if (days - dayOfMonth > 0) {
                age += 1;
            }
        }
        mAge = String.valueOf(age);
        return mAge;
    }

    @Override
    protected void release() {

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
            case R.id.dialog_user_age_layout_cancle:
                dismiss();
                break;
            case R.id.dialog_user_age_layout_confirm:
                if (null != mOnClickBtnListener) {
                    mDialogPrograss.show();
                    mOnClickBtnListener.onClickConfirmBtnListener(mAge);
                    UserBean user= UserManager.getInst().getUserBeam();
                    user.setBirthDay(mBirthday);
                    mPutUserInfoProtocol.putUserInfo(user);
                }
                dismiss();
                break;
        }
    }
    public String getCurrentAge(String date) {
        if (TextUtils.isEmpty(date)) {
            mCurrentSetYear = 1995;
            mCurrentSetMonth = 07;
            mCurrentSetDay = 15;
        } else {
            mCurrentSetYear = Integer.valueOf(date.substring(0, 4));
            mCurrentSetMonth = Integer.valueOf(date.substring(5, 7));
            mCurrentSetDay = Integer.valueOf(date.substring(8, 10));
        }
        return getAge(mCurrentSetYear,mCurrentSetMonth,mCurrentSetDay);
    }

    public interface OnClickBtnListener {
        void onClickConfirmBtnListener(String age);
    }
}
