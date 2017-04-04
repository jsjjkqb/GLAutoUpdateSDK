package com.ecarx.gl_autoupdatesdk.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ecarx.gl_autoupdatesdk.R;
import com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting;
import com.ecarx.gl_autoupdatesdk.utils.LogTool;
import com.ecarx.gl_autoupdatesdk.utils.UpdateSP;


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
public class DownloadDialogActivity extends Activity {

    private ProgressBar pgBar;
    private TextView tvPg;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        @LayoutRes int layoutId = UpdateSP.getDialogDownloadLayout();
        if (layoutId > 0) {
            setContentView(layoutId);
        } else {
            setContentView(R.layout.default_download_dialog);
        }
        pgBar = (ProgressBar) findViewById(R.id.default_update_progress_bar);
        tvPg = (TextView) findViewById(R.id.default_update_progress_text);
      //  broadcast();
    }

    /**
     * 刷新下载进度
     */
    private void updateProgress(long percent) {
        if (tvPg != null) {
            tvPg.setText(percent + "%");
            pgBar.setProgress((int) percent);
        }
        if (percent >= 100) {
            finish();
        }
    }

    /**
     * 注册广播
     */
    public void broadcast() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager
                .getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter
                .addAction("com.cn.ecarx.update.downloadBroadcast");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                /** 刷新数据 */
                long type = intent.getLongExtra("type", 0);
                updateProgress(type);
                LogTool.d("刷新数据 "+type);
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver,
                intentFilter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && UpdateSP.isForced()) {
            finish();
            if (GLAutoUpdateSetting.getInstance().getForceListener() != null) {
                GLAutoUpdateSetting.getInstance().getForceListener().onUserCancel(UpdateSP.isForced());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
