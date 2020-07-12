/*******************************************************************************
 * @(#)ExportResPortAction.java 2015-02-16
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.resView.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.ProcessFileName;
import com.neusoft.mid.cloong.identity.bean.PublicIpBean;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.common.SubZero;
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.EbsInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.FwInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.MiniPmInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.MiniPmParInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.PmInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.RaidInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.RtInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.SwInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.VmInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-资源视图-所有资源-导出所有资源详情
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-02-16 上午09:27:16
 */
@SuppressWarnings("unchecked")
public class ExportAllResAction extends ResourceManagementBaseAction {
	private static final long serialVersionUID = 2521336013204349708L;

	private static LogService logger = LogService
			.getLogger(ExportAllResAction.class);

	private XSSFWorkbook workbook;
	
	private XSSFCellStyle cellStyle ;
	
	private Font font;

	public String execute() {
		try {
			String url = Thread.currentThread().getContextClassLoader()
					.getResource("/").toString();
			int last = url.toString().indexOf("WEB-INF");
			url = url.substring(5, last - 1) + File.separator + "report"
					+ File.separator;
			String path = URLDecoder.decode(url, "UTF-8");
			export(path, "resView_allRes.xlsx");
		} catch (Exception e) {
			logger.info("导出数据时出错! Cause: " + e, e);
			this.addActionError("导出数据时出错!");
			return ConstantEnum.ERROR.toString();
		}
		return null;
	}

	private void export(String filePath, String fileName) throws IOException,
			Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
        String nName = ProcessFileName.processFileName(request,"资源视图_所有资源_资源详情_");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String time = df.format(new Date());// new Date()为获取当前系统时间
		String newName = nName + time + ".xlsx";
		response.setHeader("Content-Disposition", "attachment; filename=" + new String(newName));
		response.setContentType("application/octet-stream");
		response.setContentType("application/msexcel");// 定义输出类型

		File file = null;
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try {
			out = new BufferedOutputStream(response.getOutputStream());
			downloadExcel(filePath + fileName, out);

			file = new File(filePath + fileName);
			in = new BufferedInputStream(new FileInputStream(file));
			byte[] b = new byte[1024];
			int len;
			while ((len = in.read(b)) > 0) {
				out.write(b, 0, len);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * <p>
	 * Description:导出Excel
	 * </p>
	 */
	@SuppressWarnings("deprecation")
	private void downloadExcel(String path, BufferedOutputStream out)
			throws Exception {
		try {
			// 根据path将新的sheet页建在已有模板上
			workbook = new XSSFWorkbook(path);
			setFont();
			setTradeLine();//设置换行
			setPmSheet(0);
			setVmSheet(1);
			setEbsSheet(2);
			/*
			 * setMinipmSheet(3); setMinipmparSheet(4);
			 */
			setRaidSheet(3);
			setSwSheet(4);
			setRtSheet(5);
			setFwSheet(6);
			workbook.write(out);
		} catch (Exception e) {
			logger.info("导出数据时出错! Cause: " + e, e);
			throw new Exception(e);
		}
	}

	private void setPmSheet(int num) throws SQLException {
		Sheet sheet = workbook.getSheetAt(num);// 获取物理机sheet
		List<PmInfo> list = ibatisDAO.getData("queryPmInfo", null);
		if (list.size() == 0) {
			sheet.removeRow(sheet.getRow(3));
		}
		for (int i = 0; i < list.size(); i++) {
			PmInfo info = list.get(i);
			if (i > 0) {
				Row newRow = sheet.createRow(i + 3);
				copyRow(sheet.getRow(3), newRow);
			}

			Row row = sheet.getRow(i + 3);
			if (row != null) {
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getPmId());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getPmName());
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(info.getResPoolName());
				Cell cell4 = row.getCell(4);
				cell4.setCellValue(info.getResPoolPartName());
				Cell cell5 = row.getCell(5);
				cell5.setCellValue(info.getMachinerRoomName());
				Cell cell6 = row.getCell(6);
				cell6.setCellValue(info.getCabinetName());
				Cell cell7 = row.getCell(7);
				cell7.setCellValue(info.getAppName());
				Cell cell8 = row.getCell(8);
				cell8.setCellValue(info.getOsType());
				Cell cell9 = row.getCell(9);
				cell9.setCellValue(info.getVendorName());
				Cell cell10 = row.getCell(10);
				cell10.setCellValue(info.getServerType());
				Cell cell11 = row.getCell(11);
				cell11.setCellValue(info.getEthadaType());
				Cell cell12 = row.getCell(12);
				String pmStateText = "";
				if ("0".equals(info.getPmState())) {
					pmStateText = "未分配";
				} else if ("1".equals(info.getPmState())) {
					pmStateText = "已分配";
				} else {
					pmStateText = "不可用";
				}
				cell12.setCellValue(pmStateText);
				Cell cell13 = row.getCell(13);
				cell13.setCellValue(info.getIp());
				Cell cell14 = row.getCell(14);
				cell14.setCellValue(info.getCpuNum());
				Cell cell15 = row.getCell(15);
				cell15.setCellValue(info.getCpuFrequency());
				Cell cell16 = row.getCell(16);
				cell16.setCellValue(info.getCpuType());
				Cell cell17 = row.getCell(17);
				cell17.setCellValue(info.getNucNumPerCpu());
				Cell cell18 = row.getCell(18);
				if (info.getMemorySize() != null) {
					cell18.setCellValue(SubZero.subZero(info.getMemorySize()));
				} else {
					cell18.setCellValue("");
				}
				Cell cell19 = row.getCell(19);
				cell19.setCellValue(info.getDiskSize());
			}
		}
	}
//TODO 导出虚拟机部分

