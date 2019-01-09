package com.gop.coin.transfer.controller;

import com.alibaba.fastjson.JSON;
import com.gop.api.cloud.request.AssetRequest;
import com.gop.api.cloud.service.CloudApiService;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.UserAssetCodeConst;
import com.gop.coin.transfer.dto.AddressDto;
import com.gop.coin.transfer.dto.TransferInAddressDto;
import com.gop.coin.transfer.dto.TransferOutDto;
import com.gop.coin.transfer.service.ChannelCoinAddressDepositInfoService;
import com.gop.coin.transfer.service.WithdrawCoinAddressService;
import com.gop.domain.ChannelCoinAddressWithdraw;
import com.gop.domain.ConfigAsset;
import com.gop.domain.enums.CurrencyType;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.user.service.UserService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@Slf4j
@RestController
@RequestMapping("/coin")
public class CoinAddressManController {
	// 获取转出地址
	@Autowired
	private ConfigAssetService configAssetService;

	@Autowired
	private WithdrawCoinAddressService withdrawService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CloudApiService cloudApiService;
	
	@Autowired
	private ChannelCoinAddressDepositInfoService channelCoinAddressDepositInfoService;

	//查询转出地址
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/transfer-out-address-query", method = RequestMethod.GET)
	public PageModel<AddressDto> getCoinTransferOutAddress(@AuthForHeader AuthContext context,
			@RequestParam("assetCode") String assetCode, @RequestParam("pageSize") Integer pageSize,
			@RequestParam("pageNo") Integer pageNo) {

		ConfigAsset configAsset = configAssetService.getAssetConfig(assetCode);
		if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN)) {
			throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
		}
		int uid = context.getLoginSession().getUserId();
		PageInfo<ChannelCoinAddressWithdraw> pageInfo = withdrawService.getTransferList(uid, assetCode, pageNo,
				pageSize);

		PageModel<AddressDto> pageModel = new PageModel<>();
		pageModel.setPageSize(pageSize);
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(pageInfo.getPages());

		pageModel.setList(
				pageInfo.getList().stream().map(address -> new AddressDto(address)).collect(Collectors.toList()));
		return pageModel;
	}


	// 添加转出地址
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") ,
						 							 @Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})")})
	@RequestMapping(value = "/transfer-out-address-add", method = RequestMethod.POST)
	public void addCoinTransferOutAddress(@AuthForHeader AuthContext context,
			@RequestBody TransferOutDto transferInDto) {
		ConfigAsset configAsset = configAssetService.getAssetConfig(transferInDto.getAssetCode());
		if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN)) {
			throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
		}
		int uid = context.getLoginSession().getUserId();
		
		
		withdrawService.addWithdrawCoinAddress(transferInDto.getAddress(), transferInDto.getMemo(), uid,
				transferInDto.getAssetCode());
	}

	// 删除转出地址
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/transfer-out-address-delete", method = RequestMethod.GET)
	public void deleteCoinTransferOutAddress(@AuthForHeader AuthContext context, @RequestParam("id") Integer id) {
		int uid = context.getLoginSession().getUserId();

		withdrawService.deleteWithdrawCoinAddress(id, uid);
	}

	// 获取转入地址
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/transfer-in-address", method = RequestMethod.GET)
	public List<TransferInAddressDto> getCoinTransferInAddress(@AuthForHeader AuthContext context,
			@RequestParam("assetCode") String assetCode) {
		if (null != assetCode) {
			ConfigAsset configAsset = configAssetService.getAssetConfig(assetCode);
			if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN)) {
				throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
			}
		}
		int uid = context.getLoginSession().getUserId();
		AssetRequest request = new AssetRequest();
		request.setUid(new Long(uid));
		request.setAssetCode(assetCode);
		List<com.gop.api.cloud.response.TransferInAddressDto> newDepositeAddress = cloudApiService.transferInAddressQuery(request);
		log.info("转入地址获取结果：" + JSON.toJSONString(newDepositeAddress));
		List<TransferInAddressDto> addressDtoListForReturn = new LinkedList<>();
		newDepositeAddress.stream().forEach(addr -> {
			TransferInAddressDto addressDto = new TransferInAddressDto();
			BeanUtils.copyProperties(addr,  addressDto);
			addressDtoListForReturn.add(addressDto);
		});
		log.info("转入地址获取结果：" + JSON.toJSONString(addressDtoListForReturn));
		return addressDtoListForReturn;
	}

}
