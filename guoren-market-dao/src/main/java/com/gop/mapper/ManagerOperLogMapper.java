package com.gop.mapper;

import java.util.List;

import com.gop.domain.ManagerOperLog;

public interface ManagerOperLogMapper {

    int insert(ManagerOperLog record);

    int insertSelective(ManagerOperLog record);

    ManagerOperLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerOperLog record);

    int updateByPrimaryKey(ManagerOperLog record);
    
    void addManagerOperLog(ManagerOperLog ManagerOperLog);
    
    List<ManagerOperLog> selectManagerOperLogByAdminId(Integer adminId);
}