package com.zhangku.qukandian.protocol;

import android.content.Context;

import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.PostAdAwakenBean;

import retrofit2.Call;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/26
 * 唤醒拦截日志
 */
public class PostNewAwakenInfor extends NewBaseProtocol {

    private PostAdAwakenBean bean = new PostAdAwakenBean();

    public PostNewAwakenInfor(Context context, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean adBean,OnResultListener onResultListener) {
        super(context, onResultListener);
        bean.setAdPageType(""+adBean.getAdPageLocationId());
        bean.setReferId(""+adBean.getReferId());
        bean.setPageIndex(""+adBean.getPageIndex());
        bean.setAdvertiserId(""+adBean.getAdverId());
        bean.setAdvertiserName(adBean.getAdverName());
        bean.setAdsName(""+adBean.getReferId());
        bean.setAdType(""+adBean.getAdType());
        switch (adBean.getBelong()) {//枚举：1-首页，2-视频详情页，3-文章详情页，4-视频列表，6-开屏页，7-签到页，8-宝箱页）
            case "首页"://首页
                bean.setAdPageType(""+1);
                break;
            case "视频详情页大图":
            case "视频详情页列表":
                bean.setAdPageType(""+2);
                break;
            case "文章详情页大图":
            case "文章详情页列表":
                bean.setAdPageType(""+3);
                break;
            case "视频列表页":
                bean.setAdPageType(""+4);
                break;
            case "开屏页":
                bean.setAdPageType(""+6);
                break;
            case "签到弹框":
                bean.setAdPageType(""+7);
                break;
            case "拆宝箱弹框":
                bean.setAdPageType(""+8);
                break;
            case "未知的位置":
                bean.setAdPageType(""+0);
                break;
        }
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().postAwaken(mAuthorization,mContentType,bean);
        return call;
    }
}
