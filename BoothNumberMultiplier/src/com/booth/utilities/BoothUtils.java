package com.booth.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Main utility class for Booth algorithm, using Singleton design pattern.
 * 
 * @author Max
 *
 */
public class BoothUtils {

	/**
	 * Constants
	 */
	private static final short MAX_BIT = 16;
	private static final short ASCII_shortEGER_INDEX = 48;
	private static final short ONE_BIT_MASK = 1;

	private static BoothUtils instance;

	/**
	 * Private constructor to avoid initializing the utility class
	 */
	private BoothUtils() {
	}

	/**
	 * Returns an instance of this utility class
	 * 
	 * @return
	 */
	public static BoothUtils getInstance() {
		if (instance == null) {
			instance = new BoothUtils();
		}
		return instance;
	}

	/**
	 * The main method which starts the simulation by getting two numbers from
	 * user and runs booth multiplier.
	 * 
	 * @throws IOException
	 */
	public void driver() throws IOException {
		// Getting user's inputs in binary format (string)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter number 1 (binary): ");
		String firstNum = br.readLine();
		System.out.println("Enter number 2 (binary): ");
		String secondNum = br.readLine();
		// Converting the binary strings into short type
		int product = boothMultiplier((short) Integer.parseInt(firstNum, 2), (short) Integer.parseInt(secondNum, 2));
		System.out.println("Result: " + Integer.toBinaryString(product));
	}

	/**
	 * 1 bit ALU sums/subtracts the two given bits based on the operation flag
	 * and carry in bit.
	 * 
	 * @param firstBit
	 * @param secondBit
	 * @param operation
	 *            0 for add, 1 for subtraction
	 * @param carryIn
	 * @return
	 */
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

	/**
	 * 16 bit ALU sums/subtracts the two 16 bit values based on the operation
	 * flag passed. Throws IllegalArgumentException if an overflow happens.
	 * 
	 * @param value1
	 * @param value2
	 * @param operation
	 *            0 for add, 1 for subtraction
	 * @return
	 */
	public short ALU16Bit(final short value1, final short value2, final int operation) {
		int carryIn = operation;
		char[] finalResult = new char[MAX_BIT];
		// Going through the bits in both values, starting for 0 bit and adding
		// them together.
		for (short i = 0; i < MAX_BIT; i++) {
			short value1Bit = getBitAt(value1, i);
			short value2Bit = getBitAt(value2, i);
			Result result = ALU1Bit(value1Bit, value2Bit, operation, carryIn);
			carryIn = result.getCarryOut();
			finalResult[MAX_BIT - i - 1] = (char) (result.getValue() + ASCII_shortEGER_INDEX);
		}
		short finalResultshort = (short) Integer.parseInt(new String(finalResult), 2);
		// Checking the end result to see if an overflow happened
		if (overflow(value1, value2, finalResultshort, operation)) {
			throw new IllegalArgumentException("Bad input entry, overflow happened!");
		}
		return finalResultshort;
	}

	/**
	 * Runs Booth's multiplier algorithm for the two values passed. It also
	 * prints every step into the system console.
	 * 
	 * @param multiplier
	 * @param multiplicand
	 * @return
	 */
	public int boothMultiplier(short multiplier, short multiplicand) {
		short ac = 0, cycleCounter = MAX_BIT;
		short md = multiplicand;
		short mq = multiplier;
		short dj = 0;
		System.out.println("Cycle-counter\tMD\t\t\t\tAC\t\t\t\tMQ\t\t\tMQ_1");
		while (cycleCounter > 0) {
			short dj1 = getBitAt(mq, 0);
			printLine(ac, cycleCounter, md, mq, dj);
			if (dj == 1 && dj1 == 0) {
				// Addition
				ac = ALU16Bit(ac, md, (short) 0); // 0 means add
				printLine(ac, cycleCounter, md, mq, dj);
			} else if (dj == 0 && dj1 == 1) {
				// Subtraction
				ac = ALU16Bit(ac, md, (short) 1); // 1 means sub
				printLine(ac, cycleCounter, md, mq, dj);
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
			printLine(ac, cycleCounter, md, mq, dj);
			cycleCounter--;
		}
		// Combining the two values AC and MQ.
		return new BigInteger(Integer.toBinaryString(0xFFFF & ac) + Integer.toBinaryString(0xFFFF & mq), 2).intValue();
	}

	/**
	 * Prints the parameters passed in a tabular format.
	 * 
	 * @param ac
	 * @param cycleCounter
	 * @param md
	 * @param mq
	 * @param dj1
	 */
	private void printLine(short ac, short cycleCounter, short md, short mq, short dj1) {
		String output = format(cycleCounter, 5);
		output += "\t\t" + format(md, 16);
		output += "\t\t" + format(ac, 16);
		output += "\t\t" + format(mq, 16);
		output += "\t" + dj1;
		System.out.println(output);
	}

	/**
	 * Formats a given short value in binary for the number of bits passed.
	 * 
	 * @param value
	 * @param bits
	 * @return
	 */
	private String format(short value, int bits) {
		return String.format("%" + bits + "s", Integer.toBinaryString(0xFFFF & value)).replace(' ', '0');
	}

	/**
	 * Determines if the value passed is positive, negative or zero.
	 * 
	 * @param value
	 *            1 for +ve, -1 for -ve and zero for zero.
	 * @return
	 */
	public short sign(final short value) {
		if (value == 0)
			return 0;
		if (value >> 15 != 0)
			return -1;
		return +1;
	}

	/**
	 * Checks the values and the final result based on the operation flag passed
	 * to see if an overflow happened.
	 * 
	 * @param value1
	 * @param value2
	 * @param result
	 * @param operation
	 * @return
	 */
	public boolean overflow(final short value1, final short value2, final short result, final int operation) {
		short value1Sign = sign(value1);
		short value2Sign = sign(value2);
		short resultSign = sign(result);
		// If operation was addition
		if (operation == 0) {
			return (value1Sign > 0 && value2Sign > 0 && resultSign < 0)
					|| (value1Sign < 0 && value2Sign < 0 && resultSign > 0);
		}
		// If operation was subtraction
		return (value1Sign > 0 && value2Sign < 0 && resultSign < 0)
				|| (value1Sign < 0 && value2Sign > 0 && resultSign > 0);
	}

	/**
	 * Returns the bit at the index passed for the value.
	 * 
	 * @param value
	 * @param index
	 * @return
	 */
	public short getBitAt(final short value, final int index) {
		return (short) (value >> index & ONE_BIT_MASK);
	}

	/**
	 * Negates the value passed.
	 * 
	 * @param value
	 * @return
	 */
	public short negate(final short value) {
		return (short) (~value & ONE_BIT_MASK);
	}

}
