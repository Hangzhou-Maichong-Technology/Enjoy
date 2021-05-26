package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mchardwarekeyboard.McHardwareKeyboardManager;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * {@link McHardwareKeyBoard} 是硬件输入设备和软键盘兼容接口类.</br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</br>
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McHardwareKeyBoard#getInstance(Context)} 方法获取 {@link McHardwareKeyBoard} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McHardwareKeyBoard#compatibleHardwareKeyBoard(boolean enable)}: 设置硬件输入设备与软键盘兼容/互斥</li>
 * <li>{@link McHardwareKeyBoard#isHardwareBoardCompatible()}: 获取当前系统硬件输入设备与软键盘是否兼容</li>
 * </ol>
 */
public class McHardwareKeyBoard {
    public static final String TAG = "McHardwareKeyBoard";

    private McHardwareKeyboardManager mcHardwareKeyboardManager;

    private McHardwareKeyBoard(Context context) {
        mcHardwareKeyboardManager = (McHardwareKeyboardManager) context.getSystemService(
                McHardwareKeyboardManager.MC_HARDWAREKEYBOARD_MANAGER);
    }

    private static volatile McHardwareKeyBoard instance = null;
    public static McHardwareKeyBoard getInstance(Context context) {
        if (instance == null) {
            synchronized (McHardwareKeyBoard.class) {
                if (instance == null) {
                    instance = new McHardwareKeyBoard(context);
                }
            }
        }
        return instance;
    }

    /**
     * 设置硬件输入与软键盘互斥/兼容</br>
     * </br>
     * @param enable true：兼容，false：互斥</br>
     * @return 返回执行结果状态码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 设置成功</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 服务未启动</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}: 设置数据库失败</br>
     */
    public int compatibleHardwareKeyBoard(boolean enable) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcHardwareKeyboardManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcHardwareKeyboardManager.compatibleHardwareKeyBoard(enable);
    }

    /**
     * 检测硬件输入和软键盘是否兼容</br>
     * </br>
     * @return {@link McResultBool} Boolean 封装类 </br>
     * {@link McResultBool#TRUE}: 兼容 </br>
     * {@link McResultBool#FALSE}: 互斥 </br>
     */
    public McResultBool isHardwareBoardCompatible(){
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcHardwareKeyboardManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcHardwareKeyboardManager.isHardwareBoardCompatible() ? McResultBool.TRUE : McResultBool.FALSE;
    }
}
