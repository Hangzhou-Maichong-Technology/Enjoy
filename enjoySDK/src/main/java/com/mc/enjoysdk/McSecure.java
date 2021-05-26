package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mcsecure.McSecureManager;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * {@link McSecure} 类是 Enjoy SDK 的权限管理类，Enjoy SDK 所有的方法调用都需要该权限授权。</br>
 * {@link McSecure} 中有两个重要的元素： "SecurePassword" 和 "SafeProgram"。</br>
 * 系统出厂时默认没有设置 "SecurePassword" 该密码由第一个使用 Enjoy SDK 的开发者进行设置。</br>
 * 当该密码设置成功后，开发者可以使用该密码将自己注册为 "SafeProgram" 。 在 "SafeProgram" 列表中的应用拥有 Enjoy SDK 的使用权限。</br>
 * </br>
 * Enjoy SDK 的权限是独立于 Android 原生的权限，同时又不独立于 Android 原生的权限。以下对该权限的使用做一个解释：</br>
 * 独立于Android原生的权限：</br>
 *  1、Enjoy SDK 的权限管理全部由 {@link McSecure} 类进行管理，并不需要在程序的 AndroidManifest.xml 文件中注册所需要使用的权限，</br>
 *  在 Android 版本高于 Android6.0 的版本中，也不需要在运行时申请动态权限。拥有 "SecurePassword" 的应用可以通过注册成为 "SafeProgram" 获取权限。</br>
 *  2、只有 Enjoy SDK 中的接口程序会进行 "SafeProgram" 检查，判断应用是否有权限使用 Enjoy SDK。</br>
 *  3、使用 Enjoy SDK 的开发者，可以选择通过 {@link McSecure} 注册为 "SafeProgram" 获取系统中的所有权限。也可以通过 Android 原生的权限申请方式获取</br>
 *  应用程序所需要的权限。开发者在没有注册 "SafeProgram" 应用时，对 Android 原生的权限框架完全没有影响。</br>
 * </br>
 *  不独立于Android原生权限：</br>
 *  当应用注册为 "SafeProgram" 应用后，它将拥有类似于 {@link android.os.Process#SYSTEM_UID} 应用的权限</br>
 *  在 Android 版本高于 Android6.0 的 Android 系统中 "SafeProgram" 应用直接拥有系统的 Normal Permissions、 Dangerous Permissions等所有权限的授权。</br>
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McSecure#getInstance(Context)} 方法获取 {@link McSecure} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McSecure#getSecurePasswdStatus()}: 获取 SecurePassword 的设置状态</li>
 * <li>{@link McSecure#setSecurePasswd(String, String)}: 设置 SecurePassword</li>
 * <li>{@link McSecure#resetSecurePasswd(String)}: 还原 SecurePassword （置空）</li>
 * <li>{@link McSecure#registSafeProgram(String)}: 注册当前程序为 SafeProgram</li>
 * <li>{@link McSecure#unregistSafeProgram()}: 注销当前程序为 SafeProgram</li>
 * <li>{@link McSecure#checkSafeProgramOfSelf()}: 检查当前程序是否为 SafeProgram</li>
 * </ol>
 */
public class McSecure {
    private static final String TAG = "McSecure";

    private McSecureManager mcSecureManager;

    private McSecure(Context context) {
        mcSecureManager = (McSecureManager) context.getSystemService(
                McSecureManager.MC_SECURE_MANAGER);
    }

    private static volatile McSecure instance = null;
    public static McSecure getInstance(Context context) {
        if (instance == null) {
            synchronized (McSecure.class) {
                if (instance == null) {
                    instance = new McSecure(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获取当前 "SecurePassword" 的设置状态。</br>
     * </br>
     * @return </br>
     * 成功返回对应的状态：</br>
     * {@link com.mc.enjoysdk.transform.McSecurePasswordState#MC_SECURE_PASSWD_EXISTED}: "SecurePassword" 已经存在了</br>
     * {@link com.mc.enjoysdk.transform.McSecurePasswordState#MC_SECURE_PASSWD_EMPTY}: "SecurePassword" 为空；系统出厂时"SecurePassword"为该状态</br>
     * {@link com.mc.enjoysdk.transform.McSecurePasswordState#MC_SECURE_PASSWD_UNKNOWN}: "SecurePassword" 状态未知，该情况一般不会出现</br>
     * 失败返回错误码: </br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int getSecurePasswdStatus() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSecureManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSecureManager.getSecurePasswdStatus();
    };

    /**
     *  设置 "SecurePassword"</br>
     * </br>
     * @param oldPasswd 当 "SecurePassword" 状态为 {@link com.mc.enjoysdk.transform.McSecurePasswordState#MC_SECURE_PASSWD_EMPTY} 时，该值可以为任意值，包括 null 和 空串，</br>
     *                  当 "SecurePassword" 状态为 {@link com.mc.enjoysdk.transform.McSecurePasswordState#MC_SECURE_PASSWD_EXISTED} 时，该值必须为当前的 "SecurePassword" 密码，否则函数会执行失败</br>
     * @param newPasswd 将要设置的 "SecurePassword" 密码, 密码至少 8 字符，并且至少要包含一个字母和一个数字</br>
     * @return 返回执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_PASSWD_FORMAT_ERROR}: 密码格式错误</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_PASSWD_CHECK_FAILED}: SecurePassword 检查不通过</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_PASSWD_SET_FAILED}: 密码设置失败</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int setSecurePasswd(String oldPasswd, String newPasswd) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSecureManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSecureManager.setSecurePasswd(oldPasswd, newPasswd);
    };

    /**
     *  重置 "SecurePassword" 为出厂状态 {@link com.mc.enjoysdk.transform.McSecurePasswordState#MC_SECURE_PASSWD_EMPTY};</br>
     * </br>
     * @param oldPasswd 当 "SecurePassword" 状态为 {@link com.mc.enjoysdk.transform.McSecurePasswordState#MC_SECURE_PASSWD_EMPTY} 时，该值可以为任意值，包括 null 和 空串，</br>
     *                  当 "SecurePassword" 状态为 {@link com.mc.enjoysdk.transform.McSecurePasswordState#MC_SECURE_PASSWD_EXISTED} 时，该值必须为当前的 "SecurePassword" 密码，否则函数会执行失败</br>
     * @return 返回执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} : 执行成功</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_PASSWD_ALREADY_EMPYT}: 密码已经为空</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_PASSWD_FORMAT_ERROR}: 密码格式不正确</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_PASSWD_CHECK_FAILED}: SecurePassword 校验不通过</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int resetSecurePasswd(String oldPasswd) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSecureManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSecureManager.resetSecurePasswd(oldPasswd);
    };

    /**
     * 将调用该方法的应用注册为 "SafeProgram"，"SecurePassword"状态必须为{@link com.mc.enjoysdk.transform.McSecurePasswordState#MC_SECURE_PASSWD_EXISTED} 才能调用，否则执行失败。</br>
     * </br>
     * @param securePasswd 当前系统的 "SecurePassword"</br>
     * @return 返回执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_PASSWD_NOT_INIT}: SecurePassword 未设置</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_PASSWD_CHECK_FAILED}: SecurePassword 校验不通过</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_PROGRAM_ALREADY_IN_SAFE_PROGRAM_LIST}: 当前程序已经是 SafeProgram</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_REGISTER_SAFE_PROGRAM_FAILED}: 注册 SafeProgram 失败</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int registSafeProgram(String securePasswd) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSecureManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSecureManager.registSafeProgram(securePasswd);
    };

    /**
     * 从 "SafeProgram" 列表中，将调用该方法的应用移除。</br>
     * 卸载或者更新安装 "SafeProgram" 列表中的应用，会自动将该应用从 "SafeProgram" 中移除。需要调用 {@link McSecure#registSafeProgram(String)} 重新注册为 "SafeProgram"</br>
     * </br>
     * @return 返回执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} : 执行成功</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_PROGRAM_NOT_IN_SAFE_PROGRAM_LIST}: 当前应用不在 SafeProgram 列表内</br>
     * {@link McErrorCode#ENJOY_SECURE_MANAGER_ERROR_UNREGISTER_SAFE_PROGRAM_FAILED}: 注销 SafeProgram 失败</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int unregistSafeProgram() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcSecureManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcSecureManager.unregistSafeProgram();
    };

    /**
     * 检查自己（调用该方法的应用）是否为 "SafeProgram"</br>
     * </br>
     * @return {@link McResultBool} Boolean 封装类 </br>
     * {@link McResultBool#TRUE}: 在 "SafeProgram" 列表中 </br>
     * {@link McResultBool#FALSE}: 不在 "SafeProgram" 列表中 </br>
     */
    public McResultBool checkSafeProgramOfSelf() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcSecureManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcSecureManager.checkSafeProgramOfSelf() ? McResultBool.TRUE : McResultBool.FALSE;
    };
}
