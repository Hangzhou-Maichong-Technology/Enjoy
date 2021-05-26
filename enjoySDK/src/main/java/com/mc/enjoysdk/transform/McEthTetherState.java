package com.mc.enjoysdk.transform;

import com.mc.android.mcethtether.McEthTetherManager;

/**
 * {@link McEthTetherState} 以太网共享状态类 </br>
 */
public class McEthTetherState {
    /**
     * 以太网共享状态: 共享可用但未共享
     */
    public static final int TETHER_STATE_AVAILABLE  = McEthTetherManager.TETHER_STATE_AVAILABLE;
    /**
     * 以太网共享状态: 已共享
     */
    public static final int TETHER_STATE_TETHERED   = McEthTetherManager.TETHER_STATE_TETHERED;
    /**
     * 以太网共享状态: 共享失败
     */
    public static final int TETHER_STATE_ERRORED    = McEthTetherManager.TETHER_STATE_ERRORED;
    /**
     * 以太网共享状态: 共享源不可用
     */
    public static final int TETHER_STATE_INVALID_UPSTREAM    = McEthTetherManager.TETHER_STATE_INVALID_UPSTREAM;
    /**
     * 以太网共享状态: 共享不可用
     */
    public static final int TETHER_STATE_UNAVAILABLE  = McEthTetherManager.TETHER_STATE_UNAVAILABLE;


}
