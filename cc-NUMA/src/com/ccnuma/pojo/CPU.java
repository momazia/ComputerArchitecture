package com.ccnuma.pojo;

import java.util.Map;

public class CPU {

	private Map<Integer, Integer> registers;
	private Map<Integer, CacheEntry> cacheEntries;

	public Map<Integer, CacheEntry> getCacheEntries() {
		return cacheEntries;
	}

	public void setCacheEntries(Map<Integer, CacheEntry> cacheEntries) {
		this.cacheEntries = cacheEntries;
	}

	public Map<Integer, Integer> getRegisters() {
		return registers;
	}

	public void setRegisters(Map<Integer, Integer> registers) {
		this.registers = registers;
	}

}