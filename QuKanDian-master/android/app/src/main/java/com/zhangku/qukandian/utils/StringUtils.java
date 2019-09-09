package com.zhangku.qukandian.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/2/9.
 */

public class StringUtils {
    public static String [] returnImageUrlsFromHtml(String html) {
        List<String> imageSrcList = new ArrayList<String>();
        String htmlCode = html;
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        if (imageSrcList == null || imageSrcList.size() == 0) {
            Log.e("imageSrcList","资讯中未匹配到图片链接");
            return null;
        }
        return imageSrcList.toArray(new String[imageSrcList.size()]);
    }

    /**
     * 连接方法 类似于javascript
     * @param join 连接字符串
     * @param strAry 需要连接的集合
     * @return
     */
    public static String join(String join, ArrayList<String> strAry){
        StringBuffer sb=new StringBuffer();
        for(int i=0,len =strAry.size();i<len;i++){
            if(i==(len-1)){
                sb.append(strAry.get(i));
            }else{
                sb.append(strAry.get(i)).append(join);
            }
        }
        return sb.toString();
    }

    /**
     * 将结果集中的一列用指定字符连接起来
     * @param join 指定字符
     * @param cols 结果集
     * @param colName 列名
     * @return
     */
//    public static String join(String join, List<Map> cols, String colName){
//        List<String> aColCons = new ArrayList<String>();
//        for (Map map:
//                cols) {
//            aColCons.add(ObjectUtils.toString(map.get(colName)));
//        }
//        return join(join,aColCons);
//    }

    public static String join(String join,List<String> listStr){
        StringBuffer sb=new StringBuffer();
        for(int i=0,len =listStr.size();i<len;i++){
            if(i==(len-1)){
                sb.append(listStr.get(i));
            }else{
                sb.append(listStr.get(i)).append(join);
            }
        }
        return sb.toString();
    }
}
