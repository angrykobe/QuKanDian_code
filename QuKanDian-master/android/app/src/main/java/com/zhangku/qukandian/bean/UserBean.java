package com.zhangku.qukandian.bean;

import android.text.TextUtils;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetTestMissionProtocol;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/4/1.
 */

public class UserBean {
    /**
     * userName : sample string 1
     * name : sample string 2
     * nickName : sample string 3
     * mobileNumber : sample string 4
     * avatarUrl : sample string 5
     * birthDay : 2017-04-07T15:00:16.9410047+08:00
     * email : sample string 6
     * sumCheckIn : 7
     * postFavoriteNumber：4
     * mentorUser : {"mentorId":1,"mentooId":1,"sumMentee":1,"sumSubMentee":2,"id":3}
     * coinAccount : {"amount":1,"sum":2,"id":3,"preAmount": 3.0}
     * goldAccount : {"amount":1,"sum":2,"id":3}
     * bag1Status: 0,
     * bag8Status: 0,
     * "isGrayUser": false
     * "userAdsType": 1
     * id : 8
     */

    private int id;//
    private String userName = "";
    private String name = "";
    private String nickName = "";
    private String mobileNumber = "";
    private String avatarUrl = "";//用户头像
    private String birthDay = "";
    private String email = "";
    private int postFavoriteNumber;//
    private int sumCheckIn = 0;//签到次数 签到后签到加1每日签到
    private Integer bag1Status;//1元礼包任务状况
    private Integer bag8Status;//8元礼包任务状况
    private Integer sevenPlusBagStatus;//7+1活动状态 有值代表有资格参加7+1活动
    private String sevenPlusBagV3EndTime;//	 每个阶段的过期时间
    private int sevenPlusBagUserNumber;//如果在正常的神秘金活动内，需要计算收徒数
    private MentorUserBean mentorUser = new MentorUserBean();//	  师徒用户
    private CoinAccountBean coinAccount = new CoinAccountBean();//金钱帐户
    private GoldAccountBean goldAccount = new GoldAccountBean();//金币帐户
    private int userAdsType;//广告红包用户类型  1：新用户  2：用1天用户 3：老用户
    private MissionBean mission = new MissionBean();//完成的任务
    private WechatUserBean wechatUser;//微信用户
    private List<MissionGrarntedUsersBean> missionGrarntedUsers = new ArrayList<>();//有权参加任务的信息
    private boolean isGrayUser;//是否是灰度授权用户
    ///////////292
    private int level = 0;//当前等级
    private String levelDisplayName = "";//当前等级名称
    private int tLevel;//可升等级
    private boolean headwearFlag;//是否有专属头饰
    private boolean levelGranted;//用户是否有等级权限
    private boolean isHeben;//是否已经提现过
    private float nextExp;
    private float startExp;
    private float exp;

    public float getNextExp() {
        return nextExp;
    }

    public void setNextExp(float nextExp) {
        this.nextExp = nextExp;
    }

    public float getStartExp() {
        return startExp;
    }

    public void setStartExp(float startExp) {
        this.startExp = startExp;
    }

