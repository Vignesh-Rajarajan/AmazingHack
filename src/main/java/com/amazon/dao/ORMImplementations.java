package com.amazon.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.amazon.HibernateConn.HibernateUtilforMongo;
import com.amazon.model.ProductItem;

public class ORMImplementations implements ORMOperations {
	private static Session session;
	private static Transaction transaction;
 
	
	@Override
	public  ProductItem findbyId(int id) {
		ProductItem book=null;
		try{
			session=HibernateUtilforMongo.getSessionFactory().openSession();
			return book=(ProductItem) session.createQuery("from BookList b where b.id:id").setParameter("id", id).uniqueResult();
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
			session=HibernateUtilforMongo.getSessionFactory().openSession();
			for (Integer i:id){
				ProductItem book=(ProductItem) session.createQuery("from BookList b where b.id:id").setParameter("id", id).uniqueResult();
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
			
			session=HibernateUtilforMongo.getSessionFactory().openSession();
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
