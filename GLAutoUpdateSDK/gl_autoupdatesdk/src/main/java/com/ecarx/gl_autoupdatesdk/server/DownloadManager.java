package com.ecarx.gl_autoupdatesdk.server;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.ecarx.gl_autoupdatesdk.R;
import com.ecarx.gl_autoupdatesdk.bean.AppUpdateInfoBean;
import com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting;
import com.ecarx.gl_autoupdatesdk.utils.UpdateConstants;
import com.ecarx.gl_autoupdatesdk.utils.UpdateSP;

import java.io.File;
import java.util.LinkedList;

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
    public void notifyNotification(int percent) {
        contentView.setTextViewText(R.id.default_update_progress_text, percent + "%");
        contentView.setInt(R.id.default_update_progress_text, "setTextColor", isDarkNotificationTheme(GLAutoUpdateSetting.getInstance().getContext())==true?Color.WHITE:Color.BLACK);
        contentView.setProgressBar(R.id.default_update_progress_bar, 100, percent, false);
        notification.contentView = contentView;
        if (notification.contentView != null) {

        }
        notificationManager.notify(UpdateConstants.NOTIFICATION_ACTION, notification);

    }

    /**
     * 显示安装
     */
    public void showInstallNotificationUI(File file) {
        if (ntfBuilder == null) {
            ntfBuilder = new NotificationCompat.Builder(mContext.getApplicationContext());
        }
        ntfBuilder.setSmallIcon(mContext.getApplicationContext().getApplicationInfo().icon)
                .setContentTitle(mContext.getApplicationContext().getString(mContext.getApplicationContext().getApplicationInfo().labelRes))
                .setContentText("下载完成，点击安装").setTicker("任务下载完成");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(
                Uri.fromFile(file),
                "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(
                mContext, 0, intent, 0);
        ntfBuilder.setContentIntent(pendingIntent);
        if (notificationManager == null) {
            notificationManager = (NotificationManager) mContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        notificationManager.notify(UpdateConstants.NOTIFICATION_ACTION,
                ntfBuilder.build());

    }

    public static boolean isDarkNotificationTheme(Context context) {
        return !isSimilarColor(Color.BLACK, getNotificationColor(context));
    }

    /**
     * 获取通知栏颜色
     * @param context
     * @return
     */
    public static int getNotificationColor(Context context) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        Notification notification=builder.build();
        int layoutId=notification.contentView.getLayoutId();
        ViewGroup viewGroup= (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null, false);
        if (viewGroup.findViewById(android.R.id.title)!=null) {
            return ((TextView) viewGroup.findViewById(android.R.id.title)).getCurrentTextColor();
        }
        return findColor(viewGroup);
    }

    private static boolean isSimilarColor(int baseColor, int color) {
        int simpleBaseColor=baseColor|0xff000000;
        int simpleColor=color|0xff000000;
        int baseRed=Color.red(simpleBaseColor)-Color.red(simpleColor);
        int baseGreen=Color.green(simpleBaseColor)-Color.green(simpleColor);
        int baseBlue=Color.blue(simpleBaseColor)-Color.blue(simpleColor);
        double value=Math.sqrt(baseRed*baseRed+baseGreen*baseGreen+baseBlue*baseBlue);
        if (value<180.0) {
            return true;
        }
        return false;
    }
    private static int findColor(ViewGroup viewGroupSource) {
        int color=Color.TRANSPARENT;
        LinkedList<ViewGroup> viewGroups=new LinkedList<>();
        viewGroups.add(viewGroupSource);
        while (viewGroups.size()>0) {
            ViewGroup viewGroup1=viewGroups.getFirst();
            for (int i = 0; i < viewGroup1.getChildCount(); i++) {
                if (viewGroup1.getChildAt(i) instanceof ViewGroup) {
                    viewGroups.add((ViewGroup) viewGroup1.getChildAt(i));
                }
                else if (viewGroup1.getChildAt(i) instanceof TextView) {
                    if (((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor()!=-1) {
                        color=((TextView) viewGroup1.getChildAt(i)).getCurrentTextColor();
                    }
                }
            }
            viewGroups.remove(viewGroup1);
        }
        return color;
    }

}
