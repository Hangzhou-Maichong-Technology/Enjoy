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
import com.mc.enjoy.R;
import com.mc.enjoysdk.McHome;
import com.mc.enjoysdk.McPower;
import com.mc.enjoysdk.transform.McErrorCode;

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

    private McHome mcHome;
    private McPower mcPower;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnRawLauncher = findViewById(R.id.btn_raw_launcher);
        btnGetLauncher = findViewById(R.id.btn_get_launcher);
        btnSetLauncher = findViewById(R.id.btn_set_launcher);
        tvLauncher = findViewById(R.id.tv_launcher);
        etLauncher = findViewById(R.id.et_launcher);

        mcHome = McHome.getInstance(this);
        mcPower = McPower.getInstance(this);

        tvLauncher.setText(mcHome.getHomePackage());

        initListener();
    }

    private void initListener() {
        btnRawLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ret = mcHome.startRawLauncher();
                parseError(ret);
            }
        });

        btnGetLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLauncher.setText(mcHome.getHomePackage());
            }
        });

        btnSetLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etLauncher.getText().toString().trim())) {
                    ToastUtils.showShort("??????????????????????????????");
                    return;
                }

                int ret = mcHome.setHomePackage(etLauncher.getText().toString().trim());
                parseError(ret);

                if (ret == McErrorCode.ENJOY_COMMON_SUCCESSFUL) {
                    new AlertDialog.Builder(HomeActivity.this)
                            .setTitle("?????????????????????????????????")
                            .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mcPower.reboot();
                                }
                            })
                            .show();
                }
            }
        });
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case McErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("??????");
                break;
            case McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                ToastUtils.showShort("???????????????");
                break;
            case McErrorCode.ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR:
                ToastUtils.showShort("?????? Setting ???????????????");
                break;
            case McErrorCode.ENJOY_COMMON_ERROR_UNKNOWN:
            default:
                ToastUtils.showShort("????????????");
                break;
        }
    }
}
