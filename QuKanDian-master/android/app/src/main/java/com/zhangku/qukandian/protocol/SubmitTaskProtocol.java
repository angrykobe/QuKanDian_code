package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.text.TextUtils;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.SignRuleBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogDoubleGold;
import com.zhangku.qukandian.dialog.DialogSign;
import com.zhangku.qukandian.dialog.DialogSignBaoXiang;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yuzuoning on 2017/4/7.
 * 完成任务???
 */

public class SubmitTaskProtocol extends BaseModel {
    private PutBehaviorProtocol mPutBehaviorProtocol;
    private long mDuration;

    public SubmitTaskProtocol(Context context, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);

    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public void submitTask(final int type, final String name) {
        int time = CommonHelper.getCheckTimeStamp(QuKanDianApplication.mTimeOffset);
        String rand = CommonHelper.getStringRandom(32);

        call = getAPIService().putTaskInfoByName("Bearer "
                        + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),
                "application/json", CommonHelper.md5(key + "name" + name + "nonceStr"
                        + rand + "timestamp"
                        + time),
                time, appid, rand,
                name);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (null != response.body()) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        if (!object.optBoolean(mSuccess)) {
                            onResultListener.onFailureListener(object.optJSONObject(mError).optInt("code"), object.optJSONObject(mError).optString(mMessage));
                        } else {
                            JSONObject jsonObject = object.optJSONObject(mResult);
                            final String gold = jsonObject.optString("goldAmount");
                            String dou = jsonObject.optString("description");
                            if (Constants.READING_ON.contains(name.substring(0, 10))) {//有效阅读
                                if (null != jsonObject) {
                                    if (Constants.TYPE_NEW == type) {
                                        if ("double".equals(dou)) {
                                            new DialogDoubleGold(getContext(), gold).show();
                                        } else {
                                            CustomToast.showToast(mContext, "+" + gold, "阅读奖励");
                                        }
                                    } else if (Constants.TYPE_VIDEO == type) {
                                        if ("double".equals(dou)) {
                                            new DialogDoubleGold(getContext(), gold).show();
                                        } else {
                                            CustomToast.showToast(mContext, "+" + gold, "观看奖励");
                                        }
                                    }

                                    long lastTime = UserSharedPreferences.getInstance().getLong(Constants.LAST_TIME);
                                    if (lastTime != 0) {
                                        int black = UserSharedPreferences.getInstance().getInt(Constants.BLACKLIST);
                                        if (Math.abs(mDuration - lastTime) <= 2000) {
                                            if (black == 10) {
                                                if (null == mPutBehaviorProtocol) {
                                                    mPutBehaviorProtocol = new PutBehaviorProtocol(getContext(), UserManager.getInst().getUserBeam().getId(), 0, 1, new OnResultListener() {
                                                        @Override
                                                        public void onResultListener(Object response) {
                                                            mPutBehaviorProtocol = null;
                                                        }

                                                        @Override
                                                        public void onFailureListener(int code, String error) {
                                                            mPutBehaviorProtocol = null;
                                                        }
                                                    });
                                                    mPutBehaviorProtocol.postRequest();
                                                }
                                                black = 1;
                                                UserSharedPreferences.getInstance().putLong(Constants.LAST_TIME, mDuration);
                                                UserSharedPreferences.getInstance().putInt(Constants.BLACKLIST, black);
                                            } else {
                                                black++;
                                                UserSharedPreferences.getInstance().putInt(Constants.BLACKLIST, black);
                                            }
                                        } else {
                                            black = 1;
                                            UserSharedPreferences.getInstance().putInt(Constants.BLACKLIST, black);
                                            UserSharedPreferences.getInstance().putLong(Constants.LAST_TIME, mDuration);
                                        }
                                    }
                                }
                            }
                            if (Constants.SIGN_CONTNUED.equals(name)) {//获取签到规则
                                new GetCheckinRuleProtocol(mContext, new BaseModel.OnResultListener<SignRuleBean>() {
                                    @Override
                                    public void onResultListener(SignRuleBean response) {
                                        if (response.getToastType() == 1) {//广告
                                            DialogSign mDialogSign = new DialogSign(getContext());
                                            mDialogSign.show();
                                            mDialogSign.setGold(gold);
                                        } else {//任务
                                            DialogSignBaoXiang mDialogSignBaoXiang = new DialogSignBaoXiang(getContext());
                                            mDialogSignBaoXiang.show();
                                            mDialogSignBaoXiang.setGold(gold);
                                        }
                                    }

                                    @Override
                                    public void onFailureListener(int code, String error) {

                                    }
                                }).postRequest();

                            }
                            if (Constants.CHEST_BOX_MISSION.equals(name)) {//拆宝箱
                                DialogSignBaoXiang mDialogSignBaoXiang = new DialogSignBaoXiang(getContext());
                                mDialogSignBaoXiang.show();
                                mDialogSignBaoXiang.setGold(gold);
//                                onResultListener.onResultListener(object.optBoolean(mSuccess));
                            }
//                            if (type != -2) {
                            String temp;
                            if ("double".equals(dou)) {
                                temp = TextUtils.isEmpty(gold) ? "0" : (Integer.valueOf(gold) * 2) + "";
                            } else {
                                temp = TextUtils.isEmpty(gold) ? "0" : gold;
                            }
                            if (UserManager.getInst().hadLogin()) {
//                                UserBean userBean = UserManager.getInst().getUserBeam();
//                                UserManager.getInst().getUserBeam().getGoldAccount().setAmount(userBean.getGoldAccount().getAmount() + Integer.valueOf(temp));
//                                UserManager.getInst().getUserBeam().getGoldAccount().setSum(userBean.getGoldAccount().getSum() + Integer.valueOf(temp));
                                UserManager.getInst().goldChangeNofity(Integer.valueOf(temp));
                            }
//                            }
                            onResultListener.onResultListener(object.optBoolean(mSuccess));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtils.LogE(t.toString() + "");
            }
        });
    }

    @Override
    protected void release() {
        call.cancel();
    }
}
