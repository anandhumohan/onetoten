package com.chaipoint.thirdparty.helper.roderunner;

import java.util.List;

public class OrderDetails {
	private String order_id;
	private String order_value;
	private String amount_to_be_collected;
	private OrderType order_type;
	private List<OrderItems> order_items;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_value() {
		return order_value;
	}

	public void setOrder_value(String order_value) {
		this.order_value = order_value;
	}

	public String getAmount_to_be_collected() {
		return amount_to_be_collected;
	}

	public void setAmount_to_be_collected(String amount_to_be_collected) {
		this.amount_to_be_collected = amount_to_be_collected;
	}

	public OrderType getOrder_type() {
		return order_type;
	}

	public void setOrder_type(OrderType order_type) {
		this.order_type = order_type;
	}

	public List<OrderItems> getOrder_items() {
		return order_items;
	}

	public void setOrder_items(List<OrderItems> order_items) {
		this.order_items = order_items;
	}

}
