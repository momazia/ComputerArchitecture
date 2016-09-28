package com.booth.utilities;

import java.math.BigInteger;

public class BoothUtils {

	private static final short MAX_BIT = 16;
	private static final short ASCII_shortEGER_INDEX = 48;
	private static final short ONE_BIT_MASK = 1;
	private static BoothUtils instance;

	private BoothUtils() {
	}

	public static BoothUtils getInstance() {
		if (instance == null) {
			instance = new BoothUtils();
		}
		return instance;
	}

	public Result ALU1Bit(final short firstBit, final short secondBit, final int operation, final int carryIn) {
		short newSecondBit = secondBit;
		if (operation == 1) {
			newSecondBit = negate(secondBit);
		}
		Result result = new Result();
		result.setValue((short) (firstBit ^ newSecondBit ^ carryIn));
		result.setCarryOut((short) ((firstBit & newSecondBit) + (carryIn & (firstBit ^ newSecondBit))));
		return result;
	}

	public short ALU16Bit(final short value1, final short value2, final int operation) {
		int carryIn = operation;
		char[] finalResult = new char[MAX_BIT];
		for (short i = 0; i < MAX_BIT; i++) {
			short value1Bit = getBitAt(value1, i);
			short value2Bit = getBitAt(value2, i);
			Result result = ALU1Bit(value1Bit, value2Bit, operation, carryIn);
			carryIn = result.getCarryOut();
			finalResult[MAX_BIT - i - 1] = (char) (result.getValue() + ASCII_shortEGER_INDEX);
		}
		short finalResultshort = (short) Integer.parseInt(new String(finalResult), 2);
		if (overFlow(value1, value2, finalResultshort, operation)) {
			throw new IllegalArgumentException("Bad input entry, overflow happened!");
		}
		return finalResultshort;
	}

	public int boothMultiplier(short multiplier, short multiplicand) {
		short ac = 0, cycleCounter = MAX_BIT;
		short md = multiplicand;
		short mq = multiplier;
		short dj = 0;
		System.out.println("Cycle-counter\tMD\t\t\t\tAC\t\t\t\tMQ\t\t\tMQ_1");
		while (cycleCounter > 0) {
			short dj1 = getBitAt(mq, 0);
			prshortLine(ac, cycleCounter, md, mq, dj);
			if (dj == 1 && dj1 == 0) {
				// Addition
				ac = ALU16Bit(ac, md, (short) 0); // 0 means add
				prshortLine(ac, cycleCounter, md, mq, dj);
			} else if (dj == 0 && dj1 == 1) {
				// Subtraction
				ac = ALU16Bit(ac, md, (short) 1); // 1 means sub
				prshortLine(ac, cycleCounter, md, mq, dj);
			}
			short ac_0 = getBitAt(ac, 0);
			ac = (short) (ac >> 1);
			mq = (short) (mq >> 1);
			if (ac_0 == 1) {
				mq = (short) (mq | (short) 0x8000); // Changing the sign bit to
													// one.
			} else {
				mq = (short) (mq & (short) 0x7FFF); // Changing the sign bit to
													// zero.
			}
			dj = dj1;
			prshortLine(ac, cycleCounter, md, mq, dj);
			cycleCounter--;
		}
		return new BigInteger(Integer.toBinaryString(0xFFFF & ac) + Integer.toBinaryString(0xFFFF & mq), 2).intValue();
	}

	private void prshortLine(short ac, short cycleCounter, short md, short mq, short dj1) {
		String output = format(cycleCounter, 5);
		output += "\t\t" + format(md, 16);
		output += "\t\t" + format(ac, 16);
		output += "\t\t" + format(mq, 16);
		output += "\t" + dj1;
		System.out.println(output);
	}

	private String format(short value, int bits) {
		return String.format("%" + bits + "s", Integer.toBinaryString(0xFFFF & value)).replace(' ', '0');
	}

	public short sign(final short i) {
		if (i == 0)
			return 0;
		if (i >> 15 != 0)
			return -1;
		return +1;
	}

	public boolean overFlow(final short value1, final short value2, final short result, final int operation) {
		short value1Sign = sign(value1);
		short value2Sign = sign(value2);
		short resultSign = sign(result);
		if (operation == 0) {
			return (value1Sign > 0 && value2Sign > 0 && resultSign < 0)
					|| (value1Sign < 0 && value2Sign < 0 && resultSign > 0);
		}
		return (value1Sign > 0 && value2Sign < 0 && resultSign < 0)
				|| (value1Sign < 0 && value2Sign > 0 && resultSign > 0);
	}

	public short getBitAt(final short value, final int index) {
		return (short) (value >> index & ONE_BIT_MASK);
	}

	public short negate(final short value) {
		return (short) (~value & ONE_BIT_MASK);
	}

}
