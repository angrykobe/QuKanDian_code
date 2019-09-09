package com.zhangku.qukandian.widght;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;

import java.lang.ref.WeakReference;

/**
 * Created by yuzuoning on 2017/3/25.
 * 倒计时
 */

public class CountWidght extends LinearLayout {
    private Count mCount = null;
    private CountHandler handler;
    private TextView mTextView;
    private boolean mIsStart = true;
    private OnCountClickListener mOnCountClickListener;
    public CountWidght(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTextView = (TextView) findViewById(R.id.count_widght_text);
        mCount = new Count(60000, 1000);
        handler = new CountHandler(this);
        mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsStart){
                    if(null != mOnCountClickListener){
                        mOnCountClickListener.OnCountClickListener();
                    }
                }
            }
        });
    }

    public void startCount(){
        if (mCount == null) return;
        mCount.start();
        mIsStart = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(null != mCount){
            mCount.cancel();
            mCount = null;
        }

        mTextView = null;
        handler = null;
    }

    private static class CountHandler extends Handler {
        private WeakReference<CountWidght> mWeakReference;

        public CountHandler(CountWidght countWidght) {
            mWeakReference = new WeakReference<CountWidght>(countWidght);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mWeakReference != null && mWeakReference.get() != null) {
                mWeakReference.get().handleMessage(msg);
            }
        }

    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                mIsStart = true;
                if(mTextView==null) return;
                mTextView.setText("重新获取");
                break;
        }
    }

    class Count extends CountDownTimer {

        public Count(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mCount.cancel();
            handler.sendEmptyMessage(1);
            if(null != mOnCountClickListener){
                mOnCountClickListener.onCountCancelListener();
            }
        }

        @Override
        public void onTick(long arg0) {
            mTextView.setText(arg0 / 1000 + "秒后重发");
        }

    }
    public void setOnCountClickListener(OnCountClickListener onCountClickListener){
        mOnCountClickListener = onCountClickListener;
    }
    public interface OnCountClickListener{
        void OnCountClickListener();

        void onCountCancelListener();
    }
}
