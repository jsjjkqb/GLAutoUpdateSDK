package com.ecarx.gl_autoupdatesdk.utils;

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
public class UpdateConstants {
    public static final String DATA_UPDATE = "update";
    public static final String DATA_ACTION = "action";
    public static final String START_TYPE = "start_type";
    public static final int UPDATE_TIE = 0;
    public static final int NOTIFICATION_ACTION = 10;
    public static final int START_DOWN = 0;
    public static final int PAUSE_DOWN = 1;
    public static final int CANCEL_DOWN = 2;

    /**
     * 检测更新接口地址
     */
    public static final String CHECKURL_RELEASE = "http://musicfire-log.oss-cn-hangzhou.aliyuncs.com/G-NetLink/gl_release/auto_update_release/autoupdate_release.json";
    public static final  String CHECKURL_DEBUG = "http://musicfire-log.oss-cn-hangzhou.aliyuncs.com/G-NetLink/gl_debug/auto_update_debug/autoupdate_debug.json";
    public static final String CHECKURL_TEST = "http://musicfire-log.oss-cn-hangzhou.aliyuncs.com/G-NetLink/gl_test/auto_update_test/autoupdate_test.json";


}
