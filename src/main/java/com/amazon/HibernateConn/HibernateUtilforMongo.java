package com.amazon.HibernateConn;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.ogm.cfg.OgmConfiguration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtilforMongo {

	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory(){
		Configuration configuration = new OgmConfiguration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings( configuration.getProperties() ).build();
		 sessionFactory = configuration.buildSessionFactory( serviceRegistry );
		return sessionFactory;
	}
	
}
