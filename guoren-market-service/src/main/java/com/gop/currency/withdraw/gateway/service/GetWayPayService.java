package com.gop.currency.withdraw.gateway.service;

import java.math.BigDecimal;
import java.util.List;

public interface GetWayPayService<T> {
	
	/**
	 * 
	 * @param innerOrder 订单号
	 * @param accountNo 账号
	 * @param accoutName 账号用户名
	 * @param amount 提现数量
	 * @param param 业务特有参数
	 * @return 返回支付订单号
	 */
	
	String takeOrder(String innerOrder, String accountNo, String accoutName, String currency, BigDecimal amount, String msg);
	
	
	/**
	 * 更新对应充值渠道记录
	 * @param order
	 * @return
	 */
	public boolean updateOrder(T order);
	
	/**
	 * 更新对应充值渠道记录
	 * @param order
	 * @return
	 */
	public boolean updateOrderByVersion(T order);

	
	/**
	 * 通过主键获取渠道记录
	 * @param id
	 * @return
	 */
	public T getOrderById(Integer id);

	
	/**
	 * 通过订单获取渠道记录
	 * @param transferStatus
	 * @param limit 获取记录数量
	 * @return
	 */
	public List<T> getOrdersByStatus(String transferStatus, int limit);

	/**
	 * 获取订单并加锁
	 * @param key
	 * @return
	 */
	public T getOrderForUpdate(int key);

	/**
	 * 获取同一订单号下的所有充值记录
	 * @param txid
	 * @return
	 */
	public List<T> getRecords(String txid);
	
	/**
	 * 获取同一订单号下的所有充值记录的最后一条记录的状态值
	 * @param txid
	 * @return
	 */
	public String getLastStatusByTxId(String txid);

	/**
	 * 根据内部订单号获取最新状态订单
	 * 
	 * @param txid
	 * @return
	 */
	public T getLastOrderByTxId(String txid);

}
