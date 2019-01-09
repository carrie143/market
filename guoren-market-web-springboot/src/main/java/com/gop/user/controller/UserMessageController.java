package com.gop.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gop.domain.UserMessage;
import com.gop.domain.enums.UserMessageCategory;
import com.gop.mode.vo.PageModel;
import com.gop.user.dto.MessageDto;
import com.gop.user.service.UserMessageService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserMessageController {
	// 查询未读消息数量

	// 查询消息

	@Autowired
	private UserMessageService userMessageService;

	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	@ResponseBody
	public PageModel<MessageDto> message(@AuthForHeader AuthContext authContext,
			@RequestParam(value = "category", required = false) UserMessageCategory category,
			@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {

		int uid = authContext.getLoginSession().getUserId();
		List<UserMessage> list = userMessageService.selectUserMessageList(uid, pageNo, pageSize, category);

		PageInfo<UserMessage> pageInfo = new PageInfo<>(list);

		PageModel<MessageDto> pageMode = new PageModel<MessageDto>();
		pageMode.setPageNo(pageNo);
		pageMode.setPageSize(pageSize);
		pageMode.setPageNum(pageInfo.getPages());
		pageMode.setList(
				pageInfo.getList().stream().map(message -> new MessageDto(message)).collect(Collectors.toList()));

		return pageMode;

	}

	/**
	 * 查询未读消息及数量(只显示最新2条未读信息)
	 * 
	 * Description: <br/>
	 * 
	 * @Author：zhangxianglong
	 * @date 2016年3月21日上午10:26:21
	 * @return
	 * @see
	 */
	@Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"))
	@RequestMapping(value = "/message/unread", method = RequestMethod.GET)
	@ResponseBody
	public List<MessageDto> unReadMessage(@AuthForHeader AuthContext authContext) {

		int uid = authContext.getLoginSession().getUserId();
		List<UserMessage> list = userMessageService.unReadMessage(uid);

		return list.stream().map(message -> new MessageDto(message)).collect(Collectors.toList());
	}
}
