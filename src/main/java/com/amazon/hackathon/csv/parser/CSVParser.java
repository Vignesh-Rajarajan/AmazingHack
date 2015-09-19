package com.amazon.hackathon.csv.parser;

import java.util.List;

import com.amazon.model.ProductItem;

public class CSVParser {
	public static CSVParser instance = null;
	
	public CSVParser getInstance() {
		if( instance == null ) {
			instance = new CSVParser(); 
		}
		return instance;
	}
	
	// Fetch contents of the CSV File
	public List<ProductItem> fetchContents(String filePath) {
		return null;
	}
}
