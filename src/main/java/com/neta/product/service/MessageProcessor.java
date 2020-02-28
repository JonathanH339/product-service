package com.neta.product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.neta.api.core.event.Event;
import com.neta.api.core.product.Product;
import com.neta.util.exception.EventProcessingException;

@EnableBinding(Sink.class)
public class MessageProcessor {

	private static final Logger LOG = LoggerFactory.getLogger( MessageProcessor.class );

	private final ProductService service;

	@Autowired
	public MessageProcessor(ProductService service) {

		this.service = service;
	}

	@StreamListener(target = Sink.INPUT)
	public void process(Event<Integer, Product> event) {

		LOG.info( "Process created at {}...", event.getEventCreatedAt() );

		switch (event.getEventType()) {

			case CREATE:
				Product product = event.getData();
				LOG.info( "Create product with ID: {}", product.getProductId() );
				service.createProduct( product );
				break;

			case DELETE:
				int productId = event.getKey();
				LOG.info( "Delete product with productId: {}", productId );
				service.deleteProduct( productId );
				break;

			default:
				String errorMessage = "Incorrect event type: " + event.getEventType()
						+ ", expected CREATE or DELETE event";
				LOG.warn( errorMessage );
				throw new EventProcessingException( errorMessage );
		}

		LOG.info( "Message processing done." );
	}

}
