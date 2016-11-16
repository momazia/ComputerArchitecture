package com.ccnuma.pojo;

public class CacheEntry {

	private boolean validBit = false;
	private String tagField;
	private String value;

	public boolean isValidBit() {
		return validBit;
	}

	public void setValidBit(boolean validBit) {
		this.validBit = validBit;
	}

	public String getTagField() {
		return tagField;
	}

	public void setTagField(String tagField) {
		this.tagField = tagField;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
