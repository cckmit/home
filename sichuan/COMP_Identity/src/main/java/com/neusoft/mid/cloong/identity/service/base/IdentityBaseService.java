package com.neusoft.mid.cloong.identity.service.base;

import com.neusoft.mid.cloong.common.mybatis.IbatisDAO;

/** 安全管理service需要使用的基础数据
 * @author <a href="mailto:hejq@neusoft.com">he jiaqi </a>
 * @version $Revision 1.1 $ 2013-2-6 上午10:05:24
 */
public class IdentityBaseService  {
    /**
     * 
     */
    protected IbatisDAO ibatisDAO;

    /**
     * @return Returns the ibatisDAO.
     */
    public IbatisDAO getIbatisDAO() {
        return ibatisDAO;
    }

    /**
     * @param ibatisDAO The ibatisDAO to set.
     */
    public void setIbatisDAO(IbatisDAO ibatisDAO) {
        this.ibatisDAO = ibatisDAO;
    }
}
