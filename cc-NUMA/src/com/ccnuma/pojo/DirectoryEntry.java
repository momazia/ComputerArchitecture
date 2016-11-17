package com.ccnuma.pojo;

import java.util.Map;

public class DirectoryEntry {

	private Map<Integer, Integer> values;
	private DirectoryEntryState state;

	public Map<Integer, Integer> getValues() {
		return values;
	}

	public void setValues(Map<Integer, Integer> values) {
		this.values = values;
	}

	public DirectoryEntryState getState() {
		return state;
	}

	public void setState(DirectoryEntryState state) {
		this.state = state;
	}

}
