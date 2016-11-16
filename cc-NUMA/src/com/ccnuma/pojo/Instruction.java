package com.ccnuma.pojo;

public class Instruction {

	private String node;
	private String cpu;
	private String instruction;

	public Instruction() {

	}

	public Instruction(String node, String cpu, String instruction) {
		super();
		this.node = node;
		this.cpu = cpu;
		this.instruction = instruction;
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

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
}
