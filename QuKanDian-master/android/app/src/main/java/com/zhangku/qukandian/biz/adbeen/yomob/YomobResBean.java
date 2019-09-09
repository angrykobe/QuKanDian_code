package com.zhangku.qukandian.biz.adbeen.yomob;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/1/14
 * 你不注释一下？
 */
public class YomobResBean {
    /**
     * status : 0
     * ad_type : 3
     * goal_type : 2
     * ad_count : 1
     * ads : [{"ad_id":"10007","title":"么么秀场","description":"萝莉御姐，各种姿势等你解锁","icon":"https://resource.yomob.com/adServing/mmzb/512.png","action_type":2,"expiration_time":1542796982,"mini_program_id":"gh_b61379f06d33","mini_program_path":"/pages/home/home?data=%7B%22publisher_id%22%3A%2210001%22%2C%22sub_id%22%3A%22youyouhezi%22%2C%22ad_id%22%3A%2210007%22%2C%22ad_type%22%3A3%2C%22imei%22%3A%22868291022995894%22%2C%22anid%22%3A%22%22%2C%22idfa%22%3A%22D081F4E0-708D-44DF-A6D1-C974BA36B671%22%2C%22idfv%22%3A%22%22%2C%22is_mp%22%3A0%2C%22os%22%3A2%2C%22network%22%3A10001%2C%22version%22%3A%221.0%22%7D","tracking_events":{"impression":["https://adxapitest.yomob.com.cn/adx/adsense/tracking?expiration_time=1542796982&channelid=10006&userid=123123595948851200&version=1.0.0&appid=youyouhezi&publisherid=10006&metadata=%7B%22ip%22%3A%22211.145.63.25%22%2C%22system%22%3A%22iPhone%22%2C%22adversion%22%3A%221.0.0%22%2C%22adtype%22%3A%228%22%2C%22ip_server%22%3A%22211.145.63.25%22%2C%22udid%22%3A%22D081F4E0-708D-44DF-A6D1-C974BA36B671%22%2C%22adname%22%3A%2246%22%2C%22net%22%3A%22wifi%22%2C%22bundleid%22%3A%221021897571%22%2C%22video_url_md5%22%3A%2210007%22%2C%22language%22%3A%22zh%22%2C%22error%22%3A%22%22%2C%22bundle_id%22%3A%221021897571%22%2C%22order%22%3A%220%22%2C%22scene%22%3A%22default%22%2C%22platform%22%3A%2246%22%2C%22display%22%3A%221%22%7D&time=1542789782&platform=1&counterid=cp_adview&rowkey=f7dc20f01542789782086qg9z&signature=c7c5aead148b260498ee70ea8f1c146e","https://adservingtest.yomob.com/adservingtracking?data=8ErhVK5bCpX3BVFNfohTZceUluEyuJaX0wulk51SBnn9GkE-Wv8grzH1b1KEXzy_vnHBpNcbSL4jT1ylFpTPJEF14xIEVj3rDJmwRyKRbj5MSwoBpduElEHdV_Wk-SGgoHEMEp2zkhhdioXPgy6pPPoRPVyDp5xN-bkT_SJbatEz4DqdJfL1B1Lt8sFdfmU2MCOBknVe7VYOkQdAk8LgTH2XUE7uIFNbzX0MrizZG1O2rDvGA8rBl4rs7U77XFKSZ1gKKRpFrE2mPhdmm6as4vk97vlZDdGnD9eVFh1nkWSv5M6p7X0bywQf_m4I_ILlUcf1RRXgCqdE8LS7KY4ub4FCEg_tfSKJXe4FlbQba2SCA2o5KsPZ_wuSvPUt7mKkaRTsGsdVBuVhtTzzDQZ9D_sM00dxoN2OtTUi6WT3eXV_3cFwAL9JDqmzsNSADT3wkr45EeOXE35ZKFhVzL7RMZ_Nf0FBYrJ3CnCR3l8OV0AtZNInc5KJRKN51a5IA5l3VyBK9D8makjMn25bB0OyWEKvsA6pPDoBkoYejlD3E8E="],"click":["https://adxapitest.yomob.com.cn/adx/adsense/tracking?expiration_time=1542796982&channelid=10006&userid=123123595948851200&version=1.0.0&appid=youyouhezi&publisherid=10006&metadata=%7B%22ip%22%3A%22211.145.63.25%22%2C%22system%22%3A%22iPhone%22%2C%22adversion%22%3A%221.0.0%22%2C%22adtype%22%3A%228%22%2C%22ip_server%22%3A%22211.145.63.25%22%2C%22udid%22%3A%22D081F4E0-708D-44DF-A6D1-C974BA36B671%22%2C%22adname%22%3A%2246%22%2C%22net%22%3A%22wifi%22%2C%22bundleid%22%3A%221021897571%22%2C%22video_url_md5%22%3A%2210007%22%2C%22language%22%3A%22zh%22%2C%22error%22%3A%22%22%2C%22bundle_id%22%3A%221021897571%22%2C%22order%22%3A%220%22%2C%22scene%22%3A%22default%22%2C%22platform%22%3A%2246%22%2C%22display%22%3A%221%22%7D&time=1542789782&platform=1&counterid=cp_adclick&rowkey=641c34651542789782085kzhr&signature=3b248a9cc18e14e95a66d29e9bd8ac45","https://adservingtest.yomob.com/adservingtracking?data=L9inmgelB4TMpT1hgXBukTyhMobjPWC1Bl3L-zvnVA0n1nyvBxBeI0rB9yWALgg5Xy8qvv-TVBAq4KU1G8IeLeUhfjRyhl4QbyZT5H22eoTYfz5oZ_krQ9w55ama-dNxLJ4sxqczsa-AQk5cDSvBfV895C_14KlZb4cIitybXtZ8w-LAzKrD9d_yJNmTYmcWuDq2ZHA-wp3YOfXZmB_sIze0pL_feZyE1HyXNcV0qGEvkXDOQBuHH6RB8cxNQHUrkkS4r_q3BEQcgetXjhnQtFBglr9E3iiakR2D-0OIh1nkmy7oaqiA2kC-Dqoc98tIU_y_A_9ZwV5fw2W5jxb4mz1LTeMoKdkpwlTYBVRXeVqHYPHZsjrGibcJODEGkbrlPaKh4ciJUsyHM9hbmGLnEU1fK331mt59LZW-aZQDSgZVLuDHY2rCyrANFhiwBAV5AieBILkvcSjlHGC_LEBIYGuq1kSfBE1Xkc6mtkqZVs4m4tYH2z9XtmMWW_3q1VjhA3l7mranyBx_QtgFs6KyVIMSBwypfjCvZcr1syMEOdg="]},"creatives":{"image":{"master":{"url":"https://resource.yomob.com/adServing/mmzb/320x50.png","width":320,"height":50}}}}]
     */
    private int status;//状态码，非0为错误
    private int ad_type;//广告类型：0-奖励视频；1-静态插屏；2-原生广告；3-Banner；status 为0时存在
    private int goal_type;//推广内容类型，0：app；1：web；2：miniprogram；status 为0时存在
    private int ad_count;//广告数量，暂时只支持1；status 为0时存在
    private List<AdsBean> ads;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAd_type() {
        return ad_type;
    }

