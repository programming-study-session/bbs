<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<%
	//�X���b�hID���ƃ��[�U�[ID���擾
	String user_id_set = (String)request.getAttribute("user_id_set");
	String thread_id_set = (String)request.getAttribute("thread_id_set");
%>
<html>
<body>
 <h3>�p�X���[�h���ύX�ł��܂���</h3>
 <p><a href="./Search?thread_id=<%=thread_id_set%>&UserID=<%=user_id_set%>">�f���ֈړ�����</a></p>
 <a href="http://localhost:8090/SkillShare/index.jsp">�g�b�v�֖߂�</a>
</body>
</html>