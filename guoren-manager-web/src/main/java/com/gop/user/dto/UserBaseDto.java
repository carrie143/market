package com.gop.user.dto;

import com.gop.domain.User;
import com.gop.domain.UserLoginLog;
import com.gop.domain.enums.AuthLevel;
import lombok.Data;

import java.util.Date;

/**
 * Created by wuyanjie on 2018/4/12.
 */
@Data
public class UserBaseDto {

    Date createDate;

    Integer uid;

    String email;

    String fullname;

    AuthLevel authLevel;


    int lock_num;

    String ipadress;

    Date loginDate;

    String ip;
    public UserBaseDto(User user, UserLoginLog userLoginLog){
        this.setCreateDate(user.getCreateDate());
        this.setUid(user.getUid());
        this.setEmail(user.getEmail());
        this.setFullname(user.getFullname());
        this.setAuthLevel(user.getAuthLevel());
        this.setIp(user.getCreateip());
        this.setLock_num(user.getLockNum());
        if(userLoginLog != null) {
            this.setIpadress(userLoginLog.getIpAddress());
            this.setLoginDate(userLoginLog.getCreateDate());
        }
    }
}
