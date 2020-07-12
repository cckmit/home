/*******************************************************************************
 * @(#)ExportResPortAction.java 2015-02-16
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.appView.exceededStatistics.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.neusoft.mid.cloong.web.page.resourceManagement.action.ResourceManagementBaseAction;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.DeviceExceededNum;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.DeviceNum;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-业务视图-所有业务-导出
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-02-25 下午13:27:16
 */
public class ExportAllAppReportAction extends ResourceManagementBaseAction{
	private static final long serialVersionUID = 2521336013204349708L;

	private static LogService logger = LogService
			.getLogger(ExportAllAppReportAction.class);
	
	private XSSFWorkbook workbook;
	
	private Font font;
	
	public String execute() {
		try {
            String url = Thread.currentThread().getContextClassLoader().getResource("/").toString();
            int last=url.toString().indexOf("WEB-INF");
            url = url.substring(5,last-1)+File.separator+"report"+File.separator;
            String path = URLDecoder.decode(url,"UTF-8");
            export(path, "appView_allAppReport.xlsx");
        } catch (Exception e) {
        	logger.error("导出数据时出错! Cause: " + e, e);
        	this.addActionError("导出数据时出错!");
        	return ConstantEnum.ERROR.toString();
        }
		return null;
	}
	
	private void export(String filePath, String fileName)throws IOException, Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String nName = ProcessFileName.processFileName(request,"业务视图_性能统计_所有业务_");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        String newName = nName+time+".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename="
                + new String(newName));
        response.setContentType("application/octet-stream");
        response.setContentType("application/msexcel");// 定义输出类型
        
        File file = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            out = new BufferedOutputStream(response.getOutputStream());
            downloadExcel(filePath + fileName,out);
            
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
    private void downloadExcel(String path,BufferedOutputStream out)
            throws Exception {
        try {
        	//根据path将新的sheet页建在已有模板上
			workbook = new XSSFWorkbook(path);
			setFont();
			setDeviceNumSheet();
			
			Sheet pmSheet = workbook.getSheetAt(1);//获取物理机sheet
			List<DeviceExceededNum> pmList = ibatisDAO.getData("getExportList", "2");
			setSheet(pmSheet,pmList);
			
			Sheet vmSheet = workbook.getSheetAt(2);//获取虚拟机sheet
			List<DeviceExceededNum> vmList = ibatisDAO.getData("getExportList", "3");
			setSheet(vmSheet,vmList);
			
			Sheet minipmSheet = workbook.getSheetAt(3);//获取小型机sheet
			List<DeviceExceededNum> minipmList = ibatisDAO.getData("getExportList", "0");
			setSheet(minipmSheet,minipmList);
			
			Sheet minipmparSheet = workbook.getSheetAt(4);//获取小型机分区sheet
			List<DeviceExceededNum> minipmparList = ibatisDAO.getData("getExportList", "1");
			setSheet(minipmparSheet,minipmparList);
            workbook.write(out);
        } catch (Exception e) {
        	logger.error("导出数据时出错! Cause: " + e, e);
            throw new Exception(e);
        }
    }
    
    private void setDeviceNumSheet() throws SQLException{
    	Sheet deviceNumSheet = workbook.getSheetAt(0);//获取设备数sheet
    	List<DeviceNum> allDeviceNumList = this.getPage("queryAllDeviceNumCount", "queryAllDeviceNumList", null);
    	
    	for(int i=0;i<allDeviceNumList.size();i++){
    		DeviceNum info = allDeviceNumList.get(i);
			if(i>0){
				Row newRow = deviceNumSheet.createRow(i+3);
				copyRow(deviceNumSheet.getRow(3),newRow);
			}
			
			Row row = deviceNumSheet.getRow(i+3);
			if(row!=null){
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getApp_name());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getPm_num());
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(info.getVm_num());
				Cell cell4 = row.getCell(4);
				cell4.setCellValue(info.getEbs_num());
				Cell cell5 = row.getCell(5);
				cell5.setCellValue(info.getMiniPm_num());
				Cell cell6 = row.getCell(6);
				cell6.setCellValue(info.getMiniPmPar_num());
			}
		}
    }
    
    private void setSheet(Sheet sheet,List<DeviceExceededNum> list) throws SQLException{
    	if(list.size()==0){
        	sheet.removeRow(sheet.getRow(5));
        }
    	for(int i=0;i<list.size();i++){
			DeviceExceededNum info = list.get(i);
			if(i>0){
				Row newRow = sheet.createRow(i+5);
				copyRow(sheet.getRow(5),newRow);
			}
			
			Row row = sheet.getRow(i+5);
			if(row!=null){
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getAppName());//业务名称
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(info.getDeviceNum());//设备总数
				//CPU 超标
				Cell cell3 = row.getCell(3);
				Cell cell4 = row.getCell(4);
				Cell cell5 = row.getCell(5);
				cell3.setCellValue(formatValue(info.getCpuOverPer()));//占比
				cell4.setCellValue(info.getCpuOverNum());//设备数
				cell5.setCellValue(formatValue(info.getCpuOverAve()));//平均使用率
				//CPU 未超标
				Cell cell6 = row.getCell(6);
				Cell cell7 = row.getCell(7);
				Cell cell8 = row.getCell(8);
				cell6.setCellValue(formatValue(info.getCpuNotOverPer()));//占比
				cell7.setCellValue(info.getCpuNotOverNum());//设备数
				cell8.setCellValue(formatValue(info.getCpuNotOverAve()));//平均使用率
				//内存 超标
				Cell cell9 = row.getCell(9);
				Cell cell10 = row.getCell(10);
				Cell cell11 = row.getCell(11);
				Cell cell12 = row.getCell(12);
				cell9.setCellValue(formatValue(info.getMemOverPer()));//占比
				cell10.setCellValue(info.getMemOverNum());//设备数
				cell11.setCellValue(formatValue(info.getMemOverAve()));//平均使用率
				cell12.setCellValue(info.getMemOverFreeAve());//平均可用内存
				//内存 未超标
				Cell cell13 = row.getCell(13);
				Cell cell14 = row.getCell(14);
				Cell cell15 = row.getCell(15);
				Cell cell16 = row.getCell(16);
				cell13.setCellValue(formatValue(info.getMemNotOverPer()));//占比
				cell14.setCellValue(info.getMemNotOverNum());//设备数
				cell15.setCellValue(formatValue(info.getMemNotOverAve()));//平均使用率
				cell16.setCellValue(info.getMemNotOverFreeAve());//平均可用内存
				//磁盘 使用率>70%
				Cell cell17 = row.getCell(17);
				Cell cell18 = row.getCell(18);
				cell17.setCellValue(formatValue(info.getDiskRange1Per()));//占比
				cell18.setCellValue(info.getDiskRange1Num());//设备数
				//磁盘 30%~70%
				Cell cell19 = row.getCell(19);
				Cell cell20 = row.getCell(20);
				cell19.setCellValue(formatValue(info.getDiskRange2Per()));//占比
				cell20.setCellValue(info.getDiskRange2Num());//设备数
				//磁盘 使用率<30%
				Cell cell21 = row.getCell(21);
				Cell cell22 = row.getCell(22);
				cell21.setCellValue(formatValue(info.getDiskRange3Per()));//占比
				cell22.setCellValue(info.getDiskRange3Num());//设备数
			}
		}
    }
    
    private void copyRow(Row fromRow,Row toRow){  
        for(int i=1;i<25;i++){
        	Cell fromCell = fromRow.getCell(i);
        	Cell newCell = toRow.createCell(i);
        	if(fromCell != null){
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
        
        //单元格格式
        //newstyle.setDataFormat(fromStyle.getDataFormat());
        
        //字体样式
        newstyle.setFont(font);
        
        newCell.setCellStyle(newstyle);
    }
    
    private void setFont(){
   	 	font = workbook.createFont();
        font.setFontName("微软雅黑");  
        font.setFontHeightInPoints((short) 10);// 字体大小  
        font.setCharSet(XSSFFont.DEFAULT_CHARSET);
   }
    
    private String formatValue(float value){
		if(value % 1.0 == 0){
			return (long)value+"%";
		}else{
			return value+"%";
		}
    }
}
