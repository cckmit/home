<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!-- 资费创建 -->

<div class="page-form" id="vm_charges_edit_dialog" style="display: none;">
	<form id="vm_charges_edit_form" name="vm_charges_edit_form"   action="editChargesAction.action"
		method="post">
		<input id="chargesTypeEdit" name="charges.chargesType" type="hidden"/>
		<input id="chargesId"  name="charges.id" type="hidden"/>
		<div>
			<div class="field" id="cpuDivEdit">
				<div class="caption">CPU个数：</div>
				<div class="content">
					<input id="cpu_numberEdit" name="charges.cpuNumber" class="text" type="text"   onkeyup='this.value=this.value.replace(/[^\d]/gi,"")'
						maxlength="11"  />
				</div>
				<div class="point">(最多11位，由数字组成)</div>
			</div>
			<div class="field" id="memoryDivEdit">
				<div class="caption">内存大小：</div>
				<div class="content">
					<input id="memory_sizeEdit" name="charges.memorySize" class="text"   onkeyup='this.value=this.value.replace(/\D/gi,"")'
						type="text" maxlength="11" />
				</div>
				<div class="point">(最多11位，由数字组成,单位GB)</div>
			</div>
			
			<div class="field">
				<div class="caption">按时费用：</div>
				<div class="content">
					<input id="hour_priceEdit" name="charges.hourPrice" class="text"  onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
						type="text" maxlength="32"  />
				</div>
				<div class="point">(最多11位，格式如:2.00)</div>
			</div>
			
			<div class="field">
				<div class="caption">包月费用：</div>
				<div class="content">
					<input id="month_priceEdit" name="charges.monthPrice" class="text"  onkeyup="this.value=this.value.replace(/[^\d.]/g,'')"
						type="text" maxlength="32"  />
				</div>
				<div class="point">(最多11位，格式如:2.00)</div>
			</div>

			
			<div class="field">
				<div class="caption">描述：</div>
				<div class="content">
					<textarea id="descriptionEdit" name="charges.desc"
						class="uniform" cols="37" rows="3" maxlength="200"></textarea>
				</div>
				<div class="point">(最多256位)</div>
			</div>
		</div>
	</form>
</div>