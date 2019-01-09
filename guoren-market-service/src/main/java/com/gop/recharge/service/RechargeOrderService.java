package com.gop.recharge.service;

public interface RechargeOrderService {

    public void doCallback(int uscwalletUserId, String merId, long payMoney, long cardMoney, String orderId,
            int payResult, String privateField, String privateKey, String md5String);

}
