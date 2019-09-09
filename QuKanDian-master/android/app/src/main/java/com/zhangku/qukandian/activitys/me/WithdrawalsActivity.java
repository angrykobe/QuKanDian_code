package com.zhangku.qukandian.activitys.me;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.BaseNew.BaseTitleAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.WithdrawalsAdapter;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.GiftBean;
import com.zhangku.qukandian.bean.MyBannerBean;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.bean.WithdrawalsBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogConfirm;
import com.zhangku.qukandian.dialog.DialogNewPeopleWitOneYuan;
import com.zhangku.qukandian.dialog.DialogWithdrawalsSuccess;
import com.zhangku.qukandian.dialog.WithdrawalsFailDialog;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewGiftProtocol;
import com.zhangku.qukandian.protocol.GetNewUserInfoProtocol;
import com.zhangku.qukandian.protocol.GetNewWithdrawalsAdBannerProtocol;
import com.zhangku.qukandian.protocol.PutNewGiftProtocol;
import com.zhangku.qukandian.protocol.UploadWeChatInfoProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SetWebSettings;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.AdViewPager;
import com.zhangku.qukandian.widght.TextSwitcherView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 我要提现
 */
public class WithdrawalsActivity extends BaseTitleAct implements WithdrawalsAdapter.OnStartLoading {

    private GetNewGiftProtocol mGetGiftProtocol;
    private AdViewPager mAdViewPager;
    private WithdrawalsAdapter mAdapter;
    private RecyclerView mRecyclerview;
    private TextView mWithdrawalsSumGoldTV;
    private List<GiftBean> mList = new ArrayList<>();
    private View mBindWechatView;
    private TextView mMyGoldTV;
    private TextView mTvMoreWelfare;
    //    private TextView mInforTV;
    private View mSmartWithdrawalsTitleTV;

    private LinearLayout mLlWithdrawalsWechat;
    private LinearLayout mLlWithdrawalsAlipay;

    private String payment = "0";//默认 0 或空  为微信支付类型 、 1 支付宝

