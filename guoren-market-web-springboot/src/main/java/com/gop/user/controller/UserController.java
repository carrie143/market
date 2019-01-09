package com.gop.user.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.gop.activity.service.InviteActivityConfigService;
import com.gop.activity.service.InviteUserInfoService;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.IdentifyingCodeConst;
import com.gop.code.consts.SecurityCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.common.GetCountyAndCityByIPService;
import com.gop.common.IdentifyingCodeService;
import com.gop.common.SmsMessageService;
import com.gop.conetxt.EnvironmentContxt;
import com.gop.domain.Broker;
import com.gop.domain.InviteUserInfo;
import com.gop.domain.User;
import com.gop.domain.UserLoginLog;
import com.gop.domain.UserPreRegistrationPool;
import com.gop.domain.enums.InviteActivityConfigStatus;
import com.gop.exception.AppException;
import com.gop.sms.dto.VerifyCodeDto;
import com.gop.sms.service.MessageGenerator;
import com.gop.user.dto.CheckLoginLockedDto;
import com.gop.user.dto.UserDto;
import com.gop.user.dto.UserSimpleInfoDto;
import com.gop.user.facade.UserFacade;
import com.gop.user.service.UserService;
import com.gop.util.ConstantUtil;
import com.gop.util.EmailVerify;
import com.gop.util.MessageUtil;
import com.gop.util.PasswordUtil;
import com.gop.util.PhoneVerify;
import com.gop.util.TokenHelper;
import com.gop.web.base.annotation.AuthForHeader;
import com.gop.web.base.auth.AuthContext;
import com.gop.web.base.auth.annotation.Strategy;
import com.gop.web.base.auth.annotation.Strategys;

import lombok.extern.slf4j.Slf4j;

@RestController("demosticUserController")
@RequestMapping("/user")
@Slf4j
public class UserController {

  @Autowired
  @Qualifier("userService")
  private UserService userService;
  @Autowired
  private TokenHelper tokenHelper;

  @Autowired
  private UserFacade userFacade;

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private SmsMessageService smsMessageService;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Value("${email.urlDomain}")
  private String urlDomain;


  private long expireTime = 15;

  private final String randomCodePrix = "RandomCode";

  private final String ipPrix = "ip:";
  @Autowired
  @Qualifier("verifyCodeMessageGenerator")
  private MessageGenerator<VerifyCodeDto> verifyCodeMessageGenerator;
  @Autowired
  EnvironmentContxt environmentContxt;


  @Autowired
  @Qualifier("getCountyAndCityByIPServiceImpl")
  private GetCountyAndCityByIPService getCountyAndCityByIPService;

  @Autowired
  private InviteUserInfoService inviteUserInfoService;

  @Autowired
  private InviteActivityConfigService inviteActivityConfigService;

  public static final Integer USER_HAS_LOCKED = 1;

  public static final Integer USER_UNLOCKED = 0;

  public static final Integer UER_MAX_LOCK_TIMES = 10;// 登录及修改密码共用

