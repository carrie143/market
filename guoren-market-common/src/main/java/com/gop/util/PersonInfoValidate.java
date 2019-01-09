package com.gop.util;

import com.alibaba.fastjson.JSONObject;
import com.gop.config.ConfigurationInfo;
import com.gop.util.AsyncHttpUtil;

public class PersonInfoValidate {

	public static boolean validatePersonInfo(String username, String personid) {
		// 验证身份证
		JSONObject jsonsendcheckUserMessage = new JSONObject();
		jsonsendcheckUserMessage.put("userid", personid);
		jsonsendcheckUserMessage.put("username", username);
		String sign_jsonsendcheckUserMessage_str = RSAUtil.addSignRSA(jsonsendcheckUserMessage,
				ConfigurationInfo.PRIVATE_KEY);
		// 签名后注入签名数据
		jsonsendcheckUserMessage.put("sign", sign_jsonsendcheckUserMessage_str);

		// 发送POST请求

		String post_data_back_checkUserMessage;

		post_data_back_checkUserMessage = AsyncHttpUtil.syncPost(ConfigurationInfo.PERSON_VALIDATE_SERVER_URL,
				jsonsendcheckUserMessage.toJSONString());

		if (post_data_back_checkUserMessage != null) {
			System.err.println(post_data_back_checkUserMessage.trim());
			if ("sf00".equals(post_data_back_checkUserMessage.trim())
					|| "sf02".equals(post_data_back_checkUserMessage.trim())) {
				return true;
			}
		}
		return false;

	}

}
