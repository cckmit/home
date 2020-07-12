package com.neusoft.mid.comp.boss.ftp.util;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.neusoft.mid.comp.boss.util.DateParse;

@Component
public class FTPUtil {

	private static final Logger log = Logger.getLogger(FTPUtil.class);

	public static FTPClient getFTPClient(String ftpHost, String ftpPassword, String ftpUserName, int ftpPort) {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort); 
			ftpClient.login(ftpUserName, ftpPassword);
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				log.error("Not connect to FTP, user or password is wrong!");
				ftpClient.disconnect();
			} else {
				log.info("FTP conneted successful!");
			}
		} catch (SocketException e) {
			log.info("FTP's IP maybe error, please config succesfully!");
		} catch (IOException e) {
			log.info("FTP's port maybe error, please config succesfully!");
		}
		return ftpClient;
	}
	
	public List<String> getFiles(String filePath) {
		List<String> list = new ArrayList<String>();
		File root = new File(filePath);
		File[] files = root.listFiles();
		if (files.length != 0) {
			for (File file : files) {
				if (!file.isHidden()) {
					if (file.getName().contains("txt") && file.getName().contains(DateParse.generateDateFormatyyyyMMdd())) {
						list.add(file.getName());
					}

				}
			}
		} else {
			return null;
		}
		return list;
	}
}
