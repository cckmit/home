package com.neusoft.mid.cloong.portmap;

import com.neusoft.mid.cloong.rpproxy.interfaces.vm.DeletePortMapRequest;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.PortMapRequest;
import com.neusoft.mid.cloong.rpproxy.interfaces.vm.PortMapResponse;

public interface CreatePortMap {
	public PortMapResponse createPortMap(PortMapRequest req);
	public PortMapResponse deletePortMap(DeletePortMapRequest req);
}
