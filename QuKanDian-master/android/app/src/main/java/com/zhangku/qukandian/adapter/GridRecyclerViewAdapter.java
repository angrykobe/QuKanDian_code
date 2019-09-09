package com.zhangku.qukandian.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.ChannelBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnItemMovedListener;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.protocol.ChannelDeleteProtocol;
import com.zhangku.qukandian.protocol.ChannelSortProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 17/p1/3.
 */

public class GridRecyclerViewAdapter extends RecyclerView.Adapter implements OnItemMovedListener {
    private List<ChannelBean> mMyChannels;
    private List<ChannelBean> mOtherChannels;
    private List<ChannelBean> mChannels = new ArrayList<>();
    public static final int MY_CHANNEL_NAME = 0;
    public static final int MY_CHANNELS = 1;
    public static final int OTHER_CHANNEL_NAME = 2;
    public static final int OTHER_CHANNELS = 3;

    private ItemTouchHelper mItemTouchHelper;

    boolean isEditMode = false;
    private Context mContext;
    private OnClickFinishListener mOnClickFinishListener;
    private ChannelDeleteProtocol mChannelDeleteProtocol;
    private ChannelSortProtocol mChannelSortProtocol;

    public GridRecyclerViewAdapter(Context context, List<ChannelBean> myChannels, List<ChannelBean> otherChannels, ItemTouchHelper itemTouchHelper, OnClickFinishListener onClickFinishListener) {
        mContext = context;
        this.mMyChannels = myChannels;
        this.mOtherChannels = otherChannels;
        this.mItemTouchHelper = itemTouchHelper;
        mOnClickFinishListener = onClickFinishListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case MY_CHANNEL_NAME:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_channel_header, parent, false);
                final MyChannelHeaderViewHolder myChannelHeaderViewHolder = new MyChannelHeaderViewHolder(view);

