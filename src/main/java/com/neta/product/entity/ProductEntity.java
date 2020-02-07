package com.neta.product.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="products")
public class ProductEntity {
	
	@Id
    private String id;

    @Version
    private Integer version;

    @Indexed(unique = true)
    private int productId;
    
    private String name;
    
    private String description;
    
    private double price;

	public ProductEntity() {
		
	}

	public ProductEntity(int productId, String name, String description, double price) {		
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

	
	public Integer getVersion() {
		return version;
	}

	
	public void setVersion(Integer version) {
		this.version = version;
	}

	
	public int getProductId() {
		return productId;
	}

	
	public void setProductId(int productId) {
		this.productId = productId;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	} 
}
