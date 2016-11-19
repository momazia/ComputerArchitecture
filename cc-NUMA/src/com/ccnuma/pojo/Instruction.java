package com.ccnuma.pojo;

public class Instruction {

	private Integer node;
	private Integer cpu;
	private IInstruction iInstruction;
	private String raw;

	public Instruction() {
	}

	public Instruction(Integer node, Integer cpu, String instruction) {
		super();
		this.setNode(node);
		this.setCpu(cpu);
		this.iInstruction = instructionFactory(instruction);
		this.raw = instruction;
	}

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
