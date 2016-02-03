package com.chaipoint.thirdparty.helper.roderunner;

public class Serviceable {

	public boolean serviceable;

	public ExpectedTimeForPickup expected_time_for_pickup;

	public ExpectedTimeToDrop expected_time_to_drop;

	public TotalTime total_time;

	public boolean isServiceable() {
		return serviceable;
	}

	public void setServiceable(boolean serviceable) {
		this.serviceable = serviceable;
	}

	public ExpectedTimeForPickup getExpected_time_for_pickup() {
		return expected_time_for_pickup;
	}

	public void setExpected_time_for_pickup(ExpectedTimeForPickup expected_time_for_pickup) {
		this.expected_time_for_pickup = expected_time_for_pickup;
	}

	public ExpectedTimeToDrop getExpected_time_to_drop() {
		return expected_time_to_drop;
	}

	public void setExpected_time_to_drop(ExpectedTimeToDrop expected_time_to_drop) {
		this.expected_time_to_drop = expected_time_to_drop;
	}

	public TotalTime getTotal_time() {
		return total_time;
	}

	public void setTotal_time(TotalTime total_time) {
		this.total_time = total_time;
	}

}
