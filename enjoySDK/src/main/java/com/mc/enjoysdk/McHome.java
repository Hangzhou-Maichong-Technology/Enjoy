package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mchomemanager.McHomeManager;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * {@link McHome} 是开机启动程序接口类</br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</br>
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McHome#getInstance(Context)} 方法获取 {@link McHome} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McHome#setHomePackage(String packageName)}: 设置开机启动程序</li>
 * <li>{@link McHome#getHomePackage()}: 获取当前系统的开机启动程序</li>
 * <li>{@link McHome#startRawLauncher()}: 立即启动安卓原生桌面</li>
 * </ol>
 */
public class McHome {
    public static final String TAG = "McHome";

    private McHomeManager mcHomeManager;

    private McHome(Context context) {
        mcHomeManager = (McHomeManager) context.getSystemService(McHomeManager.MC_HOMEMANAGER_SERVICE);
    }

    private static volatile McHome instance = null;
    public static McHome getInstance(Context context) {
        if (instance == null) {
            synchronized (McHome.class) {
                if (instance == null) {
                    instance = new McHome(context);
                }
            }
        }
        return instance;
    }

    /**
     * 设置开机启动程序，重启生效.</br>
     * 注意：如果程序未安装则还是启动 Android 原生桌面.</br>
     * 如果开机启动 app 为不可以作为 launcher 的 app ，则按 home 键会返回 Android 原生桌面.</br>
     * </br>
     * @param packageName 开机启动 app 的包名,如果将这个参数设为 null ，则可以恢复 Android 原生的桌面。</br>
     * @return 返回执行结果状态码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 设置成功.</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 服务未启动.</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}: 设置数据库失败.</br>
     */
    public int setHomePackage(String packageName) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcHomeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcHomeManager.setHomePackage(packageName);
    }

    /**
     * 获取当前开机启动程序的包名。</br>
     * </br>
     * @return 如果返回 null 则表示未设置或设置出错，正常情况下返回开机启动 app 的包名。</br>
     */
    public String getHomePackage(){
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcHomeManager == null) {
            return null;
        }

        return mcHomeManager.getHomePackage();
    }

    /**
     * 立即启动 Android 原生界面.</br>
     * </br>
     * @return 返回执行结果状态码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 设置成功</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 服务未启动</br>
     */
    public int startRawLauncher(){
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcHomeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcHomeManager.startRawLauncher();
    }
}
