package com.chaipoint.thirdparty.helper.roderunner;

public class ShipResponse {
	private Status status;
	private boolean new_trip;
	private String driver_name;
	private String driver_phone;
	private String order_id;
	private String delivery_id;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isNew_trip() {
		return new_trip;
	}

	public void setNew_trip(boolean new_trip) {
		this.new_trip = new_trip;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getDriver_phone() {
		return driver_phone;
	}

	public void setDriver_phone(String driver_phone) {
		this.driver_phone = driver_phone;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

}
