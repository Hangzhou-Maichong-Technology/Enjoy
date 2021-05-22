package com.mc.enjoy.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzmct.enjoy.R;
import com.mc.android.enjoy.EnjoyErrorCode;
import com.mc.android.mctime.McTimeManager;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

/**
 * @author Woong on 1/27/21
 * @website http://woong.cn
 */
public class TimeActivity extends AppCompatActivity {
    private static final String TAG = "TimeActivity";

    private CheckBox cbAutoDateTime;
    private CheckBox cbAutoTimeZone;
    private CheckBox cbTimeFormat;
    private Button btnTime;
    private Button btnDate;
    private Spinner spinnerTimeZone;
    private Button btnNtp;
    private EditText etNtpAddress;
    private EditText etNtpTimeOut;
    private TimePickerView timePickerView;
    private TimePickerView datePickerView;

    private McTimeManager mcTimeManager;
    private String[] timezones;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        cbAutoDateTime = findViewById(R.id.cb_auto_date_time);
        cbAutoTimeZone = findViewById(R.id.cb_auto_timezone);
        cbTimeFormat = findViewById(R.id.cb_time_format);
        btnTime = findViewById(R.id.btn_time);
        btnDate = findViewById(R.id.btn_date);
        spinnerTimeZone = findViewById(R.id.spinner_timezone);
        btnNtp = findViewById(R.id.btn_ntp);
        etNtpAddress = findViewById(R.id.et_ndp_address);
        etNtpTimeOut = findViewById(R.id.et_ndp_timeout);

        initData();
        initListener();
    }

    private void initData() {
        timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (cbAutoDateTime.isChecked()) {
                    ToastUtils.showShort("已开启自动校时，无法手动配置时间");
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                int millisecond = calendar.get(Calendar.MILLISECOND);

                Log.i(TAG, "hour == " + hour + ", minute == " + minute +
                        ", second == " + second + ", millisecond == " + millisecond);
                int ret = mcTimeManager.setTime(hour, minute, second, millisecond);
                parseError(ret);
            }
        })
                .setType(new boolean[]{false, false, false, true, true, true})
                .build();

        datePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (cbAutoDateTime.isChecked()) {
                    ToastUtils.showShort("已开启自动校时，无法手动配置日期");
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Log.i(TAG, "year == " + year + ", month == " + month + ", day == " + day);
                int ret = mcTimeManager.setDate(year, month, day);
                parseError(ret);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();

        mcTimeManager = (McTimeManager) getSystemService(McTimeManager.MC_TIME_MANAGER);
        cbAutoDateTime.setChecked(mcTimeManager.isAutoDateAndTime());
        cbAutoTimeZone.setChecked(mcTimeManager.isAutoTimeZone());
        cbTimeFormat.setChecked(mcTimeManager.getCurrentTimeFormat() == McTimeManager.HOUR_24);
        etNtpAddress.setText(mcTimeManager.getNtpServerAddressInUse());
        etNtpTimeOut.setText(String.valueOf(mcTimeManager.getNtpTimeout()));

        timezones = getResources().getStringArray(R.array.time_zone);
    }

    private void initListener() {
        cbAutoDateTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int ret = mcTimeManager.switchAutoDateAndTime(isChecked);
                parseError(ret);
            }
        });

        cbAutoTimeZone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int ret = mcTimeManager.switchAutoTimeZone(isChecked);
                parseError(ret);
            }
        });

        cbTimeFormat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int ret = mcTimeManager.setTimeFormat(isChecked ? McTimeManager.HOUR_24 : McTimeManager.HOUR_12);
                parseError(ret);
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerView.show();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerView.show();
            }
        });

        spinnerTimeZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int ret = mcTimeManager.setTimeZone(timezones[position]);
                parseError(ret);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnNtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!NetworkUtils.isAvailable()) {
                            ToastUtils.showShort("请检查网络是否连接");
                            return;
                        }

                        String address = etNtpAddress.getText().toString().trim();
                        long timeOut = Long.parseLong(etNtpTimeOut.getText().toString().trim());
                        if (!mcTimeManager.checkNtpServerAddressAvailable(address, timeOut)) {
                            ToastUtils.showShort("NTP 配置参数错误");
                            return;
                        }

                        int ret = mcTimeManager.setNtpServerAddress(address, timeOut);
                        parseError(ret);
                    }
                });
            }
        });
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("成功");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                ToastUtils.showShort("服务错误");
                break;
            case EnjoyErrorCode.ENJOY_TIME_MANAGER_ERROR_PARAMETER_ERROR:
                ToastUtils.showShort("配置参数错误");
                break;
            case EnjoyErrorCode.ENJOY_TIME_MANAGER_ERROR_FUNCTION_OCCUPY:
                ToastUtils.showShort("手动自动配置冲突");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_UNKNOWN:
                ToastUtils.showShort("NTP 未知错误");
                break;
            case EnjoyErrorCode.ENJOY_TIME_MANAGER_ERROR_NTP_SERVER_ADDRESS_SET_FAILED:
                ToastUtils.showShort("NTP 服务地址设置错误");
                break;
            case EnjoyErrorCode.ENJOY_TIME_MANAGER_ERROR_NTP_TIMEOUT_SET_FAILED:
                ToastUtils.showShort("NTP 超时时间设置错误");
                break;
            case EnjoyErrorCode.ENJOY_TIME_MANAGER_ERROR_NTP_CONFIG_SET_FAILED:
                ToastUtils.showShort("NTP 设置错误");
                break;
            default:
                ToastUtils.showShort("未知错误");
                break;
        }
    }
}
