package com.zhangku.qukandian.fragment.information;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.yilan.sdk.ui.category.ChannelFragment;
import com.yilan.sdk.ui.littlevideo.KSLittleVideoFragment;
import com.zhangku.qukandian.BaseNew.BaseFra;
import com.zhangku.qukandian.R;

public class HaoTuVideoFragment extends BaseFra {

    @Override
    protected void loadData(Context context) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_hao_tu_video;
    }

    @Override
    protected void initViews(View convertView) {
        FragmentManager manager = getActivity().getSupportFragmentManager();

        KSLittleVideoFragment littleVideoFragment = KSLittleVideoFragment.newInstance();
        manager.beginTransaction().replace(R.id.content, littleVideoFragment)
                .commitAllowingStateLoss();
        //带频道导航栏
//        ChannelFragment fragment = new ChannelFragment();
//        manager.beginTransaction().replace(R.id.content, fragment).commitAllowingStateLoss();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
