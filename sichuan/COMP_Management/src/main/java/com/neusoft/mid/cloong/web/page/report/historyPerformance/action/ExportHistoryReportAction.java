/*******************************************************************************
 * @(#)ExportResPortAction.java 2015-03-03
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.report.historyPerformance.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.util.ProcessFileName;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.report.historyPerformance.info.HistoryPerformanceInfo;
import com.neusoft.mid.cloong.web.page.resourceManagement.appView.performanceStatistics.action.ExportAppReportAction;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 业务性能统计-导出
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-03-03 上午08:49:16
 */
public class ExportHistoryReportAction extends HistoryBaseAction {
	private static final long serialVersionUID = 2521336013204349708L;

	private static LogService logger = LogService
			.getLogger(ExportAppReportAction.class);

	private XSSFWorkbook workbook;

	private Font font;

	private String appId;

	private String appName;

	private String startDate;

	private String endDate;

	private String startDateForSql;

	private String endDateForSql;

	public String execute() {
		try {
			String url = Thread.currentThread().getContextClassLoader()
					.getResource("/").toString();
			int last = url.toString().indexOf("WEB-INF");
			url = url.substring(5, last - 1) + File.separator + "report"
					+ File.separator;
			String path = URLDecoder.decode(url, "UTF-8");
			export(path, "report_historyReport.xlsx");
		} catch (Exception e) {
			logger.error("导出数据时出错! Cause: " + e, e);
			this.addActionError("导出数据时出错!");
			return ConstantEnum.ERROR.toString();
		}
		return null;
	}

