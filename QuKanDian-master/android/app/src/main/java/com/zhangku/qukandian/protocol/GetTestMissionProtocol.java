package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/12/15.
 */

public class GetTestMissionProtocol extends BaseProtocol {
    public GetTestMissionProtocol(Context context, OnResultListener onResultListener) {
        super(context, onResultListener);
    }

    @Override
    protected Call getMyCall() {
        return getAPIService().getTestMission(mAuthorization, mContentType);
    }

    @Override
    protected void getResult(JSONObject object) {
        UserManager.SUCCESS = true;
        if (null != object) {
            JSONArray jsonArray = object.optJSONArray(mResult);
            if (jsonArray != null && jsonArray.length() > 0) {
                String grayStr="";
                for (int i = 0; i < jsonArray.length(); i++) {
                    grayStr += jsonArray.optJSONObject(i).optString("missionName") + ",";
                    if (jsonArray.optJSONObject(i).optString("missionName").equals("sougou_search_gift")) {
                        UserManager.SOUGOU_SEARCH_GIFT = true;
                    } else if (jsonArray.optJSONObject(i).optString("missionName").equals("ads_click_gift")) {
                        UserManager.ADS_CLICK_GIFT = true;
                    } else if (jsonArray.optJSONObject(i).optString("missionName").equals("duiba_coin")) {
                        UserManager.MALL_DUIBA_COIN = true;
                    } else if (jsonArray.optJSONObject(i).optString("missionName").equals("answer_missoin")) {
                        UserManager.BOTTOM_MISSION = true;
                    } else if (jsonArray.optJSONObject(i).optString("missionName").equals(Constants.READ_PROGRESS)) {//read_progress 阅读进度按钮显示
                        UserManager.READ_PROGRESS = true;
                    } else if (jsonArray.optJSONObject(i).optString("missionName").equals("register_coin")) {
                        UserManager.REGIST_MISSION = true;
                    } else if (jsonArray.optJSONObject(i).optString("missionName").equals("operation_toast")) {
                        UserManager.OPRATION_MISSION = true;
                    }else if (jsonArray.optJSONObject(i).optString("missionName").equals("Induct_Icon")) {
                        UserManager.OPERATION_RIGHTICON = true;
                    }else if (jsonArray.optJSONObject(i).optString("missionName").equals(Constants.HIGH_PRICE_NEWS)) {
                        UserManager.HIGH_PRICE_NEWS = true;
                    }
                }
                UserManager.getInst().setGrayJurisdiction(grayStr);
            }
        }
        if(null != onResultListener){
            onResultListener.onResultListener(null);
        }
    }
}
