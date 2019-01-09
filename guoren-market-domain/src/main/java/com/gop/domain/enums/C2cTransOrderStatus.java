package com.gop.domain.enums;

public enum C2cTransOrderStatus {
	//从数据库扩展多一种completed
	//前端如果传入completed,后台为finishde,canceled,closed,overtime
	UNPAY,PAID,COMPLAINNING,FINISHED,CANCELED,CLOSED,OVERTIME,COMPLETED;
}
