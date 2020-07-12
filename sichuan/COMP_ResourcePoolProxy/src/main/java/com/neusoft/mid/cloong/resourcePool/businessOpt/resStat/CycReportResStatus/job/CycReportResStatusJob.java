/*******************************************************************************
 * @(#)CycReportResStatusJob.java 2015年1月13日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.Holder;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.exception.CycReportResStatusException;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.info.ReportResStatusType;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.info.ReportSftpInfo;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job.tasklet.BaseResStatusTasklet;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.bean.RespReslutCode;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.notify.job.exception.TaskletException;
import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.suport.BaseJob;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.Authorization;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.CycReportResStatusReq;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.CycReportResStatusResp;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.FileNameList;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.Response;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.GenPmClass;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.NMSIPM;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMBSINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMFIREWALLINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMMCINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMMCPINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMRAIDINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMROUTERIFINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMROUTERINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMSRVINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMSWITCHIFINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMSWITCHINFO;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.PMVMINFO;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源监控信息上报处理JOB
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年1月13日 下午6:43:02
 */
public class CycReportResStatusJob extends
        BaseJob<CycReportResStatusReq, CycReportResStatusResp, Authorization, Holder<Response>> {

    /**
     * 日志记录
     */
    private static LogService logger = LogService.getLogger(CycReportResStatusJob.class);

    /**
     * SFTP基本信息
     */
    private ReportSftpInfo sftpInfo = new ReportSftpInfo();
    
    /**
     * 二期资源池SFTP基本信息
     */
    private ReportSftpInfo sftpInfoTwo;
    
    /**
     * 三期资源池SFTP基本信息
     */
    private ReportSftpInfo sftpInfoThree;

    /**
     * 本地存储临时文件的缓存路径
     */
    private String localTempPath;
    
    /**
     * 二期本地存储临时文件的缓存路径
     */
    private String localTempPathTwo;
    
    /**
     * 三期本地存储临时文件的缓存路径
     */
    private String localTempPathThree;

    /**
     * 物理机处理器
     */
    private BaseResStatusTasklet<PMSRVINFO> pmSrvTasklet;

    /**
     * 虚拟机处理器
     */
    private BaseResStatusTasklet<PMVMINFO> pmVmTaskLet;

    /**
     * 小型机分区处理器
     */
    private BaseResStatusTasklet<PMMCINFO> pmMcTasklet;

    /**
     * 虚拟机处理器
     */
    private BaseResStatusTasklet<PMMCPINFO> pmMcpTasklet;
    
    /**
     * 阵列处理器
     */
    private BaseResStatusTasklet<PMRAIDINFO> pmRaidTaskLet;
    
    /**
     * 卷处理器
     */
    private BaseResStatusTasklet<PMBSINFO> pmBsTaskLet;
    
    /**
     * 交换机处理器
     */
    private BaseResStatusTasklet<PMSWITCHINFO> pmSwitchTaskLet;
    
    /**
     * 交换机端口处理器
     */
    private BaseResStatusTasklet<PMSWITCHIFINFO> pmSwitchPortTaskLet;

    /**
     * 防火墙处理器
     */
    private BaseResStatusTasklet<PMFIREWALLINFO> pmFirewallTasklet;
    
    /**
     * 路由器处理器
     */
    private BaseResStatusTasklet<PMROUTERINFO> pmRouterTasklet;
    
    /**
     * 路由器端口处理器
     */
    private BaseResStatusTasklet<PMROUTERIFINFO> pmRouterIFTasklet;
    
    private String resPoolId = "";
    
    /**
     * 处理资源池上报的资源监控信息
     * @param parmeter
     * @return
     * @see com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.suport.BaseJob#process(java.lang.Object)
     */
    @Override
    public CycReportResStatusResp process(CycReportResStatusReq req, Authorization authInfo, Holder<Response> respHeader) {
        CycReportResStatusResp resp = new CycReportResStatusResp();
        resp.setFaultString("");
        resPoolId = authInfo.getTransactionID().substring(0, 9);
        //  根据资源池ID装载SFTP基本信息及路径      
        if("CPC-RP-02".equals(resPoolId)){
        	sftpInfo.setIp(sftpInfoTwo.getIp());
        	sftpInfo.setPort(sftpInfoTwo.getPort());
        	sftpInfo.setUserName(sftpInfoTwo.getUserName());
        	sftpInfo.setPassword(sftpInfoTwo.getPassword());
        	sftpInfo.setRootPath(sftpInfoTwo.getRootPath());
        	localTempPath = localTempPathTwo;
        }else{
        	sftpInfo.setIp(sftpInfoThree.getIp());
        	sftpInfo.setPort(sftpInfoThree.getPort());
        	sftpInfo.setUserName(sftpInfoThree.getUserName());
        	sftpInfo.setPassword(sftpInfoThree.getPassword());
        	sftpInfo.setRootPath(sftpInfoThree.getRootPath());
        	localTempPath = localTempPathThree;
        }
        try {
            // 1.校验
            boolean setp1Res = checkMessage(resp, respHeader, authInfo);
            if (!setp1Res) {
                return resp;
            }

            // 2.处理
            processReq(req, resp);
            this.genSuccRespHeader(respHeader, authInfo);
        } catch (Exception e) {
            logger.info("发生未知的异常！", e);
            genRespHeader(respHeader, authInfo, RespReslutCode.ERROR_OHTER.getCode());
            resp.setFaultString("发生异常");
        }
        return resp;
    }

    /**
     * 处理请求
     * @author fengj<br>
     *         2015年1月14日 下午5:50:33
     * @param req 请求
     * @param resp 应答
     * @throws CycReportResStatusException
     * @throws JAXBException
     * @throws TaskletException
     */
    private void processReq(CycReportResStatusReq req, CycReportResStatusResp resp) throws CycReportResStatusException,
            JAXBException, TaskletException {
        int requestID = req.getRequestID();
        int filetype = req.getFiletype();
        FileNameList fileList = req.getFileList();

        logger.info("接收到资源监控上报请求,流水号为:" + requestID + ",文件类型为:" + filetype + ",文件列表为:" + fileList.getFileName());

        switch (filetype) {
        case ReportResStatusType.CM:

            logger.info("接收到CM文件请求,不做处理...");
            break;

        case ReportResStatusType.PM:

            // 1.下载性能文件
            List<String> downloadResStatusFile = downloadResStatusFile(fileList.getFileName(), resp);

            // 2.分析性能文件
            List<NMSIPM> nmsiPMList = praseResPMFile(downloadResStatusFile);

            // 3.解析xml-bean，并入库
            saveToDB(nmsiPMList);

            break;
        case ReportResStatusType.FM:
            logger.info("接收到FM文件请求,不做处理...");
            break;
        default:
            logger.info("接收到无效的资源监控上报请求,不做处理...");
            resp.setFaultString(resp.getFaultString().concat("|接收到无效的资源监控上报请求,不做处理"));
            break;
        }

    }

    /**
     * 将获取到的性能数据存储到数据库中
     * @author fengj<br>
     *         2015年1月23日 下午12:31:56
     * @param nmsiPMList
     * @throws TaskletException
     */
    public void saveToDB(List<NMSIPM> nmsiPMList) throws TaskletException {
        for (NMSIPM nmsipm : nmsiPMList) {
            List<GenPmClass> pmInfoList = nmsipm.getPMSRVINFOOrPMVMINFOOrPMMCPINFO();           
            for (GenPmClass pmInfo : pmInfoList) {
                if (pmInfo instanceof PMVMINFO) {
                    pmVmTaskLet.execute((PMVMINFO) pmInfo, resPoolId);
                } else if (pmInfo instanceof PMSRVINFO) {
                    pmSrvTasklet.execute((PMSRVINFO) pmInfo,resPoolId);
                } else if (pmInfo instanceof PMMCPINFO) {// 小型机
                    pmMcpTasklet.execute((PMMCPINFO) pmInfo,resPoolId);
                } else if (pmInfo instanceof PMMCINFO) {// 小型机分区
                    pmMcTasklet.execute((PMMCINFO) pmInfo,resPoolId);
                } else if (pmInfo instanceof PMRAIDINFO) { // 阵列
                    pmRaidTaskLet.execute((PMRAIDINFO) pmInfo,resPoolId);
                } else if (pmInfo instanceof PMBSINFO) { // 卷
                    pmBsTaskLet.execute((PMBSINFO) pmInfo,resPoolId);
                } else if (pmInfo instanceof PMSWITCHINFO) { // 交换机
                    pmSwitchTaskLet.execute((PMSWITCHINFO) pmInfo,resPoolId);
                } else if (pmInfo instanceof PMSWITCHIFINFO) { // 交换机端口
                    pmSwitchPortTaskLet.execute((PMSWITCHIFINFO) pmInfo,resPoolId);
                } else if (pmInfo instanceof PMFIREWALLINFO) {// 防火墙
                    pmFirewallTasklet.execute((PMFIREWALLINFO) pmInfo,resPoolId);
                } else if (pmInfo instanceof PMROUTERINFO) {// 路由器
                    pmRouterTasklet.execute((PMROUTERINFO) pmInfo,resPoolId);
                } else if (pmInfo instanceof PMROUTERIFINFO) {// 路由器端口
                    pmRouterIFTasklet.execute((PMROUTERIFINFO) pmInfo,resPoolId);
                } else {
                    // 其他资源性能暂时不做处理
                }
            }       
        }
    }

    /**
     * 解析资源性能PM文件
     * @author fengj<br>
     *         2015年1月13日 下午9:20:13
     * @param downloadResStatusFile 性能文件列表
     * @return 解析后的xml-bean列表
     * @throws JAXBException 文件解析异常
     */
    public List<NMSIPM> praseResPMFile(List<String> downloadResStatusFile) throws JAXBException {
        List<NMSIPM> nmsiPMList = new ArrayList<NMSIPM>();
        for (String downloadTmepFile : downloadResStatusFile) {
            File tempFile = new File(downloadTmepFile);
            JAXBContext cxt = JAXBContext.newInstance(NMSIPM.class);
            Unmarshaller unmarshaller = cxt.createUnmarshaller();
            NMSIPM nmsiPM = (NMSIPM) unmarshaller.unmarshal(tempFile);
            nmsiPMList.add(nmsiPM);
        }
        return nmsiPMList;
    }

    /**
     * 下载资源信息文件
     * @author fengj<br>
     *         2015年1月13日 下午8:05:53
     * @param fileList 保存到本地的临时文件列表
     * @param resp 应答消息
     * @throws CycReportResStatusException
     */
    private List<String> downloadResStatusFile(List<String> fileList, CycReportResStatusResp resp)
            throws CycReportResStatusException {
        List<String> localTempFileList = new ArrayList<String>();

        JSch jsch = new JSch();
        Session session = null;
        String localFileName = null;
        try {
            session = jsch.getSession(sftpInfo.getUserName(), sftpInfo.getIp(), sftpInfo.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sftpInfo.getPassword());
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            // 1.根据url解析参数
            sftpChannel.cd(sftpInfo.getRootPath());

            for (String filePath : fileList) {

                try {
                    localFileName = getLocalFile(filePath, localTempPath);
                    logger.info("filePath++++++++++++++"+filePath);
                    logger.info("localFileName++++++++++++++"+localFileName);
                    //String filePath1 = "/home/cm/cpfnan/PMREPORT_PM_BS_INFO_2019-11-11_11_45_00_03035963.xml";
                    //String localFileName1 = "/home/cm/nan/PMREPORT_PM_BS_INFO_2019-11-11_11_45_00_03035963.xml";
                    sftpChannel.get(filePath, localFileName);
                    logger.info("filePath++++++++++++++"+filePath);
                    if (!new File(localFileName).exists()) {
                        String fileNoExistErrorStr = "下载文件[" + filePath + "]失败,可能不存在此文件!";
                        logger.error(fileNoExistErrorStr);
                        resp.setFaultString(resp.getFaultString().concat("|").concat(fileNoExistErrorStr));
                        continue;
                    }
                    localTempFileList.add(localFileName);
                } catch (Exception e) {
                    String fileNoExistErrorStr = "下载文件[" + filePath + "]发生错误,略过此文件!";
                    resp.setFaultString(resp.getFaultString().concat("|").concat(fileNoExistErrorStr));
                    logger.error(fileNoExistErrorStr,e);
                }

            }

            sftpChannel.exit();
            session.disconnect();
            return localTempFileList;
        } catch (Exception e) {
            logger.error("从SFTP[" + sftpInfo + "]下载文件时发生异常!本次获取数据失败", e);
            System.out.println("***********" + e);
            throw new CycReportResStatusException(e);
        }

    }

    /**
     * 创建本地文件路径
     * @author fengj<br>
     *         2015年1月13日 下午7:59:04
     * @param filePath 远程文件全路径
     * @param localTempPath 本地临时文件路径
     * @return 本地临时文件的全路径
     */
    private String getLocalFile(String filePath, String localTempPath) {
        String[] tempSplit = filePath.split("/");
        String fileName = tempSplit[tempSplit.length - 1];
        logger.info("localTempPath++++++++++++++fileName"+localTempPath + fileName);
        return localTempPath + fileName;
    }

    /**
     * 检查消息是否正确
     * @author fengj<br>
     *         2015年1月14日 下午5:46:59
     * @param resp 应答报文
     * @param authInfo 鉴权信息
     * @return 鉴权是否成功
     */
    private boolean checkMessage(CycReportResStatusResp resp, Holder<Response> respHeader, Authorization authInfo) {
        RespReslutCode resCode = this.checkHeaderAndAuth(authInfo.getTransactionID(), authInfo.getZoneID());
        if (!RespReslutCode.SUCCESS.equals(resCode)) {
            this.genRespHeader(respHeader, authInfo, resCode.getCode());
            resp.setFaultString(resCode.getDesc());
            return false;
        }
        return true;
    }

    /**
     * genFailRespHeaderObj 生成失败的头ID
     * @param respResultCode 错误码
     */
    protected void genRespHeader(Holder<Response> respHeader, Authorization authInfo, String respResultCode) {
        respHeader.value.setIDCAccessId(this.parseIDCId(authInfo.getTransactionID()));
        respHeader.value.setTransactionID(authInfo.getTransactionID());
        respHeader.value.setResultCode(respResultCode);
    }

    /**
     * genSuccRespHeaderObj 生成成功的应答报文对象
     * @param respHeader
     * @param authInfo
     */
    protected void genSuccRespHeader(Holder<Response> respHeader, Authorization authInfo) {
        this.genRespHeader(respHeader, authInfo, RespReslutCode.SUCCESS.getCode());
    }

    /** --------------------- SPRING SETTER AREA START ------------------ */
    /**
     * 设置sftpInfo字段数据
     * @param sftpInfo The sftpInfo to set.
     */
    public void setSftpInfo(ReportSftpInfo sftpInfo) {
        this.sftpInfo = sftpInfo;
    }

    /**
     * 设置localTempPath字段数据
     * @param localTempPath The localTempPath to set.
     */
    public void setLocalTempPath(String localTempPath) {
        this.localTempPath = localTempPath;
    }

    /**
     * 设置pmSrvTasklet字段数据
     * @param pmSrvTasklet The pmSrvTasklet to set.
     */
    public void setPmSrvTasklet(BaseResStatusTasklet<PMSRVINFO> pmSrvTasklet) {
        this.pmSrvTasklet = pmSrvTasklet;
    }

    /**
     * 设置pmVmTaskLet字段数据
     * @param pmVmTaskLet The pmVmTaskLet to set.
     */
    public void setPmVmTaskLet(BaseResStatusTasklet<PMVMINFO> pmVmTaskLet) {
        this.pmVmTaskLet = pmVmTaskLet;
    }

    /**
     * 设置pmMcTasklet字段数据
     * @param pmMcTasklet The pmMcTasklet to set.
     */
    public void setPmMcTasklet(BaseResStatusTasklet<PMMCINFO> pmMcTasklet) {
        this.pmMcTasklet = pmMcTasklet;
    }

    /**
     * 设置pmMcpTasklet字段数据
     * @param pmMcpTasklet The pmMcpTasklet to set.
     */
    public void setPmMcpTasklet(BaseResStatusTasklet<PMMCPINFO> pmMcpTasklet) {
        this.pmMcpTasklet = pmMcpTasklet;
    }

    /**
     * 设置pmRaidTaskLet字段数据
     * @param pmRaidTaskLet The pmRaidTaskLet to set.
     */
    public void setPmRaidTaskLet(BaseResStatusTasklet<PMRAIDINFO> pmRaidTaskLet) {
        this.pmRaidTaskLet = pmRaidTaskLet;
    }

    /**
     * 设置pmBsTaskLet字段数据
     * @param pmBsTaskLet The pmBsTaskLet to set.
     */
    public void setPmBsTaskLet(BaseResStatusTasklet<PMBSINFO> pmBsTaskLet) {
        this.pmBsTaskLet = pmBsTaskLet;
    }

    /**
     * 设置pmSwitchTaskLet字段数据
     * @param pmSwitchTaskLet The pmSwitchTaskLet to set.
     */
    public void setPmSwitchTaskLet(BaseResStatusTasklet<PMSWITCHINFO> pmSwitchTaskLet) {
        this.pmSwitchTaskLet = pmSwitchTaskLet;
    }

    /**
     * 设置pmSwitchPortTaskLet字段数据
     * @param pmSwitchPortTaskLet The pmSwitchPortTaskLet to set.
     */
    public void setPmSwitchPortTaskLet(BaseResStatusTasklet<PMSWITCHIFINFO> pmSwitchPortTaskLet) {
        this.pmSwitchPortTaskLet = pmSwitchPortTaskLet;
    }
    
    /**
     * 设置setPmFirewallTasklet字段数据
	 * @param pmFirewallTasklet the pmFirewallTasklet to set
	 */
	public void setPmFirewallTasklet(
			BaseResStatusTasklet<PMFIREWALLINFO> pmFirewallTasklet) {
		this.pmFirewallTasklet = pmFirewallTasklet;
	}

	/**
	 * 设置pmRouterTasklet字段数据
	 * @param pmRouterTasklet the pmRouterTasklet to set
	 */
	public void setPmRouterTasklet(
			BaseResStatusTasklet<PMROUTERINFO> pmRouterTasklet) {
		this.pmRouterTasklet = pmRouterTasklet;
	}

	/**
	 * 设置pmRouterIFTasklet字段数据
	 * @param pmRouterIFTasklet the pmRouterIFTasklet to set
	 */
	public void setPmRouterIFTasklet(
			BaseResStatusTasklet<PMROUTERIFINFO> pmRouterIFTasklet) {
		this.pmRouterIFTasklet = pmRouterIFTasklet;
	}

	public void setSftpInfoTwo(ReportSftpInfo sftpInfoTwo) {
		this.sftpInfoTwo = sftpInfoTwo;
	}

	public void setSftpInfoThree(ReportSftpInfo sftpInfoThree) {
		this.sftpInfoThree = sftpInfoThree;
	}

	public ReportSftpInfo getSftpInfoTwo() {
		return sftpInfoTwo;
	}

	public ReportSftpInfo getSftpInfoThree() {
		return sftpInfoThree;
	}

	public String getLocalTempPathTwo() {
		return localTempPathTwo;
	}

	public void setLocalTempPathTwo(String localTempPathTwo) {
		this.localTempPathTwo = localTempPathTwo;
	}

	public String getLocalTempPathThree() {
		return localTempPathThree;
	}

	public void setLocalTempPathThree(String localTempPathThree) {
		this.localTempPathThree = localTempPathThree;
	}

    /** --------------------- SPRING SETTER AREA END -------------------- **/

}
