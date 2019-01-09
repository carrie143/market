package com.gop.certification.controller.dto;

import java.util.List;

import com.gop.domain.UserInfoProfile;
import com.gop.domain.UserResidence;
import com.gop.domain.enums.UserProfileKey;

import lombok.Data;

@Data
public class ResidenceDetailDto {
	
	UserResidence residenceInfo;

	List<UserResidence> residenceHistory;

	BasicInfo basicInfo;

	public ResidenceDetailDto(UserResidence residenceInfo, List<UserResidence> residenceHistory,
			List<UserInfoProfile> lst) {
		this.residenceInfo = residenceInfo;
		this.residenceHistory = residenceHistory;
		this.basicInfo = new BasicInfo(lst);
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
