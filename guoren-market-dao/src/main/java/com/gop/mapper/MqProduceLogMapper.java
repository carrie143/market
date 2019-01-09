package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.MqProduceLog;

public interface MqProduceLogMapper {
	MqProduceLog selectByPrimaryKey(@Param("id")Long id,@Param("tableNum")int tableNum);

	int updateByPrimaryKeySelective(@Param("produceLog")MqProduceLog record,@Param("tableNum")int tableNum);

	int insertProduceLogs(@Param("list")List<MqProduceLog> list,@Param("tableNum")int tableNum);
}