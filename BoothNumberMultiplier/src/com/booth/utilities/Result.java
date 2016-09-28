package com.booth.utilities;

/**
 * The main POJO which holds the result of the multiplication.
 * 
 * @author Max
 *
 */
public class Result {

	private short value;
	private short carryOut;

	/**
	 * Constructor which sets the parameters.
	 * 
	 * @param value
	 * @param carryOut
	 */
	public Result(short value, short carryOut) {
		super();
		this.value = value;
		this.carryOut = carryOut;
	}

	/**
	 * Default constructor.
	 */
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
