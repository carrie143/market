package com.gop.util;

import com.alibaba.fastjson.JSONObject;

public class BankUtil {

	private static String postUrl = "https://endpoint.guorenbao.com/common/checkBankCard";

	/**
	 * 返回格式如下
	 * {"data":{"cardType":"CREDIT_CARD","bankName":"招商银行"},"msg":"success","status":"200"}
	 * 
	 * @param bankCard
	 * @return
	 */
	public static JSONObject getCardInfo(String bankCard) {
		JSONObject json = new JSONObject();
		json.put("bankCard", bankCard);
		String response = HttpsUtil.post(postUrl, json.toJSONString(), "UTF-8", 3000);
		JSONObject data = JSONObject.parseObject(response);

		String status = data.getString("status");
		if (!status.equals("200")) {
			return null;
		}
		return JSONObject.parseObject(data.getString("data"));
	}

	public static String getBankName(String bankCard) {
		JSONObject json = getCardInfo(bankCard);
		if (json == null) {
			return null;
		} else {
			return json.getString("bankName");
		}
	}

	public static boolean checkBankInfo(String bankNo, String bankName) {

		String bankNameRet = (String) getCardInfo(bankNo).get("bankName");
		if (bankName.trim().equals(bankNameRet.trim())) {
			return true;
		}
		;
		return false;
	}
}
