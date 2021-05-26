package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mcsystemui.McSystemUiManager;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 *  {@link McSystemUi} 是Enjoy SDK中的系统界面控制接口类</br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</br>
 * 注意： 该类中的接口，涉及到界面的刷新，所以必须在UI线程中调用。</br>
 *</br>
 * 实例化方法：</br>
 * 调用 {@link McSystemUi#getInstance(Context)} 方法获取 {@link McSystemUi} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McSystemUi#temporarilySwitchNavigation(boolean)}: 临时显示/隐藏导航栏，及时生效但重启不生效</li>
 * <li>{@link McSystemUi#temporarilySwitchStatusBar(boolean)}: 临时显示/隐藏状态栏，及时生效但重启不生效</li>
 * <li>{@link McSystemUi#getStatusBarShowStatus()}: 获取当前界面上状态栏的显示状态</li>
 * <li>{@link McSystemUi#getNavigationShowStatus()}: 获取当前界面上导航栏的显示状态</li>
 * <li>{@link McSystemUi#switchStatusBarAndNavigationOverwrite(boolean)}: 永久隐藏/显示状态栏和导航栏，不及时生效，重启生效</li>
 * <li>{@link McSystemUi#disableStatusBarItem(int)}: 禁止系统UI的部分功能显示</li>
 * <ol/>
 */
public class McSystemUi {
    public static final String TAG = "McSystemUi";

    private McSystemUiManager mcSystemUiManager;

    private McSystemUi(Context context) {
        mcSystemUiManager = (McSystemUiManager) context.getSystemService(
                McSystemUiManager.MC_SYSTEM_UI_MANAGER);
    }

    private static volatile McSystemUi instance = null;
    public static McSystemUi getInstance(Context context) {
        if (instance == null) {
            synchronized (McSystemUi.class) {
                if (instance == null) {
                    instance = new McSystemUi(context);
                }
            }
        }
        return instance;
    }

    /**
     * 控制系统状态栏的临时 隐藏/显示， 使用此方法隐藏状态栏后，无法通过下拉或者上滑唤出状态栏。</br>
     * 注意：该方法只是临时改动，重启后不生效。</br>
     * @param isShow true：显示状态栏，false：隐藏状态栏</br>
     * @return 返回方法的执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int temporarilySwitchStatusBar(boolean isShow) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSystemUiManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSystemUiManager.temporarilySwitchStatusBar(isShow);
    }

    /**
     * 获取当前系统状态栏的显示状态。</br>
     * 只能获取通过 {@link McSystemUi#temporarilySwitchStatusBar(boolean)} 方法，或者 设置->显示->永久隐藏状态栏 所设置的状态栏状态。</br>
     * @return </br>
     * 成功返回系统状态栏当前的显示状态：</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#HIDEING},</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#SHOWING} 和</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#UNKNOWN_STATUS}</br>
     * 失败返回错误码：</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int getStatusBarShowStatus() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSystemUiManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSystemUiManager.getStatusBarShowStatus();
    }

    /**
     * 控制系统导航栏的临时 隐藏/显示， 使用此方法隐藏导航栏后，无法通过下拉或者上滑唤出状态栏。</br>
     * 注意：该方法只是临时改动，重启后不生效。</br>
     * @param isShow true：显示导航栏，false：隐藏导航栏</br>
     * @return 返回方法的执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int temporarilySwitchNavigation(boolean isShow) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSystemUiManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSystemUiManager.temporarilySwitchNavigation(isShow);
    }

    /**
     * 获取当前系统导航栏的显示状态。</br>
     * 只能获取通过 {@link McSystemUi#temporarilySwitchStatusBar(boolean)} 方法，或者 设置->显示->永久隐藏状态栏 所设置的状态栏状态。</br>
     * @return </br>
     * 成功返回系统导航栏当前的显示状态：</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#HIDEING},</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#SHOWING} 和</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#UNKNOWN_STATUS}</br>
     * 失败返回对应的错误码：</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int getNavigationShowStatus() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSystemUiManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSystemUiManager.getNavigationShowStatus();
    }

    /**
     * 设置系统导航栏、状态栏是否永久关闭，和 设置->显示->永久隐藏状态栏 的设置结果保持一致。</br>
     * 注意：该方法是重启生效的。如果需要立即生效，请调用 {@link McSystemUi#temporarilySwitchStatusBar(boolean)} 和 {@link McSystemUiManager#temporarilySwitchNavigation(boolean)} 方法</br>
     * @param isShow true：设置为显示， false：设置为隐藏</br>
     * @return 返回方法执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}：相关服务未启动</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}：写入设置失败</br>
     */
    public int switchStatusBarAndNavigationOverwrite(boolean isShow) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSystemUiManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSystemUiManager.switchStatusBarAndNavigationOverwrite(isShow);
    }

    /**
     * 根据参数禁止对应的SystemUI中的控件</br>
     * 注意，重启后失效</br>
     * @param flag 控制参数，具体参数有：</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#DISABLE_EXPAND}</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#DISABLE_NOTIFICATION_ICONS}</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#DISABLE_NOTIFICATION_ALERTS}</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#DISABLE_SYSTEM_INFO}</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#DISABLE_HOME}</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#DISABLE_RECENT}</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#DISABLE_BACK}</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#DISABLE_CLOCK}</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#DISABLE_SEARCH}</br>
     * {@link com.mc.enjoysdk.transform.McSystemUiFlag#DISABLE_NONE}</br>
     * @return ：返回方法执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}：相关服务未启动</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}：写入设置失败</br>
     */
    public int disableStatusBarItem(int flag) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSystemUiManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSystemUiManager.disableStatusBarItem(flag);
    }
}
