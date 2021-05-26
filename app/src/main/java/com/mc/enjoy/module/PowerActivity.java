package com.mc.enjoy.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.blankj.utilcode.util.ToastUtils;
import com.mc.enjoy.R;
import com.mc.enjoysdk.McPower;
import com.mc.enjoysdk.transform.McErrorCode;

/**
 * @author Woong on 1/27/21
 * @website http://woong.cn
 */
public class PowerActivity extends AppCompatActivity {
    private static final String TAG = "PowerActivity";

    private Button btnReboot;
    private Button btnDialogShutdown;
    private Button btnShutdown;

    private McPower mcPower;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);

        btnReboot = findViewById(R.id.btn_reboot);
        btnDialogShutdown = findViewById(R.id.btn_dialog_shutdown);
        btnShutdown = findViewById(R.id.btn_shutdown);

        mcPower = McPower.getInstance(this);

        initListener();
    }

    private void initListener() {
        btnReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ret = mcPower.reboot();
                parseError(ret);
            }
        });

        btnDialogShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ret = mcPower.shutdown(true);
                parseError(ret);
            }
        });

        btnShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ret = mcPower.shutdown(false);
                parseError(ret);
            }
        });
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case McErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("成功");
                break;
            case McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                ToastUtils.showShort("电源服务错误");
                break;
            default:
                ToastUtils.showShort("未知错误");
                break;
        }
    }
}
