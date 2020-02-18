

package com.neta.product.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neta.api.core.product.Product;
import com.neta.product.entity.ProductEntity;
import com.neta.product.entity.ProductMapper;
import com.neta.product.persistence.ProductRepository;
import com.neta.util.exception.InvalidInputException;
import com.neta.util.exception.NotFoundException;
import com.neta.util.http.ServiceUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

@Service
public class ProductService {

	private static final Logger LOG = LoggerFactory.getLogger( ProductService.class );

	private final ProductRepository repository;

	private final ProductMapper mapper;

	private final ServiceUtil serviceUtil;

	@Autowired
	public ProductService(ProductRepository repository, ProductMapper mapper, ServiceUtil serviceUtil) {
		super();
		this.repository = repository;
		this.mapper = mapper;
		this.serviceUtil = serviceUtil;;
	}

	public Product createProduct(Product product) {

		LOG.debug( "createProduct: attempting to create product with productId" + product.getProductId() );
		
		if(product.getProductId() < 1) throw new InvalidInputException("Invalid productId: " + product.getProductId() );
		
		try {

			ProductEntity entity = mapper.apiToEntity( product );
			ProductEntity newEntity = repository.save( entity );

			LOG.debug( "createProduct: entity created for productId: {}", product.getProductId() );

			return mapper.entityToApi( newEntity );

		} catch (DuplicateKeyException e) {
			throw new InvalidInputException( "Duplicate key, Product Id: " + product.getProductId() );
		}
	}

	public Product getProduct(int productId) {

		LOG.debug( "getProduct: attempt to retrieve product with productId " + productId );
		
		if(productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

		ProductEntity entity = repository.findByProductId( productId )
				.orElseThrow( () -> new NotFoundException( "No product found for productId: " + productId ) );

		Product response = mapper.entityToApi( entity );
		response.setServiceAddress( serviceUtil.getServiceAddress() );

		return response;

	}

	public void deleteProduct(int productId) {

		LOG.debug( "deleteProduct: attempts to delete product with prodictId:{}", productId );

		repository.findByProductId( productId ).ifPresent( e -> repository.delete( e ) );

	}

}
