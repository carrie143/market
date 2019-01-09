package com.gop.c2c.service;

import java.util.List;

import com.gop.domain.C2cTransOrderComplaint;
import com.gop.domain.enums.C2cComplaintStatus;
import com.gop.domain.enums.C2cTransType;

public interface C2cTransOrderComplaintService {

	void creatComplaint(C2cTransOrderComplaint c2cTransOrderComplaint, Integer uid, String phone);

	List<C2cTransOrderComplaint> queryByStatusAndComplainType(Integer pageNo,Integer pageSize,C2cComplaintStatus status,C2cTransType complainType);

	C2cTransOrderComplaint selectByComplainId(String complainId);

	void forceTransforCoin(String complainId, Integer managerUid);

	void forceCloseComplaint(String complainId, Integer managerUid);


}
