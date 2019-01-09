package com.gop.currency.transfer.service.impl;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.code.consts.AccountCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.currency.transfer.dto.AcAccountDto;
import com.gop.currency.transfer.service.ChannelUserAccountBaseService;
import com.gop.domain.ChannelBankUserAccount;
import com.gop.domain.enums.DelFlag;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelBankUserAccountMapper;
import com.gop.mode.vo.PageModel;
import com.gop.util.BankUtil;

import lombok.extern.slf4j.Slf4j;

@Service("userBankServiceImpl")
@Slf4j
public class ChannelUserBankServiceImpl implements ChannelUserAccountBaseService {

	@Autowired
	private ChannelBankUserAccountMapper channelBankUserAccountMapper;

	@Override
	public void addAccount(String channelAccountNo, String channelAccountName, Integer uid) {
		ChannelBankUserAccount bankAccount = channelBankUserAccountMapper.selectUserAcBankByUserAcNumber(uid,
				channelAccountNo);
		if (null == bankAccount) {
			bankAccount = new ChannelBankUserAccount();
			bankAccount.setAcnumber(channelAccountNo);
			bankAccount.setName(channelAccountName);
			bankAccount.setCreateDate(new Date());
			bankAccount.setDelFlag(DelFlag.FALSE);
			bankAccount.setName(channelAccountName);
			bankAccount.setAssetCode("CNY");
			bankAccount.setUid(uid);
			bankAccount.setUpdateDate(new Date());
			bankAccount.setCreateDate(new Date());

			String channelName = BankUtil.getBankName(channelAccountNo);
			if (null == channelName) {
				throw new AppException(CommonCodeConst.SERVICE_ERROR, "无效银行卡号");
			}
			bankAccount.setBank(channelName);
			channelBankUserAccountMapper.insert(bankAccount);
		} else if (bankAccount.getDelFlag() == DelFlag.TRUE) {
			bankAccount.setDelFlag(DelFlag.FALSE);
			bankAccount.setUpdateDate(new Date());
			channelBankUserAccountMapper.updateByPrimaryKeySelective(bankAccount);
		} else {
			log.info("银行卡已存在，uid:{}, 卡号:{}", uid, channelAccountNo);
			throw new AppException(AccountCodeConst.BANK_CARD_EXIT);
		}
		
	}

	@Override
	public void deleteAccount(String channelAccountNo, Integer uid) {
		ChannelBankUserAccount bankAccount = channelBankUserAccountMapper.selectUserAcBankByUserAcNumber(uid, channelAccountNo);
		if (bankAccount == null) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "银行卡异常");
		}

		bankAccount.setDelFlag(DelFlag.TRUE);
		bankAccount.setUpdateDate(new Date());

		int flag = channelBankUserAccountMapper.updateByPrimaryKeySelective(bankAccount);
		if (flag < 1) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "删除银行卡失败");
		}
		
	}

	@Override
	public PageModel<AcAccountDto> getList(Integer uid, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("update_date desc");
		PageInfo<ChannelBankUserAccount> lst = new PageInfo<>(channelBankUserAccountMapper.bankList(uid));
		
		PageModel<AcAccountDto> pageModel = new PageModel<>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(lst.getPages());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(lst.getTotal());
		pageModel.setList(lst.getList().stream().map(key -> new AcAccountDto(key)).collect(Collectors.toList()));
		
		return pageModel;
		
	}


}
