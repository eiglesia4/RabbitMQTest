package com.naeva.example.rabbitmq.services;

import com.google.gson.Gson;
import com.naeva.example.rabbitmq.model.BridgeMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JMSProducersService {
	public final RabbitTemplate rabbitTemplate;
	public final Binding bindingPrimary;
	private final Binding bindingSecondary;

	public void sendBridgedMessage(BridgeMessage message, boolean primary) {
		log.info("Sending message: " + message.getMessage());
		String json = new Gson().toJson(message);
		if(primary) 
			rabbitTemplate.convertAndSend(bindingPrimary.getExchange(), bindingPrimary.getRoutingKey(), json, m -> {addDefaultMessageProperties(m); return m;} );
		else
			rabbitTemplate.convertAndSend(bindingSecondary.getExchange(), bindingSecondary.getRoutingKey(), 
					json, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    addDefaultMessageProperties(message);                     
                     return message;
				}
             });
	}

	public void sendMessageSimple(final String exchangeName, final String routingKey, final String textMessage) {
		log.info("Sending message " + textMessage + " to queue - " + exchangeName + " with routing key - " + routingKey);
		rabbitTemplate.convertAndSend(exchangeName, routingKey, textMessage);
	}

	public void sendMessageText(final String exchangeName, final String routingKey, final String textMessage, final int deliveryTime) {
		log.info("Sending message " + textMessage + " to queue - " + exchangeName + " with routing key - " + routingKey);
		Message message = new Message(textMessage.getBytes(), createDefaultMessageProperties());
		rabbitTemplate.send(exchangeName, routingKey, message );
	}

	public void sendMessageJSON(final String exchangeName, final String routingKey, final String message) {
		Map map = new Gson().fromJson(message, Map.class);
		final String textMessage = "Hello" + map.get("name");
		log.info("Sending message " + textMessage + "to queue - " + exchangeName + " with routing key - " + routingKey);
		rabbitTemplate.send(exchangeName, routingKey, new Message(textMessage.getBytes(), createDefaultMessageProperties()));
	}

	private  MessageProperties createDefaultMessageProperties() {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("key", "value");
		messageProperties.setAppId("App Id");
		messageProperties.setContentType("application/json");
		messageProperties.setContentEncoding("UTF-8");
		messageProperties.setTimestamp(new Date());
		messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		messageProperties.setPriority(0);
		messageProperties.setUserId("user");
		messageProperties.setClusterId("clusterId 1");
		messageProperties.setHeader("x-delay", 5000);

		return messageProperties;
	}

	private void addDefaultMessageProperties(Message message) {
		message.getMessageProperties().setHeader("key", "value");
		message.getMessageProperties().setAppId("App Id");
		message.getMessageProperties().setContentType("application/json");
		message.getMessageProperties().setContentEncoding("UTF-8");
		message.getMessageProperties().setTimestamp(new Date());
		message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		message.getMessageProperties().setPriority(0);
		message.getMessageProperties().setUserId("user");
		message.getMessageProperties().setClusterId("clusterId 1");
		message.getMessageProperties().setHeader("x-delay", 5000);
	}
}
