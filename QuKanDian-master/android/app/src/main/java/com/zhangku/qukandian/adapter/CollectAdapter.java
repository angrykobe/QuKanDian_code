package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.interfaces.OnClickSelectedLisntener;
import com.zhangku.qukandian.utils.LayoutInflaterUtils;
import com.zhangku.qukandian.widght.ItemInformationAdapterNonePictureLayout;
import com.zhangku.qukandian.widght.ItemInformationAdapterOnePictureLayout;
import com.zhangku.qukandian.widght.ItemInformationAdapterThreePictureLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yuzuoning on 2017/4/19.
 */

public class CollectAdapter extends SwipeMenuAdapter<RecyclerView.ViewHolder> {
    private List<InformationBean> mBeans = new ArrayList<>();
    private Context mContext;
    private static final int TYPE_NONE = 5;
    private static final int TYPE_ONE = 6;
    private static final int TYPE_THREE = 7;
    private boolean mShowSelectLayout = false;
    private boolean mSelecetdAll = false;
    private OnClickSelectedLisntener mOnClickSelectedLisntener;

    public CollectAdapter(Context context, List<InformationBean> beans,OnClickSelectedLisntener onClickSelectedLisntener) {
        mBeans = beans;
        mContext = context;
        mOnClickSelectedLisntener = onClickSelectedLisntener;
    }


    @Override
    public int getItemViewType(int position) {
        return returnTypeView(position);
    }

    private int returnTypeView(int position) {
        if (mBeans.get(position).getPostTextImages().size() >= 3) {
            if (mBeans.get(position).getId() % 3 != 0) {
                return TYPE_ONE;
            } else {
                return TYPE_THREE;
            }
        } else if (mBeans.get(position).getPostTextImages().size() >= 1) {
            return TYPE_ONE;
        } else {
            return TYPE_NONE;
        }
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_NONE:
                view = LayoutInflaterUtils.inflateView(mContext,
                        R.layout.item_information_adapter_none_picture_layout);
                break;
            case TYPE_ONE:
                view = LayoutInflaterUtils.inflateView(mContext,
                        R.layout.item_information_adapter_one_pic_layout);
                break;
            case TYPE_THREE:
                view = LayoutInflaterUtils.inflateView(mContext,
                        R.layout.item_information_adapter_three_picture_layout);
                break;
        }
        return view;
    }

    @Override
    public RecyclerView.ViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        RecyclerView.ViewHolder view = null;
        switch (viewType) {
            case TYPE_NONE:
                view = new NoneViewHolder(realContentView);
                break;
            case TYPE_ONE:
                view = new OneViewHolder(realContentView);
                break;
            case TYPE_THREE:
                view = new ThreeViewHolder(realContentView);
                break;
        }
        return view;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NoneViewHolder) {
            NoneViewHolder view = (NoneViewHolder) holder;
            view.mNonePictureView.setData(mBeans.get(position), false,position);
            view.mNonePictureView.showSelectLayout(mShowSelectLayout);
            view.mNonePictureView.setOnClickSelectedLisntener(mOnClickSelectedLisntener);
            view.mNonePictureView.selecetdAll(mSelecetdAll);
        } else if (holder instanceof OneViewHolder) {
            OneViewHolder view = (OneViewHolder) holder;
            view.mOnePictureView.setData(mBeans.get(position), false,position);
            view.mOnePictureView.showSelectLayout(mShowSelectLayout);
            view.mOnePictureView.setOnClickSelectedLisntener(mOnClickSelectedLisntener);
            view.mOnePictureView.selecetdAll(mSelecetdAll);
        } else if (holder instanceof ThreeViewHolder) {
            ThreeViewHolder view = (ThreeViewHolder) holder;
            view.mThreePictureView.setData(mBeans.get(position), false,position);
            view.mThreePictureView.showSelectLayout(mShowSelectLayout);
            view.mThreePictureView.setOnClickSelectedLisntener(mOnClickSelectedLisntener);
            view.mThreePictureView.selecetdAll(mSelecetdAll);
        }

    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    public void showSelectsLayout(boolean show) {
        mShowSelectLayout = show;
    }

    public void setSelectedAll(boolean all) {
        mSelecetdAll = all;
    }

    class NoneViewHolder extends RecyclerView.ViewHolder {
        ItemInformationAdapterNonePictureLayout mNonePictureView;

        public NoneViewHolder(View itemView) {
            super(itemView);
            mNonePictureView = (ItemInformationAdapterNonePictureLayout) itemView.findViewById(R.id.item_information_adapter_none_pic_layout);
        }
    }

    class OneViewHolder extends RecyclerView.ViewHolder {
        ItemInformationAdapterOnePictureLayout mOnePictureView;

        public OneViewHolder(View itemView) {
            super(itemView);
            mOnePictureView = (ItemInformationAdapterOnePictureLayout) itemView.findViewById(R.id.item_information_adapter_one_pic_layout);
        }
    }

    class ThreeViewHolder extends RecyclerView.ViewHolder {
        ItemInformationAdapterThreePictureLayout mThreePictureView;

        public ThreeViewHolder(View itemView) {
            super(itemView);
            mThreePictureView = (ItemInformationAdapterThreePictureLayout) itemView.findViewById(R.id.item_information_adapter_three_pic_layout);
        }
    }

}
