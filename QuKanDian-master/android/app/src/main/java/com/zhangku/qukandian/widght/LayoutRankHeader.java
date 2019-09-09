package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GlideUtils;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class LayoutRankHeader extends LinearLayout {
    protected ImageView mIvUserHeader;
    protected TextView mTvAllImcomTitle;
    protected TextView mTvAllIncom;
    protected TextView mTvAllRankTitle;
    protected TextView mTvAllRank;

    public LayoutRankHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIvUserHeader = (ImageView) findViewById(R.id.frament_week_rank_layout_userheader);
        mTvAllImcomTitle = (TextView) findViewById(R.id.frament_week_rank_layout_all_income_title);
        mTvAllIncom = (TextView) findViewById(R.id.frament_week_rank_layout_all_income);
        mTvAllRankTitle = (TextView) findViewById(R.id.frament_week_rank_layout_my_rank_title);
        mTvAllRank = (TextView) findViewById(R.id.frament_week_rank_layout_my_rank);

        if (UserManager.getInst().hadLogin()) {
            GlideUtils.displayCircleImage(getContext(), UserManager.getInst().getUserBeam().getAvatarUrl()
                    , mIvUserHeader, 0, 0, GlideUtils.getUserNormalOptions(), true);
        }
    }

    public void setData(double income, int rank) {
        if (mTvAllIncom == null) return;
        mTvAllIncom.setText(CommonHelper.form2(income) + "元");
        if (rank < 0) {
            mTvAllRank.setText(1000 + "+");
        } else {
            mTvAllRank.setText(rank + "");
        }
    }

    public void setViewShowStatus(boolean show) {
        viewShow(mIvUserHeader, show);
        viewShow(mTvAllImcomTitle, show);
        viewShow(mTvAllIncom, show);
        viewShow(mTvAllRankTitle, show);
        viewShow(mTvAllRank, show);
    }

    private void viewShow(View view, boolean show) {
        if(view==null) return;
        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

//    @Override
//    public void onActivityDestory() {
//        mIvUserHeader = null;
//        mTvAllImcomTitle = null;
//        mTvAllIncom = null;
//        mTvAllRankTitle = null;
//        mTvAllRank = null;
//    }
}
