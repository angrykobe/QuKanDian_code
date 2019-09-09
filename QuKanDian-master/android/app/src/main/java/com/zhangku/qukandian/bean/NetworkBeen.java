package com.zhangku.qukandian.bean;

public class NetworkBeen {

    public String ip; // IPv4地址，必填。确保填写的内容为⽤用户设备的公⽹网出⼝口IP地址，客户端对接可 填unknown。未知填unknown
    public int connectionType; // ⽹网络类型，必填。1为WI-FI，2为 2G，3为3G， 4为4G, 未知为0
    public int operatorType; // 运营商类型，必填。1为移动，2为联通，3为电信, 未知为0
    public String cellularId; // 基站ID，选填。当前连接的运营商基站ID，⽤用于辅助⽤用户定位

    @Override
    public String toString() {
        return "NetworkBeen{" +
                "ip='" + ip + '\'' +
                ", connectionType=" + connectionType +
                ", operatorType=" + operatorType +
                ", cellularId='" + cellularId + '\'' +
                '}';
    }
}