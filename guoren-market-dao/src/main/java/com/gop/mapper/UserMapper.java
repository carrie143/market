package com.gop.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gop.domain.UserStatistics;
import org.apache.ibatis.annotations.Param;

import com.gop.domain.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * Description: 获取手机号码注册个数
     * 
     * @param phone
     *            手机号码
     * @return 返回手机号码个数
     * @see
     */
    Integer getCountByPhone(@Param("phone") String phone);
    
    User selectUserByLoginPhone(@Param("phone") String phone);

    
    /**
     * 根据邮箱查询用户
     */
    User selectUserByLoginEmail(@Param("email") String email);
    
    /**
     * 修改登录密码
     * 
     * Description: <br/>
     * @Author：zhangxianglong
     * @date 2016年3月22日下午1:49:17
     * @param phone
     * @param newPwd
     * @return 
     * @see
     */
    int resetPwd(@Param("phone") String phone, 
                 @Param("newPwd") String newPwd,
                 @Param("salt") String salt);
    
    /**
     * 邮箱
     * 修改登录密码
     * 
     * Description: <br/>
     * @Author：liu
     * @date 2017年2月9日
     * @param email
     * @param newPwd
     * @return 
     * @see
     */
    int resetEmailPwd(@Param("email") String email, 
                 @Param("newPwd") String newPwd,
                 @Param("salt") String salt);    
    
    User getUserByAddress(@Param("address") String address);

    /**
     * 查询用户信息
     * @param uid
     * @param email
     * @param startDate
     * @param endDate
     * @return
     */
    public List<User> getUserListWithDate(
    		@Param("uid") Integer uid,
    		@Param("email") String email,
            @Param("fullName") String fullName,
            @Param("phone") String phone,
    		@Param("startDate") Date startDate,
    		@Param("endDate") Date endDate);
    
    /**
     * 
     * @param uid
     * @param phone
     * @param name
     * @return
     */
    public List<User> getUserList(
    		@Param("uid") Integer uid,
    		@Param("brokerId") Integer brokerId,
    		@Param("email") String email,
    		@Param("phone") String phone,
    		@Param("name") String name);
    
    
    //设置用户手机号码
    int updateUserPhoneNumberByUid(@Param("phone") String phone,@Param("uid") Integer uid);
    
    
    //设置用户昵称
    int updateUserNickNameByUid(@Param("nickName") String nickName,@Param("uid") Integer uid);
    
    void updateCreateIpByUid(@Param("uid") Integer uid,@Param("createip") String createip);

    UserStatistics getDailyStatistic(@Param("date") Date date);
}