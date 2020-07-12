package com.neusoft.mid.cloong.web.page.report.monthPerformance.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.ParseException;
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
import com.neusoft.mid.cloong.web.page.report.devicePerformance.action.DeviceBaseAction;
import com.neusoft.mid.cloong.web.page.report.monthPerformance.info.MonthPerformanceInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 设备性能统计-导出
 * @author <a href="mailto:wxin.neu@neusoft.com">王欣</a>
 * @version $Revision 1.1 $ 2016年4月8日13:10:10
 */
public class ExportMonthReportAction extends DeviceBaseAction {

    private static final long serialVersionUID = -5389786574971594590L;

    private static LogService logger = LogService.getLogger(ExportMonthReportAction.class);

    private XSSFWorkbook workbook;

    private Font font;

    private String startMonthDateExport;

    private String endMonthDateExport;

    private String startMonthDateForSql;

    private String endMonthDateForSql;

    private String deviceId;

    private String deviceName;

    private String resPoolId;

    private String poolPartId;

    private String orderType;

    private String dateType;

    // 导出方式
    private String exportWay;

    private MonthPerformanceInfo monthPerformanceInfo = new MonthPerformanceInfo();

    /** 业务下拉列表 - 没用 */
    private List<MonthPerformanceInfo> appList = new ArrayList<MonthPerformanceInfo>();

    public String execute() {
        try {
            String url = Thread.currentThread().getContextClassLoader().getResource("/").toString();
            int last = url.toString().indexOf("WEB-INF");
            url = url.substring(5, last - 1) + File.separator + "report" + File.separator;
            String path = URLDecoder.decode(url, "UTF-8");
            if (("monthPerAll").equals(exportWay)) { // 业务性能统计---导出全部数据
                export(path, "report_monthPerAll.xlsx");
            } else if (("monthPerSelected").equals(exportWay)) { // 业务性能统计---导出条件查询数据
                export(path, "report_monthPerSelected.xlsx");
            }
        } catch (Exception e) {
            logger.error("导出月度性能统计数据时出错! Cause: " + e, e);
            this.addActionError("导出月度性能统计数据时出错!");
            return ConstantEnum.ERROR.toString();
        }
        return null;
    }
    
    /**
     * @return the appList
     */
    public List<MonthPerformanceInfo> getAppList() {
        return appList;
    }

    /**
     * @param appList the appList to set
     */
    public void setAppList(List<MonthPerformanceInfo> appList) {
        this.appList = appList;
    }

