package com.mc.enjoy.module;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzmct.enjoy.R;
import com.mc.android.enjoy.EnjoyErrorCode;
import com.mc.android.mcpower.McPowerManager;
import com.mc.android.mcsystemui.McSystemUiManager;

/**
 * @author Woong on 1/27/21
 * @website http://woong.cn
 */
public class SystemUiActivity extends AppCompatActivity {
    private static final String TAG = "BarActivity";

    private CheckBox cbSync;
    private CheckBox cbStatus;
    private CheckBox cbNavigation;
    private Button btnStatusExpand;
    private Button btnStatusNotificationIcon;
    private Button btnStatusNotificationAlert;
    private Button btnStatusInfo;
    private Button btnStatusTime;
    private Button btnNavigationHome;
    private Button btnNavigationRecent;
    private Button btnNavigationBack;
    private Button btnEnableAll;

    private McSystemUiManager mcSystemUiManager;
    private McPowerManager mcPowerManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_ui);

        cbSync = findViewById(R.id.cb_sync);
        cbStatus = findViewById(R.id.cb_status);
        cbNavigation = findViewById(R.id.cb_navigation);
        btnStatusExpand = findViewById(R.id.btn_status_expand);
        btnStatusNotificationIcon = findViewById(R.id.btn_status_notification_icon);
        btnStatusNotificationAlert = findViewById(R.id.btn_status_notification_alert);
        btnStatusInfo = findViewById(R.id.btn_status_info);
        btnStatusTime = findViewById(R.id.btn_status_time);
        btnNavigationHome = findViewById(R.id.btn_navigation_home);
        btnNavigationRecent = findViewById(R.id.btn_navigation_recent);
        btnNavigationBack = findViewById(R.id.btn_navigation_back);
        btnEnableAll = findViewById(R.id.btn_enable_all);

        mcSystemUiManager = (McSystemUiManager) getSystemService(McSystemUiManager.MC_SYSTEM_UI_MANAGER);
        mcPowerManager = (McPowerManager) getSystemService(McPowerManager.MC_POWER_MANAGER);

        initData();
        initListener();
    }

    private void initData() {
        cbStatus.setChecked(mcSystemUiManager.getStatusBarShowStatus() == 1);
        cbNavigation.setChecked(mcSystemUiManager.getNavigationShowStatus() == 1);
        LogUtils.i(TAG, "bar status == " + mcSystemUiManager.getStatusBarShowStatus() + ", navigation status == " + mcSystemUiManager.getNavigationShowStatus());
    }

    private void initListener() {
        cbSync.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int ret = mcSystemUiManager.switchStatusBarAndNavigationOverwrite(isChecked);
                parseError(ret);
                new AlertDialog.Builder(SystemUiActivity.this)
                        .setTitle("是否立即重启")
                        .setPositiveButton("重启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mcPowerManager.reboot();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create()
                        .show();
            }
        });

        cbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int ret = mcSystemUiManager.temporarilySwitchStatusBar(isChecked);
                parseError(ret);
            }
        });

        cbNavigation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int ret = mcSystemUiManager.temporarilySwitchNavigation(isChecked);
                parseError(ret);
            }
        });

        btnStatusExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbStatus.isChecked()) {
                    ToastUtils.showShort("状态栏已隐藏，无法配置该项目");
                    return;
                }

                int ret = mcSystemUiManager.disableStatusBarItem(McSystemUiManager.DISABLE_EXPAND);
                parseError(ret);
            }
        });

        btnStatusNotificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbStatus.isChecked()) {
                    ToastUtils.showShort("状态栏已隐藏，无法配置该项目");
                    return;
                }

                int ret = mcSystemUiManager.disableStatusBarItem(McSystemUiManager.DISABLE_NOTIFICATION_ICONS);
                parseError(ret);
            }
        });

        btnStatusNotificationAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbStatus.isChecked()) {
                    ToastUtils.showShort("状态栏已隐藏，无法配置该项目");
                    return;
                }

                int ret = mcSystemUiManager.disableStatusBarItem(McSystemUiManager.DISABLE_NOTIFICATION_ALERTS);
                parseError(ret);
            }
        });

        btnStatusInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbStatus.isChecked()) {
                    ToastUtils.showShort("状态栏已隐藏，无法配置该项目");
                    return;
                }

                int ret = mcSystemUiManager.disableStatusBarItem(McSystemUiManager.DISABLE_SYSTEM_INFO);
                parseError(ret);
            }
        });

        btnStatusTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbStatus.isChecked()) {
                    ToastUtils.showShort("状态栏已隐藏，无法配置该项目");
                    return;
                }

                int ret = mcSystemUiManager.disableStatusBarItem(McSystemUiManager.DISABLE_CLOCK);
                parseError(ret);
            }
        });

        btnNavigationHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbNavigation.isChecked()) {
                    ToastUtils.showShort("导航栏已隐藏，无法配置该项目");
                    return;
                }

                int ret = mcSystemUiManager.disableStatusBarItem(McSystemUiManager.DISABLE_HOME);
                parseError(ret);
            }
        });

        btnNavigationRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbNavigation.isChecked()) {
                    ToastUtils.showShort("导航栏已隐藏，无法配置该项目");
                    return;
                }

                int ret = mcSystemUiManager.disableStatusBarItem(McSystemUiManager.DISABLE_RECENT);
                parseError(ret);
            }
        });

        btnNavigationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbNavigation.isChecked()) {
                    ToastUtils.showShort("导航栏已隐藏，无法配置该项目");
                    return;
                }

                int ret = mcSystemUiManager.disableStatusBarItem(McSystemUiManager.DISABLE_BACK);
                parseError(ret);
            }
        });

        btnEnableAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ret = mcSystemUiManager.disableStatusBarItem(McSystemUiManager.DISABLE_NONE);
                parseError(ret);
            }
        });
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("成功");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_SDK_NOT_SUPPORT:
                ToastUtils.showShort("服务错误");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR:
                ToastUtils.showShort("设置错误");
                break;
            default:
                ToastUtils.showShort("未知错误");
                break;
        }
    }
}
