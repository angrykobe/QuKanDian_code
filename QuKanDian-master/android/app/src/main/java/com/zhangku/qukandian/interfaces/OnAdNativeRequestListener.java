package com.zhangku.qukandian.interfaces;

import java.util.List;

/**
 * Created by yuzuoning on 2017/10/16.
 */

public interface OnAdNativeRequestListener {
    /**
     *
     * @param list  广告请求后的容器
     * @param adType  //广告类型 1 自主广告 2 联盟广告 3 合作链接
     */
    void onResponse(List list, int adType);

    void onFail();
}
