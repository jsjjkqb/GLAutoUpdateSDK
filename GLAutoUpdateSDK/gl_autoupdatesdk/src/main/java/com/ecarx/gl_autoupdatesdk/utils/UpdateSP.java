package com.ecarx.gl_autoupdatesdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting;


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
public class UpdateSP {

    public static final String KEY_DOWN_SIZE = "update_download_size";
    public static boolean isIgnore(String version) {
        SharedPreferences sp = GLAutoUpdateSetting.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getString("_update_version_ignore", "").equals(version);
    }

    public static boolean isForced() {
        SharedPreferences sp = GLAutoUpdateSetting.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getBoolean("_update_version_forced", false);
    }

    public static int getDialogLayout() {
        SharedPreferences sp =GLAutoUpdateSetting.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getInt("_update_version_layout_id", 0);
    }

    public static int getStatusBarLayout() {
        SharedPreferences sp = GLAutoUpdateSetting.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getInt("_update_version_status_layout_id", 0);
    }

    public static int getDialogDownloadLayout() {
        SharedPreferences sp = GLAutoUpdateSetting.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        return sp.getInt("_update_version_download_layout_id", 0);
    }

    public static void setIgnore(String version) {
        SharedPreferences sp = GLAutoUpdateSetting.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("_update_version_ignore", version);
        editor.commit();
    }

    public static void setForced(boolean def) {
        SharedPreferences sp = GLAutoUpdateSetting.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("_update_version_forced", def);
        editor.commit();
    }

    public static void setDialogLayout(int def) {
        SharedPreferences sp = GLAutoUpdateSetting.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("_update_version_layout_id", def);
        editor.commit();
    }

    public static void setStatusBarLayout(int def) {
        SharedPreferences sp = GLAutoUpdateSetting.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("_update_version_status_layout_id", def);
        editor.commit();
    }

    public static void setDialogDownloadLayout(int def) {
        SharedPreferences sp = GLAutoUpdateSetting.getInstance().getContext().getSharedPreferences(KEY_DOWN_SIZE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("_update_version_download_layout_id", def);
        editor.commit();
    }
}
