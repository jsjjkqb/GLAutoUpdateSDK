package com.ecarx.gl_autoupdatesdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting;
import com.ecarx.gl_autoupdatesdk.server.DownloadManager;
import com.ecarx.gl_autoupdatesdk.utils.HandlerUtil;
import com.ecarx.gl_autoupdatesdk.utils.LogTool;

import java.io.File;

import static com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting.appname;

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

public class DownloadProgressReceiver extends BroadcastReceiver {
    int type;
    public static boolean isFirtInit = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        type = intent.getIntExtra("type", 0);
        updateProgress(context, type);
    }

    private void updateProgress(final Context context, final int type) {

        HandlerUtil.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                if (type == 100) {
                    DownloadManager.getInstance(context).updateNotification(2);
                }
                if (GLAutoUpdateSetting.finshDown) {
                    GLAutoUpdateSetting.getInstance().check((GLAutoUpdateSetting.getInstance().getActivity()));
                    //下载完成
                    try {
                        DownloadManager.getInstance(context).showInstallNotificationUI(new File(appname));
                    } catch (Exception e) {
                        LogTool.d("安装的异常"+e);
                    }

                }

                LogTool.d("刷新数据 " + type);
            }
        });
    }
}
