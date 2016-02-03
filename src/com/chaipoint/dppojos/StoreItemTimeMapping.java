package com.chaipoint.dppojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ITEM_TIME_MAPPING")
public class StoreItemTimeMapping {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "STORE_ID")
	private String storeId;

	@Column(name = "CB_TIME")
	private int cbTime;

	@Column(name = "HB_TIME")
	private int hbTime;

	@Column(name = "TBP_TIME")
	private int tbpTime;

	@Column(name = "RMF_TIME")
	private int rmfTime;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public int getCbTime() {
		return cbTime;
	}

	public void setCbTime(int cbTime) {
		this.cbTime = cbTime;
	}

	public int getHbTime() {
		return hbTime;
	}

	public void setHbTime(int hbTime) {
		this.hbTime = hbTime;
	}

	public int getTbpTime() {
		return tbpTime;
	}

	public void setTbpTime(int tbpTime) {
		this.tbpTime = tbpTime;
	}

	public int getRmfTime() {
		return rmfTime;
	}

	public void setRmfTime(int rmfTime) {
		this.rmfTime = rmfTime;
	}

	public int getId() {
		return id;
	}

}
