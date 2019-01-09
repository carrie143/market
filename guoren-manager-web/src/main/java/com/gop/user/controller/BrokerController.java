package com.gop.user.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gop.domain.Broker;
import com.gop.mapper.BrokerMapper;
import com.gop.mode.vo.PageModel;
import com.gop.user.dto.BrokerDetailDto;
import com.gop.user.dto.BrokerDto;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController("managerBrokerController")
@RequestMapping("/broker")
// @Api("用户管理模块")
public class BrokerController {

	@Autowired
	private BrokerMapper brokerMapper;
//	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})") })
	@RequestMapping(value = "/infos", method = RequestMethod.GET)
	// @ApiOperation("获取券商信息")
	public PageModel<BrokerDto> getBrokerList(@AuthForHeader AuthContext context,
			@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
		
		
		PageInfo<Broker> pageInfo = new PageInfo<>(brokerMapper.getBrokerList());
		
		PageModel<BrokerDto> pageModel = new PageModel<BrokerDto>();
		
		pageModel.setPageNo(pageNo);;
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(pageInfo.getTotal());
		pageModel.setPageNum(pageInfo.getPages());
		pageModel.setList(pageInfo.getList().stream().map(broker -> new BrokerDto(broker)).collect(Collectors.toList()));
		
		return pageModel;
	}

//	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})") })
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	// @ApiOperation("获取券商信息列表")
	public BrokerDetailDto getBrokerInfo(@AuthForHeader AuthContext context,
			@RequestParam("brokerId") Long brokerId) {
		return new BrokerDetailDto(brokerMapper.selectByPrimaryKey(brokerId));
	}

}
