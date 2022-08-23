package com.naeva.example.rabbitmq.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Component
@NoArgsConstructor
@Slf4j
@Getter
@Setter
@ToString
public class BridgeMessage implements Serializable {
    private String message;
    private Boolean willFail;
    private int redeliveryCount = 0;

    public BridgeMessage(String message, Boolean willFail) {
        this.message = message;
        this.willFail = willFail;
    }

    public void incrementRedeliveryCount() {
        this.redeliveryCount++;
    }

}
