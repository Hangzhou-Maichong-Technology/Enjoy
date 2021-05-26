package com.mc.enjoysdk;

import android.content.Context;
import com.mc.android.mctime.McTimeManager;
import com.mc.enjoysdk.result.McResultBool;
import com.mc.enjoysdk.transform.McErrorCode;
import com.mc.enjoysdk.util.EnjoyUtil;

/**
 *  {@link McTime} 是Enjoy SDK中的时间设置接口类</br>
 * 使用该类中的接口方法必须先调用 {@link McSecure#registSafeProgram(String)} 方法将当前应用注册为系统安全应用。</br>
 * </br>
 * 实例化方法：</br>
 * 调用 {@link McTime#getInstance(Context)} 方法获取 {@link McTime} 实例，</br>
 * </br>
 * 接口功能：</br>
 * <ol>
 * <li>{@link McTime#switchAutoDateAndTime(boolean)}: 打开/关闭网络对时，及时生效，重启生效</li>
 * <li>{@link McTime#switchAutoTimeZone(boolean)}: 打开/关闭自动时区校准，及时生效，重启生效</li>
 * <li>{@link McTime#isAutoDateAndTime()}: 检查当前网络对时是否启用</li>
 * <li>{@link McTime#isAutoTimeZone()}: 检查当前自动时区校准是否启用</li>
 * <li>{@link McTime#setTime(int, int, int, int)}: 设置时间</li>
 * <li>{@link McTime#setDate(int, int, int)}: 设置日期</li>
 * <li>{@link McTime#setTimeFormat(int)}: 设置时间显示格式，及时生效，重启生效</li>
 * <li>{@link McTime#getCurrentTimeFormat()}: 获取当前时间显示格式</li>
 * <li>{@link McTime#setNtpServerAddress(String, long)}: 设置NTP服务器地址和超时时间，及时不生效，重启生效</li>
 * <li>{@link McTime#getNtpServerAddressInUse()}: 获取当前正在使用的NTP服务器地址信息</li>
 * <li>{@link McTime#getNtpTimeout()}: 获取当前NTP对时操作的超时时间</li>
 * <li>{@link McTime#setTimeZone(String)}: 设置时区信息，及时生效，重启生效</li>
 * <li>{@link McTime#checkNtpServerAddressAvailable(String, long)}: 检查NTP服务器地址在指定超时时间下是否可用，需要联网，该功能不能在主线程中调用</li>
 * </ol>
 */
public class McTime {
    public static final String TAG = "McTime";

    private McTimeManager mcTimeManager;


    private McTime(Context context  ) {
        mcTimeManager = (McTimeManager) context.getSystemService(McTimeManager.MC_TIME_MANAGER);
    }

    private static volatile McTime instance = null;
    public static McTime getInstance(Context context) {
        if (instance == null) {
            synchronized (McTime.class) {
                if (instance == null) {
                    instance = new McTime(context);
                }
            }
        }
        return instance;
    }

