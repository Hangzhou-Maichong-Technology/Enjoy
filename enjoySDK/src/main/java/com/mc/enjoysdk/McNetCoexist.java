package com.mc.enjoysdk;

import android.content.Context;
import android.net.ConnectivityManager;
import com.mc.android.mcnetcoexist.McNetCoexistenceManager;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 * <p>本类是 EnjoySDK 中的三网共存控制接口。</p>
 * </br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McNetCoexist#getInstance(Context)} 方法获取 {@link McNetCoexist} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McNetCoexist#netCoexistenceSwitch(boolean)} 开关网络共存功能</li>
 * <li>{@link McNetCoexist#getNetCoexistenceState()} 获取当前网络状态</li>
 * <li>{@link McNetCoexist#getNetworkSequence()} 获取网络优先级</li>
 * <li>{@link McNetCoexist#changeNetworkPriority(int, int)} 改变网络优先级</li>
 * </ol>
 */
public class McNetCoexist {
    public static final String TAG = "McNetCoexist";

    private McNetCoexistenceManager mcNetCoexistenceManager;

    private McNetCoexist(Context context) {
        mcNetCoexistenceManager = (McNetCoexistenceManager) context.getSystemService(
                McNetCoexistenceManager.MC_NET_COEXISTENCE_MANAGER);
    }

    private static volatile McNetCoexist instance = null;
    public static McNetCoexist getInstance(Context context) {
        if (instance == null) {
            synchronized (McNetCoexist.class) {
                if (instance == null) {
                    instance = new McNetCoexist(context);
                }
            }
        }
        return instance;
    }

    /**
     * <p>获取当前网络共存的优先级顺序</p>
     * </br>
     * @return 返回值是一个int[] 数组，数组包含3个元素，第一个元素优先级最高。</br>
     * 每个元素的值为 {@link ConnectivityManager#TYPE_MOBILE}、{@link ConnectivityManager#TYPE_WIFI}
     * {@link ConnectivityManager#TYPE_ETHERNET} 中的一种。
     */
    public int[] getNetworkSequence() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcNetCoexistenceManager == null) {
            return null;
        }

        return mcNetCoexistenceManager.getNetworkSequence();
    }

    /**
     * 调整某个网络的优先级，并返回调整后的所有网络优先级顺序。优先级断电后可以保持</br>
     * 默认优先级顺序为 ETHERNET:0, WIFI:1, MOBILE:2. 数字越小优先级越高</br>
     * </br>
     * example:</br>
     * 如果希望将WIFI的优先级调整到最高，则执行{@link McNetCoexistenceManager#changeNetworkPriority(int, int)} </br>
     * 第一个参数填{@link ConnectivityManager#TYPE_WIFI}, 第二个参数填 0. </br>
     * 调整后的优先级顺序为 WIFI:0, ETHERNET:1, MOBILE:2 </br>
     * </br>
     * @param netType 网络类型，值必须为{@link ConnectivityManager#TYPE_MOBILE}、{@link ConnectivityManager#TYPE_WIFI} </br>
     *      、{@link ConnectivityManager#TYPE_ETHERNET} 中的一种 </br>
     * @param toPosition 需要调整到的优先级，优先级赋值仅支持 0、1、2，其中数字越小优先级越高。</br>
     * @return 返回的数组为改变后的优先级，如果调整失败，则返回当前的网络优先级。</br>
     */
    public int[] changeNetworkPriority(int netType, int toPosition) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcNetCoexistenceManager == null) {
            return null;
        }

        return mcNetCoexistenceManager.changeNetworkPriority(netType, toPosition);
    }

    /**
     * <p>打开或者关闭网络共存功能。</p>
     * 三网共存的开关状态保存在系统变量中，断电后可以保持变量值。</br>
     * </br>
     * @param enable true: 打开三网共存， false: 关闭三网共存 </br?
     * @return 返回执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL} 执行成功 </br>
     */
    public int netCoexistenceSwitch(boolean enable) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcNetCoexistenceManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcNetCoexistenceManager.netCoexistenceSwitch(enable);
    }

    /**
     * 获取当前系统的网络共存状态。</br>
     * </br>
     * @return {@link McResultBool} Boolean 封装类 </br>
     * {@link McResultBool#TRUE}: 表示网络共存已打开 </br>
     * {@link McResultBool#FALSE}: 表示网络共存被关闭 </br>
     */
    public McResultBool getNetCoexistenceState() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcNetCoexistenceManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcNetCoexistenceManager.getNetCoexistenceState() ? McResultBool.TRUE : McResultBool.FALSE;
    }
}
