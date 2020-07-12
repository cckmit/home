<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="comp-identify" uri="/WEB-INF/taglib/comp-identify"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="right1">
	<h1>概况</h1>
	<ul class="opt-btn fr" style="margin-top: 23px;">
	    <li>       	
			<a class="exportReport" href="../app-download/sichuan_app.zip">app应用下载</a>
        </li>
	</ul> 
	<!-- 概况 -->
	<div class="details-con">
		<%--<div class="ov-block ov-msg">
            	<div class="detail-title">
                	<h3>消息公告</h3>
                </div>
                <ul>
                	<li><a href="javascript:void(0);">【重要通知】10月29日23:00至次日凌晨1：00云服务器将进行服务升级，升级期间管理控制台不可用，新购服务器延迟至升级结束后开通。</a></li>
                </ul>
            </div>--%>

		<%--
          <div class="ov-block">
              <div class="detail-title">
                <h3>服务目录审批</h3>
              </div>
            <ul class="list">
                <li>共有<a href="todo/catalog_list.html"><strong>1</strong></a>个待审批服务目录</li>
            </ul>
            <ul class="list">
              <li><a href="#">小型主机</a></li>
            </ul>
            </div>
             --%>
		<comp-identify:COMPIdentify permissionname="fwtmsp">
			<div class="ov-block">
				<div class="detail-title">
					<h3>服务条目审批</h3>
				</div>
				<img id="serLoad" style="margin:100px;" src="../images/loading.gif" />
				<comp-identify:COMPIdentify permissionname="tmsp">
					<div id="entryCountent"></div>
				</comp-identify:COMPIdentify>
				<comp-identify:COMPIdentify permissionname="fbsp">
				<div id="publishCountent"></div>
				</comp-identify:COMPIdentify>
			</div>
		</comp-identify:COMPIdentify>
		<comp-identify:COMPIdentify permissionname="user_sp">
			<div class="ov-block">
				<div class="detail-title">
					<h3>用户审批</h3>
				</div>
				<img id="serLoad3" style="margin:100px;" src="../images/loading.gif" />
				<div id="userEntryCountent"></div>
				<div id="userEntryList"></div>
			</div>
		</comp-identify:COMPIdentify>
		<comp-identify:COMPIdentify permissionname="order_sp">
			<div class="ov-block">
				<div class="detail-title">
					<h3>订单审批</h3>
				</div>
				<img id="serLoad2" style="margin:100px;" src="../images/loading.gif" />
				<div id="orderEntryCountent"></div>
				<div id="orderEntryList"></div>
			</div>
		</comp-identify:COMPIdentify>
		<div style="clear:both;"></div>
	</div>
</div>
<script type="text/javascript"
	src="../scripts/pagesjs/task/task_overview.js"></script>