  public static final String LOGIN_SESSION_ID = "loginsession";
  public static final Executor executor = Executors.newSingleThreadExecutor();
	@Autowired
	@Qualifier("IdentifyingCodeServiceImpl")
  private IdentifyingCodeService identifyingCodeService;
  @RequestMapping(value="/sendServiceCode",method=RequestMethod.GET)
	public  String getServiceCode(@RequestParam("telPhone") String telPhone) {
	  try {
		    MessageUtil messageUtil=new MessageUtil();
		    String serviceCode=messageUtil.getRandom();
		    String message1="【Bingo社区】你的验证码是：";
		    String message2="，在5分钟内有效，请勿泄露。如非本人操作，请忽略此短信。谢谢!";
		    String msg=message1+serviceCode+message2;
			String  result=	messageUtil.sendSMS(telPhone, msg);
			String[] arrstr=result.split(",");
			if(arrstr.length>1)
			{
				if( arrstr[1].equals("0"))
				{
					System.out.println("提交成功，返回值："+arrstr[1]);
					//将验证码存入redis
					String key = Joiner.on(":").join(telPhone, "code");
			        Boolean flag=identifyingCodeService.saveCode(serviceCode, key, 900, 60);
				}
				else
				{
					System.out.println("提交失败，错误码：" + arrstr[1]);
				}
			}
			else
			{
				System.out.println("提交异常，返回值：" + result);
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "hello";
	}
  //存储手机号码和验证码到redis中，
  //或者调用identifyingCodeServiceImpl.saveCode(value,key,900,60)
  @RequestMapping(value="/saveServiceCode",method=RequestMethod.GET)
 	public String saveServiceCode(@RequestParam("key") String key,@RequestParam("value") String value) {
	  ValueOperations<String, String> ops = redisTemplate.opsForValue();
	  String queryKey = Joiner.on(":").join(key, "code");
      ops.set(queryKey, value, 300, TimeUnit.SECONDS);//5分钟过期
      log.info("存储到reids的key:{},value:{}", queryKey, value);
      return key;
 	}
  // 注册
  @RequestMapping(value = "/phone-register", method = RequestMethod.POST)
  @Strategys(strategys = @Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})"))
  public JSONObject phoneRegister(@AuthForHeader AuthContext authContext,
      @RequestBody UserDto userDto) {
    String userAccount = authContext.getUserAccount();
    if (Strings.isNullOrEmpty(userAccount)) {
      log.info("无效的用户账户地址");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    // 校验userDto参数
    if (userDto == null) {
      log.info("{}:userDto为null", userAccount);
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }

    String loginPassword = userDto.getLoginPassword();
    String payPassword = userDto.getPayPassword();
    Integer invitId = userDto.getInvitId();
    String nickname = userDto.getNickname();

    if (StringUtils.isEmpty(loginPassword)) {
      log.info("{}:loginPassword为null", userAccount);
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    if (loginPassword.contains(" ")) {
      log.info("{}:paypassword为null", userAccount);
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    if (payPassword.contains(" ")) {
      log.info("{}:paypassword为null", userAccount);
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    if (StringUtils.isEmpty(payPassword)) {
      log.info("{}:paypassword为null", userAccount);
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }

    // 校验登录密码及支付密码
    loginPassword = ConstantUtil.charConvert(loginPassword);
    payPassword = ConstantUtil.charConvert(payPassword);

    if (loginPassword.length() < 6 || loginPassword.length() > 20) {
      throw new AppException(UserCodeConst.LOGIN_PASSWORD_VALID_ERROR);
    }

    if (!PasswordUtil.checkPasswordFormat(loginPassword)) {
      throw new AppException(UserCodeConst.LOGIN_PASSWORD_VALID_ERROR);
    }

    if (payPassword.length() < 8 || payPassword.length() > 20) {
      throw new AppException(UserCodeConst.PAY_PASSWORD_VALID_ERROR);
    }

    if (!PasswordUtil.checkPasswordFormat(payPassword)) {
      throw new AppException(UserCodeConst.PAY_PASSWORD_VALID_ERROR);
    }

    if (!PhoneVerify.validMobileNumber(userAccount)) {
      throw new AppException(UserCodeConst.PHONE_FORMAT_ERROR);
    }

    Boolean isReg = userService.isPhoneRegister(userAccount);
    if (isReg) {
      throw new AppException(UserCodeConst.HAS_REGISTER);
    }

    User user = userFacade
        .createUser(null, userAccount, loginPassword, payPassword, invitId, nickname, 0);

    Integer uid = user.getUid();

    // 注册成功 存储到session中

    JSONObject json = new JSONObject();
    String token = tokenHelper.generateToken(uid);
    json.put("token", token);
    return json;
  }

  // 验证手机号码是否已经注册

  @RequestMapping(value = "/phone-register-valid", method = RequestMethod.GET)
  public void isPhoneRegister(@RequestParam("phone") String phone) {
    boolean isRegistered = false;

    isRegistered = userService.isPhoneRegister(phone);

    if (isRegistered) {
      throw new AppException(UserCodeConst.HAS_REGISTER);
    } else {

      throw new AppException(UserCodeConst.NO_REGISTER);

    }
  }

  // 注册
  @Strategys(strategys = @Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})"))
  @RequestMapping(value = "/email-register", method = RequestMethod.POST)
  public JSONObject emailRegister(@AuthForHeader AuthContext authContext,
      @Valid @RequestBody UserDto userDto) {

    String userAccount = authContext.getUserAccount();

    // 校验userDto参数
    if (userDto == null) {
      log.error("userDto为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }

    String loginPassword = userDto.getLoginPassword();
    String payPassword = userDto.getPayPassword();
    Integer invitId = userDto.getInvitId();
    String nickname = userDto.getNickname();
    if (StringUtils.isEmpty(loginPassword)) {
      log.error("loginPassword为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    if (StringUtils.isEmpty(payPassword)) {
      log.error("paypassword为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    if (loginPassword.contains(" ")) {
      log.info("{}:paypassword为null", userAccount);
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    if (payPassword.contains(" ")) {
      log.info("{}:paypassword为null", userAccount);
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    // 校验登录密码及支付密码
    loginPassword = ConstantUtil.charConvert(loginPassword);
    payPassword = ConstantUtil.charConvert(payPassword);

    if (loginPassword.length() < 6 || loginPassword.length() > 20) {
      throw new AppException(UserCodeConst.LOGIN_PASSWORD_VALID_ERROR);
    }

    if (!PasswordUtil.checkPasswordFormat(loginPassword)) {
      throw new AppException(UserCodeConst.LOGIN_PASSWORD_VALID_ERROR);
    }

    if (payPassword.length() < 8 || payPassword.length() > 20) {
      throw new AppException(UserCodeConst.PAY_PASSWORD_VALID_ERROR);
    }

    if (!PasswordUtil.checkPasswordFormat(payPassword)) {
      throw new AppException(UserCodeConst.PAY_PASSWORD_VALID_ERROR);
    }

    if (!EmailVerify.validEmailNumber(userAccount)) {
      throw new AppException(UserCodeConst.EMAIL_FORMAT_ERROR);
    }

    Boolean isReg = userService.isMailRegister(userAccount);
    if (isReg) {
      throw new AppException(UserCodeConst.HAS_REGISTER);
    }

    userFacade
        .createUser(userAccount, null, loginPassword, payPassword, invitId, nickname, 0);
    User userByEmail = userService.getUserByEmail(userAccount);
    Integer uid = userByEmail.getUid();

    // 注册成功之后记录ip
    UserLoginLog userLoginLog = new UserLoginLog();
    userLoginLog.setUid(uid);
    final String ip = request.getHeader("X-Real-IP");
    if (ip == null || ip.length() == 0) {

      log.info("无法从用户uid:" + uid + "请求header中的X-Real-IP获取真实IP地址");
      userLoginLog.setIpCountry("Unknow");
      userLoginLog.setIpCity("Unknow");
      userLoginLog.setIpAddress("Unknow");
      userService.recordUserLogin(userLoginLog);
    } else {
      CompletableFuture.runAsync(() -> {
        Map<String, String> map = null;
        try {
          map = getCountyAndCityByIPService.getCountyAndCityByIp(ip);
        } catch (Exception e) {
          userLoginLog.setIpCountry("Unknow");
          userLoginLog.setIpCity("Unknow");
        }
        if (null != map) {
          userLoginLog.setIpCountry(map.get("country"));
          userLoginLog.setIpCity(map.get("city"));
        }
        userLoginLog.setIpAddress(ip);
        userService.recordUserLogin(userLoginLog);
      });
    }

    JSONObject json = new JSONObject();
    String token = tokenHelper.generateToken(uid);
    json.put("token", token);
    return json;

  }

  // 预注册
  @RequestMapping(value = "/email-pre-register", method = RequestMethod.POST)
  public void emailPreRegister(@AuthForHeader AuthContext authContext,
       @RequestBody UserDto userDto) {
    String userAccount = authContext.getUserAccount();
    // 校验userDto参数
    if (userDto == null) {
      log.error("userDto为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    String ip = request.getHeader("X-Real-IP");
    if (ip == null || ip.length() == 0) {
      ip = "Unknow";
    }
    if (redisTemplate.hasKey(ipPrix + ip)) {
      int count = Integer.valueOf(redisTemplate.opsForValue().get(ipPrix + ip));
      if (count > 4) {
        throw new AppException(UserCodeConst.USER_REGISTER_TOO_FREQUENTLY);
      }
      redisTemplate.boundValueOps(ipPrix + ip).increment(1);
    } else {
      redisTemplate.opsForValue().set(ipPrix + ip, "1", 24, TimeUnit.HOURS);
    }

    String loginPassword = userDto.getLoginPassword();
    Integer invitId = userDto.getInvitId();
    String nickname = userDto.getNickname();
    String lang = userDto.getLang();

    if (StringUtils.isEmpty(loginPassword)) {
      log.error("loginPassword为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }

    if (loginPassword.contains(" ")) {
      log.info("{}:paypassword为null", userAccount);
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    if (Strings.isNullOrEmpty(lang)) {
      lang = "";
    }

    // 校验登录密码及支付密码
    loginPassword = ConstantUtil.charConvert(loginPassword);

    if (loginPassword.length() < 6 || loginPassword.length() > 20) {
      throw new AppException(UserCodeConst.LOGIN_PASSWORD_VALID_ERROR);
    }

    if (!PasswordUtil.checkPasswordFormat(loginPassword)) {
      throw new AppException(UserCodeConst.LOGIN_PASSWORD_VALID_ERROR);
    }

    if (!EmailVerify.validEmailNumber(userAccount)) {
      throw new AppException(UserCodeConst.EMAIL_FORMAT_ERROR);
    }

    Boolean isReg = userService.isMailRegister(userAccount);
    if (isReg) {
      throw new AppException(UserCodeConst.HAS_REGISTER);
    }
    UserPreRegistrationPool userPreRegistration = userService
        .addUserPerRegistrationInformation(userAccount, null, loginPassword, invitId, nickname,
            0);
    String preRegistCount = userPreRegistration.getEmail();
    String randomCode = UUID.randomUUID().toString().trim().replaceAll("-", "");
    redisTemplate.opsForValue()
        .set(randomCodePrix + ":" + preRegistCount, randomCode, expireTime, TimeUnit.MINUTES);

    String url =
        urlDomain + lang + "/active.html?userPreRegistrationId=" + userPreRegistration.getId()
            + "&randomCode=" + randomCode;

    smsMessageService.sendEmailMessage(userAccount, url, true, "registerActiveMessage.ftl");
  }


  //激活注册账号
  @RequestMapping(value = "/email-activate", method = RequestMethod.GET)
  public void preRegisterVerification(@AuthForHeader AuthContext authContext,
      @RequestParam("userPreRegistrationId") Integer userPreRegistrationId,
      @RequestParam("randomCode") String randomCode) {

    UserPreRegistrationPool userPreRegistration = userService
        .getUserPerRegistrationInformationById(userPreRegistrationId);

    String redisRandomKey = redisTemplate.opsForValue()
        .get(randomCodePrix + ":" + userPreRegistration.getEmail());
    //校验用户randomKey
    if (Strings.isNullOrEmpty(redisRandomKey)) {
      throw new AppException(UserCodeConst.PRE_REGISTRATION_LINK_TIMEOUT);
    }
    if (!randomCode.equals(redisRandomKey)) {
      throw new AppException(UserCodeConst.PRE_REGISTRATION_LINK_TIMEOUT);
    }

    String userAccount = userPreRegistration.getEmail();

    String loginPassword = userPreRegistration.getLoginPassword();
    Integer invitId = userPreRegistration.getInviteUid();
    String nickname = userPreRegistration.getNickname();
    String loginsalt = userPreRegistration.getLoginSalt();

    if (StringUtils.isEmpty(loginPassword)) {
      log.error("loginPassword为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }

    if (loginPassword.contains(" ")) {
      log.info("{}:paypassword为null", userAccount);
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }

    Boolean isReg = userService.isMailRegister(userAccount);
    if (isReg) {
      throw new AppException(UserCodeConst.HAS_REGISTER);
    }

    final String ip = request.getHeader("X-Real-IP");
    userFacade
        .reRegistrationUserCreate(userAccount, null, loginPassword, loginsalt, invitId, nickname,
            ip);
    User userByEmail = userService.getUserByEmail(userAccount);
    Integer uid = userByEmail.getUid();

    // 注册成功之后记录ip
    UserLoginLog userLoginLog = new UserLoginLog();
    userLoginLog.setUid(uid);

    userService.updateCreateIpByUid(uid, Strings.isNullOrEmpty(ip) ? "Unknow" : ip);

    if (ip == null || ip.length() == 0) {

      log.info("无法从用户uid:" + uid + "请求header中的X-Real-IP获取真实IP地址");
      userLoginLog.setIpCountry("Unknow");
      userLoginLog.setIpCity("Unknow");
      userLoginLog.setIpAddress("Unknow");
      userService.recordUserLogin(userLoginLog);
    } else {
      CompletableFuture.runAsync(() -> {
        Map<String, String> map = null;
        try {
          map = getCountyAndCityByIPService.getCountyAndCityByIp(ip);
        } catch (Exception e) {
          userLoginLog.setIpCountry("Unknow");
          userLoginLog.setIpCity("Unknow");
        }
        if (null != map) {
          userLoginLog.setIpCountry(map.get("country"));
          userLoginLog.setIpCity(map.get("city"));
        }
        userLoginLog.setIpAddress(ip);
        userService.recordUserLogin(userLoginLog);
      });
    }


  }

  // 验证邮箱是否已经注册
  @RequestMapping(value = "/email-register-valid", method = RequestMethod.GET)
  public void isEmailRegister(@RequestParam("email") String email) {
    boolean isRegistered = false;
    // 验证邮箱是否已注册service
    log.info("email={}", email);

    isRegistered = userService.isMailRegister(email);

    if (isRegistered) {
      // 邮箱已注册
      log.info("邮箱已注册:email={}", email);
      throw new AppException(UserCodeConst.HAS_REGISTER);
    } else {
      // 邮箱没注册
      log.info("邮箱没有注册:email={}", email);

    }
  }

  // 登录
  @RequestMapping(value = "/login", method = RequestMethod.GET)

  @Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginPasswordWithLoginStrategy'}})"))
  public UserSimpleInfoDto login(@AuthForHeader AuthContext authContext) {
    User user = null;

    String userAccount = authContext.getUserAccount();

    user = userService.getUserByAccount(userAccount);
    // update user表 锁定记录清空
    User userNew = new User();
    userNew.setUid(user.getUid());
    userNew.setLockNum((byte) 0);
    userNew.setUpdateDate(new Date());
    userService.updateByPrimaryKeySelective(userNew);

    // 登录成功之后记录用户登录ip与时间
    UserLoginLog userLoginLog = new UserLoginLog();
    userLoginLog.setUid(user.getUid());
    final String ip = request.getHeader("X-Real-IP");
    if (ip == null || ip.length() == 0) {

      log.info("无法从用户uid:" + userLoginLog.getUid() + "请求header中的X-Real-IP获取真实IP地址");
      userLoginLog.setIpCountry("Unknow");
      userLoginLog.setIpCity("Unknow");
      userLoginLog.setIpAddress("Unknow");
      userService.recordUserLogin(userLoginLog);
    } else {
      CompletableFuture.runAsync(() -> {
        Map<String, String> map = null;
        try {
          map = getCountyAndCityByIPService.getCountyAndCityByIp(ip);
        } catch (Exception e) {
          userLoginLog.setIpCountry("Unknow");
          userLoginLog.setIpCity("Unknow");
        }
        if (null != map) {
          userLoginLog.setIpCountry(map.get("country"));
          userLoginLog.setIpCity(map.get("city"));
        }
        userLoginLog.setIpAddress(ip);
        userService.recordUserLogin(userLoginLog);
      });
    }
    // 返回用户信息
    UserSimpleInfoDto userSimpleInfoDto = new UserSimpleInfoDto();

    userSimpleInfoDto.setUserAccount(userAccount);

    userSimpleInfoDto.setFullName(user.getFullname());
    userSimpleInfoDto.setNickName(user.getNickname());
    userSimpleInfoDto.setUid(user.getUid());
    userSimpleInfoDto.setBrokerId(user.getBrokerId());
    String token = tokenHelper.generateToken(user.getUid());
    userSimpleInfoDto.setToken(token);
    return userSimpleInfoDto;
  }

  // 手机号登录
  @RequestMapping(value = "/phone-login", method = RequestMethod.GET)
  @Strategys(strategys = @Strategy(authStrategy = "exe({{'checkLoginPasswordWithPhoneLoginStrategy'}})"))
  public UserSimpleInfoDto phoneLogin(@AuthForHeader AuthContext authContext) {
    User user = null;

    String userAccount = authContext.getUserAccount();
    System.out.print(userAccount);
    user = userService.getUserByPhone(userAccount);
    // update user表 锁定记录清空
    User userNew = new User();
    userNew.setUid(user.getUid());
    userNew.setLockNum((byte) 0);
    userNew.setUpdateDate(new Date());
    userService.updateByPrimaryKeySelective(userNew);

    // 登录成功之后记录用户登录ip与时间
    UserLoginLog userLoginLog = new UserLoginLog();
    userLoginLog.setUid(user.getUid());
    final String ip = request.getHeader("X-Real-IP");
    if (ip == null || ip.length() == 0) {

      log.info("无法从用户uid:" + userLoginLog.getUid() + "请求header中的X-Real-IP获取真实IP地址");
      userLoginLog.setIpCountry("Unknow");
      userLoginLog.setIpCity("Unknow");
      userLoginLog.setIpAddress("Unknow");
      userService.recordUserLogin(userLoginLog);
    } else {
      CompletableFuture.runAsync(() -> {
        Map<String, String> map = null;
        try {
          map = getCountyAndCityByIPService.getCountyAndCityByIp(ip);
        } catch (Exception e) {
          userLoginLog.setIpCountry("Unknow");
          userLoginLog.setIpCity("Unknow");
        }
        if (null != map) {
          userLoginLog.setIpCountry(map.get("country"));
          userLoginLog.setIpCity(map.get("city"));
        }
        userLoginLog.setIpAddress(ip);
        userService.recordUserLogin(userLoginLog);
      });
    }
    // 返回用户信息
    UserSimpleInfoDto userSimpleInfoDto = new UserSimpleInfoDto();

    userSimpleInfoDto.setUserAccount(userAccount);

    userSimpleInfoDto.setFullName(user.getFullname());
    userSimpleInfoDto.setNickName(user.getNickname());
    userSimpleInfoDto.setUid(user.getUid());
    userSimpleInfoDto.setBrokerId(user.getBrokerId());
    String token = tokenHelper.generateToken(user.getUid());
    userSimpleInfoDto.setToken(token);
    return userSimpleInfoDto;
  }

  // 登出
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public void loginout(@AuthForHeader AuthContext authContext) {
    tokenHelper.removeToken(authContext.getToke());
    authContext.setLoginSession(null);
  }
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
  @Strategys(strategys = {
      @Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'},{'checkLoginStrategy','checkLoginPasswordStrategy','checkGoogleCodeStrategy'}})"),})
  @RequestMapping(value = "/login-password", method = RequestMethod.POST)
  public String changeloginPassword(@AuthForHeader AuthContext authContext,
      @RequestBody UserDto userDto) {

    String newPwd = userDto.getLoginPassword();

    User user = null;
    String account = authContext.getUserAccount();

    if (Strings.isNullOrEmpty(account)) {//已登陆用户修改密码,不传account-no，从session中获取id
      user = userService.getUserByUid(authContext.getLoginSession().getUserId());
    } else {//未登陆用户忘记密码，传account-no
      user = userService.getUserByEmail(account);//根据邮箱或手机号查询用户共用方法
    }

    if (user == null) {
      throw new AppException(UserCodeConst.NO_REGISTER);
    }

//    if (user.getLockNum() >= 10){
//      throw new AppException(UserCodeConst.LOCKED_USER_ERROR, "用户输入密码错误超过10次，已被锁定");
//    }

    // 验证传入参数 与 处理参数
    if (StringUtils.isEmpty(newPwd)) {
      log.info("新登录密码为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    if (newPwd.contains(" ")) {
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }

    newPwd = ConstantUtil.charConvert(newPwd);
    // 登录成功：校验新密码格式；修改登录密码
    if (newPwd.length() < 6 || newPwd.length() > 20) {
      throw new AppException(UserCodeConst.LOGIN_PASSWORD_VALID_ERROR);
    }
    if (!PasswordUtil.checkPasswordFormat(newPwd)) {
      throw new AppException(UserCodeConst.LOGIN_PASSWORD_VALID_ERROR);
    }

    userService.updatePassword(newPwd, user.getUid());
    return "成功";
  }

  // 修改支付密码
  // "checkPayPasswordStregy",
  // "checkPassportStraegy"
  @RequestMapping(value = "/pay-password", method = RequestMethod.POST)
  @Strategys(strategys = {@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
      @Strategy(authStrategy = "exe({{'checkPayPasswordStregy'},{'checkPassportStraegy'}})"),
      @Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})"),
      @Strategy(authStrategy = "exe({{'checkGoogleCodeStrategy'}})")})
  public void changePayPassword(@RequestBody UserDto userDto,
      @AuthForHeader AuthContext authContext) {

    int uid = authContext.getLoginSession().getUserId();
    String newPayPwd = userDto.getPayPassword();
    // 验证传入参数 与 处理参数
    if (StringUtils.isEmpty(newPayPwd)) {
      log.info("新支付密码为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    if (newPayPwd.contains(" ")) {
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    newPayPwd = ConstantUtil.charConvert(newPayPwd);

    // 登录成功：校验新密码格式；修改登录密码

    if (newPayPwd.length() < 8 || newPayPwd.length() > 20) {
      throw new AppException(UserCodeConst.PAY_PASSWORD_VALID_ERROR);
    }
    if (!PasswordUtil.checkPasswordFormat(newPayPwd)) {
      throw new AppException(UserCodeConst.PAY_PASSWORD_VALID_ERROR);
    }

    // user表 md5 insert操作
    userService.updatePayPassword(newPayPwd, uid);
  }
  // 前端登录，提供输入账户后失焦查询该用户有无注册，及是否需要验证码及锁定次数

  @RequestMapping(value = "/need-captha", method = RequestMethod.GET)
  public void checkNeedCaptha(@RequestParam("userName") String userName) {

    User user = userFacade.getUser(userName);
    if (null == user) {
      throw new AppException(UserCodeConst.NO_REGISTER);
    }

    // 查询验证码锁定情况 小于3次正常返回；>=3次返回需要验证码code并返回相应次数
    CheckLoginLockedDto checkLoginLockedDto = userFacade.LoginPasswordLockNum(user.getUid());
    Integer lockedNum = checkLoginLockedDto.getLockedNum();

    // >=3次返回需要验证码code并返回相应次数
    if (lockedNum >= 3) {
      JSONObject json = new JSONObject();
      json.put("num", lockedNum);
      throw new AppException(SecurityCodeConst.NEED_VERIFICATION_CODE, "", json);

    }

  }

  @Strategys(strategys = { @Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
  @RequestMapping(value = "/freshen-token", method = RequestMethod.GET)
  public JSONObject freshentoken(@AuthForHeader AuthContext authContext) {
    String token = authContext.getToke();
    tokenHelper.validToken(token);
    JSONObject json = new JSONObject();
    json.put("token", token);
    return json;
  }

  // 用户绑定手机
  @Strategys(strategys = {@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
      @Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})")})
  @RequestMapping(value = "/cellphone-config", method = RequestMethod.GET)
  public void updatePhoneNumber(@AuthForHeader AuthContext authContext,
      @RequestParam("phone") String phoneNumber) {

    User user = userService.getUserByPhone(phoneNumber);
    if (user != null) {

      throw new AppException(UserCodeConst.HAS_BINGDING);
    }
    Integer uid = authContext.getLoginSession().getUserId();
    userService.updateUserphoneNumber(phoneNumber, uid);
  }

  // 用户设置昵称
  @Strategys(strategys = {@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})")})
  @RequestMapping(value = "/nickname-config", method = RequestMethod.GET)
  public void updateNickName(@AuthForHeader AuthContext authContext,
      @RequestParam("nickName") String nickNmae) {

    Integer uid = authContext.getLoginSession().getUserId();
    userService.updateUserNickName(nickNmae, uid);

  }

  //设置支付密码
  @Strategys(strategys = {@Strategy(authStrategy = "exe({{'checkLoginStrategy'}})"),
      @Strategy(authStrategy = "exe({{'checkServiceCodeStrategy'}})")})
  @RequestMapping(value = "/paypassword-init", method = RequestMethod.POST)
  public void savePayPasssword(@AuthForHeader AuthContext authContext,
      @RequestBody UserDto userDto) {
    Integer uid = authContext.getLoginSession().getUserId();
    User user = userFacade.getUser(uid);
    if (!Strings.isNullOrEmpty(user.getPayPassword())) {
      throw new AppException(UserCodeConst.PAY_PASSWORD_INIT_ERROR, "支付密码重复设置");
    }
    String payPwd = userDto.getPayPassword();
    // 验证传入参数 与 处理参数
    if (StringUtils.isEmpty(payPwd)) {
      log.info("新支付密码为null");
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    if (payPwd.contains(" ")) {
      throw new AppException(CommonCodeConst.FIELD_ERROR);
    }
    payPwd = ConstantUtil.charConvert(payPwd);

    // 登录成功：校验新密码格式；修改登录密码

    if (payPwd.length() < 8 || payPwd.length() > 20) {
      throw new AppException(UserCodeConst.PAY_PASSWORD_VALID_ERROR);
    }
    if (!PasswordUtil.checkPasswordFormat(payPwd)) {
      throw new AppException(UserCodeConst.PAY_PASSWORD_VALID_ERROR);
    }

    // user表 md5 insert操作
    userService.updatePayPassword(payPwd, uid);
  }


}
