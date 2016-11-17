package com.ccnuma.util;

import java.util.HashMap;
import java.util.Map;

import com.ccnuma.pojo.CPU;
import com.ccnuma.pojo.CacheEntry;
import com.ccnuma.pojo.DirectoryEntry;
import com.ccnuma.pojo.DirectoryEntryState;
import com.ccnuma.pojo.LoadInstruction;
import com.ccnuma.pojo.NUMASystem;
import com.ccnuma.pojo.Node;

public class SystemSimulatorUtils {

	private static final int RT_BASE_NUMBER = 16;
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

	public Map<Integer, CPU> initializeCPUs() {
		Map<Integer, CPU> cpus = new HashMap<>();
		for (int i = 0; i < NUMBER_OF_CPUS; i++) {
			CPU cpu = new CPU();
			cpu.setRegisters(initializeRegisters());
			cpu.setCacheEntries(initializeCacheEntries());
			cpus.put(i, cpu);
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
			for (Integer cpuNumber : nodes.get(i).getCpus().keySet()) {

				CPU cpu = nodes.get(i).getCpus().get(cpuNumber);
				System.out.println("CPU " + cpuNumber + ":");

				// Printing the registers
				System.out.println("\tRegister:");
				for (Integer registerNumber : cpu.getRegisters().keySet()) {
					System.out.println("\t\tS" + registerNumber + ": " + format(cpu.getRegisters().get(registerNumber)));
				}

				// Printing the cache content
				System.out.println("\tCache " + cpuNumber + ":");
				System.out.println("\t\ti v Tag  Value");

				for (int j = 0; j < NUMBER_OF_CACHE_ROWS; j++) {
					CacheEntry cacheEntry = cpu.getCacheEntries().get(j);
					System.out.println("\t\t" + j + " " + printBoolean(cacheEntry.isValidBit()) + " " + format(cacheEntry.getTagField(), 4) + " " + format(cacheEntry.getValue()));
				}
				System.out.println();
			}

			// Printing memory and directory contents
			int startIndex = i * NUMBER_OF_WORDS_IN_MEMORY;
			int endIndex = startIndex + NUMBER_OF_WORDS_IN_MEMORY;
			System.out.println("i\tMemory\t\t\t\t\tDirectory");
			Map<Integer, Integer> memoryBlocks = nodes.get(i).getMemoryBlocks();
			Map<Integer, DirectoryEntry> directoryEntries = nodes.get(i).getDirectoryEntries();
			for (int j = startIndex; j < endIndex; j++) {
				System.out.println(j + "\t" + format(memoryBlocks.get(j)) + "\t" + printDirectoryValues(directoryEntries.get(j)));
			}
			System.out.println();
		}
	}

	private String format(Integer value) {
		return format(value, WORD_SIZE);
	}