    private void export(String filePath, String fileName) throws IOException, Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        // 拼接excel文件名
        String newName = "";
        String nName = ProcessFileName.processFileName(request,"月度性能统计_");
        if (("report_monthPerAll.xlsx").equals(fileName)) {// 导出全部--文件名为 月度性能统计+时间区间
            newName = nName
                    + startMonthDateExport.replace("-", "").replace(" ", "").replaceAll(":", "")
                    + "~"
                    + endMonthDateExport.replace("-", "").replace(" ", "").replaceAll(":", "")
                    + ".xlsx";
        } else if (("report_monthPerSelected.xlsx").equals(fileName)) {// 导出查询结果--文件名为 月度性能统计 +
                                                                       // dateType + 时间区间
            String dType = "";
            if ("0".equals(dateType)) {// 全天
                dType =  ProcessFileName.processFileName(request,"全天");
            } else if ("1".equals(dateType)) {// 白天
                dType =  ProcessFileName.processFileName(request,"白天");
            } else if ("2".equals(dateType)) {// 夜间
                dType =  ProcessFileName.processFileName(request,"夜间");
            }
            newName = nName + dType + "_"
                    + startMonthDateExport.replace("-", "").replace(" ", "").replaceAll(":", "")
                    + "~"
                    + endMonthDateExport.replace("-", "").replace(" ", "").replaceAll(":", "")
                    + ".xlsx";
        }
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
            if (null != startMonthDateExport && !"".equals(startMonthDateExport)) {
                monthPerformanceInfo.setStartDate(startMonthDateExport.replaceAll("-", "")
                        .replace(" ", "").replaceAll(":", ""));
            }
            if (null != endMonthDateExport && !"".equals(endMonthDateExport)) {
                monthPerformanceInfo.setEndDate(endMonthDateExport.replace("-", "")
                        .replace(" ", "").replaceAll(":", ""));
            }
            startMonthDateForSql = monthPerformanceInfo.getStartDate();
            endMonthDateForSql = monthPerformanceInfo.getEndDate();
            // 根据path将新的sheet页建在已有模板上
            workbook = new XSSFWorkbook(path);
            setFont();
            if (("monthPerAll").equals(exportWay)) {
                Sheet sheet1 = workbook.getSheetAt(0);// 获取物理机sheet1
                setSheet(sheet1, "pmAll");
                Sheet sheet2 = workbook.getSheetAt(1);// 获取虚拟机sheet2
                setSheet(sheet2, "vmAll");
            } else if (("monthPerSelected").equals(exportWay)) {
                Sheet sheet1 = workbook.getSheetAt(0);// 获取物理机-CPU
                setSheetSelected(sheet1, "PmAllCpu");
                Sheet sheet2 = workbook.getSheetAt(1);// 获取物理机-内存
                setSheetSelected(sheet2, "PmAllMem");
                Sheet sheet3 = workbook.getSheetAt(2);// 获取物理机-PAGE
                setSheetSelected(sheet3, "PmAllPage");
                Sheet sheet4 = workbook.getSheetAt(3);// 获取物理机-IOspeed
                setSheetSelected(sheet4, "PmAllIOspeed");
                Sheet sheet5 = workbook.getSheetAt(4);// 获取虚拟机-CPU
                setSheetSelected(sheet5, "VmAllCpu");
                Sheet sheet6 = workbook.getSheetAt(5);// 获取虚拟机-内存
                setSheetSelected(sheet6, "VmAllMem");
                Sheet sheet7 = workbook.getSheetAt(6);// 获取虚拟机-内存
                setSheetSelected(sheet7, "VmAllIOspeed");
                // Sheet sheet6 = workbook.getSheetAt(5);// 获取虚拟机-PAGE
                // setSheetSelected(sheet6, "VmAllPage");
            }

