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

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import static reactor.core.publisher.Mono.error;

@Service
public class ProductService {

	private static final Logger LOG = LoggerFactory.getLogger( ProductService.class );

	private final ProductRepository repository;
	private final ProductMapper mapper;
	private final ServiceUtil serviceUtil;

	@Autowired
	public ProductService(ProductRepository repository, ProductMapper mapper, ServiceUtil serviceUtil) {

		this.repository = repository;
		this.mapper = mapper;
		this.serviceUtil = serviceUtil;;
	}

	public Product createProduct(Product product) {

		LOG.debug( "createProduct: attempting to create product with productId" + product.getProductId() );

		if (product.getProductId() < 1)
			throw new InvalidInputException( "Invalid productId: " + product.getProductId() );

		ProductEntity entity = mapper.apiToEntity( product );
		Mono<Product> newEntity = repository.save( entity )
				.log()
				.onErrorMap( DuplicateKeyException.class,
						ex -> new InvalidInputException( "Duplicate key, Product Id: " + product.getProductId() ) )
				.map( e -> mapper.entityToApi( entity ) );

		return newEntity.block();

	}

	public Mono<Product> getProduct(int productId) {

		LOG.debug( "getProduct: attempt to retrieve product with productId " + productId );

		if (productId < 1) throw new InvalidInputException( "Invalid productId: " + productId );

		return repository.findByProductId( productId )
				.switchIfEmpty( error( new NotFoundException( "No Product found for productId: " + productId ) ) )
				.log()
				.map( e -> mapper.entityToApi( e ) )
				.map( e -> {

					e.setServiceAddress( serviceUtil.getServiceAddress() );
					return e;
				} );

	}

	public void deleteProduct(int productId) {

		LOG.debug( "deleteProduct: attempts to delete product with prodictId:{}", productId );
		repository.findByProductId( productId ).log().map( e -> repository.delete( e ) ).flatMap( e -> e ).block();
	}
}
