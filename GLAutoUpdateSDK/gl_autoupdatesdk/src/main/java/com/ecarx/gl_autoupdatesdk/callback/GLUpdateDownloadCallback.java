package com.ecarx.gl_autoupdatesdk.callback;

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
public interface GLUpdateDownloadCallback {

    void onStart();

    void onPercent(long percentNumber);

    void onDownloadComplete(String s);

    void onFail(Throwable throwable);

    void onStop();
}
