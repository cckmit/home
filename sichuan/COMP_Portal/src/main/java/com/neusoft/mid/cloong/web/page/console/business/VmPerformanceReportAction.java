/*******************************************************************************
 * @(#)ExportDeviceReportAction.java 2015-03-03
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.console.business;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.MessageFormat;

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
import com.neusoft.mid.cloong.identity.bean.UserBean;
import com.neusoft.mid.cloong.web.ConstantEnum;
import com.neusoft.mid.cloong.web.authority.auth.SessionKeys;
import com.neusoft.mid.cloong.web.page.console.business.info.VmPerformanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 虚拟机性能统计-导出
 */
public class VmPerformanceReportAction extends ResourceManagementBaseAction{
    private static final long serialVersionUID = 2521336013204349708L;

    private static LogService logger = LogService.getLogger(VmPerformanceReportAction.class);

    private XSSFWorkbook workbook;

    private Font font;

    private String startDate;

    private String endDate;

    private String startDateForSql;

    private String endDateForSql;

    private String deviceId;

    private String deviceName;
    
    private String vmIp;
    
    private VmPerformanceInfo vmPerformanceInfo = new VmPerformanceInfo();


    public String execute() {
    	
        // session中获取用户ID
        UserBean user = ((UserBean) ServletActionContext.getRequest().getSession()
                .getAttribute(SessionKeys.userInfo.toString()));
        String userId = user.getUserId();
        if("".equals(startDate) || "".equals(endDate)){
       	 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
	        String day = df.format(new Date());
	         startDate = day+"000000";
	         endDate = day+"235959";
        }
        try {
            String url = Thread.currentThread().getContextClassLoader().getResource("/").toString();
            int last = url.toString().indexOf("WEB-INF");
            url = url.substring(5, last - 1) + File.separator + "report" + File.separator;
            String path = URLDecoder.decode(url, "UTF-8");
            export(path, "report_devicePerformance.xlsx",userId);
            
        } catch (Exception e) {
            logger.error(MessageFormat.format(getText("vmPerformance.reportPerformanceInfo.fail"), userId) + e, e);
            this.addActionError(MessageFormat.format(getText("vmPerformance.reportPerformanceInfo.fail"), userId));
            return ConstantEnum.ERROR.toString();
        }
        return null;
    }

