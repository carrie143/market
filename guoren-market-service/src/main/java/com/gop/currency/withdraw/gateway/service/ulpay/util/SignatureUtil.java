//package com.gop.currency.withdraw.gateway.service.ulpay.util;
//
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//
//import com.gop.currency.withdraw.gateway.service.ulpay.cert.UlpayRaTools;
//
//public class SignatureUtil {
//	static Logger logger = LoggerFactory.getLogger(SignatureUtil.class);
//	public static boolean isSignature(String orgMsg) {
//		if (StringUtils.isEmpty(orgMsg)) {
//			return false;
//		}
//		StringBuffer orgBuf = new StringBuffer();
//		String startSign = "<SIGNED_MSG>";
//		String endSign = "</SIGNED_MSG>";
//		int startIndex = orgMsg.indexOf(startSign);
//		if(startIndex <=0 ){
//			return false;
//		}
//		int endIndex = orgMsg.indexOf(endSign);
//		if(endIndex <=0 ){
//			return false;
//		}
//		orgBuf.append(orgMsg.substring(0, startIndex)).append(startSign).append(endSign)
//				.append(orgMsg.substring(endIndex + endSign.length()));
//		String signStr = orgMsg.substring(startIndex + startSign.length(), endIndex);
//		Map<String, String> signRet = UlpayRaTools.getInstance().verify(signStr, orgBuf.toString());
//		if (!UlpayRaTools.SUCCESS.equals(signRet.get(UlpayRaTools.RET_CODE))) {
//			throw new IllegalArgumentException(signRet.get(UlpayRaTools.RET_MSG));
//		}
//		return true;
//	}
//
//	public static String addSignatrue(String orgMsg) {
//		String signLabel = "<SIGNED_MSG>signedMsg</SIGNED_MSG>";
//		String signLabelEmpty = "<SIGNED_MSG></SIGNED_MSG>";
//		orgMsg = orgMsg.replace(signLabel, signLabelEmpty);
//		UlpayRaTools tools = UlpayRaTools.getInstance();
//		logger.info("tools certPath : " + tools.certPath);
//		StringBuffer signBuffer = new StringBuffer("<SIGNED_MSG>").append(tools.sign(orgMsg))
//				.append("</SIGNED_MSG>");
//		return orgMsg.replace(signLabelEmpty, signBuffer.toString());
//	}
//
//}
