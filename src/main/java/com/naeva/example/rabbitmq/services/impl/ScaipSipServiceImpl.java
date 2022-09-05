package com.naeva.example.rabbitmq.services.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.naeva.example.rabbitmq.config.SipUAConfig;
import com.naeva.example.rabbitmq.exceptions.TPSException;
import com.naeva.example.rabbitmq.listeners.ScaipSipGwManagerEventListener;
import com.naeva.example.rabbitmq.model.ConfigScaipServer;
import com.naeva.example.rabbitmq.model.enums.ScaipSipTypeEnum;
import com.naeva.example.rabbitmq.services.ScaipSipService;
import com.naevatec.sip.ua.SipGWManager;
import com.naevatec.sip.ua.UserCredentialsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Scaip side of the equation
 *
 * @author eiglesia
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class ScaipSipServiceImpl implements ScaipSipService{
    private final SipUAConfig sipUAConfig;
    private  ConcurrentHashMap<String, SipGWManager> sipManagerMap = null; 

    @PostConstruct
    private synchronized void initialize() {
        if (sipManagerMap == null) {
            List<ConfigScaipServer> configScaipServerList = List.of(sipUAConfig.getConfigScaipServerFromDTO(ScaipSipTypeEnum.PRIMARY), sipUAConfig.getConfigScaipServerFromDTO(ScaipSipTypeEnum.FALLBACK));
            sipManagerMap = new ConcurrentHashMap<>();
            configScaipServerList.forEach(configScaipServer -> {
                log.info("Initializing Scaip SIP UA");
                SipGWManager sipManager = new SipGWManager();
                sipManager.setSipConfig(configScaipServer.getSipConfig());
                try {
                    sipManager.initUA();
                    ScaipSipGwManagerEventListener eventListener = new ScaipSipGwManagerEventListener(configScaipServer.getAlias());
                    sipManager.setListener(eventListener);
                    UserCredentialsImpl userCredentials = new UserCredentialsImpl(
                            configScaipServer.getSipUserName(), configScaipServer.getSipDomain(),
                            configScaipServer.getSipUserPasword());
                    register(userCredentials, sipManager);
                    sipManagerMap.put(configScaipServer.getAlias(), sipManager);
                } catch (Exception xcp) {
                    log.error("Could not initialize SIP interface with configuration {}",
                            configScaipServer.getSipConfig());
                    log.debug("Could not initialize SIP interface with configuration {}",
                            configScaipServer.getSipConfig(), xcp);
                    sipManager = null;
                    sipManagerMap.remove(configScaipServer.getAlias());
                } catch (TPSException e) {
                    // The exception has been already logged the error at register time, so only
                    // clean the SipManager
                    sipManager = null;
                    sipManagerMap.remove(configScaipServer.getAlias());
                }
            });

        }

    }

    public void register(UserCredentialsImpl sipCredentials, SipGWManager sipManager) throws TPSException {
        if (sipManager == null) {
            String message = "Cannot register sipuri. No SIP interface is initialized.";
            log.warn(message);
            throw new TPSException(message, TPSException.SIP_UA_NOT_INTIALIZED, null);
        } else {
            log.info("Registering sip uri {}", sipCredentials.getSipUri());

            sipManager.register(sipCredentials);
            if (log.isDebugEnabled())
                log.debug("register done for sipUri: {}", sipCredentials.getSipUri());
        }
    }

    public void unregister(UserCredentialsImpl sipCredentials, String scaipServerAlias) throws TPSException {
        SipGWManager sipManager = sipManagerMap.get(scaipServerAlias);
        if (sipManager == null) {
            String message = "Cannot unregister sipuri. No SIP interface is initialized. ScaipServerAlias " + scaipServerAlias;
            log.warn(message);
            throw new TPSException(message, TPSException.SIP_UA_NOT_INTIALIZED, null);
        } else {
            log.info("Unregistering sip uri {}", sipCredentials.getSipUri());

            sipManager.unregister(sipCredentials);
            if (log.isDebugEnabled())
                log.debug("unregister done for sipUri: {}", sipCredentials.getSipUri());
        }
    }

    public String sendMessage(String sipuriTo, String sipuriFrom, String mimeType, String message, String callId,
			Map<String, String> sipHeaders, String scaipServerAlias) throws TPSException {
        SipGWManager sipManager = sipManagerMap.get(scaipServerAlias);
        if (sipManager == null) {
            String messageException = "Cannot make outgoing call. No SIP interface is initialized. ScaipServerAlias "
                    + scaipServerAlias;
            log.warn(messageException);
            throw new TPSException(messageException, TPSException.SIP_UA_NOT_REGISTERED, null);
        } else {
            log.info("sending message from {} to {}", sipuriFrom, sipuriTo);

            
            try {
                // sipuriTo de la centralita scaip
                // sipuriFrom es la mia
                // callId identificador único
                callId = sipManager.sendMessage(sipuriTo, sipuriFrom, mimeType, message, callId, sipHeaders);
                if (callId != null) {
                    if (log.isDebugEnabled())
                        log.debug("message sent from {} to {}, callId: {}", sipuriFrom, sipuriTo, callId);
                } else
                    log.warn("message from {} to {}, could not be sent", sipuriFrom, sipuriTo);
                return callId;
            } catch (Exception e) {
                String messageException = "Error doing Sip Call";
                log.error(messageException);
                throw new TPSException(messageException, TPSException.SIP_UA_CALL_ERROR, e);
            }
        }
    }

    public String call(String sipuriTo, String sipuriFrom, String sdpOffer, String callId,
            Map<String, String> sipHeaders, String scaipServerAlias) throws TPSException {
        SipGWManager sipManager = sipManagerMap.get(scaipServerAlias);
        if (sipManager == null) {
            String message = "Cannot make outgoing call. No SIP interface is initialized. ScaipServerAlias "
                    + scaipServerAlias;
            log.warn(message);
            throw new TPSException(message, TPSException.SIP_UA_NOT_REGISTERED, null);
        } else {
            log.info("call from {} to {}", sipuriFrom, sipuriTo);

            try {
                callId = sipManager.call(sipuriTo, sipuriFrom, sdpOffer, callId, sipHeaders);
                if (callId != null) {
                    if (log.isDebugEnabled())
                        log.debug("call sent from {} to {}, callId: {}", sipuriFrom, sipuriTo, callId);
                } else
                    log.warn("call from {} to {}, could not be sent", sipuriFrom, sipuriTo);
                return callId;
            } catch (Exception e) {
                String message = "Error doing Sip Call";
                log.error(message);
                throw new TPSException(message, TPSException.SIP_UA_CALL_ERROR, e);
            }
        }
    }

    public String hangup(String callId, String scaipServerAlias) throws TPSException {
        SipGWManager sipManager = sipManagerMap.get(scaipServerAlias);
        if (sipManager == null) {
            String message = "Cannot hangup call. No SIP interface is initialized. ScaipServerAlias "
                    + scaipServerAlias;
            log.warn(message);
            throw new TPSException(message, TPSException.SIP_UA_NOT_INTIALIZED, null);
        } else {
            String hangedCallId;

            log.info("hangup call Id {}", callId);

            hangedCallId = sipManager.hangup(callId);
            if (log.isDebugEnabled())
                log.debug("hangup sent for callId: {}", callId);
            return hangedCallId;
        }
    }

    public void stopCall(String callId, String scaipServerAlias) throws TPSException {
        SipGWManager sipManager = sipManagerMap.get(scaipServerAlias);
        if (sipManager == null) {
            String message = "Cannot stop call. No SIP interface is initialized. ScaipServerAlias " + scaipServerAlias;
            log.warn(message);
            throw new TPSException(message, TPSException.SIP_UA_NOT_INTIALIZED, null);
        } else {
            log.info("stop call Id {}", callId);

            try {
                sipManager.stopCall(callId);
            } catch (Exception e) {
                String message = "Error stopping Sip Call";
                log.error(message);
                throw new TPSException(message, TPSException.SIP_UA_CALL_ERROR, e);
            }
            if (log.isDebugEnabled())
                log.debug("Call stopped for callId: {}", callId);
        }
    }


}
