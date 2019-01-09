package com.gop.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.DepositMatchBankOrderUser;

public interface DepositMatchBankOrderUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepositMatchBankOrderUser record);

    int insertSelective(DepositMatchBankOrderUser record);

    DepositMatchBankOrderUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepositMatchBankOrderUser record);

    int updateByPrimaryKey(DepositMatchBankOrderUser record);
    
    List<DepositMatchBankOrderUser> queryBankStatement(Map map);

	int unrelevanceBySerialNumber(@Param("serialNumber") String serialNumber);

	int confirmBySerialNumber(@Param("serialNumber") String orderSerialNumber);

	DepositMatchBankOrderUser selectByPrimaryKeyLock(Integer id);
}