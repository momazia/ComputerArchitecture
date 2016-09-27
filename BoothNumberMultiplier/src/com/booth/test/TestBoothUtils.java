package com.booth.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.booth.utilities.BoothUtils;
import com.booth.utilities.Result;

public class TestBoothUtils {

	private BoothUtils instance = BoothUtils.getInstance();

	@Test
	public void testNegate() {
		assertEquals(0, instance.negate(1));
		assertEquals(1, instance.negate(0));
	}

	@Test
	public void testOneBitALU_1() {
		Result result = instance.ALU1Bit(0, 0, 0, 0);
		assertEquals(0, result.getValue());
		assertEquals(0, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_2() {
		Result result = instance.ALU1Bit(0, 1, 0, 0);
		assertEquals(1, result.getValue());
		assertEquals(0, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_3() {
		Result result = instance.ALU1Bit(1, 0, 0, 0);
		assertEquals(1, result.getValue());
		assertEquals(0, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_4() {
		Result result = instance.ALU1Bit(1, 1, 0, 0);
		assertEquals(0, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_5() {
		Result result = instance.ALU1Bit(0, 0, 0, 1);
		assertEquals(1, result.getValue());
		assertEquals(0, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_6() {
		Result result = instance.ALU1Bit(1, 0, 0, 1);
		assertEquals(0, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_7() {
		Result result = instance.ALU1Bit(0, 1, 0, 1);
		assertEquals(0, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_8() {
		Result result = instance.ALU1Bit(1, 1, 0, 1);
		assertEquals(1, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_9() {
		Result result = instance.ALU1Bit(0, 0, 1, 1);
		assertEquals(0, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_10() {
		Result result = instance.ALU1Bit(1, 0, 1, 1);
		assertEquals(1, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_11() {
		Result result = instance.ALU1Bit(1, 1, 1, 1);
		assertEquals(0, result.getValue());
		assertEquals(1, result.getCarryOut());
	}

	@Test
	public void testOneBitALU_12() {
		Result result = instance.ALU1Bit(0, 1, 1, 1);
		assertEquals(1, result.getValue());
		assertEquals(0, result.getCarryOut());
	}

	@Test
	public void testGetCurrentBit() {
		assertEquals(0, instance.getBitAt(34, 0));
		assertEquals(1, instance.getBitAt(34, 1));
		assertEquals(0, instance.getBitAt(34, 2));
		assertEquals(0, instance.getBitAt(34, 3));
		assertEquals(0, instance.getBitAt(34, 4));
		assertEquals(1, instance.getBitAt(34, 5));
	}

	@Test
	public void testALU16Bit_1() {
		assertEquals(0, instance.ALU16Bit(0, 0, 0));
		assertEquals(0, instance.ALU16Bit(0, 0, 1));
	}

	@Test
	public void testALU16Bit_2() {
		assertEquals(50, instance.ALU16Bit(20, 30, 0));
		assertEquals(65526, instance.ALU16Bit(20, 30, 1));
	}

	@Test
	public void testALU16Bit_3() {
		try {
			instance.ALU16Bit(32760, 32760, 0);
			fail("An exception must have been thrown!");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}
	
	@Test
	public void testALU16Bit_4() {
		try {
			instance.ALU16Bit(-32767, -32767, 0);
			fail("An exception must have been thrown!");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	@Test
	public void testSign() {
		assertEquals(-1, instance.sign(65526));
		assertEquals(0, instance.sign(0));
		assertEquals(+1, instance.sign(32767));
	}

}
