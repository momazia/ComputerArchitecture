package com.ccnuma.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.ccnuma.pojo.Instruction;

public class FileUtils {

	private static final String IO_PATH = "../cc-NUMA/io/";

	/**
	 * static Singleton instance
	 */
	private static FileUtils instance;

	/**
	 * Private constructor for singleton
	 */
	private FileUtils() {
	}

	/**
	 * Static getter method for retrieving the singleton instance
	 */
	public static FileUtils getInstance() {
		if (instance == null) {
			instance = new FileUtils();
		}
		return instance;
	}

	public List<Instruction> readInstructionFile(String fileName) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(FileUtils.IO_PATH + fileName));
		List<Instruction> instructions = new ArrayList<>();
		for (String line : lines) {
			instructions.add(new Instruction(Integer.parseInt(line.substring(0, 2), 2), Integer.parseInt(line.substring(2, 3), 2), line.substring(5)));
		}
		return instructions;
	}

}
