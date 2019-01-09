package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.QuantFundConfig;
import com.gop.domain.enums.QuantFundConfigType;

public interface QuantFundConfigMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(QuantFundConfig record);

	int insertSelective(QuantFundConfig record);

	QuantFundConfig selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(QuantFundConfig record);

	int updateByPrimaryKey(QuantFundConfig record);

	List<QuantFundConfig> selectByCodeAndKey(@Param("fundCode")String fundCode, @Param("key")QuantFundConfigType key);

	int insertOrUpdate(QuantFundConfig record);

}