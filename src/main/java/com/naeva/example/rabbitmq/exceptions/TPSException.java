package com.naeva.example.rabbitmq.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * TPSException to be used system wide
 * 
 * @author eiglesia
 */
@AllArgsConstructor
@Getter
@Setter
public class TPSException extends Throwable{
    @NonNull
    private String message;
    @NonNull    
    private Integer errorCode;
    private Throwable parentError;

    // CLIMAX MESSAGES
    public static final int CLIMAX_MALFORMED_MESSAGE = 0;
    public static final int CLIMAX_INVALID_MESSAGE_TYPE = 1; // Climax message MT is not 18H
    public static final int CLIMAX_INVALID_GROUP_OR_PARTITION_MESSAGE = 2; // Climax message GG is not 00H
    public static final int CLIMAX_INVALID_CHECKSUM_MESSAGE = 3; // Climax message GG is not 00H
    // COMMUNICATION PROBLEMS
    public static final int CONTROL_PANEL_DISCONNECTED = 10; // The Control Panel has disconnected from TPS
    // SIP UA problems
    public static final int SIP_UA_NOT_INTIALIZED = 20;
    public static final int SIP_UA_NOT_REGISTERED = 21;
    public static final int SIP_UA_CALL_ERROR = 22;
    // SCAIP MESSAGES
    public static final int SCAIP_NO_MESSAGE_FOR_CLIMAX_CODE = 30; // There are no Scaip message for Climax XYZ on
                                                                   // climax2scaip_data table
    
    

}
