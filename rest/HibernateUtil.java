package com.amazon.rest;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;





public class HibernateUtil {

	private static SessionFactory sessionFactory ;
	 
	   public static SessionFactory buildSessionFactory()
	   {
	      try
	      {
	         if (sessionFactory == null)
	         {
	        	 Properties db_properties= new Properties();
	     		db_properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
	     		db_properties.setProperty("hibernate.connection.username", "root");
	     		db_properties.setProperty("hibernate.connection.password", "");
	     		db_properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/test");
	     		db_properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
	     		db_properties.setProperty("hibernate.cache.use_second_level_cache", "true");
	     		db_properties.setProperty("hibernate.connection.pool_size", "1");
	     		db_properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
	     		db_properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
	     		db_properties.setProperty("hibernate.ehcache.configurationResourceName", "ehcache.xml");
	     		
	     	
	     		
	     	
	     		
	     		System.out.println("^^^^^^^^^prop"+db_properties);
	     		
	     		Configuration cfg=  new Configuration().setProperties(db_properties)
	     				                                
	     				                                .addAnnotatedClass(ProductItem.class);
	     				                                
	     		
	     		StandardServiceRegistryBuilder serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());        
	    		System.out.println("@@@@@@@@@serviceRegistry"+serviceRegistry);
	    		//sessionfactory = cfg.buildSessionFactory(serviceRegistry);
	    		
	    		sessionFactory= cfg.buildSessionFactory(serviceRegistry.build());
	         }
	         return sessionFactory;
	      } catch (Throwable ex)
	      {
	         System.err.println("Initial SessionFactory creation failed." + ex);
	         throw new ExceptionInInitializerError(ex);
	      }
	   }
	
}
