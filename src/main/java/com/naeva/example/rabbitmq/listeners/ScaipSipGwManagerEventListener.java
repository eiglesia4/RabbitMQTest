package com.naeva.example.rabbitmq.listeners;

import java.util.Map;


import com.naevatec.sip.ua.GwManagerEventListener;
import com.naevatec.sip.ua.UserCredentialsImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ScaipSipGwManagerEventListener implements GwManagerEventListener {
    private String alias;

    /**
     * Signals the result of a REGISTER operation. If successful, the result is
     * true, any other way it is false.
     * If false, REGISTER will keep on trying until the sipuri is removed form
     * registered ones.
     * 
     * 
     * @param result         True if the registration try was successfull, false
     *                       otherwise
     * @param sipCredentials sip details of the URI that was trying to register
     * @param registeredUri  the URI registered by the registrar
     */
    @Override
    public void registerResult(Boolean result, UserCredentialsImpl sipCredentials, String registeredUri) {
        // TODO Auto-generated method stub
        log.info("Register Result Scaip Alias: {} - Result {} - Sip Credentials {} - Registered URI {} ", alias, result, sipCredentials, registeredUri);   
    }

    /**
     * Reports an incoming SIP message.
     * 
     * @param callId      Identifier of the call
     * @param from        sipuri of the originator of the call (remote endpoint)
     * @param to          sipuri of the recipient of the call (local endpoint)
     * @param mimeType    mime type of incoming message
     * @param message     received message
     * @param sipHeaders, headers that get along with the INVITE request
     * @return
     */
    @Override
    public String incomingMessage(String callId, String from, String to, String mimeType, String message, Map<String, String> sipHeaders) {
        // TODO Auto-generated method stub
        log.info("Incoming message callId {} - from {} - to {} - mimeType {} - message {} - sipHeaders {}", callId, from, to, mimeType, message, sipHeaders);   
        return null;
    }

    /**
     * reports the status of a previous sent message
     * 
     * @param callId     id of the sent message
     * @param successful true if successfully sent false otherwise
     */
    @Override
    public void messageSentStatus(String callId, boolean successful) {
        // TODO Auto-generated method stub
        log.info("Message Sent Status callId {} - successful {} ", callId, successful);        
    }
    
    @Override
    public void incomingCallEstablished(String arg0, String arg1, String arg2, String arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void callStopped(String arg0, String arg1, String arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public String callUpdated(String arg0, String arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String incomingCall(String arg0, String arg1, String arg2, String arg3, Map<String, String> arg4) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String outgoingCallAccepted(String arg0, String arg1, String arg2, String arg3) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void outgoingCallFailed(String arg0, String arg1, String arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void outgoingCallRinging(String arg0, String arg1, String arg2) {
        // TODO Auto-generated method stub

    }
}
