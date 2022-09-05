package com.naeva.example.rabbitmq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.naeva.example.rabbitmq.model.ConfigScaipServer;
import com.naeva.example.rabbitmq.model.dtos.ConfigScaipServerDTO;
import com.naeva.example.rabbitmq.model.enums.ScaipSipTypeEnum;
import com.naevatec.sip.ua.SipConfig;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
public class SipUAConfig {
    @Value("${sipgw.alias.primary}")
    public String aliasPrimary;
    @Value("${sipgw.auth-cache-time.primary}")
    public Integer authCacheTimePrimary;
    @Value("${sipgw.cache-client-conns.primary}")
    public Boolean cacheClientConnsPrimary;
    @Value("${sipgw.jain-sip-log.primary}")
    public Boolean jainSipLogPrimary;
    @Value("${sipgw.max-forwards.primary}")
    public Integer maxMsgForwardsPrimary;
    @Value("${sipgw.max-msg-size.primary}")
    public Long maxMsgSizePrimary;
    @Value("${sipgw.register-expires.primary}")
    public Integer registerExpiresPrimary;
    @Value("${sipgw.private-ip.primary}")
    public String privateIpPrimary;
    @Value("${sipgw.public-ip.primary}")
    public String publicIpPrimary;
    @Value("${sipgw.sip-ua-name.primary}")
    public String sipUaNamePrimary;
    @Value("${sipgw.sip-port.primary}")
    public Integer sipPortPrimary;
    @Value("${sipgw.sip-proxy.host.primary}")
    public String sipProxyHostPrimary;
    @Value("${sipgw.sip-proxy.port.primary}")
    public Integer sipProxyPortPrimary;
    @Value("${sipgw.sip-stack-name.primary}")
    public String sipStackNamePrimary;
    @Value("${sipgw.sip-transport.primary}")
    public String sipTransportPrimary;
    @Value("${sipgw.sip-type.primary}")
    public ScaipSipTypeEnum sipTypePrimary;
    @Value("${sipgw.sip-domain.primary}")
    public String sipDomainPrimary;
    @Value("${sipgw.sip-user-name.primary}")
    public String sipUserNamePrimary;
    @Value("${sipgw.sip-user-password.primary}")
    public String sipUserPasswordPrimary;

    @Value("${sipgw.alias.fall-back}")
    public String aliasFallback;
    @Value("${sipgw.auth-cache-time.fall-back}")
    public Integer authCacheTimeFallBack;
    @Value("${sipgw.cache-client-conns.fall-back}")
    public Boolean cacheClientConnsFallBack;
    @Value("${sipgw.jain-sip-log.fall-back}")
    public Boolean jainSipLogFallBack;
    @Value("${sipgw.max-forwards.fall-back}")
    public Integer maxMsgForwardsFallBack;
    @Value("${sipgw.max-msg-size.fall-back}")
    public Long maxMsgSizeFallBack;
    @Value("${sipgw.register-expires.fall-back}")
    public Integer registerExpiresFallBack;
    @Value("${sipgw.private-ip.fall-back}")
    public String privateIpFallBack;
    @Value("${sipgw.public-ip.fall-back}")
    public String publicIpFallBack;
    @Value("${sipgw.sip-ua-name.fall-back}")
    public String sipUaNameFallBack;
    @Value("${sipgw.sip-port.fall-back}")
    public Integer sipPortFallBack;
    @Value("${sipgw.sip-proxy.host.fall-back}")
    public String sipProxyHostFallBack;
    @Value("${sipgw.sip-proxy.port.fall-back}")
    public Integer sipProxyPortFallBack;
    @Value("${sipgw.sip-stack-name.fall-back}")
    public String sipStackNameFallBack;
    @Value("${sipgw.sip-transport.fall-back}")
    public String sipTransportFallBack;
    @Value("${sipgw.sip-type.fall-back}")
    public ScaipSipTypeEnum sipTypeFallBack;
    @Value("${sipgw.sip-domain.fall-back}")
    public String sipDomainFallBack;
    @Value("${sipgw.sip-user-name.fall-back}")
    public String sipUserNameFallBack;
    @Value("${sipgw.sip-user-password.fall-back}")
    public String sipUserPasswordFallBack;

