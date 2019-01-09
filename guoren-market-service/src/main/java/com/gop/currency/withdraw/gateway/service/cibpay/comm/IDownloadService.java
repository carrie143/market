/**
 * POST发送参数的文件下载接口
 *
 * @author xiezz
 * @version 1.1.2
 */
package com.gop.currency.withdraw.gateway.service.cibpay.comm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.gop.currency.withdraw.gateway.service.cibpay.config.CIBConfig;
import com.gop.currency.withdraw.gateway.service.cibpay.util.Signature;


public abstract class IDownloadService {

    /**
     * 下载文件接口，直接返回文件二进制内容
     *
     * @param url    连接URL地址
     * @param params 参数项
     * @return 若下载成功，返回下载到的文件的字节数组(byte[]类型)；若下载失败，返回出错信息json字符串(String类型)
     */
    public static Object download(String url, Map<String, String> params) {

        Object ret = null;
        try {
            IRequestService requestor = IRequestService.getInstance();
            ret = requestor.sendPost(url, params);
            if ((ret instanceof String) && CIBConfig.NEED_CHECK_SIGN && !Signature.verifyMAC(Signature.jsonToMap((String) ret))) {
                return CIBConfig.SIGN_ERROR_RESULT;
            }
        } catch (IOException e) {
            ret = CIBConfig.TXN_ERROR_RESULT;
        } catch (Exception e) {
            ret = CIBConfig.SYS_ERROR_RESULT;
        }
        return ret;
    }

    /**
     * 下载文件接口，将下载的文件内容写入filename中
     *
     * @param url      连接URL地址
     * @param params   参数项
     * @param filename 待写入文件的文件名（可带路径）
     * @return 结果json字符串
     */
    public static String download(String url, Map<String, String> params, String filename) {

        Object ret = download(url, params);
        if (ret instanceof String) {
            if (CIBConfig.NEED_CHECK_SIGN && !Signature.verifyMAC(Signature.jsonToMap((String) ret))) {
                return CIBConfig.SIGN_ERROR_RESULT;
            } else {
                return (String) ret;
            }
        } else if (ret instanceof byte[]) {
            FileOutputStream fos = null;
            try {
                File file = new File(filename);
                fos = new FileOutputStream(file);
                fos.write((byte[]) ret);
            } catch (Exception e) {
                return CIBConfig.FILE_ERROR_RESULT;
            } finally {
                if (fos != null)
                    try {
                        fos.close();
                    } catch (Exception e) {
                    }
            }
            return CIBConfig.SUCCESS_RESULT;
        } else {
            return CIBConfig.SYS_ERROR_RESULT;
        }
    }

    public abstract Object download(Map<String, String> params);

    public abstract String downloadToFile(Map<String, String> params, String filename);
}
