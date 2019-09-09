package com.zhangku.qukandian.activitys.me;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.BaseNew.BaseTitleAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.UpLevelResBean;
import com.zhangku.qukandian.bean.UserLevelBean;
import com.zhangku.qukandian.dialog.DialogUpLevel;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetUserLevelProtocol;
import com.zhangku.qukandian.protocol.PutUpLevelProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.widght.UserLevelView;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/7
 * 你不注释一下？
 */
public class UserLevelAct extends BaseTitleAct implements UserManager.IOnGoldChangeListener {

    private TextView mJurisdiction1;
    private TextView mJurisdiction2;
    private TextView mJurisdiction3;
    private TextView mJurisdiction4;
    private TextView mJurisdiction5;
    private TextView mJurisdiction6;
    private TextView mJurisdiction7;
    private TextView mJurisdiction8;
    private TextView mJurisdiction9;
    private TextView mJurisdiction10;
    private TextView mJurisdiction11;
    private ImageView userPhotoIV;
    private ImageView userHeadwearIV;
    private UserLevelView mUserLevelView;
    private TextView mLeftLevelTV;
    private TextView mRightLevelTV;
    private ProgressBar processBar;
    private TextView mNeedExperienceTV;
    private ImageView mShowMoreIconIV;
    private View mBottomIconView;
    private PutUpLevelProtocol putUpLevelProtocol;
    private UserLevelBean bean;

