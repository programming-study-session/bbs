<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<%@page import="java.sql.*"%>



<html>
<head>
<div align="right"><p><a href="http://localhost:8090/SkillShare/ChangePassword.jsp">�p�X���[�h��ύX����</a></p></div>
</head>
<body>
	<%
		ResultSet comment_result_set = (ResultSet) request.getAttribute("comment_kekka");
		ResultSet thread_result_set = (ResultSet) request.getAttribute("thread_kekka");
		ResultSet thread_info_result_set = (ResultSet) request.getAttribute("thread_info_kekka");
		String user_id_set = (String)request.getAttribute("user_id_set");
		String thread_no_info = null;
		String thread_title_info = null;
		//JSP��JSP�փ��[�U�[ID��n��
		session.setAttribute("USER_ID",user_id_set);
	%>


	<%
		//�X���b�h���̎擾
		while(thread_info_result_set.next()){

		thread_no_info = thread_info_result_set.getString(1);
		thread_title_info = thread_info_result_set.getString(2);
		}
		//JSP��JSP�փX���b�hID��n��//
		session.setAttribute("THREAD_ID",thread_no_info);
	%>

<h1>java�׋���</h1>

		<%
			while (thread_result_set.next()) {
		%>

<p><a href="./Search?thread_id=<%=thread_result_set.getString(1)%>&UserID=<%=user_id_set%>"><%=thread_result_set.getString(2)%></a></p>

		<%
			}
		%>
<fieldset>
		<legend>�g�s�b�N��ǉ�����</legend>
	<form action="./ThreadEdit" method="POST">
		<input type="hidden" name="UserID" value="<%=user_id_set%>">
		<input type="text" name="topic" style="width:25.0em;">
		<input type="submit" value="�쐬">
	</form>
</fieldset>
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
<br>
	<fieldset>
		<input type="hidden" name="UserId" value="<%=user_id_set%>">
	<legend>���b�Z�[�W</legend>
		<input type="text" name="comment" style="width:40.0em;"style="height:10.0em;">
		<input type="hidden" name="thread_id" value="<%=thread_no_info%>" />
		<input type="submit" value="���e">
	</fieldset>
</form>

</body>