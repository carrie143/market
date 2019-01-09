package com.gop.activity.service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gop.activity.service.InviteUserInfoService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.InviteUserInfo;
import com.gop.exception.AppException;
import com.gop.mapper.InviteUserInfoMapper;

@Service
public class InviteUserInfoServiceImpl implements InviteUserInfoService {
	
	@Autowired
	private InviteUserInfoMapper inviteUserInfoMapper;
	
	@Override
	public InviteUserInfo getInviteUserInfoByUid(Integer uid) {
		InviteUserInfo inviteUserInfo = inviteUserInfoMapper.getInviteUserInfoByUid(uid);
		if (null == inviteUserInfo) {
			InviteUserInfo newinviteUserInfo = new InviteUserInfo();
			newinviteUserInfo.setUid(uid);
			//生成随机八位邀请码
			newinviteUserInfo.setInviteCode(getStringRandom(8));
			if (addInviteUserInfo(newinviteUserInfo) != 1) {
				throw new AppException(CommonCodeConst.SERVICE_ERROR);
			}
			return newinviteUserInfo;
		}else {
			return inviteUserInfo;
		}
	}

	@Override
	public int addInviteUserInfo(InviteUserInfo inviteUserInfo) {
		return inviteUserInfoMapper.addInviteUserInfo(inviteUserInfo);
	}
	
	//生成随机数字和字母
    public String getStringRandom(int length) {  
        String val = "";  
        Random random = new Random();  
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }

	@Override
	public InviteUserInfo getInviteUserInfoByInviteCode(String inviteCode) {
		return inviteUserInfoMapper.getInviteUserInfoByInviteCode(inviteCode);
	}
}
