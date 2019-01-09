package com.gop.coin.transfer.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.gop.api.cloud.request.AssetRequest;
import com.gop.api.cloud.request.DepositQueryRequest;
import com.gop.api.cloud.request.WithdrawQueryRequest;
import com.gop.api.cloud.response.BrokerConfigAssetDto;
import com.gop.api.cloud.response.DepositDetailDto;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.config.CommonConstants;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gop.asset.service.ConfigAssetService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.UserAssetCodeConst;
import com.gop.code.consts.WithdrawalsCodeConst;
import com.gop.coin.transfer.dto.DepositCoinDto;
import com.gop.coin.transfer.dto.WithDrawCoinDto;
import com.gop.coin.transfer.dto.WithdrawCoinDetailDto;
import com.gop.coin.transfer.dto.WithdrawCoinFeeQueryDto;
import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.coin.transfer.service.DepositCoinQueryService;
import com.gop.coin.transfer.service.WithdrawCoinAddressService;
import com.gop.coin.transfer.service.WithdrawCoinQueryService;
import com.gop.coin.transfer.service.WithdrawCoinService;
import com.gop.config.CommonConfig;
import com.gop.domain.ChannelCoinAddressWithdraw;
import com.gop.domain.ConfigAsset;
import com.gop.domain.ConfigAssetProfile;
import com.gop.domain.DepositCoinOrderUser;
import com.gop.domain.User;
import com.gop.domain.UserGoogleCodeConfig;
import com.gop.domain.WithdrawCoinOrderUser;
import com.gop.domain.enums.AssetStatus;
import com.gop.domain.enums.AuthLevel;
import com.gop.domain.enums.ConfigAssetType;
import com.gop.domain.enums.CurrencyType;
import com.gop.domain.enums.DelFlag;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserGoogleCodeConfigService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/coin")
@Slf4j
public class AssetManagerController {

  private static final int ADDRESS_LENGTH_LIMIT = 70;
  @Autowired
  private ConfigAssetService configAssetService;

  @Autowired
  private WithdrawCoinQueryService coinWithdrawService;

  @Autowired
  private WithdrawCoinService withdrawCoinService;

  @Autowired
  private WithdrawCoinAddressService withdrawCoinAddressService;

  @Autowired
  private ConfigAssetProfileService configAssetProfileService;

  @Autowired
  private UserGoogleCodeConfigService userGoogleCodeConfigService;

  @Autowired
  private UserFacade userFacade;

  @Autowired
  private DepositCoinQueryService depositCoinQueryService;

  @Autowired
  private CloudApiService cloudApiService;

  @Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
  @RequestMapping(value = "/withdraw-coin-details", method = RequestMethod.GET)
  public PageModel<WithdrawCoinDetailDto> getWithdrawCoin(@AuthForHeader AuthContext context,
      @RequestParam("assetCode") String assetCode,
      @RequestParam(value = "pageNo", required = false) Integer pageNo,
      @RequestParam(value = "pageSize", required = false) Integer pageSize) {

    int uid = context.getLoginSession().getUserId();
    WithdrawQueryRequest request = new WithdrawQueryRequest();
    request.setUid(new Long(uid));
    request.setAssetCode(assetCode);
    request.setPageNo(pageNo);
    request.setPageSize(pageSize);
    PageInfo<com.gop.api.cloud.response.WithdrawCoinDetailDto> withdrawCoinDetailDtoPageInfo = cloudApiService
        .withdrawCoinDetail(request);
    PageModel<WithdrawCoinDetailDto> pageModelResult = new PageModel<>();
    pageModelResult = buildPageForWithdrawDetails(pageModelResult, withdrawCoinDetailDtoPageInfo);
    return pageModelResult;

  }

  private PageModel<WithdrawCoinDetailDto> buildPageForWithdrawDetails(
      PageModel<WithdrawCoinDetailDto> pageModelResult,
      PageInfo<com.gop.api.cloud.response.WithdrawCoinDetailDto> withdrawCoinDetailDtoPageInfo) {

    pageModelResult.setPageNo(withdrawCoinDetailDtoPageInfo.getPageNum());
    pageModelResult.setPageSize(withdrawCoinDetailDtoPageInfo.getPageSize());
    pageModelResult.setTotal(withdrawCoinDetailDtoPageInfo.getTotal());
    pageModelResult.setPageNum(withdrawCoinDetailDtoPageInfo.getPageNum());

    List<WithdrawCoinDetailDto> list = Lists.newLinkedList();
    withdrawCoinDetailDtoPageInfo.getList().forEach(dto -> {
      WithdrawCoinDetailDto resultDto = new WithdrawCoinDetailDto();
      BeanUtils.copyProperties(dto, resultDto, "status", "address", "platFee", "brokerFee");
      resultDto.setTransferOutAddress(dto.getAddress());
      resultDto.setFee(dto.getPlatFee().add(dto.getBrokerFee()));
      resultDto.setStatus(WithdrawCoinOrderStatus.valueOf(dto.getStatus()));
      list.add(resultDto);
    });
    pageModelResult.setList(list);
    return pageModelResult;
  }

