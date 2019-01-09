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
import com.gop.domain.ChannelAlipayUserAccount;
import com.gop.domain.enums.DelFlag;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelAlipayUserAccountMapper;
import com.gop.mode.vo.PageModel;

import lombok.extern.slf4j.Slf4j;

@Service("userAlipayServiceImpl")
@Slf4j
public class ChannelUserAlipayServiceImpl implements ChannelUserAccountBaseService {

	@Autowired
	private ChannelAlipayUserAccountMapper channelAlipayUserAccountMapper;
	
	@Override
	public void addAccount(String channelAccountNo, String channelAccountName, Integer uid) {
		ChannelAlipayUserAccount alipayAccount = channelAlipayUserAccountMapper.getRecordByUidAndAlipayAccout(uid,
				channelAccountNo);
		if (alipayAccount == null) {
			alipayAccount = new ChannelAlipayUserAccount();
			alipayAccount.setAccountNo(channelAccountNo);
			alipayAccount.setAccountName(channelAccountName);
			alipayAccount.setCreateDate(new Date());
			alipayAccount.setDelFlag(DelFlag.FALSE);
			alipayAccount.setUid(uid);
			alipayAccount.setCreateDate(new Date());
			alipayAccount.setUpdateDate(new Date());
			channelAlipayUserAccountMapper.insert(alipayAccount);
		} else if (alipayAccount.getDelFlag() == DelFlag.TRUE) {
			alipayAccount.setDelFlag(DelFlag.FALSE);
			alipayAccount.setUpdateDate(new Date());
			channelAlipayUserAccountMapper.updateByPrimaryKeySelective(alipayAccount);
		} else {
			log.info("支付宝账号已存在，uid:{}, 账户:{}", uid, channelAccountNo);
			throw new AppException(AccountCodeConst.ALIPAY_EXIT);
		}
	}

	@Override
	public void deleteAccount(String channelAccountNo, Integer uid) {
		ChannelAlipayUserAccount alipayAccount = channelAlipayUserAccountMapper.getRecordByUidAndAlipayAccout(uid,
				channelAccountNo);
		if (alipayAccount == null) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "银行卡异常");
		}

		alipayAccount.setDelFlag(DelFlag.TRUE);
		alipayAccount.setUpdateDate(new Date());

		int flag = channelAlipayUserAccountMapper.updateByPrimaryKeySelective(alipayAccount);
		if (flag < 1) {
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "删除银行卡失败");
		}
	}

	@Override
	public PageModel<AcAccountDto> getList(Integer uid, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("update_date desc");
		PageInfo<ChannelAlipayUserAccount> lst = new PageInfo<>(channelAlipayUserAccountMapper.getAlipayAccoutList(uid, DelFlag.FALSE.toString()));
		
		PageModel<AcAccountDto> pageModel = new PageModel<>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(lst.getPages());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(lst.getTotal());
		pageModel.setList(lst.getList().stream().map(key -> new AcAccountDto(key)).collect(Collectors.toList()));
		
		return pageModel;
	}

}
