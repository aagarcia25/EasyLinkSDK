-injars 'D:\TestWorks\EasyLink_WindowsTest\jar\Proguard\No\EasyLinkWinSDK_V1.02.00_20200720.jar'
-outjars 'D:\TestWorks\EasyLink_WindowsTest\jar\Proguard\Yes\EasyLinkWinSDK_V1.02.00_20200720_P.jar'

-libraryjars 'C:\DevKit\jdk8\jre\lib\accessibility.properties'
-libraryjars 'C:\DevKit\jdk8\jre\lib\amd64'
-libraryjars 'C:\DevKit\jdk8\jre\lib\applet'
-libraryjars 'C:\DevKit\jdk8\jre\lib\calendars.properties'
-libraryjars 'C:\DevKit\jdk8\jre\lib\charsets.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\classlist'
-libraryjars 'C:\DevKit\jdk8\jre\lib\cmm'
-libraryjars 'C:\DevKit\jdk8\jre\lib\content-types.properties'
-libraryjars 'C:\DevKit\jdk8\jre\lib\currency.data'
-libraryjars 'C:\DevKit\jdk8\jre\lib\deploy'
-libraryjars 'C:\DevKit\jdk8\jre\lib\deploy.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\ext'
-libraryjars 'C:\DevKit\jdk8\jre\lib\flavormap.properties'
-libraryjars 'C:\DevKit\jdk8\jre\lib\fontconfig.bfc'
-libraryjars 'C:\DevKit\jdk8\jre\lib\fontconfig.properties.src'
-libraryjars 'C:\DevKit\jdk8\jre\lib\fonts'
-libraryjars 'C:\DevKit\jdk8\jre\lib\gnu.io.rxtx.SerialPorts'
-libraryjars 'C:\DevKit\jdk8\jre\lib\hijrah-config-umalqura.properties'
-libraryjars 'C:\DevKit\jdk8\jre\lib\images'
-libraryjars 'C:\DevKit\jdk8\jre\lib\javafx.properties'
-libraryjars 'C:\DevKit\jdk8\jre\lib\javaws.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\jce.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\jfr'
-libraryjars 'C:\DevKit\jdk8\jre\lib\jfr.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\jfxswt.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\jsse.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\jvm.hprof.txt'
-libraryjars 'C:\DevKit\jdk8\jre\lib\logging.properties'
-libraryjars 'C:\DevKit\jdk8\jre\lib\management'
-libraryjars 'C:\DevKit\jdk8\jre\lib\management-agent.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\meta-index'
-libraryjars 'C:\DevKit\jdk8\jre\lib\net.properties'
-libraryjars 'C:\DevKit\jdk8\jre\lib\plugin.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\psfont.properties.ja'
-libraryjars 'C:\DevKit\jdk8\jre\lib\psfontj2d.properties'
-libraryjars 'C:\DevKit\jdk8\jre\lib\resources.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\rt.jar'
-libraryjars 'C:\DevKit\jdk8\jre\lib\security'
-libraryjars 'C:\DevKit\jdk8\jre\lib\sound.properties'
-libraryjars 'C:\DevKit\jdk8\jre\lib\tzdb.dat'
-libraryjars 'C:\DevKit\jdk8\jre\lib\tzmappings'
-libraryjars 'D:\TestWorks\EasyLink_WindowsTest\data\EasyLinkW_20190417\EasyLinkSDKw\lib\EcrProtocol_V1.01.00_20190326.jar'
-libraryjars 'D:\TestWorks\EasyLink_WindowsTest\data\EasyLinkW_20190417\EasyLinkSDKw\lib\fastjson-1.2.57.jar'
-libraryjars 'D:\TestWorks\EasyLink_WindowsTest\data\EasyLinkW_20190417\EasyLinkSDKw\lib\RXTXcomm.jar'

-dontskipnonpubliclibraryclassmembers
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-dontusemixedcaseclassnames
-keepattributes *Annotation*,InnerClasses,Signature,SourceFile,LineNumberTable
#-dontpreverify
-verbose
-dontwarn android.os.**
-ignorewarnings


