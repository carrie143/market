//package com.gop.asset.api;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.gop.asset.dto.UserAssetQueryDto;
//import com.gop.asset.service.ConfigAssetService;
//import com.gop.domain.ConfigAsset;
//import com.gop.domain.Finance;
//import com.gop.notify.service.UserApiKeyService;
//import com.gop.util.ApiRequestTransaltor;
//import com.gop.util.dto.ApiRequestExtractDto;
//
//@RestController
//@RequestMapping("/api")
//public class AssetController {
//
////	@Autowired
////	private FinanceService financeService;
//
//	@Autowired
//	private ConfigAssetService configAssetService;
//
//	@Autowired
//	UserApiKeyService userApiKeyService;
//
//	@Autowired
//	private ApiRequestTransaltor apiRequestTransaltor;
//
//	@RequestMapping(value = "/asset", method = RequestMethod.POST)
//
//	public List<Finance> MatchApi(@RequestBody String jsonString) {
//
////		ApiRequestExtractDto<UserAssetQueryDto> userAssetQueryDto = apiRequestTransaltor.handlerMatchBack(jsonString,
////				UserAssetQueryDto.class);
////
//////		List<Finance> lst = financeService.queryAccounts(userAssetQueryDto.getUid());
////		if (lst == null || lst.size()==0) {
////			lst= new ArrayList<>();
////		}
////		String accountKind = "MASTER";
////		List<ConfigAsset> configAssets = configAssetService.getAvailableAssetCode();
////		if (lst.size() < configAssets.size()) {
////			Set<String> assetCodes = lst.stream().map(a -> a.getAssetCode()).collect(Collectors.toSet());
////			for (ConfigAsset configAsset : configAssets) {
////				String assetCode = configAsset.getAssetCode();
////
////				if (!assetCodes.contains(assetCode)) {
////					financeService.createFinanceAccount(userAssetQueryDto.getUid(), userAssetQueryDto.getBrokerId(),
////							assetCode, accountKind);
////
////				}
////			}
////			lst = financeService.queryAccounts(userAssetQueryDto.getUid());
////		}
////		return lst;
//		return null;
//	}
//
//}
