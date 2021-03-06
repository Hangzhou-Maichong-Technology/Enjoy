package com.mc.enjoy.module;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import com.blankj.utilcode.util.ToastUtils;
import com.mc.enjoy.R;
import com.mc.enjoysdk.McPower;
import com.mc.enjoysdk.McRotation;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;

public class RotationActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button btnMain;
    private Button btnVice;
    private Button btnDisplay;
    private Spinner spinnerMain;
    private Spinner spinnerVice;
    private Spinner spinnerDisplay;
    private CheckBox cbViceFull;
    private McRotation mcRotation;
    private McPower mcPower;
    private int curMainPos;
    private int curVicePos;
    private int curDisplayPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);

        btnMain = findViewById(R.id.btn_main);
        btnVice = findViewById(R.id.btn_vice);
        btnDisplay = findViewById(R.id.btn_display);
        spinnerMain = findViewById(R.id.spinner_main);
        spinnerVice = findViewById(R.id.spinner_vice);
        spinnerDisplay = findViewById(R.id.spinner_display);
        cbViceFull = findViewById(R.id.cb_vice_full);

        mcRotation = McRotation.getInstance(this);
        mcPower = McPower.getInstance(this);
        cbViceFull.setChecked(mcRotation.isViceFull() == McResultBool.TRUE);
        initPos();
        initListener();
    }

    private void initPos() {
        curMainPos = mcRotation.getSystemRotation() / 90;
        curVicePos = mcRotation.getViceRotation() / 90;
        curDisplayPos = mcRotation.getDisplayRotation() / 90;
        spinnerMain.setSelection(curMainPos);
        spinnerVice.setSelection(curVicePos);
        spinnerDisplay.setSelection(curDisplayPos);
    }

    private void initListener() {
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rotation = mcRotation.getSystemRotation();
                ToastUtils.showShort("???????????????: " + rotation + ", " + getRotation(rotation));
            }
        });

        btnVice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rotation = mcRotation.getViceRotation();
                ToastUtils.showShort("???????????????: " + rotation + ", " + getRotation(rotation));
            }
        });

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rotation = mcRotation.getDisplayRotation();
                ToastUtils.showShort("??????????????????: " + rotation + ", " + getRotation(rotation));
            }
        });

        cbViceFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mcRotation.setViceFull(isChecked);
            }
        });

        spinnerMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if (position == curMainPos) {
                    return;
                }
                curMainPos = position;

                int ret = mcRotation.setSystemRotation(position * 90);
                parseError(ret);

                if (ret == McErrorCode.ENJOY_COMMON_SUCCESSFUL) {
                    new AlertDialog.Builder(RotationActivity.this)
                            .setTitle("????????????????????????????????????")
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerVice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if (position == curVicePos) {
                    return;
                }
                curVicePos = position;

                int ret = mcRotation.setViceRotation(position * 90);
                parseError(ret);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == curDisplayPos) {
                    return;
                }
                curDisplayPos = position;

                int ret = mcRotation.setDisplayRotation(position * 90);
                parseError(ret);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private String getRotation(int rotation) {
        switch (rotation) {
            case 0:
                return "??????";
            case 90:
                return "??????";
            case 180:
                return "????????????";
            case 270:
                return "????????????";
        }

        return "??????";
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case McErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("??????");
                break;
            case McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                ToastUtils.showShort("???????????????????????????");
                break;
            case McErrorCode.ENJOY_ROTATION_MANAGER_ERROR_DISPLAY_ROTATION:
                ToastUtils.showShort("?????????????????????");
                break;
            case McErrorCode.ENJOY_ROTATION_MANAGER_ERROR_SYSTEM_ROTATION:
                ToastUtils.showShort("??????????????????");
                break;
            case McErrorCode.ENJOY_ROTATION_MANAGER_ERROR_VICE_ROTATION:
                ToastUtils.showShort("??????????????????");
                break;
            case McErrorCode.ENJOY_ROTATION_MANAGER_ERROR_VICE_FULL:
                ToastUtils.showShort("?????????????????????");
                break;
            case McErrorCode.ENJOY_ROTATION_MANAGER_ERROR_ROTATION_FORMAT:
                ToastUtils.showShort("??????????????????????????????");
                break;
            default:
                ToastUtils.showShort("????????????");
                break;
        }
    }
}