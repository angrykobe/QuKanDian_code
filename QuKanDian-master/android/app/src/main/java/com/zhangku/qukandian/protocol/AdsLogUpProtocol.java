package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.LogUpBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.CacheManage;
import com.zhangku.qukandian.utils.ToastUtils;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

public class AdsLogUpProtocol extends BaseProtocol {
    private Context mContext;

    public AdsLogUpProtocol(Context context, OnResultListener<Integer> onResultListener) {
        super(context, onResultListener);
        mContext = context;
    }

    @Override
    public String getServerUrl() {
        return Config.BASE_URL_ADS_UP;//广告日志上传域名
//        return "http://log.sjgo58.com/";//测试
//        return "http://log.qukandian.com/";//正式
    }

    @Override
    protected Call getMyCall() {
        List<LogUpBean> logUpBeans = CacheManage.get(Constants.ADSLOG_UP, LogUpBean.class);
        Object [] array = logUpBeans.toArray();
        String signature = CommonHelper.md5(key  + "" + time);
        call = getAPIService().adsLogUp(mAuthorization,
                "text/plain",
                signature,
                time, appid, array
        );
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        try {
            if (object.optBoolean("success")) {
//                ToastUtils.showLongToast(mContext, object.optString(mMessage));
            } else {
//                ToastUtils.showLongToast(mContext, object.optJSONObject(mError).optString(mMessage));
            }
            if (null != onResultListener) {
                onResultListener.onResultListener(1);
            }
        } catch (Exception e) {
            if (null != onResultListener)
                onResultListener.onFailureListener(1, "网络异常，请重试");
            e.printStackTrace();
        }
    }
}
