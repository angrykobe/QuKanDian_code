package com.zhangku.qukandian.interfaces;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.bean.DetailsBean;

/**
 * Created by yuzuoning on 2017/4/21.
 */

public interface OnSharedListener {
    void onSharedListener(DetailsBean mDetailsBean, SHARE_MEDIA share_media);
}
