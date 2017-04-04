package com.ecarx.gl_autoupdatesdk;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.ecarx.gl_autoupdatesdk.bean.AppUpdateInfoBean;
import com.ecarx.gl_autoupdatesdk.bean.CheckResultRepository;
import com.ecarx.gl_autoupdatesdk.bean.UpdateBean;
import com.ecarx.gl_autoupdatesdk.callback.GLCallback;
import com.ecarx.gl_autoupdatesdk.callback.GLUpdateDownloadCallback;
import com.ecarx.gl_autoupdatesdk.callback.UICheckUpdateCallback;
import com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting;
import com.ecarx.gl_autoupdatesdk.type.RequestType;
import com.ecarx.gl_autoupdatesdk.type.UpdateType;
import com.ecarx.gl_autoupdatesdk.utils.LogTool;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.ecarx.gl_autoupdatesdk.utils.UpdateErrorCode.CHECKURL_ERRODE;


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

public class AutoUpdateSDKManager {

    /**
     *  初始化
     * @param context
     * @param callback
     */
    public static GLAutoUpdateSetting init(Context context, String checkUrl, GLCallback callback) {
        if (checkUrl != null) {
            initCustom(context,checkUrl, getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getAbsolutePath(),
                    R.layout.default_update_dialog, R.layout.default_download_notification, R.layout.default_update_dialog);
            callback.onResult(true);
        } else {
            callback.onError(CHECKURL_ERRODE);
        }
        updateCheck(context);
        return GLAutoUpdateSetting.getInstance();
     }
    /**
     *   版本检测更新
     * @param context
     */
    public static void   updateCheck(Context context) {
            GLAutoUpdateSetting.getInstance()
                    .setUpdateType(UpdateType.autoupdate);
    }

    /**
     * 更新下载
     * @param context
     * @param callback
     */
    public static void updateDownload(Context context, GLUpdateDownloadCallback callback) { /* compiled code */ }

    /**
     * 更新安装
     * @param context
     * @param apkPath
     */

    public static void updateInstall(Context context, String apkPath) { /* compiled code */ }

    /**
     *  默认 UI 更新
     * @param context
     * @param callback
     */

    public static void uiUpdateAction(Context context, UICheckUpdateCallback callback) {
        //DownloadManager.getInstance(context).initUI().default_download();
    }

    /**
     * 静默下载
     * @param context
     */

    public static void silenceUpdateAction(Context context) {
     //   DownloadManager.getInstance(context).default_download();
    }

    /**
     *    版本检测更新
     * @param context
     * @param callback
     */

    public static void asUpdateAction(Context context, UICheckUpdateCallback callback) { /* compiled code */ }

    /**
     * 初始化配置
     * @param context
     */
    public static void initCustom(Context context, String checkUrl, String downloadPath, int updateDialogId, int notification, int downloadDialogId) {
        GLAutoUpdateSetting.init(context);
        GLAutoUpdateSetting.getInstance()
                /**可填：请求方式*/
                .setMethod(RequestType.get)
                /**必填：设置存储路径*/
                .setDownloadPath(downloadPath)
                /**必填：数据更新接口，该方法一定要在setDialogLayout的前面,因为这方法里面做了重置DialogLayout的操作*/
                .setCheckUrl(checkUrl)
                /**可填：清除旧的自定义布局设置。之前有设置过自定义布局，建议这里调用下*/
                .setClearCustomLayoutSetting()
                /**可填：自定义更新弹出的dialog的布局样式，主要案例中的布局样式里面的id为（_update_content、_update_id_ok、_update_id_cancel）的view类型和id不能修改，其他的都可以修改或删除*/
                .setDialogLayout(updateDialogId)
                /**可填：自定义更新状态栏的布局样式，主要案例中的布局样式里面的id为（_update_iv_iconjjdxm_update_rich_notification_continue、_update_rich_notification_cancel、_update_title、update_progress_text、_update_progress_bar）的view类型和id不能修改，其他的都可以修改或删除*/
                .setStatusBarLayout(notification)
                /**可填：自定义强制更新弹出的下载进度的布局样式，主要案例中的布局样式里面的id为(_update_progress_bar、_update_progress_text)的view类型和id不能修改，其他的都可以修改或删除*/
                .setDialogDownloadLayout(downloadDialogId)
                /**必填：用于从数据更新接口获取的数据response中。解析出Update实例。以便框架内部处理*/
                .setCheckJsonParser(new ParseData() {
                    @Override
                    public AppUpdateInfoBean parse(String response) {
                        /**真实情况下使用的解析  response接口请求返回的数据*/
                        LogTool.d("获取更新内容：" + response);
                        CheckResultRepository checkResultRepository = JSON.parseObject(response,CheckResultRepository.class);
                        /**这里是模拟后台接口返回的json数据解析的bean，需要根据真实情况来写*/
                        UpdateBean updateBean = checkResultRepository.getData();
                        AppUpdateInfoBean updateInfoBean = new AppUpdateInfoBean();
                        /**必填：此apk包的下载地址*/
                        updateInfoBean.setUpdateUrl(updateBean.getDownload_url());
                        /**必填：此apk包的版本号*/
                        updateInfoBean.setVersionCode(updateBean.getV_code());
                        /**可填：此apk包的版本号*/
                        updateInfoBean.setApkSize(updateBean.getV_size());
                        /**必填：此apk包的版本名称*/
                        updateInfoBean.setVersionName(updateBean.getV_name());
                        /**必填：此apk包的名称*/
                        updateInfoBean.setAppName(updateBean.getApp_name());
                        /**可填：此apk包的更新内容*/
                        updateInfoBean.setUpdateContent(updateBean.getUpdate_content());
                        /**可填：此apk包是否为强制更新*/
                        updateInfoBean.setForce(updateBean.isForce());
                        return updateInfoBean;
                    }
                });
    }

}
