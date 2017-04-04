package com.ecarx.gl_autoupdatesdk.config;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.ecarx.gl_autoupdatesdk.ParseData;
import com.ecarx.gl_autoupdatesdk.UpdateAgent;
import com.ecarx.gl_autoupdatesdk.callback.OnlineCheckListener;
import com.ecarx.gl_autoupdatesdk.callback.listener.ForceListener;
import com.ecarx.gl_autoupdatesdk.callback.listener.UpdateListener;
import com.ecarx.gl_autoupdatesdk.type.RequestType;
import com.ecarx.gl_autoupdatesdk.type.UpdateType;
import com.ecarx.gl_autoupdatesdk.utils.UpdateSP;

import java.util.TreeMap;

/**
 * ========================================
 * 作 者：zhaochong
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/3/27
 * <p/>
 * 描 述：设置自动更新sdk参数
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class GLAutoUpdateSetting {

    private static GLAutoUpdateSetting instance;
    //是否已经下载完成
    public static boolean finshDown;
    private Context mContext;
    private Activity activity;
    private String checkUrl;
    public static String appname ;
    private TreeMap<String, Object> checkParams;
    private TreeMap<String, Object> onlineParams;
    private String onlineUrl;
    private ParseData parserCheckJson;
    private ParseData parserOnlineJson;
    private UpdateListener mUpdateListener;
    private ForceListener mForceListener;
    private OnlineCheckListener mOnlineCheckListener;
    private String requestResultData;
    private  String downloadPath;


    public String getDownloadPath() {
        return downloadPath;
    }

    public GLAutoUpdateSetting setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
        return this;
    }



    //设置更新对话框的布局
    public GLAutoUpdateSetting setDialogLayout(int view) {
        UpdateSP.setDialogLayout(view);
        return this;
    }

    //设置更新状态栏的布局
    public GLAutoUpdateSetting setStatusBarLayout(int view) {
        UpdateSP.setStatusBarLayout(view);
        return this;
    }

    //设置强制更新更新下载的布局
    public GLAutoUpdateSetting setDialogDownloadLayout(int view) {
        UpdateSP.setDialogDownloadLayout(view);
        return this;
    }

    //清除自定义布局设置
    public GLAutoUpdateSetting setClearCustomLayoutSetting() {
        UpdateSP.setDialogLayout(-1);
        UpdateSP.setStatusBarLayout(-1);
        UpdateSP.setDialogDownloadLayout(-1);
        return this;
    }

    //联网请求方式
    private RequestType mRequestType = RequestType.get;

    //默认需要检测更新
    private UpdateType mUpdateType = UpdateType.autoupdate;

    public static GLAutoUpdateSetting getInstance() {
        if (instance == null) {
            throw new RuntimeException("UpdateHelper not initialized!");
        } else {
            return instance;
        }
    }

    public static void init(Context appContext) {
        instance = new GLAutoUpdateSetting(appContext);
    }

    public GLAutoUpdateSetting(Context context) {
        this.mContext = context;

    }

    public GLAutoUpdateSetting setMethod(RequestType requestType) {
        this.mRequestType = requestType;
        return this;
    }

    public GLAutoUpdateSetting setCheckUrl(String url) {
        UpdateSP.setDialogLayout(0);
        this.checkUrl = url;
        return this;
    }

    public GLAutoUpdateSetting setCheckUrl(String url, TreeMap<String, Object> params) {
        this.checkUrl = url;
        this.checkParams = params;
        return this;
    }

    public GLAutoUpdateSetting setOnlineUrl(String url) {
        this.onlineUrl = url;
        return this;
    }

    public GLAutoUpdateSetting setOnlineUrl(String url, TreeMap<String, Object> params) {
        this.onlineUrl = url;
        this.onlineParams = params;
        return this;
    }

    /**
     * 设置请求返回的结果内容
     */
    public GLAutoUpdateSetting setRequestResultData(String data) {
        this.requestResultData = data;
        return this;
    }

    public GLAutoUpdateSetting setUpdateListener(UpdateListener listener) {
        this.mUpdateListener = listener;
        return this;
    }

    public GLAutoUpdateSetting setForceListener(ForceListener listener) {
        this.mForceListener = listener;
        return this;
    }

    public GLAutoUpdateSetting setOnlineCheckListener(OnlineCheckListener listener) {
        this.mOnlineCheckListener = listener;
        return this;
    }

    public GLAutoUpdateSetting setCheckJsonParser(ParseData jsonParser) {
        this.parserCheckJson = jsonParser;
        return this;
    }

    public GLAutoUpdateSetting setOnlineJsonParser(ParseData jsonParser) {
        this.parserOnlineJson = jsonParser;
        return this;
    }

    public GLAutoUpdateSetting setUpdateType(UpdateType updateType) {
        this.mUpdateType = updateType;
        return this;
    }

    public GLAutoUpdateSetting setForced(boolean isForce) {
        UpdateSP.setForced(isForce);
        return this;
    }

    public Context getContext() {
        if (mContext == null) {
            throw new RuntimeException("should call UpdateConfig.install first");
        }
        return mContext;
    }

    public UpdateType getUpdateType() {
        return mUpdateType;
    }

    public String getCheckUrl() {
        if (TextUtils.isEmpty(checkUrl)) {
            throw new IllegalArgumentException("checkUrl is null");
        }
        return checkUrl;
    }

    public String getOnlineUrl() {
        return onlineUrl;
    }

    public String getRequestResultData() {
        return requestResultData;
    }

    public TreeMap<String, Object> getOnlineParams() {
        return onlineParams;
    }

    public TreeMap<String, Object> getCheckParams() {
        return checkParams;
    }

    public RequestType getRequestMethod() {
        return mRequestType;
    }

    public ParseData getCheckJsonParser() {
        if (parserCheckJson == null) {
            throw new IllegalStateException("update parser is null");
        }
        return parserCheckJson;
    }

    public ParseData getOnlineJsonParser() {
        return parserOnlineJson;
    }



    public void check(Activity activity) {
        this.activity = activity;
        UpdateAgent.getInstance().checkUpdate(activity);
    }
    public Activity getActivity() {
        return activity;
    }

/*    public void checkNoUrl(Activity activity) {
        UpdateAgent.getInstance().checkNoUrlUpdate(activity);
    }
*/

    public UpdateListener getUpdateListener() {
        return mUpdateListener;
    }

    public ForceListener getForceListener() {
        return mForceListener;
    }

    public OnlineCheckListener getOnlineCheckListener() {
        return mOnlineCheckListener;
    }
}
