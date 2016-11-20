package com.ccnuma.pojo;

/**
 * An enumeration for different states of directory.
 * 
 * @author Mahdi Ziaee
 *
 */
public enum DirectoryEntryState {

	UN_CACHED(0), SHARED(1), DIRTY(2);

	private int value;

	DirectoryEntryState(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
