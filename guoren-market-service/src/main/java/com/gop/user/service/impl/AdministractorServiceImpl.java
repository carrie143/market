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

package com.gop.user.service.impl;

import java.util.Date;

import com.gop.domain.AdminRole;
import com.gop.domain.Role;
import com.gop.domain.enums.RoleStatus;
import com.gop.mapper.AdminRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Throwables;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.Administrators;
import com.gop.domain.enums.AdministratorsRole;
import com.gop.domain.enums.LockStatus;
import com.gop.domain.enums.ManagerSetPwdFlag;
import com.gop.exception.AppException;
import com.gop.mapper.AdministratorsMapper;
import com.gop.mapper.RoleMapper;
import com.gop.user.service.AdministractorService;
import com.gop.user.service.ManagerPasswordOperRecordService;
import com.gop.user.service.RoleManagerService;
import com.gop.util.CryptoUtils;
import com.gop.util.MD5Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdministractorServiceImpl implements AdministractorService {

	@Autowired
	private AdministratorsMapper administratorsMapper;
	
	@Autowired
	private ManagerPasswordOperRecordService managerPasswordOperRecordService;

	@Autowired
	private RoleManagerService roleManagerService;
	@Autowired
	private AdminRoleMapper adminRoleMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public Administrators getAdministractor(Integer adminId) {

		return administratorsMapper.selectByPrimaryKey(adminId);
	}

	@Override
	public Administrators getAdministractor(String account) {
		return administratorsMapper.selectByAccount(account);
	}

	@Override
	public boolean checkRights(Integer adminId, String uri) {
		Integer roleId = roleManagerService.getAdminRole(adminId);
		if (roleId != null) {
			Integer num = roleManagerService.checkRoleRights(roleId, uri);
			return num > 0 ? true : false;
		}else {
			return false;
		}
	}

	@Override
	public boolean createAdminstrator(Integer adminId, String phone, String password, String userName,
			Integer roleId, String ip) {
		Role queryRole = new Role();
		queryRole.setRoleId(roleId);
		queryRole.setStatus(RoleStatus.ENABLE.getStatus());
		Role role = roleMapper.getRoleById(queryRole);
		if(role == null){
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}
		Administrators administrators = new Administrators();
		administrators.setMobile(phone);
		administrators.setOpName(userName);
		String md5Password = MD5Util.genMD5Code(password);
		String salt = MD5Util.genMD5Code(adminId + phone);// 创建者id+手机号的md5作为盐值
		String hashPassword = CryptoUtils.getHash(md5Password, salt);
		administrators.setLoginPassword(hashPassword);
		administrators.setRole(role.getRoleName());
		administrators.setLocked(LockStatus.UNLOCK);
		administrators.setCreateDate(new Date());
		administrators.setCreateAdminId(adminId);
		administrators.setCreateip(ip);
		administrators.setUpdateDate(administrators.getCreateDate());
		administrators.setUpdateip(ip);
		int result = 0;
		AdminRole adminRole = new AdminRole();
		adminRole.setRoleId(roleId);
		adminRole.setCreateDate(new Date());
		adminRole.setUpdateDate(new Date());
		try {
			administratorsMapper.insertSelective(administrators);
			adminRole.setAdminId(administrators.getAdminId());
			result = adminRoleMapper.insert(adminRole);
		}catch (Exception e1) {
			log.error("插入表出错", e1);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
		return result > 0 ? true : false;
	}
	
	@Override
	public boolean lockAdmin(Integer adminId, String ip) {
		Administrators administrators = administratorsMapper.selectByPrimaryKey(adminId);
		if (null == administrators) {
			log.info("用户不存在");
			return false;
		}
		administrators = new Administrators();
		administrators.setAdminId(adminId);
		administrators.setLocked(LockStatus.LOCK);// 锁定
		administrators.setUpdateDate(new Date());
		administrators.setUpdateip(ip);
		int num = administratorsMapper.updateByPrimaryKeySelective(administrators);
		return num > 0 ? true : false;
	}

	@Override
	public boolean unlockAdmin(Integer adminId, String ip) {
		Administrators administrators = administratorsMapper.selectByPrimaryKey(adminId);
		if (null == administrators) {
			log.info("用户不存在");
			return false;
		}
		administrators = new Administrators();
		administrators.setAdminId(adminId);
		administrators.setLocked(LockStatus.UNLOCK);// 解锁
		administrators.setUpdateDate(new Date());
		administrators.setUpdateip(ip);
		int num = administratorsMapper.updateByPrimaryKeySelective(administrators);
		return num > 0 ? true : false;
	}

	@Override
	@Transactional
	public boolean updatePassword(Integer id, String newPassword, String ip) {
		int num = 0;
		Administrators administrators = administratorsMapper.selectByPrimaryKey(id);
		if (null != administrators) {
			String md5Password = MD5Util.genMD5Code(newPassword);
			String salt = MD5Util.genMD5Code(administrators.getCreateAdminId() + administrators.getMobile());// 创建者id+手机号的md5作为盐值
			String hashPassword = CryptoUtils.getHash(md5Password, salt);
			administrators.setLoginPassword(hashPassword);
			administrators.setUpdateDate(new Date());
			administrators.setUpdateip(ip);
			num = administratorsMapper.updateByPrimaryKeySelective(administrators);
		}
		//判断是否第一次设置密码
		if (null == managerPasswordOperRecordService.selectManagerPasswordOperRecord(id)) {
			managerPasswordOperRecordService.addManagerPasswordOperRecord(id, ManagerSetPwdFlag.TRUE);
		}else {
			managerPasswordOperRecordService.updateManagerPasswordOperRecordFlagByAdminId(id, ManagerSetPwdFlag.TRUE);
		}
		return num > 0 ? true : false;
	}

	@Override
	public PageInfo<Administrators> getAdministratorList(Integer adminId, String phone, LockStatus lockStatus,
			Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize, true);
		PageHelper.orderBy("admin_id desc");

		return new PageInfo<>(
				administratorsMapper.getAdministratorList(adminId, phone, lockStatus != null ? lockStatus.name() : null));
	}

	@Override
	public int getAdministratorCount(Integer uid, String phone, LockStatus lockStatus) {
		return administratorsMapper.getAdministratorCount(uid, phone, lockStatus != null ? lockStatus.name() : null);
	}

	@Override
	@Transactional
	public void resetLoginPassword(Integer adminId, String newPassword, String ip) {
		try {
			Administrators administrators = administratorsMapper.selectByPrimaryKey(adminId);
			if (null != administrators) {
				String md5Password = MD5Util.genMD5Code(newPassword);
				String salt = MD5Util.genMD5Code(administrators.getCreateAdminId() + administrators.getMobile());// 创建者id+手机号的md5作为盐值
				String hashPassword = CryptoUtils.getHash(md5Password, salt);
				administrators.setLoginPassword(hashPassword);
				administrators.setUpdateDate(new Date());
				administrators.setUpdateip(ip);
				administratorsMapper.updateByPrimaryKeySelective(administrators);
			}
			if (null == managerPasswordOperRecordService.selectManagerPasswordOperRecord(adminId)) {
				managerPasswordOperRecordService.addManagerPasswordOperRecord(adminId, ManagerSetPwdFlag.FALSE);
			}else {
				managerPasswordOperRecordService.updateManagerPasswordOperRecordFlagByAdminId(adminId, ManagerSetPwdFlag.FALSE);
			}
		}catch (Exception e) {
			log.error("超级管理员为管理员重置密码异常 adminId={},eMessage=" + e.getMessage(),adminId, e);
			Throwables.propagateIfInstanceOf(e, AppException.class);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}
	}

}
