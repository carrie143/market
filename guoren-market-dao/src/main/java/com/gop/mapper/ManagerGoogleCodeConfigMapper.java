package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ManagerGoogleCodeConfig;
import com.gop.domain.enums.DelFlag;

public interface ManagerGoogleCodeConfigMapper {

    int insert(ManagerGoogleCodeConfig record);

    int insertSelective(ManagerGoogleCodeConfig record);

    ManagerGoogleCodeConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagerGoogleCodeConfig record);

    int updateByPrimaryKey(ManagerGoogleCodeConfig record);
    
    int countManagerGoogleCodeConfigByAdminId(Integer adminId);
    
    void addManagerGoogleCodeConfig(ManagerGoogleCodeConfig managerGoogleCodeConfig);
    
    ManagerGoogleCodeConfig selectManagerGoogleCodeConfigByAdminId(@Param("adminId")Integer adminId,@Param("delFlag")DelFlag delflag);
    
    void updateManagerGoogleCodeConfigByAdminIdAndDelFlag(@Param("adminId")Integer adminId,
    		@Param("beginflag")DelFlag beginflag,@Param("endflag")DelFlag endflag);
    
}