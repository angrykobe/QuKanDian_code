package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import retrofit2.Call;

/**
 * 新手红包
 */
public class PutNewTaskForNewRedProtocol extends NewBaseProtocol<DoneTaskResBean> {

    public PutNewTaskForNewRedProtocol(Context context, OnResultListener<DoneTaskResBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);
        String name = Constants.REGISTER_COIN;
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
        int gold = doneTaskResBean.getGoldAmount();
        String dou = doneTaskResBean.getDescription();
        int temp = gold * ("double".equals(dou)?2:1) ;
        if (UserManager.getInst().hadLogin()) {
//            UserBean userBean = UserManager.getInst().getUserBeam();
//            UserManager.getInst().getUserBeam().getGoldAccount().setAmount(userBean.getGoldAccount().getAmount() + temp);
//            UserManager.getInst().getUserBeam().getGoldAccount().setSum(userBean.getGoldAccount().getSum() + temp);
            UserManager.getInst().goldChangeNofity(temp);
        }
        UserManager.getInst().getUserBeam().getMission().getFinished().add(Constants.REGISTER_COIN);
        super.getResult(doneTaskResBean);
    }
}
