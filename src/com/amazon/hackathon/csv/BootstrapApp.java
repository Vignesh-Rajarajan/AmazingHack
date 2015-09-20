package com.amazon.hackathon.csv;

import com.amazon.hackathon.csv.core.CSVService;
import com.amazon.hackathon.csv.notification.NotificationService;

public class BootstrapApp {
	public static void main(String[] args) {
		
		System.out.println("Bootstrap loading all the classes....");
		
		Thread CSVServiceThread = new Thread(CSVService.getInstance());
		CSVServiceThread.start();
		
		NotificationService notificationService = new NotificationService();
		notificationService.Init();
		
	}
}
