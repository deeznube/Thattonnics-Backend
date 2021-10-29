package com.example.demo;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer order_id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="product_id")
    private Product product;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product p) {
		this.product=p;
	}
	
	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getCreated_buy() {
		return created_buy;
	}

	public void setCreated_buy(String created_buy) {
		this.created_buy = created_buy;
	}

	private Integer status;
	
	private Integer quantity;
	
	private String vendor;
	
	private String buyer;
	
	private String created_buy;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created_date;  
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date arrived_date;  
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date closed_date; 
	
	@Override
	public String toString() {
		return "Category [id=" + order_id + ", Status=" + status + ", quantity=" + quantity + ", vendor=" + vendor +"]";
	}
}
