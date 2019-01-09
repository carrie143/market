/**
 * OkPayAPIImplementationLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gop.currency.withdraw.gateway.service.okpay.proxy;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

public class OkPayAPIImplementationLocator extends Service implements OkPayAPIImplementation {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OkPayAPIImplementationLocator() {
    }


    public OkPayAPIImplementationLocator(EngineConfiguration config) {
        super(config);
    }

    public OkPayAPIImplementationLocator(String wsdlLoc, QName sName) throws ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_I_OkPayAPI
    private String BasicHttpBinding_I_OkPayAPI_address = "https://api.okpay.com/OkPayAPI";

    public String getBasicHttpBinding_I_OkPayAPIAddress() {
        return BasicHttpBinding_I_OkPayAPI_address;
    }

    // The WSDD service name defaults to the port name.
    private String BasicHttpBinding_I_OkPayAPIWSDDServiceName = "BasicHttpBinding_I_OkPayAPI";

    public String getBasicHttpBinding_I_OkPayAPIWSDDServiceName() {
        return BasicHttpBinding_I_OkPayAPIWSDDServiceName;
    }

    public void setBasicHttpBinding_I_OkPayAPIWSDDServiceName(String name) {
        BasicHttpBinding_I_OkPayAPIWSDDServiceName = name;
    }

    public OkPayAPI getBasicHttpBinding_I_OkPayAPI() throws ServiceException {
       URL endpoint;
        try {
            endpoint = new URL(BasicHttpBinding_I_OkPayAPI_address);
        }
        catch (MalformedURLException e) {
            throw new ServiceException(e);
        }
        return getBasicHttpBinding_I_OkPayAPI(endpoint);
    }

    public OkPayAPI getBasicHttpBinding_I_OkPayAPI(URL portAddress) throws ServiceException {
        try {
            BasicHttpBindingOkPayAPIStub _stub = new BasicHttpBindingOkPayAPIStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_I_OkPayAPIWSDDServiceName());
            return _stub;
        }
        catch (AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_I_OkPayAPIEndpointAddress(String address) {
        BasicHttpBinding_I_OkPayAPI_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(@SuppressWarnings("rawtypes") Class serviceEndpointInterface) throws ServiceException {
        try {
            if (OkPayAPI.class.isAssignableFrom(serviceEndpointInterface)) {
                BasicHttpBindingOkPayAPIStub _stub = new BasicHttpBindingOkPayAPIStub(new java.net.URL(BasicHttpBinding_I_OkPayAPI_address), this);
                _stub.setPortName(getBasicHttpBinding_I_OkPayAPIWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new ServiceException(t);
        }
        throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(QName portName, @SuppressWarnings("rawtypes") Class serviceEndpointInterface) throws ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_I_OkPayAPI".equals(inputPortName)) {
            return getBasicHttpBinding_I_OkPayAPI();
        }
        else  {
            Remote _stub = getPort(serviceEndpointInterface);
            ((Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new QName("https://api.okpay.com", "OkPayAPIImplementation");
    }

    private HashSet<QName> ports = null;

    public Iterator<QName> getPorts() {
        if (ports == null) {
            ports = new HashSet<QName>();
            ports.add(new QName("https://api.okpay.com", "BasicHttpBinding_I_OkPayAPI"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws ServiceException {
        
if ("BasicHttpBinding_I_OkPayAPI".equals(portName)) {
            setBasicHttpBinding_I_OkPayAPIEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(QName portName, String address) throws ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
