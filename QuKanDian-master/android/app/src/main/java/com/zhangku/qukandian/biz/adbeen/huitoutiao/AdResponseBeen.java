package com.zhangku.qukandian.biz.adbeen.huitoutiao;

import java.util.List;

public class AdResponseBeen {

    private int statusCode; // 状态码
    private String msg; // 错误原因
    private String requestId; // 相应的请求ID
    private String positionId; // 广告位ID
    private List<AdInfoVO> ads; // 广告信息，详情见下文

    public int getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getPositionId() {
        return positionId;
    }

    public List<AdInfoVO> getAds() {
        return ads;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public void setAds(List<AdInfoVO> ads) {
        this.ads = ads;
    }
}


/*

广告响应样例
{
  "ads": [
    {
      "adCreative": {
        "imageUrls": "http://hsp-adx.oss-cn-shanghai.aliyuncs.com/apollo/pictu re/20170630/fc2d340586224f27bbe6ec9196ed7213/file",
        "materialHeight": 0,
        "materialWidth": 0,
        "title": "现在注册，送现金红包",
        "videoDuration": 0
      },
      "clickAction": 2,
      "clickUrl": "http://www.hzjcb.com/pr/791890114",
      "eventTracking": {
        "clc": [
          "http://api.adxhi.com/frontend/tracking/click?id=eyJhY3Rpb25UaW1lI joxNDk5NjU4OTk4MTU1LCJhY3Rpb25UeXBlIjoyLCJhY3R1YWxQcmljZSI6MCwiYWRLZXkiOiIxMzA3NjI xNzE3XzE0MDAyMjY3MjBfMTUwMTkzODE1N18xNDk5NjU4OTMxOTg1MDEwMDEwXzAxZDI4ZDExNTVkN2U5Z DQ4ZjI1N2EzMWJlZTEzZWE1IiwiY2hhbm5lbElkIjoiMjEwOTEyNDEwNyIsImRlbWFuZGVySWQiOiIxMDA wMSIsImRldmljZUlkIjoiODYyNDg0MDM1NTkzNDIxIiwiZGV2aWNlVHlwZSI6MCwiaW1laSI6Ijg2MjQ4N DAzNTU5MzQyMSIsImlwIjoiMjExLjk1LjU2LjEwIiwib3MiOiJBbmRyb2lkIiwib3NWZXJzaW9uIjoiNi4 wIiwicG9zaXRpb25JZCI6IjIyMDY2Njg5NzEiLCJwcmljZSI6MC4xLCJyZXF1ZXN0SWQiOiI2MDJhYmI1O GIwZDI0NTQ5YmYzNGQ1NGYzYmQ0NWEyYyIsInN0YXRlIjowLCJ0cmFja2luZ0lkIjoiOWY2OWIxM2ZiMGE zYmJlZjQ0YmM4MGNkYWUxMzQyZTUiLCJ0cmFkZVR5cGUiOjJ9"
        ],
        "exp": [
          "http://api.adxhi.com/frontend/tracking/exposure?id=eyJhY3Rpb25UaW 1lIjoxNDk5NjU4OTk4MTU1LCJhY3Rpb25UeXBlIjoyLCJhY3R1YWxQcmljZSI6MCwiYWRLZXkiOiIxMzA3 NjIxNzE3XzE0MDAyMjY3MjBfMTUwMTkzODE1N18xNDk5NjU4OTMxOTg1MDEwMDEwXzAxZDI4ZDExNTVkN2 U5ZDQ4ZjI1N2EzMWJlZTEzZWE1IiwiY2hhbm5lbElkIjoiMjEwOTEyNDEwNyIsImRlbWFuZGVySWQiOiIx MDAwMSIsImRldmljZUlkIjoiODYyNDg0MDM1NTkzNDIxIiwiZGV2aWNlVHlwZSI6MCwiaW1laSI6Ijg2Mj Q4NDAzNTU5MzQyMSIsImlwIjoiMjExLjk1LjU2LjEwIiwib3MiOiJBbmRyb2lkIiwib3NWZXJzaW9uIjoi Ni4wIiwicG9zaXRpb25JZCI6IjIyMDY2Njg5NzEiLCJwcmljZSI6MC4xLCJyZXF1ZXN0SWQiOiI2MDJhYm I1OGIwZDI0NTQ5YmYzNGQ1NGYzYmQ0NWEyYyIsInN0YXRlIjowLCJ0cmFja2luZ0lkIjoiOWY2OWIxM2Zi MGEzYmJlZjQ0YmM4MGNkYWUxMzQyZTUiLCJ0cmFkZVR5cGUiOjJ9"
        ]
      },
      "expirationTime": 1800,
      "price": 0.1,
      "trackingId": "9f69b13fb0a3bbef44bc80cdae1342e5"
    },
    {
      "adCreative": {
        "imageUrls": "http://hsp-adx.oss-cn-shanghai.aliyuncs.com/apollo/pictu re/20170707/e5e63381e52c4379896a8c0584ffe0f3/file",
        "materialHeight": 0,
        "materialWidth": 0,
        "title": "投资理财，我选巨财网，知名央企控股；资金有保障",
        "videoDuration": 0
      },
      "clickAction": 2,
      "clickUrl": "http://m.jucaiwoo.com/wap/event/tomRegister/5?channelId=40005 ",
      "eventTracking": {
        "clc": [
          "http://api.adxhi.com/frontend/tracking/click?id=eyJhY3Rpb25UaW1lI joxNDk5NjU4OTk4MTU1LCJhY3Rpb25UeXBlIjoyLCJhY3R1YWxQcmljZSI6MCwiYWRLZXkiOiIxMzA1NzU 4NzY4XzE0MDExNDkxOTZfMTUwNTg4MjQwM18xNDk5NjU4OTMxOTc4MDEwMDEwXzg1MTY5MGE1YTlkYmYzZ GMzMDlhZmM4ZDE2MjViMGMyIiwiY2hhbm5lbElkIjoiMjEwOTEyNDEwNyIsImRlbWFuZGVySWQiOiIxMDA wMSIsImRldmljZUlkIjoiODYyNDg0MDM1NTkzNDIxIiwiZGV2aWNlVHlwZSI6MCwiaW1laSI6Ijg2MjQ4N DAzNTU5MzQyMSIsImlwIjoiMjExLjk1LjU2LjEwIiwib3MiOiJBbmRyb2lkIiwib3NWZXJzaW9uIjoiNi4 wIiwicG9zaXRpb25JZCI6IjIyMDY2Njg5NzEiLCJwcmljZSI6MC4xLCJyZXF1ZXN0SWQiOiI2MDJhYmI1O GIwZDI0NTQ5YmYzNGQ1NGYzYmQ0NWEyYyIsInN0YXRlIjowLCJ0cmFja2luZ0lkIjoiMTBhMGM0NTQzOWI 4MDE2ZTRmYzcxMDBkZmE3NjUxMDMiLCJ0cmFkZVR5cGUiOjJ9"
        ],
        "exp": [
          "http://api.adxhi.com/frontend/tracking/exposure?id=eyJhY3Rpb25UaW 1lIjoxNDk5NjU4OTk4MTU1LCJhY3Rpb25UeXBlIjoyLCJhY3R1YWxQcmljZSI6MCwiYWRLZXkiOiIxMzA1 NzU4NzY4XzE0MDExNDkxOTZfMTUwNTg4MjQwM18xNDk5NjU4OTMxOTc4MDEwMDEwXzg1MTY5MGE1YTlkYm YzZGMzMDlhZmM4ZDE2MjViMGMyIiwiY2hhbm5lbElkIjoiMjEwOTEyNDEwNyIsImRlbWFuZGVySWQiOiIx MDAwMSIsImRldmljZUlkIjoiODYyNDg0MDM1NTkzNDIxIiwiZGV2aWNlVHlwZSI6MCwiaW1laSI6Ijg2Mj Q4NDAzNTU5MzQyMSIsImlwIjoiMjExLjk1LjU2LjEwIiwib3MiOiJBbmRyb2lkIiwib3NWZXJzaW9uIjoi Ni4wIiwicG9zaXRpb25JZCI6IjIyMDY2Njg5NzEiLCJwcmljZSI6MC4xLCJyZXF1ZXN0SWQiOiI2MDJhYm I1OGIwZDI0NTQ5YmYzNGQ1NGYzYmQ0NWEyYyIsInN0YXRlIjowLCJ0cmFja2luZ0lkIjoiMTBhMGM0NTQz OWI4MDE2ZTRmYzcxMDBkZmE3NjUxMDMiLCJ0cmFkZVR5cGUiOjJ9"
        ]
      },
      "expirationTime": 1800,
      "price": 0.1,
      "trackingId": "10a0c45439b8016e4fc7100dfa765103",
      "deeplink": "openapp.jdmobile://virtual?params=%7B%22des%22%3A%22getCoupon %22%2C%22m%5Fparam%22%3A%7B%22jdv%22%3A%22238571484%7Cxunfei%7Ct%5F301476161%5F207 944%5F1712%7Ctuiguang%7C%5F2%5Fapp%5F0%5F1f3de5d787da4ca38cc9c5bf5bc577f3%2Dp%5F17 12%22%7D%2C%22url%22%3A%22http%3A%5C%2F%5C%2Fccc%2Ex%2Ejd%2Ecom%5C%2Fdsp%5C%2Fnc%3 Fext%3DY2xpY2sudW5pb24uamQuY29tL0pkQ2xpY2svP3VuaW9uSWQ9MzAxNDc2MTYxJnNpdGVpZD0yMDc 5NDRfMTcxMiZ1dG10ZXJtX2V4dD1wXzE3MTImdG89aHR0cDovL3JlLm0uamQuY29tL2xpc3QvaXRlbS8xM Tk3Ny0xNzk2MDQ4Lmh0bWw%5FcmVfZGNwPTQ1bFJUYWdfWDRrM2hQMFBDc1dBS1BLemtwZUVPdWk1aV8wN ldXSGpXS3NmVDNaU3JldjZObmthd3ZMQkRtakpyM2l2dHpjNGJhUmZhYloxeHpOUHBCdWpsQUMyVWQwU1J rV0tRd1F5TXZGNF81OTZ4SVhETnFDbGRtSEJCVWJpLUxMWlBDT2Y4d3pX%26log%3DpcPUpdkdJVkdIsvC YwuMNRXHvw0lqr5e%5FZiwaFWnRpLnT4BgD%2DUP3WvS2uf8gx1JzQ4XCU8BQS01n0Qn%5FRiv0tuc5OVG bUp46X8ZIgxbqulKXT0k%5FtCQdwaOj53B8%5Fb5obfGslUzV5UGEmgdelNjqUvTRfVt%2DZiPgDKE7f8a 9RgYgUyjxXqxIHdj6Ldcx9CuFDCQ62CXJaihyZcNMks2BVX1FKcUHkWWlBNkpNtAEscgF6sv3TK5hlULO9 82IEb1ZZzdGNdTSajQ%2DFCJ%5FVPfjuIMJ80iepp%2DyAyOaErGblPS4lyrMnwoOYvajrjBMubWKDg0gu kaQBlDFqHn%2Dk5zn2MWqIV41Xv31tp51gZb%2DrTKZjOr4GoS56jNmv6Hq10R9GH2%5FjX3w0wZV1r4qA MiwbC7vzcdo%2Dbdcusoyvmp2dTwVLMSbfKCoCwYiwpFGVZEfvEIkZOGk8RSV0V5Pu4%2DHIF5f1itKb7Q qYcrUD%2DvoEo%26v%3D404%26SE%3D1%22%2C%22SE%22%3A%22ADC%5FY6SIXa18K2tkEf3rTu3WtF6I GQzLuCviuZPJDbwzQnzR6WO8Lj30BRIA6fPhZOp2qTAjzNPR35nB4C3%5C%2FdNo4VMlqzdJzXzx19JJqJ Rbl8Sx4TYVqJcHZQoiVs2IGl4Suap4kuQYxcWkAypugEMAovQ%3D%3D%22%2C%22category%22%3A%22j ump%22%2C%22action%22%3A%22to%22%2C%22sourceType%22%3A%22adx%22%2C%22sourceValue%2 2%3A%22xunfei%5F52%22%7D"
    }
  ],
  "statusCode": 200
}

 */