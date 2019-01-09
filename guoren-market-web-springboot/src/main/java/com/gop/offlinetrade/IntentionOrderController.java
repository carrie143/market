package com.gop.offlinetrade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.gop.code.consts.CommonCodeConst;
import com.gop.config.CommonConstants;
import com.gop.domain.TokenOrderIntention;
import com.gop.domain.enums.TokenOrderIntentionState;
import com.gop.exception.AppException;
import com.gop.mode.vo.PageModel;
import com.gop.offlineorder.service.IntentionOrderSerrvice;
import com.gop.offlineorder.service.TokenOrderService;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

@RestController
@RequestMapping("/intention")
public class IntentionOrderController {
	@Autowired
	private IntentionOrderSerrvice intentionOrderSerrvice;
	@Autowired
	private TokenOrderService tokenOrderService;

	// 发布意向
	@RequestMapping(value = "/create-intention-order", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	public void createIntentionOrder(@AuthForHeader AuthContext authContext,
			@RequestParam("assetName") String assetName, @RequestParam("number") BigDecimal number,
			@RequestParam(value = "qq") String qq, @RequestParam(value = "price") BigDecimal price,
			@RequestParam(value = "weChat", required = false, defaultValue = "") String weChatNumber,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone) {
		if (assetName.length() > 5 || qq.length() > 20 || weChatNumber.length() > 20 || phone.length() > 20) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		if (number.compareTo(BigDecimal.ZERO) <= 0) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		if (price.compareTo(BigDecimal.ZERO) <= 0) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
        
		TokenOrderIntention intentionOrder = new TokenOrderIntention();
		intentionOrder.setUid(authContext.getLoginSession().getUserId());
		intentionOrder.setPrice(price);
		intentionOrder.setCreateDate(new Date());
		intentionOrder.setUpdateDate(new Date());
		intentionOrder.setSellNum(number);
		intentionOrder.setPhone(phone);
		intentionOrder.setAssetName(assetName);
		intentionOrder.setQq(qq);
		intentionOrder.setWechat(weChatNumber);
		int successNum = tokenOrderService.getSuccessOrderByUid(authContext.getLoginSession().getUserId());
		intentionOrder.setSuccessTradeNum(successNum);
		intentionOrder.setState(TokenOrderIntentionState.PROCESSING);
		intentionOrderSerrvice.createIntentionOrderSerrvice(intentionOrder);
	}

	// 修改意向状态
	@RequestMapping(value = "/change-intention-order-state", method = RequestMethod.GET)
	@Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
			@Strategy(authStrategy = "exe({{'checkCertification'}})") })
	public void changeIntentionOrderState(@AuthForHeader AuthContext authContext,
			@RequestParam("state") TokenOrderIntentionState tokenOrderIntentionState, @RequestParam("id") int id) {

		intentionOrderSerrvice.changeIntentionOrderState(authContext.getLoginSession().getUserId(), id,
				tokenOrderIntentionState);
	}

	// 获取意向
	@RequestMapping(value = "/get-intention-order-list", method = RequestMethod.GET)
	public PageModel getIntentionOrderList(@AuthForHeader AuthContext authContext, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize,
			@RequestParam(value = "assetName", required = false) String assetName,
			@RequestParam(value = "state", required = false) TokenOrderIntentionState status,
			@RequestParam(value = "uid", required = false) Integer uid) {
		pageNo = CommonConstants.getPageNo(pageNo);
		pageSize = CommonConstants.getPageNo(pageSize);
		Page<TokenOrderIntention> page = intentionOrderSerrvice.getIntentionOrderbyPage(assetName, status, uid, pageNo,
				pageSize);

		PageModel<TokenOrderIntention> PageModel = new PageModel<TokenOrderIntention>();
		//如果用户传入非法字符串,查询为null,则回传空list
		if (page.getResult() == null) {
			PageModel.setList(Collections.EMPTY_LIST);
		}else {
			PageModel.setList(page.getResult());
		}
		PageModel.setPageNo(pageNo);
		PageModel.setPageSize(page.getPageSize());
		PageModel.setTotal(page.getTotal());
		PageModel.setPageNum(page.getPages());
		return PageModel;
	}

	// 获取意向
	@RequestMapping(value = "/get-intention-order", method = RequestMethod.GET)
	public TokenOrderIntention getIntentionOrder(@RequestParam(value = "id") Integer id) {

		return intentionOrderSerrvice.getById(id);

	}
}
