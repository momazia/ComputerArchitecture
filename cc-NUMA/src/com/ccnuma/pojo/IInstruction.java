package com.ccnuma.pojo;

/**
 * A POJO to represent the instructions read from the input file. It follows I-Instruction structure.
 * 
 * @author Mahdi Ziaee
 *
 */
public abstract class IInstruction {

	private Integer rs;
	private Integer rt;
	private Integer offset;

	/**
	 * The main constructors which sets the values into the properties of this class.
	 * 
	 * @param instruction
	 */
	public IInstruction(String instruction) {
		this.rs = Integer.parseInt(instruction.substring(6, 11), 2);
		this.rt = Integer.parseInt(instruction.substring(11, 16), 2);
		this.offset = Integer.parseInt(instruction.substring(16), 2);
	}

	/**
	 * The main method to be implemented by different types of instructions which represents the instruction's execution in the system.
	 * 
	 * @param system
	 * @param nodeNumber
	 * @param cpuNumber
	 * @return
	 */
	public abstract Integer execute(NUMASystem system, Integer nodeNumber, Integer cpuNumber);

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
