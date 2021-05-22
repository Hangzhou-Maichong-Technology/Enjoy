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

import com.blankj.utilcode.util.ToastUtils;
import com.hzmct.enjoy.R;
import com.mc.android.enjoy.EnjoyErrorCode;
import com.mc.android.mcbootanimation.McBootanimationManager;
import com.mc.android.mcpower.McPowerManager;

/**
 * @author Woong on 3/3/21
 * @website http://woong.cn
 */
public class BootAnimationActivity extends AppCompatActivity {
    private static final String TAG = "BootAnimationActivity";

    private Button btnSet;
    private Button btnReset;
    private EditText etPath;

    private McBootanimationManager mcBootanimationManager;
    private McPowerManager mcPowerManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_animation);

        btnSet = findViewById(R.id.btn_set);
        btnReset = findViewById(R.id.btn_reset);
        etPath = findViewById(R.id.et_path);

        mcBootanimationManager = (McBootanimationManager) getSystemService(McBootanimationManager.MC_BOOTANIMATION_MANAGER);
        mcPowerManager = (McPowerManager) getSystemService(McPowerManager.MC_POWER_MANAGER);

        initListener();
    }

    private void initListener() {
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPath.getText().toString().trim())) {
                    ToastUtils.showShort("开机动画包存放地址不能为空");
                    return;
                }

                int ret = mcBootanimationManager.setBootanimation(etPath.getText().toString().trim());
                parseError(ret);

                if (ret == EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL) {
                    new AlertDialog.Builder(BootAnimationActivity.this)
                            .setTitle("是否重启查看开机动画？")
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

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ret = mcBootanimationManager.resetBootanimation();
                parseError(ret);

                if (ret == EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL) {
                    new AlertDialog.Builder(BootAnimationActivity.this)
                            .setTitle("是否重启查看开机动画？")
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
                ToastUtils.showShort("配置开机动画成功");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                ToastUtils.showShort("开机动画服务未启动");
                break;
            case EnjoyErrorCode.ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_NOT_EXIST:
                ToastUtils.showShort("指定路径下找不到开机动画包");
                break;
            case EnjoyErrorCode.ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_CHECK_FAILED:
                ToastUtils.showShort("开机动画包不符合规范");
                break;
            case EnjoyErrorCode.ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_COPY_FAILED:
                ToastUtils.showShort("开机动画包拷贝失败");
                break;
            case EnjoyErrorCode.ENJOY_BOOTANIMATION_MANAGER_ERROR_PARAMETER_WRONG:
                ToastUtils.showShort("开机动画包路径错误");
                break;
            default:
                ToastUtils.showShort("未知错误");
                break;
        }

    }
}
