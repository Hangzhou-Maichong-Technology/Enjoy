package com.mc.enjoy.module;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.mc.android.mcethernet.McEthernetConfig;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.transform.McEthernetState;
import com.mc.enjoy.R;
import com.mc.enjoysdk.McEthernet;
import com.mc.enjoysdk.result.McResultBool;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Woong on 1/27/21
 * @website http://woong.cn
 */
public class EthActivity extends AppCompatActivity {
    private static final String TAG = "EthActivity";

    private CheckBox cbEthEnable;
    private TextView tvHwAddr;
    private TextView tvConnect;
    private Button btnEthGet;
    private Button btnEthSet;
    private Spinner spEthMode;
    private EditText etEthIpV4;
    private EditText etEthMask;
    private EditText etEthGateway;
    private EditText etEthDns1;
    private EditText etEthDns2;

    private McEthernetConfig mcEthernetConfig;
    private ScheduledFuture ethStateFuture;
    private int curEthMode = McEthernetConfig.DHCP_MODE;
    private McEthernet mcEthernet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eth);

        cbEthEnable = findViewById(R.id.cb_enable);
        tvHwAddr = findViewById(R.id.tv_hw_addr);
        tvConnect = findViewById(R.id.tv_connect);
        btnEthGet = findViewById(R.id.btn_get_eth);
        btnEthSet = findViewById(R.id.btn_set_eth);
        spEthMode = findViewById(R.id.sp_eth_mode);
        etEthIpV4 = findViewById(R.id.et_eth_ipv4);
        etEthMask = findViewById(R.id.et_eth_mask);
        etEthGateway = findViewById(R.id.et_eth_gateway);
        etEthDns1 = findViewById(R.id.et_eth_dns1);
        etEthDns2 = findViewById(R.id.et_eth_dns2);

        initData();
        initListener();
    }

    private void initData() {
        mcEthernet = McEthernet.getInstance(this);
        mcEthernetConfig = mcEthernet.getMcEthernetConfig();

        cbEthEnable.setChecked(mcEthernet.isEthernetEnable() == McResultBool.TRUE);
        tvHwAddr.setText(String.format(getString(R.string.eth_hw_addr), mcEthernet.getEthernetMacAddress("eth1")));
        setEthInfo();
        loopEthState();
    }

    private void initListener() {
        cbEthEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mcEthernet.switchEthernet(isChecked);
            }
        });

        btnEthGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcEthernetConfig = mcEthernet.getMcEthernetConfig();
                setEthInfo();
            }
        });

        btnEthSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcEthernetConfig.setMode(curEthMode);
                mcEthernetConfig.setIpV4Address(etEthIpV4.getText().toString().trim());
                mcEthernetConfig.setSubnetMask(etEthMask.getText().toString().trim());
                mcEthernetConfig.setGateway(etEthGateway.getText().toString().trim());
                mcEthernetConfig.setDns(etEthDns1.getText().toString().trim());
                mcEthernetConfig.setBackupDns(etEthDns2.getText().toString().trim());
                int ret = mcEthernet.setEthernetConfig(mcEthernetConfig);
                parseError(ret);
            }
        });

        spEthMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curEthMode = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setEthInfo() {
        if (mcEthernetConfig != null) {
            spEthMode.setSelection(mcEthernetConfig.getMode() - 1);
            etEthIpV4.setText(mcEthernetConfig.getIpV4Address());
            etEthMask.setText(mcEthernetConfig.getSubnetMask());
            etEthGateway.setText(mcEthernetConfig.getGateway());
            etEthDns1.setText(mcEthernetConfig.getDns());
            etEthDns2.setText(mcEthernetConfig.getBackupDns());
        }
    }

    private void loopEthState() {
        try {
            if (ethStateFuture != null) {
                ethStateFuture.cancel(true);
            }

            ethStateFuture = Executors.newScheduledThreadPool(1, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    return new Thread(runnable, "EthStateThread");
                }
            }).scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    int ethState = mcEthernet.getEthernetConnectState();
                    String state = "";
                    switch (ethState) {
                        case McEthernetState.ETHER_STATE_DISCONNECTED:
                            state = "以太网当前已断开连接";
                            break;
                        case McEthernetState.ETHER_STATE_CONNECTING:
                            state = "以太网正在连接中";
                            break;
                        case McEthernetState.ETHER_STATE_CONNECTED:
                            state = "以太网当前已连接";
                            break;
                        case McEthernetState.ETHER_STATE_DISCONNECTING:
                            state = "以太网正在断开连接";
                            break;
                        case McEthernetState.ETHER_STATE_UNKNOWN:
                            state = "以太网连接状态未知";
                            break;
                    }
                    final String ethStateString = state;

                    runOnUiThread(new Runnable() {
                        @SuppressLint("StringFormatMatches")
                        @Override
                        public void run() {
                            tvConnect.setText(String.format(getString(R.string.eth_state, ethStateString)));
                        }
                    });
                }
            }, 1, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "ethState looper error == " + e.getMessage());
        }
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case McErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("成功");
                break;
            case McErrorCode.ENJOY_ETHERNET_MANAGER_ERROR_CONFIG_INVALID:
                ToastUtils.showShort("以太网配置错误");
                break;
            case McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                ToastUtils.showShort("以太网配置服务错误");
                break;
            case McErrorCode.ENJOY_ETHERNET_MANAGER_ERROR_ETH_TETHER_IN_USE:
                ToastUtils.showShort("以太网共享使用中");
                break;
            default:
                ToastUtils.showShort("未知错误");
                break;
        }
    }
}

