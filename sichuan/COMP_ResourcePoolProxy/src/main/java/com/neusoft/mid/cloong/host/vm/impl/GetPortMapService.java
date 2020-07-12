package com.neusoft.mid.cloong.host.vm.impl;

import java.net.MalformedURLException;
import java.net.URL;

import com.neusoft.mid.cloong.portmap.webservice.PortMapService;
import com.neusoft.mid.cloong.portmap.webservice.PortMapServiceImplService;

public class GetPortMapService {
		
	public PortMapService getPortMapService(String Url) throws Exception {
		URL wsdlLocation = new URL(Url+"?wsdl");
		PortMapServiceImplService portmapservice = new PortMapServiceImplService(wsdlLocation);
		PortMapService portMapServiceImplPort = portmapservice.getPortMapServiceImplPort();
		return portMapServiceImplPort;
	}
}
