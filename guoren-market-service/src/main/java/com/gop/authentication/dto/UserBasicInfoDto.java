package com.gop.authentication.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.domain.UserBasicInfo;
import com.gop.domain.enums.Gender;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserBasicInfoDto {
	private Integer uid;

	@NotBlank
	private String firstName;

	private String middleName;

	@NotBlank
	private String lastName;

	@NotBlank
	private Gender gender;

	@NotBlank
	private String birthday;

	@NotBlank
	private String countryId;

	@NotBlank
	private String country;
	
	@NotBlank
	private Date createDate;

	public UserBasicInfoDto(UserBasicInfo userBasicInfo) {
		this.firstName = userBasicInfo.getFirstName();
		this.middleName = userBasicInfo.getMiddleName();
		this.lastName = userBasicInfo.getLastName();
		this.gender = userBasicInfo.getGender();
		this.birthday = userBasicInfo.getBirthday();
		this.countryId = userBasicInfo.getCountryId();
		this.uid = userBasicInfo.getUid();
		this.createDate = userBasicInfo.getCreateDate();
	}

}
