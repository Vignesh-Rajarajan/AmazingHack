package com.amazon.QueryProcessor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazon.dao.ORMImplementations;
import com.amazon.model.ProductItem;
import com.google.gson.Gson;

@Controller
public class QueryListener {

	@RequestMapping(value="src/queryPage",method=RequestMethod.GET)
 public String generatePage(){
		System.out.println("inside query page");
		return"query";
	}
	
	
	
	@RequestMapping(value="/submit",method=RequestMethod.POST)
	public String getQueryValue(@RequestParam("id")int id){
		ORMImplementations orm = new  ORMImplementations();
		ProductItem lsit=orm.findbyId(id);
		Gson gson= new Gson();
		String json = gson.toJson(lsit);
		return json;
		
	}
	
	
}
