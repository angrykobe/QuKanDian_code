package com.zhangku.qukandian.interfaces;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/3/24.
 */

public interface IRequestResult<T> {
    void onResult(ArrayList<T> result);
    void onFailure();
}
