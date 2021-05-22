package com.mc.enjoy.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.hzmct.enjoy.R;
import com.mc.android.mchardwarestatusmanager.McCpuStatus;
import com.mc.android.mchardwarestatusmanager.McHardwareStatusManager;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Woong on 3/3/21
 * @website http://woong.cn
 */
public class HardwareStatusActivity extends AppCompatActivity {
    private static final String TAG = "HardwareStatusActivity";

    private TextView tvUpTime;
    private TextView tvCpuTemperature;
    private TextView tvCpuUtilization;

    private McHardwareStatusManager mcHardwareStatusManager;
    private ScheduledFuture hardwareStatusFuture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_status);

        tvUpTime = findViewById(R.id.tv_up_time);
        tvCpuTemperature = findViewById(R.id.tv_cpu_temperature);
        tvCpuUtilization = findViewById(R.id.tv_cpu_utilization);

        mcHardwareStatusManager = (McHardwareStatusManager) getSystemService(McHardwareStatusManager.MC_HARDWARESTATUSMANAGER_SERVICE);

        loopHardwareState();
    }

    private void loopHardwareState() {
        try {
            if (hardwareStatusFuture != null) {
                hardwareStatusFuture.cancel(true);
            }

            hardwareStatusFuture = Executors.newScheduledThreadPool(1, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    return new Thread(runnable, "HardwareStatusThread");
                }
            }).scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            McCpuStatus mcCpuStatus = mcHardwareStatusManager.getCPUStatus();
                            long upTime = mcHardwareStatusManager.getUpTime();

                            tvUpTime.setText(String.format(getResources().getString(R.string.up_time),
                                    upTime, formatTime(upTime)));
                            tvCpuTemperature.setText(String.format(getResources().getString(R.string.cpu_temperature),
                                    mcCpuStatus.getTemperature()));
                            tvCpuUtilization.setText(String.format(getResources().getString(R.string.cpu_utilization),
                                    mcCpuStatus.getUtilization() * 100));
                        }
                    });
                }
            }, 1, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "hardware status looper error == " + e.getMessage());
        }
    }

    private String formatTime(long upTime) {
        long millisecond = upTime % 1000;
        long second = upTime / 1000;
        long minute = 0;
        long hour = 0;

        if (second >= 60) {
            minute = second / 60;
        }

        if (minute >= 60) {
            hour = minute / 60;
        }

        return hour + " 小时 " + minute % 60 + " 分 " + second % 60 + " 秒 " + millisecond + " 毫秒";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hardwareStatusFuture != null) {
            hardwareStatusFuture.cancel(true);
            hardwareStatusFuture = null;
        }
    }
}
