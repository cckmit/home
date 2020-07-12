<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="detail-title">
	<s:if test="deviceType == 'VM'">
		<s:url id="goBackUrl" action="resViewVmListAction">
			<s:param name="nodeType">${nodeType}</s:param>
			<s:param name="nodeId">${nodeId}</s:param>
			<s:param name="treeNodeName">${treeNodeName}</s:param>
			<s:param name="pnodeId">${pnodeId}</s:param>
			<s:param name="pnodeName">${pnodeName}</s:param>
			<s:param name="appId">${appId}</s:param>
			<s:param name="curFun">${curFun}</s:param>
		</s:url>
	</s:if>
	<s:elseif test="deviceType == 'PM'">
		<s:url id="goBackUrl" action="resViewPmListAction">
			<s:param name="nodeType">${nodeType}</s:param>
			<s:param name="nodeId">${nodeId}</s:param>
			<s:param name="treeNodeName">${treeNodeName}</s:param>
			<s:param name="pnodeId">${pnodeId}</s:param>
			<s:param name="pnodeName">${pnodeName}</s:param>
			<s:param name="appId">${appId}</s:param>
			<s:param name="curFun">${curFun}</s:param>
		</s:url>
	</s:elseif>
	<s:elseif test="deviceType == 'MINIPM'">
		<s:url id="goBackUrl" action="miniPmListAction">
			<s:param name="nodeType">${nodeType}</s:param>
			<s:param name="nodeId">${nodeId}</s:param>
			<s:param name="treeNodeName">${treeNodeName}</s:param>
			<s:param name="pnodeId">${pnodeId}</s:param>
			<s:param name="pnodeName">${pnodeName}</s:param>
			<s:param name="appId">${appId}</s:param>
			<s:param name="curFun">${curFun}</s:param>
		</s:url>
	</s:elseif>
	<s:else>
		<s:url id="goBackUrl" action="miniPmParListAction">
			<s:param name="nodeType">${nodeType}</s:param>
			<s:param name="nodeId">${nodeId}</s:param>
			<s:param name="treeNodeName">${treeNodeName}</s:param>
			<s:param name="pnodeId">${pnodeId}</s:param>
			<s:param name="pnodeName">${pnodeName}</s:param>
			<s:param name="appId">${appId}</s:param>
			<s:param name="curFun">${curFun}</s:param>
		</s:url>
	</s:else>
    <a href="${goBackUrl}" title="返回"><span class="back"></span></a>
    <h3 class="apply-title">
	    <s:if test="deviceType == 'VM'">虚拟机性能指标</s:if>
	    <s:elseif test="deviceType == 'PM'">物理机性能指标</s:elseif>
	    <s:elseif test="deviceType == 'MINIPM'">小型机性能指标</s:elseif>
	    <s:else>小型机分区性能指标</s:else>
    </h3>
	<ul class="title-btn">
         <s:if test="deviceType == 'VM'">
         	<li>
	         <s:url id="resViewVmInfoDetailUrl" action="resViewVmInfoDetail">
				<s:param name="vmInfo.vmId">${deviceId}</s:param>
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
         </s:if>
         <s:elseif test="deviceType == 'PM'">
         	<li>
	         <s:url id="resViewPmInfoDetailUrl" action="resViewPmInfoDetail">
				<s:param name="pmInfo.pmId">${deviceId}</s:param>
				<s:param name="nodeType">${nodeType}</s:param>
				<s:param name="nodeId">${nodeId}</s:param>
				<s:param name="treeNodeName">${treeNodeName}</s:param>
				<s:param name="pnodeId">${pnodeId}</s:param>
				<s:param name="pnodeName">${pnodeName}</s:param>
				<s:param name="appId">${appId}</s:param>
				<s:param name="curFun">${curFun}</s:param>
			 </s:url>
			 <a href="${resViewPmInfoDetailUrl}">配置属性</a>
      	  	</li>
      	 </s:elseif>
      	 <s:elseif test="deviceType == 'MINIPM'">
         	<li>
	         <s:url id="miniPmInfoDetailUrl" action="miniPmInfoDetail">
				<s:param name="miniPmInfo.miniPmId">${deviceId}</s:param>
				<s:param name="nodeType">${nodeType}</s:param>
				<s:param name="nodeId">${nodeId}</s:param>
				<s:param name="treeNodeName">${treeNodeName}</s:param>
				<s:param name="pnodeId">${pnodeId}</s:param>
				<s:param name="pnodeName">${pnodeName}</s:param>
				<s:param name="appId">${appId}</s:param>
				<s:param name="curFun">${curFun}</s:param>
			 </s:url>
			 <a href="${miniPmInfoDetailUrl}">配置属性</a>
      	  	</li>
      	 </s:elseif>
      	 <s:else>
			<li>
	         <s:url id="miniPmParInfoDetailUrl" action="miniPmParInfoDetail">
				<s:param name="miniPmParInfo.miniPmParId">${deviceId}</s:param>
				<s:param name="nodeType">${nodeType}</s:param>
				<s:param name="nodeId">${nodeId}</s:param>
				<s:param name="treeNodeName">${treeNodeName}</s:param>
				<s:param name="pnodeId">${pnodeId}</s:param>
				<s:param name="pnodeName">${pnodeName}</s:param>
				<s:param name="appId">${appId}</s:param>
				<s:param name="curFun">${curFun}</s:param>
			 </s:url>
			 <a href="${miniPmParInfoDetailUrl}">配置属性</a>
      	  	</li>
         </s:else>
       <li class="select">
	        <s:url id="realTimeUrl" action="realTimeReport">
				<s:param name="deviceId">${deviceId}</s:param>
				<s:param name="staType">CPU</s:param>
				<s:param name="deviceType">${deviceType}</s:param>
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
<div class="VirtualMachineDetail">
	<P><span style="font-weight:bold;">
		<s:if test="deviceType == 'VM'">虚拟机</s:if>
	    <s:elseif test="deviceType == 'PM'">物理机</s:elseif>
	    <s:elseif test="deviceType == 'MINIPM'">小型机</s:elseif>
	    <s:else>小型机分区</s:else>ID</span>：<s:property value="deviceId"/>
	</P>
	<P>
		<span style="font-weight:bold;">
		<s:if test="deviceType == 'VM'">虚拟机</s:if>
	    <s:elseif test="deviceType == 'PM'">物理机</s:elseif>
	    <s:elseif test="deviceType == 'MINIPM'">小型机</s:elseif>
	    <s:else>小型机分区</s:else>名称</span>：<s:property value="deviceName"/>
	</P>
