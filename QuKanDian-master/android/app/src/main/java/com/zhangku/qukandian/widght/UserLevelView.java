package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.manager.UserManager;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/7
 * 你不注释一下？
 */
public class UserLevelView extends FrameLayout {

    private int[] leftImgID = {R.mipmap.left_1_2, R.mipmap.left_1_2, R.mipmap.left_3_4,
            R.mipmap.left_3_4, R.mipmap.left_5_6, R.mipmap.left_5_6,
            R.mipmap.left_7_8, R.mipmap.left_7_8, R.mipmap.left_9_10,
            R.mipmap.left_9_10, R.mipmap.left_11, R.mipmap.left_12
    };
    private int[] rightImgID = {R.mipmap.right_1_2, R.mipmap.right_1_2, R.mipmap.right_3_4,
            R.mipmap.right_3_4, R.mipmap.right_5_6, R.mipmap.right_5_6,
            R.mipmap.right_7_8, R.mipmap.right_7_8, R.mipmap.right_9_10,
            R.mipmap.right_9_10, R.mipmap.right_11, R.mipmap.right_12
    };
    public static int[] bigLevelImg = {R.mipmap.pop_1, R.mipmap.pop_2, R.mipmap.pop_3,
            R.mipmap.pop_4, R.mipmap.pop_5, R.mipmap.pop_6,
            R.mipmap.pop_7, R.mipmap.pop_8, R.mipmap.pop_9,
            R.mipmap.pop_10, R.mipmap.pop_11, R.mipmap.pop_12
    };
    private ImageView mLeftImg;
    private TextView mRightTV;

    public UserLevelView(@NonNull Context context) {
        super(context);
    }

    public UserLevelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UserLevelView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_level, this);
        mLeftImg = findViewById(R.id.leftImg);
        mRightTV = findViewById(R.id.rightTV);


    }

    public void setLevel(int level, String levelName) {
        mLeftImg.setImageResource(leftImgID[level]);
        mRightTV.setBackgroundResource(rightImgID[level]);
        if (mRightTV != null) {//空指针报错
            mRightTV.setText("" + levelName);
        }
    }


    public void setLevelName(String levelName) {
        mLeftImg.setImageResource(R.mipmap.icon_home_grade);
        mRightTV.setBackgroundResource(R.mipmap.icon_home_grade_2);
        mRightTV.setText("" + levelName);
    }


    public static void setLevelBigImg(ImageView levelName) {
        int level1 = UserManager.getInst().getUserBeam().getLevel();
        levelName.setImageResource(bigLevelImg[level1]);
    }

    public static void setLevelBigImg(int level, ImageView levelName) {
        levelName.setImageResource(bigLevelImg[level]);
    }

}