    /**
     * 网络自动对时功能开关函数，每次网络自动对时功能从关闭到打开的事件发生时，系统都会主动的进行一次对时操作</br>
     * 注意：设置后立即生效，并且重启依旧能够保存配置</br>
     * </br>
     * @param enable true：打开网络自动对时开关，false：关闭网络自动对时开关</br>
     * @return 返回方法执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}：写入设置失败</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int switchAutoDateAndTime(boolean enable) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcTimeManager.switchAutoDateAndTime(enable);
    }

    /**
     * 自动配置时区功能开关函数</br>
     * 注意：设置后立即生效，并且重启依旧能够保存配置</br>
     * </br>
     * @param enable true：打开自动配置时区功能，false：关闭自动配置时区功能</br>
     * @return 返回方法执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}：写入设置失败</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int switchAutoTimeZone(boolean enable) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcTimeManager.switchAutoTimeZone(enable);
    }

    /**
     * 设置系统时间显示格式 24小时制/12小时制</br>
     * 注意：设置后立即生效，并且重启依旧能够保存配置</br>
     * </br>
     * @param timeFormat {@link com.mc.enjoysdk.transform.McTimeFormat#HOUR_12}：12小时制，
     * {@link com.mc.enjoysdk.transform.McTimeFormat#HOUR_24}：24小时制</br>
     * @return 返回方法执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_PARAMETER_ERROR}：参数错误</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}：写入设置失败</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int setTimeFormat(int timeFormat){
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcTimeManager.setTimeFormat(timeFormat);
    }

    /**
     * 设置系统时间，必须在网络对时功能关闭下才能使用该方法，否则执行失败。</br>
     * 可以通过 {@link McTime#isAutoDateAndTime()} 方法获取网络对时功能状态。</br>
     * </br>
     * @param hourOfDay 小时</br>
     * @param minute 分钟</br>
     * @param second 秒钟</br>
     * @param millisecond 毫秒</br>
     * @return 返回方法执行结果码：</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_PARAMETER_ERROR}：参数错误</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}：写入设置失败</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_FUNCTION_OCCUPY}：网络对时开启，功能重复</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int setTime(int hourOfDay, int minute, int second, int millisecond) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcTimeManager.setTime(hourOfDay, minute, second, millisecond);
    }

    /**
     * 设置系统该日期，必须在网络对时功能关闭下才能使用该方法，否则执行失败。</br>
     * 可以通过 {@link McTime#isAutoDateAndTime()} 方法获取网络对时功能状态。</br>
     * </br>
     * @param year 年</br>
     * @param month 月</br>
     * @param day 日</br>
     * @return 返回方法执行结果码</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_PARAMETER_ERROR}：参数错误</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}：写入设置失败</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_FUNCTION_OCCUPY}：网络对时开启，功能重复</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int setDate(int year, int month, int day) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcTimeManager.setDate(year, month, day);
    }

    /**
     * 设置系统时区，必须在自动配置时区功能关闭下才能使用该方法，否则执行失败 </br>
     * 可以通过 {@link McTime#isAutoTimeZone()} 方法获取自动配置时区功能状态。</br>
     * </br>
     * @param timeZone 时区信息，必须使用 {@link com.mc.android.mctime.McTimeZone} 类中的时区类型进行设置。</br>
     * @return 返回方法执行结果码</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_PARAMETER_ERROR}: 参数错误</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}: 写入设置失败</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_FUNCTION_OCCUPY}：自动时区校准开启，功能复用</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int setTimeZone(String timeZone) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcTimeManager.setTimeZone(timeZone);
    }

    /**
     * 设置系统的 NTP（网络时间协议) 服务器地址，
     * 建议先使用 {@link McTime#checkNtpServerAddressAvailable(String, long)} 对即将要设置的地址做检查，通过后再设置。</br>
     * 注意：NTP 服务器地址设置成功后，必须重启设备才会使用新的 NTP 服务器地址，
     * 可以通过 {@link McTime#getNtpServerAddressInUse()} 获取当前正在使用的 NTP 服务器地址。</br>
     * </br>
     * @param ntpServerAddress NTP服务器地址</br>
     * @param timeOut 获取网络时间最大超时时间，系统默认为 5s</br>
     * @return 返回方法执行结果码</br>
     * {@link McErrorCode#ENJOY_COMMON_SUCCESSFUL}: 执行成功</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_PARAMETER_ERROR}: 参数错误</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR}: 写入设置失败</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_NTP_SERVER_ADDRESS_SET_FAILED}: 设置NTP服务器地址失败</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_NTP_TIMEOUT_SET_FAILED}: 设置NTP服务器访问超时时间失败</br>
     * {@link McErrorCode#ENJOY_TIME_MANAGER_ERROR_NTP_CONFIG_SET_FAILED}: 设置 NTP 服务器地址和访问超时时间失败</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int setNtpServerAddress(String ntpServerAddress, long timeOut) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcTimeManager.setNtpServerAddress(ntpServerAddress, timeOut);
    }

    /**
     * 获取系统当前是否开启了网络自动对时功能</br>
     * </br>
     * @return {@link McResultBool} Boolean 封装类 </br>
     * {@link McResultBool#TRUE}: 开启中 </br>
     * {@link McResultBool#FALSE}: 关闭中 </br>
     */
    public McResultBool isAutoDateAndTime() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcTimeManager.isAutoDateAndTime() ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * 获取系统当前是否开启了自动配置时区功能</br>
     * </br>
     * @return {@link McResultBool} Boolean 封装类 </br>
     * {@link McResultBool#TRUE}: 开启中 </br>
     * {@link McResultBool#FALSE}: 关闭中 </br>
     */
    public McResultBool isAutoTimeZone() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcTimeManager.isAutoTimeZone() ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * 获取系统当前的时间格式</br>
     * </br>
     * @return ：</br>
     * 成功返回对应的时间格式码</br>
     * {@link com.mc.enjoysdk.transform.McTimeFormat#HOUR_12}: 执行成功，12小时制</br>
     * {@link com.mc.enjoysdk.transform.McTimeFormat#HOUR_24}: 执行成功，24小时制</br>
     * 执行失败返回错误码：</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public int getCurrentTimeFormat() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcTimeManager.getCurrentTimeFormat();
    }

    /**
     * 获取当前系统正在使用的 NTP 服务器地址</br>
     * </br>
     * @return 成功返回对应的 NTP 服务器地址字符串，失败返回 null 或字符串空串</br>
     */
    public String getNtpServerAddressInUse() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return null;
        }

        if (mcTimeManager == null) {
            return null;
        }

        return mcTimeManager.getNtpServerAddressInUse();
    }

    /**
     * 检查 NTP 服务器地址是否可用</br>
     * 注意：该功能需要联网，所以不能在主线程中调用该方法</br>
     * </br>
     * @param ntpServerAddress ：待验证的NTP服务器地址</br>
     * @param timeOut ：待验证的超时时间</br>
     * @return {@link McResultBool} Boolean 封装类 </br>
     * {@link McResultBool#TRUE}: NTP 服务器可用 </br>
     * {@link McResultBool#FALSE}: NTP 服务器不可用 </br>
     */
    public McResultBool checkNtpServerAddressAvailable(String ntpServerAddress, long timeOut) {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McResultBool.DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McResultBool.SERVICE_NOT_START;
        }

        return mcTimeManager.checkNtpServerAddressAvailable(ntpServerAddress, timeOut)
                ? McResultBool.TRUE : McResultBool.FALSE;
    }

    /**
     * 获取当前系统正在使用的 NTP 服务器访问超时时间</br>
     * </br>
     * @return </br>
     * 成功返回对应的 NTP 服务器超时时间，失败返回对应错误码：</br>
     * {@link McErrorCode#ENJOY_COMMON_ERROR_SERVICE_NOT_START}: 相关服务未启动</br>
     */
    public long getNtpTimeout() {
        if (!EnjoyUtil.isEnjoySupport()) {
            return McErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
        }

        if (mcTimeManager == null) {
            return McErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;
        }

        return mcTimeManager.getNtpTimeout();
    }

}
