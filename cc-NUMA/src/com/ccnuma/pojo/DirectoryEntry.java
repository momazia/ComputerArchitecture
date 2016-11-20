package com.ccnuma.pojo;

import java.util.Map;

/**
 * A POJO to represent an entry inside the directory. Each entry contains a state and a set of values for each of the nodes in the system.
 * 
 * @author Mahdi Ziaee
 *
 */
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
