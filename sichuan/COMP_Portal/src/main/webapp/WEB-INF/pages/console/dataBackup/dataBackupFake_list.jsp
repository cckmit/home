<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="list">
	<div class="title">
		<h2>数据备份列表</h2>
		<div id="message" style="display: none;">
			<span id="error" class="error"><s:actionerror /></span> <span
				class="error"><s:fielderror /></span> <span class="success"><s:actionmessage /></span>
		</div>
	</div>

	<div class="list-items ebs-list">
			<ul id="resultList" class="items">
						
			 <li>
			  <a href="javascript:void(0);" onclick="jumpTO(1)">
			    <div class="item-status blue">正常</div>
			    <div class="item-content">
			      <h3>dataBackStorage1</h3>
			      <div class="detail">
			        <div class="detail-col">
					  <div class="detail-row">
						 <span>用户名：</span><span>dsjfhqy</span>
					  </div>
			     	  <div class="detail-row">
						 <span>文件URL：</span><span>/home/storage/data/dsjfhqy/dataBackStorage1</span>
					  </div>
					</div>  
					<div class="detail-col">  					 
					  <div class="detail-row">
						 <span>数据大小：</span><span>0.5GB</span>
					  </div>
			     	  <div class="detail-row">
						 <span>所属服务器IP地址：</span><span>10.10.100.1</span>
					  </div>			   
			      </div>
			    </div>
			   </div>			  
			  </a></li>
			  
			  <li>
			   <a href="javascript:void(0);" onclick="jumpTO(2)">
			    <div class="item-status blue">正常</div>
			    <div class="item-content">
			      <h3>dataBackStorage2</h3>
			      <div class="detail">
			        <div class="detail-col">
					  <div class="detail-row">
						 <span>用户名：</span><span>duanyunp</span>
					  </div>
			     	  <div class="detail-row">
						 <span>文件URL：</span><span>/home/storage/data/duanyunp/dataBackStorage2</span>
					  </div>
					</div>  
					<div class="detail-col">  					 
					  <div class="detail-row">
						 <span>数据大小：</span><span>0.8GB</span>
					  </div>
			     	  <div class="detail-row">
						 <span>所属服务器IP地址：</span><span>56aa:55dd:d:2ef:0:34::20</span>
					  </div>			   
			      </div>
			    </div>
			   </div>
			   </a>
			  </li>	
			  
			  <li>
			  <a href="javascript:void(0);" onclick="jumpTO(3)">
			    <div class="item-status blue">正常</div>
			    <div class="item-content">
			      <h3>dataBackStorage3</h3>
			      <div class="detail">
			        <div class="detail-col">
					  <div class="detail-row">
						 <span>用户名：</span><span>kudhrhgsy</span>
					  </div>
			     	  <div class="detail-row">
						 <span>文件URL：</span><span>/home/storage/data/kudhrhgsy/dataBackStorage3</span>
					  </div>
					</div>  
					<div class="detail-col">  					 
					  <div class="detail-row">
						 <span>数据大小：</span><span>1.6GB</span>
					  </div>
			     	  <div class="detail-row">
						 <span>所属服务器IP地址：</span><span>10.100.100.35</span>
					  </div>			   
			      </div>
			    </div>
			   </div>
			  </a></li>	
			  
			  <li>
			  <a href="javascript:void(0);" onclick="jumpTO(4)">
			    <div class="item-status blue">正常</div>
			    <div class="item-content">
			      <h3>dataBackStorage4</h3>
			      <div class="detail">
			        <div class="detail-col">
					  <div class="detail-row">
						 <span>用户名：</span><span>udshrgai</span>
					  </div>
			     	  <div class="detail-row">
						 <span>文件URL：</span><span>/home/storage/data/udshrgai/dataBackStorage4</span>
					  </div>
					</div>  
					<div class="detail-col">  					 
					  <div class="detail-row">
						 <span>数据大小：</span><span>1.3GB</span>
					  </div>
			     	  <div class="detail-row">
						 <span>所属服务器IP地址：</span><span>10.100.10.111</span>
					  </div>			   
			      </div>
			    </div>
			   </div>
			  </a></li>	
			  
			  <li>
			  <a href="javascript:void(0);" onclick="jumpTO(5)">
			    <div class="item-status blue">正常</div>
			    <div class="item-content">
			      <h3>dataBackStorage5</h3>
			      <div class="detail">
			        <div class="detail-col">
					  <div class="detail-row">
						 <span>用户名：</span><span>ierbgsus</span>
					  </div>
			     	  <div class="detail-row">
						 <span>文件URL：</span><span>/home/storage/data/ierbgsus/dataBackStorage5</span>
					  </div>
					</div>  
					<div class="detail-col">  					 
					  <div class="detail-row">
						 <span>数据大小：</span><span>0.3GB</span>
					  </div>
			     	  <div class="detail-row">
						 <span>所属服务器IP地址：</span><span>10.100.10.50</span>
					  </div>			   
			      </div>
			    </div>
			   </div>
			  </a></li>	
		  			  					
			</ul>
			
			<div class="pageBar">
				<b>每页显示
	            <select class="select-small noleft" name="pageSize" onchange="if (this.options[this.selectedIndex].value != '') {
					location = '/COMP_Portal/console/vFWListAction.action?page=1&amp;pageSize=' + this.options[this.selectedIndex].value;}">
					<option value="10" selected="">10</option><option value="20">20</option><option value="30">30</option><option value="40">40</option><option value="50">50</option><option value="60">60</option><option value="70">70</option><option value="80">80</option><option value="90">90</option><option value="100">100</option>
				</select>
	                                                    条 共 5 条
               </b>
               <b>
		        <li><a href="javascript:void(0);">&lt;&lt;</a></li>
	  	        <li><a href="javascript:void(0);">&lt;</a></li>
	           <b> 第1/1页</b>
	  	        <li class="select"><a href="#" onclick="javaScript:window.location='/COMP_Portal/console/vFWListAction.action?page=2';">&gt;</a></li>
	  	        <li class="select"><a href="#" onclick="javaScript:window.location='/COMP_Portal/console/vFWListAction.action?page=3';">&gt;&gt;</a></li>
               </b>
               <b>
  	                                              跳转至第
                <select class="select-small noleft" name="page" onchange="if (this.options[this.selectedIndex].value != '') {
		           location = '/COMP_Portal/console/vFWListAction.action?page=' + this.options[this.selectedIndex].value;
			       }"><option value="1" selected="">1</option><option value="2">2</option><option value="3">3</option></select>
	                                              页
               </b>
			</div>
			
	</div>
</div>

<script type="text/javascript"
	src="../scripts/pagesjs/console/dataBackup/dataBackupFake_list.js"></script>