    @Override
    protected void loadData() {
        new GetUserLevelProtocol(this, new BaseModel.OnResultListener<UserLevelBean>() {
            @Override
            public void onResultListener(UserLevelBean response) {
                //等级提升
                if (response.getGrade() < response.getTGrade() && UserManager.getInst().getUserBeam().isLevelGrand()) {
                    showUpLevelDialog();
                }
                bean = response;
                UserLevelBean.LevelInfoBean levelInfo = response.getLevelInfo();//用户目前等级
                UserLevelBean.NextLevelInfoBean nextLevelInfo = response.getNextLevelInfo();//用户下一个等级
                //用户权限
                setJurisdictionImg(mJurisdiction1, levelInfo.isHeadwearFlag() ? R.mipmap.jurisdiction_1_get : R.mipmap.jurisdiction_1_no);
                setJurisdictionImg(mJurisdiction2, levelInfo.isGoldFlag() ? R.mipmap.jurisdiction_2_get : R.mipmap.jurisdiction_2_no);
                setJurisdictionImg(mJurisdiction3, levelInfo.isQuickFlag() ? R.mipmap.jurisdiction_3_get : R.mipmap.jurisdiction_3_no);
                setJurisdictionImg(mJurisdiction4, levelInfo.isYdFlag() ? R.mipmap.jurisdiction_4_get : R.mipmap.jurisdiction_4_no);
                setJurisdictionImg(mJurisdiction5, levelInfo.isBetaFlag() ? R.mipmap.jurisdiction_5_get : R.mipmap.jurisdiction_5_no);
                setJurisdictionImg(mJurisdiction6, levelInfo.isHbFlag() ? R.mipmap.jurisdiction_6_get : R.mipmap.jurisdiction_6_no);
                setJurisdictionImg(mJurisdiction7, levelInfo.isXyFlag() ? R.mipmap.jurisdiction_7_get : R.mipmap.jurisdiction_7_no);
                setJurisdictionImg(mJurisdiction8, levelInfo.isQdFlag() ? R.mipmap.jurisdiction_8_get : R.mipmap.jurisdiction_8_no);
                setJurisdictionImg(mJurisdiction9, levelInfo.isBxFlag() ? R.mipmap.jurisdiction_9_get : R.mipmap.jurisdiction_9_no);
                setJurisdictionImg(mJurisdiction10, levelInfo.isRsFlag() ? R.mipmap.jurisdiction_10_get : R.mipmap.jurisdiction_10_no);
                setJurisdictionImg(mJurisdiction11, levelInfo.isEwtxFlag() ? R.mipmap.jurisdiction_11_get : R.mipmap.jurisdiction_11_no);

                mUserLevelView.setLevel(response.getGrade(), levelInfo.getDisplayName());//用户等级

                if (!TextUtils.isEmpty(response.getUpLevelDesc())) {
                    //所需经验或者条件
                    mNeedExperienceTV.setText(response.getUpLevelDesc() + " >>");
                }
                userHeadwearIV.setVisibility(levelInfo.isHeadwearFlag() ? View.VISIBLE : View.GONE);//用户头饰

                mLeftLevelTV.setText("" + levelInfo.getDisplayName());//左边等级昵称
                float startExp = response.getGrade() == 0 ? 0 : response.getLevelInfo().getExp();
                if (nextLevelInfo == null) {
                    mLeftLevelTV.setVisibility(View.INVISIBLE);
                    mRightLevelTV.setVisibility(View.INVISIBLE);
                    processBar.setVisibility(View.INVISIBLE);
                    mNeedExperienceTV.setText("恭喜达到最高级！");
                } else {
                    mRightLevelTV.setText("" + nextLevelInfo.getDisplayName());//右边等级昵称
                    float i = nextLevelInfo.getExp() - startExp;
                    processBar.setMax((int) i);//经验值目前进度
                }
                processBar.setProgress((int) (response.getExp() - startExp));//经验条
                // if (nextLevelInfo != null && response.getExp() > nextLevelInfo.getExp() && TextUtils.isEmpty(response.getUpLevelDesc()))
                //     showUpLevelDialog();
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
    }

    @Override
    protected void initViews() {
        mJurisdiction1 = findViewById(R.id.mJurisdiction1);
        mJurisdiction2 = findViewById(R.id.mJurisdiction2);
        mJurisdiction3 = findViewById(R.id.mJurisdiction3);
        mJurisdiction4 = findViewById(R.id.mJurisdiction4);
        mJurisdiction5 = findViewById(R.id.mJurisdiction5);
        mJurisdiction6 = findViewById(R.id.mJurisdiction6);
        mJurisdiction7 = findViewById(R.id.mJurisdiction7);
        mJurisdiction8 = findViewById(R.id.mJurisdiction8);
        mJurisdiction9 = findViewById(R.id.mJurisdiction9);
        mJurisdiction10 = findViewById(R.id.mJurisdiction10);
        mJurisdiction11 = findViewById(R.id.mJurisdiction11);

        userPhotoIV = findViewById(R.id.userPhotoIV);
        userHeadwearIV = findViewById(R.id.userHeadwearIV);
        mUserLevelView = findViewById(R.id.mUserLevelView);
        mLeftLevelTV = findViewById(R.id.mLeftLevelTV);
        mRightLevelTV = findViewById(R.id.mRightLevelTV);
        processBar = findViewById(R.id.processBar);
        mNeedExperienceTV = findViewById(R.id.mNeedExperienceTV);
        mShowMoreIconIV = findViewById(R.id.mShowMoreIconIV);
        mBottomIconView = findViewById(R.id.mBottomIconView);

        GlideUtils.displayCircleImage(this, UserManager.getInst().getUserBeam().getAvatarUrl()
                , userPhotoIV, 2, ContextCompat.getColor(this, R.color.white), GlideUtils.getUserNormalOptions(), true);

//        findViewById(R.id.shareTV).setOnClickListener(this);
//        findViewById(R.id.readTV).setOnClickListener(this);
        findViewById(R.id.moreTV).setOnClickListener(this);
//        findViewById(R.id.doTaskTV).setOnClickListener(this);
//        findViewById(R.id.searchTV).setOnClickListener(this);
        findViewById(R.id.mNeedExperienceTV).setOnClickListener(this);
        findViewById(R.id.mShowMoreIconIV).setOnClickListener(this);

        findViewById(R.id.signTV).setOnClickListener(this);
        findViewById(R.id.watchTV).setOnClickListener(this);
        findViewById(R.id.doTaskTV).setOnClickListener(this);
        findViewById(R.id.searchTV).setOnClickListener(this);
        findViewById(R.id.shareTV).setOnClickListener(this);
        findViewById(R.id.awakeTV).setOnClickListener(this);
        findViewById(R.id.readTV).setOnClickListener(this);
        findViewById(R.id.openTV).setOnClickListener(this);
        UserManager.getInst().addGoldListener(this);//金币改变监听   用户升级后回调刷新页面使用
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserManager.getInst().removGoldListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_user_level;
    }

    @Override
    protected String setTitle() {
        return "用户等级";
    }

    @Override
    protected void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.mNeedExperienceTV:
                ActivityUtils.startToUserLevelInforAct(this);
                break;
            case R.id.signTV:
                // 去签到
                ActivityUtils.startToMainActivity(this, 2, 0);
                MobclickAgent.onEvent(this, "295-lvhongbao");
                break;
            case R.id.watchTV:
                // 去阅读
                ActivityUtils.startToMainActivity(this, 0, 0);
                MobclickAgent.onEvent(this, "295-lvresou");
                break;
            case R.id.awakeTV:
                // 去唤醒
                ActivityUtils.startToShoutuActivity(this);
                break;
            case R.id.openTV:
                // 去开启
                ActivityUtils.startToMainActivity(this, 0, 0);
                break;
            case R.id.shareTV:
                // 去邀请
                ActivityUtils.startToShoutuActivity(this);
                break;
            case R.id.readTV:
                // 有效阅读
                ActivityUtils.startToMainActivity(this, 0, 0);
                break;
            case R.id.doTaskTV:
                // 去完成
                ActivityUtils.startToMainActivity(this, 2, 0);
                MobclickAgent.onEvent(this, "295-lvyaoqing");
                break;
            case R.id.searchTV:
                // 去搜索
                ActivityUtils.startToMainActivity(this, 0, 10000000);
                MobclickAgent.onEvent(this, "295-lvrenwu");
                break;
            case R.id.moreTV:
                String replace = bean.getLevelDescUrl().replace("http://pubweb", UserManager.getInst().getQukandianBean().getPubwebUrl());
                ActivityUtils.startToWebviewAct(this, replace, "规则");
                break;
            case R.id.mShowMoreIconIV:
                boolean b = mBottomIconView.getVisibility() == View.VISIBLE;
                mBottomIconView.setVisibility(b ? View.GONE : View.VISIBLE);
                mShowMoreIconIV.setImageResource(b ? R.mipmap.show_list_down : R.mipmap.show_list_up);
                break;
        }
    }

    private void setJurisdictionImg(TextView tv, int imgID) {
        Drawable top = getResources().getDrawable(imgID);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
    }

    private void showUpLevelDialog() {
        if (putUpLevelProtocol == null) {
            putUpLevelProtocol = new PutUpLevelProtocol(this, new BaseModel.OnResultListener<UpLevelResBean>() {
                @Override
                public void onResultListener(UpLevelResBean response) {
                    loadData();
                    DialogUpLevel dialogUpLevel = new DialogUpLevel(UserLevelAct.this, response);
                    dialogUpLevel.show();
                    putUpLevelProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    putUpLevelProtocol = null;
                }
            });
            putUpLevelProtocol.postRequest();
        }
    }

    @Override
    public void onGoldChangeListener(int addMoney) {
        if (addMoney == 0) {
            loadData();
        }
    }
}
