package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;

/**
 * Created by yuzuoning on 2017/3/30.
 */

public class SearchHeaderView extends LinearLayout implements View.OnClickListener {
    private EditText mEtSearch;
    private ImageView mIvClear;
    private TextView mTvFinish;
    private IClickListener mIClickListener;

    public SearchHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mEtSearch = (EditText) findViewById(R.id.activity_search_layout_edit);
        mIvClear = (ImageView) findViewById(R.id.activity_search_layout_clear);
        mTvFinish = (TextView) findViewById(R.id.activity_search_layout_finish);

        mIvClear.setVisibility(View.GONE);
        mIvClear.setOnClickListener(this);
        mTvFinish.setOnClickListener(this);
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
                    if(null != mIClickListener){
                        mIClickListener.onCLickSearchListener(mEtSearch.getText().toString().trim());
                    }
//                    IMMUtils.hideSoftInput(getContext(), v);
                }
                return false;
            }
        });

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    mIvClear.setVisibility(View.VISIBLE);
                }else {
                    mIvClear.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_search_layout_clear:
                if(null != mIClickListener){
                    mEtSearch.setText("");
                    mIClickListener.onClickClearListener();
                }
                break;
            case R.id.activity_search_layout_finish:
                if(null != mIClickListener){
                    mIClickListener.onClickCancleListener();
                }
                break;
        }
    }

    public void setIClickListener(IClickListener iClickListener){
        mIClickListener = iClickListener;
    }

    public void setKeyword(String keyword) {
        mEtSearch.setText(keyword);
        mEtSearch.setSelection(keyword.length());
    }

    public interface IClickListener{
        void onClickClearListener();
        void onClickCancleListener();
        void onCLickSearchListener(String keyword);
    }
}
