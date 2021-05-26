package com.mc.enjoysdk.transform;


import com.mc.android.mcsystemui.McSystemUiManager;

/**
 * {@link McSystemUiFlag} 状态栏/导航栏控制 Flag 类 </br>
 */
public class McSystemUiFlag {
    /**
     *  {@link com.mc.enjoysdk.McSystemUi#getNavigationShowStatus()} 和 {@link com.mc.enjoysdk.McSystemUi#getStatusBarShowStatus()} 方法的返回值</br>
     *  状态为未知</br>
     */
    public final static int UNKNOWN_STATUS = McSystemUiManager.UNKNOWN_STATUS;

    /**
     *  {@link com.mc.enjoysdk.McSystemUi#getNavigationShowStatus()} 和 {@link com.mc.enjoysdk.McSystemUi#getStatusBarShowStatus()} 方法的返回值</br>
     *  当前状态为正在显示</br>
     */
    public final static int SHOWING = McSystemUiManager.SHOWING;

    /**
     *  {@link com.mc.enjoysdk.McSystemUi#getNavigationShowStatus()} 和 {@link com.mc.enjoysdk.McSystemUi#getStatusBarShowStatus()} 方法的返回值</br>
     *  当前状态为被隐藏</br>
     */
    public final static int HIDEING = McSystemUiManager.HIDEING;

    /**
     * 使用：作为 {@link com.mc.enjoysdk.McSystemUi#disableStatusBarItem(int flag)} 的参数，如果 flag & {@link McSystemUiFlag#DISABLE_EXPAND} 不等于 0 时，表明该值生效。</br>
     * 作用：在状态栏未被隐藏的情况下，禁止状态栏被下拉</br>
     */
    public static final int DISABLE_EXPAND = McSystemUiManager.DISABLE_EXPAND;

    /**
     * 使用：作为 {@link com.mc.enjoysdk.McSystemUi#disableStatusBarItem(int flag)} 的参数，如果 flag & {@link McSystemUiFlag#DISABLE_NOTIFICATION_ICONS} 不等于 0 时，表明该值生效。</br>
     * 作用：在状态栏未被隐藏的情况下，禁止通知图标显示</br>
     */
    public static final int DISABLE_NOTIFICATION_ICONS = McSystemUiManager.DISABLE_NOTIFICATION_ICONS;

    /**
     * 使用：作为 {@link com.mc.enjoysdk.McSystemUi#disableStatusBarItem(int flag)} 的参数，如果 flag & {@link McSystemUiFlag#DISABLE_NOTIFICATION_ICONS} 不等于 0 时，表明该值生效。</br>
     * 作用：在状态栏未被隐藏的情况下，禁止通知弹出</br>
     */
    public static final int DISABLE_NOTIFICATION_ALERTS = McSystemUiManager.DISABLE_NOTIFICATION_ALERTS;

    /**
     * 使用：作为 {@link com.mc.enjoysdk.McSystemUi#disableStatusBarItem(int flag)} 的参数，如果 flag & {@link McSystemUiFlag#DISABLE_SYSTEM_INFO} 不等于 0 时，表明该值生效。</br>
     * 作用：在状态栏未被隐藏的情况下，禁止系统信息显示，比如电池电量图标、wifi图标、蓝牙图标等等</br>
     */
    public static final int DISABLE_SYSTEM_INFO = McSystemUiManager.DISABLE_SYSTEM_INFO;

    /**
     * 使用：作为 {@link com.mc.enjoysdk.McSystemUi#disableStatusBarItem(int flag)} 的参数，如果 flag & {@link McSystemUiFlag#DISABLE_HOME} 不等于 0 时，表明该值生效。</br>
     * 作用：在导航栏未被隐藏的情况下，禁止显示导航栏中的 Home(主界面) 按钮</br>
     */
    public static final int DISABLE_HOME = McSystemUiManager.DISABLE_HOME;

    /**
     * 使用：作为 {@link com.mc.enjoysdk.McSystemUi#disableStatusBarItem(int flag)} 的参数，如果 flag & {@link McSystemUiFlag#DISABLE_RECENT} 不等于 0 时，表明该值生效。</br>
     * 作用：在导航栏未被隐藏的情况下，禁止显示导航栏中的 Recent(最近任务) 按钮</br>
     */
    public static final int DISABLE_RECENT = McSystemUiManager.DISABLE_RECENT;

    /**
     * 使用：作为 {@link com.mc.enjoysdk.McSystemUi#disableStatusBarItem(int flag)} 的参数，如果 flag & {@link McSystemUiFlag#DISABLE_BACK} 不等于 0 时，表明该值生效。</br>
     * 作用：在导航栏未被隐藏的情况下，禁止显示导航栏中的 Back(返回) 按钮</br>
     */
    public static final int DISABLE_BACK = McSystemUiManager.DISABLE_BACK;

    /**
     * 使用：作为 {@link com.mc.enjoysdk.McSystemUi#disableStatusBarItem(int flag)} 的参数，如果 flag & {@link McSystemUiFlag#DISABLE_CLOCK} 不等于 0 时，表明该值生效。</br>
     * 作用：在状态栏未被隐藏的情况下，禁止显示状态栏中的时间信息</br>
     */
    public static final int DISABLE_CLOCK = McSystemUiManager.DISABLE_CLOCK;

    /**
     * 使用：作为 {@link com.mc.enjoysdk.McSystemUi#disableStatusBarItem(int flag)} 的参数，如果 flag & {@link McSystemUiFlag#DISABLE_SEARCH} 不等于 0 时，表明该值生效。</br>
     * 作用：禁止使用搜索框</br>
     */
    public static final int DISABLE_SEARCH = McSystemUiManager.DISABLE_SEARCH;

    /**
     * 使用：作为 {@link com.mc.enjoysdk.McSystemUi#disableStatusBarItem(int flag)} 的参数。</br>
     * 作用：取消对所有功能的禁止，让所有被禁止的功能都能正常使用。</br>
     */
    public static final int DISABLE_NONE = McSystemUiManager.DISABLE_NONE;
}
