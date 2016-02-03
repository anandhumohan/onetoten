package com.chaipoint.dpselection;

import java.util.Date;

public class TimeCalculator {
	private String DPId;
	private String TimeSlice;
	private int TimeForUnitKm;
	private Date expectedReturnTIme;

	public String getDPId() {
		return DPId;
	}

	public void setDPId(String dPId) {
		DPId = dPId;
	}

	public String getTimeSlice() {
		return TimeSlice;
	}

	public void setTimeSlice(String timeSlice) {
		TimeSlice = timeSlice;
	}

	public int getTimeForUnitKm() {
		return TimeForUnitKm;
	}

	public void setTimeForUnitKm(int timeForUnitKm) {
		TimeForUnitKm = timeForUnitKm;
	}

	public Date getExpectedReturnTIme() {
		return expectedReturnTIme;
	}

	public void setExpectedReturnTIme(Date expectedReturnTIme) {
		this.expectedReturnTIme = expectedReturnTIme;
	}

}
