<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<div id="message" style="display: none;">
		<span class="error"><s:actionerror /></span>
		<span class="error"><s:fielderror /></span>
		<span class="success"><s:actionmessage /></span>
	</div>
       	<h1>资源计量报告</h1>
   	  <div class="details-con">
   	    <div class="detail-title">
          <h3 class="apply-title">资源计量报告</h3>
   	    </div>
        <div class="table-seach ">
        	 <p>
        	选择资源池：<select class="select-big nf" id="respool" name="respoolId"></select>
            </p>
            <p>
        	分区：<select class="select-big nf" id="respoolpart"  name="respoolPartId"></select>
            </p>
        	<p>
        	统计时间：<select class="select-min nf"  id="year" name="year">
                        <option value="00" selected="selected">请选择年</option>
                        <option value="2014">2014</option>
                        <option value="2015">2015</option>
                        <option value="2016">2016</option>
                        <option value="2017">2017</option>
                        <option value="2018">2018</option>
                        <option value="2019">2019</option>
                        <option value="2020">2020</option>
                        <option value="2021">2021</option>
                        <option value="2022">2022</option>
                    </select>年<select class="select-min nf"  id="month" name="month">
                        <option value="00" selected="selected">请选择月</option>
                        <option value="01">01</option>
                        <option value="02">02</option>
                        <option value="03">03</option>
                        <option value="04">04</option>
                        <option value="05">05</option>
                        <option value="06">06</option>
                        <option value="07">07</option>
                        <option value="08">08</option>
                        <option value="09">09</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                    </select>月
            </p>
            <ul class="opt-btn fr">
                <li>
                    <a class="search" href="javascript:void(0);" id="meterage">统计</a>
                </li>
            </ul>
        </div>
	<div class="meterageWrapper">
	<div class="clr"></div>
	<div id="tabNav">
		<ul> 
			<li><a href="#tab0">计量概况</a></li>
<!-- 			<li><a href="#tab1">物理机</a></li> -->
			<li><a href="#tab2">虚拟机</a></li> 
			<li><a href="#tab3">虚拟硬盘</a></li> 
			<%/*<li><a href="#tab4">虚拟机备份</a></li>*/ %> 
		</ul>
		<div class="clr"></div>
	</div>
	<div id="tabs" style="border:0;min-height:300px;">
		<div id="tab0" class="tab">
			<iframe name="allFrame" id="allFrame" src="" width="100%" height="260" style="border-style:none;"></iframe>
		</div>
		<div id="tab1" class="tab">
			<iframe name="pmFrame" id="pmFrame" src="" width="100%" height="260" style="border-style:none"></iframe>
		</div>
		<div id="tab2" class="tab">
			<iframe name="vmFrame" id="vmFrame" src="" width="100%" height="260"  style="border-style:none"></iframe>
		</div>
		<div id="tab3" class="tab">
			<iframe name="Report" src="" id="ebsFrame" width="100%" height="260" style="border-style:none"></iframe>
		</div>
		<%/*<div id="tab4" class="tab">
			<iframe name="Report" src="" id="vmbkFrame" width="100%" height="260" style="border-style:none"></iframe>
		</div>*/ %>
	</div>
</div>
</div>
<script type="text/javascript" src="../scripts/pagesjs/meterage/resource_report.js"></script>
<style>
</style>
