package com.gop.common.impl;

import java.util.Iterator;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.gop.code.consts.IdentifyingCodeConst;
import com.gop.common.CheckCodeService;
import com.gop.common.IdentifyingCodeService;
import com.gop.exception.AppException;

@Service
public class CheckCodeServiceImpl implements CheckCodeService {
	@Autowired
	@Qualifier("IdentifyingCodeServiceImpl")
	IdentifyingCodeService identifyingCodeService;

	@Override
	public String SaveUserSendCode(String userName) {

		/*
		 * 发送间隔60s
		 */
		Boolean createCodeEnable = identifyingCodeService.checkSendCode(userName);
		if (createCodeEnable) {
			throw new AppException(IdentifyingCodeConst.IDENTIFYING_CODE_SENDED);
		}
		String codeAccount = identifyingCodeService.getCode(userName);
		String code = null;
		if (Strings.isNullOrEmpty(codeAccount)) {
			code = RandomStringUtils.randomNumeric(6);
		} else {
			code = Splitter.on(":").splitToList(codeAccount).get(0);
		}
		String value = Joiner.on(":").join(code, userName);
		identifyingCodeService.saveCode(value, userName, 900, 60);
		return code;
	}

	@Override
	public String SaveUserSendCode(int uid, String userAccount) {
		String key = Joiner.on(":").join("code", uid);

		/*
		 * 发送间隔60s
		 */
		Boolean createCodeEnable = identifyingCodeService.checkSendCode(key);

		if (createCodeEnable) {
			throw new AppException(IdentifyingCodeConst.IDENTIFYING_CODE_SENDED);
		}
		String codeAccount = identifyingCodeService.getCode(key);
		String code = null;
		if (Strings.isNullOrEmpty(codeAccount)) {
			code = RandomStringUtils.randomNumeric(6);
		} else {
			code = Splitter.on(":").splitToList(codeAccount).get(0);
		}
		String value = Joiner.on(":").join(code, userAccount);
		identifyingCodeService.saveCode(value, key, 900, 60);

		return code;

	}

	@Override
	public boolean checkUserCode(String userName, String inputCode) {
		if (Strings.isNullOrEmpty(inputCode)) {
			return false;
		}
		String codeValue = Optional.of(identifyingCodeService.getCode(userName)).or("");//从redis中获取serviceCode
		Iterator<String> ite = Splitter.on(":").split(codeValue).iterator();
		if (!ite.hasNext()) {
			throw new AppException(IdentifyingCodeConst.IDENTIFYING_CODE_HAS_INVALID);
		}
		String code = ite.next();
		if (!inputCode.equals(code)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkUserCode(int uid, String inputCode) {
		if (Strings.isNullOrEmpty(inputCode)) {
			return false;
		}
		String key = Joiner.on(":").join("code", uid);
		String codeValue = Optional.of(identifyingCodeService.getCode(key)).or("");
		Iterator<String> ite = Splitter.on(":").split(codeValue).iterator();
		if (!ite.hasNext()) {
			throw new AppException(IdentifyingCodeConst.IDENTIFYING_CODE_HAS_INVALID);
		}

		String code = ite.next();
		if (!inputCode.equals(code)) {
			return false;
		}

		return true;
	}

	@Override
	public void delete(Integer userId) {
		String key = Joiner.on(":").join("code", userId);
		identifyingCodeService.delCode(key);

	}

	@Override
	public void delete(String userAccount) {
		identifyingCodeService.delCode(userAccount);

	}

	@Override
	public String getUserAccount(int uid) {
		String key = Joiner.on(":").join("code", uid);
		String codeValue = Optional.of(identifyingCodeService.getCode(key)).or("");
		if (Strings.isNullOrEmpty(codeValue)) {
			throw new AppException(IdentifyingCodeConst.IDENTIFYING_CODE_HAS_INVALID);
		}
		String userAccount = null;
		userAccount = Splitter.on(":").splitToList(codeValue).get(1);
		return userAccount;

	}

}
