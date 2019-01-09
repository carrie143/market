package com.gop.domain.enums;

public enum WithdrawCoinOrderType {
	
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
    
    /*
     * 拒绝
     */
    REFUSE,
    
    /*
     * 已处理
     */
    PROCESSED,
    
    /*
     * 未处理
     */
    UNTREATED

}
