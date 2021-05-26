package com.mc.android.mchardwarestatusmanager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * cpu 信息类，主要包括了 cpu 温度和占用率.</br>
 * 具体用于：</br>
 * {@link com.mc.enjoysdk.McHardwareStatus#getCPUStatus()} 的返回值.</br>
 */
public class McCpuStatus implements Parcelable {
	/**
	 * cpu 温度</br>
	 */
	private float temperature;

	/**
	 * cpu 占用率，取值范围为 0-1</br>
	 */
	private float utilization;


	/**
	 * 获取 cpu 温度值</br>
	 * </br>
	 * @return 返回获取到的 cpu 温度</br>
	 */
	public float getTemperature(){
		return -1;
	}

	/**
	 * 获取 cpu 使用率的值</br>
	 * </br>
	 * @return 返回获取到的 cpu 的使用率的值，取值范围为 0-1.</br>
	 */
	public float getUtilization(){
		return -1;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(this.temperature);
		dest.writeFloat(this.utilization);
	}

	public McCpuStatus() {
	}

	protected McCpuStatus(Parcel in) {
		this.temperature = in.readFloat();
		this.utilization = in.readFloat();
	}

	public static final Creator<McCpuStatus> CREATOR = new Creator<McCpuStatus>() {
		public McCpuStatus createFromParcel(Parcel source) {
			return new McCpuStatus(source);
		}

		public McCpuStatus[] newArray(int size) {
			return new McCpuStatus[size];
		}
	};

	@Override
	public String toString() {
		return "McCpuStatus{temperature=" + temperature +", utilization=" + utilization+"}";
	}
}
