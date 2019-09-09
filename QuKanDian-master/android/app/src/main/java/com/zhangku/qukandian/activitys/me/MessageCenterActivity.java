package com.zhangku.qukandian.activitys.me;

import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseTabActivity;
import com.zhangku.qukandian.bean.LocalMessageBean;
import com.zhangku.qukandian.bean.MessageTipBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.fragment.me.PlatformMessageFragment;
import com.zhangku.qukandian.fragment.me.UserMessageFragment;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.ClickTipObserver;
import com.zhangku.qukandian.protocol.GetMessageTipsProtocol;
import com.zhangku.qukandian.protocol.PutMessageTipProtocol;
import com.zhangku.qukandian.protocol.PutNewMessageTipProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/4/18.
 * 消息中心
 */

public class MessageCenterActivity extends BaseTabActivity implements ClickTipObserver.OnClickTipListener {
    private UserMessageFragment mUserMessageFragment;
    private PlatformMessageFragment mPlatformMessageFragment;
    private ArrayList<RelativeLayout> mTabLayouts = new ArrayList<>();
    private ArrayList<TextView> mTextViews = new ArrayList<>();
    private PutMessageTipProtocol mPutMessageTipProtocol;

    @Override
    protected String title() {
        return "消息中心";
    }

    @Override
    protected void addFragments() {
        mUserMessageFragment = new UserMessageFragment();
        mPlatformMessageFragment = new PlatformMessageFragment();

        mFragments.add(mUserMessageFragment);
        mFragments.add(mPlatformMessageFragment);

    }


    @Override
    protected void addTabList() {
        mTabNames.add("用户通知");
        mTabNames.add("平台公告");
    }

    @Override
    protected void initViews() {
        if (!UserManager.getInst().hadLogin()) {
            ActivityUtils.startToBeforeLogingActivity(this);
            finish();
            return;
        }
        super.initViews();
        setupTabIcons();
        int tab = 0;
        if(null != getIntent().getExtras()){
            tab = getIntent().getExtras().getInt("message_tab");
        }
        setCurrentTab(tab);
        ClickTipObserver.getInstance().addListener(this);
    }

    private GetMessageTipsProtocol mGetMessageTipsProtocol;

    private void setupTabIcons() {
        if (null == mGetMessageTipsProtocol) {
            mGetMessageTipsProtocol = new GetMessageTipsProtocol(this, new BaseModel.OnResultListener<List<MessageTipBean>>() {
                @Override
                public void onResultListener(List<MessageTipBean> response) {
                    mTabLayout.getTabAt(0).setCustomView(getTabView(0, response));
                    mTabLayout.getTabAt(1).setCustomView(getTabView(1, response));
                    int tabCount = mTabLayout.getTabCount();

                    for (int i = 0; i < tabCount; i++) {
                        if (response.size() > 0) {
                            if (response.get(i).getType() == 0) {
                                if (response.get(i).getCount() > 0) {
                                    mTabLayouts.get(0).setVisibility(View.VISIBLE);
                                    mTextViews.get(0).setText(response.get(i).getCount() + "");
                                } else {
                                    mTabLayouts.get(0).setVisibility(View.GONE);
                                    mTextViews.get(0).setText("");
                                }
                            } else {
                                if (response.get(i).getCount() > 0) {
                                    mTabLayouts.get(1).setVisibility(View.VISIBLE);
                                    mTextViews.get(1).setText(response.get(i).getCount() + "");
                                } else {
                                    mTabLayouts.get(1).setVisibility(View.GONE);
                                    mTextViews.get(1).setText("");
                                }
                            }

                        }

                        TabLayout.Tab tab = mTabLayout.getTabAt(i);
                        if (tab == null) {
                            continue;
                        }
                        Class c = tab.getClass();
                        try {
                            Field field = c.getDeclaredField("mView");
                            if (field == null) {
                                continue;
                            }
                            field.setAccessible(true);
                            final View view = (View) field.get(tab);
                            if (view == null) {
                                continue;
                            }
                            view.setTag(i);
                            view.setOnClickListener(new View.OnClickListener() {

                                @Override

                                public void onClick(View v) {
                                    int position = (int) view.getTag();

                                    new PutNewMessageTipProtocol(MessageCenterActivity.this, position, new BaseModel.OnResultListener<Object>() {
                                        @Override
                                        public void onResultListener(Object response) {
                                        }
                                        @Override
                                        public void onFailureListener(int code,String error) {
                                        }
                                    }).postRequest();

                                }

                            });

                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }
                    mGetMessageTipsProtocol = null;
                }

                @Override
                public void onFailureListener(int code,String error) {
                    mGetMessageTipsProtocol = null;
                }
            });
            mGetMessageTipsProtocol.postRequest();
        }
    }


    public View getTabView(final int tabIndex, final List<MessageTipBean> response) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab_view, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        TextView txt_number = (TextView) view.findViewById(R.id.number_title);
        RelativeLayout postion_layout = (RelativeLayout) view.findViewById(R.id.position_layout);
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        mTabLayouts.add(postion_layout);
        mTextViews.add(txt_number);



        txt_title.setText(mTabNames.get(tabIndex));
        img_title.setImageResource(R.mipmap.position_red);
        return view;
    }

    @Override
    public String setPagerName() {
        return "消息中心";
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        ClickTipObserver.getInstance().removeListener(this);
        mUserMessageFragment = null;
    }

    @Override
    public void onClickListener(int type, int number) {
        if (mTabLayouts.size() > 0) {
            mTabLayouts.get(type).setVisibility(View.GONE);
            switch (type) {
                case 0:
                    mUserMessageFragment.refresh();
                    break;
                case 1:
                    mPlatformMessageFragment.refresh();
                    break;
            }
        }
    }
}
