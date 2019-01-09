package com.gop.mapper;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserGoogleCodeConfig;
import com.gop.domain.enums.UserGoogleCodeFlagType;

public interface UserGoogleCodeConfigMapper {

    int insert(UserGoogleCodeConfig record);

    int insertSelective(UserGoogleCodeConfig record);

    UserGoogleCodeConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserGoogleCodeConfig record);

    int updateByPrimaryKey(UserGoogleCodeConfig record);
    
    int countGoogleCodeConfigByUid(Integer uid);
    
    int updateUserGoogleCodeFlagByUid(@Param("uid")Integer uid,@Param("flag")UserGoogleCodeFlagType flag);
    
    UserGoogleCodeConfig selectByUid(Integer uid);
    
    int updateUserGoogleCodeSecretCodeByUid(@Param("uid")Integer uid,@Param("secretCode")String secretCode);
    
    int updateSelectiveByUid(UserGoogleCodeConfig userGoogleCodeConfig);
}