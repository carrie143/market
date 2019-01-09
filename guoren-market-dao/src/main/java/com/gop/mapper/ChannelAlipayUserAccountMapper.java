package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ChannelAlipayUserAccount;

public interface ChannelAlipayUserAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelAlipayUserAccount record);

    int insertSelective(ChannelAlipayUserAccount record);

    ChannelAlipayUserAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelAlipayUserAccount record);

    int updateByPrimaryKey(ChannelAlipayUserAccount record);
    
    
    /**
     * 返回用户的支付宝列表,如果uid为空，则返回所有用户的支付宝账号
     * @param uid
     * @param delFlag TODO
     * @return
     */
    List<ChannelAlipayUserAccount> getAlipayAccoutList(@Param("uid") Integer uid, @Param("delFlag") String delFlag);
    
    /**
     * 根据用户名和支付宝账号获取数据库记录
     * @param uid
     * @param alipayAccount
     * @return
     */
    ChannelAlipayUserAccount getRecordByUidAndAlipayAccout(@Param("uid") Integer uid, @Param("alipayAccount") String alipayAccount); 
}