<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<%@page import="java.sql.*"%>


<html>
<body>
	<%
		ResultSet comment_result_set = (ResultSet) request.getAttribute("comment_kekka");
		ResultSet thread_result_set = (ResultSet) request.getAttribute("thread_kekka");
		ResultSet thread_info_result_set = (ResultSet) request.getAttribute("thread_info_kekka");
		String thread_no_info = null;
		String thread_title_info = null;
	%>

	<%
		//スレッド情報の取得
		while(thread_info_result_set.next()){

		thread_no_info = thread_info_result_set.getString(1);
		thread_title_info = thread_info_result_set.getString(2);
		}
	%>

<h1>java勉強会</h1>

		<%
			while (thread_result_set.next()) {
		%>

<p><a href="./Search?thread_id=<%=thread_result_set.getString(1)%>"><%=thread_result_set.getString(2)%></a></p>

		<%
			}
		%>
<fieldset>
		<legend>トピックを追加する</legend>
	<form action="./ThreadEdit" method="POST">
		<input type="hidden" name="UserID" value="00002">
		<input type="text" name="topic" style="width:25.0em;">
		<input type="submit" value="作成">
	</form>
</fieldset>
<p>------------------------------------------------------------------------</p>
<h1><%=thread_title_info%></h1>
<table border="1">
<tr>
	<th>No</th>
	<th>名前</th>
	<th>メッセージ</th>
	<th>時間</th>
</tr>
<%if(comment_result_set!= null) { %>


		<%
			while (comment_result_set.next()) {
		%>
		<tr>
			<td><%=comment_result_set.getString(3)%></td>
			<td><%=comment_result_set.getString(4)%></td>
			<td><%=comment_result_set.getString(5)%></td>
			<td><%=comment_result_set.getString(6)%></td>
		</tr>
		<%
	}
		}
		%>
</table>

<form action="./CommentEdit" method="post">
<br>
	<fieldset>
		<input type="hidden" name="UserId" value="00002">
	<legend>メッセージ</legend>
		<input type="text" name="comment" style="width:40.0em;"style="height:10.0em;">
		<input type="hidden" name="thread_id" value="<%=thread_no_info%>" />
		<input type="submit" value="投稿">
	</fieldset>
</form>

</body>