	private void export(String filePath, String fileName) throws IOException,
			Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String nName = ProcessFileName.processFileName(request, "业务性能统计_"
				+ appName);
		String newName = nName + "_" + startDate.replace("-", "") + "~"
				+ endDate.replace("-", "") + ".xlsx";
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(newName));
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
	private void downloadExcel(String path, BufferedOutputStream out)
			throws Exception {
		try {
			historyPerformanceInfo.setStartDate(startDate);
			historyPerformanceInfo.setEndDate(endDate);
			/* 设置查询日期，用于SQL查询（格式：yyyyMMdd） */
			formatDateForSql();
			startDateForSql = historyPerformanceInfo.getStartDate();
			endDateForSql = historyPerformanceInfo.getEndDate();

			// 根据path将新的sheet页建在已有模板上
			workbook = new XSSFWorkbook(path);
			setFont();

			// 物理机
			Sheet cpuSheet2 = workbook.getSheetAt(0);// 获取sheet:物理机_CPU
			Sheet memSheet2 = workbook.getSheetAt(1);// 获取sheet:物理机_内存
			Sheet diskSheet2 = workbook.getSheetAt(2);// 获取sheet:物理机_磁盘
			setSheet(cpuSheet2, memSheet2, diskSheet2, "2");

			// 虚拟机
			Sheet cpuSheet3 = workbook.getSheetAt(3);// 获取sheet:虚拟机_CPU
			Sheet memSheet3 = workbook.getSheetAt(4);// 获取sheet:虚拟机_内存
			Sheet diskSheet3 = workbook.getSheetAt(5);// 获取sheet:虚拟机_磁盘
			setSheet(cpuSheet3, memSheet3, diskSheet3, "3");

			/*
			 * //小型机 Sheet cpuSheet0 = workbook.getSheetAt(6);//获取sheet:小型机_CPU
			 * Sheet memSheet0 = workbook.getSheetAt(7);//获取sheet:小型机_内存 Sheet
			 * diskSheet0 = workbook.getSheetAt(8);//获取sheet:小型机_磁盘
			 * setSheet(cpuSheet0,memSheet0,diskSheet0,"0");
			 * 
			 * //小型机分区 Sheet cpuSheet1 =
			 * workbook.getSheetAt(9);//获取sheet:小型机分区_CPU Sheet memSheet1 =
			 * workbook.getSheetAt(10);//获取sheet:小型机分区_内存 Sheet diskSheet1 =
			 * workbook.getSheetAt(11);//获取sheet:小型机分区_磁盘
			 * setSheet(cpuSheet1,memSheet1,diskSheet1,"1");
			 */

			workbook.write(out);
		} catch (Exception e) {
			logger.error("导出数据时出错! Cause: " + e, e);
			throw new Exception(e);
		}
	}

	private void setSheet(Sheet cpuSheet, Sheet memSheet, Sheet diskSheet,
			String deviceType) throws SQLException {
		HistoryPerformanceInfo obj = new HistoryPerformanceInfo();
		obj.setAppId(appId);
		obj.setStartDate(startDateForSql);
		obj.setEndDate(endDateForSql);
		obj.setDeviceType(deviceType);
		List<HistoryPerformanceInfo> cpuList = ibatisDAO.getData(
				"getCpuHistory", obj);
		setSheetData(cpuSheet, cpuList, "CPU");
		List<HistoryPerformanceInfo> memList = ibatisDAO.getData(
				"getMemHistory", obj);
		setSheetData(memSheet, memList, "MEM");
		List<HistoryPerformanceInfo> diskList = ibatisDAO.getData(
				"getDiskHistory", obj);
		setSheetData(diskSheet, diskList, "DISK");
	}

	private void setSheetData(Sheet sheet, List<HistoryPerformanceInfo> list,
			String type) throws SQLException {
		Row appRow = sheet.getRow(1);// 查询条件
		Cell cell = appRow.getCell(1);
		cell.setCellValue("业务名称：" + appName + "          查询日期:" + startDate
				+ "~" + endDate);

		if (list.size() == 0) {
			sheet.removeRow(sheet.getRow(5));
		}

		for (int i = 0; i < list.size(); i++) {
			HistoryPerformanceInfo info = list.get(i);
			if (i > 0) {
				Row newRow = sheet.createRow(i + 5);
				copyRow(sheet.getRow(5), newRow);
			}

			Row row = sheet.getRow(i + 5);
			if (row != null) {
				if ("CPU".equals(type)) {
					setCpuRow(row, info);
				} else if ("MEM".equals(type)) {
					setMemRow(row, info);
				} else if ("DISK".equals(type)) {
					setDiskRow(row, info);
				}
			}
		}
	}

	private void setCpuRow(Row row, HistoryPerformanceInfo info) {
		Cell cell1 = row.getCell(1);
		Cell cell2 = row.getCell(2);
		Cell cell3 = row.getCell(3);
		Cell cell4 = row.getCell(4);
		Cell cell5 = row.getCell(5);
		Cell cell6 = row.getCell(6);
		Cell cell7 = row.getCell(7);
		Cell cell8 = row.getCell(8);
		Cell cell9 = row.getCell(9);
		Cell cell10 = row.getCell(10);
		Cell cell11 = row.getCell(11);
		cell1.setCellValue(info.getReportDate());
		cell2.setCellValue(formatValue(info.getCpuAve()));
		cell3.setCellValue(formatValue(info.getCpuMax()));
		cell4.setCellValue(info.getCpuMaxId());
		cell5.setCellValue(formatValue(info.getCpuMin()));
		cell6.setCellValue(info.getCpuMinId());
		cell7.setCellValue(info.getDeviceNum());
		cell8.setCellValue(info.getCpuOverNum());
		cell9.setCellValue(formatValue(info.getCpuOverAve()));
		cell10.setCellValue(info.getCpuNotOverNum());
		cell11.setCellValue(formatValue(info.getCpuNotOverAve()));
	}

	private void setMemRow(Row row, HistoryPerformanceInfo info) {
		Cell cell1 = row.getCell(1);
		Cell cell2 = row.getCell(2);
		Cell cell3 = row.getCell(3);
		Cell cell4 = row.getCell(4);
		Cell cell5 = row.getCell(5);
		Cell cell6 = row.getCell(6);
		Cell cell7 = row.getCell(7);
		Cell cell8 = row.getCell(8);
		Cell cell9 = row.getCell(9);
		Cell cell10 = row.getCell(10);
		Cell cell11 = row.getCell(11);
		Cell cell12 = row.getCell(12);
		Cell cell13 = row.getCell(13);
		Cell cell14 = row.getCell(14);
		Cell cell15 = row.getCell(15);
		cell1.setCellValue(info.getReportDate());
		cell2.setCellValue(formatValue(info.getMemAve()));
		cell3.setCellValue(formatValue(info.getMemMax()));
		cell4.setCellValue(info.getMemMaxId());
		cell5.setCellValue(formatData(info.getMemMaxFree()));
		cell6.setCellValue(formatValue(info.getMemMin()));
		cell7.setCellValue(info.getMemMinId());
		cell8.setCellValue(formatData(info.getMemMinFree()));
		cell9.setCellValue(info.getDeviceNum());
		cell10.setCellValue(info.getMemOverNum());
		cell11.setCellValue(formatValue(info.getMemOverAve()));
		cell12.setCellValue(formatData(info.getMemOverFreeAve()));
		cell13.setCellValue(info.getMemNotOverNum());
		cell14.setCellValue(formatValue(info.getMemNotOverAve()));
		cell15.setCellValue(formatData(info.getMemNotOverFreeAve()));
	}

	private void setDiskRow(Row row, HistoryPerformanceInfo info) {
		Cell cell1 = row.getCell(1);
		Cell cell2 = row.getCell(2);
		Cell cell3 = row.getCell(3);
		Cell cell4 = row.getCell(4);
		Cell cell5 = row.getCell(5);
		Cell cell6 = row.getCell(6);
		Cell cell7 = row.getCell(7);
		Cell cell8 = row.getCell(8);
		Cell cell9 = row.getCell(9);
		Cell cell10 = row.getCell(10);
		cell1.setCellValue(info.getReportDate());
		cell2.setCellValue(formatValue(info.getDiskAve()));
		cell3.setCellValue(formatValue(info.getDiskMax()));
		cell4.setCellValue(info.getDiskMaxId());
		cell5.setCellValue(formatValue(info.getDiskMin()));
		cell6.setCellValue(info.getDiskMinId());
		cell7.setCellValue(info.getDeviceNum());
		cell8.setCellValue(info.getDiskRange1Num());
		cell9.setCellValue(info.getDiskRange2Num());
		cell10.setCellValue(info.getDiskRange3Num());
	}

	private String formatValue(float value) {
		if (value % 1.0 == 0) {
			return (long) value + "%";
		} else {
			return value + "%";
		}
	}

	private String formatData(float value) {
		if (value % 1.0 == 0) {
			return (long) value + "";
		} else {
			return value + "";
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
		// newstyle.setDataFormat(fromStyle.getDataFormat());

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
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
