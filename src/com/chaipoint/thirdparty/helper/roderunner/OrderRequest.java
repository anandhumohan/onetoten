package com.chaipoint.thirdparty.helper.roderunner;

public class OrderRequest {
	private Pickup pickup;
	private Drop drop;
	private OrderDetails order_details;
	private String created_at;
	private String callback_url;

	public Pickup getPickup() {
		return pickup;
	}

	public void setPickup(Pickup pickup) {
		this.pickup = pickup;
	}

	public Drop getDrop() {
		return drop;
	}

	public void setDrop(Drop drop) {
		this.drop = drop;
	}

	public OrderDetails getOrder_details() {
		return order_details;
	}

	public void setOrder_details(OrderDetails order_details) {
		this.order_details = order_details;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getCallback_url() {
		return callback_url;
	}

	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}

}
