package com.neusoft.mid.cloong.resourceProxy.standard.pm;

/**
 * 向资源池同步资源规格信息
 * @author <a href="mailto:hejq@neusoft.com">hejiaqi</a>
 * @version $Revision 1.0 $ 2014-1-17 下午02:10:10
 */
public interface PMStandardSynchronize {
    /**
     * @param req 资源规格信息
     * @return 同步状态
     */
    PMStandardSynchronizeResp synchronizeStandard(PMStandardSynchronizeReq req);
}
