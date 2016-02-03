package com.chaipoint.thirdparty.helper.shadowfax;

public class RequestOrder {
	private String store_code;
	private String pickup_contact_number;
	private OrderDetails order_details;
	private CustomerDetails customer_details;

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getPickup_contact_number() {
		return pickup_contact_number;
	}

	public void setPickup_contact_number(String pickup_contact_number) {
		this.pickup_contact_number = pickup_contact_number;
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

}
