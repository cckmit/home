/*******************************************************************************
 * @(#)ReportSftpInfo.java 2015年1月13日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.info;

/**
 * TODO 这里请补充该类型的简述说明
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年1月13日 下午7:41:27
 */
public class ReportSftpInfo {

    /**
     * SFTP登陆名
     */
    private String userName;

    /**
     * SFTP密码
     */
    private String password;

    /**
     * SFTP IP地址
     */
    private String ip;

    /**
     * SFTP端口
     */
    private int port;

    /**
     * SFTP根路径
     */
    private String rootPath;

    /**
     * 获取userName字段数据
     * @return Returns the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置userName字段数据
     * @param userName The userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取password字段数据
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置password字段数据
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取ip字段数据
     * @return Returns the ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip字段数据
     * @param ip The ip to set.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取port字段数据
     * @return Returns the port.
     */
    public int getPort() {
        return port;
    }

    /**
     * 设置port字段数据
     * @param port The port to set.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * 获取rootPath字段数据
     * @return Returns the rootPath.
     */
    public String getRootPath() {
        return rootPath;
    }

    /**
     * 设置rootPath字段数据
     * @param rootPath The rootPath to set.
     */
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * toString 输出参数内容
     * @return 参数内容
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ReportSftpInfo[userName=");
        builder.append(userName);
        builder.append(",ip=");
        builder.append(ip);
        builder.append(",port=");
        builder.append(port);
        builder.append(",rootPath=");
        builder.append(rootPath);
        builder.append("]");
        return builder.toString();
    }

}
