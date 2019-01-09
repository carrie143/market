/**
 * 文件下载接口
 *
 * @author xiezz
 * @version 1.1.2
 */
package com.gop.currency.withdraw.gateway.service.cibpay.service.impl;

import java.util.Map;

import com.gop.currency.withdraw.gateway.service.cibpay.comm.IDownloadService;
import com.gop.currency.withdraw.gateway.service.cibpay.config.CIBConfig;
import com.gop.currency.withdraw.gateway.service.cibpay.util.Signature;
import com.gop.util.DateTimeUtil;


public class DownloadServiceImpl extends IDownloadService {

    private static final String SERVICE_NAME = "cib.epay.acquire.download";
    private static final String SERVICE_VER = "01";

    @Override
    public Object download(Map<String, String> params) {

        params.put("appid", CIBConfig.APP_ID);
        params.put("service", SERVICE_NAME);
        params.put("ver", SERVICE_VER);
        params.put("timestamp", DateTimeUtil.getDateTime());
        params.put("mac", Signature.generateMAC(params));

        return download(CIBConfig.PY_API, params);
    }

    @Override
    public String downloadToFile(Map<String, String> params, String filename) {

        params.put("appid", CIBConfig.APP_ID);
        params.put("service", SERVICE_NAME);
        params.put("ver", SERVICE_VER);
        params.put("timestamp", DateTimeUtil.getDateTime());
        params.put("mac", Signature.generateMAC(params));

        return download(CIBConfig.GP_API, params, filename);
    }

}
