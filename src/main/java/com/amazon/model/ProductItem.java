package com.amazon.model;

import java.awt.print.Book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.ogm.datastore.document.options.AssociationStorage;
import org.hibernate.ogm.datastore.document.options.AssociationStorageType;
import org.hibernate.ogm.datastore.mongodb.options.ReadPreference;
import org.hibernate.ogm.datastore.mongodb.options.ReadPreferenceType;
import org.hibernate.ogm.datastore.mongodb.options.WriteConcern;
import org.hibernate.ogm.datastore.mongodb.options.WriteConcernType;


@Entity
@Table
@WriteConcern(WriteConcernType.JOURNALED)
@ReadPreference(ReadPreferenceType.PRIMARY_PREFERRED)
@AssociationStorage(AssociationStorageType.ASSOCIATION_DOCUMENT)
public class ProductItem {

	
	@Id
   
	private int id;
	
	@Column
	private String title;
	
	@Column
	private String author;
	
	@Column
	private String release_date;
	
	@Column
	private String list_price;
	
	
	public ProductItem(){
		
	}
	
	public ProductItem(int id, String title, String author, String release_date,String list_price ){
		this.id=id;
		this.title=title;
		this.author=author;
		this.release_date=release_date;
		this.list_price=list_price;
	}
	
	@Override
	public String toString() {
		return "BookList [id=" + id + ", title=" + title + ", author=" + author + ", release_date=" + release_date
				+ ", list_price=" + list_price + "]";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	
	
}
