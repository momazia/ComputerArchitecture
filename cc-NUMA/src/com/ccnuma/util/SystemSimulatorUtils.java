package com.ccnuma.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccnuma.pojo.CPU;
import com.ccnuma.pojo.CacheEntry;
import com.ccnuma.pojo.DirectoryEntry;
import com.ccnuma.pojo.DirectoryEntryState;
import com.ccnuma.pojo.IInstruction;
import com.ccnuma.pojo.Instruction;
import com.ccnuma.pojo.NUMASystem;
import com.ccnuma.pojo.Node;

/**
 * The main simulator utility class.
 * 
 * @author Mahdi Ziaee
 *
 */
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

	/**
	 * Initializes the simulator. The main method to be called before using the system.
	 * 
	 * @return
	 */
	public NUMASystem initializeSimulator() {
		NUMASystem system = new NUMASystem();
		system.setNodes(initializeNodes());
		return system;
	}

	/**
	 * Initializes each nodes in the systems.
	 * 
	 * @return
	 */
	public Map<Integer, Node> initializeNodes() {
		Map<Integer, Node> nodes = new HashMap<>();
		for (int i = 0; i < NUMBER_OF_NODES; i++) {
			nodes.put(i, initializeNode(i));
		}
		return nodes;
	}

	/**
	 * Initializes individual nodes based on the node number passed.
	 * 
	 * @param nodeNumber
	 * @return
	 */
	private Node initializeNode(int nodeNumber) {
		Node node = new Node();
		node.setCpus(initializeCPUs());
		node.setDirectoryEntries(initializeDirectoryEntries(nodeNumber));
		node.setMemoryBlocks(initializeMemoryBlocks(nodeNumber));
		return node;
	}

	/**
	 * Initializes the memory blocks needed in the node number given.
	 * 
	 * @param nodeNumber
	 * @return
	 */
	public Map<Integer, Integer> initializeMemoryBlocks(int nodeNumber) {
		Map<Integer, Integer> result = new HashMap<>();
		int startIndex = nodeNumber * NUMBER_OF_WORDS_IN_MEMORY;
		int endIndex = startIndex + NUMBER_OF_WORDS_IN_MEMORY;
		for (int i = startIndex; i < endIndex; i++) {
			result.put(i, i + 5);
		}
		return result;
	}

	/**
	 * Initializes the directory entries for the given node number.
	 * 
	 * @param nodeNumber
	 * @return
	 */
	private Map<Integer, DirectoryEntry> initializeDirectoryEntries(int nodeNumber) {
		Map<Integer, DirectoryEntry> entries = new HashMap<>();
		int startIndex = nodeNumber * NUMBER_OF_WORDS_IN_MEMORY;
		int endIndex = startIndex + NUMBER_OF_WORDS_IN_MEMORY;
		for (int i = startIndex; i < endIndex; i++) {
			entries.put(i, initializeDirectoryEntry());
		}
		return entries;
	}

	/**
	 * Initializes a directory entry and sets the default values.
	 * 
	 * @return
	 */
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

	/**
	 * Initializes CPUs in the system.
	 * 
	 * @return
	 */
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

	/**
	 * Initializes all the cache entries in the system.
	 * 
	 * @return
	 */
	private Map<Integer, CacheEntry> initializeCacheEntries() {
		Map<Integer, CacheEntry> entries = new HashMap<>();
		for (int i = 0; i < NUMBER_OF_CACHE_ROWS; i++) {
			entries.put(i, new CacheEntry());
		}
		return entries;
	}

	/**
	 * Initializes the registers with default value.
	 * 
	 * @return
	 */
	private Map<Integer, Integer> initializeRegisters() {
		Map<Integer, Integer> registers = new HashMap<>();
		for (int i = 1; i <= NUMBER_OF_REGISTERS; i++) {
			registers.put(i, 0);
		}
		return registers;
	}

	/**
	 * Prints the system file given into the console. It shows all the nodes information like cache data, memory content etc.
	 * 
	 * @param system
	 */
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

	/**
	 * Formats the given value with padding size of 32.
	 * 
	 * @param value
	 * @return
	 */
	private String format(Integer value) {
		return format(value, WORD_SIZE);
	}

	/**
	 * Formats the given value using the padding size given. If the value is null, it will be set to zero.
	 * 
	 * @param value
	 * @param padding
	 * @return
	 */
	private String format(Integer value, int padding) {
		if (value == null) {
			value = 0;
		}
		return String.format("%" + padding + "s", Integer.toBinaryString(value)).replace(' ', '0');
	}

	/**
	 * Prints the content of the directory values for all the nodes in the system.
	 * 
	 * @param directoryEntry
	 * @return
	 */
	private String printDirectoryValues(DirectoryEntry directoryEntry) {
		String result = format(directoryEntry.getState().getValue(), 2) + " ";
		for (int i = 0; i < NUMBER_OF_NODES; i++) {
			result += directoryEntry.getValues().get(i) + " ";
		}
		return result;
	}

	/**
	 * Converts the boolean value to 1 = true, 0 = false format.
	 * 
	 * @param bool
	 * @return
	 */
	private int printBoolean(boolean bool) {
		return bool ? 1 : 0;
	}

	/**
	 * Goes through the 2 write steps needed for the given instruction and returns accessing cost for that instruction.
	 * 
	 * @param system
	 * @param nodeNumber
	 * @param cpuNumber
	 * @param instruction
	 * @return
	 */
	public Integer write(NUMASystem system, int nodeNumber, int cpuNumber, IInstruction instruction) {
		// 1. Search local cache
		Integer localCacheValue = searchLocalCache(system.getNodes().get(nodeNumber).getCpus().get(cpuNumber), instruction);
		int memoryAddress = getMemoryAddress(instruction);
		Integer registerValue = getDataFromRegister(system, nodeNumber, cpuNumber, instruction);

		if (localCacheValue != null) {
			// Get exclusive access prior to writing the value
			if (isDirectoryStateEqual(system, memoryAddress, DirectoryEntryState.SHARED)) {
				// Invalidate other copies
				invalidateCaches(system, instruction, memoryAddress);
				// Update the cache value
				storeDataIntoCache(system, nodeNumber, cpuNumber, registerValue, instruction);
				// Change Directory state to Dirty
				updateDirectory(system, nodeNumber, memoryAddress, DirectoryEntryState.DIRTY);
				return 1;
			}
		}
		// 2. Update home node memory
		updateMemoryAndDirectory(system, memoryAddress, registerValue, instruction);
		return 100;
	}

	/**
	 * Invalidates the other cache values by finding all the shared nodes from the directory and setting their valid bit to 0.
	 * 
	 * @param system
	 * @param instruction
	 * @param memoryAddress
	 */
	private void invalidateCaches(NUMASystem system, IInstruction instruction, int memoryAddress) {
		List<Integer> sharedNodeNumbers = findSharedNodesFromDirectory(system, memoryAddress);
		invalidateCaches(system, sharedNodeNumbers, instruction);
	}

	/**
	 * Updates the content of the memory and directory. This represents the second step in the write process.
	 * 
	 * @param system
	 * @param memoryAddress
	 * @param value
	 * @param instruction
	 */
	private void updateMemoryAndDirectory(NUMASystem system, int memoryAddress, Integer value, IInstruction instruction) {
		for (Node node : system.getNodes().values()) {
			if (node.getMemoryBlocks().containsKey(memoryAddress)) {
				// Updating the value of the memory
				node.getMemoryBlocks().put(memoryAddress, value);
				switch (node.getDirectoryEntries().get(memoryAddress).getState()) {
				case UN_CACHED: // Do nothing
					break;
				case SHARED:
					// Invalidating other cache copies
					invalidateCaches(system, instruction, memoryAddress);
					// Won't change the state
					break;
				case DIRTY:
					// Invalidating other cache copies
					invalidateCaches(system, instruction, memoryAddress);
					// Changing the state to SHARED
					node.getDirectoryEntries().get(memoryAddress).setState(DirectoryEntryState.DIRTY);
					break;
				}
				return;
			}
		}
	}

	/**
	 * Gets the data from register of the cpu for the node number given using the instruction passed.
	 * 
	 * @param system
	 * @param nodeNumber
	 * @param cpuNumber
	 * @param instruction
	 * @return
	 */
	private Integer getDataFromRegister(NUMASystem system, int nodeNumber, int cpuNumber, IInstruction instruction) {
		Integer registerNumber = getRegisterNumber(instruction);
		return system.getNodes().get(nodeNumber).getCpus().get(cpuNumber).getRegisters().get(registerNumber);
	}

	/**
	 * Invalidates the shared node numbers passed by setting their valid bit to false.
	 * 
	 * @param system
	 * @param sharedNodeNumbers
	 * @param instruction
	 */
	private void invalidateCaches(NUMASystem system, List<Integer> sharedNodeNumbers, IInstruction instruction) {
		int cacheIndex = getCacheIndex(instruction);
		Integer tagField = getTagField(instruction);
		for (Integer sharedNodeNumber : sharedNodeNumbers) {
			for (CPU cpu : system.getNodes().get(sharedNodeNumber).getCpus().values()) {
				// Finding the other matching copies
				if (cpu.getCacheEntries().containsKey(cacheIndex) && tagField.equals(cpu.getCacheEntries().get(cacheIndex).getTagField())) {
					cpu.getCacheEntries().get(cacheIndex).setValidBit(false);
				}
			}
		}
	}

	/**
	 * Checks to see if the memory address passed has the same state as the given state.
	 * 
	 * @param system
	 * @param memoryAddress
	 * @param givenState
	 * @return
	 */
	private boolean isDirectoryStateEqual(NUMASystem system, int memoryAddress, DirectoryEntryState givenState) {
		for (Node node : system.getNodes().values()) {
			// Finding the home directory
			if (node.getDirectoryEntries().containsKey(memoryAddress)) {
				DirectoryEntryState state = node.getDirectoryEntries().get(memoryAddress).getState();
				return givenState.equals(state);
			}
		}
		return false;
	}

	/**
	 * The main read function takes care of the 4 main steps in reading an instruction.
	 * 
	 * @param system
	 * @param nodeNumber
	 * @param cpuNumber
	 * @param instruction
	 * @return
	 */
	public Integer read(NUMASystem system, int nodeNumber, int cpuNumber, IInstruction instruction) {
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
		int memoryAddress = getMemoryAddress(instruction);
		Integer memoryValue = containsMostRecentCleanData(system, memoryAddress);
		if (memoryValue != null) {
			// Meaning the data is clean and it is the most recent version, So we load it into local cache and register
			loadDataIntoCacheAndRegister(system, nodeNumber, cpuNumber, instruction, memoryValue);
			// Updating the directory state and node flag
			updateDirectory(system, nodeNumber, memoryAddress, DirectoryEntryState.SHARED);
			return 100;
		}
		// 4. The data is dirty, looking up for the latest data in the dirty node
		List<Integer> dirtyNodeNumbers = findSharedNodesFromDirectory(system, memoryAddress);
		Integer latestValue = findLatestValue(system, instruction, dirtyNodeNumbers);
		if (storeDataIntoMemory(system, memoryAddress, latestValue)) {
			// Storage was successful, updating directory.
			updateDirectory(system, nodeNumber, memoryAddress, DirectoryEntryState.SHARED);
			// Reading the data from memory into cache and register
			loadDataIntoCacheAndRegister(system, nodeNumber, cpuNumber, instruction, latestValue);
			return 135;
		}
		return null;
	}

	/**
	 * Stores the value given into the memory address passed.
	 * 
	 * @param system
	 * @param memoryAddress
	 * @param value
	 * @return
	 */
	private boolean storeDataIntoMemory(NUMASystem system, int memoryAddress, Integer value) {
		for (Node node : system.getNodes().values()) {
			// Finding the home directory
			if (node.getMemoryBlocks().containsKey(memoryAddress)) {
				node.getMemoryBlocks().put(memoryAddress, value);
				return true;
			}
		}
		return false;
	}

	/**
	 * Finds the latest valid value among all the dirty nodes by checking the tag field and index.
	 * 
	 * @param system
	 * @param instruction
	 * @param dirtyNodeNumbers
	 * @return
	 */
	private Integer findLatestValue(NUMASystem system, IInstruction instruction, List<Integer> dirtyNodeNumbers) {
		Integer localCacheValue;
		for (Integer dirtyNodeNumber : dirtyNodeNumbers) {
			// Looking at all cache values
			for (int i = 0; i < NUMBER_OF_CPUS; i++) {
				localCacheValue = searchLocalCache(system.getNodes().get(dirtyNodeNumber).getCpus().get(i), instruction);
				if (localCacheValue != null) {
					// Means we found the latest value
					return localCacheValue;
				}
			}
		}
		return null;
	}

	/**
	 * Finds the list of all the shared nodes using the memory address in the directory.
	 * 
	 * @param system
	 * @param memoryAddress
	 * @return
	 */
	private List<Integer> findSharedNodesFromDirectory(NUMASystem system, int memoryAddress) {
		List<Integer> result = new ArrayList<>();
		for (Node node : system.getNodes().values()) {
			// Finding the home directory
			if (node.getDirectoryEntries().containsKey(memoryAddress)) {
				Map<Integer, Integer> nodeFlags = node.getDirectoryEntries().get(memoryAddress).getValues();
				for (Integer nodeNumber : nodeFlags.keySet()) {
					if (nodeFlags.get(nodeNumber) == 1) {
						result.add(nodeNumber);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Sets the state and value of the new node passed for the given memory address in the directory.
	 * 
	 * @param system
	 * @param newNodeNumber
	 * @param memoryAddress
	 * @param state
	 */
	private void updateDirectory(NUMASystem system, int newNodeNumber, int memoryAddress, DirectoryEntryState state) {
		for (Node node : system.getNodes().values()) {
			// Finding the home directory
			if (node.getDirectoryEntries().containsKey(memoryAddress)) {
				node.getDirectoryEntries().get(memoryAddress).setState(state);
				node.getDirectoryEntries().get(memoryAddress).getValues().put(newNodeNumber, 1);
			}
		}
	}

	/**
	 * Stores the value given into cache based on the instruction passed.
	 * 
	 * @param system
	 * @param nodeNumber
	 * @param cpuNumber
	 * @param value
	 * @param instruction
	 */
	private void storeDataIntoCache(NUMASystem system, int nodeNumber, int cpuNumber, Integer value, IInstruction instruction) {
		Integer cacheIndex = getCacheIndex(instruction);
		Integer tagField = getTagField(instruction);
		system.getNodes().get(nodeNumber).getCpus().get(cpuNumber).getCacheEntries().put(cacheIndex, new CacheEntry(true, tagField, value));
	}

	/**
	 * Store the value into cache and register.
	 * 
	 * @param system
	 * @param nodeNumber
	 * @param cpuNumber
	 * @param instruction
	 * @param value
	 */
	private void loadDataIntoCacheAndRegister(NUMASystem system, int nodeNumber, int cpuNumber, IInstruction instruction, Integer value) {
		Integer registerNumber = getRegisterNumber(instruction);
		Integer cacheIndex = getCacheIndex(instruction);
		Integer tagField = getTagField(instruction);
		system.getNodes().get(nodeNumber).getCpus().get(cpuNumber).getRegisters().put(registerNumber, value);
		system.getNodes().get(nodeNumber).getCpus().get(cpuNumber).getCacheEntries().put(cacheIndex, new CacheEntry(true, tagField, value));
	}

	/**
	 * Checks to see if the memory address given has state shared or un cached, if yes, it returns the value in the memory, else (dirty) returns null.
	 * 
	 * @param system
	 * @param memoryAddress
	 * @return
	 */
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

	/**
	 * Sets the value passed into register.
	 * 
	 * @param system
	 * @param nodeNumber
	 * @param cpuNumber
	 * @param instruction
	 * @param value
	 */
	private void loadDataIntoRegister(NUMASystem system, int nodeNumber, int cpuNumber, IInstruction instruction, Integer value) {
		Integer registerNumber = getRegisterNumber(instruction);
		system.getNodes().get(nodeNumber).getCpus().get(cpuNumber).getRegisters().put(registerNumber, value);
	}

	/**
	 * Gets the RT register number from the load instruction
	 * 
	 * @param instruction
	 * @return
	 */
	private Integer getRegisterNumber(IInstruction instruction) {
		return instruction.getRt() - RT_BASE_NUMBER;
	}

	/**
	 * Searches the local cache to see if the valid bit is 1 and the tag field matches the instruction. If yes, it returns the value of the cache, otherwise null.
	 * 
	 * @param cpu
	 * @param instruction
	 * @return
	 */
	public Integer searchLocalCache(CPU cpu, IInstruction instruction) {
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
	 * Finds the memory address by right shifting 2 bits.
	 * 
	 * @param instruction
	 * @return
	 */
	private int getMemoryAddress(IInstruction instruction) {
		return instruction.getOffset() >> 2;
	}

	/**
	 * Calculates the tag field based on the instructions offset: 4 right shifts
	 * 
	 * @param instruction
	 * @return
	 */
	private Integer getTagField(IInstruction instruction) {
		return instruction.getOffset() >> 4;
	}

	/**
	 * Calculates the cache index based on the instruction given by getting
	 * the most right two bits.
	 * 
	 * @param instruction
	 * @return
	 */
	private int getCacheIndex(IInstruction instruction) {
		return getMemoryAddress(instruction) & 3;
	}

	/**
	 * Prints the raw instruction content coming from the file in binary format and the access cost.
	 * 
	 * @param instruction
	 * @param accessCost
	 */
	public void print(Instruction instruction, Integer accessCost) {
		System.out.println(instruction.getRaw() + " | Access Cost: " + accessCost);
	}

}