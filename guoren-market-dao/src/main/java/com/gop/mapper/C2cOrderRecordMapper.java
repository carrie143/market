package com.gop.mapper;

import com.gop.domain.C2cOrderRecord;

public interface C2cOrderRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(C2cOrderRecord record);

    int insertSelective(C2cOrderRecord record);

    C2cOrderRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(C2cOrderRecord record);

    int updateByPrimaryKey(C2cOrderRecord record);

	C2cOrderRecord selectByAdvertId(String advertId);
	
	int addC2cOrderRecordByAdvertId(C2cOrderRecord c2cOrderRecord);
}