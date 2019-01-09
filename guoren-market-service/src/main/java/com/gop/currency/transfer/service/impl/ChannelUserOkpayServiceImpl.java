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
import com.gop.domain.ChannelOkpayUserAccount;
import com.gop.domain.enums.DelFlag;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelOkpayGlobalConfigMapper;
import com.gop.mapper.ChannelOkpayUserAccountMapper;
import com.gop.mode.vo.PageModel;

import lombok.extern.slf4j.Slf4j;

@Service("userOkpayServiceImpl")
@Slf4j
public class ChannelUserOkpayServiceImpl implements ChannelUserAccountBaseService {

	@Autowired
	private ChannelOkpayUserAccountMapper channelOkpayUserAccountMapper;
	
	@Autowired
	private ChannelOkpayGlobalConfigMapper okpayGlobalConfMapper;

	@Override
	public void addAccount(String channelAccountNo, String channelAccountName, Integer uid) {
		ChannelOkpayUserAccount okpayAccount = channelOkpayUserAccountMapper.getRecordByUidAndOkpayAccout(uid,
				channelAccountNo);
		if (okpayAccount == null) {
			okpayAccount = new ChannelOkpayUserAccount();
			okpayAccount.setAccountNo(channelAccountNo);
			okpayAccount.setAccountName(channelAccountName);
			okpayAccount.setCreateDate(new Date());
			okpayAccount.setDelFlag(DelFlag.FALSE);
			okpayAccount.setUid(uid);
			okpayAccount.setCreateDate(new Date());
			okpayAccount.setUpdateDate(new Date());
			channelOkpayUserAccountMapper.insertSelective(okpayAccount);
		} else if (okpayAccount.getDelFlag() == DelFlag.TRUE) {
			okpayAccount.setDelFlag(DelFlag.FALSE);
			okpayAccount.setUpdateDate(new Date());
			channelOkpayUserAccountMapper.updateByPrimaryKeySelective(okpayAccount);
		} else {
			log.info("okpay账号已存在");
			throw new AppException(AccountCodeConst.OKPAY_EXIT);
		}

	}

	@Override
	public void deleteAccount(String channelAccountNo, Integer uid) {
		ChannelOkpayUserAccount okpayAccount = channelOkpayUserAccountMapper.getRecordByUidAndOkpayAccout(uid,
				channelAccountNo);
		if (okpayAccount == null) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "银行卡异常");
		}

		okpayAccount.setDelFlag(DelFlag.TRUE);
		okpayAccount.setUpdateDate(new Date());

		int flag = channelOkpayUserAccountMapper.updateByPrimaryKeySelective(okpayAccount);
		if (flag < 1) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "删除银行卡失败");
		}
	}

	@Override
	public PageModel<AcAccountDto> getList(Integer uid, Integer pageNo, Integer pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("update_date desc");
		PageInfo<ChannelOkpayUserAccount> lst = new PageInfo<>(
				channelOkpayUserAccountMapper.getOkpayAccoutList(uid, DelFlag.FALSE.toString()));

		PageModel<AcAccountDto> pageModel = new PageModel<>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(lst.getPages());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(lst.getTotal());
		pageModel.setList(lst.getList().stream().map(key -> new AcAccountDto(key)).collect(Collectors.toList()));

		return pageModel;
	}

}
