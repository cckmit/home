package com.neusoft.mid.comp.boss.ftp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReadFTPFile {

	private Logger logger = LoggerFactory.getLogger(ReadFTPFile.class);

	private FTPClient ftpClient = null;

	/**
	 * 去 服务器的FTP路径下上读取文件
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public void readDownloadFileForFTP(String ftpUserName, String ftpPassword, String ftpRemoteAddr, String ftpHost,
			String ftpPort, String ftpLocalAddr) {
		FTPFile[] ff = null;
		logger.info("开始读取绝对路径" + ftpRemoteAddr + "文件!");
		try {
			ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPassword, ftpUserName, Integer.parseInt(ftpPort));
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpRemoteAddr);
			ff = ftpClient.listFiles();
			for (int i = 0; i < ff.length; i++) {
				try {
					downloadFile(ff[i], ftpLocalAddr, ftpRemoteAddr);
					logger.info("download file to local, path is :" + ftpLocalAddr + "/" + ff[i].getName());
				} catch (Exception e) {
					logger.error("下载文件失败！", e);
					logger.error("<" + ff[i].getName() + ">download failed");
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("not found" + ftpRemoteAddr + "file");
		} catch (SocketException e) {
			logger.error("Failed to connect to FTP.", e);
		} catch (IOException e) {
			logger.error("File reading error.", e);
		} finally {
			if (null != ftpClient && ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

	}

	private void downloadFile(FTPFile ftpFile, String relativeLocalPath, String relativeRemotePath) {
		if (ftpFile.isFile()) {
			OutputStream outputStream = null;
			try {
				File entryDir = new File(relativeLocalPath);
				// 如果文件夹路径不存在，则创建文件夹
				if (!entryDir.exists() || !entryDir.isDirectory()) {
					entryDir.mkdirs();
				}
				File locaFile = new File(relativeLocalPath + "/" + ftpFile.getName());
				outputStream = new FileOutputStream(locaFile);
				ftpClient.changeWorkingDirectory(relativeLocalPath);
				ftpClient.enterLocalPassiveMode();
				boolean flag = ftpClient.retrieveFile(ftpFile.getName(), outputStream);
				outputStream.flush();
				outputStream.close();

			} catch (Exception e) {
				logger.error("", e);
			} finally {
				try {
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (IOException e) {
					logger.error("The output file flow is abnormal");
				}
			}
		}

	}

	public void deleteRemoteFile(String ftpUserName, String ftpPassword, String ftpRemoteAddr, String fileName, String ftpHost,
			String ftpPort) {
		try {
			ftpClient = FTPUtil.getFTPClient(ftpHost, ftpPassword, ftpUserName, Integer.parseInt(ftpPort));
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpRemoteAddr);
			ftpClient.deleteFile(ftpRemoteAddr + "/" + fileName);
		} catch (FileNotFoundException e) {
			logger.error("not found" + ftpRemoteAddr + "file");
		} catch (SocketException e) {
			logger.error("Failed to connect to FTP.", e);
		} catch (IOException e) {
			logger.error("File reading error.", e);
		} finally {
			if (null != ftpClient && ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

		
	}
}
