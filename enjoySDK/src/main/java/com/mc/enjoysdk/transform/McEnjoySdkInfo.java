package com.mc.enjoysdk.transform;

import com.mc.android.enjoy.EnjoySdkInfo;

/**
 * EnjoySDK 版本信息 </br>
 */
public class McEnjoySdkInfo {
    /**
     * EnjoySDK 的主版本号，开发者可以通过该版本号对当前版本支持的接口功能进行判断</br>
     *</br>
     * 开发者可以通过  EnjoySdkInfo.SDK_VERSION 获取当前 EnjoySDK 的主版本号</br>
     */
    public final static int SDK_VERSION = EnjoySdkInfo.SDK_VERSION;

    /**
     * EnjoySDK 的版本名称。</br>
     * 比如 1.0.0 ：</br>
     * 第一位是主版本号；</br>
     * 第二位是发布版本号，表示 EnjoySDK 的发布次数；</br>
     * 第三位是预留位，用于内部开发使用</br>
     */
    public final static String VERSION = EnjoySdkInfo.VERSION;

    /**
     * EnjoySDK 匹配的 Android SDK 平台</br>
     */
    public final static String COMPATIBLE_ANDROID_SDK = EnjoySdkInfo.COMPATIBLE_ANDROID_SDK;
}
