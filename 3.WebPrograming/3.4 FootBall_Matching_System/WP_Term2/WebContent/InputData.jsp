<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="InputData.css" rel="stylesheet" type="text/css">
<title>개인정보 제공</title>
</head>
<body>
<% String result = request.getParameter("agree"); 
if(result.equals("YES")){%>
	<h1>풋살 매칭을 위한 개인정보 기입</h1>
	<h3>풋살 경기 5 vs 5 </h3>
	<form action = "submit.jsp" method = "post">
	대표자 성명 : <input type = text name=name required><br>
	연락처 (-을 반드시 써 주세요):    <input type = tel name=tel pattern="^\d{3}-\d{3,4}-\d{4}$" required><br>
	인원 수 :<input type = number name = num min = 5 required><br>
	날짜 : <input type = date name = date required><br><br>
	-장소를 선택하세요-<br>
	<input type = radio name=location value=A required> 강변 풋살장<br>
	<input type = radio name=location value=B required> 케이지 풋살<br><br>
	-상대팀에게 한마디-<br>
	<textarea name = "tell"></textarea><br>
	비밀번호 : <input type = password name = "pw" required> <br>
	<input type = submit value = '올리기'>
	</form>
	<form action = "Main.html">
	<input type = submit value = "돌아가기">
	</form>
	<%}
	else{
		response.sendRedirect("Main.html");
	}%>
</body>
</html>

<!-- 풋살 매칭을 위하여, 개인정보를 기입하는 페이지이다. 자신의 정보들을 모두 기록하고난 뒤 저장을 하게되면 정보를 서버에 저장하게 되고,
 submit.jsp로 이동을 하게된다. -->