package com.ccnuma.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.ccnuma.pojo.CPU;
import com.ccnuma.pojo.LoadInstruction;
import com.ccnuma.pojo.NUMASystem;
import com.ccnuma.pojo.Node;
import com.ccnuma.pojo.StoreInstruction;
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
		Map<Integer, CPU> cpus = SystemSimulatorUtils.getInstance().initializeCPUs();
		assertEquals(2, cpus.size());
		assertTrue(cpus.keySet().contains(0));
		assertTrue(cpus.keySet().contains(1));
	}

	@Test
	public void testSearchLocalCache() {
		Map<Integer, CPU> initializeCPUs = SystemSimulatorUtils.getInstance().initializeCPUs();
		initializeCPUs.get(0).getCacheEntries().get(0).setValidBit(true);
		assertNull(SystemSimulatorUtils.getInstance().searchLocalCache(initializeCPUs.get(0), new LoadInstruction("10001100000100010000000001101100")));
	}

	@Test
	public void testPrint() {
		NUMASystem system = SystemSimulatorUtils.getInstance().initializeSimulator();
		SystemSimulatorUtils.getInstance().print(system);
	}

	@Test
	public void testRead() {
		NUMASystem system = SystemSimulatorUtils.getInstance().initializeSimulator();
		SystemSimulatorUtils.getInstance().read(system, 0, 0, new LoadInstruction("10001100000100010000000001101100"));
		SystemSimulatorUtils.getInstance().print(system);
		SystemSimulatorUtils.getInstance().read(system, 0, 1, new LoadInstruction("10001100000100100000000001101100"));
		SystemSimulatorUtils.getInstance().print(system);
		SystemSimulatorUtils.getInstance().read(system, 1, 0, new LoadInstruction("10001100000100010000000001101100"));
		SystemSimulatorUtils.getInstance().print(system);
	}
	
	@Test
	public void testWrite() {
		NUMASystem system = SystemSimulatorUtils.getInstance().initializeSimulator();
		SystemSimulatorUtils.getInstance().read(system, 0, 0, new LoadInstruction("10001100000100010000000001101100"));
		SystemSimulatorUtils.getInstance().read(system, 0, 1, new LoadInstruction("10001100000100100000000001101100"));
		SystemSimulatorUtils.getInstance().write(system, 0, 0, new StoreInstruction("10101100000100010000000001001000"));
		SystemSimulatorUtils.getInstance().print(system);
	}

}
