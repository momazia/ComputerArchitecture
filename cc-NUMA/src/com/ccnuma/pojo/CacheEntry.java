package com.ccnuma.pojo;

public class CacheEntry {

	private boolean validBit = false;
	private String tagField;
	private Integer value;

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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

}
