package com.naeva.example.rabbitmq.model.dtos;

import com.naeva.example.rabbitmq.model.ConfigScaipServer;
import com.naeva.example.rabbitmq.model.enums.ScaipSipTypeEnum;
import com.naevatec.sip.ua.SipConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ConfigScaipServerDTO {
    String alias;
    SipConfig sipConfig;
    String sipDomain;
    String sipUserName;
    String sipUserPassword;
    ScaipSipTypeEnum sipType;
    
    public ConfigScaipServer getConfigScaipServerFromDTO() {
        return new ConfigScaipServer(alias, sipConfig, sipDomain, sipUserName, sipUserPassword, sipType);
    }
}
