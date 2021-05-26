package com.mc.enjoysdk.transform;

import com.mc.android.enjoy.EnjoyErrorCode;

/**
 * EnjoySdk 错误码声明 </br>
 * </br>
 * 格式： ENJOY_${Manager}_ERROR_${Error Type} </br>
 */
public class McErrorCode {
    /*************************************************************************************************/
    /*          错误码统一类                                                                          */
    /*          格式： ENJOY_${Manager}_ERROR_${Error Type} 所有字母大写                               */
    /*          1、所有错误码必须为负数                                                                */
    /*          2、所有错误码不能相同                                                                  */
    /*          3、所有错误码必须在指定的区间内进行分配                                                 */
    /*************************************************************************************************/

    /*************************************************************************************************/
    /*          0:successful, -1 ~ -100 Enjoy COMMON ERROR CODE                                      */
    /*************************************************************************************************/
    /**
     * 执行成功
     */
    public final static int ENJOY_COMMON_SUCCESSFUL
            = EnjoyErrorCode.ENJOY_COMMON_SUCCESSFUL;
    /**
     * 未知错误
     */
    public final static int ENJOY_COMMON_ERROR_UNKNOWN
            = EnjoyErrorCode.ENJOY_COMMON_ERROR_UNKNOWN;
    /**
     * 硬件设备不支持
     */
    public final static int ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT
            = EnjoyErrorCode.ENJOY_COMMON_ERROR_DEVICE_NOT_SUPPORT;
    /**
     * 当前系统不支持
     */
    public final static int ENJOY_COMMON_ERROR_SDK_NOT_SUPPORT
            = EnjoyErrorCode.ENJOY_COMMON_ERROR_SDK_NOT_SUPPORT;
    /**
     * 写入设置失败
     */
    public final static int ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR
            = EnjoyErrorCode.ENJOY_COMMON_ERROR_WRITE_SETTINGS_ERROR;
    /**
     * 相关服务未启动
     */
    public final static int ENJOY_COMMON_ERROR_SERVICE_NOT_START
            = EnjoyErrorCode.ENJOY_COMMON_ERROR_SERVICE_NOT_START;

    /*************************************************************************************************/
    /*                        -101 ~ -200 Enjoy EthernetManager ERROR CODE                           */
    /*************************************************************************************************/
    /**
     * 以太网配置信息有误
     */
    public final static int ENJOY_ETHERNET_MANAGER_ERROR_CONFIG_INVALID
            = EnjoyErrorCode.ENJOY_ETHERNET_MANAGER_ERROR_CONFIG_INVALID;
    /**
     * 以太网共享功能正在使用，此时无法使用 EthernetManager 相关接口服务
     */
    public final static int ENJOY_ETHERNET_MANAGER_ERROR_ETH_TETHER_IN_USE
            = EnjoyErrorCode.ENJOY_ETHERNET_MANAGER_ERROR_ETH_TETHER_IN_USE;

    /*************************************************************************************************/
    /*                        -201 ~ -300 Enjoy PowerManager ERROR CODE                              */
    /*************************************************************************************************/

    /*************************************************************************************************/
    /*                          -301 ~ -400 Enjoy SecureManager ERROR CODE                           */
    /*************************************************************************************************/
    /**
     * SecurePassword 密码检查失败
     */
    public final static int ENJOY_SECURE_MANAGER_ERROR_PASSWD_CHECK_FAILED
            = EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_CHECK_FAILED;
    /**
     * SecurePassword 密码格式错误
     */
    public final static int ENJOY_SECURE_MANAGER_ERROR_PASSWD_FORMAT_ERROR
            = EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_FORMAT_ERROR;
    /**
     * SecurePassword 密码已被清空
     */
    public final static int ENJOY_SECURE_MANAGER_ERROR_PASSWD_ALREADY_EMPYT
            = EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_ALREADY_EMPYT;
    /**
     * 设置 SecurePassword 密码失败
     */
    public final static int ENJOY_SECURE_MANAGER_ERROR_PASSWD_SET_FAILED
            = EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_SET_FAILED;
    /**
     * SecurePassword 未初始化
     */
    public final static int ENJOY_SECURE_MANAGER_ERROR_PASSWD_NOT_INIT
            = EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PASSWD_NOT_INIT;
    /**
     * 注册为 SafeProgram 失败
     */
    public final static int ENJOY_SECURE_MANAGER_ERROR_REGISTER_SAFE_PROGRAM_FAILED
            = EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_REGISTER_SAFE_PROGRAM_FAILED;
    /**
     * 从 SafeProgram 列表中注销失败
     */
    public final static int ENJOY_SECURE_MANAGER_ERROR_UNREGISTER_SAFE_PROGRAM_FAILED
            = EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_UNREGISTER_SAFE_PROGRAM_FAILED;
    /**
     * 当前应用不在 SafeProgram 列表内
     */
    public final static int ENJOY_SECURE_MANAGER_ERROR_PROGRAM_NOT_IN_SAFE_PROGRAM_LIST
            = EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PROGRAM_NOT_IN_SAFE_PROGRAM_LIST;
    /**
     * 当前应用已经在 SafeProgram 列表内
     */
    public final static int ENJOY_SECURE_MANAGER_ERROR_PROGRAM_ALREADY_IN_SAFE_PROGRAM_LIST
            = EnjoyErrorCode.ENJOY_SECURE_MANAGER_ERROR_PROGRAM_ALREADY_IN_SAFE_PROGRAM_LIST;

