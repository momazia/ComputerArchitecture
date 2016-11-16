package com.ccnuma.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.ccnuma.pojo.Instruction;
import com.ccnuma.pojo.LoadInstruction;
import com.ccnuma.pojo.StoreInstruction;
import com.ccnuma.util.FileUtils;

public class TestFileUtils {

	@Test
	public void testReadInstructionFile() throws IOException {
		try {
			List<Instruction> instructions = FileUtils.getInstance().readInstructionFile("test.txt");
			assertEquals("00", instructions.get(0).getNode());
			assertEquals("0", instructions.get(0).getCpu());
			assertEquals("1", instructions.get(0).getInstruction().getDestination());
			assertEquals(Integer.valueOf(108), instructions.get(0).getInstruction().getTarget());
			assertTrue(instructions.get(0).getInstruction() instanceof LoadInstruction);
			assertEquals("10", instructions.get(1).getNode());
			assertEquals("1", instructions.get(1).getCpu());
			assertEquals("2", instructions.get(1).getInstruction().getDestination());
			assertEquals(Integer.valueOf(72), instructions.get(1).getInstruction().getTarget());
			assertTrue(instructions.get(1).getInstruction() instanceof StoreInstruction);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
