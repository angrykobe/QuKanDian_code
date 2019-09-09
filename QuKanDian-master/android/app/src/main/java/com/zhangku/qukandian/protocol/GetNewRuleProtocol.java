package com.zhangku.qukandian.protocol;

import android.content.Context;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.RuleBean;
import com.zhangku.qukandian.manager.UserManager;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/12/6.
 */

public class GetNewRuleProtocol extends NewBaseProtocol<RuleBean> {
    public GetNewRuleProtocol(Context context, OnResultListener<RuleBean> onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getAdClickRule(mAuthorization,mContentType);
    }

    @Override
    protected void getResult(RuleBean ruleBean) {
        super.getResult(ruleBean);
        UserManager.getInst().setmRuleBean(ruleBean);
        UserManager.getInst().setReadTips(ruleBean.getReadTipList());
    }

    @Override
    protected void getErrorResult(int code, String errorStr) {
        super.getErrorResult(code, errorStr);
    }
}
