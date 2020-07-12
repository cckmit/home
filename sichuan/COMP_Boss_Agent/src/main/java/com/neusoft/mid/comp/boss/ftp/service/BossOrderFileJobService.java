/**
 * 
 */
package com.neusoft.mid.comp.boss.ftp.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.neusoft.mid.comp.boss.exception.BossException;
import com.neusoft.mid.comp.boss.exception.DbException;
import com.neusoft.mid.comp.boss.ftp.util.FTPUtil;
import com.neusoft.mid.comp.boss.ftp.util.ReadFTPFile;
import com.neusoft.mid.comp.boss.mybatis.bean.CompBossOrderT;
import com.neusoft.mid.comp.boss.service.BossOrderService;
import com.neusoft.mid.comp.boss.util.DateParse;

/**
 * @author li-lei
 *
 */
@Service("bossOrderFileJobService")
public class BossOrderFileJobService {

	@Value("${ftp_host}")
	protected String ftpHost;

	@Value("${ftp_username}")
	protected String ftpUname;

	@Value("${ftp_pwd}")
	protected String ftpPwd;

	@Value("${ftp_port}")
	protected String ftpPort;

	@Value("${remote_file_path}")
	protected String ftpRemoteAddr;

	@Value("${local_file_addr}")
	protected String ftpLocalAddr;

	@Autowired
	ReadFTPFile readFtpFile = new ReadFTPFile();

	@Autowired
	FTPUtil ftpUtil = new FTPUtil();
	
	@Autowired
	BossOrderService bossOrderService = new BossOrderService();

	private Logger logger = LoggerFactory.getLogger(BossOrderFileJobService.class);

	public void syncBossFile() throws BossException, IOException, InterruptedException {
		logger.info("Begin to find FTP boss order file, now is " + DateParse.generateDateFormatyyyyMMddHHmmss());
		readFtpFile.readDownloadFileForFTP(ftpUname, ftpPwd, ftpRemoteAddr, ftpHost, ftpPort, ftpLocalAddr);
		List<String> fileList = ftpUtil.getFiles(ftpLocalAddr);
		if (fileList != null && fileList.size() != 0) {
			for (String fileName : fileList) {
				String bossFile = ftpLocalAddr + "/" +  fileName;
				FileInputStream fis = new FileInputStream(bossFile);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				FileWriter fw = null;
				try {
					String line;
					while ((line = br.readLine()) != null) {
						CompBossOrderT bossOrder = new CompBossOrderT();
						bossOrder.setBossOrderId(line.split(",")[0]);
						bossOrder.setOperateType(line.split(",")[1]);
						bossOrder.setAgreementBegingTime(line.split(",")[2]);
						bossOrder.setAgreementEndTime(line.split(",")[3]);
						bossOrder.setResourceType(line.split(",")[4]);
						bossOrder.setChargeType(line.split(",")[5]);
						bossOrder.setPrice(line.split(",")[6]);
						bossOrder.setPaymentType(line.split(",")[7]);
						bossOrder.setTimeStamp(DateParse.generateDateFormatyyyyMMddHHmmss());
						bossOrder.setStatus("0"); // 刚同步来的订单是0未操作
						bossOrderService.updateBossOrder(bossOrder, line.split(",")[0]);
						
						logger.info("BOSS order data inser successfully!");
					}
				} catch (DbException e) {
					logger.error("BOSS order data inser failed!", e);
				} finally {
					fis.close();
					isr.close();
					br.close();
					if (null != fw) {
						fw.flush();
						fw.close();
					}

				}
				readFtpFile.deleteRemoteFile(ftpUname, ftpPwd, ftpRemoteAddr, fileName, ftpHost, ftpPort);
			}
			
		}
		
	}
}
