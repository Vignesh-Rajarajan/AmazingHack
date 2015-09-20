package com.amazon.hackathon.csv.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import com.amazon.hackathon.csv.core.util.ItemKeyConstants;
import com.amazon.hackathon.csv.model.ProductItem;

public class ParserTask implements Callable<List<ProductItem>> {
	
	List<String> csvContents = null;
	public ParserTask(List<String> csvContents) {
		this.csvContents = csvContents;
	}
	
	public List<ProductItem> call() {
		Map<String, ProductItem> resultCache = new HashMap<String, ProductItem>();
		
		for( int i=0, size = csvContents.size(); i < size; i++) {
			
			String csvLine = csvContents.get(i);
			String[] splittedCSVLine = csvLine.split(",");
			
			String itemId = splittedCSVLine[0];
			String attributeName = splittedCSVLine[1];
			String attributeValue = splittedCSVLine[2];
			ProductItem item = resultCache.get(itemId);
			
			if( item == null ) {
				item = new ProductItem();
				item.setId(itemId);
				resultCache.put(itemId, item);
			}
			String attributeKey = ItemKeyConstants.getAttributeKey(attributeName);
			
			if( attributeName.equalsIgnoreCase("title")) {
				item.setTitle(attributeValue);
			} else if( attributeName.equalsIgnoreCase("authors")) {
				item.setAuthor(attributeValue);
			} else if( attributeName.equalsIgnoreCase("releaseDate")) {
				item.setRelease_date(attributeValue);
			} else if( attributeName.equalsIgnoreCase("listPrice")) {
				item.setList_price(attributeValue);
			} else if( attributeName.equalsIgnoreCase("publisher")) {
				item.setPublisher(attributeValue);
			}  
			
		}
		
		Iterator<ProductItem> iter = resultCache.values().iterator();
		List<ProductItem> itemList = new ArrayList<ProductItem>();
		while( iter.hasNext()) {
			ProductItem item = iter.next();
			itemList.add(item);
		}
		return itemList;
	}

}
