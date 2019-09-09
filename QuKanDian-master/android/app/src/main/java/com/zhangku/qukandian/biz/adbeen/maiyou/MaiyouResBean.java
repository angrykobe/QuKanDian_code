package com.zhangku.qukandian.biz.adbeen.maiyou;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/1/14
 * 你不注释一下？
 */
public class MaiyouResBean {

    /**
     * ads : [{"tid":"7846fd3c8d864538a6f19a9b402a4f6d","adid":"s98t76q54","aurl":["http://vs.kyx936.cn/content/bxmhd/kp4.jpg"],"curl":"https://i.tandehao.com/activities/?appKey\\u003d147f58eff1204182a3ed4b9c1398eb6d\\u0026appEntrance\\u003d17\\u0026business\\u003dmoney\\u0026appType\\u003dapp","actype":1,"adtype":2,"aitype":1,"dspt":[],"clit":[],"stdt":[],"dwnt":[],"sist":[],"inst":[],"opnt":[],"title":"555","desc":"55545","appname":"C1007开屏新","apppkg":"345","apksize":0,"vc":0,"said":"H658wU3o"},{"tid":"7846fd3c8d864538a6f19a9b402a4f6d","adid":"s98t76q54","aurl":["http://engine.tuicoco.com/index/image?appKey\\u003d2pJRg8nLvusrd93qgeG1YDJb4RfB\\u0026adslotId\\u003d253076"],"curl":"http://engine.tuicoco.com/index/activity?appKey\\u003d2pJRg8nLvusrd93qgeG1YDJb4RfB\\u0026adslotId\\u003d253076","actype":1,"adtype":2,"aitype":1,"dspt":[],"clit":[],"stdt":[],"dwnt":[],"sist":[],"inst":[],"opnt":[],"title":"33","desc":"33","appname":"推啊C1007开屏","apppkg":"3434","apksize":0,"vc":0,"said":"H658wU3o"},{"tid":"7846fd3c8d864538a6f19a9b402a4f6d","adid":"s98t76q54","aurl":["http://vs.kyx936.cn/content/bxmhd/kp4.jpg"],"curl":"https://i.tandehao.com/activities/?appKey\\u003d147f58eff1204182a3ed4b9c1398eb6d\\u0026appEntrance\\u003d17\\u0026business\\u003dmoney\\u0026appType\\u003dapp","actype":1,"adtype":2,"aitype":1,"dspt":[],"clit":[],"stdt":[],"dwnt":[],"sist":[],"inst":[],"opnt":[],"title":"555","desc":"55545","appname":"C1007开屏新","apppkg":"345","apksize":0,"vc":0,"said":"H658wU3o"}]
     * code : 0
     * message : 成功
     * roll : 1
     * rollsec : 3
     * prelimit : 2
     * errorCode : 0
     */

    private int code;
    private String message;
    private int errorCode;
    private List<AdsBean> ads;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public static class AdsBean {
        /**
         * tid : 7846fd3c8d864538a6f19a9b402a4f6d
         * adid : s98t76q54
         * aurl : ["http://vs.kyx936.cn/content/bxmhd/kp4.jpg"]
         * curl : https://i.tandehao.com/activities/?appKey\u003d147f58eff1204182a3ed4b9c1398eb6d\u0026appEntrance\u003d17\u0026business\u003dmoney\u0026appType\u003dapp
         * actype : 1
         * adtype : 2
         * aitype : 1
         * dspt : []
         * clit : []
         * stdt : []
         * dwnt : []
         * sist : []
         * inst : []
         * opnt : []
         * title : 555
         * desc : 55545
         * appname : C1007开屏新
         * apppkg : 345
         * apksize : 0
         * vc : 0
         * said : H658wU3o
         */

        private String tid;//
        private String adid;//
        private String curl;//点击地址
        private int actype;//交互类型 1:展示类 3:下载类
        private int adtype;//	广告类型 1:banner 2:插屏 3:全屏 4:信息流5:激励视频
        private int aitype;//物料类型 1:图片 2:图文 3:文字 5:HTML 6:Video
        private String title;//
        private String desc;//
        private String appname;//app名称，当交互类型为3时有值
        private String apppkg;//app包名，当交互类型为3时有值
        private int apksize;//app大小，当交互类型为3时有值
        private int vc;//
        private String said;//
        private List<String> aurl;//图片地址,可能有多张图片
        private List<String> dspt;//展现上报地址,有多个时,需要逐一访问,不上报将影响收益
        private List<String> clit;//点击上报地址,有多个时,需要逐一访问,不上报将影响收益
        private List<String> stdt;//下载开始上报地址,有多个时,需要逐一访问,不上报将影响收益
        private List<String> dwnt;//下载完成上报地址,有多个时,需要逐一访问,不上报将影响收益
        private List<String> sist;//安装开始上报地址,有多个时,需要逐一访问,不上报将影响收益
        private List<String> inst;//安装完成上报地址,有多个时,需要逐一访问,不上报将影响收益
        private List<String> opnt;//安装完成后打开上报地址,有多个时,需要逐一访问,不上报将影响收益

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getAdid() {
            return adid;
        }

        public void setAdid(String adid) {
            this.adid = adid;
        }

        public String getCurl() {
            return curl;
        }

        public void setCurl(String curl) {
            this.curl = curl;
        }

        public int getActype() {
            return actype;
        }

        public void setActype(int actype) {
            this.actype = actype;
        }

        public int getAdtype() {
            return adtype;
        }

        public void setAdtype(int adtype) {
            this.adtype = adtype;
        }

        public int getAitype() {
            return aitype;
        }

        public void setAitype(int aitype) {
            this.aitype = aitype;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public String getApppkg() {
            return apppkg;
        }

        public void setApppkg(String apppkg) {
            this.apppkg = apppkg;
        }

        public int getApksize() {
            return apksize;
        }

        public void setApksize(int apksize) {
            this.apksize = apksize;
        }

        public int getVc() {
            return vc;
        }

        public void setVc(int vc) {
            this.vc = vc;
        }

        public String getSaid() {
            return said;
        }

        public void setSaid(String said) {
            this.said = said;
        }

        public List<String> getAurl() {
            return aurl;
        }

        public void setAurl(List<String> aurl) {
            this.aurl = aurl;
        }

        public List<String> getDspt() {
            return dspt;
        }

        public void setDspt(List<String> dspt) {
            this.dspt = dspt;
        }

        public List<String> getClit() {
            return clit;
        }

        public void setClit(List<String> clit) {
            this.clit = clit;
        }

        public List<String> getStdt() {
            return stdt;
        }

        public void setStdt(List<String> stdt) {
            this.stdt = stdt;
        }

        public List<String> getDwnt() {
            return dwnt;
        }

        public void setDwnt(List<String> dwnt) {
            this.dwnt = dwnt;
        }

        public List<String> getSist() {
            return sist;
        }

        public void setSist(List<String> sist) {
            this.sist = sist;
        }

        public List<String> getInst() {
            return inst;
        }

        public void setInst(List<String> inst) {
            this.inst = inst;
        }

        public List<String> getOpnt() {
            return opnt;
        }

        public void setOpnt(List<String> opnt) {
            this.opnt = opnt;
        }
    }
}
