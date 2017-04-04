package com.ecarx.glautoupdatesdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ecarx.gl_autoupdatesdk.AutoUpdateSDKManager;
import com.ecarx.gl_autoupdatesdk.callback.GLCallback;
import com.ecarx.gl_autoupdatesdk.utils.LogTool;
import com.ecarx.gl_autoupdatesdk.utils.UpdateConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    /**
     * 一般自动更新放到MainActivity里面的
     */
    private void init() {
        AutoUpdateSDKManager.init(getApplicationContext(), UpdateConstants.CHECKURL_DEBUG, new GLCallback<Boolean>() {
            @Override
            public void onResult(Boolean aBoolean) {
                LogTool.d("init onResult : " +aBoolean);
            }

            @Override
            public void onError(int i) {
                LogTool.d("init  onError : " +i);
            }

            @Override
            public void onException(Throwable throwable) {

            }
        }).check(MainActivity.this);
    }
}
