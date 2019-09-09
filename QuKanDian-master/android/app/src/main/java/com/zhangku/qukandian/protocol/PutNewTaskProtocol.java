package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

/**
 * 新手红包
 */
public class PutNewTaskProtocol extends NewBaseProtocol<DoneTaskResBean> {


    private String taskName;

    public PutNewTaskProtocol(Context context, String taskName, OnResultListener<DoneTaskResBean> onResultListener) {
        super(context, onResultListener);
        this.taskName = taskName;
    }

    @Override
    protected Call getMyCall() {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);
        call = getAPIService().putTaskInfoByName("Bearer "
                        + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
                "application/json", CommonHelper.md5(key + "name" + taskName + "nonceStr"
                        + rand + "timestamp"
                        + time),
                time, appid, rand,
                taskName);
        return call;
    }

    @Override
    protected void getResult(DoneTaskResBean doneTaskResBean) {
        int gold = doneTaskResBean.getGoldAmount();
        String dou = doneTaskResBean.getDescription();
        int temp = gold * ("double".equals(dou)?2:1) ;
        if (UserManager.getInst().hadLogin()) {
            UserManager.getInst().goldChangeNofity(temp);
        }
//        UserManager.getInst().getUserBeam().getMission().getFinished().add(Constants.REGISTER_COIN);
        super.getResult(doneTaskResBean);
    }
}
