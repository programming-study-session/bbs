<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<html>
<body>
	<div align="center">Java勉強会へ</div>
	<br>
	<br>
	<form action="./Login" Method="post">
		<div align="center"><p>メールアドレス<input type="text" name="mail_adress"/></p></div>
		<div align="center"><p>パスワード<input type="text" name="password"/></p></div>
		<input type="hidden" name="thread_id" value="69" />
		<div align="center"><input type="submit" value="入室"></div>
	</form>
</body>
</html>