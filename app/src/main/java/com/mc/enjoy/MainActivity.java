package com.mc.enjoy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mc.enjoy.bean.EnjoyBean;
import com.mc.enjoy.module.BootAnimationActivity;
import com.mc.enjoy.module.EthActivity;
import com.mc.enjoy.module.EthTetherActivity;
import com.mc.enjoy.module.FirmwareActivity;
import com.mc.enjoy.module.HardwareKeyBoardActivity;
import com.mc.enjoy.module.HardwareStatusActivity;
import com.mc.enjoy.module.HomeActivity;
import com.mc.enjoy.module.NetCoexistenceActivity;
import com.mc.enjoy.module.PowerActivity;
import com.mc.enjoy.module.RotationActivity;
import com.mc.enjoy.module.SecureActivity;
import com.mc.enjoy.module.SystemUiActivity;
import com.mc.enjoy.module.TimeActivity;
import com.mc.enjoy.module.WatchDogActivity;
import com.mc.enjoy.module.WhiteAppActivity;
import com.mc.enjoy.permission.PermissionUtil;
import com.mc.enjoysdk.McSecure;
import com.mc.enjoysdk.transform.McEnjoySdkInfo;

import java.util.ArrayList;
import static com.mc.enjoy.constant.EnjoyEnum.*;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private TextView tvEnjoySdkVersion;
    private TextView tvEnjoyVersion;
    private TextView tvEnjoyCompatibleAndroidSdk;

    private ArrayList<EnjoyBean> enjoyBeans = new ArrayList<>();
    private PermissionUtil permissionUtil = new PermissionUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        注册为安全应用后，无需动态权限申请
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !permissionUtil.hasPermission(this)) {
//            permissionUtil.requestPermission(this);
//        }

        recyclerView = findViewById(R.id.recycler_view);
        tvEnjoySdkVersion = findViewById(R.id.tv_enjoy_sdk_version);
        tvEnjoyVersion = findViewById(R.id.tv_enjoy_version);
        tvEnjoyCompatibleAndroidSdk = findViewById(R.id.tv_enjoy_compatible_android_sdk);

        initData();
        initSecure();
        initEnjoyVersion();
    }

    private void initData() {
        EnjoyBean bootAnimationBean = new EnjoyBean(BOOT_ANIMATION, "开机动画测试");
        EnjoyBean ethernetBean = new EnjoyBean(ETHERNET, "以太网配置测试");
        EnjoyBean ethTetherBean = new EnjoyBean(ETH_TETHER, "以太网共享测试");
        EnjoyBean firmwareInfoBean = new EnjoyBean(FIRMWARE_INFO, "固件信息测试");
        EnjoyBean hardwareKeyBoardBean = new EnjoyBean(HARDWARE_KEYBOARD, "硬件输入设备测试");
        EnjoyBean hardwareStatusBean = new EnjoyBean(HARDWARE_STATUS, "硬件状态测试");
        EnjoyBean homeBean = new EnjoyBean(HOME, "开机程序测试");
        EnjoyBean installBean = new EnjoyBean(INSTALL, "应用白名单测试");
        EnjoyBean netCoexistBean = new EnjoyBean(NET_COEXIST, "三网共存测试");
        EnjoyBean powerBean = new EnjoyBean(POWER, "电源测试");
        EnjoyBean rotationBean = new EnjoyBean(ROTATION, "屏幕旋转测试");
        EnjoyBean secureBean = new EnjoyBean(SECURE, "权限密码测试");
        EnjoyBean systemUiBean = new EnjoyBean(SYSTEM_UI, "系统界面测试");
        EnjoyBean timeBean = new EnjoyBean(TIME, "时间测试");
        EnjoyBean watchDogBean = new EnjoyBean(WATCH_DOG, "看门狗测试");

        enjoyBeans.add(bootAnimationBean);
        enjoyBeans.add(ethernetBean);
        enjoyBeans.add(ethTetherBean);
        enjoyBeans.add(firmwareInfoBean);
        enjoyBeans.add(hardwareKeyBoardBean);
        enjoyBeans.add(hardwareStatusBean);
        enjoyBeans.add(homeBean);
        enjoyBeans.add(installBean);
        enjoyBeans.add(netCoexistBean);
        enjoyBeans.add(powerBean);
        enjoyBeans.add(rotationBean);
        enjoyBeans.add(secureBean);
        enjoyBeans.add(systemUiBean);
        enjoyBeans.add(timeBean);
        enjoyBeans.add(watchDogBean);

        recyclerView.setAdapter(new EnjoyAdapter());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void initSecure() {
        McSecure mcSecure = McSecure.getInstance(this);
        int ret = mcSecure.setSecurePasswd("Abc12345","Abc12345");
        Log.i(TAG, "setSecurePasswd == " + ret);
        mcSecure.registSafeProgram("Abc12345");
    }

    @SuppressLint("StringFormatMatches")
    private void initEnjoyVersion() {
        tvEnjoySdkVersion.setText(String.format(getString(R.string.enjoy_sdk_version, McEnjoySdkInfo.SDK_VERSION)));
        tvEnjoyVersion.setText(String.format(getString(R.string.enjoy_version, McEnjoySdkInfo.VERSION)));
        tvEnjoyCompatibleAndroidSdk.setText(String.format(getString(R.string.enjoy_android_sdk, McEnjoySdkInfo.COMPATIBLE_ANDROID_SDK)));
    }

    private class EnjoyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_enjoy, parent, false);
            return new EnjoyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((EnjoyViewHolder) holder).onBind(position);
        }

        @Override
        public int getItemCount() {
            return enjoyBeans.size();
        }
    }

    public class EnjoyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEnjoy;

        public EnjoyViewHolder(View itemView) {
            super(itemView);
            tvEnjoy = itemView.findViewById(R.id.tv_enjoy);
        }

        public void onBind(int position) {
            final EnjoyBean bean = enjoyBeans.get(position);
            tvEnjoy.setText(bean.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (bean.getId()) {
                        case BOOT_ANIMATION:
                            startActivity(new Intent(MainActivity.this, BootAnimationActivity.class));
                            break;
                        case ETHERNET:
                            startActivity(new Intent(MainActivity.this, EthActivity.class));
                            break;
                        case ETH_TETHER:
                            startActivity(new Intent(MainActivity.this, EthTetherActivity.class));
                            break;
                        case FIRMWARE_INFO:
                            startActivity(new Intent(MainActivity.this, FirmwareActivity.class));
                            break;
                        case HARDWARE_KEYBOARD:
                            startActivity(new Intent(MainActivity.this, HardwareKeyBoardActivity.class));
                            break;
                        case HARDWARE_STATUS:
                            startActivity(new Intent(MainActivity.this, HardwareStatusActivity.class));
                            break;
                        case HOME:
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            break;
                        case INSTALL:
                            startActivity(new Intent(MainActivity.this, WhiteAppActivity.class));
                            break;
                        case NET_COEXIST:
                            startActivity(new Intent(MainActivity.this, NetCoexistenceActivity.class));
                            break;
                        case POWER:
                            startActivity(new Intent(MainActivity.this, PowerActivity.class));
                            break;
                        case ROTATION:
                            startActivity(new Intent(MainActivity.this, RotationActivity.class));
                            break;
                        case SECURE:
                            startActivity(new Intent(MainActivity.this, SecureActivity.class));
                            break;
                        case SYSTEM_UI:
                            startActivity(new Intent(MainActivity.this, SystemUiActivity.class));
                            break;
                        case TIME:
                            startActivity(new Intent(MainActivity.this, TimeActivity.class));
                            break;
                        case WATCH_DOG:
                            startActivity(new Intent(MainActivity.this, WatchDogActivity.class));
                            break;
                    }
                }
            });
        }
    }

}