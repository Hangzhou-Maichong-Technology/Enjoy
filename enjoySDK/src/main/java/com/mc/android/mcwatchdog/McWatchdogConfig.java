package com.mc.android.mcwatchdog;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>看门狗的配置类。</p>
 * 目前可以配置看门狗的超时时间，单位为秒
 */
public class McWatchdogConfig implements Parcelable {
	/**
	 * 看门狗超时时间, 单位为秒 </br>
	 */
	private int timeout;

	/**
	 * 构造函数 </br>
	 */
	public McWatchdogConfig() {
	}

	/**
	 * 构造函数 </br>
	 * @param timeout 超时时间 </br>
	 */
	public McWatchdogConfig(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * 配置看门狗超时时间 </br>
	 * @param timeout 超时时间，单位为秒 </br>
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * 获取看门狗超时时间 </br>
	 * @return 超时时间 </br>
	 */
	public int getTimeout() {
		return timeout;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.timeout);
	}

	protected McWatchdogConfig(Parcel in) {
		this.timeout = in.readInt();
	}

	public static final Creator<McWatchdogConfig> CREATOR = new Creator<McWatchdogConfig>() {
		public McWatchdogConfig createFromParcel(Parcel source) {
			return new McWatchdogConfig(source);
		}

		public McWatchdogConfig[] newArray(int size) {
			return new McWatchdogConfig[size];
		}
	};

	@Override
	public String toString() {
		return "McWatchdogConfig{" +
				"timeout = " + timeout +
				'}';
	}
}

