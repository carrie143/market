package com.gop.user.facade.impl;

import com.gop.api.cloud.request.CreateUserReq;
import com.gop.api.cloud.response.UserData;
import com.gop.api.cloud.service.CloudApiService;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.SecurityCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.common.Environment;
import com.gop.common.Environment.EnvironmentEnum;
import com.gop.domain.User;
import com.gop.domain.UserGoogleCodeConfig;
import com.gop.domain.enums.AuthLevel;
import com.gop.domain.enums.UserGoogleCodeFlagType;
import com.gop.exception.AppException;
import com.gop.user.dto.CheckLoginLockedDto;
import com.gop.user.dto.CheckPayPasswordLockedDto;
import com.gop.user.dto.UserSimpleInfoDto;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserGoogleCodeConfigService;
import com.gop.user.service.UserPayPasswordService;
import com.gop.user.service.UserService;
import com.gop.user.service.impl.CredentialRepositoryMock;
import com.gop.util.ConstantUtil;
import com.gop.util.CryptoUtils;
import com.gop.util.MD5Util;
import com.warrenstrange.googleauth.GoogleAuthenticator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserFacadeImpl implements UserFacade {

  @Autowired
  private UserPayPasswordService userPayPasswordService;

  @Autowired
  private UserService userService;

  @Autowired
  private UserGoogleCodeConfigService userGoogleCodeConfigService;

  @Autowired
  private Environment environment;

  @Autowired
  private CredentialRepositoryMock credentialRepositoryMock;

  @Autowired
  private CloudApiService cloudApiService;


  /**
   * email用户校验登录密码合格与否
   */
  @Override
  public void checkLoginPassword(Integer uid, String loginPasswod) {
    if (StringUtils.isEmpty(loginPasswod)) {
      log.info("登录密码为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    loginPasswod = ConstantUtil.charConvert(loginPasswod);

    User user = userService.getUserByUid(uid);
    if (user == null) {
      log.info("修改登录密码，在user表中找不到uid={} 的记录", uid);
      throw new AppException(UserCodeConst.NO_REGISTER);
    }

    if (!CryptoUtils
        .verify(user.getLoginPassword(), MD5Util.genMD5Code(loginPasswod), user.getLoginSalt())) {
      User userTemp = new User();
      userTemp.setUid(user.getUid());
      userTemp.setUpdateDate(new Date());
      userTemp.setLockNum((byte) (user.getLockNum() + 1));
      userService.updateByPrimaryKeySelective(userTemp);
      CheckLoginLockedDto loginLockTimes = userService.checkLoginLockTimes(user.getUid());
      JSONObject json = new JSONObject();
      json.put("num", loginLockTimes.getLockedNum());
      throw new AppException(UserCodeConst.LOGIN_ERROR, "", json);
    } else {
      User userTemp = new User();
      userTemp.setUid(user.getUid());
      userTemp.setUpdateDate(new Date());
      userTemp.setLockNum((byte) (0));
      userService.updateByPrimaryKeySelective(userTemp);
    }

  }

  /**
   * 校验email用户支付密码是否正确
   */
  @Override
  public void checkPayPassword(Integer uid, String payPasswod) {

    // 校验支付密码正确与否
    if (StringUtils.isEmpty(payPasswod)) {
      log.info("支付密码为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }

    payPasswod = ConstantUtil.charConvert(payPasswod);

    User user = userService.getUserByUid(uid);
    if (user == null) {
      log.error("修改支付密码，在user表中找不到uid={} 的记录", uid);
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    CheckPayPasswordLockedDto checkPayPasswordLockedDto = userPayPasswordService
        .CheckPayPasswordLockedTimes(uid);
    if (checkPayPasswordLockedDto.getLockedNum() >= 3) {
      throw new AppException(SecurityCodeConst.PAY_ACCOUNT_LOCK);
    }
    // 验证支付密码
    if (!CryptoUtils
        .verify(user.getPayPassword(), MD5Util.genMD5Code(payPasswod), user.getPaySalt())) {
      userPayPasswordService.addLockTimes(user.getUid());
      CheckPayPasswordLockedDto lockedTimes = userPayPasswordService
          .CheckPayPasswordLockedTimes(user.getUid());

      JSONObject json = new JSONObject();
      json.put("num", lockedTimes.getLockedNum());

      throw new AppException(UserCodeConst.PAY_PASSWORD_ERROR, "", json,
          lockedTimes.getLockedNum().toString());

    } else {
      userPayPasswordService.lockPayNumZero(uid);
    }

  }

  @Override
  public CheckLoginLockedDto LoginPasswordLockNum(Integer uid) {

    // 判断account类型
    CheckLoginLockedDto lockTimes = null;
    // 当account是email类型时

    lockTimes = userService.checkLoginLockTimes(uid);

    return lockTimes;
  }

  @Override
  public void updateUserInfo(int uid, String name) {

    User user = userService.getUserByUid(uid);
    if (null == user) {
      throw new AppException(UserCodeConst.NO_REGISTER);
    }
    user.setFullname(name);
    user.setUid(uid);
    user.setUpdateDate(new Date());
    userService.updateByPrimaryKeySelective(user);

  }

  @Override
  public UserSimpleInfoDto getUserInfoByUid(Integer uid) {
    EnvironmentEnum systemEnvironMent = environment.getSystemEnvironMent();

    if (uid == null) {
      log.info("getUserInfoByUid facade获取的uid为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }

    User user = userService.getUserByUid(uid);

    String email = user.getEmail();
    String mobile = user.getMobile();
    String fullname = user.getFullname();
    String nickname = user.getNickname();
    AuthLevel authLevel = user.getAuthLevel();

    UserSimpleInfoDto userSimpleInfoDto = new UserSimpleInfoDto();
    if (EnvironmentEnum.CHINA.equals(systemEnvironMent)) {
      userSimpleInfoDto.setUid(uid);
      userSimpleInfoDto.setUserAccount(mobile);
      userSimpleInfoDto.setFullName(fullname);
      userSimpleInfoDto.setNickName(nickname);
      userSimpleInfoDto.setBrokerId(user.getBrokerId());
      userSimpleInfoDto.setAuthLevel(authLevel);
    }

    if (EnvironmentEnum.US.equals(systemEnvironMent)) {
      userSimpleInfoDto.setUid(uid);
      userSimpleInfoDto.setUserAccount(email);
      userSimpleInfoDto.setFullName(fullname);
      userSimpleInfoDto.setNickName(nickname);
      userSimpleInfoDto.setBrokerId(user.getBrokerId());
      userSimpleInfoDto.setAuthLevel(authLevel);
    }

    return userSimpleInfoDto;

  }

  @Override
  @Transactional
  public User createUser(String email, String phone, String password, String payPwd,
      Integer invitId, String nickname,
      Integer brokerId) {
    User user = userService
        .registerUser(email, phone, password, payPwd, invitId, nickname, brokerId);
    return user;
  }

  @Override
  public void checkPayPassword(String userName, String payPassword) {
    User user = null;
    if (environment.equals(EnvironmentEnum.CHINA)) {
      user = userService.getUserByPhone(userName);
    } else {
      user = userService.getUserByEmail(userName);
    }

    if (StringUtils.isEmpty(payPassword)) {
      log.info("登录密码为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    payPassword = ConstantUtil.charConvert(payPassword);

    if (user == null) {
      throw new AppException(UserCodeConst.NO_REGISTER);
    }

    if (!CryptoUtils
        .verify(user.getPayPassword(), MD5Util.genMD5Code(payPassword), user.getPaySalt())) {
      User userTemp = new User();
      userTemp.setUid(user.getUid());
      userTemp.setUpdateDate(new Date());
      userTemp.setLockNum((byte) (user.getLockNum() + 1));
      userService.updateByPrimaryKeySelective(userTemp);
      CheckLoginLockedDto loginLockTimes = userService.checkLoginLockTimes(user.getUid());
      JSONObject json = new JSONObject();
      json.put("num", loginLockTimes.getLockedNum());
      throw new AppException(UserCodeConst.LOGIN_ERROR, "", json);
    }
  }

  @Override
  public void checkLoginPassword(String userName, String loginPasswod) {
    User user = null;

    user = userService.getUserByAccount(userName);
    if (StringUtils.isEmpty(loginPasswod)) {
      log.info("登录密码为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    loginPasswod = ConstantUtil.charConvert(loginPasswod);

    if (user == null) {
      throw new AppException(UserCodeConst.NO_REGISTER);
    }

    if (!CryptoUtils
        .verify(user.getLoginPassword(), MD5Util.genMD5Code(loginPasswod), user.getLoginSalt())) {
      User userTemp = new User();
      userTemp.setUid(user.getUid());
      userTemp.setUpdateDate(new Date());
      userTemp.setLockNum((byte) (user.getLockNum() + 1));
      userService.updateByPrimaryKeySelective(userTemp);
      // 再次检查锁定次数是否超过最大限制
      CheckLoginLockedDto loginLockTimes = userService.checkLoginLockTimes(user.getUid());
      JSONObject json = new JSONObject();
      json.put("num", loginLockTimes.getLockedNum());
      throw new AppException(UserCodeConst.LOGIN_PASSWORD_ERROR, "", json);
    }

  }

  @Override
  public User getUser(String accountNo) {
    return userService.getUserByAccount(accountNo);
  }

  @Override
  public void updateAuthStatus(Integer uid, AuthLevel status) {
    User record = new User();
    record.setUid(uid);
    record.setAuthLevel(status);
    record.setUpdateDate(new Date());
    userService.updateByPrimaryKeySelective(record);
  }

  @Override
  public User getUser(Integer uid) {
    return userService.getUserByUid(uid);
  }

  @Override
  public void checkUserGoogleCode(Integer uid, String email, String code) {
    UserGoogleCodeConfig userGoogleCodeConfig = userGoogleCodeConfigService
        .selectUserGoogleCodeConfigByUid(uid);
    if (null != userGoogleCodeConfig && UserGoogleCodeFlagType.ON
        .equals(userGoogleCodeConfig.getFlag())) {
      if (Strings.isNullOrEmpty(code)) {
        throw new AppException(UserCodeConst.GOOGLE_CODE_ERROR);
      }
      GoogleAuthenticator gAuth = new GoogleAuthenticator();
      credentialRepositoryMock.setFlag("DB");
      gAuth.setCredentialRepository(credentialRepositoryMock);
      boolean isCodeValid = gAuth.authorizeUser(email, Integer.parseInt(code));
      if (!isCodeValid) {
        throw new AppException(UserCodeConst.GOOGLE_CODE_ERROR);
      }
    }
  }

  @Transactional
  @Override
  public User reRegistrationUserCreate(String email, String phone, String password,
      String loginsalt, Integer invitId, String nickname, String ip) {

    User user = userService.preRegisterUser(email, phone, password, loginsalt, invitId, nickname);

    /**调用云交易所接口*/
    CreateUserReq createUserReq = new CreateUserReq();
    createUserReq.setEmail(user.getEmail());
    createUserReq.setPhone(user.getMobile());
    createUserReq.setBrokerUid(user.getUid().longValue());
    createUserReq.setIp(ip);
    createUserReq.setNanoTime(System.currentTimeMillis());
    UserData userData = cloudApiService.activateNewUser(createUserReq);

    return user;
  }

}
