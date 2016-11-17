package com.ccnuma.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.ccnuma.pojo.CPU;
import com.ccnuma.pojo.NUMASystem;
import com.ccnuma.pojo.Node;
import com.ccnuma.util.SystemSimulatorUtils;

public class TestSystemSimulatorUtils {

	@Test
	public void testInitializeNodes() {
		Map<Integer, Node> nodes = SystemSimulatorUtils.getInstance().initializeNodes();
		assertEquals(4, nodes.size());
		assertTrue(nodes.keySet().contains(0));
		assertTrue(nodes.keySet().contains(1));
		assertTrue(nodes.keySet().contains(2));
		assertTrue(nodes.keySet().contains(3));
	}

	@Test
	public void testInitializeValueWithZeros() {
		String register = SystemSimulatorUtils.getInstance().initializeValueWithZeros(32);
		assertEquals(32, register.length());
		assertTrue(register.startsWith("0000000000"));
	}

	@Test
	public void testInitializeCPUs() {
		Map<String, CPU> cpus = SystemSimulatorUtils.getInstance().initializeCPUs();
		assertEquals(2, cpus.size());
		assertTrue(cpus.keySet().contains("0"));
		assertTrue(cpus.keySet().contains("1"));
	}

	@Test
	public void testPrint() {
		NUMASystem system = SystemSimulatorUtils.getInstance().initializeSimulator();
		SystemSimulatorUtils.getInstance().print(system);
	}

}
