package com.ecarx.gl_autoupdatesdk.utils;

import android.os.Handler;
import android.os.Looper;

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
public class HandlerUtil {
    private static Handler handler;
    public static Handler getMainHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        return handler;
    }
}
