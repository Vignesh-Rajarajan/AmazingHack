package com.amazon.rest;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;



public class ORMImplementations implements ORMOperations {
	private static Session session;
	private static Transaction transaction;
 
	
	@Override
	public  ProductItem findbyId(String id) {
		ProductItem book=null;
		try{
			session=HibernateUtil.buildSessionFactory().openSession();
			 Statistics stats = HibernateUtil.buildSessionFactory().getStatistics();
		        System.out.println("Stats enabled="+stats.isStatisticsEnabled());
		        stats.setStatisticsEnabled(true);
		        Query query =session.createSQLQuery("Select * from ProductItem where id =:id").setParameter("id", id);
		        System.out.println(query);
			return book=(ProductItem) query.uniqueResult();
		}catch(Exception e){
			return book;
		}
		finally{
			session.close();
		}
		
		
	}

	@Override
	public  List<ProductItem> findbyIds(List<Integer> id) {
		
		List<ProductItem> list=null;
		try{
			list=new LinkedList<ProductItem>();
			session=HibernateUtil.buildSessionFactory().openSession();
			 Statistics stats = HibernateUtil.buildSessionFactory().getStatistics();
		        System.out.println("Stats enabled="+stats.isStatisticsEnabled());
		        stats.setStatisticsEnabled(true);
			for (Integer i:id){
				ProductItem book=(ProductItem) session.createQuery("from BookList b where b.id =:id").setParameter("id", id).uniqueResult();
				list.add(book);
			}
			return list;
		}
		
		catch(Exception e){
			return list;
		}
		finally{
			session.close();
		}
	}

	@Override
	public  boolean save(ProductItem book) {
		try{
			
			session=HibernateUtil.buildSessionFactory().openSession();
			transaction=session.beginTransaction();
			session.saveOrUpdate(book);
			return true;
		}
		
		catch(Exception e){
			return false;
		}
		finally{
			transaction.commit();
			session.close();
		}
		
	}

}
