package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.UserLevelInforBean;
import com.zhangku.qukandian.utils.FileBuildForBugUtils;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/4/17
 * 你不注释一下？
 */
public class UserLevelInforAdapter extends BaseRecyclerViewAdapter<UserLevelInforBean> {

    public UserLevelInforAdapter(Context context, List<UserLevelInforBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_user_level_infor,parent,false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, UserLevelInforBean bean) {
        NewHolder newHolder = (NewHolder) holder;
        if(dataIndex==0){
            newHolder.topView.setVisibility(View.VISIBLE);
        }else{
            FileBuildForBugUtils.Log("mBeans"+mBeans.size() + " == "+ dataIndex);
            if(mBeans.get(dataIndex).getDate().equals(mBeans.get(dataIndex-1).getDate())){
                newHolder.topView.setVisibility(View.GONE);
            }else{
                newHolder.topView.setVisibility(View.VISIBLE);
            }
        }

        newHolder.timeTV.setText(""+bean.getDate());
        newHolder.titleTV.setText(""+bean.getDisplayDesc());
        if(bean.getExp()>=0){
            newHolder.expTV.setText("+"+bean.getExp()+ "经验值");
        }else{
            newHolder.expTV.setText(""+bean.getExp()+ "经验值");
        }
    }


    class NewHolder extends RecyclerView.ViewHolder{
        View topView;
        TextView timeTV;
        TextView titleTV;
        TextView expTV;
        public NewHolder(View itemView) {
            super(itemView);
            topView =  itemView.findViewById(R.id.topView);
            timeTV =  itemView.findViewById(R.id.timeTV);
            titleTV =  itemView.findViewById(R.id.titleTV);
            expTV =  itemView.findViewById(R.id.expTV);
        }
    }
}
