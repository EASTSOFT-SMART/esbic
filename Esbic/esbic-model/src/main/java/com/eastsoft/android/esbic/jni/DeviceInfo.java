package com.eastsoft.android.esbic.jni;

import com.eastsoft.android.esbic.util.JsonUtil;

public class DeviceInfo
{
	private int device_type;
	private int building_no;
	private byte unit_no;
	private byte layer_no;
	private byte room_no;
	private int dev_no;

	public DeviceInfo()
	{

	}

	public DeviceInfo(int device_type, int building_no, byte unit_no, byte layer_no, byte room_no, int dev_no) {
		this.device_type = device_type;
		this.building_no = building_no;
		this.unit_no = unit_no;
		this.layer_no = layer_no;
		this.room_no = room_no;
		this.dev_no = dev_no;
	}

	public void setDevice_type(int device_type) {
		this.device_type = device_type;
	}

	public void setBuilding_no(int building_no) {
		this.building_no = building_no;
	}

	public void setUnit_no(byte unit_no) {
		this.unit_no = unit_no;
	}

	public void setLayer_no(byte layer_no) {
		this.layer_no = layer_no;
	}

	public void setRoom_no(byte room_no) {
		this.room_no = room_no;
	}

	public void setDev_no(int dev_no) {
		this.dev_no = dev_no;
	}

	public int getDevice_type() {

		return device_type;
	}

	public int getBuilding_no() {
		return building_no;
	}

	public byte getUnit_no() {
		return unit_no;
	}

	public byte getLayer_no() {
		return layer_no;
	}

	public byte getRoom_no() {
		return room_no;
	}

	public int getDev_no() {
		return dev_no;
	}

	@java.lang.Override
	public java.lang.String toString()
	{
		return JsonUtil.toString(this);
	}
}