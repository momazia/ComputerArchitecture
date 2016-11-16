package com.ccnuma.pojo;

import java.util.Map;

public class CPU {

	private Map<String, String> registers;
	private Map<Integer, CacheEntry> cacheEntries;

	public Map<String, String> getRegisters() {
		return registers;
	}

	public void setRegisters(Map<String, String> registers) {
		this.registers = registers;
	}

	public Map<Integer, CacheEntry> getCacheEntries() {
		return cacheEntries;
	}

	public void setCacheEntries(Map<Integer, CacheEntry> cacheEntries) {
		this.cacheEntries = cacheEntries;
	}

}