
package com.gop.mode.vo;


import java.io.Serializable;


/**
 * 转果仁，底层结构体
 * 
 * @author zhengminghai
 * @version 2015年12月30日
 * @see ReceiveMesage
 * @since
 */
public class ReceiveMesage implements Serializable, Cloneable
{

    private static final long serialVersionUID = 2226518756825087659L;

    private String id = "";

    private String fromAccountName = "";

    private String ammount = "";

    private String assetSymbol = "";

    private String toAddress = "";

    private String guid = "";

    public String getId()
    {
        return id;
    }

    public String getGuid()
    {
        return guid;
    }

    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFromAccountName()
    {
        return fromAccountName;
    }

    public void setFromAccountName(String fromAccountName)
    {
        this.fromAccountName = fromAccountName;
    }

    public String getAmmount()
    {
        return ammount;
    }

    public void setAmmount(String ammount)
    {
        this.ammount = ammount;
    }

    public String getAssetSymbol()
    {
        return assetSymbol;
    }

    public void setAssetSymbol(String assetSymbol)
    {
        this.assetSymbol = assetSymbol;
    }

    public String getToAddress()
    {
        return toAddress;
    }

    public void setToAddress(String toAddress)
    {
        this.toAddress = toAddress;
    }

    @Override
    public String toString()
    {
        return "ReceiveMesage [id=" + id + ", fromAccountName=" + fromAccountName + ", ammount="
               + ammount + ", assetSymbol=" + assetSymbol + ", toAddress=" + toAddress + "]";
    }

}