	private void setVmSheet(int num) throws SQLException {
		Sheet sheet = workbook.getSheetAt(num);// 获取虚拟机sheet
		List<VmInfo> list = ibatisDAO.getData("queryVmInfo", null);
		//查询所用公网信息.
		List<String> privateIpList = new ArrayList<String>();
		setAllPrivateIpValue(list,privateIpList);
		List<PublicIpBean> publicList = ibatisDAO.getData("queryPublicIp", privateIpList);
		setAllPublicIpValue(list,publicList);
		
		if (list.size() == 0) {
			sheet.removeRow(sheet.getRow(3));
		}
		for (int i = 0; i < list.size(); i++) {
			VmInfo info = list.get(i);
			if (i > 0) {
				Row newRow = sheet.createRow(i + 3);
				copyRow(sheet.getRow(3), newRow);
			}

			Row row = sheet.getRow(i + 3);
			if (row != null) {
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getVmId());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getVmName());
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(info.getResPoolName());
				Cell cell4 = row.getCell(4);
				cell4.setCellValue(info.getResPoolPartName());
				Cell cell5 = row.getCell(5);
				cell5.setCellValue(info.getPmName());
				Cell cell6 = row.getCell(6);
				cell6.setCellValue(info.getAppName());
				Cell cell7 = row.getCell(7);
				cell7.setCellValue(info.getVmOs());
				Cell cell8 = row.getCell(8);
				cell8.setCellValue(info.getOsType());
				Cell cell9 = row.getCell(9);
				cell9.setCellValue(info.getRunTime());
				Cell cell10 = row.getCell(10);
				if(info.getPrivateIp().contains(",")){
					cell10.setCellStyle(cellStyle);
					info.setPrivateIp(info.getPrivateIp().replace(",", " "));
				}
				cell10.setCellValue( info.getPrivateIp());
				
				Cell cell11 = row.getCell(11);//公网IP.
				if(StringUtils.isNotEmpty(info.getPublicIp())){
					if(info.getPublicIp().contains(",")){
						cell11.setCellStyle(cellStyle);
						info.setPublicIp(info.getPublicIp().replace(",", " "));
					}
				}else{
					cell11.setCellStyle(cellStyle);
					info.setPublicIp(info.getPublicIp());
				}
				
				cell11.setCellValue(info.getPublicIp());
				Cell cell12 = row.getCell(12);
				cell12.setCellValue(info.getCpuNum());
				Cell cell13 = row.getCell(13);
				cell13.setCellValue(info.getCpuFrequency());
				Cell cell14 = row.getCell(14);
				if (info.getMemorySize() != null) {
					cell14.setCellValue(SubZero.subZero(info.getMemorySize()));
				} else {
					cell14.setCellValue("");
				}
				Cell cell15 = row.getCell(15);
				cell15.setCellValue(info.getDiskSize());
			}
		}
	}

	/**
 * @param list
 * @param privateIpList
 */
