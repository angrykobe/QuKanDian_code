package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.utils.AdCommonBannerHelp;

/**
 * Created by yuzuoning on 2017/4/17.
 */

public class IncomeDetailsHeaderView extends LinearLayout {
    private LinearLayout mLayoutAd;

    public IncomeDetailsHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLayoutAd = (LinearLayout) findViewById(R.id.layout_income_details_header_view_adview);
        AdCommonBannerHelp.getInstance().getAdResult(getContext(), mLayoutAd);
    }

}
