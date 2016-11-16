package com.ccnuma.util;

import java.util.HashMap;
import java.util.Map;

import com.ccnuma.pojo.CPU;
import com.ccnuma.pojo.CacheEntry;
import com.ccnuma.pojo.DirectoryEntry;
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

	public Map<String, Node> initializeNodes() {
		Map<String, Node> nodes = new HashMap<>();
		for (int i = 0; i < NUMBER_OF_NODES; i++) {
			nodes.put(String.format("%2s", Integer.toBinaryString(i)).replace(' ', '0'), initializeNode(i));
		}
		return nodes;
	}

	private Node initializeNode(int nodeNumber) {
		Node node = new Node();
		node.setCpus(initializeCPUs());
		node.setDirectoryEntries(initializeDirectoryEntries());
		node.setMemoryBlocks(initializeMemoryBlocks(nodeNumber));
		return node;
	}

	public Map<Integer, String> initializeMemoryBlocks(int nodeNumber) {
		Map<Integer, String> result = new HashMap<>();
		int startIndex = nodeNumber * NUMBER_OF_WORDS_IN_MEMORY;
		int endIndex = startIndex + NUMBER_OF_WORDS_IN_MEMORY;
		for (int i = startIndex; i < endIndex; i++) {
			result.put(i, String.format("%32s", Integer.toBinaryString(i + 5)).replace(' ', '0'));
		}
		return result;
	}

	private Map<Integer, DirectoryEntry> initializeDirectoryEntries() {
		Map<Integer, DirectoryEntry> entries = new HashMap<>();
		for (int i = 0; i < NUMBER_OF_WORDS_IN_MEMORY; i++) {
			entries.put(i, initializeDirectoryEntry());
		}
		return entries;
	}

	private DirectoryEntry initializeDirectoryEntry() {
		DirectoryEntry entry = new DirectoryEntry();
		Map<String, String> values = new HashMap<>();
		for (int i = 0; i < NUMBER_OF_NODES; i++) {
			values.put(String.format("%2s", Integer.toBinaryString(i)).replace(' ', '0'), initializeValueWithZeros(2));
		}
		entry.setValues(values);
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

	private Map<String, String> initializeRegisters() {
		Map<String, String> registers = new HashMap<>();
		for (int i = 1; i <= NUMBER_OF_REGISTERS; i++) {
			registers.put(Integer.toString(i), initializeValueWithZeros(WORD_SIZE));
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
		Map<String, Node> nodes = system.getNodes();

		for (int i = 0; i < NUMBER_OF_NODES; i++) {
			String nodeNumber = String.format("%2s", Integer.toBinaryString(i)).replace(' ', '0');
			System.out.println("\t------------------------- Node (" + nodeNumber + ") --------------------------");

			for (String cpuNumber : nodes.get(nodeNumber).getCpus().keySet()) {

				CPU cpu = nodes.get(nodeNumber).getCpus().get(cpuNumber);
				System.out.println("CPU " + cpuNumber + ":");

				System.out.println("\tRegistery:");
				for (String registeryNumber : cpu.getRegisters().keySet()) {
					System.out.println("\t\tS" + registeryNumber + ": " + cpu.getRegisters().get(registeryNumber));
				}

				System.out.println("\tCache " + cpuNumber + ":");
				for (int j = 0; j < NUMBER_OF_CACHE_ROWS; j++) {
					CacheEntry cacheEntry = cpu.getCacheEntries().get(j);
					System.out.println("\t\t" + j + " " + printBoolean(cacheEntry.isValidBit()) + " " + cacheEntry.getTagField() + " " + cacheEntry.getValue());
				}

				System.out.println();
			}

		}
	}

	private int printBoolean(boolean validBit) {
		return validBit ? 1 : 0;
	}
}