# 保留我们自定义控件（继承自View）不被混�?
# -keep public class * extends android.view.View{
#    *** get*();
#    void set*(***);
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#   public <init>(android.content.Context, android.util.AttributeSet, int);
# }
# 保留Parcelable序列化类不被混淆
-keep class * extends android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混�?
-keepclassmembers class * extends java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent?**On*Listener的，不能被混�?
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# log的优化：将会关闭log输出，但sdk的log霿提供给客户?过弿关设置，承以暂时不在混淆中配置log的关�?
# -assumenosideeffects class android.util.Log {
# 
# public static boolean isLoggable(java.lang.String,int);
# 
# public static int v(...);
# 
# public static int i(...);
# 
# public static int w(...);
# 
# public static int d(...);
# 
# public static int e(...);
# 
# }
# webView处理，项目中没有使用到webView忽略即可
# -keepclassmembers class fqcn.of.javascript.interface.for.webview {
#    public *;
# }
# -keepclassmembers class * extends android.webkit.webViewClient {
#    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
#    public boolean *(android.webkit.WebView, java.lang.String);
# }
# -keepclassmembers class * extends android.webkit.webViewClient {
#   public void *(android.webkit.webView, jav.lang.String);
# }
# ############################################
# 
# 项目中特殊处理部�?
# 
# ############################################
# -----------处理反射�?---------------
# -----------处理js交互---------------
# -----------处理实体�?---------------
# 在开发的时忙我们可以将承有的实体类放在一个包内，这样我们写一次混淆就行了?
# -keep class com.paxsz.easylink.model.** { *;}
# -keep public class com.ljd.example.entity.** {
#    public void set*(***);
#    public *** get*();
#    public *** is*();
# }
-keep class com.paxsz.easylink.util.** {
    <fields>;
    <methods>;
}

# -----------项目特殊(sdk)类不参加混淆------------
# 禁止混淆的所有接口的入口类（具体�?:public类不混淆,private的混淆）
-keep class com.paxsz.easylink.api.EasyLinkSdkManager {
    !private <fields>;
    !private <methods>;
}

# 禁止混淆aidl文件(貌似并不霿要下面这丿�?)
# -keep class * implements android.os.IInterface {*;}
# 禁止混淆接口公共返回�?
-keep class com.paxsz.easylink.api.ResponseCode {
    <fields>;
    <methods>;
}

-keep class com.paxsz.easylink.device.** {
    !private <fields>;
    !private <methods>;
}

-keep class com.paxsz.easylink.model.** {
    !private <fields>;
    !private <methods>;
}

# 禁止混淆的目�?
-keep class com.paxsz.easylink.api.** {
    !private <fields>;
    !private <methods>;
}

# 不混淆fileDownloadListener SearchDeviceListener(这个其实可混�?)
-keep class com.paxsz.easylink.listener.** {
    !private <fields>;
    !private <methods>;
}

# -----------项目相关JAR�?---------
# 不要混淆GLBaiFuTong, GLComm, GLUtils  provided/CompileOnly 方式依赖的jar 不需要迌外做混淆处琿
-keep class com.pax.** {
    <fields>;
    <methods>;
}

-keep class pax.ecr.protocol.** {
    <fields>;
    <methods>;
}

-keep class gnu.io.** {
    <fields>;
    <methods>;
}

# 下面介绍常用第三方混淆配置（已按字母排序，不定期更新）：
# EventBus
# -keepattributes *Annotation*
# -keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
# }
# -keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Glide
# -keep public class * implements com.bumptech.glide.module.GlideModule
# -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
# }
# Gson
-keep class sun.misc.Unsafe {
    <fields>;
    <methods>;
}

-keep class com.google.gson.stream.** {
    <fields>;
    <methods>;
}

# ############################################
# 
# Android弿发中丿些需要保留的公共部分
# 
# ############################################
# 保留我们使用的四大组件，自定义的Application等等这些类不被混�?
# 因为这些子类都有可能被外部调�?
# -keep public class * extends android.app.Activity
# -keep public class * extends android.app.Appliction
# -keep public class * extends android.app.Service
# -keep public class * extends android.content.BroadcastReceiver
# -keep public class * extends android.content.ContentProvider
# -keep public class * extends android.app.backup.BackupAgentHelper
# -keep public class * extends android.preference.Preference
# -keep public class * extends android.view.View
# -keep public class com.android.vending.licensing.ILicensingService
# 保留support下的承有类及其内部籿
# -keep class android.support.** {*;}
# 保留继承�?
# -keep public class * extends android.support.v4.**
# -keep public class * extends android.support.v7.**
# -keep public class * extends android.support.annotation.**
# 保留R下面的资�?
# -keep class **.R$* {*;}
# 保留本地native方法不被混淆
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

# Also keep - Enumerations. Keep the special static methods that are required in
# enumeration classes.
-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
