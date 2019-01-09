package com.gop.match.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.caucho.HessianClientInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import com.gop.code.consts.CommonCodeConst;
import com.gop.config.ZkGlobalConstants;
import com.gop.exception.AppException;
import com.gop.match.service.TradeServiceFactory;
import com.gop.match.service.discovery.LoadBalance;
import com.gop.match.service.discovery.ServiceUrlStorage;
import com.gop.match.service.discovery.TradeServiceUrlFactory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TradeServiceFactoryImpl implements TradeServiceFactory {

//	private Lock lock = new ReentrantLock();
//
////	private ConcurrentHashMap<String, TradeServcie> tradeServiceStorage = new ConcurrentHashMap<>();
//
//	@Autowired
//	private TradeServiceUrlFactory tradeServiceUrlFactory;
//
//	@Override
//	public TradeServcie get(String symbol) {
//		String url = tradeServiceUrlFactory.getUrlByLoadBalance(symbol);
//		if (url == null) {
//			throw new AppException(CommonCodeConst.SERVICE_ERROR, "没有发现symbol为" + symbol + "的撮合引擎");
//		}
//
//		TradeServcie tradeServcie = tradeServiceStorage.get(url);
//		if (null == tradeServcie) {
//			lock.lock();
//			tradeServcie = tradeServiceStorage.get(symbol);
//			if (tradeServcie != null) {
//				return tradeServcie;
//			}
//			try {
//				tradeServcie = build(url);
//				log.info("创建撮合引擎,{},{}", symbol, url);
//				tradeServiceStorage.put(url, tradeServcie);
//
//			} catch (Exception e) {
//				log.error("创建URL异常:", e);
//				throw new AppException(CommonCodeConst.SERVICE_ERROR);
//			} finally {
//				lock.unlock();
//			}
//		}
//		return tradeServcie;
//	}
//
//	private TradeServcie build(String serviceUrl) {
//		HessianClientInterceptor hessianClientInterceptor = new HessianClientInterceptor();
//		hessianClientInterceptor.setServiceUrl(serviceUrl);
//		hessianClientInterceptor.setServiceInterface(TradeServcie.class);
//		hessianClientInterceptor.setConnectTimeout(1000);
//		hessianClientInterceptor.prepare();
//		return (TradeServcie) new ProxyFactory(hessianClientInterceptor.getServiceInterface(), hessianClientInterceptor)
//				.getProxy(ClassUtils.getDefaultClassLoader());
//	}

}
