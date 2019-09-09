package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdk.searchsdk.DKSearch;
import com.sdk.searchsdk.SearchView;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.UserMenuConfig;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.GlideUtils;

import java.util.List;

public class TaskMenuAdapter extends BaseRecyclerViewAdapter<UserMenuConfig> {
    public TaskMenuAdapter(Context context, List<UserMenuConfig> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new TaskMenuAdapter.NewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_for_me_top_item,parent,false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final UserMenuConfig bean) {
        TaskMenuAdapter.NewHolder view = (TaskMenuAdapter.NewHolder) holder;
        view.titleTV.setText(""+bean.getName());
        if(!TextUtils.isEmpty(bean.getDescription())){
            view.remindTV.setText(""+bean.getDescription());
        }else{
            view.remindTV.setVisibility(View.GONE);
        }
        GlideUtils.displayCircleImage(mContext, bean.getImgSrc()
                , view.img, 0, 0, GlideUtils.getUserNormalOptions(), true);
        //云告-搜索赚
        if(AdConfig.yungao_string.equals(bean.getName())){
            view.mSearchView.setVisibility(View.VISIBLE);
            view.mSearchView.setAlpha(0);//设置透明，用产品设计的样式显示
            String id = UserManager.getInst().getUserBeam().getId() + "";
            DKSearch.setUesrId(mContext,id);
            view.mSearchView.updateRewardCount();
            view.iv_search_hot.setVisibility(View.VISIBLE);
        }else {
            view.mSearchView.setVisibility(View.GONE);
            view.iv_search_hot.setVisibility(View.GONE);
        }
    }



    class NewHolder extends RecyclerView.ViewHolder{
        TextView titleTV;
        TextView remindTV;
        ImageView img;
        ImageView iv_search_hot;
        SearchView mSearchView;
        public NewHolder(View itemView) {
            super(itemView);
            titleTV =  itemView.findViewById(R.id.titleTV);
            img =  itemView.findViewById(R.id.img);
            iv_search_hot =  itemView.findViewById(R.id.iv_search_hot);
            remindTV =  itemView.findViewById(R.id.remindTV);
            mSearchView =  itemView.findViewById(R.id.mSearchView);
        }
    }
}
