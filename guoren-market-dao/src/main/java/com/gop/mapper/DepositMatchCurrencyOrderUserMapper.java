package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.DepositMatchCurrencyOrderUser;
import com.gop.domain.enums.MatchState;

public interface DepositMatchCurrencyOrderUserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(DepositMatchCurrencyOrderUser record);

	int insertSelective(DepositMatchCurrencyOrderUser record);

	DepositMatchCurrencyOrderUser selectByPrimaryKey(Integer id);
	


	int updateByPrimaryKeySelective(DepositMatchCurrencyOrderUser record);

	int updateByPrimaryKey(DepositMatchCurrencyOrderUser record);

	List<DepositMatchCurrencyOrderUser> getByStatus(@Param("status") MatchState status, @Param("name") String name,
			@Param("accountNo") String accountNo);

	Integer getEffectiveBySerialNumber(@Param("orderSerialNumber") String orderSerialNumber);

	int confirm(@Param("id") Integer id, @Param("adminUser") Integer adminId);

	int induival(@Param("id") Integer id, @Param("adminUser") Integer adminId);

	DepositMatchCurrencyOrderUser selectByPrimaryKeyLock(Integer id);
}