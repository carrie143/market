package com.gop.api.cloud.request;

import com.gop.common.Confirm;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by wuyanjie on 2018/6/27.
 */
@Data
public class WithdrawConfirmRequest {
    @NotNull
    private String clientOrderNo ;
    private String refuseMs;
    @NotNull
    private Confirm confirm;
}
