package com.chaipoint.dpselection;

import java.util.ArrayList;

public class OrderRootObject {
	private String storeId;
	private ArrayList<ItemDetails> itemDetails;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public ArrayList<ItemDetails> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(ArrayList<ItemDetails> itemDetails) {
		this.itemDetails = itemDetails;
	}

}
