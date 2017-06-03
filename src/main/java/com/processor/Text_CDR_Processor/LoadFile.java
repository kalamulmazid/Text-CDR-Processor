package com.processor.Text_CDR_Processor;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class LoadFile {

	private static ConcurrentHashMap<String, String> fileNames = new ConcurrentHashMap<>();

	private LoadFile() {
	}

	/**
	 * <h1>Method Name: loadFilesInformation</h1> This method takes directory name as input
	 * parameter and list files information into ConcurrentHashMap.
	 * <p>
	 * 
	 * @param directoryName
	 * @author MD KALAM-UL-MAZID
	 * @version 1.0
	 */
	static ConcurrentHashMap<String, String> loadFilesInformation(String directoryName) {
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				String key = file.getAbsolutePath();
				String value = file.getName();
				fileNames.put(key, value);
				// System.out.println(file.getAbsolutePath());
			}
		}
		return fileNames;
	}

}
