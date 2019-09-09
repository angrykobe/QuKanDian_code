package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.AppErrorLogDto;
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.utils.CommonHelper;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2018/3/28.
 */

public class PutNewGetGoldDownAppProtocol extends NewBaseProtocol<Object> {

    private DownAppBean bean;

    public PutNewGetGoldDownAppProtocol(Context context, DownAppBean bean, OnResultListener<Object> onResultListener) {
        super(context, onResultListener);
        this.bean = bean;
    }

    @Override
    protected Call getMyCall() {
        //-1 无操作 0 开始下载 1、下载完成 2、安装完成（未打开app） 3、试玩中(打开了app) 4 任务完成
        int type = 0;
        switch (bean.getStage()) {
            case 0:
                break;
            case 1:
                type = 1;
                break;
            case 2:
                break;
            case 3:
                type = 2;
                break;
            case 4:
                type = 3;
                break;
        }
        int taskId = bean.getTaskId();
        int gold = bean.getGold();
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);
        return getAPIService().putDownAppTask(mAuthorization, mContentType,
                CommonHelper.md5(key  + "gold" + gold+ "nonceStr" + rand + "taskId" + taskId + "timestamp" + time + "type" + type),
                time, appid, rand, type, taskId, gold);
    }
}
