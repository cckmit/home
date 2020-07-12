/**********************************************************************

 *********
 * @(#)UserOperateLogVo.java Sep 16, 2008
 * 常量类
 * Copyright 2008 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.


 ***********************************************************************

 ********/
package com.neusoft.mid.cloong.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 存放配置文件静态变量
 * @author <a href="mailto:li-lei@neusoft.com">li-lei </a>
 * @version $Revision 1.0 $ 2014年1月14日 上午10:00:50
 */
public final class Constants {

    private static final Log LOG = LogFactory.getLog(Constants.class);

    public static final String SYS_CONFIG_FILE_PATH = "/comm_conf/other/Properties.properties";

    /**
     * Utility classes should not have a public or default constructor
     */
    private Constants() {

    }

    public static Properties prop = null;

    /**
     * 登录失败次数-用于锁定用户操作
     */
    public static int LOGINFAILDNUM;

    /**
     * 锁定次数-用户禁用用户操作
     */
    public static int LOCKNUM;

    /**
     * 动态密码过期配置
     */
    public static int DYMANICCODETIMEOUT;

    /**
     * 自动解锁时间（上次锁定时间和当前系统时间的差）（毫秒）
     */
    public static int LOCKTIME;

    /**
     * 自动解锁时间（上次记录登录错误时间和当前系统时间的差）（毫秒）
     */
    public static int FAILDTIME;

    /**
     * 自动禁用时间（三次锁定时间的时间差）（毫秒）
     */
    public static int NOTUSEDTIME;

    /**
     * 找回密码短信验证码的有效期（分钟）
     */
    public static String SAVETIME = null;

    /**
     * 邮件找回密码的url
     */
    public static String FINDPASSWORDURL = null;

    /**
     * 发送邮件服务器IP
     */
    public static String SERVICEIP = null;

    /**
     * 发送邮件服务器端口
     */
    public static String SERVICEPORT = null;

    /**
     * 邮件服务器登录用户名
     */
    public static String ADDRESSER = null;

    /**
     * 邮件服务器登录密码
     */
    public static String SERVICEPASSWORD = null;

    /**
     * 发送人邮件地址
     */
    public static String EMAIL = null;

    /**
     * 是否是SSL
     */
    public static String ISSSL = null;

    /**
     * 证书位置
     */
    public static String MAILFILEPATH = null;

    /**
     * 是否短信or邮件下发成功通知
     */
    public static String ISMAILORSM = null;

    /**
     * unireport服务访问地址
     */
    public static String HOSTURL = null;
    
    /**
     * 资源池访问统计token
     */
    public static String VISITTOKEN = null;
    
    /**
     * 资源池访问统计visit ID
     */
    public static String VISITID = null;

    static {
        prop = new Properties();
        try {
            prop.load(Constants.class.getClassLoader().getResourceAsStream(
                    "/comm_conf/other/Properties.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        InputStream is = getResourceAsStream();
        if (null != is) {
            Properties prop = new Properties();
            try {
                prop.load(is);
                // 发送时间
                LOGINFAILDNUM = Integer.parseInt(prop.getProperty("loginFaildNum").trim());
                LOCKNUM = Integer.parseInt(prop.getProperty("lockNum").trim());
                LOCKTIME = Integer.parseInt(prop.getProperty("lockTime").trim());
                DYMANICCODETIMEOUT = Integer
                        .parseInt(prop.getProperty("dymaniccodetimeout").trim());
                FAILDTIME = Integer.parseInt(prop.getProperty("faildTime").trim());
                NOTUSEDTIME = Integer.parseInt(prop.getProperty("notUsedTime").trim());
                SAVETIME = prop.getProperty("saveTime").trim();
                FINDPASSWORDURL = prop.getProperty("findPasswordURL").trim();
                SERVICEIP = prop.getProperty("serviceIp").trim();
                SERVICEPORT = prop.getProperty("servicePort").trim();
                ADDRESSER = prop.getProperty("addresser").trim();
                SERVICEPASSWORD = prop.getProperty("servicePassword").trim();
                EMAIL = prop.getProperty("email").trim();
                ISSSL = prop.getProperty("isSSL").trim();
                MAILFILEPATH = prop.getProperty("mail.file.path").trim();
                ISMAILORSM = prop.getProperty("isMailorSM").trim();
                HOSTURL = prop.getProperty("hosturl").trim();
                VISITTOKEN = prop.getProperty("visitToken").trim();
                VISITID = prop.getProperty("visitID").trim();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    private static InputStream getResourceAsStream() {
        URL url = null;
        url = Thread.currentThread().getClass().getResource(SYS_CONFIG_FILE_PATH);
        if (null == url) {
            url = Constants.class.getResource(SYS_CONFIG_FILE_PATH);
        }

        try {
            return (url != null) ? url.openStream() : null;
        } catch (IOException e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        // String filePath = "/conf/other/sys_config.properties";
        // File file = new File(filePath);
        InputStream is = Constants.class
                .getResourceAsStream("/comm_conf/other/Properties.properties");
        Properties prop = new Properties();
        try {
            prop.load(is);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }
}
