package com.zhangku.qukandian.biz.adcore;

public enum ZKAdTypeEnum {
    NONE("空", -1), FLOW_NATIVE_IMG("原生", 0), FLOW_THREE_IMG("信息流三图（三图加文字）", 1), FLOW_BIG_IMG("信息流大图（大图加文字）", 2), FLOW_SMALL_IMG("信息流小图（左图右文）", 3), SPLASH("开屏", 4);
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private ZKAdTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (ZKAdTypeEnum c : ZKAdTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
