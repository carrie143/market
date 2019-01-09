package com.gop.domain.enums;

/**
 * 人民币充值，转出类型
 * 
 * <p>Title:TransferCnyStatus </p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author zhangxianglong
 * @date 2016年3月21日上午11:39:21
 * @version V1.0.0
 * @see WithdrawCurrencyOrderStatus
 * @since
 */
public enum WithdrawCurrencyOrderStatus {
    
    /*
     * 等待
     */
    WAIT,
    
    /*
     * 成功
     */
    SUCCESS,
    
    /*
     * 取消
     */
    CANCEL,
    
    /*
     * 处理中
     */
    PROCESSING,
    
    /*
     * 待确认
     */
    UNKNOWN,
    
    /*
     * 已退款
     */
    FAILURE;
}
