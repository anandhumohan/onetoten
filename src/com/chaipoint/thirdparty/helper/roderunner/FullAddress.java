package com.chaipoint.thirdparty.helper.roderunner;

public class FullAddress {
	private String address;
	private Locality locality;
	private SubLocality sub_locality;
	private City city;
	private Geo geo;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Locality getLocality() {
		return locality;
	}

	public void setLocality(Locality locality) {
		this.locality = locality;
	}

	public SubLocality getSub_locality() {
		return sub_locality;
	}

	public void setSub_locality(SubLocality sub_locality) {
		this.sub_locality = sub_locality;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}
}
