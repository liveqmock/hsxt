package com.gy.hsxt.access.web.aps.controllers.debit;

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

	Menu(String name, String value) {
		this.name = name;
		this.value = value;
	}
}
