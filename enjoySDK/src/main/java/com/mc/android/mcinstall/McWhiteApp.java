package com.mc.android.mcinstall;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 白名单应用信息类，主要包括每个应用的安装模式、是否允许卸载、安装后是否自动打开的配置 </br>
 * </br>
 * 具体用于：</br>
 * {@link com.mc.enjoysdk.McInstall#addWhiteList(ArrayList)} 方法参数 </br>
 * {@link com.mc.enjoysdk.McInstall#removeWhiteList(ArrayList)} 方法参数 </br>
 * {@link com.mc.enjoysdk.McInstall#getWhiteList()} 返回值 </br>
 */
public class McWhiteApp implements Parcelable {
    /**
     * 应用安装模式: 前台安装, 显示安装确认弹框 </br>
     */
    public static final int INSTALL_MODE_FOREGROUND = 0;

    /**
     * 应用安装模式: 后台安装，即静默安装 </br>
     */
    public static final int INSTALL_MODE_BACKGROUND = 1;

    /**
     * 应用包名 </br>
     * 保持唯一，系统会根据包名判断应用是否在白名单中 </br>
     */
    private String packageName;

    /**
     * 应用安装模式 </br>
     * </br>
     * {@link McWhiteApp#INSTALL_MODE_FOREGROUND} 前台安装模式，显示安装确认弹框</br>
     * {@link McWhiteApp#INSTALL_MODE_BACKGROUND} 后台安装模式，即静默安装</br>
     */
    private int installMode;

    /**
     * 是否允许卸载 </br>
     */
    private boolean allowUninstall;

    /**
     * 安装后是否自动打开应用 </br>
     */
    private boolean openAfterInstall;

    /**
     * 获取应用包名 </br>
     * @return 应用包名 </br>
     */
    public String getPackageName() {
        return packageName == null ? "" : packageName;
    }

    /**
     * 设置应用包名 </br>
     * @param packageName 应用包名 </br>
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * 获取应用安装模式 </br>
     * @return 应用安装模式 </br>
     * {@link McWhiteApp#INSTALL_MODE_FOREGROUND} 前台安装模式，显示安装确认弹框</br>
     * {@link McWhiteApp#INSTALL_MODE_BACKGROUND} 后台安装模式，即静默安装</br>
     */
    public int getInstallMode() {
        return installMode;
    }

    /**
     * 设置应用安装模式 </br>
     * @param installMode 应用安装模式 </br>
     * {@link McWhiteApp#INSTALL_MODE_FOREGROUND} 前台安装模式，显示安装确认弹框</br>
     * {@link McWhiteApp#INSTALL_MODE_BACKGROUND} 后台安装模式，即静默安装</br>
     */
    public void setInstallMode(int installMode) {
        this.installMode = installMode;
    }

    /**
     * 判断是否允许卸载 </br>
     * @return 是否允许卸载 </br>
     */
    public boolean isAllowUninstall() {
        return allowUninstall;
    }

    /**
     * 设置是否允许卸载 </br>
     * @param allowUninstall 是否允许卸载 </br>
     */
    public void setAllowUninstall(boolean allowUninstall) {
        this.allowUninstall = allowUninstall;
    }

    /**
     * 判断安装后是否自动打开 </br>
     * @return 安装后是否自动打开 </br>
     */
    public boolean isOpenAfterInstall() {
        return openAfterInstall;
    }

    /**
     * 设置安装后是否自动打开 </br>
     * @param openAfterInstall 安装后是否自动打开 </br>
     */
    public void setOpenAfterInstall(boolean openAfterInstall) {
        this.openAfterInstall = openAfterInstall;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeInt(this.installMode);
        dest.writeByte(allowUninstall ? (byte) 1 : (byte) 0);
        dest.writeByte(openAfterInstall ? (byte) 1 : (byte) 0);
    }

    public McWhiteApp() {
    }

    protected McWhiteApp(Parcel in) {
        this.packageName = in.readString();
        this.installMode = in.readInt();
        this.allowUninstall = in.readByte() != 0;
        this.openAfterInstall = in.readByte() != 0;
    }

    public static final Creator<McWhiteApp> CREATOR = new Creator<McWhiteApp>() {
        public McWhiteApp createFromParcel(Parcel source) {
            return new McWhiteApp(source);
        }

        public McWhiteApp[] newArray(int size) {
            return new McWhiteApp[size];
        }
    };
}

