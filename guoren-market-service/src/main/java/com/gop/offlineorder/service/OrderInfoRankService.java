package com.gop.offlineorder.service;

import com.gop.mapper.dto.OrderRankOfCoinsNumDto;
import com.gop.mapper.dto.OrderRankOfSellDto;
import com.gop.mode.vo.PageModel;

public interface OrderInfoRankService {

	/**
	 * 创建或者更新卖出完成
	 * 
	 * @param uid
	 *            用户的id
	 * @return
	 */
	public int saveOrIncrOrderSellCompleted(int uid);

	/**
	 * 创建或者更新买入完成
	 * 
	 * @param uid
	 *            用户的id
	 * @return
	 */
	public int saveOrIncrOrderBuyCompleted(int uid);

	/**
	 * 保存或更新交易币
	 * 
	 * @param asset_name
	 * @param count
	 * @return
	 */
	public int saveOrIncrOrderCompletion(String assetName);

	/**
	 * #成交最多单的记录(uid,总交易数量) 从TokenOrderCompletionCount表获取信息
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageModel<OrderRankOfSellDto> getRankOfSellFromTableOfCompletionCount(Integer pageNo, Integer pageSize);

	/**
	 * #成交最多的币种(币种,成功单数) 从TokenOrderCompletionCoins表获取信息
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageModel<OrderRankOfCoinsNumDto> getRankOfCoinsNumFromTableOfCompletionCoins(Integer pageNo,
			Integer pageSize);

}
