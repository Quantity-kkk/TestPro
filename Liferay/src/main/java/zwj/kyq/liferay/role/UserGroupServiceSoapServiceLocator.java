/**
 * UserGroupServiceSoapServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package zwj.kyq.liferay.role;

public class UserGroupServiceSoapServiceLocator extends org.apache.axis.client.Service implements zwj.kyq.liferay.role.UserGroupServiceSoapService {

    public UserGroupServiceSoapServiceLocator() {
    }


    public UserGroupServiceSoapServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UserGroupServiceSoapServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Portal_UserGroupService
    private java.lang.String Portal_UserGroupService_address = "http://127.0.0.1:8080/api/axis/Portal_UserGroupService";

    public java.lang.String getPortal_UserGroupServiceAddress() {
        return Portal_UserGroupService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Portal_UserGroupServiceWSDDServiceName = "Portal_UserGroupService";

    public java.lang.String getPortal_UserGroupServiceWSDDServiceName() {
        return Portal_UserGroupServiceWSDDServiceName;
    }

    public void setPortal_UserGroupServiceWSDDServiceName(java.lang.String name) {
        Portal_UserGroupServiceWSDDServiceName = name;
    }

    public zwj.kyq.liferay.role.UserGroupServiceSoap getPortal_UserGroupService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Portal_UserGroupService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPortal_UserGroupService(endpoint);
    }

    public zwj.kyq.liferay.role.UserGroupServiceSoap getPortal_UserGroupService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            zwj.kyq.liferay.role.Portal_UserGroupServiceSoapBindingStub _stub = new zwj.kyq.liferay.role.Portal_UserGroupServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getPortal_UserGroupServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPortal_UserGroupServiceEndpointAddress(java.lang.String address) {
        Portal_UserGroupService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (zwj.kyq.liferay.role.UserGroupServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                zwj.kyq.liferay.role.Portal_UserGroupServiceSoapBindingStub _stub = new zwj.kyq.liferay.role.Portal_UserGroupServiceSoapBindingStub(new java.net.URL(Portal_UserGroupService_address), this);
                _stub.setPortName(getPortal_UserGroupServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Portal_UserGroupService".equals(inputPortName)) {
            return getPortal_UserGroupService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:http.service.portal.liferay.com", "UserGroupServiceSoapService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:http.service.portal.liferay.com", "Portal_UserGroupService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Portal_UserGroupService".equals(portName)) {
            setPortal_UserGroupServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
