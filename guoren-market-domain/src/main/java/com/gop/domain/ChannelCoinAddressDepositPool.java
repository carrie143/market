package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.CoinAddressStatus;
import com.gop.domain.enums.DelFlag;

import lombok.Data;
@Data
public class ChannelCoinAddressDepositPool {
    private Integer id;

    private Integer uid;

    private String assetCode;

    private String name;

    private String coinAddress;
    
    private String walletAccount;

    private CoinAddressStatus addressStatus;

    private DelFlag delFlag;

    private Date createDate;

    private Date updateDate;

}