package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mcrotation.McRotationManager;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * {@link McRotation} 是Enjoy SDK中屏幕旋转控制接口类</br>
 * </br>
 * 屏幕旋转功能用于对迈冲智能设备的屏幕方向设置。</br>
 * 屏幕旋转功能主要有三个配置，分别为主屏幕系统方向，主屏幕应用方向和副屏幕（HDMI）方向。</br>
 * </br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</br>
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McRotation#getInstance(Context)} 方法获取 {@link McRotation} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McRotation#getSystemRotation()} 获取主屏幕系统方向</li>
 * <li>{@link McRotation#setSystemRotation(int)} 设置主屏幕系统方向</li>
 * <li>{@link McRotation#getDisplayRotation()} 获取主屏幕应用方向</li>
 * <li>{@link McRotation#setDisplayRotation(int)} 设置主屏幕应用方向</li>
 * <li>{@link McRotation#getViceRotation()} 获取副屏幕系统方向</li>
 * <li>{@link McRotation#setViceRotation(int)} 设置副屏幕系统方向</li>
 * <li>{@link McRotation#isViceFull()} 获取副屏幕是否全屏</li>
 * <li>{@link McRotation#setViceFull(boolean)} 设置副屏幕是否全屏</li>
 * <ol/>
 */
public class McRotation {
    public static final String TAG = "McRotation";

    private McRotationManager mcRotationManager;

    private McRotation(Context context) {
        mcRotationManager = (McRotationManager) context.getSystemService(
                McRotationManager.MC_ROTATION);
    }

    private static volatile McRotation instance = null;
    public static McRotation getInstance(Context context) {
        if (instance == null) {
            synchronized (McRotation.class) {
                if (instance == null) {
                    instance = new McRotation(context);
                }
            }
        }
        return instance;
    }

    /**
     * 设置主屏幕系统方向 </br>
     * 设置主屏幕系统方向后需重启生效，需要在客户端中添加系统重启的确认弹框及重启代码。</br>
     * 重启代码参考 {@link McPower} </br>
     * 设置主屏幕系统方向会改变开机动画的显示方向 </br>
     * </br>
     * @param rotation 屏幕方向:
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE_REVERSE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT_REVERSE}
     * @return 返回执行结果状态码，具体的解释如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 执行成功 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START} 屏幕旋转服务未启动 </br>
     * {@link McErrorCode#ENJOY_ROTATION_MANAGER_ERROR_ROTATION_FORMAT} 屏幕方向参数错误 </br>
     * {@link McErrorCode#ENJOY_ROTATION_MANAGER_ERROR_SYSTEM_ROTATION} 主屏幕系统方向旋转错误 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_UNKNOWN} 未知错误</br>
     */
    public int setSystemRotation(int rotation) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcRotationManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcRotationManager.setSystemRotation(rotation);
    }

    /**
     * 获取主屏幕系统方向 </br>
     * </br>
     * @return 屏幕方向:
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE_REVERSE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT_REVERSE}
     */
    public int getSystemRotation() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcRotationManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcRotationManager.getSystemRotation();
    }

    /**
     * 设置副屏幕方向 </br>
     * 设置副屏幕方向后需重启生效，需要在客户端中添加系统重启的确认弹框及重启代码。</br>
     * 重启代码参考 {@link McPower} </br>
     * </br>
     * @param rotation 屏幕方向:
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE_REVERSE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT_REVERSE}
     * @return 返回执行结果状态码，具体的解释如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 执行成功 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START} 屏幕旋转服务未启动 </br>
     * {@link McErrorCode#ENJOY_ROTATION_MANAGER_ERROR_ROTATION_FORMAT} 屏幕方向参数错误 </br>
     * {@link McErrorCode#ENJOY_ROTATION_MANAGER_ERROR_VICE_ROTATION} 副屏幕方向旋转错误 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_UNKNOWN} 未知错误</br>
     */
    public int setViceRotation(int rotation) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcRotationManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcRotationManager.setViceRotation(rotation);
    }

    /**
     * 获取副屏幕方向 </br>
     * </br>
     * @return 屏幕方向:
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE_REVERSE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT_REVERSE}
     */
    public int getViceRotation() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcRotationManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcRotationManager.getViceRotation();
    }

    /**
     * 设置副屏幕是否满屏 </br>
     * </br>
     * @param full 是否满屏 </br>
     * @return 返回执行结果状态码，具体的解释如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 执行成功 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START} 屏幕旋转服务未启动 </br>
     * {@link McErrorCode#ENJOY_ROTATION_MANAGER_ERROR_VICE_FULL} 副屏幕全屏错误 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_UNKNOWN} 未知错误</br>
     */
    public int setViceFull(boolean full) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcRotationManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcRotationManager.setViceFull(full);
    }

    /**
     * 获取副屏幕是否满屏 </br>
     * </br>
     * @return {@link McResultBool} Boolean 封装类 </br>
     * {@link McResultBool#TRUE}: 满屏 </br>
     * {@link McResultBool#FALSE}: 非满屏 </br>
     */
    public McResultBool isViceFull() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcRotationManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcRotationManager.isViceFull() ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * 设置主屏幕应用方向 </br>
     * 设置主屏幕应用方向后会即时生效，无需重启</br>
     * 设置主屏幕应用方向不会改变开机动画的显示方向 </br>
     * </br>
     * @param rotation 屏幕方向:
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE_REVERSE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT_REVERSE}
     * @return 返回执行结果状态码，具体的解释如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 执行成功 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START} 屏幕旋转服务未启动 </br>
     * {@link McErrorCode#ENJOY_ROTATION_MANAGER_ERROR_ROTATION_FORMAT} 屏幕方向参数错误 </br>
     * {@link McErrorCode#ENJOY_ROTATION_MANAGER_ERROR_DISPLAY_ROTATION} 主屏幕应用方向旋转错误 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_UNKNOWN} 未知错误</br>
     */
    public int setDisplayRotation(int rotation) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcRotationManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcRotationManager.setDisplayRotation(rotation);
    }

    /**
     * 获取主屏幕应用方向 </br>
     * </br>
     * @return 屏幕方向:
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_LANDSCAPE_REVERSE},
     * {@link com.mc.enjoysdk.transform.McRotationDirection#ROTATION_PORTRAIT_REVERSE}
     */
    public int getDisplayRotation() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcRotationManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcRotationManager.getDisplayRotation();
    }
}
