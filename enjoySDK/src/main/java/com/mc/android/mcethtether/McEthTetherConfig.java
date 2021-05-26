package com.mc.android.mcethtether;

import android.os.Parcel;
import android.os.Parcelable;

import com.mc.android.enjoy.EnjoyErrorCode;
import com.mc.enjoysdk.transform.McErrorCode;

/**
 * <p>以太网共享的配置信息类。</p>
 * </br>
 * <p>在以太网共享配置信息类中可以配置网关属性、DHCP服务属性、DNS服务属性。</p>
 * </br>
 */
public class McEthTetherConfig implements Parcelable {
    /**
     * 网关 IP 地址
     */
    private String gatewayAddr;
    /**
     * 网关掩码长度
     */
    private int prefixLength;
    /**
     * 暂不可用，需配置为 0
     */
    private int leaseTime;
    /**
     * 网关 DHCP 服务地址池起始值
     */
    private String dhcpStartAddr;
    /**
     * 网关 DHCP 服务地址池末尾值
     */
    private String dhcpEndAddr;
    /**
     * 网关 DNS 地址
     */
    private String defaultDns;

    public McEthTetherConfig() {
    }

    public McEthTetherConfig(String gatewayAddr, int prefixLength, String startAddr,
                             String endAddr) {
        this(gatewayAddr, prefixLength, startAddr, endAddr, null, 2);
    }

    public McEthTetherConfig(String gatewayAddr, int prefixLength, String startAddr,
                             String endAddr, int leaseTime) {
        this(gatewayAddr, prefixLength, startAddr, endAddr, null, leaseTime);
    }

    /**
     * 以太网共享的配置信息类的构造函数
     * @param gatewayAddr 网关的IP地址
     * @param prefixLength 网关的掩码长度
     * @param startAddr 网关的DHCP服务地址池起始值
     * @param endAddr 网关的DHCP服务地址池末尾值
     * @param dns 网关的DNS地址
     * @param leaseTime 暂不可用，需要配置为 0
     */
    public McEthTetherConfig(String gatewayAddr, int prefixLength, String startAddr,
                             String endAddr, String dns, int leaseTime) {}

    /**
     * 配置网关的IP地址 </br>
     * </br>
     * @param gatewayAddr 网关的IP地址 </br>
     */
    public void setGatewayAddr(String gatewayAddr) {
    }

    /**
     * 获取网关的IP地址 </br>
     * </br>
     * @return 网关的IP地址 </br>
     */
    public String getGatewayAddr() {
        return null;
    }

    /**
     * 配置网关的子网掩码长度 </br>
     * </br>
     * @param prefixLength 子网掩码长度 </br>
     */
    public void setPrefixLength(int prefixLength) {
    }

    /**
     * 获取网关的子网掩码长度 </br>
     * </br>
     * @return 网关的子网掩码长度 </br>
     */
    public int getPrefixLength() {
        return McErrorCode.ENJOY_COMMON_ERROR_UNKNOWN;
    }

    /**
     * 配置DHCP地址池的起始值 </br>
     * </br>
     * @param startAddr DHCP地址池的起始值 </br>
     */
	public void setDhcpStartAddr(String startAddr) {
	}

    /**
     * 获取DHCP地址池的起始值 </br>
     * </br>
     * @return DHCP地址的起始值 </br>
     */
	public String getDhcpStartAddr() {
        return null;
	}

    /**
     * 配置DHCP地址池的末尾值 </br>
     * </br>
     * @param endAddr DHCP地址池的末尾值 </br>
     */
	public void setDhcpEndAddr(String endAddr) {
	}

    /**
     * 获取DHCP地址池的末尾值 </br>
     * </br>
     * @return DHCP地址池的末尾值 </br>
     */
	public String getDhcpEndAddr() {
        return null;
	}

    /**
     * 配置网关使用的DNS地址 </br>
     * </br>
     * @param dns DNS地址 </br>
     */
	public void setDnsAddr(String dns) {
	}

    /**
     * 获取网关的DNS地址 </br>
     * </br>
     * @return DNS地址 </br>
     */
	public String getDnsAddr() {
        return null;
	}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gatewayAddr);
        dest.writeInt(this.prefixLength);
        dest.writeInt(this.leaseTime);
        dest.writeString(this.dhcpStartAddr);
        dest.writeString(this.dhcpEndAddr);
        dest.writeString(this.defaultDns);
    }

    protected McEthTetherConfig(Parcel in) {
        this.gatewayAddr = in.readString();
        this.prefixLength = in.readInt();
        this.leaseTime = in.readInt();
        this.dhcpStartAddr = in.readString();
        this.dhcpEndAddr = in.readString();
        this.defaultDns = in.readString();
    }

    public static final Creator<McEthTetherConfig> CREATOR = new Creator<McEthTetherConfig>() {
        public McEthTetherConfig createFromParcel(Parcel source) {
            return new McEthTetherConfig(source);
        }

        public McEthTetherConfig[] newArray(int size) {
            return new McEthTetherConfig[size];
        }
    };
}
