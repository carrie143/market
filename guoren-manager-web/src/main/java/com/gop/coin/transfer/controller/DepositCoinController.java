package com.gop.coin.transfer.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gop.api.cloud.request.DepositQueryRequest;
import com.gop.api.cloud.response.DepositDetailDto;
import com.gop.api.cloud.service.CloudApiService;
import com.gop.code.consts.UserAssetCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.domain.User;
import com.gop.domain.enums.DepositCoinAssetStatus;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.user.service.UserService;
import com.gop.util.SequenceUtil;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController("managerDepositCoinController")
@RequestMapping("/deposit/coin")
public class DepositCoinController {

	@Autowired
	private CloudApiService cloudApiService;
	@Autowired
	private UserService userService;

	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkLoginPasswordStrategy'}})") })
	@RequestMapping(value = "/uncerf", method = RequestMethod.GET)
	public void depositCoin(@AuthForHeader AuthContext context, @RequestParam("uid") Integer uid,
			@RequestParam("assetCode") String assetCode, @RequestParam("amount") BigDecimal amount) {
		return;
	}

	/**
	 * * 充值记录
	 */
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'CheckUserRoleStrategy'}})"),
		@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})") })
	@RequestMapping(value = "/transfer", method = RequestMethod.GET)
	// @ApiOperation("查询充值记录")
	public PageModel<DepositDetailDto> transfer(@AuthForHeader AuthContext context,
																							@RequestParam(value = "brokerId", required = false) Integer brokerId,
																							@RequestParam(value = "id", required = false) Integer id,
																							@RequestParam(value = "accout", required = false) String account,
																							@RequestParam(value = "uId", required = false) Integer uId,
																							@RequestParam(value = "address", required = false) String address,
																							@RequestParam(value = "txid", required = false) String txid,
																							@RequestParam(value = "assetCode", required = false) String assetCode,
																							@RequestParam(value = "beginTime", required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
																							@RequestParam(value = "endTime", required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
																							@RequestParam(value = "email", required = false)  String email,
																							@RequestParam(value = "status", required = false) DepositCoinAssetStatus status,
																							@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {
		Integer uid = uId;
		if (uid!=null) {
			User userByUid = userService.getUserByUid(uid);
			if (userByUid == null) {
				throw new AppException(UserCodeConst.NO_REGISTER,"该用户不存在");
			}
		}
		// 邮箱找id
		if (email != null){
			User user = userService.getUserByAccount(email);
			if (user==null){
				throw new AppException(UserCodeConst.EMAIL_NOT_EXIST,"该邮箱不存在");
			}
			uid = user.getUid();
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DepositQueryRequest request = new DepositQueryRequest();
		request.setUid(uid==null?null:(long)uid);
		request.setStartDate(beginTime==null?null:format.format(beginTime));
		request.setEndDate(endTime==null?null:format.format(endTime));
		request.setAddress(address);
		request.setAssetCode(assetCode);
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setStatus(status);

		PageInfo<DepositDetailDto> pageInfo = cloudApiService.depositeCoinDetail(request);
		PageModel<DepositDetailDto> pageModel = new PageModel<DepositDetailDto>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		pageModel.setPageNum(pageInfo.getPages());
		pageModel.setTotal(pageInfo.getTotal());
		pageModel.setList(pageInfo.getList());

		return pageModel;
	}
	


}
