package com.gop.domain.enums;

/**
 * 果仁充值(转入), 转出(转出)状态类型
 * 
 * <p>Title:TransferGopOutStatus </p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author zhangxianglong
 * @date 2016年3月20日下午5:52:44
 * @version V1.0.0
 * @since
 */
public enum WithdrawCoinOrderStatus {

    /*
     * 成功
     */
    SUCCESS,

    /*
     * 确认中，（走区块链需要确认是否到账）
     */
    PROCESSING,

    /*
     * 失败
     */
    FAILURE,

    //未知状态

    UNKNOWN,
    /*
     * 提交后
     */
    WAIT,

    /*
     * 拒绝
     */
    REFUSE,
    /*
     * 云平台拒绝
     */
    CLOUD_REFUSE,
    /**
     * 挂起，券商审核通过后，提现金额触发当天该券商所有用户的限额 ，需要云平台管理员再次审核确认
     */
    SUSPEND,;
    
}