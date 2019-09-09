package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;

/**
 * Created by yuzuoning on 2017/4/26.
 */

public class DialogLoadRemind extends LinearLayout {
    private TextView mTextView;
    public DialogLoadRemind(Context context) {
        super(context);
    }

    public DialogLoadRemind(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView = (TextView) findViewById(R.id.dialog_load_remind_text);
    }

    public void setTextContent(String content){
        if(!TextUtils.isEmpty(content)){
            mTextView.setText(content);
        }
    }
}
