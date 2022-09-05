package com.naeva.example.rabbitmq.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naeva.example.rabbitmq.model.BridgeMessage;
import com.naeva.example.rabbitmq.services.RabbitMQProducersService;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQListener {

	private final RabbitMQProducersService jmsProducersService;



	@RabbitListener(queues = "${app.rabbitmq.scaipPrimaryQueue}") 
	public void scaipPrimaryQueue(final GenericMessage<String> message) 
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		BridgeMessage bridgeMessage = mapper.readValue(message.getPayload(), BridgeMessage.class);
		message.getHeaders().forEach((key, value) -> {
			log.info("Key: " + key + " Value: " + value);
		});
		Long tag = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG, Long.class);

		log.info("Received message: " + bridgeMessage.getMessage() + ", with redelivery " + bridgeMessage.getRedeliveryCount() );
		if (bridgeMessage.getWillFail()) {
			Boolean willFail = (Boolean) message.getHeaders().get(AmqpHeaders.REDELIVERED);
			if (bridgeMessage.getRedeliveryCount() < 3) {
				log.info("Message will fail");
				bridgeMessage.incrementRedeliveryCount();
				jmsProducersService.sendBridgedMessage(bridgeMessage, true);
			} else {
				log.info("Diverting to secondary queue, redelivery count " + bridgeMessage.getRedeliveryCount());
				jmsProducersService.sendBridgedMessage(bridgeMessage, false);
			}
		} else {
			log.info("Message is OK");
		}

	}

	@RabbitListener(queues = "${app.rabbitmq.scaipSecondaryQueue}")
	public void scaipSecondaryQueue(final GenericMessage<String> message) 
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		BridgeMessage bridgeMessage = mapper.readValue(message.getPayload(), BridgeMessage.class);

		message.getHeaders().forEach((key, value) -> {
			log.info("Key: " + key + " Value: " + value);
		});

		log.info("Received message: " + bridgeMessage.getMessage());

	}



}
