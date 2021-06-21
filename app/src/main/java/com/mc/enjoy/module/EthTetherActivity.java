package com.mc.enjoy.module;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.mc.android.mcethtether.ISubnetDevObserver;
import com.mc.android.mcethtether.McEthTetherConfig;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.transform.McEthTetherState;
import com.mc.android.mcethtether.McEthTetherSubDev;
import com.mc.enjoy.R;
import com.mc.enjoysdk.McEthTether;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Woong on 3/3/21
 * @website http://woong.cn
 */
public class EthTetherActivity extends AppCompatActivity {
    private static final String TAG = "EthTetherActivity";

    private Button btnTetherOpen;
    private Button btnTetherClose;
    private CheckBox cbTetherPersist;
    private TextView tvTetherState;
    private RadioGroup rgTetherSource;
    private RadioButton rbTetherWifi;
    private RadioButton rbTether4G;
    private Button btnGetTether;
    private Button btnSetTether;
    private EditText etTetherGateway;
    private EditText etTetherPrefix;
    private EditText etTetherDhcpStart;
    private EditText etTetherDchpEnd;
    private EditText etTetherDns;
    private Button btnSubDev;
    private TextView tvSubDev;

    private McEthTetherState mcEthTetherManager;
    private McEthTether mcEthTether;
    private ScheduledFuture tetherStateFuture;

    private ISubnetDevObserver iSubnetDevObserver = new ISubnetDevObserver.Stub() {
        @Override
        public void onDeviceAdded(McEthTetherSubDev device) throws RemoteException {
            updateSubDev();
        }

        @Override
        public void onDeviceRemoved(McEthTetherSubDev device) throws RemoteException {
            updateSubDev();
        }

        @Override
        public void onDeviceStateChanged(McEthTetherSubDev device) throws RemoteException {
            updateSubDev();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eth_tether);

        btnTetherOpen = findViewById(R.id.btn_tether_open);
        btnTetherClose = findViewById(R.id.btn_tether_close);
        cbTetherPersist = findViewById(R.id.cb_tether_persist);
        tvTetherState = findViewById(R.id.tv_tether_state);
        rgTetherSource = findViewById(R.id.rg_tether_source);
        rbTether4G = findViewById(R.id.rb_tether_4g);
        rbTetherWifi = findViewById(R.id.rb_tether_wifi);
        btnGetTether = findViewById(R.id.btn_get_tether);
        btnSetTether = findViewById(R.id.btn_set_tether);
        etTetherGateway = findViewById(R.id.et_tether_gateway);
        etTetherPrefix = findViewById(R.id.et_tether_prefix);
        etTetherDhcpStart = findViewById(R.id.et_tether_dhcp_start);
        etTetherDchpEnd = findViewById(R.id.et_tether_dhcp_end);
        etTetherDns = findViewById(R.id.et_tether_dns);
        btnSubDev = findViewById(R.id.btn_sub_dev);
        tvSubDev = findViewById(R.id.tv_sub_dev);

        tvSubDev.setMovementMethod(ScrollingMovementMethod.getInstance());

        initData();
        initListener();
    }

    private void initData() {
        mcEthTether = McEthTether.getInstance(this);
        mcEthTether.registerObserver(iSubnetDevObserver);
        cbTetherPersist.setChecked(mcEthTether.isEthTetherPersistent() == McResultBool.TRUE);

        loopTetherState();
        updateTetherFrom();
        getTetherConfig();
        updateSubDev();
    }

