package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditFirst;
import com.gop.domain.enums.AuditStatus;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserResidence {
	private String fullName;

	private Integer id;

	private Integer uid;

	private String countryId;

	private String country;

	private String city;

	private String address;

	private String postcode;

	private String residencePhoto;

	private String residenceTranslate;

	private Integer auditUid;

	private Date auditDate;
	private AuditDealStatus status;

	private AuditStatus auditStatus;

	private AuditFirst auditFirst;

	private String auditMessageId;

	private String auditMessage;

	private Date createDate;

	private Date updateDate;

	private Byte paperType;

	public void setPaperType(Byte paperType) {
		this.paperType = paperType;
	}

	public Byte getPaperType() {
		return paperType;
	}

	// 非数据库对应字段
	private String email;

}