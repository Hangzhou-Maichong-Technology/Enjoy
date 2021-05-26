package com.mc.enjoysdk.transform;

import com.mc.android.mctime.McTimeManager;

/**
 * {@link McTimeFormat} 时间格式类 </br>
 */
public class McTimeFormat {
    /**
     * 时间格式，作为 {@link com.mc.enjoysdk.McTime#setTimeFormat(int)} 的参数 和 {@link McTimeManager#getCurrentTimeFormat()} 的返回值</br>
     * 24小时制</br>
     */
    public static final int HOUR_24 = McTimeManager.HOUR_24;

    /**
     * 时间格式，作为 {@link com.mc.enjoysdk.McTime#setTimeFormat(int)} 的参数 和 {@link McTimeManager#getCurrentTimeFormat()} 的返回值</br>
     * 12小时制</br>
     */
    public static final int HOUR_12 = McTimeManager.HOUR_12;
}
