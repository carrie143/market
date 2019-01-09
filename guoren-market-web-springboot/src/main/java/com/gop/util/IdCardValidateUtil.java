package com.gop.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Splitter;
import com.gop.util.ipaynow.EncryDecryUtils;
import com.gop.util.ipaynow.FormDateReportConvertor;

import lombok.extern.slf4j.Slf4j;

/**
 * 现在支付实名认证
 */
@Slf4j
public class IdCardValidateUtil {
  private final static String appId = "1467620918085815";
  private final static String des3Key = "B3dN8k8tiSJSYPn2pYaFipWs";
  private final static String md5Key = "gPQ7KppodJM2e9ng8aPLXIhxIygkowcV";
  private final static String url = "https://s.ipaynow.cn/auth";

  public static boolean validatePersonInfo(String username, String personid) {
    try {
      // biz_content Json
      if (personid.length() < 18) {
        return false;
      }
      
      Map<String, String> requestMap = buildRequestParam(username, personid);
      String message = buildEncryMessage(requestMap);
      String postMsg = "funcode=ID01&message="+message;
      System.out.println(postMsg);
      String msg = AsyncHttpUtil.syncPost(url,postMsg);
      String[] resInfo = msg.split("\\|");//忽略下标0
      if (resInfo.length <= 2) {
        //错误原因
        log.info("validate fail: " + EncryDecryUtils.base64Decrypt(resInfo[1]));
        return false;
      }
      //解析第二部分，获取原始报文 先解密base再解密3des
     String originalMsg = EncryDecryUtils.decryptFromBase64DES(des3Key, resInfo[1]).trim();
      if (!validateSign(resInfo[2], originalMsg)) {
        return false;
      }
      //originalMsg通过报文
      //funcode=ID01&mhtOrderNo=1477033589621&nowpayTransId=1021030630l4hOVfmvqeIR95v1
      //&responseCode=0000&responseMsg=success&responseTime=20161021150630&status=00
      //originalMsg错误报文
      //funcode=ID01&mhtOrderNo=1476967973443&nowpayTransId=10200852541yqAwxqxje62VrT4
      //&responseCode=0000&responseMsg=服务结果:库中无此号，请到户籍所在地进行核实&responseTime=20161020205254&status=01
      Map<String, String> params = Splitter.on("&").withKeyValueSeparator("=").split(originalMsg);
      if (!"00".equals(params.get("status"))) {//00表示通过
        log.info("fail validate person username={}, id={}, msgMap={}", username, personid, params.toString());
        return false;
      }
      return true;
    } catch (Exception e) {
       log.error("validate personid error username={}, id={}, errorMsg={}", username, personid, e.getMessage());
       throw new IllegalStateException("验证出错");
    }
 
  }

  
  

  private static String buildEncryMessage(Map<String, String> requestMap) throws UnsupportedEncodingException {
    String toRSAStr = FormDateReportConvertor.postFormLinkReport(requestMap);
    //message=base64(appId=xxx)| base64(3DES(报文原文))|base64(MD5(报文原文+&+ md5Key))
    String message1 = "appId=" + appId + "";
    message1 = EncryDecryUtils.base64Encrypt(message1);//base64(appId=xxx)
    String message2 = toRSAStr;
    message2 = EncryDecryUtils.encryptFromDESBase64(des3Key, message2);// base64(3DES(报文原文)
    //base64(MD5(报文原文+&+ md5Key))
    String message3 = EncryDecryUtils.base64Encrypt(EncryDecryUtils.md5(toRSAStr.trim() + "&" + md5Key));
    String message = message1 + "|" + message2 + "|" + message3 + "";
    /**
     *发送的message  一定要做URLEncoder，否则会请求失败
     **/
    return URLEncoder.encode(message, "UTF-8");
  }
  

  /**
   * 验签
   *
   * @param sign        签名
   * @param originalMsg 原文
   */
  private static boolean validateSign(String sign, String originalMsg) {

    //解析第三部分，获取原始签名
    String originalSign = EncryDecryUtils.base64Decrypt(sign);
    //商户自己生成签名, 获取到原始报文以后建议做一次trim去掉后面的空格，否则会验签不通过
    String localSign = EncryDecryUtils.md5(originalMsg.trim() + "&" + md5Key);

    //对比签名是否验证通过
    if (!originalSign.equals(localSign)) {
      log.info("fail to validate sign local:{}, original:{}", localSign, originalSign);
      return false;
    }
    return true;
  }


  private static Map<String, String> buildRequestParam(String username, String personid) {
	    Map<String, String> requestMap = new HashMap<>();
	    requestMap.put("cardName", username);
	    requestMap.put("idcard", personid);
	    requestMap.put("mhtOrderNo", String.valueOf(System.currentTimeMillis()));
	    return requestMap;
	  }

}
