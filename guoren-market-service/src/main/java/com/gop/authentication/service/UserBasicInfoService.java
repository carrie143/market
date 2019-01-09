package com.gop.authentication.service;

import com.gop.domain.UserBasicInfo;

public interface UserBasicInfoService {

	void insertOrUpdate(UserBasicInfo userBasicInfo);

	UserBasicInfo getBasicInfoByUid(Integer uid);

}
