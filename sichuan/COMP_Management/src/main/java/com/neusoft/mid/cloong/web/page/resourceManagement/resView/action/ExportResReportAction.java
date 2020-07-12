/*******************************************************************************
 * @(#)ExportResPortAction.java 2015-02-13
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.neusoft.mid.cloong.web.page.resourceManagement.info.StaCapacityInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 资源管理-资源视图-所有资源-导出统计数据
 * @author <a href="mailto:niul@neusoft.com">niul</a>
 * @version $Revision 1.1 $ 2015-02-13 上午10:27:16
 */
public class ExportResReportAction extends ResourceManagementBaseAction {
    private static final long serialVersionUID = 2521336013204349708L;

    private XSSFWorkbook workbook;

    private Font font;

    /**
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the workbook
     */
    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    /**
     * @param workbook the workbook to set
     */
    public void setWorkbook(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    /**
     * 各分区以及vcpu、内存、磁盘列表
     */
    private List<StaCapacityInfo> partsList = new ArrayList<StaCapacityInfo>();

    /**
     * @return the partsList
     */
    public List<StaCapacityInfo> getPartsList() {
        return partsList;
    }

    /**
     * @param partsList the partsList to set
     */
    public void setPartsList(List<StaCapacityInfo> partsList) {
        this.partsList = partsList;
    }

    private static LogService logger = LogService.getLogger(ExportResReportAction.class);

    public String execute() {
        try {
            String url = Thread.currentThread().getContextClassLoader().getResource("/").toString();
            int last = url.toString().indexOf("WEB-INF");
            url = url.substring(5, last - 1) + File.separator + "report" + File.separator;
            String path = URLDecoder.decode(url, "UTF-8");
            export(path, "resView_resReport.xlsx");
        } catch (Exception e) {
            logger.error("导出数据时出错! Cause: " + e, e);
            this.addActionError("导出数据时出错!");
            return ConstantEnum.ERROR.toString();
        }
        return null;
    }

    public void export(String filePath, String fileName) throws IOException, Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String nName = ProcessFileName.processFileName(request,"资源视图_所有资源_统计数据_");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        String newName = nName + time + ".xlsx";
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
        } catch (Throwable e) {
            logger.error("导出数据时出错! Cause: " + e, e);
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
    private void downloadExcel(String path, BufferedOutputStream out) {
        try {
            // 根据path将新的sheet页建在已有模板上
            workbook = new XSSFWorkbook(path);
            setFont();
            // 获取资源池
            List<StaCapacityInfo> pools = ibatisDAO.getData("getResPools", null);
            for (int i = 1; i < pools.size(); i++) {
                workbook.cloneSheet(0);
            }
            for (int i = 0; i < pools.size(); i++) {
                StaCapacityInfo pool = (StaCapacityInfo) pools.get(i);
                workbook.setSheetName(i, pool.getResPoolName());
                String resPoolId = pool.getResPoolId();
                Sheet sheet = workbook.getSheetAt(i);// 获取sheet
                // 查询资源个数
                List<String> countList = ibatisDAO.getData("queryDeviceNum", resPoolId);
                Row deviceNumRow = sheet.getRow(3);// 设备数统计
                Cell pmCell = deviceNumRow.getCell(2);// pm
                pmCell.setCellValue(countList.get(0));
                Cell vmCell = deviceNumRow.getCell(5);// vm
                vmCell.setCellValue(countList.get(1));
                Cell ebsCell = deviceNumRow.getCell(8);// ebs
                ebsCell.setCellValue(countList.get(2));
                /*
                 * 江西业支不需要展示小型机以及分区信息 Cell minipmCell = deviceNumRow.getCell(11);//minipm
                 * minipmCell.setCellValue(countList.get(3)); Cell minipmparCell =
                 * deviceNumRow.getCell(14);//minipmpar
                 * minipmparCell.setCellValue(countList.get(4));
                 */
                Cell raidCell = deviceNumRow.getCell(11);// raid
                raidCell.setCellValue(countList.get(5));
                Cell switchCell = deviceNumRow.getCell(14);// switch
                switchCell.setCellValue(countList.get(6));
                Cell routerCell = deviceNumRow.getCell(17);// router
                routerCell.setCellValue(countList.get(7));
                Cell firewallCell = deviceNumRow.getCell(18);// firewall
                firewallCell.setCellValue(countList.get(8));

                // 虚拟机容量统计
                // 各个分区
                partsList = ibatisDAO.getData("queryPartsList", resPoolId);
                if(partsList.size()==1){
                    Row firstAllRow = sheet.getRow(12);// 第一条数据|放在第12行
                    Cell partsName = firstAllRow.getCell(1);
                    partsName.setCellValue(partsList.get(0).getPoolPartName());
                 // 查询资源池VCPU最新值
                    StaCapacityInfo vcpu = new StaCapacityInfo();
                    vcpu.setResPoolId(resPoolId);
                    vcpu.setPoolPartId(partsList.get(0).getPoolPartId());
                    vcpu.setResType("0");
                    vcpu = (StaCapacityInfo) ibatisDAO.getSingleRecord("queryCapacityforNum",
                            vcpu);
                    if (vcpu != null) {
                        Cell allVcpuCell = firstAllRow.getCell(5);
                        allVcpuCell.setCellValue(String.valueOf(Integer.valueOf(vcpu.getResTotal())*2));
                        Cell usedVcpuCell = firstAllRow.getCell(8);
                        usedVcpuCell.setCellValue(vcpu.getResUsed());
                    }

                    // 查询资源池内存使用情况
                    StaCapacityInfo memory = new StaCapacityInfo();
                    memory.setResPoolId(resPoolId);
                    memory.setPoolPartId(partsList.get(0).getPoolPartId());
                    memory.setResType("1");
                    memory = (StaCapacityInfo) ibatisDAO.getSingleRecord("queryCapacityforGB",
                            memory);
                    if (memory != null) {
                        Cell allMemoryCell = firstAllRow.getCell(11);
                        allMemoryCell.setCellValue(memory.getResTotal());
                        Cell usedMemoryCell = firstAllRow.getCell(14);
                        usedMemoryCell.setCellValue(memory.getResUsed());
                    }

                    // 查询资源池磁盘使用情况
                    StaCapacityInfo disk = new StaCapacityInfo();
                    disk.setResPoolId(resPoolId);
                    disk.setPoolPartId(partsList.get(0).getPoolPartId());
                    disk.setResType("2");
                    disk = (StaCapacityInfo) ibatisDAO.getSingleRecord("queryCapacityforDiskGB",
                            disk);
                    if (disk != null) {
                        Cell allDiskCell = firstAllRow.getCell(17);
                        allDiskCell.setCellValue(disk.getResTotal());
                        Cell usedDiskCell = firstAllRow.getCell(18);
                        usedDiskCell.setCellValue(disk.getResUsed());
                    }

                }
                if (partsList.size() > 1 && partsList.size() != 0) {
                    for (int j = 0; j < partsList.size(); j++) {
                        Row firstAllRow = sheet.getRow(j + 12);// 第一条数据放在第12行
                        Row secondAllRow = sheet.createRow(j + 13);// 第二条数据放在第13行
                        Cell partsName = firstAllRow.getCell(1);
                        partsName.setCellValue(partsList.get(j).getPoolPartName());

                        // 查询资源池VCPU最新值
                        StaCapacityInfo vcpu = new StaCapacityInfo();
                        vcpu.setResPoolId(resPoolId);
                        vcpu.setPoolPartId(partsList.get(j).getPoolPartId());
                        vcpu.setResType("0");
                        vcpu = (StaCapacityInfo) ibatisDAO.getSingleRecord("queryCapacityforNum",
                                vcpu);
                        if (vcpu != null) {
                            Cell allVcpuCell = firstAllRow.getCell(5);
                            allVcpuCell.setCellValue(String.valueOf(Integer.valueOf(vcpu.getResTotal())*2));
                            Cell usedVcpuCell = firstAllRow.getCell(8);
                            usedVcpuCell.setCellValue(vcpu.getResUsed());
                        }

                        // 查询资源池内存使用情况
                        StaCapacityInfo memory = new StaCapacityInfo();
                        memory.setResPoolId(resPoolId);
                        memory.setPoolPartId(partsList.get(j).getPoolPartId());
                        memory.setResType("1");
                        memory = (StaCapacityInfo) ibatisDAO.getSingleRecord("queryCapacityforGB",
                                memory);
                        if (memory != null) {
                            Cell allMemoryCell = firstAllRow.getCell(11);
                            allMemoryCell.setCellValue(memory.getResTotal());
                            Cell usedMemoryCell = firstAllRow.getCell(14);
                            usedMemoryCell.setCellValue(memory.getResUsed());
                        }

                        // 查询资源池磁盘使用情况
                        StaCapacityInfo disk = new StaCapacityInfo();
                        disk.setResPoolId(resPoolId);
                        disk.setPoolPartId(partsList.get(j).getPoolPartId());
                        disk.setResType("2");
                        disk = (StaCapacityInfo) ibatisDAO.getSingleRecord("queryCapacityforGB",
                                disk);
                        if (disk != null) {
                            Cell allDiskCell = firstAllRow.getCell(17);
                            allDiskCell.setCellValue(disk.getResTotal());
                            Cell usedDiskCell = firstAllRow.getCell(18);
                            usedDiskCell.setCellValue(disk.getResUsed());
                        }
                        copyRow(firstAllRow, secondAllRow);
                        /*
                         * 查询资源池物理机使用情况 江西业支不需要展示 StaCapacityInfo pm = new StaCapacityInfo();
                         * pm.setResPoolId(resPoolId); pm.setResType("3"); pm = (StaCapacityInfo)
                         * ibatisDAO.getSingleRecord("queryCapacityforNum", pm); if(pm != null){ Row
                         * allPmRow = sheet.getRow(20);//总量 Cell allCell = allPmRow.getCell(2);
                         * allCell.setCellValue(pm.getResTotal()); Row usedPmRow =
                         * sheet.getRow(21);//已使用 Cell usedCell = usedPmRow.getCell(2);
                         * usedCell.setCellValue(pm.getResUsed()); }
                         */
                    }
                    sheet.removeRow(sheet.getRow(partsList.size()+12));
                }
            }
            workbook.write(out);
            logger.info("workbook.write");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导出数据时出错! Cause: " + e, e);
        }
    }

    private void copyRow(Row fromRow, Row toRow) {
        for (int i = 1; i < 19; i++) {
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

}
