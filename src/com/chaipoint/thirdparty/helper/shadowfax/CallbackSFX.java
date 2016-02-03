package com.chaipoint.thirdparty.helper.shadowfax;

public class CallbackSFX {
	private String sfx_order_id;
	private String order_status;
	private String allot_time;
	private String dispatch_time;
	private String delivery_time;
	private String cancel_reason;
	private String rider_name;
	private long rider_contact;

	public String getSfx_order_id() {
		return sfx_order_id;
	}

	public void setSfx_order_id(String sfx_order_id) {
		this.sfx_order_id = sfx_order_id;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getAllot_time() {
		return allot_time;
	}

	public void setAllot_time(String allot_time) {
		this.allot_time = allot_time;
	}

	public String getDispatch_time() {
		return dispatch_time;
	}

	public void setDispatch_time(String dispatch_time) {
		this.dispatch_time = dispatch_time;
	}

	public String getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(String delivery_time) {
		this.delivery_time = delivery_time;
	}

	public String getCancel_reason() {
		return cancel_reason;
	}

	public void setCancel_reason(String cancel_reason) {
		this.cancel_reason = cancel_reason;
	}

	public String getRider_name() {
		return rider_name;
	}

	public void setRider_name(String rider_name) {
		this.rider_name = rider_name;
	}

	public long getRider_contact() {
		return rider_contact;
	}

	public void setRider_contact(long rider_contact) {
		this.rider_contact = rider_contact;
	}

}
