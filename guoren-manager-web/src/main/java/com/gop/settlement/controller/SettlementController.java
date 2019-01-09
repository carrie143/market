package com.gop.settlement.controller;

import com.github.pagehelper.PageInfo;
import com.gop.api.cloud.enums.SettleType;
import com.gop.api.cloud.request.AssetRequest;
import com.gop.api.cloud.request.BrokerAssetAccountRequest;
import com.gop.api.cloud.request.BrokerAssetRequest;
import com.gop.api.cloud.request.BrokerWithdrawRequest;
import com.gop.api.cloud.request.SettleQueryRequest;
import com.gop.api.cloud.request.WithdrawQueryRequest;
import com.gop.api.cloud.request.WithdrawTotalAmountRequest;
import com.gop.api.cloud.response.AssetDto;
import com.gop.api.cloud.response.BrokerAssetDto;
import com.gop.api.cloud.response.BrokerConfigAssetDto;
import com.gop.api.cloud.response.SettleRecordDto;
import com.gop.api.cloud.response.WithdrawCoinDetailDto;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.UserAssetCodeConst;
import com.gop.code.consts.WithdrawalsCodeConst;
import com.gop.coin.transfer.dto.WithdrawAddressDto;
import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.coin.transfer.service.WithdrawCoinAddressService;
import com.gop.domain.ConfigAsset;
import com.gop.domain.enums.ConfigAssetType;
import com.gop.domain.enums.CurrencyType;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import com.gop.exception.AppException;
import com.gop.mapper.dto.WithdrawAddress;
import com.gop.mode.vo.PageModel;
import com.gop.settlement.controller.dto.BrokerWithdrawDto;
import com.gop.settlement.controller.dto.SettleInfoDto;
import com.gop.settlement.controller.dto.WithdrawAmountLimitDto;
import com.gop.util.IDGenerateUtil;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@RestController("managerSettlement")
@RequestMapping("/settle")
@Slf4j
// @Api("用户中心")
public class SettlementController {
    @Autowired
    private CloudApiService cloudApiService;
    @Autowired
    private ConfigAssetService configAssetService;
    @Autowired
    private ConfigAssetProfileService configAssetProfileService;
    @Autowired
    private WithdrawCoinAddressService withdrawCoinAddressService;
    // 查询提现限制
    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/withdraw-limit", method = RequestMethod.GET)
    public WithdrawAmountLimitDto withdrawAmountLimit(@AuthForHeader AuthContext context, @RequestParam(value = "assetCode") String assetCode) {
        BigDecimal cloudFee =BigDecimal.ZERO;
        BigDecimal withdrawMin =BigDecimal.ZERO;
        if (assetCode != null){
            ConfigAsset configAsset = configAssetService.getAssetConfig(assetCode);
            AssetRequest request = new AssetRequest();
            request.setAssetCode(assetCode);
            request.setUid(Long.valueOf("0"));
            List<BrokerConfigAssetDto> list = cloudApiService.getConfigAssetList(request);
            cloudFee = list.size()>0?list.stream().findAny().get().getBrokerWithdrawFee():BigDecimal.ZERO;
            withdrawMin = list.size()>0?list.stream().findAny().get().getMinWithdrawAmount():BigDecimal.ZERO;

            if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN) || list.size() == 0) {
                throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
            }
        }
        // 可提现数量
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setAssetCode(assetCode);
        assetRequest.setUid(Long.valueOf("0")); // 券商传0
        BigDecimal balance = cloudApiService.queryBalance(assetRequest);

