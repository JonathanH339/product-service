package com.neta.product.persistence;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.neta.product.entity.ProductEntity;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {
	
	Optional<ProductEntity> findByProductId(int productId);

}
