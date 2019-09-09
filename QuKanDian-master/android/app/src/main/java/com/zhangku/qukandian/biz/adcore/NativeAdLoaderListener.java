package com.zhangku.qukandian.biz.adcore;

import java.util.ArrayList;

public interface NativeAdLoaderListener {
    void onAdLoadSuccess(ArrayList<ZkNativeAd> response);
    void onAdLoadFailed(int code, String msg);
}