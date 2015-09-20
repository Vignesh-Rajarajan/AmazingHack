package com.amazon.rest;

import java.util.List;




public interface ORMOperations {

	public  ProductItem findbyId(String id);
	public  List<ProductItem> findbyIds(List<Integer> id);
	public  boolean save(ProductItem book);
	
	
}
