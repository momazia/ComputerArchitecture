package com.ccnuma.pojo;

/**
 * An enumeration to represent different instruction types.
 * 
 * @author Mahdi Ziaee
 *
 */
public enum InstructionType {

	LOAD("100011"), STORE("101011");

	private String value;

	private InstructionType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	/**
	 * Finds the type of the instruction passed based on the first 6 bits. If it does not match, it returns null.
	 * 
	 * @param instruction
	 * @return
	 */
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
