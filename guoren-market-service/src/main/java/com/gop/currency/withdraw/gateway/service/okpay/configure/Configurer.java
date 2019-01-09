package com.gop.currency.withdraw.gateway.service.okpay.configure;

import java.util.ResourceBundle;

public class Configurer {

	public final static ResourceBundle resourceBundle = ResourceBundle.getBundle("okpay");
	
	public final static String walletId =resourceBundle.getString("okpay.walletId");
	
	public final static String password = resourceBundle.getString("okpay.password");
	
}