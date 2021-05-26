package com.mc.android.mcethernet;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 以太网配置类</br>
 * 包括以太网的拨号方式、IPv4 地址信息、子网掩码、以太网网关地址、DNS地址和备用DNS地址。</br>
 * </br>
 * 具体用于： </br>
 * {@link com.mc.enjoysdk.McEthernet#setEthernetConfig(McEthernetConfig)} 方法的参数中</br>
 * {@link com.mc.enjoysdk.McEthernet#getMcEthernetConfig()} 方法的返回值中</br>
 */
public class McEthernetConfig implements Parcelable {
    /**
     * 以太网模式： DHCP 模式
     */
    public final static int DHCP_MODE = 1;
    /**
     * 以太网模式: 静态模式
     */
    public final static int STATIC_MODE = 2;
    /**
     * 以太网模式: PPPOE 模式
     */
    public final static int PPPOE_MODE = 3;
    /**
     * 以太网模式: 未知模式
     */
    public final static int UNKNOWN = 4;
    /**
     * 空 IP
     */
    public final static String NULL_IP_INFO = "0.0.0.0";

    /**
     * 以太网拨号模式</br>
     * {@link McEthernetConfig#DHCP_MODE}: DHCP 模式</br>
     * {@link McEthernetConfig#STATIC_MODE}: 静态模式</br>
     * {@link McEthernetConfig#PPPOE_MODE}: PPPOE 拨号模式</br>
     * {@link McEthernetConfig#UNKNOWN}: 未知模式</br>
     * 四种模式，EnjoySDK V1.0.0 版本只支持 {@link McEthernetConfig#DHCP_MODE} 和 {@link McEthernetConfig#STATIC_MODE}</br>
     */
    private int mode;
    /**
     * 以太网 IPv4 地址</br>
     * 当 {@link McEthernetConfig#mode} 为 {@link McEthernetConfig#STATIC_MODE} 时才会生效</br>
     */
    private String ipV4Address;
    /**
     * 以太网子网掩码</br>
     * 当 {@link McEthernetConfig#mode} 为 {@link McEthernetConfig#STATIC_MODE} 时才会生效</br>
     */
    private String subnetMask;
    /**
     * 以太网网关</br>
     * 当 {@link McEthernetConfig#mode} 为 {@link McEthernetConfig#STATIC_MODE} 时才会生效</br>
     */
    private String gateway;
    /**
     * 以太网 DNS 地址</br>
     * 当 {@link McEthernetConfig#mode} 为 {@link McEthernetConfig#STATIC_MODE} 时才会生效</br>
     */
    private String Dns;
    /**
     * 以太网 DNS 备用地址</br>
     * 当 {@link McEthernetConfig#mode} 为 {@link McEthernetConfig#STATIC_MODE} 时才会生效</br>
     */
    private String backupDns;

    public McEthernetConfig() {
    }

    public McEthernetConfig(int mode, String ipV4Address, String subnetMask, String gateway, String dns, String backupDns) {
        this.mode = mode;
        this.ipV4Address = ipV4Address;
        this.subnetMask = subnetMask;
        this.gateway = gateway;
        this.Dns = dns;
        this.backupDns = backupDns;
    }

    protected McEthernetConfig(Parcel in) {
        mode = in.readInt();
        ipV4Address = in.readString();
        subnetMask = in.readString();
        gateway = in.readString();
        Dns = in.readString();
        backupDns = in.readString();
    }

    public static final Creator<McEthernetConfig> CREATOR = new Creator<McEthernetConfig>() {
        @Override
        public McEthernetConfig createFromParcel(Parcel in) {
            return new McEthernetConfig(in);
        }

        @Override
        public McEthernetConfig[] newArray(int size) {
            return new McEthernetConfig[size];
        }
    };

    /**
     * 获取以太网的连接模式，模式由 {@link McEthernetConfig#setMode(int)} 进行设置</br>
     * @return 返回以太网连接模式状态码</br>
     * {@link McEthernetConfig#DHCP_MODE}: DHCP 模式</br>
     * {@link McEthernetConfig#STATIC_MODE}: 静态模式</br>
     * {@link McEthernetConfig#PPPOE_MODE}: PPPOE 拨号模式</br>
     * {@link McEthernetConfig#UNKNOWN}: 未知模式</br>
     */
    public int getMode() {
        return mode;
    }

    /**
     * 设置以太网的连接模式</br>
     * @param mode 模式状态码</br>
     * {@link McEthernetConfig#DHCP_MODE}: DHCP 模式</br>
     * {@link McEthernetConfig#STATIC_MODE}: 静态模式</br>
     * {@link McEthernetConfig#PPPOE_MODE}: PPPOE 拨号模式</br>
     * {@link McEthernetConfig#UNKNOWN}: 未知模式</br>
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * 获取以太网配置 IPv4 地址</br>
     * @return 返回IPv4 地址字符串，默认返回 "0.0.0.0" {@link McEthernetConfig#NULL_IP_INFO}</br>
     */
    public String getIpV4Address() {
        return ipV4Address;
    }

    /**
     * 设置以太网的 IPv4 地址到以太网配置类中</br>
     * @param ipV4Address IPv4 地址字符串</br>
     */
    public void setIpV4Address(String ipV4Address) {
        this.ipV4Address = ipV4Address;
    }

    /**
     * 获取以太网配置中的子网掩码</br>
     * @return 返回以太网配置中的子网掩码字符串，默认返回 "0.0.0.0" {@link McEthernetConfig#NULL_IP_INFO}</br>
     */
    public String getSubnetMask() {
        return subnetMask;
    }

    /**
     * 设置以太网配置中的子网掩码</br>
     * @param subnetMask 子网掩码字符串</br>
     */
    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    /**
     * 获取以太网配置中的网关地址</br>
     * @return 返回以太网配置中的网关地址，默认返回 "0.0.0.0" {@link McEthernetConfig#NULL_IP_INFO}</br>
     */
    public String getGateway() {
        return gateway;
    }

    /**
     * 设置以太网配置中的网关地址</br>
     * @param gateway 网关地址字符串</br>
     */
    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    /**
     * 获取以太网配置中的 DNS 地址</br>
     * @return 返回以太网配置中的DNS地址，默认返回 "0.0.0.0" {@link McEthernetConfig#NULL_IP_INFO}</br>
     */
    public String getDns() {
        return Dns;
    }

    /**
     * 设置以太网配置中的DNS地址</br>
     * @param dns DNS地址字符串</br>
     */
    public void setDns(String dns) {
        Dns = dns;
    }

    /**
     * 获取以太网配置中的备用 DNS 地址</br>
     * @return 返回以太网配置中的备用DNS地址，默认返回 "0.0.0.0" {@link McEthernetConfig#NULL_IP_INFO}</br>
     */
    public String getBackupDns() {
        return backupDns;
    }

    /**
     * 设置以太网配置中的备用DNS地址</br>
     * @param backupDns 备用DNS地址字符串</br>
     */
    public void setBackupDns(String backupDns) {
        this.backupDns = backupDns;
    }


    @Override
    public String toString() {
        return "McEthernetConfig{" +
                "mode=" + mode +
                ", IPv4Address='" + ipV4Address + '\'' +
                ", subnetMask='" + subnetMask + '\'' +
                ", gateway ='" + gateway + '\'' +
                ", Dns='" + Dns + '\'' +
                ", backupDns='" + backupDns + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mode);
        parcel.writeString(ipV4Address);
        parcel.writeString(subnetMask);
        parcel.writeString(gateway);
        parcel.writeString(Dns);
        parcel.writeString(backupDns);
    }
}
