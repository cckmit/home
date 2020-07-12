/**
 * 
 */
package com.neusoft.mid.cloong.vfw.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPApplyVirfwOperationRequst;
import com.neusoft.mid.cloong.rpproxy.interfaces.virfw.RPPApplyVirfwOperationResponse;

/**
 * @author Administrator
 *
 */
public interface CreateVirFW {
	
	public RPPApplyVirfwOperationResponse createVirFw(RPPApplyVirfwOperationRequst req);
}
