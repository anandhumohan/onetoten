package com.chaipoint.dppojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_CODE_CATEGORY")
public class ItemCodeCategory {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "ITEM_ID")
	private String itemId;

	@Column(name = "CATEGORY")
	private String category;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}

}
