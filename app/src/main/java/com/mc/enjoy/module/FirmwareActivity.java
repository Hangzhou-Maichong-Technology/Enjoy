package com.mc.enjoy.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.mc.enjoy.R;
import com.mc.enjoysdk.McFirmwareInfo;

public class FirmwareActivity extends AppCompatActivity {
    private static final String TAG = "FirmwareActivity";

    TextView tvFactory;
    TextView tvProduct;
    TextView tvSpecial;
    TextView tvCpuType;
    TextView tvAndroidVersion;
    TextView tvFirmwareVersion;
    TextView tvFirmwareCode;

    private McFirmwareInfo mcFirmwareInfo;

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

        mcFirmwareInfo = McFirmwareInfo.getInstance(this);

        tvFactory.setText(String.format(getResources().getString(R.string.factory), mcFirmwareInfo.getFactoryInfo()));
        tvProduct.setText(String.format(getResources().getString(R.string.product), mcFirmwareInfo.getProductInfo()));
        tvSpecial.setText(String.format(getResources().getString(R.string.special), mcFirmwareInfo.getSpecialInfo()));
        tvCpuType.setText(String.format(getResources().getString(R.string.cpu_type), mcFirmwareInfo.getCpuTypeInfo()));
        tvAndroidVersion.setText(String.format(getResources().getString(R.string.android_version), mcFirmwareInfo.getAndroidVersionInfo()));
        tvFirmwareVersion.setText(String.format(getResources().getString(R.string.firmware_version), mcFirmwareInfo.getFirmwareVersion()));
        tvFirmwareCode.setText(String.format(getResources().getString(R.string.firmware_code), mcFirmwareInfo.getFirmwareVersionCode()));
    }
}