package com.naeva.example.rabbitmq.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naeva.example.rabbitmq.exceptions.TPSException;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;


@Getter
@Setter
public class ScaipMessageDTO {
    @JsonIgnore
    String generatedXMLMessage;
    private int redeliveryCount = 0;
    private String scaipPrimaryAlias;
    private String scaipFallbackAlias;
    mrq mrq;


    public static ScaipMessageDTO createSample() throws TPSException {
        ScaipMessageDTO scaipDTO = new ScaipMessageDTO();

        scaipDTO.setMrq(new mrq());
        scaipDTO.getMrq().setRef(scaipDTO.generateUUID());
        scaipDTO.getMrq().setCid("Acct");
        scaipDTO.getMrq().setDty("dty");
        scaipDTO.getMrq().setDco("dto");
        scaipDTO.getMrq().setStc("stc");
        scaipDTO.getMrq().setPri("pri");
        scaipDTO.getMrq().setLco("lco");

        return scaipDTO;
    }

    private String generateUUID() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.DIGITS, CharacterPredicates.LETTERS)
                .build();

        return generator.generate(16, 16);
    }

    public void incrementRedeliveryCount() {
        this.redeliveryCount++;
    }

}
