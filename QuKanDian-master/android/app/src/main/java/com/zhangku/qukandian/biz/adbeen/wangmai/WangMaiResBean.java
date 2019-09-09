package com.zhangku.qukandian.biz.adbeen.wangmai;

import java.io.Serializable;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/12
 * 你不注释一下？
 */
public class WangMaiResBean {
    private int error_code;//响应状态码	0 =>成功,200000=>无广告返回
    private String request_id;//请求id，唯一标识一次请求
    private WxadBean wxad;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public WxadBean getWxad() {
        return wxad;
    }

    public void setWxad(WxadBean wxad) {
        this.wxad = wxad;
    }

    public static class WxadBean {
        private String ad_title;//广告推广标题，中文为UTF-8编码
        private String brand_name;//广告品牌名称，下载类则为app名称（如手机XXX），非下载类则为品牌名称（如 XXX）
        private String image_src;//广告图片地址 ，单个广告可能有多张图标返回。多张图片会使用分号（；）进行分隔。
        private int material_width;//图片的宽度；如果是图文或文本，则不会填充此字段
        private int material_height;//表示图片的高度；如果是图文或文本，则不会填充此字段
        private String icon_src;//广告图标地址，单个广告可能有多张图标返回。多张图片会使用分号（；）进行分隔。
        private int creative_type;//广告图标地址，单个广告可能有多张图标返回。多张图片会使用分号（；）进行分隔。
        private int interaction_type;//交互类型：1=打开网页； 2=点击下载；3=Android；4=App store；5=下载；
        private int app_size;//下载类广告 应用文件大小
        private String landing_page_url;//广告落地页地址，当广告被点击时，需引导用户跳转到此广告落地承载页URL。注：此参数为响应端可选参数，当此参数不存在时，用户点击广告后，只需回调click_url点击上报行为即可。
        private List<String> win_notice_url;//展示回调地址数组，当广告展示后需调用此地址上报展示行为，开发者需要在广告展示时向win_notice_url数组中的所有url发送HTTP请求。
        private List<String> click_url;//点击行为地址数组，当广告被点击时需调用此地址上报点击行为，开发者需要在广告展示时向click_url数组中的所有url发送HTTP请求。

        private List<String> download_track_urls;//下载行为上报地址集合，当通过广告开始下载时，需要向该集合中所有的url发送请求。注：该参数为可选参数
        private List<String> downloaded_track_urls;//下载完成上报地址集合，当通过广告下载完成时，需要向该集合中所有的url发送请求。注：该参数为可选参数
        private List<String> installed_track_urls;//安装完成上报地址集合，当通过广告下载后并安装完成时，需要向该集合中所有的url发送请求。注：该参数为可选参数
        private List<String> open_track_urls;//安装完成后打开app上报。注：该参数为可选参数
        private List<String> action_track_urls;//该设备上已经有app直接打开上报。注：该参数为可选参数

        private String deep_link;//support_deeplink=1时，该字段有可能有值，需要用该字段对deep_link进行操作，并且此时landing_page_url中的地址会变成app下载地址
        private VideoBean video;//当creative_type=6，该字段会有值
        private String source;//广告主来源
        private String adLogo;//广告主Logo

        private String app_package;//下载类广告包名；对于下载类广告，可以使用此字段判断当前设备是否已经安装此应用，如已安装，在用户对当前广告发生点击行为后，可以执行打开应用操作，并在后台通过click_url汇报此次点击，不再进行302跳转


        public List<String> getDownload_track_urls() {
            return download_track_urls;
        }

        public void setDownload_track_urls(List<String> download_track_urls) {
            this.download_track_urls = download_track_urls;
        }

        public List<String> getDownloaded_track_urls() {
            return downloaded_track_urls;
        }

        public void setDownloaded_track_urls(List<String> downloaded_track_urls) {
            this.downloaded_track_urls = downloaded_track_urls;
        }

        public List<String> getInstalled_track_urls() {
            return installed_track_urls;
        }

        public void setInstalled_track_urls(List<String> installed_track_urls) {
            this.installed_track_urls = installed_track_urls;
        }

        public List<String> getOpen_track_urls() {
            return open_track_urls;
        }

        public void setOpen_track_urls(List<String> open_track_urls) {
            this.open_track_urls = open_track_urls;
        }

        public List<String> getAction_track_urls() {
            return action_track_urls;
        }

        public void setAction_track_urls(List<String> action_track_urls) {
            this.action_track_urls = action_track_urls;
        }

        public String getDeep_link() {
            return deep_link;
        }

        public void setDeep_link(String deep_link) {
            this.deep_link = deep_link;
        }

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAdLogo() {
            return adLogo;
        }

