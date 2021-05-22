package com.mc.enjoy.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hzmct.enjoy.R;
import com.mc.android.mcfirmwareinfo.McFirmwareInfoManager;

public class FirmwareActivity extends AppCompatActivity {
    private static final String TAG = "FirmwareActivity";

    TextView tvFactory;
    TextView tvProduct;
    TextView tvSpecial;
    TextView tvCpuType;
    TextView tvAndroidVersion;
    TextView tvFirmwareVersion;
    TextView tvFirmwareCode;

    private McFirmwareInfoManager mcFirmwareInfoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firmware);

        tvFactory = findViewById(R.id.tv_factory);
        tvProduct = findViewById(R.id.tv_product);
        tvSpecial = findViewById(R.id.tv_special);
        tvCpuType = findViewById(R.id.tv_cpu_type);
        tvAndroidVersion = findViewById(R.id.tv_android_version);
        tvFirmwareVersion = findViewById(R.id.tv_firmware_version);
        tvFirmwareCode = findViewById(R.id.tv_firmware_code);

        mcFirmwareInfoManager = (McFirmwareInfoManager) getSystemService(McFirmwareInfoManager.MC_FIRMWARE_INFO_MANAGER);

        tvFactory.setText(String.format(getResources().getString(R.string.factory), mcFirmwareInfoManager.getFactoryInfo()));
        tvProduct.setText(String.format(getResources().getString(R.string.product), mcFirmwareInfoManager.getProductInfo()));
        tvSpecial.setText(String.format(getResources().getString(R.string.special), mcFirmwareInfoManager.getSpecialInfo()));
        tvCpuType.setText(String.format(getResources().getString(R.string.cpu_type), mcFirmwareInfoManager.getCpuTypeInfo()));
        tvAndroidVersion.setText(String.format(getResources().getString(R.string.android_version), mcFirmwareInfoManager.getAndroidVersionInfo()));
        tvFirmwareVersion.setText(String.format(getResources().getString(R.string.firmware_version), mcFirmwareInfoManager.getFirmwareVersion()));
        tvFirmwareCode.setText(String.format(getResources().getString(R.string.firmware_code), mcFirmwareInfoManager.getFirmwareVersionCode()));
    }
}