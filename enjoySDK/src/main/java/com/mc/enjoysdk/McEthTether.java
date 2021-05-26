package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mcethtether.ISubnetDevObserver;
import com.mc.android.mcethtether.McEthTetherConfig;
import com.mc.android.mcethtether.McEthTetherManager;
import com.mc.android.mcethtether.McEthTetherSubDev;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

import java.util.List;

/**
 * <p>EnjoySDK 中的以太网共享控制接口。</p>
 * </br>
 * <p>使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</p>
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McEthTether#getInstance(Context)} 方法获取 {@link McEthTether} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McEthTether#enableEthTether(boolean, boolean)} 打开或者关闭以太网共享</li>
 * <li>{@link McEthTether#getEthTetherState()} 获取当前以太网共享的状态</li>
 * <li>{@link McEthTether#isEthTetherPersistent()} 查询以太网配置是否断电保存</li>
 * <li>{@link McEthTether#setEthTetherConfig(McEthTetherConfig)} 配置以太网共享的相关参数</li>
 * <li>{@link McEthTether#getEthTetherConfig()} 获取当前的以太网共享的相关参数信息</li>
 * <li>{@link McEthTether#selectEthTetherFrom(int)} 选择网络共享源。</li>
 * <li>{@link McEthTether#getEthTetherFrom()} 查询以太网共享的源</li>
 * <li>{@link McEthTether#getEthSubDevList()} 获取当前所有已分配IP的设备列表</li>
 * <li>{@link McEthTether#registerObserver(ISubnetDevObserver)} 注册一个监听器来监听设备变化</li>
 * <li>{@link McEthTether#unregisterObserver(ISubnetDevObserver)} 注销一个子网设备监视器</li>
 * </ol>
 */
public class McEthTether {
    public static final String TAG = "McEthTether";

    private McEthTetherManager mcEthTetherManager;

    private McEthTether(Context context) {
        mcEthTetherManager = (McEthTetherManager) context.getSystemService(
                McEthTetherManager.MC_ETH_TETHER_MANAGER);
    }

    private static volatile McEthTether instance = null;
    public static McEthTether getInstance(Context context) {
        if (instance == null) {
            synchronized (McEthTether.class) {
                if (instance == null) {
                    instance = new McEthTether(context);
                }
            }
        }
        return instance;
    }

    /**
     * 注册一个子网设备监视器。</br>
     * interface {@link ISubnetDevObserver} 监听器包括下面3个回调接口： </br>
     * {@link ISubnetDevObserver#onDeviceAdded(McEthTetherSubDev)} 一个新设备已经从以太网共享的服务中获取到了一个IP地址 </br>
     * {@link ISubnetDevObserver#onDeviceRemoved(McEthTetherSubDev)} 一个已经分配到 IP 的设备由于长时间没有续约，导致超过租期后 IP 地址被收回 </br>
     * {@link ISubnetDevObserver#onDeviceStateChanged(McEthTetherSubDev)} 一个已经分配到 IP 的设备发生了断线或者重新上线的信息 </br>
     * @param observer 必须实现ISubnetDevObserver.Stub 接口
     */
    public void registerObserver(ISubnetDevObserver observer) {
        if (mcEthTetherManager != null) {
            mcEthTetherManager.registerObserver(observer);
        }
    }

    /**
     * 注销一个子网设备监视器 </br>
     * </br>
     * @param observer 需要注销的监视器对象 </br>
     */
    public void unregisterObserver(ISubnetDevObserver observer) {
        if (mcEthTetherManager != null) {
            mcEthTetherManager.unregisterObserver(observer);
        }
    }

