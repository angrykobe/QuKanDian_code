package com.zhangku.qukandian.utils;

import java.util.Random;
import java.util.zip.CRC32;

/**
 * Created by yuzuoning on 2017/11/20.
 */

public class VideoResolveJRTT {

    public String getUrl(String videoId) {
        int r1 = new Random().nextInt(90000000)+10000000;
        int r2 = new Random().nextInt(90000000)+10000000;
        String r = String.valueOf(r1) + String.valueOf(r2);

        String encodeUrl = "/video/urls/v/1/toutiao/mp4/" + videoId + "?" + "r=" + r;
        CRC32 crc32 = new CRC32();
        crc32.update(encodeUrl.getBytes());
        String s = crc32.getValue()+"";
        String url = "https://ib.365yg.com/video/urls/v/1/toutiao/mp4/" + videoId + "?r=" + r + "&s=" + s;
        return url;
    }
}
