package com.mc.enjoysdk;

import android.content.Context;

import com.mc.android.mchardwarestatusmanager.McCpuStatus;
import com.mc.android.mchardwarestatusmanager.McHardwareStatusManager;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * {@link McHardwareStatus} 是硬件状态接口类</br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</br>
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McHardwareStatus#getInstance(Context)} 方法获取 {@link McHardwareStatus} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McHardwareStatus#getCPUStatus()}: 获取cpu的温度和占用率。</li>
 * <li>{@link McHardwareStatus#getUpTime()}: 获取系统运行时间。</li>
 * </ol>
 */
public class McHardwareStatus {
    public static final String TAG = "McHardwareStatus";

    private McHardwareStatusManager mcHardwareStatusManager;

    private McHardwareStatus(Context context) {
        mcHardwareStatusManager = (McHardwareStatusManager) context.getSystemService(
                McHardwareStatusManager.MC_HARDWARESTATUSMANAGER_SERVICE);
    }

    private static volatile McHardwareStatus instance = null;
    public static McHardwareStatus getInstance(Context context) {
        if (instance == null) {
            synchronized (McHardwareStatus.class) {
                if (instance == null) {
                    instance = new McHardwareStatus(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取 cpu 信息，包括 cpu 的温度和使用率。</br>
     * </br>
     * @return 失败返回 null，成功返回类 {@link McCpuStatus}.</br>
     */
    public McCpuStatus getCPUStatus() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcHardwareStatusManager == null) {
            return null;
        }

        return mcHardwareStatusManager.getCPUStatus();
    }

    /**
     * 获取设备运行时间</br>
     * </br>
     * @return 成功：返回系统运行时间，单位毫秒 </br>
     * 失败：返回错误码.</br>
     * 错误码如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 服务未启动</br>
     */
    public long getUpTime() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcHardwareStatusManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcHardwareStatusManager.getUpTime();
    }
}
