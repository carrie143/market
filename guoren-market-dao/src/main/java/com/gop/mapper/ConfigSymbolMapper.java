package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ConfigSymbol;
import com.gop.domain.enums.SymbolStatus;

public interface ConfigSymbolMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ConfigSymbol record);

	int insertSelective(ConfigSymbol record);

	ConfigSymbol selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ConfigSymbol record);

	int updateByPrimaryKey(ConfigSymbol record);

	ConfigSymbol selectBySymbol(@Param("symbol") String symbol);

	List<ConfigSymbol> selectAll();

	ConfigSymbol selectBySymbolOnLised(@Param("symbol")String symbol,@Param("status") SymbolStatus listed);

	List<ConfigSymbol> selectAllSymbolBySymbol(String symbol);
	
	int updateConfigsymbolStatusByid(@Param("id")Integer id, @Param("status")SymbolStatus symbolStatus);
}