    @Override
    protected void loadData() {
        if (!UserManager.getInst().hadLogin()) {
            ActivityUtils.startToBeforeLogingActivity(QuKanDianApplication.getAppContext());
            finish();
            return;
        }
        getPayTypeList(payment);
        //头部banner 广告
        new GetNewWithdrawalsAdBannerProtocol(this, new BaseModel.OnResultListener<MyBannerBean>() {
            @Override
            public void onResultListener(MyBannerBean response) {
                boolean isHave = false;
                if (response.getStatusForLogin() == 1) {
                    isHave = true;
                } else if (response.getStatusForLogin() == 2) {
                    if (UserManager.getInst().getUserBeam().getMissionGrarntedUsers() != null) {
                        for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                            if (Constants.MY_With_BANNER.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                                isHave = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                            }
                        }
                    }
                }
                if (isHave && response.getBannerConfigs() != null && response.getBannerConfigs().size() > 0) {
                    mAdViewPager.setVisibility(View.VISIBLE);
                    mAdViewPager.initData(response.getBannerConfigs(), AnnoCon.FROM_WithdrawalsActivity);
                } else {
                    mAdViewPager.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailureListener(int code, String error) {
                mAdViewPager.setVisibility(View.GONE);
            }
        }).postRequest();
    }
    //获取不同支付类型配置的支付列表
    private void getPayTypeList(String payment){
        if (null == mGetGiftProtocol) {
            mGetGiftProtocol = new GetNewGiftProtocol(this, payment,new BaseModel.OnResultListener<ArrayList<GiftBean>>() {
                @Override
                public void onResultListener(ArrayList<GiftBean> response) {
                    ArrayList<GiftBean> list1 = new ArrayList<>();//小额提现
                    ArrayList<GiftBean> list2 = new ArrayList<>();//无门槛
                    for (GiftBean bean : response) {
//                        if (!TextUtils.isEmpty(bean.getDesc())) {
//                            list1.add(bean);
//                        }else{
//                            list2.add(bean);
//                        }
                        if (bean.getType() == 1) {
                            list1.add(bean);
                        } else {
                            list2.add(bean);
                        }
                    }
                    if (list1.size() == 0 && mSmartWithdrawalsTitleTV != null)
                        mSmartWithdrawalsTitleTV.setVisibility(View.GONE);
                    mList.clear();
                    GiftBean bean = new GiftBean();
                    bean.setGold(-2);
                    mList.addAll(list1);
                    mList.add(bean);//添加小额提现说明对象分割标识
                    mList.addAll(list2);
                    GiftBean bean1 = new GiftBean();
                    bean1.setGold(-3);
                    mList.add(bean1);//添加小额提现说明对象分割标识

                    mAdapter.notifyDataSetChanged();
                    mGetGiftProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetGiftProtocol = null;
                }
            });
            mGetGiftProtocol.postRequest();
        }
    }

    @Override
    protected void initViews() {
        mWithdrawalsSumGoldTV = findViewById(R.id.mWithdrawalsSumGoldTV);
        //绑定微信
        mBindWechatView = findViewById(R.id.withdrawalsBindWechatView);
        if (null != UserManager.getInst().getUserBeam().getWechatUser()) {
            mBindWechatView.setVisibility(View.GONE);
        } else {
            mBindWechatView.setVisibility(View.VISIBLE);
        }
        //
        mRecyclerview = findViewById(R.id.recyclerview);
        GridLayoutManager layout = new GridLayoutManager(this, 3);
        mRecyclerview.setLayoutManager(layout);
        mAdapter = new WithdrawalsAdapter(this, mList, WithdrawalsActivity.this);
        mRecyclerview.setAdapter(mAdapter);

        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0)
                    return 3;
                else if (position == mAdapter.getList().size() + 1)
                    return 3;
                else if (mList.get(position - 1).getGold() < 0)
                    return 3;
                else
                    return 1;
            }
        });
        //头部
        View headView = LayoutInflater.from(this).inflate(R.layout.head_withdrawals_ac, mRecyclerview, false);
        mAdViewPager = headView.findViewById(R.id.withdrawalsAdView);
        mTvMoreWelfare = headView.findViewById(R.id.tv_more_welfare);
        mLlWithdrawalsWechat = headView.findViewById(R.id.ll_withdrawals_wechat);
        mLlWithdrawalsAlipay = headView.findViewById(R.id.ll_withdrawals_alipay);
        //设置头部滚动提现
        TextSwitcherView mTextSwitcherView = headView.findViewById(R.id.task_activity_marquee);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number = new Random().nextInt(10000);
            int money = new Random().nextInt(Config.Moneys.length);
            int munit = new Random().nextInt(31);
            String temp = "趣友 **** " + (number >= 1000 ? number : (number + 1000))
                    + "，" + (munit > 0 ? munit : 1)
                    + "分钟前提现了" + CommonHelper.form2(Config.Moneys[money]) + "元到微信";
            arrayList.add(temp);
        }
        mTextSwitcherView.getResource(arrayList);
        //我的金币
        mMyGoldTV = headView.findViewById(R.id.withdrawalsMyGoldNumTV);
        mMyGoldTV.setText(UserManager.getInst().getUserBeam().getGoldAccount().getAmount() + "");
        //显示积分商城
        View withdrawalsIncomeTV = headView.findViewById(R.id.withdrawalsIncomeTV);
        mSmartWithdrawalsTitleTV = headView.findViewById(R.id.mSmartWithdrawalsTitleTV);
        withdrawalsIncomeTV.setOnClickListener(this);
        mAdapter.setHeaderView(headView);

        //加载h5  footview
        View footView = (View) LayoutInflater.from(this).inflate(R.layout.foot_withdrawals, mRecyclerview, false);
        WebView webView = footView.findViewById(R.id.webview);
