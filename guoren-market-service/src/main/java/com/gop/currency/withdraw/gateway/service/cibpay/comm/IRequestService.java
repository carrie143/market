/**
 * 通用请求器接口，如果需要使用自己的HTTP通讯库，请继承自该接口
 *
 * @author xiezz
 * @version 1.0.0
 */
package com.gop.currency.withdraw.gateway.service.cibpay.comm;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import com.gop.currency.withdraw.gateway.service.cibpay.config.CIBConfig;


public abstract class IRequestService {

    public abstract Object sendPost(String url, Map<String, String> param) throws IOException, KeyManagementException, NoSuchAlgorithmException;

    public static IRequestService getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        @SuppressWarnings("rawtypes")
        Class c = Class.forName(CIBConfig.httpsRequestClassName);
        return (IRequestService) c.newInstance();
    }
}
