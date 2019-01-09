package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ConfigSymbolProfile;
import com.gop.domain.enums.ConfigSymbolType;

public interface ConfigSymbolProfileMapper {

	int insert(ConfigSymbolProfile record);

	int insertSelective(ConfigSymbolProfile record);

	int insertOnDuplicate(ConfigSymbolProfile record);

	ConfigSymbolProfile selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ConfigSymbolProfile record);

	int updateByPrimaryKey(ConfigSymbolProfile record);

	List<ConfigSymbolProfile> getBySymbol(String symbol);

	List<ConfigSymbolProfile> selectAll();

	List<ConfigSymbolProfile> selectByProfileKey(ConfigSymbolType key);

	ConfigSymbolProfile selectBySymbolAndProfileKey(@Param("symbol") String symbol,
			@Param("configSymbolType") ConfigSymbolType key);
}