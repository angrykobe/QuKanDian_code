package com.zhangku.qukandian.manager;

import android.content.ContentValues;
import android.text.TextUtils;

import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.AdsCountsBean;
import com.zhangku.qukandian.bean.AppInfo;
import com.zhangku.qukandian.bean.ChannelBean;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.LabelTermsBean;
import com.zhangku.qukandian.bean.LocalFavoriteBean;
import com.zhangku.qukandian.bean.LocalMessageBean;
import com.zhangku.qukandian.bean.MessageBean;
import com.zhangku.qukandian.bean.PostTextDatasBean;
import com.zhangku.qukandian.bean.PostTextImagesBean;
import com.zhangku.qukandian.bean.PostTextVideosBean;
import com.zhangku.qukandian.bean.ReadRecordBean;
import com.zhangku.qukandian.bean.SearchHistoryBean;
import com.zhangku.qukandian.bean.TodayNewBean;
import com.zhangku.qukandian.config.Constants;

import com.zhangku.qukandian.utils.SDUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.crud.DataSupport.deleteAll;
import static org.litepal.crud.DataSupport.deleteAllAsync;
import static org.litepal.crud.DataSupport.order;
import static org.litepal.crud.DataSupport.where;

/**
 * Created by yuzuoning on 2017/3/28.
 */

public class DBTools {

    /**
     * sd卡是否可用
     *
     * @return
     */
    public static boolean sdCanUse() {
        return !SDUtils.ExistSDCard() || SDUtils.getSDFreeSize() < 10;
    }

