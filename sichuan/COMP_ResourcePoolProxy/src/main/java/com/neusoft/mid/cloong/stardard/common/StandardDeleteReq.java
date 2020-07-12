/*******************************************************************************
 * @(#)StandardDeleteReq.java 2014-1-10
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.stardard.common;

import com.neusoft.mid.cloong.info.ReqBodyInfo;

/**
 * 
 * 资源规格删除请求
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-10 下午01:36:37
 */
public class StandardDeleteReq extends ReqBodyInfo {
    
    /**
     * 规格ID
     */
    private String standardId;

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }
    
    

}
