package com.mc.enjoy.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.blankj.utilcode.util.ToastUtils;
import com.hzmct.enjoy.R;
import com.mc.android.enjoy.EnjoyErrorCode;
import com.mc.android.mchardwarekeyboard.McHardwareKeyboardManager;

/**
 * @author Woong on 3/3/21
 * @website http://woong.cn
 */
public class HardwareKeyBoardActivity extends AppCompatActivity {
    private static final String TAG = "HardwareKeyBoardActivity";

    private CheckBox cbHardwareKeyboard;

    private McHardwareKeyboardManager mcHardwareKeyboardManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware_keyboard);

        cbHardwareKeyboard = findViewById(R.id.cb_keyboard_enable);

        mcHardwareKeyboardManager = (McHardwareKeyboardManager) getSystemService(
                McHardwareKeyboardManager.MC_HARDWAREKEYBOARD_MANAGER);

        cbHardwareKeyboard.setChecked(mcHardwareKeyboardManager.isHardwareBoardCompatible());

        cbHardwareKeyboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int ret = mcHardwareKeyboardManager.compatibleHardwareKeyBoard(isChecked);
                parseError(ret);
            }
        });
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("成功");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                ToastUtils.showShort("服务未启动或者启动失败");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR:
                ToastUtils.showShort("写入 Settings 数据库出错");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_UNKNOWN:
            default:
                ToastUtils.showShort("未知错误");
                break;
        }

    }
}
