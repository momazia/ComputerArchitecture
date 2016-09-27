package com.booth.utilities;

public class BoothUtils {

	private static final int ASCII_INTEGER_INDEX = 48;
	private static final int ONE_BIT_MASK = 1;
	private static BoothUtils instance;

	private BoothUtils() {
	}

	public static BoothUtils getInstance() {
		if (instance == null) {
			instance = new BoothUtils();
		}
		return instance;
	}

	public Result ALU1Bit(final int firstBit, final int secondBit, final int operation, final int carryIn) {
		int newSecondBit = secondBit;
		if (operation == 1) {
			newSecondBit = negate(secondBit);
		}
		Result result = new Result();
		result.setValue(firstBit ^ newSecondBit ^ carryIn);
		result.setCarryOut((firstBit & newSecondBit) + (carryIn & (firstBit ^ newSecondBit)));
		return result;
	}

	public int ALU16Bit(final int value1, final int value2, final int operation) {
		int carryIn = operation;
		char[] finalResult = new char[16];
		for (int i = 0; i < 16; i++) {
			int value1Bit = getBitAt(value1, i);
			int value2Bit = getBitAt(value2, i);
			Result result = ALU1Bit(value1Bit, value2Bit, operation, carryIn);
			carryIn = result.getCarryOut();
			finalResult[16 - i - 1] = (char) (result.getValue() + ASCII_INTEGER_INDEX);
		}
		int finalResultInt = Integer.parseInt(new String(finalResult), 2);
		if (overFlow(value1, value2, finalResultInt, operation)) {
			throw new IllegalArgumentException("Bad input entry, overflow happened!");
		}
		return finalResultInt;
	}

	public int sign(int i) {
		if (i == 0)
			return 0;
		if (i >> 15 != 0)
			return -1;
		return +1;
	}

	public boolean overFlow(final int value1, final int value2, final int result, final int operation) {
		int value1Sign = sign(value1);
		int value2Sign = sign(value2);
		int resultSign = sign(result);
		if (operation == 0) {
			return (value1Sign > 0 && value2Sign > 0 && resultSign < 0)
					|| (value1Sign < 0 && value2Sign < 0 && resultSign > 0);
		}
		return (value1Sign > 0 && value2Sign < 0 && resultSign < 0)
				|| (value1Sign < 0 && value2Sign > 0 && resultSign > 0);
	}

	public int getBitAt(int value, int index) {
		return value >> index & ONE_BIT_MASK;
	}

	public int negate(int value) {
		return ~value & ONE_BIT_MASK;
	}

}
