package com.gop.match.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.match.service.SymbolService;
import com.gop.match.service.TradeServiceFactory;
import com.gop.match.service.TradeServiceManager;

@Service
public class TradeServiceManagerImpl implements TradeServiceManager {

	@Autowired
	SymbolService symbolService;
	@Autowired
	TradeServiceFactory tradeServiceFactory;

//	@Override
//	public TradeServcie getTradeService(String symbol) {
//
//		TradeServcie tradeServcie = get(symbol);
//		return tradeServcie;
//	}
//
//	private TradeServcie get(String symbol) {
//		TradeServcie tradeServcie = tradeServiceFactory.get(symbol);
//
//		return tradeServcie;
//	}

}
