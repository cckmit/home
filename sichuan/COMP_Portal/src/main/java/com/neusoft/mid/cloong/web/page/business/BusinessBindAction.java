/*******************************************************************************
 * @(#)BusinessBindAction.java 2014年2月11日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.business;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.mid.cloong.common.mybatis.BatchVO;
import com.neusoft.mid.cloong.common.util.DateParse;
import com.neusoft.mid.cloong.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.BaseAction;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.CreateResult;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务管理删除业务功能
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年2月11日 下午1:58:37
 */
public class BusinessBindAction extends BaseAction {

    /**
     * 日志记录者
     */
    private static LogService logger = LogService.getLogger(BusinessBindAction.class);

    /**
     * serialVersionUID : 序列化版本号
     */
    private static final long serialVersionUID = 7278321721961855694L;

    /**
     * 创建结果
     */
    private CreateResult result;

    /**
     * 解绑的实例ID
     */
    private String caseId;

    /**
     * 解绑的业务ID
     */
    private String[] businessIds;
    
    /**
     * 实例类型
     */
    private String caseType;

    /**
     * execute Action执行方法
     * @return 结果码
     * @see com.opensymphony.xwork2.ActionSupport#execute()
     */
    @Override
    public String execute() {
        String userId = this.getCurrentUserId();
        try {
            if (businessIds==null){
                throw new Exception("必须选择要绑定的业务！");
            }
            List<BatchVO> voList = genDAOBatchVO();
            this.ibatisDAO.updateBatchData(voList);
        } catch (Exception e) {
            logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
                    MessageFormat.format(getText("business.query.list.fail"), userId), e);
            this.addActionError(MessageFormat.format(getText("business.query.list.fail"), userId));
            result = new CreateResult("fail", "业务解绑失败！" + e.getMessage());
            return ERROR;
        }
        result = new CreateResult("success", "业务解绑成功！");
        return ConstantEnum.SUCCESS.toString();
    }

    /**
     * genDAOBatchVO 生成DAO批处理VO
     * @return       返回批处理VO
     */
    private List<BatchVO> genDAOBatchVO() {
        List<BatchVO> voList = new ArrayList<BatchVO>();
        for (String businessId : this.businessIds) {
            Map<String, String> query = new HashMap<String, String>();
            query.put("businessId", businessId);
            query.put("caseId", this.caseId);
            query.put("caseType", this.caseType);
            query.put("createTime", DateParse.generateDateFormatyyyyMMddHHmmss());//设置创建日期
            BatchVO vo = new BatchVO("ADD", "insertBusinessResBind", query);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 设置businessIds字段数据
     * @param businessIds The businessIds to set.
     */
    public void setBusinessIds(String[] businessIds) {
        this.businessIds = businessIds;
    }

    /**
     * 获取result字段数据
     * @return Returns the result.
     */
    public CreateResult getResult() {
        return result;
    }

    /**
     * 设置caseId字段数据
     * @param caseId The caseId to set.
     */
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    /**
     * 设置caseType字段数据
     * @param caseType The caseType to set.
     */
    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

}
