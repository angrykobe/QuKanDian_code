package com.zhangku.qukandian.bean;

import com.google.gson.Gson;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/2
 * 你不注释一下？
 */
public class SougouCacheBean {
    private String data;
    private long time;
    private boolean isTooLong;

    public SougouCacheBean(long time) {
        this.data = TimeUtils.formatPhotoDate(time);
        this.time = time;
    }

    public boolean isTooLong() {
        return isTooLong;
    }

    public void setTooLong(boolean tooLong) {
        isTooLong = tooLong;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSameDay(long nowtime) {
        String nowDay = TimeUtils.formatPhotoDate(nowtime);
        return data.equals(nowDay);
    }

    /**
     * 获取广告缓存
     *
     * @return
     */
    public static List<SougouCacheBean> getSougouAdCache() {
        String sougouadcache = AdsRecordUtils.getInstance().getString(Constants.AD_SOUGOU_CACHE, "");
        List<SougouCacheBean> sougouCacheBeans = GsonUtil.fromJsonForList(sougouadcache, SougouCacheBean.class);
        if (sougouCacheBeans == null) sougouCacheBeans = new ArrayList<>();
        return sougouCacheBeans;
    }

    public static void addSougouAdCache() {
        long time = System.currentTimeMillis();
        List<SougouCacheBean> sougouAdCache = SougouCacheBean.getSougouAdCache();
        sougouAdCache.add(new SougouCacheBean(time));
        AdsRecordUtils.getInstance().putString(Constants.AD_SOUGOU_CACHE, new Gson().toJson(sougouAdCache));
    }

    public static void clearSougouAdCache() {
        AdsRecordUtils.getInstance().putString(Constants.AD_SOUGOU_CACHE, "");
    }

    public static boolean isShowSougouAd() {
        //阈值条件
        if (UserManager.getInst().getmRuleBean().getSougouGoldRule().getDPT() <= UserManager.getInst().getUserBeam().getGoldAccount().getSum()//阈值最小值限制
                && ( UserManager.getInst().getmRuleBean().getSougouGoldRule().getDptMax() == 0
                || UserManager.getInst().getmRuleBean().getSougouGoldRule().getDptMax() >= UserManager.getInst().getUserBeam().getGoldAccount().getSum()//阈值最大值限制
                ) ) {//阈值
            //搜狗过来的广告在2次点击显示转圈圈
            long l = System.currentTimeMillis();
            List<SougouCacheBean> sougouAdCache = SougouCacheBean.getSougouAdCache();
            if (sougouAdCache.size() > 0) {
                if (!sougouAdCache.get(0).isSameDay(l)) {//不是同一天
                    SougouCacheBean.clearSougouAdCache();//
                    //显示
                    return true;
                } else {//同一天
                    //区分一小时
                    int oneHourNum = 0;
                    for (SougouCacheBean sougouCacheBean : sougouAdCache) {
                        if (l - sougouCacheBean.getTime() > 60 * 60 * 1000) {//大于1小时删除
                            sougouCacheBean.setTooLong(true);
                        } else {
                            oneHourNum++;
                        }
                    }
                    if (oneHourNum < UserManager.getInst().getmRuleBean().getSougouGoldRule().getHourMaxCount()) {
                        //1小时缓存数 小于 后台规定最大数量 显示剩余个数  显示
                        if (sougouAdCache.size() < UserManager.getInst().getmRuleBean().getSougouGoldRule().getDayMaxCount()) {
                            //一天缓存数量 小于 后台规定一天最大数 显示
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                //显示
                return UserManager.getInst().getmRuleBean().getSougouGoldRule().getDayMaxCount() != 0;
            }
        } else {
            return false;
        }
    }

    public static int GetSougouAdNumHour() {
        //搜狗过来的广告在2次点击显示转圈圈
        long l = System.currentTimeMillis();
        List<SougouCacheBean> sougouAdCache = SougouCacheBean.getSougouAdCache();
        if (sougouAdCache.size() > 0) {
            if (!sougouAdCache.get(0).isSameDay(l)) {//不是同一天
                SougouCacheBean.clearSougouAdCache();//
                //显示
                return UserManager.getInst().getmRuleBean().getSougouGoldRule().getHourMaxCount();
            } else {//同一天
                //区分一小时
                int oneHourNum = 0;//一小时缓存数
                for (SougouCacheBean sougouCacheBean : sougouAdCache) {
                    if (l - sougouCacheBean.getTime() > 60 * 60 * 1000) {//大于1小时删除
                        sougouCacheBean.setTooLong(true);
                    } else {
                        oneHourNum++;
                    }
                }
                int dayNum = UserManager.getInst().getmRuleBean().getSougouGoldRule().getDayMaxCount() - sougouAdCache.size();//一天剩余数
                int hourNum = UserManager.getInst().getmRuleBean().getSougouGoldRule().getHourMaxCount() - oneHourNum;//一小时剩余数
                return dayNum > hourNum ? hourNum : dayNum;//取小
            }
        } else {
            //显示
            return UserManager.getInst().getmRuleBean().getSougouGoldRule().getHourMaxCount();
        }
    }
}
