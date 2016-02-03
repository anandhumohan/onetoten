package com.chaipoint.thirdparty.helper.shadowfax;

public class ConfirmOrder {
	private int totalPayableAmount;
	private int cposOrderId;
	private int isPaid;
	private String shipOrderId;
	private String deliveryCompany;

	public String getDeliveryCompany() {
		return deliveryCompany;
	}

	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}

	public String getShipOrderId() {
		return shipOrderId;
	}

	public void setShipOrderId(String shipOrderId) {
		this.shipOrderId = shipOrderId;
	}

	public int getTotalPayableAmount() {
		return totalPayableAmount;
	}

	public void setTotalPayableAmount(int totalPayableAmount) {
		this.totalPayableAmount = totalPayableAmount;
	}

	public int getCposOrderId() {
		return cposOrderId;
	}

	public void setCposOrderId(int cposOrderId) {
		this.cposOrderId = cposOrderId;
	}

	public int getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(int isPaid) {
		this.isPaid = isPaid;
	}

}
