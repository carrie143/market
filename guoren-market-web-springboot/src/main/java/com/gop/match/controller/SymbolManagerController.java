package com.gop.match.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gop.api.cloud.request.SymbolScaleReq;
import com.gop.api.cloud.response.SymbolScale;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.code.consts.MatchCodeConst;
import com.gop.domain.ConfigSymbol;
import com.gop.domain.ConfigSymbolProfile;
import com.gop.domain.enums.ConfigSymbolType;
import com.gop.exception.AppException;
import com.gop.match.dto.ConfigSymbolDto;
import com.gop.match.dto.SymbolDto;
import com.gop.match.service.ConfigSymbolProfileService;
import com.gop.match.service.SymbolService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController
@RequestMapping("/match")
public class SymbolManagerController {
	@Autowired
	private ConfigSymbolProfileService configSymbolProfileService;
	
	@Autowired
	private SymbolService symbolService;

  @Autowired
  private CloudApiService cloudApiService;

	// 获取交易对配置参数
	@RequestMapping(value = "/symbol", method = RequestMethod.GET)
	public SymbolDto getSymbolDto(@RequestParam("symbolName") String symbolName) {

		ConfigSymbol configSymbol = symbolService.getConfigSymbolBYSymbol(symbolName);
		if (null == configSymbol) {
			throw new AppException(MatchCodeConst.INVALID_SYMBOL);
		}

		return new SymbolDto(configSymbol);
	}

	// 获取所有交易对
	@RequestMapping(value = "/symbols", method = RequestMethod.GET)
	public List<SymbolDto> getAllSymbolDto() {
		List<ConfigSymbol> configSymbols = symbolService.getConfigSymbols();
		if (null == configSymbols) {
			return new ArrayList<>();
		}

		return configSymbols.stream().map(symbol -> new SymbolDto(symbol)).collect(Collectors.toList());
	}
	// 交易对配置列表
	@RequestMapping(value = "/symbolprofiles", method = RequestMethod.GET)
	public List<ConfigSymbolProfile> getConfigSymbolProfileList(@AuthForHeader AuthContext context) {

		List<ConfigSymbolProfile> list = configSymbolProfileService.selectAll();

		List<ConfigSymbol> configSymbols = symbolService.getConfigSymbols();

		configSymbols.forEach(v -> {
														List<SymbolScale> symbolList = cloudApiService.getSymbolScale(buildSymbolScaleRequest(v));
														symbolList.forEach(s -> {
															ConfigSymbolProfile price = buildSymbolProfile(s.getTradeAsset(),
																																						 s.getPriceAsset(),ConfigSymbolType.PRICEPRECISION,
																																						 s.getPriceScale().toString());
															ConfigSymbolProfile amount = buildSymbolProfile(s.getTradeAsset(),
																																							s.getPriceAsset(),ConfigSymbolType.AMOUNTPRECISION,
																																							s.getAmountScale().toString());
															list.add(price);
															list.add(amount);
														});
													}
												 );
		return list;
	}

	private ConfigSymbolProfile buildSymbolProfile(String tradeAsset, String priceAsset,
																								 ConfigSymbolType key,String value) {
		ConfigSymbolProfile profile = new ConfigSymbolProfile();
		profile.setSymbol(tradeAsset+"_"+priceAsset);
		profile.setProfileKey(key);
		profile.setProfileValue(value);
		return profile;
	}

	private SymbolScaleReq buildSymbolScaleRequest(ConfigSymbol v){
		final String PRICEPRECISION = "priceAsset";
		final String AMOUNTPRECISION = "tradeAsset";

		Map map = new HashMap<String,String>();
		map.put(PRICEPRECISION,v.getPriceAsset());
		map.put(AMOUNTPRECISION,v.getTradeAsset());
		List<Map<String,String>> symbol = new ArrayList<>();
		symbol.add(map);
		return new SymbolScaleReq(System.nanoTime(), symbol);
	}

	// 前端查询交易对费率列表
		@RequestMapping(value = "/config-list", method = RequestMethod.GET)
		// 参数加string symbol, configSymbol
		public List<ConfigSymbolDto> coinTradeFeeConfigList(@AuthForHeader AuthContext context,
				@RequestParam(value = "key") ConfigSymbolType key) {
			// 查询列表
			// List<ConfigSymbolProfile> list =
			// configSymbolProfileService.getConfigSymbolProfileList();
			List<ConfigSymbolProfile> list = configSymbolProfileService.getConfigSymbolProfileByKey(key);
			List<ConfigSymbolDto> result = new ArrayList<>();
			// 如果非空,则迭代赋值给dto传给前端
			// 查询抛异常添加在service的impl中
			if (null != list) {
				for (ConfigSymbolProfile profile : list) {
					ConfigSymbolDto dto = new ConfigSymbolDto();
					dto.setId(profile.getId());
					dto.setSymbol(profile.getSymbol());
					dto.setProfileKey(profile.getProfileKey());
					dto.setProfileValue(profile.getProfileValue());
					result.add(dto);
				}
			} else {
				// 如果为空,则将传给前端的list赋值为空
				result = Collections.EMPTY_LIST;
			}
			return result;
		}

}
