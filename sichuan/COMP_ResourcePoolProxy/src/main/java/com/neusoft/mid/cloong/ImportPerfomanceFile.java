/*******************************************************************************
 * @(#)ImportPerfomanceFile.java 2015年1月23日
 *
 * Copyright 2015 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.neusoft.mid.cloong.resourcePool.businessOpt.resStat.CycReportResStatus.job.CycReportResStatusJob;
import com.neusoft.mid.cloong.rpws.private1072.resourceDetail.xmlbean.NMSIPM;

/**
 * 性能文件导入类
 * @author <a href="mailto:feng.jian@neusoft.com">feng.jian</a>
 * @version $Revision 1.0 $ 2015年1月23日 下午1:49:21
 */
public class ImportPerfomanceFile extends HttpServlet {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2301500773368434589L;

    /**
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            String perFilePath = req.getParameter("filepath");
            ServletContext sc = getServletContext();
            WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(sc);
            CycReportResStatusJob bean = (CycReportResStatusJob) wc.getBean("resStatusJob");

            File[] listFiles = new File(perFilePath).listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return !pathname.isDirectory();
                }
            });

            List<String> filePathStringList = new ArrayList<String>();
            for (File file : listFiles) {
                filePathStringList.add(file.getAbsolutePath());
            }

            List<NMSIPM> praseResPMFile = bean.praseResPMFile(filePathStringList);
            bean.saveToDB(praseResPMFile);

            int r = 0;
            for (NMSIPM nmsipm : praseResPMFile) {
                r += nmsipm.getPMSRVINFOOrPMVMINFOOrPMMCPINFO().size();
            }
            resp.setHeader("Content-type", "text/html;charset=UTF-8");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print("共发现" + filePathStringList.size() + "个文件.<br>");
            resp.getWriter().print("文件列表如下：" + filePathStringList + "<br>");
            resp.getWriter().print("共导入指标数：" + r + "条<br>");
        } catch (Exception e) {
            resp.getWriter().print("执行异常," + e.getLocalizedMessage());
            e.printStackTrace();
        }
        resp.getWriter().flush();
    }

}
