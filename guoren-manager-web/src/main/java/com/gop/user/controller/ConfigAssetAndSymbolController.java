package com.gop.user.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import com.gop.api.cloud.client.HttpLayerException;
import com.gop.api.cloud.request.AssetRequest;
import com.gop.api.cloud.request.BrokerSymbolFee;
import com.gop.api.cloud.request.SymbolFeeAddReq;
import com.gop.api.cloud.request.SymbolQueryReq;
import com.gop.api.cloud.response.BrokerConfigAssetDto;
import com.gop.api.cloud.response.SymbolData;
import com.gop.api.cloud.response.TransferInAddressDto;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.asset.service.ConfigAssetAndSymbolService;
import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.ManagerConfigConst;
import com.gop.code.consts.MatchCodeConst;
import com.gop.code.consts.UserAssetCodeConst;
import com.gop.coin.transfer.service.ChannelCoinAddressDepositInfoService;
import com.gop.coin.transfer.service.ChannelCoinAddressDepositPoolService;
import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.domain.ChannelCoinAddressDepositInfo;
import com.gop.domain.ConfigAsset;
import com.gop.domain.ConfigAssetProfile;
import com.gop.domain.ConfigSymbol;
import com.gop.domain.ConfigSymbolProfile;
import com.gop.domain.enums.AssetStatus;
import com.gop.domain.enums.CoinAddressStatus;
import com.gop.domain.enums.ConfigAssetType;
import com.gop.domain.enums.ConfigSymbolType;
import com.gop.domain.enums.SymbolStatus;
import com.gop.exception.AppException;
import com.gop.match.service.ConfigSymbolProfileService;
import com.gop.match.service.ConfigSymbolService;
//import com.gop.match.service.discovery.impl.ZookeeperServiceUrlDiscovery;
import com.gop.user.dto.ConfigAssetDto;
import com.gop.user.dto.ConfigSymbolDto;
import com.gop.user.dto.ConfigSymbolProfileInitDto;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController("ConfigAssetAndSymbolController")
@RequestMapping("/managerAssetAndSymbol")
public class ConfigAssetAndSymbolController {
	@Autowired
	private ConfigAssetService configAssetService;
	@Autowired
	private ConfigSymbolService configSymbolService;
	@Autowired
	private ConfigSymbolProfileService configSymbolProfileService;
	@Autowired
	private ChannelCoinAddressDepositPoolService channelCoinAddressDepositPoolService;
//	@Autowired
//	private ZookeeperServiceUrlDiscovery zookeeperServiceUrlDiscovery;
	@Autowired
	private ConfigAssetProfileService configAssetProfileService;
	@Autowired
	private ConfigAssetAndSymbolService configAssetAndSymbolService;
	@Autowired
	private ChannelCoinAddressDepositInfoService channelCoinAddressDepositInfoService;
	@Autowired
	private CloudApiService cloudApiService;

	// 添加新币种或更新币种状态
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/configasset-edit", method = RequestMethod.POST)
	public void configAssetCodeCreateOrUpdate(@AuthForHeader AuthContext context,
			@Valid @RequestBody ConfigAssetDto dto) {
		if (dto.getSupplyAmount().compareTo(BigDecimal.ZERO) < 0
				|| dto.getTotalAmount().compareTo(BigDecimal.ZERO) < 0) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}

		// 查询云端是否配置该资产，不存在抛异常
		AssetRequest request = new AssetRequest();
		request.setAssetCode(dto.getAssetCode());
		List<BrokerConfigAssetDto> list = cloudApiService.getConfigAssetList(request);
		if(list == null || list.size()<=0){
			throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
		}

