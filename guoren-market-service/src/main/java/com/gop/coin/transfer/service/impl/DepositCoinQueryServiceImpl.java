package com.gop.coin.transfer.service.impl;

import com.gop.api.cloud.request.DepositQueryRequest;
import com.gop.api.cloud.response.DepositDetailDto;
import com.gop.api.cloud.service.CloudApiService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.gop.domain.StatisticeResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.coin.transfer.service.DepositCoinQueryService;
import com.gop.domain.DepositCoinOrderUser;
import com.gop.domain.enums.DepositCoinAssetStatus;
import com.gop.mapper.DepositCoinOrderUserMapper;

@Service("depositCoinService")
public class DepositCoinQueryServiceImpl implements DepositCoinQueryService {
	@Autowired
	private DepositCoinOrderUserMapper depositCoinOrderUserMapper;
	@Autowired
	private CloudApiService cloudApiService;

	@Override
	public PageInfo<DepositCoinOrderUser> queryOrder(Integer brokerId, Integer id, String account, Integer uId,
			String address, String txid, String assetCode,Date beginTime,Date endTime ,String email,DepositCoinAssetStatus status, Integer pageNo,
			Integer pageSize) {

		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("id desc");

		return new PageInfo<DepositCoinOrderUser>(depositCoinOrderUserMapper.selectDepositOrderList(brokerId, id, account, uId, address, txid,
				assetCode, beginTime,endTime,email,status));
	}

	@Override
	public List<DepositCoinOrderUser> queryOrder(Integer uId, Date fromDate, Date toDate) {
		return depositCoinOrderUserMapper.selectByUidAndDateScope(uId, fromDate, toDate);
	}

	@Override
	public PageInfo<DepositCoinOrderUser> getTransferList(int uid, String assetCode, Integer pageSize, Integer pageNo) {
//		PageHelper.startPage(pageNo, pageSize);
//		PageHelper.orderBy("update_date desc");
//		return new PageInfo<>(depositCoinOrderUserMapper.selectListByUidAndAssetCode(uid, assetCode));
		DepositQueryRequest request = new DepositQueryRequest();
		request.setUid(new Long(uid));
		request.setAssetCode(assetCode);
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		PageInfo<DepositDetailDto> depositDetailDtoPageInfo = cloudApiService
				.depositeCoinDetail(request);
		return buildDepositCoinOrderUserPageInfo(depositDetailDtoPageInfo);
	}

	private PageInfo<DepositCoinOrderUser> buildDepositCoinOrderUserPageInfo(
			PageInfo<DepositDetailDto> depositDetailDtoPageInfo) {
		PageInfo<DepositCoinOrderUser> pageInfo = new PageInfo<>();
		BeanUtils.copyProperties(depositDetailDtoPageInfo, pageInfo, "list");

		List<DepositCoinOrderUser> list = new LinkedList<>();
		depositDetailDtoPageInfo.getList().stream().forEach(dto -> {
			//todo 一些匹配不上的其他字段是否需要？
			DepositCoinOrderUser depositCoinOrderUser = new DepositCoinOrderUser();
			depositCoinOrderUser.setAssetCode(dto.getAssetCode());
//			depositCoinOrderUser.setFee();
			depositCoinOrderUser.setAccount(dto.getAccount());
//			depositCoinOrderUser.setAccountId();
			depositCoinOrderUser.setAssetStatus(dto.getStatus());
			depositCoinOrderUser.setBrokerId(Integer.parseInt(dto.getBrokerId()));
			depositCoinOrderUser.setCoinAddress(dto.getAddress());
			depositCoinOrderUser.setUid(dto.getUid().intValue());
			depositCoinOrderUser.setNumber(dto.getAmount());
			depositCoinOrderUser.setMsg(dto.getMsg());
			depositCoinOrderUser.setCreateDate(dto.getCreateDate());
			list.add(depositCoinOrderUser);
		});
		pageInfo.setList(list);
		return pageInfo;
	}

	@Override
	public BigDecimal getTotalDeposit(String assetcode, DepositCoinAssetStatus status, Date startDate, Date endDate) {
		return depositCoinOrderUserMapper.getTotalDeposit(assetcode,status,startDate,endDate);
	}
	@Override
	public List<StatisticeResult> getTotalDeposits(String assetcode, DepositCoinAssetStatus status, Date startDate, Date endDate) {
		return depositCoinOrderUserMapper.getTotalDeposits(assetcode,status,startDate,endDate);
	}
	@Override
	public List<StatisticeResult> depositStaitstic(){
		return depositCoinOrderUserMapper.depositStaitstic();
	}
}
