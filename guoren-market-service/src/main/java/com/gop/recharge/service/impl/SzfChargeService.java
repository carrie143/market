package com.gop.recharge.service.impl;

import java.net.URLEncoder;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.gop.currency.withdraw.gateway.service.ulpay.util.HttpUtils;
import com.gop.util.MD5Util;

@Component
@Slf4j
public class SzfChargeService {

    public String createOrder(int orderId, long payMoney, String cardInfo, int cardType, String merId,
            String privateKey, String desKey, String postUrl, String returnUrl) throws Exception {
        payMoney = 0;//全额扣除
        StringBuffer sBuffer = new StringBuffer();
        String version = "3";
        sBuffer.append("version=" + version + "&");
        sBuffer.append("merId=" + merId + "&");
        sBuffer.append("payMoney=" + payMoney).append("&");
        sBuffer.append("orderId=" + orderId).append("&");
        sBuffer.append("returnUrl=" + returnUrl).append("&");
        sBuffer.append("cardInfo=" + URLEncoder.encode(cardInfo)).append("&");
        String privateField = orderId + "";
        String verifyType = "1";
        String cardTypeCombine = cardType + "";
        sBuffer.append("privateField=" + privateField).append("&");
        sBuffer.append("verifyType=" + verifyType + "&");
        sBuffer.append("cardTypeCombine=" + cardTypeCombine).append("&");
        String md5String = MD5Util.genMD5Code(version + merId + payMoney + orderId + returnUrl + cardInfo
            + privateField + verifyType + privateKey);
        sBuffer.append("md5String=" + md5String);
        SzfChargeService.log.info("send pay request:{}", sBuffer.toString());
        String response = HttpUtils.sendGetMessage(postUrl + "?" + sBuffer.toString(), "UTF8");
        SzfChargeService.log.info("response:" + response);
        return response;

    }
}