    public static void addToChannels(final List<ChannelBean> channelBeans, int type) {
        if (sdCanUse() || null == channelBeans || channelBeans.size() == 0) return;
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < channelBeans.size(); i++) {
                    ChannelBean bean = channelBeans.get(i);
                    bean.setOrderId(i);
                    updateChannel(bean);
                }
            }
        }.start();
    }

    public static List<ChannelBean> queryByChannel(int type) {
        return DataSupport.order("orderId").where("kindType = ?", type + "").find(ChannelBean.class);
    }

    public static void updateChannel(ChannelBean bean) {
        if (sdCanUse() || null == bean) return;
        List<ChannelBean> list = where("channelId = ?", bean.getChannelId() + "").find(ChannelBean.class);
        if (null == list || list.size() < 1) {
            bean.save();
        } else {
            ContentValues cv = new ContentValues();
            cv.put("channelId", bean.getChannelId());
            cv.put("displayName", bean.getDisplayName());
            cv.put("kindType", bean.getKindType());
            cv.put("yesNo", bean.isYesNo());
            cv.put("orderId", bean.getOrderId());
            cv.put("name", bean.getName());
            DataSupport.update(ChannelBean.class, cv, list.get(0).getId());
        }
    }

    public static void changeChannel(final List<ChannelBean> channelBeans) {
        if (sdCanUse() || null == channelBeans) return;
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (DataSupport.deleteAll(ChannelBean.class, "kindType = ?", channelBeans.get(0).getKindType() + "") > 0) {
                    DataSupport.saveAll(channelBeans);
                }
            }
        }.start();
    }

    public static void addSearchHistory(SearchHistoryBean bean) {
        if (sdCanUse() || null == bean) return;
        List<SearchHistoryBean> temp = where("keyword like ?", bean.getKeyword()).find(SearchHistoryBean.class);
        if (null != temp && temp.size() > 0) {
            for (SearchHistoryBean b : temp) {
                DataSupport.delete(SearchHistoryBean.class, b.getId());
            }
            bean.save();
        } else {
            bean.save();
        }
    }

    public static List<SearchHistoryBean> getSearchHistory() {
        return order("date desc").limit(10).find(SearchHistoryBean.class);
    }

    public static void clearSearchHistory() {
        deleteAll(SearchHistoryBean.class, "id >= 0");
    }

    public static void addFarverite(DetailsBean bean) {
        if (sdCanUse() || null == bean) return;
        List<DetailsBean> temp = DataSupport.where("newId = ?", bean.getNewId() + "").find(DetailsBean.class);
        if (temp.size() > 0) {
            deleteAll(DetailsBean.class, "newId = ?", bean.getNewId() + "");
            deleteAll(PostTextImagesBean.class, "postId = ?", bean.getNewId() + "");
            bean.setReleaseTime(String.valueOf(System.currentTimeMillis()));
            DataSupport.saveAll(bean.getPostTextImages());
            bean.setNewId(bean.getId());
            if (bean.getChannel() != null) {
                bean.setChannelId(bean.getChannel().getId());
            }
            bean.save();
        } else {
            bean.setReleaseTime(String.valueOf(System.currentTimeMillis()));
            if (bean.getChannel() != null) {
                bean.setChannelId(bean.getChannel().getId());
            }
            bean.setNewId(bean.getId());
            DataSupport.saveAll(bean.getPostTextImages());
            bean.save();
        }

    }

    public static void saveLocalFavorite(LocalFavoriteBean bean) {
        if (sdCanUse() || null == bean) return;
        List<LocalFavoriteBean> localFavoriteBeen = DataSupport.where("postId = ?", bean.getPostId() + "").find(LocalFavoriteBean.class);
        if (localFavoriteBeen.size() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("postId", bean.getPostId());
            contentValues.put("isFavorite", bean.isFavorite());
            DataSupport.update(LocalFavoriteBean.class, contentValues, bean.getId());
        } else {
            bean.save();
        }
    }

    public static boolean getStateById(int id) {
        List<LocalFavoriteBean> bean = DataSupport.where("postId = ?", id + "").find(LocalFavoriteBean.class);
        if (bean.size() > 0) {
            return bean.get(0).isFavorite();
        } else {
            return false;
        }
    }

    public static List<InformationBean> getFarvorite() {
        List<InformationBean> informationBeen = new ArrayList<>();
        List<DetailsBean> details = DataSupport.order("releaseTime desc").limit(20).find(DetailsBean.class);

        for (DetailsBean bean : details) {
            InformationBean info = new InformationBean();
            info.setId(bean.getNewId());
            List<PostTextImagesBean> post = where("postId = ? ", bean.getNewId() + "").find(PostTextImagesBean.class);
            info.setReleaseTime(bean.getReleaseTime());
            info.setAuthorName(bean.getAuthorName());
            info.setSummary(bean.getSummary());
            info.setPostId(bean.getNewId());
            info.setChannelId(bean.getChannelId());
            info.setDurationTime(bean.getDurationTime());
            info.setPostTextImages(post);
            info.setTextType(bean.getTextType());
            info.setReleaseTimeMemo(bean.getReleaseTimeMemo());
            info.setViewNumber(bean.getViewNumber());
            info.setViewSeedNumber(bean.getViewSeedNumber());
            info.setTitle(bean.getTitle());
            informationBeen.add(info);
        }
        return informationBeen;
    }

    public static int deleteFarvorite(InformationBean bean) {
        return DataSupport.deleteAll(DetailsBean.class, "newId = ?", bean.getId() + "");
    }

    public static void deletaAllFarvorite() {
        DataSupport.deleteAll(DetailsBean.class);
    }

    public static List<MessageBean> getMessages() {
        List<MessageBean> itemMessageBeanList = new ArrayList<>();
        List<LocalMessageBean> messageBeanList = DataSupport.order("creationTime desc").find(LocalMessageBean.class);
        if (messageBeanList.size() > 0) {
            for (int i = 0; i < messageBeanList.size(); i++) {
                MessageBean temMessageBean = new MessageBean();
                temMessageBean.setContent(messageBeanList.get(i).getContent());
                temMessageBean.setCreationTime(messageBeanList.get(i).getCreationTime());
                temMessageBean.setId(messageBeanList.get(i).getNewId());
                temMessageBean.setType(messageBeanList.get(i).getMessageType());
                temMessageBean.setLinkTo(messageBeanList.get(i).getLinkTo());
                temMessageBean.setTitle(messageBeanList.get(i).getTitle());
                temMessageBean.setActionUrl(messageBeanList.get(i).getActionUrl());
                itemMessageBeanList.add(temMessageBean);
            }
        }
        return itemMessageBeanList;
    }

    public static LocalMessageBean getMessageDate(int typeVideo) {
        List<LocalMessageBean> beans = DataSupport.order("creationTime desc").where("messageType = ?", typeVideo + "").limit(1).find(LocalMessageBean.class);
        if (beans.size() > 0) {
            return beans.get(0);
        }
        return null;
    }

    public static void insertList(final List<InformationBean> beans, final int channelId, final int type) {
        if (sdCanUse() || null == beans || beans.size() == 0) return;
        new Thread() {
            @Override
            public void run() {
                super.run();
                String key = channelId + "" + type;
                UserSharedPreferences.getInstance().putLong(key, System.currentTimeMillis());
                deleteAll(InformationBean.class, "channelId = ? and textType = ?", channelId + "", type + "");
                for (int i = 0; i < beans.size(); i++) {
                    InformationBean b = beans.get(i);
                    b.setChannelId(channelId);
                    if (b.getChannel() != null) {
                        b.setChannelName(b.getChannel().getName());
                    }
                    DataSupport.saveAll(b.getPostTextImages());
                    if (type == Constants.TYPE_VIDEO) {
                        DataSupport.saveAll(b.getPostTextVideos());
                    }
                    b.save();
                }
            }
        }.start();
    }

    public static List<InformationBean> queryListByChannelId(int channelId, int type) {
        List<InformationBean> been = DataSupport.where("channelId = ? and textType = ?", channelId + "", type + "").find(InformationBean.class);
        for (int i = 0; i < been.size(); i++) {
            List<PostTextImagesBean> imgs = DataSupport.where("postId = ?", been.get(i).getPostId() + "").find(PostTextImagesBean.class);
            if (type == Constants.TYPE_VIDEO) {
                List<PostTextVideosBean> videos = DataSupport.where("postId = ?", been.get(i).getPostId() + "").find(PostTextVideosBean.class);
                been.get(i).setPostTextVideos(videos);
            }
            been.get(i).setPostTextImages(imgs);
        }
        return been;
    }

    public static void addReadRecord(int postId, String title) {
        List<ReadRecordBean> list = DataSupport.where("postId = ? and title = ?", postId + "", title).find(ReadRecordBean.class);
        if (sdCanUse() || list.size() > 0) {
            return;
        } else {
            ReadRecordBean bean = new ReadRecordBean();
            bean.setPostId(postId);
            bean.setTitle(title);
            bean.save();
        }
    }

    public static boolean queryReadRecordByPostId(int postId, String title) {
        List<ReadRecordBean> list = DataSupport.where("postId = ? and title = ?", postId + "", title).find(ReadRecordBean.class);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void clearAll() {
//        deleteAllAsync(ChannelBean.class);
//        deleteAllAsync(SearchHistoryBean.class);
//        deleteAllAsync(DetailsBean.class);
//        deleteAllAsync(LocalFavoriteBean.class);
//        deleteAllAsync(PostTextVideosBean.class);
//        deleteAllAsync(InformationBean.class);
//        deleteAllAsync(LocalMessageBean.class);
//        deleteAllAsync(DownAppBean.class);
        deleteAllAsync(ChannelBean.class);
        deleteAllAsync(SearchHistoryBean.class);
        deleteAllAsync(DetailsBean.class);
        deleteAllAsync(PostTextImagesBean.class);
        deleteAllAsync(PostTextVideosBean.class);
        deleteAllAsync(PostTextDatasBean.class);
        deleteAllAsync(LabelTermsBean.class);
        deleteAllAsync(LocalMessageBean.class);
        deleteAllAsync(LocalFavoriteBean.class);
        deleteAllAsync(InformationBean.class);
        deleteAllAsync(ReadRecordBean.class);
        deleteAllAsync(TodayNewBean.class);
        deleteAllAsync(AdsCountsBean.class);
        deleteAllAsync(AppInfo.class);
        deleteAllAsync(DownAppBean.class);
        deleteAllAsync(DownAppBean.BountyTaskStepBean.class);
//        deleteAllAsync(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.class);
    }

    public static AppInfo qereyAppInfo(String packname) {
        List<AppInfo> list = DataSupport.where("packageName = ?", packname).find(AppInfo.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


    /////////////////////赚赏金任务
    public static void changeDownAppBean(final List<DownAppBean> list) {
        if (sdCanUse() || null == list) return;
        for (DownAppBean bean : list) {
            //更新数据
            if (DataSupport.isExist(DownAppBean.class, "appPackage=? and userId=?", bean.getAppPackage(), "" + UserManager.getInst().getUserBeam().getId())) {
                DownAppBean first = DataSupport.where("appPackage = ? and userId=?", bean.getAppPackage(), "" + UserManager.getInst().getUserBeam().getId()).findFirst(DownAppBean.class);

                bean.setDownPath(first.getDownPath());
                bean.setPlayTime(first.getPlayTime());
                bean.setStage(first.getStage());
            } else {//没有数据保存本地数据
                bean.save();
            }
        }
    }

    public static void updateDownAppBean(DownAppBean bean) {
        if (sdCanUse() || null == bean) return;
        DownAppBean first = DataSupport.where("appPackage = ? and userId=?", bean.getAppPackage(), "" + UserManager.getInst().getUserBeam().getId()).findFirst(DownAppBean.class);
        if (null == first) return;
        first.setDownPath(bean.getDownPath());
        first.setPlayTime(bean.getPlayTime());
        first.setStage(bean.getStage());
        first.update(first.getId());
    }
    /////////////////////自主广告

    public static void saveMyselfAdBean(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        if (sdCanUse() || bean == null) return;
        bean.save();
    }

    /*public static boolean getMyselfAdBeanExist(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        AdLocationBeans.AdLocationsBean.ClientAdvertisesBean movies;
        if (TextUtils.isEmpty(bean.getAdLink())) {
            movies = DataSupport.where("advertiserId = ?", "" + bean.getAdverId()).findFirst(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.class);
        } else {
            movies = DataSupport.where("targetAdLink = ? and advertiserId = ?", "" + bean.getAdLink(), "" + bean.getAdverId()).findFirst(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.class);
        }
        return movies != null;
    }*/

    /**
     * @return
     */
    /*public static int getMyselfAdBeanCount() {*/
    /*    return DataSupport.count(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.class);*/
    /*}*/

    public static void clearMyselfAdBean() {
        DataSupport.deleteAll(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.class);
    }

}
