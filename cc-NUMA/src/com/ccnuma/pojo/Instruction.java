package com.ccnuma.pojo;

public class Instruction {

	private Integer node;
	private Integer cpu;
	private IInstruction instruction;

	public Instruction() {
	}

	public Instruction(Integer node, Integer cpu, String instruction) {
		super();
		this.setNode(node);
		this.setCpu(cpu);
		this.instruction = instuctionFactory(instruction);
	}

	private IInstruction instuctionFactory(String instruction) {
		switch (InstructionType.findValueOf(instruction)) {
		case LOAD:
			return new LoadInstruction(instruction);
		case STORE:
			return new StoreInstruction(instruction);
		}
		return null;
	}

	public IInstruction getInstruction() {
		return instruction;
	}

	public void setInstruction(IInstruction instruction) {
		this.instruction = instruction;
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

}
