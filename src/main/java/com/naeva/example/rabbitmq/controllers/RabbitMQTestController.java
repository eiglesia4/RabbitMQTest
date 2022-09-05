package com.naeva.example.rabbitmq.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naeva.example.rabbitmq.model.BridgeMessage;
import com.naeva.example.rabbitmq.services.RabbitMQProducersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mqtest")
public class RabbitMQTestController {
    
    private final RabbitMQProducersService jmsProducersService;

    @RequestMapping(value="/simple", method = RequestMethod.GET)
    public String testSimple(){
        log.info("Sending a simple message");
        try {
            BridgeMessage message = new BridgeMessage("Hello World", false);
            jmsProducersService.sendBridgedMessage(message, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping(value="/retry", method = RequestMethod.GET)
    public String testRetry(@RequestParam(value="fail", required=false, defaultValue = "false") String fail){
        log.info("Sending a message with retry");
        try {
            BridgeMessage message = new BridgeMessage("Hello fail", false);
            jmsProducersService.sendBridgedMessage(message, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "OK";
    }

    @RequestMapping(value = "/scaip", method = RequestMethod.GET)
    public String testScaip(@RequestParam(value = "fail", required = false, defaultValue = "false") String fail,
            @RequestParam(value = "message", required = true) String message) {
        log.info("Sending a message with retry");
        try {
            BridgeMessage brideMessage = new BridgeMessage(message, false);
            jmsProducersService.sendBridgedMessage(brideMessage, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return "OK";
    }
}
