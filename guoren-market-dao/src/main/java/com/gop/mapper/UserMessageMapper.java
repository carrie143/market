package com.gop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gop.domain.UserMessage;
import com.gop.domain.enums.UserMessageCategory;
import com.gop.domain.enums.UserMessageStatus;

public interface UserMessageMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserMessage record);

	int insertSelective(UserMessage record);

	UserMessage selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(UserMessage record);

	int updateByPrimaryKeyWithBLOBs(UserMessage record);

	int updateByPrimaryKey(UserMessage record);

	List<UserMessage> selectUserMessageList(@Param("uid") Integer uid, @Param("category") UserMessageCategory category);

	List<UserMessage> unReadMessage(@Param("uid") Integer uid, @Param("status") String status);

	int updateByStatus(@Param("readStatus") UserMessageStatus readStatus,
			@Param("unreadStatus") UserMessageStatus unreadStatus);
}