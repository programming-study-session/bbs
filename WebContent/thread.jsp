<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<html>
<body>
<h1>java勉強会</h1>
<a href="http://localhost:8080/SkillShare/thread.jsp"><p>12/22(日)開催予定のJava勉強会について</p></a>
<a href="http://localhost:8080/SkillShare/thread2.jsp"><p>MySQL基本操作について</p></a>
<a href="http://localhost:8080/SkillShare/thread3.jsp"><p>HelloWorldをTomCatで表示</p></a>

<form method="post" action="example.cgi">
<p>トピック<input type="text" name="topic"></p>
<input type="submit" value="作成">
</form>
<p>------------------------------------------------------------------------</p>
<h1>12月22日開催予定のJava勉強会について</h1>
<table border="1">
<tr>
	<th>No</th>
	<th>名前</th>
	<th>メッセージ</th>
	<th>時間</th>
</tr>
<tr>
	<td>1</td>
	<td>日野</td>
	<td>12/21(土)に開催日を変更します</td>
	<td>2019/12/01/14:01.59</td>
</tr>
	<td>2</td>
	<td>細江</td>
	<td>了解しました。</td>
	<td>2019/12/01/14:29.33</td>
</tr>
</table>

<form method="post" action="example.cgi">
<p>名前<input type="text" name="name"></p>
メッセージ<input type="text" name="message">
<input type="submit" value="投稿">

</body>
</html>