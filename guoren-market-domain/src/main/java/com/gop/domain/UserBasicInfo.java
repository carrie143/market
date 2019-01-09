package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.Gender;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserBasicInfo {

	private Integer id;

	private String firstName;

	private String middleName;

	private String lastName;

	private Gender gender;

	private String birthday;

	private String countryId;

	private String country;

	private Integer uid;

	private Date createDate;

	private Date updateDate;

	

}