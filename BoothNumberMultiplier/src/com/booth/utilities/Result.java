package com.booth.utilities;

public class Result {

	private int value;
	private int carryOut;

	public Result(int value, int carryOut) {
		super();
		this.value = value;
		this.carryOut = carryOut;
	}

	public Result() {
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getCarryOut() {
		return carryOut;
	}

	public void setCarryOut(int carryOut) {
		this.carryOut = carryOut;
	}

}
