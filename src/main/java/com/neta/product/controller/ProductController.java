package com.neta.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.neta.api.core.product.Product;
import com.neta.api.core.product.ProductServiceAPI;
import com.neta.product.service.ProductService;

@RestController
public class ProductController implements ProductServiceAPI {

	private static final Logger LOG = LoggerFactory.getLogger( ProductService.class );

	private final ProductService service;

	@Autowired
	public ProductController(ProductService service) {

		this.service = service;
	}

	@Override
	public Product createProduct(Product product) {

		LOG.debug( "Received request to create product with productId: {} ", product.getProductId() );
		return service.createProduct( product );
	}

	@Override
	public Product getProduct(int productId) {

		LOG.debug( "Received request to get product with productId: {} ", productId );
		return service.getProduct( productId );
	}

	@Override
	public void deleteProduct(int productId) {

		LOG.debug( "Received request to delete product with productId: {} ", productId );
		service.deleteProduct( productId );
	}
}
