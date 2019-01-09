package com.gop.user.facade;

import com.gop.domain.User;
import com.gop.domain.enums.AuthLevel;
import com.gop.user.dto.CheckLoginLockedDto;
import com.gop.user.dto.UserSimpleInfoDto;

public interface UserFacade {

  public void checkLoginPassword(Integer uid, String loginPasswod);

  public void checkPayPassword(Integer uid, String payPasswod);

  public CheckLoginLockedDto LoginPasswordLockNum(Integer uid);

  public User getUser(String accountNo);

  public User getUser(Integer uid);

  public UserSimpleInfoDto getUserInfoByUid(Integer uid);

  public void checkPayPassword(String userName, String payPassword);

  public void checkLoginPassword(String userName, String loginPassword);

  public void updateUserInfo(int uid, String name);

  public User createUser(String email, String phone, String password, String payPwd,
      Integer invitId, String nickname,
      Integer brokerId);

  public User reRegistrationUserCreate(String email, String phone, String password,
      String loginsalt, Integer invitId, String nickname, String ip);

  public void updateAuthStatus(Integer uid, AuthLevel status);

  public void checkUserGoogleCode(Integer uid, String email, String code);

}
