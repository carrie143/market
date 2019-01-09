/**
 * 服务签名算法
 *
 * @author xiezz
 * @version 1.1.2
 */
package com.gop.currency.withdraw.gateway.service.cibpay.config;


import java.util.HashMap;
import java.util.Map;

public class SignAlgorithm {

    private final static String DEFAULT_SIGN_TYPE = "SHA1";
    private final static Map<String, String> signAlg = new HashMap<String, String>() {{
        put("cib.epay.acquire.easypay.acctAuth", "SHA1");
        put("cib.epay.acquire.easypay.quickAuthSMS", "RSA");
        put("cib.epay.acquire.checkSms", "RSA");
        put("cib.epay.acquire.easypay.cancelAuth", "SHA1");
        put("cib.epay.acquire.easypay.acctAuth.query", "SHA1");
        put("cib.epay.acquire.easypay", "RSA");
        put("cib.epay.acquire.easypay.query", "SHA1");
        put("cib.epay.acquire.easypay.refund", "RSA");
        put("cib.epay.acquire.easypay.refund.query", "SHA1");
        put("cib.epay.acquire.authAndPay", "RSA");
        put("cib.epay.acquire.easypay.quickAuth", "RSA");

        put("cib.epay.acquire.cashier.netPay", "SHA1");
        put("cib.epay.acquire.cashier.query", "SHA1");
        put("cib.epay.acquire.cashier.refund", "RSA");
        put("cib.epay.acquire.cashier.refund.query", "SHA1");

        put("cib.epay.payment.getMrch", "RSA");
        put("cib.epay.payment.pay", "RSA");
        put("cib.epay.payment.get", "RSA");

        put("cib.epay.acquire.settleFile", "SHA1");
        put("cib.epay.payment.receiptFile", "SHA1");
    }};

    public static String get(String service) {
        if (signAlg.containsKey(service))
            return signAlg.get(service);
        else
            return DEFAULT_SIGN_TYPE;
    }
}
