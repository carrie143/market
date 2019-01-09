package com.gop.domain.enums;

public enum AuthLevel {

	LEVEL0(0), LEVEL1(1), LEVEL2(2);
	private int level;

	private AuthLevel(int a) {
		this.level = a;
	}

	public boolean isBigerOrEqual(AuthLevel level) {
      return this.level>=level.level;
	}
	
	public boolean isBiger(AuthLevel level) {
	      return this.level>level.level;
		}
}
