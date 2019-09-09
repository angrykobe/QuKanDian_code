package com.zhangku.qukandian.activitys.me;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.TaskWebViewAct;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.EventBusBean.ShowNewPeopleTaskDialogBean;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogNewPeopleTask;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.SubmitTaskProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.GrayBgActionBar;
import com.zhangku.qukandian.widght.ItemNoviceAnswer;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by yuzuoning on 2017/11/28.
 * 新手答题
 */

public class NoviceAnswerActivity extends BaseTitleActivity implements View.OnClickListener, ItemNoviceAnswer.OnSelectedIndexListener {
    private GrayBgActionBar mGrayBgActionBar;
    private RelativeLayout mRlStartAnswerBtn;
    private RelativeLayout mRlSubmitAnswerBtn;
    private LinearLayout mLayoutAnswer;
    private ItemNoviceAnswer mItemNoviceAnswer1;
    private ItemNoviceAnswer mItemNoviceAnswer2;
    private ItemNoviceAnswer mItemNoviceAnswer3;
    private ItemNoviceAnswer mItemNoviceAnswer4;
    private ItemNoviceAnswer mItemNoviceAnswer5;
    private ScrollView mScrollView;
    private boolean isAnswerAll = false;
    private boolean isAnswer = false;
    private boolean isAnswer1 = false;
    private boolean isAnswer2 = false;
    private boolean isAnswer3 = false;
    private boolean isAnswer4 = false;
    private int mCount = 0;
    private SubmitTaskProtocol mSubmitTaskProtocol;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        mGrayBgActionBar = findViewById(R.id.gray_actionbar_layout);
        mGrayBgActionBar.setTvTitle("新手答题");
        mRlStartAnswerBtn = findViewById(R.id.start_answer_btn);
        mScrollView = findViewById(R.id.novice_answer_scrollview);
        mRlSubmitAnswerBtn = findViewById(R.id.submit_answer_btn);
        mLayoutAnswer = findViewById(R.id.answer_layout);
        mItemNoviceAnswer1 = findViewById(R.id.answer_layout1);
        mItemNoviceAnswer2 = findViewById(R.id.answer_layout2);
        mItemNoviceAnswer3 = findViewById(R.id.answer_layout3);
        mItemNoviceAnswer4 = findViewById(R.id.answer_layout4);
        mItemNoviceAnswer5 = findViewById(R.id.answer_layout5);

        mItemNoviceAnswer1.setContent(0, "1.趣看视界是什么？", "A.出行工具", "B.拍照软件", "C.资讯阅读赚钱软件", "",2);
        mItemNoviceAnswer2.setContent(1, "2.如何赚金币？", "A.阅读资讯 ", "B.浏览推荐红包 ", "C.邀好友赚金币", "D.以上我都选了",3);
        mItemNoviceAnswer3.setContent(2, "3.邀请好友的好处是？", "A.收益暴涨", "B.吃饭购物", "C.逛街散步", "",0);
        mItemNoviceAnswer4.setContent(3, "4.如何收徒拿进贡？", "A.分享链接收徒", "B.面对面收徒", "C.徒弟填写邀请码", "D.以上我都选了",3);
        mItemNoviceAnswer5.setContent(4, "5.使用中遇到问题怎么办？", "A.大声呼救 ", "B.查看帮助或反馈", "C.默默发呆", "",1);

        mItemNoviceAnswer1.setOnSelectedIndexListener(this);
        mItemNoviceAnswer2.setOnSelectedIndexListener(this);
        mItemNoviceAnswer3.setOnSelectedIndexListener(this);
        mItemNoviceAnswer4.setOnSelectedIndexListener(this);
        mItemNoviceAnswer5.setOnSelectedIndexListener(this);

        mRlStartAnswerBtn.setOnClickListener(this);
        mRlSubmitAnswerBtn.setOnClickListener(this);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_novice_answer_layout;
    }

    @Override
    public String setPagerName() {
        return "新手答题";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_answer_btn:
                MobclickAgent.onEvent(this, "293-kaishidati");
                mRlStartAnswerBtn.setVisibility(View.GONE);
                mLayoutAnswer.setVisibility(View.VISIBLE);
                mScrollView.scrollBy(0, Config.SCREEN_HEIGHT - mGrayBgActionBar.getHeight() - DisplayUtils.dip2px(this, 20));
                break;
            case R.id.submit_answer_btn:
                if (isAnswerAll) {
                    if (null == mSubmitTaskProtocol) {
                        mSubmitTaskProtocol = new SubmitTaskProtocol(this, new BaseModel.OnResultListener<Boolean>() {
                            @Override
                            public void onResultListener(Boolean response) {
                                MobclickAgent.onEvent(NoviceAnswerActivity.this, "294-finishdati");
                                if (response) {
                                    ToastUtils.showLongToast(NoviceAnswerActivity.this, "恭喜您，成功通过测试。 ");
                                    UserManager.ANSWERED = true;
                                }
                                EventBus.getDefault().post(new ShowNewPeopleTaskDialogBean());
                                ActivityUtils.startToMainActivity(NoviceAnswerActivity.this,2,0);
                                mSubmitTaskProtocol = null;
                                finish();
                            }

                            @Override
                            public void onFailureListener(int code, String error) {
                                ToastUtils.showShortToast(NoviceAnswerActivity.this, error);
                                mSubmitTaskProtocol = null;
                            }
                        });
                        mSubmitTaskProtocol.submitTask(-1, Constants.NOVICE_ANSWER);
                    }
                } else {
                    ToastUtils.showShortToast(this, "请选择正确答案");
                }
                break;
        }
    }

    @Override
    public void onSelectIndexListener(int index, int selectIndex) {
        switch (index) {
            case 0:
                isAnswer = selectIndex == 2;
                break;
            case 1:
                isAnswer1 = selectIndex == 3;
                break;
            case 2:
                isAnswer2 = selectIndex == 0;
                break;
            case 3:
                isAnswer3 = selectIndex == 3;
                break;
            case 4:
                isAnswer4 = selectIndex == 1;
                break;
        }
        isAnswerAll = isAnswer && isAnswer1 && isAnswer2 && isAnswer3 && isAnswer4;
    }
}
