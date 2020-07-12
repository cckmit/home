/*******************************************************************************
 * @(#)FsDeleteReq.java 2018年5月9日
 *
 * Copyright 2018 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.fileStore.core;

import java.io.Serializable;

import com.neusoft.mid.cloong.rpproxy.interfaces.RPPBaseReq;

/**分布式文件存储删除请求
 * @author <a href="mailto:zhang.ge@neusoft.com"> zhang.ge </a>
 * @version $Revision 1.1 $ 2018年5月9日 上午9:12:21
 */
public class DeleteFileStorageReq extends RPPBaseReq implements Serializable{
    
    private String fileStorageID;

    public String getFileStorageID() {
        return fileStorageID;
    }

    public void setFileStorageID(String fileStorageID) {
        this.fileStorageID = fileStorageID;
    }
}
