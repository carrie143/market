package com.gop.coin.transfer.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.gop.code.consts.AccountCodeConst;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.coin.transfer.dto.WithdrawAddressDto;
import com.gop.coin.transfer.service.WithdrawCoinAddressService;
import com.gop.domain.ChannelCoinAddressWithdraw;
import com.gop.domain.enums.DelFlag;
import com.gop.domain.enums.InnerAddressFlag;
import com.gop.domain.enums.WithdrawCoinAddressAuthStatus;
import com.gop.exception.AppException;
import com.gop.mapper.ChannelCoinAddressWithdrawMapper;
import com.gop.mapper.WithdrawAddressBrokerMapper;
import com.gop.mapper.dto.WithdrawAddress;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WithdrawCoinAddressServiceImpl implements WithdrawCoinAddressService {

	@Autowired
	private ChannelCoinAddressWithdrawMapper channelCoinAddressWithdrawMapper;

	@Autowired
	private WithdrawAddressBrokerMapper withdrawAddressBrokerMapper;
	private final int addressAmountLimit = 5;

	@Override
	public PageInfo<ChannelCoinAddressWithdraw> getTransferList(int uid, String assetCode, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("update_date desc");
		return new PageInfo<ChannelCoinAddressWithdraw>(channelCoinAddressWithdrawMapper.getAddress(uid, assetCode));
	}

	@Override
	@Transactional
	public void addWithdrawCoinAddress(String coinAddress, String memo, int uid, String assetCode) {
		List<ChannelCoinAddressWithdraw> channelCoinAddressWithdraws = channelCoinAddressWithdrawMapper.getAddress(uid, assetCode);
		if (channelCoinAddressWithdraws.size() >= addressAmountLimit){
			//添加地址不得超过五个
			throw new AppException(UserCodeConst.ADD_WITHDRAW_COIN_ADDRESS_LIMIT);
		}
		//校验标签是否重复
		if(channelCoinAddressWithdrawMapper.countMemoByUidAndAssetCodeAndStatus(memo, assetCode, uid) > 0) {
			throw new AppException(AccountCodeConst.COIN_ADDRESS_MEMO_EXIST);
		}
		ChannelCoinAddressWithdraw address = channelCoinAddressWithdrawMapper.selectByUidCoinAddress(uid, assetCode,
				coinAddress);
		if (null == address) {

			address = new ChannelCoinAddressWithdraw();
			address.setAssetCode(assetCode);
			address.setDelFlag(DelFlag.FALSE);
			address.setName(memo);
			address.setUid(uid);
			address.setCoinAddress(coinAddress);
			address.setAuthStatus(WithdrawCoinAddressAuthStatus.UNAUTH);
			address.setInnerAddress(InnerAddressFlag.NO);

			address.setCreateDate(new Date());
			address.setUpdateDate(new Date());

			if (channelCoinAddressWithdrawMapper.insert(address) < 0) {
				log.info("添加地址状态错误,地址id:{}", address.getId());
				throw new AppException(CommonCodeConst.SERVICE_ERROR, "地址状态错误");
			}
		} else if (address.getDelFlag().equals(DelFlag.TRUE)) {
			address.setDelFlag(DelFlag.FALSE);
			address.setUpdateDate(new Date());
			if (!Strings.isNullOrEmpty(memo)) {
				address.setName(memo);
			}
			channelCoinAddressWithdrawMapper.updateByPrimaryKeySelective(address);
		} else {
			log.info("添加地址状态错误,地址id:{}", address.getId());
			throw new AppException(AccountCodeConst.COIN_ADDRESS_EXIT, "", assetCode);
		}
	}

	@Override
	@Transactional
	public void deleteWithdrawCoinAddress(int addressId, int uid) {
		ChannelCoinAddressWithdraw address = channelCoinAddressWithdrawMapper.selectForUpdate(addressId);
		if (null == address) {
			log.info("添加地址状态错误,地址id:{}", addressId);
			new AppException(CommonCodeConst.SERVICE_ERROR, "地址状态错误");
		} else {
			if (uid != address.getUid()) {
				log.info("用户id不匹配,删除地址失败");
				throw new AppException(CommonCodeConst.SERVICE_ERROR, "删除地址失败");
			}
			address.setDelFlag(DelFlag.TRUE);
			address.setUpdateDate(new Date());
			channelCoinAddressWithdrawMapper.updateByPrimaryKeySelective(address);
		}

	}

	@Override
	public ChannelCoinAddressWithdraw getById(int id) {

		return channelCoinAddressWithdrawMapper.selectByPrimaryKey(id);
	}

	@Override
	public void addWithdrawAddressBroker(WithdrawAddressDto withdrawAddressDto) {
			withdrawAddressBrokerMapper.addWithdrawAddressBroker(withdrawAddressDto.getAssetCode(),withdrawAddressDto.getAddress(),Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
	}

	@Override
	public void updateWithdrawAddressBroker(WithdrawAddressDto withdrawAddressDto) {
		withdrawAddressBrokerMapper.updateWithdrawAddressBroker(withdrawAddressDto.getAssetCode(),withdrawAddressDto.getAddress(),Timestamp.valueOf(LocalDateTime.now()));
	}

	@Override
	public List<WithdrawAddress> getWithdrawAddressBroker(String assetCode) {
		return withdrawAddressBrokerMapper.selectWithdrawAddressBroker(assetCode);
	}

}
