
package com.neusoft.mid.cloong.portmap.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cmcccloud.wsdl.cmcc.soap.pc.services package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreatePortMap_QNAME = new QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services", "createPortMap");
    private final static QName _DeletePortMapResponse_QNAME = new QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services", "deletePortMapResponse");
    private final static QName _DeletePortMap_QNAME = new QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services", "deletePortMap");
    private final static QName _CreatePortMapResponse_QNAME = new QName("http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services", "createPortMapResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cmcccloud.wsdl.cmcc.soap.pc.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeletePortMapResponse }
     * 
     */
    public DeletePortMapResponse createDeletePortMapResponse() {
        return new DeletePortMapResponse();
    }

    /**
     * Create an instance of {@link CreatePortMap }
     * 
     */
    public CreatePortMap createCreatePortMap() {
        return new CreatePortMap();
    }

    /**
     * Create an instance of {@link CreatePortMapResponse }
     * 
     */
    public CreatePortMapResponse createCreatePortMapResponse() {
        return new CreatePortMapResponse();
    }

    /**
     * Create an instance of {@link DeletePortMap }
     * 
     */
    public DeletePortMap createDeletePortMap() {
        return new DeletePortMap();
    }

    /**
     * Create an instance of {@link PortMapResp }
     * 
     */
    public PortMapResp createPortMapResp() {
        return new PortMapResp();
    }

    /**
     * Create an instance of {@link DeletePortMapReq }
     * 
     */
    public DeletePortMapReq createDeletePortMapReq() {
        return new DeletePortMapReq();
    }

    /**
     * Create an instance of {@link FwQuintuple }
     * 
     */
    public FwQuintuple createFwQuintuple() {
        return new FwQuintuple();
    }

    /**
     * Create an instance of {@link CreatePortMapReq }
     * 
     */
    public CreatePortMapReq createCreatePortMapReq() {
        return new CreatePortMapReq();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePortMap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services", name = "createPortMap")
    public JAXBElement<CreatePortMap> createCreatePortMap(CreatePortMap value) {
        return new JAXBElement<CreatePortMap>(_CreatePortMap_QNAME, CreatePortMap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePortMapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services", name = "deletePortMapResponse")
    public JAXBElement<DeletePortMapResponse> createDeletePortMapResponse(DeletePortMapResponse value) {
        return new JAXBElement<DeletePortMapResponse>(_DeletePortMapResponse_QNAME, DeletePortMapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePortMap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services", name = "deletePortMap")
    public JAXBElement<DeletePortMap> createDeletePortMap(DeletePortMap value) {
        return new JAXBElement<DeletePortMap>(_DeletePortMap_QNAME, DeletePortMap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePortMapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.CMCCCloud.com/wsdl/CMCC/SOAP/pc/services", name = "createPortMapResponse")
    public JAXBElement<CreatePortMapResponse> createCreatePortMapResponse(CreatePortMapResponse value) {
        return new JAXBElement<CreatePortMapResponse>(_CreatePortMapResponse_QNAME, CreatePortMapResponse.class, null, value);
    }

}
