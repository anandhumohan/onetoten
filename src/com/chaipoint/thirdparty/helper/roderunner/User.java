package com.chaipoint.thirdparty.helper.roderunner;

public class User {
	private String name;
	private String phone_no;
	private String email;
	private String type;
	private String external_id;
	private FullAddress full_address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExternal_id() {
		return external_id;
	}

	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}

	public FullAddress getFull_address() {
		return full_address;
	}

	public void setFull_address(FullAddress full_address) {
		this.full_address = full_address;
	}
}
