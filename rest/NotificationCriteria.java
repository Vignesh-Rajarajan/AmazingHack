package com.amazon.rest;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity(name="notification")
public class NotificationCriteria {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="criteria_id")
	private int criteria_id;
	
	@Column(name="criteria")
	private String criteria;
	
	@ManyToMany(mappedBy="notification")
	private Set<SubscriberList> subscribers = new HashSet<SubscriberList>();

	public int getCriteria_id() {
		return criteria_id;
	}

	public void setCriteria_id(int criteria_id) {
		this.criteria_id = criteria_id;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public Set<SubscriberList> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<SubscriberList> subscribers) {
		this.subscribers = subscribers;
	}
	
	
	
	
}
