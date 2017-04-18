package com.ecarx.gl_autoupdatesdk.server;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.widget.RemoteViews;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.ecarx.gl_autoupdatesdk.R;
import com.ecarx.gl_autoupdatesdk.bean.AppUpdateInfoBean;
import com.ecarx.gl_autoupdatesdk.receiver.DownloadProgressReceiver;
import com.ecarx.gl_autoupdatesdk.utils.LogTool;
import com.ecarx.gl_autoupdatesdk.utils.UpdateConstants;
import com.ecarx.gl_autoupdatesdk.utils.UpdateSP;

import java.io.File;


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

public class DownloadManager {

    static DownloadManager sSingleton;
    private RemoteViews contentView;
    private NotificationManager notificationManager;
    private Notification notification;
    private AppUpdateInfoBean update;
    public NumberProgressBar bnp;
    private NotificationCompat.Builder ntfBuilder;
    private Context mContext;
    private int opState = 0;

    public DownloadManager(Context applicationContext) {
        if (applicationContext == null) {
            throw new RuntimeException(" applicationContext is null !");
        } else {
            this.mContext = applicationContext;
        }
    }

    public static DownloadManager getInstance(Context ctx) {
        if (sSingleton == null) {
            sSingleton = new DownloadManager(ctx.getApplicationContext());
        }
        return sSingleton;
    }

     public DownloadManager initUI() {
         createNotification();
         return this;
     }

    /**
     *
     */
    public void download() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mContext.startService(new Intent(mContext, DownloadingService.class));
            }
        }).start();
    }

    @SuppressWarnings("deprecation")
    public void createNotification() {
        notification = new Notification(
                mContext.getApplicationInfo().icon,
                "安装包正在下载...",
                System.currentTimeMillis());
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        /*** 自定义  Notification 的显示****/
        @LayoutRes int layoutId = UpdateSP.getStatusBarLayout();
        if (layoutId > 0) {
            contentView = new RemoteViews(mContext.getPackageName(), layoutId);
        } else {
            contentView = new RemoteViews(mContext.getPackageName(), R.layout.default_download_notification);
        }
        contentView.setImageViewResource(R.id.default_update_iv_icon, mContext.getApplicationInfo().icon);
        contentView.setTextViewText(R.id.default_update_title, mContext.getString(mContext.getApplicationInfo().labelRes));
        contentView.setProgressBar(R.id.default_update_progress_bar, 100, 0, false);
        contentView.setTextViewText(R.id.default_update_progress_text, "0%");

        /**暂停和开始*/
        Intent downIntent = new Intent(mContext, DownloadingService.class);
        downIntent.putExtra(UpdateConstants.DATA_ACTION, UpdateConstants.PAUSE_DOWN);
        downIntent.putExtra("update", update);
        PendingIntent pendingIntent1 = PendingIntent.getService(mContext, UpdateConstants.PAUSE_DOWN, downIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        contentView.setOnClickPendingIntent(R.id.default_update_rich_notification_continue, pendingIntent1);

        /**取消*/
        Intent cancelIntent = new Intent(mContext, DownloadingService.class);
        cancelIntent.putExtra(UpdateConstants.DATA_ACTION, UpdateConstants.CANCEL_DOWN);
        cancelIntent.putExtra("update", update);
        PendingIntent pendingIntent2 = PendingIntent.getService(mContext, UpdateConstants.CANCEL_DOWN, cancelIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        contentView.setOnClickPendingIntent(R.id.default_update_rich_notification_cancel, pendingIntent2);

        notification.contentView = contentView;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(UpdateConstants.NOTIFICATION_ACTION, notification);
    }

    public void updateNotification( int action) {
        if (opState == action) {
            /**暂停*/
            if (contentView != null) {
                contentView.setTextViewText(R.id.default_update_rich_notification_continue, "开始");
                notification.contentView = contentView;
                notificationManager.notify(UpdateConstants.NOTIFICATION_ACTION, notification);
            }
        } else if (opState == action) {
            /**开始*/
            if (contentView != null) {
                contentView.setTextViewText(R.id.default_update_rich_notification_continue, "暂停");
                notification.contentView = contentView;
                notificationManager.notify(UpdateConstants.NOTIFICATION_ACTION, notification);
            }
        } else if (opState == action) {
            /**取消*/
            if (notificationManager == null) {
                notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            }
           notificationManager.cancel(UpdateConstants.NOTIFICATION_ACTION);
        }
    }

    /**
     * 刷新下载进度
     */
    public void notifyNotification(final Context context,int percent) {

        contentView.setTextViewText(R.id.default_update_progress_text, percent + "%");
        contentView.setProgressBar(R.id.default_update_progress_bar, 100, percent, false);
        notification.contentView = contentView;
        notificationManager.notify(UpdateConstants.NOTIFICATION_ACTION, notification);
        DownloadProgressReceiver.isFirtInit = false;
    }

    /**
     * 显示安装
     */
    public void showInstallNotificationUI(File file) {
        String type = "application/vnd.android.package-archive";
        if (ntfBuilder == null) {
            ntfBuilder = new NotificationCompat.Builder(mContext.getApplicationContext());
        }
        ntfBuilder.setSmallIcon(mContext.getApplicationContext().getApplicationInfo().icon)
                .setContentTitle(mContext.getApplicationContext().getString(mContext.getApplicationContext().getApplicationInfo().labelRes))
                .setContentText("下载完成，点击安装").setTicker("任务下载完成");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (!file.exists()) {
            LogTool.d("文件不存在");
            return;
        }
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName()+".fileProvider", file);
            intent.setDataAndType(contentUri, type);
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
    }
        PendingIntent pendingIntent = PendingIntent.getActivity(
                mContext, 0, intent, 0);
        ntfBuilder.setContentIntent(pendingIntent);
        if (notificationManager == null) {
            notificationManager = (NotificationManager) mContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        notificationManager.notify(UpdateConstants.NOTIFICATION_ACTION,
                ntfBuilder.build());

    }


}
