package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.AuditStatus;
import com.gop.domain.enums.AuditDealStatus;
import com.gop.domain.enums.AuditFirst;

import lombok.Data;

@Data
public class UserIdentification {
	private String fullName;
	private Integer id;
    private Integer uid;
    private String countryId;
    private String country;
    private String cardType;
    private String cardNo;
    private Date expiredDate;
    private String cardPhoto;
    private String cardHandhold;
    private String cardTranslate;
    private String cardBack;
    private Integer auditUid;
    private Date auditDate;
    private AuditDealStatus status;
    private AuditStatus auditStatus;
    private AuditFirst auditFirst;
    private String auditMessageId;
    private String auditMessage;
    private Date createDate;
    private Date updateDate;
    private String email;
}