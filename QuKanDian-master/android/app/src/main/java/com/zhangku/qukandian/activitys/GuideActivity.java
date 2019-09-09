package com.zhangku.qukandian.activitys;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseActivity;
import com.zhangku.qukandian.bean.QukandianBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewRuleProtocol;
import com.zhangku.qukandian.protocol.GetReadTipsPro;
import com.zhangku.qukandian.protocol.GetTestMissionProtocol;
import com.zhangku.qukandian.protocol.GetTokenProtocol;
import com.zhangku.qukandian.protocol.QukandianNewProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by yuzuoning on 2017/5/9.
 */

public class GuideActivity extends BaseActivity {
    private ViewPager mViewPager;
    private ImageView mPostion01;
    private ImageView mPostion02;
    private ImageView mPostion03;
    private TextView mTvBtn;
    private int[] image = {R.mipmap.guide01, R.mipmap.guide02, R.mipmap.guide03};
    private ArrayList<View> mViews = new ArrayList<>();
    private ArrayList<View> mPositions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        hideActionBarView();
        mViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        mPostion01 = (ImageView) findViewById(R.id.guid_position_01);
        mPostion02 = (ImageView) findViewById(R.id.guid_position_02);
        mPostion03 = (ImageView) findViewById(R.id.guid_position_03);
        mTvBtn = (TextView) findViewById(R.id.guide_btn);

        mPositions.add(mPostion01);
        mPositions.add(mPostion02);
        mPositions.add(mPostion03);
        for (int i = 0; i < image.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(image[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mViews.add(iv);

        }
        mPositions.get(0).setSelected(true);
        GuildPagerAdapter adapter = new GuildPagerAdapter(mViews);
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mPositions.get(0).setSelected(true);
                        mPositions.get(1).setSelected(false);
                        mPositions.get(2).setSelected(false);
                        findViewById(R.id.positionView).setVisibility(View.VISIBLE);
                        mTvBtn.setVisibility(View.GONE);
                        break;
                    case 1:
                        mPositions.get(0).setSelected(false);
                        mPositions.get(1).setSelected(true);
                        mPositions.get(2).setSelected(false);
                        findViewById(R.id.positionView).setVisibility(View.VISIBLE);
                        mTvBtn.setVisibility(View.GONE);
                        break;
                    case 2:
                        mPositions.get(0).setSelected(false);
                        mPositions.get(1).setSelected(false);
                        mPositions.get(2).setSelected(true);
                        findViewById(R.id.positionView).setVisibility(View.GONE);
                        mTvBtn.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSharedPreferences.getInstance().putBoolean(Constants.FIRST_LOGIN,false);
                ActivityUtils.startToMainActivity(GuideActivity.this, 0, 0);
                finish();
            }
        });


        new GetTokenProtocol(this, new BaseModel.OnResultListener<Boolean>() {
            @Override
            public void onResultListener(Boolean response) {
                //获取测试任务
                new GetTestMissionProtocol(GuideActivity.this, new BaseModel.OnResultListener() {
                    @Override
                    public void onResultListener(Object response) {
                    }
                    @Override
                    public void onFailureListener(int code, String error) {
                    }
                }).postRequest();
                new GetReadTipsPro(GuideActivity.this,null).postRequest();
                new QukandianNewProtocol(GuideActivity.this, null).postRequest();
                new GetNewRuleProtocol(GuideActivity.this, null).postRequest();
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).getClientToken();

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_guide_layout;
    }

    @Override
    public String setPagerName() {
        return "引导页";
    }

    public class GuildPagerAdapter extends PagerAdapter {
        List<View> list;

        public GuildPagerAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
