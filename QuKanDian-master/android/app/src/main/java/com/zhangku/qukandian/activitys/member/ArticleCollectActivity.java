package com.zhangku.qukandian.activitys.member;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.zhangku.qukandian.bean.CollectBean;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogDeleteFavorite;
import com.zhangku.qukandian.interfaces.OnClickSelectedLisntener;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.FarvoriteProtocol;
import com.zhangku.qukandian.protocol.PutNewCollectArticleProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.RecyclerViewDivider;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.FootView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by yuzuoning on 2017/4/3.
 * 我的收藏
 */

public class ArticleCollectActivity extends BaseLoadingActivity implements View.OnClickListener, OnClickSelectedLisntener {
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private RelativeLayout mRelativeLayoutSelect;
    private TextView mTvAllSelectBtn;
    private TextView mTvDeleteBtn;
    private int mPage = 1;
    private CollectAdapter mAdapter;
    private ArrayList<InformationBean> mDatas = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isLoaded = false;
    private FarvoriteProtocol mFarvoriteProtocol;
    private PutNewCollectArticleProtocol mSynchronizationCollectProtocol;
    private FootView mFootView;
    private LinearLayout mFootLayout;
    private boolean mIsEditState = false;
    private boolean mIsSelectAll = false;
    private ArrayList<InformationBean> mFavoritesArray = new ArrayList<>();

    @Override
    protected void initActionBarData() {
        setTitle("我的收藏");
    }

