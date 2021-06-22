package com.mc.enjoy.module;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.blankj.utilcode.util.ToastUtils;
import com.mc.enjoy.R;
import com.mc.enjoysdk.McSecure;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.transform.McSecurePasswordState;

/**
 * @author Woong on 1/30/21
 * @website http://woong.cn
 */
public class SecureActivity extends AppCompatActivity {
    private static final String TAG = "SecureActivity";

    private Button btnPwdState;
    private Button btnPwdSet;
    private Button btnPwdReset;
    private Button btnPwdRegister;
    private Button btnPwdUnregister;
    private CheckBox cbPwd;
    private EditText etPwdNew;
    private EditText etPwdOld;

    private McSecure mcSecure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure);

        btnPwdState = findViewById(R.id.btn_pwd_state);
        btnPwdSet = findViewById(R.id.btn_pwd_set);
        btnPwdReset = findViewById(R.id.btn_pwd_reset);
        btnPwdRegister = findViewById(R.id.btn_pwd_register);
        btnPwdUnregister = findViewById(R.id.btn_pwd_unregister);
        cbPwd = findViewById(R.id.cb_pwd);
        etPwdNew = findViewById(R.id.et_pwd_new);
        etPwdOld = findViewById(R.id.et_pwd_old);

        initData();
        initListener();
    }

    private void initData() {
        mcSecure = McSecure.getInstance(this);

        cbPwd.setChecked(mcSecure.checkSafeProgramOfSelf() == McResultBool.TRUE);
    }

    private void initListener() {
        btnPwdState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = mcSecure.getSecurePasswdStatus();

                switch (state) {
                    case McSecurePasswordState.MC_SECURE_PASSWD_EMPTY:
                        ToastUtils.showShort("密码为空");
                        break;
                    case McSecurePasswordState.MC_SECURE_PASSWD_EXISTED:
                        ToastUtils.showShort("密码已设置");
                        break;
                    case McSecurePasswordState.MC_SECURE_PASSWD_UNKNOWN:
                        ToastUtils.showShort("未知状态");
                        break;
                }
            }
        });

        btnPwdSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPwdOld.getText().toString().trim())) {
                    ToastUtils.showShort("旧密码不能为空");
                    return;
                }

                if (TextUtils.isEmpty(etPwdNew.getText().toString().trim())) {
                    ToastUtils.showShort("新密码不能为空");
                    return;
                }

                int ret = mcSecure.setSecurePasswd(etPwdOld.getText().toString().trim(),
                        etPwdNew.getText().toString().trim());
                parseError(ret);
            }
        });

        btnPwdReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPwdOld.getText().toString().trim())) {
                    ToastUtils.showShort("旧密码不能为空");
                    return;
                }

                int ret = mcSecure.resetSecurePasswd(etPwdOld.getText().toString().trim());
                parseError(ret);
            }
        });

        btnPwdRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPwdOld.getText().toString().trim())) {
                    ToastUtils.showShort("旧密码不能为空");
                    return;
                }

                int ret = mcSecure.registSafeProgram(etPwdOld.getText().toString().trim());
                parseError(ret);
                cbPwd.setChecked(mcSecure.checkSafeProgramOfSelf() == McResultBool.TRUE);
            }
        });

        btnPwdUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SecureActivity.this)
                        .setMessage("注销权限密码会导致其他功能崩溃，是否继续注销？")
                        .setPositiveButton("注销", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int ret = mcSecure.unregistSafeProgram();
                                parseError(ret);
                                cbPwd.setChecked(mcSecure.checkSafeProgramOfSelf() == McResultBool.TRUE);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private void parseError(int ret){
        switch (ret){
            case McErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_ALREADY_EMPYT:
                Log.e(TAG, "errorDump: 密码已经为空");
                ToastUtils.showShort("密码已经为空");
                break;
            case McErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_CHECK_FAILED:
                Log.e(TAG, "errorDump: 密码检查不通过");
                ToastUtils.showShort("密码检查不通过");
                break;
            case McErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_FORMAT_ERROR:
                Log.e(TAG, "errorDump: 密码格式错误");
                ToastUtils.showShort("密码格式错误");
                break;
            case McErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_SET_FAILED:
                Log.e(TAG, "errorDump: 密码设置失败");
                ToastUtils.showShort("密码设置失败");
                break;
            case McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                Log.e(TAG, "errorDump: 服务错误");
                ToastUtils.showShort("服务错误");
                break;
            case McErrorCode.ENJOY_COMMON_SUCCESSFUL:
                Log.e(TAG, "errorDump: 成功");
                ToastUtils.showShort("成功");
                break;
            case McErrorCode.ENJOY_SECURE_MANAGER_ERROR_PROGRAM_ALREADY_IN_SAFE_PROGRAM_LIST:
                Log.e(TAG, "errorDump: 已经在安全应用列表内");
                ToastUtils.showShort("已经在安全应用列表内");
                break;
            case McErrorCode.ENJOY_SECURE_MANAGER_ERROR_PROGRAM_NOT_IN_SAFE_PROGRAM_LIST:
                Log.e(TAG, "errorDump: 不在安全应用列表内");
                ToastUtils.showShort("不在安全应用列表内");
                break;
            case McErrorCode.ENJOY_SECURE_MANAGER_ERROR_REGISTER_SAFE_PROGRAM_FAILED:
                Log.e(TAG, "errorDump: 注册安全应用失败");
                ToastUtils.showShort("注册安全应用失败");
                break;
            case McErrorCode.ENJOY_SECURE_MANAGER_ERROR_UNREGISTER_SAFE_PROGRAM_FAILED:
                Log.e(TAG, "errorDump: 注销安全应用失败");
                ToastUtils.showShort("注销安全应用失败");
                break;
            case McErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_NOT_INIT:
                Log.e(TAG, "errorDump: 鉴权密码未初始化");
                ToastUtils.showShort("鉴权密码未初始化");
                break;
            default:
                Log.e(TAG, "errorDump: 没有返回值解析");
                ToastUtils.showShort("没有返回值解析");
                break;
        }
    }
}
