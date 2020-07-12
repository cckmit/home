/*******************************************************************************
 * @(#)CancelVirFWServiceReq.java 2018年5月9日
 *
 * Copyright 2018 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.vfw.core;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**
 * @author <a href="mailto:zhang.ge@neusoft.com"> zhang.ge </a>
 * @version $Revision 1.1 $ 2018年5月9日 下午2:22:23
 */
public class CancelVirFWServiceReq extends RPPBaseReq implements Serializable {

    private String virfwid;

    public String getVirfwid() {
        return virfwid;
    }

    public void setVirfwid(String virfwid) {
        this.virfwid = virfwid;
    }
}