    @Override
    protected void actionMenuOnClick(int menuId) {
        super.actionMenuOnClick(menuId);
        mIsEditState = !mIsEditState;
        if (mIsEditState) {
            removeMenuItem(R.string.edit);
            removeMenuItem(R.string.cancel);
            addMenuItem(R.string.cancel);
            mRelativeLayoutSelect.setVisibility(View.VISIBLE);
        } else {
            removeMenuItem(R.string.cancel);
            removeMenuItem(R.string.edit);
            addMenuItem(R.string.edit);
            mFavoritesArray.clear();
            mIsSelectAll = false;
            mRelativeLayoutSelect.setVisibility(View.GONE);
        }
        deleteBtnState();
        mAdapter.showSelectsLayout(mIsEditState);
        mAdapter.setSelectedAll(mIsSelectAll);
        mTvAllSelectBtn.setSelected(mIsSelectAll);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new ArrayMap<>();
        map.put("to", "我的收藏");
        MobclickAgent.onEvent(this, "AllPv", map);
        if (isLoaded) {
            mPage = 1;
            loadData();
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (null == mFarvoriteProtocol) {
            mFarvoriteProtocol = new FarvoriteProtocol(this, Constants.SIZE, mPage, new BaseModel.OnResultListener<ArrayList<InformationBean>>() {
                @Override
                public void onResultListener(ArrayList<InformationBean> response) {
                    isLoaded = true;
                    if (response.size() > 0) {
                        removeMenuItem(R.string.edit);
                        removeMenuItem(R.string.cancel);
                        if (mIsEditState) {
                            addMenuItem(R.string.cancel);
                        } else {
                            addMenuItem(R.string.edit);
                        }
                        mPage++;
                        if (response.size() >= Constants.SIZE) {
                            mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
                        } else {
                            mSwipeMenuRecyclerView.removeOnScrollListener(mOnScrollListener);
                        }
                        mDatas.clear();
                        mDatas.addAll(response);
                        mAdapter.notifyDataSetChanged();
                        hideLoadingLayout();
                    } else {
                        removeMenuItem(R.string.edit);
                        removeMenuItem(R.string.cancel);
                        mRelativeLayoutSelect.setVisibility(View.GONE);
                        showEmptyCollect();
                    }
                    mFarvoriteProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    showLoadFail();
                    mFarvoriteProtocol = null;
                }
            });
            mFarvoriteProtocol.postRequest();
        }
    }

    @Override
    public void onLoadingFail() {
        super.onLoadingFail();
        showLoading();
        loadData();
    }

    @Override
    protected void initViews() {
//        if (!UserManager.getInst().hadLogin()) {
//            ActivityUtils.startToBeforeLogingActivity(this);
//            finish();
//            return;
//        }
        mRelativeLayoutSelect = (RelativeLayout) findViewById(R.id.article_recyclerview_select_layout);
        mTvAllSelectBtn = (TextView) findViewById(R.id.article_recyclerview_select_layout_all_select);
        mTvDeleteBtn = (TextView) findViewById(R.id.article_recyclerview_select_layout_delete);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.article_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mFootLayout = (LinearLayout) findViewById(R.id.article_recyclerview_foot);
        mSwipeMenuRecyclerView.setLayoutManager(mLinearLayoutManager);// 布局管理器。
        mSwipeMenuRecyclerView.addItemDecoration(new RecyclerViewDivider(this, 1,
                LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.grey_f2)));// 添加分割线。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        mAdapter = new CollectAdapter(this, mDatas, this);
        mSwipeMenuRecyclerView.setAdapter(mAdapter);
        mFootView = new FootView(this, mFootLayout);
        mFootLayout.addView(mFootView.getView());
        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mTvAllSelectBtn.setOnClickListener(this);
        mTvDeleteBtn.setOnClickListener(this);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0 && recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent() >= recyclerView.computeVerticalScrollRange()) {
                if (null != mFootView) {
                    mFootView.show();
                    mFootLayout.setVisibility(View.VISIBLE);
                }
                if (null == mFarvoriteProtocol) {
                    mFarvoriteProtocol = new FarvoriteProtocol(ArticleCollectActivity.this, Constants.SIZE, mPage, new BaseModel.OnResultListener<ArrayList<InformationBean>>() {
                        @Override
                        public void onResultListener(ArrayList<InformationBean> response) {
                            if (response.size() > 0) {
                                mPage++;
                                int start = mDatas.size();
                                mDatas.addAll(response);
                                mAdapter.notifyItemRangeChanged(start, response.size());
                            } else {
                                ToastUtils.showLongToast(ArticleCollectActivity.this, "没有更多数据");
                                mSwipeMenuRecyclerView.removeOnScrollListener(mOnScrollListener);
                            }
                            mFarvoriteProtocol = null;
                            if (null != mFootView) {
                                mFootView.hide();
                                mFootLayout.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailureListener(int code, String error) {
                            mFarvoriteProtocol = null;
                            if (null != mFootView) {
                                mFootView.hide();
                                mFootLayout.setVisibility(View.GONE);
                            }
                        }
                    });
                    mFarvoriteProtocol.postRequest();
                }
            }
        }
    };


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_article_collect_layout;
    }

    @Override
    public String setPagerName() {
        return "我的收藏";
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
                SwipeMenuItem deleteItem = new SwipeMenuItem(ArticleCollectActivity.this)
                        .setBackgroundDrawable(R.color.red_500)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

            }
        }
    };
    private PutNewCollectArticleProtocol mCollectArticleProtocol;
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

                DialogDeleteFavorite dialog = new DialogDeleteFavorite(ArticleCollectActivity.this, new DialogDeleteFavorite.OnClcilConfirmListener() {
                    @Override
                    public void onClcilConfirmListener(int i) {
                        if (null == mCollectArticleProtocol) {
                            CollectBean bean = new CollectBean();
                            bean.setPostId(mDatas.get(adapterPosition).getId());
                            bean.setTextType(mDatas.get(adapterPosition).getTextType());
                            bean.setZyId(mDatas.get(adapterPosition).getZyId());

                            mCollectArticleProtocol = new PutNewCollectArticleProtocol(ArticleCollectActivity.this, false, bean, new BaseModel.OnResultListener<Object>() {
                                @Override
                                public void onResultListener(Object response) {
                                    mPage = 1;
                                    loadData();
                                    ToastUtils.showShortToast(ArticleCollectActivity.this, "操作成功");
                                    mCollectArticleProtocol = null;
                                }

                                @Override
                                public void onFailureListener(int code, String error) {
                                    mCollectArticleProtocol = null;
                                }
                            });
                            mCollectArticleProtocol.postRequest();
                        }
                        mDatas.remove(adapterPosition);
                        mAdapter.notifyItemRemoved(adapterPosition);


                    }
                });
                dialog.show();
                dialog.setTitle("确定要删除这条收藏内容？");

            }
        }
    };


    @Override
    public int getLoadingViewParentId() {
        return R.id.activity_article_collect_latyou_loading_layout;
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        if (null != mAdapter) {
            mAdapter = null;
        }
        mLinearLayoutManager = null;
        if (null != mSwipeMenuRecyclerView) {
            mSwipeMenuRecyclerView.setAdapter(null);
            mSwipeMenuRecyclerView = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.article_recyclerview_select_layout_all_select:
                mIsSelectAll = !mIsSelectAll;
                mTvAllSelectBtn.setSelected(mIsSelectAll);
                mFavoritesArray.clear();
                if (mIsSelectAll) {
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).setSelected(true);
                        mFavoritesArray.add(mDatas.get(i));
                    }
                }
                mAdapter.setSelectedAll(mIsSelectAll);
                mAdapter.notifyDataSetChanged();
                deleteBtnState();
                break;
            case R.id.article_recyclerview_select_layout_delete:
                if (mFavoritesArray.size() > 0) {
                    DialogDeleteFavorite dialog = new DialogDeleteFavorite(this, new DialogDeleteFavorite.OnClcilConfirmListener() {
                        @Override
                        public void onClcilConfirmListener(int i) {
                            if (null == mSynchronizationCollectProtocol) {
                                List<CollectBean> list = new ArrayList<>();
                                for (InformationBean b : mFavoritesArray) {
                                    CollectBean bean = new CollectBean();
                                    bean.setZyId(b.getZyId());
                                    bean.setPostId(b.getPostId());
                                    bean.setTextType(b.getTextType());
                                    list.add(bean);
                                }
                                mSynchronizationCollectProtocol = new PutNewCollectArticleProtocol(ArticleCollectActivity.this, false, list, new BaseModel.OnResultListener<Object>() {
                                    @Override
                                    public void onResultListener(Object response) {
                                        mPage = 1;
                                        mIsEditState = !mIsEditState;
                                        mRelativeLayoutSelect.setVisibility(View.GONE);
                                        loadData();
                                        ToastUtils.showShortToast(ArticleCollectActivity.this, "操作成功");
                                        mSynchronizationCollectProtocol = null;
                                    }

                                    @Override
                                    public void onFailureListener(int code, String error) {
                                        mSynchronizationCollectProtocol = null;
                                    }
                                });
                                mSynchronizationCollectProtocol.postRequest();
                            }
                        }
                    });
                    dialog.show();
                    dialog.setTitle("确定要删除" + mFavoritesArray.size() + "条收藏内容？");
                } else {
                    ToastUtils.showShortToast(this, "请选择要删除的收藏内容");
                }
                break;
        }
    }

    @Override
    public void onClickSelectedLisntener(int position, boolean selected) {
        if (selected) {
            mFavoritesArray.add(mDatas.get(position));
        } else {
            mFavoritesArray.remove(mDatas.get(position));
        }
        if (mFavoritesArray.size() == mDatas.size()) {
            mTvAllSelectBtn.setSelected(true);
            mIsSelectAll = true;
        } else {
            mTvAllSelectBtn.setSelected(false);
            mIsSelectAll = false;
        }
        deleteBtnState();
    }

    private void deleteBtnState() {
        if (mFavoritesArray.size() > 0) {
            mTvDeleteBtn.setClickable(true);
            mTvDeleteBtn.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        } else {
            mTvDeleteBtn.setClickable(false);
            mTvDeleteBtn.setTextColor(ContextCompat.getColor(this, R.color.black_66));
        }
    }
}
