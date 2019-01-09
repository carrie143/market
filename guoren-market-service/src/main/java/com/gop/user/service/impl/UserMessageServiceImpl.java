package com.gop.user.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.gop.domain.UserMessage;
import com.gop.domain.enums.UserMessageCategory;
import com.gop.domain.enums.UserMessageStatus;
import com.gop.mapper.UserMessageMapper;
import com.gop.user.service.UserMessageService;

@Service
public class UserMessageServiceImpl implements UserMessageService {
	
	private static final Logger loger = LoggerFactory.getLogger(UserMessageServiceImpl.class);
	
	private static final Integer pageNum = 1;
	
	private static final Integer pageSize = 2;
	
	private static final String STATUS = "UNREAD";
	
	@Autowired
	private UserMessageMapper userMessageMapper;
	
	/**
     * 查询消息系统
     * 
     * Description: <br/>
     * @Author：zhangxianglong
     * @date 2016年3月21日上午10:55:03
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return 
     * @see
     */
    @Override
    public List<UserMessage> selectUserMessageList(Integer uid, Integer pageNum, Integer pageSize, UserMessageCategory category)
    {
        loger.info("查询消息系统开始service");
        PageHelper.startPage(pageNum, pageSize, true);
        PageHelper.orderBy("create_date desc");
        List<UserMessage> list = userMessageMapper.selectUserMessageList(uid, category);
        userMessageMapper.updateByStatus(UserMessageStatus.READ, UserMessageStatus.UNREAD);
        loger.info("查询消息系统结束service");
        return list;
    }
	
	/**
     * 查询未读消息及数量
     * 
     * @author zhangxianglong
     */
    @Override
    public List<UserMessage> unReadMessage(Integer uid)
    {
//        loger.info("查询未读消息及数量开始service");
  
        PageHelper.orderBy("create_date desc");
        List<UserMessage> list = userMessageMapper.unReadMessage(uid, STATUS);
//        loger.info("查询未读消息及数量结束service");
        return list;
    }
    
    @Override
	public int insertMessage(Integer uid, String message) {		
    	UserMessage record = new UserMessage();
		record.setUid(uid);
		record.setContent(message);
		record.setCategory(UserMessageCategory.ASSETS);
		record.setStatus(UserMessageStatus.UNREAD);
		record.setCreateDate(new Date());
		
		return userMessageMapper.insertSelective(record);
	}

	@Override
	public int insertMessage(Integer uid, String message, UserMessageCategory category) {
		UserMessage record = new UserMessage();
		record.setUid(uid);
		record.setContent(message);
		record.setCategory(category);
		record.setStatus(UserMessageStatus.UNREAD);
		record.setCreateDate(new Date());
		
		return userMessageMapper.insertSelective(record);
	}
}
