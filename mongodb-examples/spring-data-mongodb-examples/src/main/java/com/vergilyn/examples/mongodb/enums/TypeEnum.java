package com.vergilyn.examples.mongodb.enums;

public enum TypeEnum {
	A("A-desc"),
	B("B-desc"),
	C("C-desc"),
	D("D-desc");

	public final String desc;

	TypeEnum(String desc) {
		this.desc = desc;
	}
}
