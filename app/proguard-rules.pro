# To enable ProGuard in your project, edit project.properties
# to define the proguard.config property as described in that file.
#
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in ${sdk.dir}/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep class **.R$* {*;}
-keep class packagename.** {*;}
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-dontwarn
-dontskipnonpubliclibraryclassmembers
-ignorewarnings

#-keepattributes *Annotation*
#-dontwarn android.support.**
-dontwarn android.support.v4.**
-dontwarn **CompatHoneycomb
-dontwarn **CompatHoneycombMR2
-dontwarn **CompatCreatorHoneycombMR2

-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

# 保持自定义控件类不被混淆
-keep public class * extends com.zhibaicc.android.adapter.PackageAdapter
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

-keep public class * implements java.io.Serializable {
    public *;
}

# mode 混淆
-keep class com.lgmshare.common,view.model.** { *; }

# FastJson 混淆
# 如果是有mode的get和set方法，需要设置下面这条语句
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature

# universal-image-loader 混淆
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *; }

# ShareSDK 混淆
-dontwarn cn.sharesdk.**
-keep class cn.sharesdk.** { *; }

# ixintui 混淆
-keep class com.ixintui.** {*;}
-keep public interface com.ixintui.** {*;}

# slidingmenu 的混淆
-dontwarn com.jeremyfeinstein.slidingmenu.lib.**
-keep class com.jeremyfeinstein.slidingmenu.lib.** { *; }

# ActionBarSherlock混淆
-dontwarn com.actionbarsherlock.**
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }
-keep class * extends java.lang.annotation.Annotation { *; }

# 百度地图混淆
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}

# umeng混淆
-keep class com.umeng.** {*; }