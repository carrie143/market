package com.gop.currency.withdraw.gateway.service.cibpay.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.currency.withdraw.gateway.service.cibpay.service.CibBankService;
import com.gop.domain.ChannelCibBank;
import com.gop.mapper.ChannelCibBankMapper;

import lombok.extern.slf4j.Slf4j;

@Service("cibBankService")
@Slf4j
public class CibBankServiceImpl implements CibBankService {
	@Autowired
	private ChannelCibBankMapper cibBankMapper;

	@Override
	public ChannelCibBank getCibBankByName(String bankName) {
		// TODO Auto-generated method stub
		return cibBankMapper.getCibBankByName(bankName);
	}

	@Override
	public void savaCibBankByString(String str) {
		try {
			String cleanString = StringUtils.trimToEmpty(str);
			String[] strs = StringUtils.split(cleanString, "\r\n");
			for (int i = 0; i < strs.length; i++) {
				String CibBankObjectStr = strs[i];
				ChannelCibBank cib = new ChannelCibBank();
				String[] CiBBankFieldStrs = StringUtils.split(CibBankObjectStr, "|");
				cib.setBankNo(StringUtils.trimToEmpty(CiBBankFieldStrs[0]));
				cib.setBankType(StringUtils.trimToEmpty(CiBBankFieldStrs[1]));
				cib.setBankName(StringUtils.trimToEmpty(CiBBankFieldStrs[2]));
				cib.setBankShortName(StringUtils.trimToEmpty(CiBBankFieldStrs[3]));
				ChannelCibBank cibBankTemp=cibBankMapper.getCibBankByName(cib.getBankName());
				if(null ==cibBankTemp)
				{
					cibBankMapper.insertSelective(cib);
				}
			}
		} catch (Exception e) {
			log.error("insert cibBank exception:{}", e);
		}

	}

	@Override
	public void clearCibBank() {
		cibBankMapper.clearCibBank();
	}

	@Override
	public ChannelCibBank getCibBankByShortName(String shortName) {
		return cibBankMapper.getCibBankByShortName(shortName);
	}

	@Override
	public String getBankNoByName(String bankName) {
		return cibBankMapper.getBankNoByName(bankName);
	}

	@Override
	public String getBankNoByShortName(String shortName) {
		return cibBankMapper.getBankNoByShortName(shortName);
	}

}
