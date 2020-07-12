/**
 * Copyright 2014 Neusoft Research Institute. All Right Reserved
 *
 * This file is owned by Neusoft and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Neusoft.
 */
package com.neusoft.mid.cloong.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.*;
import java.util.Date;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * SFTP上传.
 * @author <a href="mailto:zhangyunfeng@neusoft.com">zhangyunfeng</a>
 * @version $Revision 1.0.0 2014-4-15 上午10:22:58
 */
@SuppressWarnings("deprecation")
public class SftpUtil {

    /**
     * log.
     */
    private static LogService logger = LogService.getLogger(SftpUtil.class);

    /**
     * sftp.
     */
    private static ChannelSftp sftp = null;

    /**
     * 创建SFTP连接.
     * @param param 连接参数.
     * @return 连接结果.
     */
    public static ChannelSftp connect(String user,String pwd,String ip,String port) {
        Session session;
        Channel channel;
        JSch jsch = new JSch();
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        try {
            session = jsch.getSession(user, ip, Integer.valueOf(port));
            session.setConfig(config);
            session.setPassword(pwd);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (NumberFormatException e) {
            logger.error("连接sftp异常：", e);
        } catch (JSchException e) {
            logger.error("连接sftp异常：", e);
        }
        return sftp;
    }

    /**
     * 判断虚拟机下面是否存在路径.
     * @param dst 路径.
     */
    public static void cdDir(String dst) {
        if (dst.indexOf('/') == 0) {
            dst = dst.substring(1, dst.length());
        }
        String[] dirs = dst.split("/");
        String dir = "";
        for (int i = 0; i < dirs.length; i++) {
            try {
                dir = dirs[i];
                if (i == 0) {
                    sftp.cd("/" + dirs[i]);
                } else {
                    sftp.cd(dirs[i]);
                }

            } catch (Exception e) {
                createDir(dir);
            }
        }
    }

    /**
     * 创建目录.
     * @param dst 地址.
     */
    public static void createDir(String dst) {
        try {
            sftp.mkdir(dst);
            sftp.cd(dst);
        } catch (Exception e) {
            logger.info("创建路径失败.", e);
        }
    }

    
    /***
     * 返回时间戳.
     * @return
     */
    public static String getTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        return sdf.format(date);
    }
    
    public static  InputStream string2InputStream(String in) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("utf-8"));
		return is;
	}
	
    /**
     * 断开SFTP连接.
     */
    public static void disconnect() throws JSchException {
        if (null != sftp) {
            sftp.disconnect();
            if (null != sftp.getSession()) {
			    sftp.getSession().disconnect();
			}
        }
    }
}
