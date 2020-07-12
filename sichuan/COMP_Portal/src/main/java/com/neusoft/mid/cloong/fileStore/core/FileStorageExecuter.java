/**
 * 
 */
package com.neusoft.mid.cloong.fileStore.core;

import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateReq;
import com.neusoft.mid.cloong.rpproxy.interfaces.filestorage.RPPFileStorageCreateResponse;

/**
 * @author Administrator
 *
 */
public interface FileStorageExecuter {
	public RPPFileStorageCreateResponse createFileStorage(RPPFileStorageCreateReq req);
}
