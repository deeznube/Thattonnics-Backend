package com.example.demo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="product")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","orders"})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer product_id;

	private String name;

	private Integer quantity;
	
	private String description;

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="category_id")
    private Category category;
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category c) {
		this.category=c;
	}
		
	@OneToMany(targetEntity=Order.class, mappedBy="product",
    		cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
    public List<Order> getOrders(){
    	return orders;
    };
	
    public void setOrders(List<Order> o) {
    	this.orders=o;
    }
	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer id2) {
		this.product_id = id2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void addQuantity(Integer quantity) {
		this.quantity += quantity;
	}

	public void removeQuantity(Integer quantity){
		this.quantity -= quantity;
	}
	
	@Override
	public String toString() {
		return "Category [id=" + product_id + ", name=" + name + ", Des=" + description + ", quantity=" + quantity +"]";
	}
	
	public Product(String name, String description, Integer quantity, Category category ) {
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.category = category;
	}
	
	public Product() {
		
	}

}
