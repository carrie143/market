package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.Status;

public class UserTransactionFeeWhiteList {
    private Integer id;

    private Integer uid;

    private Date createDate;

    private Date updateDate;

    private Integer adminId;

    private Status flag;

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

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

	public Status getFlag() {
		return flag;
	}

	public void setFlag(Status flag) {
		this.flag = flag;
	}
    
   
}