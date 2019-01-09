package com.gop.certification.controller.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResidenceAuditDto {
	
	@NotNull
	private Integer id;
	
	@NotBlank
	private String auditStatus;
	
	@NotBlank
	private String auditMessageId;
	
	@NotBlank
	private String auditMessage;
	
}
