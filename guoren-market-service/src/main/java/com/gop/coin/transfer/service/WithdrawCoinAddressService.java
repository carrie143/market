package com.gop.coin.transfer.service;

import com.github.pagehelper.PageInfo;
import com.gop.coin.transfer.dto.WithdrawAddressDto;
import com.gop.domain.ChannelCoinAddressWithdraw;
import com.gop.mapper.dto.WithdrawAddress;

import java.util.List;

public interface WithdrawCoinAddressService {

	public PageInfo<ChannelCoinAddressWithdraw> getTransferList(int uid, String assetCode, int pageNo, int pageSize);

	public void addWithdrawCoinAddress(String address, String memo, int uid, String assetCode);

	public void deleteWithdrawCoinAddress(int addressId, int uid);

	public ChannelCoinAddressWithdraw getById(int id);

	void addWithdrawAddressBroker(WithdrawAddressDto withdrawAddressDto);
	void updateWithdrawAddressBroker(WithdrawAddressDto withdrawAddressDto);
	List<WithdrawAddress> getWithdrawAddressBroker(String assetCode);
}
