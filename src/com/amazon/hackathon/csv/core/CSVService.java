package com.amazon.hackathon.csv.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import com.amazon.hackathon.csv.model.ProductItem;
import com.amazon.hackathon.csv.parser.CSVParser;

public class CSVService implements Runnable {
	
	private NotificationEngine notifEngine = null;
	private WatchService watchService = null;
	private WatchKey watchKey = null;
	private final String dirPath = "/Users/esakkiraj-1478/Esakki/Myworld/Geeky/AmazingHackathon/csvdir";
	
	private static CSVService instance = null;
	public static CSVService getInstance() {
		if( instance == null ) {
			instance = new CSVService();
		}
		return instance;
	}
	public void run() {
		Path path = Paths.get(dirPath);
		try {
			watchService = path.getFileSystem().newWatchService();
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			StartWatching();
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		}
	}
	
	// Method which watches the directory for New File Addition
	public void StartWatching() throws InterruptedException {
		System.out.println("Started Watching for File Additions.....");
		while( true ) {
			WatchKey key = watchService.take();
			
			List<WatchEvent<?>> events = key.pollEvents();
			for( WatchEvent event: events ) {
				String fileName = event.context().toString();
				if( fileName.indexOf(".csv") != -1 ) { 
					try {
						System.out.println("New file Added into Directory path");
						String fullFilePath = dirPath + File.separator + fileName;
						
						List<ProductItem> itemsList = FetchContents(fullFilePath);
						
						SendUpdatesToNotificationEngine(itemsList);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			key.reset();
		}
	}
	
	// Reads and Model the Contents into Item objects
	public List<ProductItem> FetchContents(String filePath ) throws FileNotFoundException {
		CSVParser csvParserInstance = CSVParser.getInstance();
		List<ProductItem> productItems = csvParserInstance.fetchContents(filePath);
		
		return productItems;
	}
	
	public void bindNotificationEngine( NotificationEngine notifEngine) {
		this.notifEngine = notifEngine;
	}
	
	private void SendUpdatesToNotificationEngine(List<ProductItem> itemsList) {
		this.notifEngine.ProcessUpdates(itemsList);
	}

}