//        String withdrawMin = configAssetProfileService.getStringValue(assetCode, ConfigAssetType.WITHDRAWMIN);

        List<WithdrawAddress> addresses =  withdrawCoinAddressService.getWithdrawAddressBroker(assetCode);
        WithdrawAmountLimitDto dto = new WithdrawAmountLimitDto();
        dto.setAddress(addresses.size()<=0?null:addresses.get(0).getAddress());
        dto.setAssetCode(assetCode);
        dto.setBalance(balance);
        dto.setCloudFee(cloudFee.toString());
        dto.setWithdrawMin(withdrawMin.toString());
        return dto;
    }
    // 查询提现地址
    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/withdraw-address", method = RequestMethod.GET)
    public List<WithdrawAddress> brokerWithdraw(@AuthForHeader AuthContext context, @RequestParam(value = "assetCode", required = false) String assetCode) {
        if (assetCode != null){
            ConfigAsset configAsset = configAssetService.getAssetConfig(assetCode);
            if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN)) {
                throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
            }
        }
        return withdrawCoinAddressService.getWithdrawAddressBroker(assetCode);
    }
    // 添加结算地址
    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/withdraw-address/add", method = RequestMethod.POST)
    public void addWithdrawAddress(@AuthForHeader AuthContext context, @RequestBody WithdrawAddressDto withdrawAddress) {
        ConfigAsset configAsset = configAssetService.getAssetConfig(withdrawAddress.getAssetCode());
        if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN)) {
            throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
        }
        withdrawCoinAddressService.addWithdrawAddressBroker(withdrawAddress);
    }
    // 修改结算地址
    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/withdraw-address/update", method = RequestMethod.POST)
    public void updateWithdrawAddress(@AuthForHeader AuthContext context, @RequestBody WithdrawAddressDto withdrawAddress) {
        ConfigAsset configAsset = configAssetService.getAssetConfig(withdrawAddress.getAssetCode());
        if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN)) {
            throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
        }
        withdrawCoinAddressService.updateWithdrawAddressBroker(withdrawAddress);
    }
    // 申请转出
    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/withdraw-broker", method = RequestMethod.POST)
    public void brokerWithdraw(@AuthForHeader AuthContext context, @RequestBody BrokerWithdrawDto brokerWithdrawDto) {
        BigDecimal cloudFee =BigDecimal.ZERO;
        BigDecimal withdrawMin =BigDecimal.ZERO;
        if (brokerWithdrawDto.getAssetCode() != null){
            ConfigAsset configAsset = configAssetService.getAssetConfig(brokerWithdrawDto.getAssetCode());
            AssetRequest request = new AssetRequest();
            request.setAssetCode(brokerWithdrawDto.getAssetCode());
            request.setUid(Long.valueOf("0"));
            List<BrokerConfigAssetDto> list = cloudApiService.getConfigAssetList(request);
            for (BrokerConfigAssetDto assetDto:list){
                cloudFee = assetDto.getAssetCode().equals(brokerWithdrawDto.getAssetCode())?assetDto.getBrokerWithdrawFee():BigDecimal.ZERO;
                withdrawMin = assetDto.getAssetCode().equals(brokerWithdrawDto.getAssetCode())?assetDto.getMinWithdrawAmount():BigDecimal.ZERO;
            }
            if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN) || list.size() == 0) {
                throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
            }
        }
        if (cloudFee.compareTo(brokerWithdrawDto.getAmount()) == 1){
            throw new AppException(WithdrawalsCodeConst.BROKER_LESS_CLOUD_MIN_FEE);
        }
        if (withdrawMin.compareTo(brokerWithdrawDto.getAmount()) == 1){
            throw new AppException(WithdrawalsCodeConst.BROKER_AMOUNT_NOT_RESONABLE,"转出金额过小",withdrawMin.toString());
        }
        BrokerWithdrawRequest request = new BrokerWithdrawRequest();
        request.setAssetCode(brokerWithdrawDto.getAssetCode());
        request.setAddress(brokerWithdrawDto.getAddress());
        request.setAmount(brokerWithdrawDto.getAmount());
        request.setClientOrderNo(IDGenerateUtil.generateClientOrderNo());
        cloudApiService.brokerWithdraw(request);
    }

    // 查询结算信息
    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/info-list", method = RequestMethod.GET)
    public List<SettleInfoDto> getSettleInfo(@AuthForHeader AuthContext context, @RequestParam(value = "assetCode", required = false) String assetCode) {
        if (assetCode !=null){
            ConfigAsset configAsset = configAssetService.getAssetConfig(assetCode);
            if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN)) {
                throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
            }
            return getSettleInfoByAssetCode(assetCode);
        }
        return getSettleInfoTotal();
    }

    private List<SettleInfoDto> getSettleInfoTotal() {
        List<ConfigAsset> configAssetList = configAssetService.getAllAssetCode();

        List<WithdrawAddress> withdrawAddressList = withdrawCoinAddressService.getWithdrawAddressBroker(null);

        WithdrawTotalAmountRequest request = new WithdrawTotalAmountRequest();
        request.setStatus(WithdrawCoinOrderStatus.SUCCESS);
        request.setUid(Long.valueOf("0")); // 券商传0
        Map<String,BigDecimal>  assetWithdrawn = cloudApiService.queryWithdrawTotal(request);

        BrokerAssetRequest brokerAssetRequest = new BrokerAssetRequest();
        brokerAssetRequest.setNanoTime(System.nanoTime());
        List<BrokerAssetDto> brokerAssetList = cloudApiService.queryBrokerFinance(brokerAssetRequest).getList();

        List<SettleInfoDto> list = new ArrayList<>();
        for (ConfigAsset configAsset:configAssetList){
            SettleInfoDto settleInfo = new SettleInfoDto();
            settleInfo.setBalance(BigDecimal.ZERO);

            // address
            withdrawAddressList.stream().filter(
                withdrawAddress -> configAsset.getAssetCode().equals(withdrawAddress.getAssetCode()))
                               .forEach(withdrawAddress -> {
                                   settleInfo.setAddress(withdrawAddress.getAddress());
                               });
            // balance
            brokerAssetList.stream().filter(brokerAsset ->configAsset.getAssetCode().equals(brokerAsset.getAssetCode())).forEach(brokerAsset -> {
                settleInfo.setBalance(brokerAsset.getAmountAvailable());
            });
            // withdrawTotal
            BigDecimal withdrawn = assetWithdrawn.get(configAsset.getAssetCode());
            settleInfo.setWithdrawTotal(settleInfo.getBalance().add(withdrawn==null?BigDecimal.ZERO:withdrawn));
            settleInfo.setAssetCode(configAsset.getAssetCode());
            list.add(settleInfo);
        }
        return list;
    }

    private List<SettleInfoDto> getSettleInfoByAssetCode(String assetCode) {
        List<SettleInfoDto> list = new ArrayList<>();
        String address;
        List<WithdrawAddress> withdrawAddressList = withdrawCoinAddressService.getWithdrawAddressBroker(assetCode);
        if (withdrawAddressList ==null || withdrawAddressList.size()<=0){
            address = StringUtils.EMPTY;
        }else {
            address = withdrawAddressList.stream().findAny().get().getAddress();
        }

        // 已提现总量
        WithdrawTotalAmountRequest request = new WithdrawTotalAmountRequest();
        request.setAssetCode(assetCode);
        request.setStatus(WithdrawCoinOrderStatus.SUCCESS);
        request.setUid(Long.valueOf("0"));
        Map<String,BigDecimal> map = cloudApiService.queryWithdrawTotal(request);
        BigDecimal  withdrawn = map.get(assetCode)==null?BigDecimal.ZERO:map.get(assetCode);

        // 可提现数量
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setAssetCode(assetCode);
        assetRequest.setUid(Long.valueOf("0")); // 券商传0
        BigDecimal tmp = cloudApiService.queryBalance(assetRequest);
        BigDecimal balance = tmp ==null?BigDecimal.ZERO:tmp;

        SettleInfoDto settleInfo = new SettleInfoDto();
        settleInfo.setAssetCode(assetCode);
        settleInfo.setAddress(address);
        settleInfo.setBalance(balance);
        settleInfo.setWithdrawTotal(withdrawn.add(balance));
        list.add(settleInfo);
        return list;
    }


    // 查询结算记录
    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/record-list", method = RequestMethod.GET)
    public PageModel<SettleRecordDto> getSettleRecord(@AuthForHeader AuthContext context, @RequestParam(value = "assetCode", required = false) String assetCode,@RequestParam("settleType")
        SettleType settleType,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        SettleQueryRequest request = new SettleQueryRequest();
        request.setAssetCode(assetCode);
        request.setType(settleType);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PageInfo<SettleRecordDto> pageInfo =  cloudApiService.getSettleRecord(request);

        PageModel<SettleRecordDto> pageModel = new PageModel<>();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setPageNum(pageInfo.getPages());
        pageModel.setTotal(pageInfo.getTotal());
        pageModel.setList(pageInfo.getList());
        return pageModel;
    }
    // 查询提现记录
    @Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
                             @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
    @RequestMapping(value = "/withdraw-record-list", method = RequestMethod.GET)
    public PageModel<WithdrawCoinDetailDto> getWithdrawRecord(@AuthForHeader AuthContext context, @RequestParam(value = "assetCode", required = false) String assetCode,@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
        WithdrawQueryRequest request = new WithdrawQueryRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setAssetCode(assetCode);
        request.setUid(Long.valueOf("0"));
        PageInfo<WithdrawCoinDetailDto> pageInfo = cloudApiService.withdrawCoinDetail(request);
        PageModel<WithdrawCoinDetailDto> pageModel = new PageModel<>();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setPageNum(pageInfo.getPages());
        pageModel.setTotal(pageInfo.getTotal());
        pageModel.setList(pageInfo.getList());
        return pageModel;
    }
}
