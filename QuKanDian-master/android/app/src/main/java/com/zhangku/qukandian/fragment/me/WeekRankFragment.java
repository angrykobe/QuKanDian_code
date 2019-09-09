package com.zhangku.qukandian.fragment.me;

/**
 * Created by yuzuoning on 2017/4/11.
 */

public class WeekRankFragment extends BaseRankFrgament {
    @Override
    protected void loadCurrentData() {

        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void noNetword() {
        showEmptyNetword();
    }

    @Override
    public String setPagerName() {
        return "周排行榜";
    }
}