    public void setAd_type(int ad_type) {
        this.ad_type = ad_type;
    }

    public int getGoal_type() {
        return goal_type;
    }

    public void setGoal_type(int goal_type) {
        this.goal_type = goal_type;
    }

    public int getAd_count() {
        return ad_count;
    }

    public void setAd_count(int ad_count) {
        this.ad_count = ad_count;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public static class AdsBean {
        /**
         * ad_id : 10007
         * title : 么么秀场
         * description : 萝莉御姐，各种姿势等你解锁
         * icon : https://resource.yomob.com/adServing/mmzb/512.png
         * action_type : 2
         * expiration_time : 1542796982
         * mini_program_id : gh_b61379f06d33
         * mini_program_path : /pages/home/home?data=%7B%22publisher_id%22%3A%2210001%22%2C%22sub_id%22%3A%22youyouhezi%22%2C%22ad_id%22%3A%2210007%22%2C%22ad_type%22%3A3%2C%22imei%22%3A%22868291022995894%22%2C%22anid%22%3A%22%22%2C%22idfa%22%3A%22D081F4E0-708D-44DF-A6D1-C974BA36B671%22%2C%22idfv%22%3A%22%22%2C%22is_mp%22%3A0%2C%22os%22%3A2%2C%22network%22%3A10001%2C%22version%22%3A%221.0%22%7D
         * tracking_events : {"impression":["https://adxapitest.yomob.com.cn/adx/adsense/tracking?expiration_time=1542796982&channelid=10006&userid=123123595948851200&version=1.0.0&appid=youyouhezi&publisherid=10006&metadata=%7B%22ip%22%3A%22211.145.63.25%22%2C%22system%22%3A%22iPhone%22%2C%22adversion%22%3A%221.0.0%22%2C%22adtype%22%3A%228%22%2C%22ip_server%22%3A%22211.145.63.25%22%2C%22udid%22%3A%22D081F4E0-708D-44DF-A6D1-C974BA36B671%22%2C%22adname%22%3A%2246%22%2C%22net%22%3A%22wifi%22%2C%22bundleid%22%3A%221021897571%22%2C%22video_url_md5%22%3A%2210007%22%2C%22language%22%3A%22zh%22%2C%22error%22%3A%22%22%2C%22bundle_id%22%3A%221021897571%22%2C%22order%22%3A%220%22%2C%22scene%22%3A%22default%22%2C%22platform%22%3A%2246%22%2C%22display%22%3A%221%22%7D&time=1542789782&platform=1&counterid=cp_adview&rowkey=f7dc20f01542789782086qg9z&signature=c7c5aead148b260498ee70ea8f1c146e","https://adservingtest.yomob.com/adservingtracking?data=8ErhVK5bCpX3BVFNfohTZceUluEyuJaX0wulk51SBnn9GkE-Wv8grzH1b1KEXzy_vnHBpNcbSL4jT1ylFpTPJEF14xIEVj3rDJmwRyKRbj5MSwoBpduElEHdV_Wk-SGgoHEMEp2zkhhdioXPgy6pPPoRPVyDp5xN-bkT_SJbatEz4DqdJfL1B1Lt8sFdfmU2MCOBknVe7VYOkQdAk8LgTH2XUE7uIFNbzX0MrizZG1O2rDvGA8rBl4rs7U77XFKSZ1gKKRpFrE2mPhdmm6as4vk97vlZDdGnD9eVFh1nkWSv5M6p7X0bywQf_m4I_ILlUcf1RRXgCqdE8LS7KY4ub4FCEg_tfSKJXe4FlbQba2SCA2o5KsPZ_wuSvPUt7mKkaRTsGsdVBuVhtTzzDQZ9D_sM00dxoN2OtTUi6WT3eXV_3cFwAL9JDqmzsNSADT3wkr45EeOXE35ZKFhVzL7RMZ_Nf0FBYrJ3CnCR3l8OV0AtZNInc5KJRKN51a5IA5l3VyBK9D8makjMn25bB0OyWEKvsA6pPDoBkoYejlD3E8E="],"click":["https://adxapitest.yomob.com.cn/adx/adsense/tracking?expiration_time=1542796982&channelid=10006&userid=123123595948851200&version=1.0.0&appid=youyouhezi&publisherid=10006&metadata=%7B%22ip%22%3A%22211.145.63.25%22%2C%22system%22%3A%22iPhone%22%2C%22adversion%22%3A%221.0.0%22%2C%22adtype%22%3A%228%22%2C%22ip_server%22%3A%22211.145.63.25%22%2C%22udid%22%3A%22D081F4E0-708D-44DF-A6D1-C974BA36B671%22%2C%22adname%22%3A%2246%22%2C%22net%22%3A%22wifi%22%2C%22bundleid%22%3A%221021897571%22%2C%22video_url_md5%22%3A%2210007%22%2C%22language%22%3A%22zh%22%2C%22error%22%3A%22%22%2C%22bundle_id%22%3A%221021897571%22%2C%22order%22%3A%220%22%2C%22scene%22%3A%22default%22%2C%22platform%22%3A%2246%22%2C%22display%22%3A%221%22%7D&time=1542789782&platform=1&counterid=cp_adclick&rowkey=641c34651542789782085kzhr&signature=3b248a9cc18e14e95a66d29e9bd8ac45","https://adservingtest.yomob.com/adservingtracking?data=L9inmgelB4TMpT1hgXBukTyhMobjPWC1Bl3L-zvnVA0n1nyvBxBeI0rB9yWALgg5Xy8qvv-TVBAq4KU1G8IeLeUhfjRyhl4QbyZT5H22eoTYfz5oZ_krQ9w55ama-dNxLJ4sxqczsa-AQk5cDSvBfV895C_14KlZb4cIitybXtZ8w-LAzKrD9d_yJNmTYmcWuDq2ZHA-wp3YOfXZmB_sIze0pL_feZyE1HyXNcV0qGEvkXDOQBuHH6RB8cxNQHUrkkS4r_q3BEQcgetXjhnQtFBglr9E3iiakR2D-0OIh1nkmy7oaqiA2kC-Dqoc98tIU_y_A_9ZwV5fw2W5jxb4mz1LTeMoKdkpwlTYBVRXeVqHYPHZsjrGibcJODEGkbrlPaKh4ciJUsyHM9hbmGLnEU1fK331mt59LZW-aZQDSgZVLuDHY2rCyrANFhiwBAV5AieBILkvcSjlHGC_LEBIYGuq1kSfBE1Xkc6mtkqZVs4m4tYH2z9XtmMWW_3q1VjhA3l7mranyBx_QtgFs6KyVIMSBwypfjCvZcr1syMEOdg="]}
         * creatives : {"image":{"master":{"url":"https://resource.yomob.com/adServing/mmzb/320x50.png","width":320,"height":50}}}
         */

        private String ad_id;//
        private String title;//
        private String description;//
        private String icon;//
        private int action_type;//点击物料后的互交类型 0:打开网页、1:点击下载、2：跳转微信
        private int expiration_time;//广告过期时间戳(单位秒)，过期上报后无效
        private String mini_program_id;//点击跳转的小程序原始ID，action_type为2时必传
        private String mini_program_path;//点击跳转的小程序路径，action_type为2时必传
//        private String click_through_url;//点击跳转地址，action_type不为2时必传
        private TrackingEventsBean tracking_events;//
        private CreativesBean creatives = new CreativesBean();//

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getAction_type() {
            return action_type;
        }

        public void setAction_type(int action_type) {
            this.action_type = action_type;
        }

        public int getExpiration_time() {
            return expiration_time;
        }

        public void setExpiration_time(int expiration_time) {
            this.expiration_time = expiration_time;
        }

        public String getMini_program_id() {
            return mini_program_id;
        }

        public void setMini_program_id(String mini_program_id) {
            this.mini_program_id = mini_program_id;
        }

        public String getMini_program_path() {
            return mini_program_path;
        }

        public void setMini_program_path(String mini_program_path) {
            this.mini_program_path = mini_program_path;
        }

        public TrackingEventsBean getTracking_events() {
            return tracking_events;
        }

        public void setTracking_events(TrackingEventsBean tracking_events) {
            this.tracking_events = tracking_events;
        }

        public CreativesBean getCreatives() {
            return creatives;
        }

        public void setCreatives(CreativesBean creatives) {
            this.creatives = creatives;
        }

        public static class TrackingEventsBean {
            private List<String> impression;//展示监控链接
            private List<String> click;//点击监控链接

            public List<String> getImpression() {
                return impression;
            }

            public void setImpression(List<String> impression) {
                this.impression = impression;
            }

            public List<String> getClick() {
                return click;
            }

            public void setClick(List<String> click) {
                this.click = click;
            }
        }

        public static class CreativesBean {
            /**
             * image : {"master":{"url":"https://resource.yomob.com/adServing/mmzb/320x50.png","width":320,"height":50}}
             */

            private ImageBean image = new ImageBean();

            public ImageBean getImage() {
                return image;
            }

            public void setImage(ImageBean image) {
                this.image = image;
            }

            public static class ImageBean {
                /**
                 * master : {"url":"https://resource.yomob.com/adServing/mmzb/320x50.png","width":320,"height":50}
                 */

                private MasterBean master = new MasterBean();

                public MasterBean getMaster() {
                    return master;
                }

                public void setMaster(MasterBean master) {
                    this.master = master;
                }

                public static class MasterBean {
                    /**
                     * url : https://resource.yomob.com/adServing/mmzb/320x50.png
                     * width : 320
                     * height : 50
                     */

                    private String url = "";
                    private int width;
                    private int height;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }
                }
            }
        }
    }
}
