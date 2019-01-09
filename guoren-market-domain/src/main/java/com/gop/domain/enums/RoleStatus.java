package com.gop.domain.enums;

/**
 * Created by wuyanjie on 2018/4/16.
 */
public enum RoleStatus {
    ENABLE(1),DISABLE(0);

    private int status;
    RoleStatus(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
