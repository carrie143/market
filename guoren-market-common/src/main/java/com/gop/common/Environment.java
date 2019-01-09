package com.gop.common;

public interface Environment {
	public String getMsg(String code, String... args);

	public EnvironmentEnum getSystemEnvironMent();

	public enum EnvironmentEnum {
		CHINA("domestic"), US("overseas");
		private String explian;

		private EnvironmentEnum(String explian) {
			this.explian = explian;
		}

		public String getExplian() {
			return this.explian;
		}

	}
}
