package com.ccnuma.pojo;

import com.ccnuma.util.SystemSimulatorUtils;

/**
 * The class represent a load instruction. A load instruction reads the data.
 * 
 * @author Mahdi Ziaee
 *
 */
public class LoadInstruction extends IInstruction {

	public LoadInstruction(String instruction) {
		super(instruction);
	}

	@Override
	public Integer execute(NUMASystem system, Integer nodeNumber, Integer cpuNumber) {
		return SystemSimulatorUtils.getInstance().read(system, nodeNumber, cpuNumber, this);
	}

}
