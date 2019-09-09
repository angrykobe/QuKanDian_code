package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2018/1/5.
 */

public class RecommendDailyBean {
    /**
     * id : 19
     * order : 1
     * name : awaken_mentor_gold
     * displayName : 唤醒徒弟奖励
     * description : 唤醒徒弟奖励
     * kindType : 0
     * bindingButton : 去唤醒
     * coinAmountScope : 0
     * goldAmountScope : 500
     */

    private int id;
    private int order;
    private String name;
    private String displayName;
    private String description;
    private int kindType;
    private String bindingButton;
    private float coinAmountScope;//元
    private String goldAmountScope;//金币
    private boolean isFinished;//任务是否已完成



    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKindType() {
        return kindType;
    }

    public void setKindType(int kindType) {
        this.kindType = kindType;
    }

    public String getBindingButton() {
        return bindingButton;
    }

    public void setBindingButton(String bindingButton) {
        this.bindingButton = bindingButton;
    }

    public float getCoinAmountScope() {
        return coinAmountScope;
    }

    public void setCoinAmountScope(float coinAmountScope) {
        this.coinAmountScope = coinAmountScope;
    }

    public String getGoldAmountScope() {
        return goldAmountScope;
    }

    public void setGoldAmountScope(String goldAmountScope) {
        this.goldAmountScope = goldAmountScope;
    }
}
