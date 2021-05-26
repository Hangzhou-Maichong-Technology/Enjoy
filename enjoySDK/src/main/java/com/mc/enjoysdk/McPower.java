package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mcpower.McPowerManager;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * {@link McPower} 是Enjoy SDK中的电源管理类</br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</br>
 *</br>
 * 实例化方法：</br>
 * 调用 {@link McPower#getInstance(Context)} 方法获取 {@link McPower} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McPower#reboot()}: 重启设备</li>
 * <li>{@link McPower#shutdown(boolean)}: 关机设备（部分设备硬件不支持，关机后会立即启动设备）</li>
 * </ol>
 */
public class McPower {
    public static final String TAG = "McPower";

    private McPowerManager mcPowerManager;

    private McPower(Context context) {
        mcPowerManager = (McPowerManager) context.getSystemService(McPowerManager.MC_POWER_MANAGER);
    }

    private static volatile McPower instance = null;
    public static McPower getInstance(Context context) {
        if (instance == null) {
            synchronized (McPower.class) {
                if (instance == null) {
                    instance = new McPower(context);
                }
            }
        }
        return instance;
    }

    /**
     * 重启设备</br>
     * </br>
     * @return 返回执行结果</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 一般成功时，设备已经进入重启了。</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动。</br>
     */
    public int reboot() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcPowerManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcPowerManager.reboot();
    }

    /**
     * 关机设备，部分设备可能因为电源管理单元不支持关机，所以会出现关机后设备立马启动的情况</br>
     * </br>
     * @param confirm  true: 弹框提示是否关机， false：直接关机</br>
     * @return 返回执行结果</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 一般成功时，设备已经进入重启了。</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动。</br>
     */
    public int shutdown(boolean confirm) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcPowerManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcPowerManager.shutdown(confirm);
    }
}