//        mInforTV = footView.findViewById(R.id.footWithdrawalsInforTV);
        String string = UserManager.getInst().getQukandianBean().getPubwebUrl();
        webView.loadUrl(string + "tixianshuoming/index.html");
        SetWebSettings.setWebviewNotAd(this, webView);
        mAdapter.setFooterView(footView);

        findViewById(R.id.withdrawalsSubmitBtn).setOnClickListener(this);
        mSubTitleRightTV.setText("提现记录");
        mSubTitleRightTV.setOnClickListener(this);
        mBindWechatView.setOnClickListener(this);
        mTvMoreWelfare.setOnClickListener(this);
        mLlWithdrawalsWechat.setOnClickListener(this);
        mLlWithdrawalsAlipay.setOnClickListener(this);
        MobclickAgent.onEvent(this, "294-jinrutixianye");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_withdrawals_layout;
    }

    @Override
    protected String setTitle() {
        return "我要提现";
    }

    @Override
    protected void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.withdrawalsBindWechatView:
                bindWeChat();
                break;
            case R.id.withdrawalsIncomeTV://
                MobclickAgent.onEvent(this, "294-wodeqianbao");
                ActivityUtils.startToIncomeDetailsActivity(this);
                break;
            case R.id.withdrawalsSubmitBtn:
                MobclickAgent.onEvent(this, "04_07_02_clicktixianbtn");
                final GiftBean chooseBean = mAdapter.getChooseBean();
                if (chooseBean != null) {
                    new DialogConfirm(this)
                            .setTitles("确认要兑换" + chooseBean.getTitle() + "？")
                            .setYesBtnText("确认")
                            .setListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //提现接口
                                    new PutNewGiftProtocol(WithdrawalsActivity.this, chooseBean.getId(), new BaseModel.OnResultListener<WithdrawalsBean>() {
                                        @Override
                                        public void onResultListener(WithdrawalsBean response) {
                                            int amount = UserManager.getInst().getUserBeam().getGoldAccount().getAmount();//账户余额
                                            int gold = chooseBean.getGold();//提现金币
                                            int nowGold = amount - gold;
                                            mMyGoldTV.setText(nowGold + "");
                                            mWithdrawalsSumGoldTV.setText("0");
                                            loadData();
                                            mAdapter.reset();
                                            if ("OneYuan".equals(chooseBean.getName()) && UserManager.getInst().getUserBeam().isLevelGrand()) {
                                                DialogNewPeopleWitOneYuan taskDialog = new DialogNewPeopleWitOneYuan(WithdrawalsActivity.this);
                                                taskDialog.show();
                                                UserManager.getInst().getUserBeam().setHeben(true);
                                                EventBus.getDefault().post(new NewPeopleTaskBean());
                                                MobclickAgent.onEvent(WithdrawalsActivity.this, "294-shenqingyiyuantixian");
                                            } else {
                                                DialogWithdrawalsSuccess dialog = new DialogWithdrawalsSuccess(WithdrawalsActivity.this);
                                                dialog.show();
                                            }
                                            UserManager.getInst().getUserBeam().getGoldAccount().setAmount(nowGold);
                                            UserManager.getInst().goldChangeNofity(0);
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {
                                            MobclickAgent.onEvent(WithdrawalsActivity.this, "294-tixiantiaojiantanchuang");
                                            WithdrawalsFailDialog dialog = new WithdrawalsFailDialog(WithdrawalsActivity.this, R.style.fullDialog, error);
                                            dialog.show();
                                        }
                                    }).postRequest();
                                }
                            }).show();
                } else {
                    ToastUtils.showShortToast(WithdrawalsActivity.this, "请选择提现金额");
                }
                break;
            case R.id.baseSubTitleRightTV:
                MobclickAgent.onEvent(this, "04_07_01_intowithdrawalsrecord");
                ActivityUtils.startToWithdrawalsRecordActivity(WithdrawalsActivity.this);
                break;
            case R.id.tv_more_welfare://更多福利
                MobclickAgent.onEvent(this, "296-wechatentrance1");
                MobclickAgent.onEvent(this, "296-wechatentrance2");
                String welfareUrl = UserManager.getInst().getQukandianBean().getHenbenH5Url();
                ActivityUtils.startToAboutWebviewAct(WithdrawalsActivity.this,welfareUrl,"关注领好礼");
                break;
            case R.id.ll_withdrawals_wechat://微信支付方式
                mLlWithdrawalsWechat.setBackgroundResource(R.mipmap.icon_withdrawals_type_selected);
                mLlWithdrawalsAlipay.setBackgroundResource(R.mipmap.icon_withdrawals_type_normal);
                payment = "0";
                mAdapter.setInforTVGone();
                getPayTypeList(payment);
                break;
            case R.id.ll_withdrawals_alipay://支付宝支付方式
                mLlWithdrawalsWechat.setBackgroundResource(R.mipmap.icon_withdrawals_type_normal);
                mLlWithdrawalsAlipay.setBackgroundResource(R.mipmap.icon_withdrawals_type_selected);
                payment = "1";
                mAdapter.setInforTVGone();
                getPayTypeList(payment);
                break;
        }
    }

    @Override
    public void onSelected(GiftBean bean) {
        if (bean != null) {
            mWithdrawalsSumGoldTV.setText("" + bean.getGold());
        } else {
            mWithdrawalsSumGoldTV.setText("0");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new ArrayMap<>();
        map.put("to", "我要提现");
        MobclickAgent.onEvent(this, "AllPv", map);
    }

    private UploadWeChatInfoProtocol mUploadWeChatInfoProtocol;

    private void bindWeChat() {
        mDialogPrograss.show();
        UMShareAPI.get(QuKanDianApplication.getmContext()).deleteOauth(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                UMShareAPI.get(QuKanDianApplication.getmContext()).getPlatformInfo(WithdrawalsActivity.this,
                        SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                ToastUtils.showLongToast(WithdrawalsActivity.this, "正在打开跳转页面");
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA share_media, int i, final Map<String, String> map) {
                                if (null == mUploadWeChatInfoProtocol) {
                                    mUploadWeChatInfoProtocol = new UploadWeChatInfoProtocol(WithdrawalsActivity.this, new BaseModel.OnResultListener<Boolean>() {
                                        @Override
                                        public void onResultListener(Boolean response) {
                                            if (response) {
                                                new GetNewUserInfoProtocol(WithdrawalsActivity.this, new BaseModel.OnResultListener<UserBean>() {
                                                    @Override
                                                    public void onResultListener(UserBean response) {
                                                        mDialogPrograss.dismiss();
                                                        ToastUtils.showShortToast(WithdrawalsActivity.this, "恭喜~成功绑定微信");
                                                        if (null != UserManager.getInst().getUserBeam().getWechatUser()) {
                                                            mBindWechatView.setVisibility(View.GONE);
                                                        } else {
                                                            mBindWechatView.setVisibility(View.VISIBLE);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailureListener(int code, String error) {
                                                        mDialogPrograss.dismiss();
                                                    }
                                                }).postRequest();
                                            } else {
                                                mDialogPrograss.dismiss();
                                            }
                                            mUploadWeChatInfoProtocol = null;
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {
                                            ToastUtils.showShortToast(WithdrawalsActivity.this, error);
                                            mUploadWeChatInfoProtocol = null;
                                        }
                                    });
                                    mUploadWeChatInfoProtocol.uploadWeChatInfo(new WeChatBean(map.get("name"), map.get("uid"), map.get("openid")
                                            , map.get("gender").equals("男") ? 1 : 2, map.get("iconurl")
                                            , map.get("prvinice"), map.get("city"), map.get("country")
                                            , UserManager.getInst().getUserBeam().getId()));
                                }

                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                                if (throwable.toString().contains("没有安装应用")) {
                                    ToastUtils.showLongToast(WithdrawalsActivity.this, "没有安装应用");
                                }
                                mDialogPrograss.dismiss();
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media, int i) {
                                mDialogPrograss.dismiss();
                            }
                        });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });

    }
}
