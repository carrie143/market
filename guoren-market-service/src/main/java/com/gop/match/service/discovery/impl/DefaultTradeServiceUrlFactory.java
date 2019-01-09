//package com.gop.match.service.discovery.impl;
//
//import java.util.Set;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.gop.code.consts.CommonCodeConst;
//import com.gop.exception.AppException;
//import com.gop.match.service.discovery.ListenerCallBack;
//import com.gop.match.service.discovery.LoadBalance;
//import com.gop.match.service.discovery.ServiceUrlDiscovery;
//import com.gop.match.service.discovery.ServiceUrlStorage;
//import com.gop.match.service.discovery.TradeServiceUrlFactory;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class DefaultTradeServiceUrlFactory implements TradeServiceUrlFactory {
//
//	@Autowired
//	private ServiceUrlStorage serviceUrlStorage;
//
//	@Autowired
//	private LoadBalance loadBalance;
//
//	@Autowired
//	private ServiceUrlDiscovery serviceUrlDiscovery;
//
//	@PostConstruct
//	public void init() throws Exception {
//		serviceUrlDiscovery.onAddEvent(new ListenerCallBack() {
//
//			@Override
//			public void callBack(String symbol, String url) {
//				log.info("监听到新添加的的撮合引擎:{},{}", symbol, url);
//				serviceUrlStorage.addtUrl(symbol, url);
//			}
//
//		});
//
//		serviceUrlDiscovery.OnRemoveEvent(new ListenerCallBack() {
//			@Override
//			public void callBack(String symbol, String url) {
//				log.info("监听到失效的撮合引擎:{},{}", symbol, url);
//				serviceUrlStorage.removeUrl(symbol, url);
//			}
//		});
//
//	}
//
//	@Override
//	public String getUrlByLoadBalance(String symbol) {
//		Set<String> localUrls = serviceUrlStorage.getServiceUrl(symbol);
//		if (localUrls == null || localUrls.isEmpty()) {
//			localUrls = serviceUrlDiscovery.getFromDiscovery(symbol);
//			log.info("添加撮合引擎symbol:{},url:{}", symbol, localUrls);
//			if (localUrls == null) {
//				throw new AppException(CommonCodeConst.SERVICE_ERROR, "未发现symbol:" + symbol);
//			}
//			serviceUrlStorage.addtUrlS(symbol, localUrls);
//		}
//
//		return loadBalance.loadBalance(localUrls);
//	}
//
//}
