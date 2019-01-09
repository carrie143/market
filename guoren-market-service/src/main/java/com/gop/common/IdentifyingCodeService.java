/*
 * 文件名：RedisPhoneCode.java 版权：Copyright by www.guoren.com 描述：次接口用来验证以及处理手机验证码 修改人：汪洋
 * 修改时间：2015年12月8日 跟踪单号： 修改单号： 修改内容：
 */

package com.gop.common;

public interface IdentifyingCodeService
{
    /**
     * Description: 如果两次验证码距离发送时间在60秒内则不允许发送
     * 
     * @param key
     *            要验证的手机号码
     * @return true 允许发生验证码 ，false 不允许发送验证码
     */
    public Boolean checkSendCode(String key);

    /**
     * Description: 保存验证码在redis中并且设置验证码的有效期
     * 
     * @param code
     *            验证码
     * @param key
     *            唯一值
     * @param liveTime
     *            设置验证码有效时间（秒）
     * @param avoidTime
     *            阻止重复发送验证码时间(秒)
     * @see
     */
    public Boolean saveCode(final String code, final String key, final long liveTime,
                            final long avoidTime);

    /**
     * Description: 查询redis并获取验证码
     * 
     * @phone :前台传递的验证码
     * @return 返回验证码
     * @see
     */
    public String getCode(String key);
    
    public void updateAvoidKey(String key, final long avoidTime);
    
    /**
     * 删除redis值
     * @param key
     */
    public void delCode(String key);
}
