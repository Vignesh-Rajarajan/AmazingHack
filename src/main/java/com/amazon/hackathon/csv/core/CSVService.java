package com.amazon.hackathon.csv.core;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class CSVService implements Runnable {
	private WatchService watchService = null;
	private WatchKey watchKey = null;
	private final String dirPath = "/Users/esakkiraj-1478/Esakki/Myworld/Geeky/AmazingHackathon/csvdir";

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
		
		while( true ) {
			WatchKey key = watchService.take();
			
			List<WatchEvent<?>> events = key.pollEvents();
			for( WatchEvent event: events ) {
				String fileName = event.context().toString();
				FetchContents(fileName);
			}
			key.reset();
		}
	}
	
	// Reads and Model the Contents into Item objects
	public void FetchContents(String filePath ) {
		
		
	}

}
