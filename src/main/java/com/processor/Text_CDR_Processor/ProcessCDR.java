package com.processor.Text_CDR_Processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

public class ProcessCDR {

	private static ConcurrentHashMap<String, CDR> cdrHashMap = new ConcurrentHashMap<>();

	public ProcessCDR() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * <h1>Method Name: listFiles</h1> This method takes directory name as input
	 * parameter and return list of files under that directory.
	 * <p>
	 * 
	 * @param directoryName
	 * @return ArrayList&lt;String&gt;
	 * @author MD KALAM-UL-MAZID
	 * @version 1.0
	 */
	public static ArrayList<String> listFiles(String directoryName) {

		ArrayList<String> fileNames = new ArrayList<>();
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				fileNames.add(file.getAbsolutePath());
				// System.out.println(file.getAbsolutePath());
			}
		}
		return fileNames;
	}

	public static void main(String[] args) {

		// load properties file information
		HashMap<String, String> propertiesInformation = ReadPropertiesFile.getProperties();
		// System.out.println(propertiesInformation.get("rawCDRDirectory"));

		// load filenames from raw CDR directory
		ArrayList<String> fileNames = listFiles(propertiesInformation.get("rawCDRDirectory"));
		// Math.

		// process individual file and push single CDR information into HashMap
		for (String fileName : fileNames) {
			processIndividualFile(fileName);

		}

		// Output of populated cdrHashMap
		Set<Entry<String, CDR>> values = cdrHashMap.entrySet();
		for (Entry<String, CDR> entry : values) {
			// int key = enrty.getKey();
			System.out.println(entry.getKey() + "............" + entry.getValue());
		}

		// Processing cdrHashMap for disbursing bonus

	}

	/**
	 * <h1>Method Name: processIndividualFile</h1> This method takes file name
	 * as input parameter, reads individual line in the file then tokenize and
	 * push into HashMap.
	 * <p>
	 * 
	 * @param fileName
	 * @author MD KALAM-UL-MAZID
	 * @version 1.0
	 */
	private static void processIndividualFile(String fileName) {
		try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				tokenizeAndPushIntoHashMap(line);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <h1>Method Name: tokenizeAndPushIntoHashMap</h1> This method takes String
	 * as input parameter, tokenize it and push into HashMap and return list of
	 * files under that directory.
	 * <p>
	 * 
	 * @param fileName
	 * @author MD KALAM-UL-MAZID
	 * @version 1.0
	 */

	private static void tokenizeAndPushIntoHashMap(String line) {
		// System.out.println(line);
		CDR singleCDRObject = new CDR();
		String[] cdrInfo = new String[3];
		int initialPosition = 0;

		StringTokenizer st = new StringTokenizer(line, ",");
		while (st.hasMoreTokens()) {
			// System.out.println(st.nextToken());
			cdrInfo[initialPosition] = st.nextToken();
			initialPosition++;
		}

		// Set data into singleCDRObject
		singleCDRObject.setCallingParty(cdrInfo[0]);
		singleCDRObject.setCalledParty(cdrInfo[1]);
		singleCDRObject.setCallDuration(Integer.parseInt(cdrInfo[2]));

		// Insert data into HashMap
		cdrHashMap.put(cdrInfo[0], singleCDRObject);
	}
}
