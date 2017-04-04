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
public class HttpException extends RuntimeException {

    private int code;
    private String errorMsg;

    public HttpException(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
