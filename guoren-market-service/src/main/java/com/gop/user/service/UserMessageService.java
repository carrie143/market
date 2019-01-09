package com.gop.user.service;

import java.util.List;

import com.gop.domain.UserMessage;
import com.gop.domain.enums.UserMessageCategory;

public interface UserMessageService {
    
	List<UserMessage> unReadMessage(Integer uid);
	
	/**
     * 查询消息系统
     * 
     * Description: <br/>
     * @Author：zhangxianglong
     * @date 2016年3月21日上午10:43:47
     * @param type 
     * @see
     */
     List<UserMessage> selectUserMessageList(Integer uid, Integer pageNum, Integer pageSize, UserMessageCategory category);
     
 	public int insertMessage(Integer uid, String message);
 	
 	public int insertMessage(Integer uid, String message,UserMessageCategory category);
 	
}
