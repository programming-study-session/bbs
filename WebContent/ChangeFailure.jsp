<%@ page language="java" contentType="text/html;charset=Windows-31J"%>
<%
	//スレッドID情報とユーザーIDを取得
	String user_id_set = (String)request.getAttribute("user_id_set");
	String thread_id_set = (String)request.getAttribute("thread_id_set");
%>
<html>
<body>
	<h3>パスワードの変更に失敗しました</h3>
	<p>入力頂いた現在のパスワードもしくは、確認用パスワードが間違っている可能性があります</p>
	<p>パスワードは半角大文字英字,小文字,数字から2種類は使用してください</p>
	<p><a href="./Search?thread_id=<%=thread_id_set%>&UserID=<%=user_id_set%>">掲示板へ移動する</a></p>
	<p><a href="http://localhost:8090/SkillShare/index.jsp">トップへ戻る</a></p>
</body>
</html>

<%@ page language="java" contentType="text/html;charset=Windows-31J"%>