private void setAllPrivateIpValue(List<VmInfo> list, List<String> privateIpList) {
		for(VmInfo vm:list){
				String privateIp = vm.getPrivateIp();
				String[] ipArray ;
			if(StringUtils.isNotEmpty(privateIp)){
					//判断是否多少多个值
				if(privateIp.contains(",")){
					ipArray = privateIp.split(",");
					for(int i=0;i<ipArray.length;i++){
						if(!privateIpList.contains(ipArray[i])){
								privateIpList.add(ipArray[i]);
						}
					}
				}else{
					privateIpList.add(privateIp);
				}
			}
		}
}
/**
 * 设置公网IP值.
 * @param list
 * @param publicList
 */
private void setAllPublicIpValue(List<VmInfo> list, List<PublicIpBean> publicList ) {
	
		for(VmInfo vm:list){
			String privateIP = vm.getPrivateIp();
			if(StringUtils.isNotEmpty(privateIP)){
				if(privateIP.contains(",")){
					//多个ip.
					String[] ipArray = privateIP.split(",");
					StringBuffer sb = new StringBuffer();
					for(int i=0;i<ipArray.length;i++){
						if(i==ipArray.length-1){
							sb.append(getPublicIp(ipArray[i],publicList));
						}else{
							sb.append(getPublicIp(ipArray[i],publicList)).append(",");
						}
					}
					vm.setPublicIp(sb.toString());
				}else{
					//单个IP.
					vm.setPublicIp(getPublicIp(privateIP,publicList));
				}
			}
		}
}
	
	private String getPublicIp(String ip,List<PublicIpBean> publicList ){
		 PublicIpBean publicIpBean = new PublicIpBean();
		 publicIpBean.setIp(ip);
		 if(publicList.contains(publicIpBean)){
			 int i = publicList.indexOf(publicIpBean);
			 PublicIpBean bean = publicList.get(i);
			return bean.getPublicIp();
		}
		 return " ";
	}


	private void setEbsSheet(int num) throws SQLException {
		Sheet sheet = workbook.getSheetAt(num);// 获取虚拟硬盘机sheet
		List<EbsInfo> list = ibatisDAO.getData("queryEbsInfo", null);
		if (list.size() == 0) {
			sheet.removeRow(sheet.getRow(3));
		}
		for (int i = 0; i < list.size(); i++) {
			EbsInfo info = list.get(i);
			if (i > 0) {
				Row newRow = sheet.createRow(i + 3);
				copyRow(sheet.getRow(3), newRow);
			}

			Row row = sheet.getRow(i + 3);
			if (row != null) {
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getEbsId());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getEbsName());
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(info.getResPoolName());
				Cell cell4 = row.getCell(4);
				cell4.setCellValue(info.getVmName());
				Cell cell5 = row.getCell(5);
				cell5.setCellValue(info.getAppName());
				Cell cell6 = row.getCell(6);
				cell6.setCellValue(info.getDiskSize());
			}
		}
	}

	@SuppressWarnings("unused")
	private void setMinipmSheet(int num) throws SQLException {
		Sheet sheet = workbook.getSheetAt(num);// 获取小型机sheet
		List<MiniPmInfo> list = ibatisDAO.getData("queryMiniPmInfo", null);
		if (list.size() == 0) {
			sheet.removeRow(sheet.getRow(3));
		}
		for (int i = 0; i < list.size(); i++) {
			MiniPmInfo info = list.get(i);
			if (i > 0) {
				Row newRow = sheet.createRow(i + 3);
				copyRow(sheet.getRow(3), newRow);
			}

			Row row = sheet.getRow(i + 3);
			if (row != null) {
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getMiniPmId());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getMiniPmName());
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(info.getResPoolName());
				Cell cell4 = row.getCell(4);
				cell4.setCellValue(info.getResPoolPartName());
				Cell cell5 = row.getCell(5);
				cell5.setCellValue(info.getMachinerRoomName());
				Cell cell6 = row.getCell(6);
				cell6.setCellValue(info.getCabinetName());
				Cell cell7 = row.getCell(7);
				cell7.setCellValue(info.getAppName());
				Cell cell8 = row.getCell(8);
				cell8.setCellValue(info.getOsType());
				Cell cell9 = row.getCell(9);
				cell9.setCellValue(info.getVendorName());
				Cell cell10 = row.getCell(10);
				cell10.setCellValue(info.getServerType());
				Cell cell11 = row.getCell(11);
				cell11.setCellValue(info.getRunTime());
				Cell cell12 = row.getCell(12);
				cell12.setCellValue(info.getIp());
				Cell cell13 = row.getCell(13);
				cell13.setCellValue(info.getCpuNum());
				Cell cell14 = row.getCell(14);
				cell14.setCellValue(info.getCpuFrequency());
				Cell cell15 = row.getCell(15);
				cell15.setCellValue(info.getCpuType());
				Cell cell16 = row.getCell(16);
				if (info.getMemorySize() != null) {
					cell16.setCellValue(SubZero.subZero(info.getMemorySize()));
				} else {
					cell16.setCellValue("");
				}
				Cell cell17 = row.getCell(17);
				cell17.setCellValue(info.getDiskSize());
			}
		}
	}

	@SuppressWarnings("unused")
	private void setMinipmparSheet(int num) throws SQLException {
		Sheet sheet = workbook.getSheetAt(num);// 获取小型机分区sheet
		List<MiniPmParInfo> list = ibatisDAO
				.getData("queryMiniPmParInfo", null);
		if (list.size() == 0) {
			sheet.removeRow(sheet.getRow(3));
		}
		for (int i = 0; i < list.size(); i++) {
			MiniPmParInfo info = list.get(i);
			if (i > 0) {
				Row newRow = sheet.createRow(i + 3);
				copyRow(sheet.getRow(3), newRow);
			}

			Row row = sheet.getRow(i + 3);
			if (row != null) {
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getMiniPmParId());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getMiniPmParName());
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(info.getResPoolName());
				Cell cell4 = row.getCell(4);
				cell4.setCellValue(info.getResPoolPartName());
				Cell cell5 = row.getCell(5);
				cell5.setCellValue(info.getMiniPmName());
				Cell cell6 = row.getCell(6);
				cell6.setCellValue(info.getAppName());
				Cell cell7 = row.getCell(7);
				cell7.setCellValue(info.getVmOs());
				Cell cell8 = row.getCell(8);
				cell8.setCellValue(info.getOsType());
				Cell cell9 = row.getCell(9);
				cell9.setCellValue(info.getRunTime());
				Cell cell10 = row.getCell(10);
				cell10.setCellValue(info.getPrivateIp());
				Cell cell11 = row.getCell(11);
				cell11.setCellValue(info.getCpuNum());
				Cell cell12 = row.getCell(12);
				cell12.setCellValue(info.getCpuFrequency());
				Cell cell13 = row.getCell(13);
				if (info.getMemorySize() != null) {
					cell13.setCellValue(SubZero.subZero(info.getMemorySize()));
				} else {
					cell13.setCellValue("");
				}
				Cell cell14 = row.getCell(14);
				cell14.setCellValue(info.getDiskSize());
			}
		}
	}

	private void setRaidSheet(int num) throws SQLException {
		Sheet sheet = workbook.getSheetAt(num);// 获取存储阵列sheet
		List<RaidInfo> list = ibatisDAO.getData("queryRaidInfo", null);
		if (list.size() == 0) {
			sheet.removeRow(sheet.getRow(3));
		}
		for (int i = 0; i < list.size(); i++) {
			RaidInfo info = list.get(i);
			if (i > 0) {
				Row newRow = sheet.createRow(i + 3);
				copyRow(sheet.getRow(3), newRow);
			}

			Row row = sheet.getRow(i + 3);
			if (row != null) {
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getRaidId());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getRaidName());
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(info.getResPoolName());
				Cell cell4 = row.getCell(4);
				cell4.setCellValue(info.getRelatedEqpId());
				Cell cell5 = row.getCell(5);
				cell5.setCellValue(info.getEqpSerialnum());
				Cell cell6 = row.getCell(6);
				String assetState = info.getAssetState();
				String assetStateText = "";
				if ("1".equals(assetState)) {
					assetStateText = "已使用";
				} else if ("2".equals(assetState)) {
					assetStateText = "未使用";
				} else if ("3".equals(assetState)) {
					assetStateText = "不可用";
				} else if ("4".equals(assetState)) {
					assetStateText = "丢失";
				} else if ("5".equals(assetState)) {
					assetStateText = "待确认";
				} else if ("6".equals(assetState)) {
					assetStateText = "已删除";
				}
				cell6.setCellValue(assetStateText);
				Cell cell7 = row.getCell(7);
				cell7.setCellValue(info.getSaType());
				Cell cell8 = row.getCell(8);
				String assetSlaType = info.getAssetSlaType();
				String assetSlaTypeText = "";
				if ("1".equals(assetSlaType)) {
					assetSlaTypeText = "提供服务";
				} else if ("2".equals(assetSlaType)) {
					assetSlaTypeText = "平台自服务";
				}
				cell8.setCellValue(assetSlaTypeText);
				Cell cell9 = row.getCell(9);
				cell9.setCellValue(info.getVendorName());
				Cell cell10 = row.getCell(10);
				cell10.setCellValue(info.getSaIp());
				Cell cell11 = row.getCell(11);
				cell11.setCellValue(info.getSaMicrocodeVer());
				Cell cell12 = row.getCell(12);
				cell12.setCellValue(info.getSaCapacity());
				Cell cell13 = row.getCell(13);
				cell13.setCellValue(info.getCacheCapacity());
				Cell cell14 = row.getCell(14);
				cell14.setCellValue(info.getDiskSpecification());
				Cell cell15 = row.getCell(15);
				cell15.setCellValue(info.getDiskIds());
				Cell cell16 = row.getCell(16);
				cell16.setCellValue(info.getDiskAdaptorId());
				Cell cell17 = row.getCell(17);
				cell17.setCellValue(info.getDiskAdaptorType());
				Cell cell18 = row.getCell(18);
				cell18.setCellValue(info.getHbaIds());
				Cell cell19 = row.getCell(19);
				cell19.setCellValue(info.getHbaTypes());
				Cell cell20 = row.getCell(20);
				cell20.setCellValue(info.getHbaNum());
			}
		}
	}

	private void setSwSheet(int num) throws SQLException {
		Sheet sheet = workbook.getSheetAt(num);// 获取交换机sheet
		List<SwInfo> list = ibatisDAO.getData("querySwInfo", null);
		if (list.size() == 0) {
			sheet.removeRow(sheet.getRow(3));
		}
		for (int i = 0; i < list.size(); i++) {
			SwInfo info = list.get(i);
			if (i > 0) {
				Row newRow = sheet.createRow(i + 3);
				copyRow(sheet.getRow(3), newRow);
			}

			Row row = sheet.getRow(i + 3);
			if (row != null) {
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getSwitchId());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getSwitchName());
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(info.getResPoolName());
				Cell cell4 = row.getCell(4);
				cell4.setCellValue(info.getSwitchSerialnum());
				Cell cell5 = row.getCell(5);
				cell5.setCellValue(info.getSwitchType());
				Cell cell6 = row.getCell(6);
				cell6.setCellValue(info.getVendorName());
				Cell cell7 = row.getCell(7);
				cell7.setCellValue(info.getSwVersion());
				Cell cell8 = row.getCell(8);
				cell8.setCellValue(info.getSwitchIp());
				Cell cell9 = row.getCell(9);
				String assetState = info.getAssetState();
				String assetStateText = "";
				if ("1".equals(assetState)) {
					assetStateText = "已使用";
				} else if ("2".equals(assetState)) {
					assetStateText = "未使用";
				} else if ("3".equals(assetState)) {
					assetStateText = "不可用";
				} else if ("4".equals(assetState)) {
					assetStateText = "丢失";
				} else if ("5".equals(assetState)) {
					assetStateText = "待确认";
				} else if ("6".equals(assetState)) {
					assetStateText = "已删除";
				}
				cell9.setCellValue(assetStateText);
				Cell cell10 = row.getCell(10);
				String assetOriginType = info.getAssetOriginType();
				String assetOriginTypeText = "";
				if ("1".equals(assetOriginType)) {
					assetOriginTypeText = "自产";
				} else if ("2".equals(assetOriginType)) {
					assetOriginTypeText = "外购";
				} else if ("3".equals(assetOriginType)) {
					assetOriginTypeText = "借入";
				} else if ("4".equals(assetOriginType)) {
					assetOriginTypeText = "订单";
				} else if ("5".equals(assetOriginType)) {
					assetOriginTypeText = "第三方监控源";
				} else if ("6".equals(assetOriginType)) {
					assetOriginTypeText = "可自义";
				}
				cell10.setCellValue(assetOriginTypeText);
				Cell cell11 = row.getCell(11);
				String assetSlaType = info.getAssetSlaType();
				String assetSlaTypeText = "";
				if ("1".equals(assetSlaType)) {
					assetSlaTypeText = "提供服务";
				} else if ("2".equals(assetSlaType)) {
					assetSlaTypeText = "平台自服务";
				}
				cell11.setCellValue(assetSlaTypeText);
			}
		}
	}

	private void setRtSheet(int num) throws SQLException {
		Sheet sheet = workbook.getSheetAt(num);// 获取路由器sheet
		List<RtInfo> list = ibatisDAO.getData("queryRtInfo", null);
		if (list.size() == 0) {
			sheet.removeRow(sheet.getRow(3));
		}
		for (int i = 0; i < list.size(); i++) {
			RtInfo info = list.get(i);
			if (i > 0) {
				Row newRow = sheet.createRow(i + 3);
				copyRow(sheet.getRow(3), newRow);
			}

			Row row = sheet.getRow(i + 3);
			if (row != null) {
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getRouterId());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getRouterName());
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(info.getResPoolName());
				Cell cell4 = row.getCell(4);
				cell4.setCellValue(info.getRouterType());
				Cell cell5 = row.getCell(5);
				cell5.setCellValue(info.getSwVersion());
				Cell cell6 = row.getCell(6);
				cell6.setCellValue(info.getRouterSerialnum());
				Cell cell7 = row.getCell(7);
				cell7.setCellValue(info.getVendorName());
				Cell cell8 = row.getCell(8);
				cell8.setCellValue(info.getRouterIp());
				Cell cell9 = row.getCell(9);
				String assetOriginType = info.getAssetOriginType();
				String assetOriginTypeText = "";
				if ("1".equals(assetOriginType)) {
					assetOriginTypeText = "自产";
				} else if ("2".equals(assetOriginType)) {
					assetOriginTypeText = "外购";
				} else if ("3".equals(assetOriginType)) {
					assetOriginTypeText = "借入";
				} else if ("4".equals(assetOriginType)) {
					assetOriginTypeText = "订单";
				} else if ("5".equals(assetOriginType)) {
					assetOriginTypeText = "第三方监控源";
				} else if ("6".equals(assetOriginType)) {
					assetOriginTypeText = "可自义";
				}
				cell9.setCellValue(assetOriginTypeText);
				Cell cell10 = row.getCell(10);
				String assetState = info.getAssetState();
				String assetStateText = "";
				if ("1".equals(assetState)) {
					assetStateText = "已使用";
				} else if ("2".equals(assetState)) {
					assetStateText = "未使用";
				} else if ("3".equals(assetState)) {
					assetStateText = "不可用";
				} else if ("4".equals(assetState)) {
					assetStateText = "丢失";
				} else if ("5".equals(assetState)) {
					assetStateText = "待确认";
				} else if ("6".equals(assetState)) {
					assetStateText = "已删除";
				}
				cell10.setCellValue(assetStateText);
				Cell cell11 = row.getCell(11);
				String assetSlaType = info.getAssetSlaType();
				String assetSlaTypeText = "";
				if ("1".equals(assetSlaType)) {
					assetSlaTypeText = "提供服务";
				} else if ("2".equals(assetSlaType)) {
					assetSlaTypeText = "平台自服务";
				}
				cell11.setCellValue(assetSlaTypeText);

			}
		}
	}

	private void setFwSheet(int num) throws SQLException {
		Sheet sheet = workbook.getSheetAt(num);// 获取防火墙sheet
		List<FwInfo> list = ibatisDAO.getData("queryFwInfo", null);
		if (list.size() == 0) {
			sheet.removeRow(sheet.getRow(3));
		}
		for (int i = 0; i < list.size(); i++) {
			FwInfo info = list.get(i);
			if (i > 0) {
				Row newRow = sheet.createRow(i + 3);
				copyRow(sheet.getRow(3), newRow);
			}

			Row row = sheet.getRow(i + 3);
			if (row != null) {
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getFirewallId());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getFirewallName());
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(info.getResPoolName());
				Cell cell4 = row.getCell(4);
				String appIds = info.getAppIds();
				String names = "";
				if (appIds != null && !"".equals(appIds)) {
					String[] appIdAry = appIds.split(";");
					info.setAppIdList(Arrays.asList(appIdAry));
					names = (String) this.ibatisDAO.getSingleRecord(
							"queryAppNames", info);
				}
				cell4.setCellValue(names);
				Cell cell5 = row.getCell(5);
				cell5.setCellValue(info.getFwType());
				Cell cell6 = row.getCell(6);
				cell6.setCellValue(info.getSwVersion());
				Cell cell7 = row.getCell(7);
				cell7.setCellValue(info.getFwSerialnum());
				Cell cell8 = row.getCell(8);
				cell8.setCellValue(info.getVendorName());
				Cell cell9 = row.getCell(9);
				cell9.setCellValue(info.getFwIp());
				Cell cell10 = row.getCell(10);
				String assetOriginType = info.getAssetOriginType();
				String assetOriginTypeText = "";
				if ("1".equals(assetOriginType)) {
					assetOriginTypeText = "自产";
				} else if ("2".equals(assetOriginType)) {
					assetOriginTypeText = "外购";
				} else if ("3".equals(assetOriginType)) {
					assetOriginTypeText = "借入";
				} else if ("4".equals(assetOriginType)) {
					assetOriginTypeText = "订单";
				} else if ("5".equals(assetOriginType)) {
					assetOriginTypeText = "第三方监控源";
				} else if ("6".equals(assetOriginType)) {
					assetOriginTypeText = "可自义";
				}
				cell10.setCellValue(assetOriginTypeText);
				Cell cell11 = row.getCell(11);
				String assetState = info.getAssetState();
				String assetStateText = "";
				if ("1".equals(assetState)) {
					assetStateText = "已使用";
				} else if ("2".equals(assetState)) {
					assetStateText = "未使用";
				} else if ("3".equals(assetState)) {
					assetStateText = "不可用";
				} else if ("4".equals(assetState)) {
					assetStateText = "丢失";
				} else if ("5".equals(assetState)) {
					assetStateText = "待确认";
				} else if ("6".equals(assetState)) {
					assetStateText = "已删除";
				}
				cell11.setCellValue(assetStateText);
				Cell cell12 = row.getCell(12);
				String assetSlaType = info.getAssetSlaType();
				String assetSlaTypeText = "";
				if ("1".equals(assetSlaType)) {
					assetSlaTypeText = "提供服务";
				} else if ("2".equals(assetSlaType)) {
					assetSlaTypeText = "平台自服务";
				}
				cell12.setCellValue(assetSlaTypeText);
				Cell cell13 = row.getCell(13);
				cell13.setCellValue(info.getPortNum());
				Cell cell14 = row.getCell(14);
				cell14.setCellValue(info.getConnectNum());
				Cell cell15 = row.getCell(15);
				cell15.setCellValue(info.getFirewallPolicy());
				Cell cell16 = row.getCell(16);
				cell16.setCellValue(info.getNewConnectNum());
				Cell cell17 = row.getCell(17);
				cell17.setCellValue(info.getThroughput());
			}
		}
	}

	private void copyRow(Row fromRow, Row toRow) {
		for (int i = 1; i < 25; i++) {
			Cell fromCell = fromRow.getCell(i);
			Cell newCell = toRow.createCell(i);
			if (fromCell != null) {
				copyCell(fromCell, newCell);
			}
		}
	}

	private void copyCell(Cell fromCell, Cell newCell) {
		CellStyle fromStyle = fromCell.getCellStyle();
		CellStyle newstyle = workbook.createCellStyle();

		newstyle.setAlignment(fromStyle.getAlignment());// 左右居中
		newstyle.setVerticalAlignment(fromStyle.getVerticalAlignment());// 上下居中

		// 边框的大小
		newstyle.setBorderBottom(fromStyle.getBorderBottom());
		newstyle.setBorderLeft(fromStyle.getBorderLeft());
		newstyle.setBorderRight(fromStyle.getBorderRight());
		newstyle.setBorderTop(fromStyle.getBorderTop());

		// 边框的颜色
		newstyle.setTopBorderColor(fromStyle.getTopBorderColor());
		newstyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
		newstyle.setRightBorderColor(fromStyle.getRightBorderColor());
		newstyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

		// 单元格格式
		newstyle.setDataFormat(fromStyle.getDataFormat());

		// 字体样式
		newstyle.setFont(font);

		newCell.setCellStyle(newstyle);
	}

	private void setFont() {
		font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 10);// 字体大小
		font.setCharSet(XSSFFont.DEFAULT_CHARSET);
	}
	/**
	 * 设置换行
	 */
	private void setTradeLine(){
		cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setFont(font);
		cellStyle.setAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	}
}