    private void initListener() {
        btnTetherOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ret = mcEthTether.enableEthTether(true, cbTetherPersist.isChecked());
                parseError(ret);
            }
        });

        btnTetherClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ret = mcEthTether.enableEthTether(false, cbTetherPersist.isChecked());
                parseError(ret);
            }
        });

        rgTetherSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_tether_wifi:
                        mcEthTether.selectEthTetherFrom(ConnectivityManager.TYPE_WIFI);
                        break;
                    case R.id.rb_tether_4g:
                        mcEthTether.selectEthTetherFrom(ConnectivityManager.TYPE_MOBILE);
                        break;
                    default:
                        break;
                }
            }
        });

        btnGetTether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTetherConfig();
            }
        });

        btnSetTether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                McEthTetherConfig mcEthTetherConfig = new McEthTetherConfig();
                mcEthTetherConfig.setGatewayAddr(etTetherGateway.getText().toString().trim());
                mcEthTetherConfig.setPrefixLength(Integer.parseInt(etTetherPrefix.getText().toString().trim()));
                mcEthTetherConfig.setDhcpStartAddr(etTetherDhcpStart.getText().toString().trim());
                mcEthTetherConfig.setDhcpEndAddr(etTetherDchpEnd.getText().toString().trim());
                mcEthTetherConfig.setDnsAddr(etTetherDns.getText().toString().trim());
                mcEthTether.setEthTetherConfig(mcEthTetherConfig);
            }
        });

        btnSubDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSubDev();
            }
        });
    }

    private void updateTetherFrom() {
        int tetherFrom = mcEthTether.getEthTetherFrom();
        if (tetherFrom == ConnectivityManager.TYPE_MOBILE) {
            rbTether4G.setChecked(true);
        } else if (tetherFrom == ConnectivityManager.TYPE_WIFI) {
            rbTetherWifi.setChecked(true);
        }
    }

    private void updateSubDev() {
        ArrayList<McEthTetherSubDev> devs = (ArrayList<McEthTetherSubDev>) mcEthTether.getEthSubDevList();

        if (devs != null) {
            StringBuilder s = new StringBuilder();
            for (McEthTetherSubDev dev : devs) {
                s.append(dev.toString());
                s.append("\n");
            }

            tvSubDev.setText(s.toString());
        }
    }

    private void getTetherConfig() {
        McEthTetherConfig mcEthTetherConfig = mcEthTether.getEthTetherConfig();
        if (mcEthTetherConfig != null) {
            etTetherGateway.setText(mcEthTetherConfig.getGatewayAddr());
            etTetherPrefix.setText(String.valueOf(mcEthTetherConfig.getPrefixLength()));
            etTetherDhcpStart.setText(mcEthTetherConfig.getDhcpStartAddr());
            etTetherDchpEnd.setText(mcEthTetherConfig.getDhcpEndAddr());
            etTetherDns.setText(mcEthTetherConfig.getDnsAddr());
        }
    }

    private void loopTetherState() {
        try {
            if (tetherStateFuture != null) {
                tetherStateFuture.cancel(true);
            }

            tetherStateFuture = Executors.newScheduledThreadPool(1, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    return new Thread(runnable, "EthStateThread");
                }
            }).scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String state = "";
                            switch (mcEthTether.getEthTetherState()) {
                                case McEthTetherState.TETHER_STATE_AVAILABLE:
                                    state = "可以共享但未共享";
                                    break;
                                case McEthTetherState.TETHER_STATE_UNAVAILABLE:
                                    state = "共享不可用";
                                    break;
                                case McEthTetherState.TETHER_STATE_TETHERED:
                                    state = "已共享";
                                    break;
                                case McEthTetherState.TETHER_STATE_ERRORED:
                                    state = "共享失败";
                                    break;
                                case McEthTetherState.TETHER_STATE_INVALID_UPSTREAM:
                                    state = "共享源不可用";
                                    break;
                                default:
                                    state = "未知状态";
                                    break;
                            }
                            tvTetherState.setText(String.format(getResources().getString(R.string.tether_state), state));
                        }
                    });
                }
            }, 1, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "ethState looper error == " + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mcEthTether.unregisterObserver(iSubnetDevObserver);
    }

    private void parseError(int errorCode) {
        switch (errorCode) {
            case McErrorCode.ENJOY_COMMON_SUCCESSFUL:
                ToastUtils.showShort("成功");
                break;
            case McErrorCode.ENJOY_ETHTETHER_MANAGER_ERROR_INVALID_UPSTREAM:
                ToastUtils.showShort("无效的以太网共享源，请在 WIFI 和 4G 中选择");
                break;
            case McErrorCode.ENJOY_ETHTETHER_MANAGER_ERROR_INVALID_CONFIG:
                ToastUtils.showShort("无效的以太网共享配置");
                break;
            case McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START:
                ToastUtils.showShort("以太网共享服务未启动");
                break;
            case McErrorCode.ENJOY_COMMON_ERROR_UNKNOWN:
            default:
                ToastUtils.showShort("未知错误");
                break;
        }
    }
}
