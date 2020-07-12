/**
 * 
 */
package com.neusoft.mid.cloong.filestorage;

import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateResponse;

/**
 * @author Administrator
 *
 */
public interface FileStorageExecuter {
	public RPPFileStorageCreateResponse createFileStorage(RPPFileStorageCreateReq req);
}
