package com.zhangku.qukandian.utils;

import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;

/**
 * Created by yuzuoning on 2018/1/2.
 */

public class IsShowShareState {
    public static boolean isShowForHighArt() {
        if (UserBean.getHighPriceNews()) {//高价文权限
            return true;
        } else {
            return false;
        }
    }

    public static boolean isShow() {
        if (isShareGold()) {
            return true;
        } else {
            return false;
        }
    }

    //金币文章分享权限
    public static boolean isShareGold() {
        UserBean.MissionGrarntedUsersBean bean = null;
        if (UserManager.getInst().hadLogin() && null != UserManager.getInst().getUserBeam().getMissionGrarntedUsers()) {
            for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                if (Constants.SHARE_ARTICLE.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                    bean = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i);
                }
            }
        }
        if (null == bean) {
            return false;
        } else {
            return UserManager.getInst().hadLogin() &&
                    null != UserManager.getInst().getUserBeam().getMissionGrarntedUsers()
                    && UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size() > 0
                    && bean.isIsActive();
        }
    }
}
