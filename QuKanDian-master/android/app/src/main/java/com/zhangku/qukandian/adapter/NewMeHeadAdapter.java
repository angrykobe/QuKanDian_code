package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.UserMenuConfig;
import com.zhangku.qukandian.utils.GlideUtils;

import java.util.List;

public class NewMeHeadAdapter extends BaseRecyclerViewAdapter<UserMenuConfig> {
    public NewMeHeadAdapter(Context context, List<UserMenuConfig> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_for_me_top_item,parent,false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final UserMenuConfig bean) {
        NewHolder view = (NewHolder) holder;
        view.titleTV.setText(""+bean.getName());
        if (bean.getName().equals("我的消息")){//我的消息 remindTV  特殊处理
            if(bean.isShow()){
                view.remindTV.setVisibility(View.VISIBLE);
                view.remindTV.setText("新消息");
            }else{
                view.remindTV.setVisibility(View.GONE);
            }
        }else {
            if(!TextUtils.isEmpty(bean.getDescription())){
                view.remindTV.setText(""+bean.getDescription());
            }else{
                view.remindTV.setVisibility(View.GONE);
            }
        }
        GlideUtils.displayCircleImage(mContext, bean.getImgSrc()
                , view.img, 0, 0, GlideUtils.getUserNormalOptions(), true);
    }



    class NewHolder extends RecyclerView.ViewHolder{
        TextView titleTV;
        TextView remindTV;
        ImageView img;
        public NewHolder(View itemView) {
            super(itemView);
            titleTV =  itemView.findViewById(R.id.titleTV);
            img =  itemView.findViewById(R.id.img);
            remindTV =  itemView.findViewById(R.id.remindTV);
        }
    }

}
