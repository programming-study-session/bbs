<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<html>
<body>
	<div align="center">Java勉強会へ</div>
	<br>
	<br>
	<form action="./Login" Method="post">
		<div align="center"><p>ユーザーID<input type="text" name="user_id"/></p></div>
		<div align="center"><p>パスワード<input type="text" name="password"/></p></div>
		<input type="hidden" name="thread_id" value="1" />
		<div align="center"><input type="submit" value="入室"></div>
	</form>
</body>
</html>