package com.ccnuma.main;

import java.io.IOException;
import java.util.List;

import com.ccnuma.pojo.Instruction;
import com.ccnuma.pojo.NUMASystem;
import com.ccnuma.util.FileUtils;
import com.ccnuma.util.SystemSimulatorUtils;

public class MainApplication {

	public static void main(String[] args) throws IOException {

		// Reading the instruction file
		List<Instruction> instructions = FileUtils.getInstance().readInstructionFile("input.txt");

		// Initializing the system
		NUMASystem system = SystemSimulatorUtils.getInstance().initializeSimulator();

		// Accessing costs initialization
		int totalCost = 0;

		// Executing each of the instructions
		for (Instruction instruction : instructions) {
			// Executing the instruction
			Integer accessCost = instruction.getIInstruction().execute(system, instruction.getNode(), instruction.getCpu());
			totalCost += accessCost;
			// Printing the result
			SystemSimulatorUtils.getInstance().print(instruction, accessCost);
			SystemSimulatorUtils.getInstance().print(system);
		}
		System.out.println("Total Cost: " + totalCost + " Average Cost: " + (double) totalCost / instructions.size());
	}

}
