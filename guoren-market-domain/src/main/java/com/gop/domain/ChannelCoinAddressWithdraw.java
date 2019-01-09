package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.DelFlag;
import com.gop.domain.enums.InnerAddressFlag;
import com.gop.domain.enums.WithdrawCoinAddressAuthStatus;

import lombok.Data;

@Data
public class ChannelCoinAddressWithdraw {
    private Integer id;

    private Integer uid;

    private String assetCode;

    private String name;
    
    private WithdrawCoinAddressAuthStatus authStatus;

    private String coinAddress;

    private Date createDate;

    private Date updateDate;

    private String createIp;

    private InnerAddressFlag innerAddress;

    private DelFlag delFlag;

}