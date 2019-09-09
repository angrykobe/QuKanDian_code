//package com.zhangku.qukandian.protocol;
//
//import android.content.Context;
//
//import com.zhangku.qukandian.base.BaseProtocol;
//import com.zhangku.qukandian.config.Config;
//import com.zhangku.qukandian.config.Constants;
//import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
//
//import org.json.JSONObject;
//
//import retrofit2.Call;
//
///**
// * Created by yuzuoning on 2017/4/3.
// * 收藏动作
// */
//
//public class CollectArticleProtocol extends BaseProtocol {
//    private int mPostId;
//    private boolean mYesNo;
//
//    public CollectArticleProtocol(Context context, int postId, boolean yesNo, OnResultListener<Boolean> onResultListener) {
//        super(context, onResultListener);
//        mPostId = postId;
//        mYesNo = yesNo;
//    }
//
//
//    @Override
//    protected Call getMyCall() {
//        call = getAPIService().collect("Bearer "
//                + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU), "application/json", mPostId, mYesNo);
//        return call;
//    }
//
//    @Override
//    protected void getResult(JSONObject object) {
//        if (null != onResultListener) {
//            onResultListener.onResultListener(object.optBoolean(mSuccess));
//        }
//    }
//}
