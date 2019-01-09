package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ManagerPasswordOperRecord;
import com.gop.domain.enums.ManagerSetPwdFlag;

public interface ManagerPasswordOperRecordMapper {

    int insert(ManagerPasswordOperRecord record);

    int insertSelective(ManagerPasswordOperRecord record);

    ManagerPasswordOperRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerPasswordOperRecord record);

    int updateByPrimaryKey(ManagerPasswordOperRecord record);
    
    ManagerPasswordOperRecord selectManagerPasswordOperRecord(Integer adminId);
    
    void addManagerPasswordOperRecord(ManagerPasswordOperRecord managerPasswordOperRecord);
    
    void updateManagerPasswordOperRecordFlagByAdminId(@Param("adminId")Integer adminId, @Param("modifyFlag")ManagerSetPwdFlag managerSetPwdFlag);
    
}