package com.gop.c2c.service;

import java.math.BigDecimal;
import java.util.List;

import com.gop.c2c.dto.C2cTransOrderDto;
import com.gop.domain.C2cTransOrder;
import com.gop.domain.enums.C2cPayType;
import com.gop.domain.enums.C2cTransOrderStatus;
import com.gop.domain.enums.C2cTransType;
import com.gop.mode.vo.PageModel;

public interface C2cTransOrderService {
	C2cTransOrder selectByTransOrderId(String transOrderId);

	String createTransOrder(String advertId, Integer uid, BigDecimal number, C2cPayType buyPayType, String remark);

	void updateTransOrderToPaid(String c2cTransOrderId, Integer operaUid, Integer operRecordUid, String msg);

	void updateTransOrderToFinishedAndTransforCoin(String c2cTransOrderId, Integer operaUid, Integer operRecordUid,
			String msg);

	void updateTransOrderToCancel(String c2cTransOrderId, Integer operaUid, Integer operRecordUid, String msg);

	void updateTransOrderToComplaint(String c2cTransOrderId, Integer operaUid, Integer operRecordUid, String msg);

	void updateTransOrderToOverTime(String c2cTransOrderId, Integer operaUid, Integer operRecordUid, String msg);

	void updateTransOrderFromComplaintToFinished(String transOrderId, Integer operaUid, Integer operRecordUid,
			String msg);

	boolean updatetransOrderToClosed(String transOrderId, Integer sellUid, Integer managerUid, String msg);


	List<C2cTransOrder> selectByStatus(C2cTransOrderStatus status);

	List<C2cTransOrder> queryBuyerByUncompletedStatus(Integer uid, C2cTransOrderStatus status);

	List<C2cTransOrder> querySellerByUncompletedStatus(Integer uid, C2cTransOrderStatus status);

	List<C2cTransOrder> queryByflagAndPayTypeAndStatus(Integer seller,Integer buyer,String
			completed, C2cTransOrderStatus status,Integer pageNo, Integer pageSize);

	PageModel<C2cTransOrderDto> managerOrderList(Integer pageNo, Integer pageSize, C2cTransOrderStatus status,
			Integer uid, C2cTransType orderType);

}
