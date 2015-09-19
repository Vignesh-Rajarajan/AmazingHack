package com.amazon.src;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.amazon.dao.ORMImplementations;
import com.amazon.model.ProductItem;
import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/queryPage",method=RequestMethod.GET)
	 public String generatePage(){
			System.out.println("inside query page");
			return"query";
		}
		
		
		
		@RequestMapping(value="/submit",method=RequestMethod.POST)
		public String getQueryValue(int id){
			System.out.println("inside submit page");
			ORMImplementations orm = new  ORMImplementations();
			ProductItem lsit=orm.findbyId(id);
			Gson gson= new Gson();
			String json = gson.toJson(lsit);
			return json;
			
		}
		
	
}