    public float getExp() {
        return exp;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public boolean isHeben() {
        return isHeben;
    }

    public void setHeben(boolean heben) {
        isHeben = heben;
    }

    public boolean isLevelGrand() {
        return levelGranted;
    }

    public void setLevelGrand(boolean levelGrand) {
        this.levelGranted = levelGrand;
    }

    public boolean isHeadwearFlag() {
        return headwearFlag;
    }

    public void setHeadwearFlag(boolean headwearFlag) {
        this.headwearFlag = headwearFlag;
    }

    public String getLevelDisplayName() {
        return levelDisplayName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int gettLevel() {
        return tLevel;
    }

    public void settLevel(int tLevel) {
        this.tLevel = tLevel;
    }

    public void setLevelDisplayName(String levelDisplayName) {
        this.levelDisplayName = levelDisplayName;
    }

    public int getUserAdsType() {
        return userAdsType;
    }

    public void setUserAdsType(int userAdsType) {
        this.userAdsType = userAdsType;
    }

    public boolean isGrayUser() {
        return isGrayUser;
    }

    public void setGrayUser(boolean grayUser) {
        isGrayUser = grayUser;
    }

    public List<UserBean.MissionGrarntedUsersBean> getMissionGrarntedUsers() {
        return missionGrarntedUsers;
    }

    public void setMissionGrarntedUsers(List<UserBean.MissionGrarntedUsersBean> missionGrarntedUsers) {
        this.missionGrarntedUsers = missionGrarntedUsers;
    }

    public int getSevenPlusBagUserNumber() {
        return sevenPlusBagUserNumber;
    }

    public void setSevenPlusBagUserNumber(int sevenPlusBagUserNumber) {
        this.sevenPlusBagUserNumber = sevenPlusBagUserNumber;
    }

    public UserBean() {
    }

    public UserBean(String userName, String name, String nickName, String mobileNumber, String avatarUrl, String birthDay, String email, int id) {
        this.userName = userName;
        this.name = name;
        this.nickName = nickName;
        this.mobileNumber = mobileNumber;
        this.avatarUrl = avatarUrl;
        this.birthDay = birthDay;
        this.email = email;
        this.id = id;
    }

    public UserBean(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public String getSevenPlusBagV3EndTime() {
        return sevenPlusBagV3EndTime;
    }

    public void setSevenPlusBagV3EndTime(String sevenPlusBagV3EndTime) {
        this.sevenPlusBagV3EndTime = sevenPlusBagV3EndTime;
    }

    public Integer getSevenPlusBagStatus() {
        return sevenPlusBagStatus;
    }

    public void setSevenPlusBagStatus(Integer sevenPlusBagStatus) {
        this.sevenPlusBagStatus = sevenPlusBagStatus;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPostFavoriteNumber() {
        return postFavoriteNumber;
    }

    public void setPostFavoriteNumber(int postFavoriteNumber) {
        this.postFavoriteNumber = postFavoriteNumber;
    }

    public Integer getBag1Status() {
        return bag1Status;
    }

    public void setBag1Status(Integer bag1Status) {
        this.bag1Status = bag1Status;
    }

    public Integer getBag8Status() {
        return bag8Status;
    }

    public void setBag8Status(Integer bag8Status) {
        this.bag8Status = bag8Status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSumCheckIn() {
        return sumCheckIn;
    }

    public void setSumCheckIn(int sumCheckIn) {
        this.sumCheckIn = sumCheckIn;
    }

    public MentorUserBean getMentorUser() {
        return mentorUser;
    }

    public void setMentorUser(MentorUserBean mentorUser) {
        this.mentorUser = mentorUser;
    }

    public CoinAccountBean getCoinAccount() {
        return coinAccount;
    }

    public void setCoinAccount(CoinAccountBean coinAccount) {
        this.coinAccount = coinAccount;
    }

    public GoldAccountBean getGoldAccount() {
        return goldAccount;
    }

    public void setGoldAccount(GoldAccountBean goldAccount) {
        this.goldAccount = goldAccount;
    }

    public int getId() {
        int idd = UserSharedPreferences.getInstance().getInt(Constants.USER_ID);
        return idd == 0 ? id : idd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MissionBean getMission() {
        return mission;
    }

    public void setMission(MissionBean mission) {
        this.mission = mission;
    }

    public WechatUserBean getWechatUser() {
        return wechatUser;
    }

    public void setWechatUser(WechatUserBean wechatUser) {
        this.wechatUser = wechatUser;
    }


    public class MentorUserBean {

        /**
         * mentorId : 1
         * mentooId : 1
         * sumMentee : 1
         * sumSubMentee : 2
         * id : 3
         */
        private String mentorNickName = "";
        private int mentorId = 0;//师傅id
        private int mentooId = 0;//师傅的师傅（师公）的id
        private int sumMentee = 0;
        private int sumSubMentee = 0;
        private int id = 0;

        public String getMentorNickName() {
            return mentorNickName;
        }

        public void setMentorNickName(String mentorNickName) {
            this.mentorNickName = mentorNickName;
        }

        public int getMentorId() {
            return mentorId;
        }

        public void setMentorId(int mentorId) {
            this.mentorId = mentorId;
        }

        public int getMentooId() {
            return mentooId;
        }

        public void setMentooId(int mentooId) {
            this.mentooId = mentooId;
        }

        public int getSumMentee() {
            return sumMentee;
        }

        public void setSumMentee(int sumMentee) {
            this.sumMentee = sumMentee;
        }

        public int getSumSubMentee() {
            return sumSubMentee;
        }

        public void setSumSubMentee(int sumSubMentee) {
            this.sumSubMentee = sumSubMentee;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public class CoinAccountBean {
        /**
         * amount : 1.0
         * sum : 2.0
         * id : 3
         */

        private double amount = 0;
        private double sum = 0;
        private int id = 0;
        private double preAmount = 0;

        public double getPreAmount() {
            return preAmount;
        }

        public void setPreAmount(double preAmount) {
            this.preAmount = preAmount;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public class GoldAccountBean {
        /**
         * amount : 1
         * sum : 2
         * id : 3
         */
        private int amount = 0;//账户金币（余额）
        private long sum = 0;//总金币(历史总金币)
        private int id = 0;
        private int todayAmount = 0;//今日收益

        public int getTodayAmout() {
            return todayAmount;
        }

        public void setTodayAmout(int todayAmout) {
            this.todayAmount = todayAmout;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public long getSum() {
            return sum;
        }

        public void setSum(long sum) {
            this.sum = sum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "GoldAccountBean{" +
                    "amount=" + amount +
                    ", sum=" + sum +
                    ", id=" + id +
                    ", todayAmount=" + todayAmount +
                    '}';
        }
    }

    public class MissionBean {
        private List<String> finished = new ArrayList<>();

        public List<String> getFinished() {
            return finished;
        }

        public void setFinished(List<String> finished) {
            this.finished = finished;
        }
    }

    public class WechatUserBean {
        /*
        openid	普通用户的标识，对当前开发者帐号唯一
        nickname	普通用户昵称
        sex	普通用户性别，1为男性，2为女性
        province	普通用户个人资料填写的省份
        city	普通用户个人资料填写的城市
        country	国家，如中国为CN
        headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
        privilege	用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
        unionid	用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
         */

        /**
         * unionId : sample string 1
         * openId : sample string 2
         * nickName : sample string 3
         * sex : 0
         * headimgUrl : sample string 4
         * province : sample string 5
         * city : sample string 6
         * country : sample string 7
         * id : 8
         */

        private String unionId;//
        private String openId;//
        private String nickName;//
        private int sex;//
        private String headimgUrl;//
        private String province;//
        private String city;//
        private String country;//
        private int id;//

        public String getUnionId() {
            return unionId;
        }

        public void setUnionId(String unionId) {
            this.unionId = unionId;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getHeadimgUrl() {
            return headimgUrl;
        }

        public void setHeadimgUrl(String headimgUrl) {
            this.headimgUrl = headimgUrl;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class MissionGrarntedUsersBean {
        /**
         * userId : 10053
         * groupId : 1
         * missionId : 28
         * mission : {"name":"share_article","displayName":"分享转发","missionRules":[{"missionId":28,"name":"default","displayName":"default","minCoinAmount":0,"minGoldAmount":20,"expType":0,"isFinished":false,"id":67}],"kindType":2}
         * isActive : true
         */

        private int userId;
        private int groupId;
        private int missionId;
        private UserBean.MissionGrarntedUsersBean.MissionBeanX mission;
        private boolean isActive;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public int getMissionId() {
            return missionId;
        }

        public void setMissionId(int missionId) {
            this.missionId = missionId;
        }

        public UserBean.MissionGrarntedUsersBean.MissionBeanX getMission() {
            return mission;
        }

        public void setMission(UserBean.MissionGrarntedUsersBean.MissionBeanX mission) {
            this.mission = mission;
        }

        public boolean isIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public static class MissionBeanX {
            /**
             * name : share_article
             * displayName : 分享转发
             * missionRules : [{"missionId":28,"name":"default","displayName":"default","minCoinAmount":0,"minGoldAmount":20,"expType":0,"isFinished":false,"id":67}]
             * kindType : 2
             */

            private String name;
            private String displayName;
            private int kindType;
            private List<UserBean.MissionGrarntedUsersBean.MissionBeanX.MissionRulesBean> missionRules;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            public int getKindType() {
                return kindType;
            }

            public void setKindType(int kindType) {
                this.kindType = kindType;
            }

            public List<UserBean.MissionGrarntedUsersBean.MissionBeanX.MissionRulesBean> getMissionRules() {
                return missionRules;
            }

            public void setMissionRules(List<UserBean.MissionGrarntedUsersBean.MissionBeanX.MissionRulesBean> missionRules) {
                this.missionRules = missionRules;
            }

            public static class MissionRulesBean {
                /**
                 * missionId : 28
                 * name : default
                 * displayName : default
                 * minCoinAmount : 0
                 * minGoldAmount : 20
                 * expType : 0
                 * isFinished : false
                 * id : 67
                 */

                private int missionId;
                private String name;
                private String displayName;
                private double minCoinAmount;
                private int minGoldAmount;
                private int expType;
                private boolean isFinished;
                private int id;

                public int getMissionId() {
                    return missionId;
                }

                public void setMissionId(int missionId) {
                    this.missionId = missionId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDisplayName() {
                    return displayName;
                }

                public void setDisplayName(String displayName) {
                    this.displayName = displayName;
                }

                public double getMinCoinAmount() {
                    return minCoinAmount;
                }

                public void setMinCoinAmount(double minCoinAmount) {
                    this.minCoinAmount = minCoinAmount;
                }

                public int getMinGoldAmount() {
                    return minGoldAmount;
                }

                public void setMinGoldAmount(int minGoldAmount) {
                    this.minGoldAmount = minGoldAmount;
                }

                public int getExpType() {
                    return expType;
                }

                public void setExpType(int expType) {
                    this.expType = expType;
                }

                public boolean isIsFinished() {
                    return isFinished;
                }

                public void setIsFinished(boolean isFinished) {
                    this.isFinished = isFinished;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }
    }

    /**
     * 广告红包权限
     *
     * @return
     */
    public static boolean getAdGift() {
        boolean isActivity = false;
        if (!UserManager.ADS_CLICK_GIFT) {
            isActivity = true;
        } else {
            if (UserManager.getInst().hadLogin()) {
                if (UserManager.getInst().getUserBeam().getMissionGrarntedUsers() != null) {
                    for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                        if (Constants.ADS_CLICK_GIFT.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                            isActivity = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                        }
                    }
                }
            }
        }
        return isActivity;
    }

    /**
     * 是否有权限显示显示积分商城
     *
     * @return
     */
    public static boolean getShoppingJurisdiction() {
        boolean isActivity = false;
        if (!UserManager.MALL_DUIBA_COIN) {
            isActivity = true;
        } else {
            if (UserManager.getInst().hadLogin()) {
                if (UserManager.getInst().getUserBeam().getMissionGrarntedUsers() != null) {
                    for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                        if (Constants.DUI_BA.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                            isActivity = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                        }
                    }
                }
            }
        }
        return isActivity;
    }

    /**
     * 是否有权限显示热搜fragment
     *
     * @return
     */
    public static boolean getShowSougouFrag() {
        boolean isActivity = false;
        if (!UserManager.SOUGOU_SEARCH_GIFT) {
            isActivity = true;
        } else {
            if (UserManager.getInst().hadLogin()) {
                if (UserManager.getInst().getUserBeam().getMissionGrarntedUsers() != null) {
                    for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                        if (Constants.SEARCH_SOUGOU.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                            isActivity = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                        }
                    }
                }
            }
        }
        return isActivity;
    }
    //是否有权限显示详情页的阅读进度按钮
    public static boolean getShowReadProgress() {
        boolean isActivity = false;
        if (!UserManager.READ_PROGRESS) {
            isActivity = true;
        } else {
            if (UserManager.getInst().hadLogin()) {
                if (UserManager.getInst().getUserBeam().getMissionGrarntedUsers() != null) {
                    for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                        if (Constants.READ_PROGRESS.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                            isActivity = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                        }
                    }
                }
            }
        }
        return isActivity;
    }

    /**
     * 用户是否有高价文权限
     *
     * @return
     */
    public static boolean getHighPriceNews() {
        boolean isActivity = false;
        if (!UserManager.HIGH_PRICE_NEWS) {
            isActivity = true;
        } else {
            if (UserManager.getInst().hadLogin()) {
                if (UserManager.getInst().getUserBeam().getMissionGrarntedUsers() != null) {
                    for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                        if (Constants.HIGH_PRICE_NEWS.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                            isActivity = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                        }
                    }
                }
            }
        }
        return isActivity;
    }

    /**
     * 用户是否有权限（）
     *
     * @return
     */
    public static boolean getGrayJurisdiction(@AnnoCon.UserPower.JurisictionStr String str) {
        boolean isActivity = false;
        String grayJurisdiction = UserManager.getInst().getGrayJurisdiction();
        boolean hadGrayJurisdiction = grayJurisdiction.contains(str);
        if (TextUtils.isEmpty(grayJurisdiction)) {
            new GetTestMissionProtocol(QuKanDianApplication.getmContext(), null).postRequest();
        }
        if (!hadGrayJurisdiction) {
            isActivity = true;
        } else {
            List<MissionGrarntedUsersBean> missionGrarntedUsers = UserManager.getInst().getUserBeam().getMissionGrarntedUsers();//用户权限
            for (MissionGrarntedUsersBean bean : missionGrarntedUsers) {
                if (str.equals(bean.getMission().getName())) {
                    isActivity = bean.isIsActive();
                }
            }
        }
        return isActivity;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", email='" + email + '\'' +
                ", postFavoriteNumber=" + postFavoriteNumber +
                ", sumCheckIn=" + sumCheckIn +
                ", bag1Status=" + bag1Status +
                ", bag8Status=" + bag8Status +
                ", sevenPlusBagStatus=" + sevenPlusBagStatus +
                ", sevenPlusBagV3EndTime='" + sevenPlusBagV3EndTime + '\'' +
                ", sevenPlusBagUserNumber=" + sevenPlusBagUserNumber +
                ", mentorUser=" + mentorUser +
                ", coinAccount=" + coinAccount +
                ", goldAccount=" + goldAccount +
                ", userAdsType=" + userAdsType +
                ", mission=" + mission +
                ", wechatUser=" + wechatUser +
                ", missionGrarntedUsers=" + missionGrarntedUsers +
                ", isGrayUser=" + isGrayUser +
                '}';
    }
}
