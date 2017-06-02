package com.processor.Text_CDR_Processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class FileProcessor implements Runnable {

	private String fileName;

	public FileProcessor(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void run() {
		System.out.println("Starting Processing: " + fileName);
		try {
			processFile(fileName);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Completed Processing: " + fileName);
	}

	/**
	 * <h1>Method Name: processFile</h1> This method takes file name
	 * as input parameter, reads individual line in the file then tokenize and
	 * push into HashMap.
	 * <p>
	 * 
	 * @param fileName
	 * @author MD KALAM-UL-MAZID
	 * @version 1.0
	 * @throws InterruptedException 
	 */
	private void processFile(String fileName) throws InterruptedException {
		try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				tokenizeAndPushIntoHashMap(line, fileName);
				//tokenizeAndPushIntoHashMap(line);
				Thread.sleep(100);
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

	private void tokenizeAndPushIntoHashMap(String line, String fileName) {
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

		System.out.println("Processing CDR of MSISDN: " + cdrInfo[0] + " of file " + fileName);
		// Insert data into HashMap
		ProcessCDR.cdrHashMap.put(cdrInfo[0], singleCDRObject);
	}
}
