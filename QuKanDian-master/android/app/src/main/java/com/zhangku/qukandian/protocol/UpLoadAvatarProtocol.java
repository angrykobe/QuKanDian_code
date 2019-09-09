package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.BaseProtocol;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by yuzuoning on 2017/4/1.
 */

public class UpLoadAvatarProtocol extends BaseProtocol {
    private String mPath;

    public UpLoadAvatarProtocol(Context context, String path, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
        mPath = path;
    }

    @Override
    protected Call getMyCall() {
        File file = new File(mPath);
        final RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        call = getAPIService().uploadAvatar("Bearer "
                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU),body);
        return call;
    }

    @Override
    protected void getResult(JSONObject object) {
        if (object.optBoolean("success")) {
            if (null != onResultListener) {
                onResultListener.onResultListener(true);
            }
            UserManager.getInst().updateUserIcon(object.optJSONObject("result").optString("avatarUrl").toString());
            UserBean user = UserManager.getInst().getUserBeam();
            user.setAvatarUrl(object.optJSONObject("result").optString("avatarUrl").toString());
            UserManager.getInst().setUserInfor(user);
            ToastUtils.showLongToast(getContext(), "上传成功");
        }
    }

    @Override
    public void release() {
        call.cancel();
    }
}
