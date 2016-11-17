package com.ccnuma.util;

import java.util.HashMap;
import java.util.Map;

import com.ccnuma.pojo.CPU;
import com.ccnuma.pojo.CacheEntry;
import com.ccnuma.pojo.DirectoryEntry;
import com.ccnuma.pojo.DirectoryEntryState;
import com.ccnuma.pojo.NUMASystem;
import com.ccnuma.pojo.Node;

public class SystemSimulatorUtils {

	private static final int WORD_SIZE = 32;
	private static final int NUMBER_OF_WORDS_IN_MEMORY = 16;
	private static final int NUMBER_OF_NODES = 4;
	private static final int NUMBER_OF_REGISTERS = 2;
	private static final int NUMBER_OF_CPUS = 2;
	private static final int NUMBER_OF_CACHE_ROWS = 4;
	/**
	 * static Singleton instance
	 */
	private static SystemSimulatorUtils instance;

	/**
	 * Private constructor for singleton
	 */
	private SystemSimulatorUtils() {
	}

	/**
	 * Static getter method for retrieving the singleton instance
	 */
	public static SystemSimulatorUtils getInstance() {
		if (instance == null) {
			instance = new SystemSimulatorUtils();
		}
		return instance;
	}

	public NUMASystem initializeSimulator() {
		NUMASystem system = new NUMASystem();
		system.setNodes(initializeNodes());
		return system;
	}

	public Map<Integer, Node> initializeNodes() {
		Map<Integer, Node> nodes = new HashMap<>();
		for (int i = 0; i < NUMBER_OF_NODES; i++) {
			nodes.put(i, initializeNode(i));
		}
		return nodes;
	}

	private Node initializeNode(int nodeNumber) {
		Node node = new Node();
		node.setCpus(initializeCPUs());
		node.setDirectoryEntries(initializeDirectoryEntries(nodeNumber));
		node.setMemoryBlocks(initializeMemoryBlocks(nodeNumber));
		return node;
	}

	public Map<Integer, Integer> initializeMemoryBlocks(int nodeNumber) {
		Map<Integer, Integer> result = new HashMap<>();
		int startIndex = nodeNumber * NUMBER_OF_WORDS_IN_MEMORY;
		int endIndex = startIndex + NUMBER_OF_WORDS_IN_MEMORY;
		for (int i = startIndex; i < endIndex; i++) {
			result.put(i, i + 5);
		}
		return result;
	}

	private Map<Integer, DirectoryEntry> initializeDirectoryEntries(int nodeNumber) {
		Map<Integer, DirectoryEntry> entries = new HashMap<>();
		int startIndex = nodeNumber * NUMBER_OF_WORDS_IN_MEMORY;
		int endIndex = startIndex + NUMBER_OF_WORDS_IN_MEMORY;
		for (int i = startIndex; i < endIndex; i++) {
			entries.put(i, initializeDirectoryEntry());
		}
		return entries;
	}

	private DirectoryEntry initializeDirectoryEntry() {
		DirectoryEntry entry = new DirectoryEntry();
		Map<Integer, Integer> values = new HashMap<>();
		for (int i = 0; i < NUMBER_OF_NODES; i++) {
			values.put(i, 0);
		}
		entry.setValues(values);
		entry.setState(DirectoryEntryState.UN_CACHED);
		return entry;
	}

	public Map<String, CPU> initializeCPUs() {
		Map<String, CPU> cpus = new HashMap<>();
		for (int i = 0; i < NUMBER_OF_CPUS; i++) {
			CPU cpu = new CPU();
			cpu.setRegisters(initializeRegisters());
			cpu.setCacheEntries(initializeCacheEntries());
			cpus.put(Integer.toString(i), cpu);
		}
		return cpus;
	}

	private Map<Integer, CacheEntry> initializeCacheEntries() {
		Map<Integer, CacheEntry> entries = new HashMap<>();
		for (int i = 0; i < NUMBER_OF_CACHE_ROWS; i++) {
			entries.put(i, new CacheEntry());
		}
		return entries;
	}

	private Map<Integer, Integer> initializeRegisters() {
		Map<Integer, Integer> registers = new HashMap<>();
		for (int i = 1; i <= NUMBER_OF_REGISTERS; i++) {
			registers.put(i, 0);
		}
		return registers;
	}

	public String initializeValueWithZeros(int size) {
		String result = "";
		for (int i = 0; i < size; i++) {
			result += "0";
		}
		return result;
	}

	public void print(NUMASystem system) {

		Map<Integer, Node> nodes = system.getNodes();
		// Looping through the nodes
		for (int i = 0; i < NUMBER_OF_NODES; i++) {
			String nodeNumber = format(i, 2);
			System.out.println("\t------------------------- Node (" + nodeNumber + ") --------------------------");

			// Going through the CPUs
			for (String cpuNumber : nodes.get(i).getCpus().keySet()) {

				CPU cpu = nodes.get(i).getCpus().get(cpuNumber);
				System.out.println("CPU " + cpuNumber + ":");

				// Printing the registers
				System.out.println("\tRegister:");
				for (Integer registerNumber : cpu.getRegisters().keySet()) {
					System.out.println("\t\tS" + registerNumber + ": " + format(cpu.getRegisters().get(registerNumber)));
				}

				// Printing the cache content
				System.out.println("\tCache " + cpuNumber + ":");
				for (int j = 0; j < NUMBER_OF_CACHE_ROWS; j++) {
					CacheEntry cacheEntry = cpu.getCacheEntries().get(j);
					System.out.println("\t\t" + j + " " + printBoolean(cacheEntry.isValidBit()) + " " + cacheEntry.getTagField() + " " + cacheEntry.getValue());
				}

				System.out.println();
			}

			// Printing memory and directory contents
			int startIndex = i * NUMBER_OF_WORDS_IN_MEMORY;
			int endIndex = startIndex + NUMBER_OF_WORDS_IN_MEMORY;
			System.out.println("\tMemory\t\t\t\t\tDirectory");
			Map<Integer, Integer> memoryBlocks = nodes.get(i).getMemoryBlocks();
			Map<Integer, DirectoryEntry> directoryEntries = nodes.get(i).getDirectoryEntries();
			for (int j = startIndex; j < endIndex; j++) {
				System.out.println(j + "\t" + format(memoryBlocks.get(j)) + "\t" + printDirectoryValues(directoryEntries.get(j)));
			}

		}
	}

	private String format(Integer value) {
		return format(value, WORD_SIZE);
	}

	private String format(int value, int padding) {
		return String.format("%" + padding + "s", Integer.toBinaryString(value)).replace(' ', '0');
	}

	private String printDirectoryValues(DirectoryEntry directoryEntry) {
		String result = format(directoryEntry.getState().getValue(), 2) + " ";
		for (int i = 0; i < NUMBER_OF_NODES; i++) {
			result += directoryEntry.getValues().get(i) + " ";
		}
		return result;
	}

	private int printBoolean(boolean validBit) {
		return validBit ? 1 : 0;
	}

	public void read(NUMASystem system, String cpuNumber, String nodeNumber, String instruction) {

	}
}