package com.mc.enjoysdk;

import android.content.Context;

import com.mc.android.mcethernet.McEthernetConfig;
import com.mc.android.mcethernet.McEthernetManager;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * {@link McEthernet} 是以太网控制接口类</br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</br>
 * 系统中 {@link McEthTether}（以太网共享）功能开启的情况下，该类中的接口无法使用 </br>
 *</br>
 * 实例化方法：</br>
 * 调用 {@link McEthernet#getInstance(Context)} 方法获取 {@link McEthernet} 实例，</br>
 *</br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McEthernet#switchEthernet(boolean)}: 打开或关闭以太网功能</li>
 * <li>{@link McEthernet#isEthernetEnable()}: 检查以太网功能当前的开关状态</li>
 * <li>{@link McEthernet#setEthernetConfig(McEthernetConfig)}: 配置以太网，比如配置 IP 地址的获取方式等</li>
 * <li>{@link McEthernet#getMcEthernetConfig()}: 获取当前以太网的配置信息</li>
 * <li>{@link McEthernet#getEthernetConnectState()}: 获取以太网当前的连接状态</li>
 * <li>{@link McEthernet#getEthernetMacAddress(String)}: 获取以太网 MAC 地址</li>
 * <ol/>
 */
public class McEthernet {
    public static final String TAG = "McEthernet";

    private McEthernetManager mcEthernetManager;

    private McEthernet(Context context) {
        mcEthernetManager = (McEthernetManager) context.getSystemService(McEthernetManager.MC_ETHERNET_MANAGER);
    }

    private static volatile McEthernet instance = null;
    public static McEthernet getInstance(Context context) {
        if (instance == null) {
            synchronized (McEthernet.class) {
                if (instance == null) {
                    instance = new McEthernet(context);
                }
            }
        }
        return instance;
    }

    /**
     * 打开或关闭以太网功能 </br>
     * 该方法只能在 「以太网共享」 功能关闭的情况下才能使用。</br>
     * </br>
     * @param enable true：打开以太网 false：关闭以太网</br>
     */
    public int switchEthernet(boolean enable) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcEthernetManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        mcEthernetManager.switchEthernet(enable);
        return McErrorCode.ENJOY_COMMON_SUCCESSFUL;
    }

    /**
     * 检查以太网功能当前开关状态。</br>
     * 以太网使能状态通过 {@link McEthernet#switchEthernet(boolean)} 进行配置</br>
     * </br>
     * @return {@link McResultBool} Boolean 封装类 </br>
     * {@link McResultBool#TRUE}: 以太网功能处于打开状态 </br>
     * {@link McResultBool#FALSE}: 以太网功能处于关闭状态 </br>
     */
    public McResultBool isEthernetEnable() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcEthernetManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcEthernetManager.isEthernetEnable() ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * 配置以太网，比如配置 IP 地址的获取方式等</br>
     * </br>
     * @param mcEthernetConfig :以太网配置参数，通过实例化 {@link McEthernetConfig} 获取该参数</br>
     * {@link McEthernetConfig#setMode(int)},</br>
     * {@link McEthernetConfig#setIpV4Address(String)},</br>
     * {@link McEthernetConfig#setSubnetMask(String)},</br>
     * {@link McEthernetConfig#setGateway(String)},</br>
     * {@link McEthernetConfig#setDns(String)} and</br>
     * {@link McEthernetConfig#setBackupDns(String)}.</br>
     * @return 返回执行结果状态码，具体的解释如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_ETHERNET_MANAGER_ERROR_ETH_TETHER_IN_USE}: 以太网共享功能正在启用，无法配置以太网信息</br>
     * {@link McErrorCode#ENJOY_ETHERNET_MANAGER_ERROR_CONFIG_INVALID}: 配置参数无效</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int setEthernetConfig(McEthernetConfig mcEthernetConfig) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcEthernetManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcEthernetManager.setEthernetConfig(mcEthernetConfig);
    }

    /**
     * 获取当前以太网的配置信息 </br>
     * 具体的配置信息由系统默认值或者 {@link McEthernet#setEthernetConfig(McEthernetConfig)} 方法进行配置</br>
     * </br>
     * @return 失败返回 null； 成功返回 {@link McEthernetConfig} 的实例对象，从对象中获取具体的配置信息可以使用以下方法：</br>
     * {@link McEthernetConfig#getMode()},</br>
     * {@link McEthernetConfig#getIpV4Address()},</br>
     * {@link McEthernetConfig#getSubnetMask()},</br>
     * {@link McEthernetConfig#getGateway()},</br>
     * {@link McEthernetConfig#getDns()} 和</br>
     * {@link McEthernetConfig#getBackupDns()}.</br>
     */
    public McEthernetConfig getMcEthernetConfig() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcEthernetManager == null) {
            return null;
        }

        return mcEthernetManager.getMcEthernetConfig();
    }

    /**
     * 获取以太网 MAC 地址</br>
     * </br>
     * @param ifname 以太网接口，填写 null 或者空串时默认为 "eth0"</br>
     * @return </br>
     * 成功：返回MAC地址字符串； </br>
     * 失败：返回 {@link McEthernetConfig#NULL_IP_INFO}、字符串空串或 null </br>
     */
    public String getEthernetMacAddress(String ifname) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcEthernetManager == null) {
            return null;
        }

        return mcEthernetManager.getEthernetMacAddress(ifname);
    }

    /**
     * 获取以太网当前的连接状态</br>
     * </br>
     * @return </br>
     * 成功的话，返回当前以太网连接状态的状态码：</br>
     * {@link McEthernetManager#ETHER_STATE_CONNECTED}: 以太网当前已连接</br>
     * {@link McEthernetManager#ETHER_STATE_CONNECTING}: 以太网正在连接中</br>
     * {@link McEthernetManager#ETHER_STATE_DISCONNECTED}: 以太网当前已断开连接</br>
     * {@link McEthernetManager#ETHER_STATE_DISCONNECTING}: 以太网正在断开连接</br>
     * {@link McEthernetManager#ETHER_STATE_UNKNOWN}: 以太网连接状态未知</br>
     * 失败返回错误码：</br>
     * {@link McErrorCode#ENJOY_ETHERNET_MANAGER_ERROR_ETH_TETHER_IN_USE}: 以太网共享功能正在使用</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int getEthernetConnectState() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcEthernetManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcEthernetManager.getEthernetConnectState();
    }
}
