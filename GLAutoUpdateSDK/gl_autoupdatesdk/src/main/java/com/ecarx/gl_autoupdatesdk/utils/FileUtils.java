package com.ecarx.gl_autoupdatesdk.utils;

import android.content.Context;

import com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting;

import java.io.File;
import java.math.BigDecimal;

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
public class FileUtils {


    public static File createFile(String versionName) {
        File cacheDir = getCacheDir();
        cacheDir.mkdirs();
        return new File(cacheDir, "update_v_" + versionName);
    }

    private static File getCacheDir() {
        Context context = GLAutoUpdateSetting.getInstance().getContext();
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = context.getCacheDir();
        }
        cacheDir = new File(cacheDir, "update");
        return cacheDir;
    }

    public static String HumanReadableFilesize(double size) {
        String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB"};
        double mod = 1024.0;
        int i = 0;
        for (i = 0; size >= mod; i++) {
            size /= mod;
            if (i >= units.length - 1) {
                break;
            }
        }
        try {
            BigDecimal a = new BigDecimal(size + "");
            return a.setScale(2, BigDecimal.ROUND_HALF_UP) + units[i];
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