                return myChannelHeaderViewHolder;
            case MY_CHANNELS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_channel, parent, false);
                final MyChannelViewHolder myChannelViewHolder = new MyChannelViewHolder(view);
                final int position = myChannelViewHolder.getAdapterPosition();
                if (position != 1) {
                    myChannelViewHolder.mMyChannelName.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            isEditMode = !isEditMode;
                            mItemTouchHelper.startDrag(myChannelViewHolder);
                            View view1 = parent.getChildAt(0);
                            TextView textView = (TextView) view1.findViewById(R.id.tv_btn_edit);
                            if (textView != null) {
                                textView.setText("完成");
                            }

                            return true;
                        }
                    });
                }
                myChannelViewHolder.mMyChannelName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = myChannelViewHolder.getAdapterPosition();
                        RecyclerView recyclerView = (RecyclerView) parent;
                        if (isEditMode) {
                            if (position != 1) {
                                View targetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannels.size() + 2);
                                View currentView = recyclerView.getLayoutManager().findViewByPosition(position);
                                //如果目标targetView(indexOfChild>=0)在屏幕内，则需要添加动画效果；
                                // 如果不在屏幕内，那么recyclerview的notifyItemMoved会有向目标移动的动画
                                if (recyclerView.indexOfChild(targetView) >= 0) {
                                    //目标位置的坐标
                                    float targetX, targetY;
                                    int spanCount = ((GridLayoutManager) (recyclerView.getLayoutManager())).getSpanCount();
                                    //如果已选的item在最后一行的第一个位置，那么移动后高度会变化，所以目标View就是当前View的下一个view
                                    if ((mMyChannels.size() - 1) % spanCount == 0) {
                                        View preTargetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannels.size() + 1);
                                        targetX = preTargetView.getLeft();
                                        targetY = preTargetView.getTop();
                                    } else {
                                        targetX = targetView.getLeft();
                                        targetY = targetView.getTop();
                                    }
                                    startAnimation((RecyclerView) parent, currentView, targetX, targetY);
                                    moveMineToOthers(myChannelViewHolder);
                                } else {
                                    moveMineToOthers(myChannelViewHolder);
                                }
                            }

                        } else {
                            ChannelBean bean = (ChannelBean) myChannelViewHolder.itemView.getTag();
                            if(bean!=null){
                                int i = mMyChannels.indexOf(bean);
//                                ActivityUtils.startToMainActivity(mContext, Constants.TAB_INFORMATION, i);
                                ActivityUtils.startToMainActivity(mContext, Constants.TAB_INFORMATION, i,bean);
                            }else{
                                ActivityUtils.startToMainActivity(mContext, Constants.TAB_INFORMATION, position - 1);
                            }
                            ((Activity) mContext).finish();
                        }
                    }
                });
                return myChannelViewHolder;
            case OTHER_CHANNEL_NAME:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_channel_header, parent, false);
                return new OtherHeaderViewHolder(view);
            case OTHER_CHANNELS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_channel, parent, false);
                final OthersViewHolder othersViewHolder = new OthersViewHolder(view);
                othersViewHolder.mOtherChannelName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecyclerView recyclerView = (RecyclerView) parent;
                        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                        int currentPosition = othersViewHolder.getAdapterPosition();
                        View currentView = manager.findViewByPosition(currentPosition);
                        //目标位置的前一个
                        View preTargetView = manager.findViewByPosition(mMyChannels.size() - 1 + 1);
                        if (recyclerView.indexOfChild(preTargetView) >= 0) {
                            int targetX = preTargetView.getLeft();
                            int targetY = preTargetView.getTop();
                            int targetPosition = mMyChannels.size() - 1 + 2;
                            int spanCount = ((GridLayoutManager) manager).getSpanCount();
                            //目标位置在最后一行的第一个
                            if ((targetPosition - 1) % spanCount == 0) {
                                View targetView = manager.findViewByPosition(targetPosition);
                                targetX = targetView.getLeft();
                                targetY = targetView.getTop();
                            } else {
                                targetX += preTargetView.getWidth();
                                //最后一个item可见
                                if (manager.findLastVisibleItemPosition() == getItemCount() - 1) {
                                    //最后的item在最后一行的第一个
                                    if ((mOtherChannels.size() - 1) % spanCount == 0) {
                                        int firstVisiblePosition = manager.findFirstVisibleItemPosition();
                                        if (firstVisiblePosition == 0) {

                                        } else {
                                            targetY += preTargetView.getHeight();
                                        }

                                    }
                                }
                            }

                            //当前位置是最后一个并且目标位置不是每一行的第一个，则不会触发ItemAnimator,直接刷新界面
                            //因此此时要延时250执行notifyMove
                            if (currentPosition == manager.findLastVisibleItemPosition()
                                    && (targetPosition - 1) % spanCount != 0) {
                                moveOthersToMineWidthDelay(othersViewHolder);
                            } else {
                                moveOthersToMine(othersViewHolder);
                            }

                            startAnimation(recyclerView, currentView, targetX, targetY);


                        } else {
                            moveOthersToMine(othersViewHolder);
                        }


                    }
                });
                return othersViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyChannelViewHolder) {
            if (mMyChannels.size() > 0) {
                ChannelBean channelBean = mMyChannels.get(position - 1);
                ((MyChannelViewHolder) holder).mMyChannelName.setText(channelBean.getDisplayName());
                if (position == 1) {
                    ((MyChannelViewHolder) holder).mMyChannelName.setBackgroundResource(R.color.grey);
                } else {
                    if (isEditMode) {
                        ((MyChannelViewHolder) holder).mMyChannelName.setBackgroundResource(R.mipmap.channel_edit_sub_bg);
                    } else {
                        ((MyChannelViewHolder) holder).mMyChannelName.setBackgroundResource(R.color.grey);
                    }
                }
                holder.itemView.setTag(channelBean);
            }

        } else if (holder instanceof OthersViewHolder) {
            if(mOtherChannels.size() > 0){
                holder.itemView.setVisibility(View.VISIBLE);
                ChannelBean channelBean = mOtherChannels.get(position - 2 - mMyChannels.size());
                ((OthersViewHolder) holder).mOtherChannelName.setText(channelBean.getDisplayName());
                ((OthersViewHolder) holder).mOtherChannelName.setBackgroundResource(R.mipmap.channel_edit_add_bg);

                holder.itemView.setTag(channelBean);
            }
        } else if (holder instanceof MyChannelHeaderViewHolder) {
            if (isEditMode) {
                ((MyChannelHeaderViewHolder) holder).mTvEditBtn.setText("完成");
                ((MyChannelHeaderViewHolder) holder).mTvName.setText("长按拖动排序");
                ((MyChannelHeaderViewHolder) holder).mImageView.setVisibility(View.VISIBLE);
            } else {
                ((MyChannelHeaderViewHolder) holder).mTvEditBtn.setText("编辑");
                ((MyChannelHeaderViewHolder) holder).mTvName.setText("我的频道");
                ((MyChannelHeaderViewHolder) holder).mImageView.setVisibility(View.GONE);
            }
            ((MyChannelHeaderViewHolder) holder).mTvEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int i = mMyChannels.size() + mOtherChannels.size() + 2;
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return MY_CHANNEL_NAME;
        } else if (position > 0 && position < mMyChannels.size() + 1) {
            return MY_CHANNELS;
        } else if (position == mMyChannels.size() + 1 && mOtherChannels.size() > 0) {
            return OTHER_CHANNEL_NAME;
        } else if(mOtherChannels.size() > 0){
            return OTHER_CHANNELS;
//        }  else if(position>mMyChannels.size()+1 && position<mMyChannels.size() + mOtherChannels.size() + 2){
//            return OTHER_CHANNELS;
        }
        else {
            return OTHER_CHANNEL_NAME;
        }

    }

    private void moveMineToOthers(RecyclerView.ViewHolder viewHolder) {
        if (mMyChannels.size() > 6) {
            int startPosition = viewHolder.getAdapterPosition() - 1;
            ChannelBean entity = mMyChannels.get(startPosition);
            mMyChannels.remove(startPosition);
            mOtherChannels.add(0, entity);
            if (UserManager.getInst().hadLogin()) {
                if (null == mChannelDeleteProtocol) {
                    mChannelDeleteProtocol = new ChannelDeleteProtocol(mContext);
                    mChannelDeleteProtocol.getRusult(entity.getId(), false);
                }
            }
            entity.setYesNo(false);
            DBTools.updateChannel(entity);
            notifyItemMoved(viewHolder.getAdapterPosition(), mMyChannels.size() + 2);
        } else {
            isEditMode = false;
        }

    }

    private void moveOthersToMine(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        int startPosition = position - mMyChannels.size() - 2;
        ChannelBean entity = mOtherChannels.get(startPosition);
        if (UserManager.getInst().hadLogin()) {
            if (null == mChannelDeleteProtocol) {
                new ChannelDeleteProtocol(mContext).getRusult(entity.getId(), true);
            }
        }
        entity.setYesNo(true);
        DBTools.updateChannel(entity);
        mOtherChannels.remove(startPosition);
        mMyChannels.add(entity);
        notifyItemMoved(position, mMyChannels.size() - 1 + 1);
        mChannels.clear();
        mChannels.addAll(mMyChannels);
        mChannels.addAll(mOtherChannels);
        mOnClickFinishListener.onClickFinishListener(mChannels);
        notifyDataSetChanged();
    }

    private void edit() {
        isEditMode = !isEditMode;
        if (!isEditMode) {
            mChannels.clear();
            mChannels.addAll(mMyChannels);
            mChannels.addAll(mOtherChannels);
            mOnClickFinishListener.onClickFinishListener(mChannels);
        }
        notifyDataSetChanged();
    }

    private void moveOthersToMineWidthDelay(RecyclerView.ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
        int startPosition = position - mMyChannels.size() - 2;
        ChannelBean entity = mOtherChannels.get(startPosition);
        entity.setYesNo(true);
        DBTools.updateChannel(entity);
        mOtherChannels.remove(startPosition);
        mMyChannels.add(entity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyItemMoved(position, mMyChannels.size() - 1 + 1);

            }
        }, 250);
        mChannels.clear();
        mChannels.addAll(mMyChannels);
        mChannels.addAll(mOtherChannels);
        mOnClickFinishListener.onClickFinishListener(mChannels);
        notifyDataSetChanged();
