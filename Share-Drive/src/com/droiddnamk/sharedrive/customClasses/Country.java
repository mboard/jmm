package com.droiddnamk.sharedrive.customClasses;

public class Country {
	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getDesc() {
		return desc;
	}

	public final void setDesc(String desc) {
		this.desc = desc;
	}

	String id,desc,code;

	public Country(String id, String desc, String code) {
		super();
		this.id = id;
		this.desc = desc;
		this.code = code;
	}

	public final String getCode() {
		return code;
	}

	public final void setCode(String code) {
		this.code = code;
	}
}
