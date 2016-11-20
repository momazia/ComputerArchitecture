package com.ccnuma.pojo;

import java.util.Map;

/**
 * The main POJO to hold different set of nodes in the simulator.
 * 
 * @author Mahdi Ziaee
 *
 */
public class NUMASystem {

	private Map<Integer, Node> nodes;

	public Map<Integer, Node> getNodes() {
		return nodes;
	}

	public void setNodes(Map<Integer, Node> nodes) {
		this.nodes = nodes;
	}

}
