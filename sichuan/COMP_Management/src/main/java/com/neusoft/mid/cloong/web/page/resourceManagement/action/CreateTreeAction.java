/*******************************************************************************
 * @(#)CreateTreeAction.java 2014-12-31
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.web.page.resourceManagement.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.neusoft.mid.cloong.common.core.CommonStatusCode;
import com.neusoft.mid.cloong.web.action.ConstantEnum;
import com.neusoft.mid.cloong.web.page.resourceManagement.info.CreateTreeInfo;
import com.neusoft.mid.iamp.logger.LogService;

/**
 * 生成菜单树Action
 * 
 * @author <a href="mailto:wuzhenyue@neusoft.com">wuzhenyue</a>
 * @version $Revision 1.1 $ 2014-12-30 下午03:42:16
 */
public class CreateTreeAction extends ResourceManagementBaseAction {

	private static final long serialVersionUID = 1L;

	private static LogService logger = LogService.getLogger(CreateTreeAction.class);

	private String result;

	private JSONArray jsonArray;

	/** 当前功能名称 (资源视图、业务视图) */
	private String curFun;

	/** 当前节点ID */
	private String nodeId;

	/** 当前节点的父节点ID */
	private String pnodeId;

	public String execute() {

		if ("zyst".equals(curFun)) { // 资源视图
			jsonArray = new JSONArray();

			/* 一级节点-根节点 */
			JSONObject jsonObjectRoot = new JSONObject();
			jsonObjectRoot.put("id", "zyst");
			jsonObjectRoot.put("pId", "-1");
			jsonObjectRoot.put("name", "所有资源");
			jsonObjectRoot.put("t", "所有资源");
			jsonObjectRoot.put("pName", "");
			jsonObjectRoot.put("nodeType", "top");
			jsonObjectRoot.put("curFun", "zyst");
			jsonObjectRoot.put("skipUrl", "allResourceAction.action");
			jsonObjectRoot.put("open", true);
			jsonObjectRoot.put("selected", true);
			jsonObjectRoot.put("icon", "../images/tree_res.png");
			jsonArray.add(jsonObjectRoot);

			/* 二级节点-物理机 */
			JSONObject jsonObjectRes = new JSONObject();
			jsonObjectRes.put("id", "zyst-wlj");
			jsonObjectRes.put("pId", "zyst");
			jsonObjectRes.put("name", "物理机");
			jsonObjectRes.put("t", "物理机");
			jsonObjectRes.put("pName", "所有资源");
			jsonObjectRes.put("nodeType", "res");
			jsonObjectRes.put("curFun", "zyst");
			jsonObjectRes.put("skipUrl", "resViewPmListAction.action");
			jsonObjectRes.put("open", false);
			jsonObjectRes.put("icon", "../images/tree_pm.png");
			jsonArray.add(jsonObjectRes);

			/* 二级节点-虚拟机 */
			jsonObjectRes = new JSONObject();
			jsonObjectRes.put("id", "zyst-xnj");
			jsonObjectRes.put("pId", "zyst");
			jsonObjectRes.put("name", "虚拟机");
			jsonObjectRes.put("t", "虚拟机");
			jsonObjectRes.put("pName", "所有资源");
			jsonObjectRes.put("nodeType", "res");
			jsonObjectRes.put("curFun", "zyst");
			jsonObjectRes.put("skipUrl", "resViewVmListAction.action");
			jsonObjectRes.put("open", false);
			jsonObjectRes.put("icon", "../images/tree_vm.png");
			jsonArray.add(jsonObjectRes);

			/* 二级节点-虚拟硬盘 */
			jsonObjectRes = new JSONObject();
			jsonObjectRes.put("id", "zyst-xnyp");
			jsonObjectRes.put("pId", "zyst");
			jsonObjectRes.put("name", "云硬盘");
			jsonObjectRes.put("t", "云硬盘");
			jsonObjectRes.put("pName", "所有资源");
			jsonObjectRes.put("nodeType", "res");
			jsonObjectRes.put("curFun", "zyst");
			jsonObjectRes.put("skipUrl", "resViewEbsListAction.action");
			jsonObjectRes.put("open", false);
			jsonObjectRes.put("icon", "../images/tree_disk.png");
			jsonArray.add(jsonObjectRes);
			
			/* 二级节点-小型机
			jsonObjectRes = new JSONObject();
			jsonObjectRes.put("id", "zyst-xxj");
			jsonObjectRes.put("pId", "zyst");
			jsonObjectRes.put("name", "小型机");
			jsonObjectRes.put("t", "小型机");
			jsonObjectRes.put("pName", "所有资源");
			jsonObjectRes.put("nodeType", "res");
			jsonObjectRes.put("curFun", "zyst");
			jsonObjectRes.put("skipUrl", "miniPmListAction.action");
			jsonObjectRes.put("open", false);
			jsonObjectRes.put("icon", "../images/tree_miniPm.png");
			jsonArray.add(jsonObjectRes); */
			
			/* 二级节点-小型机分区 
			jsonObjectRes = new JSONObject();
			jsonObjectRes.put("id", "zyst-xxjfq");
			jsonObjectRes.put("pId", "zyst");
			jsonObjectRes.put("name", "小型机分区");
			jsonObjectRes.put("t", "小型机分区");
			jsonObjectRes.put("pName", "所有资源");
			jsonObjectRes.put("nodeType", "res");
			jsonObjectRes.put("curFun", "zyst");
			jsonObjectRes.put("skipUrl", "miniPmParListAction.action");
			jsonObjectRes.put("open", false);
			jsonObjectRes.put("icon", "../images/tree_miniPmPar.png");
			jsonArray.add(jsonObjectRes);*/
			
			/* 二级节点-存储阵列 */
            jsonObjectRes = new JSONObject();
            jsonObjectRes.put("id", "zyst-cczl");
            jsonObjectRes.put("pId", "zyst");
            jsonObjectRes.put("name", "存储阵列");
            jsonObjectRes.put("t", "存储阵列");
            jsonObjectRes.put("pName", "所有资源");
            jsonObjectRes.put("nodeType", "res");
            jsonObjectRes.put("curFun", "zyst");
            jsonObjectRes.put("skipUrl", "raidListAction.action");
            jsonObjectRes.put("open", false);
            jsonObjectRes.put("icon", "../images/tree_raid.png");
            jsonArray.add(jsonObjectRes);
            
            /* 二级节点-交换机 */
            jsonObjectRes = new JSONObject();
            jsonObjectRes.put("id", "zyst-jhj");
            jsonObjectRes.put("pId", "zyst");
            jsonObjectRes.put("name", "交换机");
            jsonObjectRes.put("t", "交换机");
            jsonObjectRes.put("pName", "所有资源");
            jsonObjectRes.put("nodeType", "res");
            jsonObjectRes.put("curFun", "zyst");
            jsonObjectRes.put("skipUrl", "swListAction.action");
            jsonObjectRes.put("open", false);
            jsonObjectRes.put("icon", "../images/tree_sw.png");
            jsonArray.add(jsonObjectRes);
            
            /* 二级节点-交换机端口 */
            jsonObjectRes = new JSONObject();
            jsonObjectRes.put("id", "zyst-lyq");
            jsonObjectRes.put("pId", "zyst");
            jsonObjectRes.put("name", "路由器");
            jsonObjectRes.put("t", "路由器");
            jsonObjectRes.put("pName", "所有资源");
            jsonObjectRes.put("nodeType", "res");
            jsonObjectRes.put("curFun", "zyst");
            jsonObjectRes.put("skipUrl", "rtListAction.action");
            jsonObjectRes.put("open", false);
            jsonObjectRes.put("icon", "../images/tree_rt.png");
            jsonArray.add(jsonObjectRes);
            
            /* 二级节点-交换机端口 */
            jsonObjectRes = new JSONObject();
            jsonObjectRes.put("id", "zyst-fhq");
            jsonObjectRes.put("pId", "zyst");
            jsonObjectRes.put("name", "防火墙");
            jsonObjectRes.put("t", "防火墙");
            jsonObjectRes.put("pName", "所有资源");
            jsonObjectRes.put("nodeType", "res");
            jsonObjectRes.put("curFun", "zyst");
            jsonObjectRes.put("skipUrl", "fwListAction.action");
            jsonObjectRes.put("open", false);
            jsonObjectRes.put("icon", "../images/tree_fw.png");
            jsonArray.add(jsonObjectRes);
		} else { // 业务视图
			jsonArray = new JSONArray();

			/* 一级节点-根节点 */
			JSONObject jsonObjectRoot = new JSONObject();
			jsonObjectRoot.put("id", "ywst");
			jsonObjectRoot.put("pId", "-1");
			jsonObjectRoot.put("name", "所有业务");
			jsonObjectRoot.put("t", "所有业务");
			jsonObjectRoot.put("pName", "");
			jsonObjectRoot.put("nodeType", "top");
			jsonObjectRoot.put("curFun", "ywst");
			jsonObjectRoot.put("skipUrl", "cpuExceededAction.action");
			jsonObjectRoot.put("open", true);
			jsonObjectRoot.put("selected", true);
            jsonObjectRoot.put("icon", "../images/tree_res.png");
			jsonArray.add(jsonObjectRoot);

			/* 二级节点 */
			CreateTreeInfo createTreeInfoQuery = new CreateTreeInfo();
			createTreeInfoQuery.setUserId(this.getCurrentUserId());
			List<CreateTreeInfo> createTreeInfoList = null;
			try {
				createTreeInfoList = ibatisDAO.getData("queryCreateTreeInfo", createTreeInfoQuery);
			} catch (SQLException e) {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
						"创建资源视图菜单树时查询错误！原因：查询业务时，数据库异常。", e);
				return ConstantEnum.ERROR.toString();
			} catch (Exception e) {
				logger.error(CommonStatusCode.DATABASE_OPERATION_ECXEPTION,
						"创建资源视图菜单树时查询错误！原因：查询业务时，数据库异常。", e);
				return ConstantEnum.ERROR.toString();
			}

			CreateTreeInfo createTreeInfo = null;
			JSONObject jsonObjectApp = null;
			JSONObject jsonObjectAppRes = null;
			for (int i = 0; i < createTreeInfoList.size(); i++) {
				createTreeInfo = createTreeInfoList.get(i);

				/* 二级节点-业务 */
				jsonObjectApp = new JSONObject();
				jsonObjectApp.put("id", "ywst-" + createTreeInfo.getAppId());
				jsonObjectApp.put("pId", "ywst");

				String appName = createTreeInfo.getAppName();
				jsonObjectApp.put("name", this.interceptStr(appName, 10, ""));
				jsonObjectApp.put("t", appName);

				jsonObjectApp.put("pName", "所有业务");
				jsonObjectApp.put("nodeType", "app");
				jsonObjectApp.put("curFun", "ywst");
				jsonObjectApp.put("skipUrl", "cpuUsedPerReportAction.action");
				jsonObjectApp.put("icon", "../images/tree_business.png");
				if (pnodeId != null && pnodeId.equals(jsonObjectApp.get("id"))) {
					jsonObjectApp.put("open", true);
				} else {
					jsonObjectApp.put("open", false);
				}
				jsonArray.add(jsonObjectApp);

				/* 三级节点 */
				/* 三级节点-物理机 */
				jsonObjectAppRes = new JSONObject();
				jsonObjectAppRes.put("id", "ywst-" + createTreeInfo.getAppId() + "-wlj");
				jsonObjectAppRes.put("pId", "ywst-" + createTreeInfo.getAppId());
				jsonObjectAppRes.put("name", "物理机");
				jsonObjectAppRes.put("t", "物理机");
				jsonObjectAppRes.put("pName", createTreeInfo.getAppName());
				jsonObjectAppRes.put("nodeType", "app_res");
				jsonObjectAppRes.put("curFun", "ywst");
				jsonObjectAppRes.put("skipUrl", "appViewPmListAction.action");
				jsonObjectAppRes.put("open", false);
				jsonObjectAppRes.put("icon", "../images/tree_pm.png");
				jsonArray.add(jsonObjectAppRes);

				/* 三级节点-虚拟机 */
				jsonObjectAppRes = new JSONObject();
				jsonObjectAppRes.put("id", "ywst-" + createTreeInfo.getAppId() + "-xnj");
				jsonObjectAppRes.put("pId", "ywst-" + createTreeInfo.getAppId());
				jsonObjectAppRes.put("name", "虚拟机");
				jsonObjectAppRes.put("t", "虚拟机");
				jsonObjectAppRes.put("pName", createTreeInfo.getAppName());
				jsonObjectAppRes.put("nodeType", "app_res");
				jsonObjectAppRes.put("curFun", "ywst");
				jsonObjectAppRes.put("skipUrl", "appViewVmListAction.action");
				jsonObjectAppRes.put("open", false);
				jsonObjectAppRes.put("icon", "../images/tree_vm.png");
				jsonArray.add(jsonObjectAppRes);

				/* 三级节点-虚拟硬盘 */
				jsonObjectAppRes = new JSONObject();
				jsonObjectAppRes.put("id", "ywst-" + createTreeInfo.getAppId() + "-xnyp");
				jsonObjectAppRes.put("pId", "ywst-" + createTreeInfo.getAppId());
				jsonObjectAppRes.put("name", "虚拟硬盘");
				jsonObjectAppRes.put("t", "虚拟硬盘");
				jsonObjectAppRes.put("pName", createTreeInfo.getAppName());
				jsonObjectAppRes.put("nodeType", "app_res");
				jsonObjectAppRes.put("curFun", "ywst");
				jsonObjectAppRes.put("skipUrl", "appViewEbsListAction.action");
				jsonObjectAppRes.put("open", false);
				jsonObjectAppRes.put("icon", "../images/tree_disk.png");
				jsonArray.add(jsonObjectAppRes);
				
				/* 三级节点-小型机
				jsonObjectAppRes = new JSONObject();
				jsonObjectAppRes.put("id", "ywst-" + createTreeInfo.getAppId() + "-xxj");
				jsonObjectAppRes.put("pId", "ywst-" + createTreeInfo.getAppId());
				jsonObjectAppRes.put("name", "小型机");
				jsonObjectAppRes.put("t", "小型机");
				jsonObjectAppRes.put("pName", createTreeInfo.getAppName());
				jsonObjectAppRes.put("nodeType", "app_res");
				jsonObjectAppRes.put("curFun", "ywst");
				jsonObjectAppRes.put("skipUrl", "miniPmListAction.action");
				jsonObjectAppRes.put("open", false);
				jsonObjectAppRes.put("icon", "../images/tree_miniPm.png");
				jsonArray.add(jsonObjectAppRes); */
				
				/* 三级节点-小型机分区
				jsonObjectAppRes = new JSONObject();
				jsonObjectAppRes.put("id", "ywst-" + createTreeInfo.getAppId() + "-xxjfq");
				jsonObjectAppRes.put("pId", "ywst-" + createTreeInfo.getAppId());
				jsonObjectAppRes.put("name", "小型机分区");
				jsonObjectAppRes.put("t", "小型机分区");
				jsonObjectAppRes.put("pName", createTreeInfo.getAppName());
				jsonObjectAppRes.put("nodeType", "app_res");
				jsonObjectAppRes.put("curFun", "ywst");
				jsonObjectAppRes.put("skipUrl", "miniPmParListAction.action");
				jsonObjectAppRes.put("open", false);
				jsonObjectAppRes.put("icon", "../images/tree_miniPmPar.png");
				jsonArray.add(jsonObjectAppRes); */
				
				/* 二级节点-存储阵列 */
	            jsonObjectAppRes = new JSONObject();
	            jsonObjectAppRes.put("id", "ywst-" + createTreeInfo.getAppId() + "-cczl");
	            jsonObjectAppRes.put("pId", "ywst-" + createTreeInfo.getAppId());
	            jsonObjectAppRes.put("name", "存储阵列");
	            jsonObjectAppRes.put("t", "存储阵列");
	            jsonObjectAppRes.put("pName", createTreeInfo.getAppName());
                jsonObjectAppRes.put("nodeType", "app_res");
                jsonObjectAppRes.put("curFun", "ywst");
	            jsonObjectAppRes.put("skipUrl", "raidListAction.action");
	            jsonObjectAppRes.put("open", false);
	            jsonObjectAppRes.put("icon", "../images/tree_raid.png");
	            jsonArray.add(jsonObjectAppRes);
	            
	            /* 二级节点-交换机 */
	            jsonObjectAppRes = new JSONObject();
	            jsonObjectAppRes.put("id", "ywst-" + createTreeInfo.getAppId() + "-jhj");
	            jsonObjectAppRes.put("pId", "ywst-" + createTreeInfo.getAppId());
	            jsonObjectAppRes.put("name", "交换机");
	            jsonObjectAppRes.put("t", "交换机");
	            jsonObjectAppRes.put("pName", createTreeInfo.getAppName());
                jsonObjectAppRes.put("nodeType", "app_res");
                jsonObjectAppRes.put("curFun", "ywst");
	            jsonObjectAppRes.put("skipUrl", "swListAction.action");
	            jsonObjectAppRes.put("open", false);
	            jsonObjectAppRes.put("icon", "../images/tree_sw.png");
	            jsonArray.add(jsonObjectAppRes);
	            
	            /* 二级节点-交换机端口 */
	            jsonObjectAppRes = new JSONObject();
	            jsonObjectAppRes.put("id", "ywst-" + createTreeInfo.getAppId() + "-lyq");
	            jsonObjectAppRes.put("pId", "ywst-" + createTreeInfo.getAppId());
	            jsonObjectAppRes.put("name", "路由器");
	            jsonObjectAppRes.put("t", "路由器");
	            jsonObjectAppRes.put("pName", createTreeInfo.getAppName());
                jsonObjectAppRes.put("nodeType", "app_res");
                jsonObjectAppRes.put("curFun", "ywst");
	            jsonObjectAppRes.put("skipUrl", "rtListAction.action");
	            jsonObjectAppRes.put("open", false);
	            jsonObjectAppRes.put("icon", "../images/tree_rt.png");
	            jsonArray.add(jsonObjectAppRes);
	            
	            /* 二级节点-交换机端口 */
	            jsonObjectAppRes = new JSONObject();
	            jsonObjectAppRes.put("id", "ywst-" + createTreeInfo.getAppId() + "-fhq");
	            jsonObjectAppRes.put("pId", "ywst-" + createTreeInfo.getAppId());
	            jsonObjectAppRes.put("name", "防火墙");
	            jsonObjectAppRes.put("t", "防火墙");
	            jsonObjectAppRes.put("pName", createTreeInfo.getAppName());
                jsonObjectAppRes.put("nodeType", "app_res");
                jsonObjectAppRes.put("curFun", "ywst");
	            jsonObjectAppRes.put("skipUrl", "fwListAction.action");
	            jsonObjectAppRes.put("open", false);
	            jsonObjectAppRes.put("icon", "../images/tree_fw.png");
	            jsonArray.add(jsonObjectAppRes);
			}
		}

