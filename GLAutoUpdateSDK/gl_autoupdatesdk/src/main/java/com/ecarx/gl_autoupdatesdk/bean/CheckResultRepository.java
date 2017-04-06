package com.ecarx.gl_autoupdatesdk.bean;

import java.util.List;

/**
 * ========================================
 * 作 者：赵冲
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/3/27 10:45
 * <p/>
 * 描 述：这里模拟你真实接口返回的实际json数据的解析对象
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class CheckResultRepository {


    /**
     * code : 0
     * data : {"android":[{"download_url":"http://musicfire-log.oss-cn-hangzhou.aliyuncs.com/G-NetLink/gl_debug/auto_update_apk/G-netlink_debug.apk","force":true,"update_content":"海量更新内容，请尽情体验","v_code":"10","v_name":"v1.0.0.16070810","app_name":"G-netlink_debug.apk","v_sha1":"7db76e18ac92bb29ff0ef012abfe178a78477534","v_size":12365909,"app_id":"package_id"}],"iOS":[{"download_url":"http://musicfire-log.oss-cn-hangzhou.aliyuncs.com/G-NetLink/gl_debug/auto_update_apk/G-netlink_debug.apk","force":true,"update_content":"海量更新内容，请尽情体验","v_code":"10","v_name":"v1.0.0.16070810","app_name":"G-netlink_debug.apk","v_sha1":"7db76e18ac92bb29ff0ef012abfe178a78477534","v_size":12365909,"app_id":"bundle_id"}]}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<AndroidBean> android;
        private List<IOSBean> iOS;

        public List<AndroidBean> getAndroid() {
            return android;
        }

        public void setAndroid(List<AndroidBean> android) {
            this.android = android;
        }

        public List<IOSBean> getIOS() {
            return iOS;
        }

        public void setIOS(List<IOSBean> iOS) {
            this.iOS = iOS;
        }

        public static class AndroidBean {
            /**
             * download_url : http://musicfire-log.oss-cn-hangzhou.aliyuncs.com/G-NetLink/gl_debug/auto_update_apk/G-netlink_debug.apk
             * force : true
             * update_content : 海量更新内容，请尽情体验
             * v_code : 10
             * v_name : v1.0.0.16070810
             * app_name : G-netlink_debug.apk
             * v_sha1 : 7db76e18ac92bb29ff0ef012abfe178a78477534
             * v_size : 12365909
             * app_id : package_id
             */

            private String download_url;
            private boolean force;
            private String update_content;
            private String v_code;
            private String v_name;
            private String app_name;
            private String v_sha1;
            private int v_size;
            private String app_id;

            public String getDownload_url() {
                return download_url;
            }

            public void setDownload_url(String download_url) {
                this.download_url = download_url;
            }

            public boolean isForce() {
                return force;
            }

            public void setForce(boolean force) {
                this.force = force;
            }

            public String getUpdate_content() {
                return update_content;
            }

            public void setUpdate_content(String update_content) {
                this.update_content = update_content;
            }

            public String getV_code() {
                return v_code;
            }

            public void setV_code(String v_code) {
                this.v_code = v_code;
            }

            public String getV_name() {
                return v_name;
            }

            public void setV_name(String v_name) {
                this.v_name = v_name;
            }

            public String getApp_name() {
                return app_name;
            }

            public void setApp_name(String app_name) {
                this.app_name = app_name;
            }

            public String getV_sha1() {
                return v_sha1;
            }

            public void setV_sha1(String v_sha1) {
                this.v_sha1 = v_sha1;
            }

            public int getV_size() {
                return v_size;
            }

            public void setV_size(int v_size) {
                this.v_size = v_size;
            }

            public String getApp_id() {
                return app_id;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }
        }

        public static class IOSBean {
            /**
             * download_url : http://musicfire-log.oss-cn-hangzhou.aliyuncs.com/G-NetLink/gl_debug/auto_update_apk/G-netlink_debug.apk
             * force : true
             * update_content : 海量更新内容，请尽情体验
             * v_code : 10
             * v_name : v1.0.0.16070810
             * app_name : G-netlink_debug.apk
             * v_sha1 : 7db76e18ac92bb29ff0ef012abfe178a78477534
             * v_size : 12365909
             * app_id : bundle_id
             */

            private String download_url;
            private boolean force;
            private String update_content;
            private String v_code;
            private String v_name;
            private String app_name;
            private String v_sha1;
            private int v_size;
            private String app_id;

            public String getDownload_url() {
                return download_url;
            }

            public void setDownload_url(String download_url) {
                this.download_url = download_url;
            }

            public boolean isForce() {
                return force;
            }

            public void setForce(boolean force) {
                this.force = force;
            }

            public String getUpdate_content() {
                return update_content;
            }

            public void setUpdate_content(String update_content) {
                this.update_content = update_content;
            }

            public String getV_code() {
                return v_code;
            }

            public void setV_code(String v_code) {
                this.v_code = v_code;
            }

            public String getV_name() {
                return v_name;
            }

            public void setV_name(String v_name) {
                this.v_name = v_name;
            }

            public String getApp_name() {
                return app_name;
            }

            public void setApp_name(String app_name) {
                this.app_name = app_name;
            }

            public String getV_sha1() {
                return v_sha1;
            }

            public void setV_sha1(String v_sha1) {
                this.v_sha1 = v_sha1;
            }

            public int getV_size() {
                return v_size;
            }

            public void setV_size(int v_size) {
                this.v_size = v_size;
            }

            public String getApp_id() {
                return app_id;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }
        }
    }
}
