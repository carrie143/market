/*
 * 文件名：UserService.java
 * 版权：Copyright by www.guoren.com
 * 描述：
 * 修改人：zhangxianglong
 * 修改时间：2016年3月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.gop.user.service;

import com.github.pagehelper.PageInfo;
import com.gop.domain.*;
import com.gop.mode.vo.PageModel;
import com.gop.user.dto.CheckLoginLockedDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {

	/**
	 * 
	 * @return
	 */
	public void updatePayPassword(String newPwd, Integer userId);

	public User getUserByPhone(String phone);

	public User getUserByEmail(String email);

	/**
	 * 分页查询用户基本信息
	 * @param uid
	 * @param account
	 * @param startDate
	 * @param endDate
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageInfo<User> getBaseUserList(Integer uid, String account, String fullName,Date startDate, Date endDate,String orderBy, Integer pageNo,
												   Integer pageSize) ;

	/**
	 * 分页查询用户列表
	 * 
	 * @param uid
	 * @param phone
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return List<User>
	 */
	public PageInfo<User> getUserList(Integer uid, Integer brokerId, String phone, String name, Integer pageNo, Integer pageSize);

	/**
	 * Description:判断是否手机已经注册
	 * 
	 * @return 返回 null 数据库异常 ，返回ture 已经注册，返回false：没有注册
	 * @param phone
	 *            用户手机号
	 */
	public Boolean isPhoneRegister(String phone);

	/**
	 * Description:判断是否邮箱已经注册
	 * 
	 * @return 返回 null 数据库异常 ，返回ture 已经注册，返回false：没有注册
	 *            用户邮箱
	 */
	public Boolean isMailRegister(String email);

	/**
	 * Description: 注册邮箱
	 * 
	 * @param phone
	 *            用户的邮箱
	 * @see
	 * @return 返回gopToken
	 */
	public User registerUser(String email, String phone,String password, String payPwd, Integer invitId,
			String nickname, Integer brokerId);

	public CheckLoginLockedDto checkLoginLockTimes(Integer uid);

	/**
	 * 查询用户基本信息
	 * 
	 * Description: <br/>
	 * 
	 * @Author：zhangxianglong
	 * @date 2016年3月19日下午5:19:42
	 * @param uid
	 * @return
	 * @see
	 */
	public User getUserByUid(Integer uid);

	/**
	 * 修改登录密码,登录后
	 * 
	 * Description: <br/>
	 * 
	 * @Author：zhangxianglong
	 * @date 2016年3月19日下午12:31:03
	 * @param pwd
	 * @param userid
	 * @return
	 * @see
	 */
	public void updatePassword(String pwd, int userid);

	public void updateByPrimaryKeySelective(User userTemp);

	public User getUserByAccount(String account);
	
	public UserInfo getUserInfo(Integer uid);

	/**
	 * 记录用户登录日志,包含ip地址
	 * @param record
	 */
	public void recordUserLogin(UserLoginLog record);
	
	public Boolean checkUpIpCountryAndCityIsSameAsLastTime(Integer uid,String country,String city);
	
	public PageModel<UserLoginLog> getUserLoginLogByUid(Integer uid,Integer pageNo,
			Integer pageSize);
	
	//设置用户手机号码
	public void updateUserphoneNumber(String phoneNumber,Integer uid);
	
	//设置用户昵称
	public void updateUserNickName(String nickNmae,Integer uid);
	
	//添加用户预注册信息
	public UserPreRegistrationPool addUserPerRegistrationInformation(String email, String phone,String password, Integer invitId,
			String nickname, Integer brokerId);

	//获取用户预注册信息
	public UserPreRegistrationPool getUserPerRegistrationInformationById(Integer userPreRegistrationId);
	
	public User preRegisterUser(String email, String phone,String password,String loginsalt, Integer invitId,
			String nickname);
	
	//记录用户注册ip
	public void updateCreateIpByUid(Integer uid,String ip);

	public List<User> getUsers(Integer uid);

	public UserStatistics getDailyStatistic(Date date);
}