    /*************************************************************************************************/
    /*                               -401 ~ -500 Enjoy SystemUI ERROR CODE                           */
    /*************************************************************************************************/

    /*************************************************************************************************/
    /*                               -501 ~ -600 Enjoy TimeManager ERROR CODE                        */
    /*************************************************************************************************/
    /**
     * 时间参数不符合规范
     */
    public final static int ENJOY_TIME_MANAGER_ERROR_PARAMETER_ERROR
            = EnjoyErrorCode.ENJOY_TIME_MANAGER_ERROR_PARAMETER_ERROR;
    /**
     * 功能复用，网络时间核对开启时无法进行时间设置，自动校准时区功能开启时无法进行时区设置
     */
    public final static int ENJOY_TIME_MANAGER_ERROR_FUNCTION_OCCUPY
            = EnjoyErrorCode.ENJOY_TIME_MANAGER_ERROR_FUNCTION_OCCUPY;
    /**
     * NTP 服务器地址设置失败
     */
    public final static int ENJOY_TIME_MANAGER_ERROR_NTP_SERVER_ADDRESS_SET_FAILED
            = EnjoyErrorCode.ENJOY_TIME_MANAGER_ERROR_NTP_SERVER_ADDRESS_SET_FAILED;
    /**
     * NTP 时间获取超时时间设置失败
     */
    public final static int ENJOY_TIME_MANAGER_ERROR_NTP_TIMEOUT_SET_FAILED
            = EnjoyErrorCode.ENJOY_TIME_MANAGER_ERROR_NTP_TIMEOUT_SET_FAILED;
    /**
     * NTP 服务器地址和时间获取超时时间设置同时失败
     */
    public final static int ENJOY_TIME_MANAGER_ERROR_NTP_CONFIG_SET_FAILED
            = EnjoyErrorCode.ENJOY_TIME_MANAGER_ERROR_NTP_CONFIG_SET_FAILED;


    /*************************************************************************************************/
    /*                               -601 ~ -700 Enjoy InstallManager ERROR CODE                     */
    /*************************************************************************************************/
    /**
     * 添加白名单应用失败
     */
    public static final int ENJOY_INSTALL_MANAGER_ERROR_WHITE_ADD
            = EnjoyErrorCode.ENJOY_INSTALL_MANAGER_ERROR_WHITE_ADD;
    /**
     * 删除白名单应用失败
     */
    public static final int ENJOY_INSTALL_MANAGER_ERROR_WHITE_REMOVE
            = EnjoyErrorCode.ENJOY_INSTALL_MANAGER_ERROR_WHITE_REMOVE;

    /*************************************************************************************************/
    /*                               -701 ~ -800 Enjoy RotationManager ERROR CODE                    */
    /*************************************************************************************************/
    /**
     * 主屏幕应用方向错误
     */
    public static final int ENJOY_ROTATION_MANAGER_ERROR_DISPLAY_ROTATION
            = EnjoyErrorCode.ENJOY_ROTATION_MANAGER_ERROR_DISPLAY_ROTATION;
    /**
     * 主屏幕系统方向错误
     */
    public static final int ENJOY_ROTATION_MANAGER_ERROR_SYSTEM_ROTATION
            = EnjoyErrorCode.ENJOY_ROTATION_MANAGER_ERROR_SYSTEM_ROTATION;

    /**
     * 副屏幕方向错误
     */
    public static final int ENJOY_ROTATION_MANAGER_ERROR_VICE_ROTATION
            = EnjoyErrorCode.ENJOY_ROTATION_MANAGER_ERROR_VICE_ROTATION;
    /**
     * 屏幕方向参数不符合规范
     */
    public static final int ENJOY_ROTATION_MANAGER_ERROR_ROTATION_FORMAT
            = EnjoyErrorCode.ENJOY_ROTATION_MANAGER_ERROR_ROTATION_FORMAT;
    /**
     * 副屏幕全屏错误
     */
    public static final int ENJOY_ROTATION_MANAGER_ERROR_VICE_FULL
            = EnjoyErrorCode.ENJOY_ROTATION_MANAGER_ERROR_VICE_FULL;

