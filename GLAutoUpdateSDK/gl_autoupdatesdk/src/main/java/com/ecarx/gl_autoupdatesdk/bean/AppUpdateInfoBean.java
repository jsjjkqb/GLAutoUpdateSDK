package com.ecarx.gl_autoupdatesdk.bean;

import java.io.Serializable;

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
public class AppUpdateInfoBean implements Serializable {

    /**
     * original data.
     */
    private String original;
    /**
     * update content,
     */
    private String updateContent;
    /**
     * update checkUrl
     */
    private String updateUrl;
    /**
     * update code
     */
    private int versionCode;
    /**
     * update name
     */
    private String versionName;
    /**
     * update appname
     */
    private String appName;
    /**
     * update md5
     */
    private String md5;
    /**
     * update name
     */
    private long apkSize;
    /**
     * update force
     */
    private boolean force;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getApkSize() {
        return apkSize;
    }

    public void setApkSize(long apkSize) {
        this.apkSize = apkSize;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}