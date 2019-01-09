package com.gop.certification.controller.dto;

import java.util.List;

import com.gop.domain.UserIdentification;
import com.gop.domain.UserInfoProfile;
import com.gop.domain.enums.UserProfileKey;

import lombok.Data;

@Data
public class IdentityDetailDto {

	UserIdentification IdentityInfo;

	List<UserIdentification> identityHistory;

	BasicInfo basicInfo;

	public IdentityDetailDto(UserIdentification IdentityInfo, List<UserIdentification> identityHistory,
			List<UserInfoProfile> lst) {
		if (null != IdentityInfo) {
			this.IdentityInfo = IdentityInfo;
		}
		if (null != identityHistory) {
			this.identityHistory = identityHistory;
		}
		if (null != lst) {

			this.basicInfo = new BasicInfo(lst);
		}
	}

	@Data
	private class BasicInfo {

		String firstName;

		String middleName;

		String lastName;

		String gender;

		String birthday;

		String countryId;

		String country;

		public BasicInfo(List<UserInfoProfile> lst) {
			for (UserInfoProfile base : lst) {
				if (UserProfileKey.FIRSTNAME.toString().equals(base.getProfileKey())) {
					this.firstName = base.getProfileValue();
				} else if (UserProfileKey.MIDDLENAME.toString().equals(base.getProfileKey())) {
					this.middleName = base.getProfileValue();
				} else if (UserProfileKey.LASTNAME.toString().equals(base.getProfileKey())) {
					this.lastName = base.getProfileValue();
				} else if (UserProfileKey.GENDER.toString().equals(base.getProfileKey())) {
					this.gender = base.getProfileValue();
				} else if (UserProfileKey.BIRTHDAY.toString().equals(base.getProfileKey())) {
					this.birthday = base.getProfileValue();
				} else if (UserProfileKey.COUNTRYID.toString().equals(base.getProfileKey())) {
					this.countryId = base.getProfileValue();
				} else if (UserProfileKey.COUNTRY.toString().equals(base.getProfileKey())) {
					this.country = base.getProfileValue();
				}
			}

		}
	}
}
