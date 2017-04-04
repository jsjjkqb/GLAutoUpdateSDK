package com.ecarx.gl_autoupdatesdk;

import android.app.Activity;
import android.content.Intent;

import com.ecarx.gl_autoupdatesdk.bean.AppUpdateInfoBean;
import com.ecarx.gl_autoupdatesdk.callback.listener.UpdateListener;
import com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting;
import com.ecarx.gl_autoupdatesdk.server.IUpdateExecutor;
import com.ecarx.gl_autoupdatesdk.server.UpdateExecutor;
import com.ecarx.gl_autoupdatesdk.server.UpdateWorker;
import com.ecarx.gl_autoupdatesdk.type.RequestType;
import com.ecarx.gl_autoupdatesdk.utils.UpdateConstants;
import com.ecarx.gl_autoupdatesdk.view.UpdateDialogActivity;

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
public class UpdateAgent {

    private static UpdateAgent updater;
    private IUpdateExecutor executor;

    private UpdateAgent() {
        executor = UpdateExecutor.getInstance();
    }
    public static UpdateAgent getInstance() {
        if (updater == null) {
            updater = new UpdateAgent();
        }
        return updater;
    }


    /**
     * check out whether or not there is a new version on internet
     *
     * @param activity The activity who need to show update dialog
     */
    public void checkUpdate(final Activity activity) {
        UpdateWorker checkWorker = new UpdateWorker();
        RequestType requestType = GLAutoUpdateSetting.getInstance().getRequestMethod();
        checkWorker.setRequestMethod(requestType);
        checkWorker.setUrl(GLAutoUpdateSetting.getInstance().getCheckUrl());
        checkWorker.setParams(GLAutoUpdateSetting.getInstance().getCheckParams());
        checkWorker.setParser(GLAutoUpdateSetting.getInstance().getCheckJsonParser());

        final UpdateListener mUpdate = GLAutoUpdateSetting.getInstance().getUpdateListener();
        checkWorker.setUpdateListener(new UpdateListener() {
            @Override
            public void hasUpdate(AppUpdateInfoBean updateInfoBean) {
                if (mUpdate != null) {
                    mUpdate.hasUpdate(updateInfoBean);
                }

                Intent intent = new Intent(activity, UpdateDialogActivity.class);
                intent.putExtra(UpdateConstants.DATA_UPDATE, updateInfoBean);
                intent.putExtra(UpdateConstants.DATA_ACTION, UpdateConstants.UPDATE_TIE);
                intent.putExtra(UpdateConstants.START_TYPE, true);
                activity.startActivity(intent);

            }

            @Override
            public void noUpdate() {
                if (mUpdate != null) {
                    mUpdate.noUpdate();
                }
            }

            @Override
            public void onCheckError(int code, String errorMsg) {
                if (mUpdate != null) {
                    mUpdate.onCheckError(code, errorMsg);
                }
            }

            @Override
            public void onUserCancel() {
                if (mUpdate != null) {
                    mUpdate.onUserCancel();
                }
            }

            @Override
            public void onUserCancelDowning() {
                if (mUpdate != null) {
                    mUpdate.onUserCancelDowning();
                }
            }

            @Override
            public void onUserCancelInstall() {
                if (mUpdate != null) {
                    mUpdate.onUserCancelDowning();
                }
            }
        });
        executor.check(checkWorker);
    }

}
