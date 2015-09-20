package com.amazon.hackathon.csv.core.util;

public final class ItemKeyConstants {

	public static String getAttributeKey(String propertyName) {
		switch(propertyName) {
			case "title": return "title"; 
			case "authors": return "authors";
			case "release date": return "releaseDate";
			case "list price": return "listPrice";
			case "publisher": return "publisher";
		}
		return null;
	}

}
