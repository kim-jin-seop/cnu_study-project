<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import ="java.io.FileReader"%>
<%@ page import ="java.io.BufferedReader" %>
<%@ page import="java.io.File" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="mydata.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>내 정보</title>
</head>
<body>
<%
String filePath = request.getRealPath("") + "/data/";
File f = new File(filePath); 
File[] files = f.listFiles();

String id = (String)request.getParameter("id") + ".txt";
String password = (String)request.getParameter("password"); 
boolean find = false;
for(int i = 0 ; i < files.length; i ++){
	if(id.equals(files[i].getName())){
		BufferedReader br = new BufferedReader(new FileReader(files[i]));
		String pw = br.readLine();
		br.close();
		if(password.equals(pw))
			find = true;
	}
}
if(find){
	String path = request.getRealPath("") + "/data/" + id;
	BufferedReader br = new BufferedReader(new FileReader(new File(path)));
	String pw = br.readLine();
	String name = br.readLine();
	String tel = br.readLine();
	String num = br.readLine();
	String date = br.readLine();
	String place = br.readLine();
	String tell = br.readLine();
	br.close();
	%>
	<script>
	alert("<%=name%>님 환영합니다!");
	
	function remove(){
		if(confirm("정말 삭제하시겠습니까?")){
			<%
			File file = new File(path);
			if (file.delete()) {
				%>alert("삭제가 완료 되었습니다.");
				location.href = "Main.html"; <%
			}
			else {
				%>alert("삭제에 실패했습니다.");<%
			}
			%>
			return;
		}
		alert("삭제가 취소되었습니다.")
	}
	</script>
	<h1><%=name%>님의 개인정보 </h1>
	<form action = "submit.jsp" method = "post">
	대표자 성명 :<input type = "text" name = "name" value = <%=name%>><br>
	연락처 (-을 반드시 써 주세요):    <input type = tel value = <%=tel%> name=tel pattern="^\d{3}-\d{3,4}-\d{4}$" required><br>
	인원 수 :<input type = number value = <%=num%> name = num min = 5 required><br>
	날짜 : <input type = date value = <%=date%> name = date required><br><br>
	-장소를 선택하세요-<br>
	<%
	if(place.equals("A")){%>
		<input type = radio name=location value=A checked> 강변 풋살장<br>
		<input type = radio name=location value=B> 케이지 풋살<br><br>
		<%
	}
	else{%>
		<input type = radio name=location value=A> 강변 풋살장<br>
		<input type = radio name=location value=B checked> 케이지 풋살<br><br><%
	}
	%>
	-상대팀에게 한마디-<br>
	<textarea name = "tell"><%=tell%></textarea><br>
	비밀번호 : <input type = password name = "pw" required> <br>
	<input type = submit value = '수정완료 및 매칭하러 가기'>
	<input type = button value = '삭제하기' onclick = "remove()">
	</form>
	<div id = "forremove"></div>
	<%
}
else{
	%>
	<script>
	alert("아이디와 비밀번호를 확인하세요!");
	location.href = "login.jsp";
	</script>
	<%
}
%>
<%--로그인을 한 결과를 보여주는 화면 수정을 원한다면, 수정 후 저장버튼을 누르고, 삭제를 원하면 삭제버튼을 누르고 재매칭 버튼을 누르면 자동으로 타인 랜덤으로 정보를 가져온다.
로그인을 하는 방법은 우선 file의 이름들과 id가 일치하는지 확인하고 만약 일치한다면 그 파일을 한번 읽어 pw를 가져오고 비교하여 일치하면 로그인 성공 실패하면 로그인 실패를 한다.
그리고 모든 데이터를 가져와서 각각에 우선 value로 넣어줌으로써 우선 기본적인 나의 정보를 보여주게된다.
--%>
</body>
</html>