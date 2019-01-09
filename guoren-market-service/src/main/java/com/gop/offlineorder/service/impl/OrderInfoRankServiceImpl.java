package com.gop.offlineorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gop.mapper.TokenOrderCompletionCoinsMapper;
import com.gop.mapper.TokenOrderCompletionCountMapper;
import com.gop.mapper.dto.OrderRankOfCoinsNumDto;
import com.gop.mapper.dto.OrderRankOfSellDto;
import com.gop.mode.vo.PageModel;
import com.gop.offlineorder.service.OrderInfoRankService;

@Service("OrderInfoRankServiceImpl")
public class OrderInfoRankServiceImpl implements OrderInfoRankService {

	@Autowired
	private TokenOrderCompletionCoinsMapper coinMapper;

	@Autowired
	private TokenOrderCompletionCountMapper countMapper;

	@Override
	public PageModel<OrderRankOfSellDto> getRankOfSellFromTableOfCompletionCount(Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("total_count DESC");
		PageInfo<OrderRankOfSellDto> lst = new PageInfo<>(countMapper.getRankOfSell());

		PageModel<OrderRankOfSellDto> pageModel = new PageModel<>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(lst.getPages());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(lst.getTotal());
		pageModel.setList(lst.getList());
		// return countMapper.getRankOfSell();
		return pageModel;
	}

	@Override
	public PageModel<OrderRankOfCoinsNumDto> getRankOfCoinsNumFromTableOfCompletionCoins(Integer pageNo,
			Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		PageHelper.orderBy("completion_num DESC");
		PageInfo<OrderRankOfCoinsNumDto> lst = new PageInfo<>(coinMapper.getRankOfCoinsNum());

		PageModel<OrderRankOfCoinsNumDto> pageModel = new PageModel<>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(lst.getPages());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(lst.getTotal());
		pageModel.setList(lst.getList());
		// return coinMapper.getRankOfCoinsNum();
		return pageModel;
	}

	@Override
	public int saveOrIncrOrderSellCompleted(int uid) {

		return countMapper.insertOrIncrSellCompleted(uid);
	}

	@Override
	public int saveOrIncrOrderBuyCompleted(int uid) {
		// TODO Auto-generated method stub
		return countMapper.insertOrIncrBuyCompleted(uid);
	}

	@Override
	public int saveOrIncrOrderCompletion(String assetName) {
		//
		return coinMapper.saveOrIncrOrderCompleted(assetName);
	}

}
