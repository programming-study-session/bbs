<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<%
	//�X���b�hID���ƃ��[�U�[ID���擾
	String user_id_set = (String)request.getAttribute("user_id_set");
	String thread_id_set = (String)request.getAttribute("thread_id_set");
%>
<html>
<body>
	<h3>�p�X���[�h�̕ύX�Ɏ��s���܂���</h3>
	<p>���͒��������݂̃p�X���[�h�������́A�m�F�p�p�X���[�h���Ԉ���Ă���\��������܂�</p>
	<p>�p�X���[�h�͔��p�啶���p��,������,��������2��ނ͎g�p���Ă�������</p>
	<p><a href="./Search?thread_id=<%=thread_id_set%>&UserID=<%=user_id_set%>">�f���ֈړ�����</a></p>
	<p><a href="http://localhost:8090/SkillShare/index.jsp">�g�b�v�֖߂�</a></p>
</body>
</html>

<%@ page language="java" contentType="text/html;charset=Windows-31J"%>