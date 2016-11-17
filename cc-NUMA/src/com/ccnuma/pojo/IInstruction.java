package com.ccnuma.pojo;

public class IInstruction {

	private Integer rs;
	private Integer rt;
	private Integer offset;

	public IInstruction(String instruction) {
		this.rs = Integer.parseInt(instruction.substring(6, 11), 2);
		this.rt = Integer.parseInt(instruction.substring(11, 16), 2);
		this.offset = Integer.parseInt(instruction.substring(16), 2);
	}

	public Integer getRs() {
		return rs;
	}

	public void setRs(Integer rs) {
		this.rs = rs;
	}

	public Integer getRt() {
		return rt;
	}

	public void setRt(Integer rt) {
		this.rt = rt;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

}
