package com.amazon.hackathon.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.amazon.hackathon.HibernateConnection.HibernateUtil;
import com.amazon.hackathon.csv.model.NotificationCriteria;
import com.amazon.hackathon.csv.model.ProductItem;
import com.amazon.hackathon.csv.model.SubscriberList;

public class HibernateTest {

	public static void main(String[] args) {
		ProductItem item1= new ProductItem("64445737","MR Ro4bot","eldfliot","14-56-45454","15","abdce");
		ProductItem item2= new ProductItem("64436747","MR Robeot1","ellidffot alderson","14-56-45454","19","abdce");
		SubscriberList subs= new SubscriberList();
		SubscriberList subs1= new SubscriberList();
		
		subs.setSubscriber_email("sdbhjdsbs@sh.com");
		subs.setSubscriber_id(3);
		subs.setSubscriber_userid("dkjkskj");
		subs1.setSubscriber_email("sdbhjssddsbs@sh.com");
		subs1.setSubscriber_id(4);
		subs1.setSubscriber_userid("dkffjkskj");
		
		NotificationCriteria notify= new NotificationCriteria();
		notify.setCriteria_id(45);
		notify.setCriteria(">3000");
Session session=HibernateUtil.buildSessionFactory().openSession();
Transaction trans = session.beginTransaction();
session.save(item1);
session.save(item2);
trans.commit();
session.close();

	}

}
