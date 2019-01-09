package com.gop.lock.position.service.dto;


import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLockPositionBasicDto {

	/**
	 * 可锁仓数量
	 */
	private BigDecimal amount;

	/**
	 * 已锁仓数量
	 */
	private BigDecimal lockAmount;

	/**
	 * 上月奖励数量
	 */
	private BigDecimal lastRewardAmount;

	/**
	 * 总奖励数量
	 */
	private BigDecimal totalRewardAmount;
	private Integer uid;
	private String coinType;


	/**
	 * 锁仓开始时间
	 */
	private Date beginDate;

	/**
	 * 锁仓结束时间
	 */
	private Date endDate;
}