    private void export(String filePath, String fileName,String userId) throws IOException, Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String nName = ProcessFileName.processFileName(request,"设备性能统计_");
        String newName = nName + vmIp + "_"
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
            downloadExcel(filePath + fileName, out,userId);

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
    private void downloadExcel(String path, BufferedOutputStream out ,String userId) throws Exception {
        try {
            if (null != startDate && !"".equals(startDate)) {
            	vmPerformanceInfo.setStartDate(startDate.replaceAll("-", "").replace(" ", "")
                        .replaceAll(":", ""));
            }
            if (null != endDate && !"".equals(endDate)) {
            	vmPerformanceInfo.setEndDate(endDate.replace("-", "").replace(" ", "")
                        .replaceAll(":", ""));
            }

            startDateForSql = vmPerformanceInfo.getStartDate();
            endDateForSql = vmPerformanceInfo.getEndDate();

            // 根据path将新的sheet页建在已有模板上
            workbook = new XSSFWorkbook(path);
            setFont();

            Sheet sheet = workbook.getSheetAt(0);// 获取sheet
            setSheet(sheet);

            workbook.write(out);
        } catch (Exception e) {
            logger.error(MessageFormat.format(getText("vmPerformance.reportPerformanceInfo.fail"), userId) + e, e);
            this.addActionError(MessageFormat.format(getText("vmPerformance.reportPerformanceInfo.fail"), userId));
            throw new Exception(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void setSheet(Sheet sheet) throws SQLException, ParseException {
    	VmPerformanceInfo obj = new VmPerformanceInfo();
        obj.setStartDate(startDateForSql);
        obj.setEndDate(endDateForSql);
        obj.setVmIp(vmIp);
        List<VmPerformanceInfo> performanceList = new ArrayList<VmPerformanceInfo>();
        performanceList = this.ibatisDAO.getData("getVmPerformanceReport", obj);
        if(performanceList==null){
            performanceList = this.ibatisDAO.getData("getVm2PerformanceReport", obj);
        }
//        处理数据CPU、时间、网络输入/输出量
        DecimalFormat df2 = new DecimalFormat("0.00");
        for(int i=0;i<performanceList.size();i++){
        	if(null != performanceList.get(i).getCpuIdle() && !"".equals(performanceList.get(i).getCpuIdle())){
        		Float cpus = 100-Float.parseFloat(performanceList.get(i).getCpuIdle());
        		String cpu = df2.format(cpus);
        		performanceList.get(i).setCpuUtilization(String.valueOf(cpu));
        	}       	
        	if(null != performanceList.get(i).getBytesIn() && !"".equals(performanceList.get(i).getBytesIn())){
        		String [] ins1 = performanceList.get(i).getBytesIn().split("\\=");
        		String [] ins2 = ins1[1].split("\\^");
        		performanceList.get(i).setBytesIn(ins2[0]);
        	}
        	if(null != performanceList.get(i).getBytesOut() && !"".equals(performanceList.get(i).getBytesOut())){
        		String [] outs1 = performanceList.get(i).getBytesOut().split("\\=");
        		String [] outs2 = outs1[1].split("\\^");
        		performanceList.get(i).setBytesOut(outs2[0]);
        	}
        	
        	String[] times = performanceList.get(i).getReportDate().split("\\.");
        	String time = times[0];
        	performanceList.get(i).setReportDate(time);
        }
        setSheetData(sheet, performanceList);
    }

    private void setSheetData(Sheet sheet, List<VmPerformanceInfo> list)
            throws SQLException, ParseException {
        Row deviceRow = sheet.getRow(1);// 查询条件
        Cell cell = deviceRow.getCell(1);
        cell.setCellValue("虚拟机IP：" + vmIp + "         查询日期:" + startDate + "~" + endDate);

        if (list.size() == 0) {
            sheet.removeRow(sheet.getRow(3));
        }

        for (int i = 0; i < list.size(); i++) {
        	VmPerformanceInfo info = list.get(i);
            if (i > 0) {
                Row newRow = sheet.createRow(i + 3);
                copyRow(sheet.getRow(3), newRow);
            }

            Row row = sheet.getRow(i + 3);
            if (row != null) {
                setRow(row, info);
            }
        }
    }

    private void setRow(Row row, VmPerformanceInfo info)
            throws ParseException {
        String time = info.getReportDate();
//        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = sf2.format(sf1.parse(time));
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);
            Cell cell5 = row.getCell(5);
            Cell cell6 = row.getCell(6);
            Cell cell7 = row.getCell(7);
            Cell cell8 = row.getCell(8);
            cell1.setCellValue(time);
            cell2.setCellValue(info.getCpuUtilization() + "%");
            cell3.setCellValue(info.getMemUtilization() + "%");
            cell4.setCellValue(info.getMemTotalKb() + "KB");
            cell5.setCellValue(info.getDiskUtilization() + "%");
            cell6.setCellValue(info.getDiskTotalG() + "G");
            cell7.setCellValue(info.getBytesIn() + " Bytes/sec");
            cell8.setCellValue(info.getBytesOut() + " Bytes/sec");
            
     
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

	public VmPerformanceInfo getVmPerformanceInfo() {
		return vmPerformanceInfo;
	}

	public void setVmPerformanceInfo(VmPerformanceInfo vmPerformanceInfo) {
		this.vmPerformanceInfo = vmPerformanceInfo;
	}

	public String getVmIp() {
		return vmIp;
	}

	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}

}
