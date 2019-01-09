package com.gop.util;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceUtil {
	
	static private Logger logger = LoggerFactory.getLogger(PriceUtil.class);
	
	public static BigDecimal getMaxPrice(BigDecimal price){
		try{
			if(price.compareTo(new BigDecimal(0)) < 0)
				return new BigDecimal( ResourceUtils.get("common", "top_price"));
			BigDecimal stablePrice =new BigDecimal( ResourceUtils.get("common", "top_price"));
			String range = ResourceUtils.get("common", "range");
			BigDecimal rangePrice = price.add(price.multiply(new BigDecimal(range)));
			return stablePrice.compareTo( rangePrice ) < 0 ? stablePrice : rangePrice;
		}catch (Exception e) {
			logger.info("读取价格配置文异常");
			return null;
		}
		
	}
	
	
	public static BigDecimal getMinPrice(BigDecimal price){
		
		try{
			if(price.compareTo(new BigDecimal(0)) < 0)
				return new BigDecimal( ResourceUtils.get("common", "min_price"));
			BigDecimal stablePrice =new BigDecimal( ResourceUtils.get("common", "min_price"));
			String range = ResourceUtils.get("common", "range");
			BigDecimal rangePrice = price.subtract(price.multiply(new BigDecimal(range))); 
			return stablePrice.compareTo( rangePrice ) > 0 ? stablePrice : rangePrice;
		}catch (Exception e) {
			logger.info("读取价格配置文异常");
			return null;
		}
	}

}
