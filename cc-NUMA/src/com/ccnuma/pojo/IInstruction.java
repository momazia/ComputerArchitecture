package com.ccnuma.pojo;

public class IInstruction {

	private String destination;
	private Integer target;

	public IInstruction(String instruction) {
		this.destination = instruction.substring(3, 4);
		this.target = Integer.parseInt(instruction.substring(4), 16);
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

}
