package com.naeva.example.rabbitmq.services;

import com.naeva.example.rabbitmq.model.BridgeMessage;

public interface RabbitMQProducersService {
    public void sendBridgedMessage(BridgeMessage message, boolean primary);

}
