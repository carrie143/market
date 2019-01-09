package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.JubiUserInformation;
import com.gop.domain.enums.JubiRegisterFlag;

public interface JubiUserInformationMapper {

    int insert(JubiUserInformation record);

    int insertSelective(JubiUserInformation record);

    JubiUserInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JubiUserInformation record);

    int updateByPrimaryKey(JubiUserInformation record);
    
    List<JubiUserInformation> selectByPhone(String phone);
    
    List<JubiUserInformation> selectByEmail(String email);
    
    int updateJubiUserFlagRegister(@Param("id")Integer id,@Param("registerFlag")JubiRegisterFlag flag);

    
}