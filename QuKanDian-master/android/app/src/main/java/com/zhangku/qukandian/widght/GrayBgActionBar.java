package com.zhangku.qukandian.widght;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;

/**
 * Created by yuzuoning on 2017/4/12.
 */

public class GrayBgActionBar extends RelativeLayout {
    private ImageView mIvBackBtn;
    private TextView mTvTitle;
    private TextView mTvRight;
    private OnCkiclRightListener mOnCkiclRightListener;

    public GrayBgActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIvBackBtn = (ImageView) findViewById(R.id.gray_actionbar_back);
        mTvTitle = (TextView) findViewById(R.id.gray_actionbar_title);
        mTvRight = findViewById(R.id.gray_actionbar_right);
        mIvBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
        mTvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != mOnCkiclRightListener){
                    mOnCkiclRightListener.onCkiclRightListener();
                }
            }
        });
    }

    public void setTvTitle(String title) {
        mTvTitle.setText(title);
    }
    public void setTvRight(String title,OnCkiclRightListener onCkiclRightListener) {
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(title);
        mOnCkiclRightListener = onCkiclRightListener;
    }

    public interface OnCkiclRightListener {
        void onCkiclRightListener();
    }
}
