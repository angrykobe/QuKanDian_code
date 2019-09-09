package com.zhangku.qukandian.fragment.me;

import android.support.v4.util.ArrayMap;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.bean.RankBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetIncomeRankProtocol;

import java.util.Map;

/**
 * Created by yuzuoning on 2017/4/11.
 */

public class AllRankFragment extends BaseRankFrgament {
    private GetIncomeRankProtocol mGetIncomeRankProtocol;

    @Override
    protected void loadCurrentData() {
        if (null == mGetIncomeRankProtocol) {
            mGetIncomeRankProtocol = new GetIncomeRankProtocol(getContext(), new BaseModel.OnResultListener<RankBean>() {
                @Override
                public void onResultListener(RankBean response) {
                    if (response.getTopList().size() > 0) {
                        mDatas.clear();
                        mDatas.addAll(response.getTopList());
                        if (UserManager.getInst().hadLogin()) {
                            mLayoutRankHeader.setData(response.getCoinAccountSum(), response.getIndexNo());
                            mLayoutRankHeader.setViewShowStatus(true);
                        } else {
                            mLayoutRankHeader.setViewShowStatus(false);
                        }
                        mAdapter.notifyDataSetChanged();
                        hideLoadingLayout();
                    } else {
                        showEmptyMessage();
                    }
                    mGetIncomeRankProtocol = null;
                }

                @Override
                public void onFailureListener(int code,String error) {
                    showLoadFail();
                    mGetIncomeRankProtocol = null;
                }
            });
            mGetIncomeRankProtocol.postRequest();
        }
    }

    @Override
    protected void noNetword() {
        showEmptyNetword();
    }

    @Override
    protected void onLoadingFailClick() {
        super.onLoadingFailClick();
        showLoading();
        loadCurrentData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Map<String,String> map = new ArrayMap<>();
        map.put("to","收入排行页");
        MobclickAgent.onEvent(getContext(), "AllPv",map);
    }

    @Override
    public String setPagerName() {
        return "总排行榜";
    }
}
