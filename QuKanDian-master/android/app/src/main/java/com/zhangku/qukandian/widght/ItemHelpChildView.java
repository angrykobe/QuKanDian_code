package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.HelpBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.GlideUtils;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/19.
 */

public class ItemHelpChildView extends LinearLayout {
    LinearLayout mLinearLayout;

    public ItemHelpChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLinearLayout = (LinearLayout) findViewById(R.id.item_help_child_view_layout);

    }

    public void setData(List<HelpBean.DocHelpersBean> docHelpers){
        mLinearLayout.removeAllViews();
        for (int i = 0; i < docHelpers.size(); i++) {
            final HelpBean.DocHelpersBean bean = docHelpers.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_task_list_view,null,false);
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.item_task_list_view_task_layout);
            TextView mName = (TextView) view.findViewById(R.id.item_task_list_view_task_name);
            TextView mReward = (TextView) view.findViewById(R.id.item_task_list_view_task_reward);
            TextView mItemContent = (TextView) view.findViewById(R.id.item_task_list_view_item_content);
            TextView mItemDetails = (TextView) view.findViewById(R.id.item_task_list_view_item_details);
            ImageView mIcon = (ImageView) view.findViewById(R.id.item_task_list_view_task_gold_icon);
            final ImageView mImg = (ImageView) view.findViewById(R.id.item_task_list_view_item_img);
            final ImageView mArrow = (ImageView) view.findViewById(R.id.item_task_list_view_task_arrow);
            final LinearLayout mEx = (LinearLayout) view.findViewById(R.id.item_task_list_view_task_introduce);
            View line = view.findViewById(R.id.line);

            line.setVisibility(View.VISIBLE);
            mItemContent.setText(bean.getDescription());
            mName.setText(bean.getTitle());
            mReward.setVisibility(View.GONE);
            mIcon.setVisibility(View.GONE);
            mItemDetails.setVisibility(View.GONE);

            if(bean.isSelected()){
                bean.setSelected(true);
                mArrow.setSelected(true);
                mEx.setVisibility(View.VISIBLE);
            }else {
                bean.setSelected(false);
                mArrow.setSelected(false);
                mEx.setVisibility(View.GONE);
            }

            layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.isSelected()){
                        bean.setSelected(false);
                        mArrow.setSelected(false);
                        mEx.setVisibility(View.GONE);
                    }else {
                        bean.setSelected(true);
                        mArrow.setSelected(true);
                        mEx.setVisibility(View.VISIBLE);
                        if(null != bean.getDocImages() && bean.getDocImages().size() > 0){
                            mImg.setVisibility(View.VISIBLE);
                            GlideUtils.displayImage(getContext(),
                                    bean.getDocImages().get(0).getSrc(),mImg,
                                    Config.SCREEN_WIDTH, DisplayUtils.dip2px(getContext(),550));
                        }else {
                            mImg.setVisibility(View.GONE);
                        }
                    }
                }
            });
            mLinearLayout.addView(view);
        }
    }
}
