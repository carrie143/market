package com.gop.c2c.facade;

import com.gop.c2c.dto.C2cBasePayChannelDto;
import com.gop.c2c.dto.C2cTransactionOrderArgsDto;
import com.gop.domain.C2cOrderPaymentDetail;
import com.gop.domain.C2cTransOrder;

public interface C2cPayFacade {

	C2cBasePayChannelDto getbasicPayChannelDtoByPaymentDetail(C2cOrderPaymentDetail orderPaymentDetail);

}
