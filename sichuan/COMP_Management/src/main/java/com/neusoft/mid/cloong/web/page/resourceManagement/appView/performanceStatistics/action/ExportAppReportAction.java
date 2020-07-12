/*******************************************************************************
 * @(#)ExportResPortAction.java 2015-02-16
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.appView.performanceStatistics.action;

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
import com.neusoft.mid.cloong.web.page.resourceManagement.info.StaDeviceWeek;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-业务视图-单个业务-导出
 * 
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-02-16 下午16:27:16
 */
public class ExportAppReportAction extends ResourceManagementBaseAction{
	private static final long serialVersionUID = 2521336013204349708L;

	private static LogService logger = LogService
			.getLogger(ExportAppReportAction.class);
	
	private XSSFWorkbook workbook;
	
	private Font font;
	
	public String execute() {
		try {
            String url = Thread.currentThread().getContextClassLoader().getResource("/").toString();
            int last=url.toString().indexOf("WEB-INF");
            url = url.substring(5,last-1)+File.separator+"report"+File.separator;
            String path = URLDecoder.decode(url,"UTF-8");
            export(path, "appView_appReport.xlsx");
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
        String nName = ProcessFileName.processFileName(request,"业务视图_性能统计_");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        String newName = nName+treeNodeName+"_"+time+".xlsx";
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
			List<StaDeviceWeek> pmList = ibatisDAO.getData("queryPmPerList", appId);
			setSheet(pmSheet,pmList);
			
			Sheet vmSheet = workbook.getSheetAt(2);//获取虚拟机sheet
			List<StaDeviceWeek> vmList = ibatisDAO.getData("queryVmPerList", appId);
			setSheet(vmSheet,vmList);
			
			Sheet minipmSheet = workbook.getSheetAt(3);//获取小型机sheet
			List<StaDeviceWeek> minipmList = ibatisDAO.getData("queryMiniPmPerList", appId);
			setSheet(minipmSheet,minipmList);
			
			Sheet minipmparSheet = workbook.getSheetAt(4);//获取小型机分区sheet
			List<StaDeviceWeek> minipmparList = ibatisDAO.getData("queryMiniPmParPerList", appId);
			setSheet(minipmparSheet,minipmparList);
            workbook.write(out);
        } catch (Exception e) {
        	logger.error("导出数据时出错! Cause: " + e, e);
            throw new Exception(e);
        }
    }
    
    private void setDeviceNumSheet() throws SQLException{
    	Sheet deviceNumSheet = workbook.getSheetAt(0);//获取设备数sheet
    	StaDeviceWeek info = (StaDeviceWeek) ibatisDAO.getSingleRecord("queryDeviceNumbyAppId", appId);
    	
    	Row appRow = deviceNumSheet.getRow(1);//业务名称
        Cell cell = appRow.getCell(1); 
        cell.setCellValue("业务名称："+treeNodeName);
        
    	Row row = deviceNumSheet.getRow(4);
		Cell cell1 = row.getCell(1);
		cell1.setCellValue(info.getPm_num());
		Cell cell2 = row.getCell(2);
		cell2.setCellValue(info.getVm_num());
		Cell cell3 = row.getCell(3);
		cell3.setCellValue(info.getEbs_num());
		Cell cell4 = row.getCell(4);
		cell4.setCellValue(info.getMiniPm_num());
		Cell cell5 = row.getCell(5);
		cell5.setCellValue(info.getMiniPmPar_num());
    }
    
    private void setSheet(Sheet sheet,List<StaDeviceWeek> list) throws SQLException{
		Row appRow = sheet.getRow(1);//业务名称
        Cell cell = appRow.getCell(1); 
        cell.setCellValue("业务名称："+treeNodeName);
		
        if(list.size()==0){
        	sheet.removeRow(sheet.getRow(4));
        }
        
		for(int i=0;i<list.size();i++){
			StaDeviceWeek info = list.get(i);
			if(i>0){
				Row newRow = sheet.createRow(i+4);
				copyRow(sheet.getRow(4),newRow);
			}
			
			Row row = sheet.getRow(i+4);
			if(row!=null){
				String cpu = info.getCpu_processor_utilization();
				if(cpu != null && cpu !="" && "null"!= cpu){
					cpu += "%";
				}
				String mem = info.getMem_used_per();
				if(mem != null && mem !="" && "null"!= mem){
					mem += "%";
				}
				String disk = info.getDisk_used_per();
				if(disk != null && disk !="" && "null"!= disk){
					disk += "%";
				}
				Cell cell1 = row.getCell(1);
				cell1.setCellValue(info.getDevice_name());
				Cell cell2 = row.getCell(2);
				cell2.setCellValue(cpu);
				Cell cell3 = row.getCell(3);
				cell3.setCellValue(mem);
				Cell cell4 = row.getCell(4);
				cell4.setCellValue(disk);
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
}
