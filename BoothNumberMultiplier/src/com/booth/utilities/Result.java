package com.booth.utilities;

public class Result {

	private short value;
	private short carryOut;

	public Result(short value, short carryOut) {
		super();
		this.value = value;
		this.carryOut = carryOut;
	}

	public Result() {
	}

	public short getValue() {
		return value;
	}

	public void setValue(short value) {
		this.value = value;
	}

	public short getCarryOut() {
		return carryOut;
	}

	public void setCarryOut(short carryOut) {
		this.carryOut = carryOut;
	}

}
