package com.gop.recharge.service.impl;

import java.math.BigDecimal;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.gop.asset.dto.AssetOperationDto;
import com.gop.domain.RechargeOrder;
import com.gop.financecheck.enums.AccountClass;
import com.gop.financecheck.enums.AccountSubject;
import com.gop.financecheck.enums.BusinessSubject;
import com.gop.mapper.RechargeOrderMapper;
import com.gop.recharge.service.RechargeOrderService;
import com.gop.util.MD5Util;
import com.gop.util.SequenceUtil;

@Service("RechargeOrderService")
@Slf4j
public class RechargeOrderServiceImpl implements RechargeOrderService {

    @Autowired
    private RechargeOrderMapper rechargeOrderMapper;

    @Override
    public void doCallback(int uscwalletUserId, String merId, long payMoney, long cardMoney, String orderId,
            int payResult, String privateField, String privateKey, String md5String) {

        boolean paySuccess = false;
        RechargeOrder rechargeOrder = this.rechargeOrderMapper.selectByPrimaryKey(Integer.valueOf(orderId));
        if (rechargeOrder == null || rechargeOrder.getStatus() != 2) {
            return;
        }
        String payDetails = "";
        String version = "3";

        String cardMoneyStr = "";
        if (cardMoney != 0) {
            cardMoneyStr = cardMoney + "";
        }
        log.info("加密前:{}", version + merId + (int) payMoney + cardMoneyStr+"" + orderId + payResult
            + privateField + payDetails + privateKey);
        
        String combineString="";
        if (cardMoney != 0) {
            combineString = version + "|" + merId + "|" + payMoney + "|" + cardMoney + "|" + orderId + "|" + payResult + "|" + privateField + "|" + payDetails + "|" + privateKey;
        } else {
            combineString = version + merId + payMoney + orderId + payResult + privateField + payDetails + privateKey;
        }
        String md5Str = MD5Util.genMD5Code(combineString);
        log.info("加密后md5:{}", md5Str);
        log.info("参数md5Str:{}", md5String);
        if (!StringUtils.isEmpty(md5Str) && md5String.equals(md5Str)) {
            if (payResult == 1) {
                paySuccess = true;
            }
        }
        if (!paySuccess) {
            rechargeOrder.setStatus(4);
            this.rechargeOrderMapper.updateByPrimaryKey(rechargeOrder);
            return;
        }
        rechargeOrder.setPayMoney(payMoney);
        rechargeOrder.setStatus(3);
        rechargeOrder.setAssetAmount(new BigDecimal(payMoney).multiply(new BigDecimal("0.975")).divide(new BigDecimal(669), 3, BigDecimal.ROUND_DOWN));

        String requestNo = SequenceUtil.getNextId();
        rechargeOrder.setRequestNo(requestNo);

        this.rechargeOrderMapper.updateByPrimaryKey(rechargeOrder);

        //开始为用户增加资产
        AssetOperationDto assetOperationDto = new AssetOperationDto();
        assetOperationDto.setUid(rechargeOrder.getUserId());
        assetOperationDto.setRequestNo(requestNo);
        assetOperationDto.setBusinessSubject(BusinessSubject.RECHARGE);
        assetOperationDto.setAssetCode("USC");
        assetOperationDto.setAmount(rechargeOrder.getAssetAmount());
        assetOperationDto.setLockAmount(BigDecimal.ZERO);
        assetOperationDto.setLoanAmount(BigDecimal.ZERO);
        assetOperationDto.setAccountClass(AccountClass.ASSET);
        assetOperationDto.setAccountSubject(AccountSubject.DEPOSIT_CARD);
        assetOperationDto.setIndex(0);

        String walletRequestNo = SequenceUtil.getNextId();
        //减去备用金账户资产
        AssetOperationDto walletAssetOperationDto = new AssetOperationDto();
        walletAssetOperationDto.setUid(uscwalletUserId);
        walletAssetOperationDto.setRequestNo(walletRequestNo);
        walletAssetOperationDto.setBusinessSubject(BusinessSubject.RECHARGE_DEDUCT);
        walletAssetOperationDto.setAssetCode("USC");
        walletAssetOperationDto.setAmount(rechargeOrder.getAssetAmount().negate());
        walletAssetOperationDto.setLockAmount(BigDecimal.ZERO);
        walletAssetOperationDto.setLoanAmount(BigDecimal.ZERO);
        walletAssetOperationDto.setAccountClass(AccountClass.ASSET);
        walletAssetOperationDto.setAccountSubject(AccountSubject.WITHDRAW_CARD);
        walletAssetOperationDto.setIndex(1);

        List<AssetOperationDto> assetOperationDtos = Lists.newArrayList(assetOperationDto);
        assetOperationDtos.add(walletAssetOperationDto);

    }

}