        public void setAdLogo(String adLogo) {
            this.adLogo = adLogo;
        }

        public String getApp_package() {
            return app_package;
        }

        public void setApp_package(String app_package) {
            this.app_package = app_package;
        }

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getImage_src() {
            return image_src;
        }

        public void setImage_src(String image_src) {
            this.image_src = image_src;
        }

        public int getMaterial_width() {
            return material_width;
        }

        public void setMaterial_width(int material_width) {
            this.material_width = material_width;
        }

        public int getMaterial_height() {
            return material_height;
        }

        public void setMaterial_height(int material_height) {
            this.material_height = material_height;
        }

        public String getIcon_src() {
            return icon_src;
        }

        public void setIcon_src(String icon_src) {
            this.icon_src = icon_src;
        }

        public int getCreative_type() {
            return creative_type;
        }

        public void setCreative_type(int creative_type) {
            this.creative_type = creative_type;
        }

        public int getInteraction_type() {
            return interaction_type;
        }

        public void setInteraction_type(int interaction_type) {
            this.interaction_type = interaction_type;
        }

        public int getApp_size() {
            return app_size;
        }

        public void setApp_size(int app_size) {
            this.app_size = app_size;
        }

        public String getLanding_page_url() {
            return landing_page_url;
        }

        public void setLanding_page_url(String landing_page_url) {
            this.landing_page_url = landing_page_url;
        }

        public List<String> getWin_notice_url() {
            return win_notice_url;
        }

        public void setWin_notice_url(List<String> win_notice_url) {
            this.win_notice_url = win_notice_url;
        }

        public List<String> getClick_url() {
            return click_url;
        }

        public void setClick_url(List<String> click_url) {
            this.click_url = click_url;
        }

        public static class VideoBean{
            private int v_type;//视频物料类型：1.	普通类型。返回duration字段与v_url字段2.	VAST。（暂无此类物料，可以不考虑）
            private int duration;//视频时长，单位为秒
            private String v_url;//视频地址，建议在客户端下载后播放。
            private List<String> incentiveCallbackTrackers;//激励视频观看回调
            private TranckingBean v_tracking;//视频物料类型为1（普通类型）时有可能返回。
            private ExtBean ext;//关于视频类物料的扩展信息都在这里

            public int getV_type() {
                return v_type;
            }

