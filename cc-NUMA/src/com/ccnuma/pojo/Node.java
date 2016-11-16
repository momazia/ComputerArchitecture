package com.ccnuma.pojo;

import java.util.Map;

public class Node {

	private Map<String, CPU> cpus;
	private Map<Integer, String> memoryBlocks;
	private Map<Integer, DirectoryEntry> directoryEntries;

	public Map<String, CPU> getCpus() {
		return cpus;
	}

	public void setCpus(Map<String, CPU> cpus) {
		this.cpus = cpus;
	}

	public Map<Integer, String> getMemoryBlocks() {
		return memoryBlocks;
	}

	public void setMemoryBlocks(Map<Integer, String> memoryBlocks) {
		this.memoryBlocks = memoryBlocks;
	}

	public Map<Integer, DirectoryEntry> getDirectoryEntries() {
		return directoryEntries;
	}

	public void setDirectoryEntries(Map<Integer, DirectoryEntry> directoryEntries) {
		this.directoryEntries = directoryEntries;
	}
}
