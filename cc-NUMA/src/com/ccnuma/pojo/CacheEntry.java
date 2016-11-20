package com.ccnuma.pojo;

/**
 * A POJO to represent an entry inside the cache which contains a valid bit, tag field and the actual value.
 * 
 * @author Mahdi Ziaee
 *
 */
public class CacheEntry {

	private boolean validBit = false;
	private Integer tagField;
	private Integer value;

	/**
	 * The main constructor to set the values.
	 * 
	 * @param validBit
	 * @param tagField
	 * @param value
	 */
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
