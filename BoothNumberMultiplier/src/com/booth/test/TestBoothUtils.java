package com.booth.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.booth.utilities.BoothUtils;
import com.booth.utilities.Result;

public class TestBoothUtils {

	private BoothUtils instance = BoothUtils.getInstance();

	@Test
	public void testNegate() {
		assertEquals(0, instance.negate((short) 1));
		assertEquals(1, instance.negate((short) 0));
	}

	@Test
	public void testOneBitALU_1() {
		Result result = instance.ALU1Bit((short) 0, (short) 0, (short) 0, (short) 0);
		assertEquals(0, result.getValue());
		assertEquals(0, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_2() {
		Result result = instance.ALU1Bit((short) 0, (short) 1, (short) 0, (short) 0);
		assertEquals(1, result.getValue());
		assertEquals(0, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_3() {
		Result result = instance.ALU1Bit((short) 1, (short) 0, (short) 0, (short) 0);
		assertEquals(1, result.getValue());
		assertEquals(0, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_4() {
		Result result = instance.ALU1Bit((short) 1, (short) 1, (short) 0, (short) 0);
		assertEquals(0, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_5() {
		Result result = instance.ALU1Bit((short) 0, (short) 0, (short) 0, (short) 1);
		assertEquals(1, result.getValue());
		assertEquals(0, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_6() {
		Result result = instance.ALU1Bit((short) 1, (short) 0, (short) 0, (short) 1);
		assertEquals(0, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_7() {
		Result result = instance.ALU1Bit((short) 0, (short) 1, (short) 0, (short) 1);
		assertEquals(0, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_8() {
		Result result = instance.ALU1Bit((short) 1, (short) 1, (short) 0, (short) 1);
		assertEquals(1, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_9() {
		Result result = instance.ALU1Bit((short) 0, (short) 0, (short) 1, (short) 1);
		assertEquals(0, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_10() {
		Result result = instance.ALU1Bit((short) 1, (short) 0, (short) 1, (short) 1);
		assertEquals(1, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_11() {
		Result result = instance.ALU1Bit((short) 1, (short) 1, (short) 1, (short) 1);
		assertEquals(0, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_12() {
		Result result = instance.ALU1Bit((short) 0, (short) 1, (short) 1, (short) 1);
		assertEquals(1, result.getValue());
		assertEquals(0, result.getCarryOut());
	}

	@Test
	public void testGetCurrentBit() {
		assertEquals(0, instance.getBitAt((short) 34, 0));
		assertEquals(1, instance.getBitAt((short) 34, 1));
		assertEquals(0, instance.getBitAt((short) 34, 2));
		assertEquals(0, instance.getBitAt((short) 34, 3));
		assertEquals(0, instance.getBitAt((short) 34, 4));
		assertEquals(1, instance.getBitAt((short) 34, 5));
	}

	@Test
	public void testALU16Bit_1() {
		assertEquals(0, instance.ALU16Bit((short) 0, (short) 0, 0));
		assertEquals(0, instance.ALU16Bit((short) 0, (short) 0, 1));
	}

	@Test
	public void testALU16Bit_2() {
		assertEquals(50, instance.ALU16Bit((short) 20, (short) 30, 0));
		assertEquals(-10, instance.ALU16Bit((short) 20, (short) 30, 1));
	}

	@Test
	public void testALU16Bit_3() {
		try {
			instance.ALU16Bit((short) 32760, (short) 32760, 0);
			fail("An exception must have been thrown!");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	@Test
	public void testALU16Bit_4() {
		try {
			instance.ALU16Bit((short) -32767, (short) -32767, 0);
			fail("An exception must have been thrown!");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	@Test
	public void testSign() {
		assertEquals(-1, instance.sign((short) 65526));
		assertEquals(0, instance.sign((short) 0));
		assertEquals(+1, instance.sign((short) 32767));
	}

	@Test
	public void testBoothMultiplier() {
		assertEquals(6, instance.boothMultiplier((short) 2, (short) 3));
	}

}
