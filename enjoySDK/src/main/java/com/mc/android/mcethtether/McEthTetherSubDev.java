package com.mc.android.mcethtether;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>以太网共享中子网设备的信息类。</p>
 * <p>可以在本类中查看每个子网设备的相关信息，比如IP地址、MAC地址、是否在线等。</p>
 */
public class McEthTetherSubDev implements Parcelable{

	/**
	 * 设备处于未知的状态，一般一个新设备初始化时为此状态
	 */
	public static final int NETWORK_STATE_UNKNOWN = 0;
	/**
	 * 设备处于在线状态
	 */
	public static final int NETWORK_STATE_ONLINE = 1;
	/**
	 * 设备处于离线状态
	 */
	public static final int NETWORK_STATE_OFFLINE = 2;

	/**
	 * 子网设备的 IP 地址
	 */
	private String ipAddress = "";

	/**
	 * 子网设备的 MAC 地址
	 */
	private String hwAddress = "";

	/**
	 * 子网设备的主机名
	 */
	private String hostname;

	/**
	 * 子网设备的剩余租期时间
	 */
	private long remainingLeaseTime;

	/**
	 * 查询在线时的网络延迟时间
	 */
	private float delayTime = 0;

	/**
	 * 子网设备的网络状态
	 */
	private int netState = NETWORK_STATE_UNKNOWN;


	public McEthTetherSubDev() {
	}

	public McEthTetherSubDev(String ipAddress, String hwAddress, String hostname,
							 long remaining, float delayTime, int netState) {
		this.ipAddress = ipAddress;
		this.hwAddress = hwAddress;
		this.hostname = hostname;
		this.remainingLeaseTime = remaining;
		this.delayTime = delayTime;
		this.netState = netState;
	}

	public McEthTetherSubDev(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	protected McEthTetherSubDev(Parcel in) {
		ipAddress = in.readString();
		hwAddress = in.readString();
		hostname = in.readString();
		remainingLeaseTime = in.readLong();
		delayTime = in.readFloat();
		netState = in.readInt();
	}

	public static final Creator<McEthTetherSubDev> CREATOR = new Creator<McEthTetherSubDev>() {
		@Override
		public McEthTetherSubDev createFromParcel(Parcel in) {
			return new McEthTetherSubDev(in);
		}

		@Override
		public McEthTetherSubDev[] newArray(int size) {
			return new McEthTetherSubDev[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(ipAddress);
		dest.writeString(hwAddress);
		dest.writeString(hostname);
		dest.writeLong(remainingLeaseTime);
		dest.writeFloat(delayTime);
		dest.writeInt(netState);
	}

	public void readFromParcel(Parcel in) {
		ipAddress = in.readString();
		hwAddress = in.readString();
		hostname = in.readString();
		remainingLeaseTime = in.readLong();
		delayTime = in.readFloat();
		netState = in.readInt();
	}

	/**
	 * 设置子网设备的 IP 地址
	 * @param ipAddr IP 地址
	 * @return 是否设置成功 true: 成功 false: 失败
	 */
	public boolean setIpAddr(String ipAddr) {
		this.ipAddress = ipAddr;
		return true;
	}

	/**
	 * 获取子网设备的 IP 地址 </br>
	 * </br>
	 * @return 子网设备的IP地址 </br>
	 */
	public String getIpAddr() {
		return ipAddress;
	}

	/**
	 * 设置子网设备的 MAC 地址
	 * @param hwAddr MAC 地址
	 * @return 是否设置成功 true: 成功 false: 失败
	 */
	public boolean setHwAddr(String hwAddr) {
		this.hwAddress = hwAddr;
		return true;
	}

	/**
	 * 获取子网设备的 MAC 地址 </br>
	 * </br>
	 * @return 子网设备的MAC地址 </br>
	 */
	public String getHwAddr() {
		return hwAddress;
	}

	/**
	 * 设置子网设备的主机名
	 * @param hostname 主机名
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * 获取子网设备的主机名 </br>
	 * </br>
	 * @return 子网设备的主机名 </br>
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * 设置子网设备的剩余租期时间
	 * @param leaseTime 剩余租期时间
	 * @return 是否设置成功 true: 成功 false: 失败
	 */
	public boolean setRemainingTime(long leaseTime) {
		this.remainingLeaseTime = leaseTime;
		return true;
	}

	/**
	 * 获取子网设备的剩余租期时间 </br>
	 * </br>
	 * @return 剩余租期时间 </br>
	 */
	public long getRemainingTime() {
		return remainingLeaseTime;
	}

	/**
	 * 设置子网设备的网络状态
	 * @param netState 网络状态
	 */
	public void setNetState(int netState) {
		this.netState = netState;
	}

	/**
	 * 查询子网设备的网络状态 </br>
	 * </br>
	 * @return 子网设备的网站状态值，可能为{@link McEthTetherSubDev#NETWORK_STATE_OFFLINE}、
	 * {@link McEthTetherSubDev#NETWORK_STATE_ONLINE}或者{@link McEthTetherSubDev#NETWORK_STATE_UNKNOWN} </br>
	 */
	public int getNetState() {
		return netState;
	}

	/**
	 * 设置子网设备的网络延迟时间
	 * @param delayTime 网络延迟时间
	 */
	public void setDelayTime(float delayTime) {
		this.delayTime = delayTime;
	}

	/**
	 * 查询在线时的网络延迟时间 </br>
	 * </br>
	 * @return 网络延迟时间 </br>
	 */
	public float getDelayTime() {
		return delayTime;
	}

	@Override
	public String toString() {
		return "Device{" +
				"ip='" + ipAddress + '\'' +
				", hostname='" + hostname + '\'' +
				", mac='" + hwAddress + '\'' +
				", delayTime=" + delayTime + '\'' +
				", leaseTime=" + remainingLeaseTime + '\'' +
				", state=" + netState +
				'}';
	}
}

