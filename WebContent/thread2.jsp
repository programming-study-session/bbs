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
		//�X���b�h���̎擾
		while(thread_info_result_set.next()){
		
		thread_no_info = thread_info_result_set.getString(1);
		thread_title_info = thread_info_result_set.getString(2);
	}
	%>

<h1>java�׋���</h1>

		<%
			while (thread_result_set.next()) {
		%>

<p><a href="./Search?thread_id=<%=thread_result_set.getString(1)%>"><%=thread_result_set.getString(2)%></a></p>

		<%
			}
		%>
<form action="./ThreadEdit" method="POST">
<p>�g�s�b�N<input type="text" name="topic"></p>
<input type="submit" value="�쐬">
</form>
<p>------------------------------------------------------------------------</p>
<h1><%=thread_title_info%></h1>
<table border="1">
<tr>
	<th>No</th>
	<th>���O</th>
	<th>���b�Z�[�W</th>
	<th>����</th>
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
<p>���O<input type="text" name="name"></p>
<p>���b�Z�[�W<input type="text" name="comment"></p>
<input type="hidden" name="thread_id" value="<%=thread_no_info%>"/>
	<input type="submit" value="���e">
</form>
</body>