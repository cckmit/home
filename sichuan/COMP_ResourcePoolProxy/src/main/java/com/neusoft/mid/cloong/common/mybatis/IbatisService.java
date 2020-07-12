package com.neusoft.mid.cloong.common.mybatis;


public class IbatisService {
    private IbatisDAO ibatisDAO;

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
