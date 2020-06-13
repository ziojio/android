// package com.zhuj.android.android;
//
// import android.app.Activity;
// import android.app.ActivityManager;
// import android.content.ComponentName;
// import android.content.Context;
// import android.content.Intent;
// import android.content.pm.ApplicationInfo;
// import android.content.pm.PackageInfo;
// import android.content.pm.PackageManager;
// import android.content.pm.ResolveInfo;
// import android.content.pm.Signature;
// import android.graphics.drawable.Drawable;
// import android.os.Bundle;
// import android.text.TextUtils;
//
// import androidx.annotation.NonNull;
// import androidx.annotation.Nullable;
// import androidx.annotation.RequiresPermission;
//
// import com.zhuj.code.common.FileUtils;
//
// import java.io.File;
// import java.util.ArrayList;
// import java.util.List;
//
// import static android.Manifest.permission.REQUEST_INSTALL_PACKAGES;
//
//
// public final class AppUtils {
//
//     private AppUtils() {
//         throw new UnsupportedOperationException("u can't instantiate me...");
//     }
//
//     /**
//      * 判断 App 是否安装
//      *
//      * @param action   action
//      * @param category category
//      * @return {@code true}: 已安装<br>{@code false}: 未安装
//      */
//     public static boolean isInstallApp(Context context, final String action, final String category) {
//         Intent intent = new Intent(action);
//         intent.addCategory(category);
//         PackageManager pm = context.getPackageManager();
//         ResolveInfo info = pm.resolveActivity(intent, 0);
//         return info != null;
//     }
//
//     /**
//      * 判断 App 是否安装
//      *
//      * @param packageName 包名
//      * @return {@code true}: 已安装<br>{@code false}: 未安装
//      */
//     public static boolean isInstallApp(final String packageName) {
//         return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
//     }
//
//     /**
//      * 安装 App(支持 8.0)
//      * <p>8.0 需添加权限
//      * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
//      *
//      * @param filePath  文件路径
//      * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
//      *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
//      */
//     @RequiresPermission(REQUEST_INSTALL_PACKAGES)
//     public static void installApp(final String filePath, final String authority) {
//         installApp( filePath , authority);
//     }
//
//     /**
//      * 安装 App（支持 8.0）
//      * <p>8.0 需添加权限
//      * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
//      *
//      * @param file      文件
//      * @param authority 7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
//      *                  <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
//      */
//     @RequiresPermission(REQUEST_INSTALL_PACKAGES)
//     public static void installApp(Context context, File file, final String authority) {
//         if (FileUtils.isFileExists(file)) {
//             return;
//         }
//        context.startActivity(IntentUtils.getInstallAppIntent(file, authority, true));
//     }
//
//     /**
//      * 安装 App（支持 8.0）
//      * <p>8.0 需添加权限
//      * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
//      *
//      * @param activity    activity
//      * @param filePath    文件路径
//      * @param authority   7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
//      *                    <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
//      * @param requestCode 请求值
//      */
//     @RequiresPermission(REQUEST_INSTALL_PACKAGES)
//     public static void installApp(final Activity activity,
//                                   final String filePath,
//                                   final String authority,
//                                   final int requestCode) {
//         installApp(activity, FileUtils.getFileByPath(filePath), authority, requestCode);
//     }
//
//     /**
//      * 安装 App（支持 8.0）
//      * <p>8.0 需添加权限
//      * {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}</p>
//      *
//      * @param activity    activity
//      * @param file        文件
//      * @param authority   7.0 及以上安装需要传入清单文件中的{@code <provider>}的 authorities 属性
//      *                    <br>参看 https://developer.android.com/reference/android/support/v4/content/FileProvider.html
//      * @param requestCode 请求值
//      */
//     @RequiresPermission(REQUEST_INSTALL_PACKAGES)
//     public static void installApp(final Activity activity,
//                                   final File file,
//                                   final String authority,
//                                   final int requestCode) {
//         if (FileUtils.isFileExists(file)) {
//             return;
//         }
//         activity.startActivityForResult(IntentUtils.getInstallAppIntent(file, authority),
//                 requestCode);
//     }
//
//
//
//     /**
//      * 卸载 App
//      *
//      * @param packageName 包名
//      */
//     public static void uninstallApp(Context context, final String packageName) {
//         if (isSpace(packageName)) {
//             return;
//         }
//         context.startActivity(IntentUtils.getUninstallAppIntent(packageName, true));
//     }
//
//     /**
//      * 卸载 App
//      *
//      * @param activity    activity
//      * @param packageName 包名
//      * @param requestCode 请求值
//      */
//     public static void uninstallApp(final Activity activity,
//                                     final String packageName,
//                                     final int requestCode) {
//         if (isSpace(packageName)) {
//             return;
//         }
//         activity.startActivityForResult(IntentUtils.getUninstallAppIntent(packageName), requestCode);
//     }
//
//     /**
//      * 打开 App
//      *
//      * @param packageName 包名
//      */
//     public static void launchApp(Context context, final String packageName) {
//         if (isSpace(packageName)) {
//             return;
//         }
//         context.startActivity(IntentUtils.getLaunchAppIntent(packageName, true));
//     }
//
//     /**
//      * 打开 App
//      *
//      * @param activity    activity
//      * @param packageName 包名
//      * @param requestCode 请求值
//      */
//     public static void launchApp(final Activity activity, final String packageName, final int requestCode) {
//         if (isSpace(packageName)) {
//             return;
//         }
//         activity.startActivityForResult(IntentUtils.getLaunchAppIntent(packageName), requestCode);
//     }
//
//
//     /**
//      * 获取 App 具体设置
//      */
//     public static void getAppDetailsSettings(Context context) {
//         getAppDetailsSettings(context  );
//     }
//
//     /**
//      * 获取 App 具体设置
//      *
//      * @param packageName 包名
//      */
//     public static void getAppDetailsSettings(Context context, final String packageName) {
//         if (isSpace(packageName)) {
//             return;
//         }
//        context.startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName, true));
//     }
//
//     /**
//      * 获取 App 名称
//      *
//      * @return App 名称
//      */
//     public static String getAppName(Context context) {
//         return getAppName(context.getPackageName());
//     }
//
//     /**
//      * 获取 App 名称
//      *
//      * @param packageName 包名
//      * @return App 名称
//      */
//     public static String getAppName(final String packageName) {
//         if (isSpace(packageName)) {
//             return null;
//         }
//         try {
//             PackageManager pm = getPackageManager();
//             PackageInfo pi = pm.getPackageInfo(packageName, 0);
//             return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
//
//     /**
//      * 获取 App 图标
//      *
//      * @return App 图标
//      */
//     public static Drawable getAppIcon(Context context) {
//         return getAppIcon(context.getPackageName());
//     }
//
//     /**
//      * 获取 App 图标
//      *
//      * @param packageName 包名
//      * @return App 图标
//      */
//     public static Drawable getAppIcon(final String packageName) {
//         if (isSpace(packageName)) {
//             return null;
//         }
//         try {
//             PackageManager pm = getPackageManager();
//             PackageInfo pi = pm.getPackageInfo(packageName, 0);
//             return pi == null ? null : pi.applicationInfo.loadIcon(pm);
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
//
//     /**
//      * 获取 App 路径
//      *
//      * @return App 路径
//      */
//     public static String getAppPath() {
//         return getAppPath(XUtil.getContext().getPackageName());
//     }
//
//     /**
//      * 获取 App 路径
//      *
//      * @param packageName 包名
//      * @return App 路径
//      */
//     public static String getAppPath(final String packageName) {
//         if (isSpace(packageName)) {
//             return null;
//         }
//         try {
//             PackageManager pm = getPackageManager();
//             PackageInfo pi = pm.getPackageInfo(packageName, 0);
//             return pi == null ? null : pi.applicationInfo.sourceDir;
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
//
//     /**
//      * 获取 App 版本号
//      *
//      * @return App 版本号
//      */
//     public static String getAppVersionName(Context context) {
//         return getAppVersionName(context.getPackageName());
//     }
//
//     /**
//      * 获取 App 版本号
//      *
//      * @param packageName 包名
//      * @return App 版本号
//      */
//     public static String getAppVersionName(final String packageName) {
//         if (isSpace(packageName)) {
//             return null;
//         }
//         try {
//             PackageManager pm = getPackageManager();
//             PackageInfo pi = pm.getPackageInfo(packageName, 0);
//             return pi == null ? null : pi.versionName;
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
//
//     /**
//      * 获取 App 版本码
//      *
//      * @return App 版本码
//      */
//     public static int getAppVersionCode(Context context) {
//         return getAppVersionCode(context.getPackageName());
//     }
//
//     /**
//      * 获取 App 版本码
//      *
//      * @param packageName 包名
//      * @return App 版本码
//      */
//     public static int getAppVersionCode(final String packageName) {
//         if (isSpace(packageName)) {
//             return -1;
//         }
//         try {
//             PackageManager pm = getPackageManager();
//             PackageInfo pi = pm.getPackageInfo(packageName, 0);
//             return pi == null ? -1 : pi.versionCode;
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//             return -1;
//         }
//     }
//
//     /**
//      * 判断 App 是否是系统应用
//      *
//      * @return {@code true}: 是<br>{@code false}: 否
//      */
//     public static boolean isSystemApp(Context context) {
//         return isSystemApp(context.getPackageName());
//     }
//
//     /**
//      * 判断 App 是否是系统应用
//      *
//      * @param packageName 包名
//      * @return {@code true}: 是<br>{@code false}: 否
//      */
//     public static boolean isSystemApp(final String packageName) {
//         if (isSpace(packageName)) {
//             return false;
//         }
//         try {
//             PackageManager pm = getPackageManager();
//             ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
//             return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }
//
//     /**
//      * 判断 App 是否是 Debug 版本
//      *
//      * @return {@code true}: 是<br>{@code false}: 否
//      */
//     public static boolean isAppDebug(Context context) {
//         return isAppDebug(context.getPackageName());
//     }
//
//     /**
//      * 判断 App 是否是 Debug 版本
//      *
//      * @param packageName 包名
//      * @return {@code true}: 是<br>{@code false}: 否
//      */
//     public static boolean isAppDebug(final String packageName) {
//         if (isSpace(packageName)) {
//             return false;
//         }
//         try {
//             PackageManager pm = getPackageManager();
//             ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
//             return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }
//
//     /**
//      * 获取 App 签名
//      *
//      * @return App 签名
//      */
//     public static Signature[] getAppSignature(Context context) {
//         return getAppSignature(context.getPackageName());
//     }
//
//     /**
//      * 获取 App 签名
//      *
//      * @param packageName 包名
//      * @return App 签名
//      */
//     public static Signature[] getAppSignature(Context context, final String packageName) {
//         if (isSpace(packageName)) {
//             return null;
//         }
//         try {
//             PackageManager pm = context.getPackageManager();
//
//             PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
//             return pi == null ? null : pi.signatures;
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
//
//     /**
//      * 判断 App 是否处于前台
//      *
//      * @return {@code true}: 是<br>{@code false}: 否
//      */
//     public static boolean isAppForeground(Context context) {
//         ActivityManager manager = getActivityManager(context);
//         List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
//         if (info == null || info.size() == 0) {
//             return false;
//         }
//         for (ActivityManager.RunningAppProcessInfo aInfo : info) {
//             if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                 return aInfo.processName.equals(context.getPackageName());
//             }
//         }
//         return false;
//     }
//
//     /**
//      * 是否是TopActivity
//      *
//      * @param packageName
//      * @return
//      */
//     public static boolean isTopActivity(String packageName) {
//         if (TextUtils.isEmpty(packageName)) {
//             return false;
//         }
//         ComponentName topActivity = getTopActivityComponent();
//         return topActivity != null && packageName.equals(topActivity.getPackageName());
//     }
//
//     /**
//      * 获取TopActivity的组件
//      *
//      * @return
//      */
//     public static ComponentName getTopActivityComponent(Context context) {
//         ComponentName topActivity = null;
//         ActivityManager activityManager = getActivityManager(context);
//         List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager
//                 .getRunningTasks(1);
//         if (runningTaskInfos != null) {
//             topActivity = runningTaskInfos.get(0).topActivity;
//         }
//         return topActivity;
//     }
//
//     public static ActivityManager getActivityManager(Context context) {
//         return (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//     }
//
//     /**
//      * 封装 App 信息的 Bean 类
//      */
//     public static class AppInfo {
//
//         private String name;
//         private Drawable icon;
//         private String packageName;
//         private String packagePath;
//         private String versionName;
//         private int versionCode;
//         private boolean isSystem;
//
//         public Drawable getIcon() {
//             return icon;
//         }
//
//         public void setIcon(final Drawable icon) {
//             this.icon = icon;
//         }
//
//         public boolean isSystem() {
//             return isSystem;
//         }
//
//         public void setSystem(final boolean isSystem) {
//             this.isSystem = isSystem;
//         }
//
//         public String getName() {
//             return name;
//         }
//
//         public void setName(final String name) {
//             this.name = name;
//         }
//
//         public String getPackageName() {
//             return packageName;
//         }
//
//         public void setPackageName(final String packageName) {
//             this.packageName = packageName;
//         }
//
//         public String getPackagePath() {
//             return packagePath;
//         }
//
//         public void setPackagePath(final String packagePath) {
//             this.packagePath = packagePath;
//         }
//
//         public int getVersionCode() {
//             return versionCode;
//         }
//
//         public void setVersionCode(final int versionCode) {
//             this.versionCode = versionCode;
//         }
//
//         public String getVersionName() {
//             return versionName;
//         }
//
//         public void setVersionName(final String versionName) {
//             this.versionName = versionName;
//         }
//
//         /**
//          * @param name        名称
//          * @param icon        图标
//          * @param packageName 包名
//          * @param packagePath 包路径
//          * @param versionName 版本号
//          * @param versionCode 版本码
//          * @param isSystem    是否系统应用
//          */
//         public AppInfo(String packageName, String name, Drawable icon, String packagePath,
//                        String versionName, int versionCode, boolean isSystem) {
//             this.setName(name);
//             this.setIcon(icon);
//             this.setPackageName(packageName);
//             this.setPackagePath(packagePath);
//             this.setVersionName(versionName);
//             this.setVersionCode(versionCode);
//             this.setSystem(isSystem);
//         }
//
//         @NonNull
//         @Override
//         public String toString() {
//             return "pkg name: " + getPackageName() +
//                     "\napp name: " + getName() +
//                     "\napp path: " + getPackagePath() +
//                     "\napp v name: " + getVersionName() +
//                     "\napp v code: " + getVersionCode() +
//                     "\nis system: " + isSystem();
//         }
//     }
//
//     /**
//      * 获取 App 信息
//      * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
//      *
//      * @return 当前应用的 AppInfo
//      */
//     public static AppInfo getAppInfo(Context context) {
//         return getAppInfo(context, context.getPackageName());
//     }
//
//     /**
//      * 获取 App 信息
//      * <p>AppInfo（名称，图标，包名，版本号，版本 Code，是否系统应用）</p>
//      *
//      * @param packageName 包名
//      * @return 当前应用的 AppInfo
//      */
//     public static AppInfo getAppInfo(Context context,final String packageName) {
//         try {
//             PackageManager pm = context.getPackageManager();
//             PackageInfo pi = pm.getPackageInfo(packageName, 0);
//             return getBean(pm, pi);
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
//
//     /**
//      * 得到 AppInfo 的 Bean
//      *
//      * @param pm 包的管理
//      * @param pi 包的信息
//      * @return AppInfo 类
//      */
//     private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
//         if (pm == null || pi == null) {
//             return null;
//         }
//         ApplicationInfo ai = pi.applicationInfo;
//         String packageName = pi.packageName;
//         String name = ai.loadLabel(pm).toString();
//         Drawable icon = ai.loadIcon(pm);
//         String packagePath = ai.sourceDir;
//         String versionName = pi.versionName;
//         int versionCode = pi.versionCode;
//         boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
//         return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
//     }
//
//     /**
//      * 获取所有已安装 App 信息
//      * <p>{@link #getBean(PackageManager, PackageInfo)}
//      * （名称，图标，包名，包路径，版本号，版本 Code，是否系统应用）</p>
//      * <p>依赖上面的 getBean 方法</p>
//      *
//      * @return 所有已安装的 AppInfo 列表
//      */
//     public static List<AppInfo> getAppsInfo(Context context) {
//         List<AppInfo> list = new ArrayList<>();
//         PackageManager pm = context.getPackageManager();
//         // 获取系统中安装的所有软件信息
//         List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
//         for (PackageInfo pi : installedPackages) {
//             AppInfo ai = getBean(pm, pi);
//             if (ai == null) {
//                 continue;
//             }
//             list.add(ai);
//         }
//         return list;
//     }
//
//
//
//     /**
//      * 获取manifest里注册的meta-data值集合
//      *
//      * @return meta-data值集合
//      */
//     @Nullable
//     public static Bundle getMetaDatas(Context context) {
//         try {
//             PackageManager pm = context.getPackageManager();
//             return pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }
//
//     /**
//      * 获取meta-data中的String类型的值
//      *
//      * @param key
//      * @return String类型的值
//      */
//     @Nullable
//     public static String getStringValueInMetaData(String key) {
//         Bundle metaData = AppUtils.getMetaDatas();
//         return metaData != null ? metaData.getString(key) : null;
//     }
//
//     /**
//      * 获取meta-data中的Int类型的值
//      *
//      * @param key
//      * @return Int类型的值
//      */
//     public static int getIntValueInMetaData(String key) {
//         Bundle metaData = AppUtils.getMetaDatas();
//         return metaData != null ? metaData.getInt(key) : 0;
//     }
//
//     /**
//      * 获取meta-data中的Float类型的值
//      *
//      * @param key
//      * @return Float类型的值
//      */
//     public static float getFloatValueInMetaData(String key) {
//         Bundle metaData = AppUtils.getMetaDatas();
//         return metaData != null ? metaData.getFloat(key) : 0F;
//     }
//
//     /**
//      * 获取meta-data中的Double类型的值
//      *
//      * @param key
//      * @return Double类型的值
//      */
//     public static double getDoubleValueInMetaData(String key) {
//         Bundle metaData = AppUtils.getMetaDatas();
//         return metaData != null ? metaData.getDouble(key) : 0D;
//     }
//
//     // /**
//     //  * 清除 App 所有数据
//     //  *
//     //  * @param dirPaths 目录路径
//     //  * @return {@code true}: 成功<br>{@code false}: 失败
//     //  */
//     // public static boolean cleanAppData(final String... dirPaths) {
//     //     File[] dirs = new File[dirPaths.length];
//     //     int i = 0;
//     //     for (String dirPath : dirPaths) {
//     //         dirs[i++] = new File(dirPath);
//     //     }
//     //     return cleanAppData(dirs);
//     // }
//
//     // /**
//     //  * 清除 App 所有数据
//     //  *
//     //  * @param dirs 目录
//     //  * @return {@code true}: 成功<br>{@code false}: 失败
//     //  */
//     // public static boolean cleanAppData(final File... dirs) {
//     //     boolean isSuccess = CleanUtils.cleanInternalCache();
//     //     isSuccess &= CleanUtils.cleanInternalDbs();
//     //     isSuccess &= CleanUtils.cleanInternalSp();
//     //     isSuccess &= CleanUtils.cleanInternalFiles();
//     //     isSuccess &= CleanUtils.cleanExternalCache();
//     //     for (File dir : dirs) {
//     //         isSuccess &= CleanUtils.cleanCustomCache(dir);
//     //     }
//     //     return isSuccess;
//     // }
//
//     private static boolean isSpace(final String s) {
//         if (s == null) {
//             return true;
//         }
//         for (int i = 0, len = s.length(); i < len; ++i) {
//             if (!Character.isWhitespace(s.charAt(i))) {
//                 return false;
//             }
//         }
//         return true;
//     }
//
//     private static boolean isDeviceRooted() {
//         String su = "su";
//         String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
//                 "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
//         for (String location : locations) {
//             if (new File(location + su).exists()) {
//                 return true;
//             }
//         }
//         return false;
//     }
//
//     private static final int APP_INSTALL_AUTO = 0;
//     private static final int APP_INSTALL_INTERNAL = 1;
//     private static final int APP_INSTALL_EXTERNAL = 2;
//
//
//     /**
//      * whether context is system application
//      *
//      * @param context
//      * @return
//      */
//     private static boolean isSystemApplication(Context context) {
//         return context != null && isSystemApplication(context, context.getPackageName());
//
//     }
//
//     /**
//      * whether packageName is system application
//      *
//      * @param context
//      * @param packageName
//      * @return
//      */
//     private static boolean isSystemApplication(Context context, String packageName) {
//         return context != null && isSystemApplication(context.getPackageManager(), packageName);
//
//     }
//
//     /**
//      * whether packageName is system application
//      *
//      * @param packageManager
//      * @param packageName
//      * @return <ul>
//      * <li>if packageManager is null, return false</li>
//      * <li>if package name is null or is empty, return false</li>
//      * <li>if package name not exit, return false</li>
//      * <li>if package name exit, but not system app, return false</li>
//      * <li>else return true</li>
//      * </ul>
//      */
//     private static boolean isSystemApplication(PackageManager packageManager, String packageName) {
//         if (packageManager == null || packageName == null || packageName.length() == 0) {
//             return false;
//         }
//         try {
//             ApplicationInfo app = packageManager.getApplicationInfo(packageName, 0);
//             return (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0;
//         } catch (PackageManager.NameNotFoundException e) {
//             e.printStackTrace();
//         }
//         return false;
//     }
// }
