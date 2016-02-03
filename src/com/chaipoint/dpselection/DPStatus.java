package com.chaipoint.dpselection;

import java.util.Date;

public class DPStatus {
	private String DPname;
	private int noOfOrdersDelivered;
	private Date lastDispatchTime;
	private boolean isBusy;
	private int lastTotalDistance;
	private Date lastDeliveryTime;

	public Date getLastDeliveryTime() {
		return lastDeliveryTime;
	}

	public void setLastDeliveryTime(Date lastDeliveryTime) {
		this.lastDeliveryTime = lastDeliveryTime;
	}

	public int getNoOfOrdersDelivered() {
		return noOfOrdersDelivered;
	}

	public void setNoOfOrdersDelivered(int noOfOrdersDelivered) {
		this.noOfOrdersDelivered = noOfOrdersDelivered;
	}

	public Date getLastDispatchTime() {
		return lastDispatchTime;
	}

	public void setLastDispatchTime(Date lastDispatchTime) {
		this.lastDispatchTime = lastDispatchTime;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public String getDPname() {
		return DPname;
	}

	public void setDPname(String dPname) {
		DPname = dPname;
	}

	public int getLastTotalDistance() {
		return lastTotalDistance;
	}

	public void setLastTotalDistance(int lastTotalDistance) {
		this.lastTotalDistance = lastTotalDistance;
	}

}
