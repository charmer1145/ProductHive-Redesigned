package com.practice.springbootjpa.producthive.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "productsinhive")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
	/*
	 * The @Id annotation specifies the primary key of an entity.
	 * @GeneratedValue provides for the specification of generation strategies for the values of primary keys.
	 * @SequenceGenerator, you can specify the sequence name, initial value, and allocation size for the 
	 * sequence. 
	 * This allows you to control the sequence generation process and ensure database consistency. 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "prod_seq")
	@SequenceGenerator(name = "prod_seq",initialValue = 11,allocationSize = 1)
	private Long pid;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String brand;
	
	@Column(nullable = false)
	private String madein;
	
	@Column(nullable = false)
	private float price;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getMadein() {
		return madein;
	}

	public void setMadein(String madein) {
		this.madein = madein;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
