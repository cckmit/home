package com.neusoft.mid.cloong.rpproxy.interfaces.vfw;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseResp;

public class RPPAddVirFWStrategyResp extends RPPBaseResp implements Serializable {
	
 private String resultCode;
    
    private String resultDesc;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
	
}
