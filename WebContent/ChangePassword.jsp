<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<%
	String user_id = (String)session.getAttribute("USER_ID");
	String thread_id = (String)session.getAttribute("THREAD_ID");

 %>
<html>
<body>
	<div align="center">�p�X���[�h�̕ύX</div>
	<br>
	<h3>�p�X���[�h�쐬�̃��[��</h3>
	<p>8�����ȏ�,16�����ȓ��ɂ��Ă�������</p>
	<p>�A���t�@�x�b�g�̔��p������,�啶��,�����̒�����Œ�2��ނ͍������Ă�������</p>
	<br>
	<br>
	<form action="./ChangePassword" Method="post">
		<input type="hidden" name="UserID" value="<%=user_id%>">
		<input type="hidden" name="thread_id" value="<%=thread_id%>" />
		<div align="center"><p>�ύX�O�̃p�X���[�h<input type="text" name="now_password"/></p></div>
		<div align="center"><p>�ύX��̃p�X���[�h<input type="text" name="new_password"/></p></div>
		<div align="center"><p>�ē���:�ύX��̃p�X���[�h<input type="text" name="new_password2"/></p></div>
		<div align="center"><input type="submit" value="�ύX"></div>
	</form>
</body>
</html>