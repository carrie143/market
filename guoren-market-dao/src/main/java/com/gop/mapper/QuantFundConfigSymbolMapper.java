package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.QuantFundConfigSymbol;
import com.gop.domain.enums.QuantFundConfigSymbolStatus;

public interface QuantFundConfigSymbolMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuantFundConfigSymbol record);

    int insertSelective(QuantFundConfigSymbol record);

    QuantFundConfigSymbol selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuantFundConfigSymbol record);

    int updateByPrimaryKey(QuantFundConfigSymbol record);

	int insertOrUpdate(QuantFundConfigSymbol symbol);

	QuantFundConfigSymbol selectBySymbol(String symbol);

	List<QuantFundConfigSymbol> selectByFundSymbol(@Param("fundSymbol")String fundSymbol, @Param("status")QuantFundConfigSymbolStatus status);
}