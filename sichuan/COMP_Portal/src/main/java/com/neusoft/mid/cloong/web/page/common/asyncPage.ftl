<b>
	每页显示
	<select class="select-small noleft" name="pageSize" onchange="if (this.options[this.selectedIndex].value != '') {
					${jsPageMethod}(encodeURI('${url}?<#if numParam != "">${numParam}&</#if>page=${page}&pageSize=' + this.options[this.selectedIndex].value));}">
					${numPage}
				</select>
	条 共 ${total} 条
</b>
<b>
	<#if firstUrl != "">
	  	<li class="select"><a href="#" onclick="javaScript:${jsPageMethod}(encodeURI('${firstUrl}'));"><<</a></li>
	<#else>
		<li><a href="javascript:void(0);"><<</a></li>
	</#if>
	<#if prevUrl != ""> 
	  	<li class="select"><a href="#" onclick="javaScript:${jsPageMethod}(encodeURI('${prevUrl}'));"><</a></li>
	 <#else>
	  	<li><a href="javascript:void(0);"><</a></li>
	</#if>
	 <b> 第${page}/${totalPageCount}页</b>
	 <#if nextUrl != "">
	  	<li class="select"><a href="#" onclick="javaScript:${jsPageMethod}(encodeURI('${nextUrl}'));">></a></li>
	 <#else>
	  	<li><a href="javascript:void(0);">></a></li>
	 </#if>
	 <#if lastUrl != ""> 
	  	<li class="select"><a href="#" onclick="javaScript:${jsPageMethod}(encodeURI('${lastUrl}'));">>></a></li>
	 <#else>
	  	<li><a href="javascript:void(0);">>></a></li>
	 </#if>
</b>
<b>
  	跳转至第
      <select class="select-small noleft" name="page" onchange="if (this.options[this.selectedIndex].value != '') {
		${jsPageMethod}(encodeURI('${url}?<#if param != "">${param}&</#if>page=' + this.options[this.selectedIndex].value));
			}">${jumpPage}</select>
	页
</b>
