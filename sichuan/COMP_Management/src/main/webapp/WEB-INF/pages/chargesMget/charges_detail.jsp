<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- 资费创建 -->

<div class="page-form" id="vm_charges_detail_dialog" style="display: none;">
		<div>
			<div class="field" id="cpuDivDetail">
				<div class="caption">CPU个数：</div>
				<div class="content">
					<input id="cpu_numberDetail"  class="text" type="text"  style="border: 1px; "  readonly="readonly"   />
				</div>
				<div class="caption">内存大小：</div>
				<div class="content">
					<input id="memory_sizeDetail"  class="text" type="text"  style="border: 1px; "  readonly="readonly"   />
				</div>
			</div>
			
			<div class="field">
				<div class="caption">按时费用：</div>
				<div class="content">
					<input id="hour_priceDetail"  class="text"  type="text"  style="border: 1px; "  readonly="readonly"   />
				</div>
				<div class="caption">包月费用：</div>
				<div class="content">
					<input id="month_priceDetail"  class="text"  type="text"  style="border: 1px; "  readonly="readonly"   />
				</div>
			</div>
			
			<div class="field">
				<div class="caption">创建时间：</div>
				<div class="content">
					<input id="createTimeDetail"  class="text"  type="text"  style="border: 1px; "  readonly="readonly"   />
				</div>
				<div class="caption">更新时间：</div>
				<div class="content">
					<input id="updateTimeDetail"  class="text"  type="text"  style="border: 1px; "  readonly="readonly"   />
				</div>
			</div>
			
			<div class="field">
				<div class="caption">创建人：</div>
				<div class="content">
					<input id="createUserDetail"  class="text"  type="text"  style="border: 1px; "  readonly="readonly"   />
				</div>
				<div class="caption">描述：</div>
				<div class="content">
					<input id="descriptionDetail"  class="text"  type="text"  style="border: 1px; "  readonly="readonly"   />
				</div>
			</div>
	 </div>
</div>