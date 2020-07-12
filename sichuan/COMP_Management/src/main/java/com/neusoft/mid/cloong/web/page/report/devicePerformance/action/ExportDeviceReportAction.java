/*******************************************************************************
 * @(#)ExportDeviceReportAction.java 2015-03-03
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.report.devicePerformance.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.neusoft.mid.cloong.web.page.report.devicePerformance.info.DevicePerformanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 设备性能统计-导出
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-03-03 上午08:49:16
 */
public class ExportDeviceReportAction extends DeviceBaseAction {
    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(ExportDeviceReportAction.class);

    private XSSFWorkbook workbook;

    private Font font;

    private String startDate;

    private String endDate;

    private String startDateForSql;

    private String endDateForSql;

    private String deviceId;

    private String deviceName;

    private String deviceType;

    public String execute() {
        try {
            String url = Thread.currentThread().getContextClassLoader().getResource("/").toString();
            int last = url.toString().indexOf("WEB-INF");
            url = url.substring(5, last - 1) + File.separator + "report" + File.separator;
            String path = URLDecoder.decode(url, "UTF-8");

            if (("2").equals(deviceType)) {// 防火墙
                export(path, "report_devicePerformance_fw.xlsx");
            } else if (("3").equals(deviceType)) { // 阵列
                export(path, "report_devicePerformance_raid.xlsx");
            } else if (("41").equals(deviceType)) { // 交换机端口
                export(path, "report_devicePerformance_swif.xlsx");
            } else if (("51").equals(deviceType)) { // 路由器端口
                export(path, "report_devicePerformance_rtif.xlsx");
            } else {// 其它
                export(path, "report_devicePerformance.xlsx");
            }
        } catch (Exception e) {
            logger.error("导出设备性能统计数据时出错! Cause: " + e, e);
            this.addActionError("导出设备性能统计数据时出错!");
            return ConstantEnum.ERROR.toString();
        }
        return null;
    }

    private void export(String filePath, String fileName) throws IOException, Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String nName = ProcessFileName.processFileName(request,"设备性能统计_");
        String newName = nName + deviceName + "_"
                + startDate.replace("-", "").replace(" ", "").replaceAll(":", "") + "~"
                + endDate.replace("-", "").replace(" ", "").replaceAll(":", "") + ".xlsx";
        response.setHeader("Content-Disposition",
                "attachment; filename=" + new String(newName));
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
    private void downloadExcel(String path, BufferedOutputStream out) throws Exception {
        try {
            if (null != startDate && !"".equals(startDate)) {
                devicePerformanceInfo.setStartDate(startDate.replaceAll("-", "").replace(" ", "")
                        .replaceAll(":", ""));
            }
            if (null != endDate && !"".equals(endDate)) {
                devicePerformanceInfo.setEndDate(endDate.replace("-", "").replace(" ", "")
                        .replaceAll(":", ""));
            }

            startDateForSql = devicePerformanceInfo.getStartDate();
            endDateForSql = devicePerformanceInfo.getEndDate();

            // 根据path将新的sheet页建在已有模板上
            workbook = new XSSFWorkbook(path);
            setFont();

            Sheet sheet = workbook.getSheetAt(0);// 获取sheet
            setSheet(sheet, deviceType);

            workbook.write(out);
        } catch (Exception e) {
            logger.error("导出设备性能统计数据时出错! Cause: " + e, e);
            throw new Exception(e);
        }
    }

    private void setSheet(Sheet sheet, String deviceType) throws SQLException, ParseException {
        DevicePerformanceInfo obj = new DevicePerformanceInfo();
        obj.setDeviceId(deviceId);
        obj.setStartDate(startDateForSql);
        obj.setEndDate(endDateForSql);
        obj.setDeviceType(deviceType);
        List<DevicePerformanceInfo> performanceList = new ArrayList<DevicePerformanceInfo>();
        if (("0").equals(deviceType)) { // 物理机
            performanceList = ibatisDAO.getData("getPmPerformance", obj);
        } else if (("1").equals(deviceType)) { // 虚拟机
            performanceList = ibatisDAO.getData("getVmPerformance", obj);
        } else if (("2").equals(deviceType)) { // 防火墙
            performanceList = ibatisDAO.getData("getFwPerformance", obj);
        } else if (("3").equals(deviceType)) { // 阵列
            performanceList = ibatisDAO.getData("getRaidPerformance", obj);
        } else if (("4").equals(deviceType)) { // 交换机
            performanceList = ibatisDAO.getData("getSwPerformance", obj);
        } else if (("5").equals(deviceType)) { // 路由器
            performanceList = ibatisDAO.getData("getRtPerformance", obj);
        } else if (("41").equals(deviceType)) { // 交换机端口
            performanceList = ibatisDAO.getData("getSwIfPerformance", obj);
        } else if (("51").equals(deviceType)) { // 路由器端口
            performanceList = ibatisDAO.getData("getRtIfPerformance", obj);
        }

        setSheetData(sheet, performanceList, deviceType);
    }

    private void setSheetData(Sheet sheet, List<DevicePerformanceInfo> list, String deviceType)
            throws SQLException, ParseException {
        Row deviceRow = sheet.getRow(1);// 查询条件
        Cell cell = deviceRow.getCell(1);
        cell.setCellValue("名称：" + deviceName + "          查询日期:" + startDate + "~" + endDate);

        if (list.size() == 0) {
            sheet.removeRow(sheet.getRow(3));
        }

        for (int i = 0; i < list.size(); i++) {
            DevicePerformanceInfo info = list.get(i);
            if (i > 0) {
                Row newRow = sheet.createRow(i + 3);
                copyRow(sheet.getRow(3), newRow);
            }

            Row row = sheet.getRow(i + 3);
            if (row != null) {
                setRow(row, info, deviceType);
            }
        }
    }

    private void setRow(Row row, DevicePerformanceInfo info, String deviceType)
            throws ParseException {
        String time = info.getReportDate();
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sf2.format(sf1.parse(time));
        if (("2").equals(deviceType)) {// 防火墙
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);
            cell1.setCellValue(date);
            cell2.setCellValue(info.getCpuProcessorUtilization() + "%");
            cell3.setCellValue(info.getMemUsedPer() + "%");
            cell4.setCellValue(info.getThroughput() + "MB/S");
        } else if (("3").equals(deviceType)) { // 阵列
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            cell1.setCellValue(date);
            cell2.setCellValue(info.getHstDiskReadBytes() + "字节");
            cell3.setCellValue(info.getHstDiskWriteBytes() + "字节");
        } else if (("41").equals(deviceType)) { // 交换机端口
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);
            cell1.setCellValue(date);
            cell2.setCellValue(info.getPkts());
            cell3.setCellValue(info.getDiscards());
            cell4.setCellValue(info.getOctets() + "字节");
        } else if (("51").equals(deviceType)) { // 路由器端口
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            cell1.setCellValue(date);
            cell2.setCellValue(info.getPkts());
            cell3.setCellValue(info.getDiscards());
        } else {// 其它
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            cell1.setCellValue(date);
            cell2.setCellValue(info.getCpuProcessorUtilization() + "%");
            cell3.setCellValue(info.getMemUsedPer() + "%");
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
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
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
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

}
