package com.ecarx.gl_autoupdatesdk.server;


import com.ecarx.gl_autoupdatesdk.ParseData;
import com.ecarx.gl_autoupdatesdk.bean.AppUpdateInfoBean;
import com.ecarx.gl_autoupdatesdk.callback.listener.UpdateListener;
import com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting;
import com.ecarx.gl_autoupdatesdk.type.RequestType;
import com.ecarx.gl_autoupdatesdk.type.UpdateType;
import com.ecarx.gl_autoupdatesdk.utils.HandlerUtil;
import com.ecarx.gl_autoupdatesdk.utils.InstallUtil;
import com.ecarx.gl_autoupdatesdk.utils.LogTool;
import com.ecarx.gl_autoupdatesdk.utils.NetworkUtil;
import com.ecarx.gl_autoupdatesdk.utils.UpdateSP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;

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
public class UpdateWorker implements Runnable {

    protected String url;
    protected TreeMap<String, Object> checkParams;
    protected UpdateListener checkCB;
    protected ParseData parser;
    private RequestType requestType;

    public void setRequestMethod(RequestType requestType) {
        this.requestType = requestType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setParams(TreeMap<String, Object> checkParams) {
        this.checkParams = checkParams;
    }

    public void setUpdateListener(UpdateListener checkCB) {
        this.checkCB = checkCB;
    }

    public void setParser(ParseData parser) {
        this.parser = parser;
    }

    @Override
    public void run() {
        String response = null;
        try {
            response = check(requestType,url);
            LogTool.d("获取下载内容："+response);
            AppUpdateInfoBean parse = parser.parse(response);

            if (parse == null) {
                throw new IllegalArgumentException("parse response to update failed by " + parser.getClass().getCanonicalName());
            }
            int curVersion = InstallUtil.getApkVersion(GLAutoUpdateSetting.getInstance().getContext());
            if ((parse.getVersionCode() > curVersion) &&
                    ((UpdateSP.isIgnore(parse.getVersionCode() + "")
                            && (GLAutoUpdateSetting.getInstance().getUpdateType() == UpdateType.checkupdate))
                            || (!UpdateSP.isIgnore(parse.getVersionCode() + "")
                            && (GLAutoUpdateSetting.getInstance().getUpdateType() != UpdateType.autowifiupdate))
                            || (!UpdateSP.isIgnore(parse.getVersionCode() + "")
                            && (GLAutoUpdateSetting.getInstance().getUpdateType() == UpdateType.autowifiupdate)
                            && NetworkUtil.isConnectedByWifi()))) {
                /**有新版本*/
                UpdateSP.setForced(parse.isForce());
                sendHasUpdate(parse);
            } else {
                /**无新版本*/
                sendNoUpdate();
            }
        } catch (Exception e) {
            sendOnErrorMsg(-1, e.getMessage());
        }
    }

    /**
     * 请求检查更新接口
     * @param requestType
     * @param urlStr
     * @return
     * @throws Exception
     */
    protected String check(RequestType requestType, String urlStr) throws Exception {
        URL getUrl = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) getUrl.openConnection();
        urlConn.setConnectTimeout(10000);//超时
        if (requestType == RequestType.get) {
            urlConn.setRequestMethod("GET");
        }
        urlConn.connect();

        int responseCode = urlConn.getResponseCode();
        if (responseCode < 200 || responseCode >= 300)
            throw new HttpException(responseCode, urlConn.getResponseMessage());

        BufferedReader bis = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        String lines;
        while ((lines = bis.readLine()) != null) {
            sb.append(lines);
        }
        return sb.toString();
    }

    /**
     * 发送有更新信息
     * @param update
     */
    private void sendHasUpdate(final AppUpdateInfoBean update) {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.hasUpdate(update);
            }
        });
    }

    /**
     * 发送无更新信息
     */
    private void sendNoUpdate() {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.noUpdate();
            }
        });
    }

    /**
     * 发送错误信息
     * @param code
     * @param errorMsg
     */
    private void sendOnErrorMsg(final int code, final String errorMsg) {
        if (checkCB == null) return;
        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                checkCB.onCheckError(code, errorMsg);
            }
        });
    }

}
