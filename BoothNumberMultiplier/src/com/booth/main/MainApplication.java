package com.booth.main;

import java.io.IOException;

import com.booth.utilities.BoothUtils;

public class MainApplication {

	public static void main(String[] args) throws IOException {
		BoothUtils.getInstance().driver();
	}
}
