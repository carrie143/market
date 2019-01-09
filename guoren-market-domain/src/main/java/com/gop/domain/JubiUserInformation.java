package com.gop.domain;

import java.math.BigDecimal;

import com.gop.domain.enums.JubiRegisterFlag;

import lombok.Data;
@Data
public class JubiUserInformation {
    private Integer id;

    private String phone;

    private String email;

    private BigDecimal amount;

    private JubiRegisterFlag registerFlag;
    
    private String assetCode;

   
}