package com.ccnuma.pojo;

public enum InstructionType {

	LOAD("100011"), STORE("101011");

	private String value;

	private InstructionType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static InstructionType findValueOf(String instruction) {
		if (instruction == null) {
			return null;
		}
		for (InstructionType type : InstructionType.values()) {
			if (instruction.substring(0, 6).equals(type.getValue())) {
				return type;
			}
		}
		return null;
	}
}
