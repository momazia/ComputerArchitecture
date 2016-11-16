package com.ccnuma.pojo;

public class Instruction {

	private String node;
	private String cpu;
	private IInstruction instruction;

	public Instruction() {
	}

	public Instruction(String node, String cpu, String instruction) {
		super();
		this.node = node;
		this.cpu = cpu;
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

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public IInstruction getInstruction() {
		return instruction;
	}

	public void setInstruction(IInstruction instruction) {
		this.instruction = instruction;
	}

}
