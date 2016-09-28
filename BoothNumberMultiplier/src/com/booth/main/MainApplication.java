package com.booth.main;

import java.io.IOException;

import com.booth.utilities.BoothUtils;

/**
 * The main application to be executed in order to run the simulator.
 * 
 * @author Max
 *
 */
public class MainApplication {

	public static void main(String[] args) throws IOException {
		// Calling the main method driver to start the simulation
		BoothUtils.getInstance().driver();
	}
}
