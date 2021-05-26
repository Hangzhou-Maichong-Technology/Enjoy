package com.mc.enjoysdk.result;

/**
 * EnjoySDK Boolean 类型返回值封装，兼容固件不支持、服务未启动错误 </br>
 */
public enum McResultBool {
    SERVICE_NOT_START(-2),
    DEVICE_NOT_SUPPORT(-1),
    FALSE(0),
    TRUE(1);

    private int value;

    McResultBool(int value) {
        this.value = value;
    }
}
