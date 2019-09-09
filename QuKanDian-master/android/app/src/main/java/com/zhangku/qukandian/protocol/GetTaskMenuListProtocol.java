package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.bean.UserMenuConfig;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.List;

import retrofit2.Call;

/**
 * 2019年5月22日09:56:44
 * ljs
 * 几个菜单列表整成一个（我的、任务、等级）
 * 任务页面-//幸运转盘、任务宝箱、搜索赚-动态列表
 */
public class GetTaskMenuListProtocol extends NewBaseListProtocol<UserMenuConfig> {
    private static long last_time = 0;

    public static int PAGETYPE_ME = 0;
    public static int PAGETYPE_TASK = 1;
    public static int PAGETYPE_LV = 2;

    private int type;

    public GetTaskMenuListProtocol(Context context,int type, OnResultListener<List<UserMenuConfig>> onResultListener) {
        super(context, onResultListener);
        this.type = type;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getGrtList("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", type);
        return call;
    }

    @Override
    public void postRequest() {
        // 三秒防止刷
        //if (System.currentTimeMillis() - last_time < 3 * 1000) {
        //    if (onResultListener != null) {
        //        onResultListener.onFailureListener(-1100, "");
        //    }
        //    return;
        //}
        last_time = System.currentTimeMillis();
        super.postRequest();
    }

}
