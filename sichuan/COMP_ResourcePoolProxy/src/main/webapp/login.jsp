<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 
<head>
<title>登录</title>
</head>
<body>

		     
			<form method="post" action="loginAction.action" onsubmit="return check();">
			<ul>
				<li>
					用户ID:
					<br />
					<input type="text" size="16" id="userId" name="userId"/>
				</li>
				<li>
					<input class="bt" type="submit" value="登录"/>
				</li>

			</ul>
			</form>

</body>	
</html>