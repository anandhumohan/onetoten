package com.chaipoint.dpselection;

import java.util.Date;

public class ConfirmedOrderStatus {

	private String orderId;
	private boolean canClub;
	private String location;
	private String latitude;
	private String longitude;
	private Date confirmTime;
	private int extimatedPrepTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean isCanClub() {
		return canClub;
	}

	public void setCanClub(boolean canClub) {
		this.canClub = canClub;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public int getExtimatedPrepTime() {
		return extimatedPrepTime;
	}

	public void setExtimatedPrepTime(int extimatedPrepTime) {
		this.extimatedPrepTime = extimatedPrepTime;
	}

}
