package com.zhangku.qukandian.widght;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.interfaces.OnClickApkDownloadListener;
import com.zhangku.qukandian.observer.DownloadObserver;

/**
 * Created by yuzuoning on 2017/6/7.
 */

public class UpdateView extends LinearLayout implements DownloadObserver.OnDownloadObserverListener {
    private TextView mTvProgressBarText;
    private NumberProgressBar mProgressBar;
    private OnClickApkDownloadListener listener;



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mProgressBar.setMax(msg.arg2);
            mProgressBar.setProgress(msg.arg1);
            int progress = (msg.arg1 * 100 / msg.arg2);
            mTvProgressBarText.setText(progress + "%");
            if (progress == 100) {
                if (listener == null) return;
                listener.onApkDownloadListener();
            }
        }
    };


    public UpdateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DownloadObserver.getInstance().addOnDownloadObserverListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mProgressBar = (NumberProgressBar) findViewById(R.id.progressBar);
        mTvProgressBarText = (TextView) findViewById(R.id.progressBar_text);
    }

    @Override
    public void onProgress(final int curren, final int total) {
//        mProgressBar.setMax(total);
//        mProgressBar.setProgress(curren);

        Message message = mHandler.obtainMessage();
        message.arg1 = curren;
        message.arg2 = total;
        mHandler.sendMessage(message);
//        ((Activity)this.getContext()).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mTvProgressBarText.setText((curren*100/total) + "%");
//            }
//        });

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DownloadObserver.getInstance().removeOnDownloadObserverListener(this);
    }

    public void setOnDownloadSuccessListener(OnClickApkDownloadListener listener) {
        this.listener = listener;
    }
}
