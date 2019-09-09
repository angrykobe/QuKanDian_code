package com.zhangku.qukandian.activitys.me;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.CollectAdapter;
import com.zhangku.qukandian.base.BaseLoadingActivity;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.dialog.DialogDeleteFavorite;
import com.zhangku.qukandian.interfaces.OnClickSelectedLisntener;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.utils.RecyclerViewDivider;
import com.zhangku.qukandian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/4/18.
 * 浏览历史
 */

public class BrowHistoryActivity extends BaseLoadingActivity implements OnClickSelectedLisntener, View.OnClickListener, DialogDeleteFavorite.OnClcilConfirmListener {
    private SwipeMenuRecyclerView mRecyclerView;
    private CollectAdapter mAdapter;
    protected GridLayoutManager mLinearLayoutManager;
    private List<InformationBean> mDatas = new ArrayList<>();
    private RelativeLayout mRelativeLayout;
    private TextView mTvSelectedAll;
    private TextView mTvDelete;
    private boolean mIsEditState = false;
    private boolean mIsSelectAll = false;
    private DialogDeleteFavorite mDialogDeleteFavorite;
    private ArrayList<InformationBean> mInformationBeans = new ArrayList<>();

    @Override
    protected void initActionBarData() {
        setTitle("浏览历史");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new ArrayMap<>();
        map.put("to", "浏览历史");
        MobclickAgent.onEvent(this, "AllPv", map);
    }

    @Override
    protected void actionMenuOnClick(int menuId) {
        mIsEditState = !mIsEditState;
        if (mIsEditState) {
            removeMenuItem(R.string.edit);
            removeMenuItem(R.string.cancel);
            addMenuItem(R.string.cancel);
            mRelativeLayout.setVisibility(View.VISIBLE);
        } else {
            removeMenuItem(R.string.cancel);
            removeMenuItem(R.string.edit);
            addMenuItem(R.string.edit);
            mInformationBeans.clear();
            mIsSelectAll = false;
            mRelativeLayout.setVisibility(View.GONE);
        }
        mAdapter.showSelectsLayout(mIsEditState);
        mAdapter.setSelectedAll(mIsSelectAll);
        mTvSelectedAll.setSelected(mIsSelectAll);
        mAdapter.notifyDataSetChanged();
        deleteBtnState();
    }

    @Override
    protected void initViews() {
        hideLoadingLayout();
        mDatas.clear();
        mDatas.addAll(DBTools.getFarvorite());
        if (mDatas.size() > 0) {
            addMenuItem(R.string.edit);
        }
        mDialogDeleteFavorite = new DialogDeleteFavorite(this, this);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_brow_history_selecte_layout);
        mTvSelectedAll = (TextView) findViewById(R.id.activity_brow_history_selecte_all_layout);
        mTvDelete = (TextView) findViewById(R.id.activity_brow_history_selecte_delete_layout);
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.activity_brow_history_layout_recyclerview);
        mLinearLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, 1, LinearLayoutManager.HORIZONTAL
                , 0.5f, ContextCompat.getColor(this, R.color.grey_e5)));
        mAdapter = new CollectAdapter(this, mDatas, this);
        mLinearLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mLinearLayoutManager.getSpanCount();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        emptyVerification();
        mRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);

        mTvSelectedAll.setOnClickListener(this);
        mTvDelete.setOnClickListener(this);
    }

    private void emptyVerification() {
        if (DBTools.getFarvorite().size() == 0) {
            removeMenuItem(R.string.cancel);
            removeMenuItem(R.string.edit);
            mRelativeLayout.setVisibility(View.GONE);
            showEmptyCollect("世界那么大，您要多看看");
        } else {
            removeMenuItem(R.string.cancel);
            removeMenuItem(R.string.edit);
            addMenuItem(R.string.edit);
            mRelativeLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(BrowHistoryActivity.this)
                        .setBackgroundDrawable(R.color.red_500)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

            }
        }
    };
    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, final int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
            if (menuPosition == 0) {// 删除按钮被点击。
                mDialogDeleteFavorite.show();
                mDialogDeleteFavorite.setTitle("确定要删除这条内容？", adapterPosition);
            }
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_brow_history_layout;
    }

    @Override
    public String setPagerName() {
        return "浏览历史";
    }

    @Override
    public int getLoadingViewParentId() {
        return R.id.activity_brow_history_layout_loading;
    }

    @Override
    public void onClickSelectedLisntener(int position, boolean selected) {
        if (selected) {
            mInformationBeans.add(mDatas.get(position));
        } else {
            mInformationBeans.remove(mDatas.get(position));
        }
        if(mInformationBeans.size() == mDatas.size()){
            mTvSelectedAll.setSelected(true);
            mIsSelectAll = true;
        }else {
            mTvSelectedAll.setSelected(false);
            mIsSelectAll = false;
        }
        deleteBtnState();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_brow_history_selecte_all_layout:
                mIsSelectAll = !mIsSelectAll;
                mTvSelectedAll.setSelected(mIsSelectAll);
                mInformationBeans.clear();
                if(mIsSelectAll){
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).setSelected(mIsSelectAll);
                        mInformationBeans.add(mDatas.get(i));
                    }
                }
                mAdapter.setSelectedAll(mIsSelectAll);
                mAdapter.notifyDataSetChanged();
                deleteBtnState();
                break;
            case R.id.activity_brow_history_selecte_delete_layout:
                if (mInformationBeans.size() > 0) {
                    mDialogDeleteFavorite.show();
                    mDialogDeleteFavorite.setTitle("确定要删除" + mInformationBeans.size() + "条内容？", -1);
                } else {
                    ToastUtils.showShortToast(this, "请选择要删除的内容");
                }
                break;
        }
    }

    @Override
    public void onClcilConfirmListener(int position) {
        if (position < 0) {
            if(mInformationBeans.size() >= 20){
                DBTools.deletaAllFarvorite();
                mDatas.clear();
            }else {
                for (int i = 0; i < mInformationBeans.size(); i++) {
                    DBTools.deleteFarvorite(mInformationBeans.get(i));
                    mDatas.remove(mInformationBeans.get(i));
                }
            }
            mInformationBeans.clear();
            mAdapter.showSelectsLayout(false);
            mAdapter.notifyDataSetChanged();
            mDialogDeleteFavorite.dismiss();
            emptyVerification();
        } else {
            DBTools.deleteFarvorite(mDatas.get(position));
            mDatas.remove(position);
            mAdapter.notifyDataSetChanged();
            mDialogDeleteFavorite.dismiss();
            emptyVerification();
        }
    }
    private void deleteBtnState() {
        if(mInformationBeans.size() > 0){
            mTvDelete.setClickable(true);
            mTvDelete.setTextColor(ContextCompat.getColor(this,R.color.black_33));
        }else {
            mTvDelete.setClickable(false);
            mTvDelete.setTextColor(ContextCompat.getColor(this,R.color.black_66));
        }
    }
}
