package com.zhangku.qukandian.bean;

import android.support.annotation.IntDef;

import com.zhangku.qukandian.manager.UserManager;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 赚赏金
 */
public class DownAppBean extends DataSupport implements Serializable{
    /**
     * logoImgSrc : http://cdn.qu.fi.pqmnz.com/test/img/201705/513737912d7646cb8dd990fa2be5f429.jpg
     * appName : 支付宝1
     * url : http://cdn.qu.fi.pqmnz.com/test/img/201705/513737912d7646cb8dd990fa2be5f429.jpg
     * appPackage : zhifubao1.com
     * awardsNum : 500
     * gold : 500
     * trialMinutes : 3
     * description : 支付宝1广告语
     * isUnderWay : false
     * orderId : 2
     * id : 3
     */
    private String logoImgSrc;//logo
    private String appName;//App名称
    private String url;//安装包下载地址
    private String appPackage;//app包名
    private int awardsNum;//任务次数
    private int gold;//金币数量
    private int trialMinutes;//试完分钟数
    private String description;//广告语
//    private boolean isUnderWay;//是否进行中  移除 本地判断
    private int orderId;//排序
    private int id;//
    private int taskId;

    private String taskExplain;
    private List<BountyTaskStepBean> bountyTaskSteps;
    ///////////app 数据缓存字段
    private long playTime = -1;//试玩时间
    private String downPath;//下载文件路径名称
    private int stage = -1;//任务进行阶段 -1 无操作 0 开始下载 1、下载完成 2、安装完成（未打开app） 3、试玩中(打开了app) 4 任务完成
    private long startTime;//试玩时间（点击试玩）
    private long downId;//系统下载id //显示进度用
    private int userId = UserManager.getInst().getUserBeam().getId();//用户id，区别用户切换账号

    public static final int APP_NULL = -1;//无操作
    public static final int APP_DOWN_START = 0;//开始下载
    public static final int APP_DOWN_END = 1;//下载完成
    public static final int APP_INSTALL = 2;//安装完成
    public static final int APP_PLAY = 3;//试玩中
    public static final int APP_DONE = 4;//任务完成
    @IntDef({APP_NULL, APP_DOWN_START, APP_DOWN_END,APP_INSTALL,APP_PLAY,APP_DONE})
    //本地广告类型位置
    public @interface DownState {
    }

    public String getTaskExplain() {
        return taskExplain;
    }

    public void setTaskExplain(String taskExplain) {
        this.taskExplain = taskExplain;
    }

    public List<BountyTaskStepBean> getBountyTaskSteps() {
        return bountyTaskSteps;
    }

    public void setBountyTaskSteps(List<BountyTaskStepBean> bountyTaskSteps) {
        this.bountyTaskSteps = bountyTaskSteps;
    }

    public long getDownId() {
        return downId;
    }

    public void setDownId(long downId) {
        this.downId = downId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public String getDownPath() {
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(@DownState int stage) {
        this.stage = stage;
    }

    public String getLogoImgSrc() {
        return logoImgSrc;
    }

    public void setLogoImgSrc(String logoImgSrc) {
        this.logoImgSrc = logoImgSrc;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public int getAwardsNum() {
        return awardsNum;
    }

    public void setAwardsNum(int awardsNum) {
        this.awardsNum = awardsNum;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getTrialMinutes() {
        return trialMinutes;
    }

    public void setTrialMinutes(int trialMinutes) {
        this.trialMinutes = trialMinutes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static class BountyTaskStepBean extends DataSupport  implements Serializable{

        private String stepExplain;//步骤说明
        private String orderId;//步骤 从0开始算
        private String taskId;
        private List<BountyTaskStepImgsBean> bountyTaskStepImgs;

        public String getStepExplain() {
            return stepExplain;
        }

        public void setStepExplain(String stepExplain) {
            this.stepExplain = stepExplain;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public List<BountyTaskStepImgsBean> getBountyTaskStepImgs() {
            return bountyTaskStepImgs;
        }

        public void setBountyTaskStepImgs(List<BountyTaskStepImgsBean> bountyTaskStepImgs) {
            this.bountyTaskStepImgs = bountyTaskStepImgs;
        }

        public static class BountyTaskStepImgsBean implements Serializable{
            private int stepId;
            private int id;
            private String stepImgSrc;

            public int getStepId() {
                return stepId;
            }

            public void setStepId(int stepId) {
                this.stepId = stepId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getStepImgSrc() {
                return stepImgSrc;
            }

            public void setStepImgSrc(String stepImgSrc) {
                this.stepImgSrc = stepImgSrc;
            }
        }
    }

    @Override
    public String toString() {
        return "DownAppBean{" +
                "logoImgSrc='" + logoImgSrc + '\'' +
                ", appName='" + appName + '\'' +
                ", url='" + url + '\'' +
                ", appPackage='" + appPackage + '\'' +
                ", awardsNum=" + awardsNum +
                ", gold=" + gold +
                ", trialMinutes=" + trialMinutes +
                ", description='" + description + '\'' +
                ", orderId=" + orderId +
                ", id=" + id +
                ", taskId=" + taskId +
                ", taskExplain='" + taskExplain + '\'' +
                ", bountyTaskSteps=" + bountyTaskSteps +
                ", playTime=" + playTime +
                ", downPath='" + downPath + '\'' +
                ", stage=" + stage +
                ", startTime=" + startTime +
                ", downId=" + downId +
                ", userId=" + userId +
                '}';
    }
}
