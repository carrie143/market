package com.gop.domain;

import java.io.Serializable;
import java.util.Date;

import com.gop.domain.enums.OperationType;
import com.gop.domain.enums.TokenOrderState;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TokenOrderLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6780128645235670382L;

	private Integer id;

	private Integer tokenOrderId;

	private OperationType operationType;

	private Date createTime;

	private Date updatetime;

	private TokenOrderState changeStateFrom;

	private TokenOrderState changeStateTo;

	private Integer operationUid;

}