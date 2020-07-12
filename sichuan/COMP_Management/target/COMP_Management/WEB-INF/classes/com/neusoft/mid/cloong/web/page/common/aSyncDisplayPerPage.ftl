<b>
             共 ${total} 条
</b>
<b>
	<#if firstUrl != "">
	  	<li class="select"><a href="#" onclick="javaScript:${jsPageMethod}(encodeURI('${firstUrl}'), this.parentElement.parentElement.parentElement.id);"><<</a></li>
	<#else>
		<li><a href="javascript:void(0);"><<</a></li>
	</#if>
	<#if prevUrl != ""> 
	  	<li class="select"><a href="#" onclick="javaScript:${jsPageMethod}(encodeURI('${prevUrl}'), this.parentElement.parentElement.parentElement.id);"><</a></li>
	 <#else>
	  	<li><a href="javascript:void(0);"><</a></li>
	</#if>
	 <b style="float:left"> 第${page}/${totalPageCount}页</b>
	 <#if nextUrl != "">
	  	<li class="select"><a href="#" onclick="javaScript:${jsPageMethod}(encodeURI('${nextUrl}'), this.parentElement.parentElement.parentElement.id);">></a></li>
	 <#else>
	  	<li><a href="javascript:void(0);">></a></li>
	 </#if>
	 <#if lastUrl != ""> 
	  	<li class="select"><a href="#" onclick="javaScript:${jsPageMethod}(encodeURI('${lastUrl}'), this.parentElement.parentElement.parentElement.id);">>></a></li>
	 <#else>
	  	<li><a href="javascript:void(0);">>></a></li>
	 </#if>
</b>
<b>
  	第<!-- 因为页面上使用了$.uniform.update()样式，在select外新加了一个div，所以此处多了一个.parentElement -->
      <select class="select-small noleft" name="page" onchange="if (this.options[this.selectedIndex].value != '') {
		getPageData(encodeURI('${url}?<#if param != "">${param}&</#if>page=' + this.options[this.selectedIndex].value), this.parentElement.parentElement.parentElement.id);
			}">${jumpPage}</select>
	页
</b>
