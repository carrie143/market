//package com.gop.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionException;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//
//import lombok.extern.log4j.Log4j;
//
//@Component
//@Log4j
//public class TransactionUtil {
//	@Autowired
//	private DataSourceTransactionManager dataSourceTransactionManager;
//
//	public TransactionStatus beginRequiredTransactional() {
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); // 事物隔离级别，开启新事务
//		TransactionStatus status = dataSourceTransactionManager.getTransaction(def); // 获得事务状态
//		return status;
//	}
//
//	public TransactionStatus beginRequiredNewTransactional() {
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务
//		TransactionStatus status = dataSourceTransactionManager.getTransaction(def); // 获得事务状态
//		return status;
//	}
//
//	public void commit(TransactionStatus status) {
//		dataSourceTransactionManager.commit(status);
//	}
//
//	public void rollback(TransactionStatus status) {
//
//		if (status != null) {
//			dataSourceTransactionManager.rollback(status);
//		}
//
//	}
//
//}
