package com.gop.currency.withdraw.gateway.service.ulpay.configure;

import java.util.ResourceBundle;

public class Configurer {

	public final static ResourceBundle resourceBundle = ResourceBundle.getBundle("ulpay");
	
	public final static String EntCertPath =resourceBundle.getString("EntCertPath");
	
	public final static String CertPass = resourceBundle.getString("CertPass");
	
	public final static String CvmPath = resourceBundle.getString("CvmPath");
	
	public final static String batchPayUrl = resourceBundle.getString("batchPayUrl");
	
	public final static String payQueryUrl = resourceBundle.getString("payQueryUrl");
	
	public final static String merchantId = resourceBundle.getString("merchantId"); 
	
	public final static String userName = resourceBundle.getString("userName");;
	
	public final static String userPassword = resourceBundle.getString("userPassword");
	
	public final static String defaultVersion = resourceBundle.getString("defaultVersion");
	
	public final static String INFO_DATA_TYPE = resourceBundle.getString("INFO_DATA_TYPE");
	
	public final static String INFO_LEVEL = resourceBundle.getString("INFO_LEVEL");
	
	public final static String batchPayCode = resourceBundle.getString("batchPayCode");
	
	public final static String batchQueryCode = resourceBundle.getString("batchQueryCode");
	
	public final static String LEVEL = resourceBundle.getString("LEVEL");
}
