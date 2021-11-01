package com.example.demo;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getArrived_date() {
		return arrived_date;
	}

	public void setArrived_date(Date arrived_date) {
		this.arrived_date = arrived_date;
	}

	public Date getClosed_date() {
		return closed_date;
	}

	public void setClosed_date(Date closed_date) {
		this.closed_date = closed_date;
	}

	private Integer status;
	
	private Integer quantity;
	
	private String vendor;
	
	private String buyer;
	
	private String created_by;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Bangkok")
	private Date created_date; 
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Bangkok")
	private Date arrived_date; 
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Bangkok")
	private Date closed_date;
	
	
	public Order(String buyer, String created_by, String vendor,Integer quantity,Product product ) {
		this.buyer = buyer;
		this.created_by = created_by;
		this.vendor = vendor;
		this.quantity = quantity;
		this.product = product;
	}
	
	public Order() {
		
	}
}
