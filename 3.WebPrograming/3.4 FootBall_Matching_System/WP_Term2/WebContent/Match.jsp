<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="Match.css" rel="stylesheet" type="text/css">
<title>Matching</title>
</head>
<body>
	<h1>매칭을 하기 전 약관 동의</h1>
	<h3>약관</h3>
	<div>
1. 위 홈페이지에 제공하는 개인정보는 웹사이트 운영 및 매칭을 위하여 사용이 되어지고, 매칭을 위해서 타인에게 개인정보를 제공되어질 수 있습니다.<br>
2. 개인정보를 악용하는 경우 법적인 처벌을 받을 수 있습니다.<br>
3. 개인의 정보유출로 인한  피해는 책임을 지지 않습니다. 
	</div>
	<form action = "InputData.jsp" method = "post">
	<input type = radio name=agree value=YES required> 동의함
	<input type = radio name=agree value=NO required> 동의하지 않음.<br><br>
	<input type = submit value = '확인'>
	</form>
</body>
</html>

<!-- 개인정보를 제공해야되는 페이지이기 때문에, 개인정보 제공동의를 받기 위해 만든 jsp
개인정보를 받는데, 다음 InputData.jsp에서 처리하여주는데, 만약 Yes라면 데이터를 입력받고
No라면 초기 화면으로 돌아가게 된다. -->