	private String format(Integer value, int padding) {
		if (value == null) {
			return "";
		}
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

	public Integer read(NUMASystem system, int nodeNumber, int cpuNumber, LoadInstruction instruction) {
		// 1. Searching local cache
		Integer localCacheValue = searchLocalCache(system.getNodes().get(nodeNumber).getCpus().get(cpuNumber), instruction);
		if (localCacheValue != null) {
			// Loading the data into register
			loadDataIntoRegister(system, nodeNumber, cpuNumber, instruction, localCacheValue);
			return 1;
		}
		// 2. Searching other caches in the local node
		int otherCpuNumber = (~cpuNumber) & 1;
		localCacheValue = searchLocalCache(system.getNodes().get(nodeNumber).getCpus().get(otherCpuNumber), instruction);
		if (localCacheValue != null) {
			// Loading the data coming from other cache into into both local
			// cache and register
			loadDataIntoCacheAndRegister(system, nodeNumber, cpuNumber, instruction, localCacheValue);
			return 30;
		}
		// 3. Searching the home node's memory/directory
		int memoryAddress = instruction.getOffset() >> 2;
		Integer memoryValue = containsMostRecentCleanData(system, memoryAddress);
		if (memoryValue != null) {
			// Meaning the data is clean and it is the most recent version, So
			// we load it into local cache and register
			loadDataIntoCacheAndRegister(system, nodeNumber, cpuNumber, instruction, memoryValue);
			// Updating the directory state and node flag
			updateDirectory(system, nodeNumber, memoryAddress, DirectoryEntryState.SHARED);
			return 100;
		}
		return null;
	}

	private void updateDirectory(NUMASystem system, int newNodeNumber, int memoryAddress, DirectoryEntryState state) {
		for (Node node : system.getNodes().values()) {
			// Finding the home directory
			if (node.getDirectoryEntries().containsKey(memoryAddress)) {
				node.getDirectoryEntries().get(memoryAddress).setState(state);
				node.getDirectoryEntries().get(memoryAddress).getValues().put(newNodeNumber, 1);
			}
		}
	}

	private void storeDataIntoCache(NUMASystem system, int nodeNumber, int toCpu, Integer localCacheValue, LoadInstruction instruction) {
		Integer cacheIndex = getCacheIndex(instruction);
		Integer tagField = getTagField(instruction);
		system.getNodes().get(nodeNumber).getCpus().get(toCpu).getCacheEntries().put(cacheIndex, new CacheEntry(true, tagField, localCacheValue));
	}

	private void loadDataIntoCacheAndRegister(NUMASystem system, int nodeNumber, int cpuNumber, LoadInstruction instruction, Integer memoryValue) {
		Integer registerNumber = getRegisterNumber(instruction);
		Integer cacheIndex = getCacheIndex(instruction);
		Integer tagField = getTagField(instruction);
		system.getNodes().get(nodeNumber).getCpus().get(cpuNumber).getRegisters().put(registerNumber, memoryValue);
		system.getNodes().get(nodeNumber).getCpus().get(cpuNumber).getCacheEntries().put(cacheIndex, new CacheEntry(true, tagField, memoryValue));
	}

	private Integer containsMostRecentCleanData(NUMASystem system, int memoryAddress) {
		for (Node node : system.getNodes().values()) {
			if (node.getDirectoryEntries().containsKey(memoryAddress)) {
				DirectoryEntryState state = node.getDirectoryEntries().get(memoryAddress).getState();
				if (DirectoryEntryState.SHARED.equals(state) || DirectoryEntryState.UN_CACHED.equals(state)) {
					// Returning the memory value
					return node.getMemoryBlocks().get(memoryAddress);
				} else if (DirectoryEntryState.DIRTY.equals(state)) {
					return null;
				}
			}
		}
		return null;
	}

	private void loadDataIntoRegister(NUMASystem system, int nodeNumber, int cpuNumber, LoadInstruction instruction, Integer localCacheValue) {
		Integer registerNumber = getRegisterNumber(instruction);
		system.getNodes().get(nodeNumber).getCpus().get(cpuNumber).getRegisters().put(registerNumber, localCacheValue);
	}

	private Integer getRegisterNumber(LoadInstruction instruction) {
		Integer registerNumber = instruction.getRt() - RT_BASE_NUMBER;
		return registerNumber;
	}

	public Integer searchLocalCache(CPU cpu, LoadInstruction instruction) {
		int cacheIndex = getCacheIndex(instruction);
		// Checking the valid bit
		CacheEntry cacheEntry = cpu.getCacheEntries().get(cacheIndex);
		if (cacheEntry.isValidBit() && cacheEntry.getTagField() != null) {
			int tag = getTagField(instruction);
			if (cacheEntry.getTagField().equals(tag)) {
				return cacheEntry.getValue();
			}
		}
		return null;
	}

	/**
	 * Calculates the tag field based on the load instruction passed, by getting
	 * the 4 most left bits from the offset.
	 * 
	 * @param instruction
	 * @return
	 */
	private Integer getTagField(LoadInstruction instruction) {
		return instruction.getOffset() >> 4;
	}

	/**
	 * Calculates the cache index based on the load instruction given by getting
	 * the most right two bits.
	 * 
	 * @param instruction
	 * @return
	 */
	private int getCacheIndex(LoadInstruction instruction) {
		return (instruction.getOffset() >> 2) & 3;
	}
}