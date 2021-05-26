package com.mc.enjoysdk.util;

/**
 * {@link EnjoyUtil} EnjoySDK Util 工具类 </br>
 * </br>
 * * 接口功能：</br>
 * <ol>
 * <li>{@link EnjoyUtil#isEnjoySupport()}</li> 是否支持 EnjoySDK 判断 </br>
 * </ol>
 */
public class EnjoyUtil {
    private static final String TAG = "EnjoyUtil";

    /**
     *
     * @return 是否支持 EnjoySDK 功能; true: 支持 false: 不支持
     */
    public static boolean isEnjoySupport() {
        return PropUtil.getBooleanFromProp("ro.enjoysdk.support", false);
    }
}
