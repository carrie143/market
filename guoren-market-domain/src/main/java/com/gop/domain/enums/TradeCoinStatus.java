package com.gop.domain.enums;

/**
 * 买入、卖出撮合状态
 * 
 * <p>Title:TradeGopStatus </p>
 * <p>Description: </p>
 * <p>Company: </p>
 * @author zhangxianglong
 * @date 2016年3月20日下午4:54:05
 * @version V1.0.0
 * @see TradeCoinStatus
 * @since
 */
public enum TradeCoinStatus {
    
    /*
     * 撮合进行中
     */
    PROCESSING,
    
    /*
     * 撮合成功
     */
    SUCCESS,
    
    /*
     * 撮合取消
     */
    CANCEL,
    
    
    WAITING,
    
    FAIL

}
