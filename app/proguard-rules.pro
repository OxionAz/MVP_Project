# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Java\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
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

# retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# db-flow
-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }

# okio
-dontwarn okio.**

#
-keepattributes Signature
-keepattributes Exceptions
-dontwarn java.lang.invoke.*

# yandex metrica
-keep class com.yandex.metrica.impl.* { *; }
-dontwarn com.yandex.metrica.impl.*
-keep class com.yandex.metrica.* { *; }
-dontwarn com.yandex.metrica.*
