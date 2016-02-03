package com.chaipoint.thirdparty.helper.shadowfax;

public class Data {
	private String status ;
    private String store_code ;
    private OrderDetails order_details ;
    private CustomerDetails customer_details ;
    private String pickup_contact_number ;
    private String sfx_order_id ;
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStore_code() {
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	public OrderDetails getOrder_details() {
		return order_details;
	}
	public void setOrder_details(OrderDetails order_details) {
		this.order_details = order_details;
	}
	public CustomerDetails getCustomer_details() {
		return customer_details;
	}
	public void setCustomer_details(CustomerDetails customer_details) {
		this.customer_details = customer_details;
	}
	public String getPickup_contact_number() {
		return pickup_contact_number;
	}
	public void setPickup_contact_number(String pickup_contact_number) {
		this.pickup_contact_number = pickup_contact_number;
	}
	public String getSfx_order_id() {
		return sfx_order_id;
	}
	public void setSfx_order_id(String sfx_order_id) {
		this.sfx_order_id = sfx_order_id;
	}
    
    
}