</div>
<div class="BussHardsoft" >
	<ul>
	   	<li id="cpuLi">
	   		<s:url id="cpuUrl" action="realTimeReport">
				<s:param name="deviceId">${deviceId}</s:param>
				<s:param name="staType">CPU</s:param>
				<s:param name="deviceType">${deviceType}</s:param>
				<s:param name="nodeType">${nodeType}</s:param>
				<s:param name="nodeId">${nodeId}</s:param>
				<s:param name="treeNodeName">${treeNodeName}</s:param>
				<s:param name="pnodeId">${pnodeId}</s:param>
				<s:param name="pnodeName">${pnodeName}</s:param>
				<s:param name="appId">${appId}</s:param>
				<s:param name="curFun">${curFun}</s:param>
			</s:url>
			<a id="cpuUrl" href="${cpuUrl}">CPU</a>
	   	</li>
	    <li id="memLi" >
	    	<s:url id="memUrl" action="realTimeReport">
				<s:param name="deviceId">${deviceId}</s:param>
				<s:param name="staType">MEM</s:param>
				<s:param name="deviceType">${deviceType}</s:param>
				<s:param name="nodeType">${nodeType}</s:param>
				<s:param name="nodeId">${nodeId}</s:param>
				<s:param name="treeNodeName">${treeNodeName}</s:param>
				<s:param name="pnodeId">${pnodeId}</s:param>
				<s:param name="pnodeName">${pnodeName}</s:param>
				<s:param name="appId">${appId}</s:param>
				<s:param name="curFun">${curFun}</s:param>
			</s:url>
			<a id="memUrl" href="${memUrl}">内存</a>
	    </li>
	    <%-- 
	    <li id="diskLi" >
	    	<s:url id="diskUrl" action="realTimeDiskReport">
				<s:param name="deviceId">${deviceId}</s:param>
				<s:param name="deviceType">${deviceType}</s:param>
				<s:param name="nodeType">${nodeType}</s:param>
				<s:param name="nodeId">${nodeId}</s:param>
				<s:param name="treeNodeName">${treeNodeName}</s:param>
				<s:param name="pnodeId">${pnodeId}</s:param>
				<s:param name="pnodeName">${pnodeName}</s:param>
				<s:param name="appId">${appId}</s:param>
				<s:param name="curFun">${curFun}</s:param>
			</s:url>
			<a id="diskUrl" href="${diskUrl}">磁盘</a>
	    </li>
	     --%>
    </ul>
</div>