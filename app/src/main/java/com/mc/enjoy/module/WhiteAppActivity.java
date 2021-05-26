package com.mc.enjoy.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mc.android.mcinstall.McWhiteApp;
import com.mc.enjoy.R;
import com.mc.enjoysdk.McInstall;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;

import java.util.ArrayList;

public class WhiteAppActivity extends AppCompatActivity {
    private static final String TAG = "WhiteAppActivity";

    private CheckBox cbEnable;
    private EditText etPackageName;
    private RadioButton rbForeground;
    private RadioButton rbBackground;
    private CheckBox cbAllowUnistall;
    private CheckBox cbOpenAfterInstall;
    private Button btnAdd;
    private Button btnRemove;
    private Button btnWhiteList;
    private TextView tvWhiteList;

    private McInstall mcInstall;
    private ArrayList<McWhiteApp> mcWhiteApps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_app);

        cbEnable = findViewById(R.id.cb_enable);
        etPackageName = findViewById(R.id.et_package_name);
        rbForeground = findViewById(R.id.rb_install_foreground);
        rbBackground = findViewById(R.id.rb_install_background);
        cbAllowUnistall = findViewById(R.id.cb_allow_uninstall);
        cbOpenAfterInstall = findViewById(R.id.cb_open_after_install);
        btnAdd = findViewById(R.id.btn_add);
        btnRemove = findViewById(R.id.btn_remove);
        btnWhiteList = findViewById(R.id.btn_white_list);
        tvWhiteList = findViewById(R.id.tv_white_list);

        tvWhiteList.setMovementMethod(ScrollingMovementMethod.getInstance());

        initData();
        initListener();
    }

    private void initData() {
        mcInstall = McInstall.getInstance(this);
        Log.i(TAG, "whiteApps isEnable == " + mcInstall.isEnable());
        cbEnable.setChecked(mcInstall.isEnable() == McResultBool.TRUE);

        LogUtils.i(TAG, "this is allowInstall == " + mcInstall.isInstallAllow(AppUtils.getAppPackageName())
                + ", isAllowUninstall == " + mcInstall.isUninstallAllow(AppUtils.getAppPackageName())
                + ", isOpenAfterInstall == " + mcInstall.isOpenAfterInstall(AppUtils.getAppPackageName())
                + ", installMode == " + mcInstall.getInstallMode(AppUtils.getAppPackageName()));

        getWhiteApps();
    }

    private void initListener() {
        cbEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int ret = mcInstall.whiteListSwitch(isChecked);
                parseError(ret);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPackageName.getText().toString().trim())) {
                    ToastUtils.showShort("白名单应用包名不能为空");
                    return;
                }

                if (!rbForeground.isChecked() && !rbBackground.isChecked()) {
                    ToastUtils.showShort("白名单应用安装模式不能为空");
                    return;
                }

                ArrayList<McWhiteApp> mcWhiteApps = new ArrayList<>();
                McWhiteApp app = new McWhiteApp();
                app.setPackageName(etPackageName.getText().toString().trim());
                if (rbForeground.isChecked()) {
                    app.setInstallMode(McWhiteApp.INSTALL_MODE_FOREGROUND);
                } else if (rbBackground.isChecked()) {
                    app.setInstallMode(McWhiteApp.INSTALL_MODE_BACKGROUND);
                }
                app.setAllowUninstall(cbAllowUnistall.isChecked());
                app.setOpenAfterInstall(cbOpenAfterInstall.isChecked());
                mcWhiteApps.add(app);

                int ret = mcInstall.addWhiteList(mcWhiteApps);
                parseError(ret);
                getWhiteApps();
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPackageName.getText().toString().trim())) {
                    ToastUtils.showShort("白名单应用包名不能为空");
                    return;
                }

                ArrayList<McWhiteApp> mcWhiteApps = new ArrayList<>();
                McWhiteApp app = new McWhiteApp();
                app.setPackageName(etPackageName.getText().toString().trim());
                mcWhiteApps.add(app);

                int ret = mcInstall.removeWhiteList(mcWhiteApps);
                parseError(ret);
                getWhiteApps();
            }
        });

        btnWhiteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWhiteApps();
            }
        });
    }

    private void getWhiteApps() {
        mcWhiteApps.clear();
        mcWhiteApps.addAll(mcInstall.getWhiteList());

        StringBuilder s = new StringBuilder();
        for (McWhiteApp app : mcWhiteApps) {
            s.append("包名: " + app.getPackageName() + "; ");
            s.append("安装方式: " + (app.getInstallMode() == 0 ? "前台安装; " : "后台安装; "));
            s.append("允许卸载: " + (app.isAllowUninstall() ? "是; " : "否; "));
            s.append("安装完自启动: " + (app.isOpenAfterInstall() ? "是; " : "否; "));
            s.append("\n");
        }

        tvWhiteList.setText(s.toString());
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case McErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("成功");
                break;
            case McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                ToastUtils.showShort("应用白名单服务未启动");
                break;
            case McErrorCode.ENJOY_INSTALL_MANAGER_ERROR_WHITE_ADD:
                ToastUtils.showShort("添加白名单失败");
                break;
            case McErrorCode.ENJOY_INSTALL_MANAGER_ERROR_WHITE_REMOVE:
                ToastUtils.showShort("从白名单中移除失败");
                break;
            default:
                ToastUtils.showShort("未知错误");
                break;
        }
    }
}