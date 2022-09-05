package com.naeva.example.rabbitmq.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.naeva.example.rabbitmq.model.enums.ScaipSipTypeEnum;
import com.naevatec.sip.ua.SipConfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ConfigScaipServer {
    private String alias;

    public Integer authCacheTime;
    public Boolean cacheClientConns;
    public Boolean jainSipLog;
    public Integer maxMsgForwards;
    public Long maxMsgSize;
    public Integer registerExpires;
    private String privateIp;
    private String publicIp;
    private String sipUaName;
    private Integer sipPort;
    private String sipProxyHost;
    private Integer sipProxyPort;
    private String sipStackName;
    public String sipTransport;
    private String sipDomain;
    private String sipUserName;
    private String sipUserPasword;
    @Enumerated(EnumType.STRING)
    private ScaipSipTypeEnum sipType;

    public ConfigScaipServer(String alias, SipConfig baseConfig, String sipDomain, String userName, String userPassword, ScaipSipTypeEnum sipType) {
        this.authCacheTime = baseConfig.authCacheTime;
        this.cacheClientConns = baseConfig.cacheClientConnections;
        this.jainSipLog = baseConfig.jainSipLog;
        this.maxMsgForwards = baseConfig.maxForwards;
        this.maxMsgSize = baseConfig.maxMsgSize;
        this.registerExpires = baseConfig.registerExpires;
        this.sipTransport = baseConfig.transport;
        this.privateIp = baseConfig.privateIp;
        this.publicIp = baseConfig.publicIp;
        this.sipUaName = baseConfig.sipGwUAName;
        this.sipPort = baseConfig.sipPort;
        this.sipProxyHost = baseConfig.sipProxyHost;
        this.sipProxyPort = baseConfig.sipProxyPort;
        this.sipStackName = baseConfig.sipStackName;
        this.alias = alias;
        this.sipDomain = sipDomain;
        this.sipUserName = userName;
        this.sipUserPasword = userPassword;
        this.sipType = sipType;

    }

    public SipConfig getSipConfig() {
        SipConfig sipConfig = new SipConfig();
        sipConfig.authCacheTime = this.authCacheTime;
        sipConfig.cacheClientConnections = this.cacheClientConns;
        sipConfig.jainSipLog = this.jainSipLog;
        sipConfig.maxForwards = this.maxMsgForwards;
        sipConfig.maxMsgSize = this.maxMsgSize;
        sipConfig.privateIp = this.privateIp;
        sipConfig.publicIp = this.publicIp;
        sipConfig.registerExpires = this.registerExpires;
        sipConfig.sipGwUAName = this.sipUaName;
        sipConfig.sipPort = this.sipPort;
        sipConfig.sipProxyHost = this.sipProxyHost;
        sipConfig.sipProxyPort = this.sipProxyPort;
        sipConfig.sipStackName = this.sipStackName;
        sipConfig.transport = this.sipTransport;
        return sipConfig;
    }

    public SipConfig getAsthis(SipConfig baseConfig, String userName, String userPassword, ScaipSipTypeEnum sipType) {
        SipConfig sipConfig = new SipConfig();

        // From base config
        sipConfig.authCacheTime = baseConfig.authCacheTime;
        sipConfig.cacheClientConnections = baseConfig.cacheClientConnections;
        sipConfig.jainSipLog = baseConfig.jainSipLog;
        sipConfig.maxForwards = baseConfig.maxForwards;
        sipConfig.maxMsgSize = baseConfig.maxMsgSize;
        sipConfig.registerExpires = baseConfig.registerExpires;
        sipConfig.transport = baseConfig.transport;
        // Particular configuration
        sipConfig.privateIp = privateIp;
        sipConfig.publicIp = publicIp;
        sipConfig.sipGwUAName = sipUaName;
        sipConfig.sipPort = sipPort;
        sipConfig.sipProxyHost = sipProxyHost;
        sipConfig.sipProxyPort = sipProxyPort;
        sipConfig.sipStackName = sipStackName;
        
        return sipConfig;
    }

}

