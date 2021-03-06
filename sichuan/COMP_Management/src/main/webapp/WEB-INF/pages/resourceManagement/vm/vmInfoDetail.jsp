<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

       <h1>虚拟机</h1>
      <div class="details-con">
   	      <div class="detail-title">
              <a href="javascript:void(0)" id='goBack' title="返回">
                  <span class="back"></span>
              </a>
              <h3 class="apply-title">虚拟机详情</h3>
              <ul class="title-btn">
	           <li class="select">
	           <s:url id="resViewVmInfoDetailUrl" action="resViewVmInfoDetail">
					<s:param name="vmInfo.vmId">${vmInfo.vmId}</s:param>
					<s:param name="nodeType">${nodeType}</s:param>
					<s:param name="nodeId">${nodeId}</s:param>
					<s:param name="treeNodeName">${treeNodeName}</s:param>
					<s:param name="pnodeId">${pnodeId}</s:param>
					<s:param name="pnodeName">${pnodeName}</s:param>
					<s:param name="appId">${appId}</s:param>
					<s:param name="curFun">${curFun}</s:param>
				</s:url>
				<a href="${resViewVmInfoDetailUrl}">配置属性</a>
	           </li>
	           <li>
		           <s:url id="realTimeUrl" action="realTimeReport">
					<s:param name="deviceId">${vmInfo.vmId}</s:param>
					<s:param name="staType">CPU</s:param>
					<s:param name="deviceType">VM</s:param>
					<s:param name="nodeType">${nodeType}</s:param>
					<s:param name="nodeId">${nodeId}</s:param>
					<s:param name="treeNodeName">${treeNodeName}</s:param>
					<s:param name="pnodeId">${pnodeId}</s:param>
					<s:param name="pnodeName">${pnodeName}</s:param>
					<s:param name="appId">${appId}</s:param>
					<s:param name="curFun">${curFun}</s:param>
				</s:url>
				<a href="${realTimeUrl}">性能指标</a>
	           </li>
	         </ul>
          </div>
		  <s:form method="post" id="formId">
   	    	  <input type="hidden" id="msg" name="msg" value="" />
   	    	  <input type="hidden" id="errMsg" name="errMsg" value="" />
   	    	  <s:hidden id="nodeType" name="nodeType"></s:hidden>
	          <s:hidden id="nodeId" name="nodeId"></s:hidden>
	          <s:hidden id="treeNodeName" name="treeNodeName"></s:hidden>
	          <s:hidden id="pnodeId" name="pnodeId"></s:hidden>
	          <s:hidden id="pnodeName" name="pnodeName"></s:hidden>
	          <s:hidden id="appId" name="appId"></s:hidden>
	          <s:hidden id="curFun" name="curFun"></s:hidden>
   	      </s:form>
        
        
		<div class="page-form">    
		    <div class="contentDetail">
		    	<div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">位置信息</span>
		            </h5>
		            <ul class="result-list" style="width: 55%">
		            	<li><span class="result-name">所属资源池：</span><span><s:property value="vmInfo.resPoolName" />&nbsp;</span></li>
		                <li><span class="result-name">所属物理机：</span><span><a href="" onclick='pmInfoDetail("<s:property value="vmInfo.pmId" />");return false;'><s:property value="vmInfo.pmName" />&nbsp;</a></span></li>
		            </ul>
		            <ul class="result-list" style="width: 45%">
		            	<li><span class="result-name">所属分区：</span><span><s:property value="vmInfo.resPoolPartName" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">基本信息</span>
		            </h5>
		            <ul class="result-list" style="width: 55%">
		            	<li><span class="result-name">虚拟机ID：</span><span><s:property value="vmInfo.vmId" />&nbsp;</span></li>
		                <li><span class="result-name">所属业务组：</span><span><s:property value="vmInfo.appName" />&nbsp;</span></li>
		                <li><span class="result-name">镜像名称：</span><span><s:property value="vmInfo.vmOs" />&nbsp;</span></li>
		                <li><span class="result-name">投入生产运行时间：</span><span><s:property value="vmInfo.runTime" />&nbsp;</span></li>
                   <%-- <li><span class="result-name">更新时间：</span><span><s:property value="vmInfo.updateTime" />&nbsp;</span></li> --%>
		                <li><span class="result-name">IP地址：</span><span><s:property value="vmInfo.privateIp" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list" style="width: 45%">
		            	<li><span class="result-name">虚拟机名称：</span><span><s:property value="vmInfo.vmName" />&nbsp;</span></li>
		            	<li><span class="result-name">状态：</span>
		            	    <span>
						        <s:if test="vmInfo.curStatus == \"0\""><img style="margin-bottom: -3px" alt="不可用" src="../images/pm_avliable.png">不可用</s:if>
							<s:elseif test="vmInfo.curStatus == \"1\""><img style="margin-bottom: -3px" alt="已加电" src="../images/pm_powerup.png">已加电</s:elseif>
							<s:elseif test="vmInfo.curStatus == \"2\""><img style="margin-bottom: -3px" alt="运行" src="../images/pm_running.png">运行</s:elseif>
							<s:elseif test="vmInfo.curStatus == \"3\""><img style="margin-bottom: -3px" alt="关机" src="../images/pm_shutdown.png">关机</s:elseif>
							<s:elseif test="vmInfo.curStatus == \"4\""><img style="margin-bottom: -3px" alt="休眠" src="../images/pm_pause.png">休眠</s:elseif>
							<s:elseif test="vmInfo.curStatus == \"5\""><img style="margin-bottom: -3px" alt="处理中" src="../images/pm_processing.png">处理中</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		            	
		            	<li><span class="result-name">虚拟磁盘位置：</span><span><s:property value="vmInfo.osType" />&nbsp;</span></li>
                  <%--  <li><span class="result-name">IP地址：</span><span><s:property value="vmInfo.privateIp" />&nbsp;</span></li> --%>
		            	<li><span class="result-name">更新时间：</span><span><s:property value="vmInfo.updateTime" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">配置属性</span>
		            </h5>
		            <ul class="result-list" style="width: 55%">
		            	<li><span class="result-name">cpu数量(个)：</span><span><s:property value="vmInfo.cpuNum" />&nbsp;</span></li>
		                <li><span class="result-name">内存(GB)：</span><span><s:property value="vmInfo.memorySize" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list" style="width: 45%">
		            	<!-- <li><span class="result-name">cpu主频(MHz)：</span><span><s:property value="vmInfo.cpuFrequency" />&nbsp;</span></li> -->
		            	<li><span class="result-name">磁盘空间(GB)：</span><span><s:property value="vmInfo.diskSize" />&nbsp;</span></li>
		            	<li><span class="result-name">镜像(GB)：</span><span><s:property value="50" />&nbsp;</span></li>
		            </ul>
		        </div> 
		        <div class="result" style="width: 130%">
		            <table class="result-grid">
		            	<thead>
		            	    <th width="44%">块名称</th>
		            	    <th width="28%" style="text-align: left">容量(GB)</th>
		            	    <th width="28%" style="text-align: left">当前状态</th>
		                </thead>
			        </table>
		            <div style="width: 701px; height: 115px; overflow:auto;overflow-x: hidden;">
		                <table class="result-grid">
		                <tbody >
						    <s:iterator value="ebsInfoList" status="rowstatus">
							    <tr <s:if test="#rowstatus.even == true">class="even"</s:if><s:else>class=""</s:else>>
							         <s:set name="ebsName" value="" />
									 <s:set name="ebsName" value="ebsName" />
								     <s:set name="diskSize" value="" />
								     <s:set name="diskSize" value="diskSize" />
									 <s:set name="curStatus" value="" />
								     <s:set name="curStatus" value="curStatus" />
								    <td width="44%" align="center" ><s:property value="ebsName" /></td>
				                    <td width="28%" style="text-align: left" ><s:property value="diskSize" /></td>
				                    <td width="28%" style="text-align: left" >
								        <s:if test="curStatus == \"1\"">已创建</s:if>
								        <s:elseif test="curStatus == \"2\"">已挂载</s:elseif>
								        <s:elseif test="curStatus == \"3\"">创建失败</s:elseif>
								        <s:elseif test="curStatus == \"4\"">挂载失败</s:elseif>
								        <s:elseif test="curStatus == \"5\"">创建中</s:elseif>
				                    </td>
							    </tr>
						     </s:iterator>
					    </tbody>
		            </table>
		            </div>
				</div>
		    </div>	
		</div>
		<div style="padding: 15px;clear: both">
           
		</div>
    </div>
    
    <div id="pmInfoDetail" style="display:none;width:800px;">    
		    <div class="contentDetail">
		    	<div class="result">
		        	<h5 class="label">
		            	<span class="label-bg">位置信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">所属资源池：</span><span><s:property value="pmInfo.resPoolName" />&nbsp;</span></li>
		                <li><span class="result-name">机房名称：</span><span><s:property value="pmInfo.machinerRoomName" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">所属分区：</span><span><s:property value="pmInfo.resPoolPartName" />&nbsp;</span></li>
		            	<li><span class="result-name">机柜名称：</span><span><s:property value="pmInfo.cabinetName" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result">
		        	<h5 class="label">
		            	<span class="label-bg">基本信息</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">物理机ID：</span><span><s:property value="pmInfo.pmId" />&nbsp;</span></li>
		                <li><span class="result-name">所属业务组：</span><span><s:property value="pmInfo.appName" />&nbsp;</span></li>
		                <li><span class="result-name">运行状态：</span>
		            	    <span>
						        <s:if test="pmInfo.curStatus == \"0\""><img style="margin-bottom: -3px" alt="不可用" src="../images/pm_avliable.png">不可用</s:if>
							<s:elseif test="pmInfo.curStatus == \"1\""><img style="margin-bottom: -3px" alt="已加电" src="../images/pm_powerup.png">已加电</s:elseif>
							<s:elseif test="pmInfo.curStatus == \"2\""><img style="margin-bottom: -3px" alt="运行" src="../images/pm_running.png">运行</s:elseif>
							<s:elseif test="pmInfo.curStatus == \"3\""><img style="margin-bottom: -3px" alt="关机" src="../images/pm_shutdown.png">关机</s:elseif>
							<s:elseif test="pmInfo.curStatus == \"4\""><img style="margin-bottom: -3px" alt="休眠" src="../images/pm_pause.png">休眠</s:elseif>
							<s:elseif test="pmInfo.curStatus == \"5\""><img style="margin-bottom: -3px" alt="处理中" src="../images/pm_processing.png">处理中</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		                <li><span class="result-name">操作系统：</span><span><s:property value="pmInfo.osType" />&nbsp;</span></li>
		                <li><span class="result-name">主机型号：</span><span><s:property value="pmInfo.serverType" />&nbsp;</span></li>
		                <li><span class="result-name">更新时间：</span><span><s:property value="pmInfo.updateTime" />&nbsp;</span></li>
		                <li style="width: 450px"><span class="result-name">IP地址：</span><span><s:property value="pmInfo.ip" />&nbsp;</span></li>
                        <%-- <li><span class="result-name">更新时间：</span><span><s:property value="pmInfo.updateTime" />&nbsp;</span></li> --%>
		                <%-- <li><span class="result-name">投入生产运行时间：</span><span><s:property value="pmInfo.runTime" />&nbsp;</span></li>--%>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">物理机名称：</span><span><s:property value="pmInfo.pmName" />&nbsp;</span></li>
		            	<li><span class="result-name">分配状态：</span>
		            	    <span>
						        <s:if test="pmInfo.pmState == \"0\"">未分配</s:if>
							    <s:elseif test="pmInfo.pmState == \"1\"">已分配</s:elseif>
							    <s:else>不可用</s:else>
						        &nbsp;
		            	    </span>
		            	</li>
		            	<li><span class="result-name">设备厂商：</span><span><s:property value="pmInfo.vendorName" />&nbsp;</span></li>
		            	<!-- <li><span class="result-name">网卡规格：</span><span><s:property value="pmInfo.ethadaType" />&nbsp;</span></li> -->
		            	<li><span class="result-name">CPU序列号：</span><span><s:property value="pmInfo.cpueqpserialnum" />&nbsp;</span></li>
		            	<li><span class="result-name">设备序列号：</span><span><s:property value="pmInfo.serialNum" />&nbsp;</span></li>
		            	<li><span class="result-name">网络掩码：</span><span><s:property value="pmInfo.netMask" />&nbsp;</span></li>
		            </ul>
		        </div>
		        <div class="result">
		        	<h5 class="label">
		            	<span class="label-bg">配置属性</span>
		            </h5>
		            <ul class="result-list">
		            	<li><span class="result-name">CPU数量(个)：</span><span><s:property value="pmInfo.cpuNum" />&nbsp;</span></li>
		            	<%-- <li><span class="result-name">cpu类型：</span><span><s:property value="pmInfo.cpuType" />&nbsp;</span></li>--%>
		            	<li><span class="result-name">单CPU核数：</span><span><s:property value="pmInfo.nucNumPerCpu" />&nbsp;</span></li>
		                <li><span class="result-name">内存(GB)：</span><span><s:property value="pmInfo.memorySize" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list">
		            	<li><span class="result-name">CPU主频(MHz)：</span><span><s:property value="pmInfo.cpuFrequency" />&nbsp;</span></li>
		            	<li><span class="result-name">CPU型号：</span><span><s:property value="pmInfo.cpuModel" />&nbsp;</span></li>
		            	<li><span class="result-name">磁盘空间(GB)：</span><span><s:property value="pmInfo.diskSize" />&nbsp;</span></li>
		            </ul>
		        </div>
		        
		        <div class="result">
		        	<h5 class="label">
		            	<span class="label-bg">维护信息</span>
		            </h5>
		            <ul class="result-list">
		                <li><span class="result-name">NTP服务IP：</span><span><s:property value="pmInfo.ntpIp" />&nbsp;</span></li>
		                <li><span class="result-name">网关：</span><span><s:property value="pmInfo.gateway" />&nbsp;</span></li>
		                <li><span class="result-name">备份主机：</span><span><s:property value="pmInfo.bkPm" />&nbsp;</span></li>
		                <li><span class="result-name">系统交换区大小(MB)：</span><span><s:property value="pmInfo.sysExchangeAreaSize" />&nbsp;</span></li>
		                <!-- <li><span class="result-name">应用描述：</span><span><s:property value="pmInfo.applicationdescribe" />&nbsp;</span></li> -->
		            </ul>
		            <ul class="result-list">
		            	<!-- <li><span class="result-name">TCPP值：</span><span><s:property value="pmInfo.tcppValue" />&nbsp;</span></li> -->
		            	<!-- <li><span class="result-name">维保厂商：</span><span><s:property value="pmInfo.maintenanceFactory" />&nbsp;</span></li> -->
		            	<li><span class="result-name">互备方式描述：</span><span><s:property value="pmInfo.hbDescription" />&nbsp;</span></li>
		            	<li><span class="result-name">业务联系人：</span><span><s:property value="pmInfo.businessContact" />&nbsp;</span></li>
		            	<!-- <li><span class="result-name">端口标签：</span><span><s:property value="pmInfo.portlabel" />&nbsp;</span></li> -->
		            	<li><span class="result-name">上线时间：</span><span><s:property value="pmInfo.onlineTime" />&nbsp;</span></li>
		            	<li><span class="result-name">连接的交换机及端口：</span><span><s:property value="pmInfo.switchIfRelations" />&nbsp;</span></li>
		            </ul>
		        </div>
		    </div>	
		</div>
    
    <div class="page-button" >
            <ul class="opt-bottom-btn">
                <li>
                    <a href="javascript:void(0)" id="cancel">取消</a>
                </li>
            </ul>
	</div>
<script type="text/javascript">
$("#goBack").click(function(){
		backList();
	});
	
$("#cancel").click(function(){
	backList();
});
function backList(){
	var form = document.getElementById("formId");
	var nodeType = document.getElementById('nodeType').value;
	var nodeId = document.getElementById('nodeId').value;
	var treeNodeName = document.getElementById('treeNodeName').value;
	var pnodeId = document.getElementById('pnodeId').value;
	var pnodeName = document.getElementById('pnodeName').value;
	var appId = document.getElementById('appId').value;
	var curFun = document.getElementById('curFun').value;
	
	form.action = "resViewVmListAction.action?vmInfo.nodeType=" + nodeType + "&vmInfo.nodeId="
		+ nodeId + "&vmInfo.treeNodeName="
		+ treeNodeName + "&vmInfo.pnodeId="
		+ pnodeId + "&vmInfo.pnodeName="
		+ pnodeName + "&vmInfo.appId="
		+ appId + "&vmInfo.curFun=" + curFun;
	form.submit();
};
function pmInfoDetail(pmId){
		$.dialog({
			title : '物理机详细信息',
			content : document.getElementById('pmInfoDetail'),
			cancelVal : '关闭',
			cancel : true,
			lock : true
		});
}
</script>