package com.neusoft.mid.cloong.web.page.console.vlan;

import com.neusoft.mid.iamp.logger.StatusCode;

public enum VlanStatusCode implements StatusCode{

    createVlanOrderException("52001");
    
    private String code;
    
    VlanStatusCode(String code){
        
    }
    
}
