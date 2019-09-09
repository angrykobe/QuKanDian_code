package com.zhangku.qukandian.activitys.information;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.GridRecyclerViewAdapter;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.ChannelBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.observer.ChannelChangerObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/3/26.
 * 频道管理
 */

public class InformationChannelEditActivity extends BaseTitleActivity implements GridRecyclerViewAdapter.OnClickFinishListener {
    private RecyclerView mRecyclerView;
    private List<ChannelBean> myChannels = new ArrayList<>();
    private List<ChannelBean> mOtherChannels = new ArrayList<>();
    private List<ChannelBean> mChannels = new ArrayList<>();
    private GridRecyclerViewAdapter mAdapter;

    @Override
    protected void initActionBarData() {
        setTitle("频道管理");
        hideBackBtn();
        addMenuItem(R.mipmap.channel_edit_cancel_icon);
    }

    @Override
    protected void actionMenuOnClick(int menuId) {
        super.actionMenuOnClick(menuId);
        finish();
    }

    @Override
    protected void initViews() {
        myChannels.clear();
        mChannels = DBTools.queryByChannel(Constants.TYPE_NEW);
        ChannelBean re = new ChannelBean();
        re.setDisplayName("推荐");
        re.setKindType(Constants.TYPE_NEW);
        re.setYesNo(true);
        myChannels.add(re);
        for (ChannelBean bean: mChannels) {
            if(  Constants.HIGH_PRICE_NAME.equals(bean.getName()) ){//高价文
            }else if(bean.isYesNo()){
                myChannels.add(bean);
            }else {
                mOtherChannels.add(bean);
            }
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.channel_recyclerview);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(manager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelperCallback());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mAdapter = new GridRecyclerViewAdapter(this, myChannels, mOtherChannels, itemTouchHelper,this);
        mRecyclerView.setAdapter(mAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int realPosition = mAdapter.getItemViewType(position);
                if (realPosition == GridRecyclerViewAdapter.MY_CHANNEL_NAME ||
                        realPosition == GridRecyclerViewAdapter.OTHER_CHANNEL_NAME) {
                    return 3;
                } else {
                    return 1;
                }

            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_information_channel_edit_layout;
    }

    @Override
    public String setPagerName() {
        return "频道管理";
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        myChannels.clear();
        myChannels = null;
        mOtherChannels.clear();
        mOtherChannels = null;
        mChannels.clear();
        mChannels = null;
        mAdapter = null;
        mRecyclerView.setAdapter(null);
        mRecyclerView = null;
    }


    @Override
    public void onClickFinishListener(List<ChannelBean> list) {
        list.remove(0);
        DBTools.addToChannels(list,Constants.TYPE_NEW);
        myChannels.clear();
        mOtherChannels.clear();
        mChannels = DBTools.queryByChannel(Constants.TYPE_NEW);

        ChannelBean re = new ChannelBean();
        re.setDisplayName("推荐");
        re.setKindType(Constants.TYPE_NEW);
        re.setYesNo(true);
        myChannels.add(re);


        ChannelBean highPriceBean = null;
        for (ChannelBean bean: mChannels) {
            if(Constants.HIGH_PRICE_NAME.equals(bean.getName())){//高价文id
                highPriceBean = bean;
            }else if(bean.isYesNo()){
                myChannels.add(bean);
            }else {
                mOtherChannels.add(bean);
            }
        }
        mAdapter.notifyDataSetChanged();
        if(highPriceBean != null){
            list.add(highPriceBean);
        }
        ChannelChangerObserver.getInstance().notifys(list,true);
    }
}
