package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.ChannelBankUserAccount;

public interface ChannelBankUserAccountMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ChannelBankUserAccount record);

	int insertSelective(ChannelBankUserAccount record);

	ChannelBankUserAccount selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ChannelBankUserAccount record);

	int updateByPrimaryKey(ChannelBankUserAccount record);

	/**
	 * 
	 * @param uid
	 * @param acNumber
	 * @param delFlag
	 * @return
	 */
	ChannelBankUserAccount selectUserAcBankByUserAcNumber(@Param("uid") Integer uid,
			@Param("acNumber") String acNumber);

	List<ChannelBankUserAccount> bankList(@Param("userId") Integer uid);

}