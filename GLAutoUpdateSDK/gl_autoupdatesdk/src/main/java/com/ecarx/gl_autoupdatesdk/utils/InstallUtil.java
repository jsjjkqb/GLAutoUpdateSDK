package com.ecarx.gl_autoupdatesdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * ========================================
 * 作 者：zhaochong
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/3/27
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class InstallUtil {

    /**
     * install apk
     * @param context the context is used to send install apk broadcast;
     * @param filename the file name to be installed;
     */
    public static void installApk(Context context, String  filename) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        File pluginfile = new File(filename);
        if (!pluginfile.exists()) {
            LogTool.d("文件不存在");
            return;
        }
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LogTool.d("Build.VERSION_CODES.N = " + Build.VERSION_CODES.M);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.ecarx.gl_autoupdatesdk.fileProvider", pluginfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            String type = "application/vnd.android.package-archive";
            intent.setDataAndType(Uri.fromFile(pluginfile), type);
        }
        context.startActivity(intent);
    }

    public static int getApkVersion (Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionCode;
    }
}
