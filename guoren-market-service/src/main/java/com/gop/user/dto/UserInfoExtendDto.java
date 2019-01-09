package com.gop.user.dto;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

import lombok.Data;

@Data
public class UserInfoExtendDto {
	public String headImg;

	public Field getFieldByName(String name) {
		Field Field = null;
		String fieldName = ProfileUserInfoKey.getFieldName(name);
		if (null == fieldName) {
			return null;
		}
		try {
			Field = ReflectionUtils.findField(UserInfoExtendDto.class, fieldName);
		} catch (Exception e) {
			return null;
		}
		return Field;
	}

	public enum ProfileUserInfoKey {
		HEADIMG("headImg");
		private String field;

		private ProfileUserInfoKey(String field) {
			this.field = field;
		}

		public static String getFieldName(String fieldName) {
			switch (fieldName) {
			case "HEADIMG":

				return "headImg";

			default:
				return null;
			}
		}

		public static String getProfileUserInfoKey(Field field) {
			String fieldName = field.getName();
			switch (fieldName) {
			case "headImg":
				return HEADIMG.name();

			default:
				return null;
			}

		}
	}
}
