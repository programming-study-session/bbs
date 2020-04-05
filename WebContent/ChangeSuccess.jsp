<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<%
	//スレッドID情報とユーザーIDを取得
	String user_id_set = (String)request.getAttribute("user_id_set");
	String thread_id_set = (String)request.getAttribute("thread_id_set");
%>
<html>
<body>
 <h3>パスワードが変更できました</h3>
 <p><a href="./Search?thread_id=<%=thread_id_set%>&UserID=<%=user_id_set%>">掲示板へ移動する</a></p>
 <a href="http://localhost:8090/SkillShare/index.jsp">トップへ戻る</a>
</body>
</html>