		ConfigAsset configAsset = new ConfigAsset();
		configAsset.setAssetCode(dto.getAssetCode());
		configAsset.setCurrencyType(dto.getCurrencyType());
		configAsset.setStatus(dto.getStatus());
		configAsset.setName(dto.getName());
		configAsset.setSupplyAmount(dto.getSupplyAmount());
		configAsset.setTotalAmount(dto.getTotalAmount());
		// 最小精度默认值为0
		configAsset.setMinPrecision(dto.getMinPrecision() > 0 ? dto.getMinPrecision() : 0);
		// 描述默认是0
		configAsset.setDescription(StringUtils.isNotBlank(dto.getDescription()) ? dto.getDescription() : "0");
		// webUrl默认是0
		configAsset.setWebUrl(StringUtils.isNotBlank(dto.getWebUrl()) ? dto.getWebUrl() : "0");

		configAsset.setUpdateDate(new Date());

		ConfigAsset configAssetInDB = configAssetService.getAssetConfig(dto.getAssetCode());
		// 更新已有币种
		// 校验所传入更新的币种,与数据库匹配,且不能修改币的名称(防止关联表出现错误)
		if (null != configAssetInDB ) {
			configAsset.setId(configAssetInDB.getId());
			configAssetService.updateConfigAsset(configAsset);
			return;
		}
		// 添加新币种
		configAssetAndSymbolService.addConfigAssetAndInitProfile(configAsset);
	}

	// 更改已有币种配置
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/configassetprofile-edit", method = RequestMethod.GET)
	public void configAssetProfilecreateOrUpdate(@AuthForHeader AuthContext context,
			@RequestParam("asset") String asset, @RequestParam("key") ConfigAssetType key,
			@RequestParam("value") String value) {
		// 校验value,使用正则表达式
		boolean validateValue = key.validValue(value);

		if (!validateValue) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		// 查询云端是否配置该资产，不存在抛异常
		AssetRequest request = new AssetRequest();
		request.setAssetCode(asset);
		List<BrokerConfigAssetDto> list = cloudApiService.getConfigAssetList(request);
		if(list == null || list.size()<=0){
			throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
		}

		ConfigAssetProfile configAssetProfile = new ConfigAssetProfile();
		configAssetProfile.setAssetCode(asset);
		configAssetProfile.setProfileKey(key);
		configAssetProfile.setProfileValue(value);
		configAssetProfileService.createOrUpdateConfigAssetProfile(configAssetProfile);
	}

	// 已有币种列表查询
	@Strategys(strategys = {@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/configasset-list", method = RequestMethod.GET)
	public List<ConfigAsset> getConfigAssetList(@AuthForHeader AuthContext context,
			@RequestParam(value = "status", required = false) AssetStatus status) {
		List<ConfigAsset> list = configAssetService.getAllAssetCode();
		return list;
	}

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/configassetprofile-list", method = RequestMethod.GET)
	public List<ConfigAssetProfile> getConfigAssetProfileList(@AuthForHeader AuthContext context) {
		AssetRequest request = new AssetRequest();
		List<BrokerConfigAssetDto> cloudAssetList = cloudApiService.getConfigAssetList(request);
		if(cloudAssetList == null || cloudAssetList.size()<=0){
			throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
		}
		Map<String,String> map = new HashMap<>();
		cloudAssetList.forEach(asset -> map.put(asset.getAssetCode(), String.valueOf(asset.getWithdrawFee())));

		List<ConfigAssetProfile> localList = configAssetProfileService.selectAll();
		List<String> assetList = new ArrayList<>();
		localList.stream().filter(asset ->!assetList.contains(asset.getAssetCode())).forEach(asset ->assetList.add(asset.getAssetCode()));

		for (int i=0;i<assetList.size();i++){
			if (map.containsKey(assetList.get(i))){
				ConfigAssetProfile configAssetProfile = new ConfigAssetProfile();
				configAssetProfile.setAssetCode(assetList.get(i));
				configAssetProfile.setProfileKey(ConfigAssetType.CLOUDFEE);
				configAssetProfile.setProfileValue(map.get(assetList.get(i)));
				localList.add(configAssetProfile);
			}
		}
		return localList;
	}

	// 添加新交易对
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/configsymbol-create", method = RequestMethod.POST)
	public void configSymbolCreate(@AuthForHeader AuthContext context,
			@Valid @RequestBody ConfigSymbolProfileInitDto dto) {

		ValidCheck(dto);

		ConfigSymbol configSymbol =buildConfigSymbol(dto);

		List<ConfigSymbolProfile> list = configAssetAndSymbolService.initSymbolProfile(dto.getSymbol());

		list.stream().map(v ->{
			switch (v.getProfileKey()){
				case HIGHLIGHTNO:v.setProfileValue(dto.getHighlightNo().toString());break;
			}
			return v;
		}).collect(Collectors.toList());

		// up到云端
		SymbolFeeAddReq symbol = new SymbolFeeAddReq();
		symbol.setNanoTime(System.nanoTime());
		symbol.setFeeMin(BigDecimal.ZERO.toString());
		symbol.setMakerFeeRatio(BigDecimal.ZERO.toString());
		symbol.setTakerFeeRatio(BigDecimal.ZERO.toString());
		symbol.setMethod(BrokerSymbolFee.Method.RATIO);
		symbol.setPriceAsset(dto.getPriceAsset());
		symbol.setTradeAsset(dto.getTradeAsset());
		try {
			cloudApiService.updateSymbolFee(symbol);
			configAssetAndSymbolService.addConfigSymbolAndInitProfile(configSymbol, list);
		}catch (Exception e){
			throw new AppException(UserAssetCodeConst.API_SYMBOL_PROFILE_ERROR);
		}
	}

	private ConfigSymbol buildConfigSymbol(ConfigSymbolProfileInitDto dto) {
		ConfigSymbol configSymbol = new ConfigSymbol();
		configSymbol.setSymbol(dto.getSymbol());
		configSymbol.setTradeAsset(dto.getTradeAsset());
		configSymbol.setPriceAsset(dto.getPriceAsset());
		configSymbol.setStatus(dto.getStatus());
		configSymbol.setTitle(dto.getTitle());
		configSymbol.setName(dto.getName());
		configSymbol.setDescription(dto.getDescription());
		configSymbol.setMinPrecision1(dto.getMinPrecision1());
		configSymbol.setMinPrecision2(dto.getMinPrecision1());
		configSymbol.setMinAmount1(dto.getMinAmount1());
		configSymbol.setMinAmount2(dto.getMinAmount2());
		configSymbol.setMaxAmount1(dto.getMaxAmount1());
		configSymbol.setMaxAmount2(dto.getMaxAmount2());
		return configSymbol;
	}

	private void ValidCheck(ConfigSymbolProfileInitDto dto) throws AppException{
		if (dto.getTradeAsset() ==null || dto.getTradeAsset().equals(dto.getPriceAsset())) {
			throw new AppException(CommonCodeConst.FIELD_ERROR, "币种名称不能一样");
		}
		if (null == dto.getStatus() || !SymbolStatus.INIT.equals(dto.getStatus())) {
			throw new AppException(CommonCodeConst.FIELD_ERROR, "创建交易对必须是init状态");
		}

		// 传递参数校验
		ConfigAsset tradeAsset = configAssetService.getAssetConfig(dto.getTradeAsset());
		ConfigAsset priceAsset = configAssetService.getAssetConfig(dto.getPriceAsset());
		if (null == tradeAsset || null == priceAsset) {
			throw new AppException(CommonCodeConst.FIELD_ERROR, "所传币种不存在");
		}
		if (!dto.getSymbol().equals(tradeAsset.getAssetCode() + "_" + priceAsset.getAssetCode())) {
			throw new AppException(CommonCodeConst.FIELD_ERROR, "所传交易对名称与币种名称不一致");
		}
		if (dto.getMinAmount1().compareTo(BigDecimal.ZERO) < 0 || dto.getMinAmount2().compareTo(BigDecimal.ZERO) < 0
				|| dto.getMinPrecision1() < 0 || dto.getMinPrecision2() < 0
				|| dto.getMaxAmount1().compareTo(dto.getMinAmount1()) < 0
				|| dto.getMaxAmount2().compareTo(dto.getMinAmount2()) < 0) {
			throw new AppException(CommonCodeConst.FIELD_ERROR, "所传数值不能小于零");
		}
		ConfigSymbol symbolInDB = configSymbolService.getConfigSymbol(dto.getSymbol());
		if (null != symbolInDB) {
			throw new AppException(ManagerConfigConst.SYMBOL_ALREADY_HAVE);
		}

		// 查询云端交易对配置
		SymbolQueryReq request = new SymbolQueryReq();
		request.setNanoTime(System.nanoTime());
		String[] coins = new String[2];
		coins[0] = dto.getTradeAsset();
		coins[1] = dto.getPriceAsset();
		request.setCoin(coins);
		List<SymbolData> symbolList = cloudApiService.getSymbols(request);
		if(symbolList == null || symbolList.size()<=0){
			throw new AppException(UserAssetCodeConst.INVALID_SYMBOL);
		}
	}

	// 更新交易对状态
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/configsymbol-update", method = RequestMethod.POST)
	public void configSymbolUpdate(@AuthForHeader AuthContext context, @Valid @RequestBody ConfigSymbolDto dto) {
		String[] coins = dto.getSymbol().split("_");
		String tradeAsset = coins[0];
		String priceAsset = coins[1];

		if (tradeAsset.equals(priceAsset)) {
			throw new AppException(CommonCodeConst.FIELD_ERROR, "币种名称不能一样");
		}
		// 传递参数校验
		ConfigAsset tradeAssetConfig = configAssetService.getAssetConfig(tradeAsset);
		ConfigAsset priceAssetConfig = configAssetService.getAssetConfig(priceAsset);
		if (null == tradeAssetConfig || null == priceAssetConfig) {
			throw new AppException(CommonCodeConst.FIELD_ERROR, "所传币种不存在");
		}
		if (!dto.getSymbol().equals(tradeAssetConfig.getAssetCode() + "_" + priceAssetConfig.getAssetCode())) {
			throw new AppException(CommonCodeConst.FIELD_ERROR, "所传交易对名称与币种名称不一致");
		}
		if (dto.getMinAmount1().compareTo(BigDecimal.ZERO) < 0 || dto.getMinAmount2().compareTo(BigDecimal.ZERO) < 0
				|| dto.getMinPrecision1() < 0 || dto.getMinPrecision2() < 0
				|| dto.getMaxAmount1().compareTo(dto.getMinAmount1()) < 0
				|| dto.getMaxAmount2().compareTo(dto.getMinAmount2()) < 0) {
			throw new AppException(CommonCodeConst.FIELD_ERROR, "所传数值不能小于零");
		}
		// 校验更新状态
		ConfigSymbol symbolInDB = configSymbolService.getConfigSymbol(dto.getSymbol());
		if (null == symbolInDB) {
			throw new AppException(ManagerConfigConst.API_INVOKE_ERROR);
		}

		// 查询云端交易对配置
		SymbolQueryReq request = new SymbolQueryReq();
		request.setNanoTime(System.nanoTime());
		request.setCoin(coins);
		List<SymbolData> symbolList = cloudApiService.getSymbols(request);
		if(symbolList == null || symbolList.size()<=0){
			throw new AppException(UserAssetCodeConst.INVALID_SYMBOL);
		}
		ConfigSymbol configSymbol = new ConfigSymbol();
		configSymbol.setSymbol(dto.getSymbol());
		configSymbol.setTradeAsset(dto.getTradeAsset());
		configSymbol.setPriceAsset(dto.getPriceAsset());
		configSymbol.setStatus(dto.getStatus());
		configSymbol.setTitle(dto.getTitle());
		configSymbol.setName(dto.getName());
		configSymbol.setDescription(dto.getDescription());
		configSymbol.setMinPrecision1(dto.getMinPrecision1());
		configSymbol.setMinPrecision2(dto.getMinPrecision1());
		configSymbol.setMinAmount1(dto.getMinAmount1());
		configSymbol.setMinAmount2(dto.getMinAmount2());
		configSymbol.setMaxAmount1(dto.getMaxAmount1());
		configSymbol.setMaxAmount2(dto.getMaxAmount2());

		configSymbol.setId(symbolInDB.getId());
		configSymbolService.updateConfigAsset(configSymbol);
	}

	// 更改交易对配置
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})")})
		@RequestMapping(value = "/configsymbolprofile-edit", method = RequestMethod.GET)
	public void configSymbolProfilecreateOrUpdate(@AuthForHeader AuthContext context,
			@RequestParam("configSymbol") String symbol, @RequestParam("key") ConfigSymbolType key,
			@RequestParam("value") String value) {
		// 校验value,使用正则表达式
		if (!key.validateValue(value)) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		// 校验symbol,并且是当前状态为init与listed
		ConfigSymbol configSymbol = configSymbolService.getConfigSymbol(symbol);
		if (null == configSymbol) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		if (SymbolStatus.DELISTED.equals(configSymbol.getStatus())) {
			throw new AppException(MatchCodeConst.SYMBOL_CLOSED);
		}
		ConfigSymbolProfile configSymbolProfile = new ConfigSymbolProfile();
		configSymbolProfile.setSymbol(symbol.toUpperCase());
		configSymbolProfile.setProfileKey(key);
		configSymbolProfile.setProfileValue(value);
		if (key.equals(ConfigSymbolType.ASSETMINFEE)||key.equals(ConfigSymbolType.MAKERFEERATE)||key.equals(ConfigSymbolType.TAKERFEERATE)){
			// up到云端
			SymbolFeeAddReq update = new SymbolFeeAddReq();
			update.setNanoTime(System.nanoTime());
			update.setFeeMin(key.equals(ConfigSymbolType.ASSETMINFEE) ? value : BigDecimal.ZERO.toString());
			// TODO: 2018/7/10  当前版本maker，taker手续费相同
			update.setTakerFeeRatio(key.equals(ConfigSymbolType.MAKERFEERATE) ? value : BigDecimal.ZERO.toString());
			update.setMakerFeeRatio(key.equals(ConfigSymbolType.MAKERFEERATE) ? value : BigDecimal.ZERO.toString());
			update.setMethod(BrokerSymbolFee.Method.RATIO);
			update.setPriceAsset(configSymbol.getPriceAsset());
			update.setTradeAsset(configSymbol.getTradeAsset());
			try {
				cloudApiService.updateSymbolFee(update);
			}catch (Exception e){
				throw new AppException(UserAssetCodeConst.API_SYMBOL_PROFILE_ERROR);
			}
		}
		configSymbolProfileService.createOrUpdateConfigSymbolProfile(configSymbolProfile);
	}

	// 已有交易对列表查询
	@Strategys(strategys = {@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/configsymbol-list", method = RequestMethod.GET)
	public List<ConfigSymbol> getConfigSymbolList(@AuthForHeader AuthContext context) {
		List<ConfigSymbol> list = configSymbolService.getAllSymbol();
		list.stream().forEach(c -> c.setTitle(c.getTradeAsset() + "/" + c.getPriceAsset()));
		return list;
	}

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/configsymbolprofile-list", method = RequestMethod.GET)
	public List<ConfigSymbolProfile> getConfigSymbolProfileList(@AuthForHeader AuthContext context) {
		List<ConfigSymbolProfile> list = configSymbolProfileService.selectAll();
		return list;
	}

	// 云币种列表查询
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
													 @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/assets", method = RequestMethod.GET)
	public List<String> getCLoudAssetList(@AuthForHeader AuthContext context) {
		List<BrokerConfigAssetDto> list = cloudApiService.getConfigAssetList(new AssetRequest());
		List<String> assetList = new ArrayList<>();
		list.stream().filter(asset -> asset.getStatus().equals(AssetStatus.LISTED)).forEach(asset ->{
			assetList.add(asset.getAssetCode());
		});
	return assetList;
	}
}