            workbook.write(out);
        } catch (Exception e) {
            logger.error("导出月度性能统计数据时出错! Cause: " + e, e);
            throw new Exception(e);
        }
    }

    private void setSheet(Sheet sheet, String whichSheet) throws SQLException, ParseException {
        // month数据bean
        MonthPerformanceInfo obj = new MonthPerformanceInfo();
        List<MonthPerformanceInfo> performanceList = new ArrayList<MonthPerformanceInfo>();
        if (("pmAll").equals(whichSheet)) { // 月度性能统计---查询物理机数据All
            obj.setStartDate(startMonthDateForSql);
            obj.setEndDate(endMonthDateForSql);
            performanceList = ibatisDAO.getData("getPmAllPerformance", obj);
        }
        if (("vmAll").equals(whichSheet)) { // 月度性能统计---查询虚拟机数据All
            obj.setStartDate(startMonthDateForSql);
            obj.setEndDate(endMonthDateForSql);
            performanceList = ibatisDAO.getData("getVmAllPerformance", obj);
        }

        setSheetData(sheet, performanceList, whichSheet);
    }

    private void setSheetSelected(Sheet sheet, String whichSheet) throws SQLException,
            ParseException {
        // month数据bean
        MonthPerformanceInfo obj = new MonthPerformanceInfo();
        List<MonthPerformanceInfo> performanceList = new ArrayList<MonthPerformanceInfo>();
        if (("PmAllCpu").equals(whichSheet)) { // 查询性能统计---获取物理机-CPU
            obj.setOrderType("0");// 这里指定排序方式，降序排序
            obj.setDateType(dateType);
            obj.setResPoolId(resPoolId);
            obj.setPoolPartId(poolPartId);
            obj.setStartDate(startMonthDateForSql);
            obj.setEndDate(endMonthDateForSql);
            performanceList = ibatisDAO.getData("getPmAllCpuPerformance", obj);
        }
        if (("PmAllMem").equals(whichSheet)) { // 查询性能统计--- 获取物理机-内存
            obj.setOrderType("0");
            obj.setDateType(dateType);
            obj.setResPoolId(resPoolId);
            obj.setPoolPartId(poolPartId);
            obj.setStartDate(startMonthDateForSql);
            obj.setEndDate(endMonthDateForSql);
            performanceList = ibatisDAO.getData("getPmAllMemPerformance", obj);
        }
        if (("PmAllPage").equals(whichSheet)) { // 查询性能统计---获取物理机-PAGE
            obj.setOrderType("0");
            obj.setDateType(dateType);
            obj.setResPoolId(resPoolId);
            obj.setPoolPartId(poolPartId);
            obj.setStartDate(startMonthDateForSql);
            obj.setEndDate(endMonthDateForSql);
            performanceList = ibatisDAO.getData("getPmAllPagePerformance", obj);
        }
        if (("PmAllIOspeed").equals(whichSheet)) { // 查询性能统计---获取物理机-PAGE
            obj.setOrderType("0");
            obj.setDateType(dateType);
            obj.setResPoolId(resPoolId);
            obj.setPoolPartId(poolPartId);
            obj.setStartDate(startMonthDateForSql);
            obj.setEndDate(endMonthDateForSql);
            performanceList = ibatisDAO.getData("getPmAllIOspeedPerformance", obj);
        }
        if (("VmAllCpu").equals(whichSheet)) { // 查询性能统计---获取虚拟机-CPU
            obj.setOrderType("0");
            obj.setDateType(dateType);
            obj.setResPoolId(resPoolId);
            obj.setPoolPartId(poolPartId);
            obj.setStartDate(startMonthDateForSql);
            obj.setEndDate(endMonthDateForSql);
            performanceList = ibatisDAO.getData("getVmAllCpuPerformance", obj);
        }
        if (("VmAllMem").equals(whichSheet)) { // 查询性能统计---获取虚拟机-内存
            obj.setOrderType("0");
            obj.setDateType(dateType);
            obj.setResPoolId(resPoolId);
            obj.setPoolPartId(poolPartId);
            obj.setStartDate(startMonthDateForSql);
            obj.setEndDate(endMonthDateForSql);
            performanceList = ibatisDAO.getData("getVmAllMemPerformance", obj);
        }
        if (("VmAllIOspeed").equals(whichSheet)) { // 查询性能统计---获取虚拟机-内存
            obj.setOrderType("0");
            obj.setDateType(dateType);
            obj.setResPoolId(resPoolId);
            obj.setPoolPartId(poolPartId);
            obj.setStartDate(startMonthDateForSql);
            obj.setEndDate(endMonthDateForSql);
            performanceList = ibatisDAO.getData("getVmAllIOspeedPerformance", obj);
        }
        // if (("VmAllPage").equals(whichSheet)) { // 查询性能统计---获取虚拟机-PAGE
        // obj.setOrderType("0");
        // obj.setDateType(dateType);
        // obj.setResPoolId(resPoolId);
        // obj.setPoolPartId(poolPartId);
        // obj.setStartDate(startMonthDateForSql);
        // obj.setEndDate(endMonthDateForSql);
        // performanceList = ibatisDAO.getData("getVmAllPagePerformance", obj);
        // }

        setSelectSheetData(sheet, performanceList, whichSheet);
    }

    private void setSelectSheetData(Sheet sheet, List<MonthPerformanceInfo> performanceList,
            String whichSheet) throws SQLException, ParseException {
        // Row deviceRow = sheet.getRow(1);// 查询条件
        // Cell cell1 = deviceRow.getCell(7);
        // cell1.setCellValue("6-17点");

        if (performanceList.size() == 0) {
            sheet.removeRow(sheet.getRow(1));
        }
        for (int i = 0; i < performanceList.size(); i++) {
            MonthPerformanceInfo info = performanceList.get(i);
            if (i > 0) {
                Row newRow = sheet.createRow(i + 1);
                copyRow(sheet.getRow(1), newRow);
            }

            Row row = sheet.getRow(i + 1);
            if (row != null) {
                setRow(row, info, whichSheet);
            }
        }
    }

    private void setSheetData(Sheet sheet, List<MonthPerformanceInfo> performanceList,
            String whichSheet) throws SQLException, ParseException {
        if (performanceList.size() == 0) {
            sheet.removeRow(sheet.getRow(2));
        }
        for (int i = 0; i < performanceList.size(); i++) {
            MonthPerformanceInfo info = performanceList.get(i);
            if (i > 0) {
                Row newRow = sheet.createRow(i + 2);
                copyRow(sheet.getRow(2), newRow);
            }

            Row row = sheet.getRow(i + 2);
            if (row != null) {
                setRow(row, info, whichSheet);
            }
        }
    }

    private void setRow(Row row, MonthPerformanceInfo info, String whichSheet)
            throws ParseException {
        if (("pmAll").equals(whichSheet)) { // 月度性能统计---查询物理机数据All
            Cell cell0 = row.getCell(0);
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
            Cell cell16 = row.getCell(16);
            Cell cell17 = row.getCell(17);
            Cell cell18 = row.getCell(18);
            Cell cell19 = row.getCell(19);
            Cell cell20 = row.getCell(20);
            Cell cell21 = row.getCell(21);
            Cell cell22 = row.getCell(22);
            Cell cell23 = row.getCell(23);
            Cell cell24 = row.getCell(24);
            Cell cell25 = row.getCell(25);
            Cell cell26 = row.getCell(26);
            Cell cell27 = row.getCell(27);
            Cell cell28 = row.getCell(28);
            Cell cell29 = row.getCell(29);
            Cell cell30 = row.getCell(30);
            Cell cell31 = row.getCell(31);
            Cell cell32 = row.getCell(32);
            cell0.setCellValue(info.getPoolPartName());
            cell1.setCellValue(info.getAppName());
            cell2.setCellValue(info.getIp());
            cell3.setCellValue(info.getServerType());
            cell4.setCellValue(info.getCpuNum());
            cell5.setCellValue(info.getMemorySize());
            cell6.setCellValue(info.getOs());
            cell7.setCellValue(info.getAppContacts());
            cell8.setCellValue(info.getOnlineTime());
            cell9.setCellValue(info.getCpuProcessorUtilization1());
            cell10.setCellValue(info.getMaxCpuProcessorUtilization1());
            cell11.setCellValue(info.getCpuProcessorUtilization2());
            cell12.setCellValue(info.getMaxCpuProcessorUtilization2());
            cell13.setCellValue(info.getCpuProcessorUtilization0());
            cell14.setCellValue(info.getMaxCpuProcessorUtilization0());
            cell15.setCellValue(info.getMemUsedPer1());
            cell16.setCellValue(info.getMaxMemUsedPer1());
            cell17.setCellValue(info.getMemUsedPer2());
            cell18.setCellValue(info.getMaxMemUsedPer2());
            cell19.setCellValue(info.getMemUsedPer0());
            cell20.setCellValue(info.getMaxMemUsedPer0());
            cell21.setCellValue(info.getSwapMemUsedPer1());
            cell22.setCellValue(info.getMaxSwapMemUsedPer1());
            cell23.setCellValue(info.getSwapMemUsedPer2());
            cell24.setCellValue(info.getMaxSwapMemUsedPer2());
            cell25.setCellValue(info.getSwapMemUsedPer0());
            cell26.setCellValue(info.getMaxSwapMemUsedPer0());
            cell27.setCellValue(info.getDiskIOspeed1());
            cell28.setCellValue(info.getMaxDiskIOspeed1());
            cell29.setCellValue(info.getDiskIOspeed2());
            cell30.setCellValue(info.getMaxDiskIOspeed2());
            cell31.setCellValue(info.getDiskIOspeed0());
            cell32.setCellValue(info.getMaxDiskIOspeed0());
        }
        if (("vmAll").equals(whichSheet)) { // 月度性能统计---查询虚拟机数据All
            Cell cell0 = row.getCell(0);
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
            Cell cell16 = row.getCell(16);
            Cell cell17 = row.getCell(17);
            Cell cell18 = row.getCell(18);
            Cell cell19 = row.getCell(19);
            Cell cell20 = row.getCell(20);
            Cell cell21 = row.getCell(21);
            Cell cell22 = row.getCell(22);
            Cell cell23 = row.getCell(23);
            Cell cell24 = row.getCell(24);
            Cell cell25 = row.getCell(25);
            cell0.setCellValue(info.getPoolPartName());
            cell1.setCellValue(info.getAppName());
            cell2.setCellValue(info.getIp());
            cell3.setCellValue(info.getCpuNum());
            cell4.setCellValue(info.getMemorySize());
            cell5.setCellValue(info.getOs());
            cell6.setCellValue(info.getAppContacts());
            cell7.setCellValue(info.getOnlineTime());
            cell8.setCellValue(info.getCpuProcessorUtilization1());
            cell9.setCellValue(info.getMaxCpuProcessorUtilization1());
            cell10.setCellValue(info.getCpuProcessorUtilization2());
            cell11.setCellValue(info.getMaxCpuProcessorUtilization2());
            cell12.setCellValue(info.getCpuProcessorUtilization0());
            cell13.setCellValue(info.getMaxCpuProcessorUtilization0());
            cell14.setCellValue(info.getMemUsedPer1());
            cell15.setCellValue(info.getMaxMemUsedPer1());
            cell16.setCellValue(info.getMemUsedPer2());
            cell17.setCellValue(info.getMaxMemUsedPer2());
            cell18.setCellValue(info.getMemUsedPer0());
            cell19.setCellValue(info.getMaxMemUsedPer0());
            cell20.setCellValue(info.getDiskIOspeed1());
            cell21.setCellValue(info.getMaxDiskIOspeed1());
            cell22.setCellValue(info.getDiskIOspeed2());
            cell23.setCellValue(info.getMaxDiskIOspeed2());
            cell24.setCellValue(info.getDiskIOspeed0());
            cell25.setCellValue(info.getMaxDiskIOspeed0());
        }
        if (("PmAllCpu").equals(whichSheet)) { // 月度性能统计---查询物理机cpu
            Cell cell0 = row.getCell(0);
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
            cell0.setCellValue(info.getPoolPartName());
            cell1.setCellValue(info.getAppName());
            cell2.setCellValue(info.getIp());
            cell3.setCellValue(info.getServerType());
            cell4.setCellValue(info.getCpuNum());
            cell5.setCellValue(info.getMemorySize());
            cell6.setCellValue(info.getOs());
            cell7.setCellValue(info.getAppContacts());
            cell8.setCellValue(info.getOnlineTime());
            cell9.setCellValue(info.getCpuProcessorUtilization());
            cell10.setCellValue(info.getMaxCpuProcessorUtilization());
        }
        if (("PmAllMem").equals(whichSheet)) { // 月度性能统计---查询物理机mem
            Cell cell0 = row.getCell(0);
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
            cell0.setCellValue(info.getPoolPartName());
            cell1.setCellValue(info.getAppName());
            cell2.setCellValue(info.getIp());
            cell3.setCellValue(info.getServerType());
            cell4.setCellValue(info.getCpuNum());
            cell5.setCellValue(info.getMemorySize());
            cell6.setCellValue(info.getOs());
            cell7.setCellValue(info.getAppContacts());
            cell8.setCellValue(info.getOnlineTime());
            cell9.setCellValue(info.getMemUsedPer());
            cell10.setCellValue(info.getMaxMemUsedPer());
        }
        if (("PmAllPage").equals(whichSheet)) { // 月度性能统计---查询物理机page
            Cell cell0 = row.getCell(0);
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
            cell0.setCellValue(info.getPoolPartName());
            cell1.setCellValue(info.getAppName());
            cell2.setCellValue(info.getIp());
            cell3.setCellValue(info.getServerType());
            cell4.setCellValue(info.getCpuNum());
            cell5.setCellValue(info.getMemorySize());
            cell6.setCellValue(info.getOs());
            cell7.setCellValue(info.getAppContacts());
            cell8.setCellValue(info.getOnlineTime());
            cell9.setCellValue(info.getSwapMemUsedPer());
            cell10.setCellValue(info.getMaxSwapMemUsedPer());
        }
        if (("PmAllIOspeed").equals(whichSheet)) { // 月度性能统计---查询物理机page
            Cell cell0 = row.getCell(0);
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
            cell0.setCellValue(info.getPoolPartName());
            cell1.setCellValue(info.getAppName());
            cell2.setCellValue(info.getIp());
            cell3.setCellValue(info.getServerType());
            cell4.setCellValue(info.getCpuNum());
            cell5.setCellValue(info.getMemorySize());
            cell6.setCellValue(info.getOs());
            cell7.setCellValue(info.getAppContacts());
            cell8.setCellValue(info.getOnlineTime());
            cell9.setCellValue(info.getDiskIOspeed());
            cell10.setCellValue(info.getMaxDiskIOspeed());
        }
        if (("VmAllCpu").equals(whichSheet)) { // 月度性能统计---查询虚拟机数据cpu
            Cell cell0 = row.getCell(0);
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);
            Cell cell5 = row.getCell(5);
            Cell cell6 = row.getCell(6);
            Cell cell7 = row.getCell(7);
            Cell cell8 = row.getCell(8);
            Cell cell9 = row.getCell(9);
            cell0.setCellValue(info.getPoolPartName());
            cell1.setCellValue(info.getAppName());
            cell2.setCellValue(info.getIp());
            cell3.setCellValue(info.getCpuNum());
            cell4.setCellValue(info.getMemorySize());
            cell5.setCellValue(info.getOs());
            cell6.setCellValue(info.getAppContacts());
            cell7.setCellValue(info.getOnlineTime());
            cell8.setCellValue(info.getCpuProcessorUtilization());
            cell9.setCellValue(info.getMaxCpuProcessorUtilization());
        }
        if (("VmAllMem").equals(whichSheet)) { // 月度性能统计---查询虚拟机数据mem
            Cell cell0 = row.getCell(0);
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);
            Cell cell5 = row.getCell(5);
            Cell cell6 = row.getCell(6);
            Cell cell7 = row.getCell(7);
            Cell cell8 = row.getCell(8);
            Cell cell9 = row.getCell(9);
            cell0.setCellValue(info.getPoolPartName());
            cell1.setCellValue(info.getAppName());
            cell2.setCellValue(info.getIp());
            cell3.setCellValue(info.getCpuNum());
            cell4.setCellValue(info.getMemorySize());
            cell5.setCellValue(info.getOs());
            cell6.setCellValue(info.getAppContacts());
            cell7.setCellValue(info.getOnlineTime());
            cell8.setCellValue(info.getMemUsedPer());
            cell9.setCellValue(info.getMaxMemUsedPer());
        }
        if (("VmAllIOspeed").equals(whichSheet)) { // 月度性能统计---查询虚拟机数据mem
            Cell cell0 = row.getCell(0);
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);
            Cell cell5 = row.getCell(5);
            Cell cell6 = row.getCell(6);
            Cell cell7 = row.getCell(7);
            Cell cell8 = row.getCell(8);
            Cell cell9 = row.getCell(9);
            cell0.setCellValue(info.getPoolPartName());
            cell1.setCellValue(info.getAppName());
            cell2.setCellValue(info.getIp());
            cell3.setCellValue(info.getCpuNum());
            cell4.setCellValue(info.getMemorySize());
            cell5.setCellValue(info.getOs());
            cell6.setCellValue(info.getAppContacts());
            cell7.setCellValue(info.getOnlineTime());
            cell8.setCellValue(info.getDiskIOspeed());
            cell9.setCellValue(info.getMaxDiskIOspeed());
        }
        // if (("VmAllPage").equals(whichSheet)) { // 月度性能统计---查询虚拟机数据page
        // Cell cell0 = row.getCell(0);
        // Cell cell1 = row.getCell(1);
        // Cell cell2 = row.getCell(2);
        // Cell cell3 = row.getCell(3);
        // Cell cell4 = row.getCell(4);
        // Cell cell5 = row.getCell(5);
        // Cell cell6 = row.getCell(6);
        // Cell cell7 = row.getCell(7);
        // Cell cell8 = row.getCell(8);
        // cell0.setCellValue(info.getPoolPartName());
        // cell1.setCellValue(info.getAppName());
        // cell2.setCellValue(info.getIp());
        // cell3.setCellValue(info.getCpuNum());
        // cell4.setCellValue(info.getMemorySize());
        // cell5.setCellValue(info.getOs());
        // cell6.setCellValue(info.getAppContacts());
        // cell7.setCellValue(info.getSwapMemUsedPer());
        // cell8.setCellValue(info.getMaxSwapMemUsedPer());
        // }
    }

    private void copyRow(Row fromRow, Row toRow) {
        for (int i = 0; i < 33; i++) {
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
        font.setFontHeightInPoints((short) 11);// 字体大小
        font.setCharSet(XSSFFont.DEFAULT_CHARSET);
    }

    /**
     * @return the startMonthDateExport
     */
    public String getStartMonthDateExport() {
        return startMonthDateExport;
    }

    /**
     * @param startMonthDateExport the startMonthDateExport to set
     */
    public void setStartMonthDateExport(String startMonthDateExport) {
        this.startMonthDateExport = startMonthDateExport;
    }

    /**
     * @return the endMonthDateExport
     */
    public String getEndMonthDateExport() {
        return endMonthDateExport;
    }

    /**
     * @param endMonthDateExport the endMonthDateExport to set
     */
    public void setEndMonthDateExport(String endMonthDateExport) {
        this.endMonthDateExport = endMonthDateExport;
    }

    /**
     * @return the startMonthDateForSql
     */
    public String getStartMonthDateForSql() {
        return startMonthDateForSql;
    }

    /**
     * @param startMonthDateForSql the startMonthDateForSql to set
     */
    public void setStartMonthDateForSql(String startMonthDateForSql) {
        this.startMonthDateForSql = startMonthDateForSql;
    }

    /**
     * @return the endMonthDateForSql
     */
    public String getEndMonthDateForSql() {
        return endMonthDateForSql;
    }

    /**
     * @param endMonthDateForSql the endMonthDateForSql to set
     */
    public void setEndMonthDateForSql(String endMonthDateForSql) {
        this.endMonthDateForSql = endMonthDateForSql;
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

    public String getExportWay() {
        return exportWay;
    }

    public void setExportWay(String exportWay) {
        this.exportWay = exportWay;
    }

    public MonthPerformanceInfo getMonthPerformanceInfo() {
        return monthPerformanceInfo;
    }

    public void setMonthPerformanceInfo(MonthPerformanceInfo monthPerformanceInfo) {
        this.monthPerformanceInfo = monthPerformanceInfo;
    }

    /**
     * @return the resPoolId
     */
    public String getResPoolId() {
        return resPoolId;
    }

    /**
     * @param resPoolId the resPoolId to set
     */
    public void setResPoolId(String resPoolId) {
        this.resPoolId = resPoolId;
    }

    /**
     * @return the poolPartId
     */
    public String getPoolPartId() {
        return poolPartId;
    }

    /**
     * @param poolPartId the poolPartId to set
     */
    public void setPoolPartId(String poolPartId) {
        this.poolPartId = poolPartId;
    }

    /**
     * @return the orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * @return the dateType
     */
    public String getDateType() {
        return dateType;
    }

    /**
     * @param dateType the dateType to set
     */
    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

}