    /*************************************************************************************************/
    /*                               -801 ~ -900 Enjoy BootanimationManager ERROR CODE               */
    /*************************************************************************************************/
    /**
     * 开机动画文件不存在
     */
	public final static int ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_NOT_EXIST
            = EnjoyErrorCode.ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_NOT_EXIST;
    /**
     * 开机动画文件检测失败，开机动画文件格式不对
     */
	public final static int ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_CHECK_FAILED
            = EnjoyErrorCode.ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_CHECK_FAILED;
    /**
     * 拷贝开机动画文件失败
     */
	public final static int ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_COPY_FAILED
            = EnjoyErrorCode.ENJOY_BOOTANIMATION_MANAGER_ERROR_FILE_COPY_FAILED;
    /**
     * 参数错误，参数为 null，即传入的开机动画路径参数为 null
     */
	public final static int ENJOY_BOOTANIMATION_MANAGER_ERROR_PARAMETER_WRONG
            = EnjoyErrorCode.ENJOY_BOOTANIMATION_MANAGER_ERROR_PARAMETER_WRONG;
    /**
     * 无法删除开机动画文件
     */
    public final static int ENJOY_BOOTANIMATION_MANAGER_ERROR_CAN_NOT_DELETE_FILE
            = EnjoyErrorCode.ENJOY_BOOTANIMATION_MANAGER_ERROR_CAN_NOT_DELETE_FILE;
	
    /*************************************************************************************************/
    /*                               -901 ~ -1000 Enjoy HomeManager ERROR CODE                       */
    /*************************************************************************************************/

    /*************************************************************************************************/
    /*                               -1001 ~ -1100 Enjoy HardwareStatusManagerService ERROR CODE     */
    /*************************************************************************************************/

    /*************************************************************************************************/
    /*                               -1101 ~ -1200 Enjoy HardwareKeyBoardService ERROR CODE          */
    /*************************************************************************************************/


    /*************************************************************************************************/
    /*                               -1201 ~ -1300 Enjoy WatchDogManager ERROR CODE                  */
    /*************************************************************************************************/
    /**
     * 看门狗未初始化
     */
	public static final int ENJOY_WATCHDOG_MANAGER_ERROR_NOT_INIT
            = EnjoyErrorCode.ENJOY_WATCHDOG_MANAGER_ERROR_NOT_INIT;
    /**
     * 看门狗已初始化，无需再次初始化
     */
	public static final int ENJOY_WATCHDOG_MANAGER_ERROR_INIT_AGAIN
            = EnjoyErrorCode.ENJOY_WATCHDOG_MANAGER_ERROR_INIT_AGAIN;
    /**
     * 无效的看门狗超时时间
     */
	public static final int ENJOY_WATCHDOG_MANAGER_ERROR_INVALID_TIMEOUT
            = EnjoyErrorCode.ENJOY_WATCHDOG_MANAGER_ERROR_INVALID_TIMEOUT;
    /**
     * 打开看门狗节点失败
     */
	public static final int ENJOY_WATCHDOG_MANAGER_ERROR_OPEN_DEV
            = EnjoyErrorCode.ENJOY_WATCHDOG_MANAGER_ERROR_OPEN_DEV;
    /**
     * 配置看门狗超时时间失败
     */
	public static final int ENJOY_WATCHDOG_MANAGER_ERROR_SET_TIMEOUT_ERROR
            = EnjoyErrorCode.ENJOY_WATCHDOG_MANAGER_ERROR_SET_TIMEOUT_ERROR;

    /*************************************************************************************************/
    /*                               -1301 ~ -1400 Enjoy EthTetherManager ERROR CODE                 */
    /*************************************************************************************************/
    /**
     * 无效的以太网共享源，请在 WIFI 和 4G 中选择
     */
    public static final int ENJOY_ETHTETHER_MANAGER_ERROR_INVALID_UPSTREAM
            = EnjoyErrorCode.ENJOY_ETHTETHER_MANAGER_ERROR_INVALID_UPSTREAM;
    /**
     * 无效的以太网共享配置
     */
    public static final int ENJOY_ETHTETHER_MANAGER_ERROR_INVALID_CONFIG
            = EnjoyErrorCode.ENJOY_ETHTETHER_MANAGER_ERROR_INVALID_CONFIG;

    /*************************************************************************************************/
    /*                               -1401 ~ -1500 Enjoy NetCoexistence ERROR CODE                   */
    /*************************************************************************************************/

}


