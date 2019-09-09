package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.google.gson.Gson;
import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.base.NewBaseListProtocol;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.LocalMessageBean;
import com.zhangku.qukandian.bean.MessageBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/19.
 */

public class GetNewPlatformMessageProtocol extends NewBaseListProtocol<MessageBean> {
    private Date mDate;
    private int mType;

    public GetNewPlatformMessageProtocol(Context context, Date date, int type, OnResultListener<List<MessageBean>> onResultListener) {
        super(context, onResultListener);
        mDate = date;
        mType = type;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().getMessageByTime("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", mDate, mType);
        return call;
    }

//    @Override
//    protected void getResult(JSONObject object) {
//        if (object.optBoolean(mSuccess)) {
//            JSONArray array = object.optJSONArray(mResult);
//            ArrayList<MessageBean> datas = new ArrayList<>();
//            if (null != array && array.length() > 0) {
//                Gson gson = new Gson();
//                for (int i = 0; i < array.length(); i++) {
//                    MessageBean item = gson.fromJson(array.optJSONObject(i).toString(), MessageBean.class);
//                    item.setType(mType);
//                    datas.add(item);
//                    LocalMessageBean bean = new LocalMessageBean();
//                    bean.setNewId(item.getId());
//                    bean.setContent(item.getContent());
//                    bean.setIsReading(DBTools.getMessageReadStstus(item));
//                    bean.setTitle(item.getTitle());
//                    bean.setCreationTime(item.getCreationTime());
//                    bean.setMessageType(mType);
//                    bean.setUserId(UserManager.getInst().getUserBeam().getId());
//                    bean.setLinkTo(item.getLinkTo());
//                    bean.setActionUrl(item.getActionUrl());
//                    DBTools.saveMessage(bean);
//                }
//            }
//            if (null != onResultListener) {
//                onResultListener.onResultListener(datas);
//            }
//        }
//    }

}
