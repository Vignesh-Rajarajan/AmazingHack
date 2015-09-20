package com.amazon.hackathon.csv.notification;

import java.util.List;

import com.amazon.hackathon.csv.notification.subscriber.Configuration;

public class NotificationSubscriber {
	private String name;
	private String implClassName;
	private List<Configuration> criteria;
	
	public NotificationSubscriber(String subName, String subImplClass, List<Configuration> criteriaList) {
		this.name = subName;
		this.implClassName = subImplClass;
		this.criteria = criteriaList;
	}

	public List<Configuration> getCriteria() {
		return this.criteria;
	}
	public String getName() {
		return name;
	}

	public String getImplClassName() {
		return implClassName;
	}
}
