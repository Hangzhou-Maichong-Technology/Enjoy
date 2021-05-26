package com.mc.enjoysdk.transform;

import com.mc.android.mcethernet.McEthernetManager;

/**
 * {@link McEthernetState} 以太网状态类 </br>
 */
public class McEthernetState {
    /**
     * 以太网状态：未连接
     */
    public static final int ETHER_STATE_DISCONNECTED = McEthernetManager.ETHER_STATE_DISCONNECTED;
    /**
     * 以太网状态：连接中
     */
    public static final int ETHER_STATE_CONNECTING = McEthernetManager.ETHER_STATE_CONNECTING;
    /**
     * 以太网状态：已连接
     */
    public static final int ETHER_STATE_CONNECTED = McEthernetManager.ETHER_STATE_CONNECTED;
    /**
     * 以太网状态：连接断开
     */
    public static final int ETHER_STATE_DISCONNECTING = McEthernetManager.ETHER_STATE_DISCONNECTING;
    /**
     * 以太网状态：未知状态
     */
    public static final int ETHER_STATE_UNKNOWN = McEthernetManager.ETHER_STATE_UNKNOWN;
}