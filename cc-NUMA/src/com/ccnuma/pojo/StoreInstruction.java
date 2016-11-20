package com.ccnuma.pojo;

import com.ccnuma.util.SystemSimulatorUtils;

/**
 * The class represent a store instruction. A store instruction writes data.
 * 
 * @author Mahdi Ziaee
 *
 */
public class StoreInstruction extends IInstruction {

	public StoreInstruction(String instruction) {
		super(instruction);
	}

	@Override
	public Integer execute(NUMASystem system, Integer nodeNumber, Integer cpuNumber) {
		return SystemSimulatorUtils.getInstance().write(system, nodeNumber, cpuNumber, this);
	}

}
