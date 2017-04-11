package com.ecarx.glautoupdatesdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ecarx.gl_autoupdatesdk.AutoUpdateSDKManager;
import com.ecarx.gl_autoupdatesdk.callback.GLCallback;
import com.ecarx.gl_autoupdatesdk.utils.LogTool;
import com.ecarx.gl_autoupdatesdk.utils.UpdateConstants;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Button btn_force, btn_asui;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_asui = (Button) findViewById(R.id.btn_asui);
        btn_force = (Button) findViewById(R.id.btn_force);
        init(UpdateConstants.CHECKURL_TEST);
    }

    /**
     * 一般自动更新放到MainActivity里面的
     */
    private void init(String url) {
        AutoUpdateSDKManager.init(getApplicationContext(), url, new GLCallback<Boolean>() {
            @Override
            public void onResult(Boolean aBoolean) {
                LogTool.d("init onResult : " + aBoolean);
            }

            @Override
            public void onError(int i) {
                LogTool.d("init  onError : " + i);
            }

            @Override
            public void onException(Throwable throwable) {

            }
        }).check(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_force:
                init(UpdateConstants.CHECKURL_DEBUG);
                LogTool.d("强制更新");
                break;
            case R.id.btn_asui:
                init(UpdateConstants.CHECKURL_RELEASE);
                LogTool.d("默认UI更新");
                break;
            default:
                break;
        }
    }
}
