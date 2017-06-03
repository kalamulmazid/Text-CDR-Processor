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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProcessCDR {

	static HashMap<String, String> propertiesInformation = new HashMap<>();
	static ConcurrentHashMap<String, String> fileNames = new ConcurrentHashMap<>();
	static ConcurrentHashMap<String, CDR> cdrHashMap = new ConcurrentHashMap<>();

	public ProcessCDR() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		// load properties file information
		propertiesInformation = ReadPropertiesFile.getProperties();
		//System.out.println(propertiesInformation.get("rawCDRDirectory"));

		// load filenames from raw CDR directory
		fileNames = LoadFile.loadFilesInformation(propertiesInformation.get("rawCDRDirectory"));

		producer();
		
		consumer();

		//
		// Output of populated cdrHashMap
		Set<Entry<String, CDR>> cdrHashMapEntrySet = cdrHashMap.entrySet();
		for (Entry<String, CDR> entry : cdrHashMapEntrySet) {
			// int key = enrty.getKey();
			System.out.println(entry.getKey() + "............" + entry.getValue());
		}

		// Processing cdrHashMap for disbursing bonus

	}
	
	private static void consumer() {
		ExecutorService executor = Executors.newFixedThreadPool(3);

		Set<Entry<String, CDR>> cdrHashMapEntrySet = cdrHashMap.entrySet();
		for (Entry<String, CDR> cdrObjectKey : cdrHashMapEntrySet) {
			// process individual CDR of cdrHashMap
			executor.submit(new CDRHashMapProcessor(cdrObjectKey.getValue()));
		}

		System.out.println("CDR HashMap Processing Started...");
		/*
		 * below function is instructing executor service to stop taking any new
		 * task after the previous submission and should down itself after all
		 * the task is done
		 */
		executor.shutdown();

		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("CDR HashMap Processing Completed...");
	}

	private static void producer() {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		Set<Entry<String, String>> fileNamesEntrySet = fileNames.entrySet();
		for (Entry<String, String> fileName : fileNamesEntrySet) {
			// process individual file
			executor.submit(new FileProcessor(fileName.getKey()));
		}

		System.out.println("File Processing Started...");
		/*
		 * below function is instructing executor service to stop taking any new
		 * task after the previous submission and should down itself after all
		 * the task is done
		 */
		executor.shutdown();

		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("File Processing Completed...");
	}
}
