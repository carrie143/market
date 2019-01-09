package com.gop.domain;

import java.util.Date;

import com.gop.domain.enums.ManagerSetPwdFlag;

public class ManagerPasswordOperRecord {
    private Integer id;

    private Integer adminId;

    private ManagerSetPwdFlag modifyFlag;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public ManagerSetPwdFlag getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(ManagerSetPwdFlag modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}