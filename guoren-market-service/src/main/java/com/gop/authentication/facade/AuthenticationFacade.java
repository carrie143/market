package com.gop.authentication.facade;

import com.gop.authentication.dto.UserAuthLevel0Dto;
import com.gop.authentication.dto.UserIdentificationDto;
import com.gop.domain.UserIdentification;
import com.gop.domain.UserResidence;
import com.gop.domain.enums.AuditStatus;
import com.gop.domain.enums.AuthLevel;
import com.gop.mode.vo.PageModel;

public interface AuthenticationFacade {

	public void commitUserLevel1Info(UserAuthLevel0Dto userAuthLevel0Dto);

	public void commUserLevel2Info(UserResidence userResidence);

	public PageModel<UserIdentification> getUserIdentificationUnverify(Integer uid, int pageNo, int pageSize);

	public PageModel<UserIdentification> getUserIdentificationverifyed(Integer uid, int pageNo, int pageSize);

	public UserIdentificationDto getUserIdentificationInfo(Integer uid);

	public void checkPassport(Integer userId, String passort);

	public void auditUserLevel0To1(int adminId, Integer id, AuditStatus auditStatus, String auditMessageId,
			String auditMessage,String sendMessage);

	public void auditUserLevel1To2(int adminId, Integer id, AuditStatus auditStatus, String auditMessageId,
			String auditMessage, String sendMessage);

	
	
}
