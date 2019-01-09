package com.gop.sms.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gop.domain.enums.ServiceCode;
import com.gop.domain.enums.ServiceProvider;
import com.gop.domain.enums.SysCode;
import com.gop.sms.config.BaiwuSmsConfig;
import com.gop.sms.config.LingkaiSmsConfig;
import com.gop.sms.config.SmsConfig;
import com.gop.sms.config.WeiwangSmsConfig;
import com.gop.sms.strategy.ServiceStrategy;
import com.gop.sms.strategy.impl.BaiwuService;
import com.gop.sms.strategy.impl.LingkaiService;
import com.gop.sms.strategy.impl.WeiwangService;
import com.gop.utils.ResourceUtils;

public class ServiceStrategyContext {
	private static final Logger log = LoggerFactory.getLogger(ServiceStrategyContext.class);
	
	private SmsConfig smsConfig;
	private ServiceStrategy serviceStrategy;
	
	public void determineServiceStrategy(ServiceProvider serviceProvider, SysCode sysCode, ServiceCode serviceCode){
		if(serviceProvider.equals(ServiceProvider.BAIWU)){
			serviceStrategy = new BaiwuService();
			
			BaiwuSmsConfig baiwuSmsConfig = new BaiwuSmsConfig();
			baiwuSmsConfig.setPostUrl(ResourceUtils.get("sms", "baiwu_url"));
			baiwuSmsConfig.setMsgId("");
			baiwuSmsConfig.setExt("");
			
			if(sysCode.equals(SysCode.GP_MARKET) && (serviceCode.equals(ServiceCode.VERIFY_CODE) || serviceCode.equals(ServiceCode.NOTICE))){
				baiwuSmsConfig.setAccountId(ResourceUtils.get("sms", "market_baiwu_id"));
				baiwuSmsConfig.setAccountPassword(ResourceUtils.get("sms", "market_baiwu_password"));
			} else if(sysCode.equals(SysCode.GP_BAO) && (serviceCode.equals(ServiceCode.VERIFY_CODE) || serviceCode.equals(ServiceCode.NOTICE))){
				baiwuSmsConfig.setAccountId(ResourceUtils.get("sms", "bao_baiwu_id"));
				baiwuSmsConfig.setAccountPassword(ResourceUtils.get("sms", "bao_baiwu_password"));	
			} else {
				log.error("暂不支持，系统编码={}，服务编码={}",sysCode,serviceCode);
				throw new RuntimeException("系统编码和服务编码暂不支持");
			}
			smsConfig = baiwuSmsConfig;
		}
		if(serviceProvider.equals(ServiceProvider.WEIWANG)){
			serviceStrategy = new WeiwangService();
			
			WeiwangSmsConfig weiwangSmsConfig = new WeiwangSmsConfig();
			weiwangSmsConfig.setPostUrl(ResourceUtils.get("sms", "weiwang_url"));
			weiwangSmsConfig.setCorpId(ResourceUtils.get("sms", "weiwang_corpid"));
			weiwangSmsConfig.setPrdId(ResourceUtils.get("sms", "weiwang_prdid"));
			if(sysCode.equals(SysCode.GP_MARKET) && (serviceCode.equals(ServiceCode.VERIFY_CODE) || serviceCode.equals(ServiceCode.NOTICE))){
				weiwangSmsConfig.setAccountId(ResourceUtils.get("sms", "market_weiwang_id"));
				weiwangSmsConfig.setAccountPassword(ResourceUtils.get("sms", "market_weiwang_password"));
			} else if (sysCode.equals(SysCode.GP_BAO) && (serviceCode.equals(ServiceCode.VERIFY_CODE) || serviceCode.equals(ServiceCode.NOTICE))){
				weiwangSmsConfig.setAccountId(ResourceUtils.get("sms", "bao_weiwang_id"));
				weiwangSmsConfig.setAccountPassword(ResourceUtils.get("sms", "bao_weiwang_password"));
			}else {
				log.error("暂不支持，系统编码={}，服务编码={}",sysCode,serviceCode);
				throw new RuntimeException("系统编码和服务编码暂不支持");
			}
			smsConfig = weiwangSmsConfig;
		}
		if(serviceProvider.equals(ServiceProvider.LINGKAI)){
			serviceStrategy = new LingkaiService();
			
			LingkaiSmsConfig LingkaiSmsConfig = new LingkaiSmsConfig();
			LingkaiSmsConfig.setPostUrl(ResourceUtils.get("sms", "lingkai_url"));
			LingkaiSmsConfig.setCell(ResourceUtils.get("sms", "lingkai_cell"));
			LingkaiSmsConfig.setSendTime(ResourceUtils.get("sms", "lingkai_sendtime"));
			if(sysCode.equals(SysCode.GP_MARKET) && (serviceCode.equals(ServiceCode.VERIFY_CODE) || serviceCode.equals(ServiceCode.NOTICE))){
				LingkaiSmsConfig.setAccountId(ResourceUtils.get("sms", "market_lingkai_id"));
				LingkaiSmsConfig.setAccountPassword(ResourceUtils.get("sms", "market_lingkai_password"));
			} else if(sysCode.equals(SysCode.GP_BAO) && (serviceCode.equals(ServiceCode.VERIFY_CODE) || serviceCode.equals(ServiceCode.NOTICE))){
				LingkaiSmsConfig.setAccountId(ResourceUtils.get("sms", "bao_lingkai_id"));
				LingkaiSmsConfig.setAccountPassword(ResourceUtils.get("sms", "bao_lingkai_password"));
			} else {
				log.error("暂不支持，系统编码={}，服务编码={}",sysCode,serviceCode);
				throw new RuntimeException("系统编码和服务编码暂不支持");
			}
			smsConfig = LingkaiSmsConfig;
		}
	}
	
	public String sendSms(String phone, String message){
		return serviceStrategy.sendSms(smsConfig, phone, message);
	}

	public SmsConfig getSmsConfig() {
		return smsConfig;
	}

	public void setSmsConfig(SmsConfig smsConfig) {
		this.smsConfig = smsConfig;
	}

	public ServiceStrategy getServiceStrategy() {
		return serviceStrategy;
	}

	public void setServiceStrategy(ServiceStrategy serviceStrategy) {
		this.serviceStrategy = serviceStrategy;
	}
}
