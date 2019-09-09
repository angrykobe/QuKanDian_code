package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.yzs.imageshowpickerview.ImageShowPickerBean;
import com.zhangku.qukandian.bean.FeedbackBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostFeedbackPhotoProtocol extends BaseModel {

    public PostFeedbackPhotoProtocol(Context context, OnResultListener<Boolean> onResultListener) {
        super(context, onResultListener);
    }


    public void postFeedback(FeedbackBean bean, List<ImageShowPickerBean> list) {
        if (list != null && list.size() > 0) {
            List<File> files = new ArrayList<>();
            // form 表单形式上传
            for (ImageShowPickerBean bean1 : list) {
                String mPath = bean1.getImageShowPickerUrl();
                File file = new File(mPath);
                files.add(file);
            }
            setHttp(bean, files);
        } else {
            setHttp(bean, null);
        }

//            call = getAPIService().postFeedback2("Bearer "
//                            + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/x-www-form-urlencoded",
//                    bean.getContent(), bean.getContactWay(), bean.getPhoneVersion(),  filesToMultipartBodyParts(files));
//        } else {
//            call = getAPIService().postFeedback("Bearer "
//                    + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", bean);
//        }

    }


    @Override
    public void release() {
        call.cancel();
    }


    private void setHttp(FeedbackBean bean, List<File> files) {
        OkHttpClient client = new OkHttpClient();
        if (files == null) {
            MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
            requestBody.addFormDataPart("","");
            Request request = new Request.Builder().addHeader("Authorization", "Bearer "
                    + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU))
                    .url(Config.BASE_URL + "api/eco/feedback/PostV2?" + "content=" + bean.getContent()
                            + "&contactWay=" + bean.getContactWay() + "&phoneVersion=" + bean.getPhoneVersion()).post(requestBody.build()).build();
            client.newBuilder().build().newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    String s = e.getMessage();
                    if (null != onResultListener) {
                        onResultListener.onResultListener(false);
                    }
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    String s = response.toString();
                    if (null != response.body()) {
                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            if (object.optBoolean(mSuccess)) {
                            } else {
                            }
                            if (null != onResultListener) {
                                onResultListener.onResultListener(true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            // form 表单形式上传
            MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (File file : files) {
                if (file != null) {
                    // MediaType.parse() 里面是上传的文件类型。
                    RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                    String filename = file.getName();
                    // 参数分别为， 请求key ，文件名称 ， RequestBody
                    requestBody.addFormDataPart("file", filename, body);
                }
            }
            Request request = new Request.Builder().addHeader("Authorization", "Bearer "
                    + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU))
                    .url(Config.BASE_URL + "api/eco/feedback/PostV2?" + "content=" + bean.getContent()
                            + "&contactWay=" + bean.getContactWay() + "&phoneVersion=" + bean.getPhoneVersion())
                    .post(requestBody.build()).build();
            client.newBuilder().build().newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    String s = e.getMessage();
                    if (null != onResultListener) {
                        onResultListener.onResultListener(false);
                    }
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    String s = response.toString();
                    if (null != response.body()) {
                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            if (object.optBoolean(mSuccess)) {
                            } else {
                            }
                            if (null != onResultListener) {
                                onResultListener.onResultListener(true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }




    }
}
