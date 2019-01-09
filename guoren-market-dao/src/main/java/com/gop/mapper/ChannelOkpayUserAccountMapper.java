package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ChannelOkpayUserAccount;

public interface ChannelOkpayUserAccountMapper {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(ChannelOkpayUserAccount record);

	ChannelOkpayUserAccount selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ChannelOkpayUserAccount record);

	int updateByPrimaryKey(ChannelOkpayUserAccount record);

	List<ChannelOkpayUserAccount> getOkpayAccoutList(@Param("uid") Integer uid, @Param("delFlag") String delFlag);

	/**
	 * 根据用户名和支付宝账号获取数据库记录
	 * 
	 * @param uid
	 * @param alipayAccount
	 * @return
	 */
	ChannelOkpayUserAccount getRecordByUidAndOkpayAccout(@Param("uid") Integer uid,
			@Param("okpayAccount") String okpayAccount);
}