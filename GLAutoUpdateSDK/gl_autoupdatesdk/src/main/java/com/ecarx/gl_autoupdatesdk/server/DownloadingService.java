
package com.ecarx.gl_autoupdatesdk.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.ecarx.gl_autoupdatesdk.bean.AppUpdateInfoBean;
import com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting;
import com.ecarx.gl_autoupdatesdk.utils.LogTool;
import com.ecarx.gl_autoupdatesdk.utils.UpdateConstants;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zlc.season.rxdownload.RxDownload;
import zlc.season.rxdownload.entity.DownloadStatus;


/**
 * ========================================
 * 作 者：zhaochong
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/3/26 23:25
 * <p/>
 * 描 述：原理
 * 纵线
 * 首先是点击更新时，弹出进度对话框（进度，取消和运行在后台），
 * 如果是在前台完成下载，弹出安装对话框，
 * 如果是在后台完成下载，通知栏提示下载完成，
 * 横线
 * 如果进入后台后，还在继续下载点击时重新回到原界面
 * 如果强制更新无进入后台功能
 * 如果是静默更新，安静的安装
 * <p/>
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class DownloadingService extends Service {
    private Context mContext;
    private AppUpdateInfoBean update;
    private String url;
    private Subscription subscription ;
    private int opState = 0;
    private long percentNumber = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int action = intent.getIntExtra(UpdateConstants.DATA_ACTION, 0);
            switch (action) {
                case UpdateConstants.START_DOWN:
                    update = (AppUpdateInfoBean) intent.getSerializableExtra(UpdateConstants.DATA_UPDATE);
                    url = update.getUpdateUrl();
                    if (update != null && !TextUtils.isEmpty(url)) {
                        startDownload(url);
                    }
                    break;
                case UpdateConstants.PAUSE_DOWN:
                    pauseDownload(url);
                    opState = 0;
                    DownloadManager.getInstance(mContext).updateNotification(1);
                    break;
                case UpdateConstants.CANCEL_DOWN:
                    deleteDownload(url);
                    opState = 2;
                    DownloadManager.getInstance(mContext).updateNotification(2);
                    break;
                default:
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 是否正在下载
     *
     * @param url
     * @return
     */
    private boolean isDownloading(String url) {
        //接收事件可以在任何地方接收，不管该任务是否开始下载均可接收.
        if (percentNumber < 100) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 单一暂停，暂停地址为url的下载任务
     *
     * @param url
     */
    private void deleteDownload(String url) {
        RxDownload.getInstance().deleteServiceDownload(url).subscribe();
    }

    /**
     * 是否删除文件
     *
     * @return
     */
    private boolean isDeleteFile() {
        return true;
    }

    /**
     * 暂停地址为url的下载并从数据库中删除记录，deleteFile为true会同时删除该url下载产生的所有文件
     *
     * @param url
     */
    private void pauseDownload(String url) {
        RxDownload.getInstance().pauseServiceDownload(url).subscribe();
    }

    /**
     * 开始下载
     *
     * @param url
     */
    private void startDownload(String url) {
       subscription = RxDownload.getInstance()
                /**设置最大线程*/
                .maxThread(3)
                /**设置下载失败重试次数*/
                .maxRetryCount(3)
                /**Service同时下载数量*/
                .maxDownloadNumber(5)
                .download(url, update.getAppName(), GLAutoUpdateSetting.getInstance().getDownloadPath())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DownloadStatus>() {
                    @Override
                    public void onCompleted() {
                        GLAutoUpdateSetting.finshDown = true;
                        sendBroadcastType(100);
                        stopSelf();//停止服务
                        LogTool.d("下载成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogTool.d("下载失败" + e);
                        //    glUpdateDownloadCallback.onFail(e);
                    }

                    @Override
                    public void onNext(final DownloadStatus status) {
                        /** 下载状态, 解决重复下载字段问题*/
                        if (percentNumber != Long.parseLong(status.getPercent())) {
                            LogTool.d("DownloadStatus为下载进度" + status.getPercent() + "%");
                            percentNumber = Long.parseLong(status.getPercent());
                            if (percentNumber < 100) {
                                sendBroadcastType(percentNumber);
                            }
                        }
                    }
                });
    }

    /**
     * 发送下载完成信息
     *
     * @param type
     */
    private void sendBroadcastType(long type) {
        Intent intent = new Intent("com.cn.ecarx.update.downloadBroadcast");
        intent.putExtra("type", type);
        LogTool.d("发送下载数据： " + type);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pauseDownload(url);
        LogTool.d("服务销毁");
    }
}