
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.neusoft.mid.cloong.portmap.webservice;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.6
 * 2018-05-15T14:13:33.604+08:00
 * Generated source version: 2.7.6
 * 
 */

@javax.jws.WebService(
                      serviceName = "PortMapServiceImplService",
                      portName = "PortMapServiceImplPort",
                      targetNamespace = "http://service.rp.privatecloud.mid.neusoft.com/",
                      wsdlLocation = "file:/D:/sccomp/test/src/pc.wsdl",
                      endpointInterface = "PortMapService")
                      
public class PortMapServiceImpl implements PortMapService {

    private static final Logger LOG = Logger.getLogger(PortMapServiceImpl.class.getName());

    /* (non-Javadoc)
     * @see PortMapService#createPortMap(CreatePortMap  parameters )*
     */
    public CreatePortMapResponse createPortMap(CreatePortMap parameters) { 
        LOG.info("Executing operation createPortMap");
        System.out.println(parameters);
        try {
            CreatePortMapResponse _return = new CreatePortMapResponse();
            PortMapResp _returnPortMapResp = new PortMapResp();
            _returnPortMapResp.setResultCode("ResultCode-1927724841");
            _returnPortMapResp.setResultDesc("ResultDesc864414137");
            _return.setPortMapResp(_returnPortMapResp);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see PortMapService#deletePortMap(DeletePortMap  parameters )*
     */
    public DeletePortMapResponse deletePortMap(DeletePortMap parameters) { 
        LOG.info("Executing operation deletePortMap");
        System.out.println(parameters);
        try {
            DeletePortMapResponse _return = new DeletePortMapResponse();
            PortMapResp _returnPortMapResp = new PortMapResp();
            _returnPortMapResp.setResultCode("ResultCode1006723876");
            _returnPortMapResp.setResultDesc("ResultDesc-706859434");
            _return.setPortMapResp(_returnPortMapResp);
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
