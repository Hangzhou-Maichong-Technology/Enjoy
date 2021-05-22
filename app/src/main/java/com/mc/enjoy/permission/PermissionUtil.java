package com.mc.enjoy.permission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * @author Woong on 2020/5/28
 * @website http://woong.cn
 */
public class PermissionUtil {
    private static final String TAG = "PermissionUtil";

    public static final String[] permissionArray = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
    };

    @RequiresApi(Build.VERSION_CODES.M)
    public boolean hasPermission(Context context) {
        boolean hasPermission = true;

        for (String permission : permissionArray) {
            int result = context.checkSelfPermission(permission);
            if (result == -1) {
                hasPermission = false;
            }
        }

        Log.i(TAG, "hasPermission == " + hasPermission);
        return hasPermission;
    }

    /**
     * 请求权限
     */
    public void requestPermission(Context context) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
