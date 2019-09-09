package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.SignRuleBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogSign;
import com.zhangku.qukandian.dialog.DialogSignBaoXiang;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import retrofit2.Call;

/**
 * 每日签到接口
 */
public class PutNewSubmitTaskForSignEveryProtocol extends NewBaseProtocol<DoneTaskResBean> {

    public PutNewSubmitTaskForSignEveryProtocol(Context context, OnResultListener<DoneTaskResBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);
        String name = Constants.SIGN_CONTNUED;
        call = getAPIService().putTaskInfoByName("Bearer "
                        + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
                "application/json", CommonHelper.md5(key + "name" + name + "nonceStr"
                        + rand + "timestamp"
                        + time),
                time, appid, rand,
                name);
        return call;
    }

    @Override
    protected void getResult(DoneTaskResBean doneTaskResBean) {
        final int gold = doneTaskResBean.getGoldAmount();
        //每日签到没有*2的规定  这个其实可以不用，先放不处理，以后继续整理代码
        String dou = doneTaskResBean.getDescription();
        int temp = gold * ("double".equals(dou)?2:1) ;

        new GetCheckinRuleProtocol(mContext, new BaseModel.OnResultListener<SignRuleBean>() {
            @Override
            public void onResultListener(SignRuleBean response) {
                if (response.getToastType() == 1) {//广告
                    DialogSign mDialogSign = new DialogSign(getContext());
                    mDialogSign.show();
                    mDialogSign.setGold(""+gold);
                } else {//任务
                    DialogSignBaoXiang mDialogSignBaoXiang = new DialogSignBaoXiang(getContext());
                    mDialogSignBaoXiang.show();
                    mDialogSignBaoXiang.setGold(""+gold);
                    mDialogSignBaoXiang.getDataForTask(2);
                }
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();

        if (UserManager.getInst().hadLogin()) {
            UserManager.getInst().goldChangeNofity(temp);
        }
        UserManager.getInst().getUserBeam().getMission().getFinished().add(Constants.SIGN_CONTNUED);
        super.getResult(doneTaskResBean);
    }
}
