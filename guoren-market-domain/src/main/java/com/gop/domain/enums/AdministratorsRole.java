package com.gop.domain.enums;

import lombok.Data;

/**
 * 管理员角色枚举
 * 
 * @author yuxiaojian
 *
 */

public enum AdministratorsRole {

	/**
	 * 财务
	 */
	ACCOUNTANT(2),
	/**
	 * 管理员
	 */
	ADMIN(3),
	/**
	 * 客服
	 */
	CUSTOM_SERVICE(1);
	private Integer leve;

	private AdministratorsRole(int level) {
		this.leve = level;
	}

	public int getLeve() {
		return this.leve;
	}

	public boolean compareLeve(AdministratorsRole administratorsRole) {
		return this.leve.compareTo(administratorsRole.leve) > 0;
	}
}
