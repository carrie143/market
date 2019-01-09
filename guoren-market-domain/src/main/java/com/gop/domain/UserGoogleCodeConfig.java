package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.UserGoogleCodeFlagType;

public class UserGoogleCodeConfig {
    private Integer id;

    private Integer uid;

    private UserGoogleCodeFlagType flag;

    private Date createDate;

    private Date updateDate;
    
	private String secretCode;
	
	private Date resetDate;
	
	public Date getResetDate() {
		return resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public UserGoogleCodeFlagType getFlag() {
        return flag;
    }

    public void setFlag(UserGoogleCodeFlagType flag) {
        this.flag = flag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}