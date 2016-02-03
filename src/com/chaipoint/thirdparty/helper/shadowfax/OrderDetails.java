package com.chaipoint.thirdparty.helper.shadowfax;

public class OrderDetails {
	private String client_order_id ;
    private int order_value ;
    private String paid ;
    private int preparation_time ;
    private String scheduled_time ;
    
	public String getClient_order_id() {
		return client_order_id;
	}
	public void setClient_order_id(String client_order_id) {
		this.client_order_id = client_order_id;
	}
	public int getOrder_value() {
		return order_value;
	}
	public void setOrder_value(int order_value) {
		this.order_value = order_value;
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public int getPreparation_time() {
		return preparation_time;
	}
	public void setPreparation_time(int preparation_time) {
		this.preparation_time = preparation_time;
	}
	public String getScheduled_time() {
		return scheduled_time;
	}
	public void setScheduled_time(String scheduled_time) {
		this.scheduled_time = scheduled_time;
	}
    
    
}
