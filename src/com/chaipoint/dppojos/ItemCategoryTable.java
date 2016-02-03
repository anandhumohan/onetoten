package com.chaipoint.dppojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_CATEGORY_TABLE")
public class ItemCategoryTable {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "ITEM_ID")
	private String itemId;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	public int getId() {
		return id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
