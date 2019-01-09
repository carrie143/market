package com.gop.authentication.dto;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.gop.domain.UserIdentification;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class IdentificationDto {

	private Integer uid;

	@NotBlank
	private String countryId;
	@NotBlank
	private String country;
	@NotBlank
	private String cardType;
	@NotBlank
	private String cardNo;
	@NotBlank
	private Date expiredDate;
	@NotBlank
	private String cardPhoto;
	@NotBlank
	private String cardHandhold;
	private String cardTranslate;

	public IdentificationDto(UserIdentification userIdentification) {
		this.uid = userIdentification.getUid();
		this.countryId = userIdentification.getCountryId();
		this.country= userIdentification.getCountry();
		this.cardType = userIdentification.getCardType();
		this.cardNo = userIdentification.getCardNo();
		this.expiredDate = userIdentification.getExpiredDate();
		this.cardPhoto = userIdentification.getCardPhoto();
		this.cardHandhold = userIdentification.getCardHandhold();
		this.cardTranslate = userIdentification.getCardTranslate();

	}
}
