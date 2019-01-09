package com.gop.coin.transfer.service.impl;

import com.github.pagehelper.PageInfo;
import com.gop.api.cloud.request.WithdrawQueryRequest;
import com.gop.api.cloud.response.WithdrawCoinDetailDto;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.domain.enums.WithdrawCoinOrderStatus;
import com.gop.util.DateUtils;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.code.consts.WithdrawalsCodeConst;
import com.gop.coin.transfer.service.ConfigAssetProfileService;
import com.gop.coin.transfer.service.WithdrawCoinService;
import com.gop.coin.transfer.service.CheckWithdrawCoinService;
import com.gop.domain.enums.AuthLevel;
import com.gop.domain.enums.ConfigAssetType;
import com.gop.exception.AppException;
import com.gop.util.BigDecimalUtils;

@Service
public class CheckWithdrawCoinServiceImpl implements CheckWithdrawCoinService {

  // -1或者null为不让转,0为无限转
  public static final BigDecimal WITHDRAW_LEVEL_STATE_VALUE = new BigDecimal("-1");

  @Autowired
  private ConfigAssetProfileService configAssetProfileService;

  @Autowired
  private WithdrawCoinService withdrawCoinService;

  @Autowired
  private CloudApiService cloudApiService;

  @Override
  public void checkWithdrawCoinOrder(Integer uid, AuthLevel userAuthLevel, String assetCode,
      BigDecimal amount) {

    BigDecimal minWithdrawAmount = getWithdrawAmount(assetCode, ConfigAssetType.WITHDRAWMIN);
    BigDecimal maxWithdrawAmount = getWithdrawAmount(assetCode, ConfigAssetType.WITHDRAWMAX);

    Calendar currentDayBegin = new GregorianCalendar();
    currentDayBegin.set(Calendar.HOUR_OF_DAY, 0);
    currentDayBegin.set(Calendar.MINUTE, 0);
    currentDayBegin.set(Calendar.SECOND, 0);

    WithdrawQueryRequest request = new WithdrawQueryRequest();
    request.setAssetCode(assetCode);
    request.setUid(uid.longValue());
    request.setStartDate(DateUtils.formatDate(currentDayBegin.getTime()));
    request.setEndDate(DateUtils.formatDate(new Date()));
    PageInfo<WithdrawCoinDetailDto> withdrawCoinDetailDtoPageInfo = cloudApiService
        .withdrawCoinDetail(request);
    List<WithdrawCoinDetailDto> withdrawDailyList = withdrawCoinDetailDtoPageInfo.getList();
    BigDecimal withdrawDailyArreadyUsed = BigDecimal.ZERO;
    for (WithdrawCoinDetailDto dto : withdrawDailyList) {
      if (WithdrawCoinOrderStatus.SUCCESS.name().equals(dto.getStatus())
          || WithdrawCoinOrderStatus.PROCESSING.name().equals(dto.getStatus())) {
        withdrawDailyArreadyUsed = withdrawDailyArreadyUsed.add(dto.getAmount());
      }
    }

    // 1.5.1版本
    // 查询用户等级(`user`表中的auth_level)
    // AuthLevel userAuthLevel = user.getAuthLevel();

    // 查询当日系统设定额度
    // 备注:-1或者null为不让转,0为无限转
    ConfigAssetType userWithdrawLevelType = switchAuthLevelToConfigAssetType(userAuthLevel);
    BigDecimal withdrawLevelDailyLimit = configAssetProfileService.getBigDecimalValue(assetCode,
        userWithdrawLevelType);
    if (null == withdrawLevelDailyLimit || WITHDRAW_LEVEL_STATE_VALUE
        .equals(withdrawLevelDailyLimit)) {
      throw new AppException(WithdrawalsCodeConst.LESS_WITHDRAWAL_LEVEL_ERROR,
          "转出币种数量超过用户对应实名认证等级限制");
    }
    // 如果系统设定单日额度大于0则进行限定判断(等于零或者没有则认为不做限定)
    // 等于零不做限制
    if (null != withdrawLevelDailyLimit && 0 < withdrawLevelDailyLimit.compareTo(BigDecimal.ZERO)) {
      // amount应小于当天最大限额的剩余额度(单日额度-成功/等待/进行的占额)
      if (BigDecimalUtils
          .isBigger(amount, withdrawLevelDailyLimit.subtract(withdrawDailyArreadyUsed))) {

        throw new AppException(WithdrawalsCodeConst.WITHDRAWAL_COIN_ADILY_MAX_OVER_ERROR,
            "转出值超过对应等级的每日限额",
            withdrawLevelDailyLimit.toPlainString());
      }
    }

    if (BigDecimalUtils.isLess(amount, minWithdrawAmount)) {

      throw new AppException(WithdrawalsCodeConst.LESS_MIN_WITHDRAWAL_COIN_AMOUNT, "转出值过小",
          minWithdrawAmount.toPlainString());
    }

    if (BigDecimalUtils.isBigger(amount, maxWithdrawAmount)) {

      throw new AppException(WithdrawalsCodeConst.SUPER_MAX_WITHDRAWAL_COIN_AMOUNT, "转出值过大",
          maxWithdrawAmount.toPlainString());
    }

  }

  @Override
  public BigDecimal getWithdrawAmount(String assetCode, ConfigAssetType type) {
    return configAssetProfileService.getBigDecimalValue(assetCode, type);
  }

  // 将用户的等级对应转换为每日转币限定等级
  public ConfigAssetType switchAuthLevelToConfigAssetType(AuthLevel userAuthLevel) {
    switch (userAuthLevel.toString()) {
      case "LEVEL0":
        return ConfigAssetType.WITHDRAWLEVEL_0;
      case "LEVEL1":
        return ConfigAssetType.WITHDRAWLEVEL_1;
      case "LEVEL2":
        return ConfigAssetType.WITHDRAWLEVEL_2;
      case "LEVEL3":
        return ConfigAssetType.WITHDRAWLEVEL_3;
      default:
        return null;
    }
  }
}
