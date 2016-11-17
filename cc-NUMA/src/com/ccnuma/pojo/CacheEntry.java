package com.ccnuma.pojo;

public class CacheEntry {

	private boolean validBit = false;
	private Integer tagField;
	private Integer value;

	public CacheEntry(boolean validBit, Integer tagField, Integer value) {
		this.validBit = validBit;
		this.setTagField(tagField);
		this.value = value;
	}

	public CacheEntry() {
	}

	public boolean isValidBit() {
		return validBit;
	}

	public void setValidBit(boolean validBit) {
		this.validBit = validBit;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getTagField() {
		return tagField;
	}

	public void setTagField(Integer tagField) {
		this.tagField = tagField;
	}

}