    /**
     * 打开或者关闭以太网共享 </br>
     * </br>
     * @param enable true:表示打开， false:表示关闭 </br>
     * @param isPersistent true：断电保存以太网状态 false: 断电不保存以太网状态</br>
     * @return 返回值为0表示操作成功，否则为失败，返回的错误码请参考{@link McErrorCode}中定义的错误码 </br>
     */
    public int enableEthTether(boolean enable, boolean isPersistent) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcEthTetherManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcEthTetherManager.enableEthTether(enable, isPersistent);
    }

    /**
     * 获取当前以太网共享的状态 </br>
     * </br>
     * @return
     * {@link com.mc.enjoysdk.transform.McEthTetherState#TETHER_STATE_AVAILABLE}：可以共享但未共享 </br>
     * {@link com.mc.enjoysdk.transform.McEthTetherState#TETHER_STATE_TETHERED}：已共享 </br>
     * {@link com.mc.enjoysdk.transform.McEthTetherState#TETHER_STATE_ERRORED}：共享失败 </br>
     */
    public int getEthTetherState() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcEthTetherManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcEthTetherManager.getEthTetherState();
    }

    /**
     * 查询以太网配置是否断电保存</br>
     * </br>
     * @return {@link McResultBool} Boolean 封装类 </br>
     * {@link McResultBool#TRUE}: 断电保存 </br>
     * {@link McResultBool#FALSE}: 断电不保存 </br>
     */
    public McResultBool isEthTetherPersistent() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcEthTetherManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcEthTetherManager.isEthTetherPersistent() ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * 配置以太网共享的相关参数。</br>
     * </br>
     * 当配置完成后需要重新开关一次网络共享 </br>
     * 可以配置的属性包括</br>
     * {@link McEthTetherConfig#setGatewayAddr(String)} 网关地址</br>
     * {@link McEthTetherConfig#setPrefixLength(int)} 网关的掩码长度</br>
     * {@link McEthTetherConfig#setDhcpStartAddr(String)} 起始DHCP地址</br>
     * {@link McEthTetherConfig#setDhcpEndAddr(String)} 结束DHCP地址</br>
     * {@link McEthTetherConfig#setDnsAddr(String)} 默认DNS地址 </br>
     * </br>
     * @param config 一个配置类对象 </br>
     * @return 返回执行结果状态码，具体的解释如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 执行成功 </br>
     * {@link McErrorCode#ENJOY_ETHTETHER_MANAGER_ERROR_INVALID_CONFIG} 配置无效 </br>
     */
    public int setEthTetherConfig(McEthTetherConfig config) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcEthTetherManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcEthTetherManager.setEthTetherConfig(config);
    }

    /**
     * 获取当前的以太网共享的相关参数信息 </br>
     * </br>
     * @return 一个配置类对象 {@link McEthTetherConfig} </br>
     */
    public McEthTetherConfig getEthTetherConfig() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcEthTetherManager == null) {
            return null;
        }

        return mcEthTetherManager.getEthTetherConfig();
    }

    /**
     * 强制指定以太网共享的源</br>
     * 可以选择 WIFI 或者 4G，默认是 4G。 </br>
     * </br>
     * @param tetherFrom 填写 {@link android.net.ConnectivityManager#TYPE_MOBILE} 或者
     * {@link android.net.ConnectivityManager#TYPE_WIFI} </br>
     * @return 返回执行结果状态码，具体的解释如下：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 执行成功 </br>
     * {@link McErrorCode#ENJOY_ETHTETHER_MANAGER_ERROR_INVALID_UPSTREAM} 无效的以太网共享源，请在 WIFI 和 4G 中选择 </br>
     */
    public int selectEthTetherFrom(int tetherFrom) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcEthTetherManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcEthTetherManager.selectEthTetherFrom(tetherFrom);
    }

    /**
     * 查询以太网共享的源 </br>
     * </br>
     * @return 返回 {@link android.net.ConnectivityManager#TYPE_MOBILE} 或者
     * {@link android.net.ConnectivityManager#TYPE_WIFI}
     */
    public int getEthTetherFrom() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcEthTetherManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcEthTetherManager.getEthTetherFrom();
    }

    /**
     * 获取当前所有已分配 IP 的设备列表 </br>
     * </br>
     * @return 当前已分配 IP 的所有设备列表 </br>
     * {@link McEthTetherSubDev} 每个设备的信息类 </br>
     */
    public List<McEthTetherSubDev> getEthSubDevList() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcEthTetherManager == null) {
            return null;
        }

        return mcEthTetherManager.getEthSubDevList();
    }
}
