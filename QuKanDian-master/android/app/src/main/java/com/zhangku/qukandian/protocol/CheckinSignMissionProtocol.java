package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/10.
 */

public class CheckinSignMissionProtocol extends BaseProtocol {
    private int mSpanDay;
    public CheckinSignMissionProtocol(Context context,int spanDay, OnResultListener<Integer> onResultListener) {
        super(context, onResultListener);
        mSpanDay = spanDay;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getSignCheckinMission("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),"application/json",mSpanDay);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean(mSuccess)) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssss");
                JSONArray array = object.optJSONArray(mResult);
                if (null != array && array.length() > 0) {
                    JSONObject jsonObject = array.optJSONObject(0);
                    String creation = jsonObject.optString("creationTime");
//                    int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
                    String currentTime = formatter.format(new Date(System.currentTimeMillis()));
                    Date date = formatter.parse(creation);//签到时间
                    Date currentDate = formatter.parse(currentTime);//当前时间
                    int step = jsonObject.optInt("stepTime");//连续签到几次

                    if(CommonHelper.differentDays(date, currentDate) > 1){
                        if (null != onResultListener) {
                            onResultListener.onResultListener(0);
                        }
                    }else {
                        if (null != onResultListener) {
                            onResultListener.onResultListener(step);
                        }
                    }
                }else {
                    if (null != onResultListener) {
                        onResultListener.onResultListener(0);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
