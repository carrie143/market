package com.gop.api.cloud.request;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/27.
 */
@Data
public class WithdrawCoinRequest {
    @NotNull
    private Long uid;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private BigDecimal fee;
    @NotNull
    private String outOrder;
    @NotNull
    private String address;
    @NotNull
    private String assetCode;
    @NotNull
    private String message;

    private String clientOrderNo;
}
