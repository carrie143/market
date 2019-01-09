package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.C2cTransOrderComplaint;
import com.gop.domain.enums.C2cComplaintStatus;
import com.gop.domain.enums.C2cTransType;

public interface C2cTransOrderComplaintMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(C2cTransOrderComplaint record);

	int insertSelective(C2cTransOrderComplaint record);

	C2cTransOrderComplaint selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(C2cTransOrderComplaint record);

	int updateByPrimaryKey(C2cTransOrderComplaint record);

	List<C2cTransOrderComplaint> selectByStatusAndComplainType(@Param("status")C2cComplaintStatus status,@Param("complainType")C2cTransType complainType);

	C2cTransOrderComplaint selectByComplainId(String complainId);

	int updateToProcesedByComplainId(@Param("complainId") String complainId, @Param("transOrderId") String transOrderId,
			@Param("operUid") Integer operUid,@Param("preStatus") C2cComplaintStatus preStatus,@Param("endStatus") C2cComplaintStatus endStatus);

	int selectByTransOrderId(String transOrderId);
}