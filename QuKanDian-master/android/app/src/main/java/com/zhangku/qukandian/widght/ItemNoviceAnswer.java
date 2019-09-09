package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.utils.ToastUtils;

/**
 * Created by yuzuoning on 2017/11/28.
 */

public class ItemNoviceAnswer extends LinearLayout implements View.OnClickListener {
    private TextView mTvTitle;
    private TextView mTvTopic1;
    private TextView mTvTopic2;
    private TextView mTvTopic3;
    private TextView mTvTopic4;
    private int mIndex = 0;
    private int mAnswer = 0;
    private OnSelectedIndexListener mOnSelectedIndexListener;

    public ItemNoviceAnswer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTvTitle = findViewById(R.id.item_novice_answer_title);
        mTvTopic1 = findViewById(R.id.item_novice_answer_topic1);
        mTvTopic2 = findViewById(R.id.item_novice_answer_topic2);
        mTvTopic3 = findViewById(R.id.item_novice_answer_topic3);
        mTvTopic4 = findViewById(R.id.item_novice_answer_topic4);

        mTvTopic1.setOnClickListener(this);
        mTvTopic2.setOnClickListener(this);
        mTvTopic3.setOnClickListener(this);
        mTvTopic4.setOnClickListener(this);
    }

    public void setContent(int index,String title,String topic1,String topic2,String topic3,String topic4,int answer){
        mIndex = index;
        mAnswer = answer;
        mTvTitle.setText(title);
        mTvTopic1.setText(topic1);
        mTvTopic2.setText(topic2);
        mTvTopic3.setText(topic3);
        if(!topic4.isEmpty()){
            mTvTopic4.setVisibility(View.VISIBLE);
            mTvTopic4.setText(topic4);
        }else {
            mTvTopic4.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int index = 0;
        switch (view.getId()) {
            case R.id.item_novice_answer_topic1:
                index = 0;
                mTvTopic1.setSelected(true);
                mTvTopic2.setSelected(false);
                mTvTopic3.setSelected(false);
                mTvTopic4.setSelected(false);
                break;
            case R.id.item_novice_answer_topic2:
                index = 1;
                mTvTopic1.setSelected(false);
                mTvTopic2.setSelected(true);
                mTvTopic3.setSelected(false);
                mTvTopic4.setSelected(false);
                break;
            case R.id.item_novice_answer_topic3:
                index = 2;
                mTvTopic1.setSelected(false);
                mTvTopic2.setSelected(false);
                mTvTopic3.setSelected(true);
                mTvTopic4.setSelected(false);
                break;
            case R.id.item_novice_answer_topic4:
                index = 3;
                mTvTopic1.setSelected(false);
                mTvTopic2.setSelected(false);
                mTvTopic3.setSelected(false);
                mTvTopic4.setSelected(true);
                break;
        }
        if(index != mAnswer){
            ToastUtils.showLongToast(getContext(),"答题错误，请重新填写");
        } else {
            ToastUtils.showLongToast(getContext(),"恭喜您，答对了！");
        }
        if (null != mOnSelectedIndexListener) {
            mOnSelectedIndexListener.onSelectIndexListener(mIndex,index);
        }
    }

    public void setOnSelectedIndexListener(OnSelectedIndexListener onSelectedIndexListener) {
        mOnSelectedIndexListener = onSelectedIndexListener;
    }

    public interface OnSelectedIndexListener {
        void onSelectIndexListener(int index,int selectIndex);
    }
}
