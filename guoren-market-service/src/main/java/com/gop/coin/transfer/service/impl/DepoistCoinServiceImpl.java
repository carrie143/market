package com.gop.coin.transfer.service.impl;

import com.gop.api.cloud.request.DepositCallbackDto;
import com.gop.api.cloud.request.WithdrawCallbackDto;
import com.gop.code.consts.MessageConst;
import com.gop.coin.transfer.service.DepositCoinService;
import com.gop.common.Environment;
import com.gop.common.SmsMessageService;
import com.gop.user.dto.UserSimpleInfoDto;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserMessageService;
import com.gop.util.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by YAO on 2018/7/10.
 */
@Service("depoistCoinService")
@Slf4j
public class DepoistCoinServiceImpl implements DepositCoinService {
  @Autowired
  Environment environmentContxt;
  @Autowired
  private SmsMessageService smsMessageService;
  @Autowired
  private UserFacade userFacade;
  @Autowired
  private UserMessageService userMessageService;
  @Override
  public void depositConfirmCallback(DepositCallbackDto dto) {
    String transferMessage = generateTransferMessage(dto);
    userMessageService.insertMessage(dto.getUid().intValue(), transferMessage);
    UserSimpleInfoDto user = userFacade.getUserInfoByUid(dto.getUid().intValue());
    log.info("给用户{}发送邮件，信息:{}", user.getUserAccount(), transferMessage);
    smsMessageService.sendEmailMessage(user.getUserAccount(), transferMessage);
  }
  private String generateTransferMessage(DepositCallbackDto dto) {
    switch (dto.getStatus()) {
      case SUCCESS:
        return environmentContxt.getMsg(MessageConst.COIN_DEPOSIT_SUCCESS_MESSAGE,
                                        dto.getAmount().setScale(4, RoundingMode.FLOOR).toPlainString(), dto.getAssetCode(),
                                        DateUtils.formatDate(dto.getFinishDate()));
      default:
        return null;
    }
  }
}
