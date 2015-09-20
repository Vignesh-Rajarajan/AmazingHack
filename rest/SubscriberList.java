package com.amazon.rest;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name="subscribers")
public class SubscriberList {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int subscriber_id;
	
	@Column(name="userId")
	private String subscriber_userid;
	
	@Column(name="email")
	private String subscriber_email;
	
	@ManyToMany(cascade={CascadeType.PERSIST})
	@JoinTable(
	name="notification_criteria",
	joinColumns={@JoinColumn(name="subscriber_id")},
	inverseJoinColumns={@JoinColumn(name="actor_id")}
	)
	private Set<NotificationCriteria> notification = new HashSet<NotificationCriteria>();

	public int getSubscriber_id() {
		return subscriber_id;
	}

	public void setSubscriber_id(int subscriber_id) {
		this.subscriber_id = subscriber_id;
	}

	public String getSubscriber_userid() {
		return subscriber_userid;
	}

	public void setSubscriber_userid(String subscriber_userid) {
		this.subscriber_userid = subscriber_userid;
	}

	public String getSubscriber_email() {
		return subscriber_email;
	}

	public void setSubscriber_email(String subscriber_email) {
		this.subscriber_email = subscriber_email;
	}

	public Set<NotificationCriteria> getNotification() {
		return notification;
	}

	public void setNotification(Set<NotificationCriteria> notification) {
		this.notification = notification;
	}
	
	
	
	
}
