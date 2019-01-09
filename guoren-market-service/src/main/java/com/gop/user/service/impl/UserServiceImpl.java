/*
 * 文件名：UserServiceImpl.java
 * 版权：Copyright by www.guoren.com
 * 描述：
 * 修改人：wangyang
 * 修改时间：2016年3月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gop.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.gop.domain.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.gop.code.consts.CommonCodeConst;
import com.gop.code.consts.UserCodeConst;
import com.gop.common.Environment;
import com.gop.common.Environment.EnvironmentEnum;
import com.gop.domain.enums.AuthLevel;
import com.gop.exception.AppException;
import com.gop.mapper.UserInfoMapper;
import com.gop.mapper.UserLoginLogMapper;
import com.gop.mapper.UserMapper;
import com.gop.mapper.UserPreRegistrationPoolMapper;
import com.gop.mode.vo.PageModel;
import com.gop.user.dto.CheckLoginLockedDto;
import com.gop.user.dto.UserSimpleInfoDto;
import com.gop.user.service.UserPayPasswordService;
import com.gop.user.service.UserService;
import com.gop.util.CryptoUtils;
import com.gop.util.MD5Util;

import lombok.extern.slf4j.Slf4j;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserPayPasswordService userPayPasswordService;

	@Autowired
	private Environment environment;

	@Autowired
	private UserLoginLogMapper userLoginMapper;

	@Autowired
	private UserPreRegistrationPoolMapper userPreRegistrationPoolMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public Boolean isPhoneRegister(String phone) {
		User user = null;
		try {
			user = userMapper.selectUserByLoginPhone(phone);
		} catch (Exception e) {
			log.error("根据手机号获取用户id异常", e);
			return null;
		}
		if (user != null)
			return true;
		else
			return false;
	}

	@Override
	public Boolean isMailRegister(String email) {
		User user = null;
		try {
			user = userMapper.selectUserByLoginEmail(email);
		} catch (Exception e) {
			log.error("根据邮箱获取用户id异常", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		if (user != null)
			return true;
		else
			return false;
	}

	@Transactional
	public User registerUser(String email, String phone, String password, String payPwd, Integer invitId,
			String nickname, Integer brokerId) {

		if (Strings.isNullOrEmpty(email) && Strings.isNullOrEmpty(phone)) {
			log.error("注册代码错误");
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		UserSimpleInfoDto userSimpleInfoDto = new UserSimpleInfoDto();
		String md5Pwd = MD5Util.genMD5Code(password);
		String md5PayPwd = MD5Util.genMD5Code(payPwd);
		String loginsalt = CryptoUtils.getSalt();
		String paysalt = CryptoUtils.getSalt();
		String pwd = CryptoUtils.getHash(md5Pwd, loginsalt);
		String payPassword = CryptoUtils.getHash(md5PayPwd, paysalt);

		Date currentDate = new Date();
		User user1 = new User();
		user1.setEmail(email);
		user1.setMobile(phone);
		user1.setLockNum((byte) 0);
		user1.setCreateDate(currentDate);
		user1.setUpdateDate(currentDate);
		user1.setLoginSalt(loginsalt);
		user1.setPaySalt(paysalt);
		user1.setLoginPassword(pwd);
		user1.setPayPassword(payPassword);
		user1.setInviteUid(invitId);
		user1.setNickname(nickname);
		user1.setAuthLevel(AuthLevel.LEVEL0);
		user1.setBrokerId(brokerId);
		try {
			userMapper.insertSelective(user1);
		} catch (Exception e1) {
			log.error("插入user表异常", e1);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		// 支付密码锁表初始化
		UserPayPassword record = new UserPayPassword();
		record.setCreateDate(currentDate);
		record.setLockNum(0);
		record.setUid(user1.getUid());
		try {
			userPayPasswordService.insertSelective(record);
		} catch (Exception e) {
			log.error("插入userPayPassword表异常,uid={},eMessage=" + e.getMessage(), user1.getUid(), e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		userSimpleInfoDto.setUserAccount(email);
		userSimpleInfoDto.setFullName(user1.getFullname());// 前端没有传入，fullname为null
		userSimpleInfoDto.setUid(user1.getUid());
		userSimpleInfoDto.setNickName(nickname);
		userSimpleInfoDto.setBrokerId(user1.getBrokerId());
		log.info(userSimpleInfoDto + "");
		return user1;

	}

	/**
	 * 根据手机号获取用户ID
	 */
	@Override
	public User getUserByPhone(String phone) {
		User user = null;
		try {
			user = userMapper.selectUserByLoginPhone(phone);
		} catch (Exception e) {
			log.error("根据手机号获取用户id异常", e);
			return null;
		}
		if (user == null) {
			return null;
		}
		return user;
	}

	/**
	 * 根据邮箱获取用户ID
	 */
	@Override
	public User getUserByEmail(String email) {
		User user = null;
		try {
			user = userMapper.selectUserByLoginEmail(email);
		} catch (Exception e) {
			log.error("根据邮箱获取用户id异常", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		if (user == null) {
			return null;
		}
		return user;
	}

	@Override
	public PageInfo<User> getBaseUserList(Integer uid,String account,String fullName, Date startDate,Date endDate,String orderBy, Integer pageNo,
			Integer pageSize) {
		String phone = null;
		String email = null;
		if (environment.getSystemEnvironMent().equals(EnvironmentEnum.CHINA)) {
			phone = account;
		} else {
			email = account;
		}
		PageHelper.startPage(pageNo, pageSize, true);
		PageHelper.orderBy(orderBy);
		return new PageInfo<>(userMapper.getUserListWithDate(uid, email,fullName,phone, startDate, endDate));
	}

	@Override
	public PageInfo<User> getUserList(Integer uid, Integer brokerId, String account, String name, Integer pageNo,
									  Integer pageSize) {
		String phone = null;
		String email = null;
		if (environment.getSystemEnvironMent().equals(EnvironmentEnum.CHINA)) {
			phone = account;
		} else {
			email = account;
		}
		PageHelper.startPage(pageNo, pageSize, true);
		PageHelper.orderBy("create_date desc");
		return new PageInfo<>(userMapper.getUserList(uid, brokerId, email, phone, name));
	}

	@Override
	@Transactional
	public CheckLoginLockedDto checkLoginLockTimes(Integer uid) {
		// 查询该用户表锁定次数
		User user = new User();

		user = getUserByUid(uid);

		if (user == null) {
			throw new AppException(UserCodeConst.NO_REGISTER);
		}

		// 根据查询的锁定次数返回相应的dto对象
		CheckLoginLockedDto checkLoginLockedDto = new CheckLoginLockedDto();

		Byte lockNum = user.getLockNum();

		Integer num = null;

		if (lockNum == 0) {
			num = 0;
			checkLoginLockedDto.setLockedNum(num);
			return checkLoginLockedDto;
		}
		if (lockNum >= 10) {
			num = 10;
			checkLoginLockedDto.setLockedNum(num);
			return checkLoginLockedDto;
		}

		// 最后剩下：锁定次数在0到10之间，涉及到次日锁定次数清零
		Date locktime = user.getUpdateDate();
		boolean flag = isDateNow(locktime);
		if (!flag) {
			User u = new User();
			u.setUid(user.getUid());
			u.setLockNum((byte) 0);// 次数清0
			u.setUpdateDate(new Date());
			try {
				userMapper.updateByPrimaryKeySelective(u);
			} catch (Exception e) {
				log.error("更新user表的用户" + user.getUid() + "的登录锁住状态时，抛出异常:", e);
				throw new AppException(CommonCodeConst.SERVICE_ERROR);
			}
			num = 0;

		} else {

			num = (int) lockNum;
		}

		checkLoginLockedDto.setLockedNum(num);
		return checkLoginLockedDto;

	}

	private boolean isDateNow(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = sdf.format(date);
		String date2 = sdf.format(new Date());
		if (date1.equals(date2)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据用户id查询用户
	 */
	@Override
	public User getUserByUid(Integer uid) {

		User user;
		try {
			user = userMapper.selectByPrimaryKey(uid);
		} catch (Exception e) {
			log.error("根据uid查询user信息异常,eMessage=" + e.getMessage(), e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		return user;
	}

	/**
	 * 修改支付密码
	 * 
	 * @author zhangxianglong
	 */
	@Transactional
	public void updatePayPassword(String newPwd, Integer userId) {

		String genMD5Code = MD5Util.genMD5Code(newPwd);
		User user = new User();
		String salt = CryptoUtils.getSalt();
		user.setPayPassword(CryptoUtils.getHash(genMD5Code, salt));
		user.setPaySalt(salt);
		user.setUpdateDate(new Date());
		user.setUid(userId);
		userPayPasswordService.lockPayNumZero(userId);
		userMapper.updateByPrimaryKeySelective(user);

	}

	/**
	 * 修改登录密码
	 * 
	 * Description: <br/>
	 * 
	 * @Author：zhangxianglong
	 * @date 2016年3月19日下午12:27:56
	 * @param password
	 * @param userId
	 * @return
	 * @see
	 */
	public void updatePassword(String password, int userId) {
		User user = new User();
		user.setUid(userId);
		try {
			log.info("修改登录密码service开始");
			String md5Password = MD5Util.genMD5Code(password);
			String salt = CryptoUtils.getSalt();
			String hashPassword = CryptoUtils.getHash(md5Password, salt);

			user.setLoginSalt(salt);
			user.setLoginPassword(hashPassword);
			user.setUpdateDate(new Date());
			user.setLockNum((byte) 0);
			userMapper.updateByPrimaryKeySelective(user);
			log.info("修改登录密码service结束");
		} catch (Exception e) {
			log.error("修改登录密码用户userid:" + userId + " 失败信息：", e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

	}

	@Override
	public void updateByPrimaryKeySelective(User userTemp) {
		userMapper.updateByPrimaryKeySelective(userTemp);

	}

	@Override
	public UserInfo getUserInfo(Integer uid) {
		// TODO Auto-generated method stub
		return userInfoMapper.selectByPrimaryKey(uid);
	}

	@Override
	public User getUserByAccount(String account) {
		if (environment.getSystemEnvironMent().equals(EnvironmentEnum.CHINA)) {
			return getUserByPhone(account);
		} else {
			return getUserByEmail(account);
		}
	}

	@Override
	public void recordUserLogin(UserLoginLog record) {
		try {
			int selective = userLoginMapper.insertSelective(record);
			log.info("用户uid:" + record.getUid() + "登录ip为:" + record.getIpAddress());
		} catch (Exception e) {
			log.error("记录用户登录日志失败");
			;
			throw new AppException(CommonCodeConst.SERVICE_ERROR, "用户登录记录失败");
		}
	}

	@Override
	public Boolean checkUpIpCountryAndCityIsSameAsLastTime(Integer uid, String country, String city) {

		UserLoginLog userLoginLog = userLoginMapper.selectLastLoginIpByUid(uid);
		if (userLoginLog.getIpCity().equals(city) && userLoginLog.getIpCountry().equals(country)) {
			return true;
		}
		return false;
	}

	@Override
	public PageModel<UserLoginLog> getUserLoginLogByUid(Integer uid, Integer pageNo, Integer pageSize) {

		PageHelper.orderBy("create_date desc");
		PageHelper.startPage(pageNo, pageSize);
		List<UserLoginLog> userLoginLogs = userLoginMapper.selectIpByUid(uid);
		PageModel<UserLoginLog> pageModel = new PageModel<>();
		if (null == userLoginLogs || userLoginLogs.isEmpty()) {
			return new PageModel<>();
		}
		Page<UserLoginLog> page = (Page<UserLoginLog>) userLoginLogs;
		pageModel.setPageNo(pageNo);
		pageModel.setPageNum(page.getPageNum());
		pageModel.setPageSize(pageSize);
		pageModel.setTotal(page.getTotal());
		pageModel.setList(userLoginLogs);
		return pageModel;
	}

	// 设置用户手机号码
	@Override
	public void updateUserphoneNumber(String phoneNumber, Integer uid) {

		userMapper.updateUserPhoneNumberByUid(phoneNumber, uid);

	}

	// 设置用户昵称
	@Override
	public void updateUserNickName(String nickNmae, Integer uid) {

		userMapper.updateUserNickNameByUid(nickNmae, uid);

	}

	@Override
	public UserPreRegistrationPool addUserPerRegistrationInformation(String email, String phone, String password,
			Integer invitId, String nickname, Integer brokerId) {

		if (Strings.isNullOrEmpty(email) && Strings.isNullOrEmpty(phone)) {
			log.error("注册代码错误");
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		String md5Pwd = MD5Util.genMD5Code(password);
		String loginsalt = CryptoUtils.getSalt();
		String paysalt = CryptoUtils.getSalt();
		String pwd = CryptoUtils.getHash(md5Pwd, loginsalt);

		Date currentDate = new Date();
		UserPreRegistrationPool userPreRegistrationPool = new UserPreRegistrationPool();
		userPreRegistrationPool.setEmail(email);
		userPreRegistrationPool.setMobile(phone);
		userPreRegistrationPool.setLockNum((byte) 0);
		userPreRegistrationPool.setCreateDate(currentDate);
		userPreRegistrationPool.setUpdateDate(currentDate);
		userPreRegistrationPool.setLoginSalt(loginsalt);
		userPreRegistrationPool.setPaySalt(paysalt);
		userPreRegistrationPool.setLoginPassword(pwd);
		userPreRegistrationPool.setInviteUid(invitId);
		userPreRegistrationPool.setNickname(nickname);
		userPreRegistrationPool.setBrokerId(brokerId);
		try {
			userPreRegistrationPoolMapper.insertSelective(userPreRegistrationPool);
		} catch (Exception e1) {
			log.error("插入userPerRegistrationPool表异常", e1);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		return userPreRegistrationPool;
	}

	@Override
	public UserPreRegistrationPool getUserPerRegistrationInformationById(Integer userPreRegistrationId) {

		UserPreRegistrationPool userPreRegistrationPool = userPreRegistrationPoolMapper
				.selectByPrimaryKey(userPreRegistrationId);
		if (userPreRegistrationPool == null) {

			throw new AppException(UserCodeConst.GET_PRE_REGISTRATION_ERROR);
		}
		return userPreRegistrationPool;
	}

	@Override
	@Transactional
	public User preRegisterUser(String email, String phone, String password,String loginsalt,  Integer invitId, String nickname) {
		if (Strings.isNullOrEmpty(email) && Strings.isNullOrEmpty(phone)) {
			log.error("注册代码错误");
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		UserSimpleInfoDto userSimpleInfoDto = new UserSimpleInfoDto();
		Date currentDate = new Date();
		User user1 = new User();
		user1.setEmail(email);
		user1.setMobile(phone);
		user1.setLockNum((byte) 0);
		user1.setCreateDate(currentDate);
		user1.setUpdateDate(currentDate);
		user1.setLoginSalt(loginsalt);
		user1.setLoginPassword(password);
		user1.setInviteUid(invitId);
		user1.setNickname(nickname);
		user1.setAuthLevel(AuthLevel.LEVEL0);
		user1.setBrokerId(0);
		try {
			userMapper.insertSelective(user1);
		} catch (Exception e1) {
			log.error("插入user表异常", e1);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		// 支付密码锁表初始化
		UserPayPassword record = new UserPayPassword();
		record.setCreateDate(currentDate);
		record.setLockNum(0);
		record.setUid(user1.getUid());
		try {
			userPayPasswordService.insertSelective(record);
		} catch (Exception e) {
			log.error("插入userPayPassword表异常,uid={},eMessage=" + e.getMessage(), user1.getUid(), e);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		userSimpleInfoDto.setUserAccount(email);
		userSimpleInfoDto.setFullName(user1.getFullname());// 前端没有传入，fullname为null
		userSimpleInfoDto.setUid(user1.getUid());
		userSimpleInfoDto.setNickName(nickname);
		userSimpleInfoDto.setBrokerId(user1.getBrokerId());
		log.info(userSimpleInfoDto + "");
		return user1;
	}

	@Override
	public void updateCreateIpByUid(Integer uid, String createip) {
		userMapper.updateCreateIpByUid(uid,createip);
	}

	@Override
	public List<User> getUsers(Integer uid) {
		return userMapper.getUserList(uid,null, null, null, null);
	}

	@Override
	public UserStatistics getDailyStatistic(Date date) {
		return userMapper.getDailyStatistic(date);
	}

}
