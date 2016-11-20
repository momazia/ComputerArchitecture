package com.ccnuma.pojo;

/**
 * A POJO to represent the raw / actual input of the simulator.
 * 
 * @author Mahdi Ziaee
 *
 */
public class Instruction {

	private Integer node;
	private Integer cpu;
	private IInstruction iInstruction;
	private String raw;

	public Instruction() {
	}

	/**
	 * Sets the properties of this class and uses the factory to instantiate proper instruction type.
	 * 
	 * @param node
	 * @param cpu
	 * @param instruction
	 */
	public Instruction(Integer node, Integer cpu, String instruction) {
		super();
		this.setNode(node);
		this.setCpu(cpu);
		this.iInstruction = instructionFactory(instruction);
		this.raw = instruction;
	}

	/**
	 * A factory to instantiate a proper I-Instruction type based on the first 6 bits.
	 * 
	 * @param instruction
	 * @return
	 */
	private IInstruction instructionFactory(String instruction) {
		switch (InstructionType.findValueOf(instruction)) {
		case LOAD:
			return new LoadInstruction(instruction);
		case STORE:
			return new StoreInstruction(instruction);
		}
		return null;
	}

	public IInstruction getIInstruction() {
		return iInstruction;
	}

	public void setIInstruction(IInstruction instruction) {
		this.iInstruction = instruction;
	}

	public Integer getNode() {
		return node;
	}

	public void setNode(Integer node) {
		this.node = node;
	}

	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public String getRaw() {
		return this.raw;
	}

}
