package com.ccnuma.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.ccnuma.pojo.Instruction;
import com.ccnuma.util.FileUtils;

public class TestFileUtils {

	@Test
	public void testReadInstructionFile() throws IOException {
		try {
			List<Instruction> instructions = FileUtils.getInstance().readInstructionFile("test.txt");
			assertEquals("00", instructions.get(0).getNode());
			assertEquals("0", instructions.get(0).getCpu());
			assertEquals("8C11006C", instructions.get(0).getInstruction());
			assertEquals("10", instructions.get(1).getNode());
			assertEquals("1", instructions.get(1).getCpu());
			assertEquals("AC110048", instructions.get(1).getInstruction());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
