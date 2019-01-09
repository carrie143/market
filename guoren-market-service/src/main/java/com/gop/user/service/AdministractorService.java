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
import com.gop.domain.Administrators;
import com.gop.domain.enums.LockStatus;

public interface AdministractorService {

	public Administrators getAdministractor(Integer adminId);
	
	public Administrators getAdministractor(String  account);

	/**
	 * 锁定指定管理员
	 *
	 * @param adminId
	 * @param uri
	 * @return boolean
	 */
	public boolean checkRights(Integer adminId, String uri);
	
	/**
	 * 创建管理员
	 * 
	 * void
	 */
	public boolean createAdminstrator(Integer adminId, String phone, String password, String userName,
			Integer role, String ip);

	/**
	 * 锁定指定管理员
	 * 
	 * @param id
	 * @return boolean
	 */
	public boolean lockAdmin(Integer id, String ip);
	

	/**
	 * 解锁指定管理员
	 *
	 * @return boolean
	 */
	public boolean unlockAdmin(Integer id, String ip);
	
	/**
	 * 修改登录密码
	 * @param id
	 * @param newPassword
	 * @return
	 * boolean
	 */
	public boolean updatePassword(Integer id,String newPassword,String ip);
	
	/**
	 * 重置登录密码
	 * 
	 */
	public void resetLoginPassword(Integer adminId,String newPassword,String ip);
	
	/**
	 * 
	 * @param uid
	 * @param phone
	 * @param lockStatus
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Administrators> getAdministratorList(Integer uid, String phone, LockStatus lockStatus, Integer pageNo, Integer pageSize);
	
	/**
	 * 
	 * @param uid
	 * @param phone
	 * @param lockStatus
	 * @return
	 */
	public int getAdministratorCount(Integer uid, String phone, LockStatus lockStatus);

}
