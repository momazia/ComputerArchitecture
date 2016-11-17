package com.ccnuma.pojo;

import java.util.Map;

public class Node {

	private Map<String, CPU> cpus;
	private Map<Integer, Integer> memoryBlocks;
	private Map<Integer, DirectoryEntry> directoryEntries;

	public Map<String, CPU> getCpus() {
		return cpus;
	}

	public void setCpus(Map<String, CPU> cpus) {
		this.cpus = cpus;
	}

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
}
