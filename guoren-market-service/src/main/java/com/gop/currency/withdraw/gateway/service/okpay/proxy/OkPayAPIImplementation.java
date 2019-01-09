/**
 * OkPayAPIImplementation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.proxy;

import java.net.URL;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface OkPayAPIImplementation extends Service {
    public String getBasicHttpBinding_I_OkPayAPIAddress();

    public OkPayAPI getBasicHttpBinding_I_OkPayAPI() throws ServiceException;

    public OkPayAPI getBasicHttpBinding_I_OkPayAPI(URL portAddress) throws ServiceException;
}
