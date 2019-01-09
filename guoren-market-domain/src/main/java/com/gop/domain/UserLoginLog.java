package com.gop.domain;

import java.util.Date;

public class UserLoginLog {
    private Integer id;

    private Integer uid;

    private String ipAddress;
    
    private String ipCountry;
    
    private String ipCity;
    
    private Date createDate;

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public void setIpCountry(String ipCountry) {
        this.ipCountry = ipCountry;
    }
    
    public String getIpCountry() {
        return ipCountry;
    }

    public void setIpCity(String ipCity) {
        this.ipCity = ipCity;
    }
    
    public String getIpCity() {
        return ipCity;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}