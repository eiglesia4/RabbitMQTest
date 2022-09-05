package com.naeva.example.rabbitmq.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class mrq {
    private String ref;
    private String cid;
    private String dty;
    private String dco;
    private String stc;
    private String pri;
    private String lco;

}