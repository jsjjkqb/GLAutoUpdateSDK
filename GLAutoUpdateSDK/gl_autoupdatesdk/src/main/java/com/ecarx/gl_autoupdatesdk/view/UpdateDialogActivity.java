package com.ecarx.gl_autoupdatesdk.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ecarx.gl_autoupdatesdk.R;
import com.ecarx.gl_autoupdatesdk.bean.AppUpdateInfoBean;
import com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting;
import com.ecarx.gl_autoupdatesdk.server.DownloadingService;
import com.ecarx.gl_autoupdatesdk.utils.FileUtils;
import com.ecarx.gl_autoupdatesdk.utils.InstallUtil;
import com.ecarx.gl_autoupdatesdk.utils.LogTool;
import com.ecarx.gl_autoupdatesdk.utils.NetworkUtil;
import com.ecarx.gl_autoupdatesdk.utils.UpdateConstants;
import com.ecarx.gl_autoupdatesdk.utils.UpdateSP;

import java.io.File;

import static com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting.appname;
import static com.ecarx.gl_autoupdatesdk.config.GLAutoUpdateSetting.finshDown;


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
public class UpdateDialogActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private View default_update_wifi_indicator;
    private TextView default_update_content;
    private Button default_update_id_ok;
    private Button default_update_id_cancel;
    private AppUpdateInfoBean mUpdate;
    private int mAction;
    private String mPath;
    private Context mContext;
    private String text;

    //调起方式
    private boolean isActivityEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        @LayoutRes int layoutId = UpdateSP.getDialogLayout();
        if (layoutId > 0) {
            setContentView(layoutId);
        } else {
            setContentView(R.layout.default_update_dialog);
        }
        Intent intent = getIntent();
        mUpdate = (AppUpdateInfoBean) intent.getSerializableExtra(UpdateConstants.DATA_UPDATE);
        mAction = intent.getIntExtra(UpdateConstants.DATA_ACTION, 0);
        mPath =  getAppPath();
        LogTool.d("下载路径 :" + mPath);
        isActivityEnter = intent.getBooleanExtra(UpdateConstants.START_TYPE, false);
        String updateContent = null;
        default_update_wifi_indicator = findViewById(R.id.default_update_wifi_indicator);
        default_update_content = (TextView) findViewById(R.id.default_update_content);
        default_update_id_ok = (Button) findViewById(R.id.default_update_id_ok);
        default_update_id_cancel = (Button) findViewById(R.id.default_update_id_cancel);
        if (default_update_wifi_indicator != null) {
            if (NetworkUtil.isConnectedByWifi()) {
                //WiFi环境
                default_update_wifi_indicator.setVisibility(View.INVISIBLE);
            } else {
                default_update_wifi_indicator.setVisibility(View.VISIBLE);
            }
        }
        if (UpdateSP.isForced()) {
            default_update_id_cancel.setVisibility(View.GONE);
        } else {
            default_update_id_cancel.setVisibility(View.VISIBLE);
        }
        if (finshDown) {
            //完成下载
            if (isActivityEnter) {
                /**Activity方式调起的*/
                if (mUpdate.getApkSize() > 0) {
                    text = getText(R.string.default_update_dialog_installapk) + "";
                } else {
                    text = "";
                }
                updateContent = getText(R.string.default_update_newversion)
                        + mUpdate.getVersionName() + "\n"
                        + text + "\n\n"
                        + getText(R.string.default_update_updatecontent) + "\n" + mUpdate.getUpdateContent() +
                        "\n";
                default_update_id_ok.setText(R.string.default_update_installnow);
                default_update_content.setText(updateContent);
            } else {
                /**服务方式方式调起的*/
                InstallUtil.installApk(mContext,  mPath);
                finish();
                if (GLAutoUpdateSetting.getInstance().getForceListener() != null) {
                    GLAutoUpdateSetting.getInstance().getForceListener().onUserCancel(UpdateSP.isForced());
                }
            }

        } else {
            //有更新下载
            if (mUpdate.getApkSize() > 0) {
                text = getText(R.string.default_update_targetsize) + FileUtils.HumanReadableFilesize(mUpdate.getApkSize());
            } else {
                text = "";
            }
            updateContent = getText(R.string.default_update_newversion)
                    + mUpdate.getVersionName() + "\n"
                    + text + "\n\n"
                    + getText(R.string.default_update_updatecontent) + "\n" + mUpdate.getUpdateContent() +
                    "\n";
            default_update_id_ok.setText(R.string.default_update_updatenow);
            default_update_content.setText(updateContent);
        }

        default_update_id_ok.setOnClickListener(this);
        default_update_id_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.default_update_id_ok) {
            if (finshDown) {
                /** 检查文件是否存在本地*/

                if (finshDown) {
                    try {
                        InstallUtil.installApk(mContext, mPath);
                        finish();
                    } catch (Exception ex) {
                        LogTool.d(" :: " +ex +":::");
                    }
                } else {
                    Intent intent = new Intent(mContext, DownloadingService.class);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(UpdateConstants.DATA_ACTION, UpdateConstants.START_DOWN);
                    intent.putExtra(UpdateConstants.DATA_UPDATE, mUpdate);
                    startService(intent);
                    finish();
                }
            } else {
                Intent intent = new Intent(mContext, DownloadingService.class);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    intent.putExtra(UpdateConstants.DATA_ACTION, UpdateConstants.START_DOWN);
                    intent.putExtra(UpdateConstants.DATA_UPDATE, mUpdate);
                } else {
                        String authority = GLAutoUpdateSetting.getInstance().getActivity().getPackageName() + ".provider";
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(GLAutoUpdateSetting.getInstance().getActivity(), authority,new File(mPath)));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startService(intent);
                finish();
                LogTool.d("isForced  : " + UpdateSP.isForced() );
                LogTool.d("获取更新点击事件");
            }
        } else if (id == R.id.default_update_id_cancel) {
            if (GLAutoUpdateSetting.getInstance().getForceListener() != null) {
                GLAutoUpdateSetting.getInstance().getForceListener().onUserCancel(UpdateSP.isForced());
            }
            if (GLAutoUpdateSetting.getInstance().getUpdateListener() != null) {
                if (!finshDown) {
                    GLAutoUpdateSetting.getInstance().getUpdateListener().onUserCancelDowning();
                } else {
                    GLAutoUpdateSetting.getInstance().getUpdateListener().onUserCancelInstall();
                }
            }
            finish();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        UpdateSP.setIgnore(isChecked ? mUpdate.getVersionCode() + "" : "");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!UpdateSP.isForced()) {
            finish();
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && UpdateSP.isForced()) {
            if (GLAutoUpdateSetting.getInstance().getForceListener() != null) {
                GLAutoUpdateSetting.getInstance().getForceListener().onUserCancel(UpdateSP.isForced());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public String getAppPath() {
        if (mUpdate.getAppName() != null) {
            appname = GLAutoUpdateSetting.getInstance().getDownloadPath() + File.separator + mUpdate.getAppName();
            return appname;
        }
        return "";
    }

}
