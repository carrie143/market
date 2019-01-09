package com.gop.currency.transfer.service;

import com.gop.currency.transfer.dto.AcAccountDto;
import com.gop.mode.vo.PageModel;


/**
 * 用户渠道账号管理模块
 * @author Administrator
 *
 */
public interface ChannelUserAccountBaseService {
	
	public void addAccount(String channelAccountNo, String channelAccountName, Integer uid);

	public void deleteAccount(String channelAccountNo, Integer uid);

	public PageModel<AcAccountDto> getList(Integer uid, Integer pageNo,Integer pageSize);
	
}
