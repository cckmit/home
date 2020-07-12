/*******************************************************************************
 * @(#)PMMeterageAction.java 2014年2月13日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.meterage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.neusoft.mid.cloong.common.util.Base64;
import com.neusoft.mid.cloong.common.util.Constants;
import com.neusoft.mid.cloong.common.util.TripleDES;
import com.neusoft.mid.cloong.web.action.BaseAction;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 *  加密参数
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年2月13日 下午7:37:30
 */
public class GetDateParamAction extends BaseAction {

    /**
     * serialVersionUID : 序列号
     */
    private static final long serialVersionUID = 1L;
    /**
     * 日志
     */
    private static LogService logger = LogService.getLogger(GetDateParamAction.class);
    /**
     * 加密参数
     */
    private String encryptParam;
    /**
     * 参数明文
     */
    private String params;
    /**
     * unireport服务地址
     */
    private String hostUrl;
    @Override
    public String execute() throws Exception {
        hostUrl = Constants.HOSTURL;
        encryptParam = getEncryptParams();
        return ConstantEnum.SUCCESS.toString();
    }
    /**
     * 
     *加密参数
     * @return 密文
     */
    private String getEncryptParams() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(date);
        params = params+"&timestamp="+timestamp;
        String encryptDateParam = Base64.encodeToString(TripleDES.encrypt(Base64.decode(
                "tcJt1cJtLPQardwjjC98lOWnwUaKUfQp", false), params.getBytes()), false);
        try {
            encryptDateParam = URLEncoder.encode(encryptDateParam, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("unireport 访问密文生成错误",e);
        }
        return encryptDateParam;
    }



    public String getEncryptParam() {
        return encryptParam;
    }

    public void setEncryptParam(String encryptParam) {
        this.encryptParam = encryptParam;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

}
