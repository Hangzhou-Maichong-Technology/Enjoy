package com.mc.enjoy.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.hzmct.enjoy.R;
import com.mc.android.enjoy.EnjoyErrorCode;
import com.mc.android.mcsecure.McSecureManager;

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

    private McSecureManager mcSecureManager;

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
        mcSecureManager = (McSecureManager) getSystemService(McSecureManager.MC_SECURE_MANAGER);

        cbPwd.setChecked(mcSecureManager.checkSafeProgramOfSelf());
    }

    private void initListener() {
        btnPwdState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = mcSecureManager.getSecurePasswdStatus();

                switch (state) {
                    case McSecureManager.MC_SECURE_PASSWD_EMPTY:
                        ToastUtils.showShort("密码为空");
                        break;
                    case McSecureManager.MC_SECURE_PASSWD_EXISTED:
                        ToastUtils.showShort("密码已设置");
                        break;
                    case McSecureManager.MC_SECURE_PASSWD_UNKNOWN:
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

                int ret = mcSecureManager.setSecurePasswd(etPwdOld.getText().toString().trim(),
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

                int ret = mcSecureManager.resetSecurePasswd(etPwdOld.getText().toString().trim());
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

                int ret = mcSecureManager.registSafeProgram(etPwdOld.getText().toString().trim());
                parseError(ret);
            }
        });

        btnPwdUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ret = mcSecureManager.unregistSafeProgram();
                parseError(ret);
                cbPwd.setChecked(mcSecureManager.checkSafeProgramOfSelf());
            }
        });
    }


    private void parseError(int ret){
        switch (ret){
            case EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_ALREADY_EMPYT:
                Log.e(TAG, "errorDump: 密码已经为空");
                break;
            case EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_CHECK_FAILED:
                Log.e(TAG, "errorDump: 密码检查不通过");
                break;
            case EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_FORMAT_ERROR:
                Log.e(TAG, "errorDump: 密码格式错误");
                break;
            case EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_SET_FAILED:
                Log.e(TAG, "errorDump: 密码设置失败");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                Log.e(TAG, "errorDump: 服务错误");
                break;
            case EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL:
                Log.e(TAG, "errorDump: 成功");
                break;
            case EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PROGRAM_ALREADY_IN_SAFE_PROGRAM_LIST:
                Log.e(TAG, "errorDump: 已经在安全应用列表内");
                break;
            case EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PROGRAM_NOT_IN_SAFE_PROGRAM_LIST:
                Log.e(TAG, "errorDump: 不在安全应用列表内");
                break;
            case EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_REGISTER_SAFE_PROGRAM_FAILED:
                Log.e(TAG, "errorDump: 注册安全应用失败");
                break;
            case EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_UNREGISTER_SAFE_PROGRAM_FAILED:
                Log.e(TAG, "errorDump: 注销安全应用失败");
                break;
            case EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_NOT_INIT:
                Log.e(TAG, "errorDump: 鉴权密码未初始化");
                break;
            default:
                Log.e(TAG, "errorDump: 没有返回值解析");
                break;
        }
    }
}
