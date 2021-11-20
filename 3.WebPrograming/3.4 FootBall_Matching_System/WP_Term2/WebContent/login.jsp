<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link href="login.css" rel="stylesheet" type="text/css">
<title>login</title>
</head>
<body>

<h1>CNU-WithFoot</h1>
<div>
<h2>자신의 정보를 확인하고 싶으시면 로그인을 해 주세요.</h2>
<form action = "mydata.jsp">
	아이디 : <input name = "id" type = "text" required> <br>
	패스워드 : <input name = "password" type = "password" required>
	<input type = "submit" value = "login">
</form>
<h3>아이디 : 매칭 신청 전화번호 반드시 -를 붙여주세요</h3>
<h4>정보를 입력하지 않으셨나요? <a href = "Match.jsp">정보입력</a>을 누르시면 매칭 전 정보입력 단계로 이동합니다.</h4>
<h4>돌아가고 싶으시면 <a href = "Main.html">돌아가기</a>을 누르세요 </h4>
</div>

<!-- 로그인 화면, 만약 로그인을 할 때, 입력한 정보와 pw가 맞다면, 자신의 데이터를 가져오는 화면을 보여주게된다.
만약 로그인을 해서 성공을 하면 -> 자신의 data를 수정,삭제할 수 있고, 추후에 재 매칭이 가능하게 된다.
로그인을 하는 방법은 간단히 우선 데이터 id와 비밀번호를 치고 login을하면, mydata.jsp에서 받아온 정보를 가지고
로그인을 하게해줄 지 아니면 하지않게 해줄지를 정해준다. 만약 id와 pw가 다르다면, 에러를 띄우고 다시 로그인 화면으로 이동하고
아이디와 패스워드가 일치하다면 ㅇㅇㅇ님 환영합니다! 라는 문구와함께 데이터를 보여주게된다. 로그인 과정은 mydata.jsp에서 설명하도록하겠다.
추가적으로 a tag를 사용하여 정보입력을 누르면 매칭을 하는 곳으로 돌아가기를 누르면 Main.html로 이동하도록 구현하였다.
-->
</body>
</html>