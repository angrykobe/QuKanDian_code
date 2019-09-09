package com.zhangku.qukandian.fragment.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhangku.qukandian.adapter.MessageAdapter;
import com.zhangku.qukandian.base.BaseLoadMoreFragment;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.LocalMessageBean;
import com.zhangku.qukandian.bean.MessageBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewPlatformMessageProtocol;
import com.zhangku.qukandian.utils.CommonHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuzuoning on 2017/4/18.
 */
public class PlatformMessageFragment extends BaseLoadMoreFragment implements MessageAdapter.OnClickRefreshListener {
    private MessageAdapter mAdapter;
    private ArrayList<MessageBean> mDatas = new ArrayList<>();
    private GetNewPlatformMessageProtocol mGetPlatformMessageProtocol;

    @Override
    protected void noNetword() {
        showEmptyNetword();
    }

    @Override
    public void loadData(Context context) {
        Date date = null;
        if (DBTools.getMessages().size() > 0) {
            LocalMessageBean bean = DBTools.getMessageDate(Constants.TYPE_VIDEO);
            if (null != bean) {
                try {
                    date = CommonHelper.stringToDate(bean.getCreationTime(), "yyyy-MM-dd'T'HH:mm:ssss");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        if (null == mGetPlatformMessageProtocol) {
            mGetPlatformMessageProtocol = new GetNewPlatformMessageProtocol(getContext(), date, Constants.TYPE_VIDEO, new BaseModel.OnResultListener<List<MessageBean>>() {
                @Override
                public void onResultListener(List<MessageBean> response) {
//                    List<MessageBean> been = DBTools.getMessages();
//                    List<MessageBean> array = new ArrayList<>();
//                    for (MessageBean itemMessageBean : been) {
//                        if (itemMessageBean.getType() == Constants.TYPE_VIDEO) {
//                            array.add(itemMessageBean);
//                        }
//                    }
                    if (response.size() > 0) {
                        mDatas.clear();
                        mDatas.addAll(response);
                        mAdapter.notifyDataSetChanged();
                        hideLoadingLayout();
                    } else {
                        showEmptyMessage();
                    }
                    mGetPlatformMessageProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    showLoadFail();
                    mGetPlatformMessageProtocol = null;
                }
            });
            mGetPlatformMessageProtocol.postRequest();
        }
    }

    @Override
    protected void initViews(View convertView) {
        super.initViews(convertView);
        mRecyclerView.removeOnScrollListener(mOnScrollListener);
    }

    @Override
    protected void onLoadingFailClick() {
        super.onLoadingFailClick();
        showLoading();
        loadData(getContext());
    }

    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        mAdapter = new MessageAdapter(getContext(), mDatas, this);
        return mAdapter;
    }

    @Override
    protected void loadMoreData(RecyclerView recyclerView, int dx, int dy) {
        if (mFootView != null) {
            mFootView.hide();
        }
    }

    @Override
    public String setPagerName() {
        return "平台公告";
    }

    @Override
    public void onCliclRefreshListener(int pisiton) {
        mDatas.get(pisiton).setReading(true);
        mAdapter.notifyItemChanged(pisiton);
    }

    public void refresh() {
        mAdapter.setRefresh(true);
        mAdapter.notifyDataSetChanged();
    }
}
