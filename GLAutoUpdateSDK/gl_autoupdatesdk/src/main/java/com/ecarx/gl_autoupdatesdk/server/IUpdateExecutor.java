package com.ecarx.gl_autoupdatesdk.server;

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
public interface IUpdateExecutor {

    /**
     * check if is new version exist;
     */
    void check(UpdateWorker worker);

    /**
     * check if is new version exist;
     */
  //  void checkNoUrl(UpdateNoUrlWorker worker);

    /**
     * request default_download new version apk
     */
   // void onlineCheck(OnlineCheckWorker worker);
}
