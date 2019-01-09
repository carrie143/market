package com.gop.common;

import java.util.Map;

public interface UdCreditService {
	Boolean CheckUdCredit(String url, String pubkey, String secretkey, String serviceCode, String outOrderId,
			Map<String, String> parameter);
}
