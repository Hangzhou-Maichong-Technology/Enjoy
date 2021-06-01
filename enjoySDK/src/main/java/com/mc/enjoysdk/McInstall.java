package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mcinstall.McInstallManager;
import com.mc.android.mcinstall.McWhiteApp;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;
import java.util.ArrayList;

/**
 * {@link McInstall} 是 Enjoy SDK 中的应用白名单控制接口类</br>
 * </br>
 * 应用白名单功能，是一套 Android 系统管理第三方 App 的方案。 </br>
 * 白名单启用后，可以对第三方 App 的安装/卸载进行管理。 </br>
 * 白名单安装管理包含安装方式（前台安装、后台安装）、安装后是否自动打开应用； </br>
 * 白名单卸载管理包含是否允许卸载。 </br>
 * 白名单功能开启后，Pm 命令的 install 和 uninstall 将会被禁用。</br>
 * </br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</br>
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McInstall#getInstance(Context)} 方法获取 {@link McInstall} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McInstall#whiteListSwitch(boolean)} 应用白名单功能开关</li>
 * <li>{@link McInstall#addWhiteList(ArrayList)} 向应用白名单中添加应用</li>
 * <li>{@link McInstall#removeWhiteList(ArrayList)} 从应用白名单中删除应用</li>
 * <li>{@link McInstall#getWhiteList()} 获取白名单列表</li>
 * <li>{@link McInstall#isEnable()} 检查应用白名单功能是否启用</li>
 * <li>{@link McInstall#isInstallAllow(String)} 判断某个 App 是否允许安装</li>
 * <li>{@link McInstall#isUninstallAllow(String)} 判断某个 App 是否允许卸载</li>
 * <li>{@link McInstall#isOpenAfterInstall(String)} 判断某个 App 安装后是否自动打开</li>
 * <li>{@link McInstall#getInstallMode(String)} 获取某个 App 安装模式</li>
 * <ol/>
 */
public class McInstall {
    public static final String TAG = "McInstall";

    private McInstallManager mcInstallManager;

    private McInstall(Context context) {
        mcInstallManager = (McInstallManager) context.getSystemService(McInstallManager.MC_INSTALL);
    }

    private static volatile McInstall instance = null;
    public static McInstall getInstance(Context context) {
        if (instance == null) {
            synchronized (McInstall.class) {
                if (instance == null) {
                    instance = new McInstall(context);
                }
            }
        }
        return instance;
    }

    /**
     * 应用白名单功能开关 </br>
     * </br>
     * @param enable 是否启用白名单 true: 启用 false: 禁用 </br>
     * @return 返回执行结果状态码，具体的解释如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 执行成功 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START} 白名单服务未启动 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_UNKNOWN} 未知错误</br>
     */
    public int whiteListSwitch(boolean enable) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcInstallManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcInstallManager.whiteListSwitch(enable);
    }

    /**
     * 向应用白名单中添加应用. {@link McWhiteApp} 为需要添加的应用。 </br>
     * {@link McWhiteApp#setPackageName(String packageName)(int)},</br>
     * {@link McWhiteApp#setInstallMode(int installMode)(String)},</br>
     * {@link McWhiteApp#setAllowUninstall(boolean allowUninstall)(String)},</br>
     * {@link McWhiteApp#setOpenAfterInstall(boolean openAfterInstall)(String)},</br>
     * </br>
     * @param whiteList : 需要添加到白名单的应用列表 </br>
     * @return 返回执行结果状态码，具体的解释如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 执行成功 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START} 白名单服务未启动 </br>
     * {@link McErrorCode#ENJOY_INSTALL_MANAGER_ERROR_WHITE_ADD} 添加到白名单错误 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_UNKNOWN} 未知错误</br>
     */
    public int addWhiteList(ArrayList<McWhiteApp> whiteList) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcInstallManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcInstallManager.addWhiteList(whiteList);
    }

    /**
     * 从应用白名单中删除应用。{@link McWhiteApp} 为需要删除的应用。 </br>
     * {@link McWhiteApp#setPackageName(String packageName)(int)},</br>
     * {@link McWhiteApp#setInstallMode(int installMode)(String)},</br>
     * {@link McWhiteApp#setAllowUninstall(boolean allowUninstall)(String)},</br>
     * {@link McWhiteApp#setOpenAfterInstall(boolean openAfterInstall)(String)},</br>
     * </br>
     * @param whiteList : 需要从白名单中删除的应用列表 </br>
     * @return 返回执行结果状态码，具体的解释如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 执行成功 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START} 白名单服务未启动 </br>
     * {@link McErrorCode#ENJOY_INSTALL_MANAGER_ERROR_WHITE_REMOVE} 从白名单中删除错误 </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_UNKNOWN} 未知错误</br>
     */
    public int removeWhiteList(ArrayList<McWhiteApp> whiteList) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcInstallManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcInstallManager.removeWhiteList(whiteList);
    }

    /**
     * 获取白名单列表。</br>
     * </br>
     * @return 返回白名单中的所有应用列表 </br>
     * </br>
     * {@link McWhiteApp} 是获取到的应用信息。</br>
     * {@link McWhiteApp#getPackageName()} 应用包名 </br>
     * {@link McWhiteApp#getInstallMode()} 应用安装模式 </br>
     * {@link McWhiteApp#isAllowUninstall()} 应用是否允许卸载 </br>
     * {@link McWhiteApp#isOpenAfterInstall()} 应用安装后是否自动打开 </br>
     */
    public ArrayList<McWhiteApp> getWhiteList() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcInstallManager == null) {
            return null;
        }

        return mcInstallManager.getWhiteList();
    }

    /**
     * 检查应用白名单功能是否启用</br>
     * 白名单功能通过 {@link McInstall#whiteListSwitch(boolean)} 进行配置 </br>
     * </br>
     * @return 是否启用应用白名单功能 true: 已启用 false: 未启用</br>
     */
    public McResultBool isEnable() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcInstallManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcInstallManager.isEnable() ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * 判断某个 App 是否允许安装 </br>
     * </br>
     * @param packageName App 包名 </br>
     * @return 是否允许安装 true: 允许安装 false: 禁止安装
     */
    public McResultBool isInstallAllow(String packageName) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcInstallManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcInstallManager.isInstallAllow(packageName) ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * 判断某个 App 是否允许卸载 </br>
     * </br>
     * @param packageName App 包名 </br>
     * @return 是否允许卸载 true: 允许卸载 false: 禁止卸载
     */
    public McResultBool isUninstallAllow(String packageName) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcInstallManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcInstallManager.isUninstallAllow(packageName) ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * 判断某个 App 安装后是否自动打开 </br>
     * </br>
     * @param packageName App 包名 </br>
     * @return 是否允许安装后自动打开 true: 自动打开 false: 不打开
     */
    public McResultBool isOpenAfterInstall(String packageName) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcInstallManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcInstallManager.isOpenAfterInstall(packageName) ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * 获取某个 App 安装模式 </br>
     * </br>
     * @param packageName App 包名 </br>
     * @return 应用安装模式 </br>
     * {@link McWhiteApp#INSTALL_MODE_FOREGROUND} 前台安装模式，显示安装确认弹框</br>
     * {@link McWhiteApp#INSTALL_MODE_BACKGROUND} 后台安装模式，即静默安装</br>
     */
    public int getInstallMode(String packageName) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcInstallManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcInstallManager.getInstallMode(packageName);
    }
}
