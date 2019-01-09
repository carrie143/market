package com.gop.certification.dto;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.domain.UserResidence;
import com.gop.mode.vo.BaseDto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResidenceAuthenticationDto extends BaseDto{
	
	private Integer uid;
	
	@NotBlank
	private String countryId;
	
	@NotBlank
	private String country;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String address;
	
	@NotBlank
	private String postCode;
	
	@NotBlank
	private String residencePhoto;
	
	private String residenceTranslate;
	
	private Byte paperType;
	
	public UserResidence dtoToDomain(ResidenceAuthenticationDto dto) {
		UserResidence userResidence = new UserResidence();
		userResidence.setUid(dto.getUid());
		userResidence.setCountryId(dto.getCountryId());
		userResidence.setCountry(dto.getCountry());
		userResidence.setCity(dto.getCity());
		userResidence.setAddress(dto.getAddress());
		userResidence.setPostcode(dto.getPostCode());
		userResidence.setResidencePhoto(dto.getResidencePhoto());
		userResidence.setResidenceTranslate(dto.getResidenceTranslate());
		userResidence.setPaperType(null== dto.getPaperType()?0:dto.getPaperType());
		return userResidence;
	}
}
