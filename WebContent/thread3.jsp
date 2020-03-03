<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<html>
<body>
<h1>java勉強会</h1>
<p><a href="http://localhost:8080/SkillShare/thread.jsp">12/22(日)開催予定のJava勉強会について</a></p>
<p><a href="http://localhost:8080/SkillShare/thread2.jsp">MySQL基本操作について</a></p>
<p><a href="http://localhost:8080/SkillShare/thread3.jsp">HelloWorldをTomCatで表示</a></p>

<form method="post" action="example.cgi">
<p>トピック<input type="text" name="topic"></p>
<input type="submit" value="作成">
</form>
<p>------------------------------------------------------------------------</p>
<h1>HelloWorldをTomCatで表示</h1>
<table border="1">
<tr>
	<th>No</th>
	<th>名前</th>
	<th>メッセージ</th>
	<th>時間</th>
</tr>
<tr>
	<td>1</td>
	<td>鈴木(裕)</td>
	<td>以下のサイトを参考にしてくださいhttps://www.searchman.info/java_eclipse/1080.html</td>
	<td>2019/12/01/14:01.59</td>
</tr>
</table>

<form method="post" action="example.cgi">
<p>名前<input type="text" name="name"></p>
メッセージ<input type="text" name="message">
<input type="submit" value="投稿">
</form>
</body>