    public SipConfig getSipConfigPrimary() {
        SipConfig sipConfig = new SipConfig();
        sipConfig.authCacheTime = this.authCacheTimePrimary;
        sipConfig.cacheClientConnections = this.cacheClientConnsPrimary;
        sipConfig.jainSipLog = this.jainSipLogPrimary;
        sipConfig.maxForwards = this.maxMsgForwardsPrimary;
        sipConfig.maxMsgSize = this.maxMsgSizePrimary;
        sipConfig.privateIp = this.privateIpPrimary;
        sipConfig.publicIp = this.publicIpPrimary;
        sipConfig.registerExpires = this.registerExpiresPrimary;
        sipConfig.sipGwUAName = this.sipUaNamePrimary;
        sipConfig.sipPort = this.sipPortPrimary;
        sipConfig.sipProxyHost = this.sipProxyHostPrimary;
        sipConfig.sipProxyPort = this.sipProxyPortPrimary;
        sipConfig.sipStackName = this.sipStackNamePrimary;
        sipConfig.transport = this.sipTransportPrimary;
        return sipConfig;
    }
    

    public ConfigScaipServerDTO getScaipConfigPrimary() {
        ConfigScaipServerDTO scaipServerDTO = new ConfigScaipServerDTO(aliasPrimary, getSipConfigPrimary(), sipDomainPrimary, sipUserNamePrimary, sipUserPasswordPrimary, ScaipSipTypeEnum.PRIMARY);
        return scaipServerDTO;
    }

    public SipConfig getSipConfigFallback() {
        SipConfig sipConfig = new SipConfig();
        sipConfig.authCacheTime = this.authCacheTimeFallBack;
        sipConfig.cacheClientConnections = this.cacheClientConnsFallBack;
        sipConfig.jainSipLog = this.jainSipLogFallBack;
        sipConfig.maxForwards = this.maxMsgForwardsFallBack;
        sipConfig.maxMsgSize = this.maxMsgSizeFallBack;
        sipConfig.privateIp = this.privateIpFallBack;
        sipConfig.publicIp = this.publicIpFallBack;
        sipConfig.registerExpires = this.registerExpiresFallBack;
        sipConfig.sipGwUAName = this.sipUaNameFallBack;
        sipConfig.sipPort = this.sipPortFallBack;
        sipConfig.sipProxyHost = this.sipProxyHostFallBack;
        sipConfig.sipProxyPort = this.sipProxyPortFallBack;
        sipConfig.sipStackName = this.sipStackNameFallBack;
        sipConfig.transport = this.sipTransportFallBack;
        return sipConfig;
    }

    public ConfigScaipServerDTO getScaipConfigFallBack() {
        ConfigScaipServerDTO scaipServerDTO = new ConfigScaipServerDTO(aliasFallback, getSipConfigFallback(), 
                sipDomainPrimary, sipUserNameFallBack,
                sipUserPasswordFallBack, ScaipSipTypeEnum.FALLBACK);

        return scaipServerDTO;
    }

    public ConfigScaipServerDTO getScaipConfig(ScaipSipTypeEnum sipType) {
        switch (sipType) {
            case FALLBACK: {
                return getScaipConfigFallBack();
            }
            case PRIMARY:
            default: {
                return getScaipConfigPrimary() ;
            }
        }
    }

    public ConfigScaipServer getConfigScaipServerFromDTO(ScaipSipTypeEnum sipType) {
        switch(sipType) {
            case FALLBACK: {
                return new ConfigScaipServer(this.aliasFallback, getSipConfigFallback(), this.sipDomainFallBack, this.sipUserNameFallBack, this.sipUserPasswordFallBack, 
                        sipType);
            }
            case PRIMARY:
            default: {
                return new ConfigScaipServer(this.aliasPrimary, getSipConfigPrimary(), this.sipDomainPrimary, this.sipUserNamePrimary,
                        this.sipUserPasswordPrimary, sipType);
            }
        }
    } 
}
