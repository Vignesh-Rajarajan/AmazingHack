package com.amazon.hackathon.csv.notification;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.amazon.hackathon.csv.core.CSVService;
import com.amazon.hackathon.csv.core.NotificationEngine;
import com.amazon.hackathon.csv.model.ProductItem;
import com.amazon.hackathon.csv.notification.subscriber.Configuration;
import com.amazon.hackathon.csv.notification.subscriber.Subscriber;

public class NotificationService implements NotificationEngine {

	private static final String ConfigurationFile = "/Users/esakkiraj-1478/Esakki/Myworld/Geeky/AmazingHackathon/notificationconfig.xml";
	private static final int NOTIF_SEND_THREAD_COUNT = 10;
	Map<String, NotificationSubscriber> subscribers = new HashMap<String, NotificationSubscriber>();
	
	public void Init() {
		BuildSubscribersFromXML();
		registerToCSVService();
	}

	private void BuildSubscribersFromXML() {
		
		// Reading XML File
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		try {
			
			DocumentBuilder db = builderFactory.newDocumentBuilder();
			Document dom = db.parse(new File(ConfigurationFile));
			
			Element root = dom.getDocumentElement();
			NodeList subscribersList = root.getElementsByTagName("Subscriber");
			for( int i=0, listLength = subscribersList.getLength(); i<listLength; i++) {
				Element subscriberElement = (Element) subscribersList.item(i);
				
				String subscriberName = subscriberElement.getAttribute("name");
				String implClassName = subscriberElement.getAttribute("implclass");
				List<Configuration> criteria = new ArrayList<Configuration>();
				
				NodeList configsList = subscriberElement.getElementsByTagName("Configurations");
				if(configsList != null && configsList.getLength() > 0 ) {
					Element configsRoot = (Element) configsList.item(0);
					NodeList configurations = configsRoot.getElementsByTagName("config");
					
					for( int j=0; j <configurations.getLength(); j++) {
						Element configElement = (Element) configurations.item(j);
						String attributeName = configElement.getAttribute("attributename");
						String attributeValue = configElement.getAttribute("attributevalue");
						String condition = configElement.getAttribute("condition");
						
						Configuration config = new Configuration();
						config.setAttributeName(attributeName);
						config.setAttributeValue(attributeValue);
						config.setCondition(condition);
						
						criteria.add(config);
					}
				}
				
				NotificationSubscriber notifSubscriber = new NotificationSubscriber(subscriberName, implClassName, criteria);
				
				addSubscriber(notifSubscriber);
			}
			
			System.out.println("Notification Subscriber data built successfully");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	private void registerToCSVService() {
		CSVService csvService = CSVService.getInstance();
		csvService.bindNotificationEngine(this);
	}
	private void addSubscriber(NotificationSubscriber notifSub) {
		
		try {
			
			String subscriberName = notifSub.getName();
			if( subscribers.containsKey(subscriberName)) {
				throw new Exception("Subscriber with the given name "+subscriberName+", Already exists.");
			}
			subscribers.put(subscriberName, notifSub);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//This method gets executed when ProductItems are created or updated through CSV file. 
	public void ProcessUpdates(List<ProductItem> itemsList) {
		System.out.println("Update Arrived to Notification Engine");
		
		ExecutorService executor = Executors.newFixedThreadPool(NOTIF_SEND_THREAD_COUNT);
		List<NotificationSendTask> threadTaskList = new ArrayList<NotificationSendTask>();
		
	}
	
	public class  NotificationSendTask implements Callable {
		List<ProductItem> itemsListToProcess = null;
		List<ProductItem> processedItems = new ArrayList<ProductItem>();
		NotificationSubscriber notifSubscriber = null;
		
		public NotificationSendTask(List<ProductItem> itemsListToProcess, NotificationSubscriber notifSubscriber) {
			this.itemsListToProcess = itemsListToProcess;
			this.notifSubscriber = notifSubscriber;
		}
		public String call() throws InstantiationException, IllegalAccessException {
			
			for( int i=0;i<itemsListToProcess.size(); i++ ) {
				ProductItem productItem = itemsListToProcess.get(i);
				boolean isMatching = isCriteriaMatching( notifSubscriber, productItem);
				
				if( isMatching ) {
					processedItems.add(productItem);
				}

				if( processedItems.size() > 0 ) {
					String implClassString = notifSubscriber.getImplClassName();
					ClassLoader classLoader = NotificationSendTask.class.getClassLoader();
					try {
						Subscriber subscriber = (Subscriber) classLoader.loadClass(implClassString).newInstance();
						subscriber.execute(processedItems);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}
		
		private boolean isCriteriaMatching(NotificationSubscriber notifSubscriber, ProductItem item ) {
			List<Configuration> configurations = notifSubscriber.getCriteria();
			
			for( int i=0;i<configurations.size();i++) {
				Configuration config = configurations.get(i);
				
				String condition = config.getCondition();
				String attributeName = config.getAttributeName();
				String atrributeValue = config.getAttributeValue();
				
				if( attributeName.equalsIgnoreCase("listPrice")) {
					String itemListPrice = item.getList_price();
					if( itemListPrice == null ) return false;
					
					if( condition.equals("=")) {
						return ( Integer.parseInt(itemListPrice) == Integer.parseInt(atrributeValue));
					} else if( condition.equals(">=")) {
						return ( Integer.parseInt(itemListPrice) >= Integer.parseInt(atrributeValue));
					} else if( condition.equals("<=")) {
						return ( Integer.parseInt(itemListPrice) <= Integer.parseInt(atrributeValue));
					}
					
				} else if( attributeName.equalsIgnoreCase("releaseDate")) {
					String itemReleaseDate = item.getRelease_date();
					if( itemReleaseDate == null ) return false;

					DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
				} else if( attributeName.equalsIgnoreCase("title")) {
					String itemTitle = item.getTitle();
					if( itemTitle == null ) return false;
					
					if( condition.equals("=") && !itemTitle.equals(atrributeValue)) {
						return false;
					} else if( condition.equals("!=") && itemTitle.equals(atrributeValue)) {
						return false;
					}
					
				} else if( attributeName.equalsIgnoreCase("authors")) {
					String itemAuthor = item.getAuthor();
					if( itemAuthor == null ) return false;
					if( condition.equals("=") && !itemAuthor.equals(atrributeValue)) {
						return false;
					} else if( condition.equals("!=") && itemAuthor.equals(atrributeValue)) {
						return false;
					}
					
				} else if( attributeName.equalsIgnoreCase("publisher")) {
					String itemPublisher = item.getPublisher();
					if( itemPublisher == null ) return false;
					if( condition.equals("=") && !itemPublisher.equals(atrributeValue)) {
						return false;
					} else if( condition.equals("!=") && itemPublisher.equals(atrributeValue)) {
						return false;
					}
					
				}
			}
			
			return true;
		}
	}
	
}
