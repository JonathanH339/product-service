

package com.neta.product.entity;

import org.springframework.stereotype.Component;

import com.neta.api.core.product.Product;

@Component
public class ProductMapper {
	
	public Product entityToApi(ProductEntity entity)
	{
		if(entity == null) {
			return null;
		}
		
		Product newProduct = new Product();
		
		newProduct.setProductId( entity.getProductId() );
		newProduct.setName( entity.getName() );
		newProduct.setDescription( entity.getDescription() );
		newProduct.setPrice( entity.getPrice() );
		
		return newProduct;
	}
	
	public ProductEntity apiToEntity(Product product) {
		
		if(product==null) {
			return null;
		}
		
		ProductEntity newEntity = new ProductEntity();
		
		newEntity.setProductId( product.getProductId() );
		newEntity.setName( product.getName() );
		newEntity.setDescription( product.getDescription() );
		newEntity.setPrice( product.getPrice() );
		
		return newEntity;
		
	}
}
