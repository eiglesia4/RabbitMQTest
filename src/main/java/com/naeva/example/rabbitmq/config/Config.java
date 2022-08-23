package com.naeva.example.rabbitmq.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@EnableAutoConfiguration
public class Config {
    @Value("${app.rabbitmq.scaipPrimaryRoutingKey}")
    String scaipPrimaryRoutingKey;

    @Value("${app.rabbitmq.scaipSecondaryRoutingKey}")
    String scaipSecondaryRoutingKey;

    @Value("${app.rabbitmq.exchange}")
    String exchange;

    @Value("${app.rabbitmq.scaipPrimaryQueue}")
    String scaipPrimaryQueueName;

    @Value("${app.rabbitmq.scaipSecondaryQueue}")
    String scaipSecondaryQueueName;

    @Value("${spring.rabbitmq.host}")
    String rabbitmqHostName;
@Value("${spring.rabbitmq.port}")
String rabbitmqPortName;
@Value("${spring.rabbitmq.username}")
String rabbitmqUsername;
@Value("${spring.rabbitmq.password}")
String rabbitmqPassword;

    @Bean
CustomExchange delayExchange() {
    Map<String, Object> args = new HashMap<String, Object>();
    args.put("x-delayed-type", "direct");
    return new CustomExchange("my-exchange", "x-delayed-message", true, false, args);
}

    @Bean
    Queue queuePrimary() {
        return new Queue(scaipPrimaryQueueName, Boolean.TRUE);
    }

    @Bean
    Queue queueSecondary() {
        return new Queue(scaipSecondaryQueueName, Boolean.TRUE);
    }

    @Bean
    Binding bindingPrimary() {
        return BindingBuilder.bind(queuePrimary()).to(delayExchange()).with(scaipPrimaryRoutingKey).noargs();
    }

    @Bean
    Binding bindingSecondary() {
        return BindingBuilder.bind(queueSecondary()).to(delayExchange()).with(scaipSecondaryRoutingKey).noargs();
    }

    // @Bean
    // public static BeanPostProcessor rabbitTemplatePostProcessor() {
    //     return new BeanPostProcessor() {
    //         @Override
    //         public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    //             if (bean instanceof RabbitTemplate) {
    //                 RabbitTemplate rabbitTemplate = (RabbitTemplate) bean;

    //                 rabbitTemplate.addBeforePublishPostProcessors(m -> {
    //                     addDefaultMessageProperties(m);
    //                     return m;
    //                 });

    //                 rabbitTemplate.addAfterReceivePostProcessors(m -> {
    //                     addDefaultMessageProperties(m);
    //                     return m;
    //                 });
    //             }
    //             return bean;
    //         }
    //     };
    // }

    private static void addDefaultMessageProperties(Message message) {
        message.getMessageProperties().setAppId("App Id");
        message.getMessageProperties().setContentType("application/json");
        message.getMessageProperties().setContentEncoding("UTF-8");
        message.getMessageProperties().setTimestamp(new Date());
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        message.getMessageProperties().setPriority(0);
        message.getMessageProperties().setUserId("user");
        message.getMessageProperties().setClusterId("clusterId 1");
        message.getMessageProperties().setHeader("x-delay", 15000);
    }

}