  @Strategys(strategys = {@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
      @Strategy(authStrategy = "exe({{'checkPayPasswordStregy'}})"),
      @Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})"),
      @Strategy(authStrategy = "exe({{'checkGoogleCodeStrategy'}})")})
  @RequestMapping(value = "/withdraw-coin", method = RequestMethod.POST)
  public void WithdrawCoin(@AuthForHeader AuthContext context,
      @Valid @RequestBody WithDrawCoinDto dto) throws Exception {

    int uid = context.getLoginSession().getUserId();
    // 重置谷歌验证码后24小时内不允许提币
    UserGoogleCodeConfig userGoogleCodeConfig = userGoogleCodeConfigService
        .selectUserGoogleCodeConfigByUid(uid);
    if (null != userGoogleCodeConfig && null != userGoogleCodeConfig.getResetDate()) {
      Calendar calendar = new GregorianCalendar();
      calendar.setTime(userGoogleCodeConfig.getResetDate());
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      long resetTime = calendar.getTime().getTime();
      if (resetTime > new Date().getTime()) {
        throw new AppException(WithdrawalsCodeConst.RESET_GOOGLE_CODE_WITHDRAW_LIMIT);
      }
    }

    //校验提笔币种是否支持
    ConfigAsset configAsset = configAssetService.getAssetConfig(dto.getAssetCode());
    if (configAsset == null || !configAsset.getCurrencyType().equals(CurrencyType.COIN)) {
      throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
    }

    /**提现金额是否小于手续费*/
    if (dto.getAmount().compareTo(dto.getFee()) <= 0){
      throw new AppException(WithdrawalsCodeConst.USER_LESS_MIN_FEE);
    }

    /**提币地址，为空，则通过id去数据库里获取*/
    if (Strings.isEmpty(dto.getAddress())) {
      if (null == dto.getAddressId()) {
        throw new AppException(CommonCodeConst.FIELD_ERROR);
      }
      ChannelCoinAddressWithdraw address = withdrawCoinAddressService.getById(dto.getAddressId());
      if (null == address || address.getDelFlag().equals(DelFlag.TRUE) || uid != address.getUid()
          .intValue()) {
        log.info("获取货币转出地址失败,assetCode:{},addressId:{}", dto.getAssetCode(), dto.getAddressId());
        throw new AppException(CommonCodeConst.FIELD_ERROR, "地址状态异常");
      }
      dto.setAddress(address.getCoinAddress());
    }

    /**地址校验*/
    if (dto.getAddress().length() > ADDRESS_LENGTH_LIMIT) {
      throw new AppException(CommonCodeConst.FIELD_ERROR, "地址状态异常");
    }

    log.info("开始提现，提现数据：16415{},", JSON.toJSONString(dto));
    withdrawCoinService
        .withdrawCoinOrder(uid, dto.getOutOrder(), dto.getAssetCode(), dto.getAmount(),
            dto.getFee(),
            dto.getAddress(), dto.getMessage());

    log.info("转出成功");
  }

  // 用户提币时,对应币种手续费查询
  // 可以非登录用户查询,供展示用
  // @Strategys(strategys = @Strategy(authStrategy =
  // "exe({{'checkLoginStrategy'}})"))
  @RequestMapping(value = "/withdraw-coinfee-query", method = RequestMethod.GET)
  public WithdrawCoinFeeQueryDto withdrawCoinfeeQuery(@AuthForHeader AuthContext context,
      @RequestParam("key") ConfigAssetType key,
      @RequestParam(value = "assetCode") String assetCode) {
    // 校验币种是否有效
    boolean validatedAsset = configAssetService.validateAssetConfig(assetCode, AssetStatus.LISTED);
    if (!validatedAsset) {
      throw new AppException(UserAssetCodeConst.INVALID_ASSET_CODE);
    }
    String value = configAssetProfileService.getStringValue(assetCode, key);

    if (ConfigAssetType.WITHDRAWMINFEE.equals(key)) {
      AssetRequest request = new AssetRequest();
      request.setAssetCode(assetCode);
      List<BrokerConfigAssetDto> configAssetList = cloudApiService.getConfigAssetList(request);
      if (configAssetList.isEmpty()) {
        throw new AppException("云端不支持的币种：" + assetCode);
      }
      value = configAssetList.get(0).getWithdrawFee().add(new BigDecimal(value))
          .stripTrailingZeros().toString();
    }
    WithdrawCoinFeeQueryDto dto = new WithdrawCoinFeeQueryDto();
    dto.setAssetCode(assetCode);
    dto.setProfileKey(key);
    dto.setProfileValue(value);
    return dto;
  }

  //查询用户对应的可提取币种
  @Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
  @RequestMapping(value = "/withdraw-coin-asset-list", method = RequestMethod.GET)
  public List<String> getWithdrawCoinAssetList(@AuthForHeader AuthContext context) {
    Integer uid = context.getLoginSession().getUserId();
    User user = userFacade.getUser(uid);
    ConfigAssetType key = getConfigAssetType(user.getAuthLevel());
    List<ConfigAssetProfile> list = configAssetProfileService.getConfigAssetProfileList(key);
    List<String> availableList = list.stream()
        .filter(profile -> Double.valueOf(profile.getProfileValue()).compareTo(0.0) >= 0)
        .map(profile -> profile.getAssetCode()).collect(Collectors.toList());
    return availableList;
  }

  public ConfigAssetType getConfigAssetType(AuthLevel value) {
    switch (value) {
      case LEVEL0:
        return ConfigAssetType.WITHDRAWLEVEL_0;
      case LEVEL1:
        return ConfigAssetType.WITHDRAWLEVEL_1;
      case LEVEL2:
        return ConfigAssetType.WITHDRAWLEVEL_2;
      default:
        return null;
    }
  }

  //所有币种的配置列表
  @Strategys(strategys = {@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
  @RequestMapping(value = "/assetprofile-list", method = RequestMethod.GET)
  public List<ConfigAssetProfile> getConfigAssetProfileList(@AuthForHeader AuthContext context,
      @RequestParam(value = "assetCode", required = false) String assetCode,
      @RequestParam(value = "key", required = false) ConfigAssetType key) {
    List<ConfigAssetProfile> list = configAssetProfileService
        .selectByAssetCodeOrProfileKey(assetCode, key);
    return list;
  }


  @Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
  @RequestMapping(value = "/deposit-coin-details", method = RequestMethod.GET)
  public PageModel<DepositCoinDto> getDepositCoin(@AuthForHeader AuthContext context,
      @RequestParam("assetCode") String assetCode,
      @RequestParam(value = "pageNo", required = false) Integer pageNo,
      @RequestParam(value = "pageSize", required = false) Integer pageSize) {

    int uid = context.getLoginSession().getUserId();
    DepositQueryRequest request = new DepositQueryRequest();
    request.setUid(new Long(uid));
    request.setAssetCode(assetCode);
    request.setPageNo(pageNo);
    request.setPageSize(pageSize);
    PageInfo<DepositDetailDto> depositDetailDtoPageInfo = cloudApiService
        .depositeCoinDetail(request);

    PageModel<DepositCoinDto> pageResult = new PageModel<>();
    pageResult = buildPageModelForDepositDetails(pageResult, depositDetailDtoPageInfo);
    return pageResult;
  }

  private PageModel<DepositCoinDto> buildPageModelForDepositDetails(
      PageModel<DepositCoinDto> pageResult,
      PageInfo<DepositDetailDto> depositDetailDtoPageInfo) {

    pageResult.setPageNo(depositDetailDtoPageInfo.getPageNum());
    pageResult.setPageSize(depositDetailDtoPageInfo.getPageSize());
    pageResult.setTotal(depositDetailDtoPageInfo.getTotal());
    pageResult.setPageNum(depositDetailDtoPageInfo.getPageNum());

    List<DepositCoinDto> list = Lists.newLinkedList();
    depositDetailDtoPageInfo.getList().forEach(dto -> {
      DepositCoinDto resultDto = new DepositCoinDto();
      BeanUtils.copyProperties(dto, resultDto);
      resultDto.setCoindAddress(dto.getAddress());
      resultDto.setMessage(dto.getMsg());
      list.add(resultDto);
    });
    pageResult.setList(list);
    return pageResult;
  }

  // 所有币种列表
  @Strategys(strategys = {@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
  @RequestMapping(value = "/configasset-list", method = RequestMethod.GET)
  public List<ConfigAsset> getConfigAssetList(@AuthForHeader AuthContext context,
      @RequestParam(value = "status", required = false) AssetStatus status) {

    List<ConfigAsset> list = configAssetService.getAllAssetCode();
    return list;
  }

  //
  @Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
  @RequestMapping(value = "/deposit-coin-asset-list", method = RequestMethod.GET)
  public List<String> getDepositCoinAssetList(@AuthForHeader AuthContext context) {
    Integer uid = context.getLoginSession().getUserId();
    User user = userFacade.getUser(uid);
    ConfigAssetType key = getDepositTpye(user.getAuthLevel());
    List<ConfigAssetProfile> list = configAssetProfileService.getConfigAssetProfileList(key);
    List<String> availableList = list.stream()
        .filter(profile -> Double.valueOf(profile.getProfileValue()).compareTo(0.0) >= 0)
        .map(profile -> profile.getAssetCode()).collect(Collectors.toList());
    return availableList;
  }


  public ConfigAssetType getDepositTpye(AuthLevel value) {
    switch (value) {
      case LEVEL0:
        return ConfigAssetType.DEPOSITLEVEL_DEFAULT;
      case LEVEL1:
        return ConfigAssetType.DEPOSITLEVEL_DEFAULT;
      case LEVEL2:
        return ConfigAssetType.DEPOSITLEVEL_DEFAULT;
      default:
        return null;
    }
  }
}
