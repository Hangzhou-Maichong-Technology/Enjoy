package com.mc.enjoy.module;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzmct.enjoy.R;
import com.mc.android.enjoy.EnjoyErrorCode;
import com.mc.android.mcnetcoexist.McNetCoexistenceManager;

import java.util.Arrays;

/**
 * @author Woong on 2/21/21
 * @website http://woong.cn
 */
public class NetCoexistenceActivity extends AppCompatActivity {
    private static final String TAG = "NetCoexistenceActivity";

    private CheckBox cbNetState;
    private Spinner spNetPriority;
    private Spinner spNetType;
    private Button btnSetPriority;
    private Button btnGetPriority;
    private TextView tvPriority;

    private McNetCoexistenceManager mcNetCoexistenceManager;
    private int[] priority;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_coexistence);

        cbNetState = findViewById(R.id.cb_net_state);
        spNetPriority = findViewById(R.id.sp_net_priority);
        spNetType = findViewById(R.id.sp_net_type);
        btnSetPriority = findViewById(R.id.btn_set_priority);
        btnGetPriority = findViewById(R.id.btn_get_priority);
        tvPriority = findViewById(R.id.tv_priority);

        initData();
        initListener();
    }

    private void initData() {
        mcNetCoexistenceManager = (McNetCoexistenceManager) getSystemService(McNetCoexistenceManager.MC_NET_COEXISTENCE_MANAGER);
        cbNetState.setChecked(mcNetCoexistenceManager.getNetCoexistenceState());

        priority = mcNetCoexistenceManager.getNetworkSequence();
        updatePriority();
    }

    private void initListener() {
        cbNetState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int ret = mcNetCoexistenceManager.netCoexistenceSwitch(isChecked);
                parseError(ret);
            }
        });

        btnSetPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int netType = -1;
                switch (spNetType.getSelectedItemPosition()) {
                    case 2:
                        netType = ConnectivityManager.TYPE_MOBILE;
                        break;
                    case 1:
                        netType = ConnectivityManager.TYPE_WIFI;
                        break;
                    case 0:
                        netType = ConnectivityManager.TYPE_ETHERNET;
                        break;
                }

                LogUtils.i(TAG, "netType == " + netType
                        + ", priority == " + spNetPriority.getSelectedItemPosition());
                priority = mcNetCoexistenceManager.changeNetworkPriority(
                        netType, spNetPriority.getSelectedItemPosition());
                LogUtils.i(TAG, "set priority == " + Arrays.toString(priority));
                updatePriority();
            }
        });

        btnGetPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = mcNetCoexistenceManager.getNetworkSequence();
                LogUtils.i(TAG, "priority == " + Arrays.toString(priority));
                updatePriority();
            }
        });
    }

    private void updatePriority() {
        String s = "当前优先级\n第一优先级 : " + parsePriority(priority[0]) + "\n"
                + "第二优先级 : " + parsePriority(priority[1]) + "\n"
                + "第三优先级 : " + parsePriority(priority[2]) + "\n";

        tvPriority.setText(s);
    }

    private String parsePriority(int p) {
        switch (p) {
            case ConnectivityManager.TYPE_MOBILE:
                return "移动网络";
            case ConnectivityManager.TYPE_WIFI:
                return "Wi-Fi";
            case ConnectivityManager.TYPE_ETHERNET:
                return "以太网";
            default:
                return "未知网络";
        }
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("成功");
                break;
            default:
                ToastUtils.showShort("未知错误");
                break;
        }
    }
}
