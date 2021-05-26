package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mcwatchdog.McWatchdogConfig;
import com.mc.android.mcwatchdog.McWatchdogManager;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * <p>本类是 EnjoySDK  中的物理看门狗控制接口类。</p>
 * <p>使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</p>
 * 实例化方法：</br>
 * 调用 {@link McEthernet#getInstance(Context)} 方法获取 {@link McEthernet} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McWatchDog#init()} 初始化看门狗</li>
 * <li>{@link McWatchDog#kick()} 喂狗操作</li>
 * <li>{@link McWatchDog#setConfig(McWatchdogConfig)} 配置看门狗</li>
 * <li>{@link McWatchDog#getConfig()} 获取看门狗配置</li>
 * <li>{@link McWatchDog#close()} 关闭看门狗</li>
 * </ol>
 */
public class McWatchDog {
    public static final String TAG = "McWatchDog";

    private McWatchdogManager mcWatchdogManager;

    private McWatchDog(Context context) {
        mcWatchdogManager = (McWatchdogManager) context.getSystemService(McWatchdogManager.MC_WATCHDOG_MANAGER);
    }

    private static volatile McWatchDog instance = null;
    public static McWatchDog getInstance(Context context) {
        if (instance == null) {
            synchronized (McWatchDog.class) {
                if (instance == null) {
                    instance = new McWatchDog(context);
                }
            }
        }
        return instance;
    }

    /**
     * <p>初始化看门狗。</p>
     * </br>
     * 如果需要使用看门狗应用，使用之前必须先初始化，默认超时时间为16秒。</br>
     * </br>
     * @return 返回执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 配置成功 </br>
     * {@link McErrorCode#ENJOY_WATCHDOG_MANAGER_ERROR_NOT_INIT} 看门狗未初始化 </br>
     * {@link McErrorCode#ENJOY_WATCHDOG_MANAGER_ERROR_INIT_AGAIN} 看门狗重复初始化 </br>
     * {@link McErrorCode#ENJOY_WATCHDOG_MANAGER_ERROR_OPEN_DEV} 开启看门狗失败 </br>
     */
    public int init() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcWatchdogManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcWatchdogManager.init();
    }

    /**
     * <p>查询开门狗是否已启用。</p>
     * </br>
     * @return {@link McResultBool} Boolean 封装类 </br>
     * {@link McResultBool#TRUE}: 表示已启用 </br>
     * {@link McResultBool#FALSE}: 表示未启用 </br>
     */
    public McResultBool isInited() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcWatchdogManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcWatchdogManager.isInited() ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * <p>喂狗操作。</p>
     * 看门狗在初始化完成后，必须定期喂狗，喂狗间隔不能超过看门狗超时时间。 </br>
     * </br>
     * @return 返回执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 配置成功 </br>
     * {@link McErrorCode#ENJOY_WATCHDOG_MANAGER_ERROR_NOT_INIT} 看门狗未初始化 </br>
     */
    public int kick() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcWatchdogManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcWatchdogManager.kick();
    }

    /**
     * <p>配置看门狗。</p>
     * </br>
     * 配置看门狗之前也需要先初始化。 </br>
     * </br>
     * @param config 配置的类定义在 {@link McWatchdogConfig} 中，目前仅支持配置超时时间，单位为秒 </br>
     * {@link McWatchdogConfig#setTimeout(int)} 设置超时时间 </br>
     * </br>
     * @return 返回执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 配置成功 </br>
     * {@link McErrorCode#ENJOY_WATCHDOG_MANAGER_ERROR_INVALID_TIMEOUT} 超时时间无效 </br>
     * {@link McErrorCode#ENJOY_WATCHDOG_MANAGER_ERROR_SET_TIMEOUT_ERROR} 设置超时时间错误 </br>
     */
    public int setConfig(McWatchdogConfig config) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcWatchdogManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcWatchdogManager.setConfig(config);
    }

    /**
     * <p>关闭看门狗。</p>
     * 如果不需要再使用看门狗需要将看门狗关闭。</br>
     * </br>
     * @return 返回执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 关闭成功 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_UNKNOWN} 未知错误 </br>
     */
    public int close() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcWatchdogManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcWatchdogManager.close();
    }

    /**
     * <p>获取看门狗的配置信息。</p>
     * 目前仅支持超时时间 </br>
     * </br>
     * @return 返回看门狗配置 </br>
     * {@link McWatchdogConfig} 获取到的看门狗配置 </br>
     * {@link McWatchdogConfig#getTimeout()} 获取看门狗超时时间, 单位为秒 </br>
     */
    public McWatchdogConfig getConfig() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcWatchdogManager == null) {
            return null;
        }

        return mcWatchdogManager.getConfig();
    }
}
