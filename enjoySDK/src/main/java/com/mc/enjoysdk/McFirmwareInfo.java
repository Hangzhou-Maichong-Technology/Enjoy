package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mcfirmwareinfo.McFirmwareInfoManager;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * <p>本类为 EnjoySDK 中的固件和设备信息获取接口类。</p>
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McFirmwareInfo#getInstance(Context)} 方法获取 {@link McFirmwareInfo} 实例，</br>
 * </br>
 * 接口功能</br>
 * <ol>
 *     <li>{@link McFirmwareInfo#getFactoryInfo()} 获取设备的生产商信息</li>
 *     <li>{@link McFirmwareInfo#getProductInfo()} 获取设备的产品型号</li>
 *     <li>{@link McFirmwareInfo#getSpecialInfo()} 获取产品型号的定制化信息</li>
 *     <li>{@link McFirmwareInfo#getCpuTypeInfo()} 获取产品的 CPU 型号信息</li>
 *     <li>{@link McFirmwareInfo#getCpuSerial()} 获取产品的 CPU 唯一序列号</li>
 *     <li>{@link McFirmwareInfo#getAndroidVersionInfo()} 获取产品的 Android 版本信息</li>
 *     <li>{@link McFirmwareInfo#getFirmwareVersion()} 获取固件的版本名称</li>
 *     <li>{@link McFirmwareInfo#getFirmwareVersionCode()} 获取固件的版本号</li>
 * </ol>
 */
public class McFirmwareInfo {
    public static final String TAG = "McFirmwareInfo";

    private McFirmwareInfoManager mcFirmwareInfoManager;

    private McFirmwareInfo(Context context) {
        mcFirmwareInfoManager = (McFirmwareInfoManager) context.getSystemService(McFirmwareInfoManager.MC_FIRMWARE_INFO_MANAGER);
    }

    private static volatile McFirmwareInfo instance = null;
    public static McFirmwareInfo getInstance(Context context) {
        if (instance == null) {
            synchronized (McFirmwareInfo.class) {
                if (instance == null) {
                    instance = new McFirmwareInfo(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取设备的生产商信息</br>
     * </br>
     * @return 返回值是一个字符串，一般是 hzmct，返回 null 表示获取失败 </br>
     */
    public String getFactoryInfo() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcFirmwareInfoManager == null) {
            return null;
        }

        return mcFirmwareInfoManager.getFactoryInfo();
    }

    /**
     * 获取设备的产品型号 </br>
     * </br>
     * @return 返回值是一个字符串，表示这个设备的产品型号，返回 null 表示获取失败 </br>
     */
    public String getProductInfo() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcFirmwareInfoManager == null) {
            return null;
        }

        return mcFirmwareInfoManager.getProductInfo();
    }

    /**
     * 返回产品型号的定制化信息 </br>
     * </br>
     * @return 返回值是一个字符串，一般是定制产品才有的信息，返回 null 表示获取失败 </br>
     */
    public String getSpecialInfo() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcFirmwareInfoManager == null) {
            return null;
        }

        return mcFirmwareInfoManager.getSpecialInfo();
    }

    /**
     * 获取产品的 CPU 型号信息 </br>
     * </br>
     * @return 返回值是一个字符串，表示 CPU 型号，返回 null 表示获取失败 </br>
     */
    public String getCpuTypeInfo() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcFirmwareInfoManager == null) {
            return null;
        }

        return mcFirmwareInfoManager.getCpuTypeInfo();
    }

    /**
     * 返回产品的 CPU 唯一序列号 </br>
     * </br>
     * @return 返回值是一个字符串，表示 CPU 序列号，返回 null 表示获取失败 </br>
     */
    public String getCpuSerial() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcFirmwareInfoManager == null) {
            return null;
        }

        return mcFirmwareInfoManager.getCpuSerial();
    }

    /**
     * 返回产品的 Android 版本信息 </br>
     * </br>
     * @return 返回值是一个字符串，表示安卓版本信息，返回 null 表示获取失败 </br>
     */
    public String getAndroidVersionInfo() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcFirmwareInfoManager == null) {
            return null;
        }

        return mcFirmwareInfoManager.getAndroidVersionInfo();
    }

    /**
     * 返回固件的版本名称 </br>
     * </br>
     * @return 返回值是一个字符串，表示固件的版本名称，返回 null 表示获取失败 </br>
     */
    public String getFirmwareVersion() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcFirmwareInfoManager == null) {
            return null;
        }

        return mcFirmwareInfoManager.getFirmwareVersion();
    }

    /**
     * 返回固件的版本号 </br>
     * </br>
     * @return 返回值是一个 int 类型，表示固件的版本号，返回 -1 表示获取失败 </br>
     */
    public int getFirmwareVersionCode() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcFirmwareInfoManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcFirmwareInfoManager.getFirmwareVersionCode();
    }
}
