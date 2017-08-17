package com.gy.hsi.ds.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReadUtils {
	
	/**
	 * 将文件内容读取出来
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String fileToLines(File file) throws IOException {
		StringBuilder lines = new StringBuilder();

		String line;
		
		@SuppressWarnings("resource")
		final BufferedReader in = new BufferedReader(new FileReader(file));
		
		while ((line = in.readLine()) != null) {
			lines.append(line).append(System.lineSeparator());
		}

		return lines.toString();
	}
}
