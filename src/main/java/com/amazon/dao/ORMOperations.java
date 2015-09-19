package com.amazon.dao;

import java.util.List;

import com.amazon.model.ProductItem;

public interface ORMOperations {

	public  ProductItem findbyId(int id);
	public  List<ProductItem> findbyIds(List<Integer> id);
	public  boolean save(ProductItem book);
	
	
}