		result = jsonArray.toString();

		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json;charset=utf-8");
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(result.getBytes("UTF-8"));
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 截取字符串　超出的字符用symbol代替
	 * @param len 字符串长度（GBK编码），1个汉字1个单位长度，1个全角数字或全角字母（不分大小写）1个单位长度，两个半角数字或两个半角字母（不分大小写）1个单位长度
	 * @param str 待截字符串
	 * @param symbol 截取后，补在字符串后面的符号，一般为...
	 * @return String
	 */
	private String interceptStr(String str, int len, String symbol) {
		int iLen = len * 2;
		int counterOfDoubleByte = 0; // 统计会不会截取到半个汉字，能被2整除就没有半个汉字，不能被2整除截取的串最后就是半个汉字
		String strRet = "";
		try {
			if (null != str) {
				byte[] b = str.getBytes("GBK");
				if (b.length > iLen) {
					for (int i = 0; i < iLen; i++) {
						if (b[i] < 0) { // 汉字拆成的两个字节都是小于0的
							counterOfDoubleByte++;
						}
					}
					if (counterOfDoubleByte % 2 == 0) {
						strRet = new String(b, 0, iLen, "GBK") + symbol;
					} else { // 截取的串，最后是半个汉字
						strRet = new String(b, 0, iLen - 1, "GBK") + symbol;
					}
				} else {
					strRet = str;
				}
			}
		} catch (Exception ex) {
			return str.substring(0, len);
		}
		
		return strRet;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCurFun() {
		return curFun;
	}

	public void setCurFun(String curFun) {
		this.curFun = curFun;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getPnodeId() {
		return pnodeId;
	}

	public void setPnodeId(String pnodeId) {
		this.pnodeId = pnodeId;
	}
}
