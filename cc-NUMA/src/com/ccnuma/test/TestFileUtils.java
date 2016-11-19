package com.ccnuma.test;

import static org.junit.Assert.*;

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
			assertEquals(Integer.valueOf(0), instructions.get(0).getNode());
			assertEquals(Integer.valueOf(0), instructions.get(0).getCpu());
			assertEquals(Integer.valueOf(108), instructions.get(0).getIInstruction().getOffset());
			assertEquals(Integer.valueOf(0), instructions.get(0).getIInstruction().getRs());
			assertEquals(Integer.valueOf(17), instructions.get(0).getIInstruction().getRt());
			assertTrue(instructions.get(0).getIInstruction() instanceof LoadInstruction);

			assertEquals(Integer.valueOf(2), instructions.get(1).getNode());
			assertEquals(Integer.valueOf(1), instructions.get(1).getCpu());
			assertEquals(Integer.valueOf(72), instructions.get(1).getIInstruction().getOffset());
			assertEquals(Integer.valueOf(0), instructions.get(1).getIInstruction().getRs());
			assertEquals(Integer.valueOf(18), instructions.get(1).getIInstruction().getRt());
			assertTrue(instructions.get(1).getIInstruction() instanceof StoreInstruction);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
