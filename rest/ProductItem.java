package com.amazon.rest;

import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity(name="product_items")
@Table
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class ProductItem {

	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="author")
	private String author;
	
	@Column(name="release_date")
	private String release_date;
	
	@Column(name="list_price")
	private String list_price;
	
	@Column(name="publisher")
	private String publisher;
	
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public ProductItem(){
		
	}
	
	public ProductItem(String id, String title, String author, String release_date,String list_price,String publisher ){
		this.id=id;
		this.title=title;
		this.author=author;
		this.release_date=release_date;
		this.list_price=list_price;
		this.publisher=publisher;
	}
	
	@Override
	public String toString() {
		return "BookList [id=" + id + ", title=" + title + ", author=" + author + ", release_date=" + release_date
				+ ", list_price=" + list_price + "]";
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	public String getList_price() {
		return list_price;
	}
	public void setList_price(String list_price) {
		this.list_price = list_price;
	}
	public Properties getProperties() {
		Properties props = new Properties();
		
		if( this.title != null ) {
			props.put("title", title);
		}
		if( this.author != null ) {
			props.put("authors", author);
		}
		if( this.release_date != null ) {
			props.put("releaseDate", release_date);
		}
		if( this.list_price != null ) {
			props.put("listPrice", list_price);
		}
		if( this.publisher != null ) {
			props.put("publisher", publisher);
		}
		if( this.id != null ) {
			props.put("itemId", id);
		}
		
		return props;
	}
	public void setProperties(Properties props) {
		
		if( props.contains("title")) {
			this.title = props.getProperty("title");
		}
		if( props.contains("authors")) {
			this.author = props.getProperty("authors");
		}
		if( props.contains("releaseDate")) {
			this.release_date = props.getProperty("releaseDate");
		}
		if( props.contains("listPrice")) {
			this.list_price = props.getProperty("listPrice");
		}
		if( props.contains("publisher")) {
			this.publisher = props.getProperty("publisher");
		}
		if( props.contains("itemId")) {
			this.id = props.getProperty("itemId");
		}
	}
	
}
