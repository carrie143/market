package com.gop.domain;

import com.gop.domain.enums.WithdrawCoinOrderStatus;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by wuyanjie on 2018/5/25.
 */
@Data
public class WithDrawStatistic {
    private String assetCode;
    private String status;
    private BigDecimal number;
    private BigDecimal txfee;
}
