package com.zhangku.qukandian.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/17
 * 你不注释一下？
 */
public class MeBottomItem extends LinearLayout {

    private Context mContext;
    private String subtitleStr;
    private String titleStr;
    private ImageView messageRemindIV;

    public MeBottomItem(Context context) {
        this(context, null);
    }

    public MeBottomItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeBottomItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttributes(context, attrs);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MeBottomItem);
        titleStr = a.getString(R.styleable.MeBottomItem_title);
        subtitleStr = a.getString(R.styleable.MeBottomItem_subtitle);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(mContext).inflate(R.layout.item_for_me_bottom_item, this);
        TextView subtitleTV =  findViewById(R.id.subtitleTV);
        messageRemindIV = findViewById(R.id.messageRemindIV);
        TextView titleTV =  findViewById(R.id.titleTV);
        subtitleTV.setText(""+subtitleStr);
        titleTV.setText(""+titleStr);
    }

    public void hideRemind(){
        messageRemindIV.setVisibility(View.GONE);
    }

    public void showRemind(){
        messageRemindIV.setVisibility(View.VISIBLE);
    }
}
