package com.chaipoint.dppojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TIME_DISTANCE_TRAVEL")
public class TimeSliceDistanceTravel {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "STORE_ID")
	private String storeId;

	@Column(name = "FIRST_SLICE")
	private int firstSlice;

	@Column(name = "SECOND_SLICE")
	private int secondSlice;

	@Column(name = "THIRD_SLICE")
	private int thirdSlice;

	@Column(name = "FOURTH_SLICE")
	private int fourthSlice;

	@Column(name = "FIFTH_SLICE")
	private int fifthSlice;

	public int getId() {
		return id;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public int getFirstSlice() {
		return firstSlice;
	}

	public void setFirstSlice(int firstSlice) {
		this.firstSlice = firstSlice;
	}

	public int getSecondSlice() {
		return secondSlice;
	}

	public void setSecondSlice(int secondSlice) {
		this.secondSlice = secondSlice;
	}

	public int getThirdSlice() {
		return thirdSlice;
	}

	public void setThirdSlice(int thirdSlice) {
		this.thirdSlice = thirdSlice;
	}

	public int getFourthSlice() {
		return fourthSlice;
	}

	public void setFourthSlice(int fourthSlice) {
		this.fourthSlice = fourthSlice;
	}

	public int getFifthSlice() {
		return fifthSlice;
	}

	public void setFifthSlice(int fifthSlice) {
		this.fifthSlice = fifthSlice;
	}

}
