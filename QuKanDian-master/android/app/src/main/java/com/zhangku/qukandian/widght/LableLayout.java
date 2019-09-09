package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.LabelTermsBean;
import com.zhangku.qukandian.utils.ActivityUtils;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/24.
 */

public class LableLayout extends LinearLayout{
    LinearLayout mLableLayout;

    public LableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        ExitActivityObserver.getInst().addExitActivityObserverAction(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLableLayout = (LinearLayout) findViewById(R.id.lable_layout_layout);
    }

    public void setData(final List<LabelTermsBean> bean, final int from) {
        if (null != bean) {
            mLableLayout.removeAllViews();
            if (bean.size() > 0) {
                for (int i = 0; i < (bean.size() > 3 ? 3 : bean.size()); i++) {
                    final int position = i;
                    View lab = LayoutInflater.from(getContext()).inflate(R.layout.item_lab_text, this, false);
                    TextView text = (TextView) lab.findViewById(R.id.lab_text);
                    text.setText(bean.get(i).getName());
                    lab.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityUtils.startToSearchActivity(getContext(), bean.get(position).getName(),from);
                        }
                    });
                    mLableLayout.addView(lab);
                }
            }
        }
    }

//    @Override
//    public void onActivityDestory() {
//        mLableLayout.removeAllViews();
//        mLableLayout = null;
//    }

}
