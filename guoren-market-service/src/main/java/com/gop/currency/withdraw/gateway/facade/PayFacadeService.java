package com.gop.currency.withdraw.gateway.facade;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.gop.currency.withdraw.gateway.factory.ChannelFactory;
import com.gop.domain.enums.WithdrawCurrencyPayMode;

@Service
public class PayFacadeService {

	public String pay(WithdrawCurrencyPayMode payMode, String innerOrder, String bankNo, String accoutName, String currency,
			BigDecimal amount, String mesg) {

		return ChannelFactory.getInstance().getPayChannel(payMode).takeOrder(innerOrder, bankNo, accoutName, currency, amount,
				mesg);

	}

}
