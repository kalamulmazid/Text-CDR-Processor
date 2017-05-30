package com.processor.Text_CDR_Processor;

import java.util.HashMap;

public class ReadCDR {

	public ReadCDR() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashMap<String, String> propertiesInformation = ReadPropertiesFile.getProperties();
		System.out.println(propertiesInformation.get("rawCDRDirectory"));

	}

}
