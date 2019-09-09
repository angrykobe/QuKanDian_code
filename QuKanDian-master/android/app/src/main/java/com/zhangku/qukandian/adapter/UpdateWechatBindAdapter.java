package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.WeChatBean;

import java.util.List;

/**
 * Created by yuzuoning on 2017/9/18.
 */

public class UpdateWechatBindAdapter extends BaseRecyclerViewAdapter<WeChatBean> {
    private OnSelectedListener mOnSelectedListener;
    public UpdateWechatBindAdapter(Context context,OnSelectedListener onSelectedListener, List<WeChatBean> beans) {
        super(context, beans);
        mOnSelectedListener = onSelectedListener;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new BindHolder(LayoutInflater.from(mContext).inflate(R.layout.item_update_bind_wechat_view,parent,false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, final int dataIndex, WeChatBean bean) {
        BindHolder view = (BindHolder) holder;
        view.mTvText.setText(bean.getNickName());
        view.mIvSelected.setSelected(bean.isSelected());

        view.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != mOnSelectedListener){
                    mOnSelectedListener.onSelectedListener(dataIndex);
                }
            }
        });
    }

    class BindHolder extends RecyclerView.ViewHolder{
        TextView mTvText;
        ImageView mIvSelected;
        LinearLayout mLayout;
        public BindHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView;
            mTvText = itemView.findViewById(R.id.item_update_bind_wechat_text);
            mIvSelected = (ImageView) itemView.findViewById(R.id.item_update_bind_wechat_selected);
        }
    }

    public interface OnSelectedListener{
        void onSelectedListener(int position);
    }
}
