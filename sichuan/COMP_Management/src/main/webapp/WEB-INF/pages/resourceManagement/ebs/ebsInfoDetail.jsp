<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
       <h1>云硬盘</h1>
      <div class="details-con">
   	    <div class="detail-title">
            <a href="javascript:void(0)" id='goBack' title="返回">
                <span class="back"></span>
            </a>
          <h3 class="apply-title">云硬盘详情</h3>
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
   	    
   	    
   	    <!-- 云硬盘详细信息 -->
		<div class="page-form">    
		    <div class="contentDetail">
		    	<div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">位置信息</span>
		            </h5>
		            <ul class="result-list" style="width: 50%">
		            	<li><span class="result-name">所属资源池：</span><span><s:property value="ebsInfo.resPoolName" />&nbsp;</span></li>
		               
		            </ul>
		            <ul class="result-list" style="width: 50%">
		            	<li><span class="result-name">挂载虚拟机名称：</span><span><s:property value="ebsInfo.vmName" />&nbsp;</span></li>
		            	
		            </ul>
		        </div>
		        <div class="result" style="width: 130%">
		        	<h5 class="label">
		            	<span class="label-bg">基本信息</span>
		            </h5>
		            <ul class="result-list" style="width: 50%">
		            	<li><span class="result-name">云硬盘ID：</span><span><s:property value="ebsInfo.ebsId" />&nbsp;</span></li>
		                <li><span class="result-name">所属业务组：</span><span><s:property value="ebsInfo.appName" />&nbsp;</span></li>
		                <li><span class="result-name">磁盘空间(GB)：</span><span><s:property value="ebsInfo.diskSize" />&nbsp;</span></li>
		            </ul>
		            <ul class="result-list" style="width: 50%">
		            	<li><span class="result-name">云硬盘名称：</span><span><s:property value="ebsInfo.ebsName" />&nbsp;</span></li>
		            	<li><span class="result-name">状态：</span>
		            	    <span>
						        <s:if test="ebsInfo.curStatus == \"1\""><img style="margin-bottom: -3px" alt="已创建" src="../images/ebs_nomount.png">已创建</s:if>
							<s:elseif test="ebsInfo.curStatus == \"2\""><img style="margin-bottom: -3px" alt="已挂载" src="../images/ebs_mountd.png">已挂载</s:elseif>
							<s:elseif test="ebsInfo.curStatus == \"3\""><img style="margin-bottom: -3px" alt="创建失败" src="../images/ebs_fail.png">创建失败</s:elseif>
							<s:elseif test="ebsInfo.curStatus == \"4\""><img style="margin-bottom: -3px" alt="挂载失败" src="../images/ebs_nomount.png">挂载失败</s:elseif>
							<s:elseif test="ebsInfo.curStatus == \"5\""><img style="margin-bottom: -3px" alt="创建中" src="../images/ebs_processing.png">创建中</s:elseif>
						        &nbsp;
		            	    </span>
		            	</li>
		            	<li><span class="result-name">更新时间：</span><span><s:property value="ebsInfo.updateTime" />&nbsp;</span></li>
		            </ul>
		        </div>
		       
		    </div>	
		</div>
		<div style="padding: 15px;clear: both">
           
		</div>
    </div>
    <div class="page-button">
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
	
	form.action = "resViewEbsListAction.action?ebsInfo.nodeType=" + nodeType + "&ebsInfo.nodeId="
		+ nodeId + "&ebsInfo.treeNodeName="
		+ treeNodeName + "&ebsInfo.pnodeId="
		+ pnodeId + "&ebsInfo.pnodeName="
		+ pnodeName + "&ebsInfo.appId="
		+ appId + "&ebsInfo.curFun=" + curFun;
	form.submit();
}
</script>