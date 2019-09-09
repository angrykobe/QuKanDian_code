# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/yuzuoning/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-ignorewarnings
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class * extends android.view.SurfaceView
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}
-keep class org.litepal.model.**{*;}
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,Annotation,EnclosingMethod
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep class com.db.ta.sdk.** { *; }
#TONGDUN
-dontwarn android.os.**
-dontwarn com.android.internal.**
-keep class cn.tongdun.android.**{*;}
 -dontwarn com.xianwan.sdklibrary.**
      -keep class com.xianwan.sdklibrary.*.** { *; }
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}


-keep class com.zhangku.qukandian.bean.** { *; }
-keep class com.zhangku.qukandian.base.** { *; }
-keep class com.zhangku.qukandian.BaseNew.** { *; }
# 不混淆api广告代码
-keep class com.zhangku.qukandian.biz.adbeen.**{*;}
-keep class com.tencent.** { *; }
-keep class com.wwangliw.** { *; }
-keep class com.wwangliw.wxshare.share.** { *; }

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep class com.androidquery.callback.** {*;}

-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

-keep class com.huawei.hms.**{*;}

-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes SourceFile,LineNumberTable

-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-keep public class com.umeng.socialize.* {*;}


    -keep class com.umeng.scrshot.**
    -keep public class com.tencent.** {*;}
    -keep class com.umeng.socialize.sensor.**
    -keep class com.umeng.socialize.handler.**
    -keep class com.umeng.socialize.handler.*
    -keep class com.umeng.weixin.handler.**
    -keep class com.umeng.weixin.handler.*
    -keep class com.umeng.qq.handler.**
    -keep class com.umeng.qq.handler.*
    -keep class UMMoreHandler{*;}
    -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
    -keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
    -keep class com.tencent.mm.sdk.** {
     *;
    }
    -keep class com.tencent.mm.opensdk.** {
   *;
    }
    -dontwarn twitter4j.**
    -keep class twitter4j.** { *; }

    -keep class com.tencent.** {*;}
    -dontwarn com.tencent.**
    -keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
    }
    -keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
        }


    -keep class com.tencent.open.TDialog$*
    -keep class com.tencent.open.TDialog$* {*;}
    -keep class com.tencent.open.PKDialog
    -keep class com.tencent.open.PKDialog {*;}
    -keep class com.tencent.open.PKDialog$*
    -keep class com.tencent.open.PKDialog$* {*;}

    -keep class com.sina.** {*;}
    -dontwarn com.sina.**
    -keep class  com.alipay.share.sdk.** {
       *;
    }

    -keep class com.linkedin.** { *; }
    -keepattributes Signature

    -keepclassmembers class * {
       public <init> (org.json.JSONObject);
    }
    -keep public class [com.zhangku.qukandian].R$*{
    public static final int *;
    }


-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

-dontwarn com.baidu.ssp.mobile.**
-keep public class com.baidu.ssp.mobile.** {*;}
-keep class com.baidu.mobads.*.** { *; }
-keep class com.jd.jdadsdk.*.** { *; }

-keepclassmembers class com.zhangku.qukandian.javascript.MJavascriptInterface{
 public *;
}
-keepclassmembers class com.zhangku.qukandian.javascript.DialogShareInterface{
 public *;
}
-keep class com.qq.e.** {
        public protected *;
    }
    -keep class android.support.v4.app.NotificationCompat**{
        public *;
     }
    #bd sdk
    -keep class com.baidu.** {
        public protected *;
}

-keep class com.coda.sdk.**{
  public protected *;
}
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class okio.**{*;}
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-dontwarn java.nio.file.*
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Image Loader
-keep class com.squareup.picasso.Picasso
-keep class com.android.volley.toolbox.Volley
-keep class com.bumptech.glide.Glide
-keep class com.nostra13.universalimageloader.core.ImageLoader
-keep class com.facebook.drawee.backends.pipeline.Fresco

-keepattributes *JavascriptInterface*
-dontwarn com.kyview.**
-dontwarn com.kuaiyou
-keep public class com.kyview.** {*;}
-keep public class com.kuaiyou.** {*;}
-keep class com.qq.e.** {
   public protected *;
   }
-keep class com.tencent.gdt.**{
   public protected *;
   }

-keep class com.ak.android.** {*;}
 -renamesourcefileattribute SourceFile
    -keepattributes SourceFile,LineNumberTable

    # 防止内部类被混淆，无法访问
    -keepattributes Exceptions,InnerClasses,Signature,Deprecated,*Annotation*,EnclosingMethod
 -keep enum org.greenrobot.eventbus.ThreadMode { *; }

    # Only required if you use AsyncExecutor
    -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
        <init>(java.lang.Throwable);
    }

# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#基线包使用，生成mapping.txt
#生成的mapping.txt在app/buidl/outputs/mapping/release路径下，移动到/app路径下（实际不生效，路径还是不变）
-printmapping mapping.txt

#修复后的项目使用，保证混淆结果一致
#-applymapping mapping.txt
#hotfix
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
#防止inline
-dontoptimize
-keepattributes Signature
-keep class ikidou.reflect.exception.**{*;}
-keep class ikidou.reflect.typeimpl.**{*;}

-keep class pub.devrel.easypermissions.** { *; }
 #阅盟
-dontwarn com.iBookStar.**
-keep class com.iBookStar.**{*;}
# Adhub  混淆
-dontwarn com.hubcloud.adhubsdk.**

-dontwarn android.app.**
-dontwarn android.support.**


-keepattributes Signature
-keepattributes *Annotation*

-keep class com.google.protobuf.**{*;}
-keep class sun.misc.**{*;}
-keep class adhub.engine.**{*;}
-keep class android.support.** { *; }
-keep class android.app.**{*;}
-keep class **.R$* {*;}

#LY
-keep class com.ly.adpoymer.**{ *; }
-keep class com.qq.e.** {
public protected *;
}
-dontwarn org.apache.**
-keep class android.support.v4.app.NotificationCompat**{
public *;
}
-keep class com.baidu.mobads.** { *; }
-keep class com.baidu.mobad.** { *; }
-dontwarn com.androidquery.**
-keep class com.androidquery.** { *;}
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep class com.androidquery.callback.** {*;}
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
#极光广告混淆
 -dontwarn cn.jpush.**
 -keep class cn.jpush.** { *; }
 -dontwarn cn.jiguang.**
 -keep class cn.jiguang.** { *; }
 #今日头条
 -keep class com.bytedance.sdk.openadsdk.** { *; }
 -keep class com.androidquery.callback.** {*;}
 -keep class com.bytedance.sdk.openadsdk.service.TTDownloadProvider

 -keep class com.hmob.hmsdk.{ *;}
 -keep class android.support.v4.** { *;}
 -keep public class pl.droidsonroids.gif.GifIOException{<init>(int);}
 -keep class pl.droidsonroids.gif.GifInfoHandle{<init>(long,int,int,int);}
 -keep class functions.task.** {*;}
 #云告聚合搜索
 -keep class com.sdk.searchsdk.** {*;}
 #openinstall
 -dontwarn com.fm.openinstall.**
 -keep public class com.fm.openinstall.* {*; }
 -keep public interface com.fm.openinstall.* {*; }

 #好兔视频
 -keep class com.yilan.sdk.**{
     *;
 }
 -dontwarn javax.annotation.**
 -dontwarn sun.misc.Unsafe
 -dontwarn org.conscrypt.*
 -dontwarn okio.**

 ###阿里云混淆
 -keep class com.alibaba.sdk.android.**{*;}
 -keep class com.ut.**{*;}
 -keep class com.ta.**{*;}
 ######风灵广告相关
#------------------- baidu ad SDK ---------------------------
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.baidu.mobads.*.** { *; }

#------------------- baidu AD SDK end ---------------------------

#------------------- TouTiao AD SDK ---------------------------
-dontoptimize
-ignorewarnings

#保护内部类
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-keep class com.bytedance.sdk.openadsdk.** {*;}
-keep class com.androidquery.callback.** {*;}
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}

#------------------- TouTiao AD SDK end ---------------------------

#------------------- GDT AD SDK ---------------------------

# 嵌入广点通sdk时必须添加
-keep class com.qq.e.** {
    public protected *;
}

# 嵌入广点通sdk时必须添加
-keep class android.support.v4.**{ *;}
#------------------- GDT AD SDK end---------------------------

#------------------- Felink AD SDK  ---------------------------

-keep class com.felink.adSdk.** {*;}
-keep class com.felink.felinksdk.receiver.** {*;}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.request.target.SimpleTarget {*;}
-dontwarn com.yanzhenjie.permission.**
-keep class * implements com.bumptech.glide.module.GlideModule {*;}
-keep class com.bumptech.glide.request.target.** {*;}
#------------------- Felink AD SDK end---------------------------
#多游游戏混淆
-dontoptimize
-dontpreverify
-dontwarn com.duoyou.ad.**
-keep class com.duoyou.ad.*.** { *; }
-keep class **.R$* {
 *;
}
-keepclassmembers class **.R$* {
public static <fields>;
}
-ignorewarnings