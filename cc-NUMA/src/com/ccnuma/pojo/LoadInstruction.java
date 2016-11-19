package com.ccnuma.pojo;

import com.ccnuma.util.SystemSimulatorUtils;

public class LoadInstruction extends IInstruction {

	public LoadInstruction(String instruction) {
		super(instruction);
	}

	@Override
	public Integer execute(NUMASystem system, Integer nodeNumber, Integer cpuNumber) {
		return SystemSimulatorUtils.getInstance().read(system, nodeNumber, cpuNumber, this);
	}

}
