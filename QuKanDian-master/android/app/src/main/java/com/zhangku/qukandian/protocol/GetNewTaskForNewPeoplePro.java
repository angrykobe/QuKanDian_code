package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;

/**
 */

public class GetNewTaskForNewPeoplePro extends NewBaseListProtocol<NewPeopleTaskBean> {
    public GetNewTaskForNewPeoplePro(Context context, OnResultListener<List<NewPeopleTaskBean>> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getNewPeopleTask(mAuthorization, mContentType, "" + QuKanDianApplication.getCode());
        return call;
    }

    @Override
    protected void getResult(List<NewPeopleTaskBean> list) {
        if(list.size()!=0){
            int resouIndex = 0;
            for (int i = 0; i < list.size(); i++) {
                NewPeopleTaskBean newPeopleTaskBean = list.get(i);
                if (Constants.FIRST_RESOU.equals(newPeopleTaskBean.getName())) {
                    resouIndex = i;
                }
                if(newPeopleTaskBean.getKindType()==4){
                    int allAwards = 0;
                    for (TaskBean.MoreMissionBean moreMissionBean : newPeopleTaskBean.getMoreMission()) {
                        allAwards += moreMissionBean.getAwardsTime();
                    }
                    newPeopleTaskBean.setAwardsTime(allAwards);
                }
            }
            if (UserManager.getInst().getmRuleBean().getSougouGoldRule().getDPT() <= UserManager.getInst().getUserBeam().getGoldAccount().getSum()//阈值最小值限制
                    && (UserManager.getInst().getmRuleBean().getSougouGoldRule().getDptMax() == 0
                    || UserManager.getInst().getmRuleBean().getSougouGoldRule().getDptMax() >= UserManager.getInst().getUserBeam().getGoldAccount().getSum()//阈值最大值限制
            )){
                list.get(resouIndex).setIshide(false);
//                Collections.swap(list, resouIndex, 0);
            }else{
                list.get(resouIndex).setIshide(true);
                //热搜放最后面
                Collections.swap(list, resouIndex, list.size() - 1);
            }
        }

        super.getResult(list);
    }
}