            public void setV_type(int v_type) {
                this.v_type = v_type;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getV_url() {
                return v_url;
            }

            public void setV_url(String v_url) {
                this.v_url = v_url;
            }

            public List<String> getIncentiveCallbackTrackers() {
                return incentiveCallbackTrackers;
            }

            public void setIncentiveCallbackTrackers(List<String> incentiveCallbackTrackers) {
                this.incentiveCallbackTrackers = incentiveCallbackTrackers;
            }

            public TranckingBean getV_tracking() {
                return v_tracking;
            }

            public void setV_tracking(TranckingBean v_tracking) {
                this.v_tracking = v_tracking;
            }

            public ExtBean getExt() {
                return ext;
            }

            public void setExt(ExtBean ext) {
                this.ext = ext;
            }

            public static class TranckingBean{
                private List<String> v_mute_tracking_event;//广告素材为视频时，静音监控链接
                private List<String> v_unmute_tracking_event;//广告素材为视频时，取消静音监控链接
                private List<String> v_pause_tracking_event;//广告素材为视频时，视频暂停监控链接
                private List<String> v_resume_tracking_event;//广告素材为视频时，视频取消暂停监控链接
                private List<String> v_fullscreen_tracking_event;//广告素材为视频时，视频全屏监控链接
                private List<String> v_exitfullscreen_tracking_event;//广告素材为视频时，视频退出全屏监控链接
                private List<String> v_skip_tracking_event;//广告素材为视频时，跳过视频播放监控链接
                private List<ProgressTrackingEventBean> v_progress_tracking_event;//广告素材为视频时，对视频播放阶段的上报

                public List<String> getV_mute_tracking_event() {
                    return v_mute_tracking_event;
                }

                public void setV_mute_tracking_event(List<String> v_mute_tracking_event) {
                    this.v_mute_tracking_event = v_mute_tracking_event;
                }

                public List<String> getV_unmute_tracking_event() {
                    return v_unmute_tracking_event;
                }

                public void setV_unmute_tracking_event(List<String> v_unmute_tracking_event) {
                    this.v_unmute_tracking_event = v_unmute_tracking_event;
                }

                public List<String> getV_pause_tracking_event() {
                    return v_pause_tracking_event;
                }

                public void setV_pause_tracking_event(List<String> v_pause_tracking_event) {
                    this.v_pause_tracking_event = v_pause_tracking_event;
                }

                public List<String> getV_resume_tracking_event() {
                    return v_resume_tracking_event;
                }

                public void setV_resume_tracking_event(List<String> v_resume_tracking_event) {
                    this.v_resume_tracking_event = v_resume_tracking_event;
                }

                public List<String> getV_fullscreen_tracking_event() {
                    return v_fullscreen_tracking_event;
                }

                public void setV_fullscreen_tracking_event(List<String> v_fullscreen_tracking_event) {
                    this.v_fullscreen_tracking_event = v_fullscreen_tracking_event;
                }

                public List<String> getV_exitfullscreen_tracking_event() {
                    return v_exitfullscreen_tracking_event;
                }

                public void setV_exitfullscreen_tracking_event(List<String> v_exitfullscreen_tracking_event) {
                    this.v_exitfullscreen_tracking_event = v_exitfullscreen_tracking_event;
                }

                public List<String> getV_skip_tracking_event() {
                    return v_skip_tracking_event;
                }

                public void setV_skip_tracking_event(List<String> v_skip_tracking_event) {
                    this.v_skip_tracking_event = v_skip_tracking_event;
                }

                public List<ProgressTrackingEventBean> getV_progress_tracking_event() {
                    return v_progress_tracking_event;
                }

                public void setV_progress_tracking_event(List<ProgressTrackingEventBean> v_progress_tracking_event) {
                    this.v_progress_tracking_event = v_progress_tracking_event;
                }

                public static class ProgressTrackingEventBean{
                    private int millisec;//播放当前指定毫秒后上报（如果为0就是立刻上报，如果为15就是播放15毫秒后上报，该值最大为视频的播放时间）
                    private List<String> url;//上报url，集合类型

                    public int getMillisec() {
                        return millisec;
                    }

                    public void setMillisec(int millisec) {
                        this.millisec = millisec;
                    }

                    public List<String> getUrl() {
                        return url;
                    }

                    public void setUrl(List<String> url) {
                        this.url = url;
                    }
                }
            }

            public static class ExtBean implements Serializable {
                private String preimgurl;//视频前贴片内容，一般为贴 片图片的url，但是也有可能 是html代码
                private String endimgurl;//视频后贴片内容，一般为贴 片图片的url，但是也有可能 是html代码
                private String endiconurl;//视频播放完成，展示该图标
                private String enddesc;//视频播放完成，展示该描述
                private String endtitle;//视频播放完成，展示该标题
                private String endcomments;//视频播放完成，展示的评论数
                private String endbutton;//视频播发完成，展示的按钮
                private int endrating;//视频播放完成，展示的评分
                private String endbuttonurl;//视频播放完成

                public int getEndrating() {
                    return endrating;
                }

                public void setEndrating(int endrating) {
                    this.endrating = endrating;
                }

                public String getPreimgurl() {
                    return preimgurl;
                }

                public void setPreimgurl(String preimgurl) {
                    this.preimgurl = preimgurl;
                }

                public String getEndimgurl() {
                    return endimgurl;
                }

                public void setEndimgurl(String endimgurl) {
                    this.endimgurl = endimgurl;
                }

                public String getEndiconurl() {
                    return endiconurl;
                }

                public void setEndiconurl(String endiconurl) {
                    this.endiconurl = endiconurl;
                }

                public String getEnddesc() {
                    return enddesc;
                }

                public void setEnddesc(String enddesc) {
                    this.enddesc = enddesc;
                }

                public String getEndtitle() {
                    return endtitle;
                }

                public void setEndtitle(String endtitle) {
                    this.endtitle = endtitle;
                }

                public String getEndcomments() {
                    return endcomments;
                }

                public void setEndcomments(String endcomments) {
                    this.endcomments = endcomments;
                }

                public String getEndbutton() {
                    return endbutton;
                }

                public void setEndbutton(String endbutton) {
                    this.endbutton = endbutton;
                }

                public String getEndbuttonurl() {
                    return endbuttonurl;
                }

                public void setEndbuttonurl(String endbuttonurl) {
                    this.endbuttonurl = endbuttonurl;
                }

                //                private String preimgurl;//视频前贴片内容，一般为贴片图片的url，但是也有可能是html代码
//                private String endimgurl;//视频后贴片内容，一般为贴片图片的url，但是也有可能是html代码
//
//                public String getPreimgurl() {
//                    return preimgurl;
//                }
//
//                public void setPreimgurl(String preimgurl) {
//                    this.preimgurl = preimgurl;
//                }
//
//                public String getEndimgurl() {
//                    return endimgurl;
//                }
//
//                public void setEndimgurl(String endimgurl) {
//                    this.endimgurl = endimgurl;
//                }
            }

        }




    }
}
