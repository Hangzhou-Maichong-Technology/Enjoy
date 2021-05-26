package com.mc.enjoysdk.transform;

import com.mc.android.mcsecure.McSecureManager;

/**
 * {@link McSecurePasswordState} 权限密码状态类 </br>
 */
public class McSecurePasswordState {
    /**
     * 密码已存在 </br>
     */
    public final static int MC_SECURE_PASSWD_EXISTED = McSecureManager.MC_SECURE_PASSWD_EXISTED;

    /**
     * 密码为空 </br>
     */
    public final static int MC_SECURE_PASSWD_EMPTY = McSecureManager.MC_SECURE_PASSWD_EMPTY;

    /**
     * 未知密码错误 </br>
     */
    public final static int MC_SECURE_PASSWD_UNKNOWN = McSecureManager.MC_SECURE_PASSWD_UNKNOWN;
}
