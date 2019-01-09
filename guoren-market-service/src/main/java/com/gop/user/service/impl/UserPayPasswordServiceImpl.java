package com.gop.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.gop.code.consts.CommonCodeConst;
import com.gop.domain.UserPayPassword;
import com.gop.exception.AppException;
import com.gop.mapper.UserPayPasswordMapper;
import com.gop.user.dto.CheckPayPasswordLockedDto;
import com.gop.user.service.UserPayPasswordService;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付密码上锁
 * 
 * @author tjy
 */
@Service
@Slf4j
public class UserPayPasswordServiceImpl implements UserPayPasswordService {

	@Autowired
	private UserPayPasswordMapper userPayPasswordMapper;

	public static final Integer MAX_USER_MODIFY_PAY_TIMES = 3;

	@Transactional
	public CheckPayPasswordLockedDto CheckPayPasswordLockedTimes(Integer userId) {

		// 根据查询的锁定次数返回相应的dto对象
		CheckPayPasswordLockedDto lockedDto = new CheckPayPasswordLockedDto();
		UserPayPassword userPayPassword = new UserPayPassword();

		try {
			userPayPassword = userPayPasswordMapper.getInfoByUid(userId);
		} catch (Exception e1) {
			log.error("用userId查询userPayPassword表异常，eMessage=" + e1.getMessage(), e1);
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		Integer lockNum = userPayPassword.getLockNum();
		Integer num = null;

		if (lockNum == null) {
			num = 0;
			lockedDto.setLockedNum(num);
			log.info("userPayPassword表的lockNum值设为null，应该是默认0");
			return lockedDto;
		}

		if (lockNum < 0) {
			log.error("userPayPassword表锁定次数小于0，错误");
			throw new AppException(CommonCodeConst.SERVICE_ERROR);
		}

		if (lockNum == 0) {
			num = 0;
			lockedDto.setLockedNum(num);
			return lockedDto;
		}
		if (lockNum >= MAX_USER_MODIFY_PAY_TIMES) {
			num = MAX_USER_MODIFY_PAY_TIMES;
			lockedDto.setLockedNum(num);
			return lockedDto;
		}

		// 最后剩下：锁定次数在0到3之间，涉及到次日锁定次数清零
		Date locktime = userPayPassword.getCreateDate();
		boolean flag = isDateNow(locktime);
		if (!flag) {
			userPayPassword.setLockNum(0);
			userPayPassword.setCreateDate(new Date());

			try {
				userPayPasswordMapper.updateByPrimaryKeySelective(userPayPassword);
			} catch (Exception e) {
				log.error("更新userPayPassword表的用户" + userId + "的登录锁住状态时，抛出异常:", e);
				throw new AppException(CommonCodeConst.SERVICE_ERROR);
			}
			num = 0;

		} else {

			num = lockNum;
		}

		lockedDto.setLockedNum(num);
		return lockedDto;

	}

	/**
	 * 查询是否锁住，以及锁住次数
	 * 
	 * @param uid
	 * @author tjy zxl
	 * @return
	 */
	@Transactional
	public JSONObject isLocked(Integer uid) {
		JSONObject jsonObject = new JSONObject();
		UserPayPassword lockPaypwd = userPayPasswordMapper.getInfoByUid(uid);

		// 1.从未锁住过
		if (lockPaypwd == null) {
			jsonObject.put("num", 0);
			return jsonObject;
		}

		// 2.锁住3次，直接不可以使用
		if (lockPaypwd.getLockNum() >= 3) {
			jsonObject.put("result", "error");
			jsonObject.put("msg", "输入错误3次，已被锁定");
			jsonObject.put("num", 3);
			return jsonObject;
		}

		// 3.如果不是当天的数据，是之前的数据，次数清0，更新日期，返回success
		Date locktime = lockPaypwd.getCreateDate();
		boolean flag = isDateNow(locktime);
		if (!flag) {
			lockPaypwd.setLockNum(0);// 次数清0
			lockPaypwd.setCreateDate(new Date());
			try {
				userPayPasswordMapper.updateByPrimaryKeySelective(lockPaypwd);
			} catch (Exception e) {
				log.error("更新lock_paypwd表的用户" + uid + "的支付锁住状态时，抛出异常:", e);
				return null;
			}

			jsonObject.put("num", 0);
			return jsonObject;
		}

		// 4.锁住次数是5次
		// if (lockPaypwd.getLockNum() == 5)
		// {
		//
		// SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		// String locktimeStr = dateFormat2.format(locktime);
		// String date = dateFormat2.format(new Date());
		//
		// long balance = 0;
		// try
		// {
		// // 当前时间减去数据库时间(这个的除以1000得到秒，相应的60000得到分，3600000得到小时)
		// balance = (dateFormat2.parse(date).getTime()
		// - dateFormat2.parse(locktimeStr).getTime())
		// / 3600000;
		// }
		// catch (ParseException e)
		// {
		// loger.error("格式转化异常，抛出异常:", e);
		// return null;
		// }
		//
		// // 4.1 如果上锁时间超过3小时，可以使用
		// if (balance >= 3)
		// {
		// jsonObject.put("result", "success");
		// jsonObject.put("num", lockPaypwd.getLockNum());
		// return jsonObject;
		// }
		// else
		// {
		// // 4.2 没有超过3小时，返回error,和上锁次数
		// jsonObject.put("result", "error");
		// jsonObject.put("num", lockPaypwd.getLockNum());
		// return jsonObject;
		// }
		// }

		// 4.锁住次数小于3次的
		jsonObject.put("num", lockPaypwd.getLockNum());
		return jsonObject;
	}

	/**
	 * 增加锁住次数
	 * 
	 * @param uid
	 * @param result
	 *            success/error
	 * @author tjy zxl
	 * @return
	 */
	@Transactional
	public Integer addLockTimes(Integer uid) {
		UserPayPassword lockPaypwd = userPayPasswordMapper.getInfoByUid(uid);

		// 1.从未锁住过，第一次锁住时插入数据库
		if (lockPaypwd == null) {
			lockPaypwd = new UserPayPassword();
			lockPaypwd.setUid(uid);
			lockPaypwd.setLockNum(1);
			lockPaypwd.setCreateDate(new Date());
			try {
				userPayPasswordMapper.insertSelective(lockPaypwd);
			} catch (Exception e) {
				log.error("插入lock_paypwd表时，抛出异常:", e);
				return null;
			}
		} else {
			UserPayPassword lockPaypwd1 = new UserPayPassword();
			lockPaypwd1.setId(lockPaypwd.getId());
			lockPaypwd1.setLockNum(lockPaypwd.getLockNum() + 1);
			lockPaypwd1.setCreateDate(new Date());
			userPayPasswordMapper.updateByPrimaryKeySelective(lockPaypwd1);
		}

		return lockPaypwd.getLockNum();
	}

	/**
	 * 重置锁次数为0
	 * 
	 * Description: <br/>
	 * 
	 * @Author：zhangxianglong
	 * @date 2016年3月24日下午7:25:34
	 * @param uid
	 * @return
	 * @see
	 */
	public void lockPayNumZero(Integer uid) {

		UserPayPassword lockPaypwd = new UserPayPassword();
		lockPaypwd.setLockNum(0);
		lockPaypwd.setCreateDate(new Date());
		lockPaypwd.setUid(uid);
		userPayPasswordMapper.updateByUid(lockPaypwd);

	}

	/**
	 * 判断登录与锁定时间是否是今天
	 * 
	 * Description: <br>
	 * 
	 * @param locktime
	 * @return
	 * @see
	 */
	private boolean isDateNow(Date locktime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time1 = dateFormat.format(locktime);
		String time2 = dateFormat.format(new Date());
		if (time1.equals(time2)) {
			return true;
		}
		return false;
	}

	@Override
	public int insertSelective(UserPayPassword record) {
		return userPayPasswordMapper.insertSelective(record);
	}

}
