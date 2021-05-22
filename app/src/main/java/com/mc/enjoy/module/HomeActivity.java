package com.mc.enjoy.module;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.hzmct.enjoy.R;
import com.mc.android.enjoy.EnjoyErrorCode;
import com.mc.android.mchomemanager.McHomeManager;
import com.mc.android.mcpower.McPowerManager;

/**
 * @author Woong on 3/3/21
 * @website http://woong.cn
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private Button btnRawLauncher;
    private Button btnGetLauncher;
    private Button btnSetLauncher;
    private TextView tvLauncher;
    private EditText etLauncher;

    private McHomeManager mcHomeManager;
    private McPowerManager mcPowerManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnRawLauncher = findViewById(R.id.btn_raw_launcher);
        btnGetLauncher = findViewById(R.id.btn_get_launcher);
        btnSetLauncher = findViewById(R.id.btn_set_launcher);
        tvLauncher = findViewById(R.id.tv_launcher);
        etLauncher = findViewById(R.id.et_launcher);

        mcHomeManager = (McHomeManager) getSystemService(McHomeManager.MC_HOMEMANAGER_SERVICE);
        mcPowerManager = (McPowerManager) getSystemService(McPowerManager.MC_POWER_MANAGER);

        tvLauncher.setText(mcHomeManager.getHomePackage());

        initListener();
    }

    private void initListener() {
        btnRawLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ret = mcHomeManager.startRawLauncher();
                parseError(ret);
            }
        });

        btnGetLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLauncher.setText(mcHomeManager.getHomePackage());
            }
        });

        btnSetLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etLauncher.getText().toString().trim())) {
                    ToastUtils.showShort("桌面应用包名不能为空");
                    return;
                }

                int ret = mcHomeManager.setHomePackage(etLauncher.getText().toString().trim());
                parseError(ret);

                if (ret == EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL) {
                    new AlertDialog.Builder(HomeActivity.this)
                            .setTitle("是否重启查看桌面应用？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("重启", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mcPowerManager.reboot();
                                }
                            })
                            .show();
                }
            }
        });
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("成功");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                ToastUtils.showShort("服务为启动");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR:
                ToastUtils.showShort("写入 Setting 数据库错误");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_UNKNOWN:
            default:
                ToastUtils.showShort("未知错误");
                break;
        }
    }
}
