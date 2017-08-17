package com.gy.hsxt.access.web.bean;

public class Menu {
	String name;
	String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Menu(String name, String value) {
		this.name = name;
		this.value = value;
	}
}
