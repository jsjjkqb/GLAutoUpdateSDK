package com.ecarx.gl_autoupdatesdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
public class NetworkUtil {

    /**
     * Whether or not to connect to the Internet
     */
    public static boolean isConnected () {
        NetworkInfo info = getNetworkInfos();
        if (info == null || !info.isConnected()) {
            return false;
        }
        return true;
    }

    /**
     * Determine whether to use wifi
     */
    public static boolean isConnectedByWifi() {
        NetworkInfo info = getNetworkInfos();
        return info != null
                && info.isConnected()
                && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    static NetworkInfo getNetworkInfos() {
        Context context = GLAutoUpdateSetting.getInstance().getContext();
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getActiveNetworkInfo();
    }
}
