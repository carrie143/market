package com.gop.sms.service;

import com.gop.domain.ConfigEmail;
import com.gop.domain.enums.ConfigEmailStatus;

public interface ConfigEmailService {
 
	int updateConfigEmail(ConfigEmail configEmail);

	ConfigEmail getEmailByMinSendCountAndStatus(ConfigEmailStatus listed);
	
	int updateEmailCount(Integer id);
}
