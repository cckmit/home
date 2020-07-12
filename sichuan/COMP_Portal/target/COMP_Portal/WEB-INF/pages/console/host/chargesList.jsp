<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>





<div id="chargesListDiv" style="display: none;">
	<div class="toolbar">
		<span class="keyName">CPU：</span><input type="text" id="cpuNumber"
			name="charges.cupNumber" /> <span class="keyName">内存大小：</span> <input
			type="text" id="memorySize" name="charges.memorySize" /> <a
			class="search btn fr" href="javascript:initChargesInfo()">查询</a>
	</div>

	<table class="table" id="businessListTab">
		<thead>
			<tr>
				<th>CPU</th>
				<th>内存大小</th>
				<th>按时长</th>
				<th>包月费</th>
				<th>描述</th>
			</tr>
		</thead>
		<tbody id="chargesListTbody">
		</tbody>
	</table>
	<div class="pageBar" id="chargesListPageBarDiv"
		style="text-align: center"></div>
</div>