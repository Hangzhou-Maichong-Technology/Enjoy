package com.mc.enjoy.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.hzmct.enjoy.R;
import com.mc.android.enjoy.EnjoyErrorCode;
import com.mc.android.mcwatchdog.McWatchdogConfig;
import com.mc.android.mcwatchdog.McWatchdogManager;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Woong on 2/6/21
 * @website http://woong.cn
 */
public class WatchDogActivity extends AppCompatActivity {
    private static final String TAG = "WatchDogActivity";

    private CheckBox cbWatchDog;
    private Button btnKick;
    private Button btnGetConfig;
    private Button btnSetConfig;
    private EditText etTimeout;

    private McWatchdogManager mcWatchdogManager;
    private ScheduledFuture kickFuture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_dog);

        cbWatchDog = findViewById(R.id.cb_watch_dog);
        btnKick = findViewById(R.id.btn_kick);
        btnGetConfig = findViewById(R.id.btn_get_config);
        btnSetConfig = findViewById(R.id.btn_set_config);
        etTimeout = findViewById(R.id.et_timeout);

        initData();
        initListener();
    }

    private void initData() {
        mcWatchdogManager = (McWatchdogManager) getSystemService(McWatchdogManager.MC_WATCHDOG_MANAGER);

        cbWatchDog.setChecked(mcWatchdogManager.isInited());
        if (mcWatchdogManager.isInited()) {
            loopKick();
            McWatchdogConfig config = mcWatchdogManager.getConfig();
            if (config != null) {
                etTimeout.setText(String.valueOf(config.getTimeout()));
            }
        }
    }

    private void initListener() {
        cbWatchDog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int ret = mcWatchdogManager.init();
                    parseError(ret);
                    if (mcWatchdogManager.isInited()) {
                        McWatchdogConfig config = mcWatchdogManager.getConfig();
                        if (config != null) {
                            etTimeout.setText(String.valueOf(config.getTimeout()));
                        }
                    }
                } else {
                    int ret = mcWatchdogManager.close();
                    parseError(ret);
                }
            }
        });

        btnKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loopKick();
            }
        });

        btnGetConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                McWatchdogConfig mcWatchdogConfig = mcWatchdogManager.getConfig();
                if (mcWatchdogConfig != null) {
                    etTimeout.setText(String.valueOf(mcWatchdogConfig.getTimeout()));
                } else {
                    ToastUtils.showShort("配置为空，请检查看门狗是否初始化");
                }
            }
        });

        btnSetConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                McWatchdogConfig mcWatchdogConfig = new McWatchdogConfig();

                try {
                    if (TextUtils.isEmpty(etTimeout.getText().toString().trim())) {
                        ToastUtils.showShort("看门狗超时时间不能为空");
                        return;
                    }

                    int timeout = Integer.parseInt(etTimeout.getText().toString().trim());
                    mcWatchdogConfig.setTimeout(timeout);
                } catch (Exception e) {
                    mcWatchdogConfig.setTimeout(8);
                }

                int ret = mcWatchdogManager.setConfig(mcWatchdogConfig);
                parseError(ret);
            }
        });
    }

    private void loopKick() {
        try {
            if (kickFuture != null) {
                kickFuture.cancel(true);
            }

            kickFuture = Executors.newScheduledThreadPool(1, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    return new Thread(runnable, "EthStateThread");
                }
            }).scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (mcWatchdogManager.isInited()) {
                        int ret = mcWatchdogManager.kick();
                        parseError(ret);
                    }
                }
            }, 1, 6, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "ethState looper error == " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mcWatchdogManager.close();

        if (kickFuture != null) {
            kickFuture.cancel(true);
            kickFuture = null;
        }
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("成功");
                break;
            case EnjoyErrorCode.ENJOY_WATCHDOG_MANAGER_ERROR_NOT_INIT:
                ToastUtils.showShort("开门狗未初始化");
                break;
            case EnjoyErrorCode.ENJOY_WATCHDOG_MANAGER_ERROR_INIT_AGAIN:
                ToastUtils.showShort("看门狗重复初始化");
                break;
            case EnjoyErrorCode.ENJOY_WATCHDOG_MANAGER_ERROR_INVALID_TIMEOUT:
                ToastUtils.showShort("无效的超时时间");
                break;
            case EnjoyErrorCode.ENJOY_WATCHDOG_MANAGER_ERROR_OPEN_DEV:
                ToastUtils.showShort("打开看门狗节点失败");
                break;
            case EnjoyErrorCode.ENJOY_WATCHDOG_MANAGER_ERROR_SET_TIMEOUT_ERROR:
                ToastUtils.showShort("配置看门狗超时时间失败");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_UNKNOWN:
            default:
                ToastUtils.showShort("未知错误");
                break;

        }
    }
}
