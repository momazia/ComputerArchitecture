package com.ccnuma.pojo;

import java.util.Map;

/**
 * A POJO to represent each node in the system. It contains a set of CPUs, memory blocks and directory entries.
 * 
 * @author Mahdi Ziaee
 *
 */
public class Node {

	private Map<Integer, CPU> cpus;
	private Map<Integer, Integer> memoryBlocks;
	private Map<Integer, DirectoryEntry> directoryEntries;

	public Map<Integer, DirectoryEntry> getDirectoryEntries() {
		return directoryEntries;
	}

	public void setDirectoryEntries(Map<Integer, DirectoryEntry> directoryEntries) {
		this.directoryEntries = directoryEntries;
	}

	public Map<Integer, Integer> getMemoryBlocks() {
		return memoryBlocks;
	}

	public void setMemoryBlocks(Map<Integer, Integer> memoryBlocks) {
		this.memoryBlocks = memoryBlocks;
	}

	public Map<Integer, CPU> getCpus() {
		return cpus;
	}

	public void setCpus(Map<Integer, CPU> cpus) {
		this.cpus = cpus;
	}
}
