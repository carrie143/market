package com.gop.code.consts;

/**
 * 用户信息之类
 * 
 * @author wangyang
 *
 */
public class UserCodeConst {
	//已绑定邮箱
	public static final String EXIT_EMAIL = "104134";
	
	public static final String MESSAGE_SEND_FAILED = "104133";
	//已绑定手机号
	public static final String EXIT_TELPHONE = "104132";
	// 数据库更新失败
	public static final String UPDATE_FAILED = "104131";
	// 已注册
	public static final String HAS_REGISTER = "104100";
	// 未注册
	public static final String NO_REGISTER = "104102";
	// 登录错误
	public static final String LOGIN_ERROR = "104103";
	// 登录密码不合法
	public static final String LOGIN_PASSWORD_VALID_ERROR = "104104";
	// 两次登录密码不一致
	public static final String LOGIN_PASSWORD_NO_MATCH = "104105";

	// 手机号格式错误
	public static final String PHONE_FORMAT_ERROR = "104106";
	// 手机号不支持
	public static final String PHONE_NOT_SUPPORT = "104107";

	// 支付密码错误
	public static final String PAY_PASSWORD_ERROR = "104108";

	// 未实名认证
	public static final String NO_CERTIFICATION_LEVEL1 = "104109";
	
	public static final String NO_CERTIFICATION_LEVEL2 = "104118";
	
	public static final String CERTIFICATION_TO_MANNY = "104117";
	public static final String HAS_CERTIFICATION = "104116";
	// 实名认证不匹配
	public static final String CERTIFICATION_NO_MATCH = "104110";
	// 身份证错误
	public static final String IDCARD_INVALID = "104111";

	// email 格式错误
	public static final String EMAIL_FORMAT_ERROR = "104112";

	// 支付密码格式错误
	public static final String PAY_PASSWORD_VALID_ERROR = "104113";

	// 两次输入支付密码不一致
	public static final String PAY_PASSWORD_NO_MATCH = "104115";
	// 登录密码错误
	public static final String LOGIN_PASSWORD_ERROR = "104114";
	
	//聚币账户不存在
	public static final String JUBI_USER_NOT_EXIST = "104119";
	
	//聚币账户已在OurDax注册
	public static final String JUBI_USER_HAS_REGISTER = "104120";
	
	//谷歌验证码错误
	public static final String GOOGLE_CODE_ERROR = "104121";
	
	// 已绑定
	public static final String HAS_BINGDING = "104122";
	
	// 存在支付密码,无法初始化
	public static final String  PAY_PASSWORD_INIT_ERROR= "104123";
	
	//错误的链接
	public static final String  GET_PRE_REGISTRATION_ERROR= "104124";
	
	//注册操作过于频繁
	public static final String USER_REGISTER_TOO_FREQUENTLY = "104125";

	//链接超时
	public static final String  PRE_REGISTRATION_LINK_TIMEOUT= "104126";
	
	//添加转出地址不得超过五个
	public static final String ADD_WITHDRAW_COIN_ADDRESS_LIMIT = "104127";
	
	//用户uid不存在
	public static final String USER_ID_NOT_EXIST = "104128";

	//支付密码错误10次以上 被锁定
	public static final String LOCKED_USER_ERROR = "104129";

	//邮箱不存在
	public static final String EMAIL_NOT_EXIST = "104130";
}
