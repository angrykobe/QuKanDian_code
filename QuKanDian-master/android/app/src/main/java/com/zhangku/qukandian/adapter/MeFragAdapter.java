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

/**
 * Created by yuzuoning on 2017/8/28.
 */

public class MeFragAdapter extends BaseRecyclerViewAdapter<UserMenuConfig> {
    public MeFragAdapter(Context context, List<UserMenuConfig> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_for_me_bottom_item,parent,false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final UserMenuConfig bean) {
        NewHolder view = (NewHolder) holder;
        view.titleTV.setText(""+bean.getName());

        view.subtitleTV.setText(""+bean.getDescription());

        view.messageRemindIV.setVisibility(bean.isShow()?View.VISIBLE:View.GONE);

        if(dataIndex == 0){
            view.topView.setVisibility(View.VISIBLE);
        }else if(bean.getMenuType() != mBeans.get(dataIndex-1).getMenuType()){
            view.topView.setVisibility(View.VISIBLE);
        }else{
            view.topView.setVisibility(View.GONE);
        }
        if(TextUtils.isEmpty(bean.getImgSrc())){
            view.iconIV.setVisibility(View.GONE);
        }else{
            GlideUtils.displayImage(mContext,bean.getImgSrc(),view.iconIV);
            view.iconIV.setVisibility(View.VISIBLE);
        }
    }



    class NewHolder extends RecyclerView.ViewHolder{
        TextView titleTV;
        View messageRemindIV;
        View topView;
        TextView subtitleTV;
        ImageView iconIV;
        public NewHolder(View itemView) {
            super(itemView);
            titleTV =  itemView.findViewById(R.id.titleTV);
            iconIV =  itemView.findViewById(R.id.iconIV);
            messageRemindIV =  itemView.findViewById(R.id.messageRemindIV);
            subtitleTV =  itemView.findViewById(R.id.subtitleTV);
            topView =  itemView.findViewById(R.id.topView);
        }
    }

}
