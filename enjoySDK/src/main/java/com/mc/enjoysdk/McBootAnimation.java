package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mcbootanimation.McBootanimationManager;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * {@link McBootAnimation} 是开机动画设置接口类.</br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</br>
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McBootAnimation#getInstance(Context)} 方法获取 {@link McBootAnimation}实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McBootAnimation#setBootanimation(String bootanimationPath)}: 设置开机动画.</li>
 * <li>{@link McBootAnimation#resetBootanimation()}: 恢复安卓原生的开机动画.</li>
 * </ol>
 */
public class McBootAnimation {
    public static final String TAG = "McBootAnimation";

    private McBootanimationManager mcBootAnimation;

    private McBootAnimation(Context context) {
        mcBootAnimation = (McBootanimationManager) context.getSystemService(McBootanimationManager.MC_BOOTANIMATION_MANAGER);
    }

    private static volatile McBootAnimation instance = null;
    public static McBootAnimation getInstance(Context context) {
        if (instance == null) {
            synchronized (McBootAnimation.class) {
                if (instance == null) {
                    instance = new McBootAnimation(context);
                }
            }
        }
        return instance;
    }

    /**
     * 设置开机动画</br>
     * </br>
     * 通过传入的参数设置开机动画，检测开机动画文件是否存在、文件格式是否正确后。重启生效.</br>
     * @param bootAnimationPath 开机动画文件路径.</br>
     * @return 返回执行结果状态码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 设置成功</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 设置开机动画服务未启动</br>
     * {@link McErrorCode#ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_NOT_EXIST}: bootAnimationPath 指定的路径下未找到开机动画文件</br>
     * {@link McErrorCode#ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_CHECK_FAILED}: 开机动画文件不符合规范</br>
     * {@link McErrorCode#ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_COPY_FAILED}: 开机动画文件未能拷贝到指定的目录</br>
     * {@link McErrorCode#ENJOY_BOOTANIMATION_MANAGER_ERROR_PARAMETER_WRONG}: bootAnimationPath 参数错误</br>
     */
    public int setBootanimation(String bootAnimationPath) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcBootAnimation == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcBootAnimation.setBootanimation(bootAnimationPath);
    }

    /**
     * 恢复安卓原生开机动画</br>
     * </br>
     * 删除自定义开机动画文件，配置系统恢复安卓原生的开机动画.</br>
     * @return 返回执行结果状态码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 设置成功</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 设置开机动画服务未启动</br>
     * {@link McErrorCode#ENJOY_BOOTANIMATION_MANAGER_ERROR_CAN_NOT_DELETE_FILE}: 未找到自定义开机动画文件</br>
     */
    public int resetBootanimation() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcBootAnimation == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcBootAnimation.resetBootanimation();
    }
}
