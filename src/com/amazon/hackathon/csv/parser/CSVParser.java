package com.amazon.hackathon.csv.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.amazon.hackathon.csv.model.ProductItem;

public class CSVParser {
	private static CSVParser instance = null;
	private int csvLinePerThread = 100;
	private int threadPoolSize = 10;
	
	public static CSVParser getInstance() {
		if( instance == null ) {
			instance = new CSVParser(); 
		}
		return instance;
	}
	
	// Fetch contents of the CSV File
	public List<ProductItem> fetchContents(String filePath) {
		
		List<ProductItem> itemList = new ArrayList<ProductItem>();
		
		try {
			File file = new File(filePath);
			List<ParserTask> parserTasks = new ArrayList<ParserTask>();
			ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
			
			if( file.exists()) {
				
				List<String> contents = null;
				int lineCounter;
				try ( BufferedReader br = new BufferedReader(new FileReader(file))) {
					
					contents = new ArrayList<String>();
					lineCounter = 1;
					String line = null;
					while( ( line = br.readLine() ) != null) {
						contents.add(line);
						if( lineCounter == csvLinePerThread ) {
							parserTasks.add(new ParserTask(contents));
							lineCounter = 1;
						} else { 
							lineCounter++;
						}
					}
				}
				
				List<Future<List<ProductItem>>> futures = executorService.invokeAll(parserTasks);
				Map<String, ProductItem> itemResults = new HashMap<String, ProductItem>();
				for( int i=0, size = futures.size(); i<size ;i++) {
					mergeResults( itemResults, futures.get(i).get());
				}
				
				Iterator<ProductItem> iter = itemResults.values().iterator();
				while( iter.hasNext()) {
					ProductItem item = iter.next();
					itemList.add(item);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return itemList;
	}
	
	private void mergeResults(Map<String, ProductItem> itemResults , List<ProductItem> itemsList) {
		
		for( int i=0;i<itemsList.size(); i++) {
			ProductItem item = itemsList.get(i);
			String itemId = item.getId();
			if( itemResults.containsKey(itemId)) {
				ProductItem itemFromResults = itemResults.get(itemId);
				itemFromResults.setProperties(item.getProperties());
			} else {
				itemResults.put(itemId, item);
			}
		}
	}
}