//        edit();
    }

    private void startAnimation(final RecyclerView recyclerView, final View currentView, float targetX, float targetY) {
        final ImageView tempImageView = addTempView((ViewGroup) recyclerView.getParent(), recyclerView, currentView);
        TranslateAnimation animation =
                new TranslateAnimation
                        (TranslateAnimation.RELATIVE_TO_SELF, 0f,
                                TranslateAnimation.ABSOLUTE,
                                targetX - currentView.getLeft(),
                                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                                TranslateAnimation.ABSOLUTE,
                                targetY - currentView.getTop()
                        );
        animation.setDuration(360);
        animation.setFillAfter(true);
        currentView.setVisibility(View.INVISIBLE);
        tempImageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((ViewGroup) (recyclerView.getParent())).removeView(tempImageView);
                currentView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private ImageView addTempView(ViewGroup parent, RecyclerView recyclerView, View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        ImageView tempImageView = new ImageView(parent.getContext());
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        tempImageView.setImageBitmap(bitmap);
        view.setDrawingCacheEnabled(false);
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        int[] parentLocations = new int[2];
        recyclerView.getLocationOnScreen(parentLocations);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        params.setMargins(locations[0], locations[1] - parentLocations[1], 0, 0);
        parent.addView(tempImageView, params);
        return tempImageView;
    }


    @Override
    public void onItemMoved(int fromPosition, int toPosition) {
        if (toPosition != 1 && fromPosition != 1) {
            ChannelBean channelEntity = mMyChannels.get(fromPosition - 1);
//            if (UserManager.getInst().hadLogin()) {
//                new ChannelSortProtocol(mContext, channelEntity.getId(), toPosition).postRequest();
//            }
            mMyChannels.remove(fromPosition - 1);
            mMyChannels.add(toPosition - 1, channelEntity);
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    class MyChannelHeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTvEditBtn;
        TextView mTvName;

        public MyChannelHeaderViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_flag);
            mTvEditBtn = (TextView) itemView.findViewById(R.id.tv_btn_edit);
            mTvName = (TextView) itemView.findViewById(R.id.tv_channel);
        }
    }

    class MyChannelViewHolder extends RecyclerView.ViewHolder {

        TextView mMyChannelName;

        public MyChannelViewHolder(View itemView) {
            super(itemView);
            mMyChannelName = (TextView) itemView.findViewById(R.id.channel_my_text);
        }
    }

    class OtherHeaderViewHolder extends RecyclerView.ViewHolder {
        public OtherHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class OthersViewHolder extends RecyclerView.ViewHolder {

        TextView mOtherChannelName;

        public OthersViewHolder(View itemView) {
            super(itemView);
            mOtherChannelName = (TextView) itemView.findViewById(R.id.channel_other_text);
        }
    }

    public interface OnClickFinishListener {
        void onClickFinishListener(List<ChannelBean> list);
    }
}
