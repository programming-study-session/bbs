<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<%
	String user_id = (String)session.getAttribute("USER_ID");
	String thread_id = (String)session.getAttribute("THREAD_ID");

 %>
<html>
<body>
	<div align="center">パスワードの変更</div>
	<br>
	<h3>パスワード作成のルール</h3>
	<p>8文字以上,16文字以内にしてください</p>
	<p>アルファベットの半角小文字,大文字,数字の中から最低2種類は混合してください</p>
	<br>
	<br>
	<form action="./ChangePassword" Method="post">
		<input type="hidden" name="UserID" value="<%=user_id%>">
		<input type="hidden" name="thread_id" value="<%=thread_id%>" />
		<div align="center"><p>変更前のパスワード<input type="text" name="now_password"/></p></div>
		<div align="center"><p>変更後のパスワード<input type="text" name="new_password"/></p></div>
		<div align="center"><p>再入力:変更後のパスワード<input type="text" name="new_password2"/></p></div>
		<div align="center"><input type="submit" value="変更"></div>
	</form>